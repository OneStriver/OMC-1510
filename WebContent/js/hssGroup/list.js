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
					url:contextPath+"/hssGroup/delete.action",
					success:submitSuccess
				});
			}
		}
	);
}

function append(){
	$('#addGroupForm').form('reset');
	$('#addGroupForm').form({url:contextPath+'/hssGroup/add.action'});
	$('#addGroupWindow').window({title:"创建组呼组",iconCls:'icon-add'}).window('open');
}

function update(row){
	$('#updateGroupForm').form('load',row);
	$('#updateGroupId').textbox({readonly:true});
	$('#updateGroupId').textbox('textbox').css('background','#ccc');
	$('#updateGroupWindow').window({title:"修改组呼组",iconCls:'icon-save'}).window('open');
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
	var id=$('#groupSearch').searchbox('getValue');
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
	var id=$('#groupSearch').searchbox('getValue');
	params.id=id;
	params.busiType=newValue;
	$('#dg').datagrid({
		queryParams:params
	});
}

function addGroupSubmitForm(){ 
	$('#addGroupForm').form('submit',{
		success:function(data){
			$.messager.progress('close');
			addGroupSubmitSuccess(data);
		},
		onSubmit:function(){
			var isOk=$(this).form('enableValidation').form('validate');
			if(isOk) $.messager.progress({msg:'正在提交请稍等...'});
			return isOk;
		}
	});
}
function addGroupSubmitSuccess(data){
	var json=$.parseJSON(data);
	if(json&&json.error) {
		$.messager.alert('出错提示',json.error,'error');
		return false;
	}else if(json.msg){
		if($('#addGroupWindow')){
			$('#addGroupWindow').window('close');
		}
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

function updateGroupSubmitForm(){ 
	$('#updateGroupForm').form('submit',{
		success:function(data){
			$.messager.progress('close');
			updateGroupSubmitSuccess(data);
		},
		onSubmit:function(){
			var isOk=$(this).form('enableValidation').form('validate');
			if(isOk) $.messager.progress({msg:'正在提交请稍等...'});
			return isOk;
		}
	});
}
function updateGroupSubmitSuccess(data){
	var json=$.parseJSON(data);
	if(json&&json.error) {
		$.messager.alert('出错提示',json.error,'error');
		return false;
	}else if(json.msg){
		if($('#updateGroupWindow')){
			$('#updateGroupWindow').window('close');
		}
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

function onClickCell(index, field, value){
	if(field!='修改') return;
	var row=$('#dg').datagrid('getRows')[index];
	console.log("组类型:"+row.groupType);
	update(row);
}