package fileOperate;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import global.Utils;

public class loadColumns {
	private static String line = "";
	private static HashMap<String, String> Columns = new HashMap<>();
	private static ArrayList<String> index = new ArrayList<>();
	
	
	//Loading Columns's title from config file
	public static HashMap<String, String> LoadKeyColmuns(String colmuns){		
		Utils.info("Start loading Columns's name which will be using for extracting the specific items from Golden results...");
		try{
			//Reader br = new InputStreamReader(new FileInputStream(colmuns), StandardCharsets.UTF_8);
			BufferedReader br = new BufferedReader(new FileReader(colmuns));
			while((line = br.readLine()) != null){
				if(line.contains("@")){
						String [] col = line.split("	");
						Columns.put(col[1], col[2]);
				}
			}
			br.close();
		}catch (IOException io){
			Utils.error("Exception happens during loading Columen list.");;
			}
		//Utils.printArrList(Features);
		Utils.info("Loading Columns's name done.");
		Utils.cout();
		return Columns;
	}
	
	//Loading index for each selected columns
	public static ArrayList<String> SpeficIndexForExtracter(ArrayList<String> features){
		for(String feat : features){
			//Utils.mark(feat);
			index.add(Columns.get(feat));
			//Utils.mark(Columns.get(feat));
		}
		return index;
	}
}
