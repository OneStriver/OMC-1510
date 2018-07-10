function submitModuleForm(id){
	$('#'+id).form('submit',{
		success:function(data){
			submitSuccess(data);
			$.messager.progress('close');
		},
		onSubmit:function(){
			var isOk=$(this).form('enableValidation').form('validate');
			if(isOk) $.messager.progress({msg:'正在上传请稍等...'});
			return isOk;
		}
	});
}
function accept(updateUrl,insertUrl){
	if (editIndex!=undefined && $('#dg').datagrid('validateRow', editIndex)){
		$('#dg').datagrid('endEdit',editIndex);
		var url=updateUrl;;
		var row=$('#dg').datagrid('getRows')[editIndex];
		delete row.configs;
		var json=JSON.stringify(row);
		console.log(json);
		if(isInsert) url=insertUrl;
		$.ajax({
			url:url,
			traditional:true,
			data:row,
			type:'post',
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
						style:{right:'',bottom:''}
					});
				}
				
			}
		});
		editIndex = undefined;
		isInsert = undefined;
	}
}

function onRowContextMenu(e, index, row){
	e.preventDefault();
	if(row==null)return ;
	var cmenu;
	createColumnMenu(index);
	cmenu.menu('show', {
		left:e.pageX-5,
		top:e.pageY-5
	});
	
	function createColumnMenu(index){
		cmenu = $('<div/>').appendTo('body');
		cmenu.menu({
			onClick: function(item){
				if(item.name == 'delete'){
					$.post(contextPath+"/module/delete.action",{ids:row.id},
						function(data){
							submitSuccess(data);
						});
				}else if(item.name == 'openFileManager'){
					var href=contextPath+'/module/comein.action?name='+row.name;
					$('#content').prop('src',href);
					$('#filesWindow').window('open');
				}
				cmenu.remove();
			}
		});
		cmenu.menu('appendItem',{
			text: '删    除',
			name: 'delete',
			iconCls: 'icon-ok'
		}).menu('appendItem',{
			text: '打开文件管理器',
			name: 'openFileManager',
			iconCls: 'icon-ok'
		});
	}
}
