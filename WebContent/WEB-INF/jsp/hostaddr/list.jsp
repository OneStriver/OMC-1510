<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<title>主机地址管理</title>
<%@ include file="/WEB-INF/jsp/public/common.jspf"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/hostaddr/list.js?<%=new Date().getTime()%>"></script>
</head>
<body>
<!-- hosts文件使用单选方式操作，多选时点击编辑选择状态会取消 -->
	<table id="dg" title="<spring:message code="HostAddressList"/>" class="easyui-datagrid" data-options="
			url:'${pageContext.request.contextPath}/hostaddr/list.action',
			rownumbers:true,
			singleSelect:true,
			fit:true,
			striped:true,
			border:false,
			loadFilter:loadFilter,
			pagination:true,
			pageList: [10,20,30,40,50,60],
			pageNumber:${pageBean.page},
			toolbar: '#tb',
			onClickCell:onClickCell,
			pageSize: ${pageBean.pageSize}">
		<thead>
		<tr>
			<th data-options="field:'ck',checkbox:true"></th>
			<th data-options="width:'30%',field:'ip',editor:{
					type:'textbox',
					options:{
						validType:'ip'
					}
				},
				formatter:function(value,row,rowIndex){
					return '<b>'+value+'</b>';
				}"><spring:message code="IPAddress"/></th>
			<th data-options="width:'50%',field:'host',editor:{
					type:'textbox',options:{}},
					formatter:function(value,row,rowIndex){
						return '<b>'+value+'</b>';
					}"><spring:message code="HostName"/></th>
			<!-- 
			<th data-options="field:'cardNum',hidden:true,editor:{
					type:'combobox',
					options:{
						url:'${pageContext.request.contextPath}/card/listjsonarr.action',
						valueField:'cardNum',
						onLoadSuccess:onLoadSuccess,
						textField:'name',
						panelHeight:'auto',
						required:true
					}
				}"><spring:message code="CardNumber"/></th> 
			-->
			<th data-options="field:'修改',
				formatter:function(value,row,rowIndex){
					return '<a href=# ><spring:message code="Update"/></a>';
				}"></th>
		</tr>
		</thead>
	</table>
	<div id="tb" style="height:auto">
	    <omc:permit url="hostaddr/add">
			<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="append()"><spring:message code="AddHostAddress"/></a>
	    </omc:permit>
	    <omc:permit url="hostaddr/delete">
			<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" 
				onclick="removeit('${pageContext.request.contextPath}/hostaddr/delete.action')"><spring:message code="DeleteHostAddress"/></a>
	    </omc:permit>
	</div>
	
	<div id="addHostAddrWindow" class="easyui-window" data-options="width:250,iconCls:'icon-save',modal:true,closed:true,collapsible:false,minimizable:false,maximizable:false">
        <form id="addHostAddrForm" method="post">
      		<br/>
      		<span>
	      		<label><spring:message code="IPAddress"/>：</label>
	      		<input class="easyui-textbox" id="addHostAddrIp" name="ip" data-options="validType:'ip',required:true">
      		</span>
      		<br/><br/>
      		<span>
	      		<label><spring:message code="Domain"/>：</label>
	      		<input class="easyui-textbox" id="addHostAddrHost" name="host" data-options="validType:'realmName',required:true">
      		</span>
      		<br/>
      		<div style="text-align:center;padding:5px">
			   	<a href="#" class="easyui-linkbutton" onclick="submitForm()"><spring:message code="save"/></a>
	    	</div>
      	</form>
	</div>
</body>
</html>