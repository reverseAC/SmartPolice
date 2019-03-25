package cn.smartpolice.netdao;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.transform.Transformers;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import cn.smartpolice.hibernate.ContactWait;
import cn.smartpolice.hibernate.MsgAlarm;
import cn.smartpolice.hibernate.MsgChat;
import cn.smartpolice.hibernate.MsgNotice;
import cn.smartpolice.hibernate.MsgRecv;
import cn.smartpolice.hibernate.RelateWait;

@Repository("messageDao")
@Transactional(readOnly=false)
public class MessageDaoImpl implements MessageDao{
//	private Session session = HibernateUtil.currentSession();
//	private Transaction ts = session.beginTransaction();
//	private HibernateTemplate hibernateTemplate;
	@Resource(name="hibernateTemplate")
	private HibernateTemplate hibernateTemplate;
	
	private Session session;
	
	// TEST
	public static void main(String[] args) {

//			MsgRecv m = new MsgRecv();
//			m = new MessageDao().TestUnreadAlarm(1);
//			int a = m.getMessageid();
//				System.out.println(a);
				
//			for(Object object : wocao) {  
//	            Map obj = (Map) object;
//			int a = (int)obj.get("messageid");
//			System.out.println(a);
//			}
//			int a = wocao.get(0).getMessageid();
//			System.out.println(a);
			
			//测试2
//			MsgChat m = new MsgChat();
//			 m = new MessageDao().SelectMessageOfChat(3);
//			 System.out.println(m);
//			 String a = m.getColor();
//			 String b = m.getMsg();
//			 String c = m.getType();
//			 int d = m.getChatid();
//			 int e = m.getRecvid();
//			 int f = m.getSendid();
//			 Date g = m.getSendtime();
//			 System.out.println(a);
//			 System.out.println(b);
//			 System.out.println(c);
//			 System.out.println(d);
//			 System.out.println(e);
//			 System.out.println(f);
//			 System.out.println(g);
//			 System.out.println("操作成功");

			 //测试3
			// MsgChat m = new MsgChat();
			// Date t = new Date();
			// //m.setChatid(5);
			// m.setColor(null);
			// m.setMsg("fajkdf");
			// m.setRecvid(2);
			// m.setSendid(5);
			// m.setSendtime(t);
			// m.setType("1");
			// new MessageDao().insertMessageOfChat(m);
			// System.out.println("操作成功");

		}
	
	//存储过程调用测试	
	public MsgRecv TestUnreadAlarm(int sid) {
		session = this.hibernateTemplate.getSessionFactory().getCurrentSession();
		String procName="{call testAlarm(?)}";
		SQLQuery sqlquery = session.createSQLQuery(procName);  
		sqlquery.setInteger(0, sid);
//        sqlquery.setInteger(1, 5);
		List<MsgRecv> list = sqlquery.list();
		//ts.commit();	
        if (list != null) {			
        	return list.get(0);
        }
		return null;
	}

	// String 类型的值为返回Key值
	// num为0全查
	public HashMap<String, List<MsgRecv>> SelectMessageOfUnReadMsg(int sid, int num) {
		session = this.hibernateTemplate.getSessionFactory().getCurrentSession();
		HashMap<String, List<MsgRecv>> hash = new HashMap<String, List<MsgRecv>>();
		Query query;

		String sign;
		if (num > 0) {
			query = session.createQuery("from MsgRecv where state = 0 and recvuserid = ?");
			query.setInteger(0, sid).setMaxResults(num);
		} else {
			query = session.createQuery("from MsgRecv where state = 0 and recvuserid = ?");
			query.setInteger(0, sid);
		}
		//ts.commit();
		int[] messageid;
		if (!query.list().isEmpty()) {
			List<MsgRecv> list = query.list();
			messageid = new int[list.size()];
			sign = String.valueOf(list.get(list.size() - 1).getMessageid());
			hash.put(sign, list);
			System.out.println(hash.toString()+list.size());
			return hash;
		}
		//ts.commit();
		return hash;

	}

