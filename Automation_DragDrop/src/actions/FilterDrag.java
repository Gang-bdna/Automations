package actions;

import java.io.IOException;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.openqa.selenium.By;
import main.User_Console;
import fileOperate.ParseHtml;

public class FilterDrag {
	/**
	 * Author : Gang Liu	
	 * Created: Feb. 02, 2016
	 * @throws IOException 
	 */	
	public static void filterDrag(String element, String where, String content) throws InterruptedException, IOException{
	    DragDrop.rightClick(element);
	    User_Console.driver.findElement(By.id("cmdFieldFilter_text")).click();
	    User_Console.driver.findElement(By.id("FT_searchText")).clear();
	    User_Console.driver.findElement(By.id("FT_searchText")).sendKeys(content);
	    User_Console.driver.findElement(By.id("FT_searchBy")).click();
	    Document doc = ParseHtml.Doc(User_Console.driver);
	    Element ele = doc.getElementById("FT_valueList");
	    String id = ele.select("div").get(1).attr("id");
	    DragDrop.doubleClick(id);
	    User_Console.driver.findElement(By.id(id)).click();
	    User_Console.driver.findElement(By.id("dlgBtnSave")).click();
	    DragFromTo.DFT(element, where);
	}
}
