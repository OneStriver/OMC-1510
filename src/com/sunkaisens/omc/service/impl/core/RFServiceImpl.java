package com.sunkaisens.omc.service.impl.core;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Properties;

import javax.annotation.Resource;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.sunkaisens.omc.mapper.core.RFMapper;
import com.sunkaisens.omc.service.core.RFService;
import com.sunkaisens.omc.vo.core.Bsc;
import com.sunkaisens.omc.po.core.Bts;
import com.sunkaisens.omc.vo.core.PageBean;

@Service
public class RFServiceImpl implements RFService{
	@Resource
	private RFMapper rfMapper;
	

	@Override
	public PageBean getPageBean(Integer page, Integer pageSize,String bscDir) {
		PageBean p = null;
		
		List<Bts> btsList=new ArrayList<>();
		Properties pBts=new Properties();
		try(	
			InputStream is=new FileInputStream(bscDir+File.separator+"bts.config");
//				InputStream is=new FileInputStream("D://bts.config");
					){
				pBts.load(is);
			}catch (Exception e) {
				e.printStackTrace();
			}
		String[] carrierIds = pBts.getProperty("CarrierID").split("\\|");
		int len = carrierIds.length;
		rfMapper.deleteAll();
		for(int i=0;i<len;i++){
			Bts bts=new Bts();
			bts.setCarrierId(carrierIds[i]);
			bts.setCarrierType(pBts.getProperty("CarrierType").split("\\|")[i]);
			bts.setCellId(pBts.getProperty("CellID").split("\\|")[i]);
			bts.setCarrierFreq(pBts.getProperty("CarrierFreq").split("\\|")[i]);
			bts.setPn(pBts.getProperty("PN").split("\\|")[i]);
			bts.setBtsId(pBts.getProperty("BtsID").split("\\|")[i]);
			bts.setZoneId(pBts.getProperty("ZoneID").split("\\|")[i]);
			bts.setBandClass(pBts.getProperty("BandClass").split("\\|")[i]);
			bts.setTxGain(pBts.getProperty("TxGain").split("\\|")[i]);
			bts.setPilotChGain(pBts.getProperty("PilotChGain").split("\\|")[i]);
			bts.setSyncChGain(pBts.getProperty("SyncChGain").split("\\|")[i]);
			bts.setPagingChGain(pBts.getProperty("PagingChGain").split("\\|")[i]);
			bts.setNumOfPch(pBts.getProperty("NumOfPCH").split("\\|")[i]);			
			bts.setNumOfQpch(pBts.getProperty("NumOfQPCH").split("\\|")[i]);
			bts.setQpchRate(pBts.getProperty("QPCHRate").split("\\|")[i]);
			bts.setQpchCci(pBts.getProperty("QPCHCCI").split("\\|")[i]);
			bts.setQpchPwrPage(pBts.getProperty("QPCHPwrPage").split("\\|")[i]);
			bts.setQpchPwrCfg(pBts.getProperty("QPCHPwrCfg").split("\\|")[i]);
			bts.setNumAchPerpch(pBts.getProperty("NumAchPerPch").split("\\|")[i]);
			rfMapper.insert(bts);		
			btsList.add(bts);
		}
		
		p =new PageBean(page,pageSize,btsList,len);
		return p;
	}

	@Override
	public Bsc readConf(String bscDir) {
		Bsc bsc=new Bsc();
		Properties pBsc=new Properties();
		Properties pPcf=new Properties();
		Properties pBts=new Properties();
		try(	
			InputStream is=new FileInputStream(bscDir+File.separator+"bsc.config");
			InputStream is2=new FileInputStream(bscDir+File.separator+"pcf.config");
			InputStream is3=new FileInputStream(bscDir+File.separator+"bts.config");
//				InputStream is=new FileInputStream("D://bsc.config");
//				InputStream is2=new FileInputStream("D://pcf.config");
//				InputStream is3=new FileInputStream("D://bts.config");
				){
			pBsc.load(is);
			pPcf.load(is2);
			pBts.load(is3);
		}catch (Exception e) {
			e.printStackTrace();
		}
		bsc.setIp(pBsc.getProperty("RemoteVcsIP"));
		bsc.setMscid(pBsc.getProperty("MSCID"));
		bsc.setSid(pBsc.getProperty("SID"));
		bsc.setNid(pBsc.getProperty("NID"));
		bsc.setPacketZoneId(pBsc.getProperty("PacketZoneID"));
		bsc.setLocalMcc(pBsc.getProperty("LocalMCC"));
		bsc.setLocalImsi_11_12(pBsc.getProperty("LocalIMSI_11_12"));
		bsc.setDoSectorId104(pBts.getProperty("DoSectorID104(Hex)"));
		bsc.setDoColorCode(pBts.getProperty("DoColorCode"));
		bsc.setAchMaxCapSize(pBts.getProperty("AchMaxCapSize"));
		bsc.setMaxSci(pBts.getProperty("MaxSCI"));
		bsc.setDoSectorId24(pBts.getProperty("DoSectorID24"));
		return bsc;
	}

