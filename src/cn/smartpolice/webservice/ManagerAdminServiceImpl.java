package cn.smartpolice.webservice;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.smartpolice.dao.CompanyInfoDao;
import cn.smartpolice.dao.DeviceInfoDao;
import cn.smartpolice.dao.ManagerInfoDao;
import cn.smartpolice.dao.MassageDao;
import cn.smartpolice.dao.StatDao;
import cn.smartpolice.dao.UserInfoDao;
import cn.smartpolice.dao.WebserviceInfoDao;
import cn.smartpolice.hibernate.DeviceInfo;
import cn.smartpolice.hibernate.ManagerInfo;
import cn.smartpolice.hibernate.UserInfo;

@Service("managerAdminService")
@Transactional(readOnly=false)
public class ManagerAdminServiceImpl implements ManagerAdminService{

	@Resource(name="companyInfoDao")
	private CompanyInfoDao companyInfoDao;
	
	@Resource(name="deviceInfoDao")
	private DeviceInfoDao deviceInfoDao;
	
	@Resource(name="userInfoDao")
	private UserInfoDao userInfoDao;
	
	@Resource(name="webserviceInfoDao")
	private WebserviceInfoDao webserviceInfoDao;
	
	@Resource(name="massageDao")
	private MassageDao massageDao;
	
	@Resource(name="statDao")
	private StatDao statDao;
	
	@Resource(name="managerInfoDao")
	private ManagerInfoDao managerInfoDao;
	
	@Override
	public List findAllCompany() {
		List allCompany = companyInfoDao.findAllCompany();
		return allCompany;
	}

	@Override
	public List findAllDevice() {
		List allDevice = deviceInfoDao.findAllDevice();
		return allDevice;
	}
	@Override
	public List findAllMoveDevice() {
		List allMoveDevice = deviceInfoDao.findAllMoveDevice();
		return allMoveDevice;
	}

	@Override
	public List findAllUserinfo() {
		List allUserInfo = userInfoDao.findAllUserinfo();
		return allUserInfo;
	}

	@Override
	public List findAllWebservice() {
		List allWebservice = webserviceInfoDao.findAllWebserviceinfo();
		return allWebservice;
	}

	@Override
	public List findAllMsg_alarms() {
		List allmsg_alarms = massageDao.findallMsg_alarms();
		return allmsg_alarms;
	}

	@Override
	public List findAllMsg_chat() {
		List allmsg_chat = massageDao.findallMsg_chat();
		return allmsg_chat;
	}

	@Override
	public List findAllMsg_notice() {
		List allmsg_notice = massageDao.findallMsg_notcie();
		return allmsg_notice;
	}

	@Override
	public List findAllComment() {
		List allcomment = massageDao.findallComment();
		return allcomment;
	}

	@Override
	public List CompanyDetail1(int companyid) {
		List allCompanyInfo = companyInfoDao.findAllCompanyInfo(companyid);
		return allCompanyInfo;
	}

	@Override
	public List PredeviceDetailOfManager(int auditid) {
		List PredeviceInfo = deviceInfoDao.findManagerPredeviceInfo(auditid);
		return PredeviceInfo;
	}

	@Override
	public List UserDetail1(int userid) {
		List allUserInfo = userInfoDao.findManagerUserInfo(userid);
		return allUserInfo;
	}

	@Override
	public List ServiceOfChecked(int serviceid) {
		List allServiceInfo = webserviceInfoDao.findServiceInfo(serviceid);
		return allServiceInfo;
	}

	@Override
	public List All_msg_alarms(int alarmid) {
		List allalarmmsgInfo = massageDao.findAll_msg_alarms(alarmid);
		return allalarmmsgInfo;
	}

	@Override
	public List All_msg_chat(int chatid) {
		List allchatmsgInfo = massageDao.findAll_msg_chat(chatid);
		return allchatmsgInfo;
	}

	@Override
	public List All_msg_notice(int noticeid) {
		List allnoticemsgInfo = massageDao.findAll_msg_notice(noticeid);
		return allnoticemsgInfo;
	}

	@Override
	public List Allcomment(int commentid) {
		List allcommentInfo = massageDao.findAllcomment(commentid);
		return allcommentInfo;
	}

	@Override
	public void removeCompanyUser(int id) {
		this.userInfoDao.removeCompanyUser(id);
		
	}

	@Override
	public void Removedevice(int id) {
		this.deviceInfoDao.Removedevice(id);
		
	}

	@Override
	public void Removesuer(int id) {
		this.userInfoDao.Removesuer(id);
		
	}

	@Override
	public void RemoveMovedevice(int id) {
		// TODO Auto-generated method stub
		this.deviceInfoDao.RemoveMovedevice(id);
	}

	@Override
	public void RemoveMsg_alarm(int id) {
		// TODO Auto-generated method stub
		this.massageDao.RemoveMsg_alarm(id);
	}

	@Override
	public void RemoveMsg_chat(int id) {
		// TODO Auto-generated method stub
		this.massageDao.RemoveMsg_chat(id);
	}

	@Override
	public void RemoveMsg_notice(int id) {
		// TODO Auto-generated method stub
		this.massageDao.RemoveMsg_notice(id);
	}

	@Override
	public void RemoveMsg_comment(int id) {
		// TODO Auto-generated method stub
		this.massageDao.RemoveMsg_comment(id);
	}

	@Override
	public List FindAddDev() {
		List findAddDev=this.deviceInfoDao.FindAddDev();
		return findAddDev;
	}

	@Override
	public List FindAddUser() {
		List findAddUser=this.userInfoDao.FindAddUser();
		return findAddUser;
	}

	@Override
	public List allLogDev(int devid) {
		List devInfo=this.deviceInfoDao.allLogDev(devid);
		return devInfo;
	}

	@Override
	public List logUserAll(int userid) {
		// TODO Auto-generated method stub
		List userInfo = this.userInfoDao.findUser(userid);
		return userInfo;
	}

	@Override
	public String updateQues(int id,int num,String answer){
		String mark = userInfoDao.updateQues(id, num, answer);
		if(mark.equals("success")){
			return "success";
		}else{
			return "error";
		}
	}
	
	@Override
	public int getStatNum(int stat,String table,int type){
		int num=statDao.getStatNum(stat,table,type);
		return num;
	}

	@Override
	public void updateMessage(ManagerInfo info) {
		// TODO Auto-generated method stub
		this.managerInfoDao.update(info);
	}

	

}
