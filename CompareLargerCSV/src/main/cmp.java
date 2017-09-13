package main;

import java.io.File;
import java.io.IOException;

import loadCSV.compare;

public class cmp {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		String cat = "Input/cat.csv";
		String tcat = "Input/tcat.csv";
		
//		String cat = "Input/CAT_UUID.txt";
//		String tcat = "Input/TCAT_UUID.txt";
		
		File f1 = new File(cat);
		File f2 = new File(tcat);
		String difffile = "Output/Diff.csv";
		
		compare.diff(f1, f2, difffile);
	}

}
