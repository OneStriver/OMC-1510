<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>用户群组优先级配置</title>
<%@ include file="/WEB-INF/jsp/public/common.jspf"%>
<script type="text/javascript">
function submitForm(){
	$('#ff').form('submit',{
		success:function(data){
			$.messager.progress('close');
			submitSuccess(data);
		},
		onSubmit:function(){
			$.messager.progress({msg:'正在保存请稍等'});
		}
	});
}
</script>
</head>
<body>
	<div class="easyui-panel" data-options="fit:true,border:false" style="padding:10px;">
		<form id="ff" action="${pageContext.request.contextPath}/hss/userGroupUpdate.action" method="post">
			<table>
			<tr>
				<td>
					<label style="color:red;font-size: 14px">显示优先级</label>
				</td>
				<td>
					<label style="color:red;font-size: 14px">实际优先级</label>
				</td>
				<td>
					<label style="color:red;font-size: 14px">控制开关</label>
				</td>
				<td>
					<label style="color:red;font-size: 14px">用户类型</label>
				</td>
			</tr>
			<c:forEach items="${allUserGroup}" var="userGroup">
			 <tr>
			 	<td style="display: none">
			 	<input class="easyui-numberbox" name="id" data-options="value:${userGroup.id}"/>
			 	</td>
			 	<td>
					<input class="easyui-numberbox" name="level" data-options="width:100,value:${userGroup.level}"/>
					==>
				</td>
				<td>
					<input class="easyui-numberbox" name="priority" data-options="width:100,value:${userGroup.priority}"/>
					==>
				</td>
				<td>
					<select class="easyui-combobox" name="enable" data-options="editable:false,panelHeight:'50',width:50">
						<option value="0" <c:if test="${userGroup.enable == 0}">selected="selected"</c:if>>关</option>
						<option value="1" <c:if test="${userGroup.enable == 1}">selected="selected"</c:if>>开</option>
					</select>
					==>
				</td>
				<td>
					<select class="easyui-combobox" name="userType" data-options="editable:false,panelHeight:'50',width:90">
						<option value="0" <c:if test="${userGroup.userType == 0}">selected="selected"</c:if>>普通用户</option>
						<option value="1" <c:if test="${userGroup.userType == 1}">selected="selected"</c:if>>群组用户</option>
					</select>
				</td>
		    </tr>
		    </c:forEach>
		    <tr style="height: 10px;"></tr>
		    <tr>
		    	<td colspan="4" align="center">
		    		<a href="#" class="easyui-linkbutton" onclick="submitForm()">保存</a>
		    	</td>
		    </tr>
		    </table>
		</form>
	</div>

</body>
</html>