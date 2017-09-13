package interativeReport;


import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

import global.Utils;
import main.IR_UserConsole;

public class IR_CheckLoading {
	/**
	 * Author : Gang Liu	
	 * Created: Jan 21, 2016
	 */
	public static void ckLoad(String type, String xpath){
		if(type.equals("id")){
			IR_UserConsole.wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(xpath)));
			Utils.info("Features loading done!");
		}
		else if(type.equals("name")){
			IR_UserConsole.wait.until(ExpectedConditions.visibilityOfElementLocated(By.name(xpath)));
			Utils.info("Features loading done!");
		}
		else if(type.equals("xpath")){
			IR_UserConsole.wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
			Utils.info("Features loading done!");
		}
		else if(type.equals("className")){
			IR_UserConsole.wait.until(ExpectedConditions.visibilityOfElementLocated(By.className(xpath)));
			Utils.info("Features loading done!");
		}
		else if(type.equals("cssSelector")){
			IR_UserConsole.wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(xpath)));
			Utils.info("Features loading done!");
		}
		else if(type.equals("linkText")){
			IR_UserConsole.wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText(xpath)));
			Utils.info("Features loading done!");
		}
		else if(type.equals("tagName")){
			IR_UserConsole.wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName(xpath)));
			Utils.info("Features loading done!");
		}
		else if(type.equals("partialLinkText")){
			IR_UserConsole.wait.until(ExpectedConditions.visibilityOfElementLocated(By.partialLinkText(xpath)));
			Utils.info("Features loading done!");
		}
		else
			{
				Utils.error("Features loading check failed, pleas double check your <TYPE> input!");
			}		
	}
}
