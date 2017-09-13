package htmlSourceOperation;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.WebDriver;



public class parseDataFromAnalyze {
	public static String parse(WebDriver driver, String CubeName){
		Document doc = ParseHtml.Doc(driver);
		Elements ele = null;
		Element el = null;
		String count = "N/A";
		
		if(!CubeName.contains("Manufacturer")){
			ele = doc.getElementsByClass("pivotTableDataSection").select("div");
			count = ele.get(0).text().trim();
			count = count.replaceAll(",", "");
		}else{
			el = doc.getElementById("refreshSummary");
			String[] line = el.text().trim().split(" ");
			count = line[4];	
			count = count.substring(0, count.indexOf("C")-2).trim();
		}
//		Rows: 2000 out of 38821  Cols: 0
		return count;
	}
}
