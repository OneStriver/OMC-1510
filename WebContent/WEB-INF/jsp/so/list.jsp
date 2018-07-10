<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<title>动态库管理</title>
<%@ include file="/WEB-INF/jsp/public/common.jspf"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/public/grid.js?<%=new Date().getTime()%>"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/so/so.js"></script>
</head>
<body>
	<table id="dg" title="<spring:message code="StaticLibraryList"></spring:message>" class="easyui-datagrid" data-options="
			url:'${pageContext.request.contextPath}/so/list.action',
			rownumbers:true,
			singleSelect:false,
			loadFilter:loadFilter,
			fit:true,striped:true,
			pageList: [10,20,30,40,50,60],
			pageNumber:${pageBean.page},
			pagination:true,
			toolbar: '#tb',
			pageSize: ${pageBean.pageSize}">
		<thead>
		<tr>
			<th data-options="field:'ck',checkbox:true"></th>
			<th data-options="field:'name',width:'20%',
				formatter:function(value,row,rowIndex){
						return '<b>'+value+'</b>';
					}"><spring:message code="StaticLibraryFileName"></spring:message></th>
			<th data-options="field:'updateDate',width:'20%',
				formatter:function(value,row,rowIndex){
						return new Date(value).format();
					}
				"><spring:message code="UpdateDate"></spring:message></th>
		</tr>
		</thead>
	</table>
	<div id="tb" style="height:auto">
		<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" 
			onclick="$('#dynamicLibraryWindow').window('open')"><spring:message code="AddLibraryFile"/></a>
		<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" 
			onclick="$('#addEncryptLibraryWindow').window('open')">添加加密库</a>
		<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" 
			onclick="removeit('${pageContext.request.contextPath}/so/delete.action')"><spring:message code="DeleteLibraryFile"/></a>
	</div>
	
	<div id="dynamicLibraryWindow" class="easyui-window" title="<spring:message code="LibraryFileUpload"/>" data-options="modal:true,closed:true,collapsible:false,minimizable:false,maximizable:false,iconCls:'icon-save'" style="text-align:center;width:300px;height:200px;padding:10px;">
		<form id="dynamicLibraryForm" method="post" enctype="multipart/form-data" action="${pageContext.request.contextPath}/so/upload.action">
			<label><spring:message code="soFormat"/></label><br/><br/>
			<input class="easyui-filebox" name="file" data-options="onChange:function(){
				var inputFileName = $(this).filebox('getValue');
				var fileName = inputFileName.substring(inputFileName.lastIndexOf('\\')+1,(inputFileName.length));
				$(this).filebox('setValue',fileName);
			},buttonText:'<spring:message code="SelectTheNEPackage"/>',required:true,prompt:'<spring:message code="SelectZIPPackage"/>'" style="width:100%"/>
			<br/><br/>
			<a href="#" class="easyui-linkbutton" onclick="dynamicLibrarySubmitForm()"><spring:message code="Upload"/></a>
		</form>
	</div>
	
	<div id="addEncryptLibraryWindow" class="easyui-window" title="上传授权库" data-options="modal:true,closed:true,collapsible:false,minimizable:false,maximizable:false,iconCls:'icon-save'" style="text-align:center;width:300px;height:200px;padding:10px;">
		<form id="addEncryptLibraryForm" method="post" enctype="multipart/form-data" action="${pageContext.request.contextPath}/so/authUpload.action">
			<label><spring:message code="soFormat"/></label><br/><br/>
			<input class="easyui-filebox" name="file" data-options="onChange:function(){
				var inputFileName = $(this).filebox('getValue');
				var fileName = inputFileName.substring(inputFileName.lastIndexOf('\\')+1,(inputFileName.length));
				$(this).filebox('setValue',fileName);
			},buttonText:'<spring:message code="SelectTheNEPackage"/>',required:true,prompt:'<spring:message code="SelectZIPPackage"/>'" style="width:100%"/>
			<br/><br/>
			<a href="#" class="easyui-linkbutton" onclick="addEncryptLibrarySubmitForm()"><spring:message code="Upload"/></a>
		</form>
	</div>
</body>
</html>