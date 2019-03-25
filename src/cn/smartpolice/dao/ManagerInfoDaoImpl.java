package cn.smartpolice.dao;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.SQLQuery;
import org.hibernate.classic.Session;
import org.hibernate.transform.Transformers;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import cn.smartpolice.hibernate.AdminLog;
import cn.smartpolice.hibernate.CompanyUser;
import cn.smartpolice.hibernate.ManagerInfo;


@Repository("managerInfoDao")
@Transactional(readOnly=false)
public class ManagerInfoDaoImpl implements ManagerInfoDao {
	
	private Session session;
	
	@Resource(name="hibernateTemplate")
	private HibernateTemplate hibernateTemplate;
	
	@Override
	public ManagerInfo findManager(String username) {
		// TODO Auto-generated method stub
		List<ManagerInfo> manager=(List<ManagerInfo>) this.hibernateTemplate.find("from ManagerInfo where username=?",new String(username));
		if(manager.size()!=0){
			return manager.get(0);
		} else{
		return null;
		}
	}

	@Override
	public List managerMsg(){
		
		Session session=this.hibernateTemplate.getSessionFactory().getCurrentSession();
		String sql="SELECT COUNT(*) as a FROM `comment` WHERE handle='0'";
		SQLQuery query=session.createSQLQuery(sql);
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		 List List = query.list();
		return List;
	}
	
	@Override
	public String updatePassword(int id,String password){
		Session session=this.hibernateTemplate.getSessionFactory().getCurrentSession();
		System.out.println(id);
		String sql="UPDATE manager_inf SET password=? WHERE managerId=?";
		SQLQuery query=session.createSQLQuery(sql);
		query.setString(0,password);
		query.setInteger(1,id);
		query.executeUpdate();
		query.executeUpdate();
		return "success";
	}

	@Override
	public int login(AdminLog login) {
		// TODO Auto-generated method stub
		Session session=this.hibernateTemplate.getSessionFactory().getCurrentSession();
		AdminLog  log= (AdminLog) session.merge(login);
		return log.getLogId();
	}

	@Override
	public AdminLog time(int managerid) {
		// TODO Auto-generated method stub
		Session session=this.hibernateTemplate.getSessionFactory().getCurrentSession();		
		String sql = "SELECT * from adminlog where MANAGERID=?"; 
		SQLQuery query=session.createSQLQuery(sql);
		query.setInteger(0,managerid);
		if(query.list()!=null){
			return (AdminLog) query.list().get(0);
		}
		return null;
	}

	@Override
	public void logout(AdminLog logout,int logid) {
		// TODO Auto-generated method stub
		Session session=this.hibernateTemplate.getSessionFactory().getCurrentSession();
		String sql = "UPDATE adminlog SET outtime=? WHERE logid=?";
		SQLQuery query=session.createSQLQuery(sql);
		query.setDate(0, logout.getOuttime());
		query.setInteger(1,logid);
		query.executeUpdate();
	}

	@Override
	public void update(ManagerInfo manager) {
		// TODO Auto-generated method stub
		Session session=this.hibernateTemplate.getSessionFactory().getCurrentSession();
		session.update(manager);
	}

	

}
