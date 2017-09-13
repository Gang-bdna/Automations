package fileOperate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import global.Utils;

public class FectchChp {
	private static HashMap<String, ArrayList<String>> ChpSet = new HashMap<>();
	
	public static void reformURL(String [] chp, String url, String type){
		if(type.toLowerCase().equals("dp")){
			ArrayList<String> DPCHP = new ArrayList<>();
			ArrayList<String> UCCHP = new ArrayList<>();

			for(String cp : chp){
				if(cp.toLowerCase().contains("ate")){
					String [] trim = cp.split(",");
					for(String name : trim){
						if(name.contains("Data_Platform_Administrator_Guide")){
							name = name.substring(1, name.length()-2);
							String basicUrl = url.replace("/index.htm", "/" + name);
							DPCHP.add(basicUrl);
						}
						else if(name.contains("User_Console_User_Guide")){
							name = name.substring(1, name.length()-2);
							String basicUrl = url.replace("/index.htm", "/" + name);
							UCCHP.add(basicUrl);
						}
					}
				}
//				else{
//					Utils.warning("Cannot tell which catagory the URL belongs to: " + cp);
//				}
			}
			ChpSet.put("DP", DPCHP);
			ChpSet.put("UC", UCCHP);
		}
		else if(type.toLowerCase().equals("uc")){
			ArrayList<String> DPCHP = new ArrayList<>();
			ArrayList<String> UCCHP = new ArrayList<>();
			for(String cp : chp){
				if(cp.toLowerCase().contains("ate")){
					String [] trim = cp.split(",");
					for(String name : trim){
						if(name.contains("Data_Platform_Administrator_Guide")){
							name = name.substring(1, name.length()-2);
							String basicUrl = url.replace("/index.htm", "/" + name);
							DPCHP.add(basicUrl);
						}
						else if(name.contains("User_Console_User_Guide")){
							name = name.substring(1, name.length()-2);
							String basicUrl = url.replace("/index.htm", "/" + name);
							UCCHP.add(basicUrl);
						}
					}
				}
//				else{
//					Utils.warning("Cannot tell which catagory the URL belongs to: " + cp);
//				}
			}
			ChpSet.put("DP", DPCHP);
			ChpSet.put("UC", UCCHP);
		}
	}
	
	public static HashMap<String, ArrayList<String>> getChpSet(){
		return ChpSet;
	}
}
