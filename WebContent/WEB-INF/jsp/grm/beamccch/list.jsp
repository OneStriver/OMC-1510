<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>广播与公共控制信道预分配资源信息</title>
<%@ include file="/WEB-INF/jsp/public/common.jspf"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/public/grid.js"></script>
<script type="text/javascript">
function accept(updateUrl,insertUrl){
	if (editIndex!=undefined && $('#dg').datagrid('validateRow', editIndex)){
		$('#dg').datagrid('endEdit',editIndex);
		var url=updateUrl;;
		var row=$('#dg').datagrid('getRows')[editIndex];
		delete row.cgycchFreqResourceInfos;
		var json=JSON.stringify(row);
		console.log(json);
		if(isInsert) url=insertUrl;
		
		$.ajax({
			url:url,traditional:true,data:row,type:'post',
			success:function(data){//返回json结果
				var data=$.parseJSON(data);
				console.log(data);
				if(data.error){
					data='发生错误'+data.error;
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
					$('#dg').datagrid('reload');
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
	<table id="dg" title="广播与公共控制信道预分配资源信息" class="easyui-datagrid" data-options="
				url:'${pageContext.request.contextPath}/grm/beamccch/list.action',
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
			<th data-options="field:'gscCcchIndex',hidden:true"></th>
			<th data-options="field:'gscCcchVersionID',editor:'textbox'">版本号</th>
			<th data-options="field:'gscCcchAssignTime',
				formatter:function(value,row,rowIndex){
						if(typeof value==='number')
							return new Date(value*1000).format();
						return value;
					},
				editor:{
					type:'datetimebox',
					options:{
						editable:false,
						showSeconds:true
					}
				}">资源分配生效时间</th>
			<!-- =======波束资源分配信息 CgycchBeamResourceInfo 开始===================-->
			<th data-options="field:'gscCcchBeamId',
					editor:{type:'numberbox',options:{min:1,max:119}}">波束编号</th>
			<th data-options="field:'gscCcchFrameOffset',editor:'textbox'">帧偏移</th>
			<th data-options="field:'gscCcchBeamUpPowerLimit',editor:'textbox'">波束上行功率门限</th>
			<th data-options="field:'gscCcchBeamDownPowerLimit',editor:'textbox'">波束下行功率门限</th>
			<!-- =======波束资源分配信息 CgycchBeamResourceInfo 结束===================-->
			<!-- <th data-options="field:'cgycchFreqResourceInfos'"></th> -->
			<!-- CgycchFreqResourceInfo资源分配信息 波束下各个频点控制信道资源分配详细信息 -->
			<th data-options="field:'showCgycchFreqResourceInfos',
				formatter:function(value,row,rowIndex){
						return '<a href=${pageContext.request.contextPath}/grm/beamccch/cgycchfreqresourceinfo/listUI.action?gscCcchIndex='+
							row.gscCcchIndex+'&gscCcchBeamId='+row.gscCcchBeamId+'>详细信息</a>';
					}">资源分配信息</th>
		</tr>
		</thead>
	</table>
	<div id="tb" style="height:auto">
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="append()">添加</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="removeit(
				'${pageContext.request.contextPath}/grm/beamccch/delete.action')">删除</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save',plain:true" onclick="accept(
			'${pageContext.request.contextPath}/grm/beamccch/update.action',
				'${pageContext.request.contextPath}/grm/beamccch/save.action')">保存</a>
	</div>
</body>
</html>