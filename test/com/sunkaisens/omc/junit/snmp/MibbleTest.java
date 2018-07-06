package com.sunkaisens.omc.junit.snmp;

import org.junit.Before;
import org.junit.Test;

import com.sunkaisens.omc.util.snmp.MibAccess;
import com.sunkaisens.omc.util.snmp.MibParserUtil;
import com.sunkaisens.omc.util.snmp.NodeInfomation;

public class MibbleTest {

	MibParserUtil parser;
	
	@Before
	public void init(){
		parser=MibParserUtil.getInstance();
		String[] files={"WebContent/mibs/GRM-MIB.txt"};
		parser.readMibFile(files);
	}
	
	@Test
	public void readMibFile(){
		String[] files={"WebContent/mibs/GSC-MIB.txt","WebContent/mibs/SNMPv2-MIB"};
		parser.readMibFile(files);
		NodeInfomation node=parser.getNodeInfomation("sysContact");
		if(node==null) return;
//		ArrayList<MibAccess> list=mb.getMibLst();
//		for(MibAccess a:list){
//			for(NodeInfomation node:a.getNodeMap().values()){
				System.out.println(node.getSyntax());
				System.out.println("===============");
				System.out.println(node.getNodeName());
				System.out.println("===============");
				System.out.println(node.getType());
//			}
//		}
		
//		for(MibAccess o:list){
//			NodeInfomation node=o.getNode("1.3.6.1.4.1.11001.1.9");
//			if(node!=null)
//				System.out.println(node.isTable());
//		}
	}
	
	@Test
	public void test2(){
		MibAccess access=parser.getMibAccess("GRM-MIB");
		String[] oids=access.getScalarInstanceOids(new String[]{"grmSoftwareStatus", "grmPortSctp"});
		for(String s:oids)
			System.out.println(s);
	}
}
