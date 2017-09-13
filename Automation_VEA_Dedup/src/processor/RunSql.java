package processor;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;

import DBConnection.oraConn;
import DBConnection.sqlConn;
import global.Utils;
import main.VEAHelper;

public class RunSql {
	private static FileWriter docWrite;
	private static BufferedWriter bw;
	private static int columnsNumber;
	private static int records = 0;
	private static String badRes = VEAHelper.loc + "/VEA_BadResult.csv";
	private static String statement = "NO-QUERY-FOUND, PLS CHECK IF "+VEAHelper.output+" IS EMPTY!";
	
	public static void exeMssql(String command, String file) throws InterruptedException, SQLException{
		HashMap<Integer, String> DedupType = new HashMap<>();
		DedupType.put(1, "Versions Dedupping");
		DedupType.put(2, "Edtion Dedupping");
		DedupType.put(3, "All Hidden Dedupping");
		DedupType.put(4, "VEA");
		
		boolean issue = false;
		boolean hasdata = false;
		int TypeMark = 0;
		
		try
		{
		    //Process process = Runtime.getRuntime().exec(command);
			Utils.info("Start executing VEA Checking script against MSSQL env...");
			Process he = Runtime.getRuntime().exec(command);
			he.waitFor();
			he.destroy();
			Thread.sleep(10000);
			//loading query
			BufferedReader qr = new BufferedReader(new FileReader(file));
			String query = "";
			while((query=qr.readLine())!=null){
				if(query.contains("SELECT HOSTID")){
					statement = query;
					hasdata = true;
//					Utils.info(que);
					break;
				}
			}
			qr.close();
			
			//Write Testing results
			docWrite = new FileWriter(VEAHelper.result);
			bw = new BufferedWriter(docWrite);
			bw.append("Test Cases").append(",").append("Results");
			bw.newLine();
			bw.flush();
			
			Utils.cout();
			Utils.info("Analyese output...");
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line = "";
			while((line=br.readLine())!=null){
				if(line.contains("----")){
					TypeMark ++;
					line = br.readLine();
					if(line.contains("---")){
						break;
					}
					if(!line.trim().isEmpty()){
						Utils.error(DedupType.get(TypeMark) + " issue is detected!");
						bw.append(DedupType.get(TypeMark)).append(",").append("Failed");
						bw.newLine();
						bw.flush();
						issue = true;
					}
					if(line.trim().isEmpty()){
						Utils.info(DedupType.get(TypeMark) + " checking is passed.");
						bw.append(DedupType.get(TypeMark)).append(",").append("Passed");
						bw.newLine();
						bw.flush();
					}
				}
			}
			
			//No data found
			if(!hasdata){
				Utils.error("No data found, pls double check if any normalization task exists.");
				bw.append("Versions Dedupping").append(",").append("Failed");
				bw.newLine();
				bw.flush();
				bw.append("Edtion Dedupping").append(",").append("Failed");
				bw.newLine();
				bw.flush();
				bw.append("All Hidden Dedupping").append(",").append("Failed");
				bw.flush();
			}
			
			bw.close();
			br.close();
			
			//Write Bad records into file
			docWrite = new FileWriter(badRes);
			bw = new BufferedWriter(docWrite);
			
			if(!hasdata){
				bw.write("No data is found, pls double check if any normalization task exists.");
				bw.flush();
				bw.close();
			}
			//loading bad data
			//String op = "sqlcmd -S " + VEAHelper.url +" -U " + VEAHelper.username + " -P " + VEAHelper.password + " -q " +"\""+ que + "\""+" -o " + badRes;
			if(issue&&hasdata){
				Utils.cout();
				Utils.info("Loading bad records by running the below query...");
				Utils.info(statement);
//			    Process process = Runtime.getRuntime().exec(op);
//			    Thread.sleep(60000);
								
				//connect to DB server
				sqlConn.sqlConnection(VEAHelper.url, VEAHelper.username, VEAHelper.password, statement);
				ResultSet rs = oraConn.getRS();

				ResultSetMetaData rsmd = rs.getMetaData();
				columnsNumber = rsmd.getColumnCount();
				
				Utils.info("Start loading bad records...");
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
					if (records%15000 == 0){
						Utils.info("Loading the " +  records + "th rows ...");
						bw.flush();
					}
				}
				bw.flush();
				bw.close();
				
				Utils.info("Total " +  records + " rows get loaded.");
				
			    Utils.info("Query results have been recorded.");
			}else if(!issue && hasdata){
				Utils.info("No deduping issues are detected!");  
				bw.write("No deduping issues are detected!");
				bw.flush();
				bw.close();
			}
		    
		} catch (IOException e)
		{
		    e.printStackTrace();
		}
		Utils.cout();
		Utils.info("Command line has been executed completely!");
	}
	
	public static void exeOracle(String command, String file) throws InterruptedException, IOException, SQLException{
		HashMap<Integer, String> DedupType = new HashMap<>();
		DedupType.put(1, "Versions Dedupping");
		DedupType.put(2, "Edtion Dedupping");
		DedupType.put(3, "All Hidden Dedupping");
		DedupType.put(4, "VEA");
		
		boolean issue = true;
		boolean hasdata = true;
		try
		{
		    //Process process = Runtime.getRuntime().exec(command);
			Utils.info("Start executing VEA Checking script against ORACLE env...");
			Utils.info(command);
			Process he = Runtime.getRuntime().exec(command);
//			he.waitFor();
			Thread.sleep(50000);
//			he.destroy();
			
			//loading query
			String query = "";
			BufferedReader qr = new BufferedReader(new FileReader(file));
			while((query=qr.readLine())!=null){
				if(query.contains("SELECT HOSTID, HIDDEN, PROD_RID")){
					statement = query;
					break;
				}
			}
			qr.close();
			Utils.cout();
			Utils.info("Analyese output...");
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line = "";
			while((line=br.readLine())!=null){
				if(line.trim().contains("no data found")){
					Utils.error("No data found, pls double check if any normalization task exists.");
					hasdata = false;
					break;
				}
				if(line.trim().equals("No errors.")){
					issue = false;
					break;
				}
				
			}
			br.close();
			
			//Write testing results
			//String op = "cmd.exe /C sqlplus " + VEAHelper.username +"/"+ VEAHelper.password + "@" + VEAHelper.url + ":1521" +"/" + VEAHelper.sid + " @" + select + " > " + VEAHelper.result;	
			docWrite = new FileWriter(VEAHelper.result);
			bw = new BufferedWriter(docWrite);
			bw.append("Test Cases").append(",").append("Results");
			bw.newLine();
			bw.flush();

			if(hasdata){
				if(issue){
					Utils.error("Versions Dedupping Checking is failed");
					bw.append("Versions Dedupping Checking").append(",").append("Failed");
					bw.newLine();
					bw.flush();	
					Utils.error("Editions Dedupping Checking is failed");
					bw.append("Editions Dedupping Checking").append(",").append("Failed");
					bw.newLine();
					bw.flush();	
					Utils.error("All Hiddens Checking is failed");
					bw.append("All Hiddens Dedupping Checking").append(",").append("Failed");
					bw.newLine();
					bw.flush();	
				}else{
					Utils.info("Versions Dedupping Checking is Passed");
					bw.append("Versions Dedupping Checking").append(",").append("Passed");
					bw.newLine();
					bw.flush();		
					Utils.info("Editions Dedupping Checking is Passed");
					bw.append("Editions Dedupping Checking").append(",").append("Passed");
					bw.newLine();
					bw.flush();	
					Utils.info("All Hiddens Dedupping Checking is Passed");
					bw.append("All Hiddens Checking").append(",").append("Passed");
					bw.newLine();
					bw.flush();
				}
			}else{
				Utils.error("Versions Dedupping Checking is failed");
				bw.append("Versions Dedupping Checking").append(",").append("Failed");
				bw.newLine();
				bw.flush();	
				Utils.error("Editions Dedupping Checking is failed");
				bw.append("Editions Dedupping Checking").append(",").append("Failed");
				bw.newLine();
				bw.flush();	
				Utils.error("All Hiddens Checking is failed");
				bw.append("All Hiddens Dedupping Checking").append(",").append("Failed");
				bw.newLine();
				bw.flush();	
			}
			bw.close();
			
			Thread.sleep(2000);
			
			//Write bad records
			docWrite = new FileWriter(badRes);
			bw = new BufferedWriter(docWrite); 
			
			if(!hasdata){
				bw.write("No data is found, pls double check if any normalization task exists.");
				bw.flush();
			}
			
			if(issue && hasdata){
				Utils.info("Dedupping issue is detected...");
				Utils.cout();
				Utils.info("Loading bad records by running the below query...");
				Utils.info(statement);
				
				//connect to DB server
				oraConn.sqlConnection(VEAHelper.url, VEAHelper.username, VEAHelper.password, statement);
				ResultSet rs = oraConn.getRS();

				try{
					ResultSetMetaData rsmd = rs.getMetaData();
					columnsNumber = rsmd.getColumnCount();
				}catch(NullPointerException np){
					Utils.error("Failed to run sql commandline!");
				}
				
				Utils.info("Start loading bad records...");
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
					if (records%15000 == 0){
						Utils.info("Loading the " +  records + "th rows ...");
						bw.flush();
					}
				}
				bw.flush();
				bw.close();
				
				Utils.info("Total " +  records + " rows get loaded.");
//				Utils.info("Full Query results has been recored.");
				Utils.cout();
				
//			    Process process = Runtime.getRuntime().exec(op);
//				Thread.sleep(40000);
			    Utils.info("Query results have been recorded.");
			}else if(!issue && hasdata){
				Utils.info("No deduping issues are detected!");  
				bw.write("No deduping issues are detected!");
				bw.flush();
				bw.close();
			}			
		} catch (IOException e)
		{
		    e.printStackTrace();
		}
		Utils.cout();
		Utils.info("Command line has been executed completely!");
	}
}
