package cn.smartpolice.protocol;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.mina.core.buffer.IoBuffer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.smartpolice.dao.DeviceInfoDaoImpl;
import cn.smartpolice.hibernate.DeviceInfo;
import cn.smartpolice.hibernate.RelateInfo;
import cn.smartpolice.hibernate.RelateWait;
import cn.smartpolice.hibernate.UserInfo;
import cn.smartpolice.netdao.DeviceDao;
import cn.smartpolice.netdao.MessageDao;
import cn.smartpolice.netdao.RelateDao;
import cn.smartpolice.netdao.RelateWaitDao;
import cn.smartpolice.netdao.UserDao;
import cn.smartpolice.tools.JsonAnalysis;
import cn.smartpolice.workbean.AppNode;
import cn.smartpolice.workbean.PacketInfo;
import cn.smartpolice.workbean.SysInfo;

//关联管理协议 cmd=9
public class ProtocolRelate extends ProtocolBase {
	
//	@Resource(name="relateDao")
//	private RelateDao relateDao;
//	
//	@Resource(name="userDao")
//	private UserDao userDao;
//	
//	@Resource(name="deviceDao")
//	private DeviceDao deviceDao;
//	
//	@Resource(name="relateWaitDao")
//	private RelateWaitDao relateWaitDao;
	
	ApplicationContext ac = new ClassPathXmlApplicationContext("/spring/applicationContext.xml");
	UserDao userDao = (UserDao) ac.getBean("userDao");
	RelateDao relateDao = (RelateDao) ac.getBean("relateDao");
	DeviceDao deviceDao = (DeviceDao) ac.getBean("deviceDao");
	RelateWaitDao relateWaitDao = (RelateWaitDao) ac.getBean("relateWaitDao");

	boolean ref; // 1表示管理员分享，0表示自我申请
	String user;// 申请者帐号
	String muser; // 管理关联人账号
	String puser; // 前端设备账号
	String alias; // 设备别名
	byte right; // 权限代码：0管理权限，1操控权限，2查看权限，3接收权限
	String pass; // 登录前端设备密码，为0或者没有这个选项表示只登记到平台的接收权限
	String info; // 要求对方强制更新时，传递到对方的关联人信息，格式为下表形式((关联人帐号，权限，别名，密码，设置日期时间，状态)，.......)表中的密码为0表示不许登录设备状态：0待审核，1通过，-1拒绝
	byte kind; // 获取类型
	int peer;// 通信对方的ID（批准者或者请求者）
	String sign;
	String message;// 留言数据库中写的有
	JSONObject relate = new JSONObject();
	JSONArray jsonArray = new JSONArray();

	Date relatetDate;// 添加时间
	Date deleteDate;// 脱离时间
	Date updateDate;// 变更时间

	int errorPktState;

	@Override
	void ParsePktProto(PacketInfo packetInfo) {
		System.out.println("进入到cmd=9");
		this.revPacket = packetInfo;
		String data = revPacket.getData();
		JsonAnalysis jsonAnalysis = new JsonAnalysis();
		// 关联账号请求
		if (revPacket.getType() == ConstParam.TYPE_1) {
			System.out.println(data);
			ref = Boolean.parseBoolean(jsonAnalysis.getValue(data, "REF"));
			System.out.println("ref:" + ref);
			if (ref == true) {// 管理员分享
				user = jsonAnalysis.getValue(data, "USER");
				muser = jsonAnalysis.getValue(data, "MUSER");
				puser = jsonAnalysis.getValue(data, "PUSER");
				right = Byte.parseByte(jsonAnalysis.getValue(data, "RIGHT"));
				message = jsonAnalysis.getValue(data, "MESSAGE");
			} else {
				user = jsonAnalysis.getValue(data, "USER");
				puser = jsonAnalysis.getValue(data, "PUSER");
				alias = jsonAnalysis.getValue(data, "ALIAS");
				right = Byte.parseByte(jsonAnalysis.getValue(data, "RIGHT"));
				pass = jsonAnalysis.getValue(data, "PASS");
				message = jsonAnalysis.getValue(data, "MESSAGE");
			}
		}
		// 脱联账号请求
		if (revPacket.getType() == ConstParam.TYPE_3) {
			ref = Boolean.parseBoolean(jsonAnalysis.getValue(data, "REF"));
			if (ref == true) {
				user = jsonAnalysis.getValue(data, "USER");
				muser = jsonAnalysis.getValue(data, "MUSER");
				puser = jsonAnalysis.getValue(data, "PUSER");
			} else {
				user = jsonAnalysis.getValue(data, "USER");
				puser = jsonAnalysis.getValue(data, "PUSER");
			}
		}
		// 获取关联账号请求
		if (revPacket.getType() == ConstParam.TYPE_5) {
			user = jsonAnalysis.getValue(data, "USER");
			kind = Byte.parseByte(jsonAnalysis.getValue(data, "KIND"));
			if (kind == 1 || kind == 2) {
				puser = jsonAnalysis.getValue(data, "PUSER");
			}
			System.out.println("puser:" + puser);
		}
		// 关联确认/脱联通知请求
		if (revPacket.getType() == ConstParam.TYPE_7) {
			ref = Boolean.parseBoolean(jsonAnalysis.getValue(data, "REF"));
			System.out.println("ref:" + ref);
			if (ref == true) {// 管理员分享 对方同意
				peer = Integer.parseInt(jsonAnalysis.getValue(data, "PEER"));
				puser = jsonAnalysis.getValue(data, "PUSER");
				kind = Byte.parseByte(jsonAnalysis.getValue(data, "KIND"));
				if (kind != -1) {
					pass = jsonAnalysis.getValue(data, "PASS");
					message = jsonAnalysis.getValue(data, "MESSAGE");
					alias = jsonAnalysis.getValue(data, "ALIAS");
				}
			} else {// 自我申请 管理员同意
				peer = Integer.parseInt(jsonAnalysis.getValue(data, "PEER"));
				puser = jsonAnalysis.getValue(data, "PUSER");
				kind = Byte.parseByte(jsonAnalysis.getValue(data, "KIND"));
				if (kind != -1) {
					right = Byte.parseByte(jsonAnalysis.getValue(data, "RIGHT"));
					message = jsonAnalysis.getValue(data, "MESSAGE");
				}
			}
		}
		// 关联同步请求
		if (revPacket.getType() == ConstParam.TYPE_9) {
			user = jsonAnalysis.getValue(data, "USER");
			kind = Byte.parseByte(jsonAnalysis.getValue(data, "KIND"));
			info = jsonAnalysis.getValue(data, "INFO");
		}
		// 变更关联信息请求
		if (revPacket.getType() == ConstParam.TYPE_11) {
			ref = Boolean.parseBoolean(jsonAnalysis.getValue(data, "REF"));
			System.out.println("ref:" + ref);
			if (ref == true) {// 管理关联人变更
				user = jsonAnalysis.getValue(data, "USER");
				muser = jsonAnalysis.getValue(data, "MUSER");
				puser = jsonAnalysis.getValue(data, "PUSER");
				kind = Byte.parseByte(jsonAnalysis.getValue(data, "KIND"));
				right = Byte.parseByte(jsonAnalysis.getValue(data, "RIGHT"));
			} else {// 自己变更
				user = jsonAnalysis.getValue(data, "USER");
				puser = jsonAnalysis.getValue(data, "PUSER");
				System.out.println("puser:" + puser);
				kind = Byte.parseByte(jsonAnalysis.getValue(data, "KIND"));
				if (kind == 1) {
					alias = jsonAnalysis.getValue(data, "ALIAS");
				}
				if (kind == 2) {
					pass = jsonAnalysis.getValue(data, "PASS");
				}
			}
		}
		this.ExecProto();
	}

