<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<title>板卡序列号</title>
<%@ include file="/WEB-INF/jsp/public/common.jspf"%>

</head>
<body>
	<div class="easyui-panel" data-options="fit:true,border:false">
		<c:forEach items="${list}" var="o">
			<div class="easyui-panel" data-options="title:'${o.name}序列号'" style="text-align:center;font-size:20px;">
				${o.serial}
			</div>
		</c:forEach>
	</div>
</body>
</html>