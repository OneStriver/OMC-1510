<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>目标GSC配置信息</title>
<%@ include file="/WEB-INF/jsp/public/common.jspf"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/public/grid.js"></script>
<script type="text/javascript">

</script>
</head>
<body>
	<table id="dg" title="目标GSC配置列表" class="easyui-datagrid" data-options="
				url:'${pageContext.request.contextPath}/grm/grmcogscinfo/list.action',
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
			<th data-options="field:'grmCoGSCIndex',hidden:true"></th>
			<th data-options="field:'grmCoGSCIp',width:'15%',editor:'textbox'">被控目标GSC的IP</th>
			<th data-options="field:'grmCoGSCPort',
					editor:{
						type:'numberbox',
						options:{min:0,max:65535}
					}">被控目标GSC的端口号</th>
		</tr>
		</thead>
	</table>
	<div id="tb" style="height:auto">
		<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" 
			onclick="append()">添加</a>
		<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" 
			onclick="removeit('${pageContext.request.contextPath}/grm/grmcogscinfo/delete.action')">删除</a>
		<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-save',plain:true" 
			onclick="accept(
				'${pageContext.request.contextPath}/grm/grmcogscinfo/update.action',
				'${pageContext.request.contextPath}/grm/grmcogscinfo/save.action')">保存</a>
	</div>
</body>
</html>