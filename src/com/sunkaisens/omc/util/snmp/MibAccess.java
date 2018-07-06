package com.sunkaisens.omc.util.snmp;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.percederberg.mibble.Mib;
import net.percederberg.mibble.MibLoader;
import net.percederberg.mibble.MibType;
import net.percederberg.mibble.MibTypeTag;
import net.percederberg.mibble.MibValueSymbol;
import net.percederberg.mibble.snmp.SnmpModuleIdentity;
import net.percederberg.mibble.snmp.SnmpObjectType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MibAccess{
	
	protected Logger log = LoggerFactory.getLogger(getClass());
    private Map<String, NodeInfomation> nodeMap;
    // key:name value:oid;
    private Map<String, String> nameOidMap;
    private MibLoader loader;
    private final static Map<String,MibAccess> MIB_NAME_ACCESS_MAP=new HashMap<String, MibAccess>();


    public MibAccess(String fileName) throws FileNotFoundException{
        nodeMap = new HashMap<String, NodeInfomation>();
        nameOidMap = new HashMap<String, String>();
        loader = new MibLoader();
        log.debug("解析MIB文件：" + fileName);
        File file = new File(fileName);
        if (!file.exists()) {
        	log.warn(file.getAbsolutePath() + "不存在！");
        	throw new FileNotFoundException();
        }
        loader.addDir(file.getParentFile());
        try {
            loader.load(file);
            getNodeinfo();
            loader.unloadAll();
            loader.removeAllDirs();
            loader = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getNodeinfo() {
        Mib mibs[] = loader.getAllMibs();
        for (int mibCount = 0; mibCount < mibs.length; mibCount++) {
            Mib temMib = mibs[mibCount];
            MIB_NAME_ACCESS_MAP.put(temMib.getName(), this);
            Collection<?> c = temMib.getAllSymbols();
            Iterator<?> iter = c.iterator();
            while (iter.hasNext()) {
                Object symbol = iter.next();
                NodeInfomation nodeInfo = extractOid(symbol);
                if (nodeInfo != null) {
                    nodeMap.put(nodeInfo.getOid(), nodeInfo);
                    nameOidMap.put(nodeInfo.getNodeName(), nodeInfo.getOid());
                }
            }
        }
    }
    
    private NodeInfomation extractOid(Object symbol) {
        if (symbol instanceof MibValueSymbol) {
        	NodeInfomation nodeInfo = new NodeInfomation();
        	MibValueSymbol temValueSymbol = (MibValueSymbol) symbol;
            // value = ((MibValueSymbol) symbol).getValue();
        	
            nodeInfo.setNodeName(temValueSymbol.getName());
            nodeInfo.setTable(temValueSymbol.isTable());
            nodeInfo.setTableRow(temValueSymbol.isTableRow());
            nodeInfo.setTableColumn(temValueSymbol.isTableColumn());
            nodeInfo.setOid(temValueSymbol.getValue().toString());
            nodeInfo.setParent(temValueSymbol.getParent()==null?null:temValueSymbol.getParent().getName());
            nodeInfo.setChildCount(temValueSymbol.getChildCount());
            nodeInfo.setType(temValueSymbol.getType().toString());
            nodeInfo.setChildren(temValueSymbol.getChildren());
            nodeInfo.setScalar(temValueSymbol.isScalar());
            MibType temMibType = temValueSymbol.getType();
            if (temMibType instanceof SnmpModuleIdentity) {
//            	SnmpModuleIdentity sObjectType=(SnmpModuleIdentity)temMibType;
//            	System.out.println(sObjectType.getName());
                // System.out.println("moduleIdentity: "+temMibType.getClass());
            } else if (temMibType instanceof SnmpObjectType) {
                // System.out.println("objectType: "+temMibType.getClass());
            	SnmpObjectType sObjectType = (SnmpObjectType) temMibType;
                if (sObjectType != null) {
                    nodeInfo.setAccess(sObjectType.getAccess().toString());
                    nodeInfo.setStatus(sObjectType.getStatus().toString());
                    MibType syntax= sObjectType.getSyntax();
                    if(syntax.getTag()==null&&syntax.getReferenceSymbol()!=null)
                    	nodeInfo.setSyntax(syntax.getReferenceSymbol().getName());
                    else if(syntax.getTag().getCategory()!=MibTypeTag.UNIVERSAL_CATEGORY){
                    	if(syntax.getReferenceSymbol()!=null)
                    		nodeInfo.setSyntax(syntax.getReferenceSymbol().getName());
                    	else
                    		nodeInfo.setSyntax(syntax.getName());
                    }
                    else
                    	nodeInfo.setSyntax(syntax.getName());
//                	SnmpModuleIdentity sObjectType=(SnmpModuleIdentity)temMibType;
//                	System.out.println(sObjectType.getName());
                    // System.out.println("moduleIdentity: "+temMibType.getClass());
                }
            }

            return nodeInfo;
        }
        return null;

    }

 
    public boolean isTable(String nameOrOid) {
        NodeInfomation nodeInfo = getNode(nameOrOid);
        if (nodeInfo != null)
            return nodeInfo.isTable();
        return false;
    }

    public boolean isEntry(String nameOrOid) {
    	return getNode(nameOrOid).isTableRow();
    }

    public String getNodeName(String oid) {
        NodeInfomation nodeInfo = nodeMap.get(oid);
        if (nodeInfo != null)
            return nodeInfo.getNodeName();
        else
            return null;
    }

    public MibValueSymbol[] getChildren(String nameOrOid) {
        NodeInfomation nodeInfo = getNode(nameOrOid);
        if (nodeInfo != null)
            return nodeInfo.getChildren();
        else
            return null;
    }

    public int getChildCount(String nameOrOid) {
        NodeInfomation nodeInfo = getNode(nameOrOid);
        if (nodeInfo != null)
            return nodeInfo.getChildCount();
        else
            return -1;
    }

    public String getSyntax(String nameOrOid) {
        NodeInfomation nodeInfo = getNode(nameOrOid);
        if (nodeInfo != null)
            return nodeInfo.getSyntax().toString();
        else
            return null;
    }

    public String getType(String nameOrOid) {
        NodeInfomation nodeInfo = getNode(nameOrOid);
        if (nodeInfo != null)
            return nodeInfo.getType();
        else
            return null;
    }

    public String getParentOid(String nameOrOid) {
        NodeInfomation nodeInfo = getNode(nameOrOid);
        if (nodeInfo != null)
            return nodeInfo.getParent();
        else
            return null;
    }

    public String getOid(String name) {
        return nameOidMap.get(name);
    }
    
    public String getScalarInstanceOid(String name){
    	if(getNode(name).isScalar())
    		return nameOidMap.get(name)+".0";
    	throw new IllegalArgumentException(name+"不是标量！");
    }
    
    public String[] getScalarInstanceOids(String[] names){
    	String[] oids=new String[names.length];
    	for(int i=0;i<oids.length;i++){
    		if(getNode(names[i]).isScalar())
    			oids[i]=nameOidMap.get(names[i])+".0";
    		else
    			new IllegalArgumentException(names[i]+"不是标量！");
    	}
        return oids;
    }
    
    public String getOid(String name,int append){
    	return getOid(name)+"."+append;
    }
    
    public String[] getOids(String[] names,int append) {
    	String[] oids=new String[names.length];
    	for(int i=0;i<names.length;i++)
    		oids[i]=getOid(names[i], append);
        return oids;
    }
    
    public String[] getOids(String[] names) {
    	String[] oids=new String[names.length];
    	for(int i=0;i<oids.length;i++)
    		oids[i]=nameOidMap.get(names[i]);
        return oids;
    }

    public String getAccess(String nameOrOid) {
        NodeInfomation nodeInfo = getNode(nameOrOid);
        if (nodeInfo != null)
            return nodeInfo.getAccess();
        else
            return null;
    }

    public String getStatus(String nameOrOid) {
        NodeInfomation nodeInfo = getNode(nameOrOid);
        if (nodeInfo != null)
            return nodeInfo.getStatus();
        else
            return null;
    }

    private boolean isName(String str) {
       return !isOid(str);
    }
    
    private boolean isOid(String str){
    	Pattern pattern = Pattern.compile("\\.\\d+");
        Matcher match = pattern.matcher(str);
        return match.find();
    }

    public NodeInfomation getNode(String nameOrOid) {
        NodeInfomation nodeInfo=null;
        if (isName(nameOrOid)) {
            String oid = nameOidMap.get(nameOrOid);
            nodeInfo = nodeMap.get(oid);
        } else if(isOid(nameOrOid)){
            nodeInfo = nodeMap.get(nameOrOid);
        }
        return nodeInfo;
    }
    
    public NodeInfomation[] getNodes(String[] nameOrOids) {
    	NodeInfomation[] nodes=new NodeInfomation[nameOrOids.length];
    	for(int i=0;i<nodes.length;i++){
    		nodes[i]=getNode(nameOrOids[i]);
    	}
    	return nodes;
    }

	public Map<String, NodeInfomation> getNodeMap() {
		return nodeMap;
	}

	public Map<String, String> getNameOidMap() {
		return nameOidMap;
	}


	static Map<String, MibAccess> getMibNameAccessMap() {
		return MIB_NAME_ACCESS_MAP;
	}
}
