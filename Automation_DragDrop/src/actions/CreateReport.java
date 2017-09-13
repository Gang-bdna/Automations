package actions;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;

import elementCheck.CheckLoading;
import main.User_Console;
import global.Utils;

public class CreateReport {
	public static void cReport(String report){
		/* Feature loading check */
//		CheckLoading.ckLoad("className", "recent-reports-item"); 
		CheckLoading.ckLoad("className", "recent-report"); 
		User_Console.driver.findElement(By.xpath("(//button[@type='button'])[5]")).click();
		User_Console.driver.findElement(By.linkText("Analyzer Report")).click();
	    CheckLoading.ckLoad("className", "modal-footer"); 
		CheckLoading.ckLoad("id", "select-multiple");
		
	    new Select(User_Console.driver.findElement(By.id("select-multiple"))).selectByVisibleText(report);
	    User_Console.driver.findElement(By.cssSelector("button.btn.btn-primary")).click();
	    Utils.cout("  New '" +report+"' Report has been Created!");
	    Utils.cout("");
	}
}
