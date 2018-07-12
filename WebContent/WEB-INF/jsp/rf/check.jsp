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
	<div class="easyui-panel" data-options="fit:true,title:'bsc全局配置'">
		<form id="ff" action="${pageContext.request.contextPath}/rf/updateCheck.action" method="post">
			<table>
				<tr>
					<td>BSC指向：</td>
					<td><input class="easyui-textbox" name="ip" value="${bsc.ip}" data-options="validType:'ipdot3',required:true"></td>	
				</tr>
				<tr>			
					<td>MSCID：</td>
					<td><input class="easyui-textbox" name="mscId" value="${bsc.mscid}" data-options="required:true"></td>
				</tr>
				<tr>	
					<td>SID：</td>
					<td><input class="easyui-textbox" name="sid" value="${bsc.sid}" data-options="required:true"></td>
				</tr>
				<tr>
					<td>NID：</td>
					<td><input class="easyui-textbox" name="nid" value="${bsc.nid}" data-options="validType:'ipdot3',required:true"></td>
				</tr>
				<tr>	
					<td>PacketZoneID：</td>
					<td><input class="easyui-textbox" name="PacketZoneID" value="${bsc.packetZoneId}" data-options="required:true"></td>				
				</tr>
				<tr>	
					<td>LocalMCC：</td>
					<td><input class="easyui-textbox" name="localMcc" value="${bsc.localMcc}" data-options="required:true"></td>				
				</tr>
				<tr>	
					<td>LocalIMSI_11_12：</td>
					<td><input class="easyui-textbox" name="localIMSI_11_12" value="${bsc.localImsi_11_12}" data-options="required:true"></td>				
				</tr>
				<tr>	
					<td>DoSectorId104：</td>
					<td><input class="easyui-textbox" name="doSectorId104" value="${bsc.doSectorId104}" data-options="required:true"></td>				
				</tr>
				<tr>	
					<td>doColorCode：</td>
					<td><input class="easyui-textbox" name="doColorCode" value="${bsc.doColorCode}" data-options="required:true"></td>				
				</tr>
				<tr>	
					<td>maxSci：</td>
					<td><input class="easyui-textbox" name="maxSci" value="${bsc.maxSci}" data-options="required:true"></td>				
				</tr>
				<tr>	
					<td>achMaxCapSize：</td>
					<td><input class="easyui-textbox" name="achMaxCapSize" value="${bsc.achMaxCapSize}" data-options="required:true"></td>				
				</tr>
				<tr>	
					<td>DoSectorID24：</td>
					<td><input class="easyui-textbox" name="doSectorId24" value="${bsc.doSectorId24}" data-options="required:true"></td>				
				</tr>
				<tr>
					<td></td>
					<td>
						<a href="#" class="easyui-linkbutton" onclick="location='${pageContext.request.contextPath}/rf/check.action'"><spring:message code="Refresh"/></a>
						<a href="#" class="easyui-linkbutton" onclick="submit()"><spring:message code="Submit"/></a>
					</td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>