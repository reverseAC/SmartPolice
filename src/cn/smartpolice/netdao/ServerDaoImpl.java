package cn.smartpolice.netdao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import cn.smartpolice.hibernate.ServerInfo;

@Repository("serverDao")
@Transactional(readOnly=false)
/**
 * 对server_inf表的操作
 * @author 刘超
 *
 */
public class ServerDaoImpl implements ServerDao{
	
	private Session session = HibernateUtil.currentSession();
	//private Transaction ts = session.beginTransaction();
	//查找服务器信息
	public ServerInfo findServer(int id){
		
		Query query = session.createQuery("from ServerInfo where serverId = ?");
		query.setInteger(0, id);
		List<ServerInfo> list = query.list();
		//ts.commit();
		if(list != null && list.size()==1){
			return list.get(0);
		}
		return null;
	}
}
