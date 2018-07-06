package com.sunkaisens.omc.util.hss;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.sunkaisens.omc.context.core.OmcServerContext;
import com.sunkaisens.omc.po.core.Card;
import com.sunkaisens.omc.po.core.Entity;
import com.sunkaisens.omc.po.core.Module;
import com.sunkaisens.omc.po.hss.Hlr;
import com.sunkaisens.omc.po.hss.Hlrps;
import com.sunkaisens.omc.vo.hss.HssBusinessVo;

public class RemoteDbUtil {
	
	//==========================HLRps表的操作开始===========================
	//根据IMSI查询HLRps
	public static Hlrps selectMainOrBackUpHlrpsById(int flag,String imsi) {
		Hlrps hlrps = new Hlrps();
		String sql = "select IMSI,MDN,ESN,TMSI,RncLoc,GeoLoc,MSprofile,MSprofile_extra,MSvocodec,Acount_num,currloc,tstamp,numOfSms,numOfVM,status,VLRAddr,DeviceType from hss.HLRps ";
		//条件查询
		sql += " where 1=1 ";
		if(imsi != null){
			sql += " and IMSI="+imsi;
		}
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = JdbcUtil.getMainConnection(flag);
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next()){
				hlrps.setImsi(rs.getString("IMSI"));
				hlrps.setMdn(rs.getString("MDN"));
				hlrps.setEsn(rs.getString("ESN"));
				hlrps.setTmsi(rs.getString("TMSI"));
				hlrps.setMsprofile(Integer.valueOf(rs.getString("MSprofile")));
				hlrps.setMsprofile_extra(Integer.valueOf(rs.getString("MSprofile_extra")));
				hlrps.setMsvocodec(Integer.valueOf(rs.getString("MSvocodec")));
				hlrps.setAcount_num(rs.getString("Acount_num"));
				hlrps.setCurrloc(rs.getString("currloc"));
				hlrps.setTstamp(rs.getTimestamp("tstamp"));
				hlrps.setNumOfSms(Integer.valueOf(rs.getString("numOfSms")));
				hlrps.setNumOfVm(Integer.valueOf(rs.getString("numOfVM")));
				hlrps.setStatus(Integer.valueOf(rs.getString("status")));
				hlrps.setVlraddr(rs.getString("VLRAddr"));
				hlrps.setDeviceType(Integer.valueOf(rs.getString("DeviceType")));
				return hlrps;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.free(rs, ps, conn);
		}
		return null;
	}
	//==========================HLRps表的操作结束===========================
	
	
	//==========================HLR表的操作开始===========================
	//查询HLR表
	public static List<Hlr> selectMainOrBackUpHlr(int flag,int pageNum, int pageSize, HssBusinessVo hss) {
		List<Hlr> list = new ArrayList<Hlr>();
		list.clear();
		if(hss.getPriority()>7){
			hss.setPriority(-hss.getPriority());
		}else{
			hss.setPriority(hss.getPriority());
		}
		String sql = "select IMSI,MDN,ESN,TMSI,RncLoc,GeoLoc,MSprofile,MSprofile_extra,MSvocodec,Acount_num,currloc,tstamp,numOfSms,numOfVM,status,VLRAddr,DeviceType from hss.HLR ";
		//条件查询
		sql += " where 1=1 ";
		if(hss != null){
			if(hss.getImsi()!=null){
				sql += " and IMSI like '%"+hss.getImsi()+"%'";
			}
			if(hss.getMdn()!=null){
				sql += " and MDN like '%"+hss.getMdn()+"%'";
			}
			if(hss.getDeviceType()!=null){
				sql += " and DeviceType="+hss.getDeviceType();
			}
			if(hss.getMsvocodec()!=null){
				sql += " and MSvocodec="+hss.getMsvocodec();
			}
			if(hss.getStatus()!=null){
				sql += " and status="+hss.getStatus();
			}
			if(hss.getPriority()!=0){
				sql += " and (((MSprofile & 0xF0000000)<<1)>>29)="+hss.getPriority();
			}
			
			if(hss.getSwoon(1)==1){
				sql += " and (((MSprofile_extra & 0x00000020)<<26)>>31)=0";
				sql += " and (((MSprofile_extra & 0x00000040)<<25)>>31)=0";
			}else if(hss.getSwoon(1)==2){
				sql += " and (((MSprofile_extra & 0x00000020)<<26)>>31)=0";
				sql += " and (((MSprofile_extra & 0x00000040)<<25)>>31)=1";
			}
			
			if(hss.getDestroy(1)==1){
				sql += " and (((MSprofile_extra & 0x00000008)<<28)>>31)=0";
				sql += " and (((MSprofile_extra & 0x00000010)<<27)>>31)=0";
			}else if(hss.getDestroy(1)==2){
				sql += " and (((MSprofile_extra & 0x00000008)<<28)>>31)=0";
				sql += " and (((MSprofile_extra & 0x00000010)<<27)>>31)=1";
			}
			
			if(hss.getAirMonitor(1)==1){
				sql += " and (((MSprofile_extra & 0x00000200)<<22)>>31)=0";
				sql += " and (((MSprofile_extra & 0x00000400)<<21)>>31)=0";
			}else if(hss.getAirMonitor(1)==2){
				sql += " and (((MSprofile_extra & 0x00000200)<<22)>>31)=0";
				sql += " and (((MSprofile_extra & 0x00000400)<<21)>>31)=1";
			}
		}
		//分页
		sql += " order by IMSI "+" limit "+pageNum+","+pageSize;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = JdbcUtil.getMainConnection(flag);
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next()){
				Hlr hlr = new Hlr();
				hlr.setImsi(rs.getString("IMSI"));
				hlr.setMdn(rs.getString("MDN"));
				hlr.setEsn(rs.getString("ESN"));
				hlr.setTmsi(rs.getString("TMSI"));
				hlr.setMsprofile(Integer.valueOf(rs.getString("MSprofile")));
				Integer remoteMsProfileExtra = Integer.valueOf(rs.getString("MSprofile_extra"));
				hlr.setMsprofile_extra(remoteMsProfileExtra);
				//遥晕
				int swoonBit1 = ((remoteMsProfileExtra & 0x00000020)<<26)>>>31;
				int swoonBit2 = ((remoteMsProfileExtra & 0x00000040)<<25)>>>31;
				if(swoonBit1==0 && swoonBit2==0){
					hlr.setSwoon(1);
				}else if(swoonBit1==0 && swoonBit2==1){
					hlr.setSwoon(2);
				}else if(swoonBit1==1 && swoonBit2==0){
					hlr.setSwoon(3);
				}else if(swoonBit1==1 && swoonBit2==1){
					hlr.setSwoon(4);
				}
				//遥毙
				int destroyBit1 = ((remoteMsProfileExtra & 0x00000008)<<28)>>>31;
				int destroyBit2 = ((remoteMsProfileExtra & 0x00000010)<<27)>>>31;
				if(destroyBit1==0 && destroyBit2==0){
					hlr.setDestroy(1);
				}else if(destroyBit1==0 && destroyBit2==1){
					hlr.setDestroy(2);
				}else if(destroyBit1==1 && destroyBit2==0){
					hlr.setDestroy(3);
				}else if(destroyBit1==1 && destroyBit2==1){
					hlr.setDestroy(4);
				}
				//对空监听
				int airMonitorBit1 = ((remoteMsProfileExtra & 0x00000200)<<22)>>>31;
				int airMonitorBit2 = ((remoteMsProfileExtra & 0x00000400)<<21)>>>31;
				if(airMonitorBit1==0 && airMonitorBit2==0){
					hlr.setAirMonitor(1);
				}else if(airMonitorBit1==0 && airMonitorBit2==1){
					hlr.setAirMonitor(2);
				}else if(airMonitorBit1==1 && airMonitorBit2==0){
					hlr.setAirMonitor(3);
				}else if(airMonitorBit1==1 && airMonitorBit2==1){
					hlr.setAirMonitor(4);
				}
				hlr.setMsvocodec(Integer.valueOf(rs.getString("MSvocodec")));
				hlr.setAcount_num(rs.getString("Acount_num"));
				hlr.setCurrloc(rs.getString("currloc"));
				hlr.setTstamp(rs.getTimestamp("tstamp"));
				hlr.setNumOfSms(Integer.valueOf(rs.getString("numOfSms")));
				hlr.setNumOfVm(Integer.valueOf(rs.getString("numOfVM")));
				hlr.setStatus(Integer.valueOf(rs.getString("status")));
				hlr.setVlraddr(rs.getString("VLRAddr"));
				hlr.setDeviceType(Integer.valueOf(rs.getString("DeviceType")));
				list.add(hlr);
			}
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.free(rs, ps, conn);
		}
		return null;
	}
	
	//查询HLR表的数据量
	public static int selectMainOrBackUpHlrCount(int flag, HssBusinessVo hss) {
		if(hss.getPriority()>7){
			hss.setPriority(-hss.getPriority());
		}else{
			hss.setPriority(hss.getPriority());
		}
		String sql = "select count(*) from hss.HLR ";
		//条件查询
		sql += " where 1=1 ";
		if(hss != null){
			if(hss.getImsi()!=null){
				sql += " and IMSI like '%"+hss.getImsi()+"%'";
			}
			if(hss.getMdn()!=null){
				sql += " and MDN like '%"+hss.getMdn()+"%'";
			}
			if(hss.getDeviceType()!=null){
				sql += " and DeviceType="+hss.getDeviceType();
			}
			if(hss.getMsvocodec()!=null){
				sql += " and MSvocodec="+hss.getMsvocodec();
			}
			if(hss.getStatus()!=null){
				sql += " and status="+hss.getStatus();
			}
			if(hss.getPriority()!=0){
				sql += " and (((MSprofile & 0xF0000000)<<1)>>29)="+hss.getPriority();
			}
			if(hss.getSwoon(1)==1){
				sql += " and (((MSprofile_extra & 0x00000020)<<26)>>31)=0";
				sql += " and (((MSprofile_extra & 0x00000040)<<25)>>31)=0";
			}
			if(hss.getSwoon(1)==2){
				sql += " and (((MSprofile_extra & 0x00000020)<<26)>>31)=0";
				sql += " and (((MSprofile_extra & 0x00000040)<<25)>>31)=1";
			}
			if(hss.getDestroy(1)==1){
				sql += " and (((MSprofile_extra & 0x00000008)<<28)>>31)=0";
				sql += " and (((MSprofile_extra & 0x00000010)<<27)>>31)=0";
			}
			if(hss.getDestroy(1)==2){
				sql += " and (((MSprofile_extra & 0x00000008)<<28)>>31)=0";
				sql += " and (((MSprofile_extra & 0x00000010)<<27)>>31)=1";
			}
			if(hss.getAirMonitor(1)==1){
				sql += " and (((MSprofile_extra & 0x00000200)<<22)>>31)=0";
				sql += " and (((MSprofile_extra & 0x00000400)<<21)>>31)=0";
			}
			if(hss.getAirMonitor(1)==2){
				sql += " and (((MSprofile_extra & 0x00000200)<<22)>>31)=0";
				sql += " and (((MSprofile_extra & 0x00000400)<<21)>>31)=1";
			}
		}
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = JdbcUtil.getMainConnection(flag);
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next()){
				return rs.getInt("count(*)");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.free(rs, ps, conn);
		}
		return 0;
	}
	
	//根据IMSI查询HLR
	public static Hlr selectMainOrBackUpHlrById(int flag,String imsi) {
		Hlr hlr = new Hlr();
		String sql = "select IMSI,MDN,ESN,TMSI,RncLoc,GeoLoc,MSprofile,MSprofile_extra,MSvocodec,Acount_num,currloc,tstamp,numOfSms,numOfVM,status,VLRAddr,DeviceType from hss.HLR ";
		//条件查询
		sql += " where 1=1 ";
		if(imsi != null){
			sql += " and IMSI="+imsi;
		}
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = JdbcUtil.getMainConnection(flag);
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next()){
				hlr.setImsi(rs.getString("IMSI"));
				hlr.setMdn(rs.getString("MDN"));
				hlr.setEsn(rs.getString("ESN"));
				hlr.setTmsi(rs.getString("TMSI"));
				hlr.setMsprofile(Integer.valueOf(rs.getString("MSprofile")));
				hlr.setMsprofile_extra(Integer.valueOf(rs.getString("MSprofile_extra")));
				hlr.setMsvocodec(Integer.valueOf(rs.getString("MSvocodec")));
				hlr.setAcount_num(rs.getString("Acount_num"));
				hlr.setCurrloc(rs.getString("currloc"));
				hlr.setTstamp(rs.getTimestamp("tstamp"));
				hlr.setNumOfSms(Integer.valueOf(rs.getString("numOfSms")));
				hlr.setNumOfVm(Integer.valueOf(rs.getString("numOfVM")));
				hlr.setStatus(Integer.valueOf(rs.getString("status")));
				hlr.setVlraddr(rs.getString("VLRAddr"));
				hlr.setDeviceType(Integer.valueOf(rs.getString("DeviceType")));
			}
			return hlr;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.free(rs, ps, conn);
		}
		return null;
	}
	//==========================HLR表的操作结束===========================

	//=============================================================//
	public static Module selectRemoteModule(int flag,String name) {
		Module module = new Module();
		String sql = "select id,name,belong,description,version,exe,log from myomc.module where name=?";
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = JdbcUtil.getMainConnection(flag);
			ps = conn.prepareStatement(sql);
			ps.setString(1, name);
			rs = ps.executeQuery();
			while(rs.next()){
				module.setId(rs.getInt("id"));
				module.setName(rs.getString("name"));
				module.setBelong(rs.getInt("belong"));
				module.setDescription(rs.getString("description"));
				module.setVersion(rs.getString("version"));
				module.setExe(rs.getString("exe"));
				module.setLog(rs.getString("log"));
			}
			return module;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.free(rs, ps, conn);
		}
		return null;
	}
	
	public static List<Entity> selectRemoteEntity(int flag,Integer moduleId){
		List<Entity> list = new ArrayList<Entity>();
		list.clear();
		String sql = "select id,name,instId,status,type,description,moduleId,cardId from myomc.entity where moduleId=?";
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = JdbcUtil.getMainConnection(flag);
			ps = conn.prepareStatement(sql);
			ps.setInt(1, moduleId);
			rs = ps.executeQuery();
			while(rs.next()){
				Entity entity = new Entity();
				Module module = new Module();
				Card card = new Card();
				entity.setId(rs.getInt("id"));
				entity.setName(rs.getString("name"));
				entity.setInstId(rs.getInt("instId"));
				entity.setStatus(rs.getInt("status"));
				entity.setType(rs.getInt("type"));
				entity.setDescription(rs.getString("description"));
				//
				module.setId(moduleId);
				entity.setModule(module);
				//
				card.setId(rs.getInt("cardId"));
				entity.setCard(card);
				
				list.add(entity);
			}
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.free(rs, ps, conn);
		}
		return null;
	}
	
	public static Integer selectDeviceStatus(){
		List<EquipInfo> list = new ArrayList<>();
		list.clear();
		String sql = "select equip_resource,equip_ipaddr,status from "+OmcServerContext.getInstance().getNmsDbName()+" where equip_resource=49 ";
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = JdbcUtil.getNmsConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next()){
				EquipInfo equipInfo = new EquipInfo();
				equipInfo.setEquip_resource(rs.getString("equip_resource"));
				equipInfo.setEquip_ipaddr(rs.getString("equip_ipaddr"));
				equipInfo.setStatus(rs.getString("status"));
				list.add(equipInfo);
			}
			System.err.println("List的数量值:"+list.size());
			return getEquipInfo(list);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.free(rs, ps, conn);
		}
		return null;
	}
	
	private static Integer getEquipInfo(List<EquipInfo> list){
		for (EquipInfo equipInfo : list) {
			System.err.println("从网管查询的设备信息:"+equipInfo.getEquip_ipaddr()+",从网管查询的设备状态:"+equipInfo.getStatus());
			String deviceStatus = equipInfo.getStatus();
			if(deviceStatus.equals("1")){
				if((equipInfo.getEquip_ipaddr().equals(OmcServerContext.getInstance().getMainDeviceIp()))){
					return 1;
				}
				if((equipInfo.getEquip_ipaddr().equals(OmcServerContext.getInstance().getBackUpDeviceIp()))){
					return 0;
				}
			}
		}
		return null;
	}
	
}
