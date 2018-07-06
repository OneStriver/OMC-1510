<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<%@ include file="/WEB-INF/jsp/public/common.jspf"%>
<title>网卡列表</title>
<script>
var isFinished=undefined;
function onSelect(title,index){
	if(title=='<spring:message code="StaticNetworkCard"></spring:message>'&&!isFinished){
		document.getElementById('staticDiv').innerHTML=
			"<iframe style='width:100%;height:100%;border:0;scrolling:auto' "+
			"src='${pageContext.request.contextPath}/eth/listStaticUI.action'></iframe>";
		isFinished=true;
	}
}
</script>
</head>
<body style="margin:0px">
<div class="easyui-tabs" id="tt" data-options="onSelect:onSelect">
	<!-- 活动网卡 -->
	<div title="<spring:message code="ActiveNetworkCard"></spring:message>" id="activityDiv">
		<iframe style="width:100%;height:100%;border:0;scrolling:auto" 
			src='${pageContext.request.contextPath}/eth/listActivateUI.action'></iframe>
	</div>
	
	<!-- 静态网卡 -->
	<div title="<spring:message code="StaticNetworkCard"></spring:message>" id="staticDiv">
	<!--  
		<iframe style='width:100%;height:100%;border:0;scrolling:auto' 
			src='${pageContext.request.contextPath}/eth/listStaticUI.action'></iframe>
	-->
	</div>
</div>
</body>
</html>