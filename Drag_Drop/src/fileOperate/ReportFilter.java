package fileOperate;

import java.util.HashSet;

import global.Utils;

public class ReportFilter {
	private static HashSet<String> remains = new HashSet<>();
	
	public static HashSet<String> filter(HashSet<String> cubes, String reports){
		Utils.info("Applying Report filters...");
		
		//Loading Normalize related cubes 
		if(reports.toString().trim().toLowerCase().equals("normalize")){
			for(String cube : cubes){
				if(cube.contains("Normalize")){
					remains.add(cube);
				}
			}
		}
		//Loading Technopedia related cubes 
		else if(reports.toString().trim().toLowerCase().equals("technopedia")){
			for(String cube : cubes){
				if(cube.contains("Technopedia")){
					remains.add(cube);
				}
			}
		}
		//Loading Specific report
		else if(reports.toString().trim().contains(":") && !reports.toString().trim().contains(",")){
			if(cubes.contains(reports.toString().trim())){
				remains.add(reports.toString().trim());
			}else{
				Utils.warning("Report: [" + reports.toString().trim() + "] is not found in Cube banner!");
			}
		}
		//Loading Specific report list
		else if(reports.toString().trim().contains(":") && reports.toString().trim().contains(",")){
			String[] repts = reports.split(",");
			for(String rep : repts){
				if(cubes.contains(rep.toString().trim())){
					remains.add(rep.toString().trim());
				}else{
					Utils.warning("Report: [" + rep.toString().trim() + "] is not found in Cube banner!");
				}
			}
		}
		//Loading all reports found in UI
		else if(reports.toString().trim().toLowerCase().equals("all")){
			remains = cubes;
		}
		
		//check empty
		if(remains.isEmpty()){
			Utils.warning("Report or Report start with: [" + reports.toString().trim() + "] are not found from Cubes banner!");
		}
		
		Utils.info("Report filters have been applied completely!");
		Utils.info("Totol: " + remains.size() + " reports are(is) remained for drag-drop testing...");
		Utils.cout();
		
		return remains;	
	}
	
	public static HashSet<String> getRemains(){
		return remains;
	}
}
