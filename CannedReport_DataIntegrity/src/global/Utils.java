package global;

public class Utils {
	/**
	 * Author : Gang Liu	
	 * Created: Jan 21, 2016
	 */
	 public static void cout(Object o)
	    {
	        System.out.println(o);
	    }

	    public static void echo(Object o)
	    {
	        System.out.println("   [echo] " + o);
	    }

	    public static void error(Object o)
	    {
	        System.out.println("  [error] " + o);
	    }

	    public static void warning(Object o)
	    {
	        System.out.println("[warning] " + o);
	    }

	    public static void mark(Object o)
	    {
	        System.out.println("++++++++++++++++++" + o + "+++++++++++++++++++++");
	    }
	    public static void loading(Object o)
	    {
	        System.out.print("loading attributes...");
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
	        return System.nanoTime();
	    }
}
