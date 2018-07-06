<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>配置文件列表</title>
<%@ include file="/WEB-INF/jsp/public/common.jspf"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/public/grid.js?<%=new Date().getTime()%>"></script>
<script type="text/javascript">
function accept(updateUrl,insertUrl){
	if (editIndex!=undefined && $('#dg').datagrid('validateRow', editIndex)){
		$('#dg').datagrid('endEdit',editIndex);
		var url=updateUrl;;
		var row=$('#dg').datagrid('getRows')[editIndex];
		delete row.module;
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
						style:{right:'',bottom:''}
					});
					$('#dg').datagrid('reload');
				}
				
			}
		});
		editIndex = undefined;
		isInsert = undefined;
	}
}

function submitForm(){
	$('#ff').form('submit',{
		success:function(data){
			submitSuccess(data);
			$.messager.progress('close');
			$('#w').window('close');
			$('#ff').form('clear');
		},
		onSubmit:function(){
			var isOk=$(this).form('enableValidation').form('validate');
			if(isOk) $.messager.progress({msg:'正在上传请稍等'});
			return isOk;
		}
	});
}
</script>
</head>
<body>
	<table id="dg" class="easyui-datagrid" title="<spring:message code="ConfigFileList"></spring:message>" style="width:800px;height:auto"
		data-options="
			url:'${pageContext.request.contextPath}/config/list.action',
			rownumbers:true,
			fit:true,striped:true,border:false,
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
			<th data-options="field:'name',width:'25%',editor:'textbox'"><spring:message code="ConfigFileName"></spring:message></th>
			<th data-options="field:'description',width:'20%',editor:'textbox'"><spring:message code="DescriptionInformation"></spring:message></th>
			<th data-options="field:'sole',width:'20%',
				editor:{type:'checkbox',options:{on:true,off:false}}"><spring:message code="IndependentConfigFile"></spring:message></th>
			<th data-options="field:'module.name',width:'25%',
				formatter:function(value,row,rowIndex){
					if(row.module==null) return '';
					return row.module.name;
				}"><spring:message code="NEName"></spring:message></th>
		</tr>
	</thead>
	</tbody>
	</table>
	<div id="tb" style="height:auto">
		<!-- 添加(上传网元相关的配置文件) -->
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="$(w).window('open');"><spring:message code="Add"></spring:message></a>
		<!-- 删除 -->
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="removeit(
				'${pageContext.request.contextPath}/config/delete.action')"><spring:message code="Delete"></spring:message></a>
		<!-- 保存 -->
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save',plain:true" onclick="accept(
			'${pageContext.request.contextPath}/config/update.action',
				'${pageContext.request.contextPath}/config/save.action')"><spring:message code="save"></spring:message></a>
	</div>
	<!-- 添加(上传网元相关的配置文件) -->
	<div id="w" class="easyui-window" title="<spring:message code="UploadConfigFile"></spring:message>" data-options="modal:true,closed:true,iconCls:'icon-save'" style="text-align:center;width:300px;height:200px;padding:10px;">
		<form id="ff" method="post" enctype="multipart/form-data" action="${pageContext.request.contextPath}/config/upload.action">
		
			<label><spring:message code="SelectNEModule"></spring:message>:</label>
			<input id="module" class="easyui-combobox" name="moduleId" data-options="
				retuired:true,valueField:'id',textField:'description',editable:false,panelHeight:'auto',
		        url: '${pageContext.request.contextPath}/module/listjsonarr.action'"/><br/>
		         
			<!-- <label>文本配置文件后缀名为【.config】【.txt】【.properties】...</label> --><br/>
			<label>输入文件路径:</label>
			<input class="easyui-textbox"><br/><br/>
			
			<input class="easyui-filebox" name="file" data-options="onChange:function(){
				$(this).filebox('setValue',$(this).filebox('getValue').substring(12));
			},buttonText:'<spring:message code="SelectFile"></spring:message>',required:true,prompt:'<spring:message code="SelectUploadFile"></spring:message>'" style="width:100%"/>
			<br/><br/>
			<a href="#" class="easyui-linkbutton" onclick="submitForm()"><spring:message code="UploadConfigFile"></spring:message></a>
		</form>
	</div>
</body>
</html>