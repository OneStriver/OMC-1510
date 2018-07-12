<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>SGW</title>
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
			if(isOk) $.messager.progress({msg:'正在保存请稍等...'});
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
	<div class="easyui-panel" data-options="fit:true,title:'SGW配置'">
		<form id="ff" action="${pageContext.request.contextPath}/out/sgw/update.action" method="post">
			<table>
				<tr>
					<td>目录服务器IP：</td>
					<td><input class="easyui-textbox" name="remoteSipLDAPServerIP" value="${isup.remoteSipLDAPServerIP}" data-options="required:true,validType:'ip'"></td>				
					<td>目录服务器端口：</td>
					<td><input class="easyui-numberbox" name="remoteSipLDAPServerPort" value="${isup.remoteSipLDAPServerPort}" data-options="required:true,validType:['minValue[0]','maxValue[65535]']"></td>
				</tr>
				<tr>
					<td>绑定用户名：</td>
					<td><input class="easyui-textbox" name="ldapUser" value="${isup.ldapUser}" data-options="required:true"></td>				
					<td>绑定用户验证密码：</td>
					<td><input class="easyui-textbox" name="ldapPwd" value="${isup.ldapPwd}" data-options="required:true"></td>
				</tr>
				<tr>
					<td>根目录名称：</td>
					<td><input class="easyui-textbox" name="" value="${isup.prefixStr}" data-options="required:true,validType:'ip'"></td>				
					<td>工作目录名称：</td>
					<td><input class="easyui-textbox" name="" value="${isup.afterRoutingStripStr}" data-options="required:true,validType:['minValue[0]','maxValue[65535]']"></td>
				</tr>
				<tr>
					<td>本端IP：</td>
					<td><input class="easyui-textbox" name="" value="${isup.prefixStr}" data-options="required:true,validType:'ip'"></td>				
					<td>本端端口：</td>
					<td><input class="easyui-numberbox" name="" value="${isup.afterRoutingStripStr}" data-options="required:true,validType:['minValue[0]','maxValue[65535]']"></td>
				</tr>
				<tr>
					<td></td>
					<td>
						<a href="#" class="easyui-linkbutton" onclick="location='${pageContext.request.contextPath}/out/isup${index}.action'"><spring:message code="Refresh"/></a>
						<a href="#" class="easyui-linkbutton" onclick="submit()"><spring:message code="Submit"/></a>
					</td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>