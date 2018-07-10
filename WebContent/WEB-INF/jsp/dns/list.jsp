<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<title>主机名称及DNS配置</title>
<%@ include file="/WEB-INF/jsp/public/common.jspf"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/public/grid.js?<%=new Date().getTime()%>"></script>
<script type="text/javascript">

</script>
</head>
<body>
	<table id="dg" title="<spring:message code="HostNameAndDNSList"></spring:message>" class="easyui-datagrid" data-options="
			url:'${pageContext.request.contextPath}/dns/list.action',
			rownumbers:true,
			singleSelect:true,
			fit:true,
			striped:true,
			border:false,
			loadFilter:loadFilter,
			pageList: [10,20,30,40,50,60],
			pageNumber:${pageBean.page},
			pagination:true,
			toolbar: '#tb',
			onClickCell:onClickCell,
			pageSize: ${pageBean.pageSize}">
		<thead>
		<tr>
			<!-- <th data-options="field:'ck',checkbox:true"></th> -->
			<th data-options="field:'host',editor:'textbox',formatter:function(value,row,rowIndex){
						return '<b>'+value+'</b>';
					}"><spring:message code="HostName"></spring:message></th>
			<th data-options="width:'25%',field:'dns1',editor:'textbox'">DNS【1】</th>
			<th data-options="width:'25%',field:'dns2',editor:'textbox'">DNS【2】</th>
			<th data-options="width:'25%',field:'dns3',editor:'textbox'">DNS【3】</th>

		</tr>
		</thead>
	</table>
	<div id="tb" style="height:auto">
		<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" 
			onclick="accept('${pageContext.request.contextPath}/dns/update.action',
				'${pageContext.request.contextPath}/dns/save.action')"><spring:message code="Save"></spring:message></a>
	</div>
</body>
</html>