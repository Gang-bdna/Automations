package loadingReport;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;

import elementCheck.CheckLoading;
import global.Utils;
import main.Canned_Reports_DateInt;

public class CreateReport {
	/**
	 * Author : Gang Liu	
	 * Created: March 08, 2017
	 */
	public static void cReport(String report){
		/* Feature loading check */
//		CheckLoading.ckLoad("className", "recent-reports-item"); 
		CheckLoading.ckLoad("className", "recent-report"); 
		Canned_Reports_DateInt.driver.findElement(By.xpath("(//button[@type='button'])[5]")).click();
		Canned_Reports_DateInt.driver.findElement(By.linkText("Analyzer Report")).click();
	    CheckLoading.ckLoad("className", "modal-footer"); 
		CheckLoading.ckLoad("id", "select-multiple");
		
	    new Select(Canned_Reports_DateInt.driver.findElement(By.id("select-multiple"))).selectByVisibleText(report);
	    Canned_Reports_DateInt.driver.findElement(By.cssSelector("button.btn.btn-primary")).click();
	    Utils.cout("  New '" +report+"' Report has been Created!");
	    Utils.cout("");
	}
}
