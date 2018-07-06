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

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.sunkaisens.omc.service.core.OutService;
import com.sunkaisens.omc.util.ByteUtil;
import com.sunkaisens.omc.vo.core.Fxo;
import com.sunkaisens.omc.vo.core.Isup;
import com.sunkaisens.omc.vo.core.Sip;
/**
 * 
 * 
 * 
 * 
 * 
 *OutService接口实现类
 *
 */
@Service
public class OutServiceImpl implements OutService {
	
	@Override
	public Isup readConf(String isupDir,String sgw,boolean isTup) throws IOException{
		Isup isup=new Isup();
		Properties pIsup=new Properties();
		Properties pMtp3=new Properties();
		try(
			InputStream is=new FileInputStream(isupDir+File.separator+(isTup?"tup":"isup")+".config");
			InputStream is2=new FileInputStream(sgw+File.separator+"mtp3.config");
				){
			pIsup.load(is);
			pMtp3.load(is2);
		}
		isup.setPrefixStr(pIsup.getProperty("PrefixStr"));
		isup.setAfterRoutingStripStr(pIsup.getProperty("AfterRoutingStripStr"));
		isup.setMinNumOfDigits(pIsup.getProperty("MinNumOfDigits"));
		isup.settSAssignMode(pIsup.getProperty("TSAssignMode"));
		String signalLink=pMtp3.getProperty("SignalLink");
		if(StringUtils.isNotBlank(signalLink)){
			isup.setSlc(signalLink.split("\\|")[1].trim());
			isup.setTimeSlot(signalLink.split("\\|")[3].trim());
		}
		String trunk=pIsup.getProperty("Trunk");
		if(StringUtils.isNotBlank(signalLink)){
			isup.setCic(trunk.split("\\|")[1].trim());
			String[] remote=trunk.split("\\|")[4].trim().split(":");
			isup.setRemoteIp(remote[0].trim());
			isup.setRemotePort(remote[1].trim());
		}
		isup.setOpc(pIsup.getProperty("OPC"));
		isup.setDpc(pIsup.getProperty("DPC"));
		isup.setVoCoding(pIsup.getProperty("VoCoding"));
		isup.setStandard(pIsup.getProperty("Standard"));
		isup.setIsup_cpg(pIsup.getProperty("ISUP_CPG"));
		isup.setIsup_ai(pIsup.getProperty("ISUP_AI"));
		isup.setIsup_sio(pIsup.getProperty("ISUP_SIO"));
		String routing=pIsup.getProperty("Routing");
		String[] rs=routing.split("\\|");
		String[] blank=new String[9];
		if("0".endsWith(rs[0].trim())){
			isup.setRouting(blank);
		}else{
			System.arraycopy(rs, 1, blank, 0, rs.length-1);
			isup.setRouting(blank);
		}
		return isup;
	}
	
	@Override
	public Sip readSipConf(String sipDir) throws IOException{
		Sip sip=new Sip();
		Properties pSip=new Properties();
		try(	
			InputStream is=new FileInputStream(sipDir+File.separator+"sipgw.config");
//				InputStream is=new FileInputStream("D://sipgw.config");
				){
			pSip.load(is);
		}catch (Exception e) {
			e.printStackTrace();
		}
		sip.setHeartBeat(pSip.getProperty("HeartBeat"));
		sip.setLocalSipIP(pSip.getProperty("LocalSipIP"));
		sip.setLocalSipPort(pSip.getProperty("LocalSipPort"));
		sip.setRemoteSipIP(pSip.getProperty("RemoteSipIP"));
		sip.setRemoteSipPort(pSip.getProperty("RemoteSipPort"));
		sip.setRoutingNum(pSip.getProperty("RoutingNum"));
		sip.setVoiceEncoding(pSip.getProperty("VoiceEncoding"));
		return sip;
	}
	
