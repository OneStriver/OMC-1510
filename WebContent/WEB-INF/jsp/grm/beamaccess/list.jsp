<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>无线接入参数列表</title>
<%@ include file="/WEB-INF/jsp/public/common.jspf"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/public/grid.js"></script>
<script type="text/javascript">

</script>
</head>
<body style="padding:0">
	<table id="dg" title="无线接入参数列表" class="easyui-datagrid" data-options="
			url:'${pageContext.request.contextPath}/grm/beamaccess/list.action',
			rownumbers:true,
			fit:true,
			pageList: [10,20,30,40,50,60],
			pageNumber:${pageBean.page},
			pagination:true,
			toolbar: '#tb',
			loadFilter:loadFilter,
			onClickCell: onClickCell,
			pageSize: ${pageBean.pageSize}">
		<thead>
		<tr>
			<th data-options="field:'ck',checkbox:true"></th>
			<th data-options="field:'gscBeamAccessIndex',hidden:true"></th>
			<th data-options="field:'gscBeamAccessBeamId',
						editor:{type:'numberbox',options:{min:1,max:119}}">所在波束号</th>
			<th data-options="field:'gscBeamAccessMinSigValue',editor:'numberbox'">允许接入最小接收电平</th>
			<th data-options="field:'gscBeamAccessTimeoutCounter',editor:'numberbox'">无线链路超时计数器</th>
			<th data-options="field:'gscBeamAccessAreaUpdatePeriod',editor:'numberbox'">位置更新周期</th>
			<th data-options="field:'gscBeamAccessMaxRetry',editor:'numberbox'">最大重发次数</th>
			<th data-options="field:'gscBeamAccessDenyWaitTime',editor:'numberbox'">电路拒绝等待指示时长</th>
			<th data-options="field:'gscBeamAccessPermitBlockNum',editor:'numberbox'">接入准许保留块数</th>
			<th data-options="field:'gscBeamAccessUeMaxPower',editor:'numberbox'">终端控制信道最大功率</th>
			<th data-options="field:'gscBeamAccessDacchAssignTimeout',editor:'numberbox'">DACCH信道指派计时器值</th>
			<th data-options="field:'gscBeamAccessDtchAssignTimeout',editor:'numberbox'">DTCH信道指派计时器值</th>
			<th data-options="field:'gscBeamAccessDtchDelayReleaseTimeout',editor:'numberbox'">DTCH信道延时释放时间值</th>
			<th data-options="field:'gscBeamAccessPacketRemain',editor:'numberbox'">分组信道保持值</th>
			<th data-options="field:'gscBeamAccessRouteUpdatePeriod',editor:'numberbox'">路由区更新周期</th>
			<th data-options="field:'gscBeamAccessPacketWaitUpAssignTimeout',editor:'numberbox'">等待分组上行指配计时器值</th>
			<th data-options="field:'gscBeamAccessPacketDenyWaitTimeout',editor:'numberbox'">分组拒绝等待指示时长</th>
			<th data-options="field:'gscBeamAccessPdtchAssignTimeout',editor:'numberbox'">PDTCH信道指派计时器值</th>
			<th data-options="field:'gscBeamAccessPacketRqTimeout',editor:'numberbox'">分组资源请求计时器</th>
			<th data-options="field:'gscBeamAccessReportTime',width:150,
						formatter:function(value,row,rowIndex){
							if(typeof value==='number'){
								return new Date(value*1000).format();
							}
							return value;
						},
						editor:{
							type:'datetimebox',
							options:{
								editable:false,
								showSeconds:true
							}
						}">OSS发送参数时间</th>
		</tr>
		</thead>
	</table>
	<div id="tb" style="height:auto">
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="append()">添加</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="removeit(
				'${pageContext.request.contextPath}/grm/beamaccess/delete.action')">删除</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save',plain:true" onclick="accept(
			'${pageContext.request.contextPath}/grm/beamaccess/update.action',
				'${pageContext.request.contextPath}/grm/beamaccess/save.action')">保存</a>
	</div>
</body>
</html>