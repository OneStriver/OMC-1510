package com.sunkaisens.omc.service.impl.core;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.sunkaisens.omc.context.core.OmcServerContext;
import com.sunkaisens.omc.exception.CustomException;
import com.sunkaisens.omc.mapper.core.CommonMapper;
import com.sunkaisens.omc.mapper.core.EntityMapper;
import com.sunkaisens.omc.mapper.core.ItemMapper;
import com.sunkaisens.omc.mapper.core.ModuleMapper;
import com.sunkaisens.omc.mapper.core.RelevanceMapper;
import com.sunkaisens.omc.po.core.Common;
import com.sunkaisens.omc.po.core.Config;
import com.sunkaisens.omc.po.core.Item;
import com.sunkaisens.omc.po.core.Module;
import com.sunkaisens.omc.po.core.Relevance;
import com.sunkaisens.omc.service.core.CommonService;
import com.sunkaisens.omc.thread.cncpMsg.CncpProtoType;
import com.sunkaisens.omc.thread.cncpMsg.CncpTaskExecutor;
import com.sunkaisens.omc.thread.cncpMsg.SendCncpMsgFactory;
import com.sunkaisens.omc.thread.messageEncapsulation.SetUpMsg;
import com.sunkaisens.omc.thread.messageEncapsulation.SetUpResponseMsg;
import com.sunkaisens.omc.util.CncpStatusTransUtil;
import com.sunkaisens.omc.util.ZipCompressUtil;
import com.sunkaisens.omc.vo.core.PageBean;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 *CommonService接口实现类
 */
@Service
public class CommonServiceImpl implements CommonService {
	protected final Logger log = LoggerFactory.getLogger(getClass());
	@Resource
	private CommonMapper mapper;
	@Resource
	private ItemMapper itemMapper;
	@Resource
	private RelevanceMapper relevanceMapper;
	@Resource
	private EntityMapper entityMapper;
	@Resource
	private CncpTaskExecutor cncpTaskExecutor;
	@Resource
	private SendCncpMsgFactory sendCncpMsgFactory;
	@Resource
	private ModuleMapper moduleMapper;
	@Override
	public void save(Common common) throws CustomException {
		if(mapper.selectByName(common.getName())!=null)
			throw new CustomException("此配置页面已存在");
		mapper.insert(common);
	}

	@Override
	public void update(Common common) {
		mapper.update(common);
	}

	@Override
	public void deleteById(Integer id) {
		mapper.deleteById(id);
	}

	@Override
	public List<Common> findAll() {
		return mapper.selectAll();
	}

	@Override
	public Common getById(Integer id) {
		return mapper.selectById(id);
	}

	@Override
	public PageBean getPageBean(Integer requestPage, Integer pageSize) {
		List<Common> commons=mapper.selectAll();
		PageBean pageBean=new PageBean(requestPage, pageSize, commons, commons.size());
		return pageBean;
	}

	@Override
	public void deleteByIds(Integer[] ids) {
		for(Integer id:ids){
			List<Relevance> relevances=relevanceMapper.selectRelevanceByCommonId(id);
			for(Relevance r:relevances){
				r.setCommon(null);
				relevanceMapper.update(r);
			}
			mapper.deleteById(id);
		}
	}

	@Override
	public List<Relevance> getRelevanceById(Integer id) {
		return relevanceMapper.selectRelevanceByCommonId(id);
	}
	
	@Override
	public List<Relevance> generateComponent(Integer id) throws IOException, CustomException {
		//dir为/root/OAM
		String dir=OmcServerContext.getInstance().getFtpDir();
		List<Relevance> relevances=relevanceMapper.getItemByCommonId(id);
		Iterator<Relevance> iterator=relevances.iterator();
		while(iterator.hasNext()){
			Relevance relevance=iterator.next();
			List<Item> items=itemMapper.getItemByRelevanceId(relevance.getId());
			if(items==null||items.size()==0) continue;
			Iterator<Item> i=items.iterator();
			Integer moduleId=null;
			Integer maxInstId=null;
			Config config=null;
			Item item=null;
			while(i.hasNext()){
				item=i.next();
				config=item.getConfig();
				Module module=config.getModule();
				moduleId=module.getId();
				maxInstId=entityMapper.selectMaxIstdIdByModuleId(moduleId);
				if(maxInstId==null) {//这个配置项所在的网元还木有下发，不做显示
					continue;
				}else{
					break;
				}
			}
			if(maxInstId==null){
				iterator.remove();
				continue;
			}
			File moduleDir=new File(dir,moduleId+"-"+maxInstId);
			File confF=new File(moduleDir,config.getName());
			String kvSpe=ConfigServiceImpl.getConfKvSeparator(config.getName());
			if(config.getSole()){
				String value=FileUtils.readFileToString(confF).replaceAll("[\t\n\r]+", "\n");
				relevance.setValue(value);
			}else{
				relevance.setValue("");
				List<String> lines=FileUtils.readLines(confF,"UTF-8");
				for(String line:lines){
					if(line.startsWith(item.getName()+kvSpe)){
						String[] kv=line.split(kvSpe,2);
						String newValue=null;
						if(StringUtils.isEmpty(relevance.getValue())){
							newValue=kv[1];
						}else{
							newValue=relevance.getValue()+"\n"+kv[1];
						}
						relevance.setValue(newValue);
						if(!item.getMultiline()) break;
					}
				}
			}
		}
		return relevances;
	}
	
