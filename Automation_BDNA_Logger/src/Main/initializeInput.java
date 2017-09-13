package Main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;

import global.Utils;
import global.getNumRow;
import oraConnection.oraConn;
import sqlConnection.sqlConn;

public class initializeInput {
	private static ResultSet rs;	
	@SuppressWarnings("unused")
	private static HashMap<String, String> colmuns = new HashMap<>();
	@SuppressWarnings("unused")
	private static HashSet<String> HostId = new HashSet<>();
    private static HashMap<String, String> userdefine = new HashMap<>();
	private static FileWriter docWrite;
	private static BufferedWriter bw;
    private static String dbtype;
	private static String username;
    private static String password;
    private static String dbserver;
    public static String logfile;
    private static String Output;
    private static String result;
    private static String querystatementfile;
    private static String configfile = "Input/LoggerCheck.cfg";
    
	public static void initDed(HashMap<String, String> conf){		
		try{
			String line="";
			BufferedReader br = new BufferedReader(new FileReader(configfile));
			while((line = br.readLine()) != null){
				if(line.contains("@")){
						String [] ele = line.split("	");
						userdefine.put(ele[1], ele[2]);
				}
			}
			br.close();
		}catch (Exception io){
		}
		
		if(conf.containsKey("Location")){
			
			Output = conf.get("Location");
			File file = new File(Output);
			if(!file.exists()){
				file.mkdirs();
			}
			
			logfile = Output + "/BDNA_Logger_Check.log";
			Utils.conf("Log file from Console is captuered.");
			//initialize result
			result = Output + "/BDNA_Logger_Check.csv";
		}else{
			
			Output = userdefine.get("FILESLOCATION");
			File file = new File(Output);
			if(!file.exists()){
				file.mkdirs();
			}
			
			logfile = Output + "/BDNA_Logger_Check.log";
			Utils.conf("Default Log file is applied.");
			//initialize result
			result = Output + "/BDNA_Logger_Check.csv";
		}
		
		//Loading UserName 
		if(conf.containsKey("DBType")){
			dbtype = conf.get("DBType");
			Utils.conf("DB Server type from Console is captuered.");
		}else{
			dbtype = userdefine.get("DBTYPE");
			Utils.conf("Default DB Server type from config file is applied.");
		}
		
		//Loading UserName 
		if(conf.containsKey("UserName")){
			username = conf.get("UserName");
			Utils.conf("UserName from Console is captuered.");
		}else{
			username = userdefine.get("USERNAME");
			Utils.conf("Default UserName from config file is applied.");
		}
		
		//Loading Password
		if(conf.containsKey("Password")){
			password = conf.get("Password");
			Utils.conf("PassWord from Console is captuered.");
		}else{
			password = userdefine.get("PASSWORD");
			Utils.conf("Default Password from Config file is applied.");
		}
				
		//Loading Password
		if(conf.containsKey("DBServer")){
			dbserver = conf.get("DBServer");
			Utils.conf("DB Server info from Console is captuered.");
		}else{
			dbserver = userdefine.get("DBSERVER");
			Utils.conf("Default DB Server info from Config file applied.");
		}
		
		//Loading QueryStatement
		if(conf.containsKey("QueryStament")){
			querystatementfile = conf.get("QueryStament");
			Utils.conf("Query Stament file from Console is captuered.");
		}else{
			querystatementfile = userdefine.get("QUERYSTATEMENT");
			Utils.conf("Default Query Stament file is applied.");
		}
    }
    
