package fileOperate;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

import Main.ded;
import global.Utils;

public class writeDiff {
	private static FileWriter docWrite;
	private static BufferedWriter bw;
	private static String line="";
	private static HashSet<String> missID = new HashSet<>();
	
	public static void RecordDiff(HashSet<String> did, ArrayList<String> index, String locl, String dbt){
		String difffile = ded.location + "/HardCode_" +dbt + "_Diff.csv";
//		String difffile = ded.result;
		try {
			docWrite = new FileWriter(difffile);
			bw = new BufferedWriter(docWrite); 
			
		} catch (IOException e) {
			Utils.error("Initialize "+difffile+" file failed");
		}
		
		//Loading full actual results
		String fullRecord = locl + "/" + dbt + ".csv";
		try{
			BufferedReader br = new BufferedReader(new FileReader(fullRecord));
			while((line = br.readLine()) != null){
				String [] elements = line.split(",");
				//Utils.mark(did.toString());
				if(did.contains(elements[Integer.parseInt(index.get(0))])){
					//Utils.mark(elements[Integer.parseInt(index.get(0))]);
					missID.add(elements[Integer.parseInt(index.get(0))]);
					for (String s : elements){
						bw.append(s).append(",");							
					}
					bw.append('\n');
				}
			}
			
			did.removeAll(missID);
			if(!did.isEmpty()){
				bw.append('\n').append('\n').append('\n');
				bw.append("ATTENTION: ID " + did.toString() + " is MISSING from Actual results file while comparing with GOLDEN results");
				Utils.warning("ATTENTION: id " + did.toString() + " is MISSING from Actual results file while comparing with GOLDEN results");
			}
			
			//Utils.mark(did.toString());
			if(!missID.isEmpty()){
				Utils.info("The detail of differences added into " + difffile);
			}
			br.close();
			bw.close();
		}catch (IOException io){
			Utils.error("Exception happened during writing diff into " + difffile);
			Utils.cout();
		}
	}
	
	public static void pass(String locl, String dbt){
//		String difffile = ded.result;
		String difffile = ded.location + "/HardCode_" +dbt + "_Diff.csv";
		try {
			docWrite = new FileWriter(difffile);
			bw = new BufferedWriter(docWrite); 
			bw.append("Results based on the given features are matching the given Golden results!").append('\n');
			bw.append("Results: Passed");
			bw.flush();
			bw.close();
		} catch (IOException e) {
			
		}		
	}
	
	public static void writeRes(HashSet<String> hostID){
		String difffile = ded.result;
		HashSet<String> totalMis = compareActGolden.getTotalNumMisId();
		HashSet<String> detailMis = compareActGolden.getDetailMisID();
		HashSet<String> hostIdMis = compareActGolden.getMissID();
		
		try {
			docWrite = new FileWriter(difffile);
			bw = new BufferedWriter(docWrite); 
			bw.append("Host ID").append(",").append("Results").append(",").append("Reasons");
			bw.newLine();
			bw.flush();
			
			if(!hostID.isEmpty()){
				for(String id : hostID){
					if((!totalMis.isEmpty()) && totalMis.contains(id)){
						bw.append(id).append(",").append("Failed").append(",").append("The total host # uder current ID are not matching");
						bw.newLine();
						bw.flush();
					}
					else if((!detailMis.isEmpty()) && detailMis.contains(id)){
						bw.append(id).append(",").append("Failed").append(",").append("The host details under the ID are not matching");
						bw.newLine();
						bw.flush();
					}
					else if((!hostIdMis.isEmpty()) && hostIdMis.contains(id)){
						bw.append(id).append(",").append("Failed").append(",").append("The ID is missing either from GOLDEN or Query results");
						bw.newLine();
						bw.flush();
					}else{
						bw.append(id).append(",").append("Passed").append(",").append("Looks Good");
						bw.newLine();
						bw.flush();
					}
				}
				bw.close();
			}
			
		} catch (IOException e) {
			Utils.error("Failed to write results into file!");
		}		
	}
}
