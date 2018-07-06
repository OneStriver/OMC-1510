package com.sunkaisens.omc.context.core;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sunkaisens.omc.util.RegExpUtil;

/**
 * 用于读取配置文件上的配置信息并初始化对应属性
 */
public class OmcServerContext {
	// 获取log对象
	private static final Logger log = LoggerFactory.getLogger(OmcServerContext.class);

	private static OmcServerContext context;
	// 配置文件路径
	private static String location = OmcServerContext.class.getResource("/omc.properties").getPath();

	private static Properties properties;
	// OMC的IP
	private String omcIp;
	// OMC的Port
	private int omcPort;
	// 超时时长
	private int timeout;
	// 版本号
	private String version;
	// OSPF IP
	private String ospfIp;
	// OSPF Port
	private String ospfPort;
	// OSPF 用户
	private String ospfUser;
	// OSPF 密码
	private String ospfPassword;
	// OSPF 编码格式
	private String ospfCharset;
	// OSPF HostPassword
	private String hostPassword;
	// OSPF LocalIp
	private String localIp;
	// OSPF LocalPort
	private int localPort;
	// OAM IP
	private String oamIp;
	// OAM Port
	private int oamPort;
	// Agent IP
	private String agentIp;
	// FTP 存放目录
	private String ftpDir;
	// 项目名称
	private String project;
	// 北向接口 IP
	private String northIp;
	// 北向接口 Port
	private int northPort;
	// 组 IP 组 Port
	private String mainGroupIp;
	private int mainGroupPort;
	// 备设备群组的IP和Port
	private String backUpGroupIp;
	private int bakcUpGroupPort;
	// DNS IP
	private String dnsIp;
	// DNS Port
	private int dnsPort;
	private String hideEth;
	private int hssLimit;

	private String swoon;

	private String sendDelay;

	private String mainUrl;
	private String mainUserName;
	private String mainPassword;

	private String backUpUrl;
	private String backUpUserName;
	private String backUpPassword;

	private String mainDeviceIp;
	private Integer mainDevicePort;

	private String backUpDeviceIp;
	private Integer backUpDevicePort;

	private String mainBackUpAddress;

	private String loginUser;

	// NMS database
	private String nmsDbName;
	private String nmsUrl;
	private String nmsUserName;
	private String nmsPassword;

	private String connectNmsDB;

	private String pingIp;

	private String groupIp;
	private Integer groupPort;

	private String publicUrl;
	private String publicHeaderKey;
	private String publicHeaderValue;

	private String globalUrl;
	private String globalHeaderKey;
	private String globalHeaderValue;

	// 配置发送给Hss消息的超时时间
	private String sendHssMsgTimeOut;

	// 查看组和组成员接口
	private String selectServiceUrl;
	// 添加组接口
	private String addServiceUrl;
	// 修改组接口
	private String updateServiceUrl;
	// 添加组成员接口
	private String addGroupMemberUrl;
	// 修改组成员接口
	private String updateGroupMemberUrl;
	// 删除组接口
	private String deleteServiceUrl;
	// 删除组成员接口
	private String deleteGroupMemberUrl;

	// 默认用户类型
	private String defaultUserType;
	
	private int checkBrowserFlag;
	private String dowloadBrowserPath;

	/**
	 * 私有构造器
	 */
	private OmcServerContext() {
		getContext();
	}

	/**
	 * 获取OmcServerContext实例
	 */
	public synchronized static OmcServerContext getInstance() {
		if (context == null) {
			context = new OmcServerContext();
		}
		return context;
	}

