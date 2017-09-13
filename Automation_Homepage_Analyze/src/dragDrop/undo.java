package dragDrop;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import htmlSourceOperation.CheckLoading;

public class undo {
	public static void cmdUndo(WebDriver driver){
		CheckLoading.ckLoad("id", "cmdUndo", "");
		driver.findElement(By.id("cmdUndo")).click();
		CheckLoading.ckLoad("className", "viz-image", "");
		CheckLoading.ckLoad("className", "reportEmpty", "");
	}
}
