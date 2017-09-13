package elementCheck;

import java.util.ArrayList;
import java.util.HashMap;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;

import global.Utils;
import main.LoadPages;

public class CreateUser {
	private static HashMap<String,String> results = new HashMap<>();
	public static void add(WebDriver driver, String keys, HashMap<String, ArrayList<String>> IDmap) throws InterruptedException{
		//Open New Role Assignment page
		CheckLoading.ckLoad("id", "btnNewRole", "");
		smokeCheck.Navigate(driver, "id", "btnNewRole");
		CheckLoading.ckLoading(driver);
		
		//Switch to popup's frame
		driver.switchTo().frame("TB_iframeContent");
		CheckLoading.ckLoad("id", "btnSave", "");
		//Utils.info("Start to add new Users: " + keys);
	
		CheckLoading.ckLoad("id", "editUserName", "");
		Thread.sleep(2000);
		driver.findElement(By.id("editUserName")).sendKeys(keys);
		Thread.sleep(1000);
		
		//Uncheck all by default
		CheckLoading.ckLoad("id", "ckbAllChecked", "");
		driver.findElement(By.id("ckbAllChecked")).click();
		
		//Customize permissions to specific User according to config file
		for(String level : IDmap.get(keys)){
			smokeCheck.Navigate(driver, "id", level);
		}
		
		
		CheckLoading.ckLoad("id", "btnSave", "");
		driver.findElement(By.id("btnSave")).click();
		
		//Check if the user is existing
		try{
			driver.findElement(By.className("editUserNameformError"));
			Utils.error("New User:  [" + keys + "] is not existing!");
			Utils.cout('\n');
			CheckLoading.ckLoad("id", "btnCancel", "");
			driver.findElement(By.id("btnCancel")).click();
			Thread.sleep(2000);
			results.put(keys, "Failed");
		}catch(NoSuchElementException e){
			Thread.sleep(1000);
			//wait till adding operation is done
			try{
				while(driver.findElement(By.className("blockUI")) != null){
					Thread.sleep(1000);
				};
			}catch(NoSuchElementException ne){
				driver.switchTo().defaultContent();
			}
			SwitchWin.windows(driver);
			CheckLoading.ckLoad("id", "btnNewRole", "");
			Utils.info("New User:   [" + keys + "] added!");
			Utils.info("Privileges: " + LoadPages.PredefineRoles.get(keys).toString());
			Utils.cout('\n');
			results.put(keys, "Passed");
		}
	}
	
	//return results;
	public static HashMap<String, String> getResults(){
		return results;
	}
}