	public void ExecProto() {
		System.out.println("进入到关联人管理协议");
		if (revPacket.getType() == ConstParam.TYPE_1) {
			System.out.println(puser);
			int puserid = deviceDao.findDevByName(puser).getDeviceid();
			if (ref == true) {// 管理员分享
				System.out.println("管理员分享");
				AppNode muserNode = SysInfo.getInstance().getAppNodeById(revPacket.getSid());
				UserInfo userinfo = userDao.findAppuserByName(user);
				if (userinfo == null) {
					System.out.println("不存在这个联系人");
					errorPktState = ConstParam.ADD_ERROR_2;// 不存在这个用户
					byte[] errorPacket = PackPkt(ConstParam.CONTACT_2);
					SendPkt(errorPacket);
					muserNode.setLastPacketInfo(errorPacket);
				} else {
					int userid = userinfo.getUserID();
					if (user != null && muser != null && puser != null && Byte.toString(right) != null) {
						System.out.println("信息完整");
						List<RelateInfo> relateInf = (List<RelateInfo>) relateDao.findallRelateInfBypuser(puserid,
								(byte) 1);
						System.out.println("获取到了关联表");
						if (relateInf.size() == 10) {
							System.out.println("关联人达到上线");
							errorPktState = ConstParam.ADD_ERROR_2;//// 关联错误
																	//// 关联人数过多
							byte[] errorPacket = PackPkt(ConstParam.CONTACT_2);
							SendPkt(errorPacket);
						} else {
							System.out.println("关联人没达到上线");
							RelateInfo relateInf2 = relateDao.findRelateInfByuserandmuser(userid, puserid,
									(byte) 1);
							if (relateInf2 == null) {
								// 不存在这个关联人
								Date applyTime = new Date();// 获取当时时间
								RelateInfo relateinf = new RelateInfo();
								relateinf.setUserId(userid);
								relateinf.setDeviceId(puserid);
								relateinf.setPassWord(null);
								relateinf.setSetTime(null);
								relateinf.setAuthority(Byte.toString(right));
								relateinf.setState("0");
								relateinf.setAlias(puser);// 此时没有别名，就默认为设备名字
								relateDao.insertRelateInf(relateinf);
								;

								RelateWait relateWait = new RelateWait();
								relateWait.setApplyId(revPacket.getSid());
								relateWait.setDeviceId(puserid);
								relateWait.setAskId(userid);
								relateWait.setApplyType(ref);
								relateWait.setApplyRight(Byte.toString(right));
								relateWait.setApplytime(applyTime);
								relateWait.setAckTime(null);
								relateWait.setState("1");
								relateWait.setKind("0");
								relateWait.setMessage(message);
								sign = relateWaitDao.insertRelateWait(relateWait);

								AppNode userNode = SysInfo.getInstance().getAppNodeByAccount(user);
								if (userNode != null) {
									byte[] packPkt1 = ProtocolMessage.pushMsgNitice(userNode, user, puser, applyTime,
											kind, right, pass, message, sign);
									userNode.getIoSession().write(IoBuffer.wrap(packPkt1));// 发送给对方
									userNode.setLastPacketInfo(packPkt1);// 更新缓存
								}
								byte[] successPkt = PackPkt(ConstParam.RELATE_0);// 等待确认
								SendPkt(successPkt);
								muserNode.setLastPacketInfo(successPkt);// 更新缓存
							} else {
								byte[] Associated = PackPkt(ConstParam.RELATE_1);// 已存在这个关联人
								SendPkt(Associated);
								muserNode.setLastPacketInfo(Associated);// 更新缓存
							}
						}
					} else {
						errorPktState = ConstParam.ADD_ERROR_0;// 关联错误 信息不完全
						byte[] errorPacket = PackPkt(ConstParam.CONTACT_2);
						SendPkt(errorPacket);
						muserNode.setLastPacketInfo(errorPacket);// 更新缓存
					}
				}
			} else {// 自我申请
				System.out.println("自我申请");
				AppNode userNode = SysInfo.getInstance().getAppNodeById(revPacket.getSid());
				if (user != null && puser != null && Byte.toString(right) != null && pass != null) {
					System.out.println("信息完整");
					List<RelateInfo> relateInf1 = (List<RelateInfo>) relateDao.findallRelateInfBypuser(puserid,
							(byte) 1);
					System.out.println("获取到了关联表");
					if (relateInf1.size() == 10) {
						System.out.println("关联人达到上线");
						errorPktState = ConstParam.ADD_ERROR_1;// 关联错误 关联人数过多
						byte[] errorPacket = PackPkt(ConstParam.CONTACT_2);
						SendPkt(errorPacket);
					} else {
						System.out.println("关联人没达到上线");
						RelateInfo relateInf2 = relateDao.findRelateInfByuserandmuser(revPacket.getSid(), puserid,
								(byte) 1);

						if (relateInf2 == null) {
							System.out.println("不存在这个关联人");
							Date applyTime = new Date();// 获取当时时间
							RelateInfo relateinf = new RelateInfo();
							relateinf.setUserId(revPacket.getSid());
							relateinf.setDeviceId(puserid);
							relateinf.setPassWord(pass);
							relateinf.setSetTime(null);
							relateinf.setAuthority(Byte.toString(right));
							relateinf.setState("0");
							relateinf.setAlias(alias);
							relateDao.insertRelateInf(relateinf);

							RelateWait relateWait = new RelateWait();
							relateWait.setApplyId(revPacket.getSid());
							relateWait.setDeviceId(puserid);
							relateWait.setAskId(relateDao.findallRelateInfBypuser(puserid).getUserId());
							relateWait.setApplyType(ref);
							relateWait.setApplyRight(Byte.toString(right));
							relateWait.setApplytime(applyTime);
							relateWait.setState("1");
							relateWait.setKind("0");
							relateWait.setMessage(message);
							sign = relateWaitDao.insertRelateWait(relateWait);
							AppNode muserNode = SysInfo.getInstance().getAppNodeByAccount(
									relateDao.findallRelateInfBypuser(puserid).getUserId() + "");
							if (muserNode != null) {
								byte[] packPkt1 = ProtocolMessage.pushMsgNitice(muserNode, muser, puser, applyTime,
										kind, right, pass, message, sign);
								muserNode.getIoSession().write(IoBuffer.wrap(packPkt1));// 发送给对方
								muserNode.setLastPacketInfo(packPkt1);// 更新缓存
							}
							byte[] successPkt = PackPkt(ConstParam.RELATE_0);// 等待确认
							SendPkt(successPkt);
							userNode.setLastPacketInfo(successPkt);// 更新缓存
							System.out.println("缓存更新成功");
						} else {
							System.out.println("存在这个关联人");
							byte[] Associated = PackPkt(ConstParam.RELATE_1);// 已存在这个关联人
							SendPkt(Associated);
							userNode.setLastPacketInfo(Associated);
						}
					}
				} else {
					errorPktState = ConstParam.ADD_ERROR_0;// 关联错误 信息不完全
					byte[] errorPacket = PackPkt(ConstParam.CONTACT_2);
					SendPkt(errorPacket);
					userNode.setLastPacketInfo(errorPacket);
				}
			}
		}

		if (revPacket.getType() == ConstParam.TYPE_3) {

			int puserid = deviceDao.findDevByName(puser).getDeviceid();// 设备ID
			int userid = userDao.findAppuserByName(user).getUserID();
			AppNode userNode = SysInfo.getInstance().getAppNodeById(userid);
			if (ref == true) {// 管理员提出
				System.out.println("管理员提出");
				AppNode muserNode = SysInfo.getInstance().getAppNodeById(revPacket.getSid());
				RelateInfo relateInf = relateDao.findRelateInfByuserandmuser(userid, puserid, (byte) 1);
				if (relateInf == null) {
					System.out.println("不存在这个关联人");
					byte[] notRelate = PackPkt(ConstParam.CONTACT_2);// 账号没有关联
					SendPkt(notRelate);
					muserNode.setRevPktId(revPacket.getSeq());// 更新缓存
				} else {
					System.out.println("存在关联人");
					relateDao.updateRelateInf(relateInf, (byte) 8, "-1");
					RelateWait relateWait = new RelateWait();
					Date applyTime = new Date();// 获取当时时间
					relateWait.setApplyId(revPacket.getSid());
					relateWait.setDeviceId(puserid);
					relateWait.setAskId(userid);
					relateWait.setApplyType(ref);
					relateWait.setApplyRight(null);
					relateWait.setApplytime(applyTime);
					relateWait.setState("3");
					relateWait.setKind("3");
					relateWait.setMessage(null);
					sign = relateWaitDao.insertRelateWait(relateWait);

					System.out.println(userNode);
					if (userNode != null) {
						System.out.println("对方在线");
						byte[] packPkt1 = ProtocolMessage.pushMsgNitice(userNode, muser, puser, applyTime, kind, right,
								pass, message, sign);
						userNode.getIoSession().write(IoBuffer.wrap(packPkt1));// 发送给对方
						userNode.setLastPacketInfo(packPkt1);// 更新缓存
					}
					byte[] deletesuccess = PackPkt(ConstParam.CONTACT_1);
					SendPkt(deletesuccess);
					muserNode.setLastPacketInfo(deletesuccess);// 更新缓存
				}
			} else {// 自我申请脱联
				System.out.println("自我申请脱离");
				RelateInfo relateInf = relateDao.findRelateInfByuserandmuser(userid, puserid, (byte) 1);
				System.out.println(relateInf);
				if (relateInf == null) {
					byte[] notRelate = PackPkt(ConstParam.CONTACT_2);// 账号没有关联
					SendPkt(notRelate);
					userNode.setLastPacketInfo(notRelate);
				} else {
					System.out.println("是关联人");
					relateDao.updateRelateInf(relateInf, (byte) 8, "-1");
					RelateWait relateWait = new RelateWait();
					Date applyTime = new Date();// 获取当时时间
					relateWait.setApplyId(userid);
					relateWait.setDeviceId(puserid);
					relateWait.setAskId(relateDao.findallRelateInfBypuser(puserid).getUserId());
					relateWait.setApplyType(ref);
					relateWait.setApplyRight(null);
					relateWait.setApplytime(applyTime);
					relateWait.setState("3");
					relateWait.setKind("3");
					relateWait.setMessage(null);
					sign = relateWaitDao.insertRelateWait(relateWait);
					System.out.println("开始返回和发送给对方");
					AppNode muserNode = SysInfo.getInstance()
							.getAppNodeByAccount(relateDao.findallRelateInfBypuser(puserid).getUserId() + "");
					System.out.println(muserNode);
					if (muserNode != null) {
						System.out.println("对方在线");
						byte[] packPkt1 = ProtocolMessage.pushMsgNitice(muserNode, user, puser, applyTime, kind, right,
								pass, message, sign);
						muserNode.getIoSession().write(IoBuffer.wrap(packPkt1));// 发送给对方
						muserNode.setLastPacketInfo(packPkt1);// 更新缓存
					}
					byte[] deletesuccess = PackPkt(ConstParam.CONTACT_1);
					SendPkt(deletesuccess);
					userNode.setLastPacketInfo(deletesuccess);// 更新缓存
				}
			}
		}

		if (revPacket.getType() == ConstParam.TYPE_5) {// 获取关联账号请求
			System.out.println("进入到获取关联账号请求");
			int userid = userDao.findAppuserByName(user).getUserID();
			AppNode userNode = SysInfo.getInstance().getAppNodeById(userid);
			if (kind == 0) {// 所有关联的前端设备帐号；
				List<RelateInfo> relateInf = (List<RelateInfo>) relateDao.findallRelateInfByuser(userid);
				for (int i = 0; i < relateInf.size(); i++) {
					RelateInfo relateinf = relateInf.get(i);
					System.out.println("获取到的前端设备账号为：" + relateinf.getDeviceId());
					System.out.println("获取到的前端设备账号的权限为：" + relateinf.getAuthority());
					JSONObject relate = new JSONObject();
					relate.put("RIGHT", relateinf.getAuthority());
					relate.put("ACCOUNT", relateinf.getDeviceId());
					jsonArray.add(relate);
				}
				System.out.println(jsonArray);
				byte[] Associated = PackPkt(ConstParam.TO_BE_SUCCESSFUL_1);//
				SendPkt(Associated);
				jsonArray.clear();
				userNode.setLastPacketInfo(Associated);// 更新缓存
			}
			if (kind == 1) {// 获取某个前端设备上的所有管理关联人帐号
				System.out.println("kind:" + kind);
				int puserid = userDao.findAppuserByName(puser).getUserID();
				List<RelateInfo> relateInf = (List<RelateInfo>) relateDao
						.findallRelateInfBypuserandauthority(puserid);
				for (int i = 0; i < relateInf.size(); i++) {
					RelateInfo relateinf = relateInf.get(i);
					System.out.println("获取到的关联账号为：" + relateinf.getUserId());
					System.out.println("获取到的关联账号的权限为：" + relateinf.getAuthority());
					JSONObject relate = new JSONObject();
					relate.put("RIGHT", relateinf.getAuthority());
					relate.put("ACCOUNT", relateinf.getUserId());
					System.out.println(relate);
					jsonArray.add(relate);
				}
				System.out.println(jsonArray);
				byte[] Associated = PackPkt(ConstParam.TO_BE_SUCCESSFUL_1);//
				SendPkt(Associated);
				jsonArray.clear();
				userNode.setLastPacketInfo(Associated);// 更新缓存
			}
			if (kind == 2) {// 获取某个前端设备上的所有关联人帐号
				int puserid = deviceDao.findDevByName(puser).getDeviceid();
				System.out.println("kind:" + kind);
				List<RelateInfo> relateInf = (List<RelateInfo>) relateDao.findallRelateInfBypuser(puserid,
						(byte) 1);
				for (int i = 0; i < relateInf.size(); i++) {
					RelateInfo relateinf = relateInf.get(i);
					System.out.println("获取到的关联账号为：" + relateinf.getUserId());
					System.out.println("获取到的关联账号的权限为：" + relateinf.getAuthority());
					JSONObject relate = new JSONObject();
					relate.put("RIGHT", relateinf.getAuthority());
					relate.put("ACCOUNT", relateinf.getUserId());
					System.out.println(relate);
					jsonArray.add(relate);
					System.out.println("加入成功");
				}
				System.out.println(jsonArray);
				byte[] Associated = PackPkt(ConstParam.TO_BE_SUCCESSFUL_1);//
				SendPkt(Associated);
				jsonArray.clear();
				userNode.setLastPacketInfo(Associated);// 更新缓存
			}
		}

		if (revPacket.getType() == ConstParam.TYPE_7) {// 关联确认/脱联通知等请求
			System.out.println("进入到type = 7");
			System.out.println(puser);
			Date date = new Date();
			AppNode userNode = SysInfo.getInstance().getAppNodeById(revPacket.getSid());
			AppNode cuserNode = SysInfo.getInstance().getAppNodeById(peer);
			int puserid = deviceDao.findDevByName(puser).getDeviceid();
			if (kind == 1) {
				System.out.println("kind:" + kind);
				if (ref == true) {// 管理员分享，用户同意
					RelateInfo relateInf = relateDao.findRelateInfByuserandmuser(revPacket.getSid(), puserid,
							(byte) 0);// 查询账号同意这个设备被关联
					relateDao.updateRelateInf(relateInf, (byte) 1, alias);// 变更别名
					relateDao.updateRelateInf(relateInf, (byte) 2, pass);// 变更密码
					relateDao.updateRelateInf(relateInf, (byte) 8, "1");// 变更联系人状态
					RelateWait relateWait = relateWaitDao.findRelateWaitBypeer(peer, revPacket.getSid());
					System.out.println(relateWait);
					sign = relateWaitDao.updateRelateWait(relateWait, "3");
					if (cuserNode != null) {
						byte[] packPkt1 = ProtocolMessage.pushMsgNitice(cuserNode, user, puser, date, kind, right, pass,
								message, sign);
						cuserNode.getIoSession().write(IoBuffer.wrap(packPkt1));// 发送给对方
						cuserNode.setLastPacketInfo(packPkt1);// 更新缓存
					}
					byte[] successPkt = PackPkt(ConstParam.RELATE_1);
					SendPkt(successPkt);
					userNode.setLastPacketInfo(successPkt);// 更新缓存
				} else {// 用户申请，管理员同意
					System.out.println("kind:" + kind);
					RelateInfo relateInf = relateDao.findRelateInfByuserandmuser(revPacket.getSid(), puserid,
							(byte) 0);// 查询账号同意这个设备被关联
					relateDao.updateRelateInf(relateInf, (byte) 8, "1");
					relateDao.updateRelateInf(relateInf, (byte) 4, Byte.toString(right));
					RelateWait relateWait = relateWaitDao.findRelateWaitBypeer(peer, revPacket.getSid());//
					sign = relateWaitDao.updateRelateWait(relateWait, "3");
					if (cuserNode != null) {
						byte[] packPkt1 = ProtocolMessage.pushMsgNitice(cuserNode, user, puser, date, kind, right, pass,
								message, sign);
						cuserNode.getIoSession().write(IoBuffer.wrap(packPkt1));// 发送给对方
						cuserNode.setLastPacketInfo(packPkt1);// 更新缓存
					}
					byte[] successPkt = PackPkt(ConstParam.RELATE_1);
					SendPkt(successPkt);
					userNode.setLastPacketInfo(successPkt);// 更新缓存
				}
			}
			if (kind == -1) {// 拒绝
				RelateInfo relateInf = relateDao.findRelateInfByuserandmuser(2, puserid, (byte) 0);// 查询账号同意这个设备被关联
				relateDao.updateRelateInf(relateInf, (byte) 8, "-1");
				RelateWait relateWait = relateWaitDao.findRelateWaitBypeer(peer, revPacket.getSid());
				sign = relateWaitDao.updateRelateWait(relateWait, "3");
				if (cuserNode != null) {
					byte[] packPkt1 = ProtocolMessage.pushMsgNitice(cuserNode, user, puser, date, kind, right, pass,
							message, sign);
					cuserNode.getIoSession().write(IoBuffer.wrap(packPkt1));// 发送给对方
					cuserNode.setLastPacketInfo(packPkt1);// 更新缓存
				}
				byte[] successPkt = PackPkt(ConstParam.RELATE_2);
				SendPkt(successPkt);
				userNode.setLastPacketInfo(successPkt);// 更新缓存 //有问题
			}
		}
		if (revPacket.getType() == ConstParam.TYPE_9) {// 关联同步请求
			int puserid = deviceDao.findDevByName(puser).getDeviceid();
			AppNode userNode = SysInfo.getInstance().getAppNodeById(revPacket.getSid());
			switch (kind) {
			case 1:// 1 设备要求平台对比
				JSONArray Info1 = JSONArray.fromObject(info);
				List<RelateInfo> relateInf10 = relateDao.findallRelateInfBypuser(revPacket.getSid(), (byte) 1);
				for (int i = 0; i < relateInf10.size(); i++) {
					RelateInfo relateinf = relateInf10.get(i);
					JSONObject relate = new JSONObject();
					relate.put("RIGHT", relateinf.getAuthority()); // 权限
					relate.put("ACCOUNT", relateinf.getUserId()); // 关联人帐号
					relate.put("PASS", relateinf.getUserId()); // 密码
					relate.put("TIME", relateinf.getSetTime()); // 设置日期时间
					relate.put("STATE", relateinf.getState()); // 状态
					relate.put("ALIAS", relateinf.getAlias());
					System.out.println("relate:" + relate);
					jsonArray.add(relate);
					System.out.println("加入成功");
				}
				SynchPuserAndPlatform(Info1, jsonArray, puserid);// 同步
				byte[] Associated10 = PackPkt(ConstParam.TO_BE_SUCCESSFUL_1);//
				SendPkt(Associated10);
				userNode.setLastPacketInfo(Associated10);// 更新缓存
				jsonArray.clear();
			case 2:// 2 设备强制平台更新
				JSONArray Info2 = JSONArray.fromObject(info);
				List<RelateInfo> relateInf20 = relateDao.findallRelateInfBypuser(revPacket.getSid(), (byte) 1);
				for (int i = 0; i < relateInf20.size(); i++) {
					RelateInfo relateinf = relateInf20.get(i);
					JSONObject relate = new JSONObject();
					relate.put("RIGHT", relateinf.getAuthority()); // 权限
					relate.put("ACCOUNT", relateinf.getUserId()); // 关联人帐号
					relate.put("PASS", relateinf.getUserId()); // 密码
					relate.put("TIME", relateinf.getSetTime()); // 设置日期时间
					relate.put("STATE", relateinf.getState()); // 状态
					relate.put("ALIAS", relateinf.getAlias());
					System.out.println("relate:" + relate);
					jsonArray.add(relate);
					System.out.println("加入成功");
				}
				SynchPuserAndPlatform(Info2, jsonArray, puserid);// 同步
				byte[] Associated20 = PackPkt(ConstParam.TO_BE_SUCCESSFUL_1);//
				SendPkt(Associated20);
				userNode.setLastPacketInfo(Associated20);// 更新缓存
				jsonArray.clear();
			case 3:// 3 设备请求平台信息
				System.out.println("kind" + kind);
				List<RelateInfo> relateInf3 = relateDao.findallRelateInfBypuser(revPacket.getSid(), (byte) 1);
				for (int i = 0; i < relateInf3.size(); i++) {
					RelateInfo relateinf = relateInf3.get(i);
					JSONObject relate = new JSONObject();
					relate.put("RIGHT", relateinf.getAuthority()); // 权限
					relate.put("ACCOUNT", relateinf.getUserId()); // 关联人帐号
					relate.put("PASS", relateinf.getUserId()); // 密码
					relate.put("STATE", relateinf.getState()); // 状态
					relate.put("ALIAS", relateinf.getAlias());
					System.out.println("relate:" + relate);
					jsonArray.add(relate);
					System.out.println("加入成功");
				}
				byte[] Associated3 = PackPkt(ConstParam.TO_BE_SUCCESSFUL_1);//
				SendPkt(Associated3);
				userNode.setLastPacketInfo(Associated3);// 更新缓存
				jsonArray.clear();
			case 11:// 11 平台要求设备对比
				System.out.println("kind" + kind);
				List<RelateInfo> relateInf11 = relateDao.findallRelateInfBypuser(revPacket.getSid(), (byte) 1);
				for (int i = 0; i < relateInf11.size(); i++) {
					RelateInfo relateinf = relateInf11.get(i);
					JSONObject relate = new JSONObject();
					relate.put("RIGHT", relateinf.getAuthority()); // 权限
					relate.put("ACCOUNT", relateinf.getUserId()); // 关联人帐号
					relate.put("PASS", relateinf.getUserId()); // 密码
					relate.put("TIME", relateinf.getSetTime()); // 设置日期时间
					relate.put("STATE", relateinf.getState()); // 状态
					relate.put("ALIAS", relateinf.getAlias());
					System.out.println("relate:" + relate);
					jsonArray.add(relate);
					System.out.println("加入成功");
				}
				byte[] Associated11 = PackPkt(ConstParam.TO_BE_SUCCESSFUL_1);//
				SendPkt(Associated11);
				userNode.setLastPacketInfo(Associated11);// 更新缓存
				jsonArray.clear();
			case 12:// 12 平台要求设备更新
				System.out.println("kind" + kind);
				List<RelateInfo> relateInf12 = relateDao.findallRelateInfBypuser(revPacket.getSid(), (byte) 1);
				for (int i = 0; i < relateInf12.size(); i++) {
					RelateInfo relateinf = relateInf12.get(i);
					JSONObject relate = new JSONObject();
					relate.put("RIGHT", relateinf.getAuthority()); // 权限
					relate.put("ACCOUNT", relateinf.getUserId()); // 关联人帐号
					relate.put("PASS", relateinf.getUserId()); // 密码
					relate.put("TIME", relateinf.getSetTime()); // 设置日期时间
					relate.put("STATE", relateinf.getState()); // 状态
					relate.put("ALIAS", relateinf.getAlias());
					System.out.println("relate:" + relate);
					jsonArray.add(relate);
					System.out.println("加入成功");
				}
				byte[] Associated12 = PackPkt(ConstParam.TO_BE_SUCCESSFUL_1);//
				SendPkt(Associated12);
				userNode.setLastPacketInfo(Associated12);// 更新缓存
				jsonArray.clear();
			case 13:// 13 平台请求设备信息
				System.out.println("kind" + kind);
				List<RelateInfo> relateInf13 = relateDao.findallRelateInfBypuser(revPacket.getSid(), (byte) 1);
				for (int i = 0; i < relateInf13.size(); i++) {
					RelateInfo relateinf = relateInf13.get(i);
					JSONObject relate = new JSONObject();
					relate.put("RIGHT", relateinf.getAuthority()); // 权限
					relate.put("ACCOUNT", relateinf.getUserId()); // 关联人帐号
					relate.put("ACCOUNT", relateinf.getUserId()); // 密码
					relate.put("STATE", relateinf.getState()); // 状态
					relate.put("ALIAS", relateinf.getAlias());
					System.out.println("relate:" + relate);
					jsonArray.add(relate);
					System.out.println("加入成功");
				}
				byte[] Associated13 = PackPkt(ConstParam.TO_BE_SUCCESSFUL_1);//
				SendPkt(Associated13);
				userNode.setLastPacketInfo(Associated13);// 更新缓存
				jsonArray.clear();
			}
		}

		// 变更关联信息
		if (revPacket.getType() == ConstParam.TYPE_11) {
			// 查询联系人表
			System.out.println("cmd = 11");
			System.out.println(ref + puser + user);
			Date date = new Date();// 当前时间
			int userid = userDao.findAppuserByName(user).getUserID();
			int puserid = deviceDao.findDevByName(puser).getDeviceid();
			System.out.println(userid + puserid);
			RelateInfo relateInf = relateDao.findRelateInfByuserandmuser(userid, puserid, (byte) 1);
			if (ref == true) {// 管理关联人变更
				if ((kind >> 2 & 0x01) == 1) {
					System.out.println("变更权限");
					relateDao.updateRelateInf(relateInf, kind, Byte.toString(right));
					AppNode userNode = SysInfo.getInstance().getAppNodeById(userid);
					if (userNode != null) {
						byte[] packPkt1 = ProtocolMessage.pushMsgNitice(userNode, muser, puser, date, kind, right, null,
								null, sign);
						userNode.getIoSession().write(IoBuffer.wrap(packPkt1));// 发送给对方
						userNode.setLastPacketInfo(packPkt1);// 更新缓存
					}
					byte[] updatesuccessPacket = PackPkt(ConstParam.CHANGE);// 返回成功报文
					SendPkt(updatesuccessPacket);
					AppNode muserNode = SysInfo.getInstance().getAppNodeByAccount(muser);
					muserNode.setLastPacketInfo(updatesuccessPacket);// 更新缓存
				}
			} else {// 自己变更1
				if ((kind & 0x01) == 1) {
					System.out.println("变更别名");
					relateDao.updateRelateInf(relateInf, kind, alias);
					System.out.println("更新成功");
					byte[] updatesuccessPacket = PackPkt(ConstParam.CHANGE);// 返回成功报文
					System.out.println("gouzao");
					SendPkt(updatesuccessPacket);
					AppNode userNode = SysInfo.getInstance().getAppNodeByAccount(user);
					userNode.setLastPacketInfo(updatesuccessPacket);// 更新缓存
				}
				if ((kind >> 1 & 0x01) == 1) {// 2
					System.out.println("变更密码");
					relateDao.updateRelateInf(relateInf, kind, pass);
					byte[] updateSuccessPacket = PackPkt(ConstParam.CHANGE);// 返回成功报文
					SendPkt(updateSuccessPacket);
					AppNode userNode = SysInfo.getInstance().getAppNodeByAccount(user);
					userNode.setLastPacketInfo(updateSuccessPacket);// 更新缓存
				}
			}
		}

		/*
		 * }else{ System.out.println("不是管理APP"); }
		 */
	}

