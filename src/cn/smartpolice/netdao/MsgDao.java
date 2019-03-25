//package cn.smartpolice.netdao;
//
//import java.io.Serializable;
//import java.util.List;
//
//import org.hibernate.Query;
//import org.hibernate.Session;
//import org.hibernate.Transaction;
//
//import cn.smartpolice.hibernate.Msg_alarm;
//import cn.smartpolice.hibernate.Msg_chat;
//import cn.smartpolice.hibernate.Msg_notice;
//
///**
// * 消息数据库操作
// * 
// * @author 刘超
// *
// */
//public class MsgDao {
//
//	private Session session = HibernateUtil.currentSession();
//	private Transaction ts = session.beginTransaction();
//
//	// 在msg_alarm中插入一条记录
//	public Msg_alarm insertMsgAlarm(Msg_alarm alarm) {
//		Msg_alarm a = (Msg_alarm) this.session.merge(alarm);
//		this.session.clear();
//		ts.commit();
//		return a;
//	}
//
//	// 在msg_chat中插入一条记录
//	public Msg_chat insertMsgChat(Msg_chat chat) {
//		Msg_chat c = (Msg_chat) this.session.merge(chat);
//		this.session.clear();
//		ts.commit();
//		return c;
//	}
//
//	// 在msg_notice中插入一条记录
//	public Msg_notice insertMsgNotice(Msg_notice notice) {
//		Msg_notice n = (Msg_notice) this.session.merge(notice);
//		this.session.clear();
//		ts.commit();
//		return n;
//	}
//
//	// 在消息记录表中插入
//	public void insertMsgRecv(MsgRecv recv) {
//		this.session.clear();
//		this.session.save(recv);
//		ts.commit();
//	}
//
//	// 更新未读消息已读记录
//	public void updateMsgRecv(int mid,int rid) {
//		Query query = session.createQuery("update MsgRecv t set t.state = 0 where t.messageid =:mid and t.recvuserid =:rid");
//		query.setInteger("mid", mid);
//		query.setInteger("rid", rid);
//		query.executeUpdate();
//		ts.commit();
//	}
//
//	/*
//	 * // 使用自己的的ID在接收记录表中查询接收者ID public MsgRecv findMsgRecvByRecvUserIdalarm(int
//	 * recvuserid) { Query query = session.createQuery(
//	 * "from MsgRecv where state = 1 and msgtype = 1 and recvuserid = ?");
//	 * query.setInteger(0, recvuserid); // ts.commit(); if (query.list() != null
//	 * && list.size()==1 ) { List<MsgRecv> list = query.list(); return
//	 * list.get(0); } return null; }
//	 */
//	//
//	public List<MsgRecv> findMsgRecvByRecvUserIdalarm(int recvuserid) {
//		Query query = session.createQuery("from MsgRecv where state = 1 and msgtype = 1 and recvuserid = ?");
//		
//		query.setInteger(0, recvuserid);
//
//		ts.commit();
//		if (!query.list().isEmpty()) {
//			List<MsgRecv> list = query.list();
//			return list;
//		}
//		return null;
//	}
//	public List<MsgRecv> findMsgRecvByRecvUserIdalarm(int recvuserid,int num) {
//		Query query = session.createQuery("from MsgRecv where state = 1 and msgtype = 1 and recvuserid = ?");
//		
//		query.setInteger(0, recvuserid).setMaxResults(num);
//
//		ts.commit();
//		if (!query.list().isEmpty()) {
//			List<MsgRecv> list = query.list();
//			return list;
//		}
//		return null;
//	}
//
//
//	public List<MsgRecv> findMsgRecvByRecvUserIdchat(int recvuserid) {
//		Query query = session.createQuery("from MsgRecv where state = 1 and msgtype = 2 and recvuserid = ?");
//		query.setInteger(0, recvuserid);
//		ts.commit();
//		if (!query.list().isEmpty()) {
//			List<MsgRecv> list = query.list();
//			return list;
//		}
//		return null;
//	}
//	public List<MsgRecv> findMsgRecvByRecvUserIdchat(int recvuserid,int num) {
//		Query query = session.createQuery("from MsgRecv where state = 1 and msgtype = 2 and recvuserid = ?");
//		query.setInteger(0, recvuserid).setMaxResults(num);
//		ts.commit();
//		if (!query.list().isEmpty()) {
//			List<MsgRecv> list = query.list();
//			return list;
//		}
//		return null;
//	}
//	public List<MsgRecv> findMsgRecvByRecvUserIdnotice(int recvuserid) {
//		Query query = session.createQuery("from MsgRecv where state = 1 and msgtype = 8 and recvuserid = ?");
//		query.setInteger(0, recvuserid);
//		ts.commit();
//		if (!query.list().isEmpty()) {
//			List<MsgRecv> list = query.list();
//			return list;
//		}
//		return null;
//
//	}
//	public List<MsgRecv> findMsgRecvByRecvUserIdnotice(int recvuserid,int num) {
//		Query query = session.createQuery("from MsgRecv where state = 1 and msgtype = 8 and recvuserid = ?");
//		query.setInteger(0, recvuserid).setMaxResults(num);
//		ts.commit();
//		if (!query.list().isEmpty()) {
//			List<MsgRecv> list = query.list();
//			return list;
//		}
//		return null;
//
//	}
//	public List<MsgRecv> findMsgRecvByRecvUserId(int recvuserid) {
//		Query query = session.createQuery("from MsgRecv where state = 1 and recvuserid = ?");
//		query.setInteger(0, recvuserid);
//		ts.commit();
//		if (query.list() != null) {
//			List<MsgRecv> list = query.list();
//			return list;
//		}
//		return null;
//	}
//
//	// 未读消息查询使用
//	public Msg_alarm findMsgalarmByMessageid(int alarmID) {
//		Query query = session.createQuery("from Msg_alarm where alarmid = ?");
//		query.setInteger(0, alarmID);
//		List<Msg_alarm> list = query.list();
//		ts.commit();
//		if (list != null) {
//			return list.get(0);
//		}
//		return null;
//	}
//
//	public Msg_chat findMsgChatByMessageid(int chatid) {
//		Query query = session.createQuery("from Msg_chat where chatid = ?");
//		query.setInteger(0, chatid);
//		List<Msg_chat> list = query.list();
//		ts.commit();
//		if (list != null) {
//			return list.get(0);
//		}
//		return null;
//
//	}
//
//	public Msg_notice findMsgnoticeByMessageid(int noticeid) {
//		Query query = session.createQuery("from Msg_notice where noticeid = ?");
//		query.setInteger(0, noticeid);
//		List<Msg_notice> list = query.list();
//		ts.commit();
//		if (list != null/* && list.size()==1 */) {
//			return list.get(0);
//		}
//		return null;
//	}
//	/*
//	 * //通过接收消息设备id找到消息记录 public List<MsgRecv> findMsgRecvByRecvUserIdlist(int
//	 * recvuserid){ Query query = session.createQuery(
//	 * "from MsgRecv where recvuserid = ?"); query.setInteger(0, recvuserid);
//	 * List<MsgRecv> list = query.list(); //ts.commit(); if(list != null &&
//	 * list.size()==1){ return list; } return null; }
//	 */
//	//通过发送者的账号找到他的所有未读消息
//	public List<MsgRecv> findMsgRecvByUser(int recvuserid){
//		Query query = session.createQuery("from MsgRecv where state=1 and recvuserid = ?");
//		query.setInteger(0, recvuserid);
//		List<MsgRecv> list = query.list();
//		ts.commit();
//		return list;	
//	}
//
//}
