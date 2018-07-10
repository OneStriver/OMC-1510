<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>执行网元管理</title>
<%@ include file="/WEB-INF/jsp/public/common.jspf"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/entity/entity.js?<%=new Date().getTime()%>"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/public/grid.js?<%=new Date().getTime()%>"></script>
</head>
<body>
	<table id="dg" class="easyui-datagrid" title="<spring:message code="SoftwareList"/>"
		data-options="
			url:'${pageContext.request.contextPath}/entity/list.action',
			cache:false,
			onRowContextMenu:onRowContextMenu,
			border:false,
			width:800,
			rownumbers:true,
			fit:true,
			striped:true,
			pagination:true,
			pageList: [10,30,50,80,100],
			pageNumber:${pageBean.page},
			toolbar: '#tb',
			loadFilter:loadFilter,
			onClickCell:onClickCell,
			pageSize: ${pageBean.pageSize}">
	<thead>
		<tr>
			<th data-options="field:'ck',checkbox:true"></th>
			<th data-options="field:'id',hidden:true"></th>
			<th data-options="field:'name',width:'10%'"><spring:message code="SoftwareName"/></th>
			<th data-options="field:'instId'"><spring:message code="EntityNumber"/></th>
			<th data-options="field:'description',width:'10%'"><spring:message code="Description"/></th>
			<th data-options="field:'status',width:'10%',
				formatter:function(value,row,rowIndex){
					if(value==1) return '<spring:message code="Started"/>';
					else if(value==0) return '<spring:message code="NotStarted"/>';
				}"><spring:message code="Status"/></th>
			<th data-options="field:'type',width:'10%',
				formatter:function(value,row,rowIndex){
					if(value==1) return '<spring:message code="Auto-start"/>';
					else if(value==0) return '<spring:message code="ManuallyStart"/>';
				}"><spring:message code="StartType"/></th>
			<th data-options="field:'module.name',width:'10%',
				formatter:function(value,row,rowIndex){
					return row.module&&row.module.name;
				}"><spring:message code="NEType"/></th>
			<th data-options="field:'c.name',width:'10%',sortable:false,order:'asc',
				formatter:function(value,row,rowIndex){
					return row.card&&row.card.name;
				}">所在板卡</th>
		</tr>
	</thead>
	</tbody>
	</table>
	<div id="tb" style="height:auto">
		<omc:permit url="entity/save">
			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="$('#addEntityWindow').window('open')"><spring:message code="Add"/></a>
		</omc:permit>
		<omc:permit url="entity/delete">
			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" 
				onclick="removeit('${pageContext.request.contextPath}/entity/delete.action')"><spring:message code="Delete"/></a></omc:permit>
		<omc:permit url="entity/listLog">
			<a class="easyui-splitbutton" data-options="menu:'#logdownload',iconCls:'icon-undo',plain:true">
				<span onclick="exportAllLog()">导出全部日志</span>
			</a>
			<div id="logdownload">   
			    <div onclick="exportSelectedLog()">导出选中日志</div>
			</div>　
		</omc:permit>
		<omc:permit url="entity/start">
			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-ok',plain:true" onclick="start()">启动</a>
		</omc:permit>
		<omc:permit url="entity/restart">
			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-ok',plain:true" onclick="restart()">重启</a>
		</omc:permit>
		<omc:permit url="entity/stop">
			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" onclick="stop()">停止</a>
		</omc:permit>
		<omc:permit url="entity/startup">
			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-lock',plain:true" onclick="startup()">开机自启</a>
		</omc:permit>
		<omc:permit url="entity/shutdown">
			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-clear',plain:true" onclick="shutdown()">禁止开机启动</a>
		</omc:permit>
	</div>
	
	<!-- 添加网元 -->
	<div id="addEntityWindow" class="easyui-window" title="<spring:message code="AddSoftware"/>" 
		data-options="collapsible:false,minimizable:false,maximizable:false,modal:true,closed:true,iconCls:'icon-save'" 
		style="height:150px;padding:10px;">
		<form id="addEntityForm" class="easyui-form" method="post" data-options="novalidate:false,ajax:false" action="${pageContext.request.contextPath}/entity/addUI.action">
			<table>
	    		<tr>
	    			<td><spring:message code="SoftwareName"/>:</td>
	    			<td><input class="easyui-textbox" name="name" data-options="required:true"/></td>
	    			<td>网元实例ID:</td>
	    			<td><input class="easyui-numberbox" name="instId" data-options="validType:'instIdValue',precision:0,prompt:'请输入网元实例ID'"/>(选填)</td>
	    		</tr>
	    		<tr></tr>
	    		<tr>
	    			<td><spring:message code="SelecteNE"/>:</td>
	    			<td>
	    				<input class="easyui-combobox" name="module.id"
							data-options="
								editable:false,
								url:'${pageContext.request.contextPath}/module/listjsonarr.action',
								method:'post',
								valueField:'id',
								panelMaxHeight:250,
								textField:'name',
								panelHeight:'auto',
								required:true
							"/>
	    			</td>
	    			<td><spring:message code="SelectCard"/>:</td>
	    			<td>
	    				<input class="easyui-combobox" name="card.cardNum"
							data-options="
								editable:false,
								url:'${pageContext.request.contextPath}/card/listjsonarr.action',
								method:'post',
								valueField:'cardNum',
								textField:'name',
								panelHeight:'auto',
								required:true
							"/>
					</td>
	    		</tr>
	    	</table>
	    	<div style="text-align:center">
		    	<a href="#" class="easyui-linkbutton" onclick="submitForm()"><spring:message code="NextStep"/></a>
	  		</div>
		</form>
	</div>
	<!-- 查看配置文件 -->
	<div id="view" class="easyui-window" title="<spring:message code="ConfigFile"/>" data-options="modal:true,closed:true,iconCls:'icon-save'"></div>
	
	<!-- 更新软件 -->
	<div id="updateEntityWindow" class="easyui-window" title="<spring:message code="UpgradeExecutableFile"/>" data-options="modal:true,closed:true,iconCls:'icon-edit'" 
			style="text-align:center;width:300px;height:200px;padding:10px;">
		<form id="updateEntityForm" method="post" enctype="multipart/form-data" action="${pageContext.request.contextPath}/entity/updateEntity.action">
			<input type="hidden" name="id"/>
			<input type="hidden" name="instId"/>
			<input type="hidden" name="moduleId"/>
			<label><spring:message code="ZIPFormat"/></label><br/><br/>
			<input class="easyui-filebox" name="file" data-options="buttonText:'<spring:message code="SelectExecutableFilePackage"/>',required:true,prompt:'<spring:message code="SelectUploadZIPPackage"/>'" style="width:100%"/>
			<br/><br/>
			<a href="#" class="easyui-linkbutton" onclick="updateEntity()"><spring:message code="UploadExecutableFile"/></a>
		</form>
	</div>
	
	<!-- 文件管理器 -->
	<div id="entityFilesWindow" class="easyui-window" 
		data-options="modal:true,width:800,height:400,title:'<spring:message code="FileManager"/>',
			maximized:true,minimizable:false,draggable:true,closed:true,iconCls:'icon-ok'">
		<iframe style="width:100%;height:100%;border:0;scrolling:auto" id="content"></iframe>
	</div>
	<!-- 右键菜单 -->
	<div id="entityRightMenu" class="easyui-menu" data-options="onClick:menuHandler" style="width:120px;">
		<omc:permit url="entity/delete"><div data-options="name:'delete',iconCls:'icon-ok'">删    除</div></omc:permit>
		<omc:permit url="entity/updateUI"><div data-options="name:'update',iconCls:'icon-ok'">修改配置文件</div></omc:permit>
		<omc:permit url="entity/start"><div data-options="name:'start',iconCls:'icon-ok'">启    动</div></omc:permit>
		<omc:permit url="entity/restart"><div data-options="name:'restart',iconCls:'icon-ok'">重    启</div></omc:permit>
		<omc:permit url="entity/stop"><div data-options="name:'stop',iconCls:'icon-ok'">停    止</div></omc:permit>
		<omc:permit url="entity/listLog"><div data-options="name:'openLogFile',iconCls:'icon-ok'">查看此网元日志</div></omc:permit>
		<omc:permit url="entity/startup"><div data-options="name:'startup',iconCls:'icon-ok'">开机自启动</div></omc:permit>
		<omc:permit url="entity/shutdown"><div data-options="name:'shutdown',iconCls:'icon-ok'">禁止开机自启</div></omc:permit>
	</div>
	<div id="dd" class="easyui-dialog" style="padding:10px;"
			data-options="iconCls:'icon-save',resizable:true,modal:true,closed:true,onBeforeClose:onBeforeClose">   
		<div id="p" style="width:300px;"></div> 
		<div id="pContent"></div>
	</div> 
	
</body>
</html>