<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<title>路由</title>
<%@ include file="/WEB-INF/jsp/public/common.jspf"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/public/grid.js"></script>
<script type="text/javascript">
var websocket=undefined;
function openOspf(){
	if(websocket) websocket.close();
	var content=$('#content');
	$('#ospfW').window('open');
	var cmd=$('#cmd')
	cmd.keydown(function(event){
		switch(event.keyCode) {
		case 27:
			$('#ospfW').window('close');
			websocket.close();
			websocket=undefined;
			break;
		case 13:
			var val=cmd.val();
			if(val=='clear'){
				content.html('');
			}else{
				websocket.send(val);
			}
			cmd.val('');
			break;
		}
	});
	var host = location.host;
	var path = contextPath+'/ws/ospf';
	websocket = new WebSocket(encodeURI('ws://' + host + path));
	websocket.onopen = function() {
		content.html('<spring:message code="ConnectingPleaseWait"/>...\n');
	};
	websocket.onerror = function(){
		content.html(content.html()+'\n连接出错!!!');
	};
	websocket.onmessage = function(message){
		if(message.data=='Connect Success.')
			$('#cmd').prop('disabled',false);
		content.html(content.html()+'<br/>'+message.data);
		$('#ospfW').scrollTop(content.get(0).scrollHeight);
	}
}

function removeit(url){
	var rows=$('#dg').datagrid('getSelections');
	if(rows.length==0) {
		$.messager.alert('提示','请选择要删除的记录!');
		return;
	}
	$.messager.confirm(LOCALE.Confirm,LOCALE.DeleteMessage,
		function(yes){    
			var rows=$('#dg').datagrid('getSelections');
			if(yes&&rows&&rows.length){
				var destinations=[],gateways=[],genmasks=[],cardNums=[];
				$.each(rows,function(i){
					cardNums.push(rows[i]['cardNum']);
					gateways.push(rows[i]['gateway']);
					destinations.push(rows[i]['destination']);
					genmasks.push(rows[i]['genmask']);
				});
				var param={cardNums:cardNums,gateways:gateways,destinations:destinations,genmasks:genmasks};
				$.ajax({type:'post',traditional:true,data:param,
					url:url,
					success:submitSuccess
				});
			}
		}
	);
}

function append(){
	$('#w').window('open');
}

function append1(){
	$('#w1').window('open');
}

function onLoadSuccess(){
	var data=$(this).combobox('getData');
	$(this).combobox('select',data[0].cardNum);
	console.log(data);
}

