function accept(updateUrl,insertUrl){
	if (editIndex!=undefined && $('#dg').datagrid('validateRow', editIndex)){
		$('#dg').datagrid('endEdit',editIndex);
		var url=updateUrl;
		var row=$('#dg').datagrid('getRows')[editIndex];
		delete row.config;
		var json=JSON.stringify(row);
		if(isInsert) url=insertUrl;
		$.ajax({
			url:url,traditional:true,data:row,type:'post',
			success:function(data){//返回json结果
				var data=$.parseJSON(data);
				if(data.error){
					data='发生错误'+data.error;
					$.messager.alert('出错提示',data,'error');
					$('#dg').datagrid('rejectChanges',editIndex);
				}else{
					$.messager.show({
						title:'操作提示',
						msg:data.msg,
						showType:'fade',timeout:1500,
						style:{
							right:'',bottom:'',
							top:document.body.scrollTop+document.documentElement.scrollTop
						}
					});
					$('#dg').datagrid('reload');
				}
				
			}
		});
		editIndex = undefined;
		isInsert = undefined;
	}
}

function usubmitForm(){
	$('#uf').form('submit',{
		success:function(data){
			submitSuccess(data);
			$('#uw').window('close');
			$('#ug').datagrid('reload');
		},
		onSubmit:function(){
			return $(this).form('enableValidation').form('validate');
		}
	});
}

function clearForm(){
	$('#af').form('clear');
}

function onClickCell(index, field, value){
	if(field!='update') return;
	var row=$('#dg').datagrid('selectRow',index).datagrid('getSelected');
	if(!row.name) return;
	update(row);
}

function update(row){
	$('#uf').form('load',row);
	$('#uw').window('open');
}

function submitForm(){
	var validated=$('#addEntityForm').form('enableValidation').form('validate');
	if(validated){
		$('#addEntityForm').submit();
	}
}

function updateEntity(){
	$('#updateEntityForm').form('submit',{
		success:function(data){
			submitSuccess(data);
			$('#updateEntityWindow').window('close');
			$('#dg').datagrid('reload');
		},
		onSubmit:function(){
			return $(this).form('enableValidation').form('validate');
		}
	});
}

function onBeforeClose(){
	if(onBeforeClose.finished) return;
	$.messager.confirm(LOCALE.Confirm,'关此窗口会终止正在执行的任务',
		function(yes){    
			if(yes){
				onBeforeClose.suspend=true;
				$('#dd').dialog('close',true);
			}
		}
	);
	return false;
}

function batchGo(url,title,txt){
	onBeforeClose.suspend=false;
	onBeforeClose.finished=false;
	var selected=$('#dg').datagrid('getSelections');
	if(!selected.length) {
		$.messager.alert('提示','至少勾选一条记录');
		return;
	}
	var content="";
	$('#pContent').html(content);
	$('#dd').dialog({title:title,height:'auto'}).dialog('open');
	$('#p').progressbar({value:0});
	var i=0;
	function _batchGo(){
		i==selected.length&&(onBeforeClose.finished=true);
		if(onBeforeClose.finished||onBeforeClose.suspend) return;
		content+=txt+'<b>'+selected[i].name+'</b> ... ';
		$('#pContent').html(content);
		$('#dd').dialog('refresh');
		var value=(i+1)*100/selected.length;
		$('#p').progressbar('setValue',parseInt(value));
		$.post(url+"?id="+selected[i].id,function(json){
			json=$.parseJSON(json);
			if(json&&json.error) {
				content+='<font color="red">'+json.error+'</font><br/>';
			}else{
				content+='<font color="green">'+json.msg+'</font><br/>';
			}
			$('#pContent').html(content);
			if(++i==selected.length) $('#dg').datagrid('reload');
			_batchGo();
		});
	}
	_batchGo();
}

function start(){
	batchGo(contextPath+"/entity/start.action",'启动网元','正在启动网元');
}

function restart(){
	batchGo(contextPath+"/entity/restart.action",'重启网元','正在重启网元');
}

