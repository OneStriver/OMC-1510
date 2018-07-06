//设备类型联动选择控制域名和密码的显示和隐藏
msvocodec = $("#addMsvocodec").combobox('getData');
Priority = $("#addPriority").combobox('getData');
$(function() {
	//AMF
	autoAmfInput(2);
	//SQN
	autoSqnInput(6);
	//K
	autoKInput(16);
	//OP
	autoOpInput(16);
	//OPC
	autoOpcInput(16);
	
	if ($('#addHssForm').attr('action').indexOf('update') > 0) {
		$('#addGroupInfoService input[type=checkbox]').each(function() {
			if (!$(this).attr('checked')) {
				$(this).parent().css('display', 'none');
			}
		});
	}
	$('input[type=checkbox][name=groups]').on('change', function() {
		if ($('input[type=checkbox][name=groups]:checked').length > 16) {
			this.checked = false;
			$.messager.alert('提示', '最多只能添加16个组');
		}
	});
	
	//限制MDN的最大值
	$('#addMdn').numberbox('textbox').attr('maxlength',15);
	//限制IMSI的最大值
	$('#addImsi').numberbox('textbox').attr('maxlength',15);
	
	//添加的时候
	$('#addDeviceType').combobox({
		onSelect : function(record) {
			selectDevice(record.value);
		}
	});
	selectDevice($('#addDeviceType').combobox('getValue'));

	//监听业务
	var monitorSwitch = $("#addMonitorSwitch");
	
	var isChecked = monitorSwitch.is(':checked');
	if(isChecked){
		monitorSwitch.val("1");
	}else{
		monitorSwitch.val("0");
	}
	$('#addMonitorIP').textbox({
		disabled : !isChecked
	});
	$('#addMonitorPort').textbox({
		disabled : !isChecked
	});
	monitorSwitch.bind("change", function() {
		var isChecked = monitorSwitch.prop('checked');
		if(isChecked){
			monitorSwitch.val("1");
		}else{
			monitorSwitch.val("0");
		}
		$('#addMonitorIP').textbox({
			disabled : !isChecked
		});
		$('#addMonitorPort').textbox({
			disabled : !isChecked
		});
	});
	
	//分组业务
	var region = $("#addRegion");
	var isChecked = region.is(':checked');
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
		$('#addVoiceMailNum').textbox({
			disabled : !isChecked
		});
	});
	isChecked = $('input[name=voiceMailSwitch]:last').is(':checked') && isChecked;
	//默认是置灰的
	$('#addVoiceMailNum').textbox({
		disabled : true
	});
	$('input[name=voiceMailSwitch]').bind('change', function() {
		var isStatic = $(this).val() == 1;
		$('#addVoiceMailNum').textbox({
			disabled : !isStatic
		});
	});
	
	//补充业务
	$('#addSupplementService input[type=checkbox]').bind('change',
		function() {
			//每一个按钮对应后面文本框的控制
			var name = $(this).prop('name');
			var isChecked = $(this).prop('checked');
			if(isChecked){
				$(this).val("1");
			}else{
				$(this).val("0");
			}
			$('#add' + name + 'Num').textbox({
				disabled : !isChecked
			});
			if (name == 'directFwd' && isChecked) {
				$('#addSupplementService input[type=checkbox][name!=directFwd]').prop(
						'checked', false).each(function() {
					var name = $(this).prop('name');
					var isChecked = $(this).prop('checked');
					$('#add' + name + 'Num').textbox({
						disabled : !isChecked
					});
				});
			} else if (name != 'directFwd' && isChecked) {
				$('#addSupplementService input[type=checkbox][name=directFwd]').prop(
						'checked', false);
				$('#adddirectFwdNum').textbox({
					disabled : isChecked
				});
			}
		}).each(function() {
			var name = $(this).prop('name');
			var isChecked = $(this).prop('checked');
			if(isChecked){
				$(this).val("1");
			}else{
				$(this).val("0");
			}
			$('#add' + name + 'Num').textbox({
				disabled : !isChecked
			});
	});
	
	//终端用户信息
	$('#addPowerLevel').combobox({
		onLoadSuccess: function(){
			document.getElementById("addNote").innerHTML='终端EIRP最小5dbW，最大8dbW';
		},
		onChange: function(value){
			if(value == 1){
				document.getElementById("addNote").innerHTML='终端EIRP最小5dbW，最大8dbW';
			}else if(value == 2){
				document.getElementById("addNote").innerHTML='终端EIRP最小5dbW，最大8dbW';
			}else if(value == 3){
				document.getElementById("addNote").innerHTML='保留';
			}else if(value == 4){
				document.getElementById("addNote").innerHTML='终端EIRP最小20dbW，最大25dbW';
			}else if(value == 5){
				document.getElementById("addNote").innerHTML='保留';
			}else if(value == 6){
				document.getElementById("addNote").innerHTML='终端EIRP最小20dbW，最大25dbW';
			}else if(value == 7){
				document.getElementById("addNote").innerHTML='保留';
			}else{
				
			}
		}
	});
	
});

