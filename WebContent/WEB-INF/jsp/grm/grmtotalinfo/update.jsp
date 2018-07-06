<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>GRM板卡配置信息</title>
<%@ include file="/WEB-INF/jsp/public/common.jspf"%>
<script type="text/javascript">
function submitForm(){
	$('#ff').form('submit',{
		success:submitSuccess,
		onSubmit:function(){
			return $(this).form('enableValidation').form('validate');
		}
	});
}
function clearForm(){
	$('#ff').form('clear');
}

$(function(){
	$('#ff').form({
		onLoadSuccess:function(data){
			if(data.error)
				$.messager.alert('出错提示',data.error,'error');
		}
	});
	$('#ff').form('load', '${pageContext.request.contextPath}/grm/grmtotalinfo/load.action');
});
</script>
</head>
<body>
	<form id="ff" class="easyui-form" method="post" data-options="novalidate:true" action="${pageContext.request.contextPath}/grm/grmtotalinfo/update.action">
	<div class="easyui-panel" title="GRM板卡配置" style="width:1000px">
		<div style="padding:10px 60px 20px 60px">
	    	<table>
	    		<tr>
	    			<td>主用SCTP端口:</td>
	    			<td><input class="easyui-numberbox" name="grmPortSctp" data-options="min:0,max:65535,required:true"/></td>
	    			<td>运控OPC 地址:</td>
	    			<td><input class="easyui-textbox" name="grmCoOpcIp" data-options="required:true"/></td>
	    		</tr>
	    		<tr>
	    			<td>板卡　状态 　:</td>
	    			<td>
	    				<select class="easyui-combobox" name="grmSoftwareStatus" style="width:173px" data-options="disabled:true,required:true,editable:false">
	    					<option value="1" selected="selected">正常工作</option>
	    					<option value="2">无法工作</option>
	    					<option value="3">未知状态</option>
	    				</select>
	    			</td>
	    			<td>运控OPC 端口:</td>
	    			<td><input class="easyui-numberbox" name="grmCoOpcPort" data-options="min:0,max:65535,required:true"/></td>
	    		</tr>
	    	</table>
	    </div>
	    <div style="text-align:center;padding:5px">
		   	<a href="javascript:void(0)" class="easyui-linkbutton" onclick="submitForm()">更新配置</a>
		   	<a href="javascript:void(0)" class="easyui-linkbutton" onclick="clearForm()">清除填写记录</a>
    	</div>
	</div>
    </form>
</body>
</html>