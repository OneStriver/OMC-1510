<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<%@ include file="/WEB-INF/jsp/public/common.jspf"%>
<style type="text/css">
th,td{
	border:1px solid black;
}
</style>
</head>
<body>
	<table style="text-align:center;border-collapse:collapse;">
		<thead>
			<tr>
				<th><spring:message code="Number"></spring:message></th>
				<th><spring:message code="AssociatedConfigurationItemName"></spring:message></th>
				<th><spring:message code="AssociatedConfigurationItemDescription"></spring:message></th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${items}" var="item" varStatus="status">
			<tr>
				<td>${status.count}</td>
				<td>${item.name}</td>
				<td>${item.description}</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
</body>
</html>