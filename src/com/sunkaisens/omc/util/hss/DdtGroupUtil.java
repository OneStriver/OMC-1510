package com.sunkaisens.omc.util.hss;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.sunkaisens.omc.context.core.OmcServerContext;
import com.sunkaisens.omc.po.hss.GroupMember;
import com.sunkaisens.omc.po.hss.HssGroup;

public class DdtGroupUtil {
	
	public static List<HssGroup> groupInfoList = new ArrayList<HssGroup>();
	public static List<GroupMember> groupmemberList = new ArrayList<GroupMember>();
	public static List<GroupMember> groupmemberByIdList = new ArrayList<GroupMember>();
	
	public static List<HssGroup> getGroupInfoList() {
		return groupInfoList;
	}

	public static List<GroupMember> getGroupmemberListByGroupId(String groupId) {
		groupmemberByIdList.clear();
		List<GroupMember> selectGroupMemberList = DdtGroupUtil.getGroupMemberList(OmcServerContext.getInstance().getSelectServiceUrl());
		System.err.println("根据GroupId查询的组成员selectGroupMemberList的数量:"+selectGroupMemberList.size());
		for (GroupMember groupMember : selectGroupMemberList) {
			if(groupMember.getGroupId().equals(groupId)){
				groupmemberByIdList.add(groupMember);
			}
		}
		return groupmemberByIdList;
	}

