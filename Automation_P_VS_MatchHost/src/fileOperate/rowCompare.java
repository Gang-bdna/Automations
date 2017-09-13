package fileOperate;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import global.Utils;

public class rowCompare {

	private static FileWriter docWrite;
	private static BufferedWriter bw;
	private static Set<String> oraName;
	private static Set<String> sqlName;
	private static HashSet<String> scanned = new HashSet<>();

	
	public static void compare(HashMap<String, String> ora, HashMap<String, String> sql, String filename){
		try {
			LocalDate localDate = LocalDate.now();
			String datestamp = DateTimeFormatter.ofPattern("yyyMMdd").format(localDate);
			//String file = "output/" + filename;
			docWrite = new FileWriter(filename);
			bw = new BufferedWriter(docWrite); 
			//File output = new file(Prop.OUTPUT);
			// Writer bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8"));
			bw.append("Task_ID").append(",");
			bw.append("HostID_In_ Match_").append(",").append("Rows #").append(",");
			bw.append("HostID_IN_P").append(",").append("Rows #").append(",");
			bw.append("DIFFERENCE").append(",").append("Results");
			bw.newLine();

			//Start to compare rows
			Utils.info("Start to compare result...");
		
			oraName = ora.keySet();
			sqlName = sql.keySet();
			
			String sql_preBDNA = "BDNA" + datestamp + "00";
			String ora_preBDNA = "BDNA" + datestamp + "000";
			
			String sql_preDisc = "DISC_" + datestamp + "00";
			String ora_preDisc = "DISC_" + datestamp + "000";
			
			int record =0;
			HashSet<String> DIFF = new HashSet<>();
			for(String name : sqlName){
				++record;
				if(!name.isEmpty()){
					String newname = name;
					if(name.contains(sql_preBDNA)){
						newname = name.replaceAll(sql_preBDNA, ora_preBDNA).toString();
					}
					
					if(name.contains(sql_preDisc)){
						newname = name.replaceAll(sql_preDisc, ora_preDisc);
					}
					
					if(oraName.contains(newname) || ora.containsKey(newname)){
						int diff = (Integer.valueOf(ora.get(newname)) - Integer.valueOf(sql.get(name)));
						scanned.add(newname);
						if(diff != 0){
							bw.append(loadArg.taskID).append(",").append(newname).append(",").append(ora.get(newname)).append(",");
							bw.append(name).append(",").append(sql.get(name)).append(",");
							bw.append(""+diff).append(",").append("Failed");;					
							bw.newLine();
							bw.flush();
							DIFF.add(newname);
						}else{
							bw.append(loadArg.taskID).append(",").append(newname).append(",").append(ora.get(newname)).append(",");
							bw.append(name).append(",").append(sql.get(name)).append(",");
							bw.append(""+diff).append(",").append("Passed");;					
							bw.newLine();
							bw.flush();
						}
					}
					else{
					//	Utils.mark(name+"==="+newname);
						bw.append(loadArg.taskID).append(",").append("Not Found").append(",").append("N/A").append(",");
						bw.append(name).append(",").append(sql.get(name)).append(",");
						bw.append("Not Applicable").append(",").append("Failed");
						bw.newLine();
						bw.flush();
						DIFF.add(name);
					}
				}
				
				if(record%100==0){
					Utils.info("Comparing the " + record + "th Tables...");
				}
			}
			
			int totalCount = oraName.size();
			oraName.removeAll(scanned);
			if(!oraName.isEmpty()){
				for(String left : oraName){
					++record;
					//Utils.mark(left);
					bw.append(loadArg.taskID).append(",").append(left).append(",").append(ora.get(left)).append(",");
					bw.append("Not Found").append(",").append("N/A").append(",");
					bw.append("Not Applicable").append(",").append("Failed");;
					bw.newLine();
					bw.flush();
					if(record%100==0){
						Utils.info("Comparing the " + record + "th Hosts...");
					}
					DIFF.add(left);
				}
			}			
			bw.flush();
			Utils.info("Total " + record + " Hosts are verified.");
			
			if(!DIFF.isEmpty()){
				Utils.warning("Total " + DIFF.size() + " Host(s) are reported as mismatching!");
				Utils.info("Comparision between _P table and _Match tables is done.");
				Utils.error("Testing marked as FAILED!");
				bw.close();
			}else{
//				bw.newLine();
//				bw.append("All are matched!");
//				bw.newLine();
//				bw.append("Testing is PASSED!");
				if(totalCount==0){
					Utils.cout();
					Utils.info("Comparision between _P table and _Match tables is done.");
					Utils.error("Loading tables failed. Pls double check connection info!");
					Utils.error("Testing marked as FAILED!");
					bw.close();
				}else{
					Utils.cout();
					Utils.info("Comapring is done.");
					Utils.info("Product counts against Host ID between _P table and _Match tables are matched.");
					Utils.info("Testing is PASSED!");
					bw.close();
				}
			}
		}catch(Exception e){
			Utils.error("Exception druing comparasion: <Line 91> in rowCompare.java");
			//e.printStackTrace();
		}
	}
}
