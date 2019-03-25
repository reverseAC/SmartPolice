package cn.smartpolice.webservice;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.smartpolice.hibernate.AdminLog;
import cn.smartpolice.hibernate.ManagerInfo;
import cn.smartpolice.dao.ManagerInfoDao;

@Service("managerInfoService")
@Transactional(readOnly=false)

public class ManagerInfoServiceImp implements ManagerInfoService{
	
	
	@Resource(name="managerInfoDao")
	private ManagerInfoDao managerInfoDao;
	
	@Override
	public ManagerInfo login(String username) {
		ManagerInfo manager=new ManagerInfo();
		
		manager=this.managerInfoDao.findManager(username);
		return manager;
	}

	@Override
	public List managerMsg() {
		List list=this.managerInfoDao.managerMsg();
		return list;
	}
	
	@Override
	public String updatePassword(int id,String password){
		String str=this.managerInfoDao.updatePassword(id, password);
		return "success";
	}

	@Override
	public int login(AdminLog login) {
		// TODO Auto-generated method stub
		return this.managerInfoDao.login(login);
		
	}

	@Override
	public AdminLog time(int managerid) {
		// TODO Auto-generated method stub
		return this.managerInfoDao.time(managerid);
	}

	@Override
	public void logout(AdminLog logout ,int logid) {
		// TODO Auto-generated method stub
		this.managerInfoDao.logout(logout,logid);
	}

}
