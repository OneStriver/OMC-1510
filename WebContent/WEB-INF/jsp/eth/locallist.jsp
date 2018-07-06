<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>网卡列表</title>
<%@ include file="/WEB-INF/jsp/public/common.jspf"%>

<script type="text/javascript">
function append(){}
function removeit(){}
function accept(){}
function onClickCell(){}

</script>
</head>
<body>
	<table id="dg" class="easyui-datagrid" title="网卡列表" style="width:800px;height:auto"
			data-options="
				iconCls: 'icon-edit',
				singleSelect: true,
				toolbar: '#tb',
				onClickCell: onClickCell
			">
	<thead>
		<tr>
			<th data-options="field:'sn',width:'50px',align:'center'">序号</th>
			<th data-options="field:'name',width:'10%'">网卡名称</th>
			<th data-options="field:'ip',width:'20%'">IP地址</th>
			<th data-options="field:'mask',width:'10%'">子网掩码</th>
			<th data-options="field:'gateway',width:'10%'">默认网关</th>
			<th data-options="field:'mtu',width:'50px'">MTU</th>
			<th data-options="field:'up',width:'70px'">是否工作</th>
			<th data-options="field:'virtual',width:'70px'">虚拟网卡</th>
			<th data-options="field:'hardAddr',width:'20%'">硬件地址</th>
		</tr>
	</thead>
	<tbody>	
	<c:forEach items="${eths}" var="eth" varStatus="status">
		<tr>
			<td>${status.count}</td>
			<td>${eth.name}</td>
			<td>${eth.ip}</td>
			<td>${eth.mask}</td>
			<td>${eth.gateway}</td>
			<td>${eth.mtu}</td>
			<td>${eth.up}</td>
			<td>${eth.virtual}</td>
			<td>${eth.hardAddr}</td>
		</tr>
	</c:forEach>
	</tbody>
	</table>
	<div id="tb" style="height:auto">
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="append()">添加</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="removeit()">删除</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save',plain:true" onclick="accept()">保存</a>
	</div>
</body>
</html>