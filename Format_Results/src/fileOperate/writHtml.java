package fileOperate;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import Main.FR_Initialize;
import global.Utils;

public class writHtml {
	private static FileWriter docWrite;
	private static BufferedWriter bw;
	
	public static void htmlFormat(String template, HashMap<String, String> TCP, HashMap<String, ArrayList<Integer>> RS,
			HashMap<String, String> sys, String buildNum, String jobName){
		String space = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
				+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
				+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
		String single = "&nbsp;";
		String newLine = "<br>";
		String passLabel = "<td style=background-color:green align=center>";
		String failLabel = "<td style=background-color:red align=center>";
		String missLabel = "<td style=background-color:yellow align=center>";
		String tr = "<tr>";
		String td = "<td>";
		String tdc = "<td align=center>";
		String btr = "</tr>";
		String btd = "</td>";
		String tab = "<tab>";
//		String btab = "</tab>";
		String btab = "";
//		String leading = "<leading>";
//		String bleading = "</leading>";
		String leading = space;
		String bleading = "";

		Set<String> TC = TCP.keySet();
		
		try {
				docWrite = new FileWriter(FR_Initialize.loc + "/emailbody.html");
				bw = new BufferedWriter(docWrite); 
				
			} catch (Exception e) {
				Utils.error("Failed initialize email body file");
			}		
		String line = "";
		try{
			BufferedReader br = new BufferedReader(new FileReader(template));
			while((line = br.readLine()) != null){
				bw.append(line);
				bw.newLine();
				bw.flush();
				
				Set<String> header = sys.keySet();
				if(line.trim().equals("<!--Insert Sys Info-->")){
					bw.append("	1.Time Logged:").append("\n").append(newLine).append("\n");	
					
					tab = generateSpace.Space("Script Start Time", 42);
					if(!header.contains("Script Start Time")) sys.put("Script Start Time", "Not Availible");
					bw.append(leading).append("Script Start Time :").append(bleading).append(tab).append(sys.get("Script Start Time")).append(btab).append("\n").append(newLine).append("\n");
					
					tab = generateSpace.Space("Total Time Spent", 40);
					if(!header.contains("Total Time")) sys.put("Total Time", "Not Availible");
					bw.append(leading).append("Total Time Spent :").append(bleading).append(tab).append(sys.get("Total Time")).append(btab).append("\n").append(newLine).append("\n").append("\n").append(newLine).append("\n");
					
					bw.append("\n").append(newLine).append("\n").append("	2.Normalize Server Infomation:").append("\n").append(newLine).append("\n");	
					
					tab = generateSpace.Space("Machine Name", 39);
					if(!header.contains("Machine Name")) sys.put("Machine Name", "Not Availible");
					bw.append(leading).append("Machine Name :	").append(bleading).append(tab).append(sys.get("Machine Name")).append(btab).append("\n").append(newLine).append("\n");
					
					tab = generateSpace.Space("Database Type", 42);
					if(!header.contains("Database Type")) sys.put("Database Type", "Not Availible");
					bw.append(leading).append("Database Type :").append(bleading).append(tab).append(sys.get("Database Type")).append(btab).append("\n").append(newLine).append("\n");
					
					tab = generateSpace.Space("Build Versione", 44);
					if(!header.contains("Build Version")) sys.put("Build Version", "Not Availible");
					bw.append(leading).append("Build Version :").append(bleading).append(tab).append(sys.get("Build Version")).append(btab).append("\n").append(newLine).append("\n");
					
					tab = generateSpace.Space("Technopedia Version", 37);
					if(!header.contains("Technopedia Version")) sys.put("Technopedia Version", "Not Availible");
					bw.append(leading).append("Technopedia Version :").append(bleading).append(tab).append(sys.get("Technopedia Version")).append(btab).append("\n").append(newLine).append("\n");
					
					bw.append("\n").append(newLine).append("\n").append("	3.System/Hardware Information:").append("\n").append(newLine).append("\n");
					
					tab = generateSpace.Space("System Model", 42);
					if(!header.contains("System Model")) sys.put("System Model", "Not Availible");
					bw.append(leading).append("System Model :").append(bleading).append(tab).append(sys.get("System Model")).append(btab).append("\n").append(newLine).append("\n");
					
					tab = generateSpace.Space("Number of Logical CPUs", 34);
					if(!header.contains("Number of Logical CPUs")) sys.put("Number of Logical CPUs", "Not Availible");
					bw.append(leading).append("Number of Logical CPUs :").append(bleading).append(tab).append(sys.get("Number of Logical CPUs")).append(btab).append("\n").append(newLine).append("\n");
					
					tab = generateSpace.Space("Number of Physical CPUs", 34);
					if(!header.contains("Number of Physical CPUs")) sys.put("Number of Physical CPUs", "Not Availible");
					bw.append(leading).append("Number of Physical CPUs :").append(bleading).append(tab).append(sys.get("Number of Physical CPUs")).append(btab).append("\n").append(newLine).append("\n");
					
					tab = generateSpace.Space("Physical Memory", 39);
					if(!header.contains("Physical Memory")) sys.put("Physical Memory", "Not Availible");
					bw.append(leading).append("Physical Memory :").append(bleading).append(tab).append(sys.get("Physical Memory")).append(btab).append("\n").append(newLine).append("\n");	
					
					tab = generateSpace.Space("Operation System", 40);
					if(!header.contains("Operation System")) sys.put("Operation System", "Not Availible");
					bw.append(leading).append("Operation System :").append(bleading).append(tab).append(sys.get("Operation System")).append(btab).append("\n").append(newLine).append("\n");
					
					tab = generateSpace.Space("Processor Type", 42);
					if(!header.contains("Processor Type")) sys.put("Processor Type", "Not Availible");
					bw.append(leading).append("Processor Type :").append(bleading).append(tab).append(sys.get("Processor Type")).append(btab).append("\n").append(newLine).append("\n");
				}
				  
				if(line.trim().equals("<!--Insert Details-->")){
					for(String tc : TC){
						bw.append(tr).append("\n");
						bw.append(td).append(tc).append(btd).append("\n");
						ArrayList<Integer> data = new ArrayList<>();
						
						if(!RS.containsKey(TCP.get(tc))){
							bw.append(tdc).append("0/0").append(btd).append("\n");
							bw.append(missLabel).append("File Not Found").append(btd).append("\n");
						}else{
							data = RS.get(TCP.get(tc));
//							if(data.get(0)!=data.get(1))
							if(data.get(2)!= 0){
								bw.append(tdc).append(data.get(2)+"/"+data.get(0)).append(btd).append("\n");
								bw.append(failLabel).append("Failed").append(btd).append("\n");
								Utils.error("Test Case: " + tc + " failed!");
							}else{
								if(data.get(0)==0){
									bw.append(tdc).append(data.get(2)+"/"+data.get(0)).append(btd).append("\n");
									bw.append(failLabel).append("Data Not Found").append(btd).append("\n");
									Utils.error("Test Case: " + tc + " failed!");
								}else{
									bw.append(tdc).append(data.get(2)+"/"+data.get(0)).append(btd).append("\n");
									bw.append(passLabel).append("Passed").append(btd).append("\n");
									Utils.info("Test Case: " + tc + " passed!");
								}
							}
						}
						bw.append(btr).append("\n");
						bw.flush();
					}
				}
				
				if(line.contains("{Jenkins-Job_Number}")){
					String nas3 = jobName + "_" + buildNum;
					line = line.replace("{Jenkins-Job_Number}", nas3);
					bw.append(line);
					bw.flush();
				}
				
			}
			bw.close();
			br.close();
		}catch (Exception io){
			Utils.error("Exception happened during loading/writing format template!");
			io.printStackTrace();
			Utils.cout();
		}
	}
}
