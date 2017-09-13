package elementCheck;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.openqa.selenium.WebDriver;

import main.PatchBanner;
import fileOperate.ParseHtml;

public class CheckElement {
	static WebDriver driver = PatchBanner.driver;
	@SuppressWarnings("null")
	
	public static boolean find(String type, String cont){
		Document doc = ParseHtml.Doc(driver);
		Element ele = null;
		if(type.equals("id")){
			ele = doc.getElementById(cont);
		}else if(type.equals("class")){
//			ele = doc.getElementsByClass(cont).select("div").get(0);
//			Utils.info(ele.toString());
			ele.ownerDocument().getElementsByClass(cont);
		}
						
		if(ele == null)
			return false;
		else 
			return true;
	}
	
	public static boolean checkAttrByClass(String type, String cont){
		Document doc = ParseHtml.Doc(driver);
		Element ele = null;
		boolean isUp = false;
		
		ele = doc.getElementsByClass(cont).select("div").get(0);
		String attributes = ele.toString().trim();
//		Utils.info(attributes);
						
		if(attributes.contains("style=\"display:block;\"")){
			isUp = true;
		}
		
		return isUp;
	}
}
