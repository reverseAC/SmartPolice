package cn.smartpolice.netdao;


import java.util.List;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import cn.smartpolice.hibernate.UserInfo;

/**
 * UserInf表的操作
 * @author 刘超
 *
 */
@Repository("userDao")
@Transactional(readOnly=false)
public class UserDaoImpl implements UserDao {
	private Session session = HibernateUtil.currentSession();
	//private Transaction ts = session.beginTransaction();
	
	
	//通过id查找
//	public UserInfo findAppuserByID(int id){
//		System.out.println("进入查询函数");
//		List<UserInfo> list = null;
//		Query query = session.createQuery("from UserInfo where userid = ?");
//		query.setInteger(0, id);
//		if(query.list()!=null){
//			list = query.list();
//		}
//		
//		ts.commit();
//		
//		if(list != null && list.size()==1){
//			return list.get(0);
//		}
//		return null;
//	}
	public List selectAllOfCompanyUser() {
		SQLQuery query = session.createSQLQuery("select userid from company_user");
//		query.setLong(0,devid);
		List list = query.list();
		System.out.println("list.size():" + list.size());
		System.out.println(list);
		//ts.commit();
		return list;
	}

	//查出所有user对象
	public List<UserInfo> findAppuser(){
		Query query = session.createQuery("from UserInfo");
		this.session.clear();
		//ts.commit();
		if(query.list() != null/* && list.size()==1*/){
			List<UserInfo> list = query.list();
			return list;
		}
		return null;
	}
	//通过user查找
	public UserInfo findAppuserByName(String name){
		Query query = session.createQuery("from UserInfo where username=?");
		query.setString(0, name);
		List<UserInfo> list = query.list();
		//ts.commit();
		if(list != null && list.size()==1){
			return list.get(0);
		}
		return null;
	}
	//插入记录 该方法有问题：SQLGrammarException
	public int insertAppuser(UserInfo userInf){
		UserInfo user;
		user = (UserInfo) this.session.merge(userInf);
		//ts.commit();
		this.session.clear();
//		return user.getUserID();
		return user.getUserID();
	}
	////置state=0（删除标记 注销协议）
	public void changeStateToZero(int id){
		Query query = session.createQuery("update UserInfo app set app.state = 0 where app.deviceid =?");
		query.setInteger(0, id);
		//ts.commit();
	}
	public void updateAppuser(UserInfo userInf) {
		// TODO Auto-generated method stub
		this.session.update(userInf);
		//ts.commit();
	}
	
	
	
	
	
	
	
	
	//通过userid查找
	public UserInfo findAppuserByID(String user){
		Query query = session.createQuery("from UserInf where userid=?");
		query.setString(0, user);
		List<UserInfo> list = query.list();
		//ts.commit();
		if(list != null && list.size()==1){
			return list.get(0);
		}
		return null;
	}
	@Override
	public UserInfo findAppuserByID(int id) {
		System.out.println("进入查询函数");
		List<UserInfo> list = null;
		Query query = session.createQuery("from UserInfo where userid = ?");
		query.setInteger(0, id);
		if(query.list()!=null){
			list = query.list();
		}
		
		//ts.commit();
		
		if(list != null && list.size()==1){
			return list.get(0);
		}
		return null;
	}
}
