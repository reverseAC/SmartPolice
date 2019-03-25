package cn.smartpolice.test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

public class javatest {

	public static void main(String[] args) {
//		
//		String string = "98756";
//		byte[] cha = string.getBytes();
//		String s = Base64.encode(cha);
//		String s = "2017-08-02 19:27:44.0";
//		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//小写的mm表示的是分钟    
//		java.util.Date date;
//		try {
//			date = sdf.parse(s);
//
//			System.out.println(date);
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}  
//	
		
//	}
//	public static String reverse(String originStr) {
//        if(originStr == null || originStr.length() <= 1) 
//            return originStr;
//        return reverse(originStr.substring(1)) + originStr.charAt(0);
//    }
		long l = 1501673149000l;
		Date d = new Date(l);
		System.out.println(d);
}
}
