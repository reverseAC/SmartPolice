package cn.smartpolice.test;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;
import java.util.Scanner;

import javassist.compiler.ast.NewExpr;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;


public class MD5andBASE64Test {
	public static void main(String[] args) {
		/*String string = "hello MD5";
		byte[] md5 = null;
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(string.getBytes());
			md5 = md.digest();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("����ǰ:"+string+" \n���ܺ�:"+md5+"\n");
		
		String string2 = "hello BASE64";
		String base64 = new BASE64Encoder().encode(string2.getBytes());
		System.out.println("����ǰ:"+string2+" \n���ܺ�:"+base64+"\n");
		
		BASE64Decoder base64decode = new BASE64Decoder();
		try {
			String str = new String(base64decode.decodeBuffer(base64));
			System.out.println("����ǰ:"+base64+" \n���ܺ�:"+str+"\n");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
		try {
			Random rando = new Random();
			int random = rando.nextInt(99999) % 90000 + 10000; // ����һ�������
			System.out.print("�����:"+random);
			byte [] str = String.valueOf(random).getBytes();
			Scanner scan = new Scanner(System.in); 
			System.out.println("������һ��Ӣ���ַ���������ַ���:"); 
			String password = scan.nextLine();   //��ȡ�û����� 
			byte[] array = password.getBytes(); //��ȡ�ַ����� a
			for(int i=0;i<array.length;i++) //�����ַ����� 
			{ 
			    str[i]=(byte)(array[i]^str[i]); //��ÿ������Ԫ�ؽ���������� 
			    System.out.println("���ܵ�ÿ���ַ�:"+array[i]); 
			}  
			System.out.println("���ܻ���ܽ������:"); 
			System.out.println(new String(array));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	  } 
}
