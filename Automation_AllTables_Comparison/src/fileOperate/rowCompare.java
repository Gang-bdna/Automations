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
			bw.append("ORA_TABLE_NAMES").append("	").append("Rows #").append("	");
			bw.append("SQL_TABLE_NAMES").append("	").append("Rows #").append("	");
			bw.append("DIFFERENCE");
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
						bw.append(newname).append("	").append(ora.get(newname)).append("	");
						bw.append(name).append("	").append(sql.get(name)).append("	");
						bw.append(""+(Integer.valueOf(ora.get(newname)) - Integer.valueOf(sql.get(name))));					
						bw.newLine();
						bw.flush();
						scanned.add(newname);
					}
					else{
						//Utils.mark(name+"==="+newname);
						bw.append("Not Found").append("	").append("N/A").append("	");
						bw.append(name).append("	").append(sql.get(name)).append("	");
						bw.append("Not Applicable");
						bw.newLine();
						bw.flush();
					}
				}
				
				if(record%100==0){
					Utils.info("Comparing the " + record + "th Tables...");
				}
			}
			
			oraName.removeAll(scanned);
			if(!oraName.isEmpty()){
				for(String left : oraName){
					++record;
					//Utils.mark(left);
					bw.append(left).append("	").append(ora.get(left)).append("	");
					bw.append("Not Found").append("	").append("N/A").append("	");
					bw.append("Not Applicable");
					bw.newLine();
					bw.flush();
					
					if(record%100==0){
						Utils.info("Comparing the " + record + "th Tables...");
					}
				}
			}			
			//bw.flush();
			bw.close();
			Utils.info("Comparing the " + record + "th Tables...");
			Utils.info("Comapring done!");
		}catch(Exception e){
			e.printStackTrace();
			Utils.error("Exception druing comparasion: rowCompare.java");
		}
	}
}
