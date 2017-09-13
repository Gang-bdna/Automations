package actions;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import elementCheck.AttrCollect;
import elementCheck.CheckLoading;
import main.User_Console;
import global.Utils;

public class DragFT {
	/**
	 * Author : Gang Liu	
	 * Created: Jan. 22, 2016
	 */
	
//	drag item by name
	private static String rowId;
	public static void GDFT(String from, String to) throws InterruptedException{
		//loading actions
		if(to.equals("Row")){
			rowId = "rows_ui";
		}if(to.equals("Column")){
			rowId = "columns_ui";
		}else if(to.equals("Measure")){
			rowId = "measures_ui";
		}
		
		Utils.cout("  Ready to drag!");   
		long start = System.currentTimeMillis(); 
//		Utils.mark(AttrCollect.list.get(from));
	    CheckLoading.ckLoad("id", AttrCollect.list.get(from)); 
		WebElement from1 = User_Console.driver.findElement(By.id(AttrCollect.list.get(from))); 
		
//		Utils.mark(AttrCollect.list.get(from));
		CheckLoading.ckLoad("id", rowId); 
		WebElement to1 = User_Console.driver.findElement(By.id(rowId));
		DragDrop.dragdrop(from1, to1);
		Utils.elapsedTime(start, "  ===>Loading the selected dragbale item and pannel:");
		Thread.sleep(100);
	}

}
