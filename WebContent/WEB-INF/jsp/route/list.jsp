<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<title>路由管理</title>
<%@ include file="/WEB-INF/jsp/public/common.jspf"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/public/grid.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/route/route.js"></script>
</head>
<body>
	<table id="dg" title="<spring:message code="RouteList"></spring:message>" class="easyui-datagrid" data-options="
			url:'${pageContext.request.contextPath}/route/list.action',
			rownumbers:true,
			singleSelect:true,
			loadFilter:loadFilter,
			fit:true,
			striped:true,
			border:false,
			pageList: [10,20,30,40,50,60],
			pageNumber:${pageBean.page},
			pagination:true,
			toolbar: '#tb',
			pageSize: ${pageBean.pageSize}">
		<thead>
		<tr>
			<th data-options="field:'ck',checkbox:true"></th>
			<th data-options="field:'destination',width:'20%',
				formatter:function(value,row,rowIndex){
						return '<b>'+value+'</b>';
					}"><spring:message code="DestinationAddress"></spring:message></th>
			<th data-options="field:'gateway',editor:'textbox',width:'20%'"><spring:message code="Gateway"></spring:message></th>
			<th data-options="field:'genmask',editor:'textbox',width:'20%'"><spring:message code="Mask"></spring:message></th>
			<th data-options="field:'flags',editor:'textbox'">Flags</th>
			<th data-options="field:'metric',editor:'textbox'">Metric</th>
			<th data-options="field:'ref',editor:'numberbox'">Ref</th>
			<th data-options="field:'use',editor:'textbox'">Use</th>
			<th data-options="field:'iface',editor:'textbox'"><spring:message code="Interface"></spring:message></th>
			<th data-options="field:'cardNum'"><spring:message code="CardNumber"></spring:message></th>
			<!--  
			<th data-options="field:'cardNum',editor:{
					type:'combobox',
					url:'${pageContext.request.contextPath}/card/listjsonarr.action',
					valueField:'cardNum',
					textField:'name',
					panelHeight:'auto',
					required:true
				}">板卡编号</th>
			-->
		</tr>
		</thead>
	</table>
	<div id="tb" style="height:auto">
		<omc:permit url="route/addRoute"> 
			<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" 
				onclick="appendRoute()"><spring:message code="AddRoute"/></a>
		</omc:permit>	
		<omc:permit url="route/defaultRoute"> 
			<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" 
				onclick="appendDefaultGateWay()">添加默认网关</a>
		</omc:permit>
		<omc:permit url="route/deleteRoute"> 	
			<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" 
				onclick="removeit('${pageContext.request.contextPath}/route/delete.action')"><spring:message code="DeleteRoute"/></a>
		</omc:permit>
		<omc:permit url="route/ospf">
			<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" 
				onclick="openOspf('${pageContext.request.contextPath}/eth/activate.action')"><spring:message code="OSPFOperation"/></a>
	     </omc:permit>
	</div>
	
	<!-- 添加窗口 -->
	<div id="addRouteWindow" class="easyui-window" title="<spring:message code="AddRoute"/>" 
		data-options="modal:true,closed:true,iconCls:'icon-add'" style="padding:10px;">
		<form id="addRouteForm" class="easyui-form" method="post" action="${pageContext.request.contextPath}/route/save.action">
	    	<table>
	    		<tr>
	    			<td><spring:message code="DestinationAddress"/>:</td>
	    			<td><input class="easyui-textbox" name="destination" data-options="validType:'ip',required:true"/></td>
	    		</tr>
	    		<tr>
	    			<td><spring:message code="Mask"/>:</td>
	    			<td><input class="easyui-textbox" name="genmask" data-options="validType:'Mask',required:true"/></td>
	    		</tr>
	    		<tr>
	    			<td><spring:message code="Gateway"/>:</td>
	    			<td><input class="easyui-textbox" name="gateway" data-options="validType:'ip',required:true"/></td>
	    		</tr>
	    		<tr>
	    			<td>所在板卡:</td>
	    			<td><input class="easyui-combobox" name="cardNum"
						data-options="
							url:'${pageContext.request.contextPath}/card/listjsonarr.action',
							method:'get',
							onLoadSuccess:onLoadSuccess,
							valueField:'cardNum',
							textField:'name',
							panelHeight:'auto',
							required:true
						"></td>
	    		</tr>
	    	</table>
	    </form>
	    <div style="text-align:center;padding:5px">
	    	<a href="#" class="easyui-linkbutton" onclick="addRouteSubmitForm()"><spring:message code="SaveRoute"/></a>
	    </div>
	</div>
	
	<div id="addDefaultGateWayWindow" class="easyui-window" title="添加默认网关" 
		data-options="modal:true,closed:true,iconCls:'icon-add'" style="padding:10px;">
		<form id="addDefaultGateWayForm" class="easyui-form" method="post" data-options="novalidate:false" action="${pageContext.request.contextPath}/route/saveDefault.action">
	    	<table>
	    		  <tr style="display: none">
	    			<td><spring:message code="DestinationAddress"/>:</td>
	    			<td><input class="easyui-textbox" name="destination" value="0.0.0.0"/></td>
	    		</tr>
	    		<tr style="display: none">
	    			<td><spring:message code="Mask"/>:</td>
	    			<td><input class="easyui-textbox" name="genmask"  value="0.0.0.0"/></td>
	    		</tr>
	    		  
	    		<tr>
	    			<td>默认网关:</td>
	    			<td><input class="easyui-textbox" name="gateway" data-options="validType:'ip',required:true"/></td>
	    		</tr>
	    		<tr>
	    			<td>所在板卡:</td>
	    			<td><input class="easyui-combobox" name="cardNum"
						data-options="
							url:'${pageContext.request.contextPath}/card/listjsonarr.action',
							method:'get',
							onLoadSuccess:onLoadSuccess,
							valueField:'cardNum',
							textField:'name',
							panelHeight:'auto',
							required:true
						"></td>
	    		</tr>
	    	</table>
	    </form>
	    <div style="text-align:center;padding:5px">
	    	<a href="#" class="easyui-linkbutton" onclick="addDefaultGateWaySubmitForm()"><spring:message code="SaveRoute"/></a>
	    </div>
	</div>
	
	<div id="ospfWindow" class="easyui-window" title="<spring:message code="OSPFOperation"/>" 
		data-options="footer:'#footer',modal:true,closed:true,iconCls:'icon-add'" style="width:600px;height:400px;">
		<pre id="content" style="height:339px;"></pre>
	</div>
	<div id="footer">
		<input id="cmd" style="width:586px" disabled="disabled"/>
	</div>
	
</body>
</html>