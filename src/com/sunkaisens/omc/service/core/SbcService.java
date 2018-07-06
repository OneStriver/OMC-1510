package com.sunkaisens.omc.service.core;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import com.sunkaisens.omc.exception.CustomException;
import com.sunkaisens.omc.po.core.Item;
import com.sunkaisens.omc.vo.core.PageBean;
/**
 * 
 * 
 * 
 * 
 *  定义SbcService接口
 *
 */
public interface SbcService {

	List<Item> getNetItem();

	JSONObject loadScalar(String[] names,Integer packId) throws CustomException;

	PageBean loadGrid(Integer id,Integer packId,Integer page, Integer pageSize) throws CustomException;

	void updateScalar(Map<String,String> params,Integer packId) throws CustomException;
	
	JSONObject loadNetScalar(String[] names) throws CustomException;

	void updateNetScalar(Map<String, String> params) throws CustomException;

	List<Item> getGroupManageItem();

	PageBean loadGroupManageGrid(Integer id,Integer page, Integer pageSize) throws CustomException;

	void deleteGroupManage(Integer[] ids) throws CustomException;
	
	void delete(Integer[] ids,Integer packId) throws CustomException;

	void addGroupManage(Map<String, String> map) throws CustomException;

	void updateGroupManage(Map<String, String> map) throws CustomException;

	void add(Map<String, String> params, Integer packId) throws CustomException;

	void update(Map<String, String> params, Integer packId)
			throws CustomException;

	JSONObject loadTlsScalar(String[] names) throws CustomException;

	void updateTlsScalar(Map<String, String> map) throws CustomException;

	List<Item> getTlsItem();

	JSONObject loadPerformanceScalar(String[] names)throws CustomException;

	void updatePerformanceScalar(Map<String, String> map)throws CustomException;

	List<Item> getPerformanceItem();

	List<Item> getServiceItem();

	JSONObject loadServiceScalar(String[] names) throws CustomException;

	void updateServiceScalar(Map<String, String> params) throws CustomException;

	List<Item> getRuntimeItem();

	JSONObject loadRuntimeScalar(String[] names) throws CustomException;

	void updateRuntimeScalar(Map<String, String> params) throws CustomException;

	List<Item> getBoardItem();

	JSONObject loadBoardScalar(String[] names) throws CustomException;

	void updateBoardScalar(Map<String, String> params) throws CustomException;

	List<Item> getsStartupItem();

	JSONObject loadStartupScalar(String[] names) throws CustomException;

	void updateStartupScalar(Map<String, String> params) throws CustomException;

	List<Item> getGroupPolicyItem();

	PageBean loadGroupPolicyGrid(Integer id,Integer page, Integer pageSize) throws CustomException;

	void deleteGroupPolicy(Integer[] ids) throws CustomException;

	void addGroupPolicy(Map<String, String> map) throws CustomException;

	void updateGroupPolicy(Map<String, String> map) throws CustomException;

	List<Item> getGroupEnsureItem();

	JSONObject loadGroupEnsureScalar(String[] names) throws CustomException;

	void updateGroupEnsureScalar(Map<String, String> params)
			throws CustomException;

	List<Item> getAclItem();

	PageBean loadAclGrid(Integer id,Integer page, Integer pageSize) throws CustomException;

	void deleteAcl(Integer[] ids) throws CustomException;

	void addAcl(Map<String, String> map) throws CustomException;

	List<Item> getBlacklistItem();

	PageBean loadBlacklistGrid(Integer id,Integer page, Integer pageSize) throws CustomException;

	void deleteBlacklist(Integer[] ids) throws CustomException;

	void addBlacklist(Map<String, String> map) throws CustomException;

	List<Item> getSpiteItem();

	JSONObject loadSpiteScalar(String[] names) throws CustomException;

	void updateSpiteScalar(Map<String, String> params) throws CustomException;

	List<Item> getBusinessItem();

	PageBean loadBusinessGrid(Integer id,Integer page, Integer pageSize) throws CustomException;

	void deleteBusiness(Integer[] ids,String name) throws CustomException;

	void addBusiness(Map<String, String> map) throws CustomException;

	List<Item> getMediaItem();

	PageBean loadMediaGrid(Integer id,Integer page, Integer pageSize) throws CustomException;

	List<Item> getStatusItem();

	PageBean loadStatusGrid(Integer id,Integer page, Integer pageSize) throws CustomException;

	List<Item> getBoardStatusItem();

	PageBean loadBoardStatusGrid(Integer id,Integer page, Integer pageSize) throws CustomException;

	List<Item> getAlarmItem();

	PageBean loadAlarmGrid(Integer id,Integer page, Integer pageSize) throws CustomException;

	List<Item> getStatisticsItem();

	JSONObject  loadStatisticsScalar(String[] names) throws CustomException;
}