    public static void check() throws SQLException, IOException{
    	//Record Results
    	int nor_record = 0;
    	int syn_record = 0;
		docWrite = new FileWriter(result);
		bw = new BufferedWriter(docWrite);
		bw.append("Inventory ID").append(",").append("Messages").append(",").append("Step").append(",").append("Results");
		bw.newLine();
		bw.flush();
    	
    	//Connect DB
    	if(dbtype.toLowerCase().contains("ora")){
    		@SuppressWarnings("unused")
			oraConn sc = new oraConn(querystatementfile);
    		oraConn.sqlConnection(dbserver, username, password);
        	//Loading DB tables into .cvs file
        	rs = oraConn.getRS();
//        	int row = getNumRow.getNumberRows(rs);
//        	if(row == 0){
//        		bw.append("BDNA_A_LOGGER").append(",").append(row + "").append(",").append("Failed");
//        		bw.flush();
//        		Utils.info("Regression failed, no data found in BDNA_A_Logger");
//        	}
//        	else{
//        		bw.append("BDNA_A_LOGGER").append(",").append(row + "").append(",").append("Passed");
//        		bw.flush();
//        		Utils.info("Regression passed, total " + row + " rows found in BDNA_A_Logger");
//        	}
//        	bw.close();
        	try{
			    	while(rs.next()){
			        	String msg = rs.getString("MESSAGE");
			        	String invID = rs.getString("INVENTORY_ID");
			        	String taskID = rs.getString("TASK_ID");
			        	
			        	msg = msg.replaceAll(",", " ").trim().toString();
			        	if(msg.contains("ERROR")){
			        		if(!invID.isEmpty() || !taskID.isEmpty()){
			            		bw.append(invID).append(",").append(msg + "").append(",").append("Normalization Step").append(",").append("Failed");
			            		bw.newLine();
			            		bw.flush();
			            		Utils.error("Error is detected, and it was probablly occurring at Normalization step, mark test cases failed");
			            		nor_record ++;
			        		}else {
			            		bw.append(invID).append(",").append(msg + "").append(",").append("Catalog Sync Step").append(",").append("Failed");
			            		bw.newLine();
			            		bw.flush();
			            		Utils.error("Error is detected, and it was probablly occurring at Catalog Sync step, mark test cases failed");
			            		syn_record++;
			        		}
			        	}
			    	}
			    	
				}catch(NullPointerException np){	
			}
    	}
    	else {
        	@SuppressWarnings("unused")
        	sqlConn sc = new sqlConn(querystatementfile);
        	sqlConn.sqlConnection(dbserver, username, password);
        	//Loading DB tables into .cvs file
        	rs = sqlConn.getRS();	
        	
        	try{
//            	while(rs.next()){
//                	String msg = rs.getString("MESSAGE");
//                	String invID = rs.getString("INVENTORY_ID");
//                	msg = msg.replaceAll(",", " ").trim().toString();
//                	if(msg.contains("ERROR")){
//                		bw.append(invID).append(",").append(msg + "").append(",").append("Failed");
//                		bw.newLine();
//                		bw.flush();
//                		Utils.error("Error detected in BDNA_A_LOGGER table, mark test cases failed");
//                		record ++;
//                	}else{
//                		bw.append(invID).append(",").append("INFO Pass").append(",").append("Passed");
//                		bw.newLine();
//                		bw.flush();
//                	}
//            	}
            	while(rs.next()){
                	String msg = rs.getString("MESSAGE");
                	String invID = rs.getString("INVENTORY_ID");
                	String taskID = rs.getString("TASK_ID");
                	
                	msg = msg.replaceAll(",", " ").trim().toString();
                	if(msg.contains("ERROR")){
                		if(!invID.isEmpty() || !taskID.isEmpty()){
                    		bw.append(invID).append(",").append(msg + "").append(",").append("Normalization Step").append(",").append("Failed");
                    		bw.newLine();
                    		bw.flush();
                    		Utils.error("Error is detected, and it was probablly occurring at Normalization step, mark test cases failed");
                    		nor_record ++;
                		}else {
                    		bw.append(invID).append(",").append(msg + "").append(",").append("Catalog Sync Step").append(",").append("Failed");
                    		bw.newLine();
                    		bw.flush();
                    		Utils.error("Error is detected, and it was probablly occurring at Catalog Sync step, mark test cases failed");
                    		syn_record++;
                		}
                	}
            	}
            	
        	}catch(NullPointerException np){	
        	}
    	}
    	
    	if(nor_record == 0){
			bw.append("N/A").append(",").append("No errors detected during Normalization step").append(",").append("Normalization Step").append(",").append("Passed");
    		bw.newLine();
    		bw.flush();
		}
		if(syn_record==0){
			bw.append("N/A").append(",").append("No errors detected during Catalog Sync step").append(",").append("Catalog sync Step").append(",").append("Passed");
    		bw.newLine();
    		bw.flush();
		}
    	
		bw.close();
		
		Utils.cout();
		int record = nor_record + syn_record;
		if(record != 0){
			Utils.warning("Total " + record + " errors are detected, mark test cases as failed!");
			Utils.info("Logger table scann is done!");
		}else{
			Utils.info("No errors are detected, mark test cases as passed!");
			Utils.info("Logger table scann is done!");
		}
		
    	if(dbtype.toLowerCase().equals("oracle")){
    		oraConn.closeAll();
    	}else{
    		sqlConn.closeAll();
    	}
    	Utils.closeOutput();
    	}
    }
