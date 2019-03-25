package cn.smartpolice.dao;

import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.SQLQuery;
import org.hibernate.classic.Session;
import org.hibernate.transform.Transformers;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;

import cn.smartpolice.hibernate.CompanyInfo;
import cn.smartpolice.hibernate.DeviceAudit;
import cn.smartpolice.hibernate.DeviceInfo;

@Repository("deviceInfoDao")
@Transactional(readOnly=false)
public  class DeviceInfoDaoImpl implements DeviceInfoDao {

	@Resource(name="hibernateTemplate")
	private HibernateTemplate hibernateTemplate;
	@Override
	public List findAuditDevice() {
		Session session=this.hibernateTemplate.getSessionFactory().getCurrentSession();
		String sql="select dev.auditid,dev.devicename,dev.type,dev.code,com.companyname"
				+" from device_audit as dev,company_inf as com"
			   + " where dev.state like '0' and dev.type like '0' and dev.companyid=com.companyid ";
		List auditDevice=(List) session.createSQLQuery(sql).
				setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		
		return auditDevice;
	}
	@Override
	public List findAllDevice() {
		Session session=this.hibernateTemplate.getSessionFactory().getCurrentSession();
		String sql="select  dev.auditid,dev.devicename,dev.demo,cominf.companyname,comuser.name,dev.state,dev.date"
				+ " from device_audit as dev,company_inf as cominf,company_user as comuser where "
				+ "dev.companyid=cominf.companyid and comuser.companyid=cominf.companyid and "
				+ "dev.type like '0'";
		List allDevice=(List) session.createSQLQuery(sql).
				setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
	
		return allDevice;
	}
	@Override
	public List findAuditMoveDevice() {
		// 查找全部未审核的移动设备
		Session session=this.hibernateTemplate.getSessionFactory().getCurrentSession();
		String sql="select dev.auditid,dev.devicename,dev.demo,dev.code,com.companyname,dev.auditid,dev.type,dev.state"
				+" from device_audit as dev,company_inf as com"
				   + " where dev.state like '0' and dev.type like '1' and dev.companyid=com.companyid ";
		List auditMoveDevice=(List) session.createSQLQuery(sql).
				setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		
		return auditMoveDevice;
	}
	@Override
	public List findAllMoveDevice() {
		Session session=this.hibernateTemplate.getSessionFactory().getCurrentSession();
		String sql="select dev.auditid,dev.devicename,dev.demo,dev.code,"+
				" cominf.companyname,dev.state,cominf.companyid"+
				" from device_audit as dev,company_inf as cominf,company_user as comuser where"+ 
				" dev.type like '1'"+
				" AND dev.COMPANYID=cominf.COMPANYID and dev.COMPANYID=comuser.COMPANYID";;
		List allMoveDevice=(List) session.createSQLQuery(sql).
				setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		
		return allMoveDevice;
	}
	@Override
	public List findAllDev() {
		Session session=this.hibernateTemplate.getSessionFactory().getCurrentSession();
		String sql="select dev.auditid,dev.devicename,dev.demo,cominf.companyname,comuser.username,dev.state"
				+ " from device_audit as dev,company_inf as cominf,company_user as comuser where "
				+ "dev.companyid=cominf.companyid and comuser.companyid=cominf.companyid and "
				+ "dev.type like '1'";
		List allMoveDevice=(List) session.createSQLQuery(sql).
				setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		
		return allMoveDevice;
	}
	@Override
	public String Devicepass(int auditid) {
		Session session=this.hibernateTemplate.getSessionFactory().getCurrentSession();
		String sql="UPDATE device_audit SET state='1' WHERE auditid=?";
		SQLQuery query=session.createSQLQuery(sql);
		query.setInteger(0,auditid);
		query.executeUpdate();
		return "success";
	}

	@Override
	public String Devicerefused(int auditid) {
		Session session=this.hibernateTemplate.getSessionFactory().getCurrentSession();
		String sql="UPDATE device_audit SET state='-1' WHERE auditid=?";
		SQLQuery query=session.createSQLQuery(sql);
		query.setInteger(0,auditid);
		query.executeUpdate();
		return "success";
	}
	@Override
	public String MoveDevicepass(int auditid) {
		Session session=this.hibernateTemplate.getSessionFactory().getCurrentSession();
		String sql="UPDATE device_audit SET state='1' WHERE auditid=?";
		SQLQuery query=session.createSQLQuery(sql);
		query.setInteger(0,auditid);
		query.executeUpdate();
		return "success";
	}

