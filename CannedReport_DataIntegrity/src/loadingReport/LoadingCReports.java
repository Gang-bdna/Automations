package loadingReport;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import elementCheck.CheckLoading;

public class LoadingCReports {
	/**
	 * Author : Gang Liu	
	 * Created: March 08, 2017
	 */
	
	public static void loadCRs(WebDriver driver){
		/*Loading Canned Reports Management Page*/
	    driver.findElement(By.id("reportMenu")).click();
	    driver.findElement(By.linkText("Report Management")).click();
	    CheckLoading.ckLoad("className", "reports-item-shared"); 
	}
}
