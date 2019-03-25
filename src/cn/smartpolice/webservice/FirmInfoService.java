package cn.smartpolice.webservice;

import java.util.ArrayList;
import java.util.List;

import cn.smartpolice.hibernate.CompanyInfo;
import cn.smartpolice.hibernate.CompanyUser;
import cn.smartpolice.hibernate.DeviceAudit;
import cn.smartpolice.hibernate.ManagerInfo;
import cn.smartpolice.hibernate.SoftInfo;

public interface FirmInfoService {
	CompanyUser login(String username);


	void register(CompanyUser firm);


	String checkuser(String username);


	CompanyInfo register(int companyid);
	CompanyInfo find(CompanyUser user);


	CompanyUser findCompanyUserByID(int userId);


	java.util.List devicepas(int a);


	java.util.List comment(CompanyUser firm);
	/**
	 * @author 马晓林
	 * @param firm
	 * 此方法用于批量删除反馈消息
	 */
	void removeComments(int id);
	
	List checkComment(int id);
	
	java.util.List soft_unpass(int uploadid);

	
	void removeDeviceAll(int id);

	//java.util.List<DeviceAudit> devicepas(int a);


	
}
