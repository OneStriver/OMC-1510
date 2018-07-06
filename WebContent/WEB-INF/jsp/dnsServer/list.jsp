<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<title>域名配置</title>
<%@ include file="/WEB-INF/jsp/public/common.jspf"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/public/grid.js?<%=new Date().getTime()%>"></script>

</head>
<body>
	<table id="dg" title="<spring:message code="DnsManager"/>" class="easyui-datagrid" data-options="
			url:'${pageContext.request.contextPath}/dnsServer/list.action',
			rownumbers:true,
			fit:true,striped:true,border:false,
			loadFilter:loadFilter,
			pageNumber:${pageBean.page},
			toolbar: '#tb',
			onClickCell:onClickCell,
			pageList:[10,20,30,40,50],
			pagination:true,
			pageSize: ${pageBean.pageSize}">
		<thead>
		<tr>
			<th data-options="field:'ck',checkbox:false"></th>
			<th data-options="field:'name',
				formatter:function(value,row,rowIndex){
					return '<a href=${pageContext.request.contextPath}/dnsServer/listAxfrUI.action?domain='+value+'>'+value+'</a>';
				}"><spring:message code="DnsDomain"/></th>
			<th data-options="field:'clazz'"><spring:message code="DnsClass"/></th>
			<th data-options="field:'serial'"><spring:message code="DnsSerial"/></th>
		</tr>
		</thead>
	</table>
</body>
</html>