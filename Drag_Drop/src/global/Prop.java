package global;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;

import main.User_Console;

/**
 * Author : Gang Liu
 * Created: Feb. 22, 2017
 */

public class Prop 
{
	public static FileWriter docWrite;
	public static BufferedWriter bw;
	static{ try {
			docWrite = new FileWriter(User_Console.logfile);
			bw = new BufferedWriter(docWrite); 
			
		} catch (IOException e) {
			Utils.error("Failed initialize log file");
		}
	}
	
	public static Hashtable<String, Hashtable<String, ArrayList<ArrayList<String>>>> cubes = new Hashtable<>();
	public static HashSet<String> ID = new HashSet<>();
	public static HashSet<String> CUBES = new HashSet<String>();
}
/* End of Properties.java */