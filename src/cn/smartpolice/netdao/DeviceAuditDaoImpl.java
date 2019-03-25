package cn.smartpolice.netdao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import cn.smartpolice.hibernate.DeviceAudit;

@Repository("deviceAuditDao")
@Transactional(readOnly=false)
public class DeviceAuditDaoImpl implements DeviceAuditDao {
	private Session session = HibernateUtil.currentSession();
	//private Transaction ts = session.beginTransaction();
	
	public void insertDeviceAudit(DeviceAudit deviceAudit){
		this.session.save(deviceAudit);
		this.session.clear();
		//ts.commit();
		//return n.getNoticeid();
	}
}
