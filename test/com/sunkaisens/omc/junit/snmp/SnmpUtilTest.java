package com.sunkaisens.omc.junit.snmp;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.snmp4j.smi.VariableBinding;

import com.sunkaisens.omc.util.snmp.MibParserUtil;
import com.sunkaisens.omc.util.snmp.SnmpUtil;

public class SnmpUtilTest {

	private final String ip="192.168.1.127";
	private MibParserUtil parser;
	@Before
	public void init(){
		parser=MibParserUtil.getInstance();
		String[] files={"WebContent/mibs/SNMPv2-MIB"/*,"WebContent/mibs/RFC1213-MIB.my"*/};
		parser.readMibFile(files);
	}
	
	@Test
	public void getRequest() throws Exception{
		List<VariableBinding> list=SnmpUtil.getRequest(null, new String[]{"1.3.6.1.2.1.4.21.1.7.192.168.1.0","1.3.6.1.2.1.1.4.0","1.3.6.1.2.1.1.5.0"});
		for(VariableBinding vb:list){
			System.out.println(vb.getOid().toDottedString()+"="+vb.getVariable().toString());
		}
	}
	
	@Test
	public void getNextRequest() throws Exception{
		List<VariableBinding> list=SnmpUtil.getNextRequest(ip, new String[]{"1.3.6.1.2.1.1.4","1.3.6.1.2.1.1.5"});
		for(VariableBinding vb:list){
			System.out.println(vb.getOid().toDottedString()+"="+vb.getVariable().toString());
		}
	}
	
	@Test
	public void getSnmpTable() throws Exception{
		List<List<VariableBinding>> lines=SnmpUtil.getSnmpTable(ip, new String[]{"1.3.6.1.2.1.1.9.1.2","1.3.6.1.2.1.1.9.1.3","1.3.6.1.2.1.1.9.1.4"});
		for(List<VariableBinding> line:lines){
			for(VariableBinding vb:line){
				System.out.print(vb.getOid().toDottedString()+"="+vb.getVariable().toString()+"\t");
			}
			System.out.println();
		}
	}
	
	@Test
	public void setRequest() throws Exception{
		String[] oids={"1.3.6.1.2.1.1.4.0","1.3.6.1.2.1.1.5.0",
//				"1.3.6.1.2.1.4.21.1.7.192.168.1.0"
				};
		StringBuilder sb=new StringBuilder();
		for(int i=0;i<2500;i++)
			sb.append('a');
		String[] values={sb.toString(),"222222222",/*"192.168.1.1"*/};
		String[] syntaxs={
				parser.getNodeInfomation("1.3.6.1.2.1.1.4").getSyntax(),
				parser.getNodeInfomation("1.3.6.1.2.1.1.5").getSyntax(),
//				parser.getNodeInfomation("ipRouteNextHop").getSyntax()
		};
		boolean success=SnmpUtil.setRequest(ip, oids,syntaxs,values);
		System.out.println(success);
	}
	
	@Test
	public void getLastIndex()throws Exception{
		int index=SnmpUtil.getLastIndex(ip, "1.3.6.1.2.1.1.9.1.2");
		System.out.println(index);
	}
	
	@Test
	public void getIndexs()throws Exception{
		Integer[] indexs=SnmpUtil.getTableIndexs(ip, "1.3.6.1.2.1.1.9");
		for(int index:indexs)
			System.out.println(index);
	}
}
