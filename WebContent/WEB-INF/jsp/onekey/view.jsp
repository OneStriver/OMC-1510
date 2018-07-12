<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<%@ include file="/WEB-INF/jsp/public/common.jspf"%>
<title>一键导入导出</title>
<script type="text/javascript">
function oneexport(){
	$.messager.confirm('<spring:message code="Confirm"/>','<spring:message code="ExportMessage"/>',function(r){    
	    if(r){
	    	location="${pageContext.request.contextPath}/onekey/export.action";
	    	//var url="${pageContext.request.contextPath}/onekey/exportUI.action";
	    	//open(url,null,"height=200,width=400,status=yes,toolbar=no,menubar=no,location=no");
	    }    
	});
}
function oneimport(){
	$.messager.progress({msg:'<spring:message code="Uploading"></spring:message>'});
	$('#ff').form('submit',{
		success:function(data){
			submitSuccess(data);
			$.messager.progress('close');
		},
		onSubmit:function(){
			if($(this).form('enableValidation').form('validate')){
				return true;
			}else{
				$.messager.progress('close');
				return false;
			}
		}
	});
}

</script>
</head>
<body>
	<div style="text-align:center;height:50px;">
		<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-remove',size:'large'" onclick="oneexport()"><spring:message code="Export"/></a>
	</div><br/>
	<div style="text-align:center;height:100px;">
		<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add',size:'large'" onclick="$('#w').window('open');"><spring:message code="Import"/></a>
	</div>
	
	<div id="w" class="easyui-window" title="<spring:message code="UploadPackage"></spring:message>" data-options="modal:true,closed:true,iconCls:'icon-save'" style="text-align:center;width:600px;padding:10px;top:100px">
		<form id="ff" method="post" enctype="multipart/form-data" action="${pageContext.request.contextPath}/onekey/import.action">
			<label><spring:message code="SelectPackage(ZIPFormat)"/></label><br/><br/>
			
			<input class="easyui-filebox" id="upfile" name="file" data-options="onChange:function(){
				var inputFileName = $(this).filebox('getValue');
				var fileName = inputFileName.substring(inputFileName.lastIndexOf('\\')+1,(inputFileName.length));
				$(this).filebox('setValue',fileName);
			},buttonText:'<spring:message code="SelectPackage"></spring:message>',required:true,prompt:'<spring:message code="SelectUploadPackage"></spring:message>'" style="width:100%"/>
			
			<br/><br/>
			<a href="#" class="easyui-linkbutton" onclick="oneimport()"><spring:message code="OneKeyImport"/></a>
		</form>
	</div>
</body>
</html>