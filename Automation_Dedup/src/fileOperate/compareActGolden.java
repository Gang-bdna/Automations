package fileOperate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

import global.Utils;

public class compareActGolden {
	private static HashSet<String> remainID = new HashSet<>();
	public static HashSet<String> totalNumMis = new HashSet<>();
	public static HashSet<String> detailsMis = new HashSet<>();
	public static HashSet<String> idMiss = new HashSet<>();
	
	@SuppressWarnings("unused")
	public static void compareAG(HashMap<String, ArrayList<ArrayList<String>>> act, 
			HashMap<String, ArrayList<ArrayList<String>>> golden, HashSet<String> hid){
		
		//Utils.cout('\n');
		Utils.info("Start comparing...");
		Iterator<String> itr = hid.iterator();
		try{
			for(String id : hid){
				if(act.containsKey(id)&&golden.containsKey(id)){
					if(act.get(id).size() != golden.get(id).size()){
						remainID.add(id);
						totalNumMis.add(id);
						Utils.warning("Mismatching is detected,  ID: " + id + "  the total host # uder the ID is not matching!");
					}
					else {
						for(ArrayList<String> glds : golden.get(id)){
							for(String gld : glds){
								try{
									glds.set(glds.indexOf(gld), gld.toLowerCase());
								}catch(NullPointerException e){
									glds.set(glds.indexOf(gld), "null");
								}
							}
						}
						
						for(ArrayList<String> at : act.get(id)){
							for(String a : at){
								try{
									at.set(at.indexOf(a), a.toLowerCase());
								}catch(NullPointerException ne){
									at.set(at.indexOf(a), "null");
								}
								
							}
						}
							//if(!(golden.containsValue(act.get(id))&&act.containsValue(golden.get(id)))){
						if(!golden.get(id).containsAll(act.get(id))){
							remainID.add(id);
							detailsMis.add(id);
							Utils.warning("Mismatching is detected,  ID: " + id + "  the host details under the ID is not matching!");
							//Utils.info("The subtotal of " + id + " are not matching.");
							
							//Print the details of diff
							//for(ArrayList<String> temp : act.get(id)){
							//	golden.get(id).remove(temp);
							//}
							
							//Utils.cout(golden.get(id).toString());
						}
					}
				}
				else if(!(act.containsKey(id)&&golden.containsKey(id))){
						remainID.add(id);
						idMiss.add(id);
						//Utils.info("ID is not matching " + id);
						Utils.warning("Mismatching is detected,  ID: " + id + "  is missing either from GOLDEN or QUERY results!");
					}
				//if((act.get(id).size()) != (golden.get(id).size())){
				//	Utils.cout("act==> " + act.get(id).size() + " Golden==>" + golden.get(id).size() + " ID: " + id);
				//}
				//Utils.cout("act==> " + act.get(id).size() + " Golden==>" + golden.get(id).size() + " ID: " + id);
			}

		}catch(NullPointerException e){
			Utils.error("Exception happened during loading comparison!");
			Utils.cout();
			//Utils.cout('\n');
		}
		
		if(!remainID.isEmpty()){
			Utils.cout();
			Utils.warning("Total " + remainID.size() + " HOSTs are not matching.");
			Utils.warning("Mismatching IDs: " + remainID.toString());
			Utils.cout();
			//Utils.mark(remainID.toString());
		}
		else{
			Utils.info("All are matched, No dedupping issue detected.");
		}
		//Utils.info("Comparing done, results has been recorded.");
		//Utils.cout('\n');
	}
	
	//Fetch the IDs which are not matching between Actual result and Golden result
	public static HashSet<String> getDiffId(){
		return remainID;
	}
	
	public static HashSet<String> getTotalNumMisId(){
		return totalNumMis;
	}
	
	public static HashSet<String> getDetailMisID(){
		return detailsMis;
	}
	
	public static HashSet<String> getMissID(){
		return idMiss;
	}
}
