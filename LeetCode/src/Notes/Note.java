package Notes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import global.Utils;

public class Note {
	private Note(){
		//Convert a List to Array
		List<String> result = new ArrayList<>();
		result.toArray(new String[result.size()]); 
		
		//Binary <--> Integer
		Integer.toBinaryString(10);
		Integer.parseInt("10101",2);
		
		//Get the 1s of a bits
		Integer.bitCount(10);
		
		//Reverse a string (fast way)
		String res1 = new StringBuilder("any string").reverse().toString(); //not thread-safe
		String res2 = new StringBuffer("any string").reverse().toString();  //thread-safe
		
		//Basically this method is to return a default value whenever the value was not found 
		//using the key specified on the HashMap
		HashMap<String, String> map = new HashMap<>();
		map.getOrDefault("key", "Default Value");
		
		//check duplicate 'char'
		String test = "loveleetcode";
		test.indexOf('l'); //will return index 0
		test.lastIndexOf('l'); //will return index 4
		
		//StringBuilder
		String s = "Hello World";
		StringBuilder sb=new StringBuilder(s);
		sb.reverse();
		
	}
	
    public int islandPerimeter(int[][] grid) {
    	int result = 0;
    	int row = grid.length;
    	int col = grid[0].length;
    	
    	if(grid==null || row==0 || col==0) return result;
    	
    	for(int i=0; i<row; i++){
    		for(int j=0; j<col; j++){
    			if(grid[i][j]==1){
    				result +=4;
    				if(j<col-1 && grid[i][j+1]==1) result -=2;
    				if(i<row-1 && grid[i+1][j]==1) result -=2;
    			}
    		}
    	}
		return result;
    }
    
    public static int canWinNim(int[] nums) {
        int result = 0;
        for(int i : nums) {
            result ^= i;
        }
        return result;
    }
    
    public static int singleNumber(int[] nums) {
    	HashMap<Integer, Integer> pair = new HashMap<>();
    	for(int num : nums){
    		if(pair.containsKey(num)) pair.remove(num);
    		else pair.put(num, 1);
    	}
		for(int i : pair.keySet()){
			if(pair.get(i)==1) return i;
		}
		return 0;
    }
    
    public int findMaxConsecutiveOnes(int[] nums) {
    	int max = 0;
    	int count = 0;
    	int result = 0;
    	for(int i=0; i<nums.length; i++){
			max += i;
			count ++;
			if(max!=count){
				max = 0;
				count = 0;
				result = Math.max(max, result);
			}
    	}
    		
		return max;
        
    }
    
    public List<Integer> findDisappearedNumbers(int[] nums) {
    	int cusor = 0;
    	int nextpos = 0;
    	List<Integer> result = new ArrayList<Integer>();
    	if(nums==null) return null;
    	
    	for(int i=0; i<nums.length; i++){
    		cusor = nums[i];
    		while(cusor>0&&nums[cusor-1]>0){
    			nextpos = nums[cusor-1];
    			nums[cusor-1] = -1;
    			cusor = nextpos;
    		}
    	}
    	
    	for(int j=0; j<nums.length; j++){
    		if(nums[j]!=-1){
    			result.add(j+1);
    		}
    	}
    		
        return result;
    }
    
    public boolean detectCapitalUse(String word) {
    	String cap = word.toUpperCase();
    	String sml = word.toLowerCase();
    	String sub = word.substring(1);
    	char first = word.charAt(0);
    	if(word.equals(cap)||word.equals(sml)) return true;
    	if(first>='a' && first<='z' && word.substring(1).equals(word.substring(1).toLowerCase())) return true;
    	if(first>='A' && first<='Z' && word.substring(1).equals(word.substring(1).toLowerCase())) return true;
    	
    	return false;
    }
    
    public static char findTheDifference(String s, String t) {
    	if(t.length()==1) return t.charAt(0);
    	String s_left = s.substring(0, s.length()/2);
    	String s_right = s.substring(s.length()/2, s.length());
    	String t_left = t.substring(0, t.length()/2);
    	String t_right = t.substring(t.length()/2, t.length());
    	
    	if(s.length()==t.length()) return 0;
    	if(!s_left.equals(t_left)){
        	if(s_left.length()==1) return t_left.charAt(0);
        	else return findTheDifference(s_left, t_left);
    	}else{
    		if(s_right.length()==1){
    			if(s_right.charAt(0)==t_right.charAt(0)) return t_right.charAt(1);
    			else return t_right.charAt(0);
    		} else return findTheDifference(s_right, t_right);
    	}
    }
    
    public static int[] twoSum(int[] numbers, int target) {
    	int[] result = {0,0};
//    	int half = target/2;
//    	for(int i=0; i<numbers.length; i++){
//    		if(numbers[i]<=half){
//    			result[0] = i+1;
//    			int j=i+1;
//    			while(j<numbers.length){
//    				if(numbers[j]+numbers[i]==target){
//    					result[1]=j+1;
//    					return result;
//    				}
//    				j++;
//    			}
//    		}
//    	}
    	HashMap<Integer, Integer> index = new HashMap<>();
    	for(int i=0; i<numbers.length; i++){
    		index.put(target-numbers[i], i+1);
//    		System.out.println(index);
    	}
    	Set<Integer> set = index.keySet();
    	int j=0;
    	int half = target/2;
    	while(j<numbers.length && numbers[j]<=half){
    		if(set.contains(numbers[j])){
    			result[0] = j;
    			result[1] = index.get(numbers[j]);
//    			System.out.println(result[1]);
    			return result;
    		}j++;
    	}
        return result;
    }
    
