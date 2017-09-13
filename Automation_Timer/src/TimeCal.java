import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class TimeCal {

	private static FileWriter docWrite;
	private static BufferedWriter bw;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		long etime = System.currentTimeMillis();
		String file = args[0];
		
		try{
			docWrite = new FileWriter(file);
			bw = new BufferedWriter(docWrite); 
			
		 	DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		 	Calendar cal = Calendar.getInstance();
		 	String paten = dateFormat.format(cal.getTime());
		 	
		 	System.out.println("Full Testing Start at: " + paten);
		 	bw.write("StartTime:	" + paten); 
		 	bw.newLine();
			bw.write("Seconds:	" + System.currentTimeMillis());
			bw.newLine();
			bw.flush();
			bw.close();
		}catch(IOException io){
			io.printStackTrace();
		}
	}

}
