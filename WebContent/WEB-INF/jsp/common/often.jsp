<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<%@ include file="/WEB-INF/jsp/public/common.jspf"%>
<title>网元常用配置</title>
<script type="text/javascript" src="js/easyui/jquery.min.js"></script>
<script type="text/javascript">
<c:if test="${empty items}">
$(function(){
	$.messager.alert({title:'提示',msg:'此配置页面中还未设定配置项！',icon:'info',top:'100'});
});
</c:if>

function submit(){
	var validata=$('#form').form('enableValidation').form('validate');
	if(!validata) return;
	var arr=$('form').serializeArray();
	$.messager.progress();
	$.post("${pageContext.request.contextPath}/common/modify.action", 
		{"jsonarr":JSON.stringify(arr)},
		function(data){
			submitSuccess(data);
			$.messager.progress('close');
		}
	);
}

</script>
</head>
<body>
<form id="form">
<c:forEach items="${items}" var="item">
	<%request.setAttribute("old", "\n");%>
	<c:set var="new1" value="\\n"/>
	<div style="margin:8px;">
    		<label style="width:20%;display:block;float:left;text-align:right">
    			<c:if test="${item.required}"><span style="color:red;">*</span></c:if>
    			${item.name}:
    		</label>
    		<!-- 判断输入类型 开始 -->
    		<c:choose>
				<c:when test="${item.formtype == 'textbox'}">
					<c:set var="formtype" value="textbox"/>
					<c:set var="validType" value="length[${item.minLen},${item.maxLen}]"/>
				</c:when>
				<c:when test="${item.formtype == 'numberbox'}">
					<c:set var="formtype" value="numberbox"/>
					<c:set var="validType" value="null"/>
				</c:when>
				<c:when test="${item.formtype == 'url'}">
					<c:set var="formtype" value="textbox"/>
					<c:set var="validType" value="url"/>
				</c:when>
				<c:when test="${item.formtype == 'email'}">
					<c:set var="formtype" value="textbox"/>
					<c:set var="validType" value="email"/>
				</c:when>
				<c:when test="${item.formtype == 'ip'}">
					<c:set var="formtype" value="textbox"/>
					<c:set var="validType" value="ip"/>
				</c:when>
				<c:when test="${item.formtype == 'combobox'}">
					<c:set var="formtype" value="combobox"/>
				</c:when>
				<c:when test="${item.formtype == 'radio'}">
					<c:set var="formtype" value="radio"/>
				</c:when>
			</c:choose>
    		<!-- 判断输入类型 结束 -->
    		<c:choose>
    		<c:when test="${formtype=='combobox'}">
	    		<select id="${item.id}" class="easyui-combobox" name="${item.id}" data-options="editable:false,width:'50%',panelHeight:'auto'">
	    			<c:forEach items="${item.optiones}" var="options">
	    				<option value="${options.val}" <c:if test="${item.value==options.val}">selected</c:if>>${options.text}</option>
	    			</c:forEach>
				</select>
			</c:when>
			<c:when test="${formtype=='radio'}">
				<div style="width:50%;float:left;">
				<c:forEach items="${item.optiones}" var="options" varStatus="status">
					<label for="${item.name}_${status.count}"><input id="${item.name}_${status.count}" <c:if test="${item.value==options.val}">checked</c:if>
						type="radio" name="${item.id}" value="${options.val}"/>${options.text}</label>
	    		</c:forEach>
	    		</div>
			</c:when>
			<c:otherwise>
	    		<input class="easyui-${formtype}" name="${item.id}" id="${item.id}"
	    			data-options="required:${item.required},
	    							width:'50%',
	    							min:${item.min==null?-99999:item.min},
			    					max:${item.max==null?99999:item.max},
	    							value:'${fn:replace(item.value,old,new1)}',
	    							multiline:${item.multiline}
	    				<c:if test='${item.multiline}'>,height:88</c:if>"/>
	    	</c:otherwise>
	    	</c:choose>
	    	<b>${item.description}</b>
    		<br/>
   	</div>
</c:forEach>
<div style="padding:5px;margin-left:150px">
	<a class="easyui-linkbutton" href="${pageContext.request.contextPath}/common/page.action">返回</a>　
<c:if test="${!empty items}">
	<a href="#" class="easyui-linkbutton" onclick="submit()">提交</a>
</c:if>
</div>
</form>
</body>
</html>