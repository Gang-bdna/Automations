package fileOperate;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;

import global.Utils;

public class loadResultsFileInfo {
	private static HashMap<String, ArrayList<Integer>> ResultSet = new HashMap<>();
	
	public static void loadResults(String file, HashMap<String, Integer> FC){
		File folder = new File(file);
		File[] ResultFiles = folder.listFiles();
		Utils.cout();
		
		//Loading all sql files
		for(File f : ResultFiles){
			String sql = f.getAbsolutePath();
			String name = f.getName();
			ArrayList<Integer> data = new ArrayList<>();
//			Utils.info(name);
			
			if(FC.get(name) != null){
				int col = FC.get(name)-1;
				Utils.info("Laoding and analyse result from result file: " + name);
				
				try{
					BufferedReader br = new BufferedReader(new FileReader(sql));
					String line = "";
					int pass_Count = 0;
					int fail_Count = 0;
					int total = 0;
					
					if(name.substring(name.lastIndexOf(".")+1).trim().equals("tab")){
						if(name.contains("user_console_data_integrity")){
							while((line = br.readLine()) != null){
								String[] lineArr = line.split("	");
								try{
//									Utils.mark(col);
									if(lineArr[col].trim().toLowerCase().equals("pass")){
										pass_Count ++;
									}else if(lineArr[col].trim().toLowerCase().equals("fail")){
										fail_Count ++;
									}
								}catch(ArrayIndexOutOfBoundsException ob){
//									ob.printStackTrace();
									Utils.warning("Invalid Row Skipped from " + f + ": "+ line);
									continue;
								}
							}
							br.close();
							total = pass_Count + fail_Count;
							data.add(total);
							data.add(pass_Count);
							data.add(fail_Count);
							
							ResultSet.put(name, data);
						}else{
							while((line = br.readLine()) != null){
								if(line.contains("Total API Calls") || line.contains("Total reports tested")){
									String tc = line.substring(line.lastIndexOf(":")+1).trim();
									total = Integer.parseInt(tc);
								}
								if(line.contains("API Tests Failed") || line.contains("Test Cases Failed")){
									String ftc = line.substring(line.lastIndexOf(":")+1).trim();
									fail_Count = Integer.parseInt(ftc);
								}
								pass_Count = total - fail_Count;
							}
							br.close();
							total = pass_Count + fail_Count;
							data.add(total);
							data.add(pass_Count);
							data.add(fail_Count);
							
							ResultSet.put(name, data);
//							Utils.mark(total + "---" + fail_Count);
						}
						
					}else{
						while((line = br.readLine()) != null){
							String[] lineArr = line.split(",");
							try{
//								Utils.mark(col);
								if(lineArr[col].trim().toLowerCase().equals("passed")){
									pass_Count ++;
								}else if(lineArr[col].trim().toLowerCase().equals("failed")){
									fail_Count ++;
								}
							}catch(ArrayIndexOutOfBoundsException ob){
								ob.printStackTrace();
								Utils.warning("Invalid Row Skipped from " + f + ": "+ line);
								continue;
							}
						}
						br.close();
						total = pass_Count + fail_Count;
						data.add(total);
						data.add(pass_Count);
						data.add(fail_Count);
						
						ResultSet.put(name, data);
					}
					
				}catch (Exception io){
					Utils.error("Failed to load " + f.getName());
					io.printStackTrace();
					Utils.cout();
				}
			}
		}
		Utils.info("Loading Query files done.");
		Utils.cout();
		//Utils.printHashMap(QuerySet);
	}
	
	public static HashMap<String, ArrayList<Integer>> getResultSet(){
		return ResultSet;
	}
}