	@Override
	public void updateSip(String sipDir, Sip sip) throws IOException {
		MyProperties pSip=new MyProperties(":");
		File is=new File(sipDir+File.separator+"sipgw.config");
//		File is=new File("D://sipgw.config");
		pSip.load(is);
		pSip.setProperty("HeartBeat", sip.getHeartBeat());
		pSip.setProperty("LocalSipIP", sip.getLocalSipIP());
		pSip.setProperty("LocalSipPort", sip.getLocalSipPort());
		pSip.setProperty("RemoteSipIP", sip.getRemoteSipIP());
		pSip.setProperty("RemoteSipPort", sip.getRemoteSipPort());
		pSip.setProperty("RoutingNum", sip.getRoutingNum());
		pSip.setProperty("VoiceEncoding", sip.getVoiceEncoding());
		
		pSip.store();
	}
	
	
	@Override
	public Fxo readFxoConf(String fxoDir) throws IOException{
		Fxo fxo=new Fxo();
		Properties pSip=new Properties();
		try(	
			InputStream is=new FileInputStream(fxoDir+File.separator+"fxo.config");
//				InputStream is=new FileInputStream("D://sipgw.config");
				){
			pSip.load(is);
		}catch (Exception e) {
			e.printStackTrace();
		}
		fxo.setBlindDialing(pSip.getProperty("BlindDialing"));
		fxo.setPrefixStr(pSip.getProperty("PrefixStr"));
		fxo.setRingDetectMode(pSip.getProperty("RingDetectMode"));
		fxo.setWaitFor2ndDialTone(pSip.getProperty("WaitFor2ndDialTone"));
		return fxo;
	}
	
	@Override
	public void updateFxo(String fxoDir, Fxo fxo) throws IOException {
		MyProperties pSip=new MyProperties(":");
		File is=new File(fxoDir+File.separator+"fxo.config");
//		File is=new File("D://sipgw.config");
		pSip.load(is);
		pSip.setProperty("BlindDialing", fxo.getBlindDialing());
		pSip.setProperty("PrefixStr", fxo.getPrefixStr());
		pSip.setProperty("RingDetectMode", fxo.getRingDetectMode());
		pSip.setProperty("WaitFor2ndDialTone", fxo.getWaitFor2ndDialTone());

		
		pSip.store();
	}

