package cn.smartpolice.dao;

import java.util.List;

import cn.smartpolice.hibernate.AdminLog;
import cn.smartpolice.hibernate.ManagerInfo;
import cn.smartpolice.hibernate.ServerInfo;

public interface ManagerInfoDao {


	ManagerInfo findManager(String username);
	String updatePassword(int id,String password);
	List managerMsg();
	int login(AdminLog login);
	AdminLog time(int managerid);
	void logout(AdminLog logout,int logid);
	void update(ManagerInfo manager);
	
}