	/**
	 * 初始化属性
	 */
	private void getContext() {
		this.omcIp = getProperty("omc.ipaddr", "127.0.0.1");
		this.omcPort = new Integer(getProperty("omc.port", "28500"));
		// OSPF主机地址/端口
		this.ospfIp = getProperty("ospf.ip", "127.0.0.1");
		this.ospfPort = getProperty("ospf.port", "23");
		this.ospfUser = getProperty("ospf.user", "root");
		this.ospfPassword = getProperty("ospf.password", "zebra");
		this.ospfCharset = getProperty("ospf.charset", "UTF-8");
		this.hostPassword = getProperty("host.password", "password");
		this.ftpDir = getProperty("ftpDir", "/root/OAM");
		this.project = getProperty("project", "");
		this.northIp = getProperty("north.ip", "127.0.0.1");
		this.northPort = new Integer(getProperty("north.port", "8888"));

		this.mainGroupIp = getProperty("main.groupIp", "127.0.0.1");
		this.mainGroupPort = new Integer(getProperty("main.groupPort", "0"));

		this.backUpGroupIp = getProperty("backUp.groupIp", "127.0.0.1");
		this.bakcUpGroupPort = new Integer(getProperty("backUp.groupPort", "0"));

		this.dnsIp = getProperty("dns.ip", "");
		this.dnsPort = new Integer(getProperty("dns.port", "853"));
		this.version = getProperty("omc.version", "v2.0.0.1");
		this.hideEth = getProperty("hideEth", "");
		this.timeout = new Integer(getProperty("omc.timeout", "5"));
		this.swoon = getProperty("swoon", "false");
		this.setSendDelay(getProperty("sendDelay", "50"));

		// OSPF主机地址/端口
		this.ospfIp = getProperty("ospf.ip", "127.0.0.1");
		this.ospfPort = getProperty("ospf.port", "23");
		this.ospfUser = getProperty("ospf.user", "root");
		this.ospfPassword = getProperty("ospf.password", "zebra");
		this.ospfCharset = getProperty("ospf.charset", "UTF-8");
		this.hostPassword = getProperty("host.password", "password");
		this.ftpDir = getProperty("ftpDir", "/root/OAM");
		this.project = getProperty("project", "");
		this.northIp = getProperty("north.ip", "127.0.0.1");
		this.northPort = new Integer(getProperty("north.port", "8888"));
		this.groupIp = getProperty("group.ip", "127.0.0.1");
		this.groupPort = new Integer(getProperty("group.port", "0"));
		this.dnsIp = getProperty("dns.ip", "");
		this.dnsPort = new Integer(getProperty("dns.port", "853"));
		this.version = getProperty("omc.version", "v2.0.0.1");
		this.hideEth = getProperty("hideEth", "");
		this.timeout = new Integer(getProperty("omc.timeout", "3"));
		this.swoon = getProperty("swoon", "false");
		this.setSendDelay(getProperty("sendDelay", "50"));
		this.pingIp = getProperty("pingIp", "192.168.1.1");

		// OMC启动地址/端口信息
		this.localIp = getProperty("omc.ipaddr", "127.0.0.1");
		this.hssLimit = new Integer(getProperty("hss.limit", "20"));
		String _omcPort = getProperty("omc.port", "28500");
		if (_omcPort != null && _omcPort.matches("\\d+")) {
			this.localPort = Integer.valueOf(_omcPort, 10);
		}

		this.nmsDbName = getProperty("nms.dbName", "nms");
		this.nmsUrl = getProperty("nms.url", "jdbc:mysql://127.0.0.1:3306/nms?characterEncoding=utf8");
		this.nmsUserName = getProperty("nms.userName", "root");
		this.nmsPassword = getProperty("nms.password", "sunkaisens");

		// OAM地址/端口
		String _oamIp = getProperty("oam.ipaddr", "127.0.0.1");
		if (_oamIp != null && _oamIp.matches(RegExpUtil.IP)) {
			this.oamIp = _oamIp;
		}

		String _oam_port = getProperty("oam.port", "6124");
		if (_oam_port != null && _oam_port.matches("\\d+")) {
			this.oamPort = Integer.valueOf(_oam_port, 10);
		}

		// xgz代理Ip
		String _agentIp = getProperty("xgz.agentIp", "127.0.0.1");
		if (_agentIp != null && _agentIp.matches(RegExpUtil.IP)) {
			this.agentIp = _agentIp;
		}

		this.mainUrl = getProperty("main.url", "jdbc:mysql://127.0.0.1:3306/hss?characterEncoding=utf8");
		this.mainUserName = getProperty("main.userName", "root");
		this.mainPassword = getProperty("main.password", "sunkaisens");
		this.connectNmsDB = getProperty("connectNmsDB", "false");

		this.backUpUrl = getProperty("backUp.url", "jdbc:mysql://127.0.0.1:3306/hss?characterEncoding=utf8");
		this.backUpUserName = getProperty("backUp.userName", "root");
		this.backUpPassword = getProperty("backUp.password", "sunkaisens");

		this.mainDeviceIp = getProperty("main.deviceIp", "127.0.0.1");
		this.mainDevicePort = new Integer(getProperty("main.devicePort", "6124"));

		this.backUpDeviceIp = getProperty("backUp.deviceIp", "127.0.0.1");
		this.backUpDevicePort = new Integer(getProperty("backUp.devicePort", "6124"));

		this.publicUrl = getProperty("public.Url", "");
		this.publicHeaderKey = getProperty("public.HeaderKey", "");
		this.publicHeaderValue = getProperty("public.HeaderValue", "");

		this.globalUrl = getProperty("global.Url", "");
		this.globalHeaderKey = getProperty("global.HeaderKey", "");
		this.globalHeaderValue = getProperty("global.HeaderValue", "");

		this.sendHssMsgTimeOut = getProperty("sendHssMsgTimeOut", "");

		this.checkBrowserFlag = Integer.valueOf(getProperty("checkBrowserFlag", "0"));
		this.dowloadBrowserPath = getProperty("dowloadBrowserPath", "");

		// 查询接口
		this.selectServiceUrl = getProperty("select.service.Url", "");
		// 添加接口
		this.addServiceUrl = getProperty("add.service.Url", "");
		// 修改组接口
		this.updateServiceUrl = getProperty("update.service.Url", "");
		// 添加组成员接口
		this.addGroupMemberUrl = getProperty("addGroupMember.service.Url", "");
		// 修改组成员接口
		this.updateGroupMemberUrl = getProperty("updateGroupMember.service.Url", "");
		// 删除组接口
		this.deleteServiceUrl = getProperty("delete.service.Url", "");
		// 删除组成员接口
		this.deleteGroupMemberUrl = getProperty("deleteGroupMember.service.Url", "");
		// 默认用户类型
		this.defaultUserType = getProperty("defaultUserType", "");

	}

