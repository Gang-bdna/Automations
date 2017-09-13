package Main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import fileOperate.actualResult;
import fileOperate.compareActGolden;
import fileOperate.goldenResult;
import fileOperate.loadColumns;
import fileOperate.loadFeatures;
import fileOperate.loadQueryResult;
import fileOperate.writeDiff;
import global.Utils;
import oraConnection.oraConn;
import sqlConnection.sqlConn;
import sqlOperation.LoadPTableName;
import sqlOperation.LoadQryStmtOral;
import sqlOperation.loadPTableData;

public class ded {
	private static ResultSet rs;
	private static ArrayList<String> features;
	private static ArrayList<String> index;
	private static HashMap<String, ArrayList<ArrayList<String>>> aResults = new HashMap<>();	
	private static HashMap<String, ArrayList<ArrayList<String>>> gResults = new HashMap<>();	
	@SuppressWarnings("unused")
	private static HashMap<String, String> colmuns = new HashMap<>();
	@SuppressWarnings("unused")
	private static HashSet<String> HostId = new HashSet<>();
	private static HashSet<String> aHostId = new HashSet<>();
	private static HashSet<String> gHostId = new HashSet<>();
	private static HashSet<String> diffID = new HashSet<>();
    //private static HashMap<String, String> conf = new HashMap<>();
    private static HashMap<String, String> userdefine = new HashMap<>();
    private static String dbtype;
	private static String username;
    private static String password;
    private static String dbserver;
    private static String querystatementfile;
    private static String featuresfile;
    private static String actualresultfile;
    private static String columnsfile;
    private static String goldenfile;
    private static String sqlTable;
    public static String oraTable;
    public static String logfile;
    public static String location;
    public static String result;
    private static String PT;
    private static String configfile = "DeduppingCnf.cfg";
    
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
			Utils.error("Cannot load config file!");
		}
    	
		//Loading Output path and initialize log file and result file
		if(conf.containsKey("Location")){
			
			location = conf.get("Location");
			File file = new File(location);
			if(!file.exists()){
				file.mkdirs();
			}
			
			logfile = location + "/HardCode_Dedup.log";
			Utils.conf("Log file from Console is captuered.");
			//initialize result
			result = location + "/HardCode_Dedup.csv";
		}else{
			location = userdefine.get("LOCATION");
			File file = new File(location);
			if(!file.exists()){
				file.mkdirs();
			}
			
			logfile = location + "/HardCode_Dedup.log";
			Utils.conf("Default Log file is applied.");
			//initialize result
			result = location + "/HardCode_Dedup.csv";
		}
				
		//Init prop
		//Prop.Prop(logfile);
		
		//Loading DB Type 
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
		
		//Loading Table name in SQL env
		if(conf.containsKey("SQLTable")){
			sqlTable = conf.get("SQLTable");
			Utils.conf("Table name in SQL env from Console is captuered.");
		}else{
			LocalDate localDate = LocalDate.now();
			String datestamp = DateTimeFormatter.ofPattern("yyyMMdd").format(localDate);
			sqlTable = "BDNA" + datestamp +"001";
			Utils.conf("Default Table name for Mssql env is generated automatically.");
		}
		
		//Loading Table name in SQL env
		if(conf.containsKey("ORATable")){
			oraTable = conf.get("ORATable");
			Utils.conf("Table name in SQL env from Console is captuered.");
		}else{
			LocalDate localDate = LocalDate.now();
			String datestamp = DateTimeFormatter.ofPattern("yyyMMdd").format(localDate);
			oraTable = "BDNA" + datestamp +"0001";
			Utils.conf("Default Table name for Oracle env is generated automatically.");
		}
		
		//Generate new table name using postfix
		if(conf.containsKey("PostFix") && dbtype.toLowerCase().contains("sql")){
			LocalDate localDate = LocalDate.now();
			String datestamp = DateTimeFormatter.ofPattern("yyyMMdd").format(localDate);
			sqlTable = "BDNA" + datestamp +conf.get("PostFix");
		}else if(conf.containsKey("PostFix") && dbtype.toLowerCase().contains("ora")){
			LocalDate localDate = LocalDate.now();
			String datestamp = DateTimeFormatter.ofPattern("yyyMMdd").format(localDate);
			oraTable = "BDNA" + datestamp + "0"+ conf.get("PostFix");
		}
		
		//Loading Features
		if(conf.containsKey("Feature")){
			featuresfile = conf.get("Feature");
			Utils.conf("Features file from Console is captuered.");
		}else{
			featuresfile = userdefine.get("FEATURES");
			Utils.conf("Default Features file is applied.");
		}
		
		//Loading config for Golden Result
		if(conf.containsKey("Golden")){
			goldenfile = conf.get("Golden");
			Utils.conf("Golden file from Console is captuered.");
		}else{
			goldenfile = userdefine.get("GOLDENRESULT");
			Utils.conf("Default Golden file is applied.");
		}
		
		//Loading config for Golden Result
		if(conf.containsKey("Column")){
			columnsfile = conf.get("Column");
			Utils.conf("Column Name file from Console is captuered.");
		}else{
			columnsfile = userdefine.get("COLMUNS");
			Utils.conf("Default Column Name file is applied.");
		}
		
		//Loading _P table name by Inventory name
		//Loading QueryStatement
		if(conf.containsKey("DataSource")){
			PT = conf.get("DataSource");
			Utils.conf("DataSource Name from Console is captuered.");
		}else{
			PT = userdefine.get("DATASOURCE");
			Utils.conf("Default DataSource Name is applied.");
		}		
    }
    
    public static void dedupping() {
    	
    	//Connect DB
    	actualresultfile = location + "/" + dbtype + ".csv";
    	Statement stmt;
    	
    	//Load data from _P table
    	if(dbtype.toLowerCase().contains("ora")){
    		//Connect to BDNA
    		oraConn.sqlConnection(dbserver, username, password);
        	stmt = oraConn.getStmt();
        	
        	//load P table name based on inventory name
        	LoadPTableName lpt = new LoadPTableName(oraTable);
        	oraTable = LoadPTableName.GetTP(stmt, PT);
        	
        	//load query statement and query result
    		String querystatement = LoadQryStmtOral.LoadSQLQuery(querystatementfile, oraTable);
    		rs = loadPTableData.getDataFromPT(stmt, querystatement);
        	loadQueryResult.loadContentDB(rs, actualresultfile);
                	
        	//Loading actual result against features file and Group actual result by hostID
        	features = loadFeatures.LoadKeyFeatures(featuresfile);
    	}
    	else {
    		//Connect to BDNA
        	sqlConn.sqlConnection(dbserver, username, password);
        	stmt = sqlConn.getStmt();
        	
        	//load P table name based on inventory name
        	LoadPTableName lpt = new LoadPTableName(sqlTable);
        	sqlTable = LoadPTableName.GetTP(stmt, PT);
        	
        	//load query statement and query result
    		String querystatement = LoadQryStmtOral.LoadSQLQuery(querystatementfile, sqlTable);
    		rs = loadPTableData.getDataFromPT(stmt, querystatement);
        	loadQueryResult.loadContentDB(rs, actualresultfile);
          	
        	//Loading actual result against features file and Group actual result by hostID
        	features = loadFeatures.LoadKeyFeatures(featuresfile);
    	}
    	
    	//Roll back cursor to the beginning
    	try {
			rs.beforeFirst();
		} catch (SQLException | NullPointerException e1) {
			Utils.error(e1.toString());
		}
    	
    	// comparing results
    	actualResult.prepCompare(features, rs);
    	aResults = actualResult.getActualResults();
    	aHostId = actualResult.getActHostID();

    	//Loading colmuns's tile
    	colmuns = loadColumns.LoadKeyColmuns(columnsfile);
    	index = loadColumns.SpeficIndexForExtracter(features);

    	
    	//Loading Golden result against features file and Group golden result by hostID
    	try {
			goldenResult.prepCompare(index, goldenfile);
		} catch (SQLException e) {
			//e.printStackTrace();
		}
    	gResults = goldenResult.getGoldenResults();
    	gHostId = goldenResult.getGoldenHostID();

    	//Start comparing between Golden and Actual results
    	if(aHostId.size() > gHostId.size()){
    		compareActGolden.compareAG(aResults, gResults, aHostId);
    		
        	//Write result for Test Cases
        	writeDiff.writeRes(aHostId);
    	} 
    	else {
    		compareActGolden.compareAG(gResults, aResults, gHostId);
    		
        	//Write result for Test Cases
        	writeDiff.writeRes(gHostId);
    	}
    	    	
    	//Write the diff into .cvs file
    	diffID = compareActGolden.getDiffId();
    	if(diffID.isEmpty()){
    		writeDiff.pass(location, dbtype);
    	}else{
    		writeDiff.RecordDiff(diffID, index, location, dbtype);
    	}
        	
    	if(dbtype.toLowerCase().equals("oracle")){
    		try {
				oraConn.closeAll();
			} catch (SQLException e) {
				e.printStackTrace();
			}
    	}else{
    		try {
				sqlConn.closeAll();
			} catch (SQLException e) {
				e.printStackTrace();
			}
    	}
    	Utils.closeOutput();
    	try {
			rs.close();
		} catch (SQLException|NullPointerException e) {
		}
    	}
    }
