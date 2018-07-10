//批量修改
$(function(){
	var monitorSwitch = $("#batchHssUpdateMonitorSwitch");
	var isChecked = monitorSwitch.is(':checked');
	isChecked=false;
	if(!isChecked){
		$('#batchHssUpdateMonitorIP').textbox({
			disabled : true
		});
		$('#batchHssUpdateMonitorPort').textbox({
			disabled : true
		});
	}
	if(isChecked){
		monitorSwitch.val("1");
	}else{
		monitorSwitch.val("0");
	}
	$('#batchHssUpdateMonitorIP').textbox({
		disabled : !isChecked
	});
	$('#batchHssUpdateMonitorPort').textbox({
		disabled : !isChecked
	});
	monitorSwitch.bind("change", function() {
		var isChecked = monitorSwitch.prop('checked');
		if(isChecked){
			monitorSwitch.val("1");
		}else{
			monitorSwitch.val("0");
		}
		$('#batchHssUpdateMonitorIP').textbox({
			disabled : !isChecked
		});
		$('#batchHssUpdateMonitorPort').textbox({
			disabled : !isChecked
		});
	});
	
	//批量修改分组业务
 	var region = $("#batchHssUpdateRegion");
	var regionChecked=region.is(":checked");
	var boxChecked1=$("#batchHssUpdateVoiceMailSwitch1").is(":checked");
	if(!boxChecked1){
		$("#batchHssUpdateVoiceMailNum").prop("disabled",true);
	}
	 if(regionChecked){
		 region.val("1");
		 $("#batchHssUpdateVoiceMailSwitch0").prop("disabled", false);
		 $("#batchHssUpdateVoiceMailSwitch1").prop("disabled", false);
		 $("#batchHssUpdateOspfMulticast").prop("disabled",false);
		 $("#batchHssUpdateOspfBroadcast").prop("disabled",false);
	 }else{
		 region.val("0");
		 $("#batchHssUpdateVoiceMailSwitch0").prop("disabled", true);
		 $("#batchHssUpdateVoiceMailSwitch1").prop("disabled", true);
		 $("#batchHssUpdateOspfMulticast").prop("disabled",true);
		 $("#batchHssUpdateOspfBroadcast").prop("disabled",true);
	 }
	var ospfzb=$("#batchHssUpdateOspfMulticast");
	var ospfgb=$("#batchHssUpdateOspfBroadcast");
	var ospfzbChecked=ospfzb.is(":checked");
	var ospfgbChecked=ospfgb.is(":checked");
	if(ospfzbChecked){
		ospfzb.prop("checked",true);
	}else{
		ospfzb.prop("checked",false);
	}
	if(ospfgbChecked){
		ospfgb.prop("checked",true);
	}else{
		ospfgb.prop("checked",false);
	}
	region.bind('change', function() {
		var isChecked = region.prop('checked');
		if(isChecked){
			region.val("1");
		}else{
			region.val("0");
		}
		$("#batchHssUpdateVoiceMailSwitch0").prop("disabled", !isChecked);
		$("#batchHssUpdateVoiceMailSwitch1").prop("disabled", !isChecked);
		$('#batchHssUpdateVoiceMailNum').textbox({
			disabled : !isChecked
		});	
	});
	$('input[name=voiceMailSwitch]').bind('change', function() {
		var isStatic = $(this).val() == 1;
		$('#batchHssUpdateVoiceMailNum').textbox({
			disabled : !isStatic
		});
	});
	region.click(function() {
		var isChecked=region.prop("checked");
		if(isChecked){
			region.val("1");
			$("#batchHssUpdateOspfMulticast").prop("disabled",false);
			$("#batchHssUpdateOspfBroadcast").prop("disabled",false);
		}else{
			region.val("0");
			$("#batchHssUpdateOspfMulticast").prop("disabled",true);
			$("#batchHssUpdateOspfBroadcast").prop("disabled",true);
		}
	});
	
    $('#batchHssUpdateButton').bind('click', function(){
    	if($('#dg').datagrid('getSelections').length == 0){
			$.messager.alert('提示','请选择要批量修改的记录!');
		}else{
			var batchSelectFlag = true;
			var rows = $('#dg').datagrid('getSelections');
			var imsis=[];
			$(rows).each(function(i){
				var id=rows[i].imsi;
				imsis.push(id);
			});
			$(rows).each(function(i){
				if(i==0){
					
				}else{
					if(rows[i-1].deviceName!=rows[i].deviceName){
						$.messager.alert('提示','请选择相同设备类型的用户进行批量修改','info');
						batchSelectFlag = false;
					}
				}
			});
			if(batchSelectFlag){
				document.getElementById('hssflag').value = imsis;
				$('#batchHssUpdateWindow').window('open');
				$('#batchHssUpdateForm').form('reset');
			}
		}
    });
    
});

