package Login;

import org.openqa.selenium.By;

import elementCheck.CheckLoading;
import main.PatchBanner;
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
		
		PatchBanner.driver.get(URL);
		PatchBanner.driver.findElement(By.name("username")).sendKeys(userName);
		PatchBanner.driver.findElement(By.name("password")).sendKeys(passWord);
		PatchBanner.driver.findElement(By.id("loginBtn")).click();
		
		CheckLoading.ckLoad("className", "logout", "Admin Homepage");
		Utils.info("Login completed!");
	}
}
