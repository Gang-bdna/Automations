package fileOperate;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;

import global.Utils;

public class loadRowMap {
	//private static String line = "";
	//private static HashMap<String, String> rows = new HashMap<>();
	
	public static HashMap<String, String> read(String file){
		String line = "";
		HashMap<String, String> rows = new HashMap<>();
		String input = file;
		Utils.info("Start loading RowSet from: " + file);
		try{
			BufferedReader br = new BufferedReader(new FileReader(input));
			while((line = br.readLine()) != null){
				String [] ele = line.split("	");
				rows.put(ele[0], ele[1]);
			}
			br.close();
		}catch (Exception io){
			Utils.error("Failed to load rows map from " + input);
			Utils.cout('\n');
		}
		//Utils.printArrList(Features);
		Utils.info("Loading features done.");
		Utils.cout('\n');
		return rows;
	}
}
