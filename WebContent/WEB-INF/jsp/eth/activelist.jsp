<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<title>活动网卡</title>
<%@ include file="/WEB-INF/jsp/public/common.jspf"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/public/grid.js"></script>
<script type="text/javascript">

//===============方法开始==========================
function submitFormwUpdate(){
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
				$('#ffUpdate').form('submit',{
					success:function(data){
						submitSuccess(data);
						$('#wUpdate').window('close');
					},
					onSubmit:function(){
						return $(this).form('enableValidation').form('validate');
					}
				});
			}
		});
	}
	
}

function clearFormwUpdate(){
	var name=$("#ffUpdate input[name=name]");
	var val=name.val();
	$('#ffUpdate').form('clear');
	$('#ffUpdate').form('load',{name:val});
}

function appendActiveEth(){
	$('#wAdd').window('open');
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
					submitSuccess(data);
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

function etid(row){
	$('#ffUpdate').form('load',row);
	$('#wUpdate').window('open');
}

function submitForm(){
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
						$('#ff').form('submit',{
							success:function(data){
								submitSuccess(data);
								$('#wAdd').window('close');
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
	//$('#ff').form('clear');
	$("#addActiveEthName").textbox('clear');
	$("#addActiveEthIp").textbox('clear');
	$("#addActiveEthMask").textbox('clear');
	$("#addActiveEthMtu").numberbox('clear');
}

function onClickCell(rowIndex,field,value){
	if(field!='edit') return;
	var row=$('#dg').datagrid('selectRow',rowIndex).datagrid('getSelected');
	if(row.name=='bond0'||row.type=='Unspec'){
       return ;
    }
	etid(row);
}

function onLoadSuccess(){
	var data=$(this).combobox('getData');
	$(this).combobox('select',data[0].cardNum);
	console.log(data);
}
function reload(){
	$('#dg').datagrid('reload'); 
}
$(function(){
	$('#ff').form({
		success:submitSuccess
	});
	$('#ffUpdate').form({
		success:submitSuccess
	});
	
	$('#updateActiveEthName').textbox('textbox').css('background','#ccc');
});
//===============方法结束==========================
	
</script>
</head>
<body>

	<table id="dg" title="<spring:message code="ActiveNetworkCardList"/>" class="easyui-datagrid" data-options="
			url:'${pageContext.request.contextPath}/eth/listActivate.action',
			rownumbers:true,
			singleSelect:true,
			loadFilter:loadFilter,
			onClickCell:onClickCell,
			border:false,
			fit:true,
			striped:true,
			pageList: [10,20,30,40,50,60],
			pageNumber:${pageBean.page},
			pagination:true,
			toolbar:'#tb',
			pageSize: ${pageBean.pageSize},
			rowStyler: function(index,row){
			if(row.name =='bond0'||row.type=='Unspec'){
				return 'background-color:#D8D8D8;';
			}
		}">
		<thead>
		<tr>
			<th data-options="field:'ck',checkbox:true"></th>
			<th data-options="field:'name',formatter:function(value,row,rowIndex){
						return '<b>'+value+'</b>';
					}"><spring:message code="Name"/></th>
			<th data-options="field:'type',editor:'textbox'"><spring:message code="Type"/></th>
			<th data-options="field:'ip',editor:'textbox'">IP</th>
			<th data-options="field:'mask',editor:'textbox'"><spring:message code="Mask"/></th>
			<th data-options="field:'mac',editor:'textbox'"><spring:message code="HardwareAddress"/></th>
			<th data-options="field:'mtu',editor:'numberbox'">MTU</th>
			<th data-options="field:'state',editor:'textbox',formatter:function(value,row,index){
			  if(value == 'up'){
			    return '已激活';
			  }
			  if(value == 'down'){
			    return '未激活';
			  }
			  if(value == 'running'){
			    return '已连接';
			  }
			}"><spring:message code="Status"/></th>
			<th data-options="field:'broadcast',editor:'textbox'"><spring:message code="BroadcastAddress"/></th>
			<th data-options="field:'destination',editor:'textbox'"><spring:message code="RemoteAddress"/></th>
			<th data-options="field:'speed',editor:'textbox',formatter:function(value,row,index){
                           if(row.type=='Unspec'){
                                  return '';
                            }
                            return value;
                         }"><spring:message code="Rate"/></th>
			<!-- 
			<th data-options="field:'duplex',editor:'textbox'"><spring:message code="Full/Half-duplex"/></th> 
			-->
			<th data-options="field:'autoNeg',editor:'textbox',formatter:function(value,row,index){
			  if(value == 'off'){
			    return '关';
			  }
			  if(value == 'no'){
			    return '开';
			  }
			  if(row.name =='bond0'){
				return '';
		      }
			}">自动协速</th>
			<th data-options="field:'cardNum'"><spring:message code="CardNumber"/></th>
			<omc:permit url="eth/activateUpdate">
				<th data-options="field:'edit',
					formatter:function(value,row,rowIndex){
						if(row.name =='bond0'||row.type=='Unspec'){
							return '';
						}else{
						    return '<a href=# class=easyui-linkbutton>修改</a>';
						}
	                }"><spring:message code="Edit"/>
	             </th>
             </omc:permit>
		</tr>
		</thead>
	</table>
	
	<div id="tb" style="height:auto">
	    <omc:permit url="eth/activateAdd">
		<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" 
			onclick="appendActiveEth()"><spring:message code="AddInterface"/></a>
		</omc:permit>
		<%-- 
		<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" 
			onclick="removeit('${pageContext.request.contextPath}/eth/delete.action')">删除接口</a> 
		--%>
		<omc:permit url="eth/active">
		<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" 
			onclick="activate('${pageContext.request.contextPath}/eth/activate.action')"><spring:message code="Activate"/></a>
		</omc:permit>
		<omc:permit url="eth/goActive">
		<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" 
			onclick="deactivate('${pageContext.request.contextPath}/eth/deactivate.action')"><spring:message code="Deactivate"/></a>
		</omc:permit>
		<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-reload',plain:true" 
			onclick="reload()">刷新</a>
	</div>
	
	<!-- 添加窗口  -->	
	<div id="wAdd" class="easyui-window" title="<spring:message code="AddActiveInterface"/>" data-options="minimizable:false,maximizable:false,
        	collapsible:false,modal:true,closed:true,iconCls:'icon-add',width:415" style="padding:10px;">
		<!--  data-options="novalidate:true" -->
		<form id="ff" class="easyui-form" method="post" action="${pageContext.request.contextPath}/eth/activateAdd.action">
	    	<table>
	    		<tr>
	    			<td><spring:message code="Name"></spring:message>:</td>
	    			<td><input class="easyui-textbox" name="name" id="addActiveEthName" data-options="validType:'activeEth',required:true"/></td>
	    			<td>IP:</td>
	    			<td><input class="easyui-textbox" name="ip" id="addActiveEthIp" data-options="validType:'ipABC',required:true"/></td>
	    		</tr>
	    		<tr>
	    			<td><spring:message code="Mask"></spring:message>:</td>
	    			<td><input class="easyui-textbox" name="mask" id="addActiveEthMask" data-options="validType:'Mask',required:true"/></td>
	    			<td>MTU:</td>
	    			<td><input class="easyui-numberbox" name="mtu" id="addActiveEthMtu" data-options="validType:'MTU',required:true"/></td>
	    		</tr>
	    		<tr>
	    			<td><spring:message code="Card"></spring:message>:</td>
	    			<td><input class="easyui-combobox" name="cardNum"
						data-options="
							url:'${pageContext.request.contextPath}/card/listjsonarr.action',
							method:'get',
							onLoadSuccess:onLoadSuccess,
							editable:false,
							valueField:'cardNum',
							textField:'name',
							panelHeight:'auto',
							required:true
						"/></td>
	    		</tr>
	    	</table>
	    </form>
	    <div style="text-align:center;padding:5px">
	    	<a href="#" class="easyui-linkbutton" onclick="submitForm()"><spring:message code="SaveNetworkCard"/></a>
	    	<!-- 
	    	<a href="#" class="easyui-linkbutton" onclick="clearForm()"><spring:message code="ClearRecord"/></a>
	    	 -->
	    </div>
	</div>
	
	<!-- 修改窗口 -->
	<div id="wUpdate" class="easyui-window" title="<spring:message code="UpdateActiveInterface"/>" data-options="minimizable:false,maximizable:false,
        	collapsible:false,modal:true,closed:true,iconCls:'icon-add',width:415" style="padding:10px;">
		<form id="ffUpdate" class="easyui-form" method="post" data-options="novalidate:true" action="${pageContext.request.contextPath}/eth/activateUpdate.action">
	    	<table>
	    		<tr>
	    			<td><spring:message code="Name"/>:</td>

	    			<td><input class="easyui-textbox" name="name" id="updateActiveEthName"  data-options="readonly:true,required:true"/></td>

	    			<td>IP:</td>
	    			<td><input class="easyui-textbox" name="ip" id="updateActiveEthIp" data-options="validType:'ipABC',required:true"></input></td>
	    		</tr>
	    		<tr>
	    			<td><spring:message code="Mask"/>:</td>
	    			<td><input class="easyui-textbox" name="mask" id="updateActiveEthMask" data-options="validType:'Mask',required:true"></input></td>
	    			<td>MTU:</td>
	    			<td><input class="easyui-numberbox" name="mtu" data-options="validType:'MTU',required:true"></input></td>
	    		</tr>
	    		<tr>
	    			<td>所在板卡:</td>
	    			<td><input class="easyui-combobox" 
						name="cardNum"
						data-options="
							hidden:true,
							url:'${pageContext.request.contextPath}/card/listjsonarr.action',
							method:'get',
							valueField:'cardNum',
							textField:'name',
							panelHeight:'auto',
							required:true
						"></td>
	    		</tr>
	    	</table>
	    </form>
	    <div style="text-align:center;padding:5px">
	    	<a href="#" class="easyui-linkbutton" onclick="submitFormwUpdate()"><spring:message code="SaveNetworkCard"/></a>
	    	<!-- 
	    	<a href="#" class="easyui-linkbutton" onclick="clearFormwUpdate()"><spring:message code="ClearRecord"/></a>
	    	 -->
	    </div>
	</div>
	
</body>
</html>