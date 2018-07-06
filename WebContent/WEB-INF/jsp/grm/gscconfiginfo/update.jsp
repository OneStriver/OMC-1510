<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>信关站配置信息</title>
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
			else if(data.msg){
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
	$('#ff').form('load', '${pageContext.request.contextPath}/grm/gscconfiginfo/load.action');
});
</script>
</head>
<body>
	<div style="width:0px" id="mask"></div>
	<form id="ff" class="easyui-form" accept-charset="utf-8" method="post" data-options="novalidate:true" action="${pageContext.request.contextPath}/grm/gscconfiginfo/update.action">
	<div class="easyui-panel" title="信关站配置信息" style="width:1000px">
		<div style="padding:10px 60px 20px 60px">
	    	<table>
	    		<tr>
	    			<td>信关站编号:</td>
	    			<td><input class="easyui-numberbox" name="gssGwId" data-options="min:0,required:true"/></td>
	    			<td>信关站经度:</td>
	    			<td><input class="easyui-numberbox" name="gssLatitude" data-options="precision:2,required:true"/></td>
	    		</tr>
	    		<tr>
	    			<td>信关站类型:</td>
	    			<td>
	    				<select class="easyui-combobox" name="gssGwType" style="width:173px" data-options="required:true,editable:false">
	    					<option value="0" selected="selected">民用信关站</option>
	    					<option value="1">军用信关站</option>
	    				</select>
	    			</td>
	    			<td>信关站纬度:</td>
	    			<td><input class="easyui-numberbox" name="gssLongtitude" data-options="precision:2,required:true"/></td>
	    		</tr>
	    		<tr>
	    			<td>工作模式:</td>
	    			<td>
	    				<select class="easyui-combobox" name="gssPrimaryMode" style="width:173px" data-options="required:true,editable:false">
	    					<option value="0" selected="selected">主信关站模式</option>
	    					<option value="1">从信关站模式</option>
	    				</select>
	    			</td>
	    			<td>OSS配置的最大EIRP值:</td>
	    			<td><input class="easyui-numberbox" name="gssConfigMaxEIRP" data-options="min:0,precision:2,required:true"/></td>
	    		</tr>
	    		<tr>
	    			<td>起始帧的时间定位信息:</td>
	    			<td><input class="easyui-textbox" name="gssFrameStartTime" data-options="min:0,max:40,required:true"/></td>
	    			<td>硬件支持的最大EIRP值</td>
	    			<td><input class="easyui-numberbox" name="gssSupportMaxEIRP" data-options="min:0,precision:2,required:true"/></td>
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