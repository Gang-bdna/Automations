package fileOperate;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import global.Utils;
import oraConnection.oraConn;
import sqlConnection.sqlConn;

public class loadArg {
	private static ResultSet Ora_rs;
	private static ResultSet Sql_rs;
	private static Set<String> OraRowSet;
	private static Set<String> SqlRowSet;
	private static HashMap<String, String> OraMap = new HashMap<>();
	private static HashMap<String, String> SqlMap = new HashMap<>();
    private static HashMap<String, String> userdefine = new HashMap<>();
	private static String username;
    private static String password;
    private static String sqlVM;
    private static String oraVM;
    public static String temVM; 
    private static String skipfile;
    public static String logfile;
    public static String Output;
    private static String configfile = "UserConfig.cfg";
    
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
			Utils.error("Failed to read config file: <Line 45> in writeMap.java");
		}
    	
		//Loading config for the file used to save final diff results
		if(conf.containsKey("Log")){
			logfile = conf.get("Log");
			Utils.conf("Log file from Console is captuered.");
		}else{
			logfile = userdefine.get("LOGFILE");
			Utils.conf("Default Log file is applied.");
		}
		
		//Init prop
		//Prop.Prop(logfile);
		
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
				
		//Loading sql Server
		if(conf.containsKey("sqlVM")){
			sqlVM = conf.get("sqlVM");
			Utils.conf("Sql Server info from Console is captuered.");
		}else{
			sqlVM = userdefine.get("SQLVM");
			Utils.conf("Default Sql Server info from Config file applied.");
		}
		
		//Loading ora Server
		if(conf.containsKey("oraVM")){
			oraVM = conf.get("oraVM");
			Utils.conf("Oracle Server info from Console is captuered.");
		}else{
			oraVM = userdefine.get("ORAVM");
			Utils.conf("Default Oracle Server info from Config file applied.");
		}
		
		//Loading Features
		if(conf.containsKey("SkipTables")){
			skipfile = conf.get("SkipTables");
			Utils.conf("Features file from Console is captuered.");
		}else{
			skipfile = userdefine.get("SKIPTABLES");
			Utils.conf("Default Features file is applied.");
		}
		
		//Loading Output folder path
		if(conf.containsKey("Location")){
			Output = conf.get("Location");
			Utils.conf("Output location from Console is captuered.");
		}else{
			Output = userdefine.get("FILESLOCATION");
			Utils.conf("Default Features file is applied.");
		}
    }
    
    public static void dedupping() throws SQLException{
    	
    	HashSet<String> ora_set = new HashSet<>();
    	HashSet<String> sql_set = new HashSet<>();
    	HashSet<String> skip = new HashSet<>();
        String SqlRowMap = Output + "/SqlRowMap.txt";
        String OraRowMap = Output + "/OraRowMap.txt";
        String differencefile = Output + "/" + username + "_Diff.cvs";
    	//Connect ORACLE DB
    	oraConn.sqlConnection(oraVM, username, password);
    	temVM = oraVM;
    	//Loading DB tables into .cvs file
    	Ora_rs = oraConn.getRS();    	
    	ora_set = FetchTabels.tableName(Ora_rs);
    	//ora_set = FetchTabels.getTables();
    	
    	//Loading the tables not required
    	skip = loadSkips.LoadKeyFeatures(skipfile);
    	
    	//Loading all tables not in skip set
    	//OraMap = loadAllTabmes.load(oraConn.stmt, oraConn.rs, ora_set, skip, "OraRowMap.txt");

    	OraRowSet = loadAllTables.load(oraConn.stmt, oraConn.rs, ora_set, skip, OraRowMap).keySet();
    	//Utils.cout(loadAllTabmes.getTableRows().toString());
    	oraConn.closeAll();
    	System.gc();
    	
    	//Connect Mssql DB
    	Utils.mark("Connect to SQL...");
    	sqlConn.sqlConnection(sqlVM, username, password);
    	temVM = sqlVM;
    	
    	//Loading DB tables into .cvs file
    	Sql_rs = sqlConn.getRS();    	
    	sql_set = FetchTabels.tableName(Sql_rs);
    	
    	//Loading the tables not required
    	//skip = loadSkips.LoadKeyFeatures(featuresfile);
    	
    	//Loading all tables not in skip set
    	SqlRowSet = loadAllTables.load(sqlConn.stmt, sqlConn.rs, sql_set, skip, SqlRowMap).keySet();
    	sqlConn.closeAll();
    	
    	//Start to compare two DBs
    	SqlMap = loadRowMap.read(SqlRowMap);
    	OraMap = loadRowMap.read(OraRowMap);
    	rowCompare.compare(OraMap, SqlMap, differencefile);

    	Utils.closeOutput();
    	}
    }