	//查询组号的信息
	public static List<HssGroup> readHssGroupStringXml(String xmlType){
		//每次都清空集合
		groupInfoList.clear();
		Document doc = null;
		try {
			doc = DocumentHelper.parseText(xmlType);
			Element rootElt = doc.getRootElement();
			Iterator<?> firstIterators = rootElt.elementIterator();
			//resource-lists下面的子节点list
			while(firstIterators.hasNext()){
				HssGroup groupInfo = new HssGroup();
				//获取每一个list节点
				Element firstElement = (Element)firstIterators.next();
				//组号
				String groupNum = firstElement.attributeValue("name");
				groupInfo.setId(groupNum);
				//服务类型
				String serviceType = firstElement.attributeValue("serviceType");
				if(serviceType.equals("dynamic")){
					groupInfo.setGroupType(1);
				}else if(serviceType.equals("trunk")){
					groupInfo.setGroupType(2);
				}else if(serviceType.equals("normal")){
					groupInfo.setGroupType(3);
				}else if(serviceType.equals("personal")){
					groupInfo.setGroupType(4);
				}
				//优先级
				String serviceLevel = firstElement.attributeValue("serviceLevel");
				System.err.println("serviceLevel:"+serviceLevel);
				groupInfo.setPriority(Integer.valueOf(serviceLevel));
				//默认是语音
				groupInfo.setBusiType(1);
				//默认是半双工
				groupInfo.setCallType(2);
				//默认离线
				groupInfo.setStatus(0);
				groupInfo.setLocation("@");
				groupInfo.setCreateTime(new Date());
				
				//添加groupInfo到List中
				groupInfoList.add(groupInfo);
				System.err.println("groupSize:"+groupInfoList.size());
				System.err.println("groupMemberSize:"+groupmemberList.size());
			}
			return groupInfoList;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	//查询组成员信息
	public static List<GroupMember> readGroupMemberStringXml(String xmlType){
		//每次都清空集合
		groupmemberList.clear();
		Document doc = null;
		try {
			doc = DocumentHelper.parseText(xmlType);
			Element rootElt = doc.getRootElement();
			Iterator<?> firstIterators = rootElt.elementIterator();
			//resource-lists下面的子节点list
			while(firstIterators.hasNext()){
				//获取每一个list节点
				Element firstElement = (Element)firstIterators.next();
				//组号
				String groupNum = firstElement.attributeValue("name");
				//获取当前节点下面的子节点
				Iterator<?> secondIterators = firstElement.elementIterator();
				//list下面的子节点list
				while(secondIterators.hasNext()){
					GroupMember groupMemberInfo = new GroupMember();
					//获取每一个list节点
					Element secondElement = (Element)secondIterators.next();
					groupMemberInfo.setGroupId(groupNum);
					//组成员号码
					String groupMemberNum = secondElement.attributeValue("displayName");
					groupMemberInfo.setMdn(groupMemberNum);
					//组成员类型
					String groupMemberUserType = secondElement.attributeValue("userType");
					groupMemberInfo.setRole(Integer.valueOf(groupMemberUserType));
					//组成员设备类型
					String groupMemberDeviceType = secondElement.attributeValue("deviceType");
					//组成员添加时间
					groupMemberInfo.setCreateTime(new Date());
					groupmemberList.add(groupMemberInfo);
				}
				System.err.println("读取XML获取组成员信息>>>groupMemberSize:"+groupmemberList.size());
			}
			return groupmemberList;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	//发送Get请求(获取组的信息)
	public static List<HssGroup> getGroupList(String url){
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpGet httpGet = null;
		try {
			//获取通讯录
			httpGet = new HttpGet(url);
			httpGet.setHeader("X-XCAP-Asserted-Identity","sip:administrator@test.com");
			//获取返回值
			CloseableHttpResponse response = httpClient.execute(httpGet);
			HttpEntity entity = response.getEntity();
			String responseStr = EntityUtils.toString(entity, "UTF-8");
			System.err.println("获取组的信息返回的XML字符串:"+responseStr);
			if(!responseStr.equals("")||responseStr!=null){
				//解析XML文档存入缓存中
				List<HssGroup> groupList = DdtGroupUtil.readHssGroupStringXml(responseStr);
				System.err.println("获取组的信息groupList的数量:"+groupList.size());
				return groupList;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(httpGet!=null){
					httpGet.releaseConnection();
				}		
				if(httpClient!=null){
					httpClient.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	//发送Put请求 add(组)
	public static void addPutGroupList(String url,String contentBody){
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpPut httpPut = null;
		try {
			//获取通讯录
			httpPut = new HttpPut(url);
			httpPut.setHeader("X-XCAP-Asserted-Identity","sip:administrator@test.com");
			httpPut.setHeader("Content-Type", "application/public-group+xml; charset=UTF-8");
			StringEntity stringEntity=new StringEntity(contentBody, "UTF-8");
			httpPut.setEntity(stringEntity);
			//获取返回值
			CloseableHttpResponse execute = httpClient.execute(httpPut);
			HttpEntity entity = execute.getEntity();
			String responseStr = EntityUtils.toString(entity, "UTF-8");
			System.err.println("获取返回的值:"+responseStr);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(httpPut!=null){
					httpPut.releaseConnection();
				}		
				if(httpClient!=null){
					httpClient.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	//发送Put请求  update(组)
	public static void updatePutGroupList(String url,String contentBody){
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpPut httpPut = null;
		try {
			//获取通讯录
			httpPut = new HttpPut(url);
			httpPut.setHeader("X-XCAP-Asserted-Identity","sip:administrator@test.com");
			httpPut.setHeader("Content-Type", "application/xcap-att+xml; charset=UTF-8");
			StringEntity stringEntity=new StringEntity(contentBody, "UTF-8");
			httpPut.setEntity(stringEntity);
			//获取返回值
			CloseableHttpResponse execute = httpClient.execute(httpPut);
			HttpEntity entity = execute.getEntity();
			String responseStr = EntityUtils.toString(entity, "UTF-8");
			System.err.println("获取返回的值:"+responseStr);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(httpPut!=null){
					httpPut.releaseConnection();
				}		
				if(httpClient!=null){
					httpClient.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	//发送delete请求	(删除组号的信息)
	public static void deleteGroupList(String url,String id){
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpDelete httpDelete = null;
		try {
			//获取通讯录
			httpDelete = new HttpDelete(url);
			httpDelete.setHeader("X-XCAP-Asserted-Identity","sip:administrator@test.com");
			//获取返回值
			httpClient.execute(httpDelete);
			//获取返回值
			CloseableHttpResponse response = httpClient.execute(httpDelete);
			if(response.getStatusLine().getStatusCode()==HttpStatus.SC_OK){
				Iterator<HssGroup> iterators = groupInfoList.iterator();
		        while(iterators.hasNext()){
		        	HssGroup hssGroup = iterators.next();
		            if(hssGroup.getId().equals(id)){
		            	iterators.remove();
		            }
		        }
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(httpDelete!=null){
					httpDelete.releaseConnection();
				}		
				if(httpClient!=null){
					httpClient.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	//发送Get请求(获取组成员的信息)
	public static List<GroupMember> getGroupMemberList(String url){
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpGet httpGet = null;
		try {
			//获取通讯录
			httpGet = new HttpGet(url);
			httpGet.setHeader("X-XCAP-Asserted-Identity","sip:administrator@test.com");
			//获取返回值
			CloseableHttpResponse response = httpClient.execute(httpGet);
			HttpEntity entity = response.getEntity();
			String responseStr = EntityUtils.toString(entity, "UTF-8");
			System.err.println("获取组成员的信息返回的XML字符串:"+responseStr);
			if(!responseStr.equals("")||responseStr!=null){
				//解析XML文档存入缓存中
				List<GroupMember> groupMemberList = DdtGroupUtil.readGroupMemberStringXml(responseStr);
				System.err.println("获取组成员的信息groupMemberList的数量:"+groupMemberList.size());
				return groupMemberList;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(httpGet!=null){
					httpGet.releaseConnection();
				}		
				if(httpClient!=null){
					httpClient.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	//发送Put请求 add(添加组成员)
	public static void addPutGroupMemberList(GroupMember member,String url,String contentBody){
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpPut httpPut = null;
		try {
			//获取通讯录
			httpPut = new HttpPut(url);
			httpPut.setHeader("X-XCAP-Asserted-Identity","sip:administrator@test.com");
			httpPut.setHeader("Content-Type", "application/xcap-el+xml; charset=UTF-8");
			StringEntity stringEntity=new StringEntity(contentBody, "UTF-8");
			httpPut.setEntity(stringEntity);
			//获取返回值
			CloseableHttpResponse execute = httpClient.execute(httpPut);
			HttpEntity entity = execute.getEntity();
			String responseStr = EntityUtils.toString(entity, "UTF-8");
			//
			String headStr = "<?xml version='1.0' encoding='UTF-8'?>";
			responseStr = headStr + responseStr;
			Document doc = DocumentHelper.parseText(responseStr);
			Element rootElt = doc.getRootElement();
			
			GroupMember groupMemberInfo = new GroupMember();
			//获取每一个list节点
			groupMemberInfo.setGroupId(member.getGroupId());
			//组成员号码
			String groupMemberNum = rootElt.attributeValue("displayName");
			groupMemberInfo.setMdn(groupMemberNum);
			//组成员类型
			String groupMemberUserType = rootElt.attributeValue("userType");
			System.err.println("groupMemberUserType:"+groupMemberUserType);
			groupMemberInfo.setRole(Integer.valueOf(groupMemberUserType));
			//组成员设备类型
			String groupMemberDeviceType = rootElt.attributeValue("deviceType");
			//组成员添加时间
			groupMemberInfo.setCreateTime(new Date());
			groupmemberList.add(groupMemberInfo);
			
			System.err.println("获取返回的值:"+responseStr);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(httpPut!=null){
					httpPut.releaseConnection();
				}		
				if(httpClient!=null){
					httpClient.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	//发送Put请求 update(修改组成员)
	public static void updatePutGroupMemberList(GroupMember member,String url,String contentBody){
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpPut httpPut = null;
		try {
			//获取通讯录
			httpPut = new HttpPut(url);
			httpPut.setHeader("X-XCAP-Asserted-Identity","sip:administrator@test.com");
			httpPut.setHeader("Content-Type", "application/xcap-att+xml; charset=UTF-8");
			StringEntity stringEntity=new StringEntity(contentBody, "UTF-8");
			httpPut.setEntity(stringEntity);
			//获取返回值
			CloseableHttpResponse execute = httpClient.execute(httpPut);
			HttpEntity entity = execute.getEntity();
			String responseStr = EntityUtils.toString(entity, "UTF-8");
			//
			Document doc = DocumentHelper.parseText(responseStr);
			Element rootElt = doc.getRootElement();
			//组成员号码
			String groupMemberNum = rootElt.attributeValue("displayName");
			//组成员类型
			String groupMemberUserType = rootElt.attributeValue("userType");
			//组成员设备类型
			String groupMemberDeviceType = rootElt.attributeValue("deviceType");
			for (GroupMember groupMember : groupmemberList) {
				if(groupMember.getGroupId().equals(member.getGroupId()) && groupMember.getMdn().equals(member.getMdn())){
					groupMember.setMdn(groupMemberNum);
					groupMember.setRole(Integer.valueOf(groupMemberUserType));
					groupMember.setCreateTime(new Date());
				}
			}
			System.err.println("获取返回的值:"+responseStr);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(httpPut!=null){
					httpPut.releaseConnection();
				}		
				if(httpClient!=null){
					httpClient.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	//发送delete请求	(删除组成员的信息)
	public static void deleteGroupMemberList(String mdn,String groupId,String url){
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpDelete httpDelete = null;
		try {
			//获取通讯录
			httpDelete = new HttpDelete(url);
			httpDelete.setHeader("X-XCAP-Asserted-Identity","sip:administrator@test.com");
			//获取返回值
			CloseableHttpResponse response = httpClient.execute(httpDelete);
			if(response.getStatusLine().getStatusCode()==HttpStatus.SC_OK){
				Iterator<GroupMember> iterators = groupmemberList.iterator();
		        while(iterators.hasNext()){
		        	GroupMember groupMember = iterators.next();
		            if(groupMember.getGroupId().equals(groupId) && groupMember.getMdn().equals(mdn)){
		            	iterators.remove();
		            }
		        }
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(httpDelete!=null){
					httpDelete.releaseConnection();
				}		
				if(httpClient!=null){
					httpClient.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
