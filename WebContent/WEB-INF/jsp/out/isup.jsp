<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>ISUP</title>
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
	<div class="easyui-panel" data-options="fit:true,title:'ISUP${index}配置'">
		<form id="ff" action="${pageContext.request.contextPath}/out/isup/update.action" method="post">
			<input type="hidden" name="index" value="${index}">
			<table>
				<tr>
					<td>号码前缀：</td>
					<td><input class="easyui-numberbox" name="prefixStr" value="${isup.prefixStr}" data-options=""></td>				
					<td>本地出局前删除的前缀：</td>
					<td><input class="easyui-numberbox" name="afterRoutingStripStr" value="${isup.afterRoutingStripStr}" data-options=""></td>
				</tr>
				<tr>
					<td>最小呼入号长：</td>
					<td><input class="easyui-numberbox" name="minNumOfDigits" value="${isup.minNumOfDigits}" data-options=""></td>				
					<td>话路选择模式：</td>
					<td>
						<select class="easyui-combobox" name="tSAssignMode" data-options="required:true,panelHeight:'auto'">   
						    <option value="0" <c:if test="${isup.tSAssignMode=='0'}">selected</c:if>>降序</option>
						    <option value="1" <c:if test="${isup.tSAssignMode=='1'}">selected</c:if>>升序</option>
						    <option value="2" <c:if test="${isup.tSAssignMode=='2'}">selected</c:if>>偶数</option>
						    <option value="3" <c:if test="${isup.tSAssignMode=='3'}">selected</c:if>>奇数</option>
						    <option value="4" <c:if test="${isup.tSAssignMode=='4'}">selected</c:if>>下一半</option>
						    <option value="5" <c:if test="${isup.tSAssignMode=='5'}">selected</c:if>>上一半</option>
						</select>
					</td>
				</tr>
				<tr>
					<td>信令链路编码：</td>
					<td><input class="easyui-numberbox" name="slc" value="${isup.slc}" data-options="required:true,min:0,max:15"></td>				
					<td>PCM链路号码：</td>
					<td><input class="easyui-numberbox" name="cic" value="${isup.cic}" data-options="required:true"></td>
				</tr>
				<tr>
					<td>源信令点：</td>
					<td><input class="easyui-textbox" name="opc" value="${isup.opc}" data-options="validType:'ipdot3',required:true"></td>
					<td>目的信令点：</td>
					<td><input class="easyui-textbox" name="dpc" value="${isup.dpc}" data-options="validType:'ipdot3',required:true"></td>				
				</tr>
				<tr>
					<td>信令时隙：</td>
					<td><input class="easyui-numberbox" name="timeSlot" value="${isup.timeSlot}" data-options="required:true,min:1,max:31"></td>
					<td>语音编码：</td>
					<td>
						<select class="easyui-combobox" name="voCoding" data-options="required:true,panelHeight:'auto'">   
						    <option value="PCMA" <c:if test="${isup.voCoding=='PCMA'}">selected</c:if>>PCMA</option>   
						    <option value="PCMU" <c:if test="${isup.voCoding=='PCMU'}">selected</c:if>>PCMU</option>
						</select>
					</td>				
				</tr>
				<tr>
					<td>编解码标准：</td>
					<td>
						<select class="easyui-combobox" name="standard" data-options="required:true,panelHeight:'auto'">   
						    <option value="ANSI" <c:if test="${isup.standard=='ANSI'}">selected</c:if>>ANSI</option>   
						    <option value="ITU" <c:if test="${isup.standard=='ITU'}">selected</c:if>>ITU</option>
						</select>
					</td>
					<td>使用CPG：</td>
					<td>
						<select class="easyui-combobox" name="isup_cpg" data-options="required:true,panelHeight:'auto'">   
						    <option value="1" <c:if test="${isup.isup_cpg=='1'}">selected</c:if>>是</option>   
						    <option value="0" <c:if test="${isup.isup_cpg=='0'}">selected</c:if>>否</option>
						</select>
					</td>
				</tr>
				<tr>
					<td>AI指示符：</td>
					<td>
						<select class="easyui-combobox" name="isup_ai" data-options="required:true,panelHeight:'auto'">
						    <option value="0x01" <c:if test="${isup.isup_ai=='0x01'}">selected</c:if>>SUB</option>   
						    <option value="0x02" <c:if test="${isup.isup_ai=='0x02'}">selected</c:if>>UNKNOW</option>
						    <option value="0x03" <c:if test="${isup.isup_ai=='0x03'}">selected</c:if>>NATIONAL</option>
						    <option value="0x04" <c:if test="${isup.isup_ai=='0x04'}">selected</c:if>>INTERNATIONAL</option>
						    <option value="0x05" <c:if test="${isup.isup_ai=='0x05'}">selected</c:if>>NET SPECIFIC</option>
						    <option value="0x06" <c:if test="${isup.isup_ai=='0x06'}">selected</c:if>>ROUTE NATIONAL</option>
						    <option value="0x07" <c:if test="${isup.isup_ai=='0x07'}">selected</c:if>>ROUTE NET</option>
						    <option value="0x08" <c:if test="${isup.isup_ai=='0x08'}">selected</c:if>>ROUTE DN</option>
						</select>
					</td>
					<td>SIO：</td>
					<td>
						<select class="easyui-combobox" name="isup_sio" data-options="required:true,panelHeight:'auto'">
						    <option value="0x05" <c:if test="${isup.isup_sio=='0x05'}">selected</c:if>>ISUP_INTERNATIONAL</option>   
						    <option value="0x85" <c:if test="${isup.isup_sio=='0x85'}">selected</c:if>>ISUP NATIONAL</option>
						</select>
					</td>
				</tr>
				<tr>
					<td>局向号段：</td>
					<td colspan="3">
						<c:forEach items="${isup.routing}" var="r">
							<input class="easyui-textbox" value="${r}" name="routing" data-options="width:50">
						</c:forEach>
					</td>
				</tr>
				<tr>
					<td>E1接口卡 地址：</td>
					<td colspan="3">
						<input class="easyui-textbox" name="remoteIp" value="${isup.remoteIp}" data-options="required:true,validType:'ip'">
						:
						<input class="easyui-textbox" name="remotePort" value="${isup.remotePort}" data-options="required:true,validType:['minValue[0]','maxValue[65535]']">
					</td>
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