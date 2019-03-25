package cn.smartpolice.dao;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.SQLQuery;
import org.hibernate.classic.Session;
import org.hibernate.transform.Transformers;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;

import cn.smartpolice.hibernate.CommentInfo;
import cn.smartpolice.hibernate.DeviceAudit;
import cn.smartpolice.hibernate.DeviceInfo;
import cn.smartpolice.hibernate.SoftInfo;
import net.sf.json.JSONArray;

@Repository("firmUserInfoDao")
@Transactional(readOnly=false)

public class FirmUserInfoDaoImpl implements FirmUserInfoDao{
	@Resource(name="hibernateTemplate")
	private HibernateTemplate hibernateTemplate;

	@Override
	public List firmAdminSelf(int id) {
		Session session=this.hibernateTemplate.getSessionFactory().getCurrentSession();
		String sql="SELECT * from companyuser where userid=?";
		SQLQuery query=session.createSQLQuery(sql);
		query.setInteger(0,id);
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		 List firmuserInfo = query.list();
		return firmuserInfo;
	}

	
	@Override
	public String updateNumber(int id,String number) {
		Session session=this.hibernateTemplate.getSessionFactory().getCurrentSession();
		String sql="UPDATE company_user SET number=? WHERE userid=?";
		System.out.println(number);
		System.out.println(id);
		SQLQuery query=session.createSQLQuery(sql);
		query.setString(0,number);
		query.setInteger(1,id);
		query.executeUpdate();
		return "success";
		
	}

	@Override
	public String updateEmail(int id,String email) {
		Session session=this.hibernateTemplate.getSessionFactory().getCurrentSession();
		System.out.println(id);
		String sql="UPDATE company_user SET email=? WHERE userid=?";
		SQLQuery query=session.createSQLQuery(sql);
		query.setString(0,email);
		query.setInteger(1,id);
		query.executeUpdate();
		query.executeUpdate();
		return "success";
	}


	@Override
	public String updatPassword(int id, String pwd2) {
		Session session=this.hibernateTemplate.getSessionFactory().getCurrentSession();
		System.out.println(id);
		String sql="UPDATE company_user SET password=? WHERE userid=?";
		SQLQuery query=session.createSQLQuery(sql);
		query.setString(0,pwd2);
		query.setInteger(1,id);
		query.executeUpdate();
		query.executeUpdate();
		return "success";
	}


	@Override
	public List firmAdminDevice(int id) {
		Session session=this.hibernateTemplate.getSessionFactory().getCurrentSession();
		String sql="SELECT d.*,c.name from"+  
				 " device_audit as d,company_user AS c WHERE c.userid=? and c.companyid=d.companyid";
		
		SQLQuery query=session.createSQLQuery(sql);
		query.setInteger(0,id);
		List  deviceInfo=  query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		 
		return deviceInfo;
	}


	@Override
	public List firmAdmindeviceInfo(int id) {
		Session session=this.hibernateTemplate.getSessionFactory().getCurrentSession();
		String sql="SELECT d.devicename,d.code,d.demo,d.date,d.checkid,d.type,d.state,cf.name from" +
				 " device_audit as d,company_user AS cf WHERE d.auditid=? and d.companyId=cf.companyId";
		SQLQuery query=session.createSQLQuery(sql);
		query.setInteger(0,id);
		List deviceInfo = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		System.out.println(deviceInfo.toString());
		return deviceInfo;
	}
	@Override
	public List runInfoAudit(int id) {
		// TODO Auto-generated method stub
		Session session=this.hibernateTemplate.getSessionFactory().getCurrentSession();
		String sql="SELECT da.devicename,da.auditid,da.code from" +
				 " device_audit AS da WHERE da.companyid=?";
		SQLQuery query=session.createSQLQuery(sql);
		query.setInteger(0,id);
		List deviceInfo = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		System.out.println(deviceInfo.toString());
		return deviceInfo;
	}
	@Override
	public List runInfoDevice(String id) {
		// TODO Auto-generated method stub
		Session session=this.hibernateTemplate.getSessionFactory().getCurrentSession();
		String sql="SELECT di.deviceid from" +
				 " device_inf as di WHERE di.code=?";
		SQLQuery query=session.createSQLQuery(sql);
		query.setString(0, id);
		List deviceInfo = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		System.out.println(deviceInfo.toString());
		return deviceInfo;
	}

	
	@Override
	public List runInfoDeinfo(int id) {
		// TODO Auto-generated method stub
		Session session=this.hibernateTemplate.getSessionFactory().getCurrentSession();
		String sql="SELECT da.code from" +
				 " device_audit as da WHERE da.auditid=?";
		SQLQuery query=session.createSQLQuery(sql);
		query.setInteger(0,id);
		List deviceInfo = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		System.out.println(deviceInfo.toString());
		return deviceInfo;
	}