	/**
	 * 加载配置文件
	 */
	private static Properties getProperties(String location) {
		if (properties == null) {
			properties = new Properties();
			FileInputStream fis = null;
			try {
				fis = new FileInputStream(location);
				properties.load(fis);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			} finally {
				try {
					if (fis != null)
						fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return properties;
	}

	/**
	 * 从配置文件上读取属性信息
	 */
	private String getProperty(String propertyName, String defaultValue) {
		String value = getProperties(location).getProperty(propertyName, defaultValue);
		if (value == null) {
			log.warn(propertyName + " was not found in " + location);
			return null;
		}
		return value.trim();
	}

	/**
	 * 设置配置文件路径
	 */
	public void setLocation(String path) {
		if (path.startsWith("classpath:")) {
			location = OmcServerContext.class.getClassLoader().getResource(path.substring(10)).getPath();
		} else {
			location = path;
		}
	}
	
	public String getOmcIp() {
		return omcIp;
	}

	public void setOmcIp(String omcIp) {
		this.omcIp = omcIp;
	}

	public int getOmcPort() {
		return omcPort;
	}

	public void setOmcPort(int omcPort) {
		this.omcPort = omcPort;
	}

	public String getLocalIp() {
		return localIp;
	}

	public int getLocalPort() {
		return localPort;
	}

	public String getOamIp() {
		return oamIp;
	}

	public int getOamPort() {
		return oamPort;
	}

	public String getOspfIp() {
		return ospfIp;
	}

	public String getOspfPort() {
		return ospfPort;
	}

	public String getOspfUser() {
		return ospfUser;
	}

	public String getOspfPassword() {
		return ospfPassword;
	}

	public String getOspfCharset() {
		return ospfCharset;
	}

	public String getHostPassword() {
		return hostPassword;
	}

	public String getAgentIp() {
		return agentIp;
	}

	public String getFtpDir() {
		return ftpDir;
	}

	public String getProject() {
		return project;
	}

	public String getNorthIp() {
		return northIp;
	}

	public int getNorthPort() {
		return northPort;
	}

	public String getMainGroupIp() {
		return mainGroupIp;
	}

	public int getMainGroupPort() {
		return mainGroupPort;
	}

	public String getBackUpGroupIp() {
		return backUpGroupIp;
	}

	public int getBakcUpGroupPort() {
		return bakcUpGroupPort;
	}

	public String getDnsServerIp() {
		return dnsIp;
	}

	public int getDnsServerPort() {
		return dnsPort;
	}

	public String getVersion() {
		return version;
	}

	public String getHideEth() {
		return hideEth;
	}

	public String getPingIp() {
		return pingIp;
	}

	public int getTimeout() {
		return timeout;
	}

	public int getHssLimit() {
		return hssLimit;
	}

	public void setHssLimit(int hssLimit) {
		this.hssLimit = hssLimit;
	}

	public String getSwoon() {
		return swoon;
	}

	public void setSwoon(String swoon) {
		this.swoon = swoon;
	}

	public String getSendDelay() {
		return sendDelay;
	}

	public void setSendDelay(String sendDelay) {
		this.sendDelay = sendDelay;
	}

	public String getMainUrl() {
		return mainUrl;
	}

	public void setMainUrl(String mainUrl) {
		this.mainUrl = mainUrl;
	}

	public String getMainUserName() {
		return mainUserName;
	}

	public void setMainUserName(String mainUserName) {
		this.mainUserName = mainUserName;
	}

	public String getMainPassword() {
		return mainPassword;
	}

	public void setMainPassword(String mainPassword) {
		this.mainPassword = mainPassword;
	}

	public String getBackUpUrl() {
		return backUpUrl;
	}

	public void setBackUpUrl(String backUpUrl) {
		this.backUpUrl = backUpUrl;
	}

	public String getBackUpUserName() {
		return backUpUserName;
	}

	public void setBackUpUserName(String backUpUserName) {
		this.backUpUserName = backUpUserName;
	}

	public String getBackUpPassword() {
		return backUpPassword;
	}

	public void setBackUpPassword(String backUpPassword) {
		this.backUpPassword = backUpPassword;
	}

	public String getMainDeviceIp() {
		return mainDeviceIp;
	}

	public void setMainDeviceIp(String mainDeviceIp) {
		this.mainDeviceIp = mainDeviceIp;
	}

	public Integer getMainDevicePort() {
		return mainDevicePort;
	}

	public void setMainDevicePort(Integer mainDevicePort) {
		this.mainDevicePort = mainDevicePort;
	}

	public String getBackUpDeviceIp() {
		return backUpDeviceIp;
	}

	public void setBackUpDeviceIp(String backUpDeviceIp) {
		this.backUpDeviceIp = backUpDeviceIp;
	}

	public Integer getBackUpDevicePort() {
		return backUpDevicePort;
	}

	public void setBackUpDevicePort(Integer backUpDevicePort) {
		this.backUpDevicePort = backUpDevicePort;
	}

	public String getMainBackUpAddress() {
		return mainBackUpAddress;
	}

	public void setMainBackUpAddress(String mainBackUpAddress) {
		this.mainBackUpAddress = mainBackUpAddress;
	}

	public String getLoginUser() {
		return loginUser;
	}

	public void setLoginUser(String loginUser) {
		this.loginUser = loginUser;
	}

	public String getNmsDbName() {
		return nmsDbName;
	}

	public void setNmsDbName(String nmsDbName) {
		this.nmsDbName = nmsDbName;
	}

	public String getNmsUrl() {
		return nmsUrl;
	}

	public void setNmsUrl(String nmsUrl) {
		this.nmsUrl = nmsUrl;
	}

	public String getNmsUserName() {
		return nmsUserName;
	}

	public void setNmsUserName(String nmsUserName) {
		this.nmsUserName = nmsUserName;
	}

	public String getNmsPassword() {
		return nmsPassword;
	}

	public void setNmsPassword(String nmsPassword) {
		this.nmsPassword = nmsPassword;
	}

	public String getConnectNmsDB() {
		return connectNmsDB;
	}

	public void setConnectNmsDB(String connectNmsDB) {
		this.connectNmsDB = connectNmsDB;
	}

	public String getGroupIp() {
		return groupIp;
	}

	public void setGroupIp(String groupIp) {
		this.groupIp = groupIp;
	}

	public Integer getGroupPort() {
		return groupPort;
	}

	public void setGroupPort(Integer groupPort) {
		this.groupPort = groupPort;
	}

	public String getPublicUrl() {
		return publicUrl;
	}

	public void setPublicUrl(String publicUrl) {
		this.publicUrl = publicUrl;
	}

	public String getPublicHeaderKey() {
		return publicHeaderKey;
	}

	public void setPublicHeaderKey(String publicHeaderKey) {
		this.publicHeaderKey = publicHeaderKey;
	}

	public String getPublicHeaderValue() {
		return publicHeaderValue;
	}

	public void setPublicHeaderValue(String publicHeaderValue) {
		this.publicHeaderValue = publicHeaderValue;
	}

	public String getGlobalUrl() {
		return globalUrl;
	}

	public void setGlobalUrl(String globalUrl) {
		this.globalUrl = globalUrl;
	}

	public String getGlobalHeaderKey() {
		return globalHeaderKey;
	}

	public void setGlobalHeaderKey(String globalHeaderKey) {
		this.globalHeaderKey = globalHeaderKey;
	}

	public String getGlobalHeaderValue() {
		return globalHeaderValue;
	}

	public void setGlobalHeaderValue(String globalHeaderValue) {
		this.globalHeaderValue = globalHeaderValue;
	}

	public String getSendHssMsgTimeOut() {
		return sendHssMsgTimeOut;
	}

	public void setSendHssMsgTimeOut(String sendHssMsgTimeOut) {
		this.sendHssMsgTimeOut = sendHssMsgTimeOut;
	}

	public String getSelectServiceUrl() {
		return selectServiceUrl;
	}

	public void setSelectServiceUrl(String selectServiceUrl) {
		this.selectServiceUrl = selectServiceUrl;
	}

	public String getAddServiceUrl() {
		return addServiceUrl;
	}

	public void setAddServiceUrl(String addServiceUrl) {
		this.addServiceUrl = addServiceUrl;
	}

	public String getAddGroupMemberUrl() {
		return addGroupMemberUrl;
	}

	public void setAddGroupMemberUrl(String addGroupMemberUrl) {
		this.addGroupMemberUrl = addGroupMemberUrl;
	}

	public String getUpdateServiceUrl() {
		return updateServiceUrl;
	}

	public void setUpdateServiceUrl(String updateServiceUrl) {
		this.updateServiceUrl = updateServiceUrl;
	}

	public String getUpdateGroupMemberUrl() {
		return updateGroupMemberUrl;
	}

	public void setUpdateGroupMemberUrl(String updateGroupMemberUrl) {
		this.updateGroupMemberUrl = updateGroupMemberUrl;
	}

	public String getDeleteServiceUrl() {
		return deleteServiceUrl;
	}

	public void setDeleteServiceUrl(String deleteServiceUrl) {
		this.deleteServiceUrl = deleteServiceUrl;
	}

	public String getDeleteGroupMemberUrl() {
		return deleteGroupMemberUrl;
	}

	public void setDeleteGroupMemberUrl(String deleteGroupMemberUrl) {
		this.deleteGroupMemberUrl = deleteGroupMemberUrl;
	}

	public String getDefaultUserType() {
		return defaultUserType;
	}

	public void setDefaultUserType(String defaultUserType) {
		this.defaultUserType = defaultUserType;
	}

	public int getCheckBrowserFlag() {
		return checkBrowserFlag;
	}

	public void setCheckBrowserFlag(int checkBrowserFlag) {
		this.checkBrowserFlag = checkBrowserFlag;
	}

	public String getDowloadBrowserPath() {
		return dowloadBrowserPath;
	}

	public void setDowloadBrowserPath(String dowloadBrowserPath) {
		this.dowloadBrowserPath = dowloadBrowserPath;
	}
	
}