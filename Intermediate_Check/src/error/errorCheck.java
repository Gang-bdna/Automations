package error;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import exception.BuildExp;
import global.Utils;

//|| line.contains("FAILED") || line.contains("Failed")
public class errorCheck {
	
	public static void errors(String file, String error) throws BuildExp{
		try {
			String line ="";
			BufferedReader br = new BufferedReader(new FileReader(file));

			while((line = br.readLine())!= null){
				if(line.contains("ERROR: Build")){
					Utils.cout('\n');
					Utils.error(error + " are aborted due to Build failure, stay tuned...");
					Utils.info("QA is looking into this issue with priority to resolve quickly!");
					
					throw new BuildExp('\n' +"	"+ error + " are aborted due to Build failure, stay tuned..."
							+ '\n' + "	QA is looking into this issue with priority to resolve quickly!" + '\n');
				}
				
				else if(line.contains("ERROR: Deplyment")){
					Utils.cout('\n');
					Utils.error(error + " are aborted due to Deployment failure, stay tuned...");
					Utils.info("QA is looking into this issue with priority to resolve quickly!");
					
					throw new BuildExp('\n' +"	"+ error + " are aborted due to Deployment failure, stay tuned..."
							+ '\n' + "	QA is looking into this issue with priority to resolve quickly!" + '\n');
				}
				
				else if(line.contains("RollBack-Failed")){
					Utils.cout('\n');
					Utils.error(error + " are aborted due to Roll-Back failure, stay tuned...");
					Utils.info("QA is looking into this issue with priority to resolve quickly!");
					
					throw new BuildExp('\n' +"	"+ error + " are aborted due to Snapshot Roll-Back failure, stay tuned..."
							+ '\n' + "	QA is looking into this issue with priority to resolve quickly!" + '\n');
				}
				
				else if(line.contains("Sync-Failed")){
					Utils.cout('\n');
					Utils.error(error + " are aborted due to Catalog Sync failure, stay tuned...");
					Utils.info("QA is looking into this issue with priority to resolve quickly!");
					
					throw new BuildExp('\n' +"	"+ error + " are aborted due to Catalog Sync/PatchSet applying failure, stay tuned..."
							+ '\n' + "	QA is looking into this issue with priority to resolve quickly!" + '\n');
				}
				
				else if(line.contains("Version Test : FAILED")){
					Utils.cout('\n');
					Utils.error(error + " are aborted due to PatchSet applying failure, stay tuned...");
					Utils.info("QA is looking into this issue with priority to resolve quickly!");
					
					throw new BuildExp('\n' +"	"+ error + " are aborted due to Catalog Sync/PatchSet applying failure, stay tuned..."
							+ '\n' + "	QA is looking into this issue with priority to resolve quickly!" + '\n');
				}
				
//				else if(line.toLowerCase().contains("error:") || line.toLowerCase().contains("failed")){
//					throw new BuildExp('\n' +"	Smoke tests are aborted due to Build/Deployment/Roll-Back/Catalog Sync failure, stay tuned…"
//							+ '\n' + "	QA is looking into this issue with priority to resolve quickly!" + '\n');
//				}
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Utils.cout('\n');
		Utils.info("Build, Deployment, Roll-Back and Catalog sync have completed successfully.");
		Utils.info(error + " is about to start.");
		Utils.cout('\n');
	}
}
