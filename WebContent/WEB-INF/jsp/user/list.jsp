<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>用户列表</title>
<%@ include file="/WEB-INF/jsp/public/common.jspf"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/public/grid.js?<%=new Date().getTime()%>"></script>
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
						style:{right:'',bottom:''}
					});
				}
			}
		});
		editIndex = undefined;
		isInsert = undefined;
	}
}
function onLoadSuccess(){
	var roles=$('#dg').datagrid('getRows')[editIndex].roles;
	if(roles){
		var ed = $('#dg').datagrid('getEditor', {index:editIndex,field:'roleId'});
		roles.forEach(function(role){
			$(ed.target).combobox('select',role.id);
		});
	}
}

function clickCell(index, field){
	var name=$('#dg').datagrid('selectRow', index).datagrid('getSelected').name;
	if(name=='nouser') return;
	else onClickCell(index, field);
	
}
</script>
</head>
<body>
	<table id="dg" class="easyui-datagrid" title='<spring:message code="userList"></spring:message>' style="width:500px;height:auto"
			data-options="
				url:'${pageContext.request.contextPath}/user/list.action',
				rownumbers:true,
				fit:true,striped:true,border:false,
				pageList: [1,20,30,40,50],
				pageNumber:${pageBean.page},
				pageSize: ${pageBean.pageSize},
				pagination:true,
				iconCls: 'icon-edit',
				<omc:permit url='user/update'>
				onClickCell:clickCell,
				</omc:permit>
				toolbar: '#tb'
			">
	<thead>
		<tr>
			<th data-options="field:'ck',checkbox:true"></th>
			<th data-options="field:'id',hidden:true"></th>
			<th data-options="field:'name',width:'180',editor:'textbox'"><spring:message code="UserName"/></th>
			<th data-options="field:'password',width:'30%',
			formatter:function(value,row,rowIndex){
					return '******';
				},
				editor:{
					type:'textbox',
					options:{
						validType:'length[6,20]'
					}
				}"><spring:message code="Password"/></th>
			<th data-options="field:'roleId',width:'50%',
				formatter:function(value,row,rowIndex){
					if(row.roles){
						var roleNames=[];
						row.roles.forEach(function(i){roleNames.push(i.name);});
						return roleNames.toString();
					}else{
						return '';
					}
				},
				editor:{
					type:'combobox',
					options:{
						editable:false,
						valueField:'id',
						textField:'name',
						method:'get',
						url:'${pageContext.request.contextPath}/role/list.action',
						required:true,
						multiple:true,
						panelHeight:'auto'
					}
				}"><spring:message code="Role"/></th>
		</tr>
	</thead>
	</table>
	<div id="tb" style="height:auto">
		<omc:permit url="user/add">
			<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="append()"><spring:message code="Add"/></a>
		</omc:permit>
		<omc:permit url="user/delete">
			<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" 
				onclick="removeit('${pageContext.request.contextPath}/user/delete.action')"><spring:message code="Delete"/></a>
		</omc:permit>
			<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-save',plain:true" 
				onclick="accept('${pageContext.request.contextPath}/user/update.action',
					'${pageContext.request.contextPath}/user/save.action')"><spring:message code="Save"/></a>
	</div>
</body>
</html>