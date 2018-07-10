<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>  
<title>IMS用户管理</title>
<%@ include file="/WEB-INF/jsp/public/common.jspf"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/public/grid.js?<%=new Date().getTime()%>"></script>
<script type="text/javascript">
function imdAddSubmitForm(){
	$('#imsAddForm').form('submit',{
		success:function(data){
			imsSubmitSuccess(data);
			$('#imsAddWindow').window('close');
			$('#imsAddForm').form('clear');
		},
		onSubmit:function(){
			return $(this).form('enableValidation').form('validate');
		}
	});
}

function removeit(){
	var rows=$('#dg').datagrid('getSelections');
	if(rows.length==0) {
		$.messager.alert('提示','请选择要删除的记录!');
		return;
	}
	$.messager.confirm('<spring:message code="Confirm"></spring:message>','<spring:message code="DeleteMessage"></spring:message>',
		function(yes){    
			if(yes){
				var rows=$('#dg').datagrid('getSelections');
				if(rows.length==0) return;
				var ids=[];
				$(rows).each(function(i){
					var id=rows[i].ueName;
					ids.push(id);
				});
				$.ajax({type:'post',traditional:true,data:{ids:ids},
					url:"${pageContext.request.contextPath}/ims/delete.action",
					success:submitSuccess
				});
			}
		}
	);
}

function imsUpdateSubmitForm(){
	$('#imsUpdateForm').form('submit',{
		success:function(data){
			imsSubmitSuccess(data);
			$('#imsUpdateWindow').window('close');
		},
		onSubmit:function(){
			return $(this).form('enableValidation').form('validate');
		}
	});
}

function imsSubmitSuccess(data){
	var json=$.parseJSON(data);
	if(json&&json.error) {
		$.messager.alert('出错提示',json.error,'error');
		return false;
	}else if(json.msg){
		$.messager.show({
			title:'操作提示',
			msg:json.msg,
			showType:'fade',
			timeout:2000,
			style:{
				right:'',bottom:''
			}
		});
		if($('#dg')){
			$('#dg').datagrid('reload');
		} 
		return true;
	}else{
		alert('未预料消息:'+data);
		return false;
	}
}

function onClickCell(index, field, value){
	if(field!='修改') return;
	var row=$('#dg').datagrid('selectRow',index).datagrid('getSelected');
	var domain=row.ueUri.substring(row.ueUri.indexOf('@')+1);
	$('#imsUpdateForm').form('load',row);
	$('#domain').textbox('setText',domain);
	$('#imsUpdateWindow').window('open');
}

function qq(value,name){
	var options=$('#dg').datagrid('options');
	var params={};
	jQuery.extend(params, options.queryParams);
	params[name]=value;
	$('#dg').datagrid({
		queryParams:params
	});
}

function sChange(newValue, oldValue){
	var options=$('#dg').datagrid('options');
	var params={};
	jQuery.extend(params, options.queryParams);
	params.ueStatus=newValue;
	var ueName=$('#imsSearch').searchbox('getName');
	var ueValue=$('#imsSearch').searchbox('getValue');
	params[ueName]=ueValue;
	$('#dg').datagrid({
		queryParams:params
	});
}

function tChange(newValue, oldValue){
	var options=$('#dg').datagrid('options');
	var params={};
	jQuery.extend(params, options.queryParams);
	params.ueUpdateType=newValue;
	var ueName=$('#imsSearch').searchbox('getName');
	var ueValue=$('#imsSearch').searchbox('getValue');
	params[ueName]=ueValue;
	$('#dg').datagrid({
		queryParams:params
	});
}

