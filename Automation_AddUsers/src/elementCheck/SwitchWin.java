package elementCheck;

import java.util.Iterator;
import java.util.Set;

import org.openqa.selenium.WebDriver;


public class SwitchWin {
	public static WebDriver windows(WebDriver driver){
	//Get all window handles
	 Set<String> allHandles = driver.getWindowHandles();  
	 String subWindowHandler = null;
	 Iterator<String> iterator = allHandles.iterator();
	 while (iterator.hasNext()){
	     subWindowHandler = iterator.next();
	 }
	 

	 driver.switchTo().window(subWindowHandler);
	 return driver.switchTo().window(subWindowHandler);
	}
}
