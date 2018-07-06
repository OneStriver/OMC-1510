package com.sunkaisens.omc.service.impl.hss;

import java.io.ByteArrayOutputStream;
import java.io.CharArrayWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.StringReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.sunkaisens.omc.context.core.OmcServerContext;
import com.sunkaisens.omc.exception.CustomException;
import com.sunkaisens.omc.mapper.core.EntityMapper;
import com.sunkaisens.omc.mapper.core.HssMetaMapper;
import com.sunkaisens.omc.mapper.core.ModuleMapper;
import com.sunkaisens.omc.mapper.cscf.UeMapper;
import com.sunkaisens.omc.mapper.hss.AucMapper;
import com.sunkaisens.omc.mapper.hss.EPCSubscriptionDataMapper;
import com.sunkaisens.omc.mapper.hss.EPCSubscriptionTFTMapper;
import com.sunkaisens.omc.mapper.hss.GroupInfoMapper;
import com.sunkaisens.omc.mapper.hss.HlrMapper;
import com.sunkaisens.omc.mapper.hss.HlrpsMapper;
import com.sunkaisens.omc.mapper.hss.MsserviceMapper;
import com.sunkaisens.omc.mapper.hss.TerminalInfoMapper;
import com.sunkaisens.omc.mapper.hss.impl.HlrMapperImpl;
import com.sunkaisens.omc.mapper.hssreduancy.ReHlrMapper;
import com.sunkaisens.omc.po.core.Entity;
import com.sunkaisens.omc.po.core.Module;
import com.sunkaisens.omc.po.cscf.Ue;
import com.sunkaisens.omc.po.hss.Auc;
import com.sunkaisens.omc.po.hss.GroupInfo;
import com.sunkaisens.omc.po.hss.Hlr;
import com.sunkaisens.omc.po.hss.Hlrps;
import com.sunkaisens.omc.po.hss.Msservice;
import com.sunkaisens.omc.po.hss.TerminalInfo;
import com.sunkaisens.omc.service.hss.HssService;
import com.sunkaisens.omc.service.impl.hssThread.AucThread;
import com.sunkaisens.omc.service.impl.hssThread.EpcSubscriptionDataThread;
import com.sunkaisens.omc.service.impl.hssThread.EpcSubscriptionTFTThread;
import com.sunkaisens.omc.service.impl.hssThread.HlrThread;
import com.sunkaisens.omc.service.impl.hssThread.HlrpsThread;
import com.sunkaisens.omc.service.impl.hssThread.MsserviceThread;
import com.sunkaisens.omc.service.impl.hssThread.TerminalInfoThread;
import com.sunkaisens.omc.service.impl.hssThread.UeThread;
import com.sunkaisens.omc.thread.cncpMsg.CncpProtoType;
import com.sunkaisens.omc.thread.cncpMsg.CncpTaskExecutor;
import com.sunkaisens.omc.thread.cncpMsg.SendCncpMsgFactory;
import com.sunkaisens.omc.thread.messageEncapsulation.OmcToNIMsg;
import com.sunkaisens.omc.thread.messageEncapsulation.QueryMsg;
import com.sunkaisens.omc.thread.messageEncapsulation.QueryResponseMsg;
import com.sunkaisens.omc.thread.messageEncapsulation.SetUpMsg;
import com.sunkaisens.omc.thread.messageEncapsulation.SetUpResponseMsg;
import com.sunkaisens.omc.util.CncpStatusTransUtil;
import com.sunkaisens.omc.util.ReflectionUtil;
import com.sunkaisens.omc.util.hss.RemoteDbUtil;
import com.sunkaisens.omc.vo.core.PageBean;
import com.sunkaisens.omc.vo.hss.HssBusinessVo;
import com.sunkaisens.omc.websocket.OamAlarmEndpoint;

import net.sf.json.JSONObject;

/**
 * HssService 接口实现类
 */
@Service
public class HssServiceImpl implements HssService {
	protected final Logger log = LoggerFactory.getLogger(getClass());

	@Resource
	private HlrMapper hlrMapper;
	@Resource
	private HlrpsMapper hlrpsMapper;
	@Resource
	private MsserviceMapper msserviceMapper;
	@Resource
	private EPCSubscriptionDataMapper epcSubscriptionDataMapper;
	@Resource
	private AucMapper aucMapper;
	@Resource
	private EPCSubscriptionTFTMapper epcSubscriptionTFTMapper;
	@Resource
	private TerminalInfoMapper terminalInfoMapper;
	@Resource
	private GroupInfoMapper groupInfoMapper;
	@Resource
	private ModuleMapper moduleMapper;
	@Resource
	private EntityMapper entityMapper;
	@Resource
	private UeMapper ueMapper;
	@Resource
	private HssMetaMapper hssMetaMapper;
	@Resource
	private SendCncpMsgFactory sendCncpMsgFactory;
	@Resource
	private CncpTaskExecutor cncpTaskExecutor;
	@Resource
	private ReHlrMapper rehlrMapper;

	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	private List<Hlr> hlresCount = new ArrayList<>();
	private List<Hlr> hlresCountTem = new ArrayList<>();
	private Map<String,String> cacheViewItem = new ConcurrentHashMap<String, String>();
	private HssBusinessVo userOperationObject = new HssBusinessVo();
	private ExecutorService executorServicePool = Executors.newCachedThreadPool();
	
	@PostConstruct
	public void getModel() {
		cacheViewItem.clear();
		List<Map<String,?>> viewItems = hssMetaMapper.getEnableViewItem();
		for(Map<String,?> map:viewItems){
			cacheViewItem.put(map.get("name").toString(), map.get("enable").toString());
		}
    }
	
	public PageBean getRePageBean(int pageNum, int pageSize, HssBusinessVo hss) {
		int count = rehlrMapper.selectCount(hss);
		List<Hlr> hlrs = rehlrMapper.select((pageNum - 1) * pageSize, Math.min(pageSize, count), hss);
		PageBean p = new PageBean(pageNum, pageSize, hlrs, count);
		return p;
	}

	@Override
	public PageBean getPageBeanLine(int pageNum, int pageSize, HssBusinessVo hss) {
		int count = hlrMapper.selectCount(hss);
		List<Hlr> hlrs = hlrMapper.select((pageNum - 1) * pageSize, Math.min(pageSize, count), hss);
		PageBean p = new PageBean(pageNum, pageSize, hlrs, count);
		return p;
	}

	@Override
	public PageBean getPageBean(int pageNum, int pageSize, HssBusinessVo hss) {
		//首先是清空数组
		hlresCount.clear();
		hlresCountTem.clear();
		PageBean p = null;
		String swoon = OmcServerContext.getInstance().getSwoon();
		int swoon_flag = 1;
		if (swoon.equals("true")) {
			swoon_flag = 1;
		} else if (swoon.equals("false")) {
			swoon_flag = 0;
		}
		// 主备标志位
		int flag = 1;
		Integer deviceStatus = RemoteDbUtil.selectDeviceStatus();
		if (deviceStatus != null) {
			flag = deviceStatus;
		}
		// 获取hlr条数
		int count = RemoteDbUtil.selectMainOrBackUpHlrCount(flag, hss);
		log.info("远端HLR表的Count值:" + count);
		List<Hlr> hlrs = RemoteDbUtil.selectMainOrBackUpHlr(flag, (pageNum - 1) * pageSize, Math.min(pageSize, count),hss);
		if (hss.getStatus() == null) { // 查询所有的用户
			log.info(">>>>>>>查询所有的用户>>>>>>>>");
			for (Hlr hlr : hlrs) {
				String ueSwoonStr = cacheViewItem.get("UeSwoon");
				if(ueSwoonStr!=null && !"".equals(ueSwoonStr)){
					hlr.setSwoonFlag(Integer.valueOf(ueSwoonStr));
				}
				String destroyStr = cacheViewItem.get("UeDestroy");
				if(destroyStr!=null && !"".equals(destroyStr)){
					hlr.setDestroyFlag(Integer.valueOf(destroyStr));
				}
				String airMonitorStr = cacheViewItem.get("AirMonitor");
				if(airMonitorStr!=null && !"".equals(airMonitorStr)){
					hlr.setAirMonitorFlag(Integer.valueOf(airMonitorStr));
				}
				Hlrps selectHlrps = RemoteDbUtil.selectMainOrBackUpHlrpsById(flag, hlr.getImsi());
				if (selectHlrps == null) {
					hlresCount.add(hlr);
					continue;
				}
				// 在线离线状态是两者|的结果
				hlr.setStatus((selectHlrps.getStatus() | hlr.getStatus()));
				// MSC地址和网关地址
				if (hlr.getCurrloc().equals("@") & !selectHlrps.getCurrloc().equals("@")) {
					hlr.setCurrloc(selectHlrps.getCurrloc());
				}
				hlresCount.add(hlr);
			}
		} else if (hss.getStatus() == 0) { // 查询离线的用户
			log.info(">>>>>>>查询所有的离线用户>>>>>>>>");
			for (Hlr hlr : hlrs) {
				String ueSwoonStr = cacheViewItem.get("UeSwoon");
				if(ueSwoonStr!=null && !"".equals(ueSwoonStr)){
					hlr.setSwoonFlag(Integer.valueOf(ueSwoonStr));
				}
				String destroyStr = cacheViewItem.get("UeDestroy");
				if(destroyStr!=null && !"".equals(destroyStr)){
					hlr.setDestroyFlag(Integer.valueOf(destroyStr));
				}
				String airMonitorStr = cacheViewItem.get("AirMonitor");
				if(airMonitorStr!=null && !"".equals(airMonitorStr)){
					hlr.setAirMonitorFlag(Integer.valueOf(airMonitorStr));
				}
				Hlrps selectHlrps = RemoteDbUtil.selectMainOrBackUpHlrpsById(flag, hlr.getImsi());
				if (selectHlrps == null) {
					if(hlr.getStatus()==0){
						hlresCount.add(hlr);
					}
					continue;
				}
				if ((selectHlrps.getStatus() | hlr.getStatus()) == 0) {
					hlr.setStatus(0);
					// MSC地址和网管地址
					if (hlr.getCurrloc().equals("@") & !selectHlrps.getCurrloc().equals("@")) {
						hlr.setCurrloc(selectHlrps.getCurrloc());
					}
					hlresCount.add(hlr);
				}
			}
		} else if (hss.getStatus() == 1) { // 查询在线的用户
			log.info(">>>>>>>查询所有的在线用户>>>>>>>>");
			for (Hlr hlr : hlrs) {
				String ueSwoonStr = cacheViewItem.get("UeSwoon");
				if(ueSwoonStr!=null && !"".equals(ueSwoonStr)){
					hlr.setSwoonFlag(Integer.valueOf(ueSwoonStr));
				}
				String destroyStr = cacheViewItem.get("UeDestroy");
				if(destroyStr!=null && !"".equals(destroyStr)){
					hlr.setDestroyFlag(Integer.valueOf(destroyStr));
				}
				String airMonitorStr = cacheViewItem.get("AirMonitor");
				if(airMonitorStr!=null && !"".equals(airMonitorStr)){
					hlr.setAirMonitorFlag(Integer.valueOf(airMonitorStr));
				}
				Hlrps selectHlrps = hlrpsMapper.selectById(hlr.getImsi());
				if (selectHlrps == null) {
					if(hlr.getStatus()==1){
						hlresCount.add(hlr);
					}
					continue;
				}
				if ((selectHlrps.getStatus() | hlr.getStatus()) == 1) {
					hlr.setStatus(1);
					// MSC地址和网管地址
					if (hlr.getCurrloc().equals("@") & !selectHlrps.getCurrloc().equals("@")) {
						hlr.setCurrloc(selectHlrps.getCurrloc());
					}
					hlresCount.add(hlr);
				}
			}
		}
		p = new PageBean(pageNum, pageSize, hlresCount, count);
		return p;
	}

	/**
	 * 添加单个用户
	 */
	@Override
	public void save(HssBusinessVo entry) throws CustomException {
		if (null != hlrMapper.selectById(entry.getImsi())) {
			throw new CustomException("终端用户识别码【" + entry.getImsi() + "】已存在");
		}
		if (null != hlrMapper.selectByMdn(entry.getMdn())) {
			throw new CustomException("电话号码【" + entry.getMdn() + "】已存在");
		}
		//设置默认用户类型
		if(entry.getUserType()==null){
			String defaultUserType = OmcServerContext.getInstance().getDefaultUserType();
			entry.setUserType(Integer.valueOf(defaultUserType));
		}
		// 某些设备类型的用户添加发送消息给群组
		if (entry.getDeviceType() == 1 || entry.getDeviceType() == 8 || entry.getDeviceType() == 12
				|| entry.getDeviceType() == 7 || entry.getDeviceType() == 5) {
			SetUpResponseMsg responeMsg = sendRemoteHssCreate(1, entry);
		}
		//默认遥晕,遥毙正常,对空监听开启
		entry.setSwoon(1);
		entry.setDestroy(1);
		entry.setAirMonitor(1);
		//CS域
		hlrMapper.insert(convertToHlrEntity(entry));
		//AUC鉴权
		aucMapper.insert(convertToAucEntity(entry));
		//补充业务中的四个前转和分组业务中的静态分配的IP地址
		msserviceMapper.insert(convertToMsserviceEntity(entry));
		//终端信息
		terminalInfoMapper.insert(convertToTerminalInfoEntity(entry));
		// 开启分组业务(分组业务中的开启业务复选框控制)
		if (entry.getRegion() == 1) {
			hlrpsMapper.insert(convertToHlrpsEntity(entry));
		}
		// 判断是否含有组业务
		if (hasGroupBusi(entry.getDeviceType()) && entry.getGroups() != null) {
			groupInfoMapper.insert(convertToGroupInfoEntity(entry));
		}
		// 同步CSCF
		syncCreateCscf(entry);
		if (entry.getDeviceType() == 8) {
			entry.setUserType(1);
		} else {
			entry.setUserType(3);
		}
		// 发送给1510北向接口
		send1510North(entry, 1);
	}
	
