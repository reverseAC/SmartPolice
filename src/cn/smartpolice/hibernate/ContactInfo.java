package cn.smartpolice.hibernate;

import java.util.Date;


/**
 * 用户联系人信息表contact_inf
 * 
 */
public class ContactInfo {
	
	private int contactId; //联系关系编号
	private int masterId; //主动ID
	private int contactedId; //联系ID
	private String groupName; //分组
	private String alias; //联系人别名
	private Date setTime; //添加日期
	private String state; //联系人状态0等待，1成为，2否认
//	private Set<ContactInf> contactInf;
//	public Set<ContactInf> getContactInf() {
//		return contactInf;
//	}
//
//	public void setContactInf(Set<ContactInf> contactInf) {
//		this.contactInf = contactInf;
//	}

	public int getContactId() {
		return contactId;
	}

	public void setContactId(int contactId) {
		this.contactId = contactId;
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

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public Date getSetTime() {
		return setTime;
	}

	public void setSetTime(Date setTime) {
		this.setTime = setTime;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public ContactInfo() {
		super();
	}

}
