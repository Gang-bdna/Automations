package Main;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;

import fileOperate.loadArg;
import global.Utils;


public class Match {
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
			loadArg.dedupping();
		}else if(args[0].equals("-help")){
			System.out.println("Usage:");
			System.out.println("	-u                 User name");
			System.out.println("	-p                 Password");
			System.out.println("	-sql               VM/IP which the SQL DB Server is hosted on");
			System.out.println("	-ora               VM/IP which the ORA DB Server is hosted on");
			System.out.println("	-skip	           Path to Feature file");
			System.out.println("	-log               Path to Log file");
			System.out.println("	-location          Path to Output files");	
		}
		else {
			if(args.length>=1 && !args[0].equals("-help")){
				for(int i=0; i<args.length; i++){
					if(args[i].equals("-u"))
						conf.put("UserName", args[++i]);
					else if(args[i].equals("-p"))
						conf.put("Password", args[++i]);
					else if(args[i].equals("-sql"))
						conf.put("sqlVM", args[++i]);
					else if(args[i].equals("-ora"))
						conf.put("oraVM", args[++i]);
					else if(args[i].equals("-skip"))
						conf.put("SkipTables", args[++i]);
					else if(args[i].equals("-log"))
						conf.put("Log", args[++i]);
					else if(args[i].equals("-location"))
						conf.put("Location", args[++i]);
					else{
						System.out.println("   [error]: " + args[i] + " is an invalid command, "
								+ '\n' +  "            " + "help is available by typing -help");
						++i;
					}	
				}
			}
			
			loadArg.initDed(conf);
			loadArg.dedupping();
			Utils.elapsedTime(StartTime, "Automation completed.");
		}
	}

}
