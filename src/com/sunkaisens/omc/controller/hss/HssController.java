package com.sunkaisens.omc.controller.hss;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.sunkaisens.omc.exception.CustomException;
import com.sunkaisens.omc.mapper.core.HssMetaMapper;
import com.sunkaisens.omc.service.core.CardService;
import com.sunkaisens.omc.service.core.LanguageBundle;
import com.sunkaisens.omc.service.hss.GroupMemberService;
import com.sunkaisens.omc.service.hss.HssGroupService;
import com.sunkaisens.omc.service.hss.HssService;
import com.sunkaisens.omc.thread.cncpMsg.CncpProtoType;
import com.sunkaisens.omc.util.ServletUtil;
import com.sunkaisens.omc.util.hss.RemoteDbUtil;
import com.sunkaisens.omc.vo.core.PageBean;
import com.sunkaisens.omc.vo.hss.HssBusinessVo;

import net.sf.json.JSONObject;

/**
 *HSS Controller
 */
@Controller
@RequestMapping("/hss")
public class HssController {
	
	@Resource
	private LanguageBundle languageBundle;
	@Resource
	private HssService service;
	@Resource 
	private HssGroupService hssGroupService;
	@Resource
	private GroupMemberService groupMemberService;
	@Resource
	private HssMetaMapper hssMetaMapper;
	@Resource
	private CardService cardService;
	
	protected  Logger logger = LoggerFactory.getLogger(getClass());
	 
	/**
	 * 获取设备类型信息
	 */
	@ModelAttribute("deviceTypes")
	public Map<String,String> getDeviceTypes() {
		Map<String,String> device=new HashMap<String,String>();
		List<Map<String, ?>> list=hssMetaMapper.getEnableDeviceType();
		for(Map<String,?> map:list){
			device.put(map.get("id").toString(), map.get("name").toString());
		}
		return device;
	}
	/**
	 * 获取设备编码类型
	 */
	@ModelAttribute("msvocodecs")
	public Map<String,String> getMsvocodecs() {
		Map<String,String> voice=new HashMap<String,String>();
		List<Map<String, ?>> list=hssMetaMapper.getEnableVoiceType();
		for(Map<String,?> map:list){
			if("G_729".equals(map.get("name").toString())){
				voice.put(map.get("id").toString(),"G.729");
			}else{
			voice.put(map.get("id").toString(), map.get("name").toString());
			}
		}
		return voice;
	}
	
	/**
	 * 查询hss用户列表
	 */
	@RequestMapping("list")
	public @ResponseBody PageBean list(@RequestParam(defaultValue="1") Integer page,
			@RequestParam(value="rows",defaultValue="50") Integer pageSize,HssBusinessVo hss){
//		Integer status = CncpStatusPacketHandler.statusMap.get("deviceStatus");
//		System.err.println(">>>>>>>获取当前设备>>>>>>>:"+status);
		PageBean pageBean=service.getPageBean(page,pageSize,hss);
		return pageBean;
	}
	
	/**
	 * 返回前台list视图
	 */
	@RequestMapping("listUI")
	public ModelAndView listUI(Boolean isSave){
		boolean ims=hssMetaMapper.isEnable("ims");
		ModelAndView mav=new ModelAndView("hss/list");
		//开启的基本业务
		List<Map<String, ?>> basicBusinesses=hssMetaMapper.getEnableBasicBusiness();
		//关闭的基本业务
		List<Map<String, ?>> nonBasicBusinesses=hssMetaMapper.getNonBasicBusiness();
		//表格显示的列
		List<Map<String,?>> viewItems = hssMetaMapper.getEnableViewItem();
		List<Map<String, ?>> userPriority=hssMetaMapper.getConfigUserPriority();//配置用户优先级
		List<Map<String, ?>> terminalTypes=hssMetaMapper.getTerminalType();
		for(Map<String,?> map:nonBasicBusinesses){
			mav.addObject(map.get("name").toString(), map.get("enable"));
		}
		for(Map<String,?> map:viewItems){
			mav.addObject(map.get("name").toString(), map.get("enable"));
		}
		mav.addObject("basicBusinesses", basicBusinesses);
		mav.addObject("terminalTypes", terminalTypes);
		mav.addObject("pageBean", new PageBean());
		mav.addObject("ims", ims);
		mav.addObject("userPriority", userPriority);
		if(isSave!=null&&isSave)
			mav.addObject("isSave",true);
		return mav;
	}
	
	
	@RequestMapping("reduancylist")
	public @ResponseBody PageBean reduancyList(@RequestParam(defaultValue="1") Integer page,
			@RequestParam(value="rows",defaultValue="50") Integer pageSize,HssBusinessVo hss){
		PageBean pageBean=service.getRePageBean(page, pageSize, hss);
		return pageBean;
	}
    @RequestMapping("reduancyListUI")
    public ModelAndView reduancyListUI(){
    	ModelAndView mav = new ModelAndView("hss/reduancylist");
    	List<Map<String,?>> viewItems = hssMetaMapper.getEnableViewItem();
    	for(Map<String,?> map:viewItems){
			mav.addObject(map.get("name").toString(), map.get("enable"));
		}
    	mav.addObject("pageBean", new PageBean());
    	return mav;
    }
    
