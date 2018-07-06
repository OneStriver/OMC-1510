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
	<div class="easyui-panel" data-options="fit:true,title:'sip配置'">
		<form id="ff" action="${pageContext.request.contextPath}/out/sip/update.action" method="post">
			<table>
				<tr>
					<td>本地SIP地址：</td>
					<td><input class="easyui-textbox" name="localSipIP" value="${sip.localSipIP}" data-options="validType:'ipdot3',required:true"></td>	
				</tr>
				<tr>			
					<td>本地SIP端口：</td>
					<td><input class="easyui-textbox" name="localSipPort" value="${sip.localSipPort}" data-options="required:true"></td>
				</tr>
				<tr>	
					<td>本地出局前缀：</td>
					<td><input class="easyui-textbox" name="routingNum" value="${sip.routingNum}" data-options="required:true"></td>
					<td>例如：1|502 1为局号段个数，后面为出局前缀，当个数大于1时，后面使用多个|分割，例如3个号段，3|502|187|112</td>				
				</tr>
				<tr>
					<td>远端SIP地址：</td>
					<td><input class="easyui-textbox" name="remoteSipIP" value="${sip.remoteSipIP}" data-options="validType:'ipdot3',required:true"></td>
				</tr>
				<tr>	
					<td>远端SIP端口：</td>
					<td><input class="easyui-textbox" name="remoteSipPort" value="${sip.remoteSipPort}" data-options="required:true"></td>				
				</tr>
				<tr>
					<td>默认语音编码	：</td>
					<td>
						<select class="easyui-combobox" name="voiceEncoding" data-options="required:true,panelHeight:'auto'">   
						    <option value="G729" <c:if test="${sip.voiceEncoding=='G729'}">selected</c:if>>G729</option>
						    <option value="PCMA" <c:if test="${sip.voiceEncoding=='PCMA'}">selected</c:if>>PCMA</option>
						    <option value="PCMU" <c:if test="${sip.voiceEncoding=='PCMU'}">selected</c:if>>PCMU</option>
						    <option value="AMR" <c:if test="${sip.voiceEncoding=='AMR'}">selected</c:if>>AMR</option>
						</select>
					</td>
				</tr>
				<tr>	
					<td>Options支持：</td>
					<td>
						<select class="easyui-combobox" name="heartBeat" data-options="required:true,panelHeight:'auto'">   
						    <option value="YES" <c:if test="${sip.heartBeat=='YES'}">selected</c:if>>YES</option>
						    <option value="NO" <c:if test="${sip.heartBeat=='NO'}">selected</c:if>>NO</option>
						</select>
					</td>	
				</tr>
				<tr>
					<td></td>
					<td>
						<a href="#" class="easyui-linkbutton" onclick="location='${pageContext.request.contextPath}/out/sip.action'">刷新</a>
						<a href="#" class="easyui-linkbutton" onclick="submit()">提交</a>
					</td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>