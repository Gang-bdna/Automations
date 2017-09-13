package loadingReport;

import java.util.HashMap;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import org.jsoup.select.Elements;
import org.openqa.selenium.WebDriver;


import fileOperate.ParseHtml;
import global.Utils;

public class CollectCannedReportsName {
	//private static HashSet<String> ReportNameSet = new HashSet<>();
	private static HashMap<String, String> ReportNameSet = new HashMap<>();
	//private static HashMap<String, String> XPatch = new HashMap<>();
	
	public static void collect(WebDriver driver){
		Document doc = ParseHtml.Doc(driver);
		Elements attrs = doc.getElementsByClass("col-lg-7");
		
		Utils.info("Start to collect the name of canned reports ...");
		for(Element att : attrs){
			String CName;
			String CType;
			//String xpth;
			
			CName = att.getElementsByClass("reports-item-title").text().trim();
			CType = att.getElementsByClass("h6").text().trim();
			//xpth = att.getElementsByClass("reports-item-title");
			//Utils.mark(xpth);
			
			if(!CName.isEmpty()){
				//Utils.mark(CName + "==" + CType);
				ReportNameSet.put(CName, CType);
			}
			
		}
		//Utils.cout(ReportNameSet.size());
	}
	
	public static HashMap<String, String> getRptNameSet(){
		return ReportNameSet;
		
	}
	
	public static int getSize(){
		return ReportNameSet.size();
		
	}
}
