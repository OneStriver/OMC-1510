<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<%@ include file="/WEB-INF/jsp/public/common.jspf"%>
<title>恢复出厂设置</title>
<script type="text/javascript">
function onClick(){
	$.messager.confirm('确认','您确认想要恢复出厂设置吗？',function(r){    
	    if (r){    
	       $.post(contextPath+'/resetLf/reset.action',function(data){
	    	   submitSuccess(data);
	       });
	    }    
	}); 
}
</script>
</head>
<body>
	<div class="easyui-panel" data-options="footer:'#footer',fit:true,title:'<spring:message code="restoreFactorySetting"></spring:message>'">
		<div style="text-align:center;font-size:50px;">
			注意:此操作将会还原系统出厂状态,<br/>
			           在系统重启后生效。
		</div>
		<div id="footer" style="border:0px;text-align:center;">
			<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search',size:'large',onClick:onClick"><spring:message code="restoreFactorySetting"></spring:message></a>
		</div>
	</div>
</body>
</html>