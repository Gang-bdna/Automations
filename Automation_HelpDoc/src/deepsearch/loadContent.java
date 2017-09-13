package deepsearch;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;

import global.Utils;

public class loadContent {
	private static String line = "";
	private static HashMap<String, Integer> rs = new HashMap<String, Integer>();
	
	public static void search(String file, ArrayList<String> keylist){		
		Utils.info("Start Searching the given Keywords from chapeter: " + file);
		HashMap<String, Integer> count = new HashMap<String, Integer>();
		try{
			//Utils.mark(file);
			BufferedReader br = new BufferedReader(new FileReader(file));
			while((line = br.readLine()) != null){
				if(!line.contains("<NormalizeVersion>5.4.0</NormalizeVersion>")){
					for(String key : keylist){
						//Utils.mark(key);
						if(line.toLowerCase().contains(key.toLowerCase())){
							if(count.containsKey(key)){
								count.put(key, count.get(key)+1);
								//Utils.mark(file + "===" + key + "===" + count.get(key));
							}else{
								count.put(key, 1);
								//Utils.mark(file + "===" + key + "===" + count.get(key));
							}
						}
					}
				}
			}
			br.close();
		}catch (Exception io){
			Utils.error("Exception happened during loading Searching config!");
			Utils.cout('\n');
			io.printStackTrace();
		}
		//Utils.printArrList(Features);
		//Utils.cout('\n');
		rs = count;
	}
	
	public static HashMap<String, Integer> getSearchResult(){
		return rs;
	}
}
