<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>GSC配置信息</title>
<%@ include file="/WEB-INF/jsp/public/common.jspf"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/public/grid.js"></script>
<script type="text/javascript">

</script>
</head>
<body style="padding:0">
	<table id="dg" title="GSC配置信息列表" class="easyui-datagrid" data-options="
			url:'${pageContext.request.contextPath}/gsc/gscconfig/list.action',
			rownumbers:true,
			fit:true,
			pageList: [10,20,30,40,50,60],
			pageNumber:${pageBean.page},
			pagination:true,
			toolbar: '#tb',
			singleSelect:true,
			loadFilter:loadFilter,
			onClickCell: onClickCell,
			pageSize: ${pageBean.pageSize}">
		<thead>
		<tr>
			<!-- <th data-options="field:'ck',checkbox:true"></th> -->
			<!-- <th data-options="field:'gscOmuIp',editor:'textbox'">gscOmuIp</th>-->
			<!-- <th data-options="field:'gscOmuPort',editor:'numberbox'">gscOmuPort</th>  -->
			<!-- <th data-options="field:'gscSoftwareStatus',editor:'numberbox'">gsc功能运行状态</th> -->
			<th data-options="field:'gscCoGRMIp',editor:'textbox'">信关站所属GRM的IP</th>
			<th data-options="field:'gscCoGRMPortRemote',editor:'numberbox'">信关站向GRM连接时GRM方的端口号</th>
			<th data-options="field:'gscCoGRMPortLocal',editor:'numberbox'">信关站向GRM连接时GSC方的端口号</th>
			<th data-options="field:'gscCoCDUIp',editor:'textbox'">信关站相关CUD的IP</th>
			<th data-options="field:'gscCoCDUPortRemote',editor:'numberbox'">信关站向GRM连接时CDU方的端口号</th>
			<th data-options="field:'gscCoCDUPortLocal',editor:'numberbox'">信关站向GRM连接时CDU方的端口号</th>
			<th data-options="field:'gscCoGTSNbapPortLocal',editor:'numberbox'">信关站从GTS接受nbap消息所用端口</th>
			<th data-options="field:'gscCoGTSFpSendPortLocal',editor:'numberbox'">信关站向GTS发送Fp消息所用端口</th>
			<th data-options="field:'gscCoGTSFpRecvPortLocal',editor:'numberbox'">信关站从GTS接受Fp消息所用端口</th>
			<th data-options="field:'gscCoBMCPortLocal',editor:'numberbox'">gscCoBMCPortLocal</th>
			<th data-options="field:'gscCoBMUPortLocal',editor:'numberbox'">gscCoBMUPortLocal</th>
		</tr>
		</thead>
	</table>
	<div id="tb" style="height:auto">
		<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-save',plain:true" onclick="accept(
			'${pageContext.request.contextPath}/gsc/gscconfig/update.action',
				'${pageContext.request.contextPath}/gsc/gscconfig/update.action')">保存</a>
	</div>
</body>
</html>