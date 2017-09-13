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
		String file = loadArg.Output + "/" + RowsMap;
		bw = null;
		docWrite = null;
		try {
			docWrite = new FileWriter(file);
			bw = new BufferedWriter(docWrite); 
		} catch (IOException e) {
			Utils.error("Failed to write RowsMap into "+ file + ": <Line 26> in writeMap.java");
		}
		
		for (String table : tableSet){
			try {
				bw.append(table).append("	").append(rm.get(table)).append('\n');
				bw.flush();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				Utils.error("Exception happens while write the data of " + table + "into " + file +  ": <Line 26> in writeMap.java");
				bw.close();
			}
		}
		bw.close();
	}
}
