package cn.smartpolice.netdao;


import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import cn.smartpolice.hibernate.DeviceLog;
import cn.smartpolice.hibernate.UserLog;

@Repository("userLogDao")
@Transactional(readOnly=false)
/**
 * 对user_log表的操作
 * @author 刘超
 *
 */
public class UserLogDaoImpl implements UserLogDao{
	private Session session = HibernateUtil.currentSession();
	//private Transaction ts = session.beginTransaction();
	//插入记录
	public void insertUserLogInf(UserLog userLog){
		this.session.clear();
		this.session.save(userLog);
		this.session.flush();
		//ts.commit();
	}
	//通过deviceid和operate=-1查找记录
	public DeviceLog findDevLogByAppIdAndOperate(int appid){
		Query query = session.createQuery("from UserLog where userid = ? and operate = -1");
		query.setInteger(0, appid);
		List<DeviceLog> list = query.list();
		//ts.commit();
		if(list != null && list.size()==1){
			return list.get(0);
		}
		return null;
	}
}
