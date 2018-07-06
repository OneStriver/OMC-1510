<%@page import="com.sunkaisens.omc.context.core.OmcServerContext"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<html>
<head>
<title>OMC操控台</title>
<!-- 
<fmt:setBundle basename="CustomValidationMessages" var="applicationBundle"/>
 -->
<%@ include file="/WEB-INF/jsp/public/common.jspf"%>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/main.css?<%=new Date().getTime()%>">	
<script type="text/javascript" src="${pageContext.request.contextPath}/js/main.js?<%=new Date().getTime()%>"></script>
<link rel="icon" href="${pageContext.request.contextPath}/images/logo.png" type="image/png">
</head>
<body style="border:none;visibility:visible;height:100%;" onload="showTime();">
	<div id="cc" class="easyui-layout" style="height:100%;">
		<!-- 页面顶部top及菜单栏 -->  
	    <div data-options="region:'north',height:40,border:false" style="background-color:#e6f5ff">
	    	<div class="header">
    			<div style="float:left;"><img src="${pageContext.request.contextPath}/images/logo.png"></div>
    			<div style="float:left;margin-top:10px;width:50px;"><omc:version/></div>
				<div style="text-align:center;float:right;">
					<span id='timeInfo' style="font-size:13px;"></span>
					<spring:message code="currentUser"/>:
					<a href="#" id="sb" class="easyui-splitbutton" data-options="menu:'#menu'">${user.name}</a>
					<div id="menu">   
					    <div data-options="iconCls:'icon-edit'" onclick="$('#modifyPwd').dialog('open');"><spring:message code="changePassword"/></div>
					    <div data-options="iconCls:'icon-cancel'" onclick="location='${pageContext.request.contextPath}/user/logout.action'"><spring:message code="logout"/></div>   
					</div>　
					<spring:message code="currentTheme"/>:
					<a href="#" id="sb" class="easyui-splitbutton" data-options="menu:'#menu2'">
						<span style="color:${theme}"><spring:message code="${theme}"/></span>
					</a>
					<div id="menu2">   
					    <div onclick="location='${pageContext.request.contextPath}/user/switchTheme.action?theme=blue'"><spring:message code="blue"/></div>
					    <div onclick="location='${pageContext.request.contextPath}/user/switchTheme.action?theme=black'"><spring:message code="black"/></div>
					    <div onclick="location='${pageContext.request.contextPath}/user/switchTheme.action?theme=gray'"><spring:message code="gray"/></div>
					</div>　
				</div>
			</div> 
	    </div>
	    <!-- 页面底部信息 -->
	    <div data-options="region:'south',height:18,border:false" style="text-align:center">
	    	Copyright(C) sunkaisens.com. All Rights Reserved
	    </div>  
		<!-- 左侧导航菜单 -->	    
	    <div data-options="region:'west',width:220" title="<spring:message code="navigation"/>:">
			<ul id="tt1" class="easyui-tree" data-options="animate:true,dnd:true"></ul>
	    </div>  
	    <!-- 主显示区域选项卡界面 -->
	    <div data-options="region:'center'" id="center">
	    	<div id="tt" class="easyui-tabs" data-options="fit:true,border:false"> 
	    		<div title="<spring:message code="homePage"/>" data-options="">
	    			<iframe id='iframe' name='iframe' 
	    				 style="width:100%;height:100%;border:0;background: url('${pageContext.request.contextPath}/images/content.png') top center no-repeat;background-size:cover;margin:0;padding:0"></iframe>
	    		</div>
	    	</div>
	    </div>
	    <div id="dd"></div>
	</div>
	
	<div id="mm" class="easyui-menu" data-options="onClick:menuHandler,width:120">
		<div data-options="name:'close',iconCls:'icon-remove'"><spring:message code="closeCurrentPage"/></div>
		<div class="menu-sep"></div>
		<div data-options="name:'others'"><spring:message code="closeOtherPage"/></div>
		<div class="menu-sep"></div>
		<div data-options="name:'all'"><spring:message code="closeAllPage"/></div>
	</div>
	<div id="modifyPwd" class="easyui-dialog" data-options="title:'<spring:message code="changePassword"/>',iconCls:'icon-save',
			modal:true,closed:true,
			buttons:[{text:'<spring:message code="save"/>',handler:modifySelf},{text:'<spring:message code="close"/>',handler:function(){$('#modifyPwd').dialog('close');}}]">
		<form id="ff" method="post" action="${pageContext.request.contextPath}/user/modifySelf.action">
	    <table style="width:100%">
			<tr>
				<td style="padding-left:2px">
					<spring:message code="oldPassword"/>:
				</td>
				<td style="text-align:right;padding-right:2px">
					<input class="easyui-textbox" name="pwd" data-options="type:'password',required:true,prompt:'<spring:message code="pleaseInputOldPassword"/>'"/>
				</td>
			</tr>
			<tr>
				<td style="padding-left:2px">
					<spring:message code="newPassword"/>:
				</td>
				<td style="text-align:right;padding-right:2px">
					<input class="easyui-textbox" id="newPwd" name="newPwd" 
						data-options="validType:'length[6,20]',type:'password',required:true,prompt:'<spring:message code="pleaseInputNewPassword"/>'"/>
				</td>
			</tr>
			<tr>
				<td style="padding-left:2px">
					<spring:message code="affirmPassword"></spring:message>:
				</td>
				<td style="text-align:right;padding-right:2px">
					<input class="easyui-textbox" name="newPwd2" validType="equals['#newPwd','新密码']" data-options="type:'password',required:true,prompt:'<spring:message code="pleaseAffirmNewPasswordAgain"/>'"/>
				</td>
			</tr>
		</table>
		</form>
	</div>
	<div id="alarmMusic"></div>
</body>
</html>