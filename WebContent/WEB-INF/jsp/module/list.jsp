<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>资源包管理</title>
<%@ include file="/WEB-INF/jsp/public/common.jspf"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/module/list.js?<%=new Date().getTime()%>"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/public/grid.js?<%=new Date().getTime()%>"></script>
</head>
<body>
	<table id="dg" title="<spring:message code="NEBasicInformationList"/>" class="easyui-datagrid" data-options="
				url:'${pageContext.request.contextPath}/module/list.action',
				onRowContextMenu:onRowContextMenu,
				rownumbers:true,
				fit:true,striped:true,border:false,
				pageList: [10,20,30,40,50,60],
				pageNumber:${pageBean.page},
				pagination:true,
				toolbar: '#tb',
				loadFilter:loadFilter,
				pageSize: ${pageBean.pageSize}">
		<thead>
		<tr>
			<th data-options="field:'ck',checkbox:true"><spring:message code="AllSelect"/></th>
			<th data-options="field:'id'">网元ID</th>
			<th data-options="field:'name',width:150,
				formatter:function(value,row,rowIndex){
						return '<b>'+value+'</b>';
					}"><spring:message code="NEName"/></th>
			<th data-options="field:'belong',width:'15%',editor:'textbox'"><spring:message code="OwnedBusiness"/></th>
			<th data-options="field:'description',editor:'textbox'"><spring:message code="NEDescription"/></th>
			<th data-options="field:'version',editor:'textbox'"><spring:message code="NEVersion"/></th>
			<th data-options="field:'exe',editor:'textbox'"><spring:message code="ExecutableFile"/></th>
			<th data-options="field:'log',editor:'textbox'"><spring:message code="LogDirectory"/></th>
		</tr>
		</thead>
	</table>
	
	<!-- 表头 -->
	<div id="tb" style="height:auto">
		<!-- 通过单个ZIP包 -->
		<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" 
			onclick="$('#singleWindow').window('open');$('input[name=file]').attr('multiple',true);"><spring:message code="Add"/></a>
		<!-- 批量添加多个ZIP包 -->
		<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="$('#mutipleWindow').window('open')"><spring:message code="BatchAdd"/></a>
		<!-- 删除网元 -->
		<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="removeit(
				'${pageContext.request.contextPath}/module/delete.action')"><spring:message code="Delete"/></a>
	</div>
	
	<!-- 单个ZIP包 -->
	<div id="singleWindow" class="easyui-window" title="<spring:message code="UploadNE"/>" data-options="modal:true,closed:true,collapsible:false,minimizable:false,maximizable:false,iconCls:'icon-save'" style="text-align:center;width:300px;height:200px;padding:10px;">
		<form id="singleForm" method="post" enctype="multipart/form-data" action="${pageContext.request.contextPath}/module/upload.action">
			<label>网元ID</label>
			<input class="easyui-numberbox" name="moduleId" data-options="validType:['minValue[1]','maxValue[255]'],prompt:'请输入1到255'"/>(选填)
			<br/><br/>
			<input class="easyui-filebox" name="file" multiple="multiple" style="width:100%"
				data-options="onChange:function(){
				var inputFileName = $(this).filebox('getValue');
				var fileName = inputFileName.substring(inputFileName.lastIndexOf('\\')+1,(inputFileName.length));
				$(this).filebox('setValue',fileName);
			},validType:'zipFile',buttonText:'选择网元压缩包',required:true,prompt:'请选择要上传的网元ZIP包'" />
			<br/><br/>
			<a href="#" class="easyui-linkbutton" onclick="submitModuleForm('singleForm')"><spring:message code="UploadNE"/></a>
		</form>
	</div>
	
	<!-- 批量添加 -->
	<div id="mutipleWindow" class="easyui-window" title="<spring:message code="UploadNE"/>" data-options="modal:true,closed:true,collapsible:false,minimizable:false,maximizable:false,iconCls:'icon-save'" style="text-align:center;width:300px;height:200px;padding:10px;">
		<form id="mutipleForm" method="post" enctype="multipart/form-data" action="${pageContext.request.contextPath}/module/uploadAll.action">
			<label><spring:message code="ZIPFormat"/></label><br/><br/>
			<input class="easyui-filebox" name="file" data-options="onChange:function(){
				var inputFileName = $(this).filebox('getValue');
				var fileName = inputFileName.substring(inputFileName.lastIndexOf('\\')+1,(inputFileName.length));
				$(this).filebox('setValue',fileName);
			},buttonText:'<spring:message code="SelectTheNEPackage"/>',required:true,prompt:'<spring:message code="SelectZIPPackage"/>'" style="width:100%"/>
			<br/><br/>
			<a href="#" class="easyui-linkbutton" onclick="submitModuleForm('mutipleForm')"><spring:message code="UploadNE"/></a>
		</form>
	</div>
	
	<!-- 文件管理器 -->
	<div id="filesWindow" class="easyui-window" 
		data-options="modal:true,width:800,height:400,title:'<spring:message code="FileManager"></spring:message>',
			maximized:true,minimizable:false,draggable:true,closed:true,iconCls:'icon-ok'">
		<iframe style="width:100%;height:100%;border:0;scrolling:auto" id="content"></iframe>
	</div>
</body>
</html>