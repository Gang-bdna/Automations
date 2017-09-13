package sqlOperation;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import global.Utils;

public class loadPTableData {
	private static ResultSet rs;
	public static ResultSet getDataFromPT(Statement stmt, String querystatement){
        try {
			rs = stmt.executeQuery(querystatement);
			Utils.info("Database now is connected.");
			Utils.cout();
//			stmt.close();
		} catch (SQLException|NullPointerException e) {
			Utils.error("Exception happened during check with _P table!");
			Utils.error(e.toString());
			Utils.cout();
		}
		return rs;
	}
}
