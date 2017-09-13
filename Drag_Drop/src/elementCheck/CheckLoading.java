package elementCheck;


import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

import global.Utils;
import main.User_Console;

public class CheckLoading {
	/**
	 * Author : Gang Liu	
	 * Created: Jan 21, 2016
	 */
	public static void ckLoad(String type, String xpath){
		if(type.equals("id")){
			User_Console.wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(xpath)));
//			Utils.cout("  Features loading done!");
		}
		else if(type.equals("name")){
			User_Console.wait.until(ExpectedConditions.visibilityOfElementLocated(By.name(xpath)));
//			Utils.cout("  Features loading done!");
		}
		else if(type.equals("xpath")){
			User_Console.wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
//			Utils.cout("  Features loading done!");
		}
		else if(type.equals("className")){
			User_Console.wait.until(ExpectedConditions.visibilityOfElementLocated(By.className(xpath)));
//			Utils.cout("  Features loading done!");
		}
		else if(type.equals("cssSelector")){
			User_Console.wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(xpath)));
//			Utils.cout("  Features loading done!");
		}
		else if(type.equals("linkText")){
			User_Console.wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText(xpath)));
//			Utils.cout("  Features loading done!");
		}
		else if(type.equals("tagName")){
			User_Console.wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName(xpath)));
//			Utils.cout("  Features loading done!");
		}
		else if(type.equals("partialLinkText")){
			User_Console.wait.until(ExpectedConditions.visibilityOfElementLocated(By.partialLinkText(xpath)));
//			Utils.cout("  Features loading done!");
		}
		else
			{
				Utils.error("Features loading check failed, pleas double check your <TYPE> input!");
			}		
	}
}
