package com.sunkaisens.omc.controller.core;

import java.io.File;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ModelAndView;

import com.sunkaisens.omc.exception.CustomException;
import com.sunkaisens.omc.po.core.Item;
import com.sunkaisens.omc.service.core.ItemService;
import com.sunkaisens.omc.service.core.SbcService;
import com.sunkaisens.omc.vo.core.PageBean;
/**
 * 
 * 
 * 
 * 
 * 
 * Sbc Controller
 *
 */
@Controller@RequestMapping(value="/sbc")
public class SbcController {
	//获取log对象
	protected final Logger log = LoggerFactory.getLogger(getClass());
	//注入SbcService
	@Resource
	private SbcService service;
	//注入ItemService
	@Resource
	private ItemService itemService;
	
	@ModelAttribute("pageBean")
	public PageBean getPageBean() {
		return new PageBean();
	}
	
	//-------------网络配置 开始----------------
	/**
	 * 
	 * 
	 * 返回前台sbc/sbc视图
	 * @return
	 */
	@RequestMapping(value="/netUI")
	public ModelAndView netUI(){
		List<Item> items=service.getNetItem();
		ModelAndView mav=new ModelAndView("sbc/sbc");
		mav.addObject("items", items);
		mav.addObject("packid", "Net");
		return mav;
	}
	
	@RequestMapping(value="/loadNetScalar")
	public @ResponseBody String loadNetScalar(String[] names) throws CustomException{
		JSONObject json=service.loadNetScalar(names);
		return json.toString();
	}
	/**
	 * 
	 * 
	 * 
	 * 更新NetScalar操作
	 * @param request
	 * @return
	 * @throws CustomException
	 */
	@RequestMapping(value="/updateNetScalar",produces="text/html;charset=UTF-8")
	public @ResponseBody String updateNetScalar(HttpServletRequest request) throws CustomException{
		service.updateNetScalar(generate(request));
		JSONObject json=new JSONObject();
		json.element("msg", "保存完毕");
		return json.toString();
	}
	//-------------网络配置 结束----------------
	//-------------TLS配置 开始----------------
	/**
	 * 
	 * 
	 * 
	 * 返回前台sbc/tls视图
	 * @return
	 */
	@RequestMapping(value="/tlsUI")
	public ModelAndView tlsUI(){
		List<Item> items=service.getTlsItem();
		ModelAndView mav=new ModelAndView("sbc/tls");
		mav.addObject("items", items);
		mav.addObject("packid", "Tls");
		return mav;
	}
	/**
	 * 
	 * 
	 * 加载TlsScalar
	 * @param names
	 * @return
	 * @throws CustomException
	 */
	@RequestMapping(value="/loadTlsScalar")
	public @ResponseBody String loadTlsScalar(String[] names) throws CustomException{
		JSONObject json=service.loadTlsScalar(names);
		return json.toString();
	}
	
