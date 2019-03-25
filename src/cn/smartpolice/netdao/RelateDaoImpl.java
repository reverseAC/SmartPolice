package cn.smartpolice.netdao;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import cn.smartpolice.hibernate.RelateInfo;
import cn.smartpolice.hibernate.UserInfo;

@Repository("relateDao")
@Transactional(readOnly=false)
/**
 * 关联人表操作
 * @author 宣少宇
 *
 */

public class RelateDaoImpl implements RelateDao {

	private Session session = HibernateUtil.currentSession();
	//private Transaction ts = session.beginTransaction();
	public List<RelateInfo> findUserIdByDeviceId(int deviceid){
		String sql = "select userid from relate_inf where deviceid = ?";
		Query query = session.createSQLQuery(sql).setInteger(0, deviceid);
		if(query.list()!=null){
			return query.list();
		}
		return null;
	}
	public List selectMangerOfDevice() {
		SQLQuery query = session.createSQLQuery("select userid from relate_inf where authority = '0'");
//		query.setLong(0,devid);
		List list = query.list();
		System.out.println("list.size():" + list.size());
		System.out.println(list);
		//ts.commit();
		return list;
	}

	//插入联系人
	public void insertRelateInf(RelateInfo relateInf){
		System.out.println("加入关联人表");
		this.session.save(relateInf);
		//ts.commit();
		session.clear();//清空session 
		System.out.println("加入成功");
	}
	
	//查询一个dev(puser)中所有的关联人
	public List<RelateInfo> findallRelateInfBypuser(int puser,byte state){
		System.out.println("进入到查询");
		Query query = session.createQuery("from RelateInfo where deviceId = ? and state = ?");
		query.setLong(0,puser);
		query.setByte(1,state);
		List<RelateInfo> list = query.list();
		System.out.println("list.size():" + list.size());
		//ts.commit();	
		return list;		
	}
	
	//查询muser通过puser
	public RelateInfo findallRelateInfBypuser(int puserid){
		System.out.println("进入到查询");
		Query query = session.createQuery("from RelateInfo where deviceId = ? and authority = 0");
		query.setLong(0,puserid);
		List<RelateInfo> list = query.list();
		System.out.println("list.size():" + list.size());
		//ts.commit();	
		if(list != null && list.size()==1){
			return list.get(0);
		}
		return null;		
	}
	//查询一个dev(puser)中所有的管理关联人
	public List<RelateInfo> findallRelateInfBypuserandauthority(int puserid){
		System.out.println("进入到查询");
		Query query = session.createQuery("from RelateInfo where deviceId = ? and authority IN ('1','0','2') and state = 1");
		query.setLong(0,puserid);
		List<RelateInfo> list = query.list();
		System.out.println("list.size():" + list.size());
		System.out.println(list);
		//ts.commit();	
		return list;		
	}

	//查询一个user中所有的dev
	public List<RelateInfo> findallRelateInfByuser(int userid){
		Query query = session.createQuery("from RelateInfo where userId = ? and state = 1");
		query.setLong(0,userid);
		List<RelateInfo> list = query.list();
		System.out.println("list.size():" + list.size());
		System.out.println(list);
//		for(int i = 0;i < list.size();i ++){
//			System.out.println(list.get(i));
//		}
		//ts.commit();	
		return list;		
	}

	//查询user和puser的这条记录
	public RelateInfo findRelateInfByuserandpuser(String user,String puser){
		System.out.println("进入到查询");
		Query query = session.createQuery("from RelateInfo where userId = ? and deviceId = ?");
		query.setString(0, user);
		query.setString(1, puser);
		List<RelateInfo> list = query.list();
		//ts.commit();
		if(list != null && list.size()==1){
			return list.get(0);
		}
		return null;
	}

	//查询一个user与puser是否关联
	public RelateInfo findRelateInfByuserandmuser(int peer,int puserid,byte state){
		System.out.println("进入到查询");
		Query query = session.createQuery("from RelateInfo where userId = ? and deviceId = ? and state = ?");
		query.setLong(0, peer);
		query.setLong(1, puserid);
		query.setByte(2, state);
		List<RelateInfo> list = query.list();
		System.out.println("list.size():" + list.size());
		//ts.commit();
		if(list != null && list.size()==1){
			return list.get(0);
		}
		return null;
	}

	//更新表中的一条记录
	public RelateInfo updateRelateInf(RelateInfo relateInf,byte kind,String change){
			
		Session session = null;
	    try{
	        session = HibernateUtil.currentSession();
	        session.beginTransaction();
	        relateInf = (RelateInfo)session.load(RelateInfo.class, relateInf.getRelateId());//加载联系人主键的编号     
	        if((kind & 0x01) == 1){//变更别名
	        	relateInf.setAlias(change);
	        	relateInf.setSetTime(new Date());
	        	session.getTransaction().commit();
	        }
	        if((kind >> 1 & 0x01) == 1){//变更密码
	       		relateInf.setPassWord(change);
	       		relateInf.setSetTime(new Date());
				session.getTransaction().commit();
			}		
	        if((kind >> 2 & 0x01) == 1){//变更权限
	       		relateInf.setAuthority(change);
	       		relateInf.setSetTime(new Date());
				session.getTransaction().commit();
	       	}	
	        if((kind >> 3 & 0x01) == 1){//变更联系人状态
	        	relateInf.setState(change);
	        	relateInf.setSetTime(new Date());
				session.getTransaction().commit();
	        }
	    }catch(Exception e){
	        e.printStackTrace();
	        session.getTransaction().rollback();
	    }finally{
	      	HibernateUtil.closeSession();
	    }
	    return null;
	}
	public  List selectAllMannager(int userid){
		Query query = session.createQuery("from RelateInfo and authority = 1");

		if(query.list() != null){
			System.out.println(query.list().toString());
		}
		return null;
	}

	
	public RelateInfo updateRelateInf(RelateInfo relateInf,RelateInfo relateInf1){
		Session session = null;
	    try{
	        session = HibernateUtil.currentSession();
	        session.beginTransaction();
	        relateInf = (RelateInfo)session.load(RelateInfo.class, relateInf.getRelateId());//加载联系人主键的编号     
        	relateInf.setAlias(relateInf1.getAlias());
       		relateInf.setPassWord(relateInf1.getPassWord());	
       		relateInf.setAuthority(relateInf1.getAuthority());	
        	relateInf.setSetTime(relateInf1.getSetTime());
			session.getTransaction().commit();
	  	    }catch(Exception e){
	        e.printStackTrace();
	        session.getTransaction().rollback();
	    }finally{
	      	HibernateUtil.closeSession();
	    }
		return null;
	}
	
	public Object deleteRelateInfBypuser(int puserid){
		Query query = session.createQuery("delete RelateInfo where deviceId = ? and state = -1");
		query.setLong(0,puserid);
		query.executeUpdate();
		//ts.commit();	
		return null;		
	}


}
