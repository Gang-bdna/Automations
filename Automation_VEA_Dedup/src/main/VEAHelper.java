package main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import global.Utils;
import processor.RunSql;

public class VEAHelper {
    private static String configfile = "VEA.cfg";
    private static HashMap<String, String> userdefine = new HashMap<>();
	private static ArrayList<String> eleList = new ArrayList<>();
    public static String logfile;
    public static String username;
    public static String password;
    public static String url;
    public static String loc;
    public static String sqlsmt;
    public static String dbtype;
    public static String result;
    public static String sid;
    public static String output;
    
	public static void initConf(HashMap<String, String> conf){
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
		//Loading config for the file used to save final diff results
		if(conf.containsKey("Location")){
			
			loc = conf.get("Location");
			File file = new File(loc);
			if(!file.exists()){
				file.mkdirs();
			}
			
			logfile = loc + "/VEA_Check.log";
			Utils.conf("Log file from Console is captuered.");
			//initialize result
			result = loc + "/VEA_Check.csv";
		}else{
			
			loc = userdefine.get("LOCATION");
			File file = new File(loc);
			if(!file.exists()){
				file.mkdirs();
			}
			
			logfile = loc + "/VEA_Check.log";
			Utils.conf("Default Log file is applied.");
			//initialize result
			result = loc + "/VEA_Check.csv";
		}
		//Loading UserName 
		if(conf.containsKey("UserName")){
			username = conf.get("UserName");
			Utils.conf("UserName for DB is captuered.");
		}else{
			username = userdefine.get("USERNAME");
			Utils.conf("Default UserName for DB is applied.");
		}
		
		//Loading Password
		if(conf.containsKey("Password")){
			password = conf.get("Password");
			Utils.conf("PassWord for DB is captuered.");
		}else{
			password = userdefine.get("PASSWORD");
			Utils.conf("Default Password for DB is applied.");
		}
				
		//Loading VM info
		if(conf.containsKey("VM")){
			url = conf.get("VM");
			Utils.conf("VM host name or IP is captuered.");
		}else{
			url = userdefine.get("VM");
			Utils.conf("Default VM host name or IP is applied.");
		}
		
		//Loading DBType
		if(conf.containsKey("DBType")){
			dbtype = conf.get("DBType");
			Utils.conf("DB type is captuered.");
		}else{
			dbtype = userdefine.get("DBTYPE");
			Utils.conf("Default DP Type is applied.");
		}
		
		//Loading SID for Oracle client
		if(conf.containsKey("SID")){
			sid = conf.get("SID");
			Utils.conf("SID for ORACLE is captuered.");
		}else{
			sid = userdefine.get("SID");
			Utils.conf("Default SID for ORACLE is applied.");
		}
		
		//Loading query statement
		if(conf.containsKey("SQLStatement")){
			sqlsmt = conf.get("SQLStatement");
			Utils.conf("Query statement file is captuered.");
		}else{
			sqlsmt = userdefine.get("SQLSTATEMENT");
			Utils.conf("Default Query statement file is applied.");
		}
    }
	
	public static void dedupCkc() throws InterruptedException, IOException, SQLException{
		//Initialize Writer
		Utils.cout();		

		output = VEAHelper.loc + "\\sqlOutPut.txt";
		if(dbtype.toLowerCase().contains("sql")){
			Utils.info("Loading dedup checking script for MSSQL env...");
			String command = "sqlcmd -S " + url +" -U " + username + " -P " + password + " -i " + sqlsmt + " -o " + output;
			//String que = "use bdna SELECT HOSTID, HIDDEN, PROD_RID, * FROM BDNA20170518004_P ORDER BY 1,3";
			Utils.mark(command);
			RunSql.exeMssql(command, output);
		}
		else if(dbtype.toLowerCase().contains("ora")){
			Utils.info("Loading dedup checking script for ORACLE env...");
			String command = "cmd.exe /C sqlplus " + username +"/"+ password + "@" + url + ":1521" +"/" + sid + " @" + sqlsmt + " > " + output;
			//String que = "use bdna SELECT HOSTID, HIDDEN, PROD_RID, * FROM BDNA20170518004_P ORDER BY 1,3";
			RunSql.exeOracle(command, output);
		}else{
			Utils.error("The DB type is not recognized!");
		}
	}
}

