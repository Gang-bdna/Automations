package dragDrop;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;

import java.util.Iterator;
import java.util.Set;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import htmlSourceOperation.CheckElement;
import htmlSourceOperation.parseDataFromAnalyze;
import userConsoleOperation.clawerTCount;
import global.Utils;

public class RepeatCreateCube {
	/**
	 * Param gcbs	:Cubes to store all Cubes
	 * Param acts	:Operations like Drop&Dorp, Filter, Double click.
	 * Param cbs	:Cubes to filter the duplicate one
	 * Param RName	:Analyze Report Name
	 * */
	private static HashMap<String, String> attrList = new HashMap<>();
	private static HashMap<String, String> COUNTS = new HashMap<>();
//	public static String RName;
	
	public static void rcCubes(WebDriver driver, HashSet<String> cbs, HashMap<String,String> ACount, String tout) throws InterruptedException, IOException{
		int expire = Integer.valueOf(tout);
		Iterator<String> itr = cbs.iterator();
		while(itr.hasNext()){
			String RName = "";
			RName = itr.next();
			//Create report
			CreateReport.cReport(RName);
			clawerTCount.wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.id("report-iframe")));
		    
		    //loading all attributes and its attirbute's ID
		    attrList = AttrCollect.attrClt("fieldListTreeContent", driver);	
		    
	    	//Mark label
	    	int track = 0;
	    	
	    	String attribute = ACount.get(RName);
	    	
		    //drag and drop
		    Set<String> attSet = attrList.keySet();
		    for(String att : attSet){
		    	if(att.contains(attribute)){
			    	DragDrop.doubleClick(attrList.get(att));
			    	boolean hidden = DragDrop.isHidden();
			    	track ++;
			    	
			    	//timmer
			    	boolean timeout = false;
			        long startTime = System.currentTimeMillis();
			        
				    //Wait until all data has been loaded.
				    boolean cancel_button = CheckElement.find("id", "progressPaneCancel");
				    Utils.info("Loading the " + track + "-th attribte: [" + att + "]");
				    long begintime = System.currentTimeMillis();
				    while (cancel_button && !hidden){
				    	cancel_button = CheckElement.find("id", "progressPaneCancel");
				    	long currenttime = System.currentTimeMillis();
				    	
				    	if((currenttime-begintime)/1000 > expire){
					    	Utils.info("Loading " + att + " is timeout.");
//					    	Utils.info("");
					    	//write attributes name
					    	timeout = true;
					    	break;
				    	}
				    }
				    if(!timeout){
					    long etime = System.currentTimeMillis();
				        long elapsedTime_sec = (etime - startTime) / 1000;
				        long elapsedTime_mil = (etime - startTime) % 1000;
				        String time = elapsedTime_sec + "." + elapsedTime_mil;
					    Utils.info("Loading data totally takes " + time + " secs.");
//					    Utils.cout("");

				    	
					    //check data
				    	Thread.sleep(200);
//				    	Utils.mark(CheckElement.find("class", "pivotTableContent", driver));
					    if(CheckElement.find("class", "pivotTableContent")){
					    	if(RName.toLowerCase().contains("manufacturer")){
						    	String cnt = parseDataFromAnalyze.parse(driver, RName);
								int removeDash = Integer.valueOf(cnt) - 1;
						    	Utils.info("Data are found in report : " + removeDash);
						    	COUNTS.put(RName, removeDash+"");
					    	}else{
						    	String cnt = parseDataFromAnalyze.parse(driver, RName);
						    	Utils.info("Data are found in report : " + cnt);
						    	COUNTS.put(RName, cnt);
					    	}
					    }
					    else if(CheckElement.find("class", "noDataHeader")){
					    	Utils.warning("No Data are found in report.");
					    	COUNTS.put(RName, "No Data");
					    }
					    else if(CheckElement.find("class", "errorBox")){
					    	Utils.error("Error exception happens.");
					    	COUNTS.put(RName, "Error Exception");
					    }
					    else {
					    	Utils.warning("Data checking is aborted due to attributes invisible issue.");
					    }
					    
				    }else{
				    	Utils.warning("Data checking is aborted due to timeout setting.");
				    }
				    Utils.cout();

				    //Undo 
				    undo.cmdUndo(driver);
		    	}
			}
			//go back
			driver.navigate().back();
			driver.switchTo().defaultContent();
//			driver.close();
			driver.navigate().refresh();
		}
	}
	
	//Return Analyze Counts
	public static HashMap<String, String> getAnalyzeCount(){
		return COUNTS;
	}
}
