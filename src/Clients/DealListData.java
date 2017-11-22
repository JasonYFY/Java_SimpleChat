package Clients;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class DealListData {
	public static ArrayList<String> deal(String data){
		ArrayList<String> alist = new ArrayList<>();
		data = data.replace("\n", "");
		Pattern pat = Pattern.compile("\\[(.+)\\]");
		Matcher mat = pat.matcher(data);
		if(mat.find()){
			data = mat.group(1);
			Pattern pat1 = Pattern.compile("\\{([^\\}]+)\\}");
			Matcher mat1 = pat1.matcher(data);
			while( mat1.find() ){
				String ps =mat1.group(1);
				System.out.println(ps);
				alist.add(ps);
			}
		}
		return alist;
	}
//	public static void main(String[] args) {
//		deal("list:[\n{user:jason,nickname:Jason,img:1,ip:192.168.0.237,udpport:63408},\n]\n}");
//	}

}
