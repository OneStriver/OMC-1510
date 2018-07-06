package com.sunkaisens.omc.util.snmp;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * 
 * MIb解析工具实体类
 *
 */
public class MibParserUtil {
	protected final Logger log = LoggerFactory.getLogger(getClass());
	private static ArrayList<MibAccess> mibs = new ArrayList<MibAccess>();
	private static MibParserUtil mf=new MibParserUtil();
	/**
	 * 
	 * 
	 * 静态加载块
	 */
	static{
		String mibDir=MibParserUtil.class.getResource("/mib").getPath();
		File[] mibs=new File(mibDir).listFiles();
		if(mibs!=null)
			mf.readMibFile(mibs);
	}
	/**
	 * 
	 * 
	 * 无参数构造器
	 */
	private MibParserUtil(){}
	public static MibParserUtil getInstance(){
		return mf;
	}
	public void readMibFile(String[] filePath) {
		for(int i=0;i<filePath.length;i++){
			try {
				MibAccess mibAcess = new MibAccess(filePath[i]);
				mibs.add(mibAcess);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
	}

	public void readMibFile(File[] files) {
		String[] filePaths=new String[files.length];
		for(int i=0;i<filePaths.length;i++){
			try {
				MibAccess mibAcess = new MibAccess(files[i].getAbsolutePath());
				mibs.add(mibAcess);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
	}
	
	public ArrayList<MibAccess> getMibLst() {
		return mibs;
	}
	
	public NodeInfomation getNodeInfomation(String rootNodeNameOrOid,String nameOrOid){
		for(MibAccess access:mibs){
			if(access!=null&&access.getNode(rootNodeNameOrOid)!=null)
				return access.getNode(nameOrOid);
		}
		return null;
	}
	
	public NodeInfomation getNodeInfomation(String nameOrOid){
		for(MibAccess access:mibs){
			NodeInfomation node=access.getNode(nameOrOid);
			if(node!=null)
				return node;
		}
		return null;
	}
	
	public MibAccess getMibAccess(String name) {
		return MibAccess.getMibNameAccessMap().get(name);
	}
}
