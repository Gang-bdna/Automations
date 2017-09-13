package elementCheck;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

import global.Utils;
import main.CheckVersion;

public class CheckLoading {
	/**
	 * Author : Gang Liu	
	 * Created: March 08, 2017
	 */
	public static void ckLoad(String type, String xpath, String msg){
		if(type.equals("id")){
			CheckVersion.wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(xpath)));
			//Utils.info(msg);
			//Utils.info("Element " + msg + " is found!");
		}
		else if(type.equals("name")){
			CheckVersion.wait.until(ExpectedConditions.visibilityOfElementLocated(By.name(xpath)));
			Utils.info("Element " + msg + " is found!");
		}
		else if(type.equals("xpath")){
			CheckVersion.wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
			CheckVersion.wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
			//Utils.info("Element " + msg + " is found!");
		}
		else if(type.equals("className")){
			CheckVersion.wait.until(ExpectedConditions.visibilityOfElementLocated(By.className(xpath)));
			//Utils.info("Element " + msg + " is found!");
		}
		else if(type.equals("cssSelector")){
			CheckVersion.wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(xpath)));
			Utils.info("Element " + msg + " is found!");
		}
		else if(type.equals("linkText")){
			CheckVersion.wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText(xpath)));
			Utils.info("Element " + msg + " is found!");
		}
		else if(type.equals("tagName")){
			CheckVersion.wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName(xpath)));
			Utils.info("Element " + msg + " is found!");
		}
		else if(type.equals("partialLinkText")){
			CheckVersion.wait.until(ExpectedConditions.visibilityOfElementLocated(By.partialLinkText(xpath)));
			Utils.info("Element " + msg + " is found!");
		}
		else
			{
				Utils.error("Failed to load " + msg);
			}		
	}
	
	public static void ckLoading(WebDriver driver){
	    JavascriptExecutor js = (JavascriptExecutor)driver;
	    Boolean visible = false;
	    
	    while(!visible){
	        if (js.executeScript("return document.readyState").toString().equals("complete"))
	        { 
				driver.switchTo().defaultContent();
	             //Utils.cout("Reports Loading is done!");
	             visible = true;
	        } else {
	        	try {
					Thread.sleep(300);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        }
	    }
	      
	}
}
