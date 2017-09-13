package fileOperate;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Hashtable;
import global.Utils;

public class TestFile {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
//		File inputs = new File(Properties.INPUTS);
//		LoadFile.readXls(inputs, Properties.OUTPUTS);
//		LoadFile.loadIpt(Properties.OUTPUTS);
		Hashtable<String, String> st = new Hashtable<>();
		String log = "inputs/BDNA.log";
		String mor = "inputs/mor.log";
		
		st = WriteExcle.FilterLog(log);
		Utils.cout(st);
		try{
			WriteExcle.peakData(mor, st);
		}catch(FileNotFoundException fe){
			Utils.warning("Please check the input path, and make sure the output file is not in use!");
		}
	}
}