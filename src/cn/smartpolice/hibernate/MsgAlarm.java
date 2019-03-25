package cn.smartpolice.hibernate;



import java.util.Date;

/**
 * 设备报警消息表
 * @author 刘超
 *
 */
public class MsgAlarm {

	private int alarmid;
	private Date time; // date
	private int deviceid; //userid发送者
	private String url; //attach
	private int level; //级别
	private int sort;  //发送者类型

	public int getAlarmid() {
		return alarmid;
	}
	public void setAlarmid(int alarmid) {
		this.alarmid = alarmid;
	}

	public int getDeviceid() {
		return deviceid;
	}
	public void setDeviceid(int deviceid) {
		this.deviceid = deviceid;
	}

	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}


	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	public int getSort() {
		return sort;
	}
	public void setSort(int sort) {
		this.sort = sort;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}

}
