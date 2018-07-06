package com.sunkaisens.omc.util.snmp;

import java.util.ArrayList;
import java.util.List;

import org.snmp4j.smi.VariableBinding;

import net.percederberg.mibble.MibValueSymbol;
/**
 * 
 * 
 * MIB节点信息实体类
 * 
 *
 */
public class NodeInfomation {
	private String nodeName;
	private MibValueSymbol[] children;
	private int childCount;
	private String syntax;
	private String type;
	private boolean isTable;
	private boolean isTableRow;
	private boolean isTableColumn;
	private boolean isScalar; 
	private String parent;
	private String status;
	private String oid;
	private String access;
    /**
     * 
     * 
     * 无参数构造器
     */
	public NodeInfomation() {
	}

	public String getNodeName() {
		return nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	public MibValueSymbol[] getChildren() {
		return children;
	}

	public void setChildren(MibValueSymbol[] children) {
		this.children = children;
	}

	public int getChildCount() {
		return childCount;
	}

	public void setChildCount(int childCount) {
		this.childCount = childCount;
	}

	public String getSyntax() {
		return syntax;
	}

	public void setSyntax(String syntax) {
		this.syntax = syntax;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public boolean isTable() {
		return isTable;
	}

	public void setTable(boolean isTable) {
		this.isTable = isTable;
	}

	public boolean isTableRow() {
		return isTableRow;
	}

	public void setTableRow(boolean isTableRow) {
		this.isTableRow = isTableRow;
	}

	public boolean isTableColumn() {
		return isTableColumn;
	}

	public void setTableColumn(boolean isTableColumn) {
		this.isTableColumn = isTableColumn;
	}

	public boolean isScalar() {
		return isScalar;
	}

	public void setScalar(boolean isScalar) {
		this.isScalar = isScalar;
	}

	public String getParent() {
		return parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getOid() {
		return oid;
	}

	public void setOid(String oid) {
		this.oid = oid;
	}

	public String getAccess() {
		return access;
	}

	public void setAccess(String access) {
		this.access = access;
	}
	
	public static String getValueByName(String name,List<VariableBinding> vbs,MibAccess access){
		for(VariableBinding vb:vbs){
			if(name.equals(access.getNodeName(vb.getOid().toDottedString()))){
				return vb.toValueString();
			}
		}
		return null;
	}
	
	public static String[] getValuesByName(String name,List<List<VariableBinding>> lines,MibAccess access){
		List<String> values=new ArrayList<String>();
		for(List<VariableBinding> vbs:lines){
			values.add(getValueByName(name,vbs,access));
		}
		return values.toArray(new String[0]);
	}
}