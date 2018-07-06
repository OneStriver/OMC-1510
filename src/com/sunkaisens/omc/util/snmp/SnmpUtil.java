package com.sunkaisens.omc.util.snmp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.TimeoutException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.snmp4j.CommunityTarget;
import org.snmp4j.PDU;
import org.snmp4j.Snmp;
import org.snmp4j.TransportMapping;
import org.snmp4j.asn1.BER;
import org.snmp4j.event.ResponseEvent;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.smi.AbstractVariable;
import org.snmp4j.smi.Address;
import org.snmp4j.smi.Counter32;
import org.snmp4j.smi.Counter64;
import org.snmp4j.smi.Gauge32;
import org.snmp4j.smi.GenericAddress;
import org.snmp4j.smi.Integer32;
import org.snmp4j.smi.IpAddress;
import org.snmp4j.smi.Null;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.OctetString;
import org.snmp4j.smi.Opaque;
import org.snmp4j.smi.SMIConstants;
import org.snmp4j.smi.TimeTicks;
import org.snmp4j.smi.Variable;
import org.snmp4j.smi.VariableBinding;
import org.snmp4j.transport.DefaultUdpTransportMapping;
/**
 * 
 * 
 * 
 * SnmpUtil 实体类
 *
 */
public class SnmpUtil {

	private static final int DEFAULT_VERSION = SnmpConstants.version2c;
	private static final String DEFAULT_PROTOCOL = "udp";
	private static final int DEFAULT_PORT = 1801;//161;
	private static final long DEFAULT_TIMEOUT = 1 * 1000L;
	private static final int DEFAULT_RETRY = 2;
	private static final String DEFAULT_COMMUNITY = "public";
	private static final Logger log = LoggerFactory.getLogger(SnmpUtil.class);
	private static final Object[][] SYNTAX_NAME_MAPPING = {
	  { "Integer32", (int) BER.INTEGER32},
	  { "Integer", (int) BER.INTEGER32},
	  { "INTEGER", (int) BER.INTEGER32},
	  { "BIT STRING", (int) BER.BITSTRING},
	  { "OCTET STRING", (int) BER.OCTETSTRING},
	  { "OBJECT IDENTIFIER", (int) BER.OID},
	  { "TimeTicks", (int) BER.TIMETICKS},
	  { "Counter", (int) BER.COUNTER},
	  { "Counter64", (int) BER.COUNTER64},
	  { "EndOfMibView", BER.ENDOFMIBVIEW},
	  { "Gauge", (int) BER.GAUGE32},
	  { "Gauge32", (int) BER.GAUGE32},
	  { "Unsigned32", (int) BER.GAUGE32},
	  { "IpAddress", (int) BER.IPADDRESS},
	  { "NoSuchInstance", BER.NOSUCHINSTANCE},
	  { "NoSuchObject", BER.NOSUCHOBJECT},
	  { "Null", (int) BER.NULL},
	  { "Opaque", (int) BER.OPAQUE}
	};
	
	static CommunityTarget createCommunityTarget(String ip) {
		return createCommunityTarget(ip,false);
	}
	
	static CommunityTarget createCommunityTarget(String ip,boolean isTrap) {
		Address address=null;
		if(isTrap)
			address = GenericAddress.parse(DEFAULT_PROTOCOL + ":" + ip+ "/" + 162);
		else
			address = GenericAddress.parse(DEFAULT_PROTOCOL + ":" + ip+ "/" + DEFAULT_PORT);
		CommunityTarget target = new CommunityTarget();
		target.setCommunity(new OctetString(DEFAULT_COMMUNITY));
		target.setAddress(address);
		target.setVersion(DEFAULT_VERSION);
		target.setTimeout(DEFAULT_TIMEOUT);
		target.setRetries(DEFAULT_RETRY);
		return target;
	}

