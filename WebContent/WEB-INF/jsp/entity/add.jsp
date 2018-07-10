<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<%@ include file="/WEB-INF/jsp/public/common.jspf"%>
<title>下发配置</title>
<script type="text/javascript" async="async">
var pc;
$.parser.onComplete = function(){
	if(pc) clearTimeout(pc);
	pc = setTimeout(closes, 1000);
}
function closes(){
	$("#loading").fadeOut("normal",function(){
		$(this).remove();
	});
}

$(function(){
	var attr={
		height:$(document.body).height(),
		top:0
	};
	$('#setWindow').window(attr).window('open');
});

function submitForm(){
	var forms=[];
	var soles=[];
	var names=[];
	<c:forEach items="${module.configs}" var="config">
	forms.push('f${config.id}');
	soles.push('${config.sole}');
	names.push('${config.name}');
	</c:forEach>
	var configs=[];
	for(var i=0;i<forms.length;i++){
		var validata=$('#'+forms[i]).form('enableValidation').form('validate');
		if(!validata) return;
		var fields=$('#'+forms[i]).serializeArray();
		fields.forEach(function(item,i,arr){
			var id=null;
			var p1=$(':input[name="'+item.name+'"][value="'+item.value+'"]');
			if(p1&&p1.length!=0){
				id=p1.attr('id');
				if(!id) id=p1.parent().prev(':input').attr('id');
			}else{
				p1=$(':input[name="'+item.name+'"]');
				id=p1.attr('id');
				if(!id) id=p1.parent().prev(':input').attr('id');
			}
			fields[i].id=id;
		});
		var config={name:names[i],fields:fields};
		configs.push(config);
	}
	console.log(configs);
	var data={};
	data.json=JSON.stringify(configs);
	data.moduleId='${module.id}';
	data.cardNum='${entity.card.cardNum}';
	data.name='${entity.name}';
	$.messager.progress();
	$.ajax({type:'post',traditional:true,data:data,
		url:"${pageContext.request.contextPath}/entity/save.action",
		success:function(data){
			$.messager.progress('close');
			var json=$.parseJSON(data);
			console.log(json);
			if(json&&json.error) {
				$.messager.alert('出错提示',json.error,'error');
			}else{
				window.location=contextPath+"/entity/listUI.action";
			}
		}
	});
}

function goBackBefore(){
	window.location=contextPath+"/entity/listUI.action";
}

</script>
</head>
<body>
<div id='loading' style="position:absolute;z-index:1000;top:0px;left:0px;width:100%;height:100%;background-color:#DDDDDB;text-align:center;padding-top: 20%;"><h1><font color="#15428B">加载中···</font></h1></div>

<div id="setWindow" class="easyui-window" title="配置信息" data-options="iconCls:'icon-save',modal:true,fit:true,footer:'#setFooter',collapsible:false,minimizable:false,maximizable:false,closable:false">
<!-- 配置文件循环 开始 -->
<c:forEach items="${module.configs}" var="config">
	<div class="easyui-panel" title="${config.name}" data-options="fit:false">
		<div style="padding:10px 50px 20px 50px">
	    <form id="f${config.id}" method="post">
	    <c:forEach items="${config.items}" var="item">
	    	<%request.setAttribute("old", "\n");%>
	    	<c:set var="new1" value="\\n"/>
	    	<div style="margin:0px">
	    	
	    		<c:if test="${!config.sole}">
		    		<label style="display:block;width:20%;float:left;text-align:right">
		    			<c:if test="${item.required}"><span style="color:red;">*</span></c:if>${item.description}:
		    		</label>
		    		<!-- 判断输入类型 开始 -->
		    		<c:choose>
						<c:when test="${item.formtype == 'textbox'}">
							<c:set var="formtype" value="textbox"/>
							<c:set var="validType" value="tblength[${item.minLen},${item.maxLen}]"/>
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
			    		<select id="${item.id}" class="easyui-combobox" name="${item.name}" data-options="editable:false,width:'50%',panelHeight:'auto'">
			    			<c:forEach items="${item.optiones}" var="options">
			    				<option value="${options.val}" <c:if test="${item.value==options.val}">selected</c:if>>${options.text}</option> 
			    			</c:forEach>
						</select>
					</c:when>
					<c:when test="${formtype=='radio'}">
						<div style="width:50%;float:left;">
						<c:forEach items="${item.optiones}" var="options" varStatus="status">
							<label for="${item.id}"><input id="${item.id}" <c:if test="${item.value==options.val}">checked</c:if>
								type="radio" name="${item.name}" value="${options.val}"/>${options.text}</label>
			    		</c:forEach>
			    		</div>
					</c:when>
					<c:otherwise>
			    		<input class="easyui-${formtype}" name="${item.name}" id="${item.id}"
			    			<c:if test='${not item.multiline}'>value="${fn:replace(item.value,old,new1)}"</c:if>
			    			data-options="required:${item.required},
			    							width:'50%',
			    							min:${item.min==null?-99999:item.min},
			    							max:${item.max==null?99999:item.max},
			    							<c:if test='${validType!=null}'>validType:'${validType}',</c:if>
											value:'${fn:replace(item.value,old,new1)}',
											multiline:${item.multiline}<c:if test='${item.multiline}'>,height:88</c:if>"/>
					</c:otherwise>					
					</c:choose>
	    		</c:if>
	    		
	    		<c:if test="${config.sole}">
	    		<!-- ${item.name} -->
	    			<input class="easyui-textbox" name="${config.name}" id="${item.id}"
		    			data-options="required:${item.required},value:'${fn:replace(item.value,old,new1)}',multiline:true,height:88,width:'70%'"/>
	    		</c:if>
	    	</div><br/>
	    </c:forEach>
	    </form>
	    </div>
	</div>
</c:forEach>
<!-- 配置文件循环 结束 -->
<c:if test="${module.configs.size()==0}">
	<h1 style="text-align:center;">对不起，此网元配置项都已被删除，请重新添加！</h1>
</c:if>
</div>

<div id="setFooter" style="text-align:center;">
	<c:if test="${module.configs.size()!=0}">
		<a href="#" class="easyui-linkbutton" style="margin-right:10px;" onclick="submitForm()">提交</a>
	</c:if>
	<c:if test="${module.configs.size()==0}">
		<a href="#" class="easyui-linkbutton" onclick="goBackBefore()">返回</a>
	</c:if>
</div>
</body>
</html>