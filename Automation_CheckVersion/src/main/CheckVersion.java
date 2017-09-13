package main;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import global.Utils;

public class CheckVersion {
	/**
	 * Author : Gang Liu	
	 * Created: March 08, 2017
	 */
	/* Global parameters */
	public static WebDriver driver = new FirefoxDriver();
//	public static WebDriver driver = new ChromeDriver();
    public static WebDriverWait wait = new WebDriverWait(driver, 60000); 
    private static HashMap<String, String> conf = new HashMap<>();
    
	public static void main(String[] args) throws InterruptedException, IOException {
		// TODO Auto-generated method stub
		//Utils.info("Programm Begin!"); 

		long starttime = System.currentTimeMillis();
		if(args.length==0){
			System.out.println();
			System.out.println("No Input from Console is detected, default config file will be applied!");
			LoadPages.initConf(conf);
			LoadPages.funCkc(driver);
		    Utils.info("Automation script is completed."); 
		    driver.close();
		    Utils.elapsedTime(starttime, "Automation completed.");
		}
		else if(args[0].equals("-help")){
			System.out.println("Usage:");
			System.out.println("	-vm               	VM or IP of Testing VM");
			System.out.println("	-u                	User name for Admin UI login");
			System.out.println("	-p					Password for Admin UI login");
			System.out.println("	-location			Path to Log File location");
			System.out.println("	-result				Path to result log file");
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
					else if(args[i].equals("-location"))
						conf.put("Location", args[++i]);
					else if(args[i].equals("-result"))
						conf.put("Result", args[++i]);
					else{
						System.out.println("   [error]: " + args[i] + " is an invalid command, "
								+ '\n' +  "            " + "help is available by typing -help");
						++i;
					}	
				}
			}
	    
			LoadPages.initConf(conf);
			LoadPages.funCkc(driver);
			
		    System.out.println('\n');
		    Utils.info("Automation script is completed."); 
		    driver.close();
		    Utils.elapsedTime(starttime, "Automation completed.");
		}
	}
}