   static Snmp createSnmp() throws IOException {
		TransportMapping<?> transport = new DefaultUdpTransportMapping();
		Snmp snmp = new Snmp(transport);
		transport.listen();
		return snmp;
	}

   
   public static boolean sendTrap(String ip,String[] oids, String[] syntaxs,String[] values) throws IOException{
	   if (oids.length != values.length || oids.length != syntaxs.length)
			throw new RuntimeException("OID数目与values或syntaxs数目不一致");
		PDU pdu = new PDU();
		pdu.setType(PDU.TRAP);
		for (int i = 0; i < oids.length; i++) {
			Variable variable = generateVariable(values[i], syntaxs[i]);
			pdu.add(new VariableBinding(new OID(oids[i]), variable));
		}
		CommunityTarget target = createCommunityTarget(ip,true);
		ResponseEvent event = createSnmp().send(pdu, target);
		if (event.getResponse() != null) {
			boolean result=event.getResponse().getErrorStatus() == PDU.noError;
			if(!result)
				log.error("ErrorIndex:"+event.getResponse().getErrorIndex()+">"+event.getResponse().getErrorStatusText());
			return result;
		}else{
//			log.error("请求超时！", event.getError());
		}
		return false;
   }
   
   
   public static VariableBinding getRequest(String ip, String oid)
			throws IOException ,TimeoutException{
	   List<VariableBinding> vbs= getRequest(ip,new String[]{oid});
	   if(vbs==null||vbs.size()==0)
		   return null;
	   else
		   return vbs.get(0);
   }
   
   
	public static List<VariableBinding> getRequest(String ip, String[] oids)
			throws IOException ,TimeoutException{
		PDU pdu = new PDU();
		pdu.setType(PDU.GET);
		for (String oid : oids) {
			pdu.addOID(new VariableBinding(new OID(oid)));
		}
		CommunityTarget target = createCommunityTarget(ip/* , community */);
		log.debug("发送Snmp GetRequest消息：");
		log.debug(pdu.toString());
		ResponseEvent event = createSnmp().send(pdu, target);
		if (event.getResponse() != null) {
			PDU resPdu = event.getResponse();
			if(resPdu.getErrorStatus()!=0){
				log.error("索引"+resPdu.getErrorIndex()+":"+resPdu.getErrorStatus());
				throw new IOException(event.getError());
			}
			Vector<? extends VariableBinding> varbinds = resPdu.getVariableBindings();
			log.debug("接收Snmp GetRequestResponse消息：");
			log.debug(varbinds.toString());
			if(varbinds.size()>0)
				return new ArrayList<>(varbinds);
			else
				return null;
		}else{
			log.error("请求超时！",event.getError());
			throw new TimeoutException("请求超时！");
		}
	}

	public static boolean setRequest(String ip, String oid, String syntax,
			String value) throws Exception ,TimeoutException{
		return setRequest(ip,new String[]{oid},new String[]{syntax},new String[]{value});
		
	}
	
	
	public static boolean setRequest(String ip, String[] oids, String[] syntaxs,
			String[] values)  throws IOException,TimeoutException{
		if (oids.length != values.length || oids.length != syntaxs.length)
			throw new RuntimeException("OID数目与values或syntaxs数目不一致");
		PDU pdu = new PDU();
		pdu.setType(PDU.SET);
		for (int i = 0; i < oids.length; i++) {
			Variable variable = generateVariable(values[i], syntaxs[i]);
			pdu.add(new VariableBinding(new OID(oids[i]), variable));
		}
		CommunityTarget target = createCommunityTarget(ip);
		log.debug("发送Snmp SetRequest消息：");
		log.debug(pdu.toString());
		ResponseEvent event = createSnmp().send(pdu, target);
		if (event.getResponse() != null) {
			boolean result=event.getResponse().getErrorStatus() == PDU.noError;
			if(!result){
				PDU responsePdu=event.getResponse();
				log.debug("接收Snmp SetRequest消息：");
				log.debug(responsePdu.toString());
				log.error("ErrorIndex:"+responsePdu.getErrorIndex()+">"+responsePdu.getErrorStatusText());
				throw new IOException(event.getError());
			}
			return result;
		}else{
			log.error("请求超时!",event.getError());
			throw new TimeoutException("请求超时!");
		}
	}
	
	public static int getSyntaxFromString(String syntaxString) {
	    for (Object[] aSYNTAX_NAME_MAPPING : SYNTAX_NAME_MAPPING) {
	      if (aSYNTAX_NAME_MAPPING[0].equals(syntaxString)) {
	        return (Integer) aSYNTAX_NAME_MAPPING[1];
	      }
	    }
	    return BER.NULL;
	}
	
	private static Variable generateVariable(String value, String syntax) {
		return generateVariable(value,getSyntaxFromString(syntax));
	}

