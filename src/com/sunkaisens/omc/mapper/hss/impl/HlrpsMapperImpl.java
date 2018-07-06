package com.sunkaisens.omc.mapper.hss.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sunkaisens.omc.mapper.hss.HlrpsMapper;
import com.sunkaisens.omc.po.hss.Hlrps;
import com.sunkaisens.omc.vo.hss.HssBusinessVo;

public class HlrpsMapperImpl extends HssSqlSessionDaoSupport implements HlrpsMapper {

	@Override
	public int selectCount(HssBusinessVo hss) {
		Map<String,Object> param=new HashMap<>();
		param.put("hss",hss);
		int count=getSqlSession().selectOne(namespace+".selectCount", param);
		return count;
	}
	
	@Override
	public List<Hlrps> select(int jumpNum, int limit, HssBusinessVo hss) {
		Map<String,Object> param=new HashMap<>();
		param.put("jumpNum",jumpNum);
		param.put("limit",limit);
		param.put("hss",hss);
		return getSqlSession().selectList(namespace+".selectAll",param);
	}
	
	@Override
	public List<Hlrps> selectAll(int jumpNum, int limit) {
		Map<String,Object> param=new HashMap<>();
		param.put("jumpNum",jumpNum);
		param.put("limit",limit);
		return getSqlSession().selectList(namespace+".select",param);
	}
	
	@Override
	public void insert(Hlrps hlrps) {
		int index=getHssDatabaseIndex(hlrps.getImsi());
		getSqlSession(index).insert(namespace+".insert", hlrps);
	}

	@Override
	public void deleteById(String imsi) {
		int index=getHssDatabaseIndex(imsi);
		getSqlSession(index).delete(namespace+".deleteById", imsi);
	}

	@Override
	public void update(Hlrps hlrps) {
		int index=getHssDatabaseIndex(hlrps.getImsi());
		getSqlSession(index).update(namespace+".update", hlrps);
	}

	@Override
	public Hlrps selectById(String imsi) {
		return getSqlSession().selectOne(namespace+".selectById", imsi);
	}

	@Override
	public void batchAdd(int count, String imsi, String mdn, String esn,
			Integer msprofile, Integer deviceType, Integer msvocodec,Integer msprofile_extra) {
		Map<String,Object> param=new HashMap<>();
		param.put("count", count);
		param.put("imsi", imsi);
		param.put("mdn", mdn);
		param.put("esn", esn);
		param.put("msprofile", msprofile);
		param.put("deviceType", deviceType);
		param.put("msvocodec", msvocodec);
		param.put("msprofile_extra", msprofile_extra);
		getSqlSession().insert(namespace+".batchAdd", param);
		
	}

	@Override
	public void batchUpdate(int count, String imsi, String mdn, String esn,
			Integer msprofile, Integer deviceType, Integer msvocodec,Integer msprofile_extra) {
		Map<String,Object> param=new HashMap<>();
		param.put("count", count);
		param.put("imsi", imsi);
		param.put("mdn", mdn);
		param.put("esn", esn);
		param.put("msprofile", msprofile);
		param.put("deviceType", deviceType);
		param.put("msvocodec", msvocodec);
		param.put("msprofile_extra", msprofile_extra);
		getSqlSession().insert(namespace+".batchUpdate", param);
		
	}

	



}
