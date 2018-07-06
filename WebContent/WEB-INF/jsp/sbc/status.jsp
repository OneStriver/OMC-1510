<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<%@ include file="/WEB-INF/jsp/public/common.jspf"%>
<title>用户注册状态列表</title>
<script type="text/javascript">
function loadFilter(data){
	if(data.error){
		$.messager.alert('出错提示',data.error,'error');
		data = {
			total:0,
			rows: []
		};
	}
	return data;
}
</script>
</head>
<body>
   <c:forEach items="${items}" var="item">
	<%-- ---------------------表格开始------------------- --%>
	<c:choose>
	<c:when test="${item.formtype=='grid'}">
		<table id="grid_${item.id}" class="easyui-datagrid" data-options="
			striped:true,pagination:true,fit:true,
			fitColumns:false,rownumbers:true,loadFilter:loadFilter,
			pageList: [50,100,150,200],title:'用户注册状态列表',
			pageNumber:${pageBean.page},pageSize: ${pageBean.pageSize},
			url:'${pageContext.request.contextPath}/sbc/load${packid}Grid.action?id=${item.id}'
			">
		    <thead>   
		        <tr>
		        	<!-- <th data-options="field:'ck',checkbox:true"></th> -->
		        	<th data-options="field:'RecId',hidden:true"></th>
		       		<c:forEach items="${item.children}" var="child">
		       		<%@ include file="/WEB-INF/jsp/sbc/td.jspf"%>
		            <th data-options="field:'${child.name}',width:100,
		            	<c:if test="${child.name=='RegisterTime'||child.name=='LastRegisterTime'}">
		            		formatter:function(value,row,rowIndex){
								return new Date(value*1000).format();
							},
		            	</c:if>
		            	<c:if test="${child.formtype=='combobox'}">
		            		
		            		formatter:function(value,row,rowIndex){
		            			var v={};
		            		<c:forEach items="${child.optiones}" var="options">
		            			v['${options.val}']='${options.text}';
							</c:forEach>
								return v[value];
							},
		            	</c:if>
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