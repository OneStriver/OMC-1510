package com.sunkaisens.omc.service.ims;

import com.sunkaisens.omc.exception.CustomException;
import com.sunkaisens.omc.po.cscf.Ue;
import com.sunkaisens.omc.vo.core.PageBean;
/**
 * 
 * 
 * 
 * 
 * 
 * 
 * 定义ImsService接口
 *
 */
public interface ImsService {
	/**
	 * 
	 * 
	 * 
	 * 
	 * 获取数据分页列表
	 * @param pageNum
	 * @param pageSize
	 * @param ue
	 * @return
	 */
	PageBean getPageBean(int pageNum ,int pageSize, Ue ue);
    /**
     * 
     * 
     * 
     * 
     * 获取Ue实例By UeName
     * @param ueName
     * @return
     */
	Ue getByName(String ueName);
    /**
     * 
     * 
     * 
     * 
     * 更新操作
     * @param ue
     * @param domain
     * @throws CustomException
     */
	void update(Ue ue,String domain) throws CustomException;
	  /**
	   * 
	   * 
	   * 
	   * 
	   * 删除操作
	   * @param ueNames
	   */
	void deleteUes(String[] ueNames);
	  /**
	   * 
	   * 
	   * 
	   * 
	   * 保存操作
	   * @param ue
	   * @param domain
	   * @throws CustomException
	   */
	void save(Ue ue, String domain) throws CustomException;
}
