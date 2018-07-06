package com.sunkaisens.omc.service.impl.core;

import java.io.InputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xbill.DNS.DClass;
import org.xbill.DNS.Name;
import org.xbill.DNS.Rcode;
import org.xbill.DNS.Record;
import org.xbill.DNS.Resolver;
import org.xbill.DNS.SimpleResolver;
import org.xbill.DNS.TSIG;
import org.xbill.DNS.Type;
import org.xbill.DNS.Update;
import org.xbill.DNS.ZoneTransferIn;

import com.sunkaisens.omc.exception.CustomException;
import com.sunkaisens.omc.service.core.DnsServerService;
import com.sunkaisens.omc.vo.core.DnsAxfr;
import com.sunkaisens.omc.vo.core.DnsDomain;
/**
 * 
 * 
 * 
 * 
 * 
 * DnsServerService接口实现类
 *
 */
@Service
public class DnsServerServiceImpl implements DnsServerService {

	@Override
	public List<DnsDomain> list(String ip,int port) throws Exception{
		CloseableHttpClient client=HttpClients.createDefault();
		HttpGet get=new HttpGet(URI.create("http://"+ip+":"+port));
		CloseableHttpResponse response=client.execute(get);
		StatusLine sl=response.getStatusLine();
		if(sl.getStatusCode()==200){
			HttpEntity entity=response.getEntity();
			InputStream is=entity.getContent();
			List<DnsDomain> list=parseBindStatistics(is);
			is.close();
			EntityUtils.consume(entity);
			client.close();
			return list;
		}else{
			throw new CustomException(sl.getReasonPhrase());
		}
	}
	
	private List<DnsDomain> parseBindStatistics(InputStream is) throws Exception {
		List<DnsDomain> list = new ArrayList<>();
		Document doc = DocumentBuilderFactory.newInstance()
				.newDocumentBuilder().parse(is);
		XPathFactory factory = XPathFactory.newInstance();

		NodeList viewList = (NodeList) factory.newXPath().evaluate(
				"/isc/bind/statistics/views/view", doc, XPathConstants.NODESET);
		for (int i = 0; i < viewList.getLength(); i++) {
			Node view = viewList.item(i);
//			Node viewName = (Node) factory.newXPath().evaluate("child::name",
//					view, XPathConstants.NODE);
			NodeList zoneList = (NodeList) factory.newXPath().evaluate(
					"descendant::zone", view, XPathConstants.NODESET);
			for (int j = 0; j < zoneList.getLength(); j++) {
				Node zone = zoneList.item(j);
				Node zoneName = (Node) factory.newXPath().evaluate(
						"child::name", zone, XPathConstants.NODE);
				Node zoneClass = (Node) factory.newXPath().evaluate(
						"child::rdataclass", zone, XPathConstants.NODE);
				Node zoneSerial = (Node) factory.newXPath().evaluate(
						"child::serial", zone, XPathConstants.NODE);
				DnsDomain domain=new DnsDomain( zoneName.getTextContent().split("/")[0], 
						zoneClass.getTextContent(), zoneSerial.getTextContent());
				list.add(domain);
			}
		}
		return list;
	}

	@Override
	public List<DnsAxfr> listAxfr(String domain, String ip) throws Exception {
		List<DnsAxfr> as=new ArrayList<>();
		ZoneTransferIn xfr = ZoneTransferIn.newAXFR(new Name(domain), ip,null);
		@SuppressWarnings("unchecked")
		List<Record> records = xfr.run();
		for(Record r:records){
			DnsAxfr axfr=new DnsAxfr(r.getName().toString(), r.getTTL()+"", DClass.string(r.getDClass()), Type.string(r.getType()), r.rdataToString());
			axfr.setZone(domain);
			as.add(axfr);
		}
		return as;
	}
	
	@Override
	public void doUpdate(String hostIP, Name zone, String sharedKeyName,
			String sharedKey, Name name, int type, long ttl, String oldRecord,
			String record) throws Exception {
		Update update = new Update(zone);
		update.delete(name, type, oldRecord);
		update.add(name, type, ttl, record);
		send(hostIP, sharedKeyName, sharedKey, update);
	}
	
	@Override
	public void doDelete(String hostIP, Name zone, String sharedKeyName,
			String sharedKey, Name name, int type, String oldRecord)
			throws Exception {
		Update update = new Update(zone);
		update.delete(name, type, oldRecord);
		send(hostIP, sharedKeyName, sharedKey, update);
	}
	
	@Override
	public void doAdd(String hostIP, Name zone, String sharedKeyName,
			String sharedKey, Name name, int type, long ttl, String record)
			throws Exception {
		Update update = new Update(zone);
		update.add(name, type, ttl, record);
		send(hostIP, sharedKeyName, sharedKey, update);
	}
	
	private void send(String hostIP, String sharedKeyName,
			String sharedKey, Update update) throws Exception {
		Resolver resolver = new SimpleResolver(hostIP);
		if (null != sharedKeyName && null != sharedKey)
			resolver.setTSIGKey(new TSIG(sharedKeyName, sharedKey));
		resolver.setTCP(true);
		int rcode = resolver.send(update).getHeader().getRcode();
		if (Rcode.NOERROR != rcode)
			throw new Exception(Rcode.string(rcode));
	}

	@Override
	public List<DnsAxfr> listAllAxfr(String host,int port) throws Exception {
		List<DnsAxfr> all=new ArrayList<>();
		List<DnsDomain> domains=list(host, port);
		for(DnsDomain dd:domains){
			List<DnsAxfr> axfrs=listAxfr(dd.getName(), host);
			all.addAll(axfrs);
		}
		return all;
	}
}
