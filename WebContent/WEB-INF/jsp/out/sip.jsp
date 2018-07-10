<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>sip</title>
<%@ include file="/WEB-INF/jsp/public/common.jspf"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/sip/sip.js"></script>
<script type="text/javascript">
</script>
</head>
<body>
	<div class="easyui-panel" data-options="fit:true,title:'sip配置'">
		<form id="ff" action="${pageContext.request.contextPath}/out/sip/update.action" method="post">
			<table>
				<tr>
					<td>本地SIP地址:</td>
					<td><input class="easyui-textbox" id="sipLocalSipIP" name="localSipIP" value="${sip.localSipIP}" data-options="validType:'ipABC',required:true"></td>	
				</tr>
				<tr>			
					<td>本地SIP端口:</td>
					<td><input class="easyui-textbox" id="sipLocalSipPort" name="localSipPort" value="${sip.localSipPort}" data-options="validType:'sipPort',required:true"></td>
				</tr>
				<tr>
					<td>远端SIP地址:</td>
					<td><input class="easyui-textbox" id="sipRemoteSipIP" name="remoteSipIP" value="${sip.remoteSipIP}" data-options="validType:'ipABC',required:true"></td>
				</tr>
				<tr>	
					<td>远端SIP端口:</td>
					<td><input class="easyui-textbox" id="sipRemoteSipPort" name="remoteSipPort" value="${sip.remoteSipPort}" data-options="validType:'sipPort',required:true"></td>				
				</tr>
				<tr>
					<td>默认语音编码:</td>
					<td>
						<select class="easyui-combobox" name="voiceEncoding" data-options="editable:false,required:true,panelHeight:'auto',width:80">   
						    <option value="G729" <c:if test="${sip.voiceEncoding=='G729'}">selected</c:if>>G729</option>
						    <option value="PCMA" <c:if test="${sip.voiceEncoding=='PCMA'}">selected</c:if>>PCMA</option>
						    <option value="PCMU" <c:if test="${sip.voiceEncoding=='PCMU'}">selected</c:if>>PCMU</option>
						    <option value="AMR" <c:if test="${sip.voiceEncoding=='AMR'}">selected</c:if>>AMR</option>
						</select>
					</td>
				</tr>
				<tr>	
					<td>Options支持:</td>
					<td>
						<select class="easyui-combobox" name="heartBeat" data-options="editable:false,required:true,panelHeight:'auto',width:50">   
						    <option value="YES" <c:if test="${sip.heartBeat=='YES'}">selected</c:if>>是</option>
						    <option value="NO" <c:if test="${sip.heartBeat=='NO'}">selected</c:if>>否</option>
						</select>
					</td>	
				</tr>
			</table>
				
			<table>
				<tr>	
					<td>本地出局前缀:</td>
					<td>
						<label for="routing"><input id="cb"  type="checkbox" onclick="defaultRouting()" <c:if test="${sip.flag==0}">checked="checked"</c:if>/>默认网关</label>
						<span hidden="true"><input class="easyui-textbox" value="${sip.flag}" id="flag" name="flag"></span>
					</td>	
					<td id="add">
						<span style="display:none;">
						<input class="easyui-numberbox" value="${boxCount}" id="sipRoutNumCount" name="sipRoutNumCount" data-options="precision:0,width:50,<c:if test="${sip.flag==0}">disabled:true</c:if>">
						</span>
						<c:forEach items="${sip.routingNum}" var="r" varStatus="status">
							<input class="easyui-numberbox" value="${r}" id="routingNum_${status.count}" name="routingNum" data-options="validType:'sipRoutingNum',precision:0,width:80,required:true">
						</c:forEach>
					</td>
					<td><a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add',width:30,height:22"  onclick="add()"></a></td>	
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