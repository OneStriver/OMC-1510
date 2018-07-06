<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<%@ include file="/WEB-INF/jsp/public/common.jspf"%>
<title>修改配置</title>
<style type="text/css">
div label {
	display:block;
	float:left;
	width:78px;
}
</style>
<%-- <script type="text/javascript" src="${pageContext.request.contextPath}/js/public/grid.js"></script> --%>
<script type="text/javascript">

function loadFilter(data){
	if(data.error){
		$.messager.alert('出错提示',data.error,'error');
		data = {
			total:0,
			rows: []
		//{GroupID:1,GroupStartIp:'1.1.1.1',GroupEndIp:'2.2.2.2',GroupStartMdn:'123456',GroupEndMdn:'654321',ParentGroupID:'2'}
		};
	}
	return data;
}

function clickBase(event){
	if(this.value=='1'){
		$('#GroupStartIp').textbox('options').required=false;
		$('#GroupStartIp').parent().hide();
		$('#GroupEndIp').textbox('options').required=false;
		$('#GroupEndIp').parent().hide();
		$('#GroupStartMdn').textbox('options').required=true;
		$('#GroupStartMdn').parent().show();
		$('#GroupEndMdn').textbox('options').required=true;
		$('#GroupEndMdn').parent().show();
	}else if(this.value=='2'){
		$('#GroupStartIp').textbox('options').required=true;
		$('#GroupStartIp').parent().show();
		$('#GroupEndIp').textbox('options').required=true;
		$('#GroupEndIp').parent().show();
		$('#GroupStartMdn').textbox('options').required=false;
		$('#GroupStartMdn').parent().hide();
		$('#GroupEndMdn').textbox('options').required=false;
		$('#GroupEndMdn').parent().hide();
	}
}

$(function(){
	//$('input[name=baseW]').on('click',clickBase);
	//$('#bmdn').trigger('click');
	$('input[name=ParentGroupID]').on('click',clickBase);
	
});

function onClickCell(index, field, value){
	if(field!='编辑') return;
	var row=$('#dg').datagrid('getRows')[index];
	update(row);
}

function update(row){
	var selector="input[name=ParentGroupID][value="+row.ParentGroupID+"]";
	$(selector).trigger('click');
	$('#GroupID').parent().show();
	$('#ff').form({
		url:'${pageContext.request.contextPath}/sbc/update${packid}.action'
		}).form('load',row);
	$('#w').window({title:'修改组管理'}).window('open');
}

