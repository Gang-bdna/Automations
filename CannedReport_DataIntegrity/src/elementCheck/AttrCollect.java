package elementCheck;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Hashtable;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.openqa.selenium.WebDriver;
import fileOperate.ParseHtml;


public class AttrCollect{
	/**
	 * Author : Gang Liu	
	 * Created: Feb. 05, 2016
	 */
	
	
	/**
	 * Loading all the items and their names.
	 * Using item as the key to store them into a hash table.
	 * Rename the items which share the same name.
	 */
	
	public static Hashtable<String, String> list = new Hashtable<>(); 
	private static HashSet<String> set = new HashSet<>(); 
	private static FileWriter docWrite = null;
	
	public static Hashtable<String, String> attrClt(String attr_id, WebDriver driver) throws IOException{		
		CheckLoading.ckLoad("id", attr_id);
		Document doc = ParseHtml.Doc(driver);
		Elements attrs = doc.getElementById(attr_id).getElementsByClass("folderContent").select("div");
		int duplicate = 1;
		
		try {
			//To-do
			docWrite = new FileWriter("MD.cvs");
//			docWrite = new FileWriter("test.txt");
		} catch (IOException e) {
			e.printStackTrace();
		}
		//To-do
		BufferedWriter bw = new BufferedWriter(docWrite); 
		//Scan all items, store them into hash table
		//Use item as the key
		for(int item_id = 1; item_id <attrs.size(); item_id++){
			String iid = "dojoUnique" + item_id;
			boolean thrown =false;
			String item = "";
			try{
				item = doc.getElementById(attr_id).getElementById(iid).text().trim();
			}catch(Exception e){
				thrown = true;
			}
			
			if(!thrown && item!=""){
				int length = list.size();
				set.add(item);
				
				//Save dimension to cvs file
				//To-do
				bw.append(item).append('\n');
				
				if(set.size()==length){
					String ex_item = item + "_" + duplicate;
					list.put(ex_item, iid);
					duplicate ++;
				}else{
					list.put(item, iid);
				}
			}
		}
		bw.close();
		return list;
	}
}