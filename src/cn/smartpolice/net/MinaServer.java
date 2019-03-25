package cn.smartpolice.net;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.Date;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.transport.socket.nio.NioDatagramAcceptor;
import org.hyperic.sigar.CpuPerc;
import org.hyperic.sigar.OperatingSystem;
import org.hyperic.sigar.ProcCpu;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;

import cn.smartpolice.workbean.SysInfo;
/**
 * 初始化mina框架
 * @author 刘超
 *
 */
public class MinaServer {

	public static void InitMinaServer() {
		Date date = new Date();//设置系统启动时间
		SysInfo.getSysStatInfo().setServerStartDate(date);
		//创建一个非阻塞的server端Socket，用NIO
		NioDatagramAcceptor acceptor = new NioDatagramAcceptor();  
      
		//DefaultIoFilterChainBuilder chain = acceptor.getFilterChain();
		//chain.addLast("codec", new ProtocolCodecFilter(new TextLineCodecFactory(Charset.forName("UTF-8"))));
        acceptor.setHandler(new NetHandler());  
  
        /*Executor threadPool = Executors.newCachedThreadPool();  */
        
        //没有报文传输后10秒中连接空闲  session.close()
        acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 180);
        
        /*chain.addLast("logger", new LoggingFilter());*/
        /*acceptor.getSessionConfig().setIdleTime(arg0, arg1);*/
        /*chain.addLast("codec", new ProtocolCodecFilter(new TextLineCodecFactory(Charset.forName("UTF-8"))));*/  
        /*chain.addLast("threadPool", new ExecutorFilter(threadPool));*/
		//绑定服务器端口
		int bindPort = SysInfo.getSysCfgInfo().getPort();
		/*String addr = "192.168.1.109";*/
		//绑定端口，启动服务器
		try {
			acceptor.unbind();
			acceptor.bind(new InetSocketAddress(bindPort));
			/*************************************************/					
			OperatingSystem OS = OperatingSystem.getInstance(); 
			Properties props = System.getProperties(); 
	        InetAddress addr; 
			addr = InetAddress.getLocalHost();			
	        String ip = addr.getHostAddress(); 
	        Map<String, String> map = System.getenv(); 
	        String computerName = map.get("COMPUTERNAME");// 获取计算机名 
	        SysInfo.getSysStatInfo().setUsername(System.getProperty("user.name"));
	        SysInfo.getSysStatInfo().setIp(ip);
	        SysInfo.getSysStatInfo().setLocahost(addr.getHostName());
	        SysInfo.getSysStatInfo().setComputername(computerName);
	        SysInfo.getSysStatInfo().setArch(System.getProperty("os.arch"));
	        SysInfo.getSysStatInfo().setOs(OS.getDescription());
	        SysInfo.getSysStatInfo().setOsName(props.getProperty("os.name"));
	        SysInfo.getSysStatInfo().setOsversion(props.getProperty("os.version"));	  
	     
	        
		} catch (IOException e) {
			System.out.println("Mina Server start for error!"+bindPort);
			e.printStackTrace();
		}
		System.out.println("Mina Server run done!!! on port:"+bindPort); 
		
		/*try {
			Thread.sleep(6);
			acceptor.unbind();
			acceptor.getFilterChain().clear();
		} catch (InterruptedException e) {
			
			e.printStackTrace();
		}*/
	}
	
}
