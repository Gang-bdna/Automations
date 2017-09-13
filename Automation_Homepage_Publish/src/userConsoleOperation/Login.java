package userConsoleOperation;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import global.Utils;
import htmlSourceOperation.CheckLoading;

public class Login {
	public static void login(WebDriver driver,String userName, String passWord, String url){
		Utils.info("Loading UI info from Config.");
		
		String URL = "http://" + url + "/bdna/ux/index";
		driver.get(URL);
		driver.findElement(By.name("username")).sendKeys(userName);
		driver.findElement(By.name("password")).sendKeys(passWord);
		driver.findElement(By.className("btn")).click();
		CheckLoading.ckLoad("className", "dropdown", "UC Homepage");
		Utils.info("User Console Homepage is up.");
	}
}
