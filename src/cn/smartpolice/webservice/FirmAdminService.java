package cn.smartpolice.webservice;

import java.util.List;

import com.alibaba.fastjson.JSONObject;

import cn.smartpolice.hibernate.CommentInfo;
import cn.smartpolice.hibernate.CompanyUser;
import cn.smartpolice.hibernate.DeviceAudit;
import cn.smartpolice.hibernate.SoftInfo;

public interface FirmAdminService {

	public List firmAdminSelf(int id);


	public String updateNumber(int id, String number);


	public String updateEmail(int id, String email);


	public String updatPassword(int id, String pwd2);


	public List firmAdminDevice(int id);


	public List firmAdmindeviceInfo(int id);


	public List<SoftInfo> firmAdminSoft(int id);


	public List firmAdminSoftInfo(int id);
	
	public SoftInfo findSoftInfo(int id);

	public List runInfoAudit(int id);
	
	public List runInfoDevice(String id);

	
	public List runInfoDeinfo(int id);
	
	public int logtimes(int id);
	
	public List runInfoDeinfo2(String id);
	
	public List firmAdminmsg(int id);
	
	public List comment(CompanyUser user);


	public List firmAdminMsgInfo(int id);


	public void uploadPredeviceinfo(DeviceAudit dev);


	public void uploadSoftinfo(SoftInfo soft);


	public void firmRemoveSoft(int id);


	public void firmRemoveDevice(int id);


	public void feedback(CommentInfo comment);



}