	private static Variable generateVariable(String value, int syntax) {
		switch (syntax) {
		case SMIConstants.SYNTAX_OBJECT_IDENTIFIER: 
			return new OID();
		case SMIConstants.SYNTAX_INTEGER:
			return new Integer32(new Integer(value));
		case SMIConstants.SYNTAX_OCTET_STRING:
			return new OctetString(value);
		case SMIConstants.SYNTAX_GAUGE32:
			return new Gauge32(new Long(value));
		case SMIConstants.SYNTAX_COUNTER32:
			return new Counter32(new Long(value));
		case SMIConstants.SYNTAX_COUNTER64:
			return new Counter64(new Long(value));
		case SMIConstants.SYNTAX_NULL:
			return new Null();
		case SMIConstants.SYNTAX_TIMETICKS:
			TimeTicks timeTicks=new TimeTicks();
			timeTicks.setValue(value);
			return timeTicks;
		case SMIConstants.EXCEPTION_END_OF_MIB_VIEW:
			return new Null(SMIConstants.EXCEPTION_END_OF_MIB_VIEW);
		case SMIConstants.EXCEPTION_NO_SUCH_INSTANCE:
			return new Null(SMIConstants.EXCEPTION_NO_SUCH_INSTANCE);
		case SMIConstants.EXCEPTION_NO_SUCH_OBJECT:
			return new Null(SMIConstants.EXCEPTION_NO_SUCH_OBJECT);
		case SMIConstants.SYNTAX_OPAQUE:
			return new Opaque(value.getBytes());
		case SMIConstants.SYNTAX_IPADDRESS:
			return new IpAddress(value);
		default:
			throw new IllegalArgumentException("不支持的 variable syntax: "+ syntax);
		}
	}

	public static VariableBinding getNextRequest(String ip, String oid)
			throws IOException,TimeoutException {
		PDU pdu = new PDU();
		pdu.setType(PDU.GETNEXT);
		pdu.addOID(new VariableBinding(new OID(oid)));
		CommunityTarget target = createCommunityTarget(ip/* , community */);
		log.debug("发送Snmp GetNextRequest消息：");
		log.debug(pdu.toString());
		ResponseEvent event = createSnmp().send(pdu, target);
		if (event.getResponse() != null) {
			PDU resPdu = event.getResponse();
			if(resPdu.getErrorStatus()==PDU.noError){
				Vector<? extends VariableBinding> vbs=resPdu.getVariableBindings();
				log.debug("接收Snmp GetNextRequestResponse消息：");
				log.debug(vbs.toString());
				if(vbs.size()>0)
					return vbs.get(0);
				else
					return null;
			}else{
				log.error("ErrorIndex:"+event.getResponse().getErrorIndex()+">"+event.getResponse().getErrorStatusText());
				throw new IOException(event.getError());
			}
		}else{
			log.error("请求超时！",event.getError());
			throw new TimeoutException("请求超时");
		}
	}
	
	public static List<VariableBinding> getNextRequest(String ip, String[] oids)
			throws IOException,TimeoutException{
		PDU pdu = new PDU();
		pdu.setType(PDU.GETNEXT);
		for (String oid : oids) {
			pdu.addOID(new VariableBinding(new OID(oid)));
		}
		CommunityTarget target = createCommunityTarget(ip/* , community */);
		log.debug("发送Snmp GetNextRequest消息：");
		log.debug(pdu.toString());
		ResponseEvent event = createSnmp().send(pdu, target);
		if (event.getResponse() != null) {
			PDU resPdu = event.getResponse();
			if(resPdu.getErrorStatus()!=PDU.noError){
				log.error("索引"+resPdu.getErrorIndex()+":"+resPdu.getErrorStatusText());
				throw new IOException(event.getError());
			}else{
				Vector<? extends VariableBinding> varbinds = resPdu.getVariableBindings();
				log.debug("接收Snmp GetNextRequestResponse消息：");
				log.debug(varbinds.toString());
				if (varbinds.size() > 0)
					return new ArrayList<>(varbinds);
				else
					return null;
			}
		}else{
			log.error("请求超时！",event.getError());
			throw new TimeoutException("请求超时！");
		}
	}

