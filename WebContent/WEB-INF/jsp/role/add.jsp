<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<%@ include file="/WEB-INF/jsp/public/common.jspf"%>
<title>增加角色</title>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/common.js"></script>
<script type="text/javascript">
function submit(){
	var validated=$('form').form('enableValidation').form('validate');
	if(!validated) return;
	var cnodes = $('#tt').tree('getChecked');
	var inodes = $('#tt').tree('getChecked', 'indeterminate');	// 获取不确定的节点
	var nodes=$.merge(cnodes,inodes);
	if(nodes.length==0){
		$.messager.alert('警告','请选取您需要的权限');
		return;
	}
	var privilegeIds=$.map(nodes, function(n){
		return n.id;
	});
	var name=$('input[name=name]').val();
	var description=$('input[name=description]').val();
	$.ajax({
		   type: "POST",traditional:true,
		   url: "${pageContext.request.contextPath}/role/add.action",
		   data: {name:name,description:description,'privilegeIds':privilegeIds},
		   success: function(data){
			   	if(submitSuccess(data)){
					window.location='${pageContext.request.contextPath}/role/listUI.action';
			   	}
		   }
		});
}

</script>
</head>
<body>
<div style="width:500px;float:left">
	<div class="easyui-panel" style="padding:5px;">
		<ul id="tt" class="easyui-tree" data-options="
			url:'${pageContext.request.contextPath}/privilege/tree.action',
			method:'get',lines:true,
			animate:true,checkbox:true"></ul>
	</div>
</div>
<div style="float:left">
	<div class="easyui-panel" title="" style="padding:10px;float:left">
	<form class="easyui-form" data-options="novalidate:true" method="post">
		<label><spring:message code="RoleName"/>:</label>
		<input class="easyui-textbox" name="name" data-options="required:true"><br/><br/>
		<label><spring:message code="Description"/>:</label>
		<input class="easyui-textbox" name="description" data-options="multiline:true,required:true,height:200"><br/><br/>
		<div style="text-align:center">
			<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-save'" onclick="submit()"><spring:message code="Submit"/></a>
		</div>
	</form>
	</div>
</div>	
</body>
</html>