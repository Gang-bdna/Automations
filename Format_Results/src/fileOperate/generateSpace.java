package fileOperate;

public class generateSpace {
	private static String spc = "";
	public static String Space(String title, int len){
		int title_len = title.length();
//Utils.mark(title + "=="+ title_len + "==new==" + (len-title_len));
		spc = "";


		if(title_len<=len){
			for(int index=0; index<(len-title_len); index++){
				spc = spc + "&nbsp;";
			}
			
		}
//		Utils.info(spc);
		return spc;

	}
}
