//===============方法开始==========================
function activeEthUpdateSubmitForm(){
	var rows = $('#dg').datagrid('getRows');
	var boo = true;
	$(rows).each(function(i){
		//IP是否重复
		var ip = rows[i].ip;
		var row = $('#dg').datagrid('getSelections');
		var name1 = rows[i].name;
		var name2 = $('#updateActiveEthName').textbox('getValue');
		if(name1!=name2){
			var updateIp = $('#updateActiveEthIp').textbox('getValue');
			if(ip==updateIp){
				boo = false;
				$.messager.alert("提示","IP地址已存在");
				return false;
			}
		}
	});
	if(boo){
		$.messager.confirm('提示', '修改网卡可能导致网络中断和系统服务中断,确认修改吗?', function(r) {
			if (r) {
				$('#activeEthUpdateForm').form('submit',{
					success:function(data){
						submitActiveEthSuccess(data);
						$('#activeEthUpdateWindow').window('close');
					},
					onSubmit:function(){
						return $(this).form('enableValidation').form('validate');
					}
				});
			}
		});
	}
	
}

function appendActiveEth(){
	$('#activeEthAddWindow').window('open');
}

function activate(url,msg){
	var row=$('#dg').datagrid('getSelected');
	if(!row) {
		$.messager.alert('提示','请勾选一条记录!');
		return ;
	}
	if(row.name=='bond0'||row.type=='Unspec'){
        $.messager.alert('提示','该类型的网卡不能执行此操作!');
           return ;
    }
	$.messager.confirm(LOCALE.Confirm,msg?msg:'确认激活吗?',
		function(yes){    
			if(yes){
				if(!row) return;
				$.post(url,row,function(data){
					submitActiveEthSuccess(data);
				});
			}
		}
	);
}

function deactivate(url){
	var row=$('#dg').datagrid('getSelected');
	
	if(row.name=='bond0'||row.type=='Unspec'){
         $.messager.alert('提示','该类型的网卡不能执行此操作');
         return ;
	}
	activate(url,'确认去激活吗？');
}

function activeEthAddSubmitForm(){
	var rows = $('#dg').datagrid('getRows');
	var addName = $('#addActiveEthName').textbox('getValue');//ethx:y
	var addName2 = addName.substring(0,4);//ethx
	var boo1 = false;
	var boo2 = true;
	var boo3 = true;
	$(rows).each(function(i){
		var name=rows[i].name;//etha
		if(addName2==name){//有其对应的物理网卡
			boo1 = true;
		}
		if(addName==name){//名字相同
			boo2 = false;
		}
		//IP是否重复
		var ip = rows[i].ip;
		var addIp = $('#addActiveEthIp').textbox('getValue');
		if(ip==addIp){
			boo3 = false;
		}
	});
	if(boo1){
		if(boo2){
			if(boo3){
				$.messager.confirm(LOCALE.Confirm, '确认添加网卡？', function(yes) {
					if (yes) {
						$('#activeEthAddForm').form('submit',{
							success:function(data){
								submitActiveEthSuccess(data);
								$('#activeEthAddWindow').window('close');
							},
							onSubmit:function(){
								return $(this).form('enableValidation').form('validate');
							}
						});
						clearForm();
					}
				});
			}else{
				$.messager.alert("提示","IP地址已存在");
			}
		}else{
			$.messager.alert("提示","网卡已存在");
		}
	}else{
		$.messager.alert("提示","物理网卡不存在");
	}
}
function clearForm(){
	$("#addActiveEthName").textbox('clear');
	$("#addActiveEthIp").textbox('clear');
	$("#addActiveEthMask").textbox('clear');
	$("#addActiveEthMtu").numberbox('clear');
}

function onClickCell(rowIndex,field,value){
	if(field!='修改') return;
	var row=$('#dg').datagrid('selectRow',rowIndex).datagrid('getSelected');
	if(row.name=='bond0'||row.type=='Unspec'){
       return ;
    }
	editActiveEth(row);
}

function editActiveEth(row){
	$('#activeEthUpdateForm').form('load',row);
	$('#activeEthUpdateWindow').window('open');
}

function reload(){
	$('#dg').datagrid('reload'); 
}

$(function(){
	$('#activeEthAddForm').form({
		success:submitActiveEthSuccess
	});
	$('#activeEthUpdateForm').form({
		success:submitActiveEthSuccess
	});
	$('#updateActiveEthName').textbox('textbox').css('background','#ccc');
});

function submitActiveEthSuccess(data){
	var json=$.parseJSON(data);
	if(json&&json.error) {
		$.messager.alert('出错提示',json.error,'error');
		return false;
	}else if(json.msg){
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
//===============方法结束==========================