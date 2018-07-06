package com.sunkaisens.omc.mapper.hss;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sunkaisens.omc.po.hss.Hlr;
import com.sunkaisens.omc.po.hss.Hlrps;
import com.sunkaisens.omc.vo.hss.HssBusinessVo;
/**
 * 
 * 
 * 
 * 
 * 
 *定义HLRPs接口
 *
 */
public interface HlrpsMapper {
	void insert(Hlrps hlrps);
	void deleteById(String imsi);
	void update(Hlrps user);
	Hlrps selectById(String imsi);
	
	int selectCount(@Param("hss")HssBusinessVo hss);
	
	List<Hlrps> select(@Param("jumpNum") int jumpNum,@Param("limit") int limit,
			@Param("hss")HssBusinessVo hss);
	
	List<Hlrps> selectAll(@Param("jumpNum") int jumpNum,@Param("limit") int limit);
	
	void batchAdd(@Param("count")int count,@Param("imsi")String imsi,
			@Param("mdn")String mdn,@Param("esn")String esn,@Param("msprofile")Integer msprofile,
			@Param("deviceType")Integer deviceType,@Param("msvocodec")Integer msvocodec,
			@Param("msprofile_extra")Integer msprofile_extra);
	
	void batchUpdate(@Param("count")int count,@Param("imsi")String imsi,
			@Param("mdn")String mdn,@Param("esn")String esn,@Param("msprofile")Integer msprofile,
			@Param("deviceType")Integer deviceType,@Param("msvocodec")Integer msvocodec,
			@Param("msprofile_extra")Integer msprofile_extra);
}
