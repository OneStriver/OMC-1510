<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<title>共同页面</title>
<%@ include file="/WEB-INF/jsp/public/common.jspf"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/public/grid.js"></script>
<script type="text/javascript">
function accept(updateUrl,insertUrl){
	if (editIndex!=undefined && $('#dg').datagrid('validateRow', editIndex)){
		$('#dg').datagrid('endEdit',editIndex);
		var url=updateUrl;;
		var row=$('#dg').datagrid('getRows')[editIndex];
		var json=JSON.stringify(row);
		if(isInsert) url=insertUrl;
		$.ajax({
			url:url,traditional:true,data:row,type:'post',
			success:function(data){
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
	<table id="dg" class="easyui-datagrid" title="<spring:message code="CommonPageList"/>" style="width:800px;height:auto"
		data-options="
			url:'${pageContext.request.contextPath}/common/list.action',
			rownumbers:true,
			fit:true,
			striped:true,
			border:false,
			pageList: [10,20,30,40,50,60],
			pageNumber:${pageBean.page},
			pagination:true,
			toolbar: '#tb',
			loadFilter:loadFilter,
			onClickCell:onClickCell,
			pageSize: ${pageBean.pageSize}">
	<thead>
		<tr>
			<th data-options="field:'ck',checkbox:true"></th>
			<th data-options="field:'id',hidden:true"></th>
			<th data-options="field:'name',width:'25%',editor:'textbox'"><spring:message code="PageName"/></th>
			<th data-options="field:'show',
					formatter:function(value,row,rowIndex){
						if(row.id==null) return '';
						return '<a href=${pageContext.request.contextPath}/common/show.action?id='+row.id+'><spring:message code="View"/></a>';
					}"><spring:message code="ViewTheconfigurationOnThePage"/></th>
		</tr>
	</thead>
	</tbody>
	</table>
	<div id="tb" style="height:auto">
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="append()"><spring:message code="Add"/></a>
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="removeit(
				'${pageContext.request.contextPath}/common/delete.action')"><spring:message code="Delete"/></a>
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save',plain:true" onclick="accept(
			'${pageContext.request.contextPath}/common/update.action',
				'${pageContext.request.contextPath}/common/save.action')"><spring:message code="save"/></a>
	</div>
</body>
</html>