function append(){
	console.log($('#GroupID').textbox('options'));
	$('#GroupID').parent().hide();
	$('#ff').form('clear');
	$('input[name=ParentGroupID]:eq(0)').trigger('click');
	$('#ff').form({
		url:'${pageContext.request.contextPath}/sbc/add${packid}.action'
		});
	$('#w').window({title:'添加组管理'}).window('open');
}
function submitForm(){
	$('#ff').form('submit',{
		success:function(data){
			$.messager.progress('close');
			submitSuccess(data);
			$('#w').window('close');
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
	if(selected.length==0) return;
	var columns=$('#dg').datagrid('getColumnFields');
	var ids=[];
	$.each(selected,function(i){
		var id=selected[i][columns[1]];
		if(id){
			ids.push(id);
		}else{
			$('#dg').datagrid('deleteRow',$('#dg').datagrid('getRows').length-1);
		}
	});
	if(ids.length>0){
		$.ajax({type:'post',traditional:true,data:{ids:ids},
			url:url,
			success:submitSuccess
		});
	}
}

</script>
</head>
<body>
   <c:forEach items="${items}" var="item">
	<%-- ---------------------表格开始------------------- --%>
	<c:choose>
	<c:when test="${item.formtype=='grid'}">
		<div id="tb">
			<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" 
				onclick="append()"><spring:message code="Add"></spring:message></a>
			<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" 
				onclick="removeit('${pageContext.request.contextPath}/sbc/delete${packid}.action')"><spring:message code="Delete"></spring:message></a>
			<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-save',plain:true" 
				onclick="accept('${pageContext.request.contextPath}/sbc/update${packid}.action',
					'${pageContext.request.contextPath}/sbc/add${packid}.action')"><spring:message code="save"></spring:message></a>
		</div>
		<table id="dg" class="easyui-datagrid" data-options="
			striped:true,onClickCell:onClickCell,pagination:true,fit:true,
			fitColumns:false,toolbar:'#tb',rownumbers:true,loadFilter:loadFilter,
			pageList: [50,100,150,200],title:'<spring:message code="GroupManageList"></spring:message>',
			pageNumber:${pageBean.page},pageSize: ${pageBean.pageSize},
			url:'${pageContext.request.contextPath}/sbc/load${packid}Grid.action?id=${item.id}'
			">
		    <thead>   
		        <tr>
		        	<th data-options="field:'ck',checkbox:true"></th>
		        	<th data-options="field:'RecId',hidden:true"></th>
		        	<th data-options="field:'GroupCacProfileID'"><spring:message code="GroupCACConfigId"></spring:message></th>
		        	<th data-options="field:'GroupID',width:100"><spring:message code="GroupID"></spring:message></th>
		        	<th data-options="field:'GroupStartIp',width:150,
			        	formatter:function(value,row,rowIndex){
			        		if(row.ParentGroupID=='1')
								return '<span style=color:gray>'+value+'</span>';
							return '<b>'+value+'</b>';
						}"><spring:message code="GroupStartIP"></spring:message></th>
		        	<th data-options="field:'GroupEndIp',width:150,
			        	formatter:function(value,row,rowIndex){
							if(row.ParentGroupID=='1')
								return '<span style=color:gray>'+value+'</span>';
							return '<b>'+value+'</b>';
						}"><spring:message code="GroupEndIP"></spring:message></th>
		        	<th data-options="field:'GroupStartMdn',width:150,
			        	formatter:function(value,row,rowIndex){
							if(row.ParentGroupID=='2')
								return '<span style=color:gray>'+value+'</span>';
							return '<b>'+value+'</b>';
						}"><spring:message code="GroupStartMDN"></spring:message></th>
		        	<th data-options="field:'GroupEndMdn',width:150,
			        	formatter:function(value,row,rowIndex){
							if(row.ParentGroupID=='2')
								return '<span style=color:gray>'+value+'</span>';
							return '<b>'+value+'</b>';
						}"><spring:message code="GroupEndMDN"></spring:message></th>
		        	<th data-options="field:'编辑',
			        	formatter:function(value,row,rowIndex){
							return '<a href=#><spring:message code="Edit"></spring:message>/a>';
						}"><spring:message code="Edit"></spring:message></th>
		        </tr>
		    </thead>
		</table>
	</c:when>
	</c:choose>
	<%-- ---------------------表格结束------------------- --%>
   </c:forEach>
   <!-- 添加修改表单 -->
   <div id="w" class="easyui-window" data-options="modal:true,closed:true,iconCls:'icon-save'" style="text-align:center;padding:10px;">
		<form id="ff" method="post">
			<label><spring:message code="GroupCACConfigId"></spring:message></label><input class="easyui-combobox" name="GroupCacProfileID" 
				data-options="
					panelHeight:'auto',
					valueField:'GroupPolicy_cacProfileID',
					textField:'GroupPolicy_cacProfileID',
					url:'${pageContext.request.contextPath}/sbc/loadCacId.action'
				"/><br/>
			<div><label><spring:message code="GroupID"></spring:message></label><input class="easyui-textbox" id="GroupID" name="GroupID" data-options="readonly:true"><br/></div>
			<div><label><spring:message code="GroupStartIP"></spring:message></label><input class="easyui-textbox" id="GroupStartIp" name="GroupStartIp" data-options="validType:'ip'"><br/></div>
			<div><label><spring:message code="GroupEndIP"></spring:message></label><input class="easyui-textbox" id="GroupEndIp" name="GroupEndIp" data-options="validType:'ip'"><br/></div>
			<div><label><spring:message code="GroupStartMDN"></spring:message></label><input class="easyui-textbox" id="GroupStartMdn" name="GroupStartMdn" data-options="validType:'mdn'"><br/></div>
			<div><label><spring:message code="GroupEndMDN"></spring:message></label><input class="easyui-textbox" id="GroupEndMdn" name="GroupEndMdn" data-options="validType:'mdn'"><br/></div>
			<label><spring:message code="BaseOnMDN"></spring:message><input type="radio" name="ParentGroupID" value="1"></label>
			<label><spring:message code="BaseOnIP"></spring:message><input type="radio" name="ParentGroupID" value="2"></label>
			<div style="text-align:center;padding:5px;clear:both;margin-top:20px">
				<a href="#" class="easyui-linkbutton" onclick="submitForm()"><spring:message code="Submit"></spring:message></a>
			</div>
		</form>
	</div>
</body>
</html>