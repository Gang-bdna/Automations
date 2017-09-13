package main;

import error.errorCheck;
import exception.BuildExp;
import global.Utils;

public class MainEntry {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		String file = "Input/log.txt";
//		errorCheck.errors(file);

		if(args.length==0){
		    Utils.error("Pls Specify the input file that you want to check."); 
		    throw new BuildExp("Input is empty, cannot specify the file that needs to be checked!");
		}else if(args.length==2){
//			file = args[0];
			errorCheck.errors(args[0], args[1]);
		}else if(args.length >2 || args.length ==1){
			Utils.error("# of inputs are not correct!");
			Utils.error("Pls double check your input following the following format!");
			Utils.info("USAGE: sample.jar [Path to log file] [Full Testing]");
			throw new BuildExp("# of inputs are not correct!");
		}
	}
}
