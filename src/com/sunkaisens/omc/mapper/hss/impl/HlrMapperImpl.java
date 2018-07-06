package com.sunkaisens.omc.mapper.hss.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.NotSupportedException;

import com.sunkaisens.omc.mapper.hss.HlrMapper;
import com.sunkaisens.omc.po.hss.Hlr;
import com.sunkaisens.omc.vo.hss.HssBusinessVo;

public class HlrMapperImpl extends HssSqlSessionDaoSupport implements HlrMapper{
	
	@Override
	public void insert(Hlr hlr) {
		int index=getHssDatabaseIndex(hlr.getImsi());
		getSqlSession(index).insert(namespace+".insert", hlr);
	}

	@Override
	public void deleteById(String imsi) {
		int index=getHssDatabaseIndex(imsi);
		getSqlSession(index).delete(namespace+".deleteById", imsi);
	}

	@Override
	public void update(Hlr hlr) {
		int index=getHssDatabaseIndex(hlr.getImsi());
		getSqlSession(index).update(namespace+".update", hlr);
	}
	
	@Override
	public HssBusinessVo selectHssById(String imsi) {
		HssBusinessVo vo =getSqlSession().selectOne(namespace+".selectHssById", imsi);
		return vo;
	}

	@Override
	public int selectCount(HssBusinessVo hss) {
		Map<String,Object> param=new HashMap<>();
		if(hss.getPriority()>7){
			hss.setPriority(-hss.getPriority());
		}else{
			hss.setPriority(hss.getPriority());
		}
		param.put("hss",hss);
		int count=getSqlSession().selectOne(namespace+".selectCount", param);
		return count;
	}

	@Override
	public Hlr selectById(String imsi) {
		return getSqlSession().selectOne(namespace+".selectById",imsi);
	}
	
	@Override
	public Hlr selectByMdn(String mdn) {
		return getSqlSession().selectOne(namespace+".selectByMdn",mdn);
	}
	
	@Deprecated
	@Override
	public void setIsolation(String leavel) throws NotSupportedException {
		throw new NotSupportedException("此操作已被废弃，暂不支持");
	}

	@Override
	public List<Hlr> select(int jumpNum, int limit, HssBusinessVo hss) {
		Map<String,Object> param=new HashMap<>();
		param.put("jumpNum",jumpNum);
		param.put("limit",limit);
		param.put("hss",hss);
		return getSqlSession().selectList(namespace+".select",param);
	}
	
	@Override
	public List<Hlr> selectAll(int jumpNum, int limit) {
		Map<String,Object> param=new HashMap<>();
		param.put("jumpNum",jumpNum);
		param.put("limit",limit);
		return getSqlSession().selectList(namespace+".selectAll",param);
	}

	@Override
	public void batchAdd(int count, String imsi, String mdn, String esn,
			Integer msprofile, Integer deviceType, Integer msvocodec, Integer msprofile_extra) {
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
			Integer msprofile, Integer deviceType, Integer msvocodec, Integer msprofile_extra) {
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

	@Override
	public Hlr selectByEsn(String esn) {
		return getSqlSession().selectOne(namespace+".selectByEsn",esn);
	}
	
	//获取所有的Imsi
	@Override
	public List<String> selectAllImsi() {
		return getSqlSession().selectList(namespace+".selectAllImsi");
	}

	
}
