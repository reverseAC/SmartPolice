package cn.smartpolice.webservice;



import java.util.List;

import cn.smartpolice.hibernate.ManagerInfo;


public interface ManagerAuditService {
	
	public List findAuditCompany();//查找待审核的厂商
	public List findAuditDevice();//查找待审核的前端设备
	public List findAuditMoveDevice();//查找待审核的移动设备
	public String PassCompany(int userid);//通过审核
	public String RefusedCompany(int userid);//审核不通过
	public String PassDevice(int auditid);//前端设备审核通过
	public String RefusedDevice(int auditid);//前端设备审核不通过
	public String PassMoveDevice(int auditid);//移动设备审核通过
	public String RefusedMoveDevice(int auditid);//移动设备审核不通过
	public void batchPassCompanyuser(int[] userid);//厂商批量审核通过
	public void batchRefusedCompanyuser(int[] userid);//厂商批量不审核通过
	public void batchDeleteCompanyuser(int[] userid);//厂商批量删除
	public void batchPassPredevice(int[] auditid);//前端设备批量审核通过
	public void batchRefusedPredevice(int[] auditid);//前端设备批量不审核通过
	public void batchDeletePredevice(int[] auditid);//前端设备批量删除
	public void batchPassMovedevice(int[] auditid);//移动设备批量审核通过
	public void batchRefusedMovedevice(int[] auditid);//移动设备批量不审核通过
	public void batchDeleteMovedevice(int[] auditid);//移动设备批量删除
	public void batchDeleteUser(int[] userid);//批量删除注册用户
	public void batchDeleteService(int[] serviceid);
	public void batchDeleteMsgchat(int[] chatid);
	public void batchDeleteMsgalarm(int[] alarmid);
	public void batchDeleteMsgnotice(int[] noticeid);
	public void batchDeleteComment(int[] commentid);
	public List PredeviceDetailOfAuditing(int auditid);//前端设备查看详情
	public List ManagerAuditConpanyInfo(int userid);//厂商用户详细信息（审核）
	public void StopLogDev(int devid);//停用登录设备
	public void StartLogDev(int devid);//启用登录设备
}
