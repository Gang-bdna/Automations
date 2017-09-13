package main;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import deepsearch.DeepSearching;
import global.Utils;

public class HelpDoc {
	/**
	 * Author : Gang Liu	
	 * Created: March 08, 2017
	 */
	/* Global parameters */
//	public static WebDriver driver = new FirefoxDriver();
	public static WebDriver driver = new ChromeDriver();
    public static WebDriverWait wait = new WebDriverWait(driver, 60000); 
    private static HashMap<String, String> conf = new HashMap<>();
    
	public static void main(String[] args) throws InterruptedException, IOException {
		// TODO Auto-generated method stub
		//Utils.info("Programm Begin!"); 

	    try{
	         driver.manage().timeouts().implicitlyWait(100, TimeUnit.SECONDS);
	       }catch (org.openqa.selenium.UnhandledAlertException e) {                
		         Alert alert = driver.switchTo().alert(); 
		         String alertText = alert.getText().trim();
		         System.out.println("Alert data: "+ alertText);
		         alert.dismiss();
	         }

		long starttime = System.currentTimeMillis();
		if(args.length==0){
			System.out.println();
			System.out.println("No Input from Console is detected, default config file will be applied!");
			IndexKeyWord.initConf(conf);
			IndexKeyWord.funCkc(driver);
			
		    Utils.info("Automation script is completed."); 
		    //driver.close();
		    Utils.elapsedTime(starttime, "Automation completed.");
		}
		else if(args[0].equals("-help")){
			System.out.println("Usage:");
			System.out.println("	-vm			VM name or IP");
			System.out.println("	-u			User name to login UI");
			System.out.println("	-p			Password to login UIr");
			System.out.println("	-v			The Version list file"); 
			System.out.println("	-location		Path to Output location");
			System.out.println("	-type			Platform type, DP or UC?");
		}
		else {
			if(args.length>=1 && !args[0].equals("-help")){
				for(int i=0; i<args.length; i++){
					if(args[i].equals("-u"))
						conf.put("UserName", args[++i]);
					else if(args[i].equals("-p"))
						conf.put("Password", args[++i]);
					else if(args[i].equals("-vm"))
						conf.put("URL", args[++i]);
					else if(args[i].equals("-v"))
						conf.put("Version", args[++i]);
					else if(args[i].equals("-location"))
						conf.put("Location", args[++i]);
					else if(args[i].equals("-type"))
						conf.put("Platform", args[++i]);
					else{
						System.out.println("   [error]: " + args[i] + " is an invalid command, "
								+ '\n' +  "            " + "help is available by typing -help");
						++i;
					}	
				}
			}
	    
			IndexKeyWord.initConf(conf);
			IndexKeyWord.funCkc(driver);
			
		    Utils.info("Automation script is completed."); 
		    //driver.close();
		    Utils.elapsedTime(starttime, "Automation completed.");
		}
	}
}
