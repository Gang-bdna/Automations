package interativeReport;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Hashtable;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.openqa.selenium.WebDriver;

import fileOperate.ParseHtml;
import global.Utils;


public class IR_AttrCollect{
	/**
	 * Loading all the items and their names.
	 * Using item as the key to store them into a hash table.
	 * Rename the items which share the same name.
	 */
	
	public static Hashtable<String, String> list = new Hashtable<>(); 
//	private static HashSet<String> set = new HashSet<>(); 
	private static FileWriter docWrite = null;
//	private static FileWriter dw = null;
	
	public static Hashtable<String, String> attrClt(String attr_id, WebDriver driver) throws IOException, InterruptedException{		
		IR_CheckLoading.ckLoad("id", attr_id);
		Thread.sleep(1000);
		Document doc = ParseHtml.Doc(driver);
//		try {
//			//To-do
//			Thread.sleep(2000);
//			dw = new FileWriter("html.txt");
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		dw.write(doc.text().trim());
//		dw.close();
		Elements attrs = doc.getElementById(attr_id).getElementById("fieldlist").select("div");
		
				
		try {
			//To-do
			docWrite = new FileWriter("MD.cvs");
		} catch (IOException e) {
			e.printStackTrace();
		}
		//To-do
		BufferedWriter bw = new BufferedWriter(docWrite); 
		
		//Scan all items, store them into hash table
		for(int item_id = 1; item_id <attrs.size(); item_id++){
			Elements checkNode = null;
			String item = "";
			try{
				item = attrs.get(item_id).id();
			}catch(Exception e){	
			}
			checkNode = attrs.get(item_id).select("span");
			
			boolean thrown =false;
			String att = "";
			try{
				att = doc.getElementById(attr_id).getElementById(item).text().trim();
			}catch(Exception e){
				thrown = true;
			}	
			if((!thrown)&&!att.isEmpty()&&checkNode.isEmpty()){
				//Save dimension to cvs file
				bw.append(att).append('\n');
			}
		}
		
		bw.close();
		return list;
	}
}