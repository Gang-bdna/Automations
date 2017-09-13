package Main;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import Easy.toBinay;
import Notes.Note;
import global.Utils;

public class Execute {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
//		int temp = 10;
//		String bin = toBinay.intBinary(temp);
//		String cpl = toBinay.complement(bin);
//		int num = toBinay.bitToInt(cpl);
//		
//		//System.out.println(~num);
//		System.out.println(((Integer.highestOneBit(temp) << 1)-1)&(~temp));
//		System.out.println(temp + " to Binary: " + bin);
//		System.out.println(bin + " to Complement: " + cpl);
//		System.out.println(cpl + " to Number: " + num);
//		System.out.println(toBinay.findComplement(temp));
//		String[] test1 = {"a","b","c","d"};
//		String[] test2 = {"a","b","c","d","e"};
//		int t = 98;
//		String test1 = "abcd";
//		String test2 = "abcde";
//		
//		String test4 = "abcd";
//		char a = 'a';
//		char b = 'b';
//		char c = 'c';
//		int sum1 = (int)(a+b+c);
//		int sum2 = (int)(a+c);
////		System.out.println((char)(t));
//		System.out.println((char)(sum1-sum2));
//		System.out.println(Note.findTheDifference(test3, test4));

		int[] test1 = {1,2,2,3,2};
		int[] test2 = {7, 3, 5, 4, 8, 10};
		int[] test3 = {1, 3, 1, 5, 4};
		//String s = "LLL";
//		Utils.mark(Math.pow(2, 31) - 1804289383);
		//Note.intersect(test1, test2);
		//System.out.println(s = s.substring(0,s.indexOf('L')) + s.substring(s.indexOf('L')+1, s.length()));

		String s = "1230456";
//		String empty = "";
//		String next = "Switch line";
//		StringBuilder sb=new StringBuilder(s);
//		sb.append(empty).append(next);
//		//sb.reverse();
//		Utils.mark(Math.pow(2, 3));
//		Utils.mark(s.substring(3,50));
		//System.out.println(Note.reverseStr(s, 2));
//		Utils.info(s.indexOf('P'));

//		System.out.println(Note.addStrings("913259244", "71103343"));
//		Note.addStrings("913259244", "71103343");
		
		//System.out.println(Note.isAnagram("car", "rat"));
		//System.out.println(Integer.parseInt("12",26));
		
//		long etime = System.currentTimeMillis();
//		Utils.mark(etime);
//		Thread.sleep(12345);
//		long time = System.currentTimeMillis();
//		Utils.mark(time);
//		toBinay.findNthDigit2(1000000);
		int b = 8;
		
		if(toBinay.isPalindrome(9009)) {
			Utils.info("is pal");
		}
		toBinay.largestPalindrome(b);
		
//		if(toBinay.isPalindrome(2653)) {
//			Utils.info("is pal");
//		}

	}

}
