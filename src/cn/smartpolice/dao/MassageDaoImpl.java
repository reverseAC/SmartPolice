package cn.smartpolice.dao;


import java.lang.reflect.Field;
import java.util.Iterator;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.classic.Session;
import org.hibernate.mapping.Array;
import org.hibernate.transform.Transformers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import cn.smartpolice.hibernate.UserInfo;

@Repository("massageDao")
@Transactional(readOnly=false)
public class MassageDaoImpl implements MassageDao {

	@Resource(name="hibernateTemplate")
	private HibernateTemplate hibernateTemplate;
	
	public List findallMsg_alarms(){
				
		Session session=this.hibernateTemplate.getSessionFactory().getCurrentSession();
		
		String sql="SELECT m.alarmid,m.time,d.devicename,m.level FROM msg_alarm AS m,device_audit AS d WHERE m.deviceid=d.auditid";
				
		List allmsg_alarms=(List) session.createSQLQuery(sql).
				setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		
		return allmsg_alarms;
	}

	@Override
	public List findallMsg_chat() {
		Session session=this.hibernateTemplate.getSessionFactory().getCurrentSession();
		String sql="SELECT * FROM msg_chat ";
//		String sql="SELECT u.name, m.chatid,m.sendtime FROM user_inf as u,msg_chat as m ";
//				" WHERE  u.USERID = m.SENDID ";
//		String sql2="SELECT u.name, m.chatid FROM userinfo as u,msg_chat as m "+
//				" WHERE  u.USERID = m.recvid ";
//		List data1=(List) session.createSQLQuery(sql).
//				setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
//		List data2=(List) session.createSQLQuery(sql2).
//				setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
//		List allmsg_chat = new ArrayList();
//		allmsg_chat.add(data1);
//		allmsg_chat.add(data2);
		List allmsg_chat=(List) session.createSQLQuery(sql).
				setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();	
		return allmsg_chat;
		
	}

	@Override
	public List findallMsg_notcie() {
		
		Session session=this.hibernateTemplate.getSessionFactory().getCurrentSession();
		
		String sql="SELECT msg_notice.noticeid,msg_notice.title,user_inf.name,msg_notice.sendtime FROM msg_notice,user_inf"+
					" WHERE msg_notice.sendid=user_inf.userid";
				
		List allmsg_notice=(List) session.createSQLQuery(sql).
				setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		
		return allmsg_notice;
	}

	@Override
	public List findallComment() {

		Session session=this.hibernateTemplate.getSessionFactory().getCurrentSession();
		
		String sql="SELECT m.commentid,n.name,m.date,m.type,m.title " +
				"FROM comment AS m,user_inf AS n WHERE n.userid=m.userid";
				
		List allcomment=(List) session.createSQLQuery(sql).
				setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		
		return allcomment;
	}

	@Override
	public List findAll_msg_alarms(int alarmid) {
		Session session=this.hibernateTemplate.getSessionFactory().getCurrentSession();
		String sql="SELECT * from msg_alarm where alarmid=?";
		SQLQuery query=session.createSQLQuery(sql);
		query.setInteger(0,alarmid);
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		 List allalarmmsgInfo = query.list();
		return allalarmmsgInfo;
	}

	@Override
	public List findAll_msg_chat(int chatid) {
		Session session=this.hibernateTemplate.getSessionFactory().getCurrentSession();
		String sql="SELECT * from msg_chat where chatid=?";
		SQLQuery query=session.createSQLQuery(sql);
		query.setInteger(0,chatid);
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		 List allchatmsgInfo = query.list();
		return allchatmsgInfo;
	}

	@Override
	public List findAll_msg_notice(int noticeid) {
		Session session=this.hibernateTemplate.getSessionFactory().getCurrentSession();
		String sql="SELECT * from msg_notice where noticeid=?";
		SQLQuery query=session.createSQLQuery(sql);
		query.setInteger(0,noticeid);
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		 List allnoticemsgInfo = query.list();
		return allnoticemsgInfo;
	}

	@Override
	public List findAllcomment(int commentid) {
		Session session=this.hibernateTemplate.getSessionFactory().getCurrentSession();
		String sql="SELECT * from comment where commentid=?";
		SQLQuery query=session.createSQLQuery(sql);
		query.setInteger(0,commentid);
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		 List allcommentInfo = query.list();
		return allcommentInfo;
	}

	@Override
	public void RemoveMsg_alarm(int id) {
		// TODO Auto-generated method stub
		Session session=this.hibernateTemplate.getSessionFactory().getCurrentSession();
		String sql="delete from msg_alarm where alarmid=?";
		SQLQuery query=session.createSQLQuery(sql);
		query.setInteger(0,id);
		query.executeUpdate();
	}

	@Override
	public void RemoveMsg_chat(int id) {
		// TODO Auto-generated method stub
		Session session=this.hibernateTemplate.getSessionFactory().getCurrentSession();
		String sql="delete from msg_chat where chatid=?";
		SQLQuery query=session.createSQLQuery(sql);
		query.setInteger(0,id);
		query.executeUpdate();
	}

	@Override
	public void RemoveMsg_notice(int id) {
		// TODO Auto-generated method stub
		Session session=this.hibernateTemplate.getSessionFactory().getCurrentSession();
		String sql="delete from msg_notice where noticeid=?";
		SQLQuery query=session.createSQLQuery(sql);
		query.setInteger(0,id);
		query.executeUpdate();
	}

	@Override
	public void RemoveMsg_comment(int id) {
		// TODO Auto-generated method stub
		Session session=this.hibernateTemplate.getSessionFactory().getCurrentSession();
		String sql="delete from comment where commentid=?";
		SQLQuery query=session.createSQLQuery(sql);
		query.setInteger(0,id);
		query.executeUpdate();
	}

	
}
