<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<title>号码转换表</title>
<%@ include file="/WEB-INF/jsp/public/common.jspf"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/public/grid.js"></script>
<script type="text/javascript">
function submitForm(){
	$('#ff').form('submit',{
		success:function(data){
			$.messager.progress('close');
			submitSuccess(data);
		},
		onSubmit:function(){
			var isOk=$(this).form('enableValidation').form('validate');
			if(isOk) $.messager.progress({msg:'正在保存请稍等'});
			return isOk;
		}
	});
}

function onChange(newValue, oldValue){
	$('#ff').form('enableValidation').form('validate');
}

function removeSwitch(record){
	$.messager.confirm('删除', '您确定要删除吗？', function(r){
		if (r){
			record.parent().remove();
		}
	});
}

function add(){
	var ori=$('<input class="easyui-textbox" name="ori"/>');
	var des=$('<input class="easyui-textbox" name="des"/>');
	var div=$('<div/>').append(ori).append(' ==> ').append(des).append(' ')
		.append('<a href="#" class="easyui-linkbutton" onclick="removeSwitch($(this))">删除</a>');
	$('#ff').append(div);
	ori.textbox({onChange:onChange,required:true,validType:['numberTranslation','numberTranslationFrom']});
	des.textbox({onChange:onChange,required:true,validType:['numberTranslation','numberTranslationTo']});
}


$(function(){
	setInterval(function(){
		$('#ff').form('enableValidation').form('validate');
	},1000);
	
});

</script>
</head>
<body>
	<div class="easyui-panel" data-options="fit:true,border:false" style="padding:10px;">
		<form id="ff" action="${pageContext.request.contextPath}/switch/update.action" method="post">
			  <!-- 有数据时返回 -->
			  <c:forEach items="${nts}" var="nt">
			    <c:choose>
			      <c:when test="${nt.ori.startsWith('*')}">
				       <div>
						<input class="easyui-textbox" name="ori" data-options="readonly:true,disabled:true,required:true,editable:false,value:'${nt.ori}'"/>
						==>
						<input class="easyui-textbox" name="des" data-options="readonly:true,disabled:true,required:true,editable:false,value:'${nt.des}'"/>
					   </div>
			      </c:when>
			      <c:otherwise>
				      <div>
						<input class="easyui-textbox" name="ori" data-options="validType:['numberTranslation','numberTranslationFrom'],required:true,value:'${nt.ori}'"/>
						==>
						<input class="easyui-textbox" name="des" data-options="validType:['numberTranslation','numberTranslationTo'],required:true,value:'${nt.des}'"/>
					    <omc:permit url="switch/deleteSwitch">
						<a href="#" class="easyui-linkbutton" onclick="removeSwitch($(this))">删除</a>
					    </omc:permit>
					  </div>
			      </c:otherwise>
			    </c:choose>
			  </c:forEach>
		</form>
		
		<div style="padding-left:100px">
			<omc:permit url="switch/addSwitch">
				<a href="#" class="easyui-linkbutton" onclick="add()">添加转换</a>
			</omc:permit>
			<omc:permit url="switch/switchSave">
				<a href="#" class="easyui-linkbutton" onclick="submitForm()">保存</a>
			</omc:permit>
		</div>
	</div>
</body>
</html>