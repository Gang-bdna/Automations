package Main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;

import databaseOperation.LoadDataBaseData;
import dragDrop.RepeatCreateCube;
import fileOperation.compareResult;
import global.Prop;
import global.Utils;
import htmlSourceOperation.parseDataFromHP;
import userConsoleOperation.clawerTCount;

public class HP_DataCheck {
	private static HashMap<String, String> DCount = new HashMap<>();		
	private static HashMap<String, String> UCount = new HashMap<>();
	private static HashMap<String, String> ACount = new HashMap<>();
	private static HashMap<String, String> TestCases = new HashMap<>();
    private static HashMap<String, String> userdefine = new HashMap<>();
    private static HashSet<String> cubes = new HashSet<>();
    
    private static String dbtype;
	private static String username;
    private static String password;
    private static String dbserver;
    private static String querystatementfile;
    private static String loginname;
    private static String loginpwd;
    public static String logfile;
    public static String location;
    public static String result;
    private static String configfile = "Input/Homepage_Analyze_Validation.cfg";
    
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
			
			logfile = location + "/Analyze_Publish_Technopedia.log";
			Utils.conf("Log file from Console is captuered.");
			//initialize result
			result = location + "/Analyze_Publish_Technopedia.csv";
		}else{
			location = userdefine.get("LOCATION");
			File file = new File(location);
			if(!file.exists()){
				file.mkdirs();
			}
			
			logfile = location + "/Analyze_Publish_Technopedia.log";
			Utils.conf("Default Log file is applied.");
			//initialize result
			result = location + "/Analyze_Publish_Technopedia.csv";
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
		
		//Loading user name for UC login
		if(conf.containsKey("UCName")){
			loginname = conf.get("UCName");
			Utils.conf("Login UserName for User Console is captuered.");
		}else{
			loginname = userdefine.get("UILOGINNAME");
			Utils.conf("Default Login UserName for User Console is applied.");
		}
		
		//Loading config for Golden Result
		if(conf.containsKey("UCPassword")){
			loginpwd = conf.get("UCPassword");
			Utils.conf("Login password for User Console is captuered.");
		}else{
			loginpwd = userdefine.get("UILOGINPWD");
			Utils.conf("Default User Console login password is applied.");
		}		
    }
    
    public static void HP_Validate() throws InterruptedException, IOException {
       	//Loading Technopedia SW/HW/MF data from database
    	LoadDataBaseData.loading(dbtype, querystatementfile, dbserver, username, password);
    	DCount = LoadDataBaseData.getCount();

    	//Collect data from UC homepage
    	clawerTCount.retriveData(loginname, loginpwd, dbserver);
    	UCount = parseDataFromHP.getUCount();
      	
    	
    	//Collect data from Analyze Report
    	TestCases = Prop.TestCase;
    	cubes = Prop.CUBES;
    	RepeatCreateCube.rcCubes(clawerTCount.driver, cubes, TestCases, "180");
    	ACount = RepeatCreateCube.getAnalyzeCount();
    	
    	
    	//Validate count # among DB and Homepage
    	compareResult.compare(DCount, ACount);
//    	compareResult.compare(DCount, UCount);
//    	compareResult.compare(ACount, UCount);
    	
    	//Close all stream and automation
    	Utils.info("Automation completed!");
    	clawerTCount.driver.close();
    	Utils.closeOutput();
    }
}