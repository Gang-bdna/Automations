package global;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;


/**
 * Author : Gang Liu
 * Created: Feb. 22, 2017
 */

public class Prop 
{
	/*SQL Connection parameters*/
	public static final String USERNAME = "bdna";
	public static final String PASSWORD = "bdna";
	//public static final String URL = "jdbc:sqlserver://10.10.22.63;databaseName=bdna";
	public static final String URL = "jdbc:sqlserver://10.10.11.209;databaseName=bdna";
	
	/*SQL Query Statement*/
	public static final String INPUT = "C:/JavaWorkSpace/Result/QueryStatement.txt";
	
	/*Features to be used for comparison*/
	public static final String FEATURES = "C:/JavaWorkSpace/Result/Features.txt";
	
	/*OUTPUT Query Results*/
	public static final String OUTPUT = "C:/JavaWorkSpace/Result/MSSQL.cvs";
	
	/*Golden Input*/
	public static final String COLMUNS = "C:/JavaWorkSpace/Result/Columns.txt";
	
	/*Golden Input*/
	public static final String GOLDENS = "C:/JavaWorkSpace/Result/GOLDEN.cvs";
	
	/*Difference Output*/
	public static final String DIFFERENCE = "C:/JavaWorkSpace/Result/DIFFERENCE.cvs";
	
	/*Difference Output*/
	public static String LOGFILE = "C:/JavaWorkSpace/Result/LOG.txt";
	//public static String LOGFILE = "LOG.txt";
	
	/*Write into log*/
//	public static FileWriter fwriter;
//	static {
//		try {
//			fwriter = new FileWriter(ded.logfile);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
//	public static PrintWriter outputfile = new PrintWriter(fwriter);
	
	public static FileWriter docWrite;
	public static BufferedWriter bw;
	static{ try {
			docWrite = new FileWriter(LOGFILE);
			bw = new BufferedWriter(docWrite); 
			
		} catch (IOException e) {
			Utils.error("Failed initialize log file");
		}
	}
}
/* End of Properties.java */