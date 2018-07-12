<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>配置项列表</title>
<%@ include file="/WEB-INF/jsp/public/common.jspf"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/public/grid.js?<%=new Date().getTime()%>"></script>
</head>
<body>
	<table id="dg" class="easyui-datagrid" title="<spring:message code="OperationLogList"></spring:message>"
		data-options="
			url:'${pageContext.request.contextPath}/log/list.action',
			rownumbers:true,
			fit:true,striped:true,border:false,
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
			<th data-options="field:'user'"><spring:message code="User"/></th>
			<th data-options="field:'description'"><spring:message code="OperationAction"/></th>
			<th data-options="field:'success',sortable:false,
				formatter:function(value,row,rowIndex){
					return value?'<span style=color:green><spring:message code="Success"></spring:message></span>':
						'<span style=color:red><spring:message code="Failure"></spring:message></span>';
				}"><spring:message code="SuccessOrFailure"/></th>
			<th data-options="field:'reason'"><spring:message code="ReasonsForTheFailure"/></th>
			<th data-options="field:'createDate',sortable:true,
				formatter:function(value,row,rowIndex){
					return new Date(value).format();
				}"><spring:message code="OperationDate"/></th>
		</tr>
	</thead>
	</tbody>
	</table>
	<div id="tb" style="height:auto">
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" 
			onclick="removeit('${pageContext.request.contextPath}/log/delete.action')"><spring:message code="Delete"></spring:message></a>
	</div>
</body>
</html>