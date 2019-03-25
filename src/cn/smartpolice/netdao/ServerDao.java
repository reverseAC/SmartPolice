package cn.smartpolice.netdao;

import cn.smartpolice.hibernate.ServerInfo;

public interface ServerDao {

	// 查找服务器信息
	public ServerInfo findServer(int id);
}
