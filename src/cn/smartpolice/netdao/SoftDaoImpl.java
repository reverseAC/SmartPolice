package cn.smartpolice.netdao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import cn.smartpolice.hibernate.SoftInfo;

@Repository("softDao")
@Transactional(readOnly=false)
public class SoftDaoImpl implements SoftDao{
	private Session session = HibernateUtil.currentSession();
	//private Transaction ts = session.beginTransaction();
	//查找软件 根据类型
	public List<SoftInfo> findSoft(int serial){
		Query query = session.createQuery("from SoftInf where type=?");
		query.setInteger(0, serial);
	
		@SuppressWarnings("unchecked")
		List<SoftInfo> list = query.list();
		//ts.commit();
		return list;
		
	}
}

