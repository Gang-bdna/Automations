package actions;

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
import elementCheck.AttrCollect;
import elementCheck.CheckElement;
import main.User_Console;
import global.Utils;

public class RepeatCreateCube {
	/**
	 * Param gcbs	:Cubes to store all Cubes
	 * Param acts	:Operations like Drop&Dorp, Filter, Double click.
	 * Param cbs	:Cubes to filter the duplicate one
	 * Param RName	:Analyze Report Name
	 * */
	private static HashMap<String, String> attrList = new HashMap<>();
//	private static HashSet<String> cbs = new HashSet<String>();
//	public static String RName;
	
	public static void rcCubes(WebDriver driver, HashSet<String> cbs, String tout) throws InterruptedException, IOException{
//		cbs = Prop.CUBES;
//		cbs.add("Technopedia: CPU");
//		cbs.add("Technopedia: Software");
		int expire = Integer.valueOf(tout);
		Iterator<String> itr = cbs.iterator();
		while(itr.hasNext()){
			String RName = "";
			RName = itr.next();
			//Create report
			CreateReport.cReport(RName);
			User_Console.wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.id("report-iframe")));
		    
			//Initialize Writer
			FileWriter docWrite;
			BufferedWriter bw = null;
			try {
				RName = RName.replace(": ","_");
				RName = RName.replace(" ","_");

				docWrite = new FileWriter(User_Console.opt + "/" + RName + ".csv");
				bw = new BufferedWriter(docWrite); 
				
				//write title
				bw.append("Attributes").append(",").append("Response Time").append(",").append("Has Data?").append(",").append("Results");
				bw.flush();
				bw.newLine();
				
			} catch (IOException e) {
				Utils.error("Initialize "+ "Output/" +RName + ".csv" +" file failed");
				e.printStackTrace();
			}
		    
		    //loading all attributes and its attirbute's ID
		    attrList = AttrCollect.attrClt("fieldListTreeContent", driver);	

	    	//Mark label
	    	int track = 0;
	    	
		    //drag and drop
		    Set<String> attSet = attrList.keySet();
		    for(String att : attSet){
		    	DragDrop.doubleClick(attrList.get(att));
		    	boolean hidden = DragDrop.isHidden();
		    	track ++;
		    	
		    	//write attributes name
		    	bw.append(att).append(",");
		    	
		    	//timmer
		    	boolean timeout = false;
		        long startTime = System.currentTimeMillis();
		        
			    //Wait until all data has been loaded.
			    boolean cancel_button = CheckElement.find("id", "progressPaneCancel", driver);
			    Utils.info("Loading the " + track + "-th attribte: [" + att + "]");
			    long begintime = System.currentTimeMillis();
			    while (cancel_button && !hidden){
			    	cancel_button = CheckElement.find("id", "progressPaneCancel", driver);
			    	long currenttime = System.currentTimeMillis();
			    	
			    	if((currenttime-begintime)/1000 > expire){
				    	Utils.info("Loading " + att + " is timeout.");
//				    	Utils.info("");
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
//				    Utils.cout("");
			    	//write attributes name
			    	bw.append(time).append(",");
			    	
				    //check data
			    	Thread.sleep(200);
//			    	Utils.mark(CheckElement.find("class", "pivotTableContent", driver));
				    if(CheckElement.find("class", "pivotTableContent", driver)){
				    	bw.append("Y").append(",").append(User_Console.HASDATA);
				    	Utils.info("Data are found in report.");
				    }else if(CheckElement.find("class", "noDataHeader", driver)){
				    	bw.append("N").append(",").append(User_Console.NODATA);
				    	Utils.warning("No Data are found in report.");
				    }else if(CheckElement.find("class", "errorBox", driver)){
				    	bw.append("Error Exception").append(",").append(User_Console.ERROR);
				    	Utils.error("Error exception happens.");
				    }else {
				    	bw.append("Attribute gets hidden by design").append(",").append(User_Console.INVISIBLE);
				    	Utils.warning("Data checking is aborted due to attributes invisible issue.");
				    }
				    
			    }else{
			    	bw.append("Time Out (180 secs)").append(",").append("Time Out").append(",").append(User_Console.TIMEOUT);
			    	Utils.warning("Data checking is aborted due to timeout setting.");
			    }
			    Utils.cout();
			    bw.flush();
			    bw.newLine();
			    //Undo 
			    undo.cmdUndo(driver);
			}
		    bw.close();
			//go back
			driver.navigate().back();
			driver.switchTo().defaultContent();
//			driver.close();
			driver.navigate().refresh();
			// Login to testing environment
//			driver.close();
//			try {
//			    Runtime.getRuntime().exec("taskkill /F /IM firefox.exe");
//			} catch (IOException e) {
//			    e.printStackTrace();
//			}
//		    Login.login(User_Console.usn, User_Console.psw, User_Console.svr);
		}
	}
}