	/**
	 * 返回前台add视图
	 */
	@RequestMapping("addUI")
	public ModelAndView addUI(){
		int imsFlag = 0;
		if(hssMetaMapper.isEnable("ims")){
			imsFlag = 1;
		}
		//开启的基本业务
		List<Map<String, ?>> basicBusinesses=hssMetaMapper.getEnableBasicBusiness();
		//关闭的基本业务
		List<Map<String, ?>> nonBasicBusinesses=hssMetaMapper.getNonBasicBusiness();
		//终端类型
		List<Map<String, ?>> terminalTypes=hssMetaMapper.getTerminalType();
		//用户优先级
		List<Map<String, ?>> userPriority=hssMetaMapper.getConfigUserPriority();//配置用户优先级
		ModelAndView mav=new ModelAndView("hss/add");
		mav.addObject("basicBusinesses", basicBusinesses);
		mav.addObject("terminalTypes", terminalTypes);
		mav.addObject("userPriority", userPriority);
		for(Map<String,?> map:nonBasicBusinesses){
			mav.addObject(map.get("name").toString(), map.get("enable"));
		}
		mav.addObject("imsFlag", imsFlag);
		return mav;
	}
	
	/**
	 * hss 用户数据添加
	 */
	@RequestMapping(value="add",produces="text/html;charset=UTF-8")
	public @ResponseBody String add(HssBusinessVo vo) throws CustomException{
		String esn = vo.getEsn();
		if(esn==null){
			esn="";
		}
		service.save(vo);
		JSONObject json=new JSONObject();
		json.element("msg", "添加完毕");
		return json.toString();
		
	}
	
	/**
	 * 返回前台update视图
	 */
	@RequestMapping("updateUI")
	public ModelAndView updateUI(String imsi) throws CustomException{
		logger.info("前台传递的参数IMSI:"+imsi);
		HssBusinessVo hss=service.getById(imsi);
		//监听业务的监听端口
		if(hss.getMonitorPort()==null || "".equals(hss.getMonitorPort())){
			hss.setMonitorPort("9654");
		}
		//给提示功率等级的解释赋值
		String powerLevelTextStr = judgePowerLevel(hss.getPowerLevel());
		hss.setPowerLevelText(powerLevelTextStr);
		List<Map<String, ?>> basicBusinesses=hssMetaMapper.getEnableBasicBusiness();
		List<Map<String, ?>> nonBasicBusinesses=hssMetaMapper.getNonBasicBusiness();
		List<Map<String, ?>> terminalTypes=hssMetaMapper.getTerminalType();
		List<Map<String, ?>> userPriority=hssMetaMapper.getConfigUserPriority();//配置用户优先级
		ModelAndView mav=new ModelAndView("hss/update");
		mav.addObject("terminalTypes", terminalTypes);
		hss.setDeviceName(HssBusinessVo.DEVICE_MAP.get(hss.getDeviceType()));
		mav.addObject("hss", hss);
		mav.addObject("basicBusinesses", basicBusinesses);
		mav.addObject("userPriority", userPriority);
		for(Map<String,?> map:nonBasicBusinesses){
			mav.addObject(map.get("name").toString(), map.get("enable"));
		}
		return mav;
	}
	
