package actions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;

import org.jsoup.nodes.Document;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

import actions.DragFT;
import actions.TakeScreenShot;
import elementCheck.AttrCollect;
import elementCheck.CheckElement;
import main.User_Console;
import fileOperate.CvsFileWriter;
import fileOperate.ParseHtml;
import global.Prop;
import global.Utils;

public class RepeatCreateCube {
	/**
	 * Param gcbs	:Cubes to store all Cubes
	 * Param acts	:Operations like Drop&Dorp, Filter, Double click.
	 * Param cbs	:Cubes to filter the duplicate one
	 * Param RName	:Analyze Report Name
	 * */
	private static Hashtable<String, Hashtable<String, ArrayList<ArrayList<String>>>> gcbs = Prop.cubes;
	private static Hashtable<String, ArrayList<ArrayList<String>>> acts = new Hashtable<>();
	private static HashSet<String> cbs = new HashSet<String>();
	public static String RName;
	
	public static void rcCubes(WebDriver driver) throws InterruptedException, IOException{
		cbs = Prop.CUBES;
		Iterator<String> itr = cbs.iterator();
		while(itr.hasNext()){
			RName = itr.next();
			//Create report
			CreateReport.cReport(RName);
		    User_Console.wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.id("report-iframe")));
		    AttrCollect.attrClt("fieldListTreeContent", driver);	  
		    Utils.loading(AttrCollect.attrClt("fieldListTreeContent", driver));
		    //drag and drop
			acts = gcbs.get(RName);
			Enumeration ids = acts.keys();
			while(ids.hasMoreElements()){
				String keys = (String)ids.nextElement();
				ArrayList<ArrayList<String>> actions = acts.get(keys);
				
				//TODO
				for(int c=0; c<actions.size(); c++){
					ArrayList<String> dragAct = actions.get(c);
					
					//filter and drag
					if(dragAct.get(0).equals("##")&&dragAct.get(1).equals("Y")){
						DragFT.GDFT(dragAct.get(2), dragAct.get(3));
					}
					//drag
					else if(!dragAct.get(0).equals("##")&&dragAct.get(1).equals("Y")){
						FilterDrag.filterDrag(dragAct.get(2), dragAct.get(3), dragAct.get(0));
					}
				}
				
			    Utils.cout("===> Crawling the " + RName + "-"+ keys + " data from report table <===");    		    
			    Thread.sleep(200);
			    
			    //Wait until all data has been loaded.
			    boolean cancel_button = CheckElement.find("id", "progressPaneCancel");
			    Utils.cout("Loading data...");
			    int timeCounter = 1;
			    while (cancel_button){
			    	cancel_button = CheckElement.find("id", "progressPaneCancel");
			    	Thread.sleep(1000);
			    	timeCounter ++;
			    	if(timeCounter%30 == 0){
				    	Utils.cout("	["+timeCounter + " secs" + "]  still loading...");
			    	}
			    }
			    Utils.cout("Loading data totally takes " + timeCounter + " secs.");
			    
			    //Crawler and record data from report.
		    	try{
		    		Document doc = ParseHtml.Doc(driver);
		    		CvsFileWriter.ParseTable(doc, Prop.CDATA + keys + ".cvs");
		    	}catch(IndexOutOfBoundsException ie){
		    		if(CheckElement.find("class", "reportMsgIcon")){
		    			Utils.error("Error exception");
		    		}
		    	}
//			    Document doc = ParseHtml.Doc(driver);
//	    		CvsFileWriter.ParseTable(doc, Prop.CDATA + keys + ".cvs");
		    	
		    	//Take a screenshot
		    	TakeScreenShot.captureScreen(driver, Prop.CDATA);
				for(int c=0; c<actions.size(); c++){
					driver.findElement(By.id("cmdUndo")).click();
				}
			}			
			//go back
			driver.navigate().back();
			driver.switchTo().defaultContent();
		}
	}
}
