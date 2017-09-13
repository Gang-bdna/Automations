package FileOper;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Set;

import global.Utils;

public class OverWriteOF {
	private static FileWriter docWrite;
	private static BufferedWriter bw;
	private static BufferedReader br;
	
	public static void transfer(HashMap<String, String> OD, HashMap<String, Integer> TC, String result){
		Set<String> ORG_File = OD.keySet();
		String ReadFile = "";
		String NewFile = "";
		try{
			for(String org : ORG_File){
				Utils.info("Start transfering result file: " + org);
				ReadFile = result + "/" + org;
				NewFile = result + "/" + OD.get(org);
				
				//Check if result exists or not
				File file = new File(ReadFile);
				if(file.exists()){
					br = new BufferedReader(new FileReader(ReadFile));
					docWrite = new FileWriter(NewFile);
					bw = new BufferedWriter(docWrite); 
					
					String line = "";
					int skipmark = 0;
					while((line = br.readLine())!=null){
						// double check if the line string contains comma
						if(line.toString().trim().contains("\"")){
//							Utils.warning(line.toString());;
							line = line.replaceAll(", ", " ");
//							Utils.warning(line.toString());
						}
						
						// Analyze results based on key word "Passed" or "Failed"
						if(line.contains("Error type") || line.contains("Test_Case_Name")){
							if(skipmark==0){
								bw.append(line).append(",").append("Transfer Result");
								bw.flush();
								skipmark ++;
							}
						}else{
							bw.newLine();
							bw.append(line).append(",");
							bw.flush();
							String [] temp = line.split(",");
							if(temp.length>=TC.get(OD.get(org))){
								try{
									Double.parseDouble(temp[TC.get(OD.get(org))-1]);
//									Utils.info("Analyse result data : " + temp[TC.get(OD.get(org))-1] + "	==> Passed!"); 
									bw.append("Passed");
									bw.flush();
								}catch(NumberFormatException nfe){
									Utils.info("Analyse result data : " + temp[TC.get(OD.get(org))-1] + "	==> Failed!"); 
									bw.append("Failed");
									bw.flush();
								}
							}
							
						}
					}
					bw.close();
					br.close();
					Utils.cout();
				}else{
					Utils.error(file + " doesn't exist, transfermation skipped!");
					Utils.cout();
				}
			}
		}catch(IOException io){
			Utils.error("Read " + ReadFile + " failed.");
			io.printStackTrace();
		}
	}
}
