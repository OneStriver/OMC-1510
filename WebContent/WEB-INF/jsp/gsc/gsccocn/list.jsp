<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<title>GSC相关CN的信息</title>
<%@ include file="/WEB-INF/jsp/public/common.jspf"%>
<script type="text/javascript">
function loadFilter(data){
	if(data.error)
		$.messager.alert('出错提示',data.error,'error');
	return data;
}
</script>
</head>
<body>
	<table id="dg" style="width:700px;height:250px" class="easyui-datagrid" data-options='
			title:"GSC相关CN的信息",
			url:"${pageContext.request.contextPath}/grm/grmcogscinfo/list.action",
			rownumbers:true,
			fit:true,
			pageList: [1,2,3,4,5,50],
			pageNumber:${pageBean.page},
			pagination:true,
			singleSelect:true,
			//fitColumns:true,
			loadFilter:loadFilter,
			pageSize: ${pageBean.pageSize}
			'>
		<thead>
			<tr>
				<th data-options= "field:'grmCoGSCIndex',hidden:true"></th>
				<th data-options= "field:'grmCoGSCIp',width:'40%'">被控目标GSC的IP</th>
				<th data-options= "field:'grmCoGSCPort',width:'40%'">被控目标GSC的端口号</th>
				<th data-options= "field:'edit',
						formatter:function(value,row,rowIndex){
							return '<a href=${pageContext.request.contextPath}/gsc/gsccocn/glistUI.action?grmCoGSCIp='+row.grmCoGSCIp+'>查看此GSC相关CN信息</a>';
						}"></th>
			</tr>
		</thead>
	</table>
</body>
</html>