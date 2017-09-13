package main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import org.openqa.selenium.WebDriver;

import Login.Login;
import elementCheck.CheckElement;
import elementCheck.smokeCheck;
import fileOperate.loadConfig;
import global.Utils;

public class BannerCheck {
    private static String configfile = "Input/BannerCheck.cfg";
    private static HashMap<String, String> userdefine = new HashMap<>();
	private static ArrayList<String> eleList = new ArrayList<>();
    public static String logfile;
    public static String username;
    public static String password;
    public static String url;
    //private static String location = "Output/Admin_SmokeCheck.log";
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
			
			logfile = loc + "/Banner_Check.log";
			Utils.conf("Log file from Console is captuered.");
			//initialize result
			result = loc + "/Banner_Check.csv";
		}else{
			
			String loc = userdefine.get("LOCATION");
			File file = new File(loc);
			if(!file.exists()){
				file.mkdirs();
			}
			
			logfile = loc + "/Banner_Check.log";
			Utils.conf("Default Log file is applied.");
			//initialize result
			result = loc + "/Banner_Check.csv";
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
			bw.append("Test Cases").append(",").append("Result").append(",").append("Reason");
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
		    boolean showup = false;
		    
		    //Re-load expected behavior
		    String showsUp = "Passed";
		    String hidden = "Failed";
		    String expected = "expected";
		    String notExpected = "not expected";
		    
		    Set<String> behaviors = userdefine.keySet();
		    if(behaviors.contains("IsShowingUp")){
		    	showsUp = userdefine.get("IsShowingUp");
		    }
		    
		    if(showsUp.toLowerCase().contains("failed")){
		    	hidden = "Passed";
			    expected = "not expected";
			    notExpected = "expected";
		    }
		    
		    while(!(CheckElement.find("id", "refreshBtn") || CheckElement.find("id", "submitKey") || CheckElement.find("id", "NormlizeHelp")
	    			|| CheckElement.find("id", "btnNewRole") || CheckElement.find("id", "btnCataSync"))){
		    	Thread.sleep(1000);
		    	++ timer;
		    	if(timer == 120){
			    	if(CheckElement.find("id", "refreshBtn") || CheckElement.find("id", "submitKey") || CheckElement.find("id", "NormlizeHelp")
			    			|| CheckElement.find("id", "btnNewRole") || CheckElement.find("id", "btnCataSync")){
			    		Utils.info(ele + " Page is up.");
			    		showup = true;
			    		Utils.cout();
//			    		try {
//							bw.append(ele).append(",").append("Passed");
//							bw.flush();
//							bw.newLine();
//						} catch (IOException e) {
//						}
			    	}
			    	else if(CheckElement.find("id", "errorTitle")){
			    		Utils.error("Error detected while loading" + ele + "page");
			    		Utils.cout();
			    		
//			    		try {
//							bw.append(ele).append(",").append("Failed");
//							bw.flush();
//							bw.newLine();
//						} catch (IOException e) {
//						}
			    	}
			    	else{
			    		Utils.warning("Openning " + ele + " page is timeout (2 min).");
			    		
//			    		try {
//							bw.append(ele).append(",").append("TimeOut");
//							bw.flush();
//							bw.newLine();
//						} catch (IOException e) {
//						}
			    	}
		    	break;	
		    	}
		    }
		    while(CheckElement.find("id", "refreshBtn") || CheckElement.find("id", "submitKey") || CheckElement.find("id", "NormlizeHelp")
	    			|| CheckElement.find("id", "btnNewRole") || CheckElement.find("id", "btnCataSync")){
	    		Utils.info(ele + " Page is up.");
	    		showup = true;
	    		Utils.cout();
//	    		try {
//					bw.append(ele).append(",").append("Passed");
//					bw.flush();
//					bw.newLine();
//				} catch (IOException e) {
//				}
	    		break;
		    }
		    
		    // Check banner
		    try{
			    if(showup){
				    if(CheckElement.checkAttrByClass("class", "top-updateset")){
				    	Utils.error("Patchset banner shows up, which is " + expected +" at this point!");
				    	Utils.error("Mark test case " + showsUp +"!");
				    	bw.append("Banner Check").append(",").append(showsUp).append(",").append("Patchset Banner shows up. which is " + expected + " at this point");
						bw.flush();
						bw.newLine();
				    }else{
				    	Utils.info("Patchset banner does not show up, which is " + notExpected +" at this point!");
				    	Utils.info("Mark test case " + hidden + "!");
				    	bw.append("Banner Check").append(",").append(hidden).append(",").append("Patchset Banner does not show up. which is " + notExpected + " at this point");
						bw.flush();
						bw.newLine();
				    }
			    }else{
			    	Utils.error("Loading 'Technopedia' page failed, mark test case failed!");
			    	bw.append("Banner Check").append(",").append(hidden).append(",").append("Loading 'Technopedia' page failed!");;
					bw.flush();
					bw.newLine();
			    }
			    bw.close();
		    }catch(IOException e){
		    	Utils.error("Failed to record results");
		    }
		    
			driver.navigate().back();
			driver.switchTo().defaultContent();
	    }
	}
}
