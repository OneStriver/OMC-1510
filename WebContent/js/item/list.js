function accept(updateUrl,insertUrl){
	if (editIndex!=undefined && $('#dg').datagrid('validateRow', editIndex)){
		$('#dg').datagrid('endEdit',editIndex);
		var url=updateUrl;;
		var row=$('#dg').datagrid('getRows')[editIndex];
		delete row.config;
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
					$('#dg').datagrid('rejectChanges',editIndex);
				}else{
					$.messager.show({
						title:'操作提示',
						msg:data.msg,
						showType:'fade',timeout:1500,
						style:{
							right:'',bottom:'',
							top:document.body.scrollTop+document.documentElement.scrollTop
						}
					});
					$('#dg').datagrid('reload');
				}
				
			}
		});
		editIndex = undefined;
		isInsert = undefined;
	}
}

function submitForm(){
	$('#af').form('submit',{
		success:function(data){
			$.messager.progress('close');
			submitSuccess(data);
			$('#aw').window('close');
		},
		onSubmit:function(){
			var isOk=$(this).form('enableValidation').form('validate');
			if(isOk) $.messager.progress({msg:'正在保存请稍等'});
			return isOk;
		}
	});
}

function usubmitForm(){
	$('#uf').form('submit',{
		success:function(data){
			$.messager.progress('close');
			submitSuccess(data);
			$('#uw').window('close');
		},
		onSubmit:function(){
			var isOk=$(this).form('enableValidation').form('validate');
			if(isOk) $.messager.progress({msg:'正在保存请稍等'});
			return isOk;
		}
	});
}
function clearForm(){
	$('#af').form('clear');
}

function update(row){
	$('#uvalue').textbox({required:row.required});
	if(row.multiline){
		$('#uvalue').textbox({multiline:true,height:88});
		if(row.value){
			var tmp=row.value.split('\\n').join('\n');
			row.value=tmp;
		}
	}else{
		$('#uvalue').textbox({multiline:false,height:22});
	}
	$('#uf').form('load',row);
	if(row.parent)
		$('#uf').form('load',{'parent.id':row.parent.id});
	else
		$('#uf').form('load',{'parent.id':''});
	var record={};
	if('config' in row){
		$('form#uf input.easyui-combobox:eq(0)').combobox('reload',contextPath+'/config/listjsonarr.action');
		$('form#uf input.easyui-combobox:eq(1)').combobox('reload',contextPath+'/relevance/listjsonarr.action');
		record={'config.id':row.config.id,'relevance.id':row.relevance&&row.relevance.id};
	}else{
		$('form#uf input.easyui-combobox:first').combobox('reload',contextPath+'/common/listjsonarr.action');
		record={'common.id':row.common&&row.common.id};
	}
	$.extend(row, record);
	$('#uf').form('load',row);

	if(row.formtype=='combobox'||row.formtype=='radio') forOpt(row);
	$('#uw').window('open');
}

function forOpt(row){
	$('form#uf tr:gt(6)').remove();
	$('form#uf a.easyui-linkbutton:first').css('display','');
	row.optiones.forEach(function(option){
		createOptTr($('#uf'),option);
	});
}


function onClickCell(index, field, value){
	if(field!='update') return;
	var row=$('#dg').datagrid('getRows')[index];
	update(row);
	//console.log(row);
}

function aswitchMultiline(input){
	if(input.checked){
		$('#avalue').textbox({multiline:true,height:88});
	}else{
		$('#avalue').textbox({multiline:false,height:22});
	}
}

function uswitchMultiline(input){
	if(input.checked){
		$('#uvalue').textbox({multiline:true,height:88});
	}else{
		$('#uvalue').textbox({multiline:false,height:22});
	}
}

function onSelect(record){
	$('#dg').datagrid({
		queryParams:{configId:record.id}
	});
}

function openWindow(){
	$('#aw').window('open');
	$('form#af input.easyui-combobox:eq(0)').combobox('reload',contextPath+'/config/listjsonarr.action');
	$('form#af input.easyui-combobox:eq(1)').combobox('reload',contextPath+'/relevance/listjsonarr.action');
}

function switchRequired(ele){
	var checked=ele.checked;
	$(ele).parents('tr').siblings("tr:first").find("input.easyui-textbox").textbox({required:checked});
}

function uonSelect(record){
	if(record.value=='combobox'||record.value=='radio'){
		$('form#uf a.easyui-linkbutton:first').css('display','');
	}else{
		$('form#uf a.easyui-linkbutton:first').css('display','none');
	}
}

function aonSelect(record){
	$('form#af tr:gt(6)').remove();
	if(record.value=='combobox'||record.value=='radio'){
		$('form#af a.easyui-linkbutton:first').css('display','');
		createOptTr($('form#af'));
	}else{
		$('form#af a.easyui-linkbutton:first').css('display','none');
	}
}

function createOptTr($form,option){
	var optSize=$form.find('tr:gt(6)').size();
	var optIndex=optSize+1;
	var td1=$('<td/>').text('选　项 '+optIndex+':');
	var text=$('<input/>').attr('name','optiones['+(optIndex-1)+'].text');
	var td2=$('<td/>').append(text);
	var val=$('<input/>').attr('name','optiones['+(optIndex-1)+'].val');
	var td3=$('<td/>').text('选项'+optIndex+'值 :');
	var td4=$('<td/>').append(val);
	var tr=$('<tr/>').append(td1).append(td2).append(td3).append(td4);
	$form.find('table').append(tr);
	text.textbox({width:130,required:true}).textbox('setText',option&&option.text).textbox('setValue',option&&option.text);
	val.textbox({width:130,required:true,icons: [{
				iconCls:'icon-remove',
				handler: function(e){
					$(e.data.target).parents('tr').remove();
				}}]
			}).textbox('setText',option&&option.val).textbox('setValue',option&&option.val);
}

function addOption(btn){
	$form=$(btn).parents('form');
	createOptTr($form);
}
