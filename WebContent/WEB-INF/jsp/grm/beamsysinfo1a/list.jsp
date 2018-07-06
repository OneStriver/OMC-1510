<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>波束系统信息1A列表</title>
<%@ include file="/WEB-INF/jsp/public/common.jspf"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/public/grid.js"></script>
<script type="text/javascript">

</script>
</head>
<body>
	<table id="dg" title="波束系统信息1A列表" class="easyui-datagrid" data-options="
				url:'${pageContext.request.contextPath}/grm/beamsysinfo1a/list.action',
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
			<th data-options="field:'gscSysInfo1AIndex',hidden:true"></th>
			<th data-options="field:'gscSysInfo1AAssignTime',width:150,
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
			<th data-options="field:'gscSysInfo1AVersionID',width:'10%',editor:'textbox'">唯一标识</th>
			<th data-options="field:'gscSysInfo1ABeamID',
					editor:{type:'numberbox',options:{min:1,max:119}}">波束编号</th>
			<th data-options="field:'gscSysInfo1ARachControlMaxRetrans',editor:'textbox'">最大的重传次数</th>
			<th data-options="field:'gscSysInfo1ARachControlAccessClasses',editor:'textbox'">接入等级</th>
			<th data-options="field:'gscSysInfo1ARachControlCellBarAccess',editor:'textbox'">禁止等级</th>
			<th data-options="field:'gscSysInfo1AMiscInfoClass1SBReselectionHystersis',editor:'numberbox'">gscSysInfo1AMiscInfoClass1SBReselectionHystersis</th>
		</tr>
		</thead>
	</table>
	<div id="tb" style="height:auto">
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="append()">添加</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="removeit(
			'${pageContext.request.contextPath}/grm/beamsysinfo1a/delete.action')">删除</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save',plain:true" onclick="accept(
			'${pageContext.request.contextPath}/grm/beamsysinfo1a/update.action',
				'${pageContext.request.contextPath}/grm/beamsysinfo1a/save.action')">保存</a>
	</div>
</body>
</html>