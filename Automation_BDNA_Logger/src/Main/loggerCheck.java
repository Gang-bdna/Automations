package Main;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;

import global.Utils;


public class loggerCheck {
	@SuppressWarnings("unused")
	private static HashMap<String, String> colmuns = new HashMap<>();
	@SuppressWarnings("unused")
	private static HashSet<String> HostId = new HashSet<>();
	
    private static HashMap<String, String> conf = new HashMap<>();
	
	public static void main(String[] args) throws SQLException, IOException {
		// TODO Auto-generated method stub
		
		if(args.length==0){
			System.out.println();
			System.out.println("No Input from Console is detected, default config file will be applied!");
			initializeInput.initDed(conf);
			Utils.cout();
			initializeInput.check();
		}else if(args[0].equals("-help")){
			System.out.println("Usage:");
			System.out.println("	-tpy               DB Type: Oracle or MSSQL?");
			System.out.println("	-u                 User Name to login DB Server");
			System.out.println("	-p                 Password to login DB Server");
			System.out.println("	-vm                VM/IP which the DB Server is hosted on");
			System.out.println("	-query             Path to Query Statement file");
			System.out.println("	-log               Path to Log file");
			System.out.println("	-location          Path to Output location");	
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
					else if(args[i].equals("-log"))
						conf.put("Log", args[++i]);
					else if(args[i].equals("-location"))
						conf.put("Location", args[++i]);
					else if(args[i].equals("-tpy"))
						conf.put("DBType", args[++i]);
					else{
						System.out.println("   [error]: " + args[i] + " is an invalid command, "
								+ '\n' +  "            " + "help is available by typing -help");
						++i;
					}	
				}
			}
			
			initializeInput.initDed(conf);
			Utils.cout();
			initializeInput.check();
		}
	}

}
