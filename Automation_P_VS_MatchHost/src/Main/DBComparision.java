package Main;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;

import fileOperate.loadArg;
import global.Utils;


public class DBComparision {
	@SuppressWarnings("unused")
	private static HashMap<String, String> colmuns = new HashMap<>();
	@SuppressWarnings("unused")
	private static HashSet<String> HostId = new HashSet<>();
	
    private static HashMap<String, String> conf = new HashMap<>();
	
	public static void main(String[] args) throws SQLException {
		// Start to record running time
		long StartTime = System.currentTimeMillis();
		
		if(args.length==0){
			System.out.println();
			System.out.println("No Input from Console is detected, default config file will be applied!");
			loadArg.initDed(conf);
			loadArg.comp();
		}else if(args[0].equals("-help")){
			System.out.println("Usage:");
			System.out.println("	-tpy               DB Type: Oracle or MSSQL?");
			System.out.println("	-u                 User name");
			System.out.println("	-p                 Password");
			System.out.println("	-vm                VM or IP where the DB Server is hosted");
			System.out.println("	-location          Path to Output files");	
			System.out.println("	-sqlTable          Specific _P Table name in MSSQL env");
			System.out.println("	-oraTable          Specific _P Table name in ORACLE env");
			System.out.println("	-commanTable       Specific _Match Table name in BDNA_Publish");
		}
		else {
			if(args.length>=1 && !args[0].equals("-help")){
				for(int i=0; i<args.length; i++){
					if(args[i].equals("-u"))
						conf.put("UserName", args[++i]);
					else if(args[i].equals("-p"))
						conf.put("Password", args[++i]);
					else if(args[i].equals("-vm"))
						conf.put("DBServer", args[++i]);
					else if(args[i].equals("-location"))
						conf.put("Location", args[++i]);
					else if(args[i].equals("-sqlTable"))
						conf.put("SQLTable", args[++i]);
					else if(args[i].equals("-oraTable"))
						conf.put("ORATable", args[++i]);
					else if(args[i].equals("-tpy"))
						conf.put("DBType", args[++i]);
					else if(args[i].equals("-commanTable"))
						conf.put("MatchTable", args[++i]);
					
					else{
						System.out.println("   [error]: " + args[i] + " is an invalid command, "
								+ '\n' +  "            " + "help is available by typing -help");
						++i;
					}	
				}
			}
			
			loadArg.initDed(conf);
			loadArg.comp();
			Utils.elapsedTime(StartTime, "Automation completed.");
		}
	}

}
