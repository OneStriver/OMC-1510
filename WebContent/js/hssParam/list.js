function submitForm(){
	$('#ff').form('submit',{
		success:function(data){
			$.messager.progress('close');
			$.messager.alert('标题','设置完毕!');
		},
		onSubmit:function(){
			var isOk=$(this).form('enableValidation').form('validate');
			if(isOk) $.messager.progress({msg:'正在保存请稍等'});
			return isOk;
		}
	});
}

function fullSelect(ele){
	$(ele).parent().parent().find('input[type=checkbox]').prop('checked',true);
}

function reverseSelect(ele){
	$(ele).parent().parent().find('input[type=checkbox]').each(function(){
		$(this).prop('checked',!this.checked);
	});
}

