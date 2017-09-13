package elementCheck;


import org.bouncycastle.crypto.prng.ThreadedSeedGenerator;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

import global.Utils;
import main.Canned_Reports_DateInt;
import net.jcip.annotations.ThreadSafe;

public class CheckLoading {
	/**
	 * Author : Gang Liu	
	 * Created: March 08, 2017
	 */
	public static void ckLoad(String type, String xpath){
		if(type.equals("id")){
			Canned_Reports_DateInt.wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(xpath)));
			Utils.cout("  Canned Reports loading done!");
		}
		else if(type.equals("name")){
			Canned_Reports_DateInt.wait.until(ExpectedConditions.visibilityOfElementLocated(By.name(xpath)));
			Utils.cout("  Canned Reports loading done!");
		}
		else if(type.equals("xpath")){
			Canned_Reports_DateInt.wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
			Utils.cout("  Canned Reports loading done!");
		}
		else if(type.equals("className")){
			Canned_Reports_DateInt.wait.until(ExpectedConditions.visibilityOfElementLocated(By.className(xpath)));
			Utils.cout("  Canned Reports loading done!");
		}
		else if(type.equals("cssSelector")){
			Canned_Reports_DateInt.wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(xpath)));
			Utils.cout("  Canned Reports loading done!");
		}
		else if(type.equals("linkText")){
			Canned_Reports_DateInt.wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText(xpath)));
			Utils.cout("  Canned Reports loading done!");
		}
		else if(type.equals("tagName")){
			Canned_Reports_DateInt.wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName(xpath)));
			Utils.cout("  Canned Reports loading done!");
		}
		else if(type.equals("partialLinkText")){
			Canned_Reports_DateInt.wait.until(ExpectedConditions.visibilityOfElementLocated(By.partialLinkText(xpath)));
			Utils.cout("  Canned Reports loading done!");
		}
		else
			{
				Utils.error("Canned Reports loading check failed, pleas double check your <TYPE> input!");
			}		
	}
	
	public static void ckLoading(WebDriver driver){
	    JavascriptExecutor js = (JavascriptExecutor)driver;
	    Boolean visible = false;
	    
	    while(!visible){
	        if (js.executeScript("return document.readyState").toString().equals("complete"))
	        { 
				driver.switchTo().defaultContent();
	             Utils.cout("Reports Loading done!");
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
