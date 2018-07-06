<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<title>域名配置</title>
<%@ include file="/WEB-INF/jsp/public/common.jspf"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/public/grid.js?<%=new Date().getTime()%>"></script>
<script type="text/javascript">
function onClickCell(index, field, value){
	if(field!='修改') return;
	var row=$('#dg').datagrid('getRows')[index];
	if(row.type=='A') update(row);
}

function update(row){
	$('#w').window('open');
	$('#ff').form('load',row);
	$('#oldData').val(row.data);
	$('#data').textbox('setValue',row.data);
}

function submit(){
	$('#ff').form('submit',{
		success:function(data){
			$.messager.progress('close');
			if(submitSuccess(data)){
				$('#w').window(close);
			}
		},
		onSubmit:function(){
			var isOk=$(this).form('enableValidation').form('validate');
			if(isOk) $.messager.progress({msg:'正在保存请稍等'});
			return isOk;
		}
	});
}

function removeit(url){
	var selected=$('#dg').datagrid('getSelections');
	console.log(selected);
	if(selected.length==0) return;
	$.messager.confirm(LOCALE.Confirm,LOCALE.DeleteMessage,
		function(yes){    
			if(yes){
				var columns=$('#dg').datagrid('getColumnFields');
				var param={};
				$.each(columns,function(i){
					param[columns[i]]=[];
				});
				$.each(selected,function(i){
					$.each(columns,function(j){
						param[columns[j]][i]=selected[i][columns[j]];
					});
				});
				$.ajax({type:'post',traditional:true,data:param,
					url:url,
					success:submitSuccess
				});
			}
		}
	);
}

function add(){
	$('#aw').window('open');
}

function asubmit(){
	$('#af').form('submit',{
		success:function(data){
			$.messager.progress('close');
			if(submitSuccess(data)){
				$('#aw').window(close);
			}
		},
		onSubmit:function(){
			var isOk=$(this).form('enableValidation').form('validate');
			if(isOk) $.messager.progress({msg:'正在保存请稍等'});
			return isOk;
		}
	});
}
</script>
</head>

<body>
	<table id="dg" class="easyui-datagrid" 
		data-options="
			url:'${pageContext.request.contextPath}/dnsServer/listAxfr.action?domain=${domain}',
			rownumbers:true,
			singleSelect:false,
			fit:true,striped:true,border:false,
			loadFilter:loadFilter,
			toolbar: '#tb',
			pagination:true,
			pageList:[50],
			pageSize:50,
			onClickCell:onClickCell">
		<thead>
		<tr>
			<th data-options="field:'ck',checkbox:true"></th>
			<th data-options="field:'name'">NAME</th>
			<th data-options="field:'zone'">ZONE</th>
			<th data-options="field:'ttl'">TTL</th>
			<th data-options="field:'clazz'">Class</th>
			<th data-options="field:'type'">Type</th>
			<th data-options="field:'data'">Data</th>
			<th data-options="field:'修改',
				formatter:function(value,row,rowIndex){
					if(row.type=='A') return '<a href=#>修改</a>';
				}"><spring:message code="Edit"/></th>
		</tr>
		</thead>
	</table>
	<div id="tb" style="height:auto">
		<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" 
			onclick="$('#aw').window('open')"><spring:message code="Add"/></a>
		<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="removeit(
			'${pageContext.request.contextPath}/dnsServer/delete.action')"><spring:message code="Delete"/></a>
	</div>
	
	<div id="w" class="easyui-window" title="修改地址" style="padding:10px;" data-options="modal:true,closed:true">
		<form id="ff" class="easyui-form" method="post" data-options="novalidate:true" 
				action="${pageContext.request.contextPath}/dnsServer/update.action">
			请输入新地址
			<input name="zone" type="hidden">
			<input name="name" type="hidden">
			<input name="ttl" type="hidden">
			<input name="clazz" type="hidden">
			<input name="type" type="hidden">
			<input id="oldData" name="oldData" type="hidden">
			<input id="data" class="easyui-textbox" name="data" data-options="validType:'ip',required:true"/>
			<br/>
			<a href="#" class="easyui-linkbutton" onclick="submit()">提交</a>
		</form>
	</div>
	<div id="aw" class="easyui-window" title="添加主机地址域名" style="padding:10px;" data-options="modal:true,closed:true">
		<form id="af" class="easyui-form" method="post" data-options="novalidate:true"
				action="${pageContext.request.contextPath}/dnsServer/add.action">
			<input name="zone" type="hidden" value="${domain}">
			<input name="ttl" type="hidden" value="600">
			<input name="clazz" type="hidden" value="IN">
			<input name="type" type="hidden" value="A">
			域名：<input name="name" class="easyui-textbox"><br/>
			地址：<input id="data" class="easyui-textbox" name="data" data-options="validType:'ip',required:true"/><br/>
			<a href="#" class="easyui-linkbutton" onclick="asubmit()">提交</a>
		</form>
	</div>
</body>
</html>