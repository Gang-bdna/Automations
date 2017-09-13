package actions;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import elementCheck.AttrCollect;
import elementCheck.CheckLoading;
import main.User_Console;
import global.Utils;

public class DragFromTo {
	/**
	 * Author : Gang Liu	
	 * Created: Jan. 22, 2016
	 */
	
//	drag item by name
	public static void DFT(String from, String to) throws InterruptedException{
		Utils.cout("  Ready to drag!");   
		long start = System.currentTimeMillis(); 
	    CheckLoading.ckLoad("id", AttrCollect.list.get(from)); 
		WebElement from1 = User_Console.driver.findElement(By.id(AttrCollect.list.get(from))); 
		Utils.mark(AttrCollect.list.get(from));
		CheckLoading.ckLoad("xpath", to); 
		WebElement to1 = User_Console.driver.findElement(By.xpath(to));
		DragDrop.dragdrop(from1, to1);
		Utils.elapsedTime(start, "  ===>Loading the selected dragbale item and pannel:");
		Thread.sleep(1000);
	}
}
