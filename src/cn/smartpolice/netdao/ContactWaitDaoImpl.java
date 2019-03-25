package cn.smartpolice.netdao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import cn.smartpolice.hibernate.ContactInfo;
import cn.smartpolice.hibernate.ContactWait;

@Repository("contactWaitDao")
@Transactional(readOnly=false)
/**
 * contact_wait的处理
 * @author caicai
 *
 */
public class ContactWaitDaoImpl implements ContactWaitDao{
	private Session session = HibernateUtil.currentSession();
	//private Transaction ts = session.beginTransaction();
	
	//插入等待确认联系人
	public String insertInfOfContactWait(ContactWait contactWait){
		this.session.save(contactWait);
		//ts.commit();
		session.clear();
		return null;
	}
	
	//删除确认过的等待联系人的请求
	public ContactWait deleteInfOfContactWait(ContactWait contactWait){
		Session session = null;
		
		try{
			session = HibernateUtil.currentSession();
			session.beginTransaction();
			contactWait =(ContactWait)session.load(ContactWait.class, contactWait.getCwid());//加载联系人主键的编号     
			session.delete(contactWait);
			session.getTransaction().commit();
		}catch (HibernateException e) { 
			 e.printStackTrace();  
	         session.getTransaction().rollback();
		} finally{
            if (session != null){  
                if (session.isOpen()){  
                    session.close();  
                }  
            }  
        }  
		return null;
	}
	
	//查询ContactWait表
	public ContactWait selectInfOfContactWaitByMasteridAndContactid(int userid, int cuserid, byte state){
		Query query = session.createQuery("from ContactWait where contactedId= ? and masterId = ? and state = ?");
		query.setLong(0, userid);
		query.setLong(1, cuserid);
		query.setString(2, Byte.toString(state));
		List<ContactWait> list = query.list();
		//ts.commit();
		if(list != null && list.size()==1){
			return list.get(0);
		}
		return null;
	}

	//更改联系人等待表的状态
	public String updateInfOfContactWait(ContactWait contactWait,byte state){	
        Session session = null;
        try{
        	session = HibernateUtil.currentSession();
        	session.beginTransaction();
        	contactWait = (ContactWait)session.load(ContactWait.class, contactWait.getCwid());//加载联系人等待主键的编号     
        	contactWait.setState(Byte.toString(state));
			session.getTransaction().commit();	
        }catch(Exception e){
            e.printStackTrace();
            session.getTransaction().rollback();
        }finally{
        	HibernateUtil.closeSession();
        }
		return "0";
    }
	
    //查询通过contactid和state（状态）    
    public ContactWait selectInfOfContactWaitByContactidAndState(String cuser,byte state){
		Query query = session.createQuery("from ContactWait where contactId = ? and state = ?");
		query.setString(0, cuser);
		query.setByte(1, state);
		List<ContactWait> list = query.list();
		//ts.commit();
		if(list != null && list.size()==1){
			return list.get(0);
		}
		return null;
    }
    
    //查询通过masterid和state（状态）
    public ContactWait selectInfOfContactWaitByMasteridAndState(String user,byte state){
		Query query = session.createQuery("from ContactWait where masterId = ? and state = ?");
		query.setString(0, user);
		query.setByte(1, state);
		List<ContactWait> list = query.list();
		//ts.commit();
		if(list != null && list.size()==1){
			return list.get(0);
		}
		return null;
    }


}
