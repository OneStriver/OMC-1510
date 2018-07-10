function onClickCell(index, field, value){
	var row=$('#dg').datagrid('getRows')[index];
	var originalNum = row.originalNumber;
	if(field=='上移'){
		if(!originalNum.startsWith('*')){
			moveUpNumberTranslation(row);
		}
	}else if(field=='下移'){
		if(!originalNum.startsWith('*')){
			moveDownNumberTranslation(row);
		}
	}else if(field=='修改'){
		var originalNum = row.originalNumber;
		if(!originalNum.startsWith('*')){
			updateNumberTranslation(row);
		}
	}else if(field=='删除'){
		var originalNum = row.originalNumber;
		if(!originalNum.startsWith('*')){
			deleteNumberTranslation(row);
		}
	}
}

//=======================添加操作开始========================
function appendConvert(){
	$('#addForm').form({url:contextPath+'/switch/add.action'});
	$('#addNumberTranslation').window({title:"添加号码映射表",iconCls:'icon-add'}).window('open');
}
function addSubmitForm(){
	$('#addForm').form('submit',{
		success:function(data){
			$.messager.progress('close');
			addSubmitSuccess(data);
		}
	});
}
function addSubmitSuccess(data){
	var json=$.parseJSON(data);
	if(json&&json.error) {
		$.messager.alert('出错提示',json.error,'error');
		return false;
	}else if(json.msg){
		if($('#addNumberTranslation')){
			$('#addNumberTranslation').window('close');
		}
		$.messager.show({
			title:'操作提示',
			msg:json.msg,
			showType:'fade',
			timeout:1000,
			style:{
				right:'',bottom:''
			}
		});
		if($('#dg')){
			$('#dg').datagrid('reload');
		} 
		return true;
	}else{
		alert('未预料消息：'+data);
		return false;
	}
}
//=======================添加操作结束========================

//=====================更新操作开始==========================
function updateNumberTranslation(row){
	$('#updateForm').form('load',row).form({url:contextPath+'/switch/update.action'});
	$('#serviceOption').combobox('setValue', row.serviceOption);
	$('#updateNumberTranslation').window({title:"修改号码映射表",iconCls:'icon-edit'}).window('open');
}
function updateSubmitForm(){
	$('#updateForm').form('submit',{
		success:function(data){
			$.messager.progress('close');
			updateSubmitSuccess(data);
		}
	});
}
function updateSubmitSuccess(data){
	var json=$.parseJSON(data);
	console.log(json);
	if(json&&json.error) {
		$.messager.alert('出错提示',json.error,'error');
		return false;
	}else if(json.msg){
		if($('#updateNumberTranslation')){
			$('#updateNumberTranslation').window('close');
		}
		$.messager.show({
			title:'操作提示',
			msg:json.msg,
			showType:'fade',
			timeout:1000,
			style:{
				right:'',bottom:''
			}
		});
		if($('#dg')){
			$('#dg').datagrid('reload');
		} 
		return true;
	}else{
		alert('未预料消息：'+data);
		return false;
	}
}
//=====================更新操作结束==========================

//=====================删除操作开始=========================
function deleteNumberTranslation(row){
	$.messager.confirm('提示','确定删除该数据吗?',
		function(yes){    
			if(yes){
				//发送ajax请求
				$.ajax({
			        type: 'POST',
			        url: contextPath+'/switch/delete.action',
			        data: {deleteOriginalNum: row.originalNumber, deleteMappingNum: row.mappingNumber, deleteServiceOption: row.serviceOption},
			        success: function(data){
			        	deleteSubmitSuccess(data);
			        }
			    });
			}
		}
	);
}
function deleteSubmitSuccess(data){
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
		alert('未预料消息：'+data);
		return false;
	}
}
//====================删除操作结束==========================
	
//====================上移操作开始==========================	
function moveUpNumberTranslation(row){
	$.messager.confirm('提示','确定上移该数据吗?',
		function(yes){    
			if(yes){
				//发送ajax请求
				$.ajax({
			        type: 'POST',
			        url: contextPath+'/switch/moveUp.action',
			        data: {moveUpOriginalNum: row.originalNumber, moveUpMappingNum: row.mappingNumber, moveUpServiceOption: row.serviceOption},
			        success: function(data){
			        	moveUpSubmitSuccess(data);
			        }
			    });
			}
		}
	);
}
function moveUpSubmitSuccess(data){
	var json=$.parseJSON(data);
	if(json&&json.error) {
		$.messager.alert('出错提示',json.error,'error');
		return false;
	}else if(json.msg){
		$.messager.show({
			title:'操作提示',
			msg:json.msg,
			showType:'fade',
			timeout:1000,
			style:{
				right:'',bottom:''
			}
		});
		if($('#dg')){
			$('#dg').datagrid('reload');
		} 
		return true;
	}else{
		alert('未预料消息：'+data);
		return false;
	}
}
//====================上移操作结束==========================	
	
//====================下移操作开始==========================	
function moveDownNumberTranslation(row){
	$.messager.confirm('提示','确定下移该数据吗?',
		function(yes){    
			if(yes){
				//发送ajax请求
				$.ajax({
			        type: 'POST',
			        url: contextPath+'/switch/moveDown.action',
			        data: {moveDownOriginalNum: row.originalNumber, moveDownMappingNum: row.mappingNumber, moveDownServiceOption: row.serviceOption},
			        success: function(data){
			        	moveDownSubmitSuccess(data);
			        }
			    });
			}
		}
	);
}
function moveDownSubmitSuccess(data){
	var json=$.parseJSON(data);
	if(json&&json.error) {
		$.messager.alert('出错提示',json.error,'error');
		return false;
	}else if(json.msg){
		$.messager.show({
			title:'操作提示',
			msg:json.msg,
			showType:'fade',
			timeout:1000,
			style:{
				right:'',bottom:''
			}
		});
		if($('#dg')){
			$('#dg').datagrid('reload');
		} 
		return true;
	}else{
		alert('未预料消息：'+data);
		return false;
	}
}
//====================下移操作结束==========================