	//删除一个static
	public boolean SynchPuserAndPlatform(JSONArray Info, JSONArray relatetmy, int puserid) {// 同步设备和平台
		JSONArray newArrayModify = new JSONArray();
		JSONArray newArrayIn = new JSONArray();
		System.out.println("Info.size() + relatetmy.size():" + Info.size() + "," + relatetmy.size());
		for (int i = 0; i < Info.size(); i++) {
			for (int j = 0; j < relatetmy.size(); j++) {
				if (Info.getJSONObject(i).getString("ACCOUNT")
						.equals(relatetmy.getJSONObject(j).getString("ACCOUNT"))) {
					System.out.println("i + j:" + i + "," + j);
					System.out.println("id +　time:" + Info.getJSONObject(i).getString("ACCOUNT") + ","
							+ relatetmy.getJSONObject(i).getString("TIME"));
					System.out.println("id +　time:" + relatetmy.getJSONObject(j).getString("ACCOUNT") + ","
							+ Info.getJSONObject(i).getString("TIME"));
					if (Info.getJSONObject(i).getString("TIME").equals(relatetmy.getJSONObject(j).getString("TIME"))) {
						System.out.println("相同");
						break;
					} else {// 联系人，但是时间不同，意思是做了修改
						System.out.println("不同");
						if (Long.parseLong(Info.getJSONObject(i).getString("TIME")) > Long
								.parseLong(relatetmy.getJSONObject(j).getString("TIME"))) {
							System.out.println("用设备的信息，这个ID");
							newArrayModify.add(Info.getJSONObject(i));// 设备是最新的
						} else {
							System.out.println("用平台的信息，这个ID");
							newArrayModify.add(relatetmy.getJSONObject(j));// 平台是最新的
						}
						break;
					}
				} else {
					if (j == relatetmy.size() - 1) {
						System.out.println("i + j:" + i + "," + j);
						newArrayIn.add(Info.get(i));
					}
				}
			}
		}
		for (int i = 0; i < relatetmy.size(); i++) {
			for (int j = 0; j < Info.size(); j++) {
				if (relatetmy.getJSONObject(i).getString("ACCOUNT")
						.equals(Info.getJSONObject(j).getString("ACCOUNT"))) {
					break;
				} else {
					if (j == Info.size() - 1) {
						System.out.println("i + j:" + i + "," + j);
						newArrayIn.add(relatetmy.get(i));
					}
				}
			}
		}

		System.out.println("newArrayIn:" + newArrayIn);
		System.out.println("newArrayIn.size() + newArrayModify.size():" + newArrayIn.size() + newArrayModify.size());
		for (int i = 0; i < newArrayIn.size(); i++) {
			System.out.println("i :" + i);
			RelateInfo relateInfo = new RelateInfo();
			relateInfo.setUserId(newArrayIn.getJSONObject(i).getInt("ACCOUNT"));// 帐号
			System.out.println("id:" + newArrayIn.getJSONObject(i).getInt("ACCOUNT"));
			relateInfo.setAuthority(newArrayIn.getJSONObject(i).getString("RIGHT"));
			relateInfo.setDeviceId(puserid);
			relateInfo.setAlias(newArrayIn.getJSONObject(i).getString("ALIAS"));
			relateInfo.setPassWord(newArrayIn.getJSONObject(i).getString("PASS"));
			;
			relateInfo.setSetTime(new Date(newArrayIn.getJSONObject(i).getLong("TIME")));
			relateInfo.setState("1");
			relateDao.insertRelateInf(relateInfo);
		}

		for (int i = 0; i < newArrayModify.size(); i++) {
			RelateInfo relateInfo = relateDao
					.findRelateInfByuserandmuser(newArrayModify.getJSONObject(i).getInt("ACCOUNT"), puserid, (byte) 1);
			RelateInfo relateInfo1 = new RelateInfo();
			relateInfo1.setUserId(newArrayModify.getJSONObject(i).getInt("ACCOUNT"));// 帐号
			System.out.println("id:" + newArrayModify.getJSONObject(i).getInt("ACCOUNT"));
			relateInfo1.setAuthority(newArrayModify.getJSONObject(i).getString("RIGHT"));
			relateInfo1.setDeviceId(puserid);
			relateInfo1.setAlias(newArrayModify.getJSONObject(i).getString("ALIAS"));
			relateInfo1.setPassWord(newArrayModify.getJSONObject(i).getString("PASS"));
			;
			relateInfo1.setSetTime(new Date(newArrayModify.getJSONObject(i).getLong("TIME")));
			relateInfo1.setState("1");

			System.out.println(relateInfo);
			System.out.println(relateInfo1);
			relateDao.updateRelateInf(relateInfo, relateInfo1);
		}

		relateDao.deleteRelateInfBypuser(puserid);

		newArrayIn.clear();
		newArrayModify.clear();
		return true;
	}
	//
	// public static void main(String[] args) {
	// JSONArray jsonArray = new JSONArray();
	// List<RelateInfo> relateInf = (List<RelateInfo>) new
	// RelateDao().findallRelateInfBypuser(2,(byte) 1);
	// for(int i = 0; i < relateInf.size();i ++){
	// RelateInfo relateinf = relateInf.get(i);
	// JSONObject relate = new JSONObject();
	// relate.put("ACCOUNT",relateinf.getUserId());//帐号
	// relate.put("RIGHT",relateinf.getAuthority());//权限
	// relate.put("ALIAS",relateinf.getAlias());//别名
	// relate.put("PASS",relateinf.getPassWord());//密码
	// relate.put("TIME",relateinf.getSetTime().getTime());//设置时间
	// relate.put("STATE",relateinf.getState());//状态
	// jsonArray.add(relate);
	// System.out.println(relate);
	// }
	// JSONArray jsonArray1 = new JSONArray();
	// List<RelateInfo> relateInf1 = (List<RelateInfo>) new
	// RelateDao().findallRelateInfBypuser(2,(byte) 2);
	// for(int i = 0; i < relateInf1.size();i ++){
	// RelateInfo relateinf = relateInf1.get(i);
	// JSONObject relate = new JSONObject();
	// relate.put("ACCOUNT",relateinf.getUserId());//帐号
	// relate.put("RIGHT",relateinf.getAuthority());//权限
	// relate.put("ALIAS",relateinf.getAlias());//别名
	// relate.put("PASS",relateinf.getPassWord());//密码
	// relate.put("TIME",relateinf.getSetTime().getTime());//设置时间
	// relate.put("STATE",relateinf.getState());//状态
	// jsonArray1.add(relate);
	// System.out.println(relate);
	// }
	// SynchPuserAndPlatform(jsonArray,jsonArray1,2);
	// jsonArray.clear();
	// jsonArray1.clear();
	// }

