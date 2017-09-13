package DBConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import global.Utils;

public class sqlConn {
	private static ResultSet rs;
	private static String Input;
	private static Connection conn;
	private static Statement stmt;
	
	public sqlConn(String Input){
		sqlConn.Input = Input;
	}
	
	public static void sqlConnection (String VM, String UserName, String PassWord, String query){
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			Utils.error("Fail to load Sqlserver Driver, Pls double check if DB typy is matching the DB on the VM.");
			//e.printStackTrace();
		}
		
		String jdb = "jdbc:sqlserver://";
		String dbname = ";databaseName=bdna";
		String DBServer = jdb + VM + dbname;
		
        Utils.info("Trying to connect DB ...");
        
    	try {
			conn = DriverManager.getConnection(DBServer, UserName, PassWord);
			//Statement stmt = conn.createStatement();
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			Utils.error("Fail to connect SQL!");
			Utils.error("Pls make sure the SQL connect info is correct!");
			//e1.printStackTrace();
		}
		
        try {
			String querystatement = query;
			rs = stmt.executeQuery(querystatement);
			Utils.info("Database now is connected.");
			Utils.cout();
		} catch (SQLException|NullPointerException e) {
			Utils.error("Fail to run SQL query statement!");
			//e.printStackTrace();
		}
	}
	
	public static ResultSet getRS(){
		return rs;
	}
	
	public static void initResultSet() throws SQLException{
		//rs.first();
		try{
			rs.beforeFirst();
		}catch(NullPointerException e){
			
		}
	}
	
	public static void closeStmt() throws SQLException{
		stmt.close();
	}
	
	public static void closeRS() throws SQLException{
		rs.close();
	}
	
	public static void closeAll() throws SQLException{
		try{
			stmt.close();
			rs.close();
			conn.close();
		}catch(NullPointerException e){
			
		}
	}
	
}
