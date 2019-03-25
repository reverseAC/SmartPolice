package cn.smartpolice.protocol;

import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.Security;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.mina.core.buffer.IoBuffer;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import cn.smartpolice.tools.JsonAnalysis;
import cn.smartpolice.workbean.PacketInfo;
import cn.smartpolice.workbean.SysInfo;
import cn.smartpolice.workbean.UserNode;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
/**
 * @author 刘培煊
 *cmd=8
 *加密协议
 */

public class ProtocolEncryption extends ProtocolBase{
	String user;//账号
	static int encry;//1 XOR；2 DES；4 IDEA；0取消加密 加密方法可以叠加，让对方选择一种
	int num;//临时密码组个数（和下面选项在Encry>0有效)
	String cipher;//临时密码组(NUMBER：PASSWORD)
	String number;//密码编号
	static String password;//密码
	static int keyseq;
	int errorPktState;//标记返回错误报文类型
	JSONObject json0 = new JSONObject();//json对象
	JSONObject json1 = new JSONObject();//json对象
	@Override
	void ParsePktProto(PacketInfo packetInfo) {
		System.out.println("报文解析");
		super.revPacket = packetInfo;
		keyseq=packetInfo.getKeyseq();
		StringBuffer stringBuffer = new StringBuffer();
		for (int i = revPacket.getDatapos(); i < revPacket.getMessage().length; i++) {
			stringBuffer.append((char) revPacket.getMessage()[i]);
				}
		
		String datas = stringBuffer.toString();
		JsonAnalysis jsonAnalysis = new JsonAnalysis();
		//获取data的数据
		String data = jsonAnalysis.getValue(datas, "DATA");
		//取的个对应属性的值
		user = jsonAnalysis.getValue(data, "USER");
		encry = Integer.parseInt(jsonAnalysis.getValue(data, "ENCRY"));
		num = Integer.parseInt(jsonAnalysis.getValue(data, "NUM"));
		cipher = jsonAnalysis.getValue(data, "CIPHER");
		//将字符串转为json数组
		JSONArray ciphers= JSONArray.fromObject(cipher); // 把字符串转成 JSONArray  对象
		//根据keyseq给选择password
		if(num>0){
			for(int i=0;i<ciphers.size();i++){
				//获取数组中的元素
				 JSONObject job = ciphers.getJSONObject(i);
				 //获取密码编号
				 number=(String)job.get("NUMBER");
				 //若密码编号和keyseq相同，就选用次密码
				 if(keyseq==Integer.parseInt(number)){
					 password=(String)job.get("PASSWORD");
				 }
			}
		}
		//获取加密方式
		encry=this.getEncry(encry);
		this.ExecProto();
	}
	public int getEncry(int encry){
		if ((encry & 0x01) == 0) { //0 第0位 不为1
			if((encry & 0x02)==0){//1 第1位 不为1
				if((encry & 0x04)==0){//2第2位 不为1
					
				}else{
					encry=4;
				}
			}else{
				encry=2;
			}
		}else{
			encry=1;
		}
		return encry;
	}
	
	public static  String EncrySend(String data) throws Exception{
		String result="";
		if(encry==1){
				result=EncryByXOR(data, password);
			}
			if(encry==2){
				//DES加密算法  其key必须大于8个字节
				if(password.getBytes().length>=8)
					result=EncryByDES(data, password);
			}
			if(encry==4){
				result=EncryByIDEA(data, password);
			}
			if(encry==0){
				result=data;
			}
	
		return result;
	}
	
	//用XOR密钥加密 enc为加密内容 key为密钥
	public static String EncryByXOR(String enc,String key){
		Charset charset = Charset.forName("UTF-8");  
		byte[] keyBytes = key.getBytes(charset);  
		byte[] b = enc.getBytes(charset);  
        for(int i=0,size=b.length;i<size;i++){  
            for(byte keyBytes0:keyBytes){  
                b[i] = (byte) (b[i]^keyBytes0);  
            }  
        }  
        return new String(b);  
	}
	
	//用DES密钥加密 data为加密内容 key为密钥
		public static String EncryByDES(String data0,String key0)throws Exception{
			byte[] data=data0.getBytes();
	    	byte[] key=key0.getBytes();
	        // 生成一个可信任的随机数源
	        SecureRandom sr = new SecureRandom();
	        // 从原始密钥数据创建DESKeySpec对象
	        DESKeySpec dks = new DESKeySpec(key);
	        // 创建一个密钥工厂，然后用它把DESKeySpec转换成SecretKey对象
	        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
	        SecretKey securekey = keyFactory.generateSecret(dks);
	        // Cipher对象实际完成加密操作
	        Cipher cipher = Cipher.getInstance("DES");
	        // 用密钥初始化Cipher对象
	        cipher.init(Cipher.ENCRYPT_MODE, securekey, sr);
	        return cipher.doFinal(data).toString();
		}
		public static void main(String[] args) throws Exception {
			byte[] s = EncryByDES("123456789123456789","abcdefgh").getBytes();
			for(byte y:s){
				System.out.print(y+" ");
			}
			
		}
		
