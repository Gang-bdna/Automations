package databaseOperation;

import java.sql.ResultSet;
import java.util.HashMap;

import global.Utils;
import oraConnection.oraConn;
import sqlConnection.sqlConn;

public class LoadDataBaseData {
	private static HashMap<String, String> TechCount = new HashMap<>();
	
	public static void loading(String dbtype, String queryFile, String dbserver, String username, String password){
		Utils.cout();
    	// declare local parameters 
		ResultSet rs;
		int index = 0;
		
		//Connect DB
    	if(dbtype.toLowerCase().contains("ora")){
			oraConn sc = new oraConn(queryFile);
    		sc.sqlConnection(dbserver, username, password);
        	//Loading DB tables into .cvs file
        	rs = sc.getRS();
    	}
    	else {
        	sqlConn sc = new sqlConn(queryFile);
        	sc.sqlConnection(dbserver, username, password);
        	rs = sc.getRS();        	
    	}
    	
		Utils.info("Start loading Technopedia count numbers...");
    	try{
    		while(rs.next()){
    			//Write count # into map
    			switch(index){
					case 0 : 
						Utils.info("Loading Technopedia Manufacturer count...");
						TechCount.put("Technopedia: Manufacturer", rs.getString("TCount"));
					break;
					case 1 : 
						Utils.info("Loading Technopedia Software count...");
						TechCount.put("Technopedia: Software", rs.getString("TCount"));
					break;
					case 2 : 
						Utils.info("Loading Technopedia Hardware count...");
						TechCount.put("Technopedia: Hardware", rs.getString("TCount"));
						break;
    			}
    			index ++;    					
    		}    		
        	Utils.info(TechCount);
    		Utils.info("Loading Technopedia count done.");
    		Utils.cout();
    		
        	// Closing all of living SQL interface
        	if(dbtype.toLowerCase().equals("oracle")){
    			oraConn.closeAll();

        	}else{
        		sqlConn.closeAll();
        	}
//        	Utils.closeOutput();
        	rs.close();
    	}catch(Exception e){
    		Utils.error("Failing to load data from Database");
    		e.toString();
    	}
	}
	
	//Return count set
	public static HashMap<String, String> getCount(){
		return TechCount;
	}
}
