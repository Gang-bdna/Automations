package userConsoleOperation;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import htmlSourceOperation.parseDataFromHP;

public class clawerTCount {
	public static WebDriver driver = new ChromeDriver();
//	public static WebDriver driver = new FirefoxDriver();
    public static WebDriverWait wait = new WebDriverWait(driver, 60000); 
    
    public static void retriveData(String userName, String passWord, String url){
	    //declare selenium driver
    	try{
	         driver.manage().timeouts().implicitlyWait(100, TimeUnit.SECONDS);
	       }catch (org.openqa.selenium.UnhandledAlertException e) {                
		         Alert alert = driver.switchTo().alert(); 
		         String alertText = alert.getText().trim();
		         System.out.println("Alert data: "+ alertText);
		         alert.dismiss();
	         }
	    
	    //Login
    	Login.login(driver, userName, passWord, url);
    	parseDataFromHP.parse(driver);
//    	driver.close();
    }
}
