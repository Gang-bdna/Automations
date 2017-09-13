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
    /* File loading properties. */
    public static final String CONFIG_TAG = "<Config>";
    public static final String TESTCASE_TAG   = "<TestCases>";
    public static final String CONFIG_END = "</Config>";
    public static final String TESTCASE_END = "</TestCases>";
        
    /* Structures. */
	public static Hashtable<String, Hashtable<String, ArrayList<ArrayList<String>>>> cubes = new Hashtable<>();
	public static HashSet<String> ID = new HashSet<>();
	public static HashSet<String> CUBES = new HashSet<String>();
}
/* End of Properties.java */