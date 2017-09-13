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
	private static HashSet<String> InvId = new HashSet<>();
	
	public static void loaddata(Statement stmt, ResultSet rs, String table){
		FileWriter docWrite;
		BufferedWriter bw;
		int records = 0;
		try{
			//rs = sqlConn.getRS();
			String newQuery = "select * from " +  table;
			Utils.info("Start loading data from " + table);
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
			Utils.cout('\n');
		}catch (Exception e){
			Utils.error("Failed to write the data of " +table+ " into files: <Line 81> in loadSingleTables.java");
		}
	}
	
	public static void rows(Statement stmt, ResultSet rs, String table){
		try{
			//rs = sqlConn.getRS();
			String newQuery = "select count(*) Count from " +  table;
			Utils.info("Start loading data from " + table);
			rs = stmt.executeQuery(newQuery);
			
			ResultSetMetaData rsmd = rs.getMetaData();
			columnsNumber = rsmd.getColumnCount();
			if(rs.next()){
				RowsNumber = rs.getString("Count");
				//RowsNumber = rs.getRow();
			}
			else {
				RowsNumber = "0";
			}
			rs.close();
			//Utils.cout('\n');
		}catch (Exception e){
			//e.printStackTrace();
			Utils.error("Failed to load the Rows of " +table);
		}
	}
	
	public static HashSet<String> Inventory(Statement stmt, ResultSet rs, String table){
		try{
			//Inventory ID set
			//HashSet<String> InvId = new HashSet<>();
			
			String newQuery = "select INVENTORY_ID ID from " +  table;
			Utils.info("Start loading data from " + table);
			rs = stmt.executeQuery(newQuery);
			
			while(rs.next()){
				String ID = rs.getString("ID");
				Utils.mark(ID);
				InvId.add(ID);
			}
			
			rs.close();
		}catch (Exception e){
			Utils.error("Failed to load Inventory ID from " +table);
			//e.toString();
		}
		return InvId;
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
