package Main;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;

import global.Prop;
import global.Utils;


public class Dedupping {
	@SuppressWarnings("unused")
	private static HashMap<String, String> colmuns = new HashMap<>();
	@SuppressWarnings("unused")
	private static HashSet<String> HostId = new HashSet<>();
	
    private static HashMap<String, String> conf = new HashMap<>();
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		long starttime = System.currentTimeMillis();
		if(args.length==0){
			System.out.println();
			System.out.println("No Input from Console is detected, default config file will be applied!");
			ded.initDed(conf);
			ded.dedupping();
		}else if(args[0].equals("-help")){
			System.out.println("Usage:");
			System.out.println("	-tpy               DB Type: Oracle or MSSQL?");
			System.out.println("	-u                 User name to login DB Server");
			System.out.println("	-p                 Password to login DB Server");
			System.out.println("	-vm                VM or IP where the DB Server is hosted");
			System.out.println("	-query             Path to Query Statement file");
			System.out.println("	-feature           Path to Feature file");
			System.out.println("	-golden            Path to Golden result file");
			System.out.println("	-column            Path to Column's name file");
			System.out.println("	-location          Path to Output location");
			System.out.println("	-datasource         Inventory Name");
			System.out.println("	-sqlTable          Table names in MSSQL env");
			System.out.println("	-oraTable          Table names in ORACLE env");
			System.out.println("	-postfix           The Postfix of the _P that needs to check out");
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
					else if(args[i].equals("-query"))
						conf.put("QueryStament", args[++i]);
					else if(args[i].equals("-feature"))
						conf.put("Feature", args[++i]);
					else if(args[i].equals("-golden"))
						conf.put("Golden", args[++i]);
					else if(args[i].equals("-column"))
						conf.put("Column", args[++i]);
					else if(args[i].equals("-location"))
						conf.put("Location", args[++i]);
					else if(args[i].equals("-tpy"))
						conf.put("DBType", args[++i]);
					else if(args[i].equals("-datasource"))
						conf.put("DataSource", args[++i]);
					else if(args[i].equals("-sqlTable"))
						conf.put("SQLTable", args[++i]);
					else if(args[i].equals("-oraTable"))
						conf.put("ORATable", args[++i]);
					else if(args[i].equals("-postfix"))
						conf.put("PostFix", args[++i]);
					else{
						System.out.println("   [error]: " + args[i] + " is an invalid command, "
								+ '\n' +  "            " + "help is available by typing -help");
						++i;
					}	
				}
			}
			
			ded.initDed(conf);
			ded.dedupping();
			try {
				Prop.bw.close();
			} catch (IOException e) {
			}
			Utils.elapsedTime(starttime, "Automation completed.");

		}
	}

}
