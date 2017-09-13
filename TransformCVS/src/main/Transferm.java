package main;

import java.util.HashMap;

import FileOper.OverWriteOF;
import Loading.loadTestCaseConfig;
import global.Utils;

public class Transferm {

	public static String Result = "Input";
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		long starttime = System.currentTimeMillis();
		String conf = "Input/TestCases.config";
		Result = args[0];
//		Result = "Result";
		
		loadTestCaseConfig.LoadConfig(conf);
		HashMap<String, String> OD = loadTestCaseConfig.getODPair();
		HashMap<String, Integer> TC = loadTestCaseConfig.getTCPair();
		
		OverWriteOF.transfer(OD, TC, Result);
		Utils.info("Result transfermation done!");
	}

}
