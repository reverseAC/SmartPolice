package cn.smartpolice.dao;


import java.lang.reflect.Field;
import java.util.Iterator;

import javax.annotation.Resource;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.classic.Session;
import org.hibernate.transform.Transformers;

import java.util.List;
import java.util.Map;

import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import cn.smartpolice.hibernate.UserInfo;

@Repository("webserviceInfoDao")
@Transactional(readOnly=false)
public class WebserviceInfoDaoImpl implements WebserviceInfoDao {

	@Resource(name="hibernateTemplate")
	private HibernateTemplate hibernateTemplate;
	
	public List findAllWebserviceinfo(){
				
		Session session=this.hibernateTemplate.getSessionFactory().getCurrentSession();
		
		String sql="SELECT s.serverid,s.type,s.memory,s.CPU,m.name FROM server_inf AS s,manager_inf AS m"
					+" WHERE s.manager=m.managerid";
				
		List allWebservice=(List) session.createSQLQuery(sql).
				setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		
		return allWebservice;
	}

	@Override
	public List findServiceInfo(int serviceid) {
		Session session=this.hibernateTemplate.getSessionFactory().getCurrentSession();
		String sql="SELECT s.type,s.CPU,s.memory," +
				"s.port,s.ip,s.demo,s.serverid,s.manager from server_inf as s where serverid=?";
		SQLQuery query=session.createSQLQuery(sql);
		query.setInteger(0,serviceid);
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		 List allServiceInfo = query.list();
		return allServiceInfo;
	}

	@Override
	public void RemoveServer(int id) {
		// TODO Auto-generated method stub
		Session session=this.hibernateTemplate.getSessionFactory().getCurrentSession();
		String sql="DELETE from server_inf where serverid=? ";
		SQLQuery query=session.createSQLQuery(sql);
		query.setInteger(0,id);
		query.executeUpdate();
	}
	@Override
	public List findSystemLog() {
		System.out.println("查询系统日志");
		Session session=this.hibernateTemplate.getSessionFactory().getCurrentSession();
		String sql="SELECT * from manage";
		SQLQuery query= session.createSQLQuery(sql);
		query.addScalar("Imanageid", Hibernate.STRING).
			addScalar("managerid", Hibernate.INTEGER).
				addScalar("starttime", Hibernate.TIMESTAMP).
					addScalar("endtime", Hibernate.TIMESTAMP).
						addScalar("ipadr", Hibernate.STRING).
							addScalar("staff", Hibernate.STRING).
								addScalar("state", Hibernate.STRING).
									setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List allsystemLog = query.list();
		System.out.println(allsystemLog.size());
		return allsystemLog;
	}


	public String deleSystemLog(int id) {
		System.out.println("删除系统日志");
		Session session=this.hibernateTemplate.getSessionFactory().getCurrentSession();
		String sql="DELETE from manage where Imanageid = ?";
		SQLQuery query = session.createSQLQuery(sql);
		query.setInteger(0,id);
		query.executeUpdate();
		return "success";
	}
}