	// 未读聊天消息
	public HashMap<String, List<MsgChat>> SelectMessageOfUnreadChat(int sid, int num) {
		session = this.hibernateTemplate.getSessionFactory().getCurrentSession();
		HashMap<String, List<MsgChat>> hash = new HashMap<String, List<MsgChat>>();
		List<MsgChat> msgChat = null;
		String sign;
		Query query = session.createQuery("from MsgRecv where state = 0 and msgtype = 2 and recvuserid = ?");
		query.setInteger(0, sid).setMaxResults(num);
		//ts.commit();
		int[] messageid;
		if (!query.list().isEmpty()) {
			List<MsgRecv> list = query.list();
			messageid = new int[list.size()];
			sign = String.valueOf(list.get(list.size() - 1).getMessageid());
			for (int i = 0; i < list.size(); i++) {
				query = session.createQuery("from MsgChat where chatid = ?");
				query.setInteger(0, list.get(i).getMessageid());
				MsgChat cl = (MsgChat) query.list().get(0);
				msgChat = query.list();
			}
			hash.put(sign, msgChat);
			return hash;
		}
		//ts.commit();
		return hash;

	}

	// 未读报警消息
	public HashMap<String, List<MsgAlarm>> SelectMessageOfUnReadAlarm(int sid, int num) {
		session = this.hibernateTemplate.getSessionFactory().getCurrentSession();
		HashMap<String, List<MsgAlarm>> hash = new HashMap<String, List<MsgAlarm>>();
		Query query;
		List<MsgAlarm> msgAlarm = null;
		String sign;
		query = session.createQuery("from MsgRecv where state = 0 and msgtype = 1 and recvuserid = ?");
		query.setInteger(0, sid).setMaxResults(num);
		//ts.commit();
		// System.out.println("查询唯独消息表");
		// System.out.println(query.list().size());
		int[] messageid;
		if (!query.list().isEmpty()) {
			// System.out.println("不为空");
			List<MsgRecv> list = query.list();
			// System.out.println(list.size());
			messageid = new int[list.size()];
			sign = String.valueOf(list.get(list.size() - 1).getMessageid());
			for (int i = 0; i < list.size(); i++) {
				query = session.createQuery("from MsgAlarm where alarmid = ?");
				query.setInteger(0, list.get(i).getMessageid());
				// System.out.println(list.get(i).getRecvtime());
				// System.out.println("查报警消息表");
				// System.out.println(query.list().size());
				MsgAlarm al = (MsgAlarm) query.list().get(0);
				// System.out.println(al.getTime()+"///"+sign);

				msgAlarm = query.list();
			}
			// System.out.println(msgAlarm.size());
			// System.out.println("kk"+msgAlarm.get(0).getLevel());
			hash.put(sign, msgAlarm);
			return hash;
		}
		//ts.commit();
		// return null;
		return hash;
	}

	// 未读通知消息
	public HashMap<String, List<MsgNotice>> SelectMessageOfUnReadNotice(int sid, int num) {
		session = this.hibernateTemplate.getSessionFactory().getCurrentSession();
		HashMap<String, List<MsgNotice>> hash = new HashMap<String, List<MsgNotice>>();
		Query query;
		List<MsgNotice> msgNotice = null;
		String sign;
		query = session.createQuery("from MsgRecv where state = 0 and msgtype = 4 and recvuserid = ?");
		query.setInteger(0, sid).setMaxResults(num);
		//ts.commit();
		int[] messageid;
		if (!query.list().isEmpty()) {
			List<MsgRecv> list = query.list();
			messageid = new int[list.size()];
			sign = String.valueOf(list.get(list.size() - 1).getMessageid());
			for (int i = 0; i < list.size(); i++) {
				query = session.createQuery("from MsgNotice where noticeid = ?");
				query.setInteger(0, list.get(i).getMessageid());
				MsgNotice nl = (MsgNotice) query.list().get(0);
				msgNotice = query.list();
			}
			hash.put(sign, msgNotice);
			return hash;
		}
		//ts.commit();
		return hash;

	}

	//
	public MsgChat SelectMessageOfChat(int messageid) {
		session = this.hibernateTemplate.getSessionFactory().getCurrentSession();
		Query query = session.createQuery("from MsgChat where chatid = ?");
		query.setInteger(0, messageid);
		List<MsgChat> list = query.list();
		//ts.commit();
		if (list != null) {
			return list.get(0);
		}
		return null;

	}

	public MsgAlarm SelectMessageOfAlarm(int messageid) {
		session = this.hibernateTemplate.getSessionFactory().getCurrentSession();
		Query query = session.createQuery("from MsgAlarm where alarmid = ?");
		query.setInteger(0, messageid);
		List<MsgAlarm> list = query.list();
		//ts.commit();
		if (list != null) {
			return list.get(0);
		}
		return null;

	}

