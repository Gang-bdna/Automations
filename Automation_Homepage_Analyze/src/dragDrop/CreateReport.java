package dragDrop;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;

import htmlSourceOperation.CheckLoading;
import userConsoleOperation.clawerTCount;
import global.Utils;

public class CreateReport {
	public static void cReport(String report){
		/* Feature loading check */
//		CheckLoading.ckLoad("className", "recent-reports-item"); 
		CheckLoading.ckLoad("className", "recent-report", ""); 
		clawerTCount.driver.findElement(By.xpath("(//button[@type='button'])[5]")).click();
		clawerTCount.driver.findElement(By.linkText("Analyzer Report")).click();
	    CheckLoading.ckLoad("className", "modal-footer", ""); 
		CheckLoading.ckLoad("id", "select-multiple", "");
		
	    new Select(clawerTCount.driver.findElement(By.id("select-multiple"))).selectByVisibleText(report);
	    clawerTCount.driver.findElement(By.cssSelector("button.btn.btn-primary")).click();
	    Utils.info(report + "' Report has been Created!");
//	    Utils.cout();
	}
}
