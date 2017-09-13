package fileOperate;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

import global.Utils;

public class loadConfig {
	private static String line = "";
	private static ArrayList<String> Features = new ArrayList<>();
	
	public static ArrayList<String> LoadCheckList(String features){		
		Utils.info("Loading the Elements that neeed to be checked.");
		Utils.info("Checklist file found: " + features);
		try{
			BufferedReader br = new BufferedReader(new FileReader(features));
			while((line = br.readLine()) != null){
				if(line.contains("@")){
						String [] feat = line.split("	");
						Features.add(feat[1]);
				}
			}
			br.close();
		}catch (Exception io){
			Utils.error("Exception happened during loading CheckList!");
			Utils.cout('\n');
		}
		//Utils.printArrList(Features);
		Utils.info("Loading CheckList done.");
		Utils.cout('\n');
		return Features;
	}
}