	@Override
	public void updateCheck(String bscDir, Bsc bsc) throws IOException{
		
		MyProperties pBsc=new MyProperties(":");
		MyProperties pPcf=new MyProperties(":");
		MyProperties pBts=new MyProperties(":");
		
		File is=new File(bscDir+File.separator+"bsc.config");
		File is2=new File(bscDir+File.separator+"pcf.config");
		File is3=new File(bscDir+File.separator+"bts.config");
//		File is=new File("D://bsc.config");
//		File is2=new File("D://pcf.config");
//		File is3=new File("D://bts.config");
		pBsc.load(is);
		pPcf.load(is2);
		pBts.load(is3);
		
		pBsc.setProperty("RemoteVcsIP", bsc.getIp());
		pBsc.setProperty("RemotePdsnIP", bsc.getIp());
		pBsc.setProperty("RemoteMcIP", bsc.getIp());
		pPcf.setProperty("RemoteA11IP", bsc.getIp());
		pPcf.setProperty("RemoteA10IP", bsc.getIp());
		
		pBsc.setProperty("MSCID", bsc.getMscid());
		pBsc.setProperty("SID", bsc.getSid());
		pBsc.setProperty("NID", bsc.getNid());
		pBsc.setProperty("PacketZoneID", bsc.getPacketZoneId());
		pBsc.setProperty("LocalMCC", bsc.getLocalMcc());
		pBsc.setProperty("LocalIMSI_11_12", bsc.getLocalImsi_11_12());
		pBts.setProperty("DoSectorID104(Hex)", bsc.getDoSectorId104());
		pBts.setProperty("DoColorCode", bsc.getDoColorCode());
		pBts.setProperty("AchMaxCapSize", bsc.getAchMaxCapSize());
		pBts.setProperty("MaxSCI", bsc.getMaxSci());
		pBts.setProperty("DoSectorID24", bsc.getDoSectorId24());
		pBsc.store();
		pPcf.store();
		pBts.store();
	}
	
	@Override
	public void delete(String carrierID,String bscDir) throws IOException{
		rfMapper.delete(carrierID);		
		this.spell(bscDir);
	}
	
	@Override
	public void update(Bts bts,String bscDir) throws IOException{
		rfMapper.update(bts);
		this.spell(bscDir);
	}
	
	@Override
	public void insert(Bts bts,String bscDir) throws IOException{
		rfMapper.insert(bts);
		this.spell(bscDir);
	}
	
