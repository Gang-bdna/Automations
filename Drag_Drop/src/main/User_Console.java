package main;

import java.awt.AWTException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import actions.Login;
import actions.RepeatCreateCube;
import elementCheck.CubesCollect;
import fileOperate.ReportFilter;
import global.Utils;

/**
 * Author : Gang Liu	
 * Created: Jan. 21, 2016
 */

public class User_Console {
	/* Global parameters */
	public static WebDriver driver = new FirefoxDriver();
    public static WebDriverWait wait = new WebDriverWait(driver, 600); 
    public static String NODATA;
    public static String TIMEOUT;
    public static String INVISIBLE;
    public static String ERROR;
    public static String HASDATA;
    public static String logfile;
    public static String result;
    public static String opt;
    public static String usn;
    public static String psw;
    public static String svr;
    public static String reports;
//    private static String ipt;
    private static String timeout;
    private static Hashtable<String, String> conf = new Hashtable<>();
    private static HashMap<String, String> userdefine = new HashMap<>();
    private static HashSet<String> cubes = new HashSet<>();
    private static String configfile = "Inputs/DragDrop.cfg";
    
	public static void main(String[] args) throws InterruptedException, AWTException, IOException {
		long starttime = System.currentTimeMillis();
		//loading user pre-defined config file
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
		
		//Register Driver
	    try{
	         driver.manage().timeouts().implicitlyWait(600, TimeUnit.SECONDS);
	       }catch (org.openqa.selenium.UnhandledAlertException e) {                
		         Alert alert = driver.switchTo().alert(); 
		         String alertText = alert.getText().trim();
		         System.out.println("Alert data: "+ alertText);
		         alert.dismiss();
	         }
	    
		/*Loading Config and Input file*/
		if(args.length==0){
			System.out.println();
			System.out.println("No INFO are captuered from Console, Default config file will be applied!");
		}
		else if(args[0].equals("-help") || args[0].equals("-?")){
			System.out.println();
			System.out.println("	-u               User name");
			System.out.println("	-p               Password");
			System.out.println("	-s               Server");
			System.out.println("	-b               Browser");
//			System.out.println("	-input           Input file path");
			System.out.println("	-output          Output file path");
			System.out.println("	-config          Configure file path");
			System.out.println("	-timeout		 Setting timeout time");
		}else if(args.length>=1 && !args[0].equals("-help")){
			for(int i=0; i<args.length; i++){
				if(args[i].equals("-u"))
					conf.put("UserName", args[++i]);
				else if(args[i].equals("-p"))
					conf.put("Password", args[++i]);
				else if(args[i].equals("-s"))
					conf.put("Server", args[++i]);
				else if(args[i].equals("-b"))
					conf.put("Browser", args[++i]);
				else if(args[i].equals("-report"))
					conf.put("Report", args[++i]);
				else if(args[i].equals("-output"))
					conf.put("Outputs", args[++i]);
				else if(args[i].equals("-config"))
					conf.put("Config", args[++i]);
				else if(args[i].equals("-timeout"))
					conf.put("Timeout", args[++i]);
				else{
					System.out.println("   [error]: " + args[i] + " is an invalid command, "
							+ '\n' +  "            " + "help is available by typing -help");
					++i;
				}	
			}
		}
		
		//Loading config for the file used to save final diff results
		if(conf.containsKey("Outputs")){
			
			opt = conf.get("Outputs");
			File file = new File(opt);
			if(!file.exists()){
				file.mkdirs();
			}
			
			logfile = opt + "/Drag_Drop.log";
			Utils.conf("Log file from Console is captuered.");
			//initialize result
			result = opt + "/Drag_Drop.csv";
		}else{
			opt = userdefine.get("LOCATION");
			File file = new File(opt);
			if(!file.exists()){
				file.mkdirs();
			}
			
			logfile = opt + "/Drag_Drop.log";
			Utils.conf("Default Log file is applied.");
			//initialize result
			result = opt + "/Drag_Drop.csv";
		}
		
		//get input file
		if(conf.containsKey("Report")){
			reports = conf.get("Report");
			Utils.info("Userself defined Reports are captuered.");
		}else{
			reports = userdefine.get("REPORTS");
			Utils.info("Default Reports are applied.");
		}
		
		//get username
		if(conf.containsKey("Username")){
			usn = conf.get("UserName");
			Utils.info("Username are .");
		}else{
			usn = userdefine.get("USERNAME");
			Utils.info("Default Username is applied.");
		}
		
		//get password
		if(conf.containsKey("Password")){
			psw = conf.get("Password");
			Utils.info("Password is captuered from CMD console.");
		}else{
			psw = userdefine.get("PASSWORD");
			Utils.info("Default Password is applied.");
		}
		
		//get server
		if(conf.containsKey("Server")){
			svr = conf.get("Server");
			Utils.info("Server info is captuered from CMD console.");
		}else{
			svr = userdefine.get("URL");
			Utils.info("Default Server is applied.");
		}
		
		//get server
		if(conf.containsKey("Timeout")){
			timeout = conf.get("Timeout");
			Utils.info("Timeout is captuered from CMD console.");
		}else{
			timeout = userdefine.get("INTERVAL");
			Utils.info("Default Timeout is applied.");
		}
		
		//Loading results setting
		NODATA = userdefine.get("NODATA");
		TIMEOUT = userdefine.get("TIMEOUT");
		INVISIBLE = userdefine.get("INVISIBLE");
		ERROR = userdefine.get("ERROR");
		HASDATA = userdefine.get("HASDATA");
		
	    // Login to testing environment
	    Login.login(usn, psw, svr);
	    
	    // Create Analyze Report
		Utils.info("Begin to create Analyze Report!");	
   	    
		//Loading all cubes
		cubes = CubesCollect.Cubes(driver);
		
		//Filter out report list retrieving from UI
		ReportFilter.filter(cubes, reports);
		cubes = ReportFilter.getRemains();

	    // Create Analyze Report by cube name
	    RepeatCreateCube.rcCubes(driver, cubes, timeout);		
	    
	    Utils.elapsedTime(starttime, "Automation completed.");
	    driver.close();
	}

}
