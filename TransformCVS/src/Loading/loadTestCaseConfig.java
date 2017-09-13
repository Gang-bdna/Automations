package Loading;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;

import global.Utils;

public class loadTestCaseConfig {
	private static String line = "";
	private static HashMap<String, String> OD_File_Pair = new HashMap<>(); //Original_File vs New_File_Name
	private static HashMap<String, Integer>  TC_Pair = new HashMap<>();
		
	public static void LoadConfig(String ResultFolder){		
		Utils.info("Loading the Result File lists that needs to be transfermed...");
		Utils.info("Config file found: " + ResultFolder);
		try{
			BufferedReader br = new BufferedReader(new FileReader(ResultFolder));
			while((line = br.readLine()) != null){
				if(line.contains("@")){
						String [] conf = line.split("	");
						OD_File_Pair.put(conf[1], conf[2]);
						TC_Pair.put(conf[2], Integer.parseInt(conf[3]));
				}
			}
			br.close();
		}catch (Exception io){
			Utils.error("Exception happened during loading transfermation config file!");
			io.printStackTrace();
			Utils.cout();
		}
		//Utils.printArrList(Features);
		Utils.info("Loading transfermation config file done.");
		Utils.cout();
	}
	
	public static HashMap<String, String> getODPair (){
		return OD_File_Pair;
	}
	
	public static HashMap<String, Integer> getTCPair (){
		return TC_Pair;
	}
}
