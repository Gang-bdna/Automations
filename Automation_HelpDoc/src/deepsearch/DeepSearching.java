package deepsearch;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import fileOperate.Crawler;
import global.Utils;
import main.IndexKeyWord;

public class DeepSearching {
	private static ArrayList<String> keys = new ArrayList<>();
	private static HashMap<String, HashMap<String, Integer>> SearchResult = new HashMap<>();
	private static HashMap<String, ArrayList<HashMap<String, Integer>>> summary = new HashMap<>();
	private static HashMap<String, Integer> singleChpt = new HashMap<>();
	private static HashMap<String, Integer> Showtimes = new HashMap<>();
	private static String path = Crawler.content;
	
	public static void search(String cat){
		
		FileWriter docWrite;
		BufferedWriter bw = null;
		try {
			docWrite = new FileWriter(IndexKeyWord.result, true);
			bw = new BufferedWriter(docWrite); 
			bw.newLine();
		} catch (IOException e) {
			Utils.error("Initialize "+ IndexKeyWord.result +" file failed");
		}
		
		loadConfig.LoadSearchKey(IndexKeyWord.configfile);
		
		if(cat.toLowerCase().equals("dp")){
			keys = loadConfig.getDPSearchKeys();
		}else if(cat.toLowerCase().equals("uc")){
			keys = loadConfig.getUCSearchKeys();
		}
		
		File Chps = new File(path);
		File [] files = Chps.listFiles();
		for(File f: files){
			String name = f.getName();
			String filepath = path + "/" + name;
			loadContent.search(filepath, keys);
			singleChpt = loadContent.getSearchResult();
			SearchResult.put(name, singleChpt);
			//loadContent.init();
		}
		
		//Re-Wrap HasMap
		for(String chpt : SearchResult.keySet()){			
			if(!SearchResult.get(chpt).isEmpty()){
				for(String key : SearchResult.get(chpt).keySet()){
					HashMap<String, Integer> temp = new HashMap<>();
					temp.put(chpt, SearchResult.get(chpt).get(key));
					
					if(summary.containsKey(key)){
						summary.get(key).add(temp);
					}
					else if(!summary.containsKey(key)){
						ArrayList<HashMap<String, Integer>> al = new ArrayList<>();
						al.add(temp);
						summary.put(key, al);
					}
				}
			}
		}
		
		//Calculate Showing times
		for(String key : summary.keySet()){
			ArrayList<HashMap<String, Integer>> featList = summary.get(key);
			int record = 0;
			
			for(int lenght=0; lenght<featList.size(); lenght++){
				HashMap<String, Integer> temp = featList.get(lenght);
				for(String t : temp.keySet()){
					record = record + temp.get(t);
				}
			}
			Showtimes.put(key, record);
		}
		
		//Print results
		for(String key : Showtimes.keySet()){
			Utils.cout('\n');
			Utils.info("Searching Key Words: [" + key + "] appears " + Showtimes.get(key) + " times in total.");
			if(!(key.startsWith("5")||key.startsWith("6")||key.startsWith("7")||key.startsWith("8")||key.startsWith("9"))){
				try {
					bw.append(key).append(",").append("Passed");
					bw.flush();
					bw.newLine();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}else{
				try {
					bw.append(key).append(",").append("Failed");
					bw.flush();
					bw.newLine();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			//Print details
			for(int lenght=0; lenght<summary.get(key).size(); lenght++){
					HashMap<String, Integer> temp = summary.get(key).get(lenght);
					for(String t : temp.keySet()){
						Utils.info(temp.get(t) + "	time(s) in Chapeter : " + t);
					}
			}
		}
		Utils.cout('\n');
		
		//Print not found key words
		keys.removeAll(Showtimes.keySet());
		if(!keys.isEmpty()){
			for(String key : keys){
//				Utils.cout('\n');
				if(!(key.startsWith("5")||key.startsWith("6")||key.startsWith("7")||key.startsWith("8")||key.startsWith("9"))){
					Utils.error("Searching Key Words: [" + key + "]" + " is NOT found!");
					try {
						bw.append(key).append(",").append("Failed");
						bw.flush();
						bw.newLine();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}else{
					Utils.info("Version # : " + key + " checking passed!");
					try {
						bw.append(key).append(",").append("Passed");
						bw.flush();
						bw.newLine();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		try {
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}	
		Utils.cout('\n');
	}
}
