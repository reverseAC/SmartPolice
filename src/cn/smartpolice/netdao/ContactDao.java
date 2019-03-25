package cn.smartpolice.netdao;

import java.util.List;

import cn.smartpolice.hibernate.ContactInfo;

public interface ContactDao {

	//插入联系人
	public void insertInfOfContact(ContactInfo contactInf);
	
	//通过user查看Contact表是否是联系人 state状态为1
	public ContactInfo selectInfOfByMasteridAndContactid(int userid,int cuserid);
		
	//查询一个user的联系人
	public List<ContactInfo> selectInfOfContactByMasterid(int user,byte from,int a);
	
	//判断userid是否有状态为-1的
	public ContactInfo selectContactByUseridAndState(int userid,int cuserid);
	
	//更新表中的一条记录
	public ContactInfo updateInfOfContact(ContactInfo contactInfo,byte kind,String change);

	
}
