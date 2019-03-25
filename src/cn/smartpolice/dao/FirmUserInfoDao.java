package cn.smartpolice.dao;

import java.util.List;

import com.alibaba.fastjson.JSONObject;

import cn.smartpolice.hibernate.CommentInfo;
import cn.smartpolice.hibernate.CompanyInfo;
import cn.smartpolice.hibernate.CompanyUser;
import cn.smartpolice.hibernate.DeviceAudit;
import cn.smartpolice.hibernate.SoftInfo;

public interface FirmUserInfoDao {

	List firmAdminSelf(int id);


	String updateNumber(int id, String number);

	String updateEmail(int id, String email);

	List runInfoDevice(String id);
	List runInfoAudit(int id);
	String updatPassword(int id, String pwd2);


	List firmAdminDevice(int id);
	List runInfoDeinfo(int id);
	List runInfoDeinfo2(String id);
	List firmAdmindeviceInfo(int id);

	int deviceLogs(int id);
	List<SoftInfo> firmAdminSoft(int id);

	List firmAdminSoftInfo(int id);
	
	SoftInfo findSoftInfo(int id);



	List firmAdminmsg(int id);


	List firmAdminmsgInfo(int id);


	void uploadPredeviceinfo(DeviceAudit dev);


	void uploadSoftinfo(SoftInfo soft);


	void firmRemoveSoft(int id);


	void firmRemoveDevice(int id);


	void feedback(CommentInfo comment);

}