function stop(){
	batchGo(contextPath+"/entity/stop.action",'停止网元','正在停止网元');
}

function startup(){
	batchGo(contextPath+"/entity/startup.action",'设置开机自启','正在设置开机自启动');
}

function shutdown(){
	batchGo(contextPath+"/entity/shutdown.action",'禁止开机自启','正在设置禁止开机自启');
}

function menuHandler(item){
	if(item.name == 'delete'){
		$.messager.confirm(LOCALE.Confirm,LOCALE.DeleteMessage,
			function(yes){    
				if(yes){
					$.post(contextPath+"/entity/delete.action",{ids:row.id},
						function(data){
							submitSuccess(data);
						});
				}
		});
	}else if(item.name == 'update'){
		window.location=contextPath+"/entity/updateUI.action?id="+row.id;
	}else if(item.name == 'start'){
		$.messager.progress({msg:'正在启动请稍等...'});
		$.post(contextPath+"/entity/start.action",{id:row.id},
			function(data){
				$.messager.progress('close');
				submitSuccess(data);
			});
	}else if(item.name == 'stop'){
		$.messager.progress({msg:'正在停止请稍等...'});
		$.post(contextPath+"/entity/stop.action",{id:row.id},
			function(data){
				$.messager.progress('close');
				submitSuccess(data);
			});
	}else if(item.name == 'restart'){
		$.messager.progress({msg:'正在重启请稍等...'});
		$.post(contextPath+"/entity/restart.action",{id:row.id},
			function(data){
				$.messager.progress('close');
				submitSuccess(data);
			});
	}else if(item.name == 'updateEntity'){
		$('input[name=id]').val(row.id);
		$('input[name=instId]').val(row.instId);
		$('input[name=moduleId]').val(row.module.id);
		$('#updateEntityWindow').window('open');
	}else if(item.name == 'openFileManager'){
		var href=contextPath+'/entity/comein.action?id='+row.id+'&instId='+row.instId;
		$('#content').prop('src',href);
		$('#entityFilesWindow').window('open');
	}else if(item.name == 'openLogFile'){
		var href=contextPath+'/entity/listLog.action?id='+row.id+'&instId='+row.instId;
		$('#content').prop('src',href);
		$('#entityFilesWindow').window('open');
	}else if(item.name == 'startup'){
		$.messager.progress({msg:'正在设置请稍等...'});
		$.post(contextPath+"/entity/startup.action",{id:row.id},
			function(data){
				$.messager.progress('close');
				submitSuccess(data);
			});
	}else if(item.name == 'shutdown'){
		$.messager.progress({msg:'正在设置请稍等...'});
		$.post(contextPath+"/entity/shutdown.action",{id:row.id},
			function(data){
				$.messager.progress('close');
				submitSuccess(data);
			});
	}else if(item.name == 'logdownload'){
		$.messager.confirm('确认','您确认想要下载此网元日志吗？',function(r){   
		    if (r){
		    	location=contextPath+'/entity/logdownload.action?ids='+row.id;
		    }    
		}); 
	}
}

var row;
function onRowContextMenu(e, index, crow){
	e.preventDefault();
	if(crow==null)return ;
	row=crow;
	$('#dg').datagrid('unselectAll');
	$('#dg').datagrid('selectRow',index);
	$('#entityRightMenu').menu('show',{left:e.pageX-5,top:e.pageY-5});
}

function exportAllLog(){
	$.messager.confirm('确认','您确认想要下载全部日志吗？',function(r){   
	    if (r){
	    	location=contextPath+'/entity/downloadAllLog.action';
	    }    
	}); 
}

function exportSelectedLog(){
	var selected=$('#dg').datagrid('getSelections');
	if(!selected.length) {
		$.messager.alert('提示','至少勾选一条记录');
		return;
	}
	var param='';
	$.each(selected,function(){
		if(param){
			param+='&ids='+this.id
		}else{
			param+='ids='+this.id
		}
		
	});
	location=contextPath+'/entity/logdownload.action?'+param;
}