function addMingWen(checkbox) {
	$(checkbox).parent().next().textbox({
		type : checkbox.checked ? 'text' : 'password'
	});
}

function selectDevice(value) {
	
	if(value==8){	
		var DispPriority = new Array();
		$('#addUserType').combobox('setValue', 1);
		for(var i=0;i<Priority.length;i++){
			if(Priority[i].value==15){
				DispPriority.push(Priority[i]);
				$("#addPriority").combobox('clear');
				$("#addPriority").combobox('loadData',DispPriority);
				$("#addPriority").combobox('select',DispPriority[0].value);
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
		$("#addPriority").combobox('clear');
		$("#addPriority").combobox('loadData',NormalPriority);
	}
	
	if(value == 7){
		$("#addMsvocodec").combobox('clear');
		$("#addMsvocodec").combobox('loadData',msvocodec);
		for (var i=0;i<msvocodec.length;i++){
			if(msvocodec[i].value==38){
				$('#addMsvocodec').combobox('select',msvocodec[i].value);
				break;
			}
		}
	}else{
		$("#addMsvocodec").combobox('clear');
		$("#addMsvocodec").combobox('loadData',msvocodec);
	}
	
	if (value == 6 || value == 7) {
		$('#addGroupInfoService').parent().css('display', '');
	} else {
		$('#addGroupInfoService').parent().css('display', 'none');
	}
	
	//IMS信息(域名和密码)的显示和隐藏
	if (value == 8 || value == 12) {
		$('#addImsInfo').parent().css('display', '');
		$('#addDomain').textbox({
			required : true
		});
		$('#addUePassword').textbox({
			required : true
		});
	} else {
		// 后加的。只有ims与disp显示ims框
		$('#addImsInfo').parent().css('display', 'none');
		//做验证
		$('#addDomain').textbox({
			required : false
		});
		$('#addUePassword').textbox({
			required : false
		});
	}

	if (value == 5 || value == 13) {
		$('#addEpcGroupDataService').parent().css('display', '');
	} else {
		$('#addEpcGroupDataService').parent().css('display', 'none');
	}
}


//==================鉴权参数====================
function autoAmfInput(allNumber) {
	for (var i = 1; i <= allNumber; i++) {
		$('#addAmf' + i).textbox({inputEvents : $.extend({},$.fn.textbox.defaults.inputEvents,{
				keyup : function(e) {
					var t = e.data.target.getAttribute("id").substring(3);
					if (this.value.length == 2) {
						if (parseInt(t) == ($("#addAmf input").length) / 3) {
							$('#addAmf'+ (parseInt(t))).textbox('textbox').blur();
						} else {
							$('#addAmf'+ (parseInt(t) + 1)).textbox('textbox').focus();
						}
					}
				}
			})
		});
	}
	$('#addAmf1').textbox({onChange : function(value) {
		var txts = $("#addAmf input");
		var amf1Value = $("#addAmf1").textbox("getValue");
		for (var i = 0; i < txts.length / 3; i++) {
			$("#addAmf" + (i + 1)).textbox("setValue",amf1Value.substring(i * 2, (i + 1) * 2));
			}
		}
	});
}

function autoSqnInput(allNumber) {
	for (var i = 1; i <= allNumber; i++) {
		$('#addSqn' + i).textbox({inputEvents : $.extend({},$.fn.textbox.defaults.inputEvents,{
			keyup : function(e) {
				var t = e.data.target.getAttribute("id").substring(3);
				if (this.value.length == 2) {
					if (parseInt(t) == ($("#addSqn input").length) / 3) {
						$('#addSqn'+ (parseInt(t))).textbox('textbox').blur();
					} else {
						$('#addSqn'+ (parseInt(t) + 1)).textbox('textbox').focus();
					}
				}
			}
		})
	});
}

	$('#addSqn1').textbox({onChange : function(value) {
		var txts = $("#addSqn input");
		var amf1Value = $("#addSqn1").textbox("getValue");
		for (var i = 0; i < txts.length / 3; i++) {
			$("#addSqn" + (i + 1)).textbox("setValue",amf1Value.substring(i * 2, (i + 1) * 2));
		}
	}
	});
}

function autoKInput(allNumber) {
	for (var i = 1; i <= allNumber; i++) {
		$('#addK' + i).textbox({inputEvents : $.extend({},$.fn.textbox.defaults.inputEvents,{
			keyup : function(e) {var t = e.data.target.getAttribute("id").substring(1);
			if (this.value.length >= 2) {
				if (parseInt(t) == ($("#addK input").length) / 3) {
					$('#addK'+ (parseInt(t))).textbox('textbox').blur();
				} else {
					$('#addK'+ (parseInt(t) + 1)).textbox('textbox').focus();
				}
			}
		}
	})
});
}
$('#addK1').textbox({onChange : function(value) {
	var txts = $("#addK input");
	var amf1Value = $("#addK1").textbox("getValue");
	if(amf1Value!=""){
		for(var i=0;i<txts.length/3;i++){
			$("#addK" + (i + 1)).textbox("setValue",amf1Value.substring(i*2,(i+1)*2));
		}
	}
}});
}

function autoOpInput(allNumber) {
	for (var i = 1; i <= allNumber; i++) {
		$('#addOp' + i).textbox({inputEvents : $.extend({},$.fn.textbox.defaults.inputEvents,{
			keyup : function(e) {var t = e.data.target.getAttribute("id").substring(2);
			if (this.value.length == 2) {
				if (parseInt(t) == ($("#addOp input").length) / 3) {
					$('#addOp'+ (parseInt(t))).textbox('textbox').blur();
				} else {
					$('#addOp'+ (parseInt(t) + 1)).textbox('textbox').focus();
				}
			}
		}
	})});
}
	$('#addOp1').textbox({onChange : function(value) {
		var txts = $("#addOp input");
		var amf1Value = $("#addOp1").textbox("getValue");
		for (var i = 0; i < txts.length / 3; i++) {
			$("#addOp" + (i + 1)).textbox("setValue",amf1Value.substring(i * 2, (i + 1) * 2));
		}
	}});

}

function autoOpcInput(allNumber) {
	for (var i = 1; i <= allNumber; i++) {
		$('#addOpc' + i).textbox({inputEvents : $.extend({},$.fn.textbox.defaults.inputEvents,{
			keyup : function(e) {
				var t = e.data.target.getAttribute("id").substring(3);
				if (this.value.length == 2) {
					if (parseInt(t) == ($("#addOpc input").length) / 3) {
						$('#addOpc'+ (parseInt(t))).textbox('textbox').blur();
					} else {
						$('#addOpc'+ (parseInt(t) + 1)).textbox('textbox').focus();
					}
				}
			}
		})});
	}
	$('#addOpc1').textbox({
		onChange : function(value) {
			var txts = $("#addOpc input");
			var amf1Value = $("#addOpc1").textbox("getValue");
			for (var i = 0; i < txts.length / 3; i++) {
				$("#addOpc" + (i + 1)).textbox("setValue",amf1Value.substring(i * 2, (i + 1) * 2));
			}
		}});
}
//==================鉴权参数====================

//开启业务按钮控制ospf是否可选
function addCheckBox(cb){
	var ospfzb = document.getElementById("addOspfzb");
	var ospfgb = document.getElementById("addOspfgb");
	if(cb.checked){
		ospfzb.disabled = "";
		ospfgb.disabled = "";
	}else{
		ospfzb.disabled = "disabled";
		ospfgb.disabled = "disabled";
	}
}
