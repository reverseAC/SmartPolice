package cn.smartpolice.netdao;

import cn.smartpolice.hibernate.DeviceLog;
import cn.smartpolice.hibernate.UserLog;

public interface UserLogDao {

	// 插入记录
	public void insertUserLogInf(UserLog userLog);

	// 通过deviceid和operate=-1查找记录
	public DeviceLog findDevLogByAppIdAndOperate(int appid);

}
