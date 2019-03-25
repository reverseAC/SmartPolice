package cn.smartpolice.netdao;

import cn.smartpolice.hibernate.DeviceLog;

public interface DevLogDao {

	// 添加devLog记录
	public void insertDevLogInf(DeviceLog devLog);

	// 通过deviceid和operate=-1查找记录
	public DeviceLog findDevLogByDevIdAndOperate(int deviceid);
}
