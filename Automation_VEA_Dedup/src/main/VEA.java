package main;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;

import global.Utils;

public class VEA {

// Mark: https://coderanch.com/t/306966/databases/Execute-sql-file-java
	
	/**
	 * Author : Gang Liu	
	 * Created: March 08, 2017
	 */
	/* Global parameters */
	//public static WebDriver driver = new FirefoxDriver();
    private static HashMap<String, String> conf = new HashMap<>();
    
	public static void main(String[] args) throws InterruptedException, IOException, SQLException {
		// TODO Auto-generated method stub
		//Utils.info("Programm Begin!"); 

		long starttime = System.currentTimeMillis();
		if(args.length==0){
			System.out.println();
			System.out.println("No Input from Console is detected, default config file will be applied!");
			VEAHelper.initConf(conf);
			VEAHelper.dedupCkc();
		    Utils.info("Automation script is completed."); 
		    Utils.elapsedTime(starttime, "Automation completed.");
		}
		else if(args[0].equals("-help")){
			System.out.println("Usage:");
			System.out.println("	-vm                VM or IP of Testing VM");
			System.out.println("	-u                 User name for Admin UI login");
			System.out.println("	-p                 Password for Admin UI login");
			System.out.println("	-db                DB Type fo the VM");
			System.out.println("	-sid               SID of ORACLE Client");
			System.out.println("	-location	       Path to Log File location");
			System.out.println("	-query	           Path to query file that needs to be executed");
		}
		else {
			if(args.length>=1 && !args[0].equals("-help")){
				for(int i=0; i<args.length; i++){
					if(args[i].equals("-u"))
						conf.put("UserName", args[++i]);
					else if(args[i].equals("-p"))
						conf.put("Password", args[++i]);
					else if(args[i].equals("-vm"))
						conf.put("VM", args[++i]);
					else if(args[i].equals("-db"))
						conf.put("DBType", args[++i]);
					else if(args[i].equals("-sid"))
						conf.put("SID", args[++i]);
					else if(args[i].equals("-location"))
						conf.put("Location", args[++i]);
					else if(args[i].equals("-query"))
						conf.put("SQLStatement", args[++i]);
					else{
						System.out.println("   [error]: " + args[i] + " is an invalid command, "
								+ '\n' +  "            " + "help is available by typing -help");
						++i;
					}	
				}
			}
	    
			VEAHelper.initConf(conf);
			VEAHelper.dedupCkc();;
		    Utils.info("Automation script is completed."); 
		    Utils.elapsedTime(starttime, "Automation completed.");
		}
	}
}

