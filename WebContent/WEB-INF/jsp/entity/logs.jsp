<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<%@ include file="/WEB-INF/jsp/public/common.jspf"%>
<title></title>
<script type="text/javascript">
function openFileTailF(fileModel){
	var host = location.host;
	var path = contextPath+'/ws/tailF/'+fileModel.path.substr(1).replace(new RegExp("/","g"),'@@')+'@@'+fileModel.name;
	var dialog=$('<div/>').dialog({    
	    title: fileModel.name,
	    closed: false,method:'post',
	    queryParams:fileModel,fit:true,
	    modal: true,draggable:false,
	    onClose:function(){
	    	websocket.close();
	    	dialog.remove();
	    }
	});
	var websocket=null;
	if (window.WebSocket){
		websocket = new WebSocket('ws://' + host + path);
		websocket.onopen = function() {}
		websocket.onerror = function(){}
		websocket.onclose = function(){}
		websocket.onmessage = function(message){
			dialog.dialog('body').append(message.data);
			dialog.scrollTop(dialog.get(0).scrollHeight);
		}
	}else{
		dialog.panel('refresh',contextPath+'/fileManager/openFile.action');
	}
}

function createFiles(json){
	json.forEach(function(i){
		$('<div>'+i.name+'</div>').appendTo('#c').on('dblclick',function(){
			if(i.isFile){
				openFileTailF(i);
			}else if(i.isDirectory){
				if(i.path=='/') i.path='';
				cdDirectory(i.path+"/"+i.name);
			}
		}).on('mouseover',function(e){
			e.clientX;
			e.clientY;
		}).on('contextmenu',function(e){
			e.preventDefault();
			var menu=$('<div/>').menu({
				onClick: function(item){
					if(item.name == 'delete'){
						$.messager.confirm('确认对话框', '确定删除吗？', function(r){
							if(r){
								$.ajax({type: "POST",data: i,dataType:'json',
									url: contextPath+"/fileManager/delete.action",
									success:function(json){
										if(json&&json.error){
											$.messager.alert('出错提示',json.error,'error');
										}else{
											refreshPanel();
										}
									}
								});
							}
						});
					}else if(item.name == 'export'){
						location=contextPath+"/fileManager/download.action?path="+i.path+"&name="+i.name;
					}else if(item.name == 'exportAll'){
						location=contextPath+"/fileManager/downloadAll.action?path="+i.path;
					}
					menu.remove();
				}
			}).menu('appendItem', {
				text: '删除',
				name: 'delete',
				iconCls: 'icon-remove'
			}).menu('appendItem', {
				text: '导出',
				name: 'export',
				iconCls: 'icon-save'
			}).menu('appendItem', {
				text: '导出全部',
				name: 'exportAll',
				iconCls: 'icon-save'
			}).menu('show', {
				left:e.pageX-5,
				top:e.pageY-5
			});
		});
	});
}

function cdDirectory(dir){
	$('#c').empty();
	$.ajax({
		type: "POST",
		url: contextPath+"/fileManager/list.action",
		data: {dir:dir},
		dataType:'json',
		success: function(json){
			json=$.parseJSON(json);
			if(json&&json.error){
				$.messager.alert('出错提示',json.error,'error');
			}else{
				createFiles(json);
				$('#c').panel('setTitle',dir);
			}
		}
	});
}

function refreshPanel(){
	cdDirectory($('#c').panel('options').title);
}
$(function(){
	refreshPanel();
});

</script>
<body>
<div id="c" class="easyui-panel"
        data-options="fit:true,title:'${dir}',
        tools:[/*{
			iconCls:'icon-undo',
			handler:function(){
				var currDir=$('#c').panel('options').title;
				var end = currDir.lastIndexOf('/');
				if(end==0) end=1;
				cdDirectory(currDir.substring(0,end));
			}
		},'',*/{
			iconCls:'icon-reload',
			handler:refreshPanel
		}]">
正在加载请稍等...
</div>
</body>
</html>