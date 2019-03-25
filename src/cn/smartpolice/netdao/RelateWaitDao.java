package cn.smartpolice.netdao;

import java.util.List;

import cn.smartpolice.hibernate.RelateWait;

public interface RelateWaitDao {
	// 插入等待确认联系人
	public String insertRelateWait(RelateWait relateWait);

	// 查询通过askid和state（状态）
	public RelateWait findRelateWaitBymuserAndState(String muser, byte state);

	// 更改关联人等待表的状态
	public String updateRelateWait(RelateWait relateWait, String state);

	public RelateWait findRelateWaitBypeer(int peer, int puserid);

	// 查询通过applyId和state（状态）
	public RelateWait findRelateWaitByuserAndState(String user, byte state);


}
