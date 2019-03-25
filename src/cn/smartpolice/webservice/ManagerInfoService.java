package cn.smartpolice.webservice;


import java.util.List;

import cn.smartpolice.hibernate.AdminLog;
import cn.smartpolice.hibernate.ManagerInfo;

/**
 * 管理员相关业务类
 * 
 * 
 * */
public interface ManagerInfoService {
	ManagerInfo login(String username);
	String updatePassword(int id,String password);
	List managerMsg();
	int login(AdminLog login);
	AdminLog time(int managerid);
	void logout(AdminLog logout,int logid);
}
