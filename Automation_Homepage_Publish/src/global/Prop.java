package global;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import Main.HP_DataCheck;

/**
 * Author : Gang Liu
 * Created: Feb. 22, 2017
 */

public class Prop 
{
	
	public static FileWriter docWrite;
	public static BufferedWriter bw;
	static{ try {
			docWrite = new FileWriter(HP_DataCheck.logfile);
			bw = new BufferedWriter(docWrite); 
			
		} catch (IOException e) {
			Utils.error("Failed initialize log file");
		}
	}
	
	//Pre-define Cubes and Testcases
	public static HashMap<String, String> TestCase = new HashMap<>();
	public static HashSet<String> CUBES = new HashSet<>();
	static{
		CUBES.add("Technopedia: Software");
		CUBES.add("Technopedia: Hardware");
		CUBES.add("Technopedia: Manufacturer");
		TestCase.put("Technopedia: Software", "SW Release Count");
		TestCase.put("Technopedia: Hardware", "HW Model Count");
		TestCase.put("Technopedia: Manufacturer", "MFR Name (1)");
	}
	

	
	
}
/* End of Properties.java */