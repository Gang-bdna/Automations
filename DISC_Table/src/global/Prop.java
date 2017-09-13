package global;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;

import Main.mainHelper;


/**
 * Author : Gang Liu
 * Created: Feb. 10, 2016
 */

public class Prop
{
    /* Pre-defined Login Info. */
    public static final String URL = "http://vm250w/bdna-admin/Admin.aspx";
    public static final String USERNAME = "NBIAdministrator	";
    public static final String PASSWORD = "Simple.0";
    
    /* Directories and files. */
    public static final String INPUTS = "C:/Users/gliu/workspace/Automation_Testing/inputs/inputs.xlsx";
    public static final String OUTPUTS = "C:/Users/gliu/workspace/Automation_Testing/outputs/ouputs.txt";
    public static final String CDATA = "C:/Users/gliu/workspace/Automation_Testing/outputs/";
//    public static final String FILE = "C:/Users/gliu/workspace/Automation_Testing/outputs/Dimension.cvs";
    
    /* Structures. */
	public static Hashtable<String, String> login = new Hashtable<>();
	public static Hashtable<String, Hashtable<String, ArrayList<ArrayList<String>>>> cubes = new Hashtable<>();
	public static HashSet<String> ID = new HashSet<>();
	public static HashSet<String> CUBES = new HashSet<String>();
	
	public static FileWriter docWrite;
	public static BufferedWriter bw;
	static{ try {
			docWrite = new FileWriter(mainHelper.logfile);
			bw = new BufferedWriter(docWrite); 
			
		} catch (Exception e) {
			Utils.error("Failed initialize log file");
		}
	}
}
/* End of Properties.java */