	public MsgNotice SelectMessageOfNotice(int messageid) {
		session = this.hibernateTemplate.getSessionFactory().getCurrentSession();
		Query query = session.createQuery("from MsgNotice where noticeid = ?");
		query.setInteger(0, messageid);
		List<MsgNotice> list = query.list();
		//ts.commit();
		if (list != null) {
			return list.get(0);
		}
		return null;

	}

	// 插入聊天消息，返回消息编号
	public int insertMessageOfChat(MsgChat chat) {
		session = this.hibernateTemplate.getSessionFactory().getCurrentSession();
		MsgChat c = (MsgChat) this.session.merge(chat);
		this.session.clear();
		//ts.commit();
		return c.getChatid();
	}

	// 插入报警消息，返回消息编号
	public int insertMessageOfAlarm(MsgAlarm alarm) {
		session = this.hibernateTemplate.getSessionFactory().getCurrentSession();
		MsgAlarm a = (MsgAlarm) this.session.merge(alarm);
		this.session.clear();
		//ts.commit();
		return a.getAlarmid();
	}

	// 插入通知消息，返回消息编号
	public int insertMessageOfNotice(MsgNotice notice) {
		session = this.hibernateTemplate.getSessionFactory().getCurrentSession();
		MsgNotice n = (MsgNotice) this.session.merge(notice);
		this.session.clear();
		//ts.commit();
		return n.getNoticeid();
	}

	// 插入消息接受记录，返回记录编号
	public int insertMessageOfMsgRecv(MsgRecv recv) {
		session = this.hibernateTemplate.getSessionFactory().getCurrentSession();
		MsgRecv r = (MsgRecv) this.session.merge(recv);
		this.session.clear();
		//ts.commit();
		return r.getRecvid();
	}

	// 根据特征码修改消息状态为已读
	// i=0全部，1报警,2聊天,4平台
	public void UpdateMessageOfSign(int sid, String sign) {
//		if (i > 3) {
//			Query query = session.createSQLQuery("update msg_recv set state = 1 "
//					+ "where recvuserid = ? and msgtype = 4 limit 0,sign");
//			query.setInteger(0, sid);
//			ts.commit();
//			query.executeUpdate();// 函数返回操作影响的行数
//			System.out.println("更新平台消息状态...");
//		} else if (i > 1) {
//			Query query = session.createSQLQuery("update msg_recv set state = 1 "
//					+ "where recvuserid = ? and msgtype = 2 limit 0,sign");
//			query.setInteger(0, sid);
//			ts.commit();
//			query.executeUpdate();
//			System.out.println("更新聊天消息状态...");
//		} else if (i > 0) {
//			Query query = session.createSQLQuery("update msg_recv set state = 1 "
//					+ "where recvuserid = ? and msgtype = 1 limit 0,sign");
//			query.setInteger(0, sid);
//			ts.commit();
//			query.executeUpdate();
//			System.out.println("更新报警消息状态...");
//		} else {
//			Query query = session.createSQLQuery("update msg_recv set state = 1 "
//					+ "where recvuserid = ?  limit 0,sign");
//			query.setInteger(0, sid);
//			ts.commit();
//			query.executeUpdate();
//			System.out.println("更新消息接受记录...");
//		}
		return ;

	}

	/**
	 * 联系人添加应该有四个状态伴随整个添加过程： 第一种：双方协调添加
	 * 当A需要添加B时，发出添加请求，此时服务器返回已收到请求，将两者的kind值变为0，state也为0
	 * 此时服务器主动通知B用户，根据两者值同为0，附带sign标记 B用户收到添加通知，并返回sign，数据库根据sign，更新state=1
	 * B用户向服务器发出是否添加A的消息，kind值根据是否同意，更新为1或2，服务器返回已收到消息，更新state=2
	 * 服务器主动通知A用户，根据state=2查找，并附sign标记 A用户收到通知，返回给服务器，服务器根据sign更新state=3
	 * 第二种：A用户单方面添加，仅通知B用户 发出添加请求到服务器，kind=4，state=0
	 * 服务器主动通知被添加用户附sign标记，B用户收到消息并返回sign，服务器更新state=1 并将两者的关系改为关联状态 第三种：A解除B用户
	 * 发出解除请求到服务器，kind=3，state=0 服务器主动通知被删除用户附sign标记，B用户收到消息并返回sign，服务器更新state=1
	 * 并将两者的关系改为解除关联状态
	 */