	@Override
		public List <SoftInfo>firmAdminSoft(int id) {
			System.out.println(id);
			Session session=this.hibernateTemplate.getSessionFactory().getCurrentSession();
			String sql="SELECT d.softid,d.name,d.type,d.date,d.state,d.serial,c.username FROM soft_inf AS d,company_user AS c "+
							"WHERE d.uploadid=c.userid and c.userid=?";
			SQLQuery query=session.createSQLQuery(sql);
			query.setInteger(0,id);
			query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			 List <SoftInfo>softInfo = query.list();
//			 System.out.println("softInfo"+softInfo);
			return softInfo;
		}


		@Override
		public List firmAdminSoftInfo(int id) {
			Session session=this.hibernateTemplate.getSessionFactory().getCurrentSession();
			String sql="SELECT *,c.username from soft_inf as s,company_user as c where softid=? and s.uploadid=c.userid";
			SQLQuery query=session.createSQLQuery(sql);
			query.setInteger(0,id);
			query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			 List softInfo = query.list();
			 System.out.println("softInfo"+softInfo);
			return softInfo;
		}
		
		@Override
		public SoftInfo findSoftInfo(int id){
			List<SoftInfo> allsoftInfo=(List<SoftInfo>)this.hibernateTemplate.find("from SoftInfo where softid=?",new Integer(id));
			if(allsoftInfo!=null){
				return allsoftInfo.get(0);
			}else{
				return null;
			}
		}



	@Override
	public List firmAdminmsg(int id) {
		Session session=this.hibernateTemplate.getSessionFactory().getCurrentSession();
		String sql="select * from msg_notice where sendid=? ";
		SQLQuery query=session.createSQLQuery(sql);
		query.setInteger(0,id);
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		 List firmmsg = query.list();
		 System.out.println("firmmsg"+firmmsg);
		return firmmsg;
	}


	@Override
	public List firmAdminmsgInfo(int id) {
		Session session=this.hibernateTemplate.getSessionFactory().getCurrentSession();
		String sql="select a.name,b.sendtime,b.content from msg_notice as b,manager_inf as a where noticeid=?";
		SQLQuery query=session.createSQLQuery(sql);
		query.setInteger(0,id);
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		 List firmmsg = query.list();
		 System.out.println("firmmsg"+firmmsg);
		return firmmsg;
	}


	@Override
	public void uploadPredeviceinfo(DeviceAudit dev) {
		this.hibernateTemplate.save(dev);
		
	}


	@Override
	public void uploadSoftinfo(SoftInfo soft) {
		this.hibernateTemplate.save(soft);
		
	}


	@Override
	public void firmRemoveSoft(int id) {
		Session session=this.hibernateTemplate.getSessionFactory().getCurrentSession();
		String sql="delete from softinfo where softid=?";
		SQLQuery query=session.createSQLQuery(sql);
		query.setInteger(0,id);
		query.executeUpdate();
		
	}


	@Override
	public void firmRemoveDevice(int id) {
		Session session=this.hibernateTemplate.getSessionFactory().getCurrentSession();
		String sql="delete from device_audit where auditid=?";
		SQLQuery query=session.createSQLQuery(sql);
		query.setInteger(0,id);
		query.executeUpdate();
		
		
	}


	@Override
	public void feedback(CommentInfo comment) {
		// TODO Auto-generated method stub
		Session session=this.hibernateTemplate.getSessionFactory().getCurrentSession();
		session.save(comment);
	}


	@Override
	public List runInfoDeinfo2(String id) {
		// TODO Auto-generated method stub
		Session session=this.hibernateTemplate.getSessionFactory().getCurrentSession();
		String sql="SELECT di.deviceid,di.serial,di.type,di.date,di.state from" +
				 " device_inf as di WHERE di.code=?";
		SQLQuery query=session.createSQLQuery(sql);
		query.setString(0, id);
		List deviceInfo = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		System.out.println("id是"+id+"...查到的di"+deviceInfo.toString());
		return deviceInfo;
	}


	@Override
	public int deviceLogs(int id) {
		// TODO Auto-generated method stub
		Session session=this.hibernateTemplate.getSessionFactory().getCurrentSession();
		String sql="SELECT dl.operate from" +
				 " device_log as dl WHERE dl.deviceid=?";
		SQLQuery query=session.createSQLQuery(sql);
		query.setInteger(0, id);
		List deviceInfo = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		int count=0;
		for(int i=0;i<deviceInfo.size();i++){
			Map Moperate = (Map)deviceInfo.get(i);
			int operate = (int) Moperate.get("operate");
			if(operate==1){
				count++;
			}
		}
		return count;
	}


	

	
}
