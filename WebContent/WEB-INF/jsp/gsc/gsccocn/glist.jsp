<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>GSC相关CN的信息</title>
<%@ include file="/WEB-INF/jsp/public/common.jspf"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/public/grid.js"></script>
<script type="text/javascript">

</script>
</head>
<body>
	<table id="dg" title="GSC相关CN的信息" class="easyui-datagrid" data-options="
				url:'${pageContext.request.contextPath}/gsc/gsccocn/list.action?grmCoGSCIp=${grmCoGSCIp}',
				rownumbers:true,
				fit:true,
				pageList: [10,20,30,40,50,60],
				pageNumber:${pageBean.page},
				pagination:true,
				toolbar: '#tb',
				onClickCell: onClickCell,
				loadFilter:loadFilter,
				pageSize: ${pageBean.pageSize}">
		<thead>
		<tr>
			<th data-options="field:'ck',checkbox:true">全选</th>
			<th data-options="field:'gscCoCNIndex',hidden:true"></th>
			<th data-options="field:'gscCoCNStatus',width:'15%',editor:'numberbox'">CN的工作模式</th>
			<th data-options="field:'gscCoCNIp',editor:'textbox'">主CN的IP</th>
			<th data-options="field:'gscCoCNSctpPortLocal',editor:'numberbox'">与主CN进行SCTP连接时本地端口号</th>
			<th data-options="field:'gscCoCNSctpPortRemote',editor:'numberbox'">与主CN进行SCTP连接时CN端口</th>
		</tr>
		</thead>
	</table>
	<div id="tb" style="height:auto">
		<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="append()">添加</a>
		<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="removeit(
				'${pageContext.request.contextPath}/gsc/gsccocn/delete.action?grmCoGSCIp=${grmCoGSCIp}')">删除</a>
		<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-save',plain:true" onclick="accept(
			'${pageContext.request.contextPath}/gsc/gsccocn/update.action',
				'${pageContext.request.contextPath}/gsc/gsccocn/save.action?grmCoGSCIp=${grmCoGSCIp}')">保存</a>
	</div>
</body>
</html>