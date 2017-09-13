package interativeReport;

import org.openqa.selenium.By;

import main.IR_UserConsole;
import global.Utils;

public class IR_Login {
	/**
	 * Author : Gang Liu	
	 * Created: Jan 21, 2016
	 */		
	public static void login(String userName, String passWord, String url){
		Utils.cout("  Loging info from Config file.");
		
		IR_UserConsole.driver.get(url);
		IR_UserConsole.driver.findElement(By.name("username")).sendKeys(userName);
		IR_UserConsole.driver.findElement(By.name("password")).sendKeys(passWord);
		IR_UserConsole.driver.findElement(By.className("btn")).click();
		
		Utils.cout("  Login completed!");
		Utils.cout("  Loading home page!");
	}
}
