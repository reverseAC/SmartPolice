package cn.smartpolice.protocol;


import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.util.Base64;

import cn.smartpolice.tools.ByteArrayProc;
import cn.smartpolice.tools.JsonAnalysis;
import cn.smartpolice.workbean.FileNodeInfo;
import cn.smartpolice.workbean.PacketInfo;
import cn.smartpolice.workbean.SysInfo;
import cn.smartpolice.workbean.UserNode;
/**
 * 
 * @author 刘培煊
 * cmd = 7
 * 文件传输协议
 *
 */


public class ProtocolFile extends ProtocolBase{
	String user;//请求文件者帐号
	String fileUrl;//文件URL.String 
	int size;//请求大小 之后代表请求的大小还有多少没有发送
	int from;//起始位置
	int index; //支持的传输方式
	URL url;//文件URL.url
	String info;
	long lenAll;//文件的最初长度
	int errorPktState;//标记返回错误报文类型
	int LEN2=0;//本次传输的总长度
	int LEN1=0;//本次传输的实际长度
	int limit=1024*3;//文件太大 每次发送10k
	//int limit=20;
	byte[] buf=new byte[limit]; //存放每次发送10k的数组
	String str="";//存放每次发送的文件内容
	int port;//文件服务器port 暂时还没有文件服务器
	String ip;//文件服务器ip
	boolean flag=true;//信号量 用于中断传输
	JSONObject json0 = new JSONObject();//json对象
	JSONObject json1 = new JSONObject();//json对象
	

