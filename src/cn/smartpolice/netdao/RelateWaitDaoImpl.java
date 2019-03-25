package cn.smartpolice.netdao;

import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import cn.smartpolice.hibernate.ContactWait;
import cn.smartpolice.hibernate.RelateWait;

@Repository("relateWaitDao")
@Transactional(readOnly=false)
public class RelateWaitDaoImpl implements RelateWaitDao{
	private Session session = HibernateUtil.currentSession();
	//private Transaction ts = session.beginTransaction();

	//插入等待确认联系人
	public String insertRelateWait(RelateWait relateWait){
		this.session.save(relateWait);
		//ts.commit();
		return "0";
	}
	
	//查询通过askid和state（状态）    
    public RelateWait findRelateWaitBymuserAndState(String muser,byte state){
		Query query = session.createQuery("from RelateWait where askId = ? and state = ?");
		query.setString(0, muser);
		query.setByte(1, state);
		List<RelateWait> list = query.list();
		//ts.commit();
		if(list != null && list.size()==1){
			return list.get(0);
		}
		return null;
    }
//    public static void main(String[] args) {
//    	RelateWait relateWait = new RelateWaitDao().findRelateWaitBypeer(1,2);
//    	new RelateWaitDao().updateRelateWait(relateWait,(byte) 3);
//	}
  //更改关联人等待表的状态
  	public String updateRelateWait(RelateWait relateWait,String state){	
          Session session = null;
          try{
          	session = HibernateUtil.currentSession();
          	session.beginTransaction();
          	relateWait = (RelateWait)session.load(RelateWait.class, relateWait.getRwid());//加载联系人等待主键的编号     
          	relateWait.setState(state);
  			session.getTransaction().commit();	
          }catch(Exception e){
              e.printStackTrace();
              session.getTransaction().rollback();
          }finally{
          	HibernateUtil.closeSession();
          }
  		return "0";
      }
  	
    public RelateWait findRelateWaitBypeer(int peer,int puserid){
		Query query = session.createQuery("from RelateWait where applyId = ? and askId = ?");
		query.setLong(0, peer);
		query.setLong(1, puserid);
		List<RelateWait> list = query.list();
		//ts.commit();
		System.out.println("list.size():" + list.size());
		if(list != null && list.size()==1){
			return list.get(0);
		}
		return null;
	}
	
    
	//查询通过applyId和state（状态）    
    public RelateWait findRelateWaitByuserAndState(String user,byte state){
		Query query = session.createQuery("from RelateWait where applyId = ? and state = ?");
		query.setString(0, user);
		query.setByte(1, state);
		List<RelateWait> list = query.list(
				);
		//ts.commit();
		if(list != null && list.size()==1){
			return list.get(0);
		}
		return null;
    }


}
