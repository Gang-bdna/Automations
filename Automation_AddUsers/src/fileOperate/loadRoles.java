package fileOperate;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import global.Utils;

public class loadRoles {
	private static String line = "";
	private static HashMap<String, ArrayList<String>> roles = new HashMap<>();
	private static ArrayList<String> userList = new ArrayList<>();
	
	public static void LoadCheckList(String features){		
		Utils.info("Loading the Elements that neeed to be checked.");
		Utils.info("Checklist file found: " + features);
		try{
			BufferedReader br = new BufferedReader(new FileReader(features));
			while((line = br.readLine()) != null){
				ArrayList<String> temp = new ArrayList<>();
				String[] list;
				if(line.contains("@")){
						String [] feat = line.split("	");
						userList.add(feat[1]);
						list = feat[2].toString().split(",");
						for(String s : list){
							temp.add(s);
						}
						
						roles.put(feat[1], temp);
				}
			}
			br.close();
		}catch (Exception io){
			Utils.error("Exception happened during loading CheckList!");
			Utils.cout('\n');
			io.printStackTrace();
		}
		//Utils.printArrList(Features);
		Utils.info("Loading CheckList done.");
		Utils.cout('\n');
	}
	
	//Return User List
	public static ArrayList<String> getUserList(){
		return userList;
	}
	
	//Return Pairs<UserName, Roles>
	public static HashMap<String, ArrayList<String>> getRoles(){
		return roles;
	}
}