	@Override
	public String MoveDevicerefused(int auditid) {
		Session session=this.hibernateTemplate.getSessionFactory().getCurrentSession();
		String sql="UPDATE device_audit SET state='-1' WHERE auditid=?";
		SQLQuery query=session.createSQLQuery(sql);
		query.setInteger(0,auditid);
		query.executeUpdate();
		return "success";
	}
@Override
	public List findAuditPredeviceInfo(int auditid) {
		Session session=this.hibernateTemplate.getSessionFactory().getCurrentSession();
		String sql="SELECT * FROM device_audit AS d,company_inf as i WHERE d.auditid=?"+
                    " and d.companyid=i.companyid";
		SQLQuery query=session.createSQLQuery(sql);
		query.setInteger(0,auditid);
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		 List PredeviceInfo = query.list();
		return PredeviceInfo;
	}
	@Override
	public List findManagerPredeviceInfo(int auditid) {
		Session session=this.hibernateTemplate.getSessionFactory().getCurrentSession();
		String sql="SELECT d.devicename,d.code,d.demo,d.date,i.companyname,d.auditid,d.type,d.state FROM device_audit AS d,company_inf as i WHERE d.auditid=?"+
                    " and d.companyid=i.companyid";
		SQLQuery query=session.createSQLQuery(sql);
		query.setInteger(0,auditid);
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		 List PredeviceInfo = query.list();
		return PredeviceInfo;
	}
	@Override
	public List ManagerAuditConpanyInfo(int userid) {
		Session session=this.hibernateTemplate.getSessionFactory().getCurrentSession();
		String sql="SELECT * from company_user where userid=? ";
		SQLQuery query=session.createSQLQuery(sql);
		query.setInteger(0,userid);
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		 List alluserInfo = query.list();
		return alluserInfo;
	}
	@Override
	public void Removedevice(int id) {
		Session session=this.hibernateTemplate.getSessionFactory().getCurrentSession();
		String sql="delete from device_audit where auditid=?";
		SQLQuery query=session.createSQLQuery(sql);
		query.setInteger(0,id);
		query.executeUpdate();
		
	}
	@Override
	public void RemoveMovedevice(int id) {
		// TODO Auto-generated method stub
		Session session=this.hibernateTemplate.getSessionFactory().getCurrentSession();
		String sql="delete from device_audit where auditid=?";
		SQLQuery query=session.createSQLQuery(sql);
		query.setInteger(0,id);
		query.executeUpdate();
	}
	@Override
	public List FindAddDev() {
		Session session=this.hibernateTemplate.getSessionFactory().getCurrentSession();
		String sql="select *from device_audit where type like '0' and state like '1'";
		List findAddDevice=(List) session.createSQLQuery(sql).
				setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
	return findAddDevice;
	}
	@Override
	public DeviceInfo FindDevInfo(int devid)  {
		System.out.println("查找用户具体信息");
		Session session=this.hibernateTemplate.getSessionFactory().getCurrentSession();
		System.out.println("创建session");
		String sql="select *from device_inf where deviceid=? and state='1'";
		SQLQuery query=session.createSQLQuery(sql);
		System.out.println("执行查询语句");
		query.setInteger(0,devid);
		DeviceInfo dev = null;
		List list = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		System.out.println(list.toString());
		if(list!=null){
		for(int i=0;i<list.size();i++){
			Map map =(Map) list.get(i);
			dev = new DeviceInfo();
			dev.setCode(map.get("code").toString());	
			System.out.println("code"+dev.getCode());
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date;
			try {
				System.out.println(map.get("date").toString());
				date = sdf.parse(map.get("date").toString());
				dev.setDate(date);
				
			} catch (ParseException | java.text.ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			dev.setDeviceid(Integer.parseInt(map.get("deviceid").toString()));
			dev.setLatitude(map.get("latitude").toString());
			dev.setLongitude(map.get("longitude").toString());
			dev.setMaxconlimit(Integer.parseInt( map.get("maxconlimit").toString()));
			dev.setMphone( map.get("mphone").toString());
			dev.setPassword( map.get("password").toString());
			dev.setSerial( map.get("serial").toString());
			dev.setState(map.get("state").toString());
			dev.setType( map.get("type").toString());
			dev.setUsername( map.get("username").toString());
			System.out.println("密码"+dev.getPassword());
			return dev;
		}
		}
		return dev;

	}

	@Override
	public String StopLogDev(int devid) {
		// TODO Auto-generated method stub
		Session session=this.hibernateTemplate.getSessionFactory().getCurrentSession();
		String sql="UPDATE device_inf SET state='0' WHERE deviceid=?";
		SQLQuery query=session.createSQLQuery(sql);
		query.setInteger(0,devid);
		query.executeUpdate();
		return "success";
	}
	@Override
	public String StartLogDev(int devid) {
		// TODO Auto-generated method stub
		Session session=this.hibernateTemplate.getSessionFactory().getCurrentSession();
		String sql="UPDATE device_inf SET state='1' WHERE deviceid=?";
		SQLQuery query=session.createSQLQuery(sql);
		query.setInteger(0,devid);
		query.executeUpdate();
		return "success";
	}
	@Override
	public List allLogDev(int devid) {
		Session session=this.hibernateTemplate.getSessionFactory().getCurrentSession();
		String sql="select *from device_inf where deviceid='?'";
		SQLQuery query=session.createSQLQuery(sql);
		query.setInteger(0,devid);
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		 List devInfo = query.list();
		return devInfo;
	}
	
	
}