	private Map<String,String> generateFile(HttpServletRequest request) throws Exception{
		CommonsMultipartResolver resolver=new CommonsMultipartResolver(request.getServletContext());
		Map<String,String> map=new HashMap<String,String>();
		if(resolver.isMultipart(request)){
			String path=request.getServletContext().getRealPath("/WEB-INF/sbc");
			MultipartHttpServletRequest req=(MultipartHttpServletRequest)request;
			Iterator<String> files=req.getFileNames();
			while(files.hasNext()){
				String field=files.next();
				MultipartFile file=req.getFile(field);
				if(file==null){
					map.put(field,request.getParameter(field));
				}else{
					File target=new File(path,field);
					if(!target.getParentFile().exists()) target.getParentFile().mkdirs();
					if(!target.exists()) target.createNewFile();
					file.transferTo(target);
					//map.put(field,target.toString());
					map.put(field,target.getName());
				}
			}
			Enumeration<String> fields=req.getParameterNames();
			while(fields.hasMoreElements()){
				String field=fields.nextElement();
				map.put(field,request.getParameter(field));
			}
		}
		return map;
	}
	/**
	 * 
	 * 
	 * 
	 * 更新TlsScalar操作
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/updateTlsScalar",produces="text/html;charset=UTF-8")
	public @ResponseBody String updateTlsScalar(HttpServletRequest request) throws Exception{
		service.updateTlsScalar(generateFile(request));
		JSONObject json=new JSONObject();
		json.element("msg", "保存完毕");
		return json.toString();
	}
	//-------------TLS配置 结束----------------
	//-------------性能参数配置 开始----------------
	@RequestMapping(value="/performanceUI")
	public ModelAndView performanceUI(){
		List<Item> items=service.getPerformanceItem();
		ModelAndView mav=new ModelAndView("sbc/sbc");
		mav.addObject("items", items);
		mav.addObject("packid", "Performance");
		return mav;
	}
	
	@RequestMapping(value="/loadPerformanceScalar")
	public @ResponseBody String loadPerformanceScalar(String[] names) throws CustomException{
		JSONObject json=service.loadPerformanceScalar(names);
		return json.toString();
	}
	
	@RequestMapping(value="/updatePerformanceScalar",produces="text/html;charset=UTF-8")
	public @ResponseBody String updatePerformanceScalar(HttpServletRequest request) throws CustomException{
		service.updatePerformanceScalar(generate(request));
		JSONObject json=new JSONObject();
		json.element("msg", "保存完毕");
		return json.toString();
	}
	//-------------性能参数配置 结束----------------
	//-------------基本服务配置 开始----------------
	@RequestMapping(value="/serviceUI")
	public ModelAndView serviceUI(){
		List<Item> items=service.getServiceItem();
		ModelAndView mav=new ModelAndView("sbc/sbc");
		mav.addObject("items", items);
		mav.addObject("packid", "Service");
		return mav;
	}
	
	@RequestMapping(value="/loadServiceScalar")
	public @ResponseBody String loadServiceScalar(String[] names) throws CustomException{
		JSONObject json=service.loadServiceScalar(names);
		return json.toString();
	}
	
	@RequestMapping(value="/updateServiceScalar",produces="text/html;charset=UTF-8")
	public @ResponseBody String updateServiceScalar(HttpServletRequest request) throws CustomException{
		service.updateServiceScalar(generate(request));
		JSONObject json=new JSONObject();
		json.element("msg", "保存完毕");
		return json.toString();
	}
	//-------------基本服务配置 结束----------------
	//-------------运行参数 开始----------------
	@RequestMapping(value="/runtimeUI")
	public ModelAndView runtimeUI(){
		List<Item> items=service.getRuntimeItem();
		ModelAndView mav=new ModelAndView("sbc/sbc");
		mav.addObject("items", items);
		mav.addObject("packid", "Runtime");
		return mav;
	}
	
	@RequestMapping(value="/loadRuntimeScalar")
	public @ResponseBody String loadRuntimeScalar(String[] names) throws CustomException{
		JSONObject json=service.loadRuntimeScalar(names);
		return json.toString();
	}
	
	@RequestMapping(value="/updateRuntimeScalar",produces="text/html;charset=UTF-8")
	public @ResponseBody String updateRuntimeScalar(HttpServletRequest request) throws CustomException{
		service.updateRuntimeScalar(generate(request));
		JSONObject json=new JSONObject();
		json.element("msg", "保存完毕");
		return json.toString();
	}
	//-------------运行参数 结束----------------
	//-------------板卡规划配置 开始----------------
	@RequestMapping(value="/boardUI")
	public ModelAndView boardUI(){
		List<Item> items=service.getBoardItem();
		ModelAndView mav=new ModelAndView("sbc/sbc");
		mav.addObject("items", items);
		mav.addObject("packid", "Board");
		return mav;
	}
	
	@RequestMapping(value="/loadBoardScalar")
	public @ResponseBody String loadBoardScalar(String[] names) throws CustomException{
		JSONObject json=service.loadBoardScalar(names);
		return json.toString();
	}
	
	@RequestMapping(value="/updateBoardScalar",produces="text/html;charset=UTF-8")
	public @ResponseBody String updateBoardScalar(HttpServletRequest request) throws CustomException{
		service.updateBoardScalar(generate(request));
		JSONObject json=new JSONObject();
		json.element("msg", "保存完毕");
		return json.toString();
	}
	//-------------板卡规划配置 结束----------------
	//-------------开机启动 开始----------------
	@RequestMapping(value="/startupUI")
	public ModelAndView startupUI(){
		List<Item> items=service.getsStartupItem();
		ModelAndView mav=new ModelAndView("sbc/sbc");
		mav.addObject("items", items);
		mav.addObject("packid", "Startup");
		return mav;
	}
	
	@RequestMapping(value="/loadStartupScalar")
	public @ResponseBody String loadStartupScalar(String[] names) throws CustomException{
		JSONObject json=service.loadStartupScalar(names);
		return json.toString();
	}
	
	@RequestMapping(value="/updateStartupScalar",produces="text/html;charset=UTF-8")
	public @ResponseBody String updateStartupScalar(HttpServletRequest request) throws CustomException{
		service.updateStartupScalar(generate(request));
		JSONObject json=new JSONObject();
		json.element("msg", "保存完毕");
		return json.toString();
	}
	//-------------开机启动 结束----------------
	
	private Map<String,String> generate(HttpServletRequest request){
		Enumeration<String> names=request.getParameterNames();
		Map<String,String> map=new HashMap<>();
		while(names.hasMoreElements()){
			String name=names.nextElement();
			String value=request.getParameter(name);
			map.put(name, value);
		}
		return map;
	}
	
	//-------------组配置 开始------------------
	@RequestMapping(value="/groupManageUI")
	public ModelAndView groupManageUI(){
		List<Item> items=service.getGroupManageItem();
		ModelAndView mav=new ModelAndView("sbc/groupManage");
		mav.addObject("items", items);
		mav.addObject("packid", "GroupManage");
		return mav;
	}
	
	@RequestMapping(value="/loadGroupManageGrid")
	public @ResponseBody PageBean loadGroupManageGrid(
			Integer id,@RequestParam(defaultValue="1") Integer page,
			@RequestParam(value="rows",defaultValue="50") Integer pageSize) throws CustomException{
		PageBean pageBean=service.loadGroupManageGrid(id,page,pageSize);
		return pageBean;
	}
	
	@RequestMapping(value="/deleteGroupManage")
	public @ResponseBody String deleteGroupManage(Integer[] ids) throws CustomException{
		service.deleteGroupManage(ids);
		JSONObject json=new JSONObject();
		json.element("msg", "删除成功");
		return json.toString();
	}
	
	@RequestMapping(value="/addGroupManage",produces="text/html;charset=UTF-8")
	public @ResponseBody String addGroupManage(HttpServletRequest request) throws CustomException{
		service.addGroupManage(generate(request));
		JSONObject json=new JSONObject();
		json.element("msg", "保存完毕");
		return json.toString();
	}
	
	@RequestMapping(value="/updateGroupManage",produces="text/html;charset=UTF-8")
	public @ResponseBody String updateGroupManage(HttpServletRequest request) throws CustomException{
		service.updateGroupManage(generate(request));
		JSONObject json=new JSONObject();
		json.element("msg", "保存完毕");
		return json.toString();
	}
	//-------------组配置 结束------------------
	//-------------组策略 开始------------------
	@RequestMapping(value="/groupPolicyUI")
	public ModelAndView groupPolicyUI(){
		List<Item> items=service.getGroupPolicyItem();
		ModelAndView mav=new ModelAndView("sbc/groupPolicy");
		mav.addObject("items", items);
		mav.addObject("packid", "GroupPolicy");
		return mav;
	}
	
	@RequestMapping(value="/loadGroupPolicyGrid")
	public @ResponseBody PageBean loadGroupPolicyGrid(
			Integer id,@RequestParam(defaultValue="1") Integer page,
			@RequestParam(value="rows",defaultValue="50") Integer pageSize) throws CustomException{
		PageBean pageBean=service.loadGroupPolicyGrid(id,page,pageSize);
		return pageBean;
	}
	
	@RequestMapping(value="/loadCacId",produces="text/html;charset=UTF-8")
	public @ResponseBody String loadCacId() throws CustomException{
		PageBean pageBean=service.loadGroupPolicyGrid(9,1,10000);
		Iterator<?> i=pageBean.getRows().iterator();
		while(i.hasNext()){
			JSONObject o=(JSONObject)i.next();
			if(!o.containsKey("GroupPolicy_cacProfileID"))
				i.remove();
		}
		JSONArray json=JSONArray.fromObject(pageBean.getRows());
		return json.toString();
	}
	
	@RequestMapping(value="/deleteGroupPolicy")
	public @ResponseBody String deleteGroupPolicy(Integer[] ids) throws CustomException{
		service.deleteGroupPolicy(ids);
		JSONObject json=new JSONObject();
		json.element("msg", "删除成功");
		return json.toString();
	}
	
	@RequestMapping(value="/addGroupPolicy")
	public @ResponseBody String addGroupPolicy(HttpServletRequest request) throws CustomException{
		service.addGroupPolicy(generate(request));
		JSONObject json=new JSONObject();
		json.element("msg", "保存完毕");
		return json.toString();
	}
	
	@RequestMapping(value="/updateGroupPolicy")
	public @ResponseBody String updateGroupPolicy(HttpServletRequest request) throws CustomException{
		service.updateGroupPolicy(generate(request));
		JSONObject json=new JSONObject();
		json.element("msg", "保存完毕");
		return json.toString();
	}
	//-------------组策略 结束------------------
	//-------------用户优先保障策略 开始----------------
	@RequestMapping(value="/groupEnsureUI")
	public ModelAndView groupEnsureUI(){
		List<Item> items=service.getGroupEnsureItem();
		ModelAndView mav=new ModelAndView("sbc/sbc");
		mav.addObject("items", items);
		mav.addObject("packid", "GroupEnsure");
		return mav;
	}
	
	@RequestMapping(value="/loadGroupEnsureScalar")
	public @ResponseBody String loadGroupEnsureScalar(String[] names) throws CustomException{
		JSONObject json=service.loadGroupEnsureScalar(names);
		return json.toString();
	}
	
	@RequestMapping(value="/updateGroupEnsureScalar",produces="text/html;charset=UTF-8")
	public @ResponseBody String updateGroupEnsureScalar(HttpServletRequest request) throws CustomException{
		service.updateGroupEnsureScalar(generate(request));
		JSONObject json=new JSONObject();
		json.element("msg", "保存完毕");
		return json.toString();
	}
	//-------------用户优先保障策略 结束----------------
	//-------------ACLInfo 开始------------------
	@RequestMapping(value="/aclUI")
	public ModelAndView aclUI(){
		List<Item> items=service.getAclItem();
		ModelAndView mav=new ModelAndView("sbc/acl");
		mav.addObject("items", items);
		mav.addObject("packid", "Acl");
		return mav;
	}
	
	@RequestMapping(value="/loadAclGrid")
	public @ResponseBody PageBean loadAclGrid(
			Integer id,@RequestParam(defaultValue="1") Integer page,
			@RequestParam(value="rows",defaultValue="50") Integer pageSize) throws CustomException{
		PageBean pageBean=service.loadAclGrid(id,page,pageSize);
		return pageBean;
	}
	
	@RequestMapping(value="/deleteAcl")
	public @ResponseBody String deleteAcl(Integer[] ids) throws CustomException{
		service.deleteAcl(ids);
		JSONObject json=new JSONObject();
		json.element("msg", "删除成功");
		return json.toString();
	}
	
	@RequestMapping(value="/addAcl")
	public @ResponseBody String addAcl(HttpServletRequest request) throws CustomException{
		service.addAcl(generate(request));
		JSONObject json=new JSONObject();
		json.element("msg", "保存完毕");
		return json.toString();
	}
	//-------------ACLInfo 结束------------------
	//-------------黑名单 开始------------------
	@RequestMapping(value="/blacklistUI")
	public ModelAndView blacklistUI(){
		List<Item> items=service.getBlacklistItem();
		ModelAndView mav=new ModelAndView("sbc/blacklist");
		mav.addObject("items", items);
		mav.addObject("packid", "Blacklist");
		return mav;
	}
	
	@RequestMapping(value="/loadBlacklistGrid")
	public @ResponseBody PageBean loadBlacklistGrid(
			Integer id,@RequestParam(defaultValue="1") Integer page,
			@RequestParam(value="rows",defaultValue="50") Integer pageSize) throws CustomException{
		PageBean pageBean=service.loadBlacklistGrid(id,page,pageSize);
		return pageBean;
	}
	
	@RequestMapping(value="/deleteBlacklist")
	public @ResponseBody String deleteBlacklist(Integer[] ids) throws CustomException{
		service.deleteBlacklist(ids);
		JSONObject json=new JSONObject();
		json.element("msg", "删除成功");
		return json.toString();
	}
	
	@RequestMapping(value="/addBlacklist")
	public @ResponseBody String addBlacklist(HttpServletRequest request) throws CustomException{
		service.addBlacklist(generate(request));
		JSONObject json=new JSONObject();
		json.element("msg", "保存完毕");
		return json.toString();
	}
	//-------------黑名单 结束------------------
	//-------------恶意访问检测策略 开始----------------
	@RequestMapping(value="/spiteUI")
	public ModelAndView spiteUI(){
		List<Item> items=service.getSpiteItem();
		ModelAndView mav=new ModelAndView("sbc/sbc");
		mav.addObject("items", items);
		mav.addObject("packid", "Spite");
		return mav;
	}
	
	@RequestMapping(value="/loadSpiteScalar")
	public @ResponseBody String loadSpiteScalar(String[] names) throws CustomException{
		JSONObject json=service.loadSpiteScalar(names);
		return json.toString();
	}
	
	@RequestMapping(value="/updateSpiteScalar",produces="text/html;charset=UTF-8")
	public @ResponseBody String updateSpiteScalar(HttpServletRequest request) throws CustomException{
		service.updateSpiteScalar(generate(request));
		JSONObject json=new JSONObject();
		json.element("msg", "保存完毕");
		return json.toString();
	}
	//-------------恶意访问检测策略 结束----------------
	//-------------业务限制策略 开始------------------
	@RequestMapping(value="/businessUI")
	public ModelAndView businessUI(){
		List<Item> items=service.getBusinessItem();
		ModelAndView mav=new ModelAndView("sbc/business");
		mav.addObject("items", items);
		mav.addObject("packid", "Business");
		return mav;
	}
	
	@RequestMapping(value="/loadBusinessGrid")
	public ModelAndView loadBusinessGrid(
			Integer id,@RequestParam(defaultValue="1") Integer page,
			@RequestParam(value="rows",defaultValue="50") Integer pageSize) throws CustomException{
		ModelAndView mav=new ModelAndView("sbc/business2");
		PageBean pageBean=new PageBean();
//		try{
			Item item=itemService.findById(id);
			mav.addObject("item", item);
			pageBean=service.loadBusinessGrid(id,page,pageSize);
//		}catch(Exception e){
//			mav.addObject("error", e.getMessage());
//		}
		mav.addObject("pageBean", pageBean);
		return mav;
	}
	
	@RequestMapping(value="/deleteBusiness")
	public @ResponseBody String deleteBusiness(Integer[] ids,String name) throws CustomException{
		service.deleteBusiness(ids,name);
		JSONObject json=new JSONObject();
		json.element("msg", "删除成功");
		return json.toString();
	}
	
	@RequestMapping(value="/addBusiness")
	public @ResponseBody String addBusiness(HttpServletRequest request) throws CustomException{
		service.addBusiness(generate(request));
		JSONObject json=new JSONObject();
		json.element("msg", "保存完毕");
		return json.toString();
	}
	//-------------业务限制策略 结束------------------
	//-------------媒体链路信息 开始------------------
	@RequestMapping(value="/mediaUI")
	public ModelAndView mediaUI(){
		List<Item> items=service.getBusinessItem();
		ModelAndView mav=new ModelAndView("sbc/media");
		mav.addObject("items", items);
		mav.addObject("packid", "Media");
		return mav;
	}
	
	@RequestMapping(value="/loadMediaGrid")
	public @ResponseBody PageBean loadMediaGrid(
			Integer id,@RequestParam(defaultValue="1") Integer page,
			@RequestParam(value="rows",defaultValue="50") Integer pageSize) throws CustomException{
		PageBean pageBean=service.loadMediaGrid(id,page,pageSize);
		return pageBean;
	}
	//-------------媒体链路信息 结束------------------
	//-------------用户注册状态 开始------------------
	@RequestMapping(value="/statusUI")
	public ModelAndView statusUI(){
		List<Item> items=service.getStatusItem();
		ModelAndView mav=new ModelAndView("sbc/status");
		mav.addObject("items", items);
		mav.addObject("packid", "Status");
		return mav;
	}
	
	@RequestMapping(value="/loadStatusGrid")
	public @ResponseBody PageBean loadStatusGrid(
			Integer id,@RequestParam(defaultValue="1") Integer page,
			@RequestParam(value="rows",defaultValue="50") Integer pageSize) throws CustomException{
		PageBean pageBean=service.loadStatusGrid(id,page,pageSize);
		return pageBean;
	}
	//-------------用户注册状态 结束------------------
	//-------------板卡状态 开始------------------
	@RequestMapping(value="/boardStatusUI")
	public ModelAndView boardStatusUI(){
		List<Item> items=service.getBoardStatusItem();
		ModelAndView mav=new ModelAndView("sbc/boardStatus");
		mav.addObject("items", items);
		mav.addObject("packid", "BoardStatus");
		return mav;
	}
	
	@RequestMapping(value="/loadBoardStatusGrid")
	public @ResponseBody PageBean loadBoardStatusGrid(
			Integer id,@RequestParam(defaultValue="1") Integer page,
			@RequestParam(value="rows",defaultValue="50") Integer pageSize) throws CustomException{
		PageBean pageBean=service.loadBoardStatusGrid(id,page,pageSize);
		return pageBean;
	}
	//-------------板卡状态 结束------------------
	//-------------运行告警 开始------------------
	@RequestMapping(value="/alarmUI")
	public ModelAndView alarmUI(){
		List<Item> items=service.getAlarmItem();
		ModelAndView mav=new ModelAndView("sbc/alarm");
		mav.addObject("items", items);
		mav.addObject("packid", "Alarm");
		return mav;
	}
	
	@RequestMapping(value="/loadAlarmGrid")
	public @ResponseBody PageBean loadAlarmGrid(
			Integer id,@RequestParam(defaultValue="1") Integer page,
			@RequestParam(value="rows",defaultValue="50") Integer pageSize) throws CustomException{
		PageBean pageBean=service.loadAlarmGrid(id,page,pageSize);
		return pageBean;
	}
	//-------------运行告警 结束------------------
	//-------------统计信息 开始----------------
	@RequestMapping(value="/statisticsUI")
	public ModelAndView statisticsUI(){
		List<Item> items=service.getStatisticsItem();
		ModelAndView mav=new ModelAndView("sbc/statistics");
		mav.addObject("items", items);
		mav.addObject("packid", "Statistics");
		return mav;
	}
	
	@RequestMapping(value="/loadStatisticsScalar")
	public @ResponseBody String loadStatisticsScalar(String[] names) throws CustomException{
		JSONObject json=service.loadStatisticsScalar(names);
		return json.toString();
	}
	//-------------统计信息 结束------------------
}
