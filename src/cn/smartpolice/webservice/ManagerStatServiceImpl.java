package cn.smartpolice.webservice;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.smartpolice.dao.CompanyInfoDao;
import cn.smartpolice.dao.DeviceInfoDao;
import cn.smartpolice.dao.StatDao;
import cn.smartpolice.dao.UserInfoDao;
import cn.smartpolice.hibernate.CompanyUser;
import cn.smartpolice.hibernate.DeviceInfo;
import cn.smartpolice.hibernate.MsgNotice;
import cn.smartpolice.hibernate.MsgRecv;
import cn.smartpolice.hibernate.UserInfo;
import cn.smartpolice.tools.Stattools;


@Service("managerStatService")
@Transactional(readOnly=false)
public class ManagerStatServiceImpl implements ManagerStatService {

	@Resource(name = "statDao")
	private StatDao statDao;

	@Resource(name = "statTool")
	private Stattools statTool;
	
	@Resource(name = "deviceInfoDao")
	private DeviceInfoDao deviceInfoDao;
	
	@Resource(name = "userInfoDao")
	private UserInfoDao userInfoDao;
	
	@Override
	public List getCompanyData() {
		List CompanyData = statDao.statCompanyData();
		return CompanyData;
	}


	@Override
	public List getDeviceData() {
		List DeviceData = statDao.statDeviceData();
		return DeviceData;
	}


	@Override
	public List getMoveDeviceData() {
		List MoveDeviceData = statDao.statMoveDeviceData();
		return MoveDeviceData;
	}


	@Override
	public List getUserinfo() {
		List UserinfoData = statDao.statUserinfoData();
		return UserinfoData;
	}


	@Override
	public List StatLogdev(List<Integer> devIds) {
		List<DeviceInfo> devList=new ArrayList<DeviceInfo>();
		// TODO Auto-generated method stub
		for(int i =0;i<devIds.size();i++){
			devList.add(this.deviceInfoDao.FindDevInfo(devIds.get(i)));
		}
		return devList;
	}


	@Override
	public List StatLogUser(List<Integer> devIds) {
		// TODO Auto-generated method stub
		List<UserInfo> devList=new ArrayList<UserInfo>();
		// TODO Auto-generated method stub
		for(int i =0;i<devIds.size();i++){
			devList.add(this.userInfoDao.findUserInfo(devIds.get(i)));
		}
		return devList;
	}


	@Override
	public List allApp() {
		return this.userInfoDao.findUserAll();
	}


	@Override
	public List allUserManager() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public List allUser() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public List allCompanyUser() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public List allManager() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void insertMsgRecv(int id, MsgRecv msg, MsgNotice notice) {
		// TODO Auto-generated method stub
		
	}

}
