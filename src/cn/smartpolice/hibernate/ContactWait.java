package cn.smartpolice.hibernate;

import java.util.Date;
/**
	等待联系确认表contact_wait
 *
 */

public class ContactWait {
	private int cwid;//联系编号
	private int masterId;//主用户
	private int contactedId;//联系用户
	private String groupName;//所在组名
	private Date applyTime;//申请时间
	private String state; //0发出添加消息，1收到添加消息，2审核添加消息，4反馈添加结果
	private String kind;  //0未审核，2拒绝添加，1同意添加
	private String message;//留言
	public int getCwid() {
		return cwid;
	}
	public void setCwid(int cwid) {
		this.cwid = cwid;
	}
	public int getMasterId() {
		return masterId;
	}
	public void setMasterId(int masterId) {
		this.masterId = masterId;
	}
	public int getContactedId() {
		return contactedId;
	}
	public void setContactedId(int contactedId) {
		this.contactedId = contactedId;
	}

	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public Date getApplyTime() {
		return applyTime;
	}
	public void setApplyTime(Date applyTime) {
		this.applyTime = applyTime;
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
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
}
