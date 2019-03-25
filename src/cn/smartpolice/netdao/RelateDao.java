package cn.smartpolice.netdao;

import java.util.List;

import cn.smartpolice.hibernate.RelateInfo;

public interface RelateDao {

	public List<RelateInfo> findUserIdByDeviceId(int deviceid);

	// 插入联系人
	public void insertRelateInf(RelateInfo relateInf);

	// 查询一个dev(puser)中所有的关联人
	public List<RelateInfo> findallRelateInfBypuser(int puser, byte state);

	// 查询muser通过puser
	public RelateInfo findallRelateInfBypuser(int puserid);

	// 查询一个dev(puser)中所有的管理关联人
	public List<RelateInfo> findallRelateInfBypuserandauthority(int puserid);

	// 查询一个user中所有的dev
	public List<RelateInfo> findallRelateInfByuser(int userid);

	// 查询user和puser的这条记录
	public RelateInfo findRelateInfByuserandpuser(String user, String puser);

	// 查询一个user与puser是否关联
	public RelateInfo findRelateInfByuserandmuser(int peer, int puserid, byte state);

	// 更新表中的一条记录
	public RelateInfo updateRelateInf(RelateInfo relateInf, byte kind, String change);
	
	//删除
	public Object deleteRelateInfBypuser(int puserid);
	
	//同步
	public RelateInfo updateRelateInf(RelateInfo relateInf,RelateInfo relateInf1);
	
	public List selectMangerOfDevice();
}
