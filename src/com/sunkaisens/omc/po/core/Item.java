package com.sunkaisens.omc.po.core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
/**
 * 
 * 
 * 
 * 
 * 
 *Item实体类
 *
 */
public class Item extends BaseProperty implements Serializable{
	private static final long serialVersionUID = 1L;
	//配置项的父配置项
	private Item parent;
	//配置项所属那个配置文件
	private Config config;
	//关联配置项
	private Relevance relevance;
	//item表中没有上下级关系
	private List<Item> children=new ArrayList<Item>();
	/**
	 * 
	 * 
	 * 
	 * 
	 * 无参数构造器
	 */
	public Item(){}
    /**
     * 
     * 
     * 
     * 
     * 
     * 有参数构造器
     * @param name
     * @param value
     * @param description
     * @param formtype
     * @param multiline
     * @param min
     * @param max
     * @param minLen
     * @param maxLen
     * @param required
     * @param orderNum
     * @param optiones
     * @param config
     */
	public Item(String name, String value, String description,
			String formtype, Boolean multiline, Integer min, Integer max,
			Integer minLen, Integer maxLen, Boolean required, Integer orderNum,
			List<Options> optiones,Config config) {
		super(name, value, description, formtype, multiline, min, max, minLen, 
				maxLen, required, orderNum, optiones);
		this.config=config;
	}
	
	
	public Item getParent() {
		return parent;
	}

	public void setParent(Item parent) {
		this.parent = parent;
	}

	public Config getConfig() {
		return config;
	}

	public void setConfig(Config config) {
		this.config = config;
	}

	public Relevance getRelevance() {
		return relevance;
	}

	public void setRelevance(Relevance relevance) {
		this.relevance = relevance;
	}

	public List<Item> getChildren() {
		return children;
	}

	public void setChildren(List<Item> children) {
		this.children = children;
	}
}
