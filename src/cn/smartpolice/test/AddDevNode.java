package cn.smartpolice.test;

import java.util.Date;
import java.util.Random;

import cn.smartpolice.hibernate.DeviceInfo;
import cn.smartpolice.protocol.ConstParam;
import cn.smartpolice.workbean.DevNode;
import cn.smartpolice.workbean.SysInfo;

public class AddDevNode {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		DeviceInfo devinf = null;
		for(int i=0;i<10;i++){
			devinf = new DeviceInfo();
			devinf.setDeviceid(i);
			devinf.setUsername("我是"+i);
			
			// ip和port需要是局部变量，得到应答的报文会变化（掉线的情况）

			DevNode devNode = new DevNode(); // 创建节点
			Random rando = new Random();
			int random = rando.nextInt(999999) % 900000 + 100000; // 产生一个6位随机数
			devNode.setLink(random);
			devNode.setIp("127.0.0.1");
			devNode.setPort(8899);
			devNode.setAccount(devinf.getUsername());
			devNode.setId(devinf.getDeviceid());
			devNode.setRevPktDate(new Date());


			// 刚创建的节点的登录状态置为0，表示还未验证
			devNode.setState(ConstParam.LOGIN_STATE_2);
			// 将节点信息添加到报文中 仅仅为了在ProtocolLogin中取获取节点id (可优化) 下同
			// 将节点添加到队列 在登录处理中从全局队列中取 下同
			SysInfo.getInstance().addUserNode(devNode);
			System.out.println("创建节点:");
			System.out.println(devNode);
	
	}

	}
}

