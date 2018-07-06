<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>波束同步信息列表</title>
<%@ include file="/WEB-INF/jsp/public/common.jspf"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/public/grid.js"></script>
<script type="text/javascript">

</script>
</head>
<body>
	<table id="dg" title="波束同步信息列表" class="easyui-datagrid" data-options="
				url:'${pageContext.request.contextPath}/grm/beamsynchro/list.action',
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
			<th data-options="field:'gscBeamSyncIndex',hidden:true"></th>
			<th data-options="field:'gscBeamSyncAssignTime',width:150,
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
				}">资源分配生效时间</th>
			<th data-options="field:'gscBeamSyncVersionID',width:'15%',editor:'textbox'">唯一标识</th>
			<th data-options="field:'gscBeamSyncBeamID',
					editor:{type:'numberbox',options:{min:1,max:119}}">波束编号</th>
			<th data-options="field:'gscBeamSyncCcchFreqNum',editor:'numberbox'">频点号</th>
			<th data-options="field:'gscBeamSyncSacId',editor:'textbox'">服务区码</th>
			<th data-options="field:'gscBeamSyncLacId',editor:'textbox'">位置区号码</th>
			<th data-options="field:'gscBeamSyncRacId',editor:'numberbox'">路由区编码</th>
			<th data-options="field:'gscBeamSyncPlmnMcc',editor:'textbox'">移动国家号码</th>
			<th data-options="field:'gscBeamSyncPlmnMnc',editor:'textbox'">移动网号</th>
		</tr>
		</thead>
	</table>
	<div id="tb" style="height:auto">
		<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="append()">添加</a>
		<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="removeit(
				'${pageContext.request.contextPath}/grm/beamsynchro/delete.action')">删除</a>
		<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-save',plain:true" onclick="accept(
			'${pageContext.request.contextPath}/grm/beamsynchro/update.action',
				'${pageContext.request.contextPath}/grm/beamsynchro/save.action')">保存</a>
	</div>
</body>
</html>