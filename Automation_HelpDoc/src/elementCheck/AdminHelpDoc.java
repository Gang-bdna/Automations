package elementCheck;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import fileOperate.Crawler;
import fileOperate.FectchChp;
import fileOperate.loadVersion;
import global.Utils;
import main.IndexKeyWord;

public class AdminHelpDoc {
	
	private static HashSet<String> version = new HashSet<>();
	public static void loadAHD(WebDriver driver, String Cat, String file) throws InterruptedException, IOException{
		//initialize version
//		version.add("5.1");
//		version.add("5.2");
//		version.add("5.3");
//		version.add("5.4");
//		version.add("5.5");
		version = loadVersion.version(file);
		
		//Initialize Writer
		FileWriter docWrite;
		BufferedWriter bw = null;
		try {
			docWrite = new FileWriter(IndexKeyWord.result);
			bw = new BufferedWriter(docWrite); 
			
			//write title
			bw.append("KEY WORLD").append(",").append("RESULT");
			bw.flush();
			bw.newLine();
			
		} catch (IOException e) {
			Utils.error("Initialize "+ IndexKeyWord.result +" file failed");
		}
		
		if(Cat.equals("DP")){
			 Utils.cout('\n');
			 Utils.info("Start to load Admin Help Doc ...");
			 Thread.sleep(5000);
			 smokeCheck.Navigate(driver, "cssSelector", "#NormlizeHelp > div.helpDark");
			 
			//Get all window handles
			 Set<String> allHandles = driver.getWindowHandles();  

			 //Get current handle or default handle
			 //String currentWindowHandle = allHandles.iterator().next();

			 //Remove first/default Handle
			 //String firstHandle = allHandles.iterator().next();
			 allHandles.remove(allHandles.iterator().next());

			 //get the last Window Handle
			 String lastHandle = allHandles.iterator().next();
			 
			 //driver.switchTo().window(firstHandle);
			 //Close the first windows
			 driver.close();
			 //switch to second/last window
			 Thread.sleep(5000);
			 driver.switchTo().window(lastHandle);
//			 CheckLoading.ckLoading(driver);
			 Thread.sleep(10000);
			 
			 String title = driver.getTitle();
			 //get current URL
			 String url = driver.getCurrentUrl();
			 String ChpURL = url.replace("/index.htm", "/whdata/whtdata0.htm");
			 
			 driver.get(ChpURL);
			 String html = driver.getPageSource();
			 Document doc = Jsoup.parse(html);
			 Element linksOnPage = doc.select("script").get(1);
			 
			 //Utils.mark(linksOnPage.toString().trim());
			 
			 HashMap<String, ArrayList<String>> DPSet = new HashMap<>();
			 HashMap<String, ArrayList<String>> UCSet = new HashMap<>();
			 
			 String[] DPChpt = linksOnPage.toString().trim().split(";");

			 FectchChp.reformURL(DPChpt, url, Cat);	
			 DPSet = FectchChp.getChpSet();
			 //Utils.mark(DPSet.toString());
			 
			 //Start crawl pages
			 Crawler.crawler(DPSet, Cat);
			 
			 Utils.cout('\n');
			 Utils.info("Help Doc: [" + title + "] is launched.");
//			 v = v.substring(0, 3);
			 bw.append("Title Checking").append(",");
			 
			 int red = 0;
//			 version.remove(v);
			 for(String dpv : version){
				 if(title.contains(dpv)){
					 Utils.error("The version of Help Doc: [" + title + "] is not supposed to be showing up!");
					 red = red + 1;
				 }
			 }
			 
			 if(red==0){
				 bw.append("Passed");
				 bw.flush();
			 }else{
				 bw.append("Failed");
				 bw.flush();
			 }
			 
			 Utils.cout('\n');
			 driver.close();
			 bw.close();
		}
		else if(Cat.equals("UC")){
			Utils.info("Start to load User Console Help Doc ...");
			CheckLoading.ckLoad("id", "userDropDown", "");
	        driver.findElement(By.id("userDropDown")).click();
	        CheckLoading.ckLoad("id", "userConsoleHelp", "");
	        driver.findElement(By.id("userConsoleHelp")).click();
			 Set<String> allHandles = driver.getWindowHandles();  

			 //Remove first/default Handle
			 allHandles.remove(allHandles.iterator().next());

			 //get the last Window Handle
			 String lastHandle = allHandles.iterator().next();
			 
			 //Close the first windows
			 driver.close();
			 Thread.sleep(5000);
			 //switch to second/last window
			 driver.switchTo().window(lastHandle);
			 CheckLoading.ckLoading(driver);
			 Thread.sleep(3000);
			 String title = driver.getTitle();
			 
			 //get current URL
			 String url = driver.getCurrentUrl();
			 String ChpURL = url.replace("/index.htm", "/whdata/whtdata0.htm");

			 //retrieve Chp's title
			 driver.get(ChpURL);
			 String html = driver.getPageSource();
			 Document doc = Jsoup.parse(html);
			 Element ChpTitle = doc.select("script").get(1);

			 HashMap<String, ArrayList<String>> DPSet = new HashMap<>();
			 HashMap<String, ArrayList<String>> UCSet = new HashMap<>();
			 
			 String[] Chpt = ChpTitle.toString().trim().split(";");
			 FectchChp.reformURL(Chpt, url, Cat);	
			 DPSet = FectchChp.getChpSet();
			 
			 //Start crawl pages
			 Crawler.crawler(DPSet, Cat);

			 Utils.cout('\n');
			 Utils.info("Help Doc: [" + title + "] is launched.");
			 
//			 v = v.substring(0, 3);
			 bw.append("Title Checking").append(",");
			 
			 int red = 0;
//			 version.remove(v);
			 for(String dpv : version){
				 if(title.contains(dpv)){
					 Utils.error("The version of Help Doc: [" + title + "] is not supposed to be showing up!");
					 red = red + 1;
				 }
			 }
			 
			 if(red==0){
				 bw.append("Passed");
				 bw.flush();
			 }else{
				 bw.append("Failed");
				 bw.flush();
			 }
			 			 
			 Utils.cout('\n');
			 driver.close();
			 bw.close();
		}
		else{
			Utils.error("Openning the Help Doc of " + Cat + " failed.");
		}
	}
}