    public static int getSum(int a, int b) {
        int sum;
    	int carry;
    	
    	if(b==0) return a;
    	
    	sum = a^b;
    	carry = (a&b)<<1;
    	
    	return getSum(sum, carry);
    }
    
    public int addDigits(int num) {
        int rest = num;
    	String s = num + "";
    	
    	return addDigits(rest);
    }
    
    public static String[] findRelativeRanks(int[] nums) {
        if(nums.length==0) return null;
        int[] nu = new int[nums.length];
        
        for(int i= 0; i<nums.length; i++){
            nu[i]=nums[i];
        }
        
        Arrays.sort(nums);
        int inx = 4;
        

                    
        String[] result = new String[nums.length];
        HashMap<Integer,String> pair = new HashMap<>();
        
        if(nums.length==1) return new String[] {"Gold Medal"};
        if(nums.length==2) {
            pair.put(nums[1], "Gold Medal");
            pair.put(nums[0], "Silver Medal");
        }
        if(nums.length==3) {
            pair.put(nums[2], "Gold Medal");
            pair.put(nums[1], "Silver Medal");
            pair.put(nums[0], "Bronze Medal");
        }
        
        if(nums.length>3){
            pair.put(nums[nums.length-1], "Gold Medal");
            pair.put(nums[nums.length-2], "Silver Medal");
            pair.put(nums[nums.length-3], "Bronze Medal");
            
        for(int i= nums.length-4; i>=0; i--){
            pair.put(nums[i], inx+"");
            inx++;
        }
    }
        
        for(int i=0; i<nu.length; i++){
            result[i] = pair.get(nu[i]);
            Utils.info(nu[i] + " <===> " + result[i]);
            
        }
        return result;
    }
    
    public static int minMoves(int[] nums) {
    	int step = 0;
    	int min = Integer.MAX_VALUE;
//    	Arrays.sort(nums);
    	for(int i=0; i<nums.length; i++){
    		step += nums[i];
    		min = Math.min(min, nums[i]);
    		//step = step + (nums[i]-nums[i-1])*(nums.length-i);
    	}
    	Utils.info(min);
		return step-(nums.length*min);  
    }
    
    public static int[] intersection(int[] nums1, int[] nums2) {
    	HashSet<Integer> res = new HashSet<>();
    	HashSet<Integer> tem = new HashSet<>();
    	for(int num : nums1){
    		tem.add(num);
    	}
    	
    	for(int n : nums2){
    		if(tem.contains(n)) res.add(n);
    	}
    	
    	int[] result = new int[res.size()];
    	int i = 0;
    	for(int r : res){
    		result[i] = r;
    		i++;
    	}
    	return result;
    }
    
    public static int firstUniqChar(String s) {
    	int index = -1;
    	HashMap<Character, Integer> map = new HashMap<>();
    	char[] cha = s.toCharArray();
    	for(char c : cha){
    		if(map.containsKey(c)){
    			map.put(c, map.get(c)+1);
    		}else{
    			map.put(c, 1);
    		}
    	}
    	
    	if(!map.containsValue(1)||s.isEmpty()) return -1;
    	for(int i=0; i<cha.length; i++){
    		if(map.get(cha[i])==1){
    			index=i;
    			break;
    		}
    	}
        return index;
    }
    
    public static int maxProfit(int[] prices) {
        int profit = 0;
        if(prices.length==0 || prices.length==1) return 0;
    	int max = prices[0];
    	int min = prices[0];
    	//profit = prices[0];
		max = Math.max(max, prices[0]);
		min = Math.min(min, prices[0]);
    	for(int i=0; i<prices.length; i++){
    		max = Math.max(max, prices[i]);
    		min = Math.min(min, prices[i]);
    		int j = i;
    		if(j<prices.length-1&&prices[i+1]>max){
    			profit = profit + prices[i+1] - min;
    			Utils.mark(profit +"--"+min);
    			max = prices[i+1];
    			min = prices[i+1];
    			//i++;
    		}
    	}
        return profit;
    }
    
    public static int titleToNumber(String s) {
        int num = 0;
        if(s.length()==0 || s.equals("")) return num;
        char[] ch = s.toCharArray();
        for(int i=ch.length-1; i>=0; i--){
            num = num + (int)Math.pow(26, i) * ((int)ch[ch.length-1-i]-64);
        }
        
        return num;
    }
    
    public static int majorityElement(int[] nums) {
    	Arrays.sort(nums);
        return nums[nums.length/2 +1];
    }
    