	/**
	 * 发送同步消息
	 */
	@Override
	public void sendSync(int flag, String imsi) throws CustomException {
		if (imsi == null){
			throw new IllegalArgumentException("imsi不能为空");
		}
		// Module module = moduleMapper.selectByName("hss");
		Module module = RemoteDbUtil.selectRemoteModule(flag, "hss");
		if (module == null) {
			throw new CustomException("网元【hss】还未上传");
		}
		Integer moduleId = module.getId();
		Integer instId = null;
		// List<Entity> entities = entityMapper.selectByModuleId(moduleId);
		List<Entity> entities = RemoteDbUtil.selectRemoteEntity(flag, moduleId);
		if (entities.size() == 0) {
			OamAlarmEndpoint.broadcast("网元【hss】还未下发");
			return;
		} else if (entities.size() == 1) {
			instId = entities.get(0).getInstId();
		} else {
			HlrMapperImpl impl = (HlrMapperImpl) hlrMapper;
			instId = Long.parseLong(imsi) % impl.getHssDataSourceCount() == 0 ? impl.getHssDataSourceCount()
					: (int) Long.parseLong(imsi) % impl.getHssDataSourceCount();
		}
		String data = "UE:" + imsi + "\n";
		for (int i = 0; i < 3; i++) {
			SetUpMsg msg = sendCncpMsgFactory.createSendSetCncpMsg(
					CncpProtoType.OMC_HEADER, CncpProtoType.OAM_HEADER, CncpProtoType.CNCP_SET_MSG, moduleId, instId, (char)CncpProtoType.OAM_SET_UE_SYNC, data);
			SetUpResponseMsg resmsg;
			if (flag == 1) {
				resmsg = cncpTaskExecutor.invokeSetUpMsg(msg, true, OmcServerContext.getInstance().getMainDeviceIp(), OmcServerContext.getInstance().getMainDevicePort());
			} else {
				resmsg = cncpTaskExecutor.invokeSetUpMsg(msg, true, OmcServerContext.getInstance().getBackUpDeviceIp(), OmcServerContext.getInstance().getBackUpDevicePort());
			}
			int result = resmsg.getResult();
			if (result == 0){
				return;
			}
		}
	}

	//给OAM发送消息
	public void sendSetUes(final String[] imsis, final int update_delete) {
		Runnable r = new Runnable() {
			public void run() {
				Module module = moduleMapper.selectByName("hss");
				if (module == null) {
					OamAlarmEndpoint.broadcast("网元【hss】还未下发");
					return;
				}
				Integer moduleId = module.getId();
				Integer instId = null;
				List<Entity> entities = entityMapper.selectByModuleId(moduleId);
				if (entities.size() == 0) {
					OamAlarmEndpoint.broadcast("网元【hss】还未下发");
					return;
				}
				StringBuilder error = new StringBuilder();
				for (int i = 0; i < imsis.length; i++) {
					String imsi = imsis[i];
					if (entities.size() == 1) {
						instId = entities.get(0).getInstId();
					} else {
						HlrMapperImpl hlrMapperImpl = (HlrMapperImpl) hlrMapper;
						int count = hlrMapperImpl.getHssDataSourceCount();
						instId = Long.parseLong(imsi) % count == 0 ? count : (int) (Long.parseLong(imsi) % count);
					}

					String message = "UE:" + imsi + "\n";
					SetUpResponseMsg resp = null;
					for (int j = 0; j < 3; i++) {
						SetUpMsg msg =  sendCncpMsgFactory.createSendSetCncpMsg(
								CncpProtoType.OMC_HEADER, CncpProtoType.OAM_HEADER, CncpProtoType.CNCP_SET_MSG, 
								moduleId, instId, update_delete, message);
						resp = cncpTaskExecutor.invokeSetUpMsg(msg, true, OmcServerContext.getInstance().getOamIp(), OmcServerContext.getInstance().getOamPort());
						if (resp.getResult() != 1){
							break;
						}
					}
					
					if (resp.getResult() != 0) {
						if (resp.getResult() == CncpProtoType.TIMEOUT) {
							OamAlarmEndpoint.broadcast("与HSS同步超时");
							return;
						}
						if (update_delete != CncpProtoType.OAM_SET_UE_DELETE)
							error.append("IMSI【" + imsi + "】同步失败\n");
					}
				}
				if (error.length() > 0){
					OamAlarmEndpoint.broadcast(error.toString());
				}
			}
		};
		executorServicePool.execute(r);
	}

	/**
	 * 修改和删除给远端HSS发送的消息
	 */
	public void sendRemoteSetUe(final int flag, final String imsi, final int update_delete) {
		Module module = RemoteDbUtil.selectRemoteModule(flag, "hss");
		if (module == null) {
			if (flag == 1) {
				OamAlarmEndpoint.broadcast("主设备连接失败，请检查连接！！！");
				return;
			} else {
				OamAlarmEndpoint.broadcast("备设备连接失败，请检查连接！！！");
				return;
			}
		}
		Integer moduleId = module.getId();
		Integer instId = null;
		List<Entity> entities = RemoteDbUtil.selectRemoteEntity(flag, moduleId);
		if (entities.size() == 0) {
			if (flag == 1) {
				OamAlarmEndpoint.broadcast("主设备连接失败,请检查连接！！！");
				return;
			} else {
				OamAlarmEndpoint.broadcast("备设备连接失败,请检查连接！！！");
				return;
			}
		} else if (entities.size() == 1) {
			instId = entities.get(0).getInstId();
		} else {
			HlrMapperImpl hlrMapperImpl = (HlrMapperImpl) hlrMapper;
			int count = hlrMapperImpl.getHssDataSourceCount();
			instId = Long.parseLong(imsi) % count == 0 ? count : (int) (Long.parseLong(imsi) % count);
		}
		String message = "UE:" + imsi + "\n";
		log.info("实例》》》moduleId=" + moduleId + ",》》》instId=" + instId);
		SetUpResponseMsg resp = null;
		for (int i = 0; i < 3; i++) {
			SetUpMsg msg =  sendCncpMsgFactory.createSendSetCncpMsg(
					CncpProtoType.OMC_HEADER, CncpProtoType.OAM_HEADER, CncpProtoType.CNCP_SET_MSG, 
					moduleId, instId, update_delete, message);
			if (flag == 1) {
				resp = cncpTaskExecutor.invokeSetUpMsg(msg, true, OmcServerContext.getInstance().getMainDeviceIp(), OmcServerContext.getInstance().getMainDevicePort());
			} else {
				resp = cncpTaskExecutor.invokeSetUpMsg(msg, true, OmcServerContext.getInstance().getBackUpDeviceIp(), OmcServerContext.getInstance().getBackUpDevicePort());
			}
			if (resp.getResult() != 1){
				break;
			}
		}
		if (resp.getResult() != 0) {
			if (flag == 1) {
				OamAlarmEndpoint.broadcast("主设备HSS同步失败,请检查配置！！！");
			} else {
				OamAlarmEndpoint.broadcast("备设备HSS同步失败,请检查配置！！！");
			}
		}
	}

	private boolean hasGroupBusi(int devType) {
		int[] busiGroupArr = new int[] { 6, 7 };
		return ArrayUtils.contains(busiGroupArr, devType);
	}

	@Override
	public void saveEntries(Collection<HssBusinessVo> entries) throws CustomException {
		for (HssBusinessVo entry : entries) {
			save(entry);
		}
	}

	private void syncDeleteCscf(String mdn) {
		ueMapper.deleteByName(mdn);
	}

	private void syncCreateCscf(HssBusinessVo entry) {
		if ((entry.getDeviceType() == 12 || entry.getDeviceType() == 8) && hssMetaMapper.isEnable("ims")) {
			final String sipUriPatten = "sip:%s@%s";
			Ue ue = new Ue();
			ue.setUeName(entry.getMdn());
			if (entry.getUePassword() == null) {
				ue.setUePassword("");
			} else {
				ue.setUePassword(entry.getUePassword());
			}
			ue.setUeUri(String.format(sipUriPatten, entry.getMdn(), entry.getDomain()));
			// 以下是数据库不允许为空的字段, 而且没有给予默认值, 如不注入参数会引发异常
			ue.setUePcscfDomain("");
			ue.setUeScscfDomain("");
			ue.setUeHomeScscfDomain("");
			ue.setUeScscfAddr("0.0.0.0:0");
			ue.setUePcscfAddr("0.0.0.0:0");
			ue.setUeAddr("0.0.0.0:0");
			ue.setUeStatus(0);
			ue.setUeUpdateType(0);
			ue.setUeHomeScscfAddr("0.0.0.0:0");
			ue.setUeUpdateTstamp(new Date());
			if (ueMapper.selectByName(entry.getMdn()) == null) {
				ueMapper.insert(ue);
			} else {
				ueMapper.update(ue);
			}
		}
	}
	
	/**
	 * 更新用户操作
	 */
	@Override
	public boolean update(HssBusinessVo entry,Integer userOPerationType) throws CustomException {
		if (null == hlrMapper.selectById(entry.getImsi())) {
			throw new CustomException("此终端用户已不存在");
		}
		Hlr hlr = hlrMapper.selectById(entry.getImsi());
		if (!hlr.getEsn().equals(entry.getEsn())) {
			if (null != hlrMapper.selectByEsn(entry.getEsn())) {
				throw new CustomException("ESN号码【" + entry.getEsn() + "】已存在");
			}
		}
		// 更新用户发送消息给群组
		if (entry.getDeviceType() == 1 || entry.getDeviceType() == 8 || entry.getDeviceType() == 12
				|| entry.getDeviceType() == 7 || entry.getDeviceType() == 5) {
			// 发送给主设备的群组
			SetUpResponseMsg responseMsg = sendRemoteHssUpdate(1, entry);
		}
		hlrMapper.update(convertToHlrEntity(entry));
		aucMapper.update(convertToAucEntity(entry));
		msserviceMapper.update(convertToMsserviceEntity(entry));
		TerminalInfo terminal = terminalInfoMapper.selectById(entry.getImsi());
		if (terminal != null) {
			terminalInfoMapper.update(convertToTerminalInfoEntity(entry));
		} else {
			terminal = convertToTerminalInfoEntity(entry);
			terminalInfoMapper.insert(terminal);
		}
		groupInfoMapper.deleteById(entry.getImsi());
		if (hasGroupBusi(entry.getDeviceType()) && entry.getGroups() != null && entry.getGroups().length > 0) {
			GroupInfo groupInfo = convertToGroupInfoEntity(entry);
			groupInfoMapper.insert(groupInfo);
		}
		if (entry.getRegion() == 1) {
			if (hlrpsMapper.selectById(entry.getImsi()) != null){
				hlrpsMapper.update(convertToHlrpsEntity(entry));
			}else{
				hlrpsMapper.insert(convertToHlrpsEntity(entry));
			}
		} else {
			hlrpsMapper.deleteById(entry.getImsi());
		}
		// 同步CSCF
		syncCreateCscf(entry);
		entry.setCreateTime(terminal.getCreateTime());
		
		// 发送给1510北向接口
		if(userOPerationType!=4){
			System.err.println("修改操作优先级:"+entry.getPriority());
			send1510North(entry, 2);
		}
		// 发送给HSS TODO
		if (!"yewu".equals(OmcServerContext.getInstance().getProject())) {
			// 主设备发送消息
			sendRemoteSetUe(1, entry.getImsi(), CncpProtoType.OAM_SET_UE_UPDATE);
			// 备设备发送消息
			sendRemoteSetUe(0, entry.getImsi(), CncpProtoType.OAM_SET_UE_UPDATE);
		}
		return true;
	}

