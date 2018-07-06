package com.sunkaisens.omc.service.impl.core;

import java.net.Inet6Address;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import org.springframework.stereotype.Service;

import com.sunkaisens.omc.service.core.DeviceService;
import com.sunkaisens.omc.vo.core.LocalEth;
/**
 * 
 * 
 * 
 * 
 * 
 *DeviceService接口实现类
 *
 */
@Service
public class DeviceServiceImpl implements DeviceService {
	@Override
	public List<LocalEth> list() throws SocketException{
		Enumeration<NetworkInterface> nis=NetworkInterface.getNetworkInterfaces();
		return getAllEth(nis);
	}
	
	private List<LocalEth> getAllEth(Enumeration<NetworkInterface> nis)throws SocketException{
		List<LocalEth> list=new ArrayList<LocalEth>();
		while(nis.hasMoreElements()){
			NetworkInterface ni=nis.nextElement();
			Boolean up=ni.isUp();
			Boolean virtual=ni.isVirtual();
			String hardAddr=ni.getHardwareAddress()==null?null:new String(ni.getHardwareAddress());
			Integer mtu=ni.getMTU();
			String name=ni.getName();
			List<InterfaceAddress> ias=ni.getInterfaceAddresses();
			if(!up){
				LocalEth eth=new LocalEth(name, null, null, null, null, hardAddr, mtu, up, virtual);
				list.add(eth);
			}else{
				for(InterfaceAddress ia:ias){
					if(ia.getAddress() instanceof Inet6Address) continue;
					String mask=ia.getNetworkPrefixLength()+"";
					String ip=ia.getAddress().getHostAddress();
					String broadcast=ia.getBroadcast()==null?null:ia.getBroadcast().getHostAddress();
					LocalEth eth=new LocalEth(name, ip, mask, null, broadcast, hardAddr, mtu, up, virtual);
					list.add(eth);
				}
			}
		}
		return list;
	}
	
	public static void main(String[] args) throws Exception {
		new DeviceServiceImpl().list();
	}
}
