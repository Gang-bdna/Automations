package elementCheck;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.openqa.selenium.WebDriver;

import main.User_Console;
import fileOperate.ParseHtml;

public class CheckElement {
	static WebDriver driver = User_Console.driver;
	@SuppressWarnings("null")
	public static boolean find(String type, String cont){
		Document doc = ParseHtml.Doc(driver);
		Element ele = null;
		if(type.equals("id")){
			ele = doc.getElementById(cont);
		}else if(type.equals("class")){
			ele.ownerDocument().getElementsByClass(cont);
		}
						
		if(ele == null)
			return false;
		else return true;
	}
}
