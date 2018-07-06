<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>波束系统信息3I列表</title>
<%@ include file="/WEB-INF/jsp/public/common.jspf"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/public/grid.js"></script>
<script type="text/javascript">

</script>
</head>
<body>
	<table id="dg" title="波束系统信息3I" class="easyui-datagrid" data-options="
				url:'${pageContext.request.contextPath}/grm/beamsysinfo3i/list.action',
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
			<th data-options="field:'ck',checkbox:true"></th>
			<th data-options="field:'gscSysInfo3IIndex',hidden:true"></th>
			<th data-options="field:'gscSysInfo3IAssignTime',width:150,
				formatter:function(value,row,rowIndex){
						if(typeof value==='number')
							return new Date(value*1000).format();
						return value;
					},
				editor:{
					type:'datetimebox',
					options:{
						showSeconds:true
					}
				}">资源分配生效时间</th>
			<th data-options="field:'gscSysInfo3IVersionID',width:'10%',editor:'textbox'">唯一标识</th>
			<th data-options="field:'gscSysInfo3IBeamID',
					editor:{type:'numberbox',options:{min:1,max:119}}">波束编号</th>
			<th data-options="field:'gscSysInfo3ILAIClass3PauseTimer',editor:'textbox'">T3115</th>
			<th data-options="field:'gscSysInfo3ILAIClass3RACHAccessTimer',editor:'textbox'">T3146</th>
			<th data-options="field:'gscSysInfo3ILAIClass3AlertTimer',editor:'textbox'">告警定时器，T3112</th>
			<th data-options="field:'gscSysInfo3ILAIClass3PagingTimer',editor:'textbox'">寻呼定时器</th>
			<th data-options="field:'gscSysInfo3IPeriodicLUTimer',editor:'textbox'">定时器T3212的值</th>
			<th data-options="field:'gscSysInfo3IDualModeHoldTimer',editor:'textbox'">gscSysInfo3IDualModeHoldTimer</th>
			<th data-options="field:'gscSysInfo3IPositionParaRACHPositionTimer',editor:'textbox'">RACH 位置定时器</th>
			<th data-options="field:'gscSysInfo3IPositionParaGPSUpdateTimer',editor:'textbox'">周期性GPS测量</th>
			<th data-options="field:'gscSysInfo3IPositionParaGPSUpdateDistance',editor:'textbox'">北斗更新距离</th>
			<th data-options="field:'gscSysInfo3IPositionParaGPSPositionAge',editor:'textbox'">gscSysInfo3IPositionParaGPSPositionAge</th>
			<th data-options="field:'gscSysInfo3IPositionParaPageGPSPositionAge',editor:'textbox'">gscSysInfo3IPositionParaPageGPSPositionAge</th>
			<th data-options="field:'gscSysInfo3IPositionParaPageResponseCurrentGPS',editor:'textbox'">gscSysInfo3IPositionParaPageResponseCurrentGPS</th>
			<th data-options="field:'gscSysInfo3IPositionParaPositionReportingRequired',editor:'textbox'">位置报告请求</th>
		</tr>
		</thead>
	</table>
	<div id="tb" style="height:auto">
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="append()">添加</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="removeit(
				'${pageContext.request.contextPath}/grm/beamsysinfo3i/delete.action')">删除</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save',plain:true" onclick="accept(
			'${pageContext.request.contextPath}/grm/beamsysinfo3i/update.action',
				'${pageContext.request.contextPath}/grm/beamsysinfo3i/save.action')">保存</a>
	</div>
</body>
</html>