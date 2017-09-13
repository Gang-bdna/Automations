package ExcelOpt;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class WritetToReport {
	private static FileWriter docWrite = null;
	private static BufferedWriter bw = null;
	private static int len_g;
	private static int len_u;
	private static int max_len;
	private static final String NA = "N/A";
	public static void Record(ArrayList<String> golden, ArrayList<String> ui, String file) throws IOException{
		len_g = golden.size();
		len_u = ui.size();
		max_len = Math.max(len_g, len_u);

		try {
			//To-do
			docWrite = new FileWriter(file);
			bw = new BufferedWriter(docWrite); 
			bw.append("RequriementDoc").append("	");
			bw.append("UI Behaviors").append('\n');
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		for(int i=0; i<max_len; i++){
			if(len_g<=i){
				bw.append(NA).append("	");
			}else {
				bw.append(golden.get(i)).append("	");
			}
			
			if(len_u<=i){
				bw.append(NA).append('\n');
			}else {
				bw.append(ui.get(i)).append('\n');
			}
		}
		bw.flush();
		bw.close();
	}
}
