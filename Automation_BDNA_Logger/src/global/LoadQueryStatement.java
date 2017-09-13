package global;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class LoadQueryStatement {
	public  static String Query = "";
	
	public static String LoadSQLQuery(String input){
		//String Table = "select * from DISC_MISSING_HOSTS";
		//String Table = "BDNA20170222001_P";
		//String Table = "BDNA20170228005_P";
		String line = "";
		
		Utils.info("Start loading query statement from " + input);
		try{
			BufferedReader br = new BufferedReader(new FileReader(input));
			while((line = br.readLine()) != null){
				Query= Query + line ; 
			}
			br.close();
			Utils.info("Loading query statement is done.");
			//Utils.cout('\n');
		}catch (IOException io){
			Utils.error("Exception happened during loading QueryStatement!");;
			Utils.cout();
		}
		return Query;
	}
}
