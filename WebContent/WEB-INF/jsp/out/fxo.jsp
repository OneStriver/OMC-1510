<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>sip</title>
<%@ include file="/WEB-INF/jsp/public/common.jspf"%>
<script type="text/javascript">
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

$(function(){
	$('td:even').css({'text-align':'right'});
	$('.easyui-combobox').combobox({width:130});
});

</script>
</head>
<body>
	<div class="easyui-panel" data-options="fit:true,title:'fxo配置'">
		<form id="ff" action="${pageContext.request.contextPath}/out/fxo/update.action" method="post">
			<table>
				<tr>
					<td>WaitFor2ndDialTone：</td>
					<td><input class="easyui-textbox" name="WaitFor2ndDialTone" value="${fxo.waitFor2ndDialTone}" data-options="required:true"></td>	
				</tr>
				<tr>			
					<td>PrefixStr：</td>
					<td><input class="easyui-textbox" name="PrefixStr" value="${fxo.prefixStr}" data-options="required:true"></td>
				</tr>
				<tr>	
					<td>BlindDialing：</td>
					<td><input class="easyui-textbox" name="BlindDialing" value="${fxo.blindDialing}" data-options="required:true"></td>				
				</tr>
				<tr>
					<td>RingDetectMode：</td>
					<td><input class="easyui-textbox" name="RingDetectMode" value="${fxo.ringDetectMode}" data-options="required:true"></td>
				</tr>
				<tr>
					<td>
						<a href="#" class="easyui-linkbutton" onclick="location='${pageContext.request.contextPath}/out/fxo.action'">刷新</a>
						<a href="#" class="easyui-linkbutton" onclick="submit()">提交</a>
					</td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>