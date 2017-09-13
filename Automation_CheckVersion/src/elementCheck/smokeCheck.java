package elementCheck;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import global.Utils;
import main.CheckVersion;

public class smokeCheck {	
	public static void Navigate(WebDriver driver, String type, String ID){
//		driver.switchTo().defaultContent();
		if(type.toLowerCase().equals("id")){
			CheckLoading.ckLoad(type, ID, ""); 
			driver.findElement(By.id(ID)).click();
		}
		else if(type.toLowerCase().equals("className")){
			CheckLoading.ckLoad(type, ID, ID); 
			driver.findElement(By.className(ID)).click();
		}
		else if(type.toLowerCase().equals("linkText")){
			CheckLoading.ckLoad(type, ID, ID); 
			driver.findElement(By.linkText(ID)).click();
		}
		else if(type.toLowerCase().equals("xpath")){
			CheckLoading.ckLoad(type, "//a[contains(., '"+ ID +"')]", ID); 
		    driver.findElement(By.xpath("//a[contains(., '"+ ID +"')]")).click();
		}
		else{
			Utils.error("Pls double check your input, " + ID + "was not found!");
		}
	}
}
