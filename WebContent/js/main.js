var currTitle;
$(function() {
	// 加载一级菜单
	$('#tt').tabs({
		onContextMenu:function(e,title,index){
			e.preventDefault();
			currTitle=title;
			$('#mm').menu('show', {
				left: e.pageX-5,
				top: e.pageY-5
			});
		}
	});
	$('#tt1').tree({
		url : contextPath+"/privilege/menu.action",
		loadFilter:function(data,parent){
			return data;
		},
		onLoadSuccess:function(node,data){
			//$('#tt1').tree('collapseAll');
		},
		onClick : function(node) {
//			if (node.url=='serialNumber/listUI') {
//				$.messager.progress({msg:'正在读取请稍等'});
//				$.ajax({
//					   type: "POST",
//					   url:  contextPath+"/"+node.url+".action",
//					   success: function(msg){
//						   var json=$.parseJSON(msg);
//						   $.messager.progress('close');
//						   if(json&&json.error) {
//								$.messager.alert('出错提示',json.error,'error');
//								return false;
//							}else{
//						        $.messager.show({
//							    height:320,
//							    width:300,
//								title:node.text,
//								msg:function(){
//									var message = '';
//									var arr = json.list;
//									for(var i = 0 ;i < arr.length;i++){
//										message+='<div style="font-size:14px"><p style="color:#323232">'+arr[i].name+':</p><p style="color:#555555;line-height:5px">'+arr[i].serial+"</p>"+'<div class="easyui-linkbutton" id='+arr[i].name+' onclick="downLoad(this)"data-options="height:30,width:60">导出</div>'+'</div>'+'<br>';
//										
//									}
//									return message;
//								},
//								timeout:20000,
//								style:{
//									right:'',
//									bottom:''
//								}
//
//							});
//							}
//					   }
//					});
//				return;
//			}
//			if(node.url=='resetLf/listUI'){
//				$.messager.confirm(node.text,'<div style="font-size:12px"><span style="color:#f23737">注意:</span>此操作将会还原系统出厂状态,<br/>并在系统重启后生效</div>',function(r){    
//				    if (r){    
//				    	$.messager.progress({msg:'正在进行设置请稍等'});
//						$.ajax({
//							type:'post',
//							url:contextPath+"/resetLf/reset.action",
//							success:function(data){
//								$.messager.progress('close');
//								submitSuccess(data);
//							}
//						});
//				    }    
//				}); 
//				return;
//			}
			if(node.url)addTab(node.text, contextPath+"/"+node.url+".action");
		}
	});
	$('#cc').layout('resize');
	detectOam();
});
function downLoad(i){
	var url =contextPath+"/serialNumber/downLoad.action?name="+i.id;
//	location = url
//	document.URL=url
//	location.href = url;
	open(url,null,"height=200,width=400,status=yes,toolbar=no,menubar=no,location=no"); 
//	var url = contextPath+"/serialNumber/downLoad.action";
//	$.ajax({
//		type:'post',
//		data:{name:i.id},
//		url:url,
//		success:function(data){
//			
//		}
//	});
}
function menuHandler(item){
	switch(item.name){
		case 'close':
			$('#tt').tabs('close',currTitle);
			break;
		case 'others':
			var length=$('#tt').tabs('tabs').length;
			var currTab=$('#tt').tabs('getTab',currTitle);
			for(var i=0;i<length;i++){
				var index=0;
				var currIndex=$('#tt').tabs('getTabIndex',currTab);
				if(currIndex==0) index=1;
				$('#tt').tabs('close',index);
			}
			break;
		case 'all':
			var length=$('#tt').tabs('tabs').length;
			for(var i=0;i<length;i++){
				$('#tt').tabs('close',0);
			}
			break;
	}
}

function view(url) {
	$('#iframe').attr('src', url);
}

function refreshTab(cfg){
	var refresh_tab=cfg.tabTitle?$('#tt').tabs('getTab',cfg.tabTitle):$('#tt').tabs('getSelected');
	if(refresh_tab&&refresh_tab.find('iframe').length>0){
		var iframe=refresh_tab.find('iframe')[0];
		var url=cfg.url?cfg.url:iframe.src;
		iframe.contentWindow.location.href=url;
	}
}

/*
 * 添加选项卡方法
 */
function addTab(title, url) {
	// 先判断是否存在标题为title的选项卡
	var tab = $('#tt').tabs('exists', title);
	if (tab) {
		// 若存在则直接打开
		$('#tt').tabs('select', title);
		refreshTab({tabTitle:title,url:url});
	} else {
		// 否则创建
		$('#tt').tabs('add',{
				title : title,
				content : "<iframe width='100%' height='100%' id='iframe' frameborder='0' scrolling='auto'  src='"
						+ url + "'></iframe>",
				closable : true
			});
	}
}

var time=0;
$.post(contextPath+'/user/showTime.action',
	function(data){time=Number(data);}
);
function showTime() {
	var date = new Date(time);
	time=time+1000;
	$('#timeInfo').html(date.format());
}
setInterval(showTime, 1000);
//date.toLocaleString()
/*
 * 检测浏览器窗口大小改变 来改变页面layout大小
 */
// $(function(){
// $(window).resize(function(){
// $('#cc').layout('resize');
// });
// });

function detectOam(){
	var host = location.host;
	console.log("DetectOam的Host:"+host);
	var path = contextPath+'/ws/oam';
	console.log("DetectOam的Path:"+path);
	if (window.WebSocket){
		websocket = new WebSocket('ws://' + host + path);
		websocket.onopen = function() {}
		websocket.onerror = function(){}
		websocket.onclose = function(){}
		websocket.onmessage = function(message){
			var music=document.getElementById('alarmMusic');
			music.innerHTML='<audio src="'+contextPath+'/media/suspend-error.oga" autoplay="true"></audio>';
			$.messager.show({
				title:'告警',
				msg:message.data,
				timeout:5000,
				showType:'show'
			});
		}
	}
}

function modifySelf(){
	$('#ff').form('submit',{
		success:function(data){
			$.messager.progress('close');
			submitSuccess(data);
			$('#modifyPwd').dialog('close');
		},
		onSubmit:function(){
			var isOk=$(this).form('validate');
			if(isOk) $.messager.progress({msg:'正在保存请稍等'});
			return isOk;
		}
	});
}

window.onbeforeunload=function(event){return "";}
