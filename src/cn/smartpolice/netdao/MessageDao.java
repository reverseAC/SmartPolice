package cn.smartpolice.netdao;

import java.util.HashMap;
import java.util.List;

import cn.smartpolice.hibernate.ContactWait;
import cn.smartpolice.hibernate.MsgAlarm;
import cn.smartpolice.hibernate.MsgChat;
import cn.smartpolice.hibernate.MsgNotice;
import cn.smartpolice.hibernate.MsgRecv;
import cn.smartpolice.hibernate.RelateWait;

public interface MessageDao {
	// String 类型的值为返回Key值
	// num为0全查
	public HashMap<String, List<MsgRecv>> SelectMessageOfUnReadMsg(int sid, int num);

	// 未读聊天消息
	public HashMap<String, List<MsgChat>> SelectMessageOfUnreadChat(int sid, int num);

	// 未读报警消息
	public HashMap<String, List<MsgAlarm>> SelectMessageOfUnReadAlarm(int sid, int num);

	// 未读通知消息
	public HashMap<String, List<MsgNotice>> SelectMessageOfUnReadNotice(int sid, int num);

	public MsgChat SelectMessageOfChat(int messageid);

	public MsgAlarm SelectMessageOfAlarm(int messageid);

	public MsgNotice SelectMessageOfNotice(int messageid);

	// 插入聊天消息，返回消息编号
	public int insertMessageOfChat(MsgChat chat);

	// 插入报警消息，返回消息编号
	public int insertMessageOfAlarm(MsgAlarm alarm);

	// 插入通知消息，返回消息编号
	public int insertMessageOfNotice(MsgNotice notice);

	// 插入消息接受记录，返回记录编号
	public int insertMessageOfMsgRecv(MsgRecv recv);

	// 根据特征码修改消息状态为已读
	// i=0全部，1报警,2聊天,4平台
	// 需要修改！！调用接口并无i值传入
	//public void UpdateMessageOfSign(int sid, String sign, int i);
	public void UpdateMessageOfSign(int sid, String sign);
	// 在contactwait中以主动添加用户查找 作为主动方，查找时应该查是否有state=2的消息
	public HashMap<String, List<ContactWait>> SelectMessageOfContactWaitMaster(int id);

	// 在contactwait中以被添加用户查找 作为被动方，查找时应查是否有state=0的消息
	public HashMap<String, List<ContactWait>> SelectMessageOfContactWaitcontacted(int id);

	// 管理员主动分享 将传入的ID在管理员字段查询，条件state为2或者0，如果有匹配，返回整个对象队列
	public HashMap<String, List<RelateWait>> SelectMessageOfShare(int id);

	// 关联人主动申请 将传入的ID在请求用户字段查询，条件state为0或者2，如果有匹配，返回整个对象队列
	public HashMap<String, List<RelateWait>> SelectMessageOfApply(int id);

}
