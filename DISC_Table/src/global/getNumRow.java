package global;

import java.sql.ResultSet;

public class getNumRow {
	public static int getNumberRows(ResultSet rs){
	    try{
	       if(rs.last()){
	          return rs.getRow();
	       } else {
	           return 0;
	       }
	    } catch (Exception e){
	       System.out.println("Error getting row count");
	       e.printStackTrace();
	    }
	    return 0;
	}
}
