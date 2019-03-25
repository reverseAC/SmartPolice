package cn.smartpolice.hibernate;

import java.util.Date;

public class RelateWait {
	private int rwid;//联系编号
	private int applyId;//请求用户
	private int deviceId;//关联设备
	private int askId;//确认用户
	private boolean applyType;//0申请，1分享
	private String applyRight;//关联关系：0代表管理权限，1为操控权限，2为查看权限，3为接收权限
	private Date applytime;//请求时间
	private Date ackTime;//确认时间
	private String state;//状态
	private String kind;//0请求确认；1用户确认；2、用户否认；3 解除通知；4 请求通知（不需要用户确认）
	//private String password;//密码
	private String message;//留言

	public int getRwid() {
		return rwid;
	}
	public void setRwid(int rwid) {
		this.rwid = rwid;
	}
	public int getApplyId() {
		return applyId;
	}
	public void setApplyId(int applyId) {
		this.applyId = applyId;
	}
	public int getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(int deviceId) {
		this.deviceId = deviceId;
	}
	public int getAskId() {
		return askId;
	}
	public void setAskId(int askId) {
		this.askId = askId;
	}
	public boolean isApplyType() {
		return applyType;
	}
	public void setApplyType(boolean applyType) {
		this.applyType = applyType;
	}
	public String getApplyRight() {
		return applyRight;
	}
	public void setApplyRight(String applyRight) {
		this.applyRight = applyRight;
	}
	public Date getApplytime() {
		return applytime;
	}
	public void setApplytime(Date applytime) {
		this.applytime = applytime;
	}
	public Date getAckTime() {
		return ackTime;
	}
	public void setAckTime(Date ackTime) {
		this.ackTime = ackTime;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getKind() {
		return kind;
	}
	public void setKind(String kind) {
		this.kind = kind;
	}
	public RelateWait() {
		super();
	}
}
