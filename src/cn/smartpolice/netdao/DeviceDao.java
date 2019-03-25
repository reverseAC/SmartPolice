package cn.smartpolice.netdao;

import cn.smartpolice.hibernate.DeviceInfo;

public interface DeviceDao {

	public DeviceInfo findDevByID(int id);

	// 通过username查找
	public DeviceInfo findDevByName(String name);

	// 添加记录
	public int insertDevInf(DeviceInfo deviceInf);

	// 置state=0（删除标记 注销协议）
	public void changeStateToZero(int id);

	public void updateDevInf(DeviceInfo deviceInf);
}
