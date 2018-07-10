function reboot(){
	$.messager.confirm('操作提示','该操作会引起系统重启,确定要执行?',function(yes){
		if(yes){
			$.post(contextPath+'/card/reboot.action',function(data){
				$.messager.show({
					msg:$.parseJSON(data).msg,
					title:'操作提示',showType:'fade',timeout:1000,style:{right:'',bottom:''}
				});
			});
		}
	 }
   );
}
	
function seriNum(){
	$.messager.progress({msg:'正在读取请稍等'});
	$.ajax({
	   type: "POST",
	   url:  contextPath+"/serialNumber/listSerialNumber.action",
	   success: function(msg){
		   var json=$.parseJSON(msg);
		   $.messager.progress('close');
		   if(json&&json.error) {
				$.messager.alert('出错提示',json.error,'error');
				return false;
			}else{
		        $.messager.show({
			    height:320,
			    width:300,
				title:'板卡序列号',
				msg:function(){
					var message = '';
					var arr = json.list;
					for(var i = 0 ;i < arr.length;i++){
						message+='<div style="font-size:14px"><p style="color:#323232">'+arr[i].name+':</p><p style="color:#555555;line-height:5px">'+"“"+arr[i].serial+"”"+"</p>"+'<div class="easyui-linkbutton" id='+arr[i].name+' onclick="downLoad(this)"data-options="height:30,width:60">导出</div>'+'</div>'+'<br>';
					}
					return message;
				},
				timeout:10000,
				style:{
					right:'',
					bottom:''
				}

			});
		  }
	   }
	});
}
	
function reSet(){
	$.messager.confirm('恢复出厂设置','<div style="font-size:12px"><span style="color:#f23737">注意:</span>此操作将会还原系统出厂状态,<br/>并且<span style="color:#f23737">重启系统</span>，确认执行？</div>',function(r){    
	    if (r){    
	    	$.messager.progress({msg:'正在进行设置请稍等'});
			$.ajax({
				type:'post',
				url:contextPath+"/resetLf/reset.action",
				success:function(data){
					$.messager.progress('close');
					submitSuccess(data);
					setTimeout(function(){reboot2();},1100);
				}
			});			
	    }	    
	}); 
}
	
function reboot2(){
	$.post(contextPath+'/card/reboot.action',function(data){
		$.messager.show({
			msg:$.parseJSON(data).msg,
			title:'操作提示',showType:'fade',timeout:1000,style:{right:'',bottom:''}
		});
	});
}
	
function downLoad(i){
	console.log(i.id);
	var url =contextPath+"/serialNumber/downLoad.action?name="+i.id;
//	location = url
//	document.URL=url
//	location.href = url;
	open(url,null,"height=200,width=400,status=yes,toolbar=no,menubar=no,location=no"); 
//	var url = contextPath+"/serialNumber/downLoad.action";
//	$.ajax({
//		type:'post',
//		data:{name:i.id},
//		url:url,
//		success:function(data){
//			
//		}
//	});
}
	
$('#dg').datagrid({
	onClickCell:function(index,field,value){
		alert(value);
	}
})

function append(){
	$('#cardAddWindow').window('open');
}

function submitCardForm(){
	$('#ff').form('submit',{
		success:function(data){
			$.messager.progress('close');
			console.log("data:"+data);
			submitCardSuccess(data);
		},
		onSubmit:function(){
			var isOk = $(this).form('enableValidation').form('validate');
			if(isOk){
				$.messager.progress({
					msg : '正在添加请稍等...'
				});
				return true;
			}else{
				return false;
			}
		}
	});
}

function clearForm() {
	$('#ff').form('clear');
}

function removeit(url) {
	var rows = $('#dg').datagrid('getSelections');
	if (rows.length == 0) {
		$.messager.alert('提示', '请选择要删除的记录!');
		return;
	}
	$.messager.confirm(LOCALE.Confirm, LOCALE.DeleteMessage, function(yes) {
		if (yes) {
			var columns = $('#dg').datagrid('getSelections');
			var selectIds = [];
			$.each(columns, function(i) {
				var selectId = columns[i].id;
				selectIds.push(selectId);
			});
			if (selectIds.length > 0) {
				$.ajax({
					type : 'post',
					traditional : true,
					data : {
						ids : selectIds
					},
					url : url,
					success : function(data){
						submitCardSuccess(data);
					}
				});
			}
		}
	});
}

function submitCardSuccess(data){
	var json=$.parseJSON(data);
	console.log(json);
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
		$('#cardAddWindow').window('close');
		//清空填写的记录
		clearForm();
		return true;
	}else{
		$.messager.alert('出错提示',data,'error');
		return false;
	}
}

var zhInputFlag = false;
$(function(){
	$('#addCardName').textbox({
		events: $.extend({},$.fn.textbox.defaults.events,{
			keypress:function(event){
				return true;
			},
			compositionstart:function(event){
		       console.log("中文输入:开始");
		       zhInputFlag = true;
		    },
		    compositionend:function(event){
			   	console.log("中文输入:结束");
		    	zhInputFlag = false;
		    },
		    input:function(event){
		    	this.value = replacePunctuationMethod(this.value);
		    }
		})
	});
	
});

// 禁止中英文下面的标点符号
function replacePunctuationMethod(inputValue){
	inputValue=inputValue.replace(/[\uff0c|\u3002|\u3001|\uff1b|\u003d|\u2018|\u2019|\u3010|\u3011||\u2018|\u002c|\u002e|\u002f|\u003b|\u005b|\u005d|\u005c|']/g,'');
	return inputValue;
}

