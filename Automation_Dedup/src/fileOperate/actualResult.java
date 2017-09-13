package fileOperate;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import global.Utils;

public class actualResult {	
	private static HashMap<String, ArrayList<ArrayList<String>>> actualResults = new HashMap<>();	
	private static HashSet<String> ActHostId = new HashSet<>();
	
	public static void prepCompare(ArrayList<String> features, ResultSet rs){	
		Utils.info("Start loading and groupping actual result from Query Results");
		try{
			while(rs.next()){
				ArrayList<ArrayList<String>> rows = new ArrayList<>();
				ArrayList<String> row = new ArrayList<>();
				String hid = rs.getString("HOSTID");
			for (String feat : features){
				ActHostId.add(hid);
				String cell = rs.getString(feat);

				//Record hostId
				row.add(cell);
			}		
				// Group result by HostID
				if (actualResults.containsKey(hid)){
					actualResults.get(hid).add(row);
				}
				else {
					rows.add(row);
					actualResults.put(hid, rows);
				}
				//Utils.cout(rs.getString(feat));
				//Utils.mark(row.toString());
			}
			//Utils.mark(rows.size());			
		}catch (Exception ne){
			Utils.error("Exception happened during groupping actual results!");
			Utils.cout();
		}
		Utils.info("Loading and groupping done.");
		Utils.cout();
	}
	
	// Return Result Group
	public static HashMap<String, ArrayList<ArrayList<String>>> getActualResults(){
		return actualResults;
	}
	
	//Return Golden HostID
	public static HashSet<String> getActHostID(){
		return ActHostId;
	}
}
