package Main;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;

import global.Prop;
import global.Utils;


public class HP_MainEntry {
	@SuppressWarnings("unused")
	private static HashMap<String, String> colmuns = new HashMap<>();
	@SuppressWarnings("unused")
	private static HashSet<String> HostId = new HashSet<>();
	
    private static HashMap<String, String> conf = new HashMap<>();
	
	public static void main(String[] args) throws InterruptedException, IOException {
		// TODO Auto-generated method stub
		long starttime = System.currentTimeMillis();
		if(args.length==0){
			System.out.println();
			System.out.println("No Input from Console is detected, default config file will be applied!");
			HP_DataCheck.initDed(conf);
			HP_DataCheck.HP_Validate();
		}else if(args[0].equals("-help")){
			System.out.println("Usage:");
			System.out.println("	-tpy               DB Type: Oracle or MSSQL?");
			System.out.println("	-u                 User name to login DB Server");
			System.out.println("	-p                 Password to login DB Server");
			System.out.println("	-vm                VM or IP where the DB Server is hosted");
			System.out.println("	-query             Path to Query Statement file");
			System.out.println("	-ucname            Login UserName for UC");
			System.out.println("	-ucpwd             Login Password for UC");
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
					else if(args[i].equals("-ucname"))
						conf.put("UCName", args[++i]);
					else if(args[i].equals("-ucpwd"))
						conf.put("UCPassword", args[++i]);
					else if(args[i].equals("-tpy"))
						conf.put("DBType", args[++i]);
					else if(args[i].equals("-location"))
						conf.put("Location", args[++i]);
					else{
						System.out.println("   [error]: " + args[i] + " is an invalid command, "
								+ '\n' +  "            " + "help is available by typing -help");
						++i;
					}	
				}
			}
			
			HP_DataCheck.initDed(conf);
			HP_DataCheck.HP_Validate();
			try {
				Prop.bw.close();
			} catch (IOException e) {
			}
			Utils.elapsedTime(starttime, "Automation completed.");

		}
	}

}
