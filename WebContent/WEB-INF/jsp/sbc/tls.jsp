<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<%@ include file="/WEB-INF/jsp/public/common.jspf"%>
<title>修改配置</title>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/sbc/sbc.js"></script>
<script type="text/javascript">
function load(){
	var names=$.map($('#ff input[name]').get(),function(i){return i.name;});
	if(names&&names.length){
		$.messager.progress({msg:'正在加载请稍等'});
		$.ajax({type:'post',traditional:true,data:{names:names},
			url:contextPath+'/sbc/loadTlsScalar.action',
			success:function(data){
				$.messager.progress('close');
				var json=$.parseJSON(data);
				if(json.error){
					data='发生错误'+json.error;
					$.messager.alert('出错提示',data,'error');
				}else{
					$('#TLSFunc').combobox('select',json.TLSFunc);
					var arr=['TLSFunc','LocalTlsIP','LocalTlsPort','RemoteTlsIP','RemoteTlsPort'];
					for(var i in json){
						if(arr.indexOf(i)>-1){
							$('input[textboxname='+i+']').textbox('setValue',json[i]);
						}else{
							$('input[textboxname='+i+']').filebox({prompt:json[i]});
						}
					}
				}
				onSelect({value:$('#TLSFunc').combobox('getValue')});
			}
		});
	}
}

function onSelect(record){
	var arr=['ClientCertificate','ClientKey','CACertificate'];
	if(record.value=='1'){
		for(var i=0;i<arr.length;i++){
			$('input[textboxname='+arr[i]+']').filebox({disabled:false,required:true}).prev().children('span').css('display','');
		}
	}else if(record.value=='0'){
		for(var i=0;i<arr.length;i++){
			$('input[textboxname='+arr[i]+']').filebox({disabled:true,required:false}).prev().children('span').css('display','none');;
		}
	}
}

$(function(){
	load();
});
</script>
</head>
<body>
<div class="easyui-panel" title=" " data-options="fit:true,tools:[{iconCls:'icon-reload',handler:load}]">
	<div style="padding:10px 50px 20px 50px;">
	<form id="ff" method="post" enctype="multipart/form-data"
	 	action="${pageContext.request.contextPath}/sbc/updateTlsScalar.action">
    	<div style="float:left;margin:8px">
    		<label style="display:block;width:145px;float:left;text-align:right">
    			<span style="color:red;">*</span><spring:message code="TLSFunctionSwitch"></spring:message>:
    		</label>
    		<select class="easyui-combobox" name="TLSFunc" id="TLSFunc"
    			data-options="editable:false,width:130,panelHeight:'auto',required:true,onSelect:onSelect">
    				<option value="0"><spring:message code="Close"></spring:message></option>
    				<option value="1"><spring:message code="Open"></spring:message></option>
			</select>
    	</div>
    	<div style="float:left;margin:8px">
    		<label style="display:block;width:145px;float:left;text-align:right">
    			<span style="color:red;">*</span><spring:message code="ClientCertificateFile"></spring:message>:
    		</label>
    		<input class="easyui-filebox" id="ClientCertificate" name="ClientCertificate" data-options="buttonText:'<spring:message code="SelectFile"></spring:message>',required:true"/>
    	</div>
    	<div style="float:left;margin:8px">
    		<label style="display:block;width:145px;float:left;text-align:right">
    			<span style="color:red;">*</span><spring:message code="ClientKey-ValueFile"></spring:message>:
    		</label>
    		<input class="easyui-filebox" name="ClientKey" data-options="buttonText:'<spring:message code="SelectFile"></spring:message>',required:true"/>
    	</div>
    	<div style="float:left;margin:8px;display:none">
    		<label style="display:block;width:145px;float:left;text-align:right">
    			<span style="color:red;">*</span><spring:message code="ClientCertificateFile"></spring:message>:
    		</label>
    		<input class="easyui-textbox" name="ServerCertificate"/>
    	</div>
    	<div style="float:left;margin:8px;display:none">
    		<label style="display:block;width:145px;float:left;text-align:right">
    			<span style="color:red;">*</span><spring:message code="ClientKey-ValueFile"></spring:message>:
    		</label>
    		<input class="easyui-textbox" name="ServerKey"/>
    	</div>
    	<div style="float:left;margin:8px">
    		<label style="display:block;width:145px;float:left;text-align:right">
    			<span style="color:red;">*</span><spring:message code="CACertificateFile"></spring:message>:
    		</label>
    		<input class="easyui-filebox" name="CACertificate" data-options="buttonText:'<spring:message code="SelectFile"></spring:message>',required:true"/>
    	</div>
    	<div style="float:left;margin:8px;display:none">
    		<label style="display:block;width:145px;float:left;text-align:right">
    			<span style="color:red;">*</span><spring:message code="LocalAddress"></spring:message>:
    		</label>
    		<input class="easyui-textbox" name="LocalTlsIP" data-options="validType:'ip',required:false"/>
    	</div>
    	<div style="float:left;margin:8px">
    		<label style="display:block;width:145px;float:left;text-align:right">
    			<span style="color:red;">*</span><spring:message code="LocalPort"></spring:message>:
    		</label>
    		<input class="easyui-numberbox" name="LocalTlsPort" data-options="validType:['minValue[0]','maxValue[65535]'],required:true"/>
    	</div>
    	<div style="float:left;margin:8px;display:none">
    		<label style="display:block;width:145px;float:left;text-align:right">
    			<span style="color:red;">*</span><spring:message code="RemoteAddress"></spring:message>:
    		</label>
    		<input class="easyui-textbox" name="RemoteTlsIP" data-options="validType:'ip',required:false"/>
    	</div>
    	<div style="float:left;margin:8px">
    		<label style="display:block;width:145px;float:left;text-align:right">
    			<span style="color:red;">*</span><spring:message code="RemotePort"></spring:message>:
    		</label>
    		<input class="easyui-numberbox" name="RemoteTlsPort" data-options="validType:['minValue[0]','maxValue[65535]'],required:true"/>
    	</div>
    </form>
    </div>
	<div style="text-align:center;padding:5px;clear:both;margin-top:200px">
		<a href="#" class="easyui-linkbutton" onclick="load()"><spring:message code="Refresh"></spring:message></a>
		<a href="#" class="easyui-linkbutton" onclick="submitForm()"><spring:message code="Submit"></spring:message></a>
		<a href="#" class="easyui-linkbutton" onclick="clearForm()"><spring:message code="Clear"></spring:message></a>
	</div>
</div>
</body>
</html>