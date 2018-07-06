package com.sunkaisens.omc.util.hss;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.sunkaisens.omc.context.core.OmcServerContext;

public class JdbcUtil {
	
	//主设备数据库连接地址
	public static Connection getNmsConnection(){
    	try {
    		Connection conn = null;
    		//主设备
			conn = DriverManager.getConnection(
					OmcServerContext.getInstance().getNmsUrl(),
					OmcServerContext.getInstance().getNmsUserName(),
					OmcServerContext.getInstance().getNmsPassword());
			return conn;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
    }
	
	//主设备数据库连接地址
	public static Connection getMainConnection(int flag){
    	try {
    		Connection conn = null;
    		//主设备
    		if(flag==1){
    			conn = DriverManager.getConnection(
    					OmcServerContext.getInstance().getMainUrl(),
    					OmcServerContext.getInstance().getMainUserName(),
    					OmcServerContext.getInstance().getMainPassword());
    		}else{
    			conn = DriverManager.getConnection(
    					OmcServerContext.getInstance().getBackUpUrl(),
    					OmcServerContext.getInstance().getBackUpUserName(),
    					OmcServerContext.getInstance().getBackUpPassword());
    		}
			return conn;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
    }
	
	
	public static Connection getUserBelongHssConnection(){
    	try {
			Connection conn = DriverManager.getConnection(
					"jdbc:mysql://172.16.23.118:3306/hss?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull",
					"root",
					"sunkaisens");
			return conn;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
    }
	
    public static Connection getHss1Connection(){
    	try {
			Connection conn = DriverManager.getConnection(
					"jdbc:mysql://172.16.23.22:3306/myomc?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull",
					"root",
					"sunkaisens");
			return conn;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
    }
    
    public static Connection getHss2Connection(){
    	try {
    		Connection conn = DriverManager.getConnection(
					"jdbc:mysql://172.16.23.38:3306/myomc?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull",
					"root",
					"sunkaisens");
    		return conn;
    	} catch (Exception e) {
    		throw new RuntimeException(e);
    	}
    }
    
    public static Connection getHss3Connection(){
    	try {
    		Connection conn = DriverManager.getConnection(
					"jdbc:mysql://172.16.23.54:3306/myomc?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull",
					"root",
					"sunkaisens");
    		return conn;
    	} catch (Exception e) {
    		throw new RuntimeException(e);
    	}
    }
    
    public static Connection getHss4Connection(){
    	try {
    		Connection conn = DriverManager.getConnection(
					"jdbc:mysql://172.16.23.70:3306/myomc?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull",
					"root",
					"sunkaisens");
    		return conn;
    	} catch (Exception e) {
    		throw new RuntimeException(e);
    	}
    }
    
	public static void free(ResultSet rs, Statement st, Connection conn) {
		try {
			if (rs != null)
				rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (st != null)
					st.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				if (conn != null)
					try {
						conn.close();
					} catch (Exception e) {
						e.printStackTrace();
					}
			}
		}
	}
	
}