	public static List<List<VariableBinding>> getSnmpTable(String ip,
			String[] oids,Integer limit) throws IOException,TimeoutException {
		List<VariableBinding> row = getNextRequest(ip, oids);
		List<List<VariableBinding>> lines = new ArrayList<>();
		while (row != null) {
			if (row.get(0).getVariable().isException()
					|| row.get(0).getOid().size() >= new OID(oids[0]).size() + 2
					|| !row.get(0).getOid().toDottedString()
							.startsWith(oids[0]))
				return lines;
			lines.add(row);
			if(limit!=null&&lines.size()==limit) return lines;
			List<String> rowOids = new ArrayList<String>();
			for (VariableBinding varbind : row) {
				rowOids.add(varbind.getOid().toDottedString());
			}
			row = getNextRequest(ip,rowOids.toArray(new String[rowOids.size()]));
		}
		return lines;
	}
	
	public static List<List<VariableBinding>> getSnmpTable(String ip,
			String[] oids) throws IOException,TimeoutException {
		return getSnmpTable(ip,oids,null);
	}
	
	

	public static List<VariableBinding> getSnmpTable(String ip, String oid)
			throws IOException,TimeoutException {
		List<VariableBinding> cols = new ArrayList<VariableBinding>();
		for (List<VariableBinding> v : getSnmpTable(ip, new String[] { oid })) {
			cols.add(v.get(0));
		}
		return cols;
	}

	public static int getLastIndex(String ip, String columnOid) throws IOException,TimeoutException {
		List<VariableBinding> list = getSnmpTable(ip, columnOid);
		if (list == null || list.isEmpty())
			return -1;
		VariableBinding v = list.get(list.size() - 1);
		return v.getOid().getValue()[v.getOid().size() - 1];
	}
	
	public static Integer[] getTableIndexs(String ip, String tableOid) throws IOException,TimeoutException {
		List<Integer> list=new ArrayList<>();
		String oid = tableOid;
		String entryOid=tableOid+".1";
		VariableBinding vb = null;
		boolean isFirst=true;
		String columnOid=null;
		while (true) {
			vb=getNextRequest(ip, oid);
			if (null == vb) break;
			oid=vb.getOid().toDottedString();
			if(isFirst){
				columnOid=oid.substring(0, oid.lastIndexOf("."));
				isFirst=false;
			}
			if (!vb.getOid().toDottedString().startsWith(entryOid)) break;
			if(vb.getOid().getValue().length!=entryOid.split("\\.").length+2) break;
			if(!vb.getOid().toDottedString().startsWith(columnOid)) break;
			list.add(vb.getOid().get(vb.getOid().size()-1));
		}
		return list.toArray(new Integer[0]);
	}
	
	
	public static int snmpTableRowCount(String ip,String tableOid) throws IOException,TimeoutException{
		int count = 0;
		String oid = tableOid;
		String entryOid=tableOid+".1";
		VariableBinding vb = null;
		boolean isFirst=true;
		String columnOid=null;
		while (true) {
			vb=getNextRequest(ip, oid);
			if (null == vb) break;
			oid=vb.getOid().toDottedString();
			if(isFirst){
				columnOid=oid.substring(0, oid.lastIndexOf("."));
				isFirst=false;
			}
			if (!vb.getOid().toDottedString().startsWith(entryOid)) break;
			if(vb.getOid().getValue().length!=entryOid.split("\\.").length+2) break;
			if(!vb.getOid().toDottedString().startsWith(columnOid)) break;
			count++;
		}
		return count;
	}

	public static void snmpTableCreate(String ip,String rowStatusInstanceOid, 
			String[] instanceOids, String[] values,String[] syntaxs) throws Exception {
		String[] newOids=new String[instanceOids.length+1];
		String[] newValues=new String[values.length+1];
		String[] newSyntaxs=new String[syntaxs.length+1];
		System.arraycopy(instanceOids, 0, newOids, 1, instanceOids.length);
		System.arraycopy(values, 0,newValues, 1, values.length);
		System.arraycopy(syntaxs, 0, newSyntaxs, 1, syntaxs.length);
		newOids[0]=rowStatusInstanceOid;
		newValues[0]="4";
		newSyntaxs[0]=AbstractVariable.getSyntaxString(SMIConstants.SYNTAX_INTEGER);
		setRequest(ip, newOids, newSyntaxs, newValues);
	}
}
