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
	if(field!='修改') return;
	var row=$('#dg').datagrid('getRows')[index];
	update(row);
}

function append(){
	$('#addHostAddrForm').form('clear');
	$('#addHostAddrHost').parent().show();
	var attr={
		title:'添加主机地址'
	};
	$('#addHostAddrForm').prop('action',contextPath+'/hostaddr/save.action');
	$('#addHostAddrWindow').window(attr).window('open');
}

function update(row){
	$('#addHostAddrHost').parent().hide();
	$('#addHostAddrForm').form('load',row);
	var attr={
		title:'修改主机地址'
	};
	$('#addHostAddrForm').prop('action',contextPath+'/hostaddr/update.action');
	$('#addHostAddrWindow').window(attr).window('open');
}

function submitForm(){
	$('#addHostAddrForm').form('submit',{
		success:function(data){
			$.messager.progress('close');
			if(submitHostAddrSuccess(data)){
				$('#addHostAddrWindow').window(close);
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
						success:submitHostAddrSuccess
					});
				}
			
			}
		}
	);
}

function submitHostAddrSuccess(data){
	var json=$.parseJSON(data);
	if(json&&json.error) {
		$.messager.alert('出错提示',json.error,'error');
		return false;
	}else if(json.msg){
		$.messager.show({
			title:'操作提示',
			msg:json.msg,
			showType:'fade',
			timeout:3000,
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