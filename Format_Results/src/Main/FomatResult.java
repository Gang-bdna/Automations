package Main;

import java.util.HashMap;

import global.Utils;

public class FomatResult {

    private static HashMap<String, String> conf = new HashMap<>();
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		long starttime = System.currentTimeMillis();
		if(args.length==0){
			System.out.println();
			System.out.println("No Input from Console is detected, default config file will be applied!");
			FR_Initialize.initConf(conf);
			FR_Initialize.resultAnalyze();
		    Utils.info("Automation script is completed."); 
		    Utils.elapsedTime(starttime, "Automation completed.");
		}
		else if(args[0].equals("-help")){
			System.out.println("Usage:");
			System.out.println("	-results			Results folder");
			System.out.println("	-jobnumber			Jenkins Job number");
			System.out.println("	-jobname			Jenkins Job name");
			System.out.println("	-sysinfo			System info file");
			System.out.println("	-location			Path to Log File location");
		}
		else {
			if(args.length>=1 && !args[0].equals("-help")){
				for(int i=0; i<args.length; i++){
					if(args[i].equals("-result"))
						conf.put("ResultFolder", args[++i]);
					else if(args[i].equals("-jobnum"))
						conf.put("JobNumber", args[++i]);
					else if(args[i].equals("-jobname"))
						conf.put("JobName", args[++i]);
					else if(args[i].equals("-sysinfo"))
						conf.put("SysInfo", args[++i]);
					else if(args[i].equals("-location"))
						conf.put("Location", args[++i]);
					else{
						System.out.println("   [error]: " + args[i] + " is an invalid command, "
								+ '\n' +  "            " + "help is available by typing -help");
						++i;
					}	
				}
			}
	    
			FR_Initialize.initConf(conf);
			FR_Initialize.resultAnalyze();
		    System.out.println('\n');
		    Utils.info("Automation script is completed."); 
		    Utils.elapsedTime(starttime, "Automation completed.");
		}
	}
}
