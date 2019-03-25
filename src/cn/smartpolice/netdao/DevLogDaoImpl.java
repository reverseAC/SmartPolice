package cn.smartpolice.netdao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import cn.smartpolice.hibernate.DeviceLog;

@Repository("devLogDao")
@Transactional(readOnly=false)
/**
 * 对device_log表的操作
 * @author 刘超
 *
 */
public class DevLogDaoImpl implements DevLogDao {
	private Session session = HibernateUtil.currentSession();
	//private Transaction ts = session.beginTransaction();
	//添加devLog记录
	public void insertDevLogInf(DeviceLog devLog){
		this.session.clear();
		this.session.save(devLog);
		//ts.commit();
	}
	//通过deviceid和operate=-1查找记录
	public DeviceLog findDevLogByDevIdAndOperate(int deviceid){
		Query query = session.createQuery("from DeviceLog where deviceid = ? and operate = -1");
		query.setInteger(0, deviceid);
		List<DeviceLog> list = query.list();
		//ts.commit();
		if(list != null && list.size()==1){
			return list.get(0);
		}
		return null;
	}
}
