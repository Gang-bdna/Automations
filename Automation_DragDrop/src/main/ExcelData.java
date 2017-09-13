package main;

import java.io.IOException;

import ExcelOpt.CvsCompare;
import ExcelOpt.FetchColumn;
import ExcelOpt.WritetToReport;

public class ExcelData {
	private static String recordFile = "MD_AutoRep.cvs";
	private static String INPUT_XLS = "C:/Users/gliu/Desktop/MD/MD1.xlsx";
	private static String UI_CVS = "C:/Users/gliu/workspace/Automation_Testing/MD.cvs";
	private static final int clm = 4;
	private static final int sheet = 1;
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		FetchColumn golden = new FetchColumn(INPUT_XLS);
		FetchColumn ui = new FetchColumn(UI_CVS);

		CvsCompare cc = new CvsCompare(golden.ReadExcel(clm, sheet), ui.ReadCvs(), golden.ReadExcel(clm, sheet));
		cc.listComp();
		
		WritetToReport wtr = new WritetToReport();
		wtr.Record(CvsCompare.getGolden(), CvsCompare.getUi(), recordFile);
	}
}
