package elementCheck;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

import global.Utils;
import main.PatchBanner;

public class CheckLoading {
	/**
	 * Author : Gang Liu	
	 * Created: March 08, 2017
	 */
	public static void ckLoad(String type, String xpath, String msg){
		if(type.equals("id")){
			PatchBanner.wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(xpath)));
			//Utils.info("Element " + msg + " is found!");
		}
		else if(type.equals("name")){
			PatchBanner.wait.until(ExpectedConditions.visibilityOfElementLocated(By.name(xpath)));
			Utils.info("Element " + msg + " is found!");
		}
		else if(type.equals("xpath")){
			PatchBanner.wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
			//Utils.info("Element " + msg + " is found!");
		}
		else if(type.equals("className")){
			PatchBanner.wait.until(ExpectedConditions.visibilityOfElementLocated(By.className(xpath)));
			//Utils.info("Element " + msg + " is found!");
		}
		else if(type.equals("cssSelector")){
			PatchBanner.wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(xpath)));
			Utils.info("Element " + msg + " is found!");
		}
		else if(type.equals("linkText")){
			PatchBanner.wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText(xpath)));
			Utils.info("Element " + msg + " is found!");
		}
		else if(type.equals("tagName")){
			PatchBanner.wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName(xpath)));
			Utils.info("Element " + msg + " is found!");
		}
		else if(type.equals("partialLinkText")){
			PatchBanner.wait.until(ExpectedConditions.visibilityOfElementLocated(By.partialLinkText(xpath)));
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
	             Utils.cout();
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
