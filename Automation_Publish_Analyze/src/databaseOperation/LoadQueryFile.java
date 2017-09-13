package databaseOperation;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import global.Utils;

public class LoadQueryFile {
	public  static String Query = "";
	
	public static String LoadSQLQuery(String input){
		String line = "";
		
		Utils.info("Start loading query statement from: " + input);
		try{
			BufferedReader br = new BufferedReader(new FileReader(input));
			while((line = br.readLine()) != null){
				Query= Query + line + "\n"; 
			}
			br.close();
//			Utils.info(Query);
			Utils.info("Loading query statement is done.");
			//Utils.cout('\n');
		}catch (IOException io){
			Utils.error("Exception happened during loading QueryStatement!");;
			Utils.cout();
		}
		return Query;
	}
}
