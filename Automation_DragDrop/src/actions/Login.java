package actions;

import org.openqa.selenium.By;

import elementCheck.CheckLoading;
import main.User_Console;
import global.Utils;

public class Login {
	/**
	 * Author : Gang Liu	
	 * Created: Jan 21, 2016
	 */		
	public static void login(String userName, String passWord, String url){
		Utils.cout("  Loging info from Config file.");
		String URL = "http://" + url + "/bdna/ux/index";
		User_Console.driver.get(url);
		User_Console.driver.findElement(By.name("username")).sendKeys(userName);
		User_Console.driver.findElement(By.name("password")).sendKeys(passWord);
		User_Console.driver.findElement(By.className("btn")).click();
		CheckLoading.ckLoad("className", "dropdown");
		
		Utils.cout("  Login completed!");
		Utils.cout("  Loading home page!");
	}
}

