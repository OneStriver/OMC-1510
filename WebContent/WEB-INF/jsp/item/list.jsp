<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>配置项列表</title>
<%@ include file="/WEB-INF/jsp/public/common.jspf"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/public/grid.js?<%=new Date().getTime()%>"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/item/list.js?<%=new Date().getTime()%>"></script>
</head>
<body>
	<table id="dg" class="easyui-datagrid" title="<spring:message code="ConfigItemList"/>"
		data-options="
			url:'${pageContext.request.contextPath}/item/list.action',
			rownumbers:true,
			frozenColumns:[[{field:'ck',checkbox:true},
				{field:'name',title:'<spring:message code="ConfigurationItemName"/>',sortable:true,order:'desc'}
				]],
			fit:true,striped:true,border:false,
			pageList: [10,20,30,40,50,60],
			pageNumber:${pageBean.page},
			pagination:true,
			toolbar: '#tb',
			loadFilter:loadFilter,
			onClickCell:onClickCell,
			pageSize: ${pageBean.pageSize}">
	<thead>
		<tr>
			<!-- <th data-options="field:'ck',checkbox:true"></th> -->
			<th data-options="field:'站位',hidden:true"></th>
			<th data-options="field:'id',hidden:true"></th>
			<!-- <th data-options="field:'name'">配置项名称</th> -->
			<th data-options="field:'parent.id',hidden:true,
				formatter:function(value,row,rowIndex){
						return row.parent&&row.parent.id;
					}"><spring:message code="SuperiorItems"/></th>
			<th data-options="field:'value',width:200"><spring:message code="DefaultValue"/></th>
			<th data-options="field:'description'"><spring:message code="ConfigurationItemDescription"/></th>
			<th data-options="field:'formtype',hidden:true,
				formatter:function(value,row,rowIndex){
						var coms={textbox:'文本框',numberbox:'数值框',ip:'IP框',email:'E-mail',
								url:'URL',combobox:'下拉列表',radio:'单选框',grid:'表格',hidden:'隐藏'};
						return coms[value];

					}"><spring:message code="FillControls"/></th>
			<th data-options="field:'max'"><spring:message code="MaxValue"/></th>
			<th data-options="field:'min'"><spring:message code="MinValue"/></th>
			<th data-options="field:'maxLen'"><spring:message code="MaxLength"/></th>
			<th data-options="field:'minLen'"><spring:message code="MinLength"/></th>
			<th data-options="field:'orderNum',sortable:true,order:'asc'"><spring:message code="SerialNumber"/></th>
			<th data-options="field:'multiline',
				formatter:function(value,row,rowIndex){
						return value?'<spring:message code="Yes"/>':'<spring:message code="No"/>';
					}"><spring:message code="MultipleLineConfiguration"/></th>
			<th data-options="field:'required',
				formatter:function(value,row,rowIndex){
							return value?'<spring:message code="Yes"></spring:message>':'<spring:message code="No"></spring:message>';
						}"><spring:message code="Required"/></th>
			<th data-options="field:'config.name',
				formatter:function(value,row,rowIndex){
					return row.config&&row.config.name;
				}"><spring:message code="ConfigFile"/></th>
			<th data-options="field:'relevance.name',
				formatter:function(value,row,rowIndex){
					return row.relevance&&row.relevance.name;
				}"><spring:message code="AssociatedConfiguration"/></th>
			<th data-options="field:'update',
				formatter:function(value,row,rowIndex){
					return '<a href=# ><spring:message code="Edit"/></a>';
				}"><spring:message code="Edit"/></th>
		</tr>
	</thead>
	</tbody>
	</table>
	<div id="tb" style="height:auto">
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="openWindow()"><spring:message code="Add"/></a>
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" 
			onclick="removeit('${pageContext.request.contextPath}/item/delete.action')"><spring:message code="Delete"/></a>
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save',plain:true" onclick="accept(
			'${pageContext.request.contextPath}/item/update.action',
				'${pageContext.request.contextPath}/item/save.action')"><spring:message code="save"/></a>
		<spring:message code="ConfigFile"/>:<input class="easyui-combobox" id="configId" name="configId"
				data-options="
						width:200,
						panelMaxHeight:400,
						editable:false,
						onSelect:onSelect,
						url:'${pageContext.request.contextPath}/config/listjsonarr.action?isAll=true',
						method:'post',
						valueField:'id',
						textField:'name',
						panelHeight:'auto'
				">
	</div>
	<!-- 添加窗口 -->
	<div id="aw" class="easyui-window" title="<spring:message code="AddConfigurationItem"/>" data-options="modal:true,closed:true,iconCls:'icon-save'" style="padding:10px;">
		<!-- data-options="novalidate:true" -->
		<form id="af" class="easyui-form" method="post" action="${pageContext.request.contextPath}/item/save.action">
			<table>
	    		<tr>
	    			<td><spring:message code="ConfigName"/>:</td>
	    			<td><input class="easyui-textbox" name="name" data-options="required:true"/></td>
	    			<td><spring:message code="DefaultConfig"/>:</td>
	    			<td><input class="easyui-textbox" name="value" id="avalue" data-options="required:true"/></td>
	    		</tr>
	    		<tr>
	    			<td><spring:message code="ConfigurationDescription"/>:</td>
	    			<td><input class="easyui-textbox" name="description" data-options="required:true"/></td>
	    			<td><spring:message code="FillControls"/>:</td>
	    			<td>
	    				<select class="easyui-combobox" name="formtype"
	    					data-options="required:true,editable:false,width:130,panelHeight:'auto',onSelect:aonSelect">
							<option value="textbox">文本框</option>
							<option value="numberbox">数值框</option>
							<option value="ip">IP框</option>
							<option value="email">E-mail</option>
							<option value="url">URL</option>
							<option value="combobox">下拉列表</option>
							<option value="radio">单选框</option>
							<option value="hidden">隐藏</option>
						</select>
					</td>
	    		</tr>
	    		<tr>
	    			<td><spring:message code="MaxValue"/>　:</td>
	    			<td><input class="easyui-numberbox" name="max"/></td>
	    			<td><spring:message code="MinValue"/>　:</td>
	    			<td><input class="easyui-numberbox" name="min"/></td>
	    		</tr>
	    		<tr>
	    			<td><spring:message code="MaxLength"/>:</td>
	    			<td><input class="easyui-numberbox" name="maxLen" data-options="value:9999,min:1,required:true"/></td>
	    			<td><spring:message code="MinLength"/>:</td>
	    			<td><input class="easyui-numberbox" name="minLen" data-options="value:1,min:1,required:true"/></td>
	    		</tr>
	    		<tr>
	    			<td><spring:message code="MultipleLineConfiguration"/>:</td>
	    			<td><input name="multiline" type="checkbox" value="true" onchange="aswitchMultiline(this)"/></td>
	    			<td><spring:message code="Required"/>　:</td>
	    			<td><input name="required" type="checkbox" value="true" checked onchange="switchRequired(this)"/></td>
	    		</tr>
	    		<tr>
		    		<td><spring:message code="ConfigFile"/>:</td>
	    			<td>
	    				<input class="easyui-combobox" name="config.id"
							data-options="
								panelMaxHeight:250,
								editable:false,
								method:'post',
								valueField:'id',
								textField:'name',
								panelHeight:'auto',
								required:true
						"/>
					</td>
	    			<td><spring:message code="AssociatedGroup"/>　:</td>
	    			<td>
	    				<input class="easyui-combobox" name="relevance.id"
							data-options="
								panelMaxHeight:250,
								editable:false,
								method:'post',
								valueField:'id',
								textField:'name',
								panelHeight:'auto'
						"/>
					</td>
	    		</tr>
	    		<tr>
					<td><spring:message code="ViewSerialNumber"/>:</td>
					<td><input name="orderNum" class="easyui-numberbox" data-options="max:9999,min:-9999"/></td>
					<td><spring:message code="SuperiorItems"/>:</td>
					<td><input name="parent.id" class="easyui-numberbox" data-options="max:9999,min:-9999"/></td>
	    		</tr>
	    	</table>
	    	<div style="text-align:center">
		    	<a href="#" class="easyui-linkbutton" onclick="addOption(this)" style="display:none"><spring:message code="AddOption"/></a>
		    	<a href="#" class="easyui-linkbutton" onclick="submitForm()"><spring:message code="save"/></a>
		    	<a href="#" class="easyui-linkbutton" onclick="clearForm()"><spring:message code="Clear"/></a>
	  		</div>
		</form>
	</div>
	<!-- 更新窗口 -->
	<div id="uw" class="easyui-window" title="<spring:message code="UpdateConfigurationItem"/>" data-options="modal:true,closed:true,iconCls:'icon-save'" style="padding:10px;">
		<form id="uf" class="easyui-form" method="post" data-options="novalidate:true" action="${pageContext.request.contextPath}/item/update.action">
			<input type="hidden" name="id"/>
			<table>
	    		<tr>
	    			<td><spring:message code="ConfigurationName"/>:</td>
	    			<td><input class="easyui-textbox" name="name" data-options="required:true,editable:false"/></td>
	    			<td><spring:message code="DefaultConfig"/>:</td>
	    			<td><input class="easyui-textbox" name="value" id="uvalue" data-options="required:true"/></td>
	    		</tr>
	    		<tr>
	    			<td><spring:message code="ConfigurationDescription"/>:</td>
	    			<td><input class="easyui-textbox" name="description" data-options="required:true"></input></td>
	    			<td><spring:message code="FillControls"/>:</td>
	    			<td>
	    				<select class="easyui-combobox" name="formtype" 
	    						data-options="required:true,editable:false,width:130,panelHeight:'auto',onSelect:uonSelect">
							<option value="textbox">文本框</option>
							<option value="numberbox">数值框</option>
							<option value="ip">IP框</option>
							<option value="email">E-mail</option>
							<option value="url">URL</option>
							<option value="combobox">下拉列表</option>
							<option value="radio">单选框</option>
							<option value="hidden">隐藏</option>
						</select>
					</td>
	    		</tr>
	    		<tr>
	    			<td><spring:message code="MaxValue"/>　:</td>
	    			<td><input class="easyui-numberbox" name="max"/></td>
	    			<td><spring:message code="MinValue"/>　:</td>
	    			<td><input class="easyui-numberbox" name="min"/></td>
	    		</tr>
	    		<tr>
	    			<td><spring:message code="MaxLength"/>:</td>
	    			<td><input class="easyui-numberbox" name="maxLen" data-options="value:9999,min:1,required:true"/></td>
	    			<td><spring:message code="MinLength"/>:</td>
	    			<td><input class="easyui-numberbox" name="minLen" data-options="value:1,min:1,required:true"/></td>
	    		</tr>
	    		<tr>
	    			<td><spring:message code="MultipleLineConfiguration"/>:</td>
	    			<td><input name="multiline" type="checkbox" value="true" onchange="uswitchMultiline(this)"/></td>
	    			<td><spring:message code="Required"/>　:</td>
	    			<td><input name="required" type="checkbox" value="true" onchange="switchRequired(this)"/></td>
	    			
	    		</tr>
	    		<tr>
	    			<td><spring:message code="ConfigFile"/>:</td>
	    			<td>
	    				<input class="easyui-combobox" name="config.id"
							data-options="
								panelMaxHeight:250,
								required:true,
								editable:false,
								method:'post',
								valueField:'id',
								textField:'name',
								panelHeight:'auto'
						"/>
					</td>
	    			<td><spring:message code="AssociatedGroup"/>　:</td>
	    			<td>
	    				<input class="easyui-combobox" name="relevance.id"
							data-options="
								panelMaxHeight:250,
								editable:false,
								method:'post',
								valueField:'id',
								textField:'name',
								panelHeight:'auto'
						"/>
					</td>
	    		</tr>
	    		<tr>
					<td><spring:message code="ViewSerialNumber"/>:</td>
					<td><input name="orderNum" class="easyui-numberbox" data-options="max:9999,min:-9999"/></td>
					<td><spring:message code="SuperiorItems"/>:</td>
					<td><input name="parent.id" class="easyui-numberbox" data-options="editable:false,max:9999,min:-9999"/></td>
	    		</tr>
	    	</table>
	    	<div style="text-align:center">
	    		<a href="#" class="easyui-linkbutton" onclick="addOption(this)" style="display:none"><spring:message code="AddOption"/></a>
		    	<a href="#" class="easyui-linkbutton" onclick="usubmitForm()"><spring:message code="save"/></a>
	  		</div>
		</form>
	</div>
</body>
</html>