package fileOperate;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
	private static HashMap<String, String> PTMap = new HashMap<>();
	private static HashMap<String, String> MHTMap = new HashMap<>();
    private static HashMap<String, String> userdefine = new HashMap<>();
	private static String username;
    private static String password;
    public static String logfile;
    public static String Output;
    private static String dbtype;
    private static String dbserver;
    private static String sqlTable;
    private static String oraTable;
    private static String matchhost;
    public static String result;
    public static String taskID="";
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
			Utils.error("Failed to read config file.");
		}
		
		//Loading Output folder path and setup the path for log file and result file
		if(conf.containsKey("Location")){
			
			Output = conf.get("Location");
			File file = new File(Output);
			if(!file.exists()){
				file.mkdirs();
			}
			
			logfile = Output + "/P_vs_MatchHost.log";
			Utils.conf("Log file from Console is captuered.");
			//initialize result
			result = Output + "/P_vs_MatchHost.csv";
		}else{
			Output = userdefine.get("LOCATION");
			File file = new File(Output);
			if(!file.exists()){
				file.mkdirs();
			}
			
			logfile = Output + "/P_vs_MatchHost.log";
			Utils.conf("Default Log file is applied.");
			//initialize result
			result = Output + "/P_vs_MatchHost.csv";
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
		
		//Loading Table name in SQL env
		if(conf.containsKey("SQLTable")){
			sqlTable = conf.get("SQLTable");
			Utils.conf("Table name in SQL env from Console is captuered.");
		}else{
			LocalDate localDate = LocalDate.now();
			String datestamp = DateTimeFormatter.ofPattern("yyyMMdd").format(localDate);
			sqlTable = "BDNA" + datestamp +"001_P";
			Utils.conf("Default Table name for Mssql env is generated automatically.");
		}
		
		//Loading Table name in SQL env
		if(conf.containsKey("ORATable")){
			oraTable = conf.get("ORATable");
			Utils.conf("Table name in ORA env from Console is captuered.");
		}else{
			LocalDate localDate = LocalDate.now();
			String datestamp = DateTimeFormatter.ofPattern("yyyMMdd").format(localDate);
			oraTable = "BDNA" + datestamp +"0001_P";
			Utils.conf("Default Table name for Oracle env is generated automatically.");
//			oraTable = "BDNA201707130001_P";
		}
		
		//Loading Table name in SQL env
		if(conf.containsKey("CommanTable")){
			matchhost = conf.get("CommanTable");
			Utils.conf("Table name for MatchHost from Console is captuered.");
		}else{
			matchhost = userdefine.get("COMMANTABLE");
			Utils.conf("Default MatchHost name info from Config file applied.");
		}
		
    }
    
    public static void comp() throws SQLException{
    	
    	//Connect ORACLE DB
        String differencefile = result;
        if(dbtype.toLowerCase().contains("ora")){
        	//Connect Mssql DB
            String PRowMap = Output + "/" + oraTable;
            String MHRowMap = Output + "/" + matchhost;
//            String differencefile = Output + "/" + dbtype + "_Tables_Diff.csv";
            //String differencefile = Output + "/" + oraTable + "-VS-" + matchhost + "_Diff.cvs";
            
        	Utils.cout();
        	Utils.info("Connecting to ENV: " + dbserver + "-" + dbtype + " ...");
        	oraConn.sqlConnection(dbserver, username, password);
        	Ora_rs = oraConn.getRS();    	
        	Utils.info("Connecting to " + oraTable);
        	taskID = loadSingleTable.TaskID(oraConn.stmt, oraConn.rs, oraTable, "TASK_ID");
        	SqlRowSet = loadAllTables.load(oraConn.stmt, oraConn.rs, oraTable, "prod_rid").keySet();
        	oraConn.closeAll();
        	
        	Utils.cout();
        	Utils.info("Connecting to " + matchhost);
        	oraConn.sqlConnection(dbserver, "bdna_publish", password);
        	Ora_rs = oraConn.getRS();    
        	SqlRowSet = loadAllTables.load(oraConn.stmt, oraConn.rs, matchhost, "CAT_SW_PRODUCT_ID").keySet();
        	oraConn.closeAll();
        	
        	//Start to compare two DBs
        	PTMap = loadRowMap.read(PRowMap);
        	MHTMap = loadRowMap.read(MHRowMap);
        	rowCompare.compare(MHTMap, PTMap, differencefile);

        	Utils.closeOutput();
        }else{
        	//Connect Mssql DB
//        	sqlTable = "BDNA20170518002_P";
        	
            String PRowMap = Output + "\\" + sqlTable;
            String MHRowMap = Output + "\\" + matchhost;
//            String differencefile = Output + "/" + dbtype + "_Tables_Diff.csv";
//            String differencefile = Output + "/" + "P_vs_MatchHost.csv";
            
        	Utils.cout();
        	Utils.info("ENV: " + dbserver + "-" + dbtype + " ...");
        	Utils.info("Connecting to " + sqlTable);
        	sqlConn.sqlConnection(dbserver, username, password);
        	Sql_rs = sqlConn.getRS();    	
        	taskID = loadSingleTable.TaskID(sqlConn.stmt, sqlConn.rs, sqlTable, "TASK_ID");
        	SqlRowSet = loadAllTables.load(sqlConn.stmt, sqlConn.rs, sqlTable, "prod_rid").keySet();

        	sqlConn.closeAll();
        	
        	Utils.cout();
        	Utils.info("Connecting to " + matchhost);
        	sqlConn.sqlConnection(dbserver, "bdna_publish", password);
        	Sql_rs = sqlConn.getRS();    	
        	SqlRowSet = loadAllTables.load(sqlConn.stmt, sqlConn.rs, matchhost, "CAT_SW_PRODUCT_ID").keySet();
        	sqlConn.closeAll();
        	
        	//Start to compare two DBs
        	Utils.cout();
        	PTMap = loadRowMap.read(PRowMap);
        	MHTMap = loadRowMap.read(MHRowMap);
        	
        	Utils.cout();
        	rowCompare.compare(MHTMap, PTMap, differencefile);

        	Utils.closeOutput();
        	
        }
	}
}
