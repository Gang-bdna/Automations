package global;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;

/**
 * Author : Gang Liu
 * Created: Feb. 10, 2016
 */

public class Prop
{
    /* Pre-defined Login Info. */
    public static final String URL = "http://vm275w/bdna/ux/index";
    public static final String USERNAME = "testuser_001	";
    public static final String PASSWORD = "Simple.0";
    
    /* Directories and files. */
    public static final String INPUTS = "C:/Users/gliu/workspace/Automation_Testing/inputs/inputs.xlsx";
    public static final String OUTPUTS = "C:/Users/gliu/workspace/Automation_Testing/outputs/ouputs.txt";
    public static final String CDATA = "C:/Users/gliu/workspace/Automation_Testing/outputs/";
//    public static final String FILE = "C:/Users/gliu/workspace/Automation_Testing/outputs/Dimension.cvs";

    /* File loading properties. */
    public static final String CONFIG_TAG = "<Config>";
    public static final String TESTCASE_TAG   = "<TestCases>";
    public static final String CONFIG_END = "</Config>";
    public static final String TESTCASE_END = "</TestCases>";
    
    /* For Email info*/
    public static final String emailMsgTxt      = "Automation testing is done! Results has been attached.";
    public static final String emailSubjectTxt  = "Automation Testing Results";
    public static final String emailFromAddress = "gliu@bdna.com";
    public static final String[] emailList = {"gliu@bdna.com"};
    
    public static final String SMTP_HOST_NAME = "mail.bdna.com";
    public static final String SMTP_AUTH_USER = "gliu@bdna.com";
    public static final String SMTP_AUTH_PWD  = "qazwsx123";
    
    /* Structures. */
	public static Hashtable<String, String> login = new Hashtable<>();
	public static Hashtable<String, Hashtable<String, ArrayList<ArrayList<String>>>> cubes = new Hashtable<>();
	public static HashSet<String> ID = new HashSet<>();
	public static HashSet<String> CUBES = new HashSet<String>();
}
/* End of Properties.java */