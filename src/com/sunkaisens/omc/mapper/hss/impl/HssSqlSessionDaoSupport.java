package com.sunkaisens.omc.mapper.hss.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.dao.support.DaoSupport;
import org.springframework.util.Assert;

public abstract class HssSqlSessionDaoSupport extends DaoSupport {
	protected String namespace;
	public int hssDataSourceCount=4;
	
	private List<SqlSession> sqlSessions = new ArrayList<SqlSession>();

    private boolean externalSqlSession;
    
    public HssSqlSessionDaoSupport(){
		namespace=getClass().getInterfaces()[0].getName();
	}
    
    public void setHssDataSourceCount(int hssDataSourceCount) {
		this.hssDataSourceCount = hssDataSourceCount;
	}
    
    public int getHssDataSourceCount() {
		return hssDataSourceCount;
	}
    
    public final void setSqlSessionFactories(List<SqlSessionFactory> sqlSessionFactories) {
        if (!this.externalSqlSession) {
        	for (SqlSessionFactory sessionFactory : sqlSessionFactories){
        		this.sqlSessions.add(new SqlSessionTemplate(sessionFactory));
        	}
        }
    }
    
    public int getHssDatabaseIndex(String imsi){
    	int index=(int)(Long.parseLong(imsi)%hssDataSourceCount);
    	return index==0?hssDataSourceCount:index;
    }
   
    public final void setSqlSessionTemplates(List<SqlSessionTemplate> sqlSessionTemplates) {
    	for (SqlSessionTemplate sqlSessionTemplate : sqlSessionTemplates){
    		sqlSessions.add(sqlSessionTemplate);
    	}
        this.externalSqlSession = true;
    }

    public final SqlSession getSqlSession() {
    	return this.sqlSessions.get(0);
    }
    
    public final SqlSession getSqlSession(int index) {
    	return this.sqlSessions.get(index);
    }

    protected void checkDaoConfig() {
    	Assert.notEmpty(this.sqlSessions, "Property List<SqlSessionTemplate> or List<SqlSessionFactory> is required");
    }

}
