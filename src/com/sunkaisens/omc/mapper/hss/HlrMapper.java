package com.sunkaisens.omc.mapper.hss;

import java.util.List;

import javax.transaction.NotSupportedException;

import org.apache.ibatis.annotations.Param;

import com.sunkaisens.omc.po.hss.Hlr;
import com.sunkaisens.omc.vo.hss.HssBusinessVo;
/**
 * 
 * 
 * 
 * 
 * 
 * 定义HLR接口
 *
 */
public interface HlrMapper {
	void insert(Hlr hlr);
	
	void deleteById(String imsi);
	
	void update(Hlr hlr);
	
	HssBusinessVo selectHssById(String imsi);
	
	//查询所有的Imsi
	List<String> selectAllImsi(); 
	
	List<Hlr> select(@Param("jumpNum") int jumpNum,@Param("limit") int limit,
			@Param("hss")HssBusinessVo hss);
	
	List<Hlr> selectAll(@Param("jumpNum") int jumpNum,@Param("limit") int limit);
	
	int selectCount(@Param("hss")HssBusinessVo hss);
	
	Hlr selectById(String imsi);
	
	Hlr selectByMdn(String mdn);
	
	void setIsolation(String leavel) throws NotSupportedException;

	void batchAdd(@Param("count")int count,@Param("imsi")String imsi,
			@Param("mdn")String mdn,@Param("esn")String esn,@Param("msprofile")Integer msprofile,
			@Param("deviceType")Integer deviceType,@Param("msvocodec")Integer msvocodec,
			@Param("msprofile_extra")Integer msprofile_extra);
	
	void batchUpdate(@Param("count")int count,@Param("imsi")String imsi,
			@Param("mdn")String mdn,@Param("esn")String esn,@Param("msprofile")Integer msprofile,
			@Param("deviceType")Integer deviceType,@Param("msvocodec")Integer msvocodec,
			@Param("msprofile_extra")Integer msprofile_extra);
	
	Hlr selectByEsn(String esn);
}
