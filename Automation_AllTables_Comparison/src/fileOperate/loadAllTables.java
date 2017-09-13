package fileOperate;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;

import global.Utils;

public class loadAllTables {
	public static HashSet<String> emptyTable = new HashSet<>();
	public static HashSet<String> remainTables = new HashSet<>();
	//private static HashMap<String, String> TableRows = new HashMap<>();
	
	public static HashMap<String, String> load(Statement stmt, ResultSet rs, HashSet<String> nSet, HashSet<String> skip, String file) 
			throws SQLException{
		HashMap<String, String> TableRows = new HashMap<>();
		//Remove all "Skip" item from Table's Name set
		if(!skip.isEmpty()){
			nSet.removeAll(skip);
		}
		
		Utils.info(nSet.size() + " tables waiting for check and loading...");
		//Loading inventory ID 
		HashMap<String, String> inv_tables = new HashMap<>();
		HashMap<String, Integer> tables_withID = new HashMap<>();
		HashSet<String> tables_withoutID = new HashSet<>();
		HashSet<String> temp_id = new HashSet<>();
		HashSet<String> inv = new HashSet<>();
		
		inv = loadSingleTable.Inventory(stmt, rs, "MATCH_INVENTORY");
		
		if(inv.isEmpty()){
			Utils.info("No Inventory found, skip Inventory table!");
			//give any fake data
			for(String name : nSet){
				if(name.contains("9999")){
					tables_withID.put(name, 4);
					temp_id.add(name);
				}else{
					tables_withoutID.add(name);
				}
			}
		}
		else{
			inv.add("9999");
			for(String name : nSet){
				for(String id : inv){
					if(name.contains(id)){
						tables_withID.put(name, id.length());
						temp_id.add(name);
					}else{
						tables_withoutID.add(name);
					}
				}
			}
			
		}
		//Loading all of the tables not in Skip set
		tables_withoutID.removeAll(temp_id);
		for (String name : tables_withoutID){
			try{
				//Utils.info("String loading the " + tableNumber + "th table");
				//loadQueryResult.loadContentDB(sqlConn.stmt, sqlConn.rs, name);
				loadSingleTable.rows(stmt, rs, name);
				String rows = loadSingleTable.getRowsSet();
				TableRows.put(name, rows);
				Utils.info("Total " + rows + " are discovered against " + name);
				Utils.cout('\n');
				
				//writeMap.write(TableRows, file);
			}catch (Exception e){
				Utils.error("Error happens during loading the data from the tables whose name doesn't contain Inventory ID: <Line 66> in loadAllTables.java");
				break;
			}
		}
		
		//Combine result by Inv ID
		for (String name : tables_withID.keySet()){
			try{
				loadSingleTable.rows(stmt, rs, name);
				String rows = loadSingleTable.getRowsSet();
				Utils.info("Total " + rows + " are discovered against " + name);
				Utils.cout('\n');
				
				String newname = name.substring(0, name.length()-tables_withID.get(name)-1);
				//Utils.mark(newname);
				if(inv_tables.containsKey(newname)){
					inv_tables.put(newname, String.valueOf(Integer.valueOf(inv_tables.get(newname))+Integer.valueOf(rows)));
					//Utils.mark(name + "====" + inv_tables.get(newname));
				}else{
					inv_tables.put(newname, rows);
				}
			}catch(Exception e){
				Utils.error("Error happens during loading the data from the tables whose name contains Inventory ID: <Line 88> in loadAllTables.java");
			}
		}
		

		//write combine result into TableRows and files
//		if(!inv_tables.isEmpty()){
//			for(String name : inv_tables.keySet()){
//				
//			}
//		}
		TableRows.putAll(inv_tables);
		try {
			writeMap.write(TableRows, file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Utils.error("Error happens while writing <Table : Rows> into " +file+ ": <Line 104> in loadAllTables.java");
			e.printStackTrace();
		}
		return TableRows;
	}

	
	public static HashSet<String> getEmptyTable(){
		return emptyTable;
	}
}
