package fileOperate;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

import global.Utils;

public class writeDiff {
	private static FileWriter docWrite;
	private static BufferedWriter bw;
	private static String line="";
	private static HashSet<String> missID = new HashSet<>();
	
	public static void RecordDiff(HashSet<String> did, ArrayList<String> index, String difffile, String fullRecord){
		try {
			docWrite = new FileWriter(difffile);
			bw = new BufferedWriter(docWrite); 
			bw.append("PRODUCT").append("	").append("ID").append("	");
			bw.append("HIDDEN").append("	").append("HOSTID").append("	");
			bw.append("SUITE_ID").append("	").append("GROUP_RID").append("	");
			bw.append("PROD_RID").append("	").append("EDITIONS_RID").append("	");
			bw.append("EDITION").append("	").append("VERSIONS_RID").append("	").append("VERGROUPS_RID").append("	"); 
			bw.append("PATCH").append("	").append("SUBVERSION").append("	");
			bw.append("VER_COEXISTS").append("	").append("EDI_COEXISTS").append("	");
			bw.append("VERGRP_MAX").append("	").append("VORD").append("	");
			bw.append("EORD").append("	").append("MERGED").append("	");
			bw.append("RELS_RID").append("	").append("INTERNAL_NAME").append("	");
			bw.append("INTERNAL_VERSION").append("	").append("INTERNAL_PUB").append("	");
			bw.append("ORIGINATE_FROM");
			bw.newLine();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//Loading full actual results
		try{
			BufferedReader br = new BufferedReader(new FileReader(fullRecord));
			while((line = br.readLine()) != null){
				String [] elements = line.split("	");
				//Utils.mark(did.toString());
				if(did.contains(elements[Integer.parseInt(index.get(0))])){
					//Utils.mark(elements[Integer.parseInt(index.get(0))]);
					missID.add(elements[Integer.parseInt(index.get(0))]);
					for (String s : elements){
						bw.append(s).append("	");							
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
			Utils.cout('\n');
		}
	}
}