		//用IDEA密钥加密 enc为加密内容 key为密钥
		public static String EncryByIDEA(String data,String key) throws NoSuchAlgorithmException,
			NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException{
			 String result = null;
	    	 //加入bouncyCastle支持
	        Security.addProvider(new BouncyCastleProvider());
	        //还原密钥
	        SecretKey k=new SecretKeySpec(key.getBytes(),"IDEA");
	        //实例化
	        Cipher cipher=Cipher.getInstance("IDEA/ECB/ISO10126Padding");
	        //初始化，设置为加密模式
	        cipher.init(Cipher.ENCRYPT_MODE, k);
	        //执行操作
	        byte[] data_en =cipher.doFinal(data.getBytes());
	        result = Base64.encodeBase64String(data_en);
	       return result;
		}

	@Override
	void ExecProto() {
		//找到节点
		UserNode userNode = null;
		// 通过报文sort判断是dev还是app
		if (revPacket.getSort() == ConstParam.SORT_2) {
			userNode = SysInfo.getInstance().getDevNodeById(revPacket.getSid());
		}
		if (revPacket.getSort() == ConstParam.SORT_0) {
			userNode = SysInfo.getInstance().getAppNodeById(revPacket.getSid());
		}
		
		if (revPacket.getType() == ConstParam.TYPE_1){
			if(encry==0||encry==1||encry==2||encry==4){
				if(!(encry==2&&password.getBytes().length<8)){
					byte[] packet = PackPkt(2);//发送正确加密
					SendPkt(packet);
				}else{
					errorPktState=1;//如果用des加密  而且key小于8个字节 则发送错误
					byte[]  packet = PackPkt(1);
					SendPkt(packet);
					userNode.setLastPacketInfo(packet);// 更新缓存
				}
			}else{//请求的加密方法不存在
				errorPktState=0;
				byte[] packet = PackPkt(1);
				SendPkt(packet);
				userNode.setLastPacketInfo(packet);// 更新缓存
			}
		}
	}
	
	@Override
	byte[] PackPkt(int i) {
		PacketMsg msg = new PacketMsg();
		msg.setCmd(ConstParam.CMD_8);
		msg.setOpt(ConstParam.OPT_16);
		msg.setAck(revPacket.getSeq());//返回报文的ack与发送过来的报文seq一致
		msg.setSid(ConstParam.SERVER_ID);
		String packetBody = null;
		switch (i) {
		case 1: // ret = -1
			if(revPacket.getType() == ConstParam.TYPE_2&&	errorPktState==0){
				msg.setType(ConstParam.TYPE_2);
				json1.clear();
				json1.put("RET","-1");
				json1.put("INFO", "There is no need to query unread messages");
				json0.put("DATA", json1);
				//packetBody = "{'DATA':{'RET':'-1','INFO':'There is no need to query unread messages'}}"; //请求失败,加密算法不存在
				packetBody=json0.toString();
				}
			if(encry==2&&password.getBytes().length<8&&	errorPktState==1){
				msg.setType(ConstParam.TYPE_2);
				json1.clear();
				json1.put("RET","-1");
				json1.put("INFO", "The key is not in conformity with the length");
				json0.put("DATA", json1);
				packetBody=json0.toString();
				//packetBody = "{'DATA':{'RET':'-1','INFO':'The key is not in conformity with the length'}}"; //请求失败,密钥长度不符合
				}
			break;
			
		case 2: // ret = 0
			if (revPacket.getType() == ConstParam.TYPE_2) {
				msg.setType(ConstParam.TYPE_2);
				json1.clear();
				json1.put("RET","0");
				json1.put("INFO",encry);
				json0.put("DATA", json1);
				packetBody=json0.toString();
//				packetBody = "{'DATA':{'RET':'0','INFO':"+encry+"}}"; //选择加密的方法
			}
			break;
		}
		msg.setData(packetBody);
		return msg.createMessage(msg);
	}

	@Override
	void SendPkt(byte[] sendPacket) {
		// TODO Auto-generated method stub
		revPacket.getIoSession().write(IoBuffer.wrap(sendPacket));// 发送报文
	}

}
