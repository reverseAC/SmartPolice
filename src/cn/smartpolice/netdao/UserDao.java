package cn.smartpolice.netdao;

import java.util.List;

import cn.smartpolice.hibernate.UserInfo;

public interface UserDao {

	// 通过id查找
	public UserInfo findAppuserByID(int id);

	// 查出所有user对象
	public List<UserInfo> findAppuser();

	// 通过user查找
	public UserInfo findAppuserByName(String name);

	// 插入记录 该方法有问题：SQLGrammarException
	public int insertAppuser(UserInfo userInf);

	// 置state=0（删除标记 注销协议）
	public void changeStateToZero(int id);

	public void updateAppuser(UserInfo userInf);
	
	public List selectAllOfCompanyUser();
}
