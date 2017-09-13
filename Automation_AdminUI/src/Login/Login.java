package Login;

import org.openqa.selenium.By;

import elementCheck.CheckLoading;
import main.Admin;
import global.Utils;

public class Login {
	/**
	 * Author : Gang Liu	
	 * Created: March 08, 2017
	 */		
	public static void login(String userName, String passWord, String url){
		Utils.info("Loging info from Config file.");
		
		//http://vm250w/bdna-admin/Admin.aspx
		String URL = "http://" + url + "/bdna-admin/Admin.aspx";
		
		Admin.driver.get(URL);
		Admin.driver.findElement(By.name("username")).sendKeys(userName);
		Admin.driver.findElement(By.name("password")).sendKeys(passWord);
		Admin.driver.findElement(By.id("loginBtn")).click();
		
		CheckLoading.ckLoad("className", "logout", "Admin Homepage");
		Utils.info("Login completed!");
	}
}
