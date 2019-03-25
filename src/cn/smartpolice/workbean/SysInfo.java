package cn.smartpolice.workbean;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import cn.smartpolice.hibernate.SoftInfo;
import cn.smartpolice.protocol.MsgTaskCheckThread;
import cn.smartpolice.protocol.ProtocolAccount;
import cn.smartpolice.protocol.ProtocolBase;
import cn.smartpolice.protocol.ProtocolControl;
import cn.smartpolice.protocol.ProtocolDiscoverLAN;
import cn.smartpolice.protocol.ProtocolEncryption;
import cn.smartpolice.protocol.ProtocolFile;
import cn.smartpolice.protocol.ProtocolLogin;
import cn.smartpolice.protocol.ProtocolMessage;
import cn.smartpolice.protocol.ProtocolQuery;
import cn.smartpolice.protocol.ProtocolRelate;
import cn.smartpolice.protocol.ProtocolTest;
import cn.smartpolice.protocol.ProtocolUpgrade;


/**
 * 系统信息 使用synchronizedSet方法使HashSet具有同步的能力：Set s =
 * Collections.synchronizedSet(new HashSet(...));
 * 
 * @author 刘超
 *
 */
public class SysInfo {
//    private static int 
	private static SysInfo instance;
	private static UIUserInfo uIUserInfo; // 界面用户登录信息
	public static UIUserInfo getuIUserInfo() {
		return uIUserInfo;
	}

	public static void setuIUserInfo(UIUserInfo uIUserInfo) {
		SysInfo.uIUserInfo = uIUserInfo;
	}


	private static Set<UserNode> userNodeQueue = Collections.synchronizedSet(new HashSet<UserNode>()); // dev队列
	// private static HashSet<AppNode> appNodeQueue = new HashSet<AppNode>();

	private static ArrayList<FileNodeInfo> fileDataInfoQueue = new ArrayList<FileNodeInfo>(); // 文件队列	
//	private static ArrayList<SoftInfo> SoftInfQueue = new ArrayList<SoftInfo>(); //软件队列
	private static SysStatInfo sysStatInfo = new SysStatInfo(); // 系统统计信息


	private static SysCfgInfo sysCfgInfo; // 系统配置信息
	private static  List<MsgTask> msgTaskQueue = Collections.synchronizedList(new ArrayList<MsgTask>());
	private static Thread msgTaskCheckThread;

	// 协议处理类数组
	private static ProtocolBase[] prtocolBases = { new ProtocolTest(), new ProtocolLogin(), new ProtocolAccount(),
			new ProtocolControl(), new ProtocolQuery(), new ProtocolMessage(), new ProtocolDiscoverLAN(),
			new ProtocolFile(),new ProtocolEncryption(),new ProtocolRelate(),new ProtocolFile(),new ProtocolUpgrade()};
	
	public static SysStatInfo getSysStatInfo() {
		sysStatInfo.setOpenFileNum(fileDataInfoQueue.size());//设置文件打开数
		return sysStatInfo;
	}

	public static Thread getMsgTaskCheckThread() {
		return msgTaskCheckThread;
	}
	public Thread createNewThread(){	
		msgTaskCheckThread=new Thread((new MsgTaskCheckThread()));
		return msgTaskCheckThread;                                                  
	}
	public static ProtocolBase[] getPrtocolBases() {
		return prtocolBases;
	}

	public static ArrayList<FileNodeInfo> getFileDataInfoQueue() {
		return fileDataInfoQueue;
	}
	


	public static Set<UserNode> getUserNodeQueue() {
		return userNodeQueue;
	}

	public static synchronized List<MsgTask> getMsgTaskQueue() {
		return  msgTaskQueue;
	}

	public static void setSysCfgInfo(SysCfgInfo sysCfgInfo) {
		SysInfo.sysCfgInfo = sysCfgInfo;
	}
	public static SysCfgInfo getSysCfgInfo() {
		return sysCfgInfo;
	}

	// 将构造器私有
	private SysInfo() {
	}

	// 得到SysInfo单列
	public static SysInfo getInstance() {
		instance = new SysInfo();
		return instance;
	}

	// 根据id找到app信息节点
	public AppNode getAppNodeById(int id) {
		for (UserNode userNode : userNodeQueue) {
			// id相等并判断是否是appnode
			if (userNode.getId() == id && userNode instanceof AppNode) {
				AppNode appNode = (AppNode) userNode; // 向下类型转换
				return appNode;
			}
		}
		return null;
	}
	/**
	 * 根据用户账户查找节点
	 * @param string 用户账户
	 * @return
	 */
	public AppNode getAppNodeByAccount(String account) {
		for (UserNode userNode : userNodeQueue) {
			// id相等并判断是否是appnode
			if (userNode.getAccount().equals(account) && userNode instanceof AppNode) {
				AppNode appNode = (AppNode) userNode; // 向下类型转换
				return appNode;
			}
		}
		return null;
	}
	/**
	 * 根据用户账户查找节点
	 * @param string 用户账户
	 * @return
	 */
	public DevNode getDevNodeByAccount(String account) {
		for (UserNode userNode : userNodeQueue) {
			if (userNode.getAccount().equals(account) && userNode instanceof DevNode) {
				DevNode devNode = (DevNode) userNode;
				return devNode;
			}
		}
		return null;
	}
	// 根据id找到dev信息节点
	public DevNode getDevNodeById(int id) {
		for (UserNode userNode : userNodeQueue) {
			if (userNode.getId() == id && userNode instanceof DevNode) {
				DevNode devNode = (DevNode) userNode;
				return devNode;
			}
		}
		return null;
	}

	// 添加一个userNode节点到队列中
	public synchronized void addUserNode(UserNode userNode) {

		this.userNodeQueue.add(userNode);
	}

	// 删除userNode队列中的一个节点
	public synchronized void removeUserNode(UserNode userNode) {

		this.userNodeQueue.remove(userNode);
	}

	public void addMsgTask(MsgTask msgTask) {

		this.msgTaskQueue.add(msgTask);
	}
	public boolean removeMsgTask(MsgTask msgTask) {

		return this.msgTaskQueue.remove(msgTask);
		
	}
	
	
	/**
	 * 消息处理线程创建
	 */
	public void msgTaskThreadCheck(){
		Thread msgTaskCheckThread = null;
		if (SysInfo.getMsgTaskCheckThread() == null) {
			msgTaskCheckThread = SysInfo.getInstance().createNewThread();
			msgTaskCheckThread.start();
		} else {
			if (!SysInfo.getMsgTaskCheckThread().isAlive()) {
				msgTaskCheckThread = SysInfo.getInstance().createNewThread();
				msgTaskCheckThread.start();
			}
		}
	}
}
