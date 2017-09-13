package ExcelOpt;

import java.util.ArrayList;

public class CvsCompare {
	private static ArrayList<String> file1 = new ArrayList<String>();
	private static ArrayList<String> file2 = new ArrayList<String>();
	private static ArrayList<String> temp = new ArrayList<String>();
	
	public CvsCompare(ArrayList<String> file1, ArrayList<String> file2, ArrayList<String> temp) {
		// TODO Auto-generated constructor stub
		this.file1 = file1;
		this.file2 = file2;
		this.temp = temp;
	}
	public void listComp(){		
		
		file1.removeAll( file2 );
		file2.removeAll( temp );

	    System.out.println( file1 );
	    System.out.println( file2 );
	}
	public static ArrayList<String> getGolden(){
		return file1;
	}
	public static ArrayList<String> getUi(){
		return file2;
	}
	public static ArrayList<String> gettemp(){
		return temp;
	}
}
