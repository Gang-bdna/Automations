package elementCheck;

import java.util.HashSet;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import fileOperate.ParseHtml;
import global.Utils;

public class CubesCollect {
	private static HashSet<String> set = new HashSet<>(); 
	public static HashSet<String> Cubes(WebDriver driver) throws InterruptedException{
		//open Cubes banner
		CheckLoading.ckLoad("className", "recent-report"); 
		driver.findElement(By.xpath("(//button[@type='button'])[5]")).click();
		driver.findElement(By.linkText("Analyzer Report")).click();
	    CheckLoading.ckLoad("className", "modal-footer"); 
		CheckLoading.ckLoad("id", "select-multiple");
		
		
		//loading cubes
		Utils.cout();
		Utils.info("Loading Cubes Nmae...");
		CheckLoading.ckLoad("id", "select-multiple");
		Document doc = ParseHtml.Doc(driver);
		Elements attrs = doc.getElementById("select-multiple").getElementsByClass("ng-binding").select("option");
		
		for(int att = 0; att<attrs.size(); att++){
			String item = "";
			item = attrs.get(att).text().trim();
			set.add(item);
		}
		Utils.info("Loading is done, total " + attrs.size() + " Cubes are found.");
		Utils.cout();
		
		CheckLoading.ckLoad("className", "modal-footer");
	    driver.findElement(By.cssSelector("div.modal-footer > button.btn.btn-default")).click();
	    
	    CheckLoading.ckLoad("className", "btn-group");
	    driver.navigate().refresh();
	    CheckLoading.ckLoad("className", "btn-group");
		return set;
	}
}