//================添加用户=================
function addHssSubmitForm() {	
	$('#addHssForm').form('submit', {
		success : function(data) {
			$.messager.progress('close');
			if (submitSuccess(data)) {
				$('#addHssWindow').window(close);
			}
		},
		onSubmit : function() {
			var isOk = $(this).form('enableValidation').form('validate');
			if (isOk) {
				$.messager.progress({
					msg : '正在添加,请稍等...'
				});
				$('#addMdn,#addImsi').textbox('enable');
			}
			return isOk;
		}
	});
}
function loadAddScript(){
	$('head:first').append('<script src="'+contextPath+'/js/hss/hss.js"></script>');
}
function append(){
	var attr={
		height:$(document.body).height(),
		top:0
	};
	$('#addHssWindow').window(attr).window('open');
}

//================修改用户=================
function submitUpdateForm() {
	$("#updateHssForm").form('submit', {
		success : function(data) {
			$.messager.progress('close');
			if (submitSuccess(data)) {
				$('#updateWindow').window(close);
			}
		},
		onSubmit : function() {
			var isOk = $(this).form('enableValidation').form('validate');
			if (isOk) {
				$.messager.progress({
					msg : '正在保存请稍等'
				});
				$('#updateMdn,#updateImsi').textbox('enable');
			}
			return isOk;
		}
	});
}
function loadUpdateScript(){
	$('head:first').append('<script src="'+contextPath+'/js/hss/update.js"></script>');
}
//点击修改执行
function onClickCell(index, field, value){
	if(field!='修改') return;
	var row=$('#dg').datagrid('getRows')[index];
	update(row);
}
function update(row){
	var attr={
		title:LOCALE.hssUpdateTitle,
		height:$(document.body).height(),
		top:0,
		href:contextPath+'/hss/updateUI.action?imsi='+row.imsi
	};
	$('#updateWindow').window(attr).window('open');
}

//=================批量添加用户====================
function loadBatchScript(){
	$('head:first').append('<script src="'+contextPath+'/js/hss/batchAdd.js"></script>');
}
function batchAppend(){
	var attr={
		height:$(document.body).height(),
		top:0
	};
	$('#batchAddWindow').window(attr).window('open');
}
function batchAddHssSubmitForm() {
	$("#batchAddHssForm").form('submit', {
		url:contextPath+'/hss/batchAdd.action',
		success : function(data) {
			$.messager.progress('close');
			if (submitSuccess(data)) {
				$('#batchAddWindow').window(close);
			}
		},
		onSubmit : function() {
			var isOk = $(this).form('enableValidation').form('validate');
			if (isOk) {
				$.messager.progress({
					msg : '正在批量插入数据请稍等...'
				});
				$('#batchAddImsi,#batchAddMdn').textbox('enable');
			}
			return isOk;
		}
	});
}

//批量修改操作
function batchHssUpdateSubmit(){
	$('#batchHssUpdateForm').form('submit',{
		success:function(data){
			$.messager.progress('close');
			if(submitSuccess(data)){
				$('#batchHssUpdateWindow').window(close);
			}
		},
		onSubmit:function(){
			var isOk=$(this).form('enableValidation').form('validate');
			if(isOk) $.messager.progress({msg:'正在修改请稍等...'});
			return isOk;
		}
	});
}

function importHss(){
	$('#hssF').form('submit',{
		success:function(data){
			$.messager.progress('close');
			if(submitSuccess(data)){
				$('#importHss').window(close);
			}
		},
		onSubmit:function(){
			var isOk=$(this).form('enableValidation').form('validate');
			if(isOk) $.messager.progress({msg:'正在导入请稍等...'});
			return isOk;
		}
	});
}

