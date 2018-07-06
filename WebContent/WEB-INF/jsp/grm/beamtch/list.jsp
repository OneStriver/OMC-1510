<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>业务信道预分配资源信息</title>
<%@ include file="/WEB-INF/jsp/public/common.jspf"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/public/grid.js"></script>
<script type="text/javascript">
function accept(updateUrl,insertUrl){
	if (editIndex!=undefined && $('#dg').datagrid('validateRow', editIndex)){
		$('#dg').datagrid('endEdit',editIndex);
		var url=updateUrl;;
		var row=$('#dg').datagrid('getRows')[editIndex];
		delete row.cgytchBeamResourceInfos;
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
					$('#dg').datagrid('reload');
					$.messager.show({
						title:'操作提示',
						msg:data.msg,
						showType:'fade',timeout:1500,
						style:{
							right:'',bottom:''
							//top:document.body.scrollTop+document.documentElement.scrollTop
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
	<table id="dg" title="业务信道预分配资源信息" class="easyui-datagrid" data-options="
				url:'${pageContext.request.contextPath}/grm/beamtch/list.action',
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
			<th data-options="field:'gscTchIndex',hidden:true"></th>
			<th data-options="field:'gscTchVersionID',editor:'textbox'">版本号</th>
			<th data-options="field:'gscTchAssignTime',width:150,
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
			<th data-options="field:'gscTchBeamId',
					editor:{type:'numberbox',options:{min:1,max:119}}">波束编号</th>
			<th data-options="field:'gscTchFrameOffset',editor:'textbox'">帧偏移</th>
			<th data-options="field:'gscTchBeamUpPowerLimit',editor:'textbox'">波束上行功率门限</th>
			<th data-options="field:'gscTchBeamDownPowerLimit',editor:'textbox'">波束下行功率门限</th>
			<!-- =======波束资源分配信息 CgycchBeamResourceInfo 结束===================-->
			<!-- <th data-options="field:'cgycchFreqResourceInfos'"></th> -->
			<!-- CgycchFreqResourceInfo资源分配信息 波束下各个频点控制信道资源分配详细信息 -->
			<th data-options="field:'showCgytchBeamResourceInfos',
				formatter:function(value,row,rowIndex){
						return '<a href=${pageContext.request.contextPath}/grm/beamtch/cgytchresourceinfo/listUI.action?gscTchIndex='+
							row.gscTchIndex+'&gscTchBeamId='+row.gscTchBeamId+'>详细信息</a>';
					}">资源分配信息</th>
		</tr>
		</thead>
	</table>
	<div id="tb" style="height:auto">
		<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="append()">添加</a>
		<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="removeit(
				'${pageContext.request.contextPath}/grm/beamtch/delete.action')">删除</a>
		<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-save',plain:true" onclick="accept(
			'${pageContext.request.contextPath}/grm/beamtch/update.action',
				'${pageContext.request.contextPath}/grm/beamtch/save.action')">保存</a>
	</div>
</body>
</html>