function mingwen(checkbox){
	$(checkbox).parent().next().textbox({type:checkbox.checked?'text':'password'});
}
</script>
</head>
<body>
	<table id="dg" title="<spring:message code="IMSUserList"></spring:message>" class="easyui-datagrid" style="width:800px;" data-options="
				url:'${pageContext.request.contextPath}/ims/list.action',
				border:false,
				rownumbers:true,
				fit:true,striped:true,
				onClickCell:onClickCell,
				pageList: [1,20,30,40,50],
				pageNumber:${pageBean.page},
				singleSelect:false,
				pagination:true,
				toolbar: '#tb',
				loadFilter:loadFilter,
				pageSize: ${pageBean.pageSize}">
		<thead>
		<tr>
			<th data-options="field:'ck',checkbox:true"></th>
			<th data-options="field:'ueName',width:'10%',
				formatter:function(value,row,rowIndex){
					return '<b>'+value+'</b>';
				}"><spring:message code="MDN"></spring:message>:</th>
			<th data-options="field:'ueUri',width:'20%'"><spring:message code="UserPublicIdentity"></spring:message></th>
			<th data-options="field:'ueUpdateType',width:'15%',
				formatter:function(value,row,rowIndex){
					switch(value){
					case 0:
						return '<spring:message code="LocalDomainUser"></spring:message>';
					case 1:
						return '<spring:message code="CallDomainUser"></spring:message>';
					case 2:
						return '<spring:message code="SynchroDomainUser"></spring:message>';
					default:
						return '<font color=red><spring:message code="UnknownUserType"></spring:message></font>';
					}
				}"><spring:message code="UserType"></spring:message></th>
			<th data-options="field:'ueStatus',
				formatter:function(value,row,rowIndex){
					if(value==200) return '<font color=green><spring:message code="AlreadyRegister"></spring:message></font>';
					else return '<font color=red><spring:message code="NotRegister"></spring:message></font>';
				}"><spring:message code="RegistrationStatus"></spring:message></th>
			<th data-options="field:'修改',
				formatter:function(value,row,rowIndex){
					return '<a href=# ><spring:message code="Update"></spring:message></a>';
				}"><spring:message code="Update"></spring:message></th>
		</tr>
	</thead>
	</table>
	<div id="tb" style="height:auto">
		<omc:permit url="ims/add">
			<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="$('#aw').window('open')"><spring:message code="Add"></spring:message></a>
		</omc:permit>
		<omc:permit url="ims/delete">
			<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="removeit()"><spring:message code="Delete"></spring:message></a>
		</omc:permit>
		<select class="easyui-combobox" name="ueStatus" 
			data-options="editable:false,width:110,onChange:sChange,panelHeight:'auto'">
			<option value="" selected="selected"><spring:message code="RegistrationStatus"></spring:message></option>
			<option value="200"><spring:message code="AlreadyRegister"></spring:message></option>
			<option value="0"><spring:message code="NotRegister"></spring:message></option>
		</select>
		<select class="easyui-combobox" name="ueUpdateType" 
			data-options="editable:false,width:110,onChange:tChange,panelHeight:'auto'">
			<option value="" selected="selected"><spring:message code="UserType"></spring:message></option>
			<option value="0"><spring:message code="LocalDomainUser"></spring:message></option>
			<option value="1"><spring:message code="CallDomainUser"></spring:message></option>
			<option value="2"><spring:message code="SynchroDomainUser"></spring:message></option>
		</select>
		<input id="imsSearch" class="easyui-searchbox" style="width:200px" 
			data-options="searcher:qq,prompt:'<spring:message code="PleaseEnterTheNumber"></spring:message>',menu:'#imsMenu'"/>
	</div>
	<div id="imsMenu" style="width:110px"> 
		<div data-options="name:'ueName'"><spring:message code="MDN"></spring:message></div> 
	</div> 
	
	<!-- 添加窗口 -->
	<div id="imsAddWindow" class="easyui-window" title="<spring:message code="CreateUser"></spring:message>" style="width:350px;padding:10px;"
			data-options="modal:true,closed:true,iconCls:'icon-add',minimizable:false,maximizable:false,collapsible:false">
		<form id="imsAddForm" class="easyui-form" method="post" data-options="novalidate:false" action="${pageContext.request.contextPath}/ims/save.action">
	    	<label><spring:message code="MDN"></spring:message>:</label>
	    	<input class="easyui-numberbox" name="ueName" data-options="validType:'phone',required:true,width:130"/>
	    	<br/><br/>
	    	<label><spring:message code="Domain"></spring:message>:</label>
	    	<input class="easyui-textbox" name="domain" style="margin-bottom:10px;" data-options="value:'test.com',required:true,width:100"/>
	    	<br/><br/>
	    	<label><spring:message code="ClearText"></spring:message><input type="checkbox" onchange="mingwen(this)"> <spring:message code="Password"></spring:message></label>
	    	<input class="easyui-textbox" name="uePassword" data-options="required:true,type:'password',width:100"/>
	    </form>
	    <div style="text-align:center;padding:5px">
	    	<a href="#" class="easyui-linkbutton" onclick="imdAddSubmitForm()"><spring:message code="save"></spring:message></a>
	    </div>
	 </div>
	 
	 <!-- 更新窗口 -->
	 <div id="imsUpdateWindow" class="easyui-window" title="<spring:message code="UpdateUser"></spring:message>" style="width:300px;padding:10px;"
	 		data-options="modal:true,closed:true,iconCls:'icon-edit',minimizable:false,maximizable:false,collapsible:false">
		<form id="imsUpdateForm" class="easyui-form" method="post" data-options="novalidate:true" action="${pageContext.request.contextPath}/ims/update.action">
	    	<label><spring:message code="MDN"></spring:message>:</label>
	    	<input class="easyui-numberbox" name="ueName" data-options="editable:false,required:true,width:130"/>
	    	<br/><br/>
	    	<label><spring:message code="Domain"></spring:message>:</label>
	    	<input class="easyui-textbox" id="domain" name="domain" data-options="required:true,width:130"/>
	    	<br/><br/>
	    	<label><spring:message code="ClearText"></spring:message><input type="checkbox" onchange="mingwen(this)"> <spring:message code="Password"></spring:message></label>
	    	<input class="easyui-textbox" name="uePassword" data-options="required:true,type:'password',width:130"/>
	    </form>
	    <div style="text-align:center;padding:5px">
	    	<a href="#" class="easyui-linkbutton" onclick="imsUpdateSubmitForm()"><spring:message code="save"></spring:message></a>
	    </div>
	 </div>
</body>
</html>