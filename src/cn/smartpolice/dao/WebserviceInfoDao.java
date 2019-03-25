package cn.smartpolice.dao;

import java.util.List;

public interface WebserviceInfoDao {
	public List findAllWebserviceinfo();

	public List findServiceInfo(int serviceid);
	
	public void RemoveServer(int id);
	
	public List findSystemLog();
	
	public String deleSystemLog(int id);

}
