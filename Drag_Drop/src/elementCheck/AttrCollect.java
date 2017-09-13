package elementCheck;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;


import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.openqa.selenium.WebDriver;
import fileOperate.ParseHtml;
import global.Utils;


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
	
//	public static HashMap<String, String> attributesList = new HashMap<>(); 
//	private static HashSet<String> set = new HashSet<>(); 
//	private static FileWriter docWrite = null;
	
	public static HashMap<String, String> attrClt(String attr_id, WebDriver driver) throws IOException{		
		HashSet<String> set = new HashSet<>(); 
		HashMap<String, String> attributesList = new HashMap<>(); 
		CheckLoading.ckLoad("id", attr_id);
		Document doc = ParseHtml.Doc(driver);
		Elements attrs = doc.getElementById(attr_id).getElementsByClass("folderContent").select("div");
		int duplicate = 1;
//		try {
//			//To-do
//			docWrite = new FileWriter("MD.cvs");
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		//To-do
//		BufferedWriter bw = new BufferedWriter(docWrite); 
		for(int item_id = 1; item_id <attrs.size(); item_id++){
			String iid = "dojoUnique" + item_id;
			boolean thrown =false;
			String item = "";
			try{
				item = doc.getElementById(attr_id).getElementById(iid).text().trim();
			}catch(Exception e){
				thrown = true;
//				e.printStackTrace();
			}
			
			item = item.replace(",", "");
			if(!thrown && item!=""){
//				int length = attributesList.size();
				
				//Save dimension to cvs file
				//To-do
//				bw.append(item).append('\n');
				
				if(set.contains(item)){
					String ex_item = item + "_" + duplicate;
					attributesList.put(ex_item, iid);
//					Utils.mark(ex_item);
					duplicate ++;
				}else{
					attributesList.put(item, iid);
					set.add(item);
				}
			}
		}
//		bw.close();
//		Utils.cout(attributesList.toString());
		Utils.info("Total " + attributesList.size() + " attributes are found.");
		Utils.cout();
		return attributesList;
	}
}