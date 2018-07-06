<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>波束系统信息4C列表</title>
<%@ include file="/WEB-INF/jsp/public/common.jspf"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/public/grid.js"></script>
<script type="text/javascript">

</script>
</head>
<body>
	<table id="dg" title="波束系统信息4C列表" class="easyui-datagrid" data-options="
				url:'${pageContext.request.contextPath}/grm/beamsysinfo4c/list.action',
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
			<th data-options="field:'gscSysInfo4CIndex',hidden:true"></th>
			<th data-options="field:'gscSysInfo4CAssignTime',width:150,
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
			<th data-options="field:'gscSysInfo4CVersionID',width:'10%',editor:'textbox'">唯一标识</th>
			<th data-options="field:'gscSysInfo4CBeamID',
					editor:{type:'numberbox',options:{min:1,max:119}}">波束编号</th>
			<th data-options="field:'gscSysInfo4CHHTSQT',editor:'textbox'">针对额外功率Class0的终端</th>
			<th data-options="field:'gscSysInfo4CHHTDataSQT',editor:'textbox'">在fax和data期间，针对ExtendedPowerClass0终端的SQT值</th>
			<th data-options="field:'gscSysInfo4CVTSQT',editor:'textbox'">终端的SQT值</th>
			<th data-options="field:'gscSysInfo4CVTDataSQT',editor:'textbox'">预留配置接口</th>
			<th data-options="field:'gscSysInfo4CFTSQT',editor:'textbox'">Class 6</th>
			<th data-options="field:'gscSysInfo4CFTDataSQT',editor:'textbox'">预留配置接口</th>
			<th data-options="field:'gscSysInfo4CPANinit',editor:'textbox'">预留配置接口</th>
			<th data-options="field:'gscSysInfo4CPANmin',editor:'textbox'">预留配置接口</th>
			<th data-options="field:'gscSysInfo4CPANmax',editor:'textbox'">预留配置接口</th>
			<th data-options="field:'gscSysInfo4CGainUp',editor:'textbox'">预留配置接口</th>
			<th data-options="field:'gscSysInfo4CGainDn',editor:'textbox'">预留配置接口</th>
			<th data-options="field:'gscSysInfo4COlthresh',editor:'textbox'">预留配置接口</th>
			<th data-options="field:'gscSysInfo4COlupGain',editor:'textbox'">预留配置接口</th>
			<th data-options="field:'gscSysInfo4COldnGain',editor:'textbox'">预留配置接口</th>
			<th data-options="field:'gscSysInfo4CVarUp',editor:'textbox'">预留配置接口</th>
			<th data-options="field:'gscSysInfo4CVarDn',editor:'textbox'">预留配置接口</th>
			<th data-options="field:'gscSysInfo4CSQIfactor',editor:'textbox'">预留配置接口</th>
			<th data-options="field:'gscSysInfo4CMestep',editor:'textbox'">预留配置接口</th>
			<th data-options="field:'gscSysInfo4CLQIn1',editor:'textbox'">预留配置接口</th>
			<th data-options="field:'gscSysInfo4CLQIn2',editor:'textbox'">预留配置接口</th>
		</tr>
		</thead>
	</table>
	<div id="tb" style="height:auto">
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="append()">添加</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="removeit(
				'${pageContext.request.contextPath}/grm/beamsysinfo4c/delete.action')">删除</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save',plain:true" onclick="accept(
			'${pageContext.request.contextPath}/grm/beamsysinfo4c/update.action',
				'${pageContext.request.contextPath}/grm/beamsysinfo4c/save.action')">保存</a>
	</div>
</body>
</html>