<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>板卡管理</title>
<%@ include file="/WEB-INF/jsp/public/common.jspf"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/public/grid.js?<%=new Date().getTime()%>"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/card/card.js"></script>

</head>
<body>
	<table id="dg" class="easyui-datagrid" title="<spring:message code="CardList"/>" style="width:800px;height:auto"
		data-options="
			url:'${pageContext.request.contextPath}/card/list.action',
			rownumbers:true,
			fit:true,
			striped:true,
			border:false,
			pageList: [10,20,30,40,50,60],
			pageNumber:${pageBean.page},
			pagination:true,
			toolbar: '#tb',
			loadFilter:loadFilter,
			onClickCell:onClickCell,
			pageSize: ${pageBean.pageSize}">
	<thead>
		<tr>
			<th data-options="field:'ck',checkbox:true"></th>
			<th data-options="field:'id',hidden:true"></th>
			<th data-options="field:'name',width:'10%'"><spring:message code="CardName"/></th>
			<th data-options="field:'cardNum',width:'10%'"><spring:message code="CardNumber"/></th>
			<th data-options="field:'slotId',width:'10%'"><spring:message code="SlotNumber"/></th>
			<th data-options="field:'ip',width:'10%'" ><spring:message code="IPAddress"/></th>
			<th data-options="field:'serial',width:'40%',
				formatter:function(value,row,rowIndex){
					return value?'['+value+']':'';
				}">板卡序列号</th>
			<th data-options="field:'oamStatus',width:'10%',
				formatter:function(value,row,rowIndex){
					return value?'<font color=green>已启动</font>':'<font color=red>未启动</font>';
				}">板卡状态</th>
		</tr>
	</thead>
	</table>
	
	<div id="tb" style="height:auto">
		<omc:permit url="card/addCard">
			<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="append()" >添加</a>
		</omc:permit>
		<omc:permit url="card/deleteCard">
			<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="removeit(
					'${pageContext.request.contextPath}/card/delete.action')">删除</a>
		</omc:permit>
		<omc:permit url="card/reboot">
			<a href="#" class="easyui-linkbutton" data-options="plain:true" onclick="reboot()">重启全部板卡</a>
		</omc:permit>
		<omc:permit url="cardResetLf/reset">
			<a href="#" class="easyui-linkbutton" data-options="plain:true" onclick="reSet()">恢复出厂配置</a>
		</omc:permit>
	</div>
	
	<!-- 添加窗口  -->	
	<div id="addCardWindow" class="easyui-window" title="添加板卡" data-options="minimizable:false,maximizable:false,
        	collapsible:false,modal:true,closed:true,iconCls:'icon-add',width:430" style="padding:10px;">
		<form id="addCardForm" class="easyui-form" method="post" action="${pageContext.request.contextPath}/card/save.action">
	    	<table>
	    		<tr>
	    			<td><spring:message code="CardName"/>:</td>
	    			<td><input class="easyui-textbox" id="addCardName" name="name" data-options="validType:'cardName',required:true"/></td>
	    			<td><spring:message code="CardNumber"/>:</td>
	    			<td><input class="easyui-numberbox" id="addCardNum" name="cardNum" data-options="validType:'cardNum',required:true"/></td>
	    		</tr>
	    		<tr>
	    			<td><spring:message code="SlotNumber"/>:</td>
	    			<td><input class="easyui-numberbox" id="addCardSlotId" name="slotId" data-options="validType:'slotNum',required:true"/></td>
	    			<td><spring:message code="IPAddress"/>:</td>
	    			<td><input class="easyui-textbox" id="addCardIp" name="ip" data-options="validType:'ipAdd',required:true"/></td>
	    		</tr>
	    	</table>
	    </form>
	    <div style="text-align:center;padding:5px">
	    	<a href="#" class="easyui-linkbutton" onclick="submitCardForm()">新增板卡</a>
	    </div>
	</div>
	
</body>
</html>