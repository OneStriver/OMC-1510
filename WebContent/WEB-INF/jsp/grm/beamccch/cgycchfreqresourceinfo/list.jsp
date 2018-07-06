<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<title>资源分配信息</title>
<%@ include file="/WEB-INF/jsp/public/common.jspf"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/public/grid.js"></script>
<script type="text/javascript">
function accept(updateUrl){
	if (editIndex!=undefined && $('#dg').datagrid('validateRow', editIndex)){
		$('#dg').datagrid('endEdit',editIndex);
		var rows=$('#dg').datagrid('getRows');
		for(i in rows)
			delete rows[i].logicChannelInfos;
		
		var json=JSON.stringify(rows);
		console.log(json);
		$.ajax({
			url:updateUrl,traditional:true,data:{json:json},type:'post',
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
						msg:data.msg,
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
	<table id="dg" title="资源分配信息" class="easyui-datagrid" data-options="
				url:'${pageContext.request.contextPath}/grm/beamccch/cgycchfreqresourceinfo/list.action?gscCcchIndex=${gscCcchIndex}',
				rownumbers:true,
				fit:true,
				singleSelect:true,
				autoRowHeight:false,
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
			<th data-options="field:'gscCcchBeamId',
				formatter:function(value,row,rowIndex){
						return ${gscCcchBeamId};
					}">波束号</th>
			<th data-options="field:'gscCcchCarrFreq',editor:'textbox'">频点号</th>
			<th data-options="field:'gscCcchCarrUpPowerLimit',editor:'textbox'">频点上行功率门限</th>
			<th data-options="field:'gscCcchCarrDownPowerLimit',editor:'textbox'">频点下行功率门限</th>
			<th data-options="field:'gscCcchCarrDirection',editor:'textbox'">频点方向</th>
			<th data-options="field:'gscCcchCarrSlotNo',editor:'textbox'">开始时隙</th>
			<th data-options="field:'gscCcchCarrSlotLen',editor:'textbox'">时隙长度</th>
			<th data-options="field:'gscCcchCncarrType',editor:'textbox'">控制载波类型</th>
			<!-- LogicChannelInfo 逻辑信道信息 -->
			<th data-options="field:'showLogicChannelInfos',
				formatter:function(value,row,rowIndex){
						return '<a href=${pageContext.request.contextPath}/grm/beamccch/logicchannelinfo/listUI.action?gscCcchIndex='+
							${gscCcchIndex}+'&gscCcchCarrFreq='+row.gscCcchCarrFreq+'>详细信息</a>';
					}">逻辑信道信息</th>
		</tr>
		</thead>
	</table>
	<div id="tb" style="height:auto">
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="append()">添加</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="removeit()">删除</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save',plain:true" onclick="accept(
			'${pageContext.request.contextPath}/grm/beamccch/cgycchfreqresourceinfo/update.action?gscCcchIndex=${gscCcchIndex}')">保存</a>
	</div>
</body>
</html>