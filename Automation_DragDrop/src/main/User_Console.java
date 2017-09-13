package main;

import java.awt.AWTException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.concurrent.TimeUnit;

import javax.mail.MessagingException;

import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import actions.Login;
import actions.RepeatCreateCube;
import global.Prop;
import global.Utils;

/**
 * Author : Gang Liu	
 * Created: Jan. 21, 2016
 */

public class User_Console {
	/* Global parameters */
	public static WebDriver driver = new FirefoxDriver();
    public static WebDriverWait wait = new WebDriverWait(driver, 300000); 
    private static String usn;
    private static String psw;
    private static String svr;
    private static String ipt;
    private static String opt;
    private static Hashtable<String, String> conf = new Hashtable<>();
    private static HashMap<String, String> userdefine = new HashMap<>();
    private static String configfile = "Inputs/DragDrop.cfg";
    
	public static void main(String[] args) throws InterruptedException, AWTException, IOException {		
		Utils.cout("Programm Begin!"); 
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
	         driver.manage().timeouts().implicitlyWait(100, TimeUnit.SECONDS);
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
		else if(args[0].equals("-help")){
			System.out.println();
			System.out.println("	-u               User name");
			System.out.println("	-p               Password");
			System.out.println("	-s               Server");
			System.out.println("	-b               Browser");
			System.out.println("	-input           Input file path");
			System.out.println("	-output          Output file path");
			System.out.println("	-config          Configure file path");
			System.out.println("eg: java [AUTOMATION_Test.JAR] -u [BDNA] -p [HELLO] -s [localhost/UX/LOGIN] -b [Firefox]");	
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
				else if(args[i].equals("-input"))
					conf.put("Inputs", args[++i]);
				else if(args[i].equals("-output"))
					conf.put("Outputs", args[++i]);
				else if(args[i].equals("-config"))
					conf.put("Config", args[++i]);
				else{
					System.out.println("   [error]: " + args[i] + " is an invalid command, "
							+ '\n' +  "            " + "help is available by typing -help");
					++i;
				}	
			}
		}
		
		//get input file
		if(conf.containsKey("Inputs")){
			ipt = conf.get("Inputs");
			Utils.cout("  Userself defined input info are captuered.");
		}else{
			ipt = userdefine.get("INPUTS");
			Utils.cout("  Default Input file applied.");
		}
		
		if(conf.containsKey("Outputs")){
			opt = conf.get("Outputs");
			Utils.cout("  Userself defined output info are captuered");
		}else{
			opt = userdefine.get("LOCATION");
			Utils.cout("  Default Output file applied.");
		}
		
		//get username
		if(conf.containsKey("Username")){
			usn = conf.get("UserName");
			Utils.cout("  Username are .");
		}else{
			usn = userdefine.get("USERNAME");
			Utils.cout("  Default Username is applied.");
		}
		
		//get password
		if(conf.containsKey("Password")){
			psw = conf.get("Password");
			Utils.cout("  Password is captuered from CMD console.");
		}else{
			psw = userdefine.get("PASSWORD");
			Utils.cout("  Default Password is applied.");
		}
		
		//get server
		if(conf.containsKey("Server")){
			svr = conf.get("Server");
			Utils.cout("  Server info is captuered from CMD console.");
		}else{
			svr = userdefine.get("URL");
			Utils.cout("  Default Server is applied.");
		}
    
	    
	    // Login to testing environment
	    Login.login(usn, psw, svr);
	    
	    // Create Analyze Report
		Utils.cout("Begin to create Analyze Report!");	
   	    
	    // Create Analyze Report by cube name
	    RepeatCreateCube.rcCubes(driver);		
	    
	    // Sent Automation report to QA group
		
		
	    Utils.cout("==========Program Done!============="); 
	    driver.close();
	}

}