	/**
	 * hss数据更新操作
	 */
	@RequestMapping(value="update",produces="text/html;charset=UTF-8")
	public @ResponseBody String update(HssBusinessVo vo) throws CustomException{
		// 0 表示需要给1510北向接口发送消息  4表示不需要给1510北向接口发送消息
		service.update(vo,0);
		JSONObject json=new JSONObject();
		json.element("msg", "修改完毕");
		return json.toString();
	}
	
	/**
	 * hss用户数据删除操作
	 */
	@RequestMapping(value="delete",produces="text/html;charset=UTF-8")
	public @ResponseBody String delete(String[] imsis) throws CustomException{
		service.deleteEntries(imsis);
		JSONObject json=new JSONObject();
		json.element("msg", "删除完毕");
		return json.toString();
	}
	
	/**
	 * 判断Mdn是否存在
	 */
	@RequestMapping(value="existMdn",produces="text/html;charset=UTF-8")
	public @ResponseBody String existMdn(String mdn)throws CustomException{
		Boolean exist=service.existMdn(mdn);
		return exist.toString();
	}
	
	/**
     * 批量添加转到页面
     */
	@RequestMapping(value="batchAddUI")
	public ModelAndView batchAddUI()throws Exception{
		int flag = 0;
		if(hssMetaMapper.isEnable("ims")){
			flag = 1;
		}
		//boolean ims=hssMetaMapper.isEnable("ims");
		ModelAndView mav=new ModelAndView("hss/batchAdd");
		List<Map<String, ?>> basicBusinesses=hssMetaMapper.getEnableBasicBusiness();
		List<Map<String, ?>> nonBasicBusinesses=hssMetaMapper.getNonBasicBusiness();
		List<Map<String,?>> viewItems = hssMetaMapper.getEnableViewItem();
		List<Map<String, ?>> userPriority=hssMetaMapper.getConfigUserPriority();//配置用户优先级
		List<Map<String, ?>> terminalTypes=hssMetaMapper.getTerminalType();
		mav.addObject("basicBusinesses", basicBusinesses);
		mav.addObject("terminalTypes", terminalTypes);
		for(Map<String,?> map:nonBasicBusinesses){
			mav.addObject(map.get("name").toString(), map.get("enable"));
		}
		for(Map<String,?> map:viewItems){
			mav.addObject(map.get("name").toString(), map.get("enable"));
		}
		mav.addObject("basicBusinesses", basicBusinesses);
		mav.addObject("flag", flag);
		mav.addObject("userPriority", userPriority);
		return mav;
	}

    /**
     * 批量添加操作
     */
	@RequestMapping(value="batchAdd",produces="text/html;charset=UTF-8")
	public @ResponseBody String batchAdd(HssBusinessVo vo,Integer batchCount,MultipartFile aucExcel)throws Exception{
		service.batchAdd(vo,batchCount,aucExcel.isEmpty()?null:aucExcel.getInputStream());
		JSONObject json=new JSONObject();
		json.element("msg", "插入数据完毕...");
		return json.toString();
	}
	
	/**
	 * 批量修改操作
	 */
	@RequestMapping(value="batchHssUpdate",produces="text/html;charset=UTF-8")
	public @ResponseBody String batchHssUpdate(HssBusinessVo vo,String[] imsis)throws Exception{
		service.batchHssUpdate(vo,imsis);
		JSONObject json=new JSONObject();
		json.element("msg", "批量修改完毕");
		return json.toString();
	}
	
	/**
	 * hss数据导出操作
	 */
	@RequestMapping(value="export")
	public void exportHss(HttpServletResponse response)throws Exception{
		InputStream is=service.exportHss();
		ServletUtil.setFileDownloadHeader(response, "用户数据(Hss_GroupServer).sql");
		IOUtils.copy(is, response.getOutputStream());
		is.close();
	}
	
	/**
	 * 数据导入操作
	 */
	@RequestMapping(value="import",produces="text/html;charset=UTF-8")
	public @ResponseBody String importHss(MultipartFile file)throws Exception{
		if(!file.getOriginalFilename().toLowerCase().endsWith(".sql")){
			throw new CustomException("扩展名不是.sql");
		}
		service.importHss(file.getInputStream());
		JSONObject json=new JSONObject();
		json.element("msg", "导入完毕");
		return json.toString();
	}
	