	public byte[] PackPkt(int i) {
		PacketMsg msg = new PacketMsg();
		msg.setCmd(ConstParam.CMD_9);
		msg.setSid(ConstParam.SERVER_ID);
		msg.setOpt(ConstParam.OPT_8);
		if (revPacket.getType() == ConstParam.TYPE_1) {// 关联请求
			msg.setType(ConstParam.TYPE_2); // type = 2
		}
		if (revPacket.getType() == ConstParam.TYPE_3) {// 脱离请求
			msg.setType(ConstParam.TYPE_4); // type = 4
		}
		if (revPacket.getType() == ConstParam.TYPE_5) {
			msg.setType(ConstParam.TYPE_6); // type=6
		}
		if (revPacket.getType() == ConstParam.TYPE_7) {
			msg.setType(ConstParam.TYPE_8); // type=8
		}
		if (revPacket.getType() == ConstParam.TYPE_9) {
			msg.setType(ConstParam.TYPE_10); // type=10
		}
		if (revPacket.getType() == ConstParam.TYPE_11) {
			msg.setType(ConstParam.TYPE_12); // type=12
		}
		String packetBody = null; // 报文数据域
		switch (i) {
		case 0: // ret = 0
			if (revPacket.getType() == ConstParam.TYPE_1) {
				packetBody = "{'DATA':{'RET':'0','INFO':'deng dai que ren'}}";
			}
			if (revPacket.getType() == ConstParam.TYPE_3) {
				packetBody = "{'DATA':{'RET':'0','INFO':'" + deleteDate + "'}}";
			}
			if (revPacket.getType() == ConstParam.TYPE_11) {// 变更联系人
				if ((kind & 1) == 1) {// 变更权限别名
					packetBody = "{'DATA':{'RET':'0','INFO':'bian geng cheng gong'}}";
				}
				if ((kind & 2) == 2) {// 变更权限密码
					packetBody = "{'DATA':{'RET':'0','INFO':'bian geng cheng gong'}}";
				}
				if ((kind & 4) == 4) {// 变更权限成功
					System.out.println("djalfjalddlgh");
					packetBody = "{'DATA':{'RET':'0','INFO':'bian geng cheng gong'}}";
				}
			}
			break;
		case 1:// ret = 1
			if (revPacket.getType() == ConstParam.TYPE_1) {
				packetBody = "{'DATA':{'RET':'1','INFO':'yi cun zai '}}";
			}
			if (revPacket.getType() == ConstParam.TYPE_3) {
				packetBody = "{'DATA':{'RET':'1','INFO':'tuo lian cheng gong  '}}";
			}
			if (revPacket.getType() == ConstParam.TYPE_5) {
				packetBody = "{'DATA':{'RET':'1','INFO':'" + jsonArray + "'}}";
			}
			if (revPacket.getType() == ConstParam.TYPE_7) {
				packetBody = "{'DATA':{'RET':'1','INFO':'zhang hao cheng gong guan lian'}}";
			}
			break;
		case 2:// ret = -1
			if (revPacket.getType() == ConstParam.TYPE_1) {
				if (errorPktState == ConstParam.ADD_ERROR_0) {
					packetBody = "{'DATA':{'RET':'-1','INFO':'xin xi bu quan'}}";
				}
				if (errorPktState == ConstParam.ADD_ERROR_1) {
					packetBody = "{'DATA':{'RET':'-1','INFO':'guan lian ren shang xian le'}}";
				}
				if (errorPktState == ConstParam.ADD_ERROR_2) {
					packetBody = "{'DATA':{'RET':'-1','INFO':'guan lian ren shang xian le'}}";
				}
			}
			if (revPacket.getType() == ConstParam.TYPE_3) {
				packetBody = "{'DATA':{'RET':'-1','INFO':'bu cun zai guan lian zhang hao'}}";
			}
			if (revPacket.getType() == ConstParam.TYPE_7) {
				packetBody = "{'DATA':{'RET':'-1','INFO':'ju jue chong gong'}}";
			}
			break;
		}
		msg.setData(packetBody);
		return msg.createMessage(msg);
	}

	public void SendPkt(byte[] sendPacket) {
		revPacket.getIoSession().write(IoBuffer.wrap(sendPacket));// 发送报文
	}
}
