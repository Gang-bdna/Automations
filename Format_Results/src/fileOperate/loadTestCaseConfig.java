package fileOperate;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;

import global.Utils;

public class loadTestCaseConfig {
	private static String line = "";
	private static HashMap<String, Integer> File_Col_Pair = new HashMap<>();
	private static HashMap<String, String> File_TC_Pair = new HashMap<>();
		
	public static void LoadConfig(String ResultFolder){		
		Utils.info("Loading basic config settings..");
		Utils.info("Config file found: " + ResultFolder);
		try{
			BufferedReader br = new BufferedReader(new FileReader(ResultFolder));
			while((line = br.readLine()) != null){
				if(line.contains("@")){
						String [] conf = line.split("	");
//						Utils.mark(conf[3]);
						File_Col_Pair.put(conf[2], Integer.parseInt(conf[3]));
						File_TC_Pair.put(conf[1], conf[2]);
				}
			}
			br.close();
		}catch (Exception io){
			Utils.error("Exception happened during loading config file!");
			io.printStackTrace();
			Utils.cout();
		}
		//Utils.printArrList(Features);
		Utils.info("Loading config settings done.");
		Utils.cout();
	}
	
	public static HashMap<String, Integer> getFCPair (){
		return File_Col_Pair;
	}
	
	public static HashMap<String, String> getFTCPair (){
		return File_TC_Pair;
	}
}
