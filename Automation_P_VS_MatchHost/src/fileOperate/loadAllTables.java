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
	
	public static HashMap<String, String> load(Statement stmt, ResultSet rs, String table, String column) 
			throws SQLException{
		HashMap<String, String> TableRows = new HashMap<>();
		
		//Loading inventory ID 
		HashSet<String> inv = new HashSet<>();
		
		inv = loadSingleTable.HostID(stmt, rs, table, column);
		
		Utils.info("Loading data from " + table + "...");
		//Loading all of the tables not in Skip set
		for (String hid : inv){
			try{
				loadSingleTable.rows(stmt, rs, table, column, hid);
				String rows = loadSingleTable.getRowsSet();
				TableRows.put(hid, rows);
//				Utils.info("Total " + rows + " Rows are discovered against HostID:" + hid);
//				Utils.cout('\n');
				
				//writeMap.write(TableRows, file);
			}catch (Exception e){
				Utils.error("Error happens during loading the data from the tables whose name doesn't contain Host ID: <Line 66> in loadAllTables.java");
				break;
			}
		}
		try {
			writeMap.write(TableRows, table);
		} catch (IOException e) {
			Utils.error("Error happens while writing <Table : Rows> into " +table+ ": <Line 104> in loadAllTables.java");
		}
		return TableRows;
	}

	
	public static HashSet<String> getEmptyTable(){
		return emptyTable;
	}
}
