package fileOperate;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import global.Utils;

public class  writeMap {
	private static FileWriter docWrite;
	private static BufferedWriter bw;
	
	public static void write(HashMap<String, String> rm, String RowsMap) throws IOException{
		Set<String> tableSet = new HashSet<>();
		tableSet = rm.keySet();
		bw = null;
		docWrite = null;
		try {
			//String file = "output/" + RowsMap;
			docWrite = new FileWriter(RowsMap);
			bw = new BufferedWriter(docWrite); 
		} catch (IOException e) {
			Utils.error("Failed to write RowsMap into "+ RowsMap + ": <Line 26> in writeMap.java");
		}
		
		for (String table : tableSet){
			try {
				bw.append(table).append("	").append(rm.get(table)).append('\n');
				bw.flush();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				Utils.error("Exception happens while write the data of " + table + "into " + RowsMap +  ": <Line 26> in writeMap.java");
				bw.close();
			}
		}
		bw.close();
	}
}
