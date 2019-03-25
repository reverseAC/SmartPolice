package cn.smartpolice.netdao;

/**
 * Device_inf表的操作
 * @author 刘超
 *
 */
import java.io.Serializable;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import cn.smartpolice.hibernate.DeviceInfo;

@Repository("deviceDao")
@Transactional(readOnly=false)
public class DeviceDaoImpl implements DeviceDao {
	private Session session = HibernateUtil.currentSession();
	//private Transaction ts = session.beginTransaction();
	public DeviceInfo findDevByID(int id){
		Query query = session.createQuery("from DeviceInfo where deviceid = ?");
		query.setInteger(0, id);
		List<DeviceInfo> list = query.list();
		//ts.commit();
		if(list != null && list.size()==1){
			return list.get(0);
		}
		return null;
	}
	//通过username查找
	public DeviceInfo findDevByName(String name){
		List<DeviceInfo> list = null;
		Query query = session.createQuery("from DeviceInfo where username=?");
		query.setString(0, name);
		if(query.setString(0, name)!=null){
			list = query.list();
		}
		
		//ts.commit();
		if(list != null && list.size()==1){
			return list.get(0);
		}
		return null;
	}
	//添加记录
	public int insertDevInf(DeviceInfo deviceInf){
		DeviceInfo dev = (DeviceInfo) this.session.merge(deviceInf);
		this.session.clear();
		//ts.commit();
		return dev.getDeviceid();
	}
	//置state=0（删除标记 注销协议）
	public void changeStateToZero(int id){
		Query query = session.createQuery("update DeviceInfo dev set dev.state = 0 where dev.deviceid =?");
		query.setInteger(0, id);
		//ts.commit();
	}
	public void updateDevInf(DeviceInfo deviceInf) {
		// TODO Auto-generated method stub
		this.session.update(deviceInf);
		//ts.commit();
	}
}
