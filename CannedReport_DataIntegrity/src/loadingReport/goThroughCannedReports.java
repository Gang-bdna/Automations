package loadingReport;

import java.util.HashMap;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import elementCheck.CheckLoading;
import global.Utils;

public class goThroughCannedReports {
	private static Set<String> keys;
	
	public static void openCReports(HashMap<String, String> RNSet, WebDriver driver){
		keys = RNSet.keySet();
		for (String key : keys){
			Utils.mark(key);
			boolean mark = false;
			int scrollDown = 500;
			
			JavascriptExecutor jse = (JavascriptExecutor)driver;			

			while (!mark){
				try{
					//driver.findElement(By.xpath("//a[contains(., '"+ key +"')]")).click();
					driver.findElement(By.linkText(key)).click();
					CheckLoading.ckLoading(driver);
					mark = true;
					
					//if(RNSet.get(key).equals("Dashboard")){
					//	driver.switchTo().defaultContent();
					//	CheckLoading.ckLoad("id", "visualPanelElement");
					//}
					
					//Thread.sleep(5000);
				}catch(Exception e){
					//mark = false;
					jse.executeScript("scroll(0, "+ scrollDown + ")");
					scrollDown = scrollDown + 500;
				}
			}
			//frame_01489190332738

		    Utils.cout(key + " loading done.");
		    
			//go back
			driver.navigate().back();
			driver.switchTo().defaultContent();
		}
	}
}
