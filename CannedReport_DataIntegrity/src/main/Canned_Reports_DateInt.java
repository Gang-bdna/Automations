package main;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import global.Prop;
import global.Utils;
import loadingReport.CollectCannedReportsName;
import loadingReport.LoadingCReports;
import loadingReport.goThroughCannedReports;
import Login.Login;

public class Canned_Reports_DateInt {
	/**
	 * Author : Gang Liu	
	 * Created: March 08, 2017
	 */
	/* Global parameters */
	public static WebDriver driver = new FirefoxDriver();
    public static WebDriverWait wait = new WebDriverWait(driver, 300000); 
    private static HashMap<String, String> nameSet = new HashMap<>();
    
	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		Utils.cout("Programm Begin!"); 
		
	    try{
	         driver.manage().timeouts().implicitlyWait(100, TimeUnit.SECONDS);
	       }catch (org.openqa.selenium.UnhandledAlertException e) {                
		         Alert alert = driver.switchTo().alert(); 
		         String alertText = alert.getText().trim();
		         System.out.println("Alert data: "+ alertText);
		         alert.dismiss();
	         }
	    
	    Login.login(Prop.USERNAME, Prop.PASSWORD, Prop.URL);
	    LoadingCReports.loadCRs(driver);
	    
	    CollectCannedReportsName.collect(driver);
	    
	    nameSet = CollectCannedReportsName.getRptNameSet();
	    goThroughCannedReports.openCReports(nameSet, driver);
	    
	    Utils.cout("==========Program Done!============="); 
	    driver.close();
	}

}
