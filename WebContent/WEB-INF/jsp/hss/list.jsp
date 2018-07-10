<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>终端用户列表</title>
<%@ include file="/WEB-INF/jsp/public/common.jspf"%>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/hss/list.css"/>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/hss/add.css"/>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/easyui/datagrid-detailview.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/hss/list.js"></script>
<style type="text/css">
.pagination .pagination-num{
	width:10em;
}
</style>
</head>
<body>
	<table id="dg" title='<spring:message code="TerminalUserList"/>' 
			class="easyui-datagrid" data-options="
			url:'${pageContext.request.contextPath}/hss/list.action',
			border:false,
			rownumbers:true,
			fit:true,
			striped:true,
			pageList: [10,20,50,100,150,200],
			pageNumber:${pageBean.page},
			singleSelect:false,
			pagination:true,
			toolbar: '#tableHead',
			onClickCell:onClickCell,
			onRowContextMenu:onRowContextMenu,
			pageSize: ${pageBean.pageSize},
			onDblClickRow:onDblClickRow,
			rowStyler:rowStyler">
		<thead>
		<tr>
			<th data-options="field:'ck',checkbox:true"></th>
			<c:if test="${IMSI==1}">
			<th data-options="field:'imsi',width:'12%'"><spring:message code="IMSI"/></th>
			</c:if>
			<c:if test="${MDN==1}">
			<th data-options="field:'mdn',width:'12%',editor:'textbox',
				formatter:function(value,row,rowIndex){
					return '<b>'+value+'</b>';
				}"><spring:message code="MDN"/></th>
			</c:if>
			<c:if test="${DeviceName==1}">
			<th data-options="field:'deviceName'"><spring:message code="DeviceName"/></th>
			</c:if>
			<c:if test="${SpeechCodec==1}">
			<th data-options="field:'vocodecName'"><spring:message code="SpeechCodec"/></th>
			</c:if>
			<c:if test="${MscAddr==1}">
			<th data-options="field:'mscAddr',width:150,
				formatter:function(value,row,rowIndex){
					return row.currloc.split('@')[0];
				}"><spring:message code="MscAddr"/></th>
			</c:if>
			<c:if test="${GateWayAddr==1}">
			<th data-options="field:'gwAddr',width:150,
				formatter:function(value,row,rowIndex){
					return row.currloc.split('@')[1];
				}"><spring:message code="GateWayAddr"/></th>
			</c:if>
			<c:if test="${bmNum==1}">
			<th data-options="field:'acount_num'"><spring:message code="bmNum"/></th>
			</c:if>
			<c:if test="${UeSwoon==1}">	
			<th data-options="field:'swoon',
				formatter:function(value,row,rowIndex){
					if(value==1) return '<font color=red>否</font>';
					else if (value==2) return '已遥晕';
					else if (value==3) return '恢复已发送';<!--恢复遥晕未确认  -->
					else if (value==4) return '<font color=green>已发送</font>';<!--遥晕未确认  -->
					else return '其他';
				}"><spring:message code="UeSwoon"/></th>
			</c:if>
			<th data-options="field:'swoonFlag',hidden:true"></th>
			<c:if test="${UeDestroy==1}">
			<th data-options="field:'destroy',
				formatter:function(value,row,rowIndex){
					if(value==1) return '<font color=red>否</font>';
					else if (value==2) return '已遥毙';
					else if (value==3) return '恢复已发送';<!--恢复遥毙未确认  -->
					else if (value==4) return '<font color=green>已发送</font>';<!--遥毙未确认  -->
					else return '其他';
				}"><spring:message code="UeDestroy"/></th>
			</c:if>
			<th data-options="field:'destroyFlag',hidden:true"></th>
			<c:if test="${AirMonitor==1}">	
			<th data-options="field:'airMonitor',
				formatter:function(value,row,rowIndex){
					if(value==1) return '<font color=green>关闭</font>';
					else if (value==2) return '<font color=red>开启</font>';
					else if (value==3) return '恢复已发送';<!--恢复对空监听开启未确认  -->
					else if (value==4) return '<font color=red>已发送</font>';<!--对空监听开启未确认  -->
					else return '其他';
				}"><spring:message code="AirMonitor"/></th>
			</c:if>
			<th data-options="field:'airMonitorFlag',hidden:true"></th>
			<c:if test="${LastUpdateTime==1}">
			<th data-options="field:'tstamp',width:150,
				formatter:function(value,row,rowIndex){
					if(typeof value==='number'){
						return new Date(value).format();
					}
					return value;
				}"><spring:message code="LastUpdateTime"/></th>
			</c:if>
			<th data-options="field:'currloc',hidden:true"></th>
			<th data-options="field:'status',
				formatter:function(value,row,rowIndex){
					return value==0?'<font color=red><spring:message code="Off-Line"/></font>':'<font color=green><spring:message code="On-Line"/></font>';
				}"><spring:message code="UserStatus"/></th>
			<c:if test="${Update==1}">
			<th data-options="field:'修改',
				formatter:function(value,row,rowIndex){
					return '<a href=#><spring:message code="Update"/></a>';
				}"><spring:message code="Update"/></th>
			</c:if>
		</tr>
	</thead>
	</table>
	
	<!-- 表格顶部按钮信息 -->
	<div id="tableHead" style="height:auto">
		<omc:permit url="hss/add">
			<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="append()"><spring:message code="Add"/></a>
		</omc:permit>
		<omc:permit url="hss/delete">
			<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="removeit()"><spring:message code="Delete"/></a>
		</omc:permit>
		<select class="easyui-combobox" name="deviceType"
			data-options="editable:false,onChange:dChange,width:110,panelHeight:'80'">
			<option value="" selected="selected"><spring:message code="AllDeviceType"/></option>
			<c:forEach items="${deviceTypes}" var="item">
				<option value="${item.key}">${item.value}</option>
			</c:forEach>
		</select>
		<select class="easyui-combobox" name="msvocodec" 
			data-options="editable:false,width:110,onChange:mChange,panelHeight:'80'">
			<option value="" selected="selected"><spring:message code="AllEncodeType"/></option>
			<c:forEach items="${msvocodecs}" var="item">
				<option value="${item.key}">${item.value}</option>
			</c:forEach>
		</select>
		<select class="easyui-combobox" name="status" 
			data-options="editable:false,width:85,onChange:sChange,panelHeight:'auto'">
			<option value="" selected="selected"><spring:message code="AllStatus"/></option>
			<option value="0"><spring:message code="Off-Line"/></option>
			<option value="1"><spring:message code="On-Line"/></option>
		</select>
		<select class="easyui-combobox" name="destroy" 
			data-options="editable:false,width:85,onChange:destroyChange,panelHeight:'auto'">
			<option value="0" selected="selected">遥毙状态</option>
			<option value="1">否</option>
			<option value="2">已遥毙</option>
		</select>
		<select class="easyui-combobox" name="airMonitor" 
			data-options="editable:false,width:110,onChange:airMonitorChange,panelHeight:'auto'">
			<option value="0" selected="selected">对空监听状态</option>
			<option value="1">关闭</option>
			<option value="2">开启</option>
		</select>
		<input id="ss" class="easyui-searchbox" style="width:200px" 
			data-options="validType:'integer',searcher:qq,prompt:'<spring:message code="PleaseEnterTheNumber"/>',menu:'#searchMenu'"/>
		<omc:permit url="hss/batchAdd">
			<a href="#" class="easyui-linkbutton" onclick="batchAppend()" ><spring:message code="ImportByNo"/></a>
		</omc:permit>
		<omc:permit url="hss/importSql">
			<a href="#" class="easyui-linkbutton" data-options="onClick:function(){$('#importHss').window('open');}"><spring:message code="ImportSql"/></a>
		</omc:permit>
		<omc:permit url="hss/exportSql">
			<a href="#" class="easyui-linkbutton" data-options="onClick:exportHss"><spring:message code="ExportSql"/></a>
		</omc:permit>
		<omc:permit url="hss/importExcel">
			<a href="#" class="easyui-linkbutton" data-options="onClick:function(){$('#importExcel').window('open');}">导入Excel</a>
		</omc:permit>
		<span hidden="true">
		<omc:permit url="hss/importXml">
			<a href="#" class="easyui-linkbutton" data-options="onClick:function(){$('#importXml').window('open');}">导入AUC-XML</a>
		</omc:permit> 
		</span>
		<omc:permit url="hss/batchUpdate">
		<a id="batchHssUpdateButton" href="#" class="easyui-linkbutton">批量修改</a>
		</omc:permit>
	</div>
	
	<!-- 搜索框中显示的数据-->
	<div id="searchMenu" style="width:120px"> 
	    <div data-options="name:'mdn'"><spring:message code="MDN"/></div> 
		<div data-options="name:'imsi'"><spring:message code="IMSI"/></div> 
	</div>
	
	<!-- 右键菜单 -->
	<div id="rightMenu" class="easyui-menu" data-options="width:120">   
    	<div id="readStatus" data-options="name:'readStatus'"><spring:message code="ReadUerStatus"/></div>
    	<div id="syncStatus" data-options="name:'syncStatus'"><spring:message code="PushUserData"/></div>
    	<div id="ueSwoon" data-options="name:'ueSwoon'"><spring:message code="UeSwoon"/></div>
    	<div id="ueNormal" data-options="name:'ueNormal'"><spring:message code="UeNormal"/></div>
    	<div id="ueDestroy" data-options="name:'ueDestroy'"><spring:message code="UeDestroy"/></div>
    	<div id="ueRestore" data-options="name:'ueRestore'"><spring:message code="UeRestore"/></div>
    	<div id="airMonitorOpen" data-options="name:'airMonitorOpen'"><spring:message code="AirMonitorOpen"/></div>
    	<div id="airMonitorClose" data-options="name:'airMonitorClose'"><spring:message code="AirMonitorClose"/></div>
    </div>
    
	<!-- (readStatus)读取用户状态对话窗口 -->
	<div id="rightMenuReadStatus" class="easyui-window" title="<spring:message code="UserStatus"/>" 
        data-options="iconCls:'icon-save',resizable:true,modal:false,closed:true,width:550">
        <table border="1" cellspacing="0" style="width:535px">
        	<tr>
	        	<td>
        			IMSI:<label title="UE-IMSI"></label> 
	        	</td>
	        	<td>
        			<spring:message code="MDN"/>:<label title="UE-MDN"></label> 
	        	</td>
        	</tr>
        	<tr>
        		<td>
        			<spring:message code="bmNum"/>:<label title="UE-BM"></label> 
        		</td>
        		<td hidden="true">
        			LAC:<label title="CS-RncLoc"></label>
        		</td>
        		<td>
        			<spring:message code="CurrentServiceVLR"/>:<label title="VLR"></label> 
        		</td>
        	</tr>
        	<tr>
        		<td>
        			<spring:message code="BaseServ"/>:<label title="CS-BaseServ"></label>
        		</td>
        		<td>
        			<spring:message code="SupplyServ"/>:<label title="CS-SupplyServ"></label>
        		</td>
        	</tr>
        	<tr  hidden="true">
        		<td>
        			<spring:message code="GeographicalLocation"/>:<label title="CS-GeoLo"></label> 
        			<spring:message code="CurrentServiceSGSN"/>:<label title="SGSN"></label>
        		</td>
        	</tr>
        	<tr>
        		<td><spring:message code="CSDomainTMSI"/>:<label title="CS-TMSI"></label></td>
        		<td><spring:message code="CSDomainStatus"/>:<label title="CS-Status"></label></td>
        	</tr>
        	<tr hidden="true">
        		<td><spring:message code="PSDomainTMSI"/>:<label title="PS-TMSI"></label></td>
        		<td><spring:message code="PSDomainStatus"/>:<label title="PS-Status"></label></td>
        	</tr>
        	<tr>
        		<td><spring:message code="UnconditionalForwardingNumber"/>:<label title="FwdUNC"></label></td>
        		<td><spring:message code="CallForwardingNumber"/>:<label title="FwdOnBusyNum"></label></td>
        	</tr>
        	<tr>
        		<td><spring:message code="NoResponseForwardingNumber"/>:<label title="FwdNoAnswer"></label></td>
        		<td><spring:message code="NoReachableForwardingNumber"/>:<label title="FwdNANum"></label></td>
        	</tr>
        </table>
	</div>
	
	<!-- 添加用户窗口 -->
	<div id="addHssWindow" class="easyui-window"
        data-options="
        	onLoad:loadAddScript,
        	fit:true,
        	left:0,
        	top:0,
        	collapsible:false,
        	minimizable:false,
        	maximizable:false,
        	resizable:false,
        	draggable:false,
        	iconCls:'icon-save',
        	modal:true,
        	title:'添加用户',
        	closed:true,
        	href:contextPath+'/hss/addUI.action',
        	footer:'#addHssFooter'
        ">
	</div>
	<div id="addHssFooter" style="border:0px;text-align:center;">
	   	<a href="javascript:void(0)" class="easyui-linkbutton" onclick="addHssSubmitForm()"><spring:message code="AddTerminalUser"/></a>
	</div>
	
	<!-- 修改用户窗口 -->
	<div id="updateWindow" class="easyui-window"
       data-options="
       	onLoad:loadUpdateScript,
       	fit:true,
       	left:0,
       	top:0,
       	collapsible:false,
       	minimizable:false,
       	maximizable:false,
       	resizable:false,
       	draggable:false,
       	iconCls:'icon-save',
       	modal:true,
       	title:'修改用户',
       	closed:true,
       	footer:'#updatefooter'
       ">
	</div>
	<div id="updatefooter" style="border:0px;text-align:center;">
	   	<a href="javascript:void(0)" class="easyui-linkbutton" onclick="submitUpdateForm()"><spring:message code="AddTerminalUser"/></a>
	</div>
	
	<!-- 批量添加窗口 -->
	<div id="batchAddWindow" class="easyui-window" style="text-align:center" 
        	data-options="
        	onLoad:loadBatchScript,
        	title:'<spring:message code="InsertBatch"/>',
        	height:500,
        	width:560,
        	top:50,
        	iconCls:'icon-save',
        	draggable:false,
        	resizable:false,
        	modal:true,
        	closed:true,       	
        	minimizable:false,
        	maximizable:false,
        	collapsible:false,
        	href:contextPath+'/hss/batchAddUI.action',
        	footer:'#batchAddHssFooter'
        ">
	</div>		
	<div id="batchAddHssFooter" style="text-align:center;padding:5px">
	   	<a href="javascript:void(0)" class="easyui-linkbutton" onclick="batchAddHssSubmitForm()"><spring:message code="InsertBatchTerminalUser"/></a> 
   	</div>
	
	<!-- ==============批量修改开始=============== -->
	<div id="batchHssUpdateWindow" class="easyui-window" style="text-align:center" 
			data-options="
			title:'批量修改',
			iconCls:'icon-save',
			draggable:true,
			modal:true,
			minimizable:false,
        	maximizable:false,
        	collapsible:false,
        	closed:true,
        	resizable:false,
        	height:480,
        	width:560,
        	top:50,
        	footer:'#batchUpdateFooter'
			">
    <form id="batchHssUpdateForm" method="post"  action="${pageContext.request.contextPath}/hss/batchHssUpdate.action" enctype="multipart/form-data">   
    <!-- 基本信息开始  -->
    <div id="batchHssUpdateBasicInfo" class="easyui-panel" data-options="title:'<spring:message code="BasicInformation"/>'">
    <div style="padding:10px 10px 10px 10px">
    	<table>
    		<tr>
    			<td><label><spring:message code="SpeechCodec"/>:</label></td>
    			<td>
    				<select class="easyui-combobox" id="batchHssUpdateMsvocodec" name="msvocodec" data-options="width:100,editable:false,required:true,panelHeight:'80'"> 
    					<c:forEach items="${msvocodecs}" var="item">
							<option value="${item.key}">${item.value}</option>
						</c:forEach>
    				</select>
    			</td>
    		</tr>
    	</table>
    </div>
	</div>
	<!-- 基本业务开始  -->
	<div id="batchHssUpdateBasicService" class="easyui-panel" data-options="title:'<spring:message code="BasicBusiness"/>'">
		<div style="padding:10px 10px 20px 10px">
        	<c:forEach items="${basicBusinesses}" var="business" varStatus="status">
        		<c:if test="${status.count%4==0}">
					<label><input type="checkbox" name="${business.name}" value="1" 
					<c:if test="${business.name=='shortMsg' || business.name=='secrecy' || business.name=='callerAllow' || business.name=='groupCallBusiness' || business.name=='circuitData' || business.name=='halfDuplexSingleCall' || business.name=='duplexSingleCall' || business.name=='NationalTraffic'}">
					checked
					</c:if> 
					/>
					<spring:message code="${business.i18n}"/>
					</label>&nbsp;&nbsp;&nbsp;<br/>
				</c:if>
				<c:if test="${status.count%4!=0}">
				<label><input type="checkbox" name="${business.name}" value="1" 
					<c:if test="${business.name=='shortMsg' || business.name=='secrecy' || business.name=='callerAllow' || business.name=='groupCallBusiness' || business.name=='circuitData' || business.name=='halfDuplexSingleCall' || business.name=='duplexSingleCall' || business.name=='NationalTraffic'}">
					checked
					</c:if> 
					/>
					<spring:message code="${business.i18n}"/>
				</label>&nbsp;&nbsp;&nbsp;
				</c:if>
			</c:forEach>
		</div>
	</div>
	<!-- 监听业务 开始 -->
	<c:if test="${jtyw==1}">
		<div id="batchHssUpdateMonitorService" class="easyui-panel" data-options="title:'<spring:message code="ListeningBusiness"/>'">
		<div style="padding:10px 20px 20px 20px">
			<label for="batchHssUpdateMonitorSwitch"><input id="batchHssUpdateMonitorSwitch" type="checkbox" name="monitorSwitch" value="0"/><spring:message code="OpenBusiness"/></label>
			<label><spring:message code="ListeningAddr"/>:</label><input id="batchHssUpdateMonitorIP" name="monitorIP" class="easyui-textbox" data-options="validType:'ip',disabled:true,required:true"/>
			<label><spring:message code="ListeningPort"/>:</label><input id="batchHssUpdateMonitorPort" name="monitorPort" class="easyui-numberbox" data-options="disabled:true,required:true,value:9654" style="width:140px;"/>
		</div>
		</div>
	</c:if>
	<!-- 监听业务 结束 -->
	
	<!-- 分组业务 开始 -->
	<span hidden="true">
	<c:if test="${fzye==1}">
	<div id="batchHssUpdateGroupService" class="easyui-panel" data-options="title:'<spring:message code="GroupingBusiness"/>'">
		<div style="padding:10px 60px 20px 60px">
		<!-- 开启分组业务 -->
		<label for="batchHssUpdateRegion"><input type="checkbox" id="batchHssUpdateRegion" type="checkbox" name="region" value="1" checked onclick="batchHssUpdateCheckBox(this);"/><spring:message code="OpenBusiness"/></label>
		<!-- 动态分配 -->
		<input type="radio" id="batchHssUpdateVoiceMailSwitch0" name="voiceMailSwitch" value="0" checked="checked" disabled="disabled"/>
		<label><spring:message code="DynamicGrouping"/></label>
		&nbsp;&nbsp;
		<!-- 静态分配 -->
		<input type="radio" id="batchHssUpdateVoiceMailSwitch1" name="voiceMailSwitch" value="1" disabled="disabled"/>
		<label><spring:message code="StaticGrouping"/></label>
		<label><spring:message code="IPAddress"/></label><input class="easyui-textbox" id="batchHssUpdateVoiceMailNum" name="voiceMailNum" data-options="validType:'ip',required:true,disabled:true"/>
		<br/>
		<label><input type="checkbox" id="batchHssUpdateOspfMulticast" name="ospfMulticast" value="1" checked="checked"/>OSPF组播</label>&nbsp;
		<label><input type="checkbox" id="batchHssUpdateOspfBroadcast" name="ospfBroadcast" value="1" checked="checked"/>OSPF广播</label>
		</div>
	</div>
	</c:if>
	</span>
	<!-- 调度业务 开始 -->
	<div id="batchHssUpdateScheduleService" class="easyui-panel" data-options="title:'<spring:message code="ScheduingBusiness"/>'">
		<div style="padding:10px 10px 10px 10px">
			<label><spring:message code="Priority"/>:</label>
			<select class="easyui-combobox" name="priority" data-options="editable:false,panelHeight:'100',width:143">
				<c:forEach items="${userPriority}" var="item">
					<option value="${item.priority}">${item.level}</option>
				</c:forEach>
			</select>
		</div>
	</div>
	<!-- 终端信息 开始 -->
	<span hidden="true">
	<c:if test="${zdxx==1}">
	<div id="batchHssUpdateTerminalInfo" class="easyui-panel" data-options="title:'<spring:message code="TerminalInformation"/>'">
		<div style="padding:10px 10px 10px 10px">
		<table>
	    	<tr>
	    		<td><label><spring:message code="TerminalNumber"/>:</label></td>
	    		<td><input class="easyui-textbox" name="terminalId" data-options="width:143,validType:'terminalLength'"/></td>
	    		<td><label><spring:message code="TerminalType"/>:</label></td>
	    		<td>
	    			<select class="easyui-combobox" name="terminalType" data-options="width:143,editable:false,panelHeight:'100'">
						<c:forEach items="${terminalTypes}" var="terminal">
							<option value="${terminal.terminalId }">${terminal.terminalName}</option>
						</c:forEach>
	    			</select>
	    		</td>
	    	</tr>
	    	<tr>
	    		<td><label><spring:message code="MaxPowerLevel"/>:</label></td>
	    		<td>
	    			<select class="easyui-combobox" name="powerLevel" data-options="width:143,editable:false,panelHeight:'100'">
	    				<c:forEach var="i" begin="1" end="10" step="1">
							<option value="${i}">${i}</option>
						</c:forEach>
	    			</select>
	    		</td>
	    		<td><label><spring:message code="GWId"/>:</label></td>
	    		<td><input class="easyui-numberbox" name="gwId" data-options="value:2" style="width:143px;"/></td>
	    	</tr>
	    	<tr>
	    		<td><label><spring:message code="Department"/>:</label></td>
	    		<td><input class="easyui-textbox" name="department" data-options="width:143,validType:'departmentLength'"/>
	    		</td>
	    		<td><label><spring:message code="UserType"/>:</label></td>
	    		<td>
	    			<select class="easyui-combobox" name="userType" id="batchUpdateUserType" data-options="width:143,editable:false,panelHeight:'auto'">
						<option value="1" selected="selected">军用用户</option>
	    			</select>
	    		</td>
	    	</tr>
	    	<tr>
	    		<td><span hidden="true"><label><spring:message code="UserName"/>:</label></span></td>
	    		<td><span hidden="true"><input class="easyui-textbox" name="username" data-options=""/></span></td>
	    	</tr>
	    	<tr>
	    		<td><span hidden="true"><label><spring:message code="UserInfo"/>:</label></span></td>
	    		<td colspan="3"><span hidden="true"><input class="easyui-textbox" name="userInfo" data-options="width:370"/></span></td>
	    	</tr>
	    	<tr>
	    		<td><span hidden="true"><label>服务优先级:</label></span></td>
	    		<td>
	    		<span hidden="true">
	    		<select class="easyui-combobox" name="servicePriority" id="batchUpdateServicePriority" data-options="editable:false,panelHeight:'100'">
						<option value="1">1</option>
						<option value="2">2</option>
						<option value="3">3</option>
						<option value="4">4</option>
						<option value="5" selected="selected">5</option>
	    		</select>
	    		</span>
	    		</td>
	    	</tr>
	    </table>
	    </div>
	</div>
	</c:if>
	</span>
	<!-- 终端信息 结束 -->
	<div>
		<!-- 提交前台需要批量修改的IMSI号码  -->
		<input type="hidden" id="hssflag" name="imsis"/>
	</div>
	<div id="batchUpdateFooter" style="text-align:center;padding:5px">
		<a href="#" class="easyui-linkbutton" onclick="batchHssUpdateSubmit()"><spring:message code="UpdateBatchTerminalUser"/></a>
    </div>
	</form>
	</div>
	<!-- ==============批量修改结束=============== -->
	
	<!-- 导入Excel开始 -->
	<div id="importExcel" class="easyui-window"
        	data-options="width:400,title:'<spring:message code="UserImport"/>',iconCls:'icon-save',modal:true,closed:true,resizable:false,minimizable:false,maximizable:false">
      	<form id="hssExcel" action="${pageContext.request.contextPath}/hss/importExcel.action" method="post" enctype="multipart/form-data">
      		<br/>
			<input class="easyui-filebox" name="file" data-options="onChange:function(){
				$(this).filebox('setValue',$(this).filebox('getValue').substring(12));
			},validType:'excelFile',width:330,buttonText:'选择Excel文件',required:true,prompt:'选择要导入的Excel文件'"/>
			<!-- 隐藏标志位 -->
			<input type="text" id="hid" name="hid" hidden="true">
			<input type="text" id="hidCall" name="hidCall" value="1" hidden="true">
			<br/><br/>
        	<div style="text-align:center">
        		<a href="${pageContext.servletContext.contextPath}/media/03_hx.xls" class="easyui-linkbutton">下载模板</a>
			</div>
			<br/>
			<div style="text-align:center;padding:5px;clear:both;">
			<a href="#" class="easyui-linkbutton" onclick="importExcel()"><spring:message code="ImportUser"/></a>
			</div>
      	</form>
    </div>
    
    <div id="importXml" class="easyui-window"
        	data-options="width:400,title:'<spring:message code="UserImport"/>',iconCls:'icon-save',modal:true,closed:true,resizable:false,minimizable:false,maximizable:false">
      	<form id="hssXml" action="${pageContext.request.contextPath}/hss/importXml.action" method="post" enctype="multipart/form-data">
      		<br/>
			<input class="easyui-filebox" name="file" data-options="onChange:function(){
				$(this).filebox('setValue',$(this).filebox('getValue').substring(12));
			},validType:'xmlFile',width:380,buttonText:'选择Xml文件',required:true,prompt:'选择要导入的Xml文件'"/>
			<br/><br/>
			<div style="text-align:center;padding:5px;clear:both;">
			<a href="#" class="easyui-linkbutton" onclick="importXml()"><spring:message code="ImportUser"/></a>
			</div>
      	</form>
    </div>

	<div id="importHss" class="easyui-window"
        	data-options="width:400,title:'<spring:message code="UserImport"/>',iconCls:'icon-save',modal:true,closed:true,resizable:false,collapsible:false,minimizable:false,maximizable:false">
      	<form id="hssF" action="${pageContext.request.contextPath}/hss/import.action" method="post" enctype="multipart/form-data">
      		<br/>
			<input class="easyui-filebox" name="file" data-options="onChange:function(){
				$(this).filebox('setValue',$(this).filebox('getValue').substring(12));
			},validType:'sqlFile',width:380,buttonText:'<spring:message code="SelectSqlFile"/>',required:true,prompt:'<spring:message code="PleaseSelectSqlFile"/>'"/>
			<br/><br/>
			<div style="text-align:center;padding:5px;clear:both;">
			<a href="#" class="easyui-linkbutton" onclick="importHss()"><spring:message code="ImportUser"/></a>
			</div>
      	</form>
    </div>
    
    <omc:permit url="hss/readStatus">
    <div id="mystatus">
		<div>IMSI:<label title="UE-IMSI"></label>                                                   </div>
		<div><spring:message code="MDN"/>:<label title="UE-MDN"></label>                            </div>
		<br/>
		<!-- <div><spring:message code="bmNum"/>:<label title="UE-BM"></label>                      </div> -->    
		<!--  <div>LAC:<label title="CS-RncLoc"></label>                                            </div>-->   
		<!-- <div><spring:message code="GeographicalLocation"/>:<label title="CS-GeoLo"></label>    </div> -->
		<div style="clear:left;color:red">CS域:</div>
		<div><spring:message code="CSDomainTMSI"/>:<label title="CS-TMSI"></label>                  </div>
		<div><spring:message code="CSDomainStatus"/>:<label title="CS-Status"></label>              </div>
		<div><spring:message code="CurrentServiceVLR"/>:<label title="VLR"></label>                 </div>
		<div><spring:message code="BaseServ"/>:<label title="CS-BaseServ"></label>               	</div>
		<div><spring:message code="SupplyServ"/>:<label title="CS-SupplyServ"></label>              </div>
		<div><spring:message code="UnconditionalForwardingNumber"/>:<label title="FwdUNC"></label>  </div>
		<div><spring:message code="CallForwardingNumber"/>:<label title="FwdOnBusyNum"></label>     </div>
		<div><spring:message code="NoResponseForwardingNumber"/>:<label title="FwdNoAnswer"></label></div>
		<div><spring:message code="NoReachableForwardingNumber"/>:<label title="FwdNANum"></label>  </div>

		<br/>
		<div style="clear:left;color:red" >PS域:<label title="psDomain"></label></div>
		<div><spring:message code="PSDomainStatus"/>:<label title="PS-Status"></label>              </div>
        <div><spring:message code="ESN"/>:<label title="ESN"></label> </div>
        <!-- 不可达前转号码 -->
        <!-- 不可达前转号码显示因为时间原因在CDMA项目中暂时屏蔽，计划显示方式为：在CS域下面再起一行显示四个转移状态，当开通该业务时显示对应的转移号码，不开通不显示 -->
		<!-- <div><spring:message code="NoReachableForwardingNumber"/>:<label title="FwdNANum"></label> </div> -->
        <!-- PS域TMSI -->
        <!-- PS-TMSI 在CDMA项目中不使用 -->
		<!-- <div><spring:message code="PSDomainTMSI"/>:<label title="PS-TMSI"></label>                  </div> -->
		<!-- 所有项目通用规则：当status为1，且AssignedIP不是0000时，前台显示激活，当AssignedIP为4个0时，显示不激活；当status不为1时，均显示不激活 -->
		<!-- <div><spring:message code="CurrentServiceSGSN"/>:<label title="SGSN"></label>  </div> -->
		<!-- 在CDMA项目中因为GGSN没有ps域注册，直接上行AAAAuthReq，然后BillingStart，HSS隐含注册，将设备状态更改为1，并将UE IP填写进内存的AssignedIP中，在固定IP时AssignedIP与FixedIP相同 -->
		<!-- 在CDMA项目中由于GGSN没有上报PCF地址，只上报了GGSN ID(GGSNip:port),故在CDMA项目中只显示一下内容：
		ESN，PS域状态，UE IP，GGSN ID和UE。基本业务：分组业务 -->
        <div id="AssignedIP"><spring:message code="AssignedIP"/>:<label title="AssignedIP"></label>              </div>
		<div id="GgsnAddr"><spring:message code="GgsnAddr"/>:<label title="GGSNADDR"></label>               </div>
		<div><spring:message code="BaseServ"/>:<label title="PS-BaseServ"></label>       </div> 
    </div>
    </omc:permit> 
</body>

</html>