	void ParsePktProto(PacketInfo packetInfo)  {
		//报文解析
		LEN2=0;//每次传送初始化LEN2
		this.revPacket = packetInfo;
		System.out.println("解析");
		StringBuffer stringBuffer = new StringBuffer();
		for (int i = revPacket.getDatapos(); i < revPacket.getMessage().length; i++) {
			stringBuffer.append((char) revPacket.getMessage()[i]);
		}
		String datas = stringBuffer.toString();
		JsonAnalysis jsonAnalysis = new JsonAnalysis();
		String data = jsonAnalysis.getValue(datas, "DATA");
		
		user = jsonAnalysis.getValue(data, "USER");
		fileUrl = (jsonAnalysis.getValue(data, "FILE")).toString();
		//只有type=1的时候 才会有size from index 不然Integer.parseInt里面放空值会出错
		if(revPacket.getType() == ConstParam.TYPE_1){
			size = Integer.parseInt(jsonAnalysis.getValue(data, "SIZE"));
			from = Integer.parseInt(jsonAnalysis.getValue(data, "FROM"));
			index = Integer.parseInt(jsonAnalysis.getValue(data, "INDEX"));
		}
		System.out.println("33333");
			try {
				url= new URL(fileUrl);
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			this.ExecProto();
}
	
	//文件传输队列初始化 加入文件队列
	void addfileQueue(FileNodeInfo fileNode){	
			SysInfo.getFileDataInfoQueue().add(fileNode);//加入文件队列
		} 
	
	//移出文件队列
	void remfileQueue(FileNodeInfo fileNode){	
		SysInfo.getFileDataInfoQueue().remove(fileNode);//加入文件队列
	} 
 
	//建立文件节点信息
	public FileNodeInfo FileNode(File file) { 
		FileNodeInfo fileNode=new FileNodeInfo();
		fileNode.setFileName(file.getName());//文件名
		fileNode.setFileSize(Long.toString(lenAll));//文件的字节数
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(file.lastModified());
		String time;//定义时间
		time = sdf.format(cal.getTime());//获取到当前修改的时间
		fileNode.setTime(time);//时间
		
		fileNode.setUrl(fileUrl);//url
		try {
			String MD5=getMd5ByFile(url);//文件的MD5
			fileNode.setMD5(MD5);//MD5
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	return fileNode;
	}

	//计算文件MD5
	 public static String getMd5ByFile(URL url) throws FileNotFoundException {  
         String value = null; 
         File file= new File(url.getFile());//此处获取文件File
         FileInputStream in = new FileInputStream(file);  
         try {
			value = DigestUtils.md5Hex(IOUtils.toByteArray(in));
		    IOUtils.closeQuietly(in);    
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {  
            if(null != in) {  
                try {  
                in.close();  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
        }  
    }     
        
     return value;  
     } 
	 
	//文件带内编码发送  每次发送10k	 
	 public void Base64Send(File file){
		//找到节点
			UserNode userNode = null;
			// 通过报文sort判断是dev还是app
			if (revPacket.getSort() == ConstParam.SORT_2) {
				userNode = SysInfo.getInstance().getDevNodeById(revPacket.getSid());
			}
			if (revPacket.getSort() == ConstParam.SORT_0) {
				userNode = SysInfo.getInstance().getAppNodeById(revPacket.getSid());
			}
			
		 int count=0;
		 try {
			FileInputStream fis = new FileInputStream(file);
			try {
				fis.skip(from);//跳过from前面的内容
				InputStream  inBuffer = new BufferedInputStream(fis);  
				//传输从from开始  size大小的文件
				while(size>limit||size==limit){
					
					//中断传输
					if(flag==false)
						return;
					
					if ((count = inBuffer.read(buf)) != -1) {//每次读取10k字节放入缓冲区中
						//buf=Base64.encodeBase64(buf);//转换为base64再发送
						str=new String(Base64.encodeBase64(buf));
					}
					size=size-limit;
					LEN2=LEN2+limit;
					LEN1=limit;
					byte[] packet = PackPkt(3);
					SendPkt(packet);
					//当文件太大时候 发送频率太快  容易丢包 每发送一个间断1ms
					try {
						Thread.sleep(1);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					userNode.setLastPacketInfo(packet);// 更新缓存
					buf=new byte[limit];
				}
				
				//发送剩余小于10k的字节
				//中断传输
				if(flag==false)
					return;
				
				byte[] buf1=new byte[size];//存放剩余的字节
				if ((count = inBuffer.read(buf1)) != -1) {
//					String s=new String(buf1);
				
					//buf=Base64.encodeBase64(buf1);//转换为base64再发送
					str=new String(Base64.encodeBase64(buf1));
//					System.out.println("最后发送的是：");
//					System.out.println(new String(Base64.decodeBase64(str.getBytes())));
				}
				LEN2=LEN2+size;
				LEN1=size;
				byte[] packet = PackPkt(3);
				SendPkt(packet);
				userNode.setLastPacketInfo(packet);// 更新缓存
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 }

//带内二进制发送	 
	 public void BinarySend(File file){
		//找到节点
			UserNode userNode = null;
			// 通过报文sort判断是dev还是app
			if (revPacket.getSort() == ConstParam.SORT_2) {
				userNode = SysInfo.getInstance().getDevNodeById(revPacket.getSid());
			}
			if (revPacket.getSort() == ConstParam.SORT_0) {
				userNode = SysInfo.getInstance().getAppNodeById(revPacket.getSid());
			}
			
		 ByteArrayProc byteArrayProc = new ByteArrayProc();
		List<byte[]> packet = new ArrayList<byte[]>();
//		 int count=0;
		// String result="";
		 try {
			FileInputStream fis = new FileInputStream(file);
			try {
				fis.skip(from);//跳过from前面的内容
				FileChannel fcin = fis.getChannel(); //通道
				// 定义缓冲区，并指定大小  
		        ByteBuffer buffer = ByteBuffer.allocate(limit);  
			//	InputStream  inBuffer = new BufferedInputStream(fis); 
				//传输从from开始  size大小的文件
				while(size>limit||size==limit){
					
					//中断传输
					if(flag==false)
						return;
					
					  // 清空缓冲区  
		            buffer.clear(); 
		            if (( fcin.read(buffer)) != -1) ;
		            buffer.flip();
		            //获取缓冲区里面的内容
			    	 byte[] dst = new byte[buffer.limit()];
			         buffer.get(dst);
			         
					size=size-limit;
					LEN2=LEN2+limit+from;
					LEN1=limit;
					//将二进制文件加入到报文后面 再发送
					byte[] packet1 = PackPkt(4);
					packet.add(packet1);
					packet.add(dst);
					
					byte[] packets = byteArrayProc.sysCopy(packet); // 将多个byte[]拼接
					SendPkt(packets);
					//当文件太大时候 发送频率太快  容易丢包 每发送一个间断10ms
					try {
						Thread.sleep(1);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					userNode.setLastPacketInfo(packets);// 更新缓存
					packet.clear();				
				}
							
				//发送剩余小于10k的内容
				//中断传输
				if(size>0&&size<limit){
				if(flag==false)
					return;
			
				  // 清空缓冲区  
	            buffer.clear(); 
	            if ((fcin.read(buffer)) != -1) ;
	            buffer.flip();
	            //获取缓冲区里面的内容
		    	 byte[] dst = new byte[buffer.limit()];
		         buffer.get(dst);
		         //关闭通道
		         fcin.close();
		         fis.close();
		         
				LEN2=LEN2+size+from;
				LEN1=size;
				//将二进制文件加入到报文后面 再发送
				byte[] packet1 = PackPkt(4);
				packet.add(packet1);
				packet.add(dst);
				byte[] packets = byteArrayProc.sysCopy(packet); // 将多个byte[]拼接
				SendPkt(packets);
				userNode.setLastPacketInfo(packets);// 更新缓存
			
				}
				
				} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 }

	@SuppressWarnings({ "static-access", "deprecation" })
	void ExecProto(){
		//找到节点
		UserNode userNode = null;
		// 通过报文sort判断是dev还是app
		if (revPacket.getSort() == ConstParam.SORT_2) {
			userNode = SysInfo.getInstance().getDevNodeById(revPacket.getSid());
		}
		if (revPacket.getSort() == ConstParam.SORT_0) {
			userNode = SysInfo.getInstance().getAppNodeById(revPacket.getSid());
		}
		
		try {
			url = new URL(fileUrl);
			String fileName = url.getFile();//获取文件名
			File file= new File(fileName);//此处获取文件File
			lenAll=file.length();
			FileNodeInfo fileNode=FileNode(file);//建立文件传输节点
		
			//type=2文件传输应答
			if (revPacket.getType() == ConstParam.TYPE_1){
				
				if(!file.exists()){//1文件是否存在
					errorPktState=0;
					byte[] errorPacket = PackPkt(errorPktState);
					SendPkt(errorPacket);
					userNode.setLastPacketInfo(errorPacket);// 更新缓存
				}else{
					if((size<(lenAll-from))||(size==(lenAll-from))){//3要求传送的大小不大于文件真实大小
						if(index==0||index==1||index==2||index==4||index==8||index==16){//4对index进行判断 是否为支持的传输方式
							this.addfileQueue(fileNode);//加入文件传输队列
					//带内编码
					if(index == 0){
						this.Base64Send(file);
					}
					if(index == 1){//1index=1//带内二进制发送
						this.BinarySend(file);
					}//1
						//ftp
					if(index == 2){
						byte[] packet = PackPkt(5);
						SendPkt(packet);	
						userNode.setLastPacketInfo(packet);// 更新缓存
					}
					//http
					if(index == 4){
						byte[] packet = PackPkt(6);
						SendPkt(packet);
						userNode.setLastPacketInfo(packet);// 更新缓存
					}
					//ftp中转
					if(index == 8){
						byte[] packet = PackPkt(7);
						SendPkt(packet);
						userNode.setLastPacketInfo(packet);// 更新缓存
					}
					//http中转
					if(index == 16){
						byte[] packet = PackPkt(8);
						SendPkt(packet);
						userNode.setLastPacketInfo(packet);// 更新缓存
					}
					this.remfileQueue(fileNode);//下载完后 从文件队列里面移除
						}//4
						else{
							//不支持的传输方式
							errorPktState=4;
							byte[] errorPacket = PackPkt(errorPktState);
							SendPkt(errorPacket);
							userNode.setLastPacketInfo(errorPacket);// 更新缓存
						}
					}//3
					else{//文件请求大小超过文件真实大小
						errorPktState=1;
						byte[] errorPacket = PackPkt(errorPktState);
						SendPkt(errorPacket);
						userNode.setLastPacketInfo(errorPacket);// 更新缓存
					}
				}
			}
			
			//type=3文件传输终止
			if (revPacket.getType() == ConstParam.TYPE_3){
				//文件为发送完毕 则终止发送
				if(size>0)
					flag=false;
				else{
					//文件如果已经发送完毕 则终止发送无效
					errorPktState=2;
					byte[] errorPacket = PackPkt(errorPktState);
					SendPkt(errorPacket);
					userNode.setLastPacketInfo(errorPacket);// 更新缓存
				}
			}
			//type=5文件信息应答
			if (revPacket.getType() == ConstParam.TYPE_4){
				if(file.exists()){//1文件是否存在
					//FileNodeInfo fileNode=FileNode(file);
					//传输文件信息
					//info="'(FILEURL,SIZE,MD5)':['"+fileNode.getUrl()+"','"+fileNode.getFileSize()+"','"+fileNode.getMD5()+"']";
					json1.clear();
					json1.put("FILE", fileNode.getUrl());
					json1.put("SIZE", Integer.parseInt( fileNode.getFileSize()));
					json1.put("MD5",fileNode.getMD5());
					byte[] packet = PackPkt(2);
					SendPkt(packet);
					userNode.setLastPacketInfo(packet);// 更新缓存
				}//1
				else{//文件不存在
					errorPktState=3;
					byte[] errorPacket = PackPkt(errorPktState);
					SendPkt(errorPacket);
					userNode.setLastPacketInfo(errorPacket);// 更新缓存
					}
			}
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	byte[] PackPkt(int i) {
		PacketMsg msg = new PacketMsg();
		msg.setCmd(ConstParam.CMD_7);
		msg.setOpt(ConstParam.OPT_16);
		msg.setAck(revPacket.getSeq());//返回报文的ack与发送过来的报文seq一致
		msg.setSid(ConstParam.SERVER_ID);
		String packetBody = null;
		switch (i) {
		case 1: // ret = -1
			if (revPacket.getType() == ConstParam.TYPE_1&&errorPktState==0) {
					msg.setType(ConstParam.TYPE_2);
					json1.clear();
					json1.put("RET", "-1");
					json1.put("INFO","File does not exist" );
					json0.put("DATA", json1);
					packetBody=json0.toString();//传输失败 文件不存在 errorPktState=0
					//packetBody = "{'DATA':{'RET':'-1','INFO':'File does not exist'}}";//传输失败 文件不存在 errorPktState=0
			}
			if (revPacket.getType() == ConstParam.TYPE_1&&errorPktState==1) {
				msg.setType(ConstParam.TYPE_2);
				json1.clear();
				json1.put("RET", "-1");
				json1.put("INFO","File a request is too big" );
				json0.put("DATA", json1);
				packetBody=json0.toString();//传输失败 请求文件太大 errorPktState=1
				//packetBody = "{'DATA':{'RET':'-1','INFO':'File a request is too big'}}";//传输失败 请求文件太大 errorPktState=1
		}
			if (revPacket.getType() == ConstParam.TYPE_3&&errorPktState==2) {
				msg.setType(ConstParam.TYPE_3);
				json1.clear();
				json1.put("RET", "-1");
				json1.put("INFO","The file has been sent" );
				json0.put("DATA", json1);
				packetBody=json0.toString();//传输失败 文件不存在 errorPktState=2
				//packetBody = "{'DATA':{'RET':'-1','INFO':'The file has been sent'}}";//传输失败 文件不存在 errorPktState=2
		}
			if (revPacket.getType() == ConstParam.TYPE_4&&errorPktState==3) {
				msg.setType(ConstParam.TYPE_5);
				json1.clear();
				json1.put("RET", "-1");
				json1.put("INFO","File does not exist" );
				json0.put("DATA", json1);
				packetBody=json0.toString();//传输失败 文件不存在 errorPktState=3
				//packetBody = "{'DATA':{'RET':'-1','INFO':'File does not exist'}}";//传输失败 文件不存在 errorPktState=3
		}
			if (revPacket.getType() == ConstParam.TYPE_1&&errorPktState==4) {
				msg.setType(ConstParam.TYPE_2);
				json1.clear();
				json1.put("RET", "-1");
				json1.put("INFO","The mode of transport that is not supported" );
				json0.put("DATA", json1);
				packetBody=json0.toString();//传输失败 不支持的传输方式 errorPktState=4
				//packetBody = "{'DATA':{'RET':'-1','INFO':'The mode of transport that is not supported'}}";//传输失败 不支持的传输方式 errorPktState=4
		}
			break;
			
		case 2: // type=5 文件信息
			if (revPacket.getType() == ConstParam.TYPE_4) {
				msg.setType(ConstParam.TYPE_5);
				JSONObject json2 = new JSONObject();//json对象
				json2.put("RET","0");
				json2.put("INFO", json1);
				json0.put("DATA", json2);
				packetBody=json0.toString();
				//packetBody = "{'DATA':{'INFO':{"+info+"}}}"; 
			}
			break;
			
		case 3://ret=0
			if (revPacket.getType() == ConstParam.TYPE_1) {
				
				msg.setType(ConstParam.TYPE_2);
				json1.clear();
				json1.put("RET",0);
				json1.put("CONTENT",str);
				json1.put("LEN",LEN1);
				json1.put("INFO", String.valueOf(LEN2));			
				json0.put("DATA", json1);
				packetBody=json0.toString();
				//packetBody = "{'DATA':{'RET':'0','CONTENT':'"+buf+"','LEN':'"+LEN1+"','INFO':'"+LEN2+"'}}"; //传输成功
			}
			break;
			
		case 4://ret=1
			if (revPacket.getType() == ConstParam.TYPE_1) {
				msg.setType(ConstParam.TYPE_2);
				json1.clear();
				json1.put("RET",1);
				json1.put("CONTENT","OK");
				json1.put("LEN",LEN1);
				json1.put("INFO",String.valueOf(LEN2));			
				json0.put("DATA", json1);
				packetBody=json0.toString();
				//packetBody = "{'DATA':{'RET':'1','CONTENT':'OK','LEN':'"+LEN1+"','INFO':'"+LEN2+"'}}"; //传输成功
			}
			break;
			
		case 5://ret=2
			if (revPacket.getType() == ConstParam.TYPE_1) {
				msg.setType(ConstParam.TYPE_2);
				json1.clear();
				json1.put("RET", 2);
				json1.put("CONTENT", port);
				json0.put("DATA", json1);
				packetBody=json0.toString();
				//packetBody = "{'DATA':{'RET':'2','CONTENT':'"+port+"}}"; //传输成功
			}
			break;
			
		case 6://ret=4
			if (revPacket.getType() == ConstParam.TYPE_1) {
				msg.setType(ConstParam.TYPE_2);
				json1.clear();
				json1.put("RET", 4);
				json1.put("CONTENT", port);
				json0.put("DATA", json1);
				packetBody=json0.toString();
				//packetBody = "{'DATA':{'RET':'4','CONTENT':'"+port+"}}"; //传输成功
			}
			break;
			
		case 7://ret=8
			if (revPacket.getType() == ConstParam.TYPE_1) {
				msg.setType(ConstParam.TYPE_2);
				json1.clear();
				json1.put("RET", 8);
				json1.put("CONTENT", ip);
				json0.put("DATA", json1);
				packetBody=json0.toString();
				//packetBody = "{'DATA':{'RET':'8','CONTENT':'"+ip+"}}"; //传输成功
			}
			break;
			
		case 8://ret=16
			if (revPacket.getType() == ConstParam.TYPE_1) {
				msg.setType(ConstParam.TYPE_2);
				json1.clear();
				json1.put("RET", 16);
				json1.put("CONTENT", ip);
				json0.put("DATA", json1);
				packetBody=json0.toString();
				//packetBody = "{'DATA':{'RET':'16','CONTENT':'"+ip+"}}"; //传输成功
			}
			break;
		}
		msg.setData(packetBody);
		return msg.createMessage(msg);
	}
	
	void SendPkt(byte[] sendPacket) {
		revPacket.getIoSession().write(IoBuffer.wrap(sendPacket));// 发送报文
	}
	
}
