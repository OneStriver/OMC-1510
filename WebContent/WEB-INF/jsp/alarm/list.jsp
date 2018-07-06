<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>配置项列表</title>
<%@ include file="/WEB-INF/jsp/public/common.jspf"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/public/grid.js"></script>
<script type="text/javascript">
 $(function(){
	 var row=$('#dg').datagrid('getRows');
		if(row.length==0){
			$.messager.alert('提示','当前无告警数据!!!');
		}
 });
</script>
</head>
<body>
	<table id="dg" class="easyui-datagrid" title="<spring:message code="AlarmList"></spring:message>"
		data-options="
			url:'${pageContext.request.contextPath}/alarm/list.action',
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
			<th data-options="field:'level'"><spring:message code="Level"></spring:message></th>
			<th data-options="field:'description'"><spring:message code="Description"></spring:message></th>
			<th data-options="field:'createDate',sortable:true,
				formatter:function(value,row,rowIndex){
					return new Date(value).format();
				}"><spring:message code="OperationDate"></spring:message></th>
		</tr>
	</thead>
	</tbody>
	</table>
	<div id="tb" style="height:auto">
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" 
			onclick="removeit('${pageContext.request.contextPath}/alarm/delete.action')"><spring:message code="Delete"></spring:message></a>
	</div>
</body>
</html>