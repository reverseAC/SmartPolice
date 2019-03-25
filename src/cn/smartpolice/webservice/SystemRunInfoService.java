package cn.smartpolice.webservice;

import java.util.List;

public interface SystemRunInfoService {
	List getSystemRunInfoFromDB();
	public List AllServers();
	public List ServerInfo(int id);
	public void RemoveServer(int id);
	public List SystemLog();
	public String deleSystemLog(int id);
}