	@Override
	public void update(String isupDir, String sgwDir,Isup isup,boolean isTup) throws IOException {
		MyProperties pIsup=new MyProperties(":");
		MyProperties pMtp3=new MyProperties(":");
		MyProperties pIsup_m3ua=new MyProperties(":");
		MyProperties pM3ua=new MyProperties(":");
		File is=new File(isupDir+File.separator+(isTup?"tup":"isup")+".config");
		File is2=new File(sgwDir+File.separator+"mtp3.config");
		File is3=new File(isupDir+File.separator+(isTup?"tup":"isup")+"_m3ua.config");
		File is4=new File(sgwDir+File.separator+"m3ua.config");
		pIsup.load(is);
		pMtp3.load(is2);
		pIsup_m3ua.load(is3);
		pM3ua.load(is4);
		
		pIsup.setProperty("PrefixStr", isup.getPrefixStr());
		pIsup.setProperty("AfterRoutingStripStr", isup.getAfterRoutingStripStr());
		pIsup.setProperty("MinNumOfDigits", isup.getMinNumOfDigits());
		pIsup.setProperty("TSAssignMode", isup.gettSAssignMode());
		String signalLink=pMtp3.getProperty("SignalLink");
		String[] sl=signalLink.split("\\|");
		sl[1]=isup.getSlc();
		pMtp3.setProperty("SignalLink",StringUtils.join(sl, "|"));
		String trunk=pIsup.getProperty("Trunk");
		String[] tk=trunk.split("\\|");
		tk[1]=isup.getCic();
		pIsup.setProperty("Trunk",StringUtils.join(tk, "|"));
		//源信令点
		String opc=isup.getOpc();
		pIsup.setProperty("OPC",opc);
		pMtp3.setProperty("OPC",opc.replace("\\.", "-"));
		byte[] opcIps=ByteUtil.dotDivide(opc);
		String opcHexIp="0x"+ByteUtil.bytes2Hex(opcIps);
		String route=pIsup_m3ua.getProperty("Route");
		String[] rs=route.split("\\|");
		String[] str=rs[1].split(":");
		str[3]=opcHexIp;
		rs[1]=StringUtils.join(str, ":");
		pIsup_m3ua.setProperty("Route", StringUtils.join(rs, "|"));
		route=pM3ua.getProperty("Route");
		rs=route.split("\\|");
		str=rs[2].split(":");
		str[3]=opcHexIp;
		rs[2]=StringUtils.join(str, ":");
		pM3ua.setProperty("Route", StringUtils.join(rs, "|"));
		//目的信令点
		String dpc=isup.getDpc();
		pIsup.setProperty("DPC",dpc);
		String linkSet=pMtp3.getProperty("LinkSet");
		String[] ls=linkSet.split("\\|");
		ls[1]=dpc.replace("\\.","-");
		pMtp3.setProperty("LinkSet", StringUtils.join(ls, "|"));
		byte[] dpcIps=ByteUtil.dotDivide(dpc);
		String dpcHexIp="0x"+ByteUtil.bytes2Hex(dpcIps);
		route=pIsup_m3ua.getProperty("Route");
		rs=route.split("\\|");
		str=rs[2].split(":");
		str[3]=dpcHexIp;
		rs[2]=StringUtils.join(str, ":");
		pIsup_m3ua.setProperty("Route", StringUtils.join(rs, "|"));
		route=pM3ua.getProperty("Route");
		rs=route.split("\\|");
		str=rs[1].split(":");
		str[3]=dpcHexIp;
		rs[1]=StringUtils.join(str, ":");
		pM3ua.setProperty("Route", StringUtils.join(rs, "|"));
		
		sl[3]=isup.getTimeSlot();
		pMtp3.setProperty("SignalLink", StringUtils.join(sl, "|"));
		pIsup.setProperty("VoCoding", isup.getVoCoding());
		
		pIsup.setProperty("Standard", isup.getStandard());
		pMtp3.setProperty("Standard", isup.getStandard());
		pIsup_m3ua.setProperty("Standard", isup.getStandard());
		pM3ua.setProperty("Standard", isup.getStandard());
		if(isTup){
			pIsup.setProperty("TUP_CPG", isup.getIsup_cpg());
			pIsup.setProperty("TUP_AI", isup.getIsup_ai());
			pIsup.setProperty("TUP_SIO", isup.getIsup_sio());
		}else{
			pIsup.setProperty("ISUP_CPG", isup.getIsup_cpg());
			pIsup.setProperty("ISUP_AI", isup.getIsup_ai());
			pIsup.setProperty("ISUP_SIO", isup.getIsup_sio());
		}
		String[] routing=isup.getRouting();
		routing=removeBlank(routing);
		String routingLine=routing.length+"|"+StringUtils.join(routing, "|");
		pIsup.setProperty("Routing",routingLine);
		
		sl[4]=isup.getRemoteIp()+":"+isup.getRemotePort();
		pMtp3.setProperty("SignalLink", StringUtils.join(sl, "|"));
		tk[4]=sl[4];
		pIsup.setProperty("Trunk",  StringUtils.join(tk, "|"));
		
		pIsup.store();
		pMtp3.store();
		pIsup_m3ua.store();
		pM3ua.store();
	}
	
	private String[] removeBlank(String[] arr){
		List<String> list=new ArrayList<>();
		for(String a:arr){
			if(StringUtils.isNotBlank(a)){
				list.add(a);
			}
		}
		return list.toArray(new String[0]);
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
