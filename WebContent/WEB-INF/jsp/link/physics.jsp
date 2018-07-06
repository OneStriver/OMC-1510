<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>物理链路</title>
<%@ include file="/WEB-INF/jsp/public/common.jspf"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/easyui/datagrid-detailview.js"></script>
</head>
<body>
	<tr>
		<td>
		<a href="#" class="easyui-linkbutton" onclick="location='${pageContext.request.contextPath}/link/ping.action'">ping</a>
		</td>
	</tr>
	<tr>
		<td>
		${line}
		</td>
	</tr>
	
</body>
</html>
