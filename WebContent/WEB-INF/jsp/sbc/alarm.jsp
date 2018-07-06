<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<%@ include file="/WEB-INF/jsp/public/common.jspf"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/sbc/sbc.js"></script>
<title>运行告警列表</title>
</head>
<body>
   <c:forEach items="${items}" var="item">
	<%-- ---------------------表格开始------------------- --%>
	<c:choose>
	<c:when test="${item.formtype=='grid'}">
		<table id="grid_${item.id}" class="easyui-datagrid" data-options="
			striped:true,pagination:true,fit:true,
			fitColumns:true,rownumbers:true,loadFilter:loadFilter,
			pageList: [50,100,150,200],title:'运行告警列表',
			pageNumber:${pageBean.page},pageSize: ${pageBean.pageSize},
			url:'${pageContext.request.contextPath}/sbc/load${packid}Grid.action?id=${item.id}'
			">
		    <thead>   
		        <tr>
		        	<th data-options="field:'ck',checkbox:true"></th>
		        	<th data-options="field:'RecId',hidden:true"></th>
		       		<c:forEach items="${item.children}" var="child">
		       		<%@ include file="/WEB-INF/jsp/sbc/td.jspf"%>
		            <th data-options="field:'${child.name}',
		            	<c:if test="${child.formtype=='hidden'}">hidden:true,</c:if>
						editor:{
							type:'${cformtype}',
							options:{
								data: [
								<c:forEach items="${child.optiones}" var="options">
									{text:'${options.text}',value:'${options.val}'},
								</c:forEach>
								],
								panelHeight:'auto',
								<c:if test="${child.formtype!='hidden'}">required:${child.required},</c:if>
								<c:if test='${cvalidType!=null}'>validType:${cvalidType},</c:if>
								min:${child.min==null?-99999:child.min},
    							max:${child.max==null?99999:child.max}
							}
					}">${child.description}</th>
		        	</c:forEach> 
		        </tr>
		    </thead>
		</table>
	</c:when>
	</c:choose>
	<%-- ---------------------表格结束------------------- --%>
   </c:forEach>
</body>
</html>