package fileOperate;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;

import global.Utils;

public class loadSingleTable {

	private static int columnsNumber;
	private static String RowsNumber;
	private static String TaskID;
	private static HashSet<String> HostId = new HashSet<>();
	
	public static void loaddata(Statement stmt, ResultSet rs, String table){
		FileWriter docWrite;
		BufferedWriter bw;
		int records = 0;
		try{
			//rs = sqlConn.getRS();
			String newQuery = "select * from " +  table;
			Utils.info("Loading data from " + table + "...");
			rs = stmt.executeQuery(newQuery);
			
			ResultSetMetaData rsmd = rs.getMetaData();
			columnsNumber = rsmd.getColumnCount();
			if(rs.next()){
				bw = null;
				docWrite = null;
				try {
					String file = "output/" + table;
					docWrite = new FileWriter(file);
					bw = new BufferedWriter(docWrite); 
				} catch (IOException e) {
					Utils.error("Failed to write the data of " +table+ " into files: <Line 40> in loadSingleTables.java");
				}
				
				//back to the first row
				rs.previous();
				while (rs.next()){
					++ records;
					for (int i=1; i<=columnsNumber; i++){
						try{
							//bw.append(rs.getString(i).toLowerCase().toString());
							String [] Line = rs.getString(i).split("	");
							for (String s : Line){
								bw.append(s).append("	");							
							}
						}catch (NullPointerException ne){
							bw.append("null").append("	");
							continue;
						}
					}
					bw.append('\n');
					if (records%10000 == 0){
						Utils.info("Loading the " +  records + "th rows ...");
						bw.flush();
					}
				}
				rs.close();
				//stmt.close();
				bw.flush();
				bw.close();
				System.gc();
			}
			else {
				//bw.write("Empty file");
				Utils.warning(table + " is EMPTY.");
				Utils.info("No data gets written into file.");
			}
			
			Utils.info("Total " +  records + " rows get loaded.");
			//Utils.info("Full Query results has been recored.");
			Utils.cout();
		}catch (Exception e){
			Utils.error("Failed to write the data of " +table+ " into files: <Line 81> in loadSingleTables.java");
		}
	}
	
	public static void rows(Statement stmt, ResultSet rs, String table, String column, String ID){
		try{
			//rs = sqlConn.getRS();
			String newQuery = "select  count(" + column + ")" + " IDCount from " +  table + " where " + column + " in " + "(" + ID +") and " + "task_id = " + loadArg.taskID;
//			String newQuery = "select  count(" + column + ")" + " IDCount from " +  table + " where " + column + " in " + "(" + ID +")";
//			Utils.info("Start loading data from " + table);
			rs = stmt.executeQuery(newQuery);
			
			//Utils.mark(newQuery);
			ResultSetMetaData rsmd = rs.getMetaData();
			columnsNumber = rsmd.getColumnCount();
			if(rs.next()){
				RowsNumber = rs.getString("IDCount");
				//RowsNumber = rs.getRow();
			}
			else {
				RowsNumber = "0";
			}
			rs.close();
			//Utils.cout('\n');
		}catch (Exception e){
			e.printStackTrace();
			Utils.error("Failed to load the Rows of " +table);
		}
	}
	
	public static String TaskID(Statement stmt, ResultSet rs, String table, String Column){
		try{
			//Inventory ID set
			//HashSet<String> InvId = new HashSet<>();
			
			String newQuery = "select distinct " + Column + " ID from " +  table;
//			String newQuery = "select distinct " + Column + " ID from " +  table + " where task_id = 16745";
			Utils.info("Loading Task ID from " + table + "...");
			Utils.info(newQuery);
			rs = stmt.executeQuery(newQuery);
			while(rs.next()){
				TaskID = rs.getString("ID");
			}
			
			rs.close();
		}catch (Exception e){
			Utils.error("Failed to load Task ID from " +table);
			e.printStackTrace();;
		}
		return TaskID;
	}
	
	public static HashSet<String> HostID(Statement stmt, ResultSet rs, String table, String Column){
		try{
			//Inventory ID set
			//HashSet<String> InvId = new HashSet<>();
			
//			String newQuery = "select distinct " + Column + " ID from " +  table;
			String newQuery = "select distinct " + Column + " ID from " +  table + " where task_id = " + loadArg.taskID;
			Utils.info("Loading data from " + table + "...");
			Utils.info(newQuery);
			rs = stmt.executeQuery(newQuery);
			
			while(rs.next()){
				String ID = rs.getString("ID");
				//Utils.mark(ID);
				HostId.add(ID);
			}
			
			rs.close();
		}catch (Exception e){
			Utils.error("Failed to load Host ID from " +table);
			e.printStackTrace();;
		}
		return HostId;
	}
	
	
	public static boolean isEmpty(Statement stmt, ResultSet rs, String table) throws SQLException{
		String newQuery = "select * from " +  table;
		rs = stmt.executeQuery(newQuery);
		if(rs.next()){
			return true;
		}
		else {
			return false;
		}
	}
	
	public static String getRowsSet(){
		return RowsNumber;
	}
}
