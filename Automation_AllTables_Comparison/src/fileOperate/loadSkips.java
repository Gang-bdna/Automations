package fileOperate;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashSet;

import global.Utils;

public class loadSkips {
	private static String line = "";
	private static HashSet<String> Features = new HashSet<>();
	
	public static HashSet<String> LoadKeyFeatures(String features){		
		Utils.info("Start loading features that are used as the keypoint during comparison.");
		Utils.info("Features Location: " + features);
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
			Utils.error("Exception happened during loading features!");
			Utils.cout('\n');
		}
		//Utils.printArrList(Features);
		Utils.info("Loading features done.");
		Utils.cout('\n');
		return Features;
	}
}
