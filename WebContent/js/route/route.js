var websocket=undefined;
function openOspf(){
	if(websocket) websocket.close();
	var content=$('#content');
	$('#ospfWindow').window('open');
	var cmd=$('#cmd');
	cmd.keydown(function(event){
		switch(event.keyCode) {
		case 27:
			$('#ospfWindow').window('close');
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
		if(message.data=='Connect Success.'){
			$('#cmd').prop('disabled',false);
		}
		content.html(content.html()+'<br/>'+message.data);
		$('#ospfWindow').scrollTop(content.get(0).scrollHeight);
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
				$.ajax({
					type:'post',
					traditional:true,
					data:param,
					url:url,
					success:routeSubmitSuccess
				});
			}
		}
	);
}

function appendRoute(){
	$('#addRouteWindow').window('open');
}

function appendDefaultGateWay(){
	$('#addDefaultGateWayWindow').window('open');
}

function onLoadSuccess(){
	var data=$(this).combobox('getData');
	$(this).combobox('select',data[0].cardNum);
	console.log(data);
}

//路由
function addRouteSubmitForm(){
	$('#addRouteForm').form('submit',{
		onSubmit:function(){
			return $(this).form('enableValidation').form('validate');
		},
		success:function(data){    
			routeSubmitSuccess(data);
			$('#addRouteWindow').window('close');
	    }
	});
}

//默认网关
function addDefaultGateWaySubmitForm(){
	$('#addDefaultGateWayForm').form('submit',{
		onSubmit:function(){
			return $(this).form('enableValidation').form('validate');
		},
		success:function(data){    
			routeSubmitSuccess(data);
			$('#addDefaultGateWayWindow').window('close');
	    }   
	});
}

$(function(){
	$('#addRouteForm').form({
		success:routeSubmitSuccess
	});
	$('#addDefaultGateWayForm').form({
		success:routeSubmitSuccess
	});
});

function routeSubmitSuccess(data){
	var json=$.parseJSON(data);
	if(json&&json.error) {
		$.messager.alert('出错提示',json.error,'error');
		return false;
	}else if(json.msg){
		$.messager.show({
			title:'操作提示',
			msg:json.msg,
			showType:'fade',
			timeout:2000,
			style:{
				right:'',bottom:''
			}
		});
		if($('#dg')){
			$('#dg').datagrid('reload');
		} 
		return true;
	}else{
		alert('未预料消息:'+data);
		return false;
	}
}