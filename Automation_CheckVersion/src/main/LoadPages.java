package main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.openqa.selenium.WebDriver;

import Login.Login;
import elementCheck.CheckElement;
import elementCheck.smokeCheck;
import global.Utils;

public class LoadPages {
    private static String configfile = "Input/Version_Check.cfg";
    private static HashMap<String, String> userdefine = new HashMap<>();
    public static HashMap<String, ArrayList<String>> PredefineRoles = new HashMap<>();
	private static ArrayList<String> eleList = new ArrayList<>();
    public static String logfile;
    public static String username;
    public static String password;
    public static String url;
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
			
			logfile = loc + "/Version_Check.log";
			result = loc + "/SysInfo.log";
			Utils.conf("Log file from Console is captuered.");
			//initialize result
//			result = loc + "/SysInfo.log";
		}else{
			
			String loc = userdefine.get("LOCATION");
			File file = new File(loc);
			if(!file.exists()){
				file.mkdirs();
			}
			
			logfile = loc + "/Version_Check.log";
			result = loc + "/SysInfo.log";
			Utils.conf("Default Log file is applied.");
			//initialize result
//			result = loc + "/SysInfo.log";
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
		if(conf.containsKey("Result")){
			result = conf.get("Result");
			Utils.conf("Result file is captuered.");
		}else{
			result = userdefine.get("RESULT");
			Utils.conf("Result file is applied.");
		}
    }
	
	public static void funCkc(WebDriver driver) throws InterruptedException, IOException{
		//Initialize Writer
		FileWriter docWrite;
		BufferedWriter bw = null;
		
	    //Login to Admin homepage
	    Login.login(username, password, url);
	    	    
	    //Loading checklist from config file
	    eleList.add("About");
	    
	    //Check functional elements
	    Utils.info("Start to check functional elements!");
	    for(String ele: eleList){
		    smokeCheck.Navigate(driver, "id", "slideMenu");
		    
		    Utils.info("Start to load " + ele + " page");
		    smokeCheck.Navigate(driver, "xpath", ele);
		    Thread.sleep(1000);
			 
		    driver.switchTo().frame("TB_iframeContent");		 
			 
		    int timer = 0;
		    while(!(CheckElement.find(driver, "id", "tb_close"))){
		    	Thread.sleep(1000);
		    	++ timer;
		    	if(timer == 120){
			    	if(CheckElement.find(driver, "id", "tb_close")){
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
		    while((CheckElement.find(driver, "id", "tb_close"))){
	    		Utils.info(ele + " Page is up.");
	    		Utils.cout('\n');
	    		break;
		    }
		    
			 String html = driver.getPageSource();
			 Document doc = Jsoup.parse(html);
			 Elements paragraph = doc.body().select("p");
			 
			 String[] versions = paragraph.get(0).toString().trim().split("<br>");
			 
//			 Utils.mark(versions[0]);
//			 Utils.mark(versions[1]);
			 

		    //Write results
			try {
				docWrite = new FileWriter(result, true);
				bw = new BufferedWriter(docWrite); 
				
				for(String v : versions){
					if(v.contains("Technopedia Version")){
						bw.append("Technopedia Version:" + v.substring(v.indexOf(":")+1));
						bw.flush();
						bw.newLine();
					}
					else if(v.contains("Platform Version")){
						bw.append("Build Version:" + v.substring(v.indexOf(":")+1));
						bw.flush();
						bw.newLine();
					}				}
				bw.close();
				
			} catch (IOException e) {
				Utils.error("Initialize "+ result +" file failed");
			}
			
	    }
	}
}
