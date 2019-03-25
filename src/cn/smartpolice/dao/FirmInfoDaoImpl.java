package cn.smartpolice.dao;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.management.Query;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.classic.Session;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.transform.Transformers;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sun.net.httpserver.Authenticator.Success;

import cn.smartpolice.hibernate.CommentInfo;
import cn.smartpolice.hibernate.CompanyInfo;
import cn.smartpolice.hibernate.CompanyUser;
import cn.smartpolice.hibernate.DeviceAudit;
import cn.smartpolice.hibernate.ManagerInfo;
import cn.smartpolice.hibernate.SoftInfo;


@Repository("firmInfoDao")
@Transactional(readOnly=false)

public class FirmInfoDaoImpl implements FirmInfoDao{
	@Resource(name="hibernateTemplate")
	private HibernateTemplate hibernateTemplate;
	@Override
	public CompanyUser findFirm(String username) {
		// TODO Auto-generated method stub
		List<CompanyUser> firm=(List<CompanyUser>) this.hibernateTemplate.find("from CompanyUser where username=?",new String(username));
		if(firm.size()!=0){
			return firm.get(0);
		} else{
		return null;
		}
	}
	@Override
	public CompanyInfo findFirm1(int companyid) {
		// TODO Auto-generated method stub
		List<CompanyInfo> firm=(List<CompanyInfo>) this.hibernateTemplate.find("from CompanyInfo where companyid=?",new Integer(companyid));
		if(firm.size()!=0){
			return firm.get(0);
		} else{
		return null;
		}
	}

	@Override
	public void register(CompanyUser firm) {
		this.hibernateTemplate.save(firm);
		
	}

	@Override
	public String checkuser(String username) {
		
	Session session=this.hibernateTemplate.getSessionFactory().getCurrentSession();
		
		String sql="select username from company_user ";
			//获得SQLQuery对象
		  SQLQuery query = session.createSQLQuery(sql);
		  //设定结果结果集中的每个对象为Map类型   
		  query.setResultTransformer(CriteriaSpecification.ALIAS_TO_ENTITY_MAP);
		  //执行查询
		  List list = query.list();
		  System.out.println("List:"+list);
		  System.out.println(username);
		  boolean b = list.contains(username);
		  
		  	
		if(b==true){
			 System.out.println("成功了");		}
		/*else{
			System.out.println(username);
			return "success";
		}
		*/
		  return "error";
	}
	@Override
	public CompanyInfo find(CompanyUser user) {
		CompanyInfo companyid1 = user.getCompanyId();
		int  companyid = companyid1.getCompanyId();
		List<CompanyInfo> firm=(List<CompanyInfo>) this.hibernateTemplate.find("from CompanyInfo where companyid=?",new Integer(companyid));
		if(firm.size()!=0){
			return firm.get(0);
		} else{
		return null;
		}
	}
	@Override
	public CompanyUser findCompanyUserByID(int userId) {
		Session session=this.hibernateTemplate.getSessionFactory().getCurrentSession();
		String sql="select * from company_user where userid=?";
		SQLQuery query=session.createSQLQuery(sql);
		query.setInteger(0,userId);
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		 List user = query.list();
		 System.out.println(user.toString());
		 
		return null;
	}

	@Override
	public List company_device_unpass(int companyid) {	
		List<DeviceAudit> firm=(List<DeviceAudit>) this.hibernateTemplate.find("from DeviceAudit where companyid=? and state= 0",new Integer(companyid));
		//DeviceAudit user=firm.get(0);
		//System.out.print(user.getCheckId());
		return firm;
		
		}
	public List comment(CompanyUser user) {	
		System.out.println("进入查询函数");
		Session session=this.hibernateTemplate.getSessionFactory().getCurrentSession();
		System.out.println("1");
		String sql="select * from comment where userid=?";
		System.out.println("2");
		SQLQuery query=session.createSQLQuery(sql);
		System.out.println("3");
		query.setInteger(0, user.getUserId());
		System.out.println("4");
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		System.out.println("5");
		List list = query.list();
		System.out.println("查到："+list.toString());
		return list;
		
		}
	public List soft_unpass( int  uploadid)
	{
		List<SoftInfo> soft=(List<SoftInfo>) this.hibernateTemplate.find("from SoftInfo where uploadid=?",new Integer(uploadid));
		
		
		return soft;
		
		
	}
	@Override
	public void removeComments(int id) {
		// TODO Auto-generated method stub
		Session session=this.hibernateTemplate.getSessionFactory().getCurrentSession();
		String sql="delete * from comment where commentid=?";
		SQLQuery query=session.createSQLQuery(sql);
		query.setInteger(0, id);
		query.executeUpdate();
	}
	@Override
	public List checkComment(int id) {
		// TODO Auto-generated method stub
		Session session=this.hibernateTemplate.getSessionFactory().getCurrentSession();
		String sql="select * from comment where commentid=?";
		SQLQuery query=session.createSQLQuery(sql);
		query.setInteger(0, id);
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List list = query.list();
		System.out.println(list);
		return list;
	}
	@Override
	public void removeDeviceAll(int id) {
		// TODO Auto-generated method stub
		Session session=this.hibernateTemplate.getSessionFactory().getCurrentSession();
		String sql="delete * from device_audit where auditid=?";
		SQLQuery query=session.createSQLQuery(sql);
		query.setInteger(0, id);
		query.executeUpdate();
	}

	
		
	
	
	
	


}
