package dragDrop;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import global.Utils;
import userConsoleOperation.clawerTCount;

public class DragDrop {
	/**
	 * Author : Gang Liu	
	 * Created: Jan 21, 2016
	 */	
	
	/* Drag and drop */
	private static boolean hidden = false;
	public static void dragdrop(WebElement from, WebElement to){
	     Actions builder = new Actions(clawerTCount.driver);     
	     if (from.isDisplayed()){
	    	 Action dragAndDrop = builder.clickAndHold(from)
	    			 .moveByOffset(1, 1)
		    		 .moveToElement(to)
		    		 .moveByOffset(1, 1)
		    		 .release()
		    		 .build();
		     dragAndDrop.perform();	
		     
		     Utils.info("  Drag completed!");
	
	     }else{
	    	 Utils.info("Features was not displayed to drag");
	     }
	     	
	}
	
	/* Right Click */
	public static void rightClick(String from){
	     Actions builder = new Actions(clawerTCount.driver);
	     WebElement ele = clawerTCount.driver.findElement(By.xpath(from));
	     if (ele.isDisplayed()){
	    	 builder.contextClick(ele).perform();		     
		     Utils.info("  Right Click!");
		     Utils.cout();	
	     }else{
	    	 Utils.info("Features was not displayed to drag");
	     }	     
	}
	
	/* Double Click */
	public static void doubleClick(String from){
	     Actions builder = new Actions(clawerTCount.driver);
	     WebElement ele = clawerTCount.driver.findElement(By.id(from));
	     if (ele.isDisplayed()){
	    	 builder.doubleClick(ele).perform();	
	    	 hidden = false;
//		     Utils.cout("  Double Click!");
//		     Utils.cout("");	
	     }else{
	    	 Utils.info("Attributes is hidden by design.");
	    	 hidden = true;
	     }	  
	}
	
	public static boolean isHidden(){
		return hidden;
	}
	/* Double Click 2 */
	 public static void dd(WebElement sourceElement, WebElement destinationElement)
	    {
	        (new Actions(clawerTCount.driver)).dragAndDrop(sourceElement, destinationElement).moveByOffset(1, 1).perform();
	    }
}
