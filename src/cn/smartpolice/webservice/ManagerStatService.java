package cn.smartpolice.webservice;

import java.util.List;

import cn.smartpolice.hibernate.MsgNotice;
import cn.smartpolice.hibernate.MsgRecv;

public  interface ManagerStatService {

	public List getCompanyData();//获取厂商注册报表数据

	public List getDeviceData();//获取设备注册报表数据

	public List getMoveDeviceData();//获取所有移动监控设备的报表数据

	public List getUserinfo();//获取所有注册用户的报表数据
	
	public List StatLogdev(List<Integer> devIds);
	
	public List StatLogUser(List<Integer> devIds);
	
	public List allApp();//查找所有APP用户
	public List allUserManager();//所有设备管理员
	public List allUser();//所有人
	public List allCompanyUser();//厂商用户
	public List allManager();//管理员	
	public void insertMsgRecv(int id,MsgRecv msg,MsgNotice notice);

}
