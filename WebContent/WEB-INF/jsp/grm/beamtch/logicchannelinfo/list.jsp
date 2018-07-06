<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>逻辑信道信息</title>
<%@ include file="/WEB-INF/jsp/public/common.jspf"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/public/grid.js"></script>
<script type="text/javascript">
function accept(updateUrl){
	if (editIndex!=undefined && $('#dg').datagrid('validateRow', editIndex)){
		$('#dg').datagrid('endEdit',editIndex);
		var rows=$('#dg').datagrid('getRows');
		var json=JSON.stringify(rows);
		console.log(json);
		var data={json:json,gscCcchIndex:'${gscCcchIndex}',gscCcchCarrFreq:'${gscCcchCarrFreq}'};
		$.ajax({
			url:updateUrl,traditional:true,type:'post',
			data:data,
			success:function(data){//返回json结果
				var data=$.parseJSON(data);
				console.log(data);
				if(data.error){
					data='发生错误 '+data.error;
					$.messager.alert('出错提示',data,'error');
					$('#dg').datagrid('rejectChanges',editIndex);
				}else{
					$.messager.show({
						title:'操作提示',
						msg:data,
						showType:'fade',timeout:1500,
						style:{
							right:'',bottom:'',
							top:document.body.scrollTop+document.documentElement.scrollTop
						}
					});
				}
				
			}
		});
		editIndex = undefined;
		isInsert = undefined;
	}
}
</script>
</head>
<body>
	<table id="dg" title="逻辑信道信息" class="easyui-datagrid" data-options="
				url:'${pageContext.request.contextPath}/grm/logicchannelinfo/list.action?gscCcchIndex=${gscCcchIndex}&&gscCcchCarrFreq=${gscCcchCarrFreq}',
				rownumbers:true,
				fit:true,
				singleSelect:true,
				autoRowHeight:false,
				pagination:true,
				toolbar: '#tb',
				loadFilter:loadFilter,
				onClickCell: onClickCell">
		<thead>
		<tr>
			<!-- <th data-options="field:'ck',checkbox:true"></th> -->
			<th data-options="field:'gscCcchIndex',hidden:true,
				formatter:function(value,row,rowIndex){
						return ${gscCcchIndex};
					}"></th>
			<th data-options="field:'gscCcchCarrFreq',
				formatter:function(value,row,rowIndex){
						return ${gscCcchCarrFreq};
					}">频点号</th>
			<th data-options="field:'gscCcchLogicChannelType',editor:'textbox'">逻辑信道类型</th>
			<th data-options="field:'gscCcchLogicChannelConfig',editor:'textbox'">逻辑信道配置信息</th>
		</tr>
		</thead>
	</table>
	<div id="tb" style="height:auto">
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="append()">添加</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="removeit()">删除</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save',plain:true" onclick="accept(
			'${pageContext.request.contextPath}/grm/beamccch/logicchannelinfo/update.action',
				'${pageContext.request.contextPath}/grm/beamccch/logicchannelinfo/update.action')">保存</a>
	</div>
</body>
</html>