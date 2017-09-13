package global;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class Utils {
	/**
	 * Author : Gang Liu	
	 * Created: Feb 21, 2017
	 */
//	 static LocalDateTime local = LocalDateTime.now();
//	 static String paten = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:MM:SS").format(local);
	// static FileWriter fwriter;
	 public static void cout(Object o)
	    {
		 System.out.println(o);
			try {
				Prop.bw.newLine();
				Prop.bw.flush();
			} catch (IOException e) {
				Utils.error("Failed to write log.");
				//e.printStackTrace();
			}
	    }

	 public static void log(Object o)
	    {
		 	DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		 	Calendar cal = Calendar.getInstance();
		 	String paten = dateFormat.format(cal.getTime());
	        System.out.println(paten+ " [INFO] " + o);
	    }

	 public static void info(Object o)
	    {		    
		 	DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		 	Calendar cal = Calendar.getInstance();
		 	String paten = dateFormat.format(cal.getTime());
			System.out.println(paten+ " [INFO] " + o);
			//Prop.outputfile.println(paten+ " [INFO] " + o);
			try {
				Prop.bw.append(paten+ " [INFO] " + o);
				Prop.bw.newLine();
				Prop.bw.flush();
			} catch (IOException e) {
				//Utils.error("Failed to write log.");
			}
	    }
	    
	 public static void conf(Object o)
	    {		    
//		 	LocalDateTime local = LocalDateTime.now();
//		 	String paten = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:MM:SS").format(local);
		 	DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		 	Calendar cal = Calendar.getInstance();
		 	String paten = dateFormat.format(cal.getTime());
			System.out.println(paten+ " [CONFIG] " + o);
			//Prop.outputfile.println(paten+ " [CONFIG] " + o);
			try {
				Prop.bw.append(paten+ " [INFO] " + o);
				Prop.bw.newLine();
				Prop.bw.flush();
			} catch (IOException e) {
				//Utils.error("Failed to write log.");
			}
	    }
	 
	 public static void closeOutput(){
	    	try {
				Prop.bw.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				//Utils.error("Failed to write log.");
			}
	    }
	    
	 public static void error(Object o)
	    {
		 	DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		 	Calendar cal = Calendar.getInstance();
		 	String paten = dateFormat.format(cal.getTime());
	        System.out.println(paten+ " [ERROR] " + o);
			try {
				Prop.bw.append(paten+ " [ERROR] " + o);
				Prop.bw.newLine();
				Prop.bw.flush();
			} catch (IOException e) {
				//Utils.error("Failed to write log.");
			}
	    }

	 public static void warning(Object o)
	    {
		 	DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		 	Calendar cal = Calendar.getInstance();
		 	String paten = dateFormat.format(cal.getTime());
	        System.out.println(paten+ " [WARNING] " + o);
			try {
				Prop.bw.append(paten+ " [WARNING] " + o);
				Prop.bw.newLine();
				Prop.bw.flush();
			} catch (IOException e) {
				//Utils.error("Failed to write log.");
			}
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
	        //Prop.outputfile.println(" [INFO] " + message);
	        //Prop.outputfile.println(" [INFO] " + "  ===>Elapsed Time: " + elapsedTime_sec + "." + elapsedTime_mil + " second(s)");
	        try {
				Prop.bw.append(" [INFO] " + message);
				Prop.bw.newLine();
				Prop.bw.append(" [INFO] " + "  ===>Elapsed Time: " + elapsedTime_sec + "." + elapsedTime_mil + " second(s)");
				Prop.bw.newLine();
				Prop.bw.flush();
			} catch (IOException e) {
				//Utils.error("Failed to write log.");
			}
	        return System.nanoTime();
	    }
}
