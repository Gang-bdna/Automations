package main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import Login.Login;
import elementCheck.AttrCollect;
import elementCheck.CheckElement;
import elementCheck.CheckLoading;
import elementCheck.CreateUser;
import elementCheck.smokeCheck;
import fileOperate.IndexID;
import fileOperate.loadConfig;
import fileOperate.loadRoles;
import global.Utils;

public class LoadPages {
    private static String configfile = "Role_Assignment.cfg";
    private static HashMap<String, String> userdefine = new HashMap<>();
    private static HashMap<String, String> IDPairs = new HashMap<>();
    public static HashMap<String, ArrayList<String>> PredefineRoles = new HashMap<>();
    private static HashMap<String, ArrayList<String>> MappedID = new HashMap<>();
	private static ArrayList<String> eleList = new ArrayList<>();
	private static ArrayList<String> userList = new ArrayList<>();
    public static String logfile;
    public static String username;
    public static String password;
    public static String url;
    private static String checklist;
    private static String result;
    
	public static void initConf(HashMap<String, String> conf){
		try{
			String line="";
			BufferedReader br = new BufferedReader(new FileReader(configfile));
			while((line = br.readLine()) != null){
				if(line.contains("@")){
						String [] ele = line.split("	");
						userdefine.put(ele[1], ele[2]);
				}
			}
			br.close();
		}catch (Exception io){
			Utils.error("Cannot load config file!");
		}
		//Loading config for the file used to save final diff results
		if(conf.containsKey("Location")){
			
			String loc = conf.get("Location");
			File file = new File(loc);
			if(!file.exists()){
				file.mkdirs();
			}
			
			logfile = loc + "/Role_Assignment.log";
			Utils.conf("Log file from Console is captuered.");
			//initialize result
			result = loc + "/Role_Assignment.csv";
		}else{
			
			String loc = userdefine.get("LOCATION");
			File file = new File(loc);
			if(!file.exists()){
				file.mkdirs();
			}
			
			logfile = loc + "/Role_Assignment.log";
			Utils.conf("Default Log file is applied.");
			//initialize result
			result = loc + "/Role_Assignment.csv";
		}
		//Loading UserName 
		if(conf.containsKey("UserName")){
			username = conf.get("UserName");
			Utils.conf("UserName from Console is captuered.");
		}else{
			username = userdefine.get("USERNAME");
			Utils.conf("Default UserName from config file is applied.");
		}
		
		//Loading Password
		if(conf.containsKey("Password")){
			password = conf.get("Password");
			Utils.conf("PassWord from Console is captuered.");
		}else{
			password = userdefine.get("PASSWORD");
			Utils.conf("Default Password from Config file is applied.");
		}
				
		//Loading Password
		if(conf.containsKey("URL")){
			url = conf.get("URL");
			Utils.conf("DP URL info from Console is captuered.");
		}else{
			url = userdefine.get("URL");
			Utils.conf("Default DP URL info from Config file applied.");
		}
		
		//Loading User List
		if(conf.containsKey("UserList")){
			checklist = conf.get("UserList");
			Utils.conf("User Name List is captuered.");
		}else{
			checklist = userdefine.get("USERLIST");
			Utils.conf("Default User Name List is applied.");
		}
    }
	
	public static void funCkc(WebDriver driver) throws InterruptedException, IOException{
		//Initialize Writer
		FileWriter docWrite;
		BufferedWriter bw = null;
		
	    //Login to Admin homepage
	    Login.login(username, password, url);
	    
	    //Loading Users vs Roles from User List
	    loadRoles.LoadCheckList(checklist);
	    
	    //Loading checklist from config file
	    eleList.add("Security");
	    userList = loadRoles.getUserList();
	    
	    //Check functional elements
	    Utils.info("Start to check functional elements!");
	    for(String ele: eleList){
		    smokeCheck.Navigate(driver, "id", "slideMenu");
		    
		    Utils.info("Start to load " + ele + " page");
		    smokeCheck.Navigate(driver, "xpath", ele);
		    Thread.sleep(1000);

		    int timer = 0;
		    while(!(CheckElement.find("id", "refreshBtn") || CheckElement.find("id", "submitKey") || CheckElement.find("id", "NormlizeHelp")
	    			|| CheckElement.find("id", "btnNewRole") || CheckElement.find("id", "btnCataSync"))){
		    	Thread.sleep(1000);
		    	++ timer;
		    	if(timer == 120){
			    	if(CheckElement.find("id", "refreshBtn") || CheckElement.find("id", "submitKey") || CheckElement.find("id", "NormlizeHelp")
			    			|| CheckElement.find("id", "btnNewRole") || CheckElement.find("id", "btnCataSync")){
			    		Utils.info(ele + " Page is up.");
			    		Utils.cout('\n');
			    	}
			    	else if(CheckElement.find("id", "errorTitle")){
			    		Utils.error("Error detected while loading" + ele + "page");
			    		Utils.cout('\n');
			    	}
			    	else{
			    		Utils.warning("Openning " + ele + " page is timeout (2 min).");
			    	}
		    	break;	
		    	}
		    }
		    while(CheckElement.find("id", "refreshBtn") || CheckElement.find("id", "submitKey") || CheckElement.find("id", "NormlizeHelp")
	    			|| CheckElement.find("id", "btnNewRole") || CheckElement.find("id", "btnCataSync")){
	    		Utils.info(ele + " Page is up.");
	    		Utils.cout('\n');
	    		break;
		    }
		    
		    //Retrieve Role-User maps from "New Role Assignment page
		    IDPairs = AttrCollect.attrClt(driver);
		    
		    //Loading roles map from config file
		    PredefineRoles = loadRoles.getRoles();
		    
		    //Index roles to pairs<Users, Role-ID>
		    MappedID = IndexID.map(IDPairs, PredefineRoles);
		    
		    //Back to home page
			CheckLoading.ckLoad("id", "btnCancel", "");
			driver.findElement(By.id("btnCancel")).click();
			Thread.sleep(2000);
		    
		    //Start to add users
		    Utils.info("Start to add new Users from User List...");
		    for(String user : userList){
		    	driver.switchTo().defaultContent();
			    CreateUser.add(driver, user, MappedID);
			    driver.navigate().refresh();
			    Thread.sleep(1000);
		    }

		    //Write results
			try {
				//result = result.substring(0, result.length()-4) + "_" + url + ".csv";
				docWrite = new FileWriter(result);
				bw = new BufferedWriter(docWrite); 
				
				//write title
				bw.append("UserName").append(",").append("Result");
				bw.flush();
				bw.newLine();
				
			} catch (IOException e) {
				Utils.error("Initialize "+ result +" file failed");
			}
			
		    HashMap<String,String> results = new HashMap<>();
		    Set<String> names = new HashSet<>();
		    results = CreateUser.getResults();
		    names = results.keySet();
		    for(String name : names){
				try {
					bw.append(name).append(",").append(results.get(name));
					bw.flush();
					bw.newLine();
				} catch (IOException e) {
					Utils.error("Failed to write result.");
				}
		    }
		    try {
				bw.close();
			} catch (IOException e) {
			}
//			driver.navigate().back();
//			driver.switchTo().defaultContent();
	    }
	}
}
