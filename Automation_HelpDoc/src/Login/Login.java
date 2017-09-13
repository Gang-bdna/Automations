package Login;

import org.openqa.selenium.By;

import elementCheck.CheckLoading;
import main.HelpDoc;
import global.Utils;

public class Login {
	/**
	 * Author : Gang Liu	
	 * Created: March 08, 2017
	 */		
	public static void login(String userName, String passWord, String url, String dp){
		Utils.info("Loging info from Config file.");
		
		//http://vm250w/bdna-admin/Admin.aspx
		if(dp.toLowerCase().equals("dp")){
			String URL = "http://" + url + "/bdna-admin/Admin.aspx";
			HelpDoc.driver.get(URL);
			HelpDoc.driver.findElement(By.name("username")).sendKeys(userName);
			HelpDoc.driver.findElement(By.name("password")).sendKeys(passWord);
			HelpDoc.driver.findElement(By.id("loginBtn")).click();
			CheckLoading.ckLoad("className", "logout", "Admin Homepage");
			Utils.info("Login completed!");
		}
		else if(dp.toLowerCase().equals("uc")){
			String URL = "http://" + url + "/bdna/ux/index";
			HelpDoc.driver.get(URL);
			HelpDoc.driver.findElement(By.name("username")).sendKeys(userName);
			HelpDoc.driver.findElement(By.name("password")).sendKeys(passWord);
			HelpDoc.driver.findElement(By.className("btn")).click();
			CheckLoading.ckLoad("className", "dropdown", "UC Homepage");
			Utils.info("Login completed!");
		}
		else{
			Utils.error("Login into " + url + " failed.");
		}
	}
}
