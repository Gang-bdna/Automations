package elementCheck;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Hashtable;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.openqa.selenium.WebDriver;

import fileOperate.ParseHtml;
import global.Utils;


public class AttrCollect{
	/**
	 * Loading all the items and their names.
	 * Using item as the key to store them into a hash table.
	 * Rename the items which share the same name.
	 */
	
	public static HashMap<String, String> list = new HashMap<>(); 
	
	public static HashMap<String, String> attrClt(WebDriver driver) throws IOException, InterruptedException{		
		//Open New Role Assignment page
		CheckLoading.ckLoad("id", "btnNewRole", "");
		smokeCheck.Navigate(driver, "id", "btnNewRole");
		CheckLoading.ckLoading(driver);
		
		//Switch to popup's frame
		driver.switchTo().frame("TB_iframeContent");
		CheckLoading.ckLoad("id", "btnSave", "");
		
		//Retrieve the IDs of Roles
		Thread.sleep(1000);
		Document doc = ParseHtml.Doc(driver);
		
		//Get role-list
		Elements attrs = doc.getElementsByClass("role-list").select("tbody").get(0).getElementsByClass("clone");
		
		//Set default level
		list.put("DEFAULT", "ckbAllChecked");
		
		//Scan all items, store them into hash table
		for(int item_id = 0; item_id <attrs.size(); item_id++){
			Elements checkNode = null;
			String item = "";
			try{
				list.put(attrs.get(item_id).select("label").text().trim().toUpperCase(), attrs.get(item_id).select("input").get(0).id());
			}catch(Exception e){	
			}
		}
		
		return list;
	}
}