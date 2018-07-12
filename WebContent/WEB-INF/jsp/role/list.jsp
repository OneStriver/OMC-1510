<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>角色列表</title>
<%@ include file="/WEB-INF/jsp/public/common.jspf"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/public/grid.js?<%=new Date().getTime()%>"></script>
<script type="text/javascript">

function append(){
	window.location='${pageContext.request.contextPath}/role/addUI.action';
}

</script>
</head>
<body>
	<table id="dg" class="easyui-datagrid" title='<spring:message code="RoleList"></spring:message>'
			data-options="
				fit:true,
				striped:true,
				border:false,
				rownumbers:true,
				pageList: [10,20,30,40,50],
				pageNumber:${pageBean.page},
				pageSize: ${pageBean.pageSize},
				pagination:true,
				url: '${pageContext.request.contextPath}/role/list.action',
				iconCls: 'icon-edit',
				toolbar: '#tb'
			">
	<thead>
		<tr>
			<th data-options="field:'ck',checkbox:true"></th>
			<th data-options="field:'id',hidden:true"></th>
			<th data-options="field:'name',width:'10%'"><spring:message code="RoleName"/></th>
			<th data-options="field:'description',width:'50%'"><spring:message code="Description"/></th>
			<th data-options="field:'edit',
				formatter:function(value,row,rowIndex){
							return '<a href=${pageContext.request.contextPath}/role/updateUI.action?id='+row.id+'><spring:message code="Edit"></spring:message><a/>';
						}"><spring:message code="Edit"/></th>
		</tr>
	</thead>
	</table>
	<div id="tb" style="height:auto">
		<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="append()"><spring:message code="Add"/></a>
		<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" 
			onclick="removeit('${pageContext.request.contextPath}/role/delete.action')"><spring:message code="Delete"/></a>
	</div>
</body>
</html>