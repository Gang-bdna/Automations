package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import Login.Login;
import deepsearch.DeepSearching;
import elementCheck.AdminHelpDoc;
import elementCheck.CheckElement;
import elementCheck.smokeCheck;
import global.Utils;

public class IndexKeyWord {
    public static String configfile = "HelpDoc.cfg";
    private static HashMap<String, String> userdefine = new HashMap<>();
	private static ArrayList<String> eleList = new ArrayList<>();
	private static ArrayList<String> dpKey = new ArrayList<>();
	private static ArrayList<String> ucKey = new ArrayList<>();
    public static String logfile;
    public static String username;
    public static String password;
    public static String url;
    public static String location;
    private static String dpuc;
    private static String version;
    public static String result;
    
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
		
		//Loading DP or UC
		if(conf.containsKey("Platform")){
			dpuc = conf.get("Platform");
			System.out.println("Help Doc type is captuered.");
		}else{
			dpuc = userdefine.get("PLATFORM");
			System.out.println("Default Help Doc type is applied.");
		}
		
		//Loading config for the file used to save final diff results
		if(conf.containsKey("Location")){
			String loc = conf.get("Location");
			File file = new File(loc);
			if(!file.exists()){
				file.mkdirs();
			}
			logfile = loc + "/" + dpuc + "_HelpDoc.log";
			Utils.conf("Log file from Console is captuered.");
			//initialize result file
			result = loc + "/" + dpuc + "_HelpDoc.csv";
			location = loc;
		}else{
			String loc = userdefine.get("FILESLOCATION");
			File file = new File(loc);
			if(!file.exists()){
				file.mkdirs();
			}
			logfile = loc + "/" + dpuc + "_HelpDoc.log";
			Utils.conf("Default Log file is applied.");
			//initialize result file
			result = loc + "/" + dpuc + "_HelpDoc.csv";
			location = loc;
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
		
		
		//Loading DP or UC
		if(conf.containsKey("Version")){
			version = conf.get("Version");
			Utils.conf("The major version of DP or UC is captuered.");
		}else{
			version = userdefine.get("VERSION");
			Utils.conf("Default major version of DP or UC is applied.");
		}
    }
	
	public static void funCkc(WebDriver driver) throws InterruptedException, IOException{
	    //Login to Admin homepage
	    Login.login(username, password, url, dpuc);
	    Thread.sleep(1000);
	    if(dpuc.equals("DP")){
		    AdminHelpDoc.loadAHD(driver, dpuc, version);
		    Thread.sleep(500);
			DeepSearching.search("DP");
	    }
	    else if(dpuc.equals("UC")){
	        Utils.cout('\n');
	        AdminHelpDoc.loadAHD(driver, dpuc, version);
		    Thread.sleep(500);
			DeepSearching.search("UC");
	    }
	}
}