	/**
	 * 导入Excel表格数据
	 */
	@RequestMapping(value="importExcel",produces="text/html;charset=UTF-8")
	public @ResponseBody String importExcel(MultipartFile file,HttpServletRequest request)throws Exception{
		if(file.getOriginalFilename().toLowerCase().endsWith(".xlsx")){
			throw new CustomException("目前仅支持97-03版Excel");
		}else if(!file.getOriginalFilename().toLowerCase().endsWith(".xls")){
			throw new CustomException("扩展名不是.xls");
		}
		//能否获取标识flag
		String str = request.getParameter("hid");
		String hidCall = request.getParameter("hidCall");
		int flag = Integer.valueOf(str);
		int hidValue = Integer.valueOf(hidCall);
		service.importExcel(file.getInputStream(),flag,hidValue);
		JSONObject json=new JSONObject();
		json.element("msg", "导入完毕");
		return json.toString();
	}
	
	/**
	 * 导入Xml表格数据
	 */
	@RequestMapping(value="importXml",produces="text/html;charset=UTF-8")
	public @ResponseBody String importXml(MultipartFile file)throws Exception{
		if(!file.getOriginalFilename().toLowerCase().endsWith(".xml")){
			throw new CustomException("扩展名不是.xml");
		}
		service.importXml(file.getInputStream());
		JSONObject json=new JSONObject();
		json.element("msg", "导入完毕");
		return json.toString();
	}
	
	/**
	 * 返回前台listMdn视图
	 */
	@RequestMapping(value="listMdnUI")
	public ModelAndView listMdnUI()throws Exception{
		ModelAndView mav=new ModelAndView("hss/listMdn");
		mav.addObject("pageBean", new PageBean());
		return mav;
	}
	
	
	@RequestMapping("listMdnNum")
	public @ResponseBody PageBean listMdnNum(@RequestParam(defaultValue="1") Integer page,
			@RequestParam(value="rows",defaultValue="50") Integer pageSize,HssBusinessVo hss){
		System.err.println("result:"+hss.getPriority());
		PageBean pageBean=service.getPageBeanLine(page,pageSize,hss);
		return pageBean;
	}
	
	/**
	 * 用户权限调整
	 */
	@RequestMapping(value="updatePriority",produces="text/html;charset=UTF-8")
	public @ResponseBody String updatePriority(String[] imsis,Integer priority){
		service.updatePriority(imsis,priority);
		JSONObject json=new JSONObject();
		json.element("msg", "调整完毕");
		return json.toString();
	}
	
	/**
	 * 获取用户当前状态
	 */
	@RequestMapping("readStatus")
	public @ResponseBody String readStatus(String imsi,HttpServletRequest request,HttpSession session)throws CustomException{
		ResourceBundle bundle = languageBundle.getBundle(request, session);
		//主备标志位
		int flag = 1;
		Integer deviceStatus = RemoteDbUtil.selectDeviceStatus();
		if(deviceStatus!=null){
			flag = deviceStatus;
		}
		JSONObject json = service.readStatus(flag,imsi,bundle);
		return json.toString();
	}
	
	/**
	 * 同步用户状态
	 */
	@RequestMapping("syncStatus")
	public @ResponseBody String syncStatus(String imsi)throws CustomException{
		//主备标志位
		int flag = 1;
		Integer deviceStatus = RemoteDbUtil.selectDeviceStatus();
		if(deviceStatus!=null){
			flag = deviceStatus;
		}
		service.sendSync(flag,imsi);
		JSONObject json=new JSONObject();
		json.element("msg", "同步完毕");
		return json.toString();
	}
	
	/**
	 * 摇毙操作
	 */
	@RequestMapping("ueDestroy")
	public @ResponseBody String ueDestroy(String imsi)throws CustomException{
		//主备标志位
		int flag = 1;
		Integer deviceStatus = RemoteDbUtil.selectDeviceStatus();
		if(deviceStatus!=null){
			flag = deviceStatus;
		}
		service.updateUeDestroy(flag,imsi);
		service.sendRemoteSetUe(flag,imsi,CncpProtoType.OAM_SET_UE_DESTROY);
		JSONObject json=new JSONObject();
		json.element("msg", "摇毙成功");
		return json.toString();
	}	
	
