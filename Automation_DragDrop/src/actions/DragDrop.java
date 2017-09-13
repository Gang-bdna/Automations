package actions;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import main.User_Console;
import global.Utils;

public class DragDrop {
	/**
	 * Author : Gang Liu	
	 * Created: Jan 21, 2016
	 */	
	
	/* Drag and drop */
	public static void dragdrop(WebElement from, WebElement to){
	     Actions builder = new Actions(User_Console.driver);     
	     if (from.isDisplayed()){
	    	 Action dragAndDrop = builder.clickAndHold(from)
	    			 .moveByOffset(1, 1)
		    		 .moveToElement(to)
		    		 .moveByOffset(1, 1)
		    		 .release()
		    		 .build();
		     dragAndDrop.perform();	
		     
		     Utils.cout("  Drag completed!");
	
	     }else{
	    	 Utils.cout("Features was not displayed to drag");
	     }
	     	
	}
	
	/* Right Click */
	public static void rightClick(String from){
	     Actions builder = new Actions(User_Console.driver);
	     WebElement ele = User_Console.driver.findElement(By.xpath(from));
	     if (ele.isDisplayed()){
	    	 builder.contextClick(ele).perform();		     
		     Utils.cout("  Right Click!");
		     Utils.cout("");	
	     }else{
	    	 Utils.cout("Features was not displayed to drag");
	     }	     
	}
	
	/* Double Click */
	public static void doubleClick(String from){
	     Actions builder = new Actions(User_Console.driver);
	     WebElement ele = User_Console.driver.findElement(By.id(from));
	     if (ele.isDisplayed()){
	    	 builder.doubleClick(ele).perform();		     
		     Utils.cout("  Double Click!");
		     Utils.cout("");	
	     }else{
	    	 Utils.cout("Features was not displayed to drag");
	     }	     
	}
	/* Double Click 2 */
	 public static void dd(WebElement sourceElement, WebElement destinationElement)
	    {
	        (new Actions(User_Console.driver)).dragAndDrop(sourceElement, destinationElement).moveByOffset(1, 1).perform();
	    }
}
