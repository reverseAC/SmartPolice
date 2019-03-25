package cn.smartpolice.webservice;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.smartpolice.dao.FirmInfoDao;
import cn.smartpolice.hibernate.CompanyInfo;
import cn.smartpolice.hibernate.CompanyUser;
import cn.smartpolice.hibernate.DeviceAudit;
import cn.smartpolice.hibernate.ManagerInfo;
import cn.smartpolice.hibernate.SoftInfo;


@Service("firmInfoService")
@Transactional(readOnly=false)

public class FirmInfoServiceImpl  implements FirmInfoService {
	
	
	@Resource(name="firmInfoDao")
	private FirmInfoDao firmInfoDao;

	@Override
	public CompanyUser login(String username) {
		
		CompanyUser firm=new CompanyUser();
		
		firm=this.firmInfoDao.findFirm(username);
		return firm;
		
	}

	@Override
	public CompanyInfo register(int companyid) {
		
		CompanyInfo firm=new CompanyInfo();
		
		firm=this.firmInfoDao.findFirm1(companyid);
		return firm;
		
	}


	@Override
	public void register(CompanyUser firm) {
		this.firmInfoDao.register(firm);
		
	}




	@Override
	public String checkuser(String username) {
		String str=this.firmInfoDao.checkuser(username);
		return str;
		
	}

	@Override
	public CompanyInfo find(CompanyUser user) {
		
		CompanyInfo firm=new CompanyInfo();
		
		firm=this.firmInfoDao.find(user);
		return firm;
	}

	@Override
	public CompanyUser findCompanyUserByID(int userId) {
		CompanyUser firm=new CompanyUser();
		
		firm=this.firmInfoDao.findCompanyUserByID(userId);
		return firm;
	}
	
	@Override
	public List devicepas( int companyid) {
		
		
		List list=this.firmInfoDao.company_device_unpass(companyid);
		return  list;
	
	}
	@Override
	public List comment(CompanyUser userid ) {
		
		List list=this.firmInfoDao.comment(userid);
		return list;	
		
	}
	@Override
	public List soft_unpass( int uploadid) {
		
		List list=this.firmInfoDao.soft_unpass(uploadid);
		return list;	

}

	@Override
	public void removeComments(int id ) {
		// TODO Auto-generated method stub
		this.firmInfoDao.removeComments(id);
	}

	@Override
	public List checkComment(int id) {
		// TODO Auto-generated method stub
		List list=this.firmInfoDao.checkComment(id);
		return list;
	}

	@Override
	public void removeDeviceAll(int id) {
		// TODO Auto-generated method stub
		this.firmInfoDao.removeDeviceAll(id);
	}
}