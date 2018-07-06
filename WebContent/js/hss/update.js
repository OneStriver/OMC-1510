var allMsvocodec;
$(function(){
	allMsvocodec = $("#updateMsvocodec").combobox('getData');
	$('#updateMdn').numberbox('textbox').css('background','#ccc');
	$('#updateDeviceType').textbox('textbox').css('background','#ccc');
	$('#updateImsi').numberbox('textbox').css('background','#ccc');
	
	//分组业务
	var region = $("#updateRegion");
	isChecked = region.is(':checked');
	if(isChecked){
		region.val("1");
	}else{
		region.val("0");
	}
	$('input[name=voiceMailSwitch]').prop("disabled", !isChecked);
	region.bind('change', function() {
		var isChecked = region.prop('checked');
		if(isChecked){
			region.val("1");
		}else{
			region.val("0");
		}
		$('input[name=voiceMailSwitch]').prop("disabled", !isChecked);
		isChecked = isChecked && $('input[name=voiceMailSwitch]:last').is(':checked');
		$('#updateVoiceMailNum').textbox({
			disabled : !isChecked
		});
	});
	isChecked = $('input[name=voiceMailSwitch]:last').is(':checked') && isChecked;
	$('#updateVoiceMailNum').textbox({
		disabled : !isChecked
	});
	$('input[name=voiceMailSwitch]').bind('change', function() {
		var isStatic = $(this).val() == 1;
		$('#updateVoiceMailNum').textbox({
			disabled : !isStatic
		});
	});
	
	selectDevice($('#updateDeviceType').textbox('getValue'));
	
	$('#updatePowerLevel').combobox({
		onChange: function(value){
			judgePowerLevel(value);
		}
	});
	
});

function judgePowerLevel(value){
	if(value == 1){
		$("#updateNote").text("终端EIRP最小5dbW，最大8dbW");
	}else if(value == 2){
		$("#updateNote").text("终端EIRP最小5dbW，最大8dbW");
	}else if(value == 3){
		$("#updateNote").text("保留");
	}else if(value == 4){
		$("#updateNote").text("终端EIRP最小20dbW，最大25dbW");
	}else if(value == 5){
		$("#updateNote").text("保留");
	}else if(value == 6){
		$("#updateNote").text("终端EIRP最小20dbW，最大25dbW");
	}else if(value == 7){
		$("#updateNote").text("保留");
	}
}


function selectDevice(value) {
	$("#updateMsvocodec").combobox('clear');
	$("#updateMsvocodec").combobox('loadData',allMsvocodec);
	
	if(value == 2){
		$("#updateMsvocodec").combobox('clear');
		$("#updateMsvocodec").combobox('setValue',8);
		$("#updateMsvocodec").combobox('setText','PCMA');
		$("#updateMsvocodec").combobox('readonly',true);
	}
	
	if(value == 'CDMA'){
		$("#updateMsvocodec").combobox('readonly',true);
	}else{
		$("#updateMsvocodec").combobox('clear');
		$("#updateMsvocodec").combobox('loadData',allMsvocodec);
	}
	
	if (value == 5) {
		var data = new Array();
		for ( var i in allmsvocodec) {
			var item = allmsvocodec[i];
			if(item.text == 'VC12' || item.text == 'VC24' || item.text == 'VC40'){
				if(item.text == 'VC12'){
					item.selected = true;
				}
				data[data.length] = item;
			}
		}
		$("#updateMsvocodec").combobox('clear');
		$("#updateMsvocodec").combobox('readonly',false);
		$("#updateMsvocodec").combobox('loadData',data);
	}
	
	if (value == 8) {
		$('#updateUserType').combobox('setValue', 1);
	}
	
	if (value == 6 || value == 7) {
		$('#updateGroupInfoService').parent().css('display', '');
	} else {
		$('#updateGroupInfoService').parent().css('display', 'none');
	}
	
	//IMS信息(域名和密码)的显示和隐藏
	if (value == 8 || value == 12) {
		$('#updateImsInfo').parent().css('display', '');
		$('#updateDomain').textbox({
			required : true
		});
		$('#updateUePassword').textbox({
			required : true
		});
	} else {
		// 后加的。只有ims与disp显示ims框
		$('#updateImsInfo').parent().css('display', 'none');
		//做验证
		$('#updateDomain').textbox({
			required : false
		});
		$('#updateUePassword').textbox({
			required : false
		});
	}

	if (value == 5 || value == 13) {
		$('#updateEpcGroupDataService').parent().css('display', '');
	} else {
		$('#updateEpcGroupDataService').parent().css('display', 'none');
	}
}

function updateMingWen(checkbox) {
	$(checkbox).parent().next().textbox({
		type : checkbox.checked ? 'text' : 'password'
	});
}

//开启业务按钮控制ospf是否可选
function updateCheckBox(cb){
	var ospfzb = document.getElementById("updateOspfzb");
	var ospfgb = document.getElementById("updateOspfgb");
	if(cb.checked){
		ospfzb.disabled = "";
		ospfgb.disabled = "";
	}else{
		ospfzb.disabled = "disabled";
		ospfgb.disabled = "disabled";
	}
}

