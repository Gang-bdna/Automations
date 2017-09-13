package Easy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class keyBoard {
    public static String[] findWords(String[] words) {
        String[] strs = {"QWERTYUIOP","ASDFGHJKL","ZXCVBNM"};
        HashMap<Character, Integer> keyBoard = new HashMap<>();
        List<String> result = new ArrayList<>();
        
        for(int i=0; i<strs.length; i++){
            char[] ch = strs[i].toCharArray();
            for(char c : ch){
                keyBoard.put(c, i+1);
            }
        }
        
        for(String word : words){
        	if(ispartial(word, keyBoard)){
        		result.add(word);
        	}
        }
        return result.toArray(new String[result.size()]); 
    }
    public static boolean ispartial(String world, HashMap<Character, Integer> kb){
    	
    	char[] w = world.toUpperCase().toCharArray();
    	char c = world.toUpperCase().charAt(0);
    	int index = kb.get(c);
    	
    	for(char r : w){
    		if(kb.get(r)!=index) return false;
    	}
		return true;
    }
    
}
