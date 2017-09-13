package fileOperate;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import elementCheck.CheckLoading;
import global.Utils;
import main.HelpDoc;
import main.IndexKeyWord;

public class Crawler {
	private static FileWriter docWrite;
	private static BufferedWriter bw;
	private static ArrayList<String> docs = new ArrayList<>();
	private static HashMap<String, Integer> DupCk = new HashMap<>();
	public static String content;
	
	public static void crawler(HashMap<String, ArrayList<String>> URL, String DP) throws IOException, InterruptedException{
		if(DP.toLowerCase().equals("dp")){
			
			content = IndexKeyWord.location + "/DPHelp_Chapeters";
			File file = new File(content);
			if(!file.exists()){
				file.mkdirs();
				//Utils.mark(content + " is created!");
			}else if(file.exists() && file.length()!=0){
				File [] files = file.listFiles();
				for(File f : files){
					f.delete();
				}
			}
			
			 for(String url : URL.get(DP)){
				 HelpDoc.driver.get(url);
				 CheckLoading.ckLoad("className", "whtbtnshow", "Loading");
				 String html = HelpDoc.driver.getPageSource();
				 Document doc = Jsoup.parse(html);
				 Elements paragraph = doc.body().select("p");
				 String temp = doc.title().toString().trim(); 
				 
				 String title = null;
				 
				 if(!DupCk.containsKey(temp)){
					 DupCk.put(temp, 1);
					 title = content + "/" + temp;
					 docs.add(temp);
					 
					try {
						docWrite = new FileWriter(title);
						bw = new BufferedWriter(docWrite); 
						
					} catch (IOException e) {
						Utils.error("Initialize "+url+" file failed!");
					}
					//Utils.mark(title);
					 bw.append(temp);
					 bw.newLine();
					 
					 for(Element pgh : paragraph){
						 bw.append(pgh.text().trim());
						 bw.newLine();
						 bw.flush();
					 }
					 
					 bw.close();
					 Utils.info("Parsing the content of [Admin Console Help Doc] : " + doc.title().toString().trim());
				 }
			}
		}
		else if(DP.toLowerCase().equals("uc")){
			
			content = IndexKeyWord.location + "/UCHelp_Chapeters";
			File file = new File(content);
			if(!file.exists()){
				file.mkdirs();
				//Utils.mark(content + " is created!");
			}else if(file.exists() && file.length()!=0){
				File [] files = file.listFiles();
				for(File f : files){
					f.delete();
				}
			}
			
			 for(String url : URL.get(DP)){
				 HelpDoc.driver.get(url);
				 CheckLoading.ckLoad("className", "whtbtnshow", "Loading");
				 String html = HelpDoc.driver.getPageSource();
				 Document doc = Jsoup.parse(html);
				 Elements paragraph = doc.body().select("p");
				 String temp = doc.title().toString().trim();
				 String title = null;
				 
				 if(!DupCk.containsKey(temp)){
					 DupCk.put(temp, 1);
					 title = content + "/" + temp;
					// Utils.mark(title);
					 docs.add(temp);
					 
						try {
							docWrite = new FileWriter(title.toString());
							bw = new BufferedWriter(docWrite); 
							
						} catch (Exception e) {
							Utils.error("Initialize " + title + " file failed!");
							//e.printStackTrace();
						}
						//Utils.mark(title);
						 bw.append(temp);
						 bw.newLine();
						 
						 for(Element pgh : paragraph){
							 bw.append(pgh.text().trim());
							 bw.newLine();
							 bw.flush();
						 }
						 bw.close();
						 Utils.info("Parsing the content of [User Console Help Doc] : " + doc.title().toString().trim());
				 }
			}
		}
		
		
	}
}
