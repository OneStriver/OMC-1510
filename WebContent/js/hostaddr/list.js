var editIndex = undefined;
var isInsert = undefined;
function loadFilter(data){
	if(data.error){
		$.messager.alert('出错提示',data.error,'error');
		data = {
			total:0,
			rows: []
		};
	}
	return data;
}

function onLoadSuccess(){
	var data=$(this).combobox('getData');
	$(this).combobox('select',data[0].cardNum);
}

function onClickCell(index, field, value){
	if(field!='编辑') return;
	var row=$('#dg').datagrid('getRows')[index];
	update(row);
}

function append(){
	$('#ff').form('clear');
	$('#host').parent().show();
	var attr={
		title:'添加主机地址'
	};
	$('#ff').prop('action',contextPath+'/hostaddr/save.action');
	$('#w').window(attr).window('open');
}

function update(row){
	$('#host').parent().hide();
	$('#ff').form('load',row);
	var attr={
		title:'修改主机地址'
	};
	$('#ff').prop('action',contextPath+'/hostaddr/update.action');
	$('#w').window(attr).window('open');
}

function submitForm(){
	$('#ff').form('submit',{
		success:function(data){
			$.messager.progress('close');
			if(submitSuccess(data)){
				$('#w').window(close);
			}
		},
		onSubmit:function(){
			var isOk=$(this).form('enableValidation').form('validate');
			if(isOk) {
				$.messager.progress({msg:'正在保存请稍等'});
			}
			return isOk;
		}
	});
}

function removeit(url){
	var rows=$('#dg').datagrid('getSelections');
	if(rows.length==0) {
		$.messager.alert('提示','请选择要删除的记录!');
		return;
	}
	$.messager.confirm(LOCALE.Confirm,LOCALE.DeleteMessage,
		function(yes){    
			if(yes){
				var selected=$('#dg').datagrid('cancelEdit', editIndex).datagrid('getSelections');
				editIndex = undefined;isInsert = undefined;
				if(selected.length==0) return;
				var columns=$('#dg').datagrid('cancelEdit', editIndex).datagrid('getColumnFields');
				var ids=[],hostNames=[];
				$.each(selected,function(i){
					var id=selected[i][columns[1]];
					if(id){
						ids.push(id);
						hostNames.push(selected[i][columns[2]]);
					}else{
						$('#dg').datagrid('deleteRow',$('#dg').datagrid('getRows').length-1);
					}
				});
				if(ids.length>0){
					$.ajax({type:'post',traditional:true,data:{ids:ids,hostNames:hostNames},
						url:url,
						success:submitSuccess
					});
				}
			
			}
		}
	);
}