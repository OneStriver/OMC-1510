<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<%@ include file="/WEB-INF/jsp/public/common.jspf"%>
</head>
<body>
	<table>
		<thead>
			<tr><th><spring:message code="SerialNumber"></spring:message></th><th><spring:message code="ConfigurationItemName"></spring:message></th><th><spring:message code="ConfigurationItemDescription"></spring:message></th><th><spring:message code="ConfigFile"></spring:message></th></tr>
		</thead>
		<tbody>
		<c:forEach items="${items}" var="item" varStatus="status">
			<tr>
				<td>${status.count}</td>
				<td>${item.name}</td>
				<td>${item.description}</td>
				<td>${item.config.name}</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
</body>
</html>