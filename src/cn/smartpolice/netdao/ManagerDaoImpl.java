package cn.smartpolice.netdao;

import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
@Repository("managerDao")
@Transactional(readOnly=false)
public class ManagerDaoImpl implements ManagerDao{

	private Session session = HibernateUtil.currentSession();
	public List selectAllOfManager() {
		SQLQuery query = session.createSQLQuery("select managerid from manager_inf");
		List list = query.list();
		System.out.println("list.size():" + list.size());
		System.out.println(list);
		//ts.commit();
		return list;
	}
}
