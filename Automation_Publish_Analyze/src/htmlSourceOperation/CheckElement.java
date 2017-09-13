package htmlSourceOperation;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.openqa.selenium.WebDriver;

import htmlSourceOperation.ParseHtml;
import userConsoleOperation.clawerTCount;

public class CheckElement {
	static WebDriver driver = clawerTCount.driver;
	@SuppressWarnings("null")
	
	public static boolean find(String type, String cont){
		Document doc = ParseHtml.Doc(driver);
		Element ele = null;
		if(type.equals("id")){
			ele = doc.getElementById(cont);
		}else if(type.equals("class")){
//			ele.ownerDocument().getElementsByClass(cont);
			ele = doc.getElementsByClass(cont).first();
		}
						
		if(ele == null)
			return false;
		else 
			return true;
	}
}
