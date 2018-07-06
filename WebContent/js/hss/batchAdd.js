
////////////////////
Priority = $("#batchAddPriority").combobox('getData');
$(function(){
	$('#batchAddDeviceType').combobox({
		onSelect : function(record) {
			batchDeviceSelect(record.value);
		}
	});
	batchDeviceSelect($('#batchAddDeviceType').combobox('getValue'));
});

function batchDeviceSelect(value){
	var batchAddMsvocodecs = $("#batchAddMsvocodec").combobox('getData');
	if(value == 7){
		$("#batchAddMsvocodec").combobox('clear');
		$("#batchAddMsvocodec").combobox('loadData',batchAddMsvocodecs);
		for (var i=0;i<batchAddMsvocodecs.length;i++){
			if(batchAddMsvocodecs[i].value==38){
				$('#batchAddMsvocodec').combobox('select',batchAddMsvocodecs[i].value);
				break;
			}
		}
	}else{
		$("#batchAddMsvocodec").combobox('clear');
		$("#batchAddMsvocodec").combobox('loadData',batchAddMsvocodecs);
	}
	
	if(value==8){	
		var DispPriority = new Array();
		$('#batchAddUserType').combobox('setValue', 1);
		for(var i=0;i<Priority.length;i++){
			if(Priority[i].value==15){
				DispPriority.push(Priority[i]);
				$("#batchAddPriority").combobox('clear');
				$("#batchAddPriority").combobox('loadData',DispPriority);
				$("#batchAddPriority").combobox('select',DispPriority[0].value);
			}
		}
	}else{
		var NormalPriority = new Array();
		NormalPriority.splice(0,NormalPriority.length);
		for(var i=0;i<Priority.length;i++){
			if(Priority[i].value!=15){
				NormalPriority.push(Priority[i]);
			}
		}
		$("#batchAddPriority").combobox('clear');
		$("#batchAddPriority").combobox('loadData',NormalPriority);
	}
	
	//IMS信息的显示和隐藏
	if(value==8||value==12){
		$('#batchAddImsInfo').parent().css('display', '');
		$('#batchAddDomain').textbox({
			required : true
		});
		$('#batchUePassword').textbox({
			required : true
		});
	}else{	
		$('#batchAddImsInfo').parent().css('display', 'none');
		$('#batchAddDomain').textbox({
			required : false
		});
		$('#batchUePassword').textbox({
			required : false
		});
	}
	
}

function batchAddMingWen(checkbox) {
	$(checkbox).parent().next().textbox({
		type : checkbox.checked ? 'text' : 'password'
	});
}

//监听业务JS
var monitorSwitch = $('#batchAddMonitorSwitch');
var isChecked = monitorSwitch.is(':checked');
if(isChecked){
	monitorSwitch.val("1");
}else{
	monitorSwitch.val("0");
}
$('#batchAddMonitorIP').textbox({
	disabled : !isChecked
});
$('#batchAddMonitorPort').textbox({
	disabled : !isChecked
});
monitorSwitch.bind("change", function() {
	var isChecked = monitorSwitch.prop('checked');
	if(isChecked){
		monitorSwitch.val("1");
	}else{
		monitorSwitch.val("0");
	}
	$('#batchAddMonitorIP').textbox({
		disabled : !isChecked
	});
	$('#batchAddMonitorPort').textbox({
		disabled : !isChecked
	});
});

