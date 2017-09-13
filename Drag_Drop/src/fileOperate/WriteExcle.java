package fileOperate;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;

import global.Utils;

public class WriteExcle {
    // Now, let's write some data into our XLSX file

//	private static String terminal = "@$%$%@@$%$%@";
	public static void peakData(String in1, Hashtable<String, String> steps) throws IOException{
		FileWriter docWrite = null;
//		String input = "inputs/monitor_oracle_tablespaces.log";
//		String input = "inputs/mor.log";
		File parse = new File("outputs/mors.cvs");
		docWrite = new FileWriter(parse);
		ArrayList<String> keylist = new ArrayList<String>();
		BufferedReader br = new BufferedReader(new FileReader(in1));
		String line = "";
		
		//fetch keys
	    Enumeration e = steps.keys();
	    while (e.hasMoreElements()) {
	      String key = (String) e.nextElement();
	      keylist.add(key);
	    }
		
		//print header
		if((line = br.readLine()).contains("Current Time")){
			String [] actions = line.split(",");
			for(int k=0; k<actions.length; k++){
				docWrite.append(actions[k]).append("	");
			}
			docWrite.append("Steps");
			docWrite.append('\n');
		}
		
		//record data
		int lineCount = 0;
		while((line = br.readLine()) != null){
			// Tracking the processing...
			++ lineCount;
			if((lineCount%100)==0 || lineCount ==1){
				Utils.info("Processing the "+ lineCount +"th lines...");
			}
			
			// Processing data, and write them into file.
			if(!(line.contains("Current Time"))){
				String [] actions = line.split(",");
				for(int k=0; k<actions.length; k++){
					docWrite.append(actions[k]).append("	");
				}
				for(String k : keylist){
					if(line.contains(k)){
						docWrite.append(steps.get(k));
					}
				}
				docWrite.append('\n');
			}
		}
		docWrite.close();
		Utils.info("Processing the "+ lineCount +"th lines...");
		Utils.info("=== Program Done! ===");
	}
	
	public static Hashtable<String, String> FilterLog(String input) throws IOException{
		FileWriter docWrite = null;
		File parse = new File("outputs/timestemp.txt");
		docWrite = new FileWriter(parse);
		BufferedReader br = new BufferedReader(new FileReader(input));
		String line = "";
		
		String impt = "Importing";
		String mpg = "Mapping ";
//		String pub = "Publish inventory";
		String pub = "Publish";
//		String anl = "Analyze process";
		String anl = "Analyze";
		
		Hashtable<String, String> timestemp = new Hashtable<>();
		
		//record data into Hash table
		while((line = br.readLine()) != null){
				if((line.contains(impt))){
					String [] actions = line.split(",");
					docWrite.append(impt).append("	");
					docWrite.append((actions[0].substring(0, 16))).append(" ");
					docWrite.append('\n');
					timestemp.put((actions[0].substring(0, 16)), impt);
//					impt = terminal;
				}
				if((line.contains(mpg))){
					String [] actions = line.split(",");
					docWrite.append(mpg).append("	");
					docWrite.append((actions[0].substring(0, 16))).append(" ");
					docWrite.append('\n');
					timestemp.put((actions[0].substring(0, 16)), mpg);
//					mpg = terminal;
				}
				if((line.contains(pub))){
					String [] actions = line.split(",");
					docWrite.append(pub).append("	");
					docWrite.append((actions[0].substring(0, 16))).append(" ");
					docWrite.append('\n');
					timestemp.put((actions[0].substring(0, 16)), pub);
//					pub = terminal;
				}
				if((line.contains(anl))){
					String [] actions = line.split(",");
					docWrite.append(anl).append("	");
					docWrite.append((actions[0].substring(0, 16))).append(" ");
					docWrite.append('\n');
					timestemp.put((actions[0].substring(0, 16)), anl);
//					anl = terminal;
				}
		}
		docWrite.close();
		return timestemp;
	}
}
