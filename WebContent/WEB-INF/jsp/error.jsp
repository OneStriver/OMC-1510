<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>${error}
<%-- <html>
	<head>
	<%@ include file="/WEB-INF/jsp/public/common.jspf"%>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/public/grid.js"></script>
	<title>error page</title>
	<script type="text/javascript">
		 function pageLoad(){
			eval('var data = ${error}');
			//alert(data.error);
			if(data.error){
				$.messager.alert('出错提示',data.error,'error');
			}
		} 
	</script>
	</head>
	<body onload="pageLoad();" style="width:300px;height:300px"></body>
</html> --%>
