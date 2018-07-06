<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<%@ include file="/WEB-INF/jsp/public/common.jspf"%>
<title></title>
<script type="text/javascript">
function openFile(fileModel){
	var dialog=$('<div/>').dialog({    
	    title: fileModel.name,
	    closed: false,method:'post',
	    queryParams:fileModel,fit:true,
	    href: contextPath+"/fileManager/openFile.action",    
	    modal: true,draggable:false,
	    onClose:function(){
	    	dialog.remove();
	    }
	});
}

function createFiles(json){
	json.forEach(function(i){
		$('<div style="border:1px dotted gray;margin:10px 0;cursor:pointer;">'+i.name+'</div>').appendTo('#c').on('dblclick',function(){
			if(i.isFile){
				openFile(i);
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
					}
					menu.remove();
				}
			}).menu('appendItem', {
				text: '删除',
				name: 'delete',
				iconCls: 'icon-remove'
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
        tools:[{
			iconCls:'icon-undo',
			handler:function(){
				var currDir=$('#c').panel('options').title;
				var end = currDir.lastIndexOf('/');
				if(end==0) end=1;
				cdDirectory(currDir.substring(0,end));
			}
		},'',{
			iconCls:'icon-reload',
			handler:refreshPanel
		}]">
正在加载亲稍等
</div>
</body>
</html>