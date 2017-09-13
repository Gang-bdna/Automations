package htmlSourceOperation;

import java.util.HashMap;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.WebDriver;

import global.Utils;


public class parseDataFromHP {
	private static HashMap<String, String> UCount = new HashMap<>();
	
	public static void parse(WebDriver driver){
		//Collect html source code
		Utils.info("Start collect technopedia data from User Console homepage...");
		CheckLoading.ckLoad("className", "home-footer", "UC Homepage");
		Document doc = ParseHtml.Doc(driver);
		Elements ele = null;

		//Write technopedia into map
		ele = doc.getElementsByClass("home-footer").select("span");
		for(Element e : ele){
			String source = e.toString();
			String countString = e.text().trim();
			countString = countString.replaceAll(",", "");
//			int count = Integer.valueOf(countString);
			if(source.contains("TECHNOPEDIA_MANUFACTURER")){
				UCount.put("Technopedia: Manufacturer", countString);
			}else if(source.contains("TECHNOPEDIA_SOFTWARE")){
				UCount.put("Technopedia: Software", countString);
			}else if(source.contains("TECHNOPEDIA_HARDWARE")){
				UCount.put("Technopedia: Hardware", countString);
			}
		}
		
		Utils.info(UCount);
		Utils.info("Technopedia cunt collection is completed.");
		Utils.cout();
	}
	
	//Return UC count.
	public static HashMap<String, String> getUCount(){
		return UCount;
	}
}
