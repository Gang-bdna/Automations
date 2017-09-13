package global;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Utils {
	/**
	 * Author : Gang Liu	
	 * Created: Feb 21, 2017
	 */
	// static LocalDateTime local = LocalDateTime.now();
	// static String paten = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:MM:SS").format(local);
	// static FileWriter fwriter;
	 public static void cout(Object o)
	    {
		 System.out.println(o);
		 Prop.outputfile.println(o);
	    }
	 
	 public static void print(Object o)
	    {
		 System.out.println(o);
	    }

	 public static void log(Object o)
	    {
		 	LocalDateTime local = LocalDateTime.now();
		 	String paten = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:MM:SS").format(local);
	        System.out.println(paten+ " [INFO] " + o);
	    }

	 public static void info(Object o)
	    {		    
		 	LocalDateTime local = LocalDateTime.now();
		 	String paten = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:MM:SS").format(local);
			System.out.println(paten+ " [INFO] " + o);
			Prop.outputfile.println(paten+ " [INFO] " + o);
	    }
	    
	 public static void conf(Object o)
	    {		    
		 	LocalDateTime local = LocalDateTime.now();
		 	String paten = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:MM:SS").format(local);
			System.out.println(paten+ " [CONFIG] " + o);
			Prop.outputfile.println(paten+ " [CONFIG] " + o);
	    }
	 
	 public static void closeOutput(){
		 	LocalDateTime local = LocalDateTime.now();
		 	String paten = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:MM:SS").format(local);
	    	Prop.outputfile.close();
	    }
	    
	 public static void error(Object o)
	    {
		 	LocalDateTime local = LocalDateTime.now();
		 	String paten = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:MM:SS").format(local);
	        System.out.println(paten+ " [ERROR] " + o);
	    }

	 public static void warning(Object o)
	    {
		 	LocalDateTime local = LocalDateTime.now();
		 	String paten = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:MM:SS").format(local);
	        System.out.println(paten+ " [WARNING] " + o);
	    }

	 public static void mark(Object o)
	    {
	        System.out.println("++++++++++++++++++" + o + "+++++++++++++++++++++");
	    }
	 public static void loading(Object o)
	    {
	        System.out.print("loading attributes...");
	    }
	    
	 public static void printArrList(ArrayList<String> arr)
	    {
	        for (String ele : arr){
	        	System.out.println(ele);
	        }
	    }
	    /**
	     * (1) Output time elapsed since last checkpoint.
	     * (2) Return start time for this checkpoint.
	     * @param startTime
	     *            Start time for the current phase.
	     * @param message
	     *            User-friendly message.
	     * @return
	     *            Start time for the next phase.
	     */
	    public static long elapsedTime(long startTime, String message)
	    {
	        if(message != null)
	        {
	            System.out.println(message);
	        }
	        long etime = System.currentTimeMillis();
	        long elapsedTime_sec = (etime - startTime) / 1000;
	        long elapsedTime_mil = (etime - startTime) % 1000;
	        System.out.print("  ===>Elapsed Time: " + elapsedTime_sec + "." + elapsedTime_mil + " second(s)");
	        System.out.println("");
	        Prop.outputfile.println(" [INFO] " + message);
	        Prop.outputfile.println(" [INFO] " + "  ===>Elapsed Time: " + elapsedTime_sec + "." + elapsedTime_mil + " second(s)");
	        return System.nanoTime();
	    }
}
