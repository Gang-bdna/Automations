package interativeReport;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;

import global.Utils;
import main.IR_UserConsole;

public class IR_CreateReport {
	public static void cReport(String report){
		/* Feature loading check */
//		CheckLoading.ckLoad("className", "recent-reports-item"); 
		IR_CheckLoading.ckLoad("className", "recent-report"); 
		IR_UserConsole.driver.findElement(By.xpath("(//button[@type='button'])[5]")).click();
		IR_UserConsole.driver.findElement(By.linkText("Interactive Report")).click();
		IR_CheckLoading.ckLoad("className", "modal-footer"); 
		IR_CheckLoading.ckLoad("id", "select-multiple");
		
	    new Select(IR_UserConsole.driver.findElement(By.id("select-multiple"))).selectByVisibleText(report);
	    IR_UserConsole.driver.findElement(By.cssSelector("button.btn.btn-primary")).click();
	    Utils.info("New '" +report+"' Report has been Created!");
	    Utils.cout();
	}
}


