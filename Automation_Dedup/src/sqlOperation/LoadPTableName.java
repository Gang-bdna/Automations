package sqlOperation;

//import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import global.Utils;

public class LoadPTableName {
	private static String PTable = "";
	private static ResultSet rs;
	public LoadPTableName(String PTable){
		this.PTable = PTable;
	}
	
	public static String GetTP(Statement stmt, String DataSource){
		String query = "select NAME_PREFIX P from BMS_TASK where TASK_NAME ='" + DataSource + "'";
		boolean isEmpty = true;
        try {
			rs = stmt.executeQuery(query);
			Utils.info("Table 'BMS_TASK' now is connected.");
			
	        while(rs.next()){
	        	PTable = rs.getString("P");
	        	isEmpty = false;
	        }
	        rs.close();
		} catch (SQLException|NullPointerException e) {
			Utils.error("Fail to load data from BMS_TASK table against the given data source.");
			isEmpty = true;
			Utils.cout();
//			e.printStackTrace();
		}

        if(isEmpty){
//        	PTable = PTable + "_P";
			Utils.error("Fail to load data from BMS_TASK table against the given data source: " + DataSource);
			Utils.warning("Script will take the default _P table name instead: " + PTable + "_P (current datasteamp + user predifined/default postfix)");
			Utils.cout();
        }else{
            Utils.info("Prefix of _P table against the given Data Source '" +DataSource+ "' is caught : " + PTable);
    		Utils.cout();
        }
		return PTable+"_P";
	}
}
