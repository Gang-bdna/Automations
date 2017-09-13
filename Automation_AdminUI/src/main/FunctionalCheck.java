package main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.openqa.selenium.WebDriver;

import Login.Login;
import elementCheck.CheckElement;
import elementCheck.smokeCheck;
import fileOperate.loadConfig;
import global.Utils;

public class FunctionalCheck {
    private static String configfile = "AdminUI_SmokeCheck.cfg";
    private static HashMap<String, String> userdefine = new HashMap<>();
	private static ArrayList<String> eleList = new ArrayList<>();
    public static String logfile;
    public static String username;
    public static String password;
    public static String url;
    private static String location = "Output/Admin_SmokeCheck.log";
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
			
			logfile = loc + "/Admin_SmokeCheck.log";
			Utils.conf("Log file from Console is captuered.");
			//initialize result
			result = loc + "/Admin_SmokeCheck.csv";
		}else{
			
			String loc = userdefine.get("LOCATION");
			File file = new File(loc);
			if(!file.exists()){
				file.mkdirs();
			}
			
			logfile = loc + "/Admin_SmokeCheck.log";
			Utils.conf("Default Log file is applied.");
			//initialize result
			result = loc + "/Admin_SmokeCheck.csv";
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
		
		//Loading CheckList
		if(conf.containsKey("CheckList")){
			checklist = conf.get("CheckList");
			Utils.conf("Output location for results and logs is captuered.");
		}else{
			checklist = userdefine.get("CHECKLIST");
			Utils.conf("Default CheckList location is applied.");
		}
    }
	
	public static void funCkc(WebDriver driver) throws InterruptedException{
		//Initialize Writer
		FileWriter docWrite;
		BufferedWriter bw = null;
		try {
			docWrite = new FileWriter(result);
			bw = new BufferedWriter(docWrite); 
			
			//write title
			bw.append("FEATURES").append(",").append("RESULT");
			bw.flush();
			bw.newLine();
			
		} catch (IOException e) {
			Utils.error("Initialize "+ result +" file failed");
		}
		
	    //Login to Admin homepage
	    Login.login(username, password, url);
	    
	    //Loading checklist from config file
	    eleList = loadConfig.LoadCheckList(checklist);
	    
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
			    		try {
							bw.append(ele).append(",").append("Passed");
							bw.flush();
							bw.newLine();
						} catch (IOException e) {
						}
			    	}
			    	else if(CheckElement.find("id", "errorTitle")){
			    		Utils.error("Error detected while loading" + ele + "page");
			    		Utils.cout('\n');
			    		
			    		try {
							bw.append(ele).append(",").append("Failed");
							bw.flush();
							bw.newLine();
						} catch (IOException e) {
						}
			    	}
			    	else{
			    		Utils.warning("Openning " + ele + " page is timeout (2 min).");
			    		
			    		try {
							bw.append(ele).append(",").append("TimeOut");
							bw.flush();
							bw.newLine();
						} catch (IOException e) {
						}
			    	}
		    	break;	
		    	}
		    }
		    while(CheckElement.find("id", "refreshBtn") || CheckElement.find("id", "submitKey") || CheckElement.find("id", "NormlizeHelp")
	    			|| CheckElement.find("id", "btnNewRole") || CheckElement.find("id", "btnCataSync")){
	    		Utils.info(ele + " Page is up.");
	    		Utils.cout('\n');
	    		try {
					bw.append(ele).append(",").append("Passed");
					bw.flush();
					bw.newLine();
				} catch (IOException e) {
				}
	    		break;
		    }
		    
			driver.navigate().back();
			driver.switchTo().defaultContent();
	    }
	}
}