	/**
	 * 删除用户数据
	 */
	@Override
	public void deleteEntries(String[] imsis) throws CustomException {
		if (imsis == null){
			throw new CustomException("至少选择一条记录删除！");
		}
		for (String imsi : imsis) {
			deleteEntry(imsi);
		}
	}
	@Override
	public void deleteEntry(String imsi) throws CustomException {
		if (imsi == null) {
			throw new IllegalArgumentException("imsi不能为空");
		}
		Hlr deleteHlr = hlrMapper.selectById(imsi);
		// 删除用户发送消息给群组
		if (deleteHlr.getDeviceType() == 1 || deleteHlr.getDeviceType() == 8 || deleteHlr.getDeviceType() == 12
				|| deleteHlr.getDeviceType() == 7 || deleteHlr.getDeviceType() == 5) {
			SetUpResponseMsg responseMsg = sendRemoteHssDelete(1, imsi);
		}
		HssBusinessVo hss = hlrMapper.selectHssById(imsi);
		String mdn = hss.getMdn();
		hlrMapper.deleteById(imsi);
		aucMapper.deleteById(imsi);
		msserviceMapper.deleteById(imsi);
		hlrpsMapper.deleteById(imsi);
		terminalInfoMapper.deleteById(imsi);
		groupInfoMapper.deleteById(imsi);
		
		syncDeleteCscf(mdn);
		//发送给1510北向接口
		send1510North(hss, 3);
		// 发送给HSS TODO
		if (!"yewu".equals(OmcServerContext.getInstance().getProject())) {
			// 发送给主设备的消息
			sendRemoteSetUe(1, imsi, CncpProtoType.OAM_SET_UE_DELETE);
			// 发送给备设备的消息
			sendRemoteSetUe(0, imsi, CncpProtoType.OAM_SET_UE_DELETE);
		}
	}

	public JSONObject readStatus(int flag, String imsi, ResourceBundle bundle) throws CustomException {
		ParseUserMsg parseUserMsg = new ParseUserMsg();
		HssBusinessVo vo = getById(imsi);
		// Module module = moduleMapper.selectByName("hss");
		Module module = RemoteDbUtil.selectRemoteModule(flag, "hss");
		if (module == null) {
			throw new CustomException("网元【hss】还未上传");
		}
		Integer moduleId = module.getId();
		Integer instId = null;
		// List<Entity> entities = entityMapper.selectByModuleId(moduleId);
		List<Entity> entities = RemoteDbUtil.selectRemoteEntity(flag, moduleId);
		if (entities.size() == 0) {
			throw new CustomException("网元【hss】还未下发");
		} else if (entities.size() == 1) {
			instId = entities.get(0).getInstId();
		} else {
			HlrMapperImpl hlrMapperImpl = (HlrMapperImpl) hlrMapper;
			int count = hlrMapperImpl.getHssDataSourceCount();
			instId = Long.parseLong(imsi) % count == 0 ? count : (int) (Long.parseLong(imsi) % count);
		}
		String ims = vo.getImsi();
		String message = "UE:" + ims + "\n";
		QueryResponseMsg respMsg;
		QueryMsg msg = sendCncpMsgFactory.createSendQueryCncpMsg(
				CncpProtoType.OMC_HEADER, CncpProtoType.OAM_HEADER, CncpProtoType.CNCP_GET_MSG, 
				moduleId, instId, CncpProtoType.OAM_GET_UE, message);
		if(flag==1){
			respMsg = cncpTaskExecutor.invokeQueryMsg(msg, true, OmcServerContext.getInstance().getMainDeviceIp(), OmcServerContext.getInstance().getMainDevicePort());
		}else{
			respMsg = cncpTaskExecutor.invokeQueryMsg(msg, true, OmcServerContext.getInstance().getBackUpDeviceIp(), OmcServerContext.getInstance().getBackUpDevicePort());
		}
		if (respMsg.getResult() != CncpProtoType.SUCCESS) {
			throw new CustomException(CncpStatusTransUtil.transLocaleStatusMessage(respMsg.getResult()));
		} else {
			String respDataString = respMsg.getData();
			log.info("收到的数据:" + respDataString);
			Map<String, String> map = parseUserMsg.parseString(respDataString, bundle, vo.getRegion() == 1);
			JSONObject result = JSONObject.fromObject(map);
			log.info("读取用户状态结果：" + result.toString());
			return result;
		}
	}

	/**
	 * 通过GET_UE消息获取Hss给的用户的所有信息(2018-6-28日志)
	 */
	public HssBusinessVo setHssBussinessVoValue(String returnData, HssBusinessVo vo) {
		String[] cutStrings = returnData.split("\n");
		for (String singleStr : cutStrings) {
			log.info("解析消息的数据:" + singleStr);
			if (singleStr.startsWith("AuC_Data")) {
				log.info("AuC_Data的数据");
				String[] allAucData = singleStr.substring(9, singleStr.length() - 4).split("\\|");
				// KEY|OP|OPC|AMF|RAND|SQN|START|STOP|END
				vo.setK(allAucData[0].trim());
				vo.setOp(allAucData[1].trim());
				vo.setOpc(allAucData[2].trim());
				vo.setAmf(allAucData[3].trim());
				vo.setRand(allAucData[4].trim());
				vo.setSqn(allAucData[5].trim());
				vo.setStart(allAucData[6].trim());
				vo.setStop(allAucData[7].trim());
			}
			if (singleStr.startsWith("CS_Data")) {
				log.info("CS_Data的数据"+singleStr);
				String[] allCsData = singleStr.substring(8, singleStr.length() - 4).split("\\|");
				// #CS_Data:ESN|TMSI|RncLoc|GeoLoc|GKIP:GKPort|GWIP:GWPort|Status|VlrIP:VlrPort|VoCoder|Profile|Profile_ext|FwdUNC|FwdOnBusyNum|FwdNoAnswer|FwdNANum|VoicemailNum|wiretapAddr|END
				vo.setEsn(allCsData[0].trim());
				vo.setTmsi(allCsData[1].trim());
				vo.setCurrloc(allCsData[4].trim() + "@" + allCsData[5].trim());
				vo.setStatus(Integer.valueOf(allCsData[6].trim()));
				vo.setVlraddr(allCsData[7].trim() + "@");
				vo.setMsvocodec(Integer.valueOf(allCsData[8].trim()));
				//vo.setMsprofile(Integer.valueOf(allCsData[9].trim()));
				//vo.setMsprofile_extra(Integer.valueOf(allCsData[10].trim()));
				if ((allCsData.length - 1) >= 11) {
					if (allCsData[11].trim() != null && !"".equals(allCsData[11].trim())) {
						vo.setDirectFwd(1);
						vo.setDirectFwdNum(allCsData[11].trim());
					}
				}
				if ((allCsData.length - 1) >= 12) {
					if (allCsData[12].trim() != null && !"".equals(allCsData[12].trim())) {
						vo.setFwdOnBusy(1);
						vo.setFwdOnBusyNum(allCsData[12].trim());
					}
				}
				if ((allCsData.length - 1) >= 13) {
					if (allCsData[13].trim() != null && !"".equals(allCsData[13].trim())) {
						vo.setFwdNoAnswer(1);
						vo.setFwdNoAnswerNum(allCsData[13].trim());
					}
				}
				if ((allCsData.length - 1) >= 14) {
					if (allCsData[14].trim() != null && !"".equals(allCsData[14].trim())) {
						vo.setFwdNA(1);
						vo.setFwdNANum(allCsData[14].trim());
					}
				}
				if ((allCsData.length - 1) >= 15) {
					if (allCsData[15].trim() != null && !"".equals(allCsData[15].trim())) {
						vo.setVoiceMailSwitch(1);
						vo.setVoiceMailNum(allCsData[15].trim());
					}
				}
				if ((allCsData.length - 1) >= 16) {
					if (allCsData[16].trim() != null && !"".equals(allCsData[16].trim())) {
						String monitorStr = allCsData[16].trim();
						String[] monitorSplit = monitorStr.split(":");
						vo.setMonitorSwitch(1);
						if(monitorSplit.length!=0){
							vo.setMonitorIP(monitorSplit[0]);
							vo.setMonitorPort(monitorSplit[1]);
						}
					}
				}
			}
			if (singleStr.startsWith("GROUP_Data")) {
				log.info("GROUP_Data的数据"+singleStr);
				String[] groupData = singleStr.substring(11, singleStr.length() - 4).split("\\|");
				// #GROUP_Data:GroupNum1|GroupNum2|GroupNum3|GroupNum4|GroupNum5|GroupNum6|GroupNum7|GroupNum8|GroupNum9|GroupNum10|GroupNum11|GroupNum12|GroupNum13|GroupNum14|GroupNum15|GroupNum16|END
				if ((groupData.length - 1) >= 0) {
					vo.setGroup1(groupData[0].trim());
				}
				if ((groupData.length - 1) >= 1) {
					vo.setGroup2(groupData[1].trim());
				}
				if ((groupData.length - 1) >= 2) {
					vo.setGroup3(groupData[2].trim());
				}
				if ((groupData.length - 1) >= 3) {
					vo.setGroup4(groupData[3].trim());
				}
				if ((groupData.length - 1) >= 4) {
					vo.setGroup5(groupData[4].trim());
				}
				if ((groupData.length - 1) >= 5) {
					vo.setGroup6(groupData[5].trim());
				}
				if ((groupData.length - 1) >= 6) {
					vo.setGroup7(groupData[6].trim());
				}
				if ((groupData.length - 1) >= 7) {
					vo.setGroup8(groupData[7].trim());
				}
				if ((groupData.length - 1) >= 8) {
					vo.setGroup9(groupData[8].trim());
				}
				if ((groupData.length - 1) >= 9) {
					vo.setGroup10(groupData[9].trim());
				}
				if ((groupData.length - 1) >= 10) {
					vo.setGroup11(groupData[10].trim());
				}
				if ((groupData.length - 1) >= 11) {
					vo.setGroup12(groupData[11].trim());
				}
				if ((groupData.length - 1) >= 12) {
					vo.setGroup13(groupData[12].trim());
				}
				if ((groupData.length - 1) >= 13) {
					vo.setGroup14(groupData[13].trim());
				}
				if ((groupData.length - 1) >= 14) {
					vo.setGroup15(groupData[14].trim());
				}
				if ((groupData.length - 1) >= 15) {
					vo.setGroup16(groupData[15].trim());
				}
			}
		}
		return vo;
	}
	
