package Main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;


import fileOperate.loadResultsFileInfo;
import fileOperate.loadSysInfo;
import fileOperate.loadTestCaseConfig;
import fileOperate.writHtml;
import global.Utils;


public class FR_Initialize {
	private static String tc = "Input/TestCases.config";
	private static String template = "Input/Template.html";
//    private static HashMap<String, String> conf = new HashMap<>();
    private static String configfile = "Input/InputSetting.cfg";
    private static HashMap<String, String> userdefine = new HashMap<>();
    public static HashMap<String, ArrayList<String>> PredefineRoles = new HashMap<>();
//	private static ArrayList<String> eleList = new ArrayList<>();
    public static String logfile;
    public static String resultfolder;
    public static String jobNumber;
    public static String jobName;
    public static String sysinfo;
    public static String loc;

    
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
			
			logfile = loc + "/resultFormat.log";
			Utils.conf("Log file from Console is captuered.");
			//initialize result
//			result = loc + "/SysInfo.log";
		}else{
			
			loc = userdefine.get("LOCATION");
			File file = new File(loc);
			if(!file.exists()){
				file.mkdirs();
			}
			
			logfile = loc + "/resultFormat.log";
			Utils.conf("Default Log file is applied.");
			//initialize result
//			result = loc + "/SysInfo.log";
		}
		//Loading Result collection folder 
		if(conf.containsKey("ResultFolder")){
			resultfolder = conf.get("ResultFolder");
			Utils.conf("UserName from Console is captuered.");
		}else{
			resultfolder = userdefine.get("RESULTFOLDER");
			Utils.conf("Default Result Collection folder from config file is applied.");
		}
		
		//Loading Jenkins job build number
		if(conf.containsKey("JobNumber")){
			jobNumber = conf.get("JobNumber");
			Utils.conf("PassWord from Console is captuered.");
		}else{
			jobNumber = userdefine.get("JOBNUMBER");
			Utils.conf("Default Jenkins Job build number from Config file is applied.");
		}
		
		//Loading Jenkins job name
		if(conf.containsKey("JobName")){
			jobName = conf.get("JobName");
			Utils.conf("PassWord from Console is captuered.");
		}else{
			jobName = userdefine.get("JOBNAME");
			Utils.conf("Default Jenkins Job name from Config file is applied.");
		}
				
		//Loading System basic info
		if(conf.containsKey("SysInfo")){
			sysinfo = conf.get("SysInfo");
			Utils.conf("DP URL info from Console is captuered.");
		}else{
			sysinfo = userdefine.get("SYSINFO");
			Utils.conf("Default System basic info file path from Config file applied.");
		}
    }
	
	public static void resultAnalyze() {
		HashMap<String, Integer> FC = new HashMap<>();
		
		loadTestCaseConfig.LoadConfig(tc);
		FC = loadTestCaseConfig.getFCPair();
		Utils.info("Test Cases and Test Result List");
		System.out.println("----------");
		Utils.printHashMap(loadTestCaseConfig.getFTCPair());
		System.out.println("----------");
//		Utils.info(loadConfig.getFCPair().toString());
//		Utils.mark(FC.toString());
		loadResultsFileInfo.loadResults(resultfolder, FC);
//		Utils.info(loadResultsFileInfo.getResultSet().toString());
				
		HashMap<String, String> sys = new HashMap<>();
		loadSysInfo.format(sysinfo);
		sys = loadSysInfo.getSysInfo();
		
		writHtml.htmlFormat(template, loadTestCaseConfig.getFTCPair(), loadResultsFileInfo.getResultSet(), sys, jobNumber, jobName);
	}
}
