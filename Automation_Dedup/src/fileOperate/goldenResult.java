package fileOperate;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import global.Utils;


public class goldenResult {	
	private static HashMap<String, ArrayList<ArrayList<String>>> goldenResults = new HashMap<>();	
	private static HashSet<String> goldenHostId = new HashSet<>();
	private static String line = "";
	private static String hostId;
	private static BufferedReader br;
	//private static ArrayList<String> row = new ArrayList<>();
	
	public static void prepCompare(ArrayList<String> index, String file) throws SQLException{	
		Utils.info("Start loading and grouping Golden results from " + file);
		try{
			br = new BufferedReader(new FileReader(file));
			while((line = br.readLine()) != null){
				ArrayList<String> row = new ArrayList<>();
				ArrayList<ArrayList<String>> rows = new ArrayList<>();
				String [] elements = line.split("	");
				hostId = elements[Integer.parseInt(index.get(0))];
				
				//Loading golden result according index
				/*
				for(String idx : index){
					if(elements[Integer.parseInt(idx)].equals("NULL")){
						row.add("null");
					}
					else{
						row.add(elements[Integer.parseInt(idx)]);
					}
				}
				*/
				
				for(String idx : index){
					row.add(elements[Integer.parseInt(idx)]);
				}
				//Record HostID
				goldenHostId.add(hostId);
				
				//Group golden by Host ID
				if(goldenResults.containsKey(hostId)){
					goldenResults.get(hostId).add(row);
				} 
				else{
					rows.add(row);
					goldenResults.put(hostId, rows);
				}
				//br.close();
			}
		}catch (IOException io){
			Utils.error("Exception happened during loading Golden Results from " + file);
		}
		Utils.info("Loading and grouping done.");
		Utils.cout();
		try {
			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Utils.error("Exception happens during loading Golden Results.");
			Utils.cout();
		}
	}
	
	//Return Golden Result
	public static HashMap<String, ArrayList<ArrayList<String>>> getGoldenResults(){
		return goldenResults;
	}
	
	//Return Golden HostID
	public static HashSet<String> getGoldenHostID(){
		return goldenHostId;
	}
}
