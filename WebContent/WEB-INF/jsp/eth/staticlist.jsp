<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<title>静态网卡</title>
<%@ include file="/WEB-INF/jsp/public/common.jspf"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/public/grid.js"></script>
<script type="text/javascript">
$(function() {
	$('#glym').css('display', 'none');
	<omc:permit url="omc/viewableDns">
		$('#glym').css('display', '');
	</omc:permit>

	$('#updateStaticEthName').textbox('textbox').css('background', '#ccc');
});

//=============方法开始======================

function submitFormwUpdate() {
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
			}
		});
	}
}

function clearFormwUpdate() {
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
function submitForm() {
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
function clearForm() {
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

</script>
</head>
<body>
	<table id="dg"
		title="<spring:message code="StaticNetworkCardList"></spring:message>"
		class="easyui-datagrid"
		data-options="
			url:'${pageContext.request.contextPath}/eth/listStatic.action',
			rownumbers:true,
			singleSelect:true,
			border:false,
			loadFilter:loadFilter,
			onClickCell:onClickCell,
			fit:true,striped:true,
			pageList: [10,20,30,40,50,60],
			pageNumber:${pageBean.page},
			pagination:true,
			toolbar:'#tb',
			pageSize: ${pageBean.pageSize}">
		<thead>
			<tr>
				<th data-options="field:'ck',checkbox:true"></th>
				<th data-options="field:'name',formatter:function(value,row,rowIndex){
						return '<b>'+value+'</b>';
					}"><spring:message code="Name" /></th>
				<th data-options="field:'type',editor:'textbox'"><spring:message
						code="Type" /></th>
				<th data-options="field:'ip',editor:'textbox'">IP</th>
				<th data-options="field:'mask',editor:'textbox'"><spring:message
						code="Mask" /></th>
				<th data-options="field:'mac',editor:'textbox'"><spring:message
						code="HardwareAddress" /></th>
				<th data-options="field:'mtu',editor:'numberbox'">MTU</th>
				<omc:permit url="eth/staticEthState">
					<th data-options="field:'state',editor:'textbox'">启动协议<spring:message code="Status"/></th>
				</omc:permit>
				<omc:permit url="eth/staticEthBroadcast">
					<th data-options="field:'broadcast',editor:'textbox'"><spring:message code="BroadcastAddress" /></th>
				</omc:permit>
				<th data-options="field:'destination',editor:'textbox',
					formatter:function(value,row,index){
                        if(value=='yes'){
                            return '是';
                          }
                         return '否';
                    }">是否开机启动</th>
                <!-- ospf操作 -->
				<th data-options="field:'ospf',editor:'textbox',
					formatter:function(value,row,index){
                        if(value=='true'){
                            return '开启';
                          }
                         return '关闭';
                    }">OSPF状态</th>
				<th data-options="field:'speed',editor:'textbox',hidden:true"><spring:message code="Rate" /></th>
				<!-- 对端地址 -->
				<th data-options="field:'duplex',editor:'textbox',hidden:true"><spring:message code="Full/Half-duplex" /></th>
				<th data-options="field:'autoNeg',editor:'textbox',hidden:true">自动协速</th>
				<!-- 板卡号 -->
				<th data-options="field:'cardNum'"><spring:message code="CardNumber" /></th>
				<omc:permit url="eth/staticUpdate">
					<th data-options="field:'edit',
						formatter:function(value,row,rowIndex){
							return '<a href=#><spring:message code="Update"/></a>';
						}"><spring:message code="Update" /></th>
				</omc:permit>
			</tr>
		</thead>
	</table>
	
	<div id="tb" style="height: auto">
	    <omc:permit url="eth/staticAdd">
			<a href="#" class="easyui-linkbutton"
				data-options="iconCls:'icon-add',plain:true" onclick="append()"><spring:message code="AddInterface" /></a> 
		</omc:permit>
		<omc:permit url="eth/delete">		
			<a href="#" class="easyui-linkbutton"
				data-options="iconCls:'icon-remove',plain:true"
				onclick="removeit('${pageContext.request.contextPath}/eth/delete.action')"><spring:message code="DeleteInterface" /></a>
		</omc:permit>		
		<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-reload',plain:true" 
			onclick="reload()">刷新</a>
	</div>
	
	<!-- 添加窗口 -->
	<div id="staticEthAddWindow" class="easyui-window" title="<spring:message code="AddStaticInterface"/>"
		data-options="minimizable:false,maximizable:false,collapsible:false,modal:true,closed:true,iconCls:'icon-add'"
		style="padding: 10px;">
		<form id="staticEthAddForm" class="easyui-form" method="post" action="${pageContext.request.contextPath}/eth/staticAdd.action">
			<table>
				<tr>
					<td><spring:message code="Name" />:</td>
					<td><input class="easyui-textbox" id="addStaticEthName" name="name"
						data-options="validType:'eth',required:true" /></td>
					<td width="56px"><span>IP:</span></td>
					<td><input class="easyui-textbox" id="addStaticEthIp" name="ip"
						data-options="validType:'ipABC',required:true" /></td>
				</tr>
				<tr>
					<td><spring:message code="Mask" />:</td>
					<td><input class="easyui-textbox" id="addStaticEthMask" name="mask"
						data-options="validType:'Mask',required:true" /></td>
					<td width="56px"><span>MTU:</span></td>
					<td><input class="easyui-numberbox" id="addStaticEthMtu" name="mtu"
						data-options="validType:'MTU',required:true" /></td>
				</tr>
				<tr>
					<td>所在板卡:</td>
					<td><input class="easyui-combobox" id="addStaticEthCardNum" name="cardNum"
						data-options="
								url:'${pageContext.request.contextPath}/card/listjsonarr.action',
								method:'get',
								editable:false,
								onLoadSuccess:onLoadSuccess,
								valueField:'cardNum',
								textField:'name',
								panelHeight:'100',
								required:true">
					</td>
					<omc:permit url="omc/viewableDns">
					<td>关联域名:</td>
					<td><input class="easyui-combobox" id="addStaticEthDns" name="dns"
						data-options="
							url:'${pageContext.request.contextPath}/hostaddr/listAllAxfr.action',
							method:'get',
							editable:false,
							valueField:'host',
							textField:'host',
							panelHeight:'100',
							multiple:true,
							required:false" />
					</td>
					</omc:permit>
				</tr>
				<tr style="display:none;">
					<td style="text-align:right"><input type="checkbox" id="addStaticEthOspf" name="ospf" value="true"/></td>
					<td><font style="font-size:14px">是否添加OSPF路由</font></td>
				</tr>
			</table>
		</form>
		<div style="text-align: center; padding: 5px">
			<a href="#" class="easyui-linkbutton" onclick="staticEthAddSubmitForm()"><spring:message code="SaveNetworkCard" /></a> 
		</div>
	</div>
	
	<!-- 更新窗口 -->
	<div id="staticEthUpdateWindow" class="easyui-window"
		title="<spring:message code="UpdateStaticInterface"/>"
		data-options="minimizable:false,maximizable:false,
        	collapsible:false,modal:true,closed:true,iconCls:'icon-add',width:435"
		style="padding: 10px;">
		<form id="staticEthUpdateForm" class="easyui-form" method="post"
			data-options="novalidate:true" action="${pageContext.request.contextPath}/eth/staticUpdate.action">
			<table>
				<tr>
					<td><spring:message code="Name" />:</td>
					<td><input class="easyui-textbox" id="updateStaticEthName" name="name" data-options="readonly:true,required:true" /></td>
					<td style="display:inline"><span>IP:</span></td>
					<td><input class="easyui-textbox" id="updateStaticEthIp" name="ip" data-options="validType:'ipABC',required:true"></input></td>
				</tr>
				<tr>
					<td><spring:message code="Mask" />:</td>
					<td><input class="easyui-textbox" id="updateStaticEthMask" name="mask"
						data-options="validType:'Mask',required:true" /></td>
					<td style="display:inline"><span>MTU:</span></td>
					<td><input class="easyui-numberbox" id="updateStaticEthMtu" name="mtu"
						data-options="validType:'MTU',required:true" /></input></td>
				</tr>
				<tr>
					<td>开机自启:</td>
					<td><input class="easyui-combobox" name="onBootorNo" value="是" data-options="
						    panelHeight:'auto',
						    width:136,
							valueField: 'value',
							textField: 'text',
							data: [{
								value: 'yes',
								text: '启动',
								selected:true
							},{
								value: 'no',
								text: '不启动'
							}]" /></td>
					<omc:permit url="omc/viewableDns">
					<td>关联域名:</td>
					<td><input id="updateStaticEthDns" name="dns"></td>
					</omc:permit>
				</tr>
				<tr>
					<td>所在板卡:</td>
					<td><input class="easyui-combobox" id="updateStaticEthCardNum" name="cardNum" data-options="
							hidden:true,
							url:'${pageContext.request.contextPath}/card/listjsonarr.action',
							method:'get',
							editable:false,
							valueField:'cardNum',
							textField:'name',
							panelHeight:'100',
							required:true
						"></td>
					<td style="text-align:right;display: none;"><input type="checkbox" id="updateStaticEthOspf" name="ospf" value="true"/></td>
					<td style="display: none;"><font style="font-size:14px">是否添加OSPF路由</font></td>
				</tr>
				<tr style="display: none">
					<td>MAC:</td>
					<td><input class="easyui-textbox" id="updateStaticEthMac" name="mac" /></input></td>
				</tr>
			</table>
		</form>
		<div style="text-align: center; padding: 5px">
			<a href="#" class="easyui-linkbutton" onclick="staticEthUpdateSubmitForm()"><spring:message code="SaveNetworkCard" /></a> 
		</div>
	</div>
</body>
</html>