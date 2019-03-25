package cn.smartpolice.dao;

import java.util.ArrayList;
import java.util.List;

import cn.smartpolice.hibernate.CompanyInfo;
import cn.smartpolice.hibernate.CompanyUser;
import cn.smartpolice.hibernate.DeviceAudit;
import cn.smartpolice.hibernate.SoftInfo;

public interface FirmInfoDao {

	
		
	CompanyUser findFirm(String username);


	void register(CompanyUser firm);


	String checkuser(String username);


	CompanyInfo findFirm1(int companyid);
	
	CompanyInfo find(CompanyUser user);


	CompanyUser findCompanyUserByID(int userId);


	


	


	java.util.List company_device_unpass(int companyid);

	java.util.List comment(CompanyUser user);
	/**
	 * @author 马晓林
	 * @param firm
	 * 此方法用于批量删除反馈消息
	 */
	void removeComments(int id);
	
	java.util.List checkComment(int id);
	
	java.util.List   soft_unpass (int uploadid);
	
	void removeDeviceAll(int id);
}
