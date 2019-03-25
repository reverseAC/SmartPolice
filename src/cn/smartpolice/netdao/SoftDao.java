package cn.smartpolice.netdao;

import java.util.List;

import cn.smartpolice.hibernate.SoftInfo;

public interface SoftDao {

	// 查找软件 根据类型
	public List<SoftInfo> findSoft(int serial);
}