	private void spell(String bscDir) throws IOException{
		List<Bts> btsList = rfMapper.selectAll();
		MyProperties pBts=new MyProperties(":");	
		File is=new File(bscDir+File.separator+"bts.config");
//		File is=new File("D://bts.config");
		pBts.load(is);
		StringBuffer carrierId = new StringBuffer();
		StringBuffer carrierType = new StringBuffer();
		StringBuffer carrierFreq = new StringBuffer();
		StringBuffer cellId = new StringBuffer();
		StringBuffer pn = new StringBuffer();
		StringBuffer btsId = new StringBuffer();
		StringBuffer zoneId = new StringBuffer();
		StringBuffer bandClass = new StringBuffer();
		StringBuffer txGain = new StringBuffer();
		StringBuffer pilotChGain = new StringBuffer();
		StringBuffer syncChGain = new StringBuffer();
		StringBuffer pagingChGain = new StringBuffer();
		StringBuffer numOfPCH = new StringBuffer();
		StringBuffer numOfQPCH = new StringBuffer();
		StringBuffer qPCHRate = new StringBuffer();
		StringBuffer qPCHCCI = new StringBuffer();
		StringBuffer qPCHPwrPage = new StringBuffer();
		StringBuffer qPCHPwrCfg = new StringBuffer();
		StringBuffer numAchPerPch = new StringBuffer();
		if(btsList!=null&&btsList.size()>0){
			for(Bts bts:btsList){
				carrierId.append(bts.getCarrierId()+"|");
				carrierType.append(bts.getCarrierType()+"|");
				carrierFreq.append(bts.getCarrierFreq()+"|");
				cellId.append(bts.getCellId()+"|");
				pn.append(bts.getPn()+"|");
				btsId.append(bts.getBtsId()+"|");
				zoneId.append(bts.getZoneId()+"|");
				bandClass.append(bts.getBandClass()+"|");
				txGain.append(bts.getTxGain()+"|");
				pilotChGain.append(bts.getPilotChGain()+"|");
				syncChGain.append(bts.getSyncChGain()+"|");
				pagingChGain.append(bts.getPagingChGain()+"|");
				numOfPCH.append(bts.getNumOfPch()+"|");
				numOfQPCH.append(bts.getNumOfQpch()+"|");
				qPCHRate.append(bts.getQpchRate()+"|");
				qPCHCCI.append(bts.getQpchCci()+"|");
				qPCHPwrPage.append(bts.getQpchPwrPage()+"|");
				qPCHPwrCfg.append(bts.getQpchPwrCfg()+"|");
				numAchPerPch.append(bts.getNumAchPerpch()+"|");
			}
			carrierId.deleteCharAt(carrierId.length()-1);
			carrierType.deleteCharAt(carrierType.length()-1);
			carrierFreq.deleteCharAt(carrierFreq.length()-1);
			cellId.deleteCharAt(cellId.length()-1);
			pn.deleteCharAt(pn.length()-1);
			btsId.deleteCharAt(btsId.length()-1);
			zoneId.deleteCharAt(zoneId.length()-1);
			bandClass.deleteCharAt(bandClass.length()-1);
			txGain.deleteCharAt(txGain.length()-1);
			pilotChGain.deleteCharAt(pilotChGain.length()-1);
			syncChGain.deleteCharAt(syncChGain.length()-1);
			pagingChGain.deleteCharAt(pagingChGain.length()-1);
			numOfPCH.deleteCharAt(numOfPCH.length()-1);
			numOfQPCH.deleteCharAt(numOfQPCH.length()-1);
			qPCHRate.deleteCharAt(qPCHRate.length()-1);
			qPCHCCI.deleteCharAt(qPCHCCI.length()-1);
			qPCHPwrPage.deleteCharAt(qPCHPwrPage.length()-1);
			qPCHPwrCfg.deleteCharAt(qPCHPwrCfg.length()-1);
			numAchPerPch.deleteCharAt(numAchPerPch.length()-1);
		}
		pBts.setProperty("CarrierID", carrierId.toString());
		pBts.setProperty("CarrierType", carrierType.toString());
		pBts.setProperty("CarrierFreq", carrierFreq.toString());
		pBts.setProperty("CellID", cellId.toString());
		pBts.setProperty("PN", pn.toString());
		pBts.setProperty("BtsID", btsId.toString());
		pBts.setProperty("ZoneID", zoneId.toString());
		pBts.setProperty("BandClass", bandClass.toString());
		pBts.setProperty("TxGain", txGain.toString());
		pBts.setProperty("PilotChGain", pilotChGain.toString());
		pBts.setProperty("SyncChGain", syncChGain.toString());
		pBts.setProperty("PagingChGain", pagingChGain.toString());
		pBts.setProperty("NumOfPCH", numOfPCH.toString());
		pBts.setProperty("NumOfQPCH", numOfQPCH.toString());
		pBts.setProperty("QPCHRate", qPCHRate.toString());
		pBts.setProperty("QPCHCCI", qPCHCCI.toString());
		pBts.setProperty("QPCHPwrPage", qPCHPwrPage.toString());
		pBts.setProperty("QPCHPwrCfg", qPCHPwrCfg.toString());
		pBts.setProperty("NumAchPerPch", numAchPerPch.toString());
		pBts.store();
	}
	
	
	class MyProperties{
		private final LinkedHashMap<String,String> kvs=new LinkedHashMap<>();
		private File file;
		private String kvSpli=":";
		public MyProperties(String kvSpli){
			this.kvSpli=kvSpli;
		}
		public void load(File file) throws IOException{
			this.file=file;
			List<String> lines=FileUtils.readLines(file);
			for(String line:lines){
				if(StringUtils.isNotBlank(line)&&!line.trim().startsWith("#")){
					String[] kv=line.split(kvSpli,2);
					if(kv.length==2)
						kvs.put(kv[0], kv[1]);
					else
						kvs.put(kv[0], "");
				}
			}
		}
		public void store() throws IOException{
			try(
					Writer writer=new OutputStreamWriter(new BufferedOutputStream(new FileOutputStream(file)));
					){
				for(Entry<String,String> entry:kvs.entrySet()){
					writer.write(entry.getKey()+kvSpli+entry.getValue()+"\n");
				}
			}
		}
		
		public void setProperty(String key,String value){
			kvs.put(key, value);
		}
		
		public String getProperty(String key){
			return kvs.get(key);
		}
	}


	

}
