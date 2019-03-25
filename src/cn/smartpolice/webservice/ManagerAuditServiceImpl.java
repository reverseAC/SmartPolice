package cn.smartpolice.webservice;



import javax.annotation.Resource;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.smartpolice.dao.CompanyInfoDao;
import cn.smartpolice.dao.DeviceInfoDao;
import cn.smartpolice.dao.ManagerInfoDao;
import cn.smartpolice.dao.MassageDao;
import cn.smartpolice.dao.SystemRunInfoDao;
import cn.smartpolice.dao.UserInfoDao;
import cn.smartpolice.dao.WebserviceInfoDao;
import cn.smartpolice.hibernate.ManagerInfo;


@Service("managerAuditService")
@Transactional(readOnly=false)
public class ManagerAuditServiceImpl implements ManagerAuditService {
	
	@Resource(name="companyInfoDao")
	private CompanyInfoDao companyInfoDao;
	
	@Resource(name="massageDao")
	private MassageDao massageDao;
	
	
	@Resource(name="deviceInfoDao")
	private DeviceInfoDao deviceInfoDao;
	
	@Resource(name="managerInfoDao")
	private ManagerInfoDao managerInfoDao;
	
	@Resource(name="userInfoDao")
	private UserInfoDao userInfoDao;
	
	@Resource(name = "webserviceInfoDao")
	private WebserviceInfoDao webserviceInfoDao;
	//查找待审核的厂商
	@Override
	public List findAuditCompany() {
		
		List auditCompany = companyInfoDao.findAuditCompany();
		return auditCompany;
	}

	@Override
	public List findAuditDevice() {
		List auditDevice = deviceInfoDao.findAuditDevice();
		return auditDevice;
	}
	@Override
	public List findAuditMoveDevice() {
		List auditMoveDevice = deviceInfoDao.findAuditMoveDevice();
		return auditMoveDevice;
	}

	@Override
	public String PassCompany(int userid) {
		String Companypass = companyInfoDao.Companypass(userid);
		return Companypass;
		
	}

	@Override
	public String RefusedCompany(int userid) {
		String Companyrefused = companyInfoDao.Companyrefused(userid);
		return Companyrefused;
		
	}

	@Override
	public String PassDevice(int auditid) {
		String PassDevice = deviceInfoDao.Devicepass(auditid);
		return PassDevice;
	}

	@Override
	public String RefusedDevice(int auditid) {
		String RefusedDevice = deviceInfoDao.MoveDevicerefused(auditid);
		return RefusedDevice;
	}

	@Override
	public String PassMoveDevice(int auditid) {
		String PassMoveDevice = deviceInfoDao.MoveDevicepass(auditid);
		return PassMoveDevice;
	}

	@Override
	public String RefusedMoveDevice(int auditid) {
		String RefusedMoveDevice = deviceInfoDao.MoveDevicerefused(auditid);
		return RefusedMoveDevice;
	}

	@Override
	public void batchPassCompanyuser(int[] userid) {
		for(int i =0;i<userid.length;i++){
			this.companyInfoDao.Companypass(userid[i]);
		}

		
	}

	@Override
	public void batchRefusedCompanyuser(int[] userid) {
		for(int i =0;i<userid.length;i++){
			this.companyInfoDao.Companyrefused(userid[i]);
		}

	}
	@Override
	public void batchDeleteCompanyuser(int[] userid) {
		for(int i =0;i<userid.length;i++){
			//this.companyInfoDao.Companyrefused(userid[i]);
			this.userInfoDao.removeCompanyUser(userid[i]);
		}
		
	}

	@Override
	public void batchPassPredevice(int[] auditid) {
		for(int i =0;i<auditid.length;i++){
			this.deviceInfoDao.Devicepass(auditid[i]);
		}
		
	}

	@Override
	public void batchRefusedPredevice(int[] auditid) {
		for(int i =0;i<auditid.length;i++){
			this.deviceInfoDao.MoveDevicerefused(auditid[i]);
		}
		
	}
	@Override
	public void batchPassMovedevice(int[] auditid) {
		for(int i =0;i<auditid.length;i++){
			this.deviceInfoDao.MoveDevicepass(auditid[i]);
		}
		
	}
	@Override
	public void batchDeletePredevice(int[] auditid) {
		for(int i =0;i<auditid.length;i++){
			this.deviceInfoDao.Removedevice(auditid[i]);
		}
		
		
	}

	@Override
	public void batchRefusedMovedevice(int[] auditid) {
		for(int i =0;i<auditid.length;i++){
			this.deviceInfoDao.MoveDevicerefused(auditid[i]);
		}
		
	}
	@Override
	public void batchDeleteMovedevice(int[] auditid) {
		for(int i =0;i<auditid.length;i++){
			this.deviceInfoDao.RemoveMovedevice(auditid[i]);;
		}
		
	}

	@Override
	public List PredeviceDetailOfAuditing(int auditid) {
		List PredeviceInfo = deviceInfoDao.findAuditPredeviceInfo(auditid);
		return PredeviceInfo;
	}
	
	@Override
	public void batchDeleteService(int[] serviceid) {
		for(int i =0;i<serviceid.length;i++){
			this.webserviceInfoDao.RemoveServer(serviceid[i]);
		}
		
	}

	@Override
	public List ManagerAuditConpanyInfo(int userid) {
		List alluserInfo = deviceInfoDao.ManagerAuditConpanyInfo(userid);
		return alluserInfo;
	}

	@Override
	public void StopLogDev(int devid) {
		// TODO Auto-generated method stub
		this.deviceInfoDao.StopLogDev(devid);
	}
	@Override
	public void StartLogDev(int devid) {
		// TODO Auto-generated method stub
		this.deviceInfoDao.StartLogDev(devid);
	}

	@Override
	public void batchDeleteUser(int[] userid) {
		for(int i =0;i<userid.length;i++){
			this.userInfoDao.Removesuer(userid[i]);
		}
		
	}

	@Override
	public void batchDeleteMsgchat(int[] chatid) {
		// TODO Auto-generated method stub
		for(int i =0;i<chatid.length;i++){
			this.massageDao.RemoveMsg_chat(chatid[i]);
			}
	}

	@Override
	public void batchDeleteMsgalarm(int[] alarmid) {
		// TODO Auto-generated method stub
		for(int i =0;i<alarmid.length;i++){
			this.massageDao.RemoveMsg_alarm(alarmid[i]);
			}
	}

	@Override
	public void batchDeleteMsgnotice(int[] noticeid) {
		// TODO Auto-generated method stub
		for(int i =0;i<noticeid.length;i++){
			this.massageDao.RemoveMsg_notice(noticeid[i]);
			}
	}

	@Override
	public void batchDeleteComment(int[] commentid) {
		// TODO Auto-generated method stub
		for(int i =0;i<commentid.length;i++){
			this.massageDao.RemoveMsg_comment(commentid[i]);
			}
		
	}

	

	

	

	

}
