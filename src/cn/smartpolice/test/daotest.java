package cn.smartpolice.test;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.transform.Transformers;
import org.springframework.orm.hibernate3.HibernateTemplate;

import cn.smartpolice.hibernate.DeviceInfo;
import cn.smartpolice.hibernate.MsgNotice;
import cn.smartpolice.netdao.DeviceDao;
import cn.smartpolice.netdao.HibernateUtil;
import cn.smartpolice.netdao.MessageDaoImpl;

public class daotest {
	@Resource(name="deviceDao")
	private static DeviceDao deviceDao;
//	public void setDeviceDao(DeviceDao deviceDao) {
//		this.deviceDao = deviceDao;
//	}
	public static void main(String[] args) {
		
//		DeviceInf dev = new DeviceInf();
//		String userString = "13882758888";
//		dev.setSerial("12345");
//		dev.setCode("1234");
//		dev.setUsername(userString);
//		dev.setPassword("123456");
//		dev.setState("1");
//		dev.setLongitude("12.2222");
//		dev.setLatitude("23.3333");
//		dev.setDate(new Date());
//		dev.setType("1");
//		dev.setMaxconlimit(10);
//		dev.setMphone(userString);
//		new DeviceDao().insertDevInf(dev);
//		System.out.print(new DeviceDao().findDevByName(userString).getDeviceid());
//		UserInf app = new UserInf();
//		String userString = "13882758888";
//		app.setUserName(userString);
//		app.setPassword("12345678");
//		app.setSet("1");
//		app.setRegDate(new Date());
//		app.setAuthority("1");
//		app.setState("1");
//		app.setName("liuchao");
//		app.setBirth(new Date());
//   	app.setSex(true);
//		app.setType("1");
//		app.setMail("124568@qq.com");
//		app.setMphone(userString);
//		new UserDao().insertAppuser(app);
//		System.out.print(new UserDao().findAppuserByName(userString).getUserID());
//	    List<Integer> list = new RelateDao().findUserIdByDeviceId(1);
//	    System.out.print(list);
//		String s[]={"1","2","3"};
//		String s1[]={"1","2","3"};
//		List<String> list=new ArrayList<String>();
//		list.add("1");
//		list.add("2");
//		list.add("3");
//		JSONObject json0 = new JSONObject();//json对象
//		JSONObject json1 = new JSONObject();//json对象
//		JSONArray array = new JSONArray();//json数组
//		json0.put("aa","1");
//		json0.put("bb","2");
//		json1.put("aa","1");
//		json1.put("bb","2");
//		array.add(json0);
//		array.add(json1);
//		//json1.put("info", json1);
//		System.out.println(array);
		

		//DeviceInfo  dev = dao.FindDevInfo(1);
		//DeviceInfo list = deviceDao.findDevByID(1);
		//System.out.println(list.getCode());
//		MessageDaoImpl dao = new MessageDaoImpl();
//		MsgNotice notice = new MsgNotice();
//		notice.setMsg("测试");
//		notice.setTitle("测试");
//		notice.setSendid(1);
//		notice.setSets("UTF-8");
//		dao.insertMessageOfNotice(notice);
		Session session = HibernateUtil.currentSession();
		Transaction tx = session.beginTransaction();
		String sql="SELECT d.devicename,d.code,d.demo,d.date,d.checkid,d.type,d.state,cf.name from" +
				 " device_audit as d,company_user AS cf WHERE d.auditid=? and d.companyId=cf.companyId";
		SQLQuery query=session.createSQLQuery(sql);
		query.setInteger(0,3);
		tx.commit();
		List deviceInfo = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		
		System.out.println(deviceInfo.toString());
		
		
	}
}
