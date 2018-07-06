<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>组成员列表</title>
<%@ include file="/WEB-INF/jsp/public/common.jspf"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/hssMember/list.js?<%=new Date().getTime()%>"></script>
</head>
<body>
	<table id="dg" title="<spring:message code="GroupMemberList"></spring:message>" class="easyui-datagrid" style="width:800px;" data-options="
				url:'${pageContext.request.contextPath}/groupMember/list.action?groupId=${groupId}',
				border:false,
				rownumbers:true,
				fit:true,striped:true,
				pageList: [10,20,30,40,50],
				pageNumber:${pageBean.page},
				singleSelect:false,
				pagination:true,
				toolbar: '#tb',
				onClickCell:onClickCell,
				pageSize: ${pageBean.pageSize}">
		<thead>
		<tr>
			<th data-options="field:'ck',checkbox:true"></th>
			<th data-options="field:'mdn',width:'10%'"><spring:message code="MDN"></spring:message></th>
			<th data-options="field:'groupId'"><spring:message code="GroupNumber"></spring:message></th>
			<th data-options="field:'priority'" hidden="true"><spring:message code="GroupMemberPriority"></spring:message></th>
			<th data-options="field:'role',
				formatter:function(value,row,rowIndex){
					switch(value){
					case 1:
						return '调度员';
					case 2:
						return '超级终端';
					case 3:
						return '普通用户';
					default:
						return value;
					}
				}"><spring:message code="GroupMemberRole"></spring:message></th>
			
			<th data-options="field:'service'" hidden="true"><spring:message code="GroupMemberService"></spring:message></th>
			<th data-options="field:'addtion'" hidden="true"><spring:message code="GroupMemberAdditionalService"></spring:message></th>
			<th data-options="field:'createTime',width:150,
				formatter:function(value,row,rowIndex){
					if(typeof value==='number'){
						return new Date(value).format();
					}
					return value;
				}" hidden="true"><spring:message code="GroupMemberAddTime"></spring:message></th>
			<th data-options="field:'updateTime',width:150,
				formatter:function(value,row,rowIndex){
					if(typeof value==='number'){
						return new Date(value).format();
					}
					return value;
				}," hidden="true"><spring:message code="LastUpdateTime"></spring:message></th>
			<th data-options="field:'修改',hidden:true,
				formatter:function(value,row,rowIndex){
					return '<a href=#><spring:message code="Update"></spring:message></a>'
				}"><spring:message code="Update"></spring:message></th> 
		</tr>
	</thead>
	</table>
	
	<div id="tb" style="height:auto">
		<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="append()"><spring:message code="Add"></spring:message></a>
		<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="removeit()"><spring:message code="Delete"></spring:message></a>
		<span hidden="true">
		<select class="easyui-combobox" name="priority"
			data-options="editable:false,onChange:pChange,width:110,panelHeight:'auto'">
			<option value="" selected="selected"><spring:message code="AllPriority"></spring:message></option>
			<c:forEach items="${groupPriority}" var="item">
				<option value="${item.priority}">${item.level}</option>
			</c:forEach>
		</select>
		</span>
		<!-- 组成员角色 -->
		<span hidden="true">
			<select class="easyui-combobox" name="role" 
				data-options="editable:false,width:120,onChange:rChange,panelHeight:'auto'">
				<option value="" selected="selected"><spring:message code="AllGroupMemberRole"></spring:message></option>
				<option value="1">调度员</option>
				<option value="2">超级终端</option>
				<option value="3">普通用户</option>
			</select>
		</span>
		<!-- 返回操作 -->
		<a href="${pageContext.request.contextPath}/hssGroup/listUI.action" class="easyui-linkbutton" 
			data-options="iconCls:'icon-back',plain:true">返回</a>
	</div>
	
	<div id="groupMemberWindow" class="easyui-window" data-options="
			modal:true,
			closed:true,
			minimizable:false,
			maximizable:false,
			collapsible:false,
			novalidate:true
		">
		<form id="groupMemberForm" class="easyui-form" method="post">
	    	<table>
	    		<tr>
	    			<!-- 组号、电话号码  -->
	    			<td><label><spring:message code="MDN"></spring:message>:</label></td>
	    			<td><input class="easyui-numberbox" id="groupMemberMdn" name="mdn" data-options="min:0,required:true,width:130"/></td>
	    			<td><label><spring:message code="GroupNumber"></spring:message>:</label></td>
	    			<td><input class="easyui-textbox" name="groupMemberGroupId" data-options="min:0,required:true,value:'${groupId}',width:130"/></td>
	    		</tr>
	    		<tr>
	    			<!-- 组成员优先级 -->
	    			<td hidden="true"><label><spring:message code="GroupMemberPriority"></spring:message>:</label></td>
	    			<td hidden="true">
	    				<select class="easyui-combobox" id="groupMemberPriority" name="priority"
							data-options="editable:false,width:130,panelHeight:'auto'">
							<c:forEach items="${groupPriority}" var="item">
								<option value="${item.priority}">${item.level}</option>
							</c:forEach>
						</select>
					</td>
					<!-- 组成员角色 -->
	    			<td id="roleTitle"><label><spring:message code="GroupMemberRole"></spring:message>:</label></td>
	    			<td id="roleContent">
	    				<select class="easyui-combobox" id="groupMemberRole" name="role" data-options="editable:false,panelHeight:'auto',width:130">
							<!-- <option value="1">调度员</option>
							<option value="2">超级终端</option> -->
							<option value="3">普通用户</option>
	    				</select>
	    			</td>
	    		</tr>
	    		<!-- 组成员服务、组成员附加服务 -->
	    		<tr hidden="true">
	    			<td><label><spring:message code="GroupMemberService"/>:</label></td>
	    			<td><input class="easyui-numberbox" id="groupMemberService" name="service" data-options="disabled:true"/></td>
	    			<td><label><spring:message code="GroupMemberAdditionalService"/>:</label></td>
	    			<td><input class="easyui-numberbox" id="groupMemberAddition" name="addtion" data-options="disabled:true"/></td>
	    			<td><input class="easyui-numberbox" id="groupMemberAddOrUpdate" name="addOrUpdate" value="0"/></td>
	    		</tr> 
	    	</table>
	    </form>
	    <div style="text-align:center;padding:5px">
	    	<a href="#" class="easyui-linkbutton" onclick="groupMemberSubmitForm()">　<spring:message code="save"></spring:message>　</a>
	    </div>
	 </div>
</body>
</html>