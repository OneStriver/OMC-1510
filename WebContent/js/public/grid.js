var editIndex = undefined;
var isInsert = undefined;
function onClickCell(index, field){
	if(editIndex === undefined || editIndex==index){
		editIndex=index;
		$('#dg').datagrid('selectRow', index).datagrid('beginEdit', editIndex);
		var ed = $('#dg').datagrid('getEditor', {index:index,field:field});
		if(ed){
			($(ed.target).data('textbox') ? $(ed.target).textbox('textbox') : $(ed.target)).focus();
		}else {
			$('#dg').datagrid('selectRow', editIndex);
		}
	}else{
		$.messager.alert('提示','上一步的操作未保存,请先保存!');return
				$('#dg').datagrid('cancelEdit',editIndex);
				editIndex=index;	
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
			if(yes){
				var selected=$('#dg').datagrid('cancelEdit', editIndex).datagrid('getSelections');
				editIndex = undefined;isInsert = undefined;
				if(selected.length==0) return;
				var columns=$('#dg').datagrid('cancelEdit', editIndex).datagrid('getColumnFields');
				var ids=[];
				$.each(selected,function(i){
					var id=selected[i][columns[1]];
					if(id){
						ids.push(id);
					}else{
						$('#dg').datagrid('deleteRow',$('#dg').datagrid('getRows').length-1);
					}
				});
				if(ids.length>0){
					$.ajax({type:'post',traditional:true,data:{ids:ids},
						url:url,
						success:submitSuccess
					});
				}
			}
		}
	);
}

function accept(updateUrl,insertUrl){
	if (editIndex!=undefined && $('#dg').datagrid('validateRow', editIndex)){
		var selected=$('#dg').datagrid('getSelected');
		if(!selected){
			$.messager.alert('警告','请将编辑状态的记录选中后保存'); 
			return;
		}
		$('#dg').datagrid('endEdit',editIndex);
		var url=updateUrl;;
		var row=$('#dg').datagrid('getRows')[editIndex];
		var json=JSON.stringify(row);
		console.log(json);
		if(isInsert) url=insertUrl;
		$.ajax({
			//contentType:'application/json;charset=utf-8',//async:false,
			url:url,traditional:true,data:row,type:'post',
			success:submitSuccess
		});
		editIndex = undefined;
		isInsert = undefined;
	}
}

function append(){
	if(editIndex||isInsert){
		$.messager.alert('警告','每次只能添加或修改一行'); 
		return;
	}
	var appendRow={};
	$('#dg').datagrid('options').columns[0].forEach(function(value){
		if(value.field!='ck') appendRow[value.field]='';
	});
	$('#dg').datagrid('cancelEdit',editIndex);
	$('#dg').datagrid('appendRow',appendRow);
	editIndex = $('#dg').datagrid('getRows').length-1;
	$('#dg').datagrid('selectRow', editIndex).datagrid('beginEdit', editIndex);
	isInsert=true;
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
