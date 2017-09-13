package fileOperate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;


public class IndexID {
	private static HashMap<String, ArrayList<String>> IDs = new HashMap<>();
	public static HashMap<String, ArrayList<String>> map(HashMap<String, String> IDPairs,
			HashMap<String, ArrayList<String>> PredefineRoles){
		//get the Role names
		Set<String> roles = PredefineRoles.keySet();
//		Utils.mark(roles);
		try{
			for(String name : roles){
				ArrayList<String> temp = new ArrayList<>();
				for(String rol : PredefineRoles.get(name)){
//					Utils.mark(name + "==" + rol);
					temp.add(IDPairs.get(rol.toUpperCase()));
				}
				IDs.put(name, temp);
			}
		}catch(NullPointerException nu){
			
		}
			
		return IDs;
	}
}
