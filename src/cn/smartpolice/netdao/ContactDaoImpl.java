package cn.smartpolice.netdao;

import java.util.Date;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import cn.smartpolice.hibernate.*;

@Repository("contactDao")
@Transactional(readOnly=false)
/**
 * ContactInf表的操作
 */
public class ContactDaoImpl implements ContactDao {
	private Session session = HibernateUtil.currentSession();
	//private Transaction ts = session.beginTransaction();
	
	//插入联系人
	public void insertInfOfContact(ContactInfo contactInf){
		System.out.println("加入联系人表");
		this.session.save(contactInf);
		//ts.commit();
	}

	//通过user查看Contact表是否是联系人 state状态为1
	public ContactInfo selectInfOfByMasteridAndContactid(int userid,int cuserid){
		Query query = session.createQuery("from ContactInfo where masterId = ? and contactedId = ? and state = 1");
		query.setLong(0, userid);
		query.setLong(1, cuserid);
		List<ContactInfo> list = query.list();
		//ts.commit();
		if(list != null && list.size()==1){
			return list.get(0);
		}
		return null;
	}
	
	//判断userid是否有状态为-1的
		public ContactInfo selectContactByUseridAndState(int userid,int cuserid){
			Query query = session.createQuery("from ContactInfo where contactedId = ? and masterId = ? and state = 0");
			query.setLong(0, userid);
			query.setLong(1, cuserid);
			List<ContactInfo> list = query.list();
			//ts.commit();
			if(list != null && list.size()==1){
				return list.get(0);
			}
			return null;
		}
	
	//查询一个user的联系人
	@SuppressWarnings("unchecked")
	public List<ContactInfo> selectInfOfContactByMasterid(int user,byte from,int a){
		Query query = session.createQuery("from ContactInfo where masterId = ? and state = 1");
		query.setLong(0, user);
		query.setFirstResult(a * (int)(from - 1));
		query.setMaxResults(a); 
		List<ContactInfo> list = query.list();
		for(int i =0;i < list.size();i ++){
			System.out.println(list.get(i));
		}
		//ts.commit();	
		return list;
	}	


	//更新表中的一条记录
	public ContactInfo updateInfOfContact(ContactInfo contactInfo,byte kind,String change){
		System.out.println("更新表的一个记录");
        Session session = null;
        try{
        	session = HibernateUtil.currentSession();
        	session.beginTransaction();
			contactInfo = (ContactInfo)session.load(ContactInfo.class,contactInfo.getContactId());//加载联系人主键的编号     
			if((kind & 0x01) == 1){
				contactInfo.setAlias(change);
				session.getTransaction().commit();
			}
			if((kind >> 1 & 0x01) == 1){//更改组名
				contactInfo.setGroupName(change);
				session.getTransaction().commit();
			}	
			if((kind >> 3 & 0x01) == 1){//更改状态
				System.out.println("变更状态");
				contactInfo.setState(change); 
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
	
}