function exportHss(){
	$.messager.confirm(LOCALE.Confirm,LOCALE.ExportMessage,function(r){    
	    if(r){    
	    	var url=contextPath+"/hss/export.action";
	    	var subWin=open(url,null,"height=200,width=600,status=yes,toolbar=no,menubar=no,location=no");  
	    }
	});
}
function importExcel(){
	$.messager.confirm('提示',
		'<div>是否删除原有用户，并导入新用户</div><div>确定:删除原有用户，并导入新用户</div><div>取消:不删除原有用户，并导入新用户</div>',
		function(r){    
	    if (r){
			$('#hssExcel').form('submit',{
				success:function(data){
					$.messager.progress('close');
					if(submitSuccess(data)){
						$('#importExcel').window(close);
					}
				},
				onSubmit:function(){
					document.getElementById('hid').value='1';
					var isOk=$(this).form('enableValidation').form('validate');
					if(isOk) $.messager.progress({msg:'正在导入请稍等...'});
					return isOk;
				}
			});
	    }else{
	    	$('#hssExcel').form('submit',{
				success:function(data){
					$.messager.progress('close');
					if(submitSuccess(data)){
						$('#importExcel').window(close);
					}
				},
				onSubmit:function(){
					/*$.messager.alert('提示','取消操作会更新数据库中的数据');*/
					document.getElementById('hid').value='2';
					var isOk=$(this).form('enableValidation').form('validate');
					if(isOk) $.messager.progress({msg:'正在导入请稍等...'});
					return isOk;
				}
			});
	    }   
	});
	
	
}
function importXml(){
	$('#hssXml').form('submit',{
		success:function(data){
			$.messager.progress('close');
			if(submitSuccess(data)){
				$('#importXml').window(close);
			}
		},
		onSubmit:function(){
			var isOk=$(this).form('enableValidation').form('validate');
			if(isOk) $.messager.progress({msg:'正在导入请稍等...'});
			return isOk;
		}
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
				var imsis=[];
				$(rows).each(function(i){
					var id=rows[i].imsi;
					imsis.push(id);
				});
				$.messager.progress({msg:'正在删除请稍等...'});
				$.ajax({type:'post',traditional:true,data:{imsis:imsis},
					url:contextPath+"/hss/delete.action",
					success:function(data){
						$.messager.progress('close');
						submitSuccess(data);
					}
				});
			}
		}
	);
}

function qq(value,name){
	if(!/^\d*$/.test(value)){
		return;
	}
	var options=$('#dg').datagrid('options');
	var params={};
	jQuery.extend(params, options.queryParams);
	delete params.mdn;
	delete params.imsi;
	params[name]=value;
	$('#dg').datagrid({
		queryParams:params
	});
}

//表格顶部设备类型修改事件
function dChange(newValue, oldValue){
	if(!/^\d*$/.test($('#ss').searchbox('getValue'))){
		$('#ss').searchbox('setValue','');
	}
	var options=$('#dg').datagrid('options');
	var params={};
	jQuery.extend(params, options.queryParams);
	var imsiOrMdnName=$('#ss').searchbox('getName');
	var imsiOrMdnValue=$('#ss').searchbox('getValue');
	if(imsiOrMdnValue) params[imsiOrMdnName]=imsiOrMdnValue;
	else delete params[imsiOrMdnName];
	params.deviceType=newValue;
	console.log("发送[设备类型]远端的请求的参数:"+params);
	$('#dg').datagrid({
		queryParams:params
	});
}

//表格顶部语音编码类型修改事件
function mChange(newValue, oldValue){
	if(!/^\d*$/.test($('#ss').searchbox('getValue'))){
		$('#ss').searchbox('setValue','');
	}
	var options=$('#dg').datagrid('options');
	var params={};
	jQuery.extend(params, options.queryParams);
	var imsiOrMdnName=$('#ss').searchbox('getName');
	var imsiOrMdnValue=$('#ss').searchbox('getValue');
	if(imsiOrMdnValue) params[imsiOrMdnName]=imsiOrMdnValue;
	else delete params[imsiOrMdnName];
	params.msvocodec=newValue;
	console.log("发送[语音编码类型]远端的请求的参数:"+params);
	$('#dg').datagrid({
		queryParams:params
	});
}

