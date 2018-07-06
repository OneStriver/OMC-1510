function submitForm(){
	$('#ff').form('submit',{
		success:function(data){
			$.messager.progress('close');
			submitSuccess(data);
		},
		onSubmit:function(){
			var isOk=$(this).form('enableValidation').form('validate');
			if(isOk) $.messager.progress({msg:'正在保存请稍等'});
			return isOk;
		}
	});
}

function clearForm(){
	$('#ff').form('clear');
}

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

function accept(btn,updateUrl,insertUrl){
	var grid=$(btn).parent().next().children('table:first');
	if (editIndex!=undefined && grid.datagrid('validateRow', editIndex)){
		grid.datagrid('endEdit',editIndex);
		var url=updateUrl;
		var row=grid.datagrid('getRows')[editIndex];
		var json=JSON.stringify(row);
		console.log(json);
		if(isInsert) url=insertUrl;
		$.ajax({
			url:url,traditional:true,data:row,type:'post',
			success:function(data){//返回json结果
				var data=$.parseJSON(data);
				console.log(data);
				if(data.error){
					data='发生错误'+data.error;
					$.messager.alert('出错提示',data,'error');
					grid.datagrid('rejectChanges',editIndex);
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

function removeit(btn,url){
	var grid=$(btn).parent().next().children('table:first');
	var selected=grid.datagrid('cancelEdit', editIndex).datagrid('getSelections');
	if(selected.length==0) return;
	var columns=grid.datagrid('cancelEdit', editIndex).datagrid('getColumnFields');
	var ids=[];
	$.each(selected,function(i){
		var id=selected[i][columns[1]];
		if(id){
			ids.push(id);
		}else{
			grid.datagrid('deleteRow',grid.datagrid('getRows').length-1);
		}
	});
	if(ids.length>0){
		$.ajax({type:'post',traditional:true,data:{ids:ids},
			url:url,
			success:submitSuccess
		});
	}
}
var editIndex = undefined;
var isInsert = undefined;
function append(btn){
	var grid=$(btn).parent().next().children('table:first');
	grid.datagrid('cancelEdit',editIndex);
	grid.datagrid('appendRow',{});
	editIndex = grid.datagrid('getRows').length-1;
	grid.datagrid('selectRow', editIndex).datagrid('beginEdit', editIndex);
	isInsert=true;
}

function onClickCell(index, field){
	var grid=$(this);
	if(editIndex === undefined || editIndex==index){
		editIndex=index;
		grid.datagrid('selectRow', index).datagrid('beginEdit', editIndex);
		var ed = grid.datagrid('getEditor', {index:index,field:field});
		if(ed){
			($(ed.target).data('textbox') ? $(ed.target).textbox('textbox') : $(ed.target)).focus();
		}else {
			grid.datagrid('selectRow', editIndex);
		}
	}else{
		grid.datagrid('cancelEdit',editIndex);
		editIndex=index;
	}
}