	/**
	 * 摇毙恢复操作
	 */
	@RequestMapping("ueRestore")
	public @ResponseBody String ueRestore(String imsi)throws CustomException{
		//主备标志位
		int flag = 1;
		Integer deviceStatus = RemoteDbUtil.selectDeviceStatus();
		if(deviceStatus!=null){
			flag = deviceStatus;
		}
		service.updateUeRestore(flag,imsi);
		service.sendRemoteSetUe(flag,imsi,CncpProtoType.OAM_SET_UE_RESTORE);
		JSONObject json=new JSONObject();
		json.element("msg", "恢复完毕");
		return json.toString();
	}
	
	/**
	 * 摇晕操作
	 */
	@RequestMapping("ueSwoon")
	public @ResponseBody String ueSwoon(String imsi)throws CustomException{
		//主备标志位
		int flag = 1;
		Integer deviceStatus = RemoteDbUtil.selectDeviceStatus();
		if(deviceStatus!=null){
			flag = deviceStatus;
		}
		service.updateUeSwoon(flag,imsi);
		service.sendRemoteSetUe(flag,imsi,CncpProtoType.OAM_SET_UE_SWOON);
		JSONObject json=new JSONObject();
		json.element("msg", "摇晕成功");
		return json.toString();
	}
	
	/**
	 * 摇晕恢复操作
	 */
	@RequestMapping("ueNormal")
	public @ResponseBody String ueNormal(String imsi)throws CustomException{
		//主备标志位
		int flag = 1;
		Integer deviceStatus = RemoteDbUtil.selectDeviceStatus();
		if(deviceStatus!=null){
			flag = deviceStatus;
		}
		service.updateUeNormal(flag,imsi);
		service.sendRemoteSetUe(flag,imsi,CncpProtoType.OAM_SET_UE_NORMAL);
		JSONObject json=new JSONObject();
		json.element("msg", "恢复完毕");
		return json.toString();
	}
	
	/**
	 * 开启对空监听
	 */
	@RequestMapping("airMonitorOpen")
	public @ResponseBody String airMonitorOpen(String imsi)throws CustomException{
		//主备标志位
		int flag = 1;
		Integer deviceStatus = RemoteDbUtil.selectDeviceStatus();
		if(deviceStatus!=null){
			flag = deviceStatus;
		}
		service.updateAirMonitorOpen(flag,imsi);
		service.sendRemoteSetUe(flag,imsi,CncpProtoType.OAM_SET_UE_NORMAL);
		JSONObject json=new JSONObject();
		json.element("msg", "开启对空监听完毕");
		return json.toString();
	}
	
	/**
	 * 关闭对空监听
	 */
	@RequestMapping("airMonitorClose")
	public @ResponseBody String airMonitorClose(String imsi)throws CustomException{
		//主备标志位
		int flag = 1;
		Integer deviceStatus = RemoteDbUtil.selectDeviceStatus();
		if(deviceStatus!=null){
			flag = deviceStatus;
		}
		service.updateAirMonitorClose(flag,imsi);
		service.sendRemoteSetUe(flag,imsi,CncpProtoType.OAM_SET_UE_NORMAL);
		JSONObject json=new JSONObject();
		json.element("msg", "关闭对空监听完毕");
		return json.toString();
	}
	
	private String judgePowerLevel(int powerLevel){
		if(powerLevel==1){
			return "终端EIRP最小5dbW，最大8dbW";
		}else if(powerLevel==2){
			return "终端EIRP最小5dbW，最大8dbW";
		}else if(powerLevel==3){
			return "保留";
		}else if(powerLevel==4){
			return "终端EIRP最小20dbW，最大25dbW";
		}else if(powerLevel==5){
			return "保留";
		}else if(powerLevel==6){
			return "终端EIRP最小20dbW，最大25dbW";
		}else if(powerLevel==7){
			return "保留";
		}else{
			return "";
		}
	}
	
}
