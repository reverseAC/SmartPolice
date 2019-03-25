package cn.smartpolice.dao;

import java.util.List;

import cn.smartpolice.hibernate.UserInfo;

public interface UserInfoDao {
	public List findAllUserinfo();//查找全部用户信息

	public List findManagerUserInfo(int userid);//用户详细信息

	public void removeCompanyUser(int id);

	public void Removesuer(int id);
	
	public List FindAddUser();//新增用户
	
	public UserInfo findUserInfo(int userid);
	
	public List findUser(int userid);
	
	public List findUserAll();
	
	public String updateQues(int id,int num,String answer);
	
}
