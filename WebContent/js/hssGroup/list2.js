function removeit(){
	var rows=$('#dg').datagrid('getSelections');
	if(rows.length==0) {
		$.messager.alert('提示','请选择要删除的记录!');
		return;
	}
	$.messager.confirm('确认','您确认想要删除记录吗？',
		function(yes){    
			if(yes){
				var rows=$('#dg').datagrid('getSelections');
				if(rows.length==0) return;
				var ids=[];
				$(rows).each(function(i){
					var id=rows[i].id;
					ids.push(id);
				});
				$.ajax({type:'post',traditional:true,data:{ids:ids},
					url:contextPath+"/meetingGroup/delete.action",
					success:submitSuccess
				});
			}
		}
	);
}

function append(){
	$('#ff').form({url:contextPath+'/meetingGroup/add.action'});
	$('#id').textbox({readonly:false}).textbox('setText','').textbox('setValue');
	$('#w').window({title:"创建会议组",iconCls:'icon-add'}).window('open');
}

function update(row){
	$('#ff').form('load',row).form({url:contextPath+'/meetingGroup/update.action'});
	$('#id').textbox({readonly:true});
	$('#w').window({title:"修改会议",iconCls:'icon-save'}).window('open');
}

function qq(value,name){
	var options=$('#dg').datagrid('options');
	var params={};
	jQuery.extend(params, options.queryParams);
	delete params.id;
	params[name]=value;
	$('#dg').datagrid({
		queryParams:params
	});
}

function callChange(newValue, oldValue){
	var options=$('#dg').datagrid('options');
	var params={};
	jQuery.extend(params, options.queryParams);
	var id=$('#ss').searchbox('getValue');
	params.id=id;
	params.callType=newValue;
	$('#dg').datagrid({
		queryParams:params
	});
}

function busiChange(newValue, oldValue){
	var options=$('#dg').datagrid('options');
	var params={};
	jQuery.extend(params, options.queryParams);
	var id=$('#ss').searchbox('getValue');
	params.id=id;
	params.busiType=newValue;
	$('#dg').datagrid({
		queryParams:params
	});
}

function submitForm(){
	$('#ff').form('submit',{
		success:function(data){
			$.messager.progress('close');
			submitSuccess(data);
		},
		onSubmit:function(){
			var isOk=$(this).form('enableValidation').form('validate');
			if(isOk) $.messager.progress({msg:'正在提交请稍等'});
			return isOk;
		}
	});
}

function clearForm(){
	$('#ff').form('clear');
}

function onClickCell(index, field, value){
	if(field!='修改') return;
	var row=$('#dg').datagrid('getRows')[index];
	update(row);
}