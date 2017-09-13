package fileOperate;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;


public class loadSysInfo {
	private static HashMap<String, String> sys = new HashMap<>();
	public static void format(String file){
		try {
			String line = "";
			BufferedReader br = new BufferedReader(new FileReader(file));
			while((line = br.readLine())!=null){
				if(line.contains("StartTime")){
					sys.put("Script Start Time", line.substring(line.indexOf(":	")+1).trim().toString());
//					Utils.warning(line.substring(line.lastIndexOf(":	")+1).trim().toString());
				}
				else if(line.contains("Seconds")){
					String tm = line.substring(line.indexOf(":")+1).trim().toString();
//					Utils.mark(tm);
					long start = Long.valueOf(tm);
					long end = System.currentTimeMillis();
					long tSec = (end - start)/1000;
					long hours = tSec/3600;
					long remainSec = tSec%3600;
					long mins = remainSec/60;
					long secs = remainSec%60;
					
					String h = hours+"";
					String m = mins+"";
					String s = secs+"";

					if(hours<10) h = "0" + h;
					if(mins<10) m = "0" + m;
					if(secs<10) s = "0" + s;
					
					
					String totalTime = h + ":" + m  + ":" + s + "  (hh:mm:ss)";
					sys.put("Total Time", totalTime);
//					Utils.mark(totalTime);
				}
				else if(line.contains("Host Name")){
					sys.put("Machine Name", line.substring(line.indexOf(":")+1).trim().toString());
//					Utils.warning(line.substring(line.indexOf(":")+1).trim().toString());
				}
				else if(line.contains("OS Name")){
					sys.put("Operation System", line.substring(line.indexOf(":") + 1).trim().toString());
//					Utils.warning(line.substring(line.indexOf(":")+1).trim().toString());
				}
				else if(line.contains("System Model")){
					sys.put("System Model", line.substring(line.indexOf(":") + 1).trim().toString());
//					Utils.warning(line.substring(line.indexOf(":")+1).trim().toString());
				}
				else if(line.contains("System Type")){
					sys.put("System Type", line.substring(line.indexOf(":") + 1).trim().toString());
//					Utils.warning(line.substring(line.indexOf(":")+1).trim().toString());
				}
				else if(line.contains("Total Physical Memory")){
					sys.put("Physical Memory", line.substring(line.indexOf(":") + 1).trim().toString());
//					Utils.warning(line.substring(line.indexOf(":")+1).trim().toString());
				}
				else if(line.contains("Name=")){
					sys.put("Processor Type", line.substring(line.indexOf("=") + 1).trim().toString());
//					Utils.warning(line.substring(line.indexOf("=")+1).trim().toString());
				}
				else if(line.contains("NumberOfCores=")){
					sys.put("Number of Physical CPUs", line.substring(line.indexOf("=") + 1).trim().toString());
//					Utils.warning(line.substring(line.indexOf("=")+1).trim().toString());
				}
				else if(line.contains("NumberOfLogicalProcessors=")){
					sys.put("Number of Logical CPUs", line.substring(line.indexOf("=") + 1).trim().toString());
//					Utils.warning(line.substring(line.indexOf("=")+1).trim().toString());
				}
				else if(line.contains("Database Type")){
					sys.put("Database Type", line.substring(line.indexOf(":") + 1).trim().toString());
//					Utils.warning(line.substring(line.indexOf("=")+1).trim().toString());
				}
				else if(line.contains("Build Version")){
					sys.put("Build Version", line.substring(line.indexOf(":") + 1).trim().toString());
//					Utils.warning(line.substring(line.indexOf("=")+1).trim().toString());
				}
				else if(line.contains("Technopedia Version")){
					sys.put("Technopedia Version", line.substring(line.indexOf(":") + 1).trim().toString());
//					Utils.warning(line.substring(line.indexOf("=")+1).trim().toString());
				}
				else{
//					Utils.info(line);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	
	public static HashMap<String, String> getSysInfo(){
		return sys;
	}
}