//表格顶部在线状态修改事件
function sChange(newValue, oldValue){
	if(!/^\d*$/.test($('#ss').searchbox('getValue'))){
		$('#ss').searchbox('setValue','');
	}
	var options=$('#dg').datagrid('options');
	var params={};
	jQuery.extend(params, options.queryParams);
	var imsiOrMdnName=$('#ss').searchbox('getName');
	var imsiOrMdnValue=$('#ss').searchbox('getValue');
	if(imsiOrMdnValue) params[imsiOrMdnName]=imsiOrMdnValue;
	else delete params[imsiOrMdnName];
	params.status=newValue;
	console.log("发送[在线状态]远端的请求的参数:"+params);
	$('#dg').datagrid({
		queryParams:params
	});
}

//表格顶部过滤遥毙状态修改事件
function destroyChange(newValue, oldValue){
	if(!/^\d*$/.test($('#ss').searchbox('getValue'))){
		$('#ss').searchbox('setValue','');
	}
	var options=$('#dg').datagrid('options');
	var params={};
	jQuery.extend(params, options.queryParams);
	var imsiOrMdnName=$('#ss').searchbox('getName');
	var imsiOrMdnValue=$('#ss').searchbox('getValue');
	if(imsiOrMdnValue) params[imsiOrMdnName]=imsiOrMdnValue;
	else delete params[imsiOrMdnName];
	params.destroy=newValue;
	console.log("发送[遥毙状态]远端的请求的参数:"+params);
	$('#dg').datagrid({
		queryParams:params
	});
}

//表格顶部过滤对空监听状态修改事件
function airMonitorChange(newValue, oldValue){
	if(!/^\d*$/.test($('#ss').searchbox('getValue'))){
		$('#ss').searchbox('setValue','');
	}
	var options=$('#dg').datagrid('options');
	var params={};
	jQuery.extend(params, options.queryParams);
	var imsiOrMdnName=$('#ss').searchbox('getName');
	var imsiOrMdnValue=$('#ss').searchbox('getValue');
	if(imsiOrMdnValue) params[imsiOrMdnName]=imsiOrMdnValue;
	else delete params[imsiOrMdnName];
	params.airMonitor=newValue;
	console.log("发送[对空监听状态]远端的请求的参数:"+params);
	$('#dg').datagrid({
		queryParams:params
	});
}

function onRowContextMenu(e, index, row){
	e.preventDefault();
	if(row==null)return;
	var ueDestroy = $('#rightMenu').menu('getItem', $('#ueDestroy')[0]);
	var ueRestore = $('#rightMenu').menu('getItem', $('#ueRestore')[0]);
	var ueSwoon = $('#rightMenu').menu('getItem', $('#ueSwoon')[0]);
	var ueNormal = $('#rightMenu').menu('getItem', $('#ueNormal')[0]);
	var airMonitorOpen = $('#rightMenu').menu('getItem', $('#airMonitorOpen')[0]);
	var airMonitorClose = $('#rightMenu').menu('getItem', $('#airMonitorClose')[0]);
	console.log("row.swoonFlag"+row.swoonFlag);
	if(row.deviceName!='TR'){
		$('#rightMenu')
			.menu('hideItem', ueDestroy.target).menu('hideItem', ueRestore.target)
			.menu('hideItem', ueSwoon.target).menu('hideItem', ueNormal.target)
			.menu('hideItem', airMonitorOpen.target).menu('hideItem', airMonitorClose.target);
	}else{
		if(row.swoonFlag==1){
			$('#rightMenu')
			.menu('showItem', ueSwoon.target).menu('showItem', ueNormal.target);
			if(row.swoon==2){
				$('#rightMenu')
				.menu('hideItem', ueSwoon.target)
				.menu('showItem', ueNormal.target);
			}else{
				$('#rightMenu')
				.menu('showItem', ueSwoon.target)
				.menu('hideItem', ueNormal.target);;
			}
		}else{
			$('#rightMenu')
			.menu('hideItem', ueSwoon.target).menu('hideItem', ueNormal.target);
		}
		
		if(row.destroyFlag==1){
			$('#rightMenu')
			.menu('showItem', ueDestroy.target).menu('showItem', ueRestore.target);
			if(row.destroy==2){
				$('#rightMenu')
				.menu('hideItem', ueDestroy.target)
				.menu('showItem', ueRestore.target)
				.menu('hideItem', ueSwoon.target)
				.menu('hideItem', ueNormal.target);
			}else{
				if(row.swoonFlag==1){
					$('#rightMenu')
					.menu('showItem', ueDestroy.target)
					.menu('hideItem', ueRestore.target)
					.menu('showItem', ueSwoon.target)
					.menu('hideItem', ueNormal.target);
				}else{
					$('#rightMenu')
					.menu('showItem', ueDestroy.target)
					.menu('hideItem', ueRestore.target)
				}
			}
		}else{
			$('#rightMenu')
			.menu('hideItem', ueDestroy.target).menu('hideItem', ueRestore.target);
		}
		
		if(row.airMonitorFlag==1){
			$('#rightMenu')
			.menu('showItem', airMonitorOpen.target).menu('showItem', airMonitorClose.target);
			if(row.airMonitor==2){
				$('#rightMenu')
				.menu('hideItem', airMonitorOpen.target)
				.menu('showItem', airMonitorClose.target);
			}else{
				$('#rightMenu')
				.menu('showItem', airMonitorOpen.target)
				.menu('hideItem', airMonitorClose.target);
			}
		}else{
			$('#rightMenu')
			.menu('hideItem', airMonitorOpen.target).menu('hideItem', airMonitorClose.target);
		}
	}
	
	$('#rightMenu').menu({
		onClick:function(item){
			menuHandler(item,row);
		}
	}).menu('show', {
		left:e.pageX-5,
		top:e.pageY-5
	});
}

