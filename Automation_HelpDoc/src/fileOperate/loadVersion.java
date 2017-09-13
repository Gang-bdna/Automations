package fileOperate;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;

import global.Utils;

public class loadVersion {
	private static HashSet<String> ver = new HashSet<>();
	public static HashSet<String> version(String file){
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line = "";
			while((line = br.readLine())!=null && !(line = br.readLine()).contains("#")){
				ver.add(line.trim().toString());
			}
		} catch (IOException e) {
			Utils.error("Failed to loading versions from file: " + file);
		}
		return ver;
		
	}
}
