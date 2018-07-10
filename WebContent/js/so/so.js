function dynamicLibrarySubmitForm(){
	$('#dynamicLibraryForm').form('submit',{
		success:function(data){
			$.messager.progress('close');
			if(submitSoSuccess(data)){
				$('#dynamicLibraryWindow').window('close');
			}
		},
		onSubmit:function(){
			var ok = $(this).form('enableValidation').form('validate');
			if(ok) $.messager.progress({msg:'正在上传请稍等...'});
			return ok;
		}
	});
}

function addEncryptLibrarySubmitForm(){
	$('#addEncryptLibraryForm').form('submit',{
		success:function(data){
			$.messager.progress('close');
			if(submitSoSuccess(data)){
				$('#addEncryptLibraryWindow').window('close');
			}
		},
		onSubmit:function(){
			var ok = $(this).form('enableValidation').form('validate');
			if(ok) $.messager.progress({msg:'正在上传请稍等...'});
			return ok;
		}
	});
}

function submitSoSuccess(data){
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