    public static boolean isAnagram(String s, String t) {
		ArrayList<Character> slist = new ArrayList<>();
		ArrayList<Character> tlist = new ArrayList<>();
		ArrayList<Character> temp = new ArrayList<>();
		char[] schar = s.toCharArray();
		char[] tchar = t.toCharArray();
		boolean res = false;

		if(s.equals(t)) return true;
		if(s.length()==0||t.length()==0) return false; 
		
		for(int i=0; i<schar.length; i++){
			slist.add(schar[i]);
			temp.add(schar[i]);
		}
		for(int i=0; i<tchar.length; i++){
			tlist.add(tchar[i]);
		}
		
		
		if(slist.removeAll(tlist)&&tlist.removeAll(temp)) res = true;
		return res;
    }
    
    public static int arrangeCoins(int n) {
    	return (int)(Math.sqrt(8*(long)n+1)-1)/2;
    }
    
    public static boolean containsDuplicate(int[] nums) {
		if(nums.length<2) return false;
		int index = 0;
		boolean res = false;
		Arrays.sort(nums);
		while(index<nums.length-1){
			int i=index + 1;
			if(nums[index]==nums[i]){
				res = true;
				break;
			}
			index ++;
		}
		return res;
    }
    
	static int value(char r)
	{
		if (r == 'I')
			return 1;
		if (r == 'V')
			return 5;
		if (r == 'X')
			return 10;
		if (r == 'L')
			return 50;
		if (r == 'C')
			return 100;
		if (r == 'D')
			return 500;
		if (r == 'M')
			return 1000;
	 
		return -1;
	}
	
    public static int romanToInt(String s) {
    	HashMap<Character, Integer> map = new HashMap<>();
    	map.put('I', 1);
    	map.put('V', 5);
    	map.put('X', 10);
    	map.put('L', 50);
    	map.put('C', 100);
    	map.put('D', 500);
    	map.put('M', 1000);
    	
    	char[] seq = s.toCharArray();
    	int sum = 0;
    	for(int i=0; i<seq.length-1; i++){
    		int n = map.get(seq[i]);
    		int m = map.get(seq[i+1]);
    		
    		if(n < m){
    			sum -= n;
    		}else{
    			sum += n;
    		}
    	}
        return sum+map.get(seq[seq.length-1]);
    }
    
    public static void intersect(int[] nums1, int[] nums2) {
        ArrayList<Integer> res = new ArrayList<>();
        Arrays.sort(nums1);
        Arrays.sort(nums2);
        
        int i1 = nums1.length;
        int i2 = nums2.length;
        int ind1 = 0;
        int ind2 = 0;
        while(ind1<i1 && ind2<i2){
            if(nums1[ind1]<nums2[ind2]){
            	ind1++;
            }
            else if(nums1[ind1]>nums2[ind2]){
            	ind2++;
            }
            else{
                res.add(nums1[ind1]);
                ind1++;
                ind2++;
            }
        }
        
        int [] result = new int[res.size()];
        int pos = 0;
        for(int i : res){
        	result[pos] = i;
        	pos++;
        }  
    }
    
    public static boolean checkRecord(String s) {
        if(s.isEmpty()) return false;
        if(s.indexOf('A')!=s.lastIndexOf('A')&&s.indexOf('A')!=-1) return false; 
        while(s.indexOf('L')!=-1&&s.lastIndexOf('L')!=s.indexOf('L')&&s.length()>=3){
        	s = s.substring(s.indexOf('L'), s.length());
        	Utils.mark(s);
            int l = s.indexOf('L');
            if(s.length()<3) return true;
        	if(s.charAt(l+1)=='L' && s.charAt(l+2)=='L'){
        		return false;
        	}else{
        		s = s.substring(l+1, s.length());
        		
        	}
        }
        return true;
    }
    
    public static int missingNumber(int[] nums) {
    	int res = nums.length;
    	for(int i=0; i<nums.length; i++){
    		res = res^nums[i]^i;
    	}
    	return res;
    }
    
    public static String reverseStr(String s, int k) {
    	String res="";
    	if(k==1 || s.length()<2) return s;
    	int l = s.length();
    	for(int i=1; 2*k*(i-1)<l; i++){
        	int len = s.length();
    		StringBuilder sb = new StringBuilder(s.substring(0,Math.min(k, len)));
    		res = res + sb.reverse().toString();
    		if(len<k) return res;
    		if(s.substring(k, Math.min(2*k, s.length())).isEmpty())break;
    		res = res + s.substring(k, Math.min(2*k, len));
    		Utils.mark(i+":  "+res+ " == " +s + "  ==  " + s.length());
    		s=s.substring(Math.min(2*k, len));
    	}
        return res;
    }
    
    public static long toInteger(String num){
    	char[] cha = num.toCharArray();
		long index = 1;
		long len = num.length();
		long res = 0;
		for(int i=0; i<num.length(); i++){
			res = (int) (res + (cha[i] - '0')*Math.pow(10, len-index));
			index ++;
		}
//		Utils.mark(res);
		return res;
    }
	
    public static int addStrings(String num1, String num2) {
	  
	    	return 0;
    }
}