function readStatus(row,tip){
	$.post(contextPath+"/hss/readStatus.action",{imsi:row.imsi},
		function(data){
			$.messager.progress('close');
			$('#rightMenuReadStatus').window('close');
			var json=$.parseJSON(data);
			if(json&&json.error) {
				if(tip){
					$.messager.alert('出错提示',json.error,'error');
				}
			}else{
				for(var i in json){
					$('#rightMenuReadStatus label[title='+i+']').html(json[i]);
				}
			$('#rightMenuReadStatus').window('open');
			}
		});
}

function menuHandler(item,row){
	switch(item.name){
	case 'readStatus':
		$.messager.progress({msg:'正在读取用户状态请稍等...'});
		readStatus(row,true);
		break;
	case 'syncStatus':
		$.messager.progress({msg:'正在同步用户状态请稍等...'});
		$.post(contextPath+"/hss/syncStatus.action",{imsi:row.imsi},
			function(data){
				$.messager.progress('close');
				submitSuccess(data);
			});
		break;
	case 'ueDestroy':
		$.messager.progress({msg:'正在摇毙用户请稍等...'});
		$.post(contextPath+"/hss/ueDestroy.action",{imsi:row.imsi},
			function(data){
				$.messager.progress('close');				
				submitSuccess(data);				
			});
		break;
	case 'ueRestore':
		$.messager.progress({msg:'正在恢复摇毙用户请稍等...'});
		$.post(contextPath+"/hss/ueRestore.action",{imsi:row.imsi},
			function(data){
				$.messager.progress('close');
				submitSuccess(data);
			});
		break;
	case 'ueSwoon':
		$.messager.progress({msg:'正在摇晕用户请稍等...'});
		$.post(contextPath+"/hss/ueSwoon.action",{imsi:row.imsi},
			function(data){
				$.messager.progress('close');
				submitSuccess(data);
			});
		break;
	case 'ueNormal':
		$.messager.progress({msg:'正在恢复摇晕用户请稍等...'});
		$.post(contextPath+"/hss/ueNormal.action",{imsi:row.imsi},
			function(data){
				$.messager.progress('close');
				submitSuccess(data);
			});
		break;
	case 'airMonitorOpen':
		$.messager.progress({msg:'正在开启对空监听请稍等...'});
		$.post(contextPath+"/hss/airMonitorOpen.action",{imsi:row.imsi},
			function(data){
				$.messager.progress('close');
				submitSuccess(data);
			});
		break;
	case 'airMonitorClose':
		$.messager.progress({msg:'正在关闭对空监听请稍等...'});
		$.post(contextPath+"/hss/airMonitorClose.action",{imsi:row.imsi},
			function(data){
				$.messager.progress('close');
				submitSuccess(data);
			});
		break;
	}
}


