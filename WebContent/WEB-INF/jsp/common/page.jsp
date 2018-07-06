<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<%@ include file="/WEB-INF/jsp/public/common.jspf"%>
<title>网元常用配置</title>
<style type="text/css">
</style>
<script type="text/javascript">
<c:if test="${empty commons}">
$(function(){
	$.messager.alert({title:'提示',msg:'暂未提供相关配置页面！',icon:'info',top:'100'});
});
</c:if>
</script>
</head>
<body>
<c:forEach items="${commons}" var="common">
	<div style="padding-left:50px;margin-top:10px;">
		<a class="easyui-linkbutton" data-options="size:'large'" style="width:200px"
			href="${pageContext.request.contextPath}/common/component.action?id=${common.id}">${common.name}</a>
	</div>
</c:forEach>
</body>
</html>