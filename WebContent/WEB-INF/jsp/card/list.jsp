<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>板卡列表</title>
<%@ include file="/WEB-INF/jsp/public/common.jspf"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/public/grid.js?<%=new Date().getTime()%>"></script>

<script type="text/javascript">
function reboot(){
	$.messager.confirm('操作提示','该操作会引起系统重启,确定要执行?',function(yes){
		if(yes){
			$.post(contextPath+'/card/reboot.action',function(data){
				$.messager.show({
					msg:$.parseJSON(data).msg,
					title:'操作提示',showType:'fade',timeout:1000,style:{right:'',bottom:''}
				});
			});
		}
	}
	
  );
}
	
function seriNum(){
	$.messager.progress({msg:'正在读取请稍等'});
	$.ajax({
	   type: "POST",
	   url:  contextPath+"/serialNumber/listSerialNumber.action",
	   success: function(msg){
		   var json=$.parseJSON(msg);
		   $.messager.progress('close');
		   if(json&&json.error) {
				$.messager.alert('出错提示',json.error,'error');
				return false;
			}else{
		        $.messager.show({
			    height:320,
			    width:300,
				title:'板卡序列号',
				msg:function(){
					var message = '';
					var arr = json.list;
					for(var i = 0 ;i < arr.length;i++){
						message+='<div style="font-size:14px"><p style="color:#323232">'+arr[i].name+':</p><p style="color:#555555;line-height:5px">'+"“"+arr[i].serial+"”"+"</p>"+'<div class="easyui-linkbutton" id='+arr[i].name+' onclick="downLoad(this)"data-options="height:30,width:60">导出</div>'+'</div>'+'<br>';
					}
					return message;
				},
				timeout:10000,
				style:{
					right:'',
					bottom:''
				}

			});
		  }
	   }
	});
}
	
function reSet(){
	$.messager.confirm('恢复出厂设置','<div style="font-size:12px"><span style="color:#f23737">注意:</span>此操作将会还原系统出厂状态,<br/>并且<span style="color:#f23737">重启系统</span>，确认执行？</div>',function(r){    
	    if (r){    
	    	$.messager.progress({msg:'正在进行设置请稍等'});
			$.ajax({
				type:'post',
				url:contextPath+"/resetLf/reset.action",
				success:function(data){
					$.messager.progress('close');
					submitSuccess(data);
					setTimeout(function(){reboot2();},1100);
				}
			});			
	    }	    
	}); 
}
	
function reboot2(){
	$.post(contextPath+'/card/reboot.action',function(data){
		$.messager.show({
			msg:$.parseJSON(data).msg,
			title:'操作提示',showType:'fade',timeout:1000,style:{right:'',bottom:''}
		});
	});
}
	
	function downLoad(i){
		console.log(i.id);
		var url =contextPath+"/serialNumber/downLoad.action?name="+i.id;
//		location = url
//		document.URL=url
//		location.href = url;
		open(url,null,"height=200,width=400,status=yes,toolbar=no,menubar=no,location=no"); 
//		var url = contextPath+"/serialNumber/downLoad.action";
//		$.ajax({
//			type:'post',
//			data:{name:i.id},
//			url:url,
//			success:function(data){
//				
//			}
//		});
	}
	
$('#dg').datagrid({
	onClickCell:function(index,field,value){
		alert(value);
	}
})

</script>
</head>
<body>
	<table id="dg" class="easyui-datagrid" title="<spring:message code="CardList"/>" style="width:800px;height:auto"
		data-options="
			url:'${pageContext.request.contextPath}/card/list.action',
			rownumbers:true,
			fit:true,striped:true,border:false,
			pageList: [10,20,30,40,50,60],
			pageNumber:${pageBean.page},
			pagination:true,
			toolbar: '#tb',
			loadFilter:loadFilter,
			onClickCell:onClickCell,
			pageSize: ${pageBean.pageSize}">
	<thead>
		<tr>
			<th data-options="field:'ck',checkbox:true"></th>
			<th data-options="field:'id',hidden:true"></th>
			<th data-options="field:'name',width:'10%',editor:'textbox'"><spring:message code="CardName"/></th>
			<th data-options="field:'cardNum',width:'10%',editor:{type:'numberbox',options:{validType:'cardNum'}}"><spring:message code="CardNumber"/></th>
			<th data-options="field:'slotId',width:'10%',editor:{type:'numberbox',options:{validType:'slotNum'}}"><spring:message code="SlotNumber"/></th>
			<th data-options="field:'ip',width:'10%',editor:{type:'textbox',options:{validType:'ipAdd'}}" ><spring:message code="IPAddress"/></th>
				
			<th data-options="field:'serial',width:'40%',
				formatter:function(value,row,rowIndex){
					return value?'['+value+']':'';
				}">板卡序列号</th>
			<th data-options="field:'oamStatus',width:'10%',
				formatter:function(value,row,rowIndex){
					return value?'<font color=green>已启动</font>':'<font color=red>未启动</font>';
				}">板卡状态</th>
		</tr>
	</thead>
	</tbody>
	</table>
	
	<div id="tb" style="height:auto">
		<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="append()" ><spring:message code="Add"/></a>
		<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="removeit(
				'${pageContext.request.contextPath}/card/delete.action')"><spring:message code="Delete"/></a>
		<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-save',plain:true" onclick="accept(
			'${pageContext.request.contextPath}/card/update.action',
				'${pageContext.request.contextPath}/card/save.action')" ><spring:message code="save"/></a>
		<a href="#" class="easyui-linkbutton" data-options="plain:true" onclick="reboot()">重启全部板卡</a>
		<!-- 
		<a href="#" class="easyui-linkbutton" data-options="plain:true" onclick="seriNum()">板卡序列号</a> 
		-->
		<a href="#" class="easyui-linkbutton" data-options="plain:true" onclick="reSet()">恢复出厂配置</a>
	</div>
</body>
</html>