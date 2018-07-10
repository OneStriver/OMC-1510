<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<title>静态网卡</title>
<%@ include file="/WEB-INF/jsp/public/common.jspf"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/public/grid.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/eth/staticEth.js"></script>
</head>
<body>
	<table id="dg" title="<spring:message code="StaticNetworkCardList"></spring:message>"
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
				<th data-options="field:'type',editor:'textbox'"><spring:message code="Type" /></th>
				<th data-options="field:'ip',editor:'textbox'">IP</th>
				<th data-options="field:'mask',editor:'textbox'"><spring:message code="Mask" /></th>
				<th data-options="field:'mac',editor:'textbox'"><spring:message code="HardwareAddress" /></th>
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
						}"><spring:message code="Update" />
					</th>
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