	/**
	 * 在contactwait中以主动添加用户查找 作为主动方，查找时应该查是否有state=2的消息
	 * 
	 * @return
	 */
	public HashMap<String, List<ContactWait>> SelectMessageOfContactWaitMaster(int id) {
		session = this.hibernateTemplate.getSessionFactory().getCurrentSession();
		HashMap<String, List<ContactWait>> hash = new HashMap<String, List<ContactWait>>();
		Query query;
		List<ContactWait> contactWait = null;
		String sign;
		query = session.createQuery("from ContactWait where state = 2 and masterid = ?");
		query.setInteger(0, id);
		//ts.commit();
		int[] ctwt;
		if (!query.list().isEmpty()) {
			List<ContactWait> list = query.list();
			ctwt = new int[list.size()];
			sign = String.valueOf(list.get(list.size() - 1).getCwid());
			hash.put(sign, contactWait);
			return hash;
		}
		//ts.commit();
		return hash;
	}

	/**
	 * 在contactwait中以被添加用户查找 作为被动方，查找时应查是否有state=0的消息
	 * 
	 * @return
	 */
	public HashMap<String, List<ContactWait>> SelectMessageOfContactWaitcontacted(int id) {
		session = this.hibernateTemplate.getSessionFactory().getCurrentSession();
		HashMap<String, List<ContactWait>> hash = new HashMap<String, List<ContactWait>>();
		Query query;
		List<ContactWait> contactWait = null;
		String sign;
		query = session.createQuery("from ContactWait where state = 0 and contactedid = ?");
		query.setInteger(0, id);
		//ts.commit();
		int[] ctwt;
		if (!query.list().isEmpty()) {
			List<ContactWait> list = query.list();
			ctwt = new int[list.size()];
			sign = String.valueOf(list.get(list.size() - 1).getCwid());
			hash.put(sign, contactWait);
			return hash;
		}
		//ts.commit();
		return hash;

	}

	/**
	 * 设备关联有两种种形式： 管理员主动：既管理员主动分享设备给关联人，需要关联人同意
	 * 关联过程有四种状态，既发起分享时，状态为0,当通知到关联人，状态为1，关联人发起主动确认，状态为2，最后主动通知分享设备的管理员，状态为3
	 * 关联人主动申请成为某设备关联人，需要管理员同意 发起申请时状态为0，通知到管理员后，状态为1，管理员主动确认，状态为2，最后通知到申请人，状态为3
	 * 关联过程同管理员主动情况
	 */

	/**
	 * 管理员主动分享 将传入的ID在管理员字段查询，条件state为2或者0，如果有匹配，返回整个对象队列
	 */
	public HashMap<String, List<RelateWait>> SelectMessageOfShare(int id) {
		session = this.hibernateTemplate.getSessionFactory().getCurrentSession();
		HashMap<String, List<RelateWait>> hash = new HashMap<String, List<RelateWait>>();
		Query query;
		List<RelateWait> relateWait = null;
		String sign;
		query = session.createQuery("from RelateWait where state = 2 or state = 0 and applyid = ? and applytype = 1");
		query.setInteger(0, id);
		//ts.commit();
		int[] rtwt;
		if (!query.list().isEmpty()) {
			List<RelateWait> list = query.list();
			rtwt = new int[list.size()];
			sign = String.valueOf(list.get(list.size() - 1).getRwid());
			hash.put(sign, relateWait);
			return hash;
		}
		//ts.commit();
		return hash;

	}

	/**
	 * 关联人主动申请 将传入的ID在请求用户字段查询，条件state为0或者2，如果有匹配，返回整个对象队列
	 * 
	 * @param id
	 * @return
	 */
	public HashMap<String, List<RelateWait>> SelectMessageOfApply(int id) {
		session = this.hibernateTemplate.getSessionFactory().getCurrentSession();
		HashMap<String, List<RelateWait>> hash = new HashMap<String, List<RelateWait>>();
		Query query;
		List<RelateWait> relateWait = null;
		String sign;
		query = session.createQuery("from RelateWait where state = 2 or state = 0 and applyid = ? and applytype = 0");
		query.setInteger(0, id);
		//ts.commit();
		int[] rtwt;
		if (!query.list().isEmpty()) {
			List<RelateWait> list = query.list();
			rtwt = new int[list.size()];
			sign = String.valueOf(list.get(list.size() - 1).getRwid());
			hash.put(sign, relateWait);
			return hash;
		}
		//ts.commit();
		return hash;
	}

}
