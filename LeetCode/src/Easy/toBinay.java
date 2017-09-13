package Easy;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import global.Utils;

public class toBinay {
	public static String intBinary(int test){
		//String binaryString = Integer.toBinaryString(test);
		return Integer.toBinaryString(test);
	}
	
	public static int bitToInt(String b){
		return Integer.parseInt(b,2);
	}
	
	public static String complement(String bin){
		String b = "";
		char[] bt = bin.toCharArray();
		for(int i=0; i<bt.length; i++){
			if(bt[i]=='0'){
				b=b+"1";
			} 
			if(bt[i]=='1'){
				b = b+"0";
			}
		}
		return b;
	}
	
	 public static int findComplement(int num) 
	    {
	        int i = 0;
	        int j = 0;
	        
	        while (i < num)
	        {
	            i += Math.pow(2, j);
	            j++;
	        }
	        
	        return i - num;
	    }
	 public static int maxProfit(int[] prices) {
		 int min = Integer.MAX_VALUE;
		 int max = 0;
		 for(int i=0; i<prices.length; i++){
			 if(prices[i]<min){
				 min = prices[i];
			 }else{
				 max = Math.max(max, prices[i]-min);
			 }
		 }
		return max;
	        
	    }
	 
	 public static double findMaxAverage(int[] nums, int k){

		 int sum = 0;
		 int length = nums.length;
		 if(k>nums.length) k = nums.length;
		 for(int i=0; i<k; i++){
			 sum+=nums[i];
		 }
		 
		 int left=0;
		 int right=k;
		 int max = sum;
		 while(right<length){
			 sum = sum - nums[left] + nums[right];
			 left++;
			 right++;
			 max = Math.max(max, sum);
		 }
		return (double)max/k;
	        
	    }
	 
	 public static int findNthDigit(int n) {
		 String seq="";
		 for(int i=1; i<=n; i++){
			 seq = seq + i;
		 }
		 String digit = seq.substring(n-1, n);
		Utils.info(digit);
		return Integer.parseInt(digit);
	        
	    }
	 
	 public static int findNthDigit2(int n) {
		 int length = 1;
		 int index = 1;
		 int start = 0;
		 while(n>length){
			 length = length*10;
			 index++;
		 }
		 
		 int total=0;
		 for(int i=1; i<index-1; i++){
			 total = (int) (total + 9*i*Math.pow(10, i-1));
		 }
		 
		 Utils.info(total);
		return Integer.parseInt("0");
	        
	    }
	 
	 public static boolean isUgly(int num) {
		 HashSet<Integer> prime = new HashSet<>();
		 prime.add(2);
		 prime.add(3);
		 prime.add(5);
		 prime.add(1);
		 
		 if(num==1) return true;
		 int temp = (int) Math.sqrt(num);
		 //temp = Math.min(temp, 5);
		 for(int d=2; d<=temp;d++){
			 if(num!=1){
				 while(num%d==0){
					 prime.add(d);
					 num/=d;
					 Utils.info(d);
				 }
			 }
		 }
		 Utils.info(num);
		 prime.add(num);
		 if(prime.size()==4){
			 return true;
		 }
		return false;  
	 }
	 
	 public static int findPairs(int[] nums, int k){
		 Set<Integer> base = new HashSet<>();
		 int len = nums.length;
		 int count = 0;

		 for(int i=0; i<len; i++){
			 int temp = nums[i];
			 Utils.info(temp);
			 base.add(temp);
		 }
		 
		 for(int j=0; j<len; j++){
			 if(base.contains(nums[j]-k)){
				 count++;
			 }
		 }
		 Utils.mark(count);
		return count;
	    }
	 
	 public static boolean isDived(int up, int lower, long pal){
		 int tem = up;
		 boolean isD = false;
		 while(tem>=lower){
			 if(pal%tem==0){
				 if((pal/tem)<=up){
					 isD = true;
					 Utils.mark(tem);
					 break;
				 }
			 }
			 else{
				 tem --;
			 }
		 }
		 return isD;
	 }
	 
	 public static boolean isPalindrome(long pal){
		 String p = pal+"";
		 StringBuilder rev = new StringBuilder().append(pal).reverse();
		 if(p.contains(rev)){
			 return true;
		 }else return false;
	 }
	 public static int largestPalindrome(int n) {
		 if(n==1) return 9;
		 int uplevel = (int) Math.pow(10, n) -1;
		 int lowerlevel = (int) Math.pow(10, n-1);
		 long maxPal = 0;
		 long max = 0;
		 
		 int tem = 9* (int) Math.pow(10, n-1);
		 for(int i=uplevel; i>=tem; i--){
			 for(int j=uplevel; j>=lowerlevel; j--){
				 maxPal = i*j;
				 if(isPalindrome(maxPal)){
					 max = Math.max(max, maxPal);
//					 Utils.info(max);
				 }
			 }
		 }
		return (int) (max%1337);
	        
	    }
}
