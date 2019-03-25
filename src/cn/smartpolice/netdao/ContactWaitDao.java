package cn.smartpolice.netdao;

import cn.smartpolice.hibernate.ContactWait;

public interface ContactWaitDao {
	// 插入等待确认联系人
	public String insertInfOfContactWait(ContactWait contactWait);

	// 删除确认过的等待联系人的请求
	public ContactWait deleteInfOfContactWait(ContactWait contactWait);

	// 查询ContactWait表
	public ContactWait selectInfOfContactWaitByMasteridAndContactid(int userid,int cuserid,byte state);

	// 更改联系人等待表的状态
	public String updateInfOfContactWait(ContactWait contactWait, byte state);

	// 查询通过contactid和state（状态）
	public ContactWait selectInfOfContactWaitByContactidAndState(String cuser, byte state);

	// 查询通过masterid和state（状态）
	public ContactWait selectInfOfContactWaitByMasteridAndState(String user, byte state);

}
