package loadCSV;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;

import global.Utils;


public class compare {
	private static FileWriter docWrite;
	private static BufferedWriter bw;
	
	public static void diff(File leftInput, File rightInput, String diff_file) throws IOException {
		long starttime = System.currentTimeMillis();
		//Write diff
		try {
			docWrite = new FileWriter(diff_file);
			bw = new BufferedWriter(docWrite); 
			
		} catch (IOException e) {
			Utils.error("Initialize "+diff_file+" file failed");
		}
		
		//Loading data from .csv file
		CsvParserSettings settings = new CsvParserSettings();
	    CsvParser leftParser = new CsvParser(settings);
	    CsvParser rightParser = new CsvParser(settings);

	    leftParser.beginParsing(leftInput);
	    rightParser.beginParsing(rightInput);

	    String[] left;
	    String[] right;
	    int row = 0;
	    
	    //Comparison
	    Utils.info("Start comparing...");
	    while ((left = leftParser.parseNext()) != null && (right = rightParser.parseNext()) != null) {
	        row++;
	        if (!Arrays.equals(left, right)) {
	            //System.out.println(row + ":\t" + Arrays.toString(left) + " != " + Arrays.toString(right));
	        	bw.append(Arrays.toString(left).substring(1, Arrays.toString(left).length()-1)).append('\n');
	        }
	        
	        if(row%5000==0){
				bw.flush();
	        }
	    }

	    leftParser.stopParsing();
	    rightParser.stopParsing();
	    
	    if(row==0){
	    	System.out.println("The given two csv files are exactly idential!");
	    }else{

	    	Utils.error("Total " +row+ " Rows are Mismatching!");
	    	Utils.info("Differeces are recorded!");
	    	Utils.elapsedTime(starttime, "");
	    }
	    
	    bw.close();
	}
	
}
