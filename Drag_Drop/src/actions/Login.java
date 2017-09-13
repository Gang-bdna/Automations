package actions;

import org.openqa.selenium.By;

import elementCheck.CheckLoading;
import global.Utils;
import main.User_Console;

public class Login {
	/**
	 * Author : Gang Liu	
	 * Created: Jan 21, 2016
	 */		
//	public static WebDriver driver = new FirefoxDriver();
//    public static WebDriverWait wait = new WebDriverWait(driver, 30*60); 
    
	public static void login(String userName, String passWord, String url){
		
		//Register Driver
//	    try{
//	    	driver.manage().timeouts().implicitlyWait(30*60, TimeUnit.SECONDS);
//	       }catch (org.openqa.selenium.UnhandledAlertException e) {                
//		         Alert alert = driver.switchTo().alert(); 
//		         String alertText = alert.getText().trim();
//		         System.out.println("Alert data: "+ alertText);
//		         alert.dismiss();
//	         }
	    
	    //login
		Utils.info("Loging info from Config file.");
		String URL = "http://" + url + "/bdna/ux/index";
		User_Console.driver.get(URL);
		User_Console.driver.findElement(By.name("username")).sendKeys(userName);
		User_Console.driver.findElement(By.name("password")).sendKeys(passWord);
		User_Console.driver.findElement(By.className("btn")).click();
		CheckLoading.ckLoad("className", "dropdown");
		
		Utils.info("Login completed!");
		Utils.info("Now the page is on: " + URL);
		User_Console.driver.switchTo().defaultContent();
	}
	
//	public static WebDriver getDriver(){
//		return driver;
//	}
}

