package elementCheck;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.openqa.selenium.WebDriver;

import main.CheckVersion;
import fileOperate.ParseHtml;

public class CheckElement {
//	static WebDriver driver = AddUser.driver;
	@SuppressWarnings("null")
	
	public static boolean find(String type, String cont){
		WebDriver driver = CheckVersion.driver;
		Document doc = ParseHtml.Doc(driver);
		Element ele = null;
		if(type.equals("id")){
			ele = doc.getElementById(cont);
		}else if(type.equals("class")){
			ele.ownerDocument().getElementsByClass(cont);
		}
						
		if(ele == null)
			return false;
		else 
			return true;
	}
	
	public static boolean find(WebDriver driver, String type, String cont){
//		Document doc = ParseHtml.Doc(driver);
		String html = driver.getPageSource();
		Document doc = Jsoup.parse(html);
		Element ele = null;
		if(type.equals("id")){
			ele = doc.getElementById(cont);
		}else if(type.equals("class")){
			ele.ownerDocument().getElementsByClass(cont);
		}
						
		if(ele == null)
			return false;
		else 
			return true;
	}
}
