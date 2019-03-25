package cn.smartpolice.hibernate;

public class SystemRunInfo {

	private int infoId;
	private String CurrentUserNum;
	private String InterfaceUserNum;
	private String MaxLoginUserNum;
	private String DeviceLoginNum;
	private String FileOpenedNum;
	private String MaxLoginDeviceNum;
	private String AppLoginNum;
	private String AchiveMsgNum;	//achieve
	private String SendMsgNum;
	
	public int getInfoId() {
		return infoId;
	}
	public void setInfoId(int infoId) {
		this.infoId = infoId;
	}
	public String getCurrentUserNum() {
		return CurrentUserNum;
	}
	public void setCurrentUserNum(String currentUserNum) {
		CurrentUserNum = currentUserNum;
	}
	public String getInterfaceUserNum() {
		return InterfaceUserNum;
	}
	public void setInterfaceUserNum(String interfaceUserNum) {
		InterfaceUserNum = interfaceUserNum;
	}
	public String getMaxLoginUserNum() {
		return MaxLoginUserNum;
	}
	public void setMaxLoginUserNum(String maxLoginUserNum) {
		MaxLoginUserNum = maxLoginUserNum;
	}
	public String getDeviceLoginNum() {
		return DeviceLoginNum;
	}
	public void setDeviceLoginNum(String deviceLoginNum) {
		DeviceLoginNum = deviceLoginNum;
	}
	public String getFileOpenedNum() {
		return FileOpenedNum;
	}
	public void setFileOpenedNum(String fileOpenedNum) {
		FileOpenedNum = fileOpenedNum;
	}
	public String getMaxLoginDeviceNum() {
		return MaxLoginDeviceNum;
	}
	public void setMaxLoginDeviceNum(String maxLoginDeviceNum) {
		MaxLoginDeviceNum = maxLoginDeviceNum;
	}
	public String getAppLoginNum() {
		return AppLoginNum;
	}
	public void setAppLoginNum(String appLoginNum) {
		AppLoginNum = appLoginNum;
	}
	public String getAchiveMsgNum() {
		return AchiveMsgNum;
	}
	public void setAchiveMsgNum(String achiveMsgNum) {
		AchiveMsgNum = achiveMsgNum;
	}
	public String getSendMsgNum() {
		return SendMsgNum;
	}
	public void setSendMsgNum(String sendMsgNum) {
		SendMsgNum = sendMsgNum;
	}
}
