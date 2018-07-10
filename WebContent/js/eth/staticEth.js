$(function() {
	$('#updateStaticEthName').textbox('textbox').css('background', '#ccc');
});

//=============方法开始======================

function staticEthUpdateSubmitForm() {
	var rows = $('#dg').datagrid('getRows');
	var boo = true;
	$(rows).each(function(i){
		//IP是否重复
		var ip = rows[i].ip;
		var row = $('#dg').datagrid('getSelections');
		var name1 = rows[i].name;
		var name2 = $('#updateStaticEthName').textbox('getValue');
		if(name1!=name2){
			var updateIp = $('#updateStaticEthIp').textbox('getValue');
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
				$('#staticEthUpdateWindow ').window('close');
				$('#staticEthUpdateForm').form('submit', {
					success : function(data) {
						submitStaticEthSuccess(data);
					},
					onSubmit : function() {
						return $(this).form('enableValidation').form('validate');
					}
				});
				updateClearForm();
			}
		});
	}
}

function updateClearForm() {
	var name = $("#staticEthUpdateForm input[name=name]");
	var val = name.val();
	$('#staticEthUpdateForm').form('clear');
	$('#staticEthUpdateForm').form('load', {
		name : val
	});
}

function append() {
	$('#staticEthAddWindow').window('open');
}

//添加静态网卡
function staticEthAddSubmitForm() {
	var rows = $('#dg').datagrid('getRows');
	var addName = $('#addStaticEthName').textbox('getValue');//ethx:y
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
		var addIp = $('#addStaticEthIp').textbox('getValue');
		if(ip==addIp){
			boo3 = false;
		}
	});
	if(boo1){
		if(boo2){
			if(boo3){
				$.messager.confirm(LOCALE.Confirm, '确认添加网卡？', function(yes) {
					if (yes) {
						$('#staticEthAddWindow').window('close');
						$('#staticEthAddForm').form('submit', {
							success : function(data) {
								submitStaticEthSuccess(data);
							},
							onSubmit : function() {
								return $(this).form('enableValidation').form('validate');
							}
						});
						addClearForm(); 
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
function addClearForm() {
	$("#addStaticEthName").textbox('clear');
	$("#addStaticEthIp").textbox('clear');
	$("#addStaticEthMask").textbox('clear');
	$("#addStaticEthMtu").textbox('clear');
}

function reload() {
	$('#dg').datagrid('reload');
}

function onLoadSuccess() {
	var data = $(this).combobox('getData');
	$(this).combobox('select', data[0].cardNum);
	console.log(data);
}

function onClickCell(rowIndex, field, value) {
	if (field != '修改'){
		return;
	}
	var row = $('#dg').datagrid('selectRow', rowIndex).datagrid('getSelected');
	updateStaticEth(row);
}


var Alldata; 
var SelectData = new Array();
function updateStaticEth(row) {
	$('#staticEthUpdateForm').form('load', row);
	$.ajax({
		url : '${pageContext.request.contextPath}/hostaddr/listAllAxfr.action?eth='
				+ row.name + '&cardNum=' + row.cardNum,
		dataType : 'json',
		success : function(data) {
			Alldata = data;
			data.unshift({
				'key' : 'all',
				'host' : '全选'
			})
			data.unshift({
				'key' : 'exit',
				'host' : '取消'
			})
			$('#updateStaticEthDns').combobox({
				data : data,
				valueField : 'host',
				textField : 'host',
				editable : false,
				panelHeight : '100',
				multiple : true,
				required : false,
				width : '136',
				formatter : function(row) {
					if ('全选' == row.host || '取消' == row.host) {
						return '<div style="cursor:pointer;">'+ row.host+ '</div>';
					}else{
						return '<div style="cursor:pointer;">'+ row.host + '</div>';
					}
				},
				onSelect : function(row) {
					var length = Alldata.length;
					if ('all' == (row.key)) {
						$('#updateStaticEthDns').combobox('unselect',row.host);
						for (var i = 2; i < length; i++) {
							$('#updateStaticEthDns').combobox('select',Alldata[i].host);
							SelectData.push(Alldata[i]);
						}
					}
					if ('exit' == (row.key)) {
						$('#updateStaticEthDns').combobox('unselect',row.host);
						$.each(SelectData,function(i) {
							$('#updateStaticEthDns').combobox('unselect',SelectData[i].host);
						});

					}
				}
			})
		}
	});
	$('#staticEthUpdateWindow').window('open');
}

function removeit(url) {
	var rows = $('#dg').datagrid('getSelections');
	if (rows.length == 0) {
		$.messager.alert('提示', '请选择要删除的记录!');
		return;
	}
	$.messager.confirm(LOCALE.Confirm, LOCALE.DeleteMessage, function(yes) {
		if (yes) {
			var selected = $('#dg').datagrid('cancelEdit', editIndex)
					.datagrid('getSelections');
			editIndex = undefined;
			isInsert = undefined;
			if (selected.length == 0){
				return;
			}
			var columns = $('#dg').datagrid('cancelEdit', editIndex)
					.datagrid('getColumnFields');
			var ids = [];
			var cardNums = [];
			$.each(selected, function(i) {
				var id = selected[i][columns[1]];
				var cardNumStr = selected[i].cardNum;
				if (id) {
					ids.push(id);
					cardNums.push(cardNumStr);
				} else {
					$('#dg').datagrid('deleteRow',$('#dg').datagrid('getRows').length - 1);
				}
			});
			
			if (ids.length > 0) {
				$.ajax({
					type : 'post',
					traditional : true,
					data : {
						cardNums : cardNums,
						ids : ids
					},
					url : url,
					success : function(data) {
						submitStaticEthSuccess(data);
					}
				});
			}
		}
	});
}

function submitStaticEthSuccess(data){
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
			timeout:3000,
			style:{
				right:'',bottom:''
			}
		});
		if($('#dg')) $('#dg').datagrid('reload');
		return true;
	}else{
		alert('未预料消息：'+data);
		return false;
	}
}
//=============方法结束======================