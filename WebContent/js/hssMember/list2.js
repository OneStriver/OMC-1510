function removeit(){
	var rows=$('#dg').datagrid('getSelections');
	if(rows.length==0) {
		$.messager.alert('提示','请选择要删除的记录!');
		return;
	}
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
				var groupId=[];
				var mdn=[];
				$(rows).each(function(i){
					mdn.push(rows[i].mdn);
					groupId.push(rows[i].groupId);
				});
				$.ajax({type:'post',traditional:true,data:{mdn:mdn,groupId:groupId},
					url:contextPath+"/meetingMember/delete.action",
					success:submitSuccess
				});
			}
		}
	);
}

function append(){
	$('#ff').form({url:contextPath+'/meetingMember/add.action'});
	$('#w').window({title:"添加会议成员信息",iconCls:'icon-add'}).window('open');
}

function update(row){
	$('#ff').form('load',row).form({url:contextPath+'/meetingMember/update.action'});
	$('#w').window({title:"修改会议成员信息",iconCls:'icon-edit'}).window('open');
}

function qq(value,name){
	var options=$('#dg').datagrid('options');
	var params={};
	jQuery.extend(params, options.queryParams);
	delete params.mdn;
	delete params.groupId;
	params[name]=value;
	$('#dg').datagrid({
		queryParams:params
	});
}

function pChange(newValue, oldValue){
	var options=$('#dg').datagrid('options');
	var params={};
	jQuery.extend(params, options.queryParams);
	var name=$('#ss').searchbox('getName');
	var value=$('#ss').searchbox('getValue');
	params[name]=value;
	params.priority=newValue;
	$('#dg').datagrid({
		queryParams:params
	});
}

function rChange(newValue, oldValue){
	var options=$('#dg').datagrid('options');
	var params={};
	jQuery.extend(params, options.queryParams);
	var name=$('#ss').searchbox('getName');
	var value=$('#ss').searchbox('getValue');
	params[name]=value;
	params.role=newValue;
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
	$('#mdn,#groupId').textbox('clear');
}

function onClickCell(index, field, value){
	if(field!='修改') return;
	var row=$('#dg').datagrid('getRows')[index];
	update(row);
}