	/**
	 * 修改的时候获取修改的用户信息
	 */
	@Override
	public HssBusinessVo getById(String imsi) throws CustomException {
		HssBusinessVo voTemp = null;
		HssBusinessVo vo = hlrMapper.selectHssById(imsi);
		if (vo == null) {
			throw new CustomException("此终端用户已不存在！");
		}
		logger.info(">>>>>>>获取本地的Msprofile值>>>:"+vo.getMsprofile());
		// 解码
		vo.decodeMsprofileCtx(1);
		vo.decodeMsprofileExtraCtx();
		// 操作HSS库中的数据 默认 1是主设备
		int operationDbflag = 1;
		//从网管获取现在是在哪个设备上
		String connectNms = OmcServerContext.getInstance().getConnectNmsDB();
		if("true".equals(connectNms)){
			Integer deviceStatus = RemoteDbUtil.selectDeviceStatus();
			if (deviceStatus != null) {
				operationDbflag = deviceStatus;
			}
			QueryResponseMsg returnMsg = sendHssGetUe(operationDbflag, imsi, CncpProtoType.OAM_GET_UE);
			if (returnMsg.getResult() == CncpProtoType.SUCCESS) {
				String returnData = returnMsg.getData();
				log.info("获取用户信息》收到的消息:" + returnData);
				// 消息查询信息赋值
				voTemp = setHssBussinessVoValue(returnData, vo);
			}else{
				voTemp = vo;
			}
			logger.info(">>>>>>>获取远端的Msprofile值>>>:"+voTemp.getMsprofile());
			
			Hlrps hlrps = hlrpsMapper.selectById(imsi);
			if (hlrps != null) {
				Integer hrlpsMsprofile = hlrps.getMsprofile();
				voTemp.setOspfMulticast(((hrlpsMsprofile & 0x00080000) << 12) >>> 31); // OSPF组播;
				voTemp.setOspfBroadcast(((hrlpsMsprofile & 0x00040000) << 13) >>> 31); // OSPF广播;
			}
			
			Ue ue = ueMapper.selectByName(voTemp.getMdn());
			if (ue != null) {
				String[] ss = ue.getUeUri().split("\\:|\\@");
				if (ss != null && ss.length >= 3)
					voTemp.setDomain(ss[2]);
				voTemp.setUePassword(ue.getUePassword());
			}
			if (voTemp.getCreateTime() == null) {
				voTemp.setCreateTime(voTemp.getTstamp());
			}
			List<String> gs = new ArrayList<String>();
			if (vo.getGroup1() != null)
				gs.add(voTemp.getGroup1());
			if (vo.getGroup2() != null)
				gs.add(voTemp.getGroup2());
			if (vo.getGroup3() != null)
				gs.add(voTemp.getGroup3());
			if (vo.getGroup4() != null)
				gs.add(voTemp.getGroup4());
			if (vo.getGroup5() != null)
				gs.add(voTemp.getGroup5());
			if (vo.getGroup6() != null)
				gs.add(voTemp.getGroup6());
			if (vo.getGroup7() != null)
				gs.add(voTemp.getGroup7());
			if (vo.getGroup8() != null)
				gs.add(voTemp.getGroup8());
			if (vo.getGroup9() != null)
				gs.add(voTemp.getGroup9());
			if (vo.getGroup10() != null)
				gs.add(voTemp.getGroup10());
			if (vo.getGroup11() != null)
				gs.add(voTemp.getGroup11());
			if (vo.getGroup12() != null)
				gs.add(voTemp.getGroup12());
			if (vo.getGroup13() != null)
				gs.add(voTemp.getGroup13());
			if (vo.getGroup14() != null)
				gs.add(voTemp.getGroup14());
			if (vo.getGroup15() != null)
				gs.add(voTemp.getGroup15());
			if (vo.getGroup16() != null)
				gs.add(voTemp.getGroup16());
			voTemp.setGroups(gs.toArray(new String[0]));
			// 终端用户信息
			TerminalInfo terInfo = terminalInfoMapper.selectById(imsi);
			if (terInfo != null) {
				voTemp.setTerminalId(terInfo.getTerminalId());
				voTemp.setTerminalType(terInfo.getTerminalType());
				voTemp.setPowerLevel(terInfo.getPowerLevel());
				voTemp.setDepartment(terInfo.getDepartment());
			}
			return voTemp;
			
		}else{
			
			Hlrps hlrps = hlrpsMapper.selectById(imsi);
			if (hlrps != null) {
				Integer hrlpsMsprofile = hlrps.getMsprofile();
				vo.setOspfMulticast(((hrlpsMsprofile & 0x00080000) << 12) >>> 31); // OSPF组播;
				vo.setOspfBroadcast(((hrlpsMsprofile & 0x00040000) << 13) >>> 31); // OSPF广播;
			}
			Ue ue = ueMapper.selectByName(vo.getMdn());
			if (ue != null) {
				String[] ss = ue.getUeUri().split("\\:|\\@");
				if (ss != null && ss.length >= 3)
					vo.setDomain(ss[2]);
				vo.setUePassword(ue.getUePassword());
			}
			if (vo.getCreateTime() == null) {
				vo.setCreateTime(vo.getTstamp());
			}
			List<String> gs = new ArrayList<String>();
			if (vo.getGroup1() != null)
				gs.add(vo.getGroup1());
			if (vo.getGroup2() != null)
				gs.add(vo.getGroup2());
			if (vo.getGroup3() != null)
				gs.add(vo.getGroup3());
			if (vo.getGroup4() != null)
				gs.add(vo.getGroup4());
			if (vo.getGroup5() != null)
				gs.add(vo.getGroup5());
			if (vo.getGroup6() != null)
				gs.add(vo.getGroup6());
			if (vo.getGroup7() != null)
				gs.add(vo.getGroup7());
			if (vo.getGroup8() != null)
				gs.add(vo.getGroup8());
			if (vo.getGroup9() != null)
				gs.add(vo.getGroup9());
			if (vo.getGroup10() != null)
				gs.add(vo.getGroup10());
			if (vo.getGroup11() != null)
				gs.add(vo.getGroup11());
			if (vo.getGroup12() != null)
				gs.add(vo.getGroup12());
			if (vo.getGroup13() != null)
				gs.add(vo.getGroup13());
			if (vo.getGroup14() != null)
				gs.add(vo.getGroup14());
			if (vo.getGroup15() != null)
				gs.add(vo.getGroup15());
			if (vo.getGroup16() != null)
				gs.add(vo.getGroup16());
			vo.setGroups(gs.toArray(new String[0]));
			
			// 终端用户信息
			TerminalInfo terInfo = terminalInfoMapper.selectById(imsi);
			if (terInfo != null) {
				vo.setTerminalId(terInfo.getTerminalId());
				vo.setTerminalType(terInfo.getTerminalType());
				vo.setPowerLevel(terInfo.getPowerLevel());
				vo.setDepartment(terInfo.getDepartment());
			}
			return vo;
			
		}
	}

	@Override
	public boolean existMdn(String mdn) {
		return hlrMapper.selectByMdn(mdn) != null;
	}
	
