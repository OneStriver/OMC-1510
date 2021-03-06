<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<%@ include file="/WEB-INF/jsp/public/common.jspf"%>
<title>修改配置</title>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/public/grid.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/sbc/sbc.js"></script>
</head>
<body>
   <c:forEach items="${items}" var="item">
	<%-- ---------------------表格开始------------------- --%>
	<c:choose>
	<c:when test="${item.formtype=='grid'}">
		<div id="tb">
			<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" 
				onclick="append(this)">添加</a>
			<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" 
				onclick="removeit(this,'${pageContext.request.contextPath}/sbc/delete${packid}.action')">删除</a>
			<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-save',plain:true" 
				onclick="accept(this,'${pageContext.request.contextPath}/sbc/update.action',
					'${pageContext.request.contextPath}/sbc/save.action')">保存</a>
		</div>
		<table id="grid_${item.id}" class="easyui-datagrid" data-options="
			striped:true,onClickCell:onClickCell,pagination:true,fit:true,
			fitColumns:false,toolbar:'#tb',rownumbers:true,loadFilter:loadFilter,
			pageList: [50,100,150,200],title:'黑名单列表',
			pageNumber:${pageBean.page},pageSize: ${pageBean.pageSize},
			url:'${pageContext.request.contextPath}/sbc/load${packid}Grid.action?id=${item.id}'
			">
		    <thead>   
		        <tr>
		        	<th data-options="field:'ck',checkbox:true"></th>
		        	<th data-options="field:'RecId',hidden:true"></th>
		       		<c:forEach items="${item.children}" var="child">
		       		<%@ include file="/WEB-INF/jsp/sbc/td.jspf"%>
		            <th data-options="field:'${child.name}',width:200,
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