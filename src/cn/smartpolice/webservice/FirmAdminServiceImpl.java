package cn.smartpolice.webservice;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;

import cn.smartpolice.dao.FirmInfoDao;
import cn.smartpolice.dao.FirmUserInfoDao;
import cn.smartpolice.hibernate.CommentInfo;
import cn.smartpolice.hibernate.CompanyUser;
import cn.smartpolice.hibernate.DeviceAudit;
import cn.smartpolice.hibernate.SoftInfo;


@Service("firmAdminService")
@Transactional(readOnly=false)
public class FirmAdminServiceImpl  implements FirmAdminService{
	
	
	@Resource(name="firmUserInfoDao")
	private FirmUserInfoDao firmUserInfoDao;
	@Resource(name="firmInfoDao")
	private FirmInfoDao firmInfoDao;
	
	@Override
	public List firmAdminSelf(int id) {
		List firmsuserInfo = firmUserInfoDao.firmAdminSelf(id);
		return firmsuserInfo;
	}


	@Override
	public String updateNumber(int id,String number) {
		String str = firmUserInfoDao.updateNumber(id,number);
		return str;
	}

	@Override
	public String updateEmail(int id,String email) {
		String str = firmUserInfoDao.updateEmail(id,email);
		return str;
		
	}


	@Override
	public String updatPassword(int id, String pwd2) {
		String str = firmUserInfoDao.updatPassword(id,pwd2);
		return str;
	}





	@Override
	public List firmAdmindeviceInfo(int id) {
		List deviceInfo = firmUserInfoDao.firmAdmindeviceInfo(id);
		return deviceInfo;
	}


	@Override
	public List<SoftInfo> firmAdminSoft(int id) {
		List <SoftInfo>softInfo = firmUserInfoDao.firmAdminSoft(id);
		return softInfo;
	}


	@Override
	public List firmAdminSoftInfo(int id) {
		List softInfo = firmUserInfoDao.firmAdminSoftInfo(id);
		return softInfo;
	}
	
	@Override
	public SoftInfo findSoftInfo(int id){
		SoftInfo softInfo=firmUserInfoDao.findSoftInfo(id);
		return softInfo;
	}



	@Override
	public List firmAdminmsg(int id) {
		List firmmsg = firmUserInfoDao.firmAdminmsg(id);
		return firmmsg;
	}


	@Override
	public List firmAdminMsgInfo(int id) {
		List firmmsg = firmUserInfoDao.firmAdminmsgInfo(id);
		return firmmsg;
	}


	@Override
	public void uploadPredeviceinfo(DeviceAudit dev) {
		this.firmUserInfoDao.uploadPredeviceinfo(dev);
		
	}


	@Override
	public void uploadSoftinfo(SoftInfo soft) {
		this.firmUserInfoDao.uploadSoftinfo(soft);
		
	}


	@Override
	public void firmRemoveSoft(int id) {
		this.firmUserInfoDao.firmRemoveSoft(id);	
		}


	@Override
	public void firmRemoveDevice(int id) {
		this.firmUserInfoDao.firmRemoveDevice(id);
		
	}


	@Override
	public void feedback(CommentInfo comment) {
		this.firmUserInfoDao.feedback(comment);
		
	}


	@Override
	public List firmAdminDevice(int id) {
		// TODO Auto-generated method stub
		List deviceInfo =  firmUserInfoDao.firmAdminDevice(id);
			
	
		return deviceInfo;
	}


	@Override
	public List comment(CompanyUser user) {
		// TODO Auto-generated method stub
		List msg = this.firmInfoDao.comment(user);
		return msg;
	}


	@Override
	public List runInfoAudit(int id) {
		// TODO Auto-generated method stub
		List runInfo = this.firmUserInfoDao.runInfoAudit(id);
		return runInfo;
	}

	@Override
	public List runInfoDevice(String id) {
		// TODO Auto-generated method stub
		List runInfo = this.firmUserInfoDao.runInfoDevice(id);
		return runInfo;
	}


	@Override
	public List runInfoDeinfo(int id) {
		// TODO Auto-generated method stub
		if(id!=-1){
			List runInfoDeinfo = this.firmUserInfoDao.runInfoDeinfo(id);
			return runInfoDeinfo;
		}
		return null;
		
	}


	@Override
	public List runInfoDeinfo2(String id) {
		// TODO Auto-generated method stub
		List runInfoDeinfo = this.firmUserInfoDao.runInfoDeinfo2(id);
		return runInfoDeinfo;
	}


	@Override
	public int logtimes(int id) {
		// TODO Auto-generated method stub
		int count = this.firmUserInfoDao.deviceLogs(id);
		return count;
	}
	
}
