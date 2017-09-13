package fileOperate;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

import global.Utils;

public class loadQueryResult {
	private static FileWriter docWrite;
	private static BufferedWriter bw;
	private static int columnsNumber;
	private static int records = 0;
	
	public static void loadContentDB(ResultSet rs, String file){
		try {
			docWrite = new FileWriter(file);
			bw = new BufferedWriter(docWrite); 
			//File output = new file(Prop.OUTPUT);
			// Writer bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8"));
		} catch (IOException e) {
			Utils.error("Initialize Query Result file failed.");
		}
		
		try{
			//rs = sqlConn.getRS();
			Utils.info("Start loading query results...");
			ResultSetMetaData rsmd = rs.getMetaData();
			columnsNumber = rsmd.getColumnCount();
			
			while (rs.next()){
				++ records;
				for (int i=1; i<=columnsNumber; i++){
					try{
						String [] Line = rs.getString(i).split("	");
						for (String s : Line){
							bw.append(s).append(",");	
						}
					}catch (NullPointerException ne){
						bw.append("null").append(",");
						continue;
					}			
				}
				bw.append('\n');
				if (records%1000 == 0){
					Utils.info("Loading the " +  records + "th rows ...");
				}
			}
			bw.flush();
			bw.close();
			Utils.info("Total " +  records + " rows get loaded.");
			Utils.info("Full Query results has been recored.");
			Utils.cout();
		}catch (Exception e){
			Utils.error("Exception happened during loading Query Results from _P table!");
			Utils.error("Pls double check if the Inventory name is matched any normalize task in Normalize hoempage!");
			Utils.error("Or check if the default _P table is existing or not (current datasteamp + user predifined postfix)");
			Utils.cout();
		}
	}
}
