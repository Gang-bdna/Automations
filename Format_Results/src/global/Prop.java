package global;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;

import Main.FR_Initialize;


/**
 * Author : Gang Liu
 * Created: Feb. 10, 2016
 */

public class Prop
{
  
    /* Structures. */
	public static Hashtable<String, String> login = new Hashtable<>();
	public static Hashtable<String, Hashtable<String, ArrayList<ArrayList<String>>>> cubes = new Hashtable<>();
	public static HashSet<String> ID = new HashSet<>();
	public static HashSet<String> CUBES = new HashSet<String>();
	
	public static FileWriter docWrite;
	public static BufferedWriter bw;
	static{ try {
			docWrite = new FileWriter(FR_Initialize.logfile);
			bw = new BufferedWriter(docWrite); 
			
		} catch (Exception e) {
			Utils.error("Failed initialize log file");
		}
	}
}
/* End of Properties.java */