package cn.smartpolice.webservice;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.smartpolice.dao.SystemRunInfoDao;
import cn.smartpolice.dao.SystemRunInfoDaoImpl;
import cn.smartpolice.dao.WebserviceInfoDao;

@Service("systemRunInfoService")
@Transactional(readOnly=false)
public class SystemRunInfoServiceImpl implements SystemRunInfoService{

	@Resource(name = "systemRunInfoDao")
	private SystemRunInfoDao systemRunInfoDao;

	@Resource(name = "webserviceInfoDao")
	private WebserviceInfoDao webserviceInfoDao;
	
	@Override
	public List getSystemRunInfoFromDB() {
		return systemRunInfoDao.getSystemRunInfoFromDB();
	}

	@Override
	public List AllServers() {
		List allServers = webserviceInfoDao.findAllWebserviceinfo();
		return allServers;
	}

	@Override
	public List ServerInfo(int id) {
		List ServerInfo = webserviceInfoDao.findServiceInfo(id);
		return ServerInfo;
	}

	@Override
	public void RemoveServer(int id) {
		// TODO Auto-generated method stub
		this.webserviceInfoDao.RemoveServer(id);
		
	}
	
	/*public static void main(String[] args) {
		SystemRunInfoDaoImpl daoImpl = new  SystemRunInfoDaoImpl();
		System.out.println(daoImpl.getSystemRunInfoFromDB());
	}*/
	public List SystemLog() {
		System.out.println("systemruninfoserviceimpl");
		List ServerInfo = webserviceInfoDao.findSystemLog();
		return ServerInfo;
	}
	
	public String deleSystemLog(int id) {
		this.webserviceInfoDao.deleSystemLog(id);
		return "success";
	}

}
