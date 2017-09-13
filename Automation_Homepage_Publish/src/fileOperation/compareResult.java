package fileOperation;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Set;

import Main.HP_DataCheck;
import global.Utils;

public class compareResult {
	private static FileWriter docWrite;
	private static BufferedWriter bw;
	public static void compare(HashMap<String, String> BTC, HashMap<String, String> UTC){
		Utils.info("Strat comparing data count among publish and homepage...");
		
		//Initialize bufferedwriter
		try {
			docWrite = new FileWriter(HP_DataCheck.result);
			bw = new BufferedWriter(docWrite); 
			bw.append("Test Cases").append(",").append("Data in DataBase").append(",").append("Data on Homepage").append(",").append("Results");
			bw.flush();
			bw.newLine();
		} catch (IOException e) {
			Utils.error("Initialize "+HP_DataCheck.result+" file failed");
		}
		
		//Compare and Write reulsts
		try{
			Set<String> Bkey = BTC.keySet();
			Set<String> Ukey = UTC.keySet();

			for(String key : Bkey){
				if(Ukey.contains(key)){
					if(BTC.get(key).equals(UTC.get(key))){
						Utils.info(key + " counts are matched");
						bw.append(key).append(",").append(BTC.get(key)+"").append(",").append(UTC.get(key)+"").append(",").append("Passed");
						bw.flush();
						bw.newLine();
					}else if(!BTC.get(key).equals(UTC.get(key)))	{
						Utils.error(key + " counts do not match!");
						bw.append(key).append(",").append(BTC.get(key)+"").append(",").append(UTC.get(key)+"").append(",").append("Failed");
						bw.flush();
						bw.newLine();
					}
					Ukey.remove(key);
				}else{
					Utils.error(key + " count is not found in UC Homepage! Mark testcase failed!");
					bw.append(key).append(",").append(BTC.get(key)+"").append(",").append("N/A").append(",").append("Failed");
					bw.flush();
					bw.newLine();
				}
			}
			
			if(!Ukey.isEmpty()){
				for(String k : Ukey){
					Utils.error(k + " count is not found in publish! Mark testcase failed!");
					bw.append(k).append(",").append(BTC.get(k)+"").append(",").append("N/A").append(",").append("Failed");
					bw.flush();
					bw.newLine();
				}
			}
			
			bw.close();
		}catch(IOException io){
			Utils.error("Exception happens during writing results.");
			io.printStackTrace();
		}
	}
}
