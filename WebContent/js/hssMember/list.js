function append(){
	$('#groupMemberMdn').numberbox('clear');
	$('#roleTitle').show();
	$('#roleContent').show();
	$('#groupMemberForm').form({url:contextPath+'/groupMember/add.action'});
	$('#groupMemberWindow').window({title:LOCALE.AddGroupMemberMessage,iconCls:'icon-add'}).window('open');
}

function update(row){
	$('#groupMemberForm').form('load',row).form({url:contextPath+'/groupMember/update.action'});
	$('#roleTitle').hide();
	$('#roleContent').hide();
	$('#groupMemberWindow').window({title:"修改组成员信息",iconCls:'icon-edit'}).window('open');
}

function pChange(newValue, oldValue){
	var options=$('#dg').datagrid('options');
	var params={};
	jQuery.extend(params, options.queryParams);
	params.priority=newValue;
	$('#dg').datagrid({
		queryParams:params
	});
}

function rChange(newValue, oldValue){
	var options=$('#dg').datagrid('options');
	var params={};
	jQuery.extend(params, options.queryParams);
	params.role=newValue;
	$('#dg').datagrid({
		queryParams:params
	});
}

function removeit(){
	var rows=$('#dg').datagrid('getSelections');
	if(rows.length==0) {
		$.messager.alert('提示','请选择要删除的记录!');
		return;
	}
	$.messager.confirm(LOCALE.Confirm,LOCALE.DeleteMessage,
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
					url:contextPath+"/groupMember/delete.action",
					success:groupMemberSubmitSuccess
				});
			}
		}
	);
}

function onClickCell(index, field, value){
	if(field!='修改') return;
	if(field=='修改'){
		$('#groupMemberAddOrUpdate').numberbox('setValue',1);
	}
	var row=$('#dg').datagrid('getRows')[index];
	update(row);
}

function groupMemberSubmitForm(){
	var addOrUpdate =  $('#groupMemberAddOrUpdate').numberbox('getValue');
	var mdn = $('#groupMemberMdn').numberbox('getValue');
	$.ajax({type:'post',traditional:true,data:{mdn:mdn},
		url:contextPath+"/groupMember/selectBelongGroup.action",
		success:function (data){
			var json=$.parseJSON(data);
			console.log("获取的所属组信息是:"+json.groupStr);
			if(json.groupStr==''){
				$('#groupMemberForm').form('submit',{
		    		success:function(data){
		    			$.messager.progress('close');
		    			groupMemberSubmitSuccess(data);
		    		},
		    		onSubmit:function(){
		    			var isOk=$(this).form('enableValidation').form('validate');
		    			if(isOk) $.messager.progress({msg:'正在提交请稍等...'});
		    			return isOk;
		    		}
		    	});
			}else{
				if(addOrUpdate==0){
					$.messager.confirm(LOCALE.Confirm,"该用户已经属于【"+json.groupStr+"】组,确定添加吗?",function(r){    
						if(r){    
							$('#groupMemberForm').form('submit',{
								success:function(data){
									$.messager.progress('close');
									groupMemberSubmitSuccess(data);
								},
								onSubmit:function(){
									var isOk=$(this).form('enableValidation').form('validate');
									if(isOk) $.messager.progress({msg:'正在提交请稍等...'});
									return isOk;
								}
							});
						}
					});
				}else{
					$('#groupMemberForm').form('submit',{
						success:function(data){
							$.messager.progress('close');
							groupMemberSubmitSuccess(data);
						},
						onSubmit:function(){
							var isOk=$(this).form('enableValidation').form('validate');
							if(isOk) $.messager.progress({msg:'正在提交请稍等...'});
							return isOk;
						}
					});
				}
			}
		}
	});
}

function groupMemberSubmitSuccess(data){
	var json=$.parseJSON(data);
	if(json&&json.error) {
		$.messager.alert('出错提示',json.error,'error');
		return false;
	}else if(json.msg){
		if($('#groupMemberWindow')){
			$('#groupMemberWindow').window('close');
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

