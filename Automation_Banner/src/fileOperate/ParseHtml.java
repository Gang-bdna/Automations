package fileOperate;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.openqa.selenium.WebDriver;

import main.PatchBanner;;
/**
 * Author : Gang Liu	
 * Created: Feb. 03, 2016
 */	
public class ParseHtml {
	public static Document Doc(WebDriver driver){
//		CheckLoading.ckLoad("className", "pivotTableRowLabelSection");
//		CheckLoading.ckLoad("className", "ZONE_rowAttributes");
		String html = driver.getPageSource();
		Document doc = Jsoup.parse(html);
//		FileWriter docWrite = new FileWriter("html.txt", true);
//		docWrite.append(doc.html());
		return doc;
	}
	public static Document Doc(){
//		CheckLoading.ckLoad("className", "pivotTableRowLabelSection");
//		CheckLoading.ckLoad("className", "ZONE_rowAttributes");
		String html = PatchBanner.driver.getPageSource();
		Document doc = Jsoup.parse(html);
//		FileWriter docWrite = new FileWriter("html.txt", true);
//		docWrite.append(doc.html());
		return doc;
	}
}
