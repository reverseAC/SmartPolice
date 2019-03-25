package cn.smartpolice.webservice;

import java.util.List;

import cn.smartpolice.hibernate.DeviceInfo;
import cn.smartpolice.hibernate.ManagerInfo;

public interface ManagerAdminService {
	public List findAllCompany();
	public List findAllDevice();
	public List findAllMoveDevice();
	public List findAllUserinfo();
	public List findAllWebservice();
	public List findAllMsg_alarms();
	public List findAllMsg_chat();
	public List findAllMsg_notice();
	public List findAllComment();
	public List CompanyDetail1(int companyid);
	public List PredeviceDetailOfManager(int auditid);
	public List UserDetail1(int userid);
	public List ServiceOfChecked(int serviceid);
	public List All_msg_alarms(int alarmid);
	public List All_msg_chat(int chatid);
	public List All_msg_notice(int noticeid);
	public List Allcomment(int commentid);
	public void removeCompanyUser(int id);
	public void Removedevice(int id);
	public void Removesuer(int id);
	public void RemoveMovedevice(int id);
	public void RemoveMsg_alarm(int id);
	public void RemoveMsg_chat(int id);
	public void RemoveMsg_notice(int id);
	public void RemoveMsg_comment(int id);
	public List FindAddDev();
	public List FindAddUser();
	public List allLogDev(int devid);
	public List logUserAll(int userid);
	public String updateQues(int id,int num,String answer);
	public int getStatNum(int stat,String table,int type);
	public void updateMessage(ManagerInfo info);
}
