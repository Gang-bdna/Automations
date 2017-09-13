package deepsearch;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

import global.Utils;

public class loadConfig {
	private static String line = "";
	private static ArrayList<String> DPKeys = new ArrayList<>();
	private static ArrayList<String> UCKeys = new ArrayList<>();
	
	public static void LoadSearchKey(String config){		
		Utils.info("Loading the key words for Searching");
		Utils.info("Config file found: " + config);
		try{
			BufferedReader br = new BufferedReader(new FileReader(config));
			while((line = br.readLine()) != null){
				if(line.contains("<DPSK>")){
						String [] feat = line.split("	");
						DPKeys.add(feat[1]);
				}
				else if(line.contains("<UCSK>")){
					String [] feat = line.split("	");
					UCKeys.add(feat[1]);
				}
			}
			br.close();
		}catch (Exception io){
			Utils.error("Exception happened during loading Searching config!");
			io.printStackTrace();
			Utils.cout('\n');
		}
		//Utils.printArrList(Features);
		Utils.info("Loading Search Items are done.");
		Utils.cout('\n');
	}
	
	public static ArrayList<String> getDPSearchKeys(){
		return DPKeys;
	}
	
	public static ArrayList<String> getUCSearchKeys(){
		return UCKeys;
	}
}
