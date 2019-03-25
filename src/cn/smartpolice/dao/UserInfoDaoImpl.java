package cn.smartpolice.dao;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.classic.Session;
import org.hibernate.transform.Transformers;

import java.util.List;
import java.util.Map;

import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;

import cn.smartpolice.hibernate.DeviceInfo;
import cn.smartpolice.hibernate.UserInfo;

@Repository("userInfoDao")
@Transactional(readOnly=false)
public class UserInfoDaoImpl implements UserInfoDao {

	@Resource(name="hibernateTemplate")
	private HibernateTemplate hibernateTemplate;
	
	@Override
	public List findAllUserinfo(){
				
		Session session=this.hibernateTemplate.getSessionFactory().getCurrentSession();
		
		String sql="select user_inf.userid,user_inf.username,user_inf.`name`,user_inf.type," +
				"user_inf.authority,user_inf.state" +
				" FROM user_inf";
				
		List allUserinfo=(List) session.createSQLQuery(sql).
				setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		
		return allUserinfo;
	}

	@Override
	public List findManagerUserInfo(int userid) {
		Session session=this.hibernateTemplate.getSessionFactory().getCurrentSession();
		String sql="SELECT * from user_inf where userid=? ";
		SQLQuery query=session.createSQLQuery(sql);
		query.setInteger(0,userid);
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		 List allUserInfo = query.list();
		return allUserInfo;	
		}

	@Override
	public void removeCompanyUser(int id) {
		Session session=this.hibernateTemplate.getSessionFactory().getCurrentSession();
		String sql="DELETE from company_user where userid=? ";
		SQLQuery query=session.createSQLQuery(sql);
		query.setInteger(0,id);
		query.executeUpdate();
	
		
	}

	@Override
	public void Removesuer(int id) {
		System.out.println("删除用户");
		Session session=this.hibernateTemplate.getSessionFactory().getCurrentSession();
		String sql="DELETE from user_inf where userid=? ";
		SQLQuery query=session.createSQLQuery(sql);
		query.setInteger(0,id);
		query.executeUpdate();
	}

	@Override
	public List FindAddUser() {
		Session session=this.hibernateTemplate.getSessionFactory().getCurrentSession();
		String sql="select *from user_inf where state like '1'";
		List findAddUser=(List) session.createSQLQuery(sql).
				setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
	
		return findAddUser;
	}

	@Override
	public UserInfo findUserInfo(int userid) {
		// TODO Auto-generated method stub
		Session session=this.hibernateTemplate.getSessionFactory().getCurrentSession();
		String sql="select *from user_inf where userid=? and state='1'";
		SQLQuery query=session.createSQLQuery(sql);
		query.setInteger(0,userid);
		List userInf=query.
				setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		UserInfo user=null;
		if(userInf!=null){
			for(int i=0;i<userInf.size();i++){
				Map map =(Map) userInf.get(i);
				System.out.println(map.toString());
				user = new UserInfo();
				user.setUserID(Integer.parseInt(map.get("userid").toString()));
				System.out.println("userid: "+user.getUserID());
				user.setName(map.get("name").toString());
				System.out.println("name: "+user.getName());
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date date;
				try {
					System.out.println(map.get("regdate").toString());
					date = sdf.parse(map.get("regdate").toString());
					System.out.println(sdf.parse(map.get("regdate").toString()));
					user.setRegDate(date);
					System.out.println("时间："+user.getRegDate());
				} catch (ParseException | java.text.ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				return user;
			}
			}
			return user;

	}
	public List findUser(int userid) {
		// TODO Auto-generated method stub
		Session session=this.hibernateTemplate.getSessionFactory().getCurrentSession();
		String sql="select *from user_inf where userid=? and state='1'";
		SQLQuery query=session.createSQLQuery(sql);
		query.setInteger(0,userid);
		List userInf=query.
				setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();	
		
				return userInf;


	}

	@Override
	public List findUserAll() {
		Session session=this.hibernateTemplate.getSessionFactory().getCurrentSession();
		String sql="select userid from user_inf where state like '1'";
		Query query=session.createSQLQuery(sql);
		List list=query.list();
		System.out.println(list.toString());
		return list;
	}
	
	@Override
	public String updateQues(int id,int num,String answer){
		Session session=this.hibernateTemplate.getSessionFactory().getCurrentSession();
		System.out.println("正在执行更新密保问题操作，用户id为:"+id);
		String ques="";
		if(num==1){
			ques="question_one_answer";
		}else if(num==2){
			ques="question_two_answer";
		}
		String sql="UPDATE manager_inf SET "+ques+"=? WHERE managerId=?";
		SQLQuery query=session.createSQLQuery(sql);
		query.setString(0,answer);
		query.setInteger(1,id);
		query.executeUpdate();
		query.executeUpdate();
		return "success";
	}
}