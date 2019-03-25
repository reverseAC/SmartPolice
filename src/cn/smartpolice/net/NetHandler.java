package cn.smartpolice.net;

import java.net.SocketAddress;
import java.util.Date;


import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

import cn.smartpolice.protocol.ProtocolProc;
import cn.smartpolice.workbean.SysInfo;


public class NetHandler extends IoHandlerAdapter{

	ProtocolProc ProtocolProc = new ProtocolProc();
	int sendPacketNum = 0;
	@Override
	public void sessionCreated(IoSession session) throws Exception {
		// TODO Auto-generated method stub
		SocketAddress remoteAddress = session.getRemoteAddress(); //获取远程客户端地址信息
		Date date = new Date();
		System.out.println(remoteAddress+"连接进来咯！！:"+date.toString());
	}
	
	@Override
	public void sessionOpened(IoSession session) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("Session Opened -->");
		SocketAddress remoteAddress = session.getRemoteAddress(); //获取远程客户端地址信息
		System.out.println(remoteAddress);
	}
	
	@Override
	public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
		// TODO Auto-generated method stub
		//System.out.println("连接空闲");
		//session.close(true);
	}
	
	@Override
	public void sessionClosed(IoSession session) throws Exception {
		// TODO Auto-generated method stub
		Date date = new Date();
		System.out.println("...>远程网络连接被关闭时间:"+date.toString());
	}
	
	@Override
	public void messageReceived(IoSession session, Object message) throws Exception {
		// TODO Auto-generated method stub
		long time1 = new Date().getTime();
		System.out.println("--> 消息接入 ...");
		/*String msg = message.toString();
		System.out.println("原始数据："+msg); */
		IoBuffer ioBuffer = (IoBuffer) message; //用IoBuffer读取传入的消息字节
		
		byte[] b = new byte [ioBuffer.limit()];
		ioBuffer.get(b);
		
		ProtocolProc.RecvPktProc(session,b);
		long time2 = new Date().getTime();
		System.out.println("系统反应时间："+(time2-time1)+"毫秒");
		System.out.println("----等待消息接入----");
	}
	
	@Override
	public void messageSent(IoSession session, Object message) throws Exception {
		
		System.out.println("信息已经传送给客户端"); 
		SysInfo.getSysStatInfo().setSendnum(++sendPacketNum);//统计发送报文
//		if(message instanceof IoBuffer){
//			IoBuffer ioBuffer = (IoBuffer)message;
//			byte[] b= new byte [ioBuffer.limit()];
//			ioBuffer.get(b);
//			StringBuffer buffer = new StringBuffer();
//			for(int i=0;i<b.length;i++){
//				
//				 buffer.append((char) b [i]);
//			}
//			System.out.println("发送完成!"); 
//		}
	}
	
	@Override
	public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("--其他方法抛出异常--:"+cause);
		System.out.println("客户端"+session.getRemoteAddress()+"关闭了链接");
		
	}
}
