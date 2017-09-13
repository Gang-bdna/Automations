package Login;

import org.openqa.selenium.By;

import main.Canned_Reports_DateInt;
import global.Utils;

public class Login {
	/**
	 * Author : Gang Liu	
	 * Created: March 08, 2017
	 */		
	public static void login(String userName, String passWord, String url){
		Utils.cout("  Loging info from Config file.");
		
		Canned_Reports_DateInt.driver.get(url);
		Canned_Reports_DateInt.driver.findElement(By.name("username")).sendKeys(userName);
		Canned_Reports_DateInt.driver.findElement(By.name("password")).sendKeys(passWord);
		Canned_Reports_DateInt.driver.findElement(By.className("btn")).click();
		
		Utils.cout('\n');		
	}
}