	/**
	 * 批量更新操作(暂时没有使用) TODO
	 */
	@Override
	public void batchUpdate(final HssBusinessVo entry, final Integer batchCount, InputStream is) {
		if (entry.getDeviceType() == 1 || entry.getDeviceType() == 8 || entry.getDeviceType() == 12
				|| entry.getDeviceType() == 7 || entry.getDeviceType() == 5) {
			for (int i = 0; i < batchCount; i++) {
				HssBusinessVo vo = new HssBusinessVo();
				if (entry.getDeviceType() == 8) {
					vo.setUserType(1);
				} else {
					vo.setUserType(3);
				}
				vo.setDeviceType(entry.getDeviceType());
				vo.setImsi(Long.parseLong(entry.getImsi()) + i + "");
				vo.setMdn(Long.parseLong(entry.getMdn()) + i + "");
				vo.setDomain(entry.getDomain());
				vo.setUePassword(entry.getUePassword());
				sendHssUpdate(vo);
			}
		}

		final long start = System.currentTimeMillis();
		final CyclicBarrier barrier = new CyclicBarrier(2, new Runnable() {
			public void run() {
				long end = System.currentTimeMillis();
				log.info("======================用户批量修改完毕,用时" + (end - start) + "毫秒=============");
				OamAlarmEndpoint.broadcast("用户批量修改完毕,用时" + (end - start) / 1000.0f + "秒");
				if (is != null) {
					try {
						importAucExcel(is);
					} catch (CustomException e) {
						OamAlarmEndpoint.broadcast("excel错误," + e.getMessage());
					}
				}
			}
		});
		new Thread() {
			public void run() {
				try {
					if (entry.getRegion() == 1) {
						hlrpsMapper.batchAdd(batchCount, entry.getImsi(), entry.getMdn(), entry.getEsn(),
								entry.getMsprofile(), entry.getDeviceType(), entry.getMsvocodec(),
								entry.getMsprofile_extra());
					} else if (entry.getRegion() == 0) {
						hlrMapper.batchAdd(batchCount, entry.getImsi(), entry.getMdn(), entry.getEsn(),
								entry.getMsprofile(), entry.getDeviceType(), entry.getMsvocodec(),
								entry.getMsprofile_extra());
					}
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				try {
					barrier.await();
				} catch (InterruptedException | BrokenBarrierException e) {
					e.printStackTrace();
				}
			};
		}.start();
		if (entry.getDeviceType() == 8 || entry.getDeviceType() == 12) {
			new Thread() {
				public void run() {
					try {
						ueMapper.batchDelete(batchCount, entry.getMdn());
						ueMapper.batchAdd(batchCount, entry.getMdn(), entry.getDomain(), entry.getUePassword());
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					try {
						barrier.await();
					} catch (InterruptedException | BrokenBarrierException e) {
						e.printStackTrace();
					}
				};
			}.start();
		} else {
			new Thread() {
				public void run() {
					try {
						ueMapper.batchDelete(batchCount, entry.getMdn());
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					try {
						barrier.await();
					} catch (InterruptedException | BrokenBarrierException e) {
						e.printStackTrace();
					}
				};
			}.start();
		}
	}

	/**
	 * 批量修改工具类
	 */
	public HssBusinessVo setUpdateHssValue(HssBusinessVo newVo, HssBusinessVo oldVo) {
		
		newVo.setMsvocodec(oldVo.getMsvocodec()); 					// 语音编码
		newVo.setPairNet(oldVo.getPairNet()); 						// 双网
		newVo.setCircuitData(oldVo.getCircuitData());				//电路数据
		newVo.setHalfDuplexSingleCall(oldVo.getHalfDuplexSingleCall());//半双工单呼
		newVo.setDuplexSingleCall(oldVo.getDuplexSingleCall());		//双工单呼
		newVo.setInternationality(oldVo.getInternationality()); 	// 国际业务
		newVo.setNationality(oldVo.getNationality());				//国内业务(1510中是出局属性)
		//1510中没有使用
		newVo.setOspfMulticast(oldVo.getOspfMulticast()); 			// OSPF组播
		newVo.setOspfBroadcast(oldVo.getOspfBroadcast()); 			// OSPF广播
		
		newVo.setShortMsg(oldVo.getShortMsg()); 					// 短信业务
		newVo.setData(oldVo.getData()); 							// 数据业务
		newVo.setGroupCallBusiness(oldVo.getGroupCallBusiness()); 	//组呼业务
		newVo.setThreeWay(oldVo.getThreeWay()); 					// 三方通话
		newVo.setCallWaitting(oldVo.getCallWaitting()); 			// 呼叫等待
		newVo.setCallInLimit(oldVo.getCallInLimit()); 				// 呼入限制
		newVo.setCallOutLimit(oldVo.getCallOutLimit()); 			// 呼出限制
		newVo.setSecrecy(oldVo.getSecrecy());						// 保密业务
		newVo.setCallerAllow(oldVo.getCallerAllow()); 				// 来电显示
		newVo.setCallerHide(oldVo.getCallerHide()); 				// 主叫号码隐藏
		newVo.setMonitorSwitch(oldVo.getMonitorSwitch()); 			// 监听开关
		newVo.setMonitorIP(oldVo.getMonitorIP()); 					// 监听IP
		newVo.setMonitorPort(oldVo.getMonitorPort()); 				// 监听端口
		
		newVo.setRegion(oldVo.getRegion());				 			// 分组业务
		newVo.setVoiceMailSwitch(oldVo.getVoiceMailSwitch());		//选择的是动态分配还是静态分配
		if(oldVo.getVoiceMailSwitch()==1){
			newVo.setVoiceMailNum(oldVo.getVoiceMailNum());			//如果选择的是静态分配才需要IP地址
		}
		
		newVo.setPriority(oldVo.getPriority()); 					// 优先级
		newVo.setTstamp(new Timestamp(new Date().getTime())); 		// 最近修改日期

		// 终端用户信息批量添加的信息
		newVo.setTerminalId(oldVo.getTerminalId());
		newVo.setTerminalType(oldVo.getTerminalType());
		newVo.setPowerLevel(oldVo.getPowerLevel());
		newVo.setGwId(oldVo.getGwId());
		newVo.setDepartment(oldVo.getDepartment());
		newVo.setUserType(oldVo.getUserType());
		newVo.setUsername(oldVo.getUsername());
		newVo.setUserInfo(oldVo.getUserInfo());
		newVo.setServicePriority(oldVo.getServicePriority());
		return newVo;
	}

	/**
	 * 批量修改操作
	 */
	@Override
	public void batchHssUpdate(HssBusinessVo vo, String[] imsis) {
		for (String imsi : imsis) {
			try {
				HssBusinessVo batchHss = getById(imsi);
				batchHss = setUpdateHssValue(batchHss, vo);
				update(batchHss,0);
			} catch (CustomException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 导入Excel用户数据
	 */
	@Override
	public void importExcel(InputStream inputStream, int flag, int hidValue) throws CustomException {
		// 查询出所有的Imsi并且删除数据中的数据
		List<String> imsis = selectAllImsi();
		if (flag == 1) {
			deleteEntries(imsis.toArray(new String[imsis.size()]));
		}
		Properties ps = new Properties();
		// 获取流
		try (InputStream dbis = getClass().getResourceAsStream("/omc.properties");) {
			ps.load(dbis);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		if (inputStream != null) {
			HssBusinessVo vo = new HssBusinessVo();
			vo.setMsvocodec(0);
			// 默认开启电话会议
			vo.setGroupCallBusiness(hidValue);
			// HSSF 2007以前的版本 XSSF 2007以后的版本
			try (Workbook wb = new HSSFWorkbook(inputStream);) {
				// 每一页
				Iterator<Sheet> sheetIterator = wb.sheetIterator();
				while (sheetIterator.hasNext()) {
					Iterator<Row> rowIterator = sheetIterator.next().rowIterator();
					while (rowIterator.hasNext()) {
						Row row = rowIterator.next();
						String imsi = row.getCell(0).toString();
						if (row.getCell(0).getCellType() == Cell.CELL_TYPE_NUMERIC) {
							imsi = "" + (long) row.getCell(0).getNumericCellValue();
							vo.setImsi(imsi);
							String mdn = "" + (long) row.getCell(1).getNumericCellValue();
							vo.setMdn(mdn);
							String esn = row.getCell(2).getStringCellValue();
							vo.setEsn(esn);
							Integer DeviceId = hssMetaMapper.getDeviceTypeIdByName(row.getCell(3).toString());
							vo.setDeviceType(DeviceId);
							//// 用户类型赋值
							if (DeviceId == 8) {
								vo.setUserType(1);
							} else {
								vo.setUserType(3);
							}
							////
							String vocName = row.getCell(4).toString();
							if (vocName.equals("PCMU")) {
								vo.setMsvocodec(0);
							} else if (vocName.equals("PCMA")) {
								vo.setMsvocodec(8);
							} else if (vocName.equals("G_729")) {
								vo.setMsvocodec(18);
							}
							vo.setVocodecName(vocName);

							vo.setPriority((int) row.getCell(5).getNumericCellValue());
							vo.setDomain(row.getCell(6).toString());

							String rowCell = row.getCell(7).toString();

							vo.setUePassword(rowCell);

							int voiceMailSwitch = 0;
							if (row.getCell(8).toString().equals("开启")) {
								voiceMailSwitch = 1;
							}
							vo.setVoiceMailSwitch(voiceMailSwitch);

							vo.setVoiceMailNum(row.getCell(9).toString());

							int ospfMulticast = 0;
							if (row.getCell(10).toString().equals("开启") || row.getCell(17).toString() == "") {
								ospfMulticast = 1;
							}
							vo.setOspfMulticast(ospfMulticast);
							int ospfBroadcast = 0;
							if (row.getCell(11).toString().equals("开启") || row.getCell(17).toString() == "") {
								ospfBroadcast = 1;
							}
							vo.setOspfBroadcast(ospfBroadcast);

							if (row.getCell(12).toString() == "") {
								vo.setAmf("F24C");
							} else {
								vo.setAmf(row.getCell(12).toString());
							}
							if (row.getCell(13).toString() == "") {
								vo.setSqn("000000000000");
							} else {
								vo.setSqn(row.getCell(13).toString());
							}

							if (row.getCell(14).toString() == "") {
								vo.setK("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF");
							} else {
								vo.setK(row.getCell(14).toString());
							}
							if (row.getCell(15).toString() == "") {
								vo.setOp("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF");
							} else {
								vo.setOp(row.getCell(15).toString());
							}
							if (row.getCell(16).toString() == "") {
								vo.setOpc("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF");
							} else {
								vo.setOpc(row.getCell(16).toString());
							}

							int data = 0;
							if (row.getCell(17).toString().equals("开启") || row.getCell(17).toString() == "") {
								data = 1;
							}
							vo.setData(data);
							int shortMsg = 0;
							if (row.getCell(18).toString().equals("开启") || row.getCell(18).toString() == "") {
								shortMsg = 1;
							}
							vo.setShortMsg(shortMsg);
							int inter = 0;
							if (row.getCell(19).toString().equals("开启") || row.getCell(19).toString() == "") {
								inter = 1;
							}
							vo.setInternationality(inter);
							int callWaitting = 0;
							if (row.getCell(20).toString().equals("开启") || row.getCell(20).toString() == "") {
								callWaitting = 1;
							}
							vo.setCallWaitting(callWaitting);
							int threeWay = 0;
							if (row.getCell(21).toString().equals("开启") || row.getCell(21).toString() == "") {
								threeWay = 1;
							}
							vo.setThreeWay(threeWay);
							int pairNet = 0;
							if (row.getCell(22).toString().equals("开启") || row.getCell(22).toString() == "") {
								pairNet = 1;
							}
							vo.setPairNet(pairNet);
							int callInLimit = 0;
							if (row.getCell(23).toString().equals("开启")) {
								callInLimit = 1;
							}
							vo.setCallInLimit(callInLimit);
							int callOutLimit = 0;
							if (row.getCell(24).toString().equals("开启")) {
								callOutLimit = 1;
							}
							vo.setCallOutLimit(callOutLimit);

							// 保存操作或者更新操作
							if (flag == 1) {
								vo.setRegion(1);
								save(vo);
							} else {
								if (imsis.contains(imsi)) {
									vo.setRegion(1);
									update(vo,0);
								} else {
									vo.setRegion(1);
									save(vo);
								}
							}
							// 同步CSCF和群组 2018-6-29日志
						}
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
				throw new CustomException(e.getMessage());
			}
		}

	}
	
	/**
	 * 导入XML文件
	 */
	@Override
	public void importXml(InputStream inputStream) throws CustomException {
		if (inputStream != null) {
			HssBusinessVo vo = new HssBusinessVo();
			vo.setMsvocodec(0);
			vo.setDeviceType(0);
			try {
				DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
				DocumentBuilder builder = factory.newDocumentBuilder();
				Document doc = builder.parse(inputStream);
				Element root = doc.getDocumentElement();
				if (!root.getTagName().equals("usim")) {
					throw new CustomException("XML中的格式不正确");
				}
				NodeList nl = root.getElementsByTagName("Tinformation");
				for (int i = 0; i < nl.getLength(); i++) {
					Element element = (Element) nl.item(i);
					String imsi = element.getAttribute("IMSI");
					String MSISDN = element.getAttribute("MSISDN");
					vo.setImsi(imsi);
					vo.setMdn(MSISDN);
					vo.setEsn(MSISDN.substring(MSISDN.length() - 8, MSISDN.length()));
					save(vo);
				}
			} catch (Exception e) {
				e.printStackTrace();
				throw new CustomException(e.getMessage());
			}
		}
	}
	
	/**
	 * 批量添加用户
	 */
	@Override
	public void batchAdd(final HssBusinessVo entry, final Integer batchCount, InputStream is) throws CustomException {
		ExecutorService batchAddPool = new ThreadPoolExecutor(10, 20, 0, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());
		// 默认选中广播和组播
		entry.setOspfMulticast(1);
		entry.setOspfBroadcast(1);
		// 默认开启分组业务(开启数据写入Hlrps表一份)
		entry.setRegion(1);
		//默认遥晕,遥毙正常,对空监听开启
		entry.setSwoon(1);
		entry.setDestroy(1);
		entry.setAirMonitor(1);
		//用户类型
		if (entry.getDeviceType() == 8) {
			entry.setUserType(1);
		} else {
			entry.setUserType(3);
		}
		//开始批量写入时间
		long startTime = System.currentTimeMillis();
		if(entry.getTerminalId()==null || "".equals(entry.getTerminalId())){
			entry.setTerminalId("1");
		}
		if(entry.getDepartment()==null || "".equals(entry.getDepartment())){
			entry.setDepartment("1");
		}
		if(entry.getUserType()==null || "".equals(entry.getUserType())){
			entry.setUserType(1);
		}
		TerminalInfoThread terminalInfoThread = new TerminalInfoThread(terminalInfoMapper, batchCount, entry);
		batchAddPool.execute(terminalInfoThread);
		
		HlrThread hlrThread = new HlrThread(hlrMapper, batchCount, entry);
		batchAddPool.execute(hlrThread);
		
		if (entry.getRegion() == 1) {
			HlrpsThread hlrpsThread = new HlrpsThread(hlrpsMapper, batchCount, entry);
			batchAddPool.execute(hlrpsThread);
		}
		
		AucThread aucThread = new AucThread(aucMapper, batchCount, entry);
		batchAddPool.execute(aucThread);
		
		MsserviceThread msserviceThread = new MsserviceThread(msserviceMapper, batchCount, entry);
		batchAddPool.execute(msserviceThread);
		
		EpcSubscriptionTFTThread epcSubscriptionTFTTread = new EpcSubscriptionTFTThread(epcSubscriptionTFTMapper, batchCount, entry);
		batchAddPool.execute(epcSubscriptionTFTTread);
		
		if((entry.getDeviceType() == 8 || entry.getDeviceType() == 12) && hssMetaMapper.isEnable("ims")){
			UeThread ueThread = new UeThread(ueMapper, batchCount, entry);
			batchAddPool.execute(ueThread);
		}else if (entry.getDeviceType() == 5 || entry.getDeviceType() == 13) {
			EpcSubscriptionDataThread epcSubscriptionDataThread = new EpcSubscriptionDataThread(epcSubscriptionDataMapper, batchCount, entry);
			batchAddPool.execute(epcSubscriptionDataThread);
		}
		batchAddPool.shutdown();
		try {
			batchAddPool.awaitTermination(1, TimeUnit.HOURS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		//结束批量写入时间
		long endTime = System.currentTimeMillis();
		logger.debug("=================用户导入完毕,用时" + (endTime - startTime) + "毫秒=============");
		OamAlarmEndpoint.broadcast("用户导入完毕,用时" + (endTime - startTime) / 1000.0f + "秒");
		//启动线程给群组发送消息
		SendGroupThread sendGroupThread = new SendGroupThread(entry, batchCount);
		executorServicePool.submit(sendGroupThread);
		SendNIThread sendNIThread = new SendNIThread(entry, batchCount);
		executorServicePool.submit(sendNIThread);
	}
	
	class SendGroupThread implements Runnable {
		private HssBusinessVo entry;
		private Integer batchCount;
		public SendGroupThread(){
			
	    }
	    public SendGroupThread(HssBusinessVo entry,Integer batchCount){
	    	this.entry = entry;
	    	this.batchCount = batchCount;
	    }
	    @Override
	    public void run() {
	    	if (entry.getDeviceType() == 1 || entry.getDeviceType() == 8 || entry.getDeviceType() == 12
					|| entry.getDeviceType() == 7 || entry.getDeviceType() == 5) {
				for (int i = 0; i < batchCount; i++) {
					HssBusinessVo vo = new HssBusinessVo();
					if (entry.getDeviceType() == 8) {
						vo.setUserType(1);
					} else {
						vo.setUserType(3);
					}
					vo.setMsvocodec(entry.getMsvocodec());
					vo.setDeviceType(entry.getDeviceType());
					vo.setImsi(Long.parseLong(entry.getImsi()) + i + "");
					vo.setMdn(Long.parseLong(entry.getMdn()) + i + "");
					vo.setDomain(entry.getDomain());
					vo.setUePassword(entry.getUePassword());
					// 发送给群组消息
					sendHssCreate(vo);
					System.err.println("发送群组消息完成");
				}
			}
	    }
	}
	
	class SendNIThread implements Runnable {
		private HssBusinessVo entry;
		private Integer batchCount;
		public SendNIThread(){
			
	    }
	    public SendNIThread(HssBusinessVo entry,Integer batchCount){
	    	this.entry = entry;
	    	this.batchCount = batchCount;
	    }
	    @Override
	    public void run() {
	    	for (int i = 0; i < batchCount; i++) {
				// 发送给1510北向接口
				try {
					send1510North(entry, 1);
				} catch (CustomException e) {
					e.printStackTrace();
				}
			}
	    }
	}
	
	/**
	 * 导出用户数据sql文件
	 */
	@Override
	public InputStream exportHss() throws IOException {
		try (InputStream dbis = getClass().getResourceAsStream("/db.properties");) {
			Properties ps = new Properties();
			ps.load(dbis);
			String user = ps.getProperty("jdbc.username");
			String pwd = ps.getProperty("jdbc.password");
			String hssUrl = ps.getProperty("jdbc.hss.url");
			String grouphost = ps.getProperty("jdbc.groupserver.url");
			if (StringUtils.isEmpty(grouphost)) {
				grouphost = "127.0.0.1";
			} else {
				int startindex = grouphost.indexOf("jdbc:mysql://");
				startindex += "jdbc:mysql://".length();
				if (grouphost.charAt(startindex) == '/') {
					startindex++;
					int endindex = grouphost.lastIndexOf("/");
					grouphost = grouphost.substring(startindex, endindex);
				} else {
					int endindex = grouphost.lastIndexOf(":");
					grouphost = grouphost.substring(startindex, endindex);
				}
			}
			String hssName = hssUrl.substring(hssUrl.lastIndexOf("/") + 1, hssUrl.lastIndexOf("?"));
			if (isFenku()) {
				HlrMapperImpl hlrMapperImpl = (HlrMapperImpl) hlrMapper;
				int count = hlrMapperImpl.getHssDataSourceCount();
				StringBuilder cmd = new StringBuilder();
				cmd.append("mysqldump -u" + user + " -p" + pwd + " --triggers=false --databases CSCF_DB ");
				for (int i = 1; i <= count; i++) {
					cmd.append("hss" + i + " ");
				}
				cmd.append("groupserver");
				InputStream is1 = Runtime.getRuntime().exec(cmd.toString()).getInputStream();
				return is1;
			} else {
				String cmd = "mysqldump --skip-comments -u" + user + " -p" + pwd + " --databases hss CSCF_DB groupserver ";
				cmd = cmd.replace("hss", hssName);
				Process process = Runtime.getRuntime().exec(cmd);
				InputStream is = process.getInputStream();
				return is;
			}
		}
	}

	public static boolean isFenku() {
		return HssServiceImpl.class.getResourceAsStream("/applicationContext-dao-hss.xml") != null;
	}
	
	/**
	 * 导入用户数据sql文件
	 */
	@Override
	public void importHss(InputStream is) throws Exception {
		Properties ps = new Properties();
		InputStream dbis = getClass().getResourceAsStream("/db.properties");
		ps.load(dbis);
		String grouphost = ps.getProperty("jdbc.groupserver.url");
		if (StringUtils.isEmpty(grouphost)) {
			grouphost = "127.0.0.1";
		} else {
			int startindex = grouphost.indexOf("jdbc:mysql://");
			startindex += "jdbc:mysql://".length();
			if (grouphost.charAt(startindex) == '/') {
				startindex++;
				int endindex = grouphost.lastIndexOf("/");
				grouphost = grouphost.substring(startindex, endindex);
			} else {
				int endindex = grouphost.lastIndexOf(":");
				grouphost = grouphost.substring(startindex, endindex);
			}
		}

		try (
			Connection connection = DriverManager.getConnection(ps.getProperty("jdbc.hss.url"),
				ps.getProperty("jdbc.username"), ps.getProperty("jdbc.password"));
			Connection groupconnection = DriverManager.getConnection("jdbc:mysql://" + grouphost + ":3306/mysql",
				ps.getProperty("jdbc.username"), ps.getProperty("jdbc.password"));
			InputStream is8 = getClass().getResourceAsStream("/sql/fenku/hss_trigger.sql");
			Reader reader8 = new InputStreamReader(is8);
			CharArrayWriter triggerWriter = new CharArrayWriter();
			InputStream is4 = getClass().getResourceAsStream("/sql/fenku/hss-fenku.sql");
			Reader reader4 = new InputStreamReader(is4);
			CharArrayWriter writer4 = new CharArrayWriter();
			) {
			String hssUrl = ps.getProperty("jdbc.hss.url");
			String hssName = hssUrl.substring(hssUrl.lastIndexOf("/") + 1, hssUrl.lastIndexOf("?"));
			ScriptRunner runner = new ScriptRunner(connection);
			ScriptRunner grouprunner = new ScriptRunner(groupconnection);
			runner.setLogWriter(null);
			grouprunner.setLogWriter(null);
			grouprunner.runScript(new StringReader("drop database if exists groupserver ;"));
			grouprunner.runScript(new StringReader("create database groupserver char set utf8;"));
			grouprunner.runScript(new StringReader("use groupserver;"));

			ByteArrayOutputStream err = new ByteArrayOutputStream();
			PrintWriter writer = new PrintWriter(err);
			runner.setErrorLogWriter(writer);

			String sql = new String(IOUtils.toByteArray(is), "UTF-8");
			Reader reader = new StringReader(sql);
			runner.runScript(reader);
			if (!ps.getProperty("jdbc.hss.url").equals("jdbc.groupserver.url")) {
				grouprunner.runScript(new StringReader(sql));
			}

			if (isFenku()) {
				IOUtils.copy(reader8, triggerWriter);
				HlrMapperImpl hlrMapperImpl = (HlrMapperImpl) hlrMapper;
				for (int i = 1, len = hlrMapperImpl.getHssDataSourceCount(); i <= len; i++) {
					String create = "use " + hssName + i + ";";
					runner.runScript(new StringReader(create));
					runner.setDelimiter("$");
					runner.runScript(new StringReader(triggerWriter.toString().replace("hss", hssName)));
					runner.setDelimiter(";");
				}
				runner.runScript(new StringReader("drop database if exists " + hssName + ";"));
				IOUtils.copy(reader4, writer4);
				runner.runScript(new StringReader(writer4.toString().replace("hss", hssName)));
			}
			if (StringUtils.isNotEmpty(err.toString())) {
				writer.close();
				err.close();
				throw new CustomException(err.toString());
			}
		}
		dbis.close();
	}

	/**
	 * 导入AUC模版Excel
	 */
	@Override
	public void importAucExcel(InputStream is) throws CustomException {
		try (Workbook wb = new HSSFWorkbook(is);) {
			Iterator<Sheet> sheetIterator = wb.sheetIterator();
			while (sheetIterator.hasNext()) {
				Iterator<Row> rowIterator = sheetIterator.next().rowIterator();
				while (rowIterator.hasNext()) {
					Row row = rowIterator.next();
					String imsi = row.getCell(0).toString();
					if (imsi.equals("IMSI") || StringUtils.isBlank(imsi))
						continue;
					switch (row.getCell(0).getCellType()) {
					case Cell.CELL_TYPE_NUMERIC:
						imsi = "" + (long) row.getCell(0).getNumericCellValue();
						break;
					}

					String k = row.getCell(1).toString();
					String op = row.getCell(2).toString();
					String opc = row.getCell(3).toString();
					String amf = row.getCell(4).toString();
					String sqn = row.getCell(5).toString();
					String rand = row.getCell(6).toString();

					String startStr = row.getCell(7).toString();
					String stopStr = row.getCell(8).toString();
					Time start = Time.valueOf(startStr);
					Time stop = Time.valueOf(stopStr);
					switch (row.getCell(7).getCellType()) {
					case Cell.CELL_TYPE_NUMERIC:
						if (DateUtil.isCellDateFormatted(row.getCell(7))) {
							start = new Time(row.getCell(7).getDateCellValue().getTime());
						} else {
							start = new Time((long) row.getCell(7).getNumericCellValue());
						}
						break;
					}
					switch (row.getCell(8).getCellType()) {
					case Cell.CELL_TYPE_NUMERIC:
						if (DateUtil.isCellDateFormatted(row.getCell(8))) {
							start = new Time(row.getCell(8).getDateCellValue().getTime());
						} else {
							start = new Time((long) row.getCell(8).getNumericCellValue());
						}
						break;
					}
					Auc auc = new Auc(imsi, k, op, opc, amf, sqn, rand, new Timestamp(new Date().getTime()), start,
							stop);
					aucMapper.update(auc);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw new CustomException(e.getMessage());
		}
	}

	/**
	 * 发送给1510的北向接口
	 */
	private void send1510North(HssBusinessVo hss, Integer userOperation) throws CustomException {
		StringBuilder sb = new StringBuilder();
		sb.append("deviceType").append(":").append(hssMetaMapper.getDeviceNameByDeviceTypeId(hss.getDeviceType())).append("\n");
		sb.append("deviceNumber").append(":").append(hss.getMdn()).append("\n");
		sb.append("IMSI").append(":").append(hss.getImsi()).append("\n");
		sb.append("Priority").append(":").append(hss.getPriority()).append("\n");
		sb.append("PermitCallOut").append(":").append(hss.getInternationality()).append("\n");
		
		OmcToNIMsg msg =  sendCncpMsgFactory.createOmcToNICncpMsg(
				CncpProtoType.OMC_HEADER, CncpProtoType.NI_HEADER, CncpProtoType.CNCP_OMC_NI_MSG, 
				0, 0, userOperation, sb.toString());
		cncpTaskExecutor.invokeOmcToNIMsg(msg, true, OmcServerContext.getInstance().getNorthIp(), OmcServerContext.getInstance().getNorthPort());
	}

	/**
	 * 批量添加发送给HSS的消息
	 */
	private void sendHssCreate(HssBusinessVo vo) {
		String data = new ParseUserMsg().hssVo2str(vo);
		SetUpMsg msg = sendCncpMsgFactory.createSendSetCncpMsg(CncpProtoType.OMC_HEADER, CncpProtoType.OAM_HEADER, CncpProtoType.CNCP_SET_MSG, 0, 0, (char)CncpProtoType.OAM_SET_HSS_CREATE, data);
		cncpTaskExecutor.invokeSetUpMsg(msg, false, OmcServerContext.getInstance().getMainGroupIp(), OmcServerContext.getInstance().getMainGroupPort());
	}
	
	/**
	 * 批量修改发送给HSS的消息
	 */
	private void sendHssUpdate(HssBusinessVo vo) {
		String data = new ParseUserMsg().hssVo2str(vo);
		SetUpMsg msg = sendCncpMsgFactory.createSendSetCncpMsg(CncpProtoType.OMC_HEADER, CncpProtoType.OAM_HEADER, CncpProtoType.CNCP_SET_MSG, 0, 0, (char)CncpProtoType.OAM_SET_HSS_UPDATE, data);
		cncpTaskExecutor.invokeSetUpMsg(msg, false, OmcServerContext.getInstance().getMainGroupIp(), OmcServerContext.getInstance().getMainGroupPort());
	}

	/**
	 * 添加向群组发送消息
	 */
	private SetUpResponseMsg sendRemoteHssCreate(int flag, HssBusinessVo vo) {
		String data = new ParseUserMsg().hssVo2str(vo);
		SetUpMsg msg = sendCncpMsgFactory.createSendSetCncpMsg(CncpProtoType.OMC_HEADER, CncpProtoType.OAM_HEADER, CncpProtoType.CNCP_SET_MSG, 0, 0, (char)CncpProtoType.OAM_SET_HSS_CREATE, data);
		if(flag == 1){
			return cncpTaskExecutor.invokeSetUpMsg(msg, false, OmcServerContext.getInstance().getMainGroupIp(), OmcServerContext.getInstance().getMainGroupPort());
		}else{
			return cncpTaskExecutor.invokeSetUpMsg(msg, false, OmcServerContext.getInstance().getBackUpGroupIp(), OmcServerContext.getInstance().getBakcUpGroupPort());
		}
	}

	/**
	 * 修改向群组发送消息
	 */
	private SetUpResponseMsg sendRemoteHssUpdate(int flag, HssBusinessVo vo) {
		String data = new ParseUserMsg().hssVo2str(vo);
		SetUpMsg msg = sendCncpMsgFactory.createSendSetCncpMsg(CncpProtoType.OMC_HEADER, CncpProtoType.OAM_HEADER, CncpProtoType.CNCP_SET_MSG, 0, 0, (char)CncpProtoType.OAM_SET_HSS_UPDATE, data);
		if(flag == 1){
			return cncpTaskExecutor.invokeSetUpMsg(msg, false, OmcServerContext.getInstance().getMainGroupIp(), OmcServerContext.getInstance().getMainGroupPort());
		}else{
			return cncpTaskExecutor.invokeSetUpMsg(msg, false, OmcServerContext.getInstance().getBackUpGroupIp(), OmcServerContext.getInstance().getBakcUpGroupPort());
		}
	}
	
	/**
	 * 删除向群组发送消息
	 */
	private SetUpResponseMsg sendRemoteHssDelete(int flag, String imsi) {
		String data = "UE:" + imsi + "\n";
		SetUpMsg msg = sendCncpMsgFactory.createSendSetCncpMsg(CncpProtoType.OMC_HEADER, CncpProtoType.OAM_HEADER, CncpProtoType.CNCP_SET_MSG, 0, 0, (char)CncpProtoType.OAM_SET_HSS_DELETE, data);
		if(flag == 1){
			return cncpTaskExecutor.invokeSetUpMsg(msg, false, OmcServerContext.getInstance().getMainGroupIp(), OmcServerContext.getInstance().getMainGroupPort());
		}else{
			return cncpTaskExecutor.invokeSetUpMsg(msg, false, OmcServerContext.getInstance().getBackUpGroupIp(), OmcServerContext.getInstance().getBakcUpGroupPort());
		}
	}
	
	/**
	 * 发送给HSS获取信息
	 */
	@Override
	public QueryResponseMsg sendHssGetUe(int flag, String imsi, int getUeMsgType) {
		Module module = RemoteDbUtil.selectRemoteModule(flag, "hss");
		if (module == null) {
			OamAlarmEndpoint.broadcast("网元【hss】还未下发");
			return null;
		}
		Integer moduleId = module.getId();
		Integer instId = null;
		List<Entity> entities = RemoteDbUtil.selectRemoteEntity(flag, moduleId);
		if (entities.size() == 0) {
			OamAlarmEndpoint.broadcast("网元【hss】还未下发");
			return null;
		} else if (entities.size() == 1) {
			instId = entities.get(0).getInstId();
		} else {
			HlrMapperImpl hlrMapperImpl = (HlrMapperImpl) hlrMapper;
			int count = hlrMapperImpl.getHssDataSourceCount();
			instId = Long.parseLong(imsi) % count == 0 ? count : (int) (Long.parseLong(imsi) % count);
		}
		String message = "UE:" + imsi + "\n";
		log.info("实例》》》moduleId=" + moduleId + ",》》》instId=" + instId);
		QueryMsg msg = sendCncpMsgFactory.createSendQueryCncpMsg(
				CncpProtoType.OMC_HEADER, CncpProtoType.OAM_HEADER, CncpProtoType.CNCP_GET_MSG, 
				moduleId, instId, getUeMsgType, message);
		QueryResponseMsg resp = null;
		if (flag == 1) {
			resp = cncpTaskExecutor.invokeQueryMsg(msg, true, OmcServerContext.getInstance().getMainDeviceIp(), OmcServerContext.getInstance().getMainDevicePort());
		} else {
			resp = cncpTaskExecutor.invokeQueryMsg(msg, true, OmcServerContext.getInstance().getBackUpDeviceIp(), OmcServerContext.getInstance().getBackUpDevicePort());
		}
		return resp;
	}

	/**
	 * 修改优先级
	 */
	@Override
	public void updatePriority(String[] imsis, Integer priority) {
		for (String imsi : imsis) {
			Hlr hlr = hlrMapper.selectById(imsi);
			if (hlr == null)
				continue;
			Hlrps hlrps = hlrpsMapper.selectById(imsi);
			Integer msprofile = hlr.getMsprofile();
			HssBusinessVo util = new HssBusinessVo();
			util.setMsprofile(msprofile);
			util.setPriority(priority);
			// util.setGroupCall(1);
			hlr.setMsprofile(util.getMsprofile());
			hlrMapper.update(hlr);
			if (hlrps != null) {
				hlrps.setMsprofile(hlr.getMsprofile());
				hlrpsMapper.update(hlrps);
			}
		}
		if (!"yewu".equals(OmcServerContext.getInstance().getProject())) {
			sendSetUes(imsis, CncpProtoType.OAM_SET_UE_UPDATE);
		}
	}

	@Override
	public void updateUeDestroy(int flag, String imsi) throws CustomException {
		// Module module = moduleMapper.selectByName("hss");
		Module module = RemoteDbUtil.selectRemoteModule(flag, "hss");
		if (module == null) {
			throw new CustomException("网元【hss】还未下发");
		}
		Integer moduleId = module.getId();
		Integer instId = null;
		// List<Entity> entities = entityMapper.selectByModuleId(moduleId);
		List<Entity> entities = RemoteDbUtil.selectRemoteEntity(flag, moduleId);
		if (entities.size() == 0) {
			throw new CustomException("网元【hss】还未下发");
		} else if (entities.size() == 1) {
			instId = entities.get(0).getInstId();
		} else {
			HlrMapperImpl hlrMapperImpl = (HlrMapperImpl) hlrMapper;
			int count = hlrMapperImpl.getHssDataSourceCount();
			instId = Long.parseLong(imsi) % count == 0 ? count : (int) (Long.parseLong(imsi) % count);
		}
		String message = "UE:" + imsi + "\n";
		SetUpMsg msg = sendCncpMsgFactory.createSendSetCncpMsg(
				CncpProtoType.OMC_HEADER, CncpProtoType.OAM_HEADER, CncpProtoType.CNCP_SET_MSG, 
				moduleId, instId, (char)CncpProtoType.OAM_SET_UE_DESTROY, message);
		SetUpResponseMsg resp;
		if(flag == 1){
			resp = cncpTaskExecutor.invokeSetUpMsg(msg, true, OmcServerContext.getInstance().getMainDeviceIp(), OmcServerContext.getInstance().getMainDevicePort());
		}else{
			resp = cncpTaskExecutor.invokeSetUpMsg(msg, true, OmcServerContext.getInstance().getBackUpDeviceIp(), OmcServerContext.getInstance().getBackUpDevicePort());
		}
		if (resp.getResult() == CncpProtoType.SUCCESS) {
			// Hlr hlr = hlrMapper.selectById(imsi);
			// 查询远端HLR
			Hlr hlr = RemoteDbUtil.selectMainOrBackUpHlrById(flag, imsi);
			userOperationObject.setMsprofile_extra(hlr.getMsprofile_extra());
			userOperationObject.decodeMsprofileExtraCtx();
			userOperationObject.setDestroy(4);
			hlr.setMsprofile_extra(userOperationObject.encodeMsProfileExtraCtx(1));
			hlrMapper.update(hlr);

			// Hlrps hlrps = hlrpsMapper.selectById(imsi);
			// 查询远端HLRps
			Hlrps hlrps = RemoteDbUtil.selectMainOrBackUpHlrpsById(flag, imsi);
			if (hlrps != null) {
				userOperationObject.setMsprofile_extra(hlrps.getMsprofile_extra());
				userOperationObject.decodeMsprofileExtraCtx();
				userOperationObject.setDestroy(4);
				userOperationObject.setMsprofile_extra(userOperationObject.encodeMsProfileExtraCtx(1));
				hlrpsMapper.update(hlrps);
			}
		} else {
			throw new CustomException(CncpStatusTransUtil.transLocaleStatusMessage(resp.getResult()));
		}
	}
	
	@Override
	public void updateUeRestore(int flag, String imsi) throws CustomException {
		// Module module = moduleMapper.selectByName("hss");
		Module module = RemoteDbUtil.selectRemoteModule(flag, "hss");
		if (module == null) {
			throw new CustomException("网元【hss】还未下发");
		}
		Integer moduleId = module.getId();
		Integer instId = null;
		// List<Entity> entities = entityMapper.selectByModuleId(moduleId);
		List<Entity> entities = RemoteDbUtil.selectRemoteEntity(flag, moduleId);
		if (entities.size() == 0) {
			throw new CustomException("网元【hss】还未下发");
		} else if (entities.size() == 1) {
			instId = entities.get(0).getInstId();
		} else {
			HlrMapperImpl hlrMapperImpl = (HlrMapperImpl) hlrMapper;
			int count = hlrMapperImpl.getHssDataSourceCount();
			instId = Long.parseLong(imsi) % count == 0 ? count : (int) (Long.parseLong(imsi) % count);
		}
		String message = "UE:" + imsi + "\n";
		SetUpMsg msg = sendCncpMsgFactory.createSendSetCncpMsg(
				CncpProtoType.OMC_HEADER, CncpProtoType.OAM_HEADER, CncpProtoType.CNCP_SET_MSG, 
				moduleId, instId, (char)CncpProtoType.OAM_SET_UE_RESTORE, message);
		SetUpResponseMsg resp;
		if(flag == 1){
			resp = cncpTaskExecutor.invokeSetUpMsg(msg, true, OmcServerContext.getInstance().getMainDeviceIp(), OmcServerContext.getInstance().getMainDevicePort());
		}else{
			resp = cncpTaskExecutor.invokeSetUpMsg(msg, true, OmcServerContext.getInstance().getBackUpDeviceIp(), OmcServerContext.getInstance().getBackUpDevicePort());
		}
		if (resp.getResult() == CncpProtoType.SUCCESS) {
			// Hlr hlr = hlrMapper.selectById(imsi);
			// 查询远端HLR
			Hlr hlr = RemoteDbUtil.selectMainOrBackUpHlrById(flag, imsi);
			userOperationObject.setMsprofile_extra(hlr.getMsprofile_extra());
			userOperationObject.decodeMsprofileExtraCtx();
			userOperationObject.setDestroy(1);
			hlr.setMsprofile_extra(userOperationObject.encodeMsProfileExtraCtx(1));
			hlrMapper.update(hlr);

			// Hlrps hlrps = hlrpsMapper.selectById(imsi);
			// 查询远端HLRps
			Hlrps hlrps = RemoteDbUtil.selectMainOrBackUpHlrpsById(flag, imsi);
			if (hlrps != null) {
				userOperationObject.setMsprofile_extra(hlrps.getMsprofile_extra());
				userOperationObject.decodeMsprofileExtraCtx();
				userOperationObject.setDestroy(1);
				userOperationObject.setMsprofile_extra(userOperationObject.encodeMsProfileExtraCtx(1));
				hlrpsMapper.update(hlrps);
			}
		} else {
			throw new CustomException(CncpStatusTransUtil.transLocaleStatusMessage(resp.getResult()));
		}
	}

	@Override
	public void updateUeSwoon(int flag, String imsi) throws CustomException {
		// Module module = moduleMapper.selectByName("hss");
		Module module = RemoteDbUtil.selectRemoteModule(flag, "hss");
		if (module == null) {
			throw new CustomException("网元【hss】还未下发");
		}
		Integer moduleId = module.getId();
		Integer instId = null;
		// List<Entity> entities = entityMapper.selectByModuleId(moduleId);
		List<Entity> entities = RemoteDbUtil.selectRemoteEntity(flag, moduleId);
		if (entities.size() == 0) {
			throw new CustomException("网元【hss】还未下发");
		} else if (entities.size() == 1) {
			instId = entities.get(0).getInstId();
		} else {
			HlrMapperImpl hlrMapperImpl = (HlrMapperImpl) hlrMapper;
			int count = hlrMapperImpl.getHssDataSourceCount();
			instId = Long.parseLong(imsi) % count == 0 ? count : (int) (Long.parseLong(imsi) % count);
		}
		String message = "UE:" + imsi + "\n";
		SetUpMsg msg = sendCncpMsgFactory.createSendSetCncpMsg(
				CncpProtoType.OMC_HEADER, CncpProtoType.OAM_HEADER, CncpProtoType.CNCP_SET_MSG, 
				moduleId, instId, (char)CncpProtoType.OAM_SET_UE_SWOON, message);
		SetUpResponseMsg resp;
		if(flag == 1){
			resp = cncpTaskExecutor.invokeSetUpMsg(msg, true, OmcServerContext.getInstance().getMainDeviceIp(), OmcServerContext.getInstance().getMainDevicePort());
		}else{
			resp = cncpTaskExecutor.invokeSetUpMsg(msg, true, OmcServerContext.getInstance().getBackUpDeviceIp(), OmcServerContext.getInstance().getBackUpDevicePort());
		}
		if (resp.getResult() == CncpProtoType.SUCCESS) {
			// Hlr hlr = hlrMapper.selectById(imsi);
			// 查询远端HLR
			Hlr hlr = RemoteDbUtil.selectMainOrBackUpHlrById(flag, imsi);
			userOperationObject.setMsprofile_extra(hlr.getMsprofile_extra());
			userOperationObject.decodeMsprofileExtraCtx();
			userOperationObject.setSwoon(4);
			hlr.setMsprofile_extra(userOperationObject.encodeMsProfileExtraCtx(1));
			hlrMapper.update(hlr);
			// Hlrps hlrps = hlrpsMapper.selectById(imsi);
			// 查询远端HLRps
			Hlrps hlrps = RemoteDbUtil.selectMainOrBackUpHlrpsById(flag, imsi);
			if (hlrps != null) {
				userOperationObject.setMsprofile_extra(hlrps.getMsprofile_extra());
				userOperationObject.decodeMsprofileExtraCtx();
				userOperationObject.setSwoon(4);
				userOperationObject.setMsprofile_extra(userOperationObject.encodeMsProfileExtraCtx(1));
				
				hlrpsMapper.update(hlrps);
			}
		} else {
			throw new CustomException(CncpStatusTransUtil.transLocaleStatusMessage(resp.getResult()));
		}
	}

	@Override
	public void updateUeNormal(int flag, String imsi) throws CustomException {
		// Module module = moduleMapper.selectByName("hss");
		Module module = RemoteDbUtil.selectRemoteModule(flag, "hss");
		if (module == null) {
			throw new CustomException("网元【hss】还未下发");
		}
		Integer moduleId = module.getId();
		Integer instId = null;
		// List<Entity> entities = entityMapper.selectByModuleId(moduleId);
		List<Entity> entities = RemoteDbUtil.selectRemoteEntity(flag, moduleId);
		if (entities.size() == 0) {
			throw new CustomException("网元【hss】还未下发");
		} else if (entities.size() == 1) {
			instId = entities.get(0).getInstId();
		} else {
			HlrMapperImpl hlrMapperImpl = (HlrMapperImpl) hlrMapper;
			int count = hlrMapperImpl.getHssDataSourceCount();
			instId = Long.parseLong(imsi) % count == 0 ? count : (int) (Long.parseLong(imsi) % count);
		}
		String message = "UE:" + imsi + "\n";
		SetUpMsg msg = sendCncpMsgFactory.createSendSetCncpMsg(
				CncpProtoType.OMC_HEADER, CncpProtoType.OAM_HEADER, CncpProtoType.CNCP_SET_MSG, 
				moduleId, instId, (char)CncpProtoType.OAM_SET_UE_NORMAL, message);
		SetUpResponseMsg resp;
		if(flag == 1){
			resp = cncpTaskExecutor.invokeSetUpMsg(msg, true, OmcServerContext.getInstance().getMainDeviceIp(), OmcServerContext.getInstance().getMainDevicePort());
		}else{
			resp = cncpTaskExecutor.invokeSetUpMsg(msg, true, OmcServerContext.getInstance().getBackUpDeviceIp(), OmcServerContext.getInstance().getBackUpDevicePort());
		}
		if (resp.getResult() == CncpProtoType.SUCCESS) {
			// Hlr hlr = hlrMapper.selectById(imsi);
			// 查询远端HLR
			Hlr hlr = RemoteDbUtil.selectMainOrBackUpHlrById(flag, imsi);
			userOperationObject.setMsprofile_extra(hlr.getMsprofile_extra());
			userOperationObject.decodeMsprofileExtraCtx();
			userOperationObject.setSwoon(1);
			hlr.setMsprofile_extra(userOperationObject.encodeMsProfileExtraCtx(1));
			hlrMapper.update(hlr);

			// Hlrps hlrps = hlrpsMapper.selectById(imsi);
			// 查询远端HLRps
			Hlrps hlrps = RemoteDbUtil.selectMainOrBackUpHlrpsById(flag, imsi);
			if (hlrps != null) {
				userOperationObject.setMsprofile_extra(hlrps.getMsprofile_extra());
				userOperationObject.decodeMsprofileExtraCtx();
				userOperationObject.setSwoon(1);
				hlrps.setMsprofile_extra(userOperationObject.encodeMsProfileExtraCtx(1));
				hlrpsMapper.update(hlrps);
			}
		} else {
			throw new CustomException(CncpStatusTransUtil.transLocaleStatusMessage(resp.getResult()));
		}
	}

	@Override
	public void updateAirMonitorOpen(int flag, String imsi) throws CustomException {
		// Module module = moduleMapper.selectByName("hss");
		Module module = RemoteDbUtil.selectRemoteModule(flag, "hss");
		if (module == null) {
			throw new CustomException("网元【hss】还未下发");
		}
		Integer moduleId = module.getId();
		Integer instId = null;
		// List<Entity> entities = entityMapper.selectByModuleId(moduleId);
		List<Entity> entities = RemoteDbUtil.selectRemoteEntity(flag, moduleId);
		if (entities.size() == 0) {
			throw new CustomException("网元【hss】还未下发");
		} else if (entities.size() == 1) {
			instId = entities.get(0).getInstId();
		} else {
			HlrMapperImpl hlrMapperImpl = (HlrMapperImpl) hlrMapper;
			int count = hlrMapperImpl.getHssDataSourceCount();
			instId = Long.parseLong(imsi) % count == 0 ? count : (int) (Long.parseLong(imsi) % count);
		}
		String message = "UE:" + imsi + "\n";
		SetUpMsg msg = sendCncpMsgFactory.createSendSetCncpMsg(
				CncpProtoType.OMC_HEADER, CncpProtoType.OAM_HEADER, CncpProtoType.CNCP_SET_MSG, 
				moduleId, instId, (char)CncpProtoType.OAM_SET_UE_NORMAL, message);
		SetUpResponseMsg resp;
		if(flag == 1){
			resp = cncpTaskExecutor.invokeSetUpMsg(msg, true, OmcServerContext.getInstance().getMainDeviceIp(), OmcServerContext.getInstance().getMainDevicePort());
		}else{
			resp = cncpTaskExecutor.invokeSetUpMsg(msg, true, OmcServerContext.getInstance().getBackUpDeviceIp(), OmcServerContext.getInstance().getBackUpDevicePort());
		}
		if (resp.getResult() == CncpProtoType.SUCCESS) {
			// Hlr hlr = hlrMapper.selectById(imsi);
			// 查询远端HLR
			Hlr hlr = RemoteDbUtil.selectMainOrBackUpHlrById(flag, imsi);
			userOperationObject.setMsprofile_extra(hlr.getMsprofile_extra());
			userOperationObject.decodeMsprofileExtraCtx();
			userOperationObject.setAirMonitor(4);
			hlr.setMsprofile_extra(userOperationObject.encodeMsProfileExtraCtx(1));
			hlrMapper.update(hlr);

			// Hlrps hlrps = hlrpsMapper.selectById(imsi);
			// 查询远端HLRps
			Hlrps hlrps = RemoteDbUtil.selectMainOrBackUpHlrpsById(flag, imsi);
			if (hlrps != null) {
				userOperationObject.setMsprofile_extra(hlrps.getMsprofile_extra());
				userOperationObject.decodeMsprofileExtraCtx();
				userOperationObject.setAirMonitor(4);
				hlrps.setMsprofile_extra(userOperationObject.encodeMsProfileExtraCtx(1));
				hlrpsMapper.update(hlrps);
			}
		} else {
			throw new CustomException(CncpStatusTransUtil.transLocaleStatusMessage(resp.getResult()));
		}
	}

	@Override
	public void updateAirMonitorClose(int flag, String imsi) throws CustomException {
		// Module module = moduleMapper.selectByName("hss");
		Module module = RemoteDbUtil.selectRemoteModule(flag, "hss");
		if (module == null) {
			throw new CustomException("网元【hss】还未下发");
		}
		Integer moduleId = module.getId();
		Integer instId = null;
		// List<Entity> entities = entityMapper.selectByModuleId(moduleId);
		List<Entity> entities = RemoteDbUtil.selectRemoteEntity(flag, moduleId);
		if (entities.size() == 0) {
			throw new CustomException("网元【hss】还未下发");
		} else if (entities.size() == 1) {
			instId = entities.get(0).getInstId();
		} else {
			HlrMapperImpl hlrMapperImpl = (HlrMapperImpl) hlrMapper;
			int count = hlrMapperImpl.getHssDataSourceCount();
			instId = Long.parseLong(imsi) % count == 0 ? count : (int) (Long.parseLong(imsi) % count);
		}
		String message = "UE:" + imsi + "\n";
		SetUpMsg msg = sendCncpMsgFactory.createSendSetCncpMsg(
				CncpProtoType.OMC_HEADER, CncpProtoType.OAM_HEADER, CncpProtoType.CNCP_SET_MSG, 
				moduleId, instId, (char)CncpProtoType.OAM_SET_UE_NORMAL, message);
		SetUpResponseMsg resp;
		if(flag == 1){
			resp = cncpTaskExecutor.invokeSetUpMsg(msg, true, OmcServerContext.getInstance().getMainDeviceIp(), OmcServerContext.getInstance().getMainDevicePort());
		}else{
			resp = cncpTaskExecutor.invokeSetUpMsg(msg, true, OmcServerContext.getInstance().getBackUpDeviceIp(), OmcServerContext.getInstance().getBackUpDevicePort());
		}
		if (resp.getResult() == CncpProtoType.SUCCESS) {
			// Hlr hlr = hlrMapper.selectById(imsi);
			// 查询远端HLR
			Hlr hlr = RemoteDbUtil.selectMainOrBackUpHlrById(flag, imsi);
			userOperationObject.setMsprofile_extra(hlr.getMsprofile_extra());
			userOperationObject.decodeMsprofileExtraCtx();
			userOperationObject.setAirMonitor(1);
			hlr.setMsprofile_extra(userOperationObject.encodeMsProfileExtraCtx(1));
			hlrMapper.update(hlr);

			// Hlrps hlrps = hlrpsMapper.selectById(imsi);
			// 查询远端HLRps
			Hlrps hlrps = RemoteDbUtil.selectMainOrBackUpHlrpsById(flag, imsi);
			if (hlrps != null) {
				userOperationObject.setMsprofile_extra(hlrps.getMsprofile_extra());
				userOperationObject.decodeMsprofileExtraCtx();
				userOperationObject.setAirMonitor(1);
				hlrps.setMsprofile_extra(userOperationObject.encodeMsProfileExtraCtx(1));
				
				hlrpsMapper.update(hlrps);
			}
		} else {
			throw new CustomException(CncpStatusTransUtil.transLocaleStatusMessage(resp.getResult()));
		}
	}
	
	// 查询所有的Imsi
	@Override
	public List<String> selectAllImsi() {
		List<String> allImsi = hlrMapper.selectAllImsi();
		return allImsi;
	}
	
	/**
	 * 获取终端信息
	 */
	private TerminalInfo convertToTerminalInfoEntity(HssBusinessVo vo) {
		Timestamp createTime = new Timestamp(new Date().getTime());
		TerminalInfo terminal = new TerminalInfo();
		terminal.setCreateTime(createTime);
		terminal.setImsi(vo.getImsi());
		terminal.setDepartment(vo.getDepartment());
		terminal.setGwId(vo.getGwId());
		terminal.setPowerLevel(vo.getPowerLevel());
		terminal.setSuportBuss(vo.getMsprofile());// 暂时同msprofile
		terminal.setTerminalId(vo.getTerminalId());
		terminal.setTerminalType(vo.getTerminalType());
		terminal.setUserInfo(vo.getUserInfo());
		terminal.setUsername(vo.getUsername());
		terminal.setUserType(vo.getUserType());
		terminal.setServicePriority(vo.getServicePriority());
		return terminal;
	}

	/**
	 * 获取组信息
	 */
	private GroupInfo convertToGroupInfoEntity(HssBusinessVo entry) {
		GroupInfo groupInfo = new GroupInfo();
		groupInfo.setImsi(entry.getImsi());
		String[] groups = entry.getGroups();
		int len = groups.length;
		if (groups == null || len == 0)
			return null;
		if (len >= 1)
			groupInfo.setGroup1(groups[0]);
		if (len >= 2)
			groupInfo.setGroup2(groups[1]);
		if (len >= 3)
			groupInfo.setGroup3(groups[2]);
		if (len >= 4)
			groupInfo.setGroup4(groups[3]);
		if (len >= 5)
			groupInfo.setGroup5(groups[4]);
		if (len >= 6)
			groupInfo.setGroup6(groups[5]);
		if (len >= 7)
			groupInfo.setGroup7(groups[6]);
		if (len >= 8)
			groupInfo.setGroup8(groups[7]);
		if (len >= 9)
			groupInfo.setGroup9(groups[8]);
		if (len >= 10)
			groupInfo.setGroup10(groups[9]);
		if (len >= 11)
			groupInfo.setGroup11(groups[10]);
		if (len >= 12)
			groupInfo.setGroup12(groups[11]);
		if (len >= 13)
			groupInfo.setGroup13(groups[12]);
		if (len >= 14)
			groupInfo.setGroup14(groups[13]);
		if (len >= 15)
			groupInfo.setGroup15(groups[14]);
		if (len >= 16)
			groupInfo.setGroup16(groups[15]);
		return groupInfo;
	}

	/**
	 * 获取Hlr信息
	 */
	private Hlr convertToHlrEntity(HssBusinessVo vo) {
		if (vo == null){
			throw new IllegalArgumentException("vo不能为空");
		}
		Hlr hlr = new Hlr();
		hlr.setImsi(vo.getImsi());
		hlr.setMdn(vo.getMdn());
		hlr.setEsn(vo.getEsn());
		hlr.setMsprofile(vo.getMsprofile());
		hlr.setMsprofile_extra(vo.getMsprofile_extra());
		hlr.setMsvocodec(vo.getMsvocodec());
		hlr.setTstamp(vo.getTstamp());
		hlr.setDeviceType(vo.getDeviceType());
		return hlr;
	}

	/**
	 * 获取鉴权信息
	 */
	private Auc convertToAucEntity(HssBusinessVo vo) {
		if (vo == null){
			throw new IllegalArgumentException("vo不能为空");
		}
		StringBuilder k = new StringBuilder(), 
				opc = new StringBuilder(), 
				sqn = new StringBuilder(),
				amf = new StringBuilder(), 
				op = new StringBuilder();
		for (int i = 1; i < 17; i++) {
			if (i < 3) {
				String _amf = (String) ReflectionUtil.getFieldValue(vo, "amf" + i);
				amf.append(_amf);
				amf.append(" ");
			}
			if (i < 7) {
				String _sqn = (String) ReflectionUtil.getFieldValue(vo, "sqn" + i);
				sqn.append(_sqn);
				sqn.append(" ");
			}
			String _k = (String) ReflectionUtil.getFieldValue(vo, "k" + i);
			k.append(_k);
			k.append(" ");
			String _opc = (String) ReflectionUtil.getFieldValue(vo, "opc" + i);
			opc.append(_opc);
			opc.append(" ");
			String _op = (String) ReflectionUtil.getFieldValue(vo, "op" + i);
			op.append(_op);
			op.append(" ");
		}
		Auc auc = new Auc();
		auc.setImsi(vo.getImsi());
		auc.setK(k.toString());
		auc.setOp(op.toString());
		auc.setOpc(opc.toString());
		auc.setAmf(amf.toString());
		auc.setSqn(sqn.toString());
		auc.setRand(vo.getRand());
		auc.setTstamp(vo.getTstamp());
		auc.setStart(vo.getStart());
		auc.setStop(vo.getStop());
		return auc;
	}

	/**
	 * 获取Msservice信息 (包括补充业务,分组业务中静态分配的IP地址)
	 */
	private Msservice convertToMsserviceEntity(HssBusinessVo vo) {
		if (vo == null){
			throw new IllegalArgumentException("vo不能为空");
		}
		Msservice ms = new Msservice();
		ms.setImsi(vo.getImsi());
		ms.setDirectFwdNum(vo.getDirectFwdNum());
		ms.setFwdOnBusyNum(vo.getFwdOnBusyNum());
		ms.setFwdNoAnswerNum(vo.getFwdNoAnswerNum());
		ms.setFwdNANum(vo.getFwdNANum());
		ms.setVoiceMailNum(vo.getVoiceMailNum());	//静态分配的IP地址
		ms.setWireTapAddr(vo.getWireTapAddr());
		return ms;
	}

	/**
	 * 获取Hlrps信息
	 */
	private Hlrps convertToHlrpsEntity(HssBusinessVo vo) {
		if (vo == null)
			throw new IllegalArgumentException("vo不能为空");
		Hlrps hlrps = new Hlrps();
		hlrps.setImsi(vo.getImsi());
		hlrps.setMdn(vo.getMdn());
		hlrps.setEsn(vo.getEsn());
		hlrps.setMsprofile(vo.getMsprofile());
		hlrps.setMsprofile_extra(vo.getMsprofile_extra());
		hlrps.setMsvocodec(vo.getMsvocodec());
		hlrps.setTstamp(vo.getTstamp());
		hlrps.setDeviceType(vo.getDeviceType());
		return hlrps;
	}

}
