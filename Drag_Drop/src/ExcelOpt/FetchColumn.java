package ExcelOpt;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class FetchColumn {

    // public Workbook workbook= null;
    // public Sheet firstSheet= null;

    public String INPUT_XLS = "C:/Users/gliu/Desktop/MD1.xlsx";
//    public ArrayList<Cell> obj = new ArrayList<Cell>();
    
    public FetchColumn(String filepath) {
        INPUT_XLS = filepath;
    }

    public ArrayList<String> ReadExcel(int clm, int sheet) throws IOException {

        FileInputStream inputStream = new FileInputStream(new File(INPUT_XLS));

        Workbook workbook = new XSSFWorkbook(inputStream);

        Sheet firstSheet = workbook.getSheetAt(sheet-1);
        
        Iterator<Row> iterator = firstSheet.iterator();
        
        ArrayList<String> obj = new ArrayList<String>();

        while (iterator.hasNext()) {
            Row nextRow = iterator.next();
            String fields = null;
            try{
            	fields = nextRow.getCell(clm-1).toString();
            }catch(NullPointerException ne){
            	
            }
            
            if(!fields.startsWith("Attribute")){
            	obj.add(fields);
            }
        }
//        System.out.print(obj);
        return obj;
    }
    
    public ArrayList<String> ReadCvs() throws IOException {
    	BufferedReader br = new BufferedReader(new FileReader(new File(INPUT_XLS)));
    	ArrayList<String> obj = new ArrayList<String>();String line;
    	while ((line = br.readLine()) != null) {

    	    String[] cell = line.split(",");
    	    obj.add(cell[0]);
    	}
        return obj;
    }
}