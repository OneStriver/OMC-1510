package com.sunkaisens.omc.service.hss;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.List;
import java.util.ResourceBundle;

import com.sunkaisens.omc.exception.CustomException;
import com.sunkaisens.omc.thread.messageEncapsulation.QueryResponseMsg;
import com.sunkaisens.omc.vo.core.PageBean;
import com.sunkaisens.omc.vo.hss.HssBusinessVo;

import net.sf.json.JSONObject;

/**
 * 核心网终端业务处理接口
 */
public interface HssService{
	
	/**
	 * 返回HssBusinessVo
	 */
	HssBusinessVo getById(String imsi) throws CustomException;
	
	/**
	 * 用户分页列表
	 */
	PageBean getPageBean(int pageNum ,int pageSize,HssBusinessVo hss);
	
	
	/**
	 * 获取HssReduancy数据
	 */
	PageBean getRePageBean(int pageNum ,int pageSize,HssBusinessVo hss);
	
	/**
	 * 删除用户
	 */
	void deleteEntry(String imsi) throws CustomException;
	
	/**
	 * 批量删除用户
	 */
	void deleteEntries(String[] imsis) throws CustomException ;

	/**
	 * 批量添加
	 */
	void saveEntries(Collection<HssBusinessVo> entries) throws CustomException;

	/**
	 * 修改用户
	 */
	boolean update(HssBusinessVo entry,Integer userOperationType) throws CustomException;
	
	/**
	 * 读取用户状态
	 */
	JSONObject readStatus(int flag,String imsi,ResourceBundle bundle) throws CustomException;

	/**
	 * 添加用户
	 */
	void save(HssBusinessVo entry) throws CustomException;
	
	/**
	 * 同步消息
	 */
	void sendSync(int flag,String data) throws CustomException;

	/**
	 * 判断此号码是否存在
	 */
	boolean existMdn(String mdn);

	/**
	 * 按号段添加s
	 */
	void batchAdd(HssBusinessVo vo, Integer batchCount,InputStream is) throws CustomException;
	
	/**
	 * 按号段修改
	 */
	void batchUpdate(HssBusinessVo vo, Integer batchCount,InputStream is);
	
	//批量修改操作
	void batchHssUpdate(HssBusinessVo vo,String[] imsis);
	
	
	void sendRemoteSetUe(int flag,String imsi,int update_delete);
	
	QueryResponseMsg sendHssGetUe(int flag,String imsi,int update_delete);
	
	/**
	 * 同步消息
	 */
	public void sendSetUes(final String[] imsis,final int update_delete) ;

	/**
	 * 导出
	 */
	InputStream exportHss() throws IOException;

	/**
	 * 导入
	 */
	void importHss(InputStream is)throws Exception;

	/**
	 * 修改优先级
	 */
	void updatePriority(String[] imsis, Integer priority);

	//遥毙
	void updateUeRestore(int flag,String imsi) throws CustomException;

	void updateUeDestroy(int flag,String imsi) throws CustomException;
	
	//遥晕
	void updateUeSwoon(int flag,String imsi) throws CustomException;
	
	void updateUeNormal(int flag,String imsi) throws CustomException;
	
	//对空监听
	void updateAirMonitorOpen(int flag,String imsi) throws CustomException;
	
	void updateAirMonitorClose(int flag,String imsi) throws CustomException;

	void importAucExcel(InputStream is) throws CustomException;

	void importExcel(InputStream inputStream,int flag,int hidValue) throws CustomException;

	void importXml(InputStream inputStream) throws CustomException;
	
	/**
	 * 查询所有的Imsi
	 */
	List<String> selectAllImsi();

	PageBean getPageBeanLine(int pageNum, int pageSize, HssBusinessVo hss);
	
}