	@Override
	public void modifyConf(JSONArray arr) throws IOException, CustomException {
		Map<File,String> restore=new HashMap<>();
		Map<File,String> restorenew=new HashMap<>();
		List<String> confNames=new ArrayList<String>();
		List<Integer> moduleIds=new ArrayList<Integer>();
		List<Integer> instIds=new ArrayList<Integer>();
		String dir=OmcServerContext.getInstance().getFtpDir();
		try {
			for(Object o:arr){
				JSONObject json=(JSONObject)o;
				Relevance relevance=relevanceMapper.selectById(json.getInt("name"));
				if(relevance==null){
					throw new CustomException("此关联配置【"+relevanceMapper
							.selectById(json.getInt("name")).getName()+"】已被删除！");
				}else{// 级联
					List<Item> items=itemMapper.getItemByRelevanceId(relevance.getId());
					for(Item i:items){
						modifySingle(restore,restorenew, confNames, moduleIds, instIds, dir, json,i);
					}
				}
			}
		} catch (Exception e) {
			for(Entry<File,String> entry:restore.entrySet()){
				FileUtils.write(entry.getKey(),entry.getValue(),false);
			}
			throw new CustomException(e);
		}
		for(int i=0;i<moduleIds.size();i++){
			String content = "file:" +confNames.get(i) +"\n";
			SetUpMsg msg = sendCncpMsgFactory.createSendSetCncpMsg(
					CncpProtoType.OMC_HEADER, CncpProtoType.OAM_HEADER, CncpProtoType.CNCP_SET_MSG, 
					moduleIds.get(i), instIds.get(i), CncpProtoType.CNCP_SET_NE_FILE_UPDATE, content);
			SetUpResponseMsg resMsg = cncpTaskExecutor.invokeSetUpMsg(msg, true, OmcServerContext.getInstance().getOamIp(), OmcServerContext.getInstance().getOamPort());
			if(resMsg.getResult()!=CncpProtoType.SUCCESS){
				for(Entry<File,String> entry:restore.entrySet()){
					FileUtils.write(entry.getKey(),entry.getValue(),false);
				}
				throw new CustomException(CncpStatusTransUtil.transLocaleStatusMessage(resMsg.getResult()));
			}
		}
		
		Set<Integer> unique=new HashSet<>(moduleIds);
		
		for(Integer moduleId:unique){
			Module module=moduleMapper.selectById(moduleId);
			Integer instId=entityMapper.selectMaxIstdIdByModuleId(moduleId);
			File moduleDir=new File(dir,moduleId+"-"+instId);
			for(Entry<File,String> entry:restorenew.entrySet()){
				FileUtils.write(entry.getKey(),entry.getValue(),false);
			}
			File zip = new File(moduleDir + ".zip");
			log.info("正在打包:"+zip.getName());
			ZipCompressUtil.zip(zip, moduleDir,new File(moduleDir+File.separator+module.getLog()));
			log.info("正在结束:"+zip.getName());
		}
	}
	
	
	private void modifySingle(Map<File, String> restore,Map<File, String> restorenew,
			List<String> confNames, List<Integer> moduleIds,
			List<Integer> instIds, String dir, JSONObject json, Item item)
			throws IOException, CustomException {
		Set<String> hasMul=new HashSet<>();
		Config config=item.getConfig();
		Module module=config.getModule();
		Integer moduleId=module.getId();
		moduleIds.add(moduleId);
		Integer instId=entityMapper.selectMaxIstdIdByModuleId(moduleId);
		if(instId==null){
			log.warn("关联配置项【"+json.getString("name")+"】所关联的网元【"+
					module.getName()+"】还未下发");
			return;
		}
		instIds.add(instId);
		File moduleDir=new File(dir,moduleId+"-"+instId);
		File confF=new File(moduleDir,config.getName());
		confNames.add(config.getName());
		String value=json.getString("value");
		if(!restore.containsKey(confF))
			restore.put(confF, FileUtils.readFileToString(confF));
		if(config.getSole()){
			FileUtils.write(confF,value,false);
		}else{
			String kvSpe=ConfigServiceImpl.getConfKvSeparator(config.getName());
			String key=item.getName();
			List<String> lines=FileUtils.readLines(confF);
			List<String> newLines=new ArrayList<String>();
			for(String line:lines){
				if(line.startsWith(key+kvSpe)){
					if(hasMul.contains(key)) continue;
					if(item.getMultiline()){
						String[] muls=value.split("\n");
						for(String mul:muls){
							newLines.add(key+kvSpe+mul.trim());
						}
						hasMul.add(key);
					}else{
						newLines.add(key+kvSpe+value);
					}
				}else{
					newLines.add(line);
				}
			}
			FileUtils.writeLines(confF, newLines);
			restorenew.put(confF, FileUtils.readFileToString(confF));
		}
	}
}