function submitForm(){
	$('#ff').form('submit',{
		onSubmit:function(){
			return $(this).form('enableValidation').form('validate');
		}
	});
}
function clearForm(){
	$('#ff').form('clear');
}
function submitForm1(){
	$('#ff1').form('submit',{
		onSubmit:function(){
			return $(this).form('enableValidation').form('validate');
		},
		success:function(data){    
			submitSuccess(data);
	    }   
	});
}
function clearForm1(){
	$('#ff1').form('clear');
}
$(function(){
	$('#ff').form({
		success:submitSuccess
	});
});
</script>
</head>
<body>
	<table id="dg" title="<spring:message code="RouteList"></spring:message>" class="easyui-datagrid" data-options="
			url:'${pageContext.request.contextPath}/route/list.action',
			rownumbers:true,
			singleSelect:true,
			loadFilter:loadFilter,
			fit:true,striped:true,border:false,
			pageList: [10,20,30,40,50,60],
			pageNumber:${pageBean.page},
			pagination:true,
			toolbar: '#tb',
			pageSize: ${pageBean.pageSize}">
		<thead>
		<tr>
			<th data-options="field:'ck',checkbox:true"></th>
			<th data-options="field:'destination',width:'20%',
				formatter:function(value,row,rowIndex){
						return '<b>'+value+'</b>';
					}"><spring:message code="DestinationAddress"></spring:message></th>
			<th data-options="field:'gateway',editor:'textbox',width:'20%'"><spring:message code="Gateway"></spring:message></th>
			<th data-options="field:'genmask',editor:'textbox',width:'20%'"><spring:message code="Mask"></spring:message></th>
			<th data-options="field:'flags',editor:'textbox'">Flags</th>
			<th data-options="field:'metric',editor:'textbox'">Metric</th>
			<th data-options="field:'ref',editor:'numberbox'">Ref</th>
			<th data-options="field:'use',editor:'textbox'">Use</th>
			<th data-options="field:'iface',editor:'textbox'"><spring:message code="Interface"></spring:message></th>
			<th data-options="field:'cardNum'"><spring:message code="CardNumber"></spring:message></th>
			
			<!--  
			<th data-options="field:'cardNum',editor:{
					type:'combobox',
					url:'${pageContext.request.contextPath}/card/listjsonarr.action',
					valueField:'cardNum',
					textField:'name',
					panelHeight:'auto',
					required:true
				}">板卡编号</th>-->
		</tr>
		</thead>
	</table>
	<div id="tb" style="height:auto">

		<omc:permit url="route/addRoute"> 

		<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" 
			onclick="append()"><spring:message code="AddRoute"/></a>
		</omc:permit>	
		<omc:permit url="route/defaultRoute"> 
		<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" 
			onclick="append1()">添加默认网关</a>
		</omc:permit>
		<omc:permit url="route/deleteRoute"> 	
		<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" 
			onclick="removeit('${pageContext.request.contextPath}/route/delete.action')"><spring:message code="DeleteRoute"/></a>

		</omc:permit>
		<omc:permit url="route/ospf">

		<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" 
			onclick="openOspf('${pageContext.request.contextPath}/eth/activate.action')"><spring:message code="OSPFOperation"/></a>
	     </omc:permit>
	</div>
	
	<!-- 添加窗口 -->
	<div id="w" class="easyui-window" title="<spring:message code="AddRoute"/>" 
		data-options="modal:true,closed:true,iconCls:'icon-add'" style="padding:10px;">
		<!-- data-options="novalidate:true" -->
		<form id="ff" class="easyui-form" method="post" action="${pageContext.request.contextPath}/route/save.action">
	    	<table>
	    		<tr>
	    			<td><spring:message code="DestinationAddress"/>:</td>
	    			<td><input class="easyui-textbox" name="destination" data-options="validType:'ip',required:true"/></td>
	    		</tr>
	    		<tr>
	    			<td><spring:message code="Mask"/>:</td>
	    			<td><input class="easyui-textbox" name="genmask" data-options="validType:'Mask',required:true"/></td>
	    		</tr>
	    		<tr>
	    			<td><spring:message code="Gateway"/>:</td>
	    			<td><input class="easyui-textbox" name="gateway" data-options="validType:'ip',required:true"/></td>
	    		</tr>
	    		<tr>
	    			<td>所在板卡:</td>
	    			<td><input class="easyui-combobox" 
						name="cardNum"
						data-options="
							url:'${pageContext.request.contextPath}/card/listjsonarr.action',
							method:'get',
							onLoadSuccess:onLoadSuccess,
							valueField:'cardNum',
							textField:'name',
							panelHeight:'auto',
							required:true
						"></td>
	    		</tr>
	    	</table>
	    </form>
	    <div style="text-align:center;padding:5px">
	    	<a href="#" class="easyui-linkbutton" onclick="submitForm()"><spring:message code="SaveRoute"/></a>
	    	<a href="#" class="easyui-linkbutton" onclick="clearForm()"><spring:message code="ClearRecord"/></a>
	    </div>
	</div>
	<div id="w1" class="easyui-window" title="添加默认网关" 
		data-options="modal:true,closed:true,iconCls:'icon-add'" style="padding:10px;">
		<form id="ff1" class="easyui-form" method="post" data-options="novalidate:false" action="${pageContext.request.contextPath}/route/saveDefault.action">
	    	<table>
	    		  <tr style="display: none">
	    			<td><spring:message code="DestinationAddress"/>:</td>
	    			<td><input class="easyui-textbox" name="destination" value="0.0.0.0"/></td>
	    		</tr>
	    		<tr style="display: none">
	    			<td><spring:message code="Mask"/>:</td>
	    			<td><input class="easyui-textbox" name="genmask"  value="0.0.0.0"/></td>
	    		</tr>
	    		  
	    		<tr>
	    			<td>默认网关:</td>
	    			<td><input class="easyui-textbox" name="gateway" data-options="validType:'ip',required:true"/></td>
	    		</tr>
	    		<tr>
	    			<td>所在板卡:</td>
	    			<td><input class="easyui-combobox" 
						name="cardNum"
						data-options="
							url:'${pageContext.request.contextPath}/card/listjsonarr.action',
							method:'get',
							onLoadSuccess:onLoadSuccess,
							valueField:'cardNum',
							textField:'name',
							panelHeight:'auto',
							required:true
						"></td>
	    		</tr>
	    	</table>
	    </form>
	    <div style="text-align:center;padding:5px">
	    	<a href="#" class="easyui-linkbutton" onclick="submitForm1()"><spring:message code="SaveRoute"/></a>
	    	<a href="#" class="easyui-linkbutton" onclick="clearForm1()"><spring:message code="ClearRecord"/></a>
	    </div>
	</div>
	<div id="ospfW" class="easyui-window" title="<spring:message code="OSPFOperation"/>" 
		data-options="footer:'#footer',modal:true,closed:true,iconCls:'icon-add'" style="width:600px;height:400px;">
		<pre id="content" style="height:339px;"></pre>
	</div>
	<div id="footer">
		<input id="cmd" style="width:586px" disabled="disabled"/>
	</div>
	
</body>
</html>