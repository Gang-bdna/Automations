package fileOperate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;

import global.Utils;

public class FetchTabels{
	//private static HashSet<String> tnames = new HashSet<>();
	
	public static HashSet<String> tableName(ResultSet rs){
		int records = 0;
		HashSet<String> tnames = new HashSet<>();
		Utils.info("Start loading table name from " + loadArg.temVM);
		tnames.clear();
		try {
			while (rs.next()){
				++ records;
				String names = rs.getNString("table_name").toUpperCase();
				tnames.add(names);
				
				//Utils.cout(names);
				if (records%50 == 0){
					Utils.info(records + " TABLES found");
				}
			}
		} catch (SQLException e) {
			Utils.error("Failed to load Tables' name: <Line 29> in FetchTables.java");
		}
		Utils.info("Total " + records + " TABELS are found.");
		return tnames;		
	}
}

