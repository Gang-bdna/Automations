package fileOperate;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.WebDriver;

import main.User_Console;
import global.Utils;

/**
 * Author : Gang Liu	
 * Created: Jan. 22, 2016
 */

public class CvsFileWriter {	
	private static FileWriter docWrite = null;
//	private static FileWriter test = null;
	static WebDriver driver = User_Console.driver;	
//	public static Document doc = ParseHtml.Doc(driver);
//	public static Document doc = ParseHtml.Doc();
//	public static void ParseTable(WebDriver driver, String report) throws IOException{
	public static void ParseTable(Document doc, String report) throws IOException{
//		Document doc = ParseHtml.Doc();
		try {
			docWrite = new FileWriter(report);
//			docWrite = new FileWriter("test.txt");
		} catch (IOException e) {
			e.printStackTrace();
		}
//		Utils.cout(doc);
		BufferedWriter bw = new BufferedWriter(docWrite); 
//		test.write(doc.body());
		long start = System.currentTimeMillis(); 
		try{
			Elements row_headers = doc.getElementsByClass("pivotTableRowLabelHeaderContainer").select("table").select("tbody");
			Elements row_data = doc.getElementsByClass("pivotTableRowLabelSection").select("table").select("tbody").select("tr");
			Elements col_data = doc.getElementsByClass("pivotTableDataSection").select("table").select("tbody").select("tr");
			final int count_col = row_headers.select("td").size();
			final int count_row = row_data.size();
			final int count_cln = col_data.size();
			final int cl = row_headers.select("tr").select("td").size();
//			Utils.mark(doc.html());
//			test.write(doc.html().trim());
			
			//TO-DO
			ArrayList<Elements> ele = new ArrayList<Elements>();
			Elements headers = doc.getElementsByClass("pivotTableRowLabelHeaderContainer").select("table").select("tbody")
					.get(0).getElementsByAttributeValue("valign", "top");
			ele.add(headers);
			
			try{
				Elements column = doc.getElementsByClass("pivotTableColumnHeaderSection").select("table").select("tbody")
						.get(0).getElementsByAttributeValue("valign", "top");
				ele.add(column);
				
				int diff = Math.abs(column.select("tr").size() - headers.select("tr").size());
				
				//print column headers
				for(int temp=0; temp<diff; temp++){
					int sp = count_col;
					Elements e = ele.get(1).get(temp).select("td");
					for(Element td : e){
						 while(sp>0){
							 bw.append("	");
							 --sp;
						 }
						 bw.append(td.text()).append("	");	 
						 if(!(td.attr("colspan").isEmpty())){
							 int space = Integer.parseInt(td.attr("colspan"));
							 while(space>1){
								 bw.append("	");
								 space --;
							 }
						 }

					}bw.append('\n');
				}
			}catch(IndexOutOfBoundsException index){
				Utils.warning("No Column attributes added.");
			}
			
			
			
			//print rows headers (left part)
			for(Element row : ele.get(0).get(0).select("td")){
				bw.append(row.text()).append("	");
			}
			
			//print measures headers (right part)
			Elements measure_headers = doc.getElementsByClass("ZONE_measures");
			if(measure_headers.isEmpty()){
				try{
					for(Element cols : ele.get(1).last().select("td")){
						bw.append(cols.text()).append("	");
					}
				}catch(IndexOutOfBoundsException id){
					Utils.warning("No Measures Attributes Added!");
				}
			}else{
				for(Element measureHeader:measure_headers.select("td")){
					bw.append(measureHeader.text().trim()).append("	");
				}
			}
			bw.append('\n');

			//Print table data			
			int max = Math.max(count_row, count_cln);
			for(int k=0; k<max; k++){
				
				try{
					Elements tds = row_data.get(k).select("td");
					for(Element e : tds){
						int colmn_size = cl;
						
						if(tds.size()==colmn_size){
							bw.append(e.text().trim()).append("	");
						}else{
							while(tds.size()<colmn_size){
								bw.append("	");
								-- colmn_size ;
							}
							bw.append(e.text().trim()).append(" ");
						}
					}
					
					//using try-catch to print data within/without Column items
					try{
						if(tds.size()==cl){
							for(Element e : col_data.get(k).select("td")){
								bw.append(e.text()).append("	"); 
							}
						}else{
							for(Element e : col_data.get(k).select("td")){
								bw.append("	").append(e.text()); 
							}
						}
					}catch(IndexOutOfBoundsException id){
						id.getMessage();
					}
				}catch(IndexOutOfBoundsException ie){
					for(Element e : col_data.get(k).select("td")){
						bw.append(e.text()).append("	");  
					}
				}
				bw.append('\n');
			}

		}catch(IOException np){
			Utils.warning("Null Pointer!");
		}finally{
			Utils.elapsedTime(start, "  ===>Crawling Data completed:");
			bw.close();
		}
//		bw.close();
	}
}