//批量修改操作
function batchHssUpdate(){
	var imsies = document.getElementById("hssflag").value;
	var imsies1 = $("#hssflag").val();
	$('#buForm').form('submit',{
		success:function(data){
			$.messager.progress('close');
			if(submitSuccess(data)){
				$('#batchHss').window(close);
			}
		},
		onSubmit:function(){
			var isOk=$(this).form('enableValidation').form('validate');
			if(isOk) $.messager.progress({msg:'正在修改请稍等...'});
			return isOk;
		}
	});
}

function batchUpdate(){
	$('#batchForm').form('submit',{
		url:contextPath+'/hss/batchUpdate.action',
		success:function(data){
			var json=$.parseJSON(data);
			console.log(json);
			if(json&&json.error) {
				$.messager.alert('出错提示',json.error,'error');
				return false;
			}else if(json.msg){
				$('#batchW').window(close);
				$.messager.show({
					title:'操作提示',
					msg:json.msg,
					showType:'fade',
					timeout:1000,
					style:{
						right:'',bottom:''
					}
				});
			}
		},
		onSubmit:function(){
			var isOk=$(this).form('enableValidation').form('validate');
			return isOk;
		}
	});
}

var timer;
function onRowMouseOver(e,rowIndex,row){
	if(e.target.tagName=='TD'&&e.target.getAttribute('field')=='mdn'){
		if(timer) clearTimeout(timer);
		timer=setTimeout(
			function(){
				readStatus(row,false);	
			},1000);
	}
}

function onRowMouseOut(e,rowIndex,row){
	if(e.target.tagName=='TD'&&e.target.getAttribute('field')=='mdn'){
//		$('#dd').window('close');
//		clearTimeout(timer);
	}
}


function onCollapseRow(index,row){
	$('#mystatus'+index+' label[title]').html('');
}

function onExpandRow(index,row){
	var json = $.ajax({url:contextPath+'/hss/readStatus.action?imsi='+row.imsi,async:false,type:"POST"}).responseText;
	json=$.parseJSON($.parseJSON(json));
	console.log(json);
	if(json&&json.error) {
		$('#mystatus'+index).html(json.error);
	}else{
		var div=$('#mystatus'+index);
		for(var i in json){
			if(i=='PS-Exist'&&json[i]=='no'){
				div.find('label[title=psDomain]').parent().hide();
				div.find('label[title=PS-Status]').parent().hide();
				div.find('label[title=ESN]').parent().hide();
				div.find('label[title=AssignedIP]').parent().hide();
				div.find('label[title=GGSNADDR]').parent().hide();
				div.find('label[title=PS-BaseServ]').parent().hide();
				return;
			}
			
			if(json[i]==null||json[i]===''){
				div.find('label[title='+i+']').parent().hide();
			}else{
					div.find('label[title='+i+']').parent().show();
					div.find('label[title='+i+']').html(json[i]);
			}
		}
		for(var i in json){
			if(i=='PS-Status'&&json[i]=='未激活'){
				div.find('label[title="AssignedIP"]').parent().hide();
				div.find('label[title="GGSNADDR"]').parent().hide();
				return;
			
			}
		}
	}
}

function detailFormatter(rowIndex, rowData){
	var div=$('#mystatus').clone(true,true).wrap('<div></div>').parent();
	div.find('div').attr('id','mystatus'+rowIndex);
	return div.html();
}

var expandArr=[];
function onDblClickRow(index,row){
	if(expandArr[index]){
		expandArr[index]=false;
		$('#dg').datagrid('collapseRow',index);
	}else{
		expandArr[index]=true;
		$('#dg').datagrid('expandRow',index);
	}
}

//遥毙之后该行呈现红色
function rowStyler(index,row){
	console.log("IMSI:"+row.imsi+">>>msprofile_extra的值为:"+row.msprofile_extra);
	if (row.destroy==2){
		return 'background-color:red;';
	}
}

//开启业务按钮控制ospf是否可选
function checkBoxBatchUpdate(cb){
	var ospfzb1 = document.getElementById("ospfzb1BatchUpdate");
	var ospfgb1 = document.getElementById("ospfgb1BatchUpdate");
	if(cb.checked){
		ospfzb1.disabled = "";
		ospfgb1.disabled = "";
	}else{
		ospfzb1.disabled = "disabled";
		ospfgb1.disabled = "disabled";
	}
}
