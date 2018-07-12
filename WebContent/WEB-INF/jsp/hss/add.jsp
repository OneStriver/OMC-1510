<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>添加终端用户</title>
<%@ include file="/WEB-INF/jsp/public/common.jspf"%>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/hss/add.css"/>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/hss/hss.js"></script>
</head>
<body style="margin:auto">
	<form id="addHssForm" class="easyui-form" method="post" action="${pageContext.request.contextPath}/hss/add.action">
	
	<!-- 基本信息 开始 -->
	<div id="addBasicInfo" class="easyui-panel" data-options="title:'<spring:message code="BasicInformation"/>'">
		<div style="padding:10px 60px 20px 60px">
	    	<table>
	    		<tr>
	    			<td><label><spring:message code="MDN"/>(MDN):</label></td>
	    			<td width="310px"><input class="easyui-numberbox" id="addMdn" name="mdn" 
	    				data-options="validType:'mdnMax',min:0,required:true"/></td>
	    			<td><label><spring:message code="DeviceType"/>:</label></td>
	    			<td>
	    				<select class="easyui-combobox" id="addDeviceType" name="deviceType" data-options="required:true,editable:false,panelHeight:'100',width:100">
	    					<c:forEach items="${deviceTypes}" var="item">
								<option value="${item.key}">${item.value}</option>
							</c:forEach>
	    				</select>
	    			</td>
	    		</tr>
	    		<tr>
	    			<td><label><spring:message code="IMSI"/>(IMSI):</label></td>
	    			<td width="310px"><input class="easyui-numberbox" id="addImsi" name="imsi" 
		    			data-options="validType:'imsi',required:true"/></td>
	    			<td><label><spring:message code="SpeechCodec"/></label></td>
	    			<td>
	    				<select class="easyui-combobox" id="addMsvocodec" name="msvocodec" data-options="editable:false,required:true,panelHeight:'100'" style="width:100px">
	    					<c:forEach items="${msvocodecs}" var="item">
								<option value="${item.key}">${item.value}</option>
							</c:forEach>
	    				</select>
	    			</td>
	    		</tr>
	    		<tr>
	    			<td><label><spring:message code="ESN"/>(ESN):</label></td>
	    			<td width="310px"><input class="easyui-textbox" id="addEsn" name="esn" data-options="validType:'esn'"/>
	    			</td>
	    		</tr>
	    	</table>
	    </div>
	</div>
	<!-- 基本信息 结束 -->
	
	<!-- 基本业务开始 -->
	<div id="addBasicService" class="easyui-panel" data-options="title:'<spring:message code="BasicBusiness"/>'">
		<div style="padding:10px 55px 20px 60px">
			<c:forEach items="${basicBusinesses}" var="business" varStatus="status">
					<label for="jb_${status.count}"><input id="jb_${status.count}" type="checkbox" name="${business.name}" value="1" 
						<c:if test="${business.name=='shortMsg' || business.name=='secrecy' || business.name=='callerAllow' || business.name=='groupCallBusiness' 
						|| business.name=='circuitData' || business.name=='halfDuplexSingleCall' || business.name=='duplexSingleCall' || business.name=='NationalTraffic'}">
							checked
						</c:if>
					/>
					<spring:message code="${business.i18n}"/>
					</label>&nbsp;&nbsp;&nbsp;
					<c:if test="${status.count%10==0}"><br/></c:if>
			</c:forEach>
		</div>
	</div>
	<!-- 基本业务 结束 -->
	
	<!-- IMS信息 开始 -->
	<c:if test="${imsFlag==1}">
	<div id="addImsInfo" class="easyui-panel" data-options="title:'<spring:message code="IMSInformation"/>'">
		<div style="padding:10px 60px 20px 60px">
	    	<label><spring:message code="Domain"/>:</label>
	    	<input class="easyui-textbox" id="addDomain" name="domain" data-options="value:'test.com',required:true,width:130"/>　
	    	<label><spring:message code="ClearText"/>:<input type="checkbox" onchange="addMingWen(this)"><spring:message code="Password"/>:</label>
	    	<input class="easyui-textbox" id="addUePassword" name="uePassword" data-options="required:true,type:'password',width:130"/>
		</div>
	</div>
	</c:if>
	<!-- IMS信息 结束 -->
	
	<!-- 监听业务 开始 -->
	<c:if test="${jtyw==1}">
	<div id="addMonitorService" class="easyui-panel" data-options="title:'<spring:message code="ListeningBusiness"/>'">
		<div style="padding:10px 60px 20px 60px">
			<!-- 开启监听业务 -->
			<label for="addMonitorSwitch">
				<input id="addMonitorSwitch" type="checkbox" name="monitorSwitch" value="0"/><spring:message code="OpenBusiness"/>
			</label>&nbsp;
			<!-- 监听IP -->
			<label><spring:message code="ListeningAddr"/>:</label>
			<input id="addMonitorIP" name="monitorIP" class="easyui-textbox" data-options="validType:'ip',disabled:true,required:true"/>
			<!-- 监听Port -->
			<label><spring:message code="ListeningPort"/>:</label>
			<input id="addMonitorPort" name="monitorPort" class="easyui-numberbox" data-options="disabled:true,required:true,value:9654"/>
		</div>
	</div>
	</c:if>
	<!-- 监听业务 结束 -->
	
	<!-- 分组业务 开始 -->
	<c:if test="${fzye==1}">
	<div id="addPacketService" class="easyui-panel" data-options="title:'<spring:message code="GroupingBusiness"/>'">
		<div style="padding:10px 60px 20px 60px">
			<!-- 开启分组业务  -->
			<label for="addRegion"><input type="checkbox" id="addRegion" name="region" value="1" checked onclick="addCheckBox(this);"/><spring:message code="OpenBusiness"/></label>
			<!-- 动态分配  -->
			<input type="radio" name="voiceMailSwitch" value="0" checked="checked" disabled="disabled"/>
			<label><spring:message code="DynamicGrouping"/></label>
			&nbsp;&nbsp;
			<!-- 静态分配 -->
			<input type="radio" name="voiceMailSwitch" value="1" disabled="disabled"/>
			<label><spring:message code="StaticGrouping"/></label>
			<label><spring:message code="IPAddress"/>:&nbsp;</label><input class="easyui-textbox" id="addVoiceMailNum" name="voiceMailNum" data-options="validType:'ipAdd',required:true,disabled:true"/>
			<br/>
			<!-- OSPF组播 -->
			<label><input type="checkbox" id="addOspfzb" name="ospfMulticast" value="1" /><spring:message code="OSPFMulticast"/></label>&nbsp;
			<!-- OSPF广播 -->
			<label><input type="checkbox" id="addOspfgb" name="ospfBroadcast" value="1" /><spring:message code="OSPFBroadcast"/></label>
			
		</div>
	</div>
	</c:if>
	<!-- 分组业务 结束 -->
	
	<!-- 调度业务(优先级) 开始 -->
	<c:if test="${ddyw==1}">
	<div id="addScheduleService" class="easyui-panel" data-options="title:'<spring:message code="ScheduingBusiness"/>'">
		<div style="padding:10px 60px 20px 60px">
			<label><spring:message code="Priority"/>:</label>
			<select class="easyui-combobox" id="addPriority" name="priority" data-options="editable:false,panelHeight:'100',width:100">
				<c:forEach items="${userPriority}" var="item">
					<option value="${item.priority}">${item.level}</option>
				</c:forEach>
			</select>
		</div>
	</div>
	</c:if>
	<!-- 调度业务(优先级) 结束 -->

	<!-- 补充业务 开始 -->
	<c:if test="${bcyw==1}">
	<div id="addSupplementService" class="easyui-panel" data-options="title:'<spring:message code="SupplementaryServices"/>'">
		<div style="padding:10px 60px 20px 60px">
			<!-- 无应答前转 -->
			<input type="checkbox" name="fwdNoAnswer" value="0"/>
			<label><spring:message code="NoResponseForwarding"/>:</label>
			<input class="easyui-numberbox" id="addfwdNoAnswerNum" name="fwdNoAnswerNum" data-options="validType:'mdn',prompt:'<spring:message code="PleaseEnterThePhoneNumber"/>',disabled:true,required:true"/>
			&nbsp;&nbsp;&nbsp;&nbsp;
			<!-- 遇忙前转 -->
			<input type="checkbox" name="fwdOnBusy" value="0"/>
			<label><spring:message code="CallForwarding"/>:</label>
			<input class="easyui-numberbox" id="addfwdOnBusyNum" name="fwdOnBusyNum" data-options="validType:'mdn',prompt:'<spring:message code="PleaseEnterThePhoneNumber"/>',disabled:true,required:true"/>
			<br/>
			<!-- 无条件前转 -->
			<input type="checkbox" name="directFwd" value="0"/>
			<label><spring:message code="UnconditionalForwarding"/>:</label>
			<input class="easyui-numberbox" id="adddirectFwdNum" name="directFwdNum" data-options="validType:'mdn',prompt:'<spring:message code="PleaseEnterThePhoneNumber"/>',disabled:true,required:true"/>
			&nbsp;&nbsp;&nbsp;&nbsp;
			<!-- 不可达前转 -->
			<input type="checkbox" name="fwdNA" value="0"/>
			<label><spring:message code="NoReachableForwarding"/>:</label>
			<input class="easyui-numberbox" id="addfwdNANum" name="fwdNANum" data-options="validType:'mdn',prompt:'<spring:message code="PleaseEnterThePhoneNumber"/>',disabled:true,required:true"/>
		</div>
	</div>
	</c:if>
	<!-- 补充业务 结束 -->
	
	<!-- GroupInfo(用户归属的组) 开始    -->
	<c:if test="${groupInfo==1}">
	<div id="addGroupInfoService" class="easyui-panel" data-options="title:'<spring:message code="GroupMessage"/>'">
		<div style="padding:10px 10px 20px 60px">
			<c:forEach items="${groups}" var="group" varStatus="status">
				<label for="g_${status.count}">
					<input id="g_${status.count}" type="checkbox" name="groups" value="${group.id}">${group.id}
				</label>
			</c:forEach>
		</div>
	</div>
	</c:if>
	<!-- GroupInfo(用户归属的组) 结束   -->
	
	<!-- 鉴权参数 开始 -->
	<c:if test="${auc==1}">
	<div id="addAucService" class="easyui-panel" data-options="title:'<spring:message code="AUC"/>'">
		<div style="padding:10px 60px 10px 60px">
		<table>
			<tr id="addAmf">
				<td><label>AMF:</label></td>
				<td>
					<input class="easyui-textbox" data-options="validType:'checkParam',width:40" id="addAmf1" name="amf1" value="F2"/>
					<label>-</label>
					<input class="easyui-textbox" data-options="validType:'checkParam',width:40" id="addAmf2" name="amf2" value="4C"/>
				</td>
			</tr>
			<tr id="addSqn">
				<td><label>SQN:</label></td>
				<td>
					<input class="easyui-textbox" data-options="validType:'checkParam',width:40" id="addSqn1" name="sqn1" value="00"/>
					<label>-</label>
					<input class="easyui-textbox" data-options="validType:'checkParam',width:40" id="addSqn2" name="sqn2" value="00"/>
					<label>-</label>
					<input class="easyui-textbox" data-options="validType:'checkParam',width:40" id="addSqn3" name="sqn3" value="00"/>
					<label>-</label>
					<input class="easyui-textbox" data-options="validType:'checkParam',width:40" id="addSqn4" name="sqn4" value="00"/>
					<label>-</label>
					<input class="easyui-textbox" data-options="validType:'checkParam',width:40" id="addSqn5" name="sqn5" value="00"/>
					<label>-</label>
					<input class="easyui-textbox" data-options="validType:'checkParam',width:40" id="addSqn6" name="sqn6" value="00"/>
				</td>
			</tr>
			<tr id="addK">
				<td><label>K:</label></td>
				<td>
					<input class="easyui-textbox" data-options="validType:'checkParam',width:40" id="addK1" name="k1" value="FF"/>
					<label>-</label>
					<input class="easyui-textbox" data-options="validType:'checkParam',width:40" id="addK2" name="k2" value="FF"/>
					<label>-</label>
					<input class="easyui-textbox" data-options="validType:'checkParam',width:40" id="addK3" name="k3" value="FF"/>
					<label>-</label>
					<input class="easyui-textbox" data-options="validType:'checkParam',width:40" id="addK4" name="k4" value="FF"/>
					<label>-</label>
					<input class="easyui-textbox" data-options="validType:'checkParam',width:40" id="addK5" name="k5" value="FF"/>
					<label>-</label>
					<input class="easyui-textbox" data-options="validType:'checkParam',width:40" id="addK6" name="k6" value="FF"/>
					<label>-</label>
					<input class="easyui-textbox" data-options="validType:'checkParam',width:40" id="addK7" name="k7" value="FF"/>
					<label>-</label>
					<input class="easyui-textbox" data-options="validType:'checkParam',width:40" id="addK8" name="k8" value="FF"/>
					<label>-</label>
					<input class="easyui-textbox" data-options="validType:'checkParam',width:40" id="addK9" name="k9" value="FF"/>
					<label>-</label>
					<input class="easyui-textbox" data-options="validType:'checkParam',width:40" id="addK10" name="k10" value="FF"/>
					<label>-</label>
					<input class="easyui-textbox" data-options="validType:'checkParam',width:40" id="addK11" name="k11" value="FF"/>
					<label>-</label>
					<input class="easyui-textbox" data-options="validType:'checkParam',width:40" id="addK12" name="k12" value="FF"/>
					<label>-</label>
					<input class="easyui-textbox" data-options="validType:'checkParam',width:40" id="addK13" name="k13" value="FF"/>
					<label>-</label>
					<input class="easyui-textbox" data-options="validType:'checkParam',width:40" id="addK14" name="k14" value="FF"/>
					<label>-</label>
					<input class="easyui-textbox" data-options="validType:'checkParam',width:40" id="addK15" name="k15" value="FF"/>
					<label>-</label>
					<input class="easyui-textbox" data-options="validType:'checkParam',width:40" id="addK16" name="k16" value="FF"/>
				</td>
			</tr>
			<tr id="addOp">
				<td><label>OP:</label></td>
				<td>
					<input class="easyui-textbox" data-options="validType:'checkParam',width:40" id="addOp1" name="op1" value="FF"/>
					<label>-</label>
					<input class="easyui-textbox" data-options="validType:'checkParam',width:40" id="addOp2" name="op2" value="FF"/>
					<label>-</label>
					<input class="easyui-textbox" data-options="validType:'checkParam',width:40" id="addOp3" name="op3" value="FF"/>
					<label>-</label>
					<input class="easyui-textbox" data-options="validType:'checkParam',width:40" id="addOp4" name="op4" value="FF"/>
					<label>-</label>
					<input class="easyui-textbox" data-options="validType:'checkParam',width:40" id="addOp5" name="op5" value="FF"/>
					<label>-</label>
					<input class="easyui-textbox" data-options="validType:'checkParam',width:40" id="addOp6" name="op6" value="FF"/>
					<label>-</label>
					<input class="easyui-textbox" data-options="validType:'checkParam',width:40" id="addOp7" name="op7" value="FF"/>
					<label>-</label>
					<input class="easyui-textbox" data-options="validType:'checkParam',width:40" id="addOp8" name="op8" value="FF"/>
					<label>-</label>
					<input class="easyui-textbox" data-options="validType:'checkParam',width:40" id="addOp9" name="op9" value="FF"/>
					<label>-</label>
					<input class="easyui-textbox" data-options="validType:'checkParam',width:40" id="addOp10" name="op10" value="FF"/>
					<label>-</label>
					<input class="easyui-textbox" data-options="validType:'checkParam',width:40" id="addOp11" name="op11" value="FF"/>
					<label>-</label>
					<input class="easyui-textbox" data-options="validType:'checkParam',width:40" id="addOp12" name="op12" value="FF"/>
					<label>-</label>
					<input class="easyui-textbox" data-options="validType:'checkParam',width:40" id="addOp13" name="op13" value="FF"/>
					<label>-</label>
					<input class="easyui-textbox" data-options="validType:'checkParam',width:40" id="addOp14" name="op14" value="FF"/>
					<label>-</label>
					<input class="easyui-textbox" data-options="validType:'checkParam',width:40" id="addOp15" name="op15" value="FF"/>
					<label>-</label>
					<input class="easyui-textbox" data-options="validType:'checkParam',width:40" id="addOp16" name="op16" value="FF"/>
				</td>
			</tr>
			<tr id="addOpc">
				<td><label>OPC:</label></td>
				<td>
					<input class="easyui-textbox" data-options="validType:'checkParam',width:40" id="addOpc1" name="opc1" value="FF"/>
					<label>-</label>
					<input class="easyui-textbox" data-options="validType:'checkParam',width:40" id="addOpc2" name="opc2" value="FF"/>
					<label>-</label>
					<input class="easyui-textbox" data-options="validType:'checkParam',width:40" id="addOpc3" name="opc3" value="FF"/>
					<label>-</label>
					<input class="easyui-textbox" data-options="validType:'checkParam',width:40" id="addOpc4" name="opc4" value="FF"/>
					<label>-</label>
					<input class="easyui-textbox" data-options="validType:'checkParam',width:40" id="addOpc5" name="opc5" value="FF"/>
					<label>-</label>
					<input class="easyui-textbox" data-options="validType:'checkParam',width:40" id="addOpc6" name="opc6" value="FF"/>
					<label>-</label>
					<input class="easyui-textbox" data-options="validType:'checkParam',width:40" id="addOpc7" name="opc7" value="FF"/>
					<label>-</label>
					<input class="easyui-textbox" data-options="validType:'checkParam',width:40" id="addOpc8" name="opc8" value="FF"/>
					<label>-</label>
					<input class="easyui-textbox" data-options="validType:'checkParam',width:40" id="addOpc9" name="opc9" value="FF"/>
					<label>-</label>
					<input class="easyui-textbox" data-options="validType:'checkParam',width:40" id="addOpc10" name="opc10" value="FF"/>
					<label>-</label>
					<input class="easyui-textbox" data-options="validType:'checkParam',width:40" id="addOpc11" name="opc11" value="FF"/>
					<label>-</label>
					<input class="easyui-textbox" data-options="validType:'checkParam',width:40" id="addOpc12" name="opc12" value="FF"/>
					<label>-</label>
					<input class="easyui-textbox" data-options="validType:'checkParam',width:40" id="addOpc13" name="opc13" value="FF"/>
					<label>-</label>
					<input class="easyui-textbox" data-options="validType:'checkParam',width:40" id="addOpc14" name="opc14" value="FF"/>
					<label>-</label>
					<input class="easyui-textbox" data-options="validType:'checkParam',width:40" id="addOpc15" name="opc15" value="FF"/>
					<label>-</label>
					<input class="easyui-textbox" data-options="validType:'checkParam',width:40" id="addOpc16" name="opc16" value="FF"/>
				</td>
			</tr>
		</table>
		</div>
		<div style="padding:0px 60px 20px 60px">		
			<label>TIME:</label>
			<input class="easyui-timespinner" style="width:90px;" name="start"
				required="required" data-options="showSeconds:true,editable:false" value="00:00:00">
			<label>-</label>
			<input class="easyui-timespinner" style="width:90px;" name="stop"
				required="required" data-options="showSeconds:true,editable:false" value="23:59:59">
		</div>
	</div>
	</c:if>
	<!-- 鉴权参数 结束 -->
	
	<!-- TFT设置  开始-->
	<c:if test="${tft==1}">
	<div id="addTftSetService" class="easyui-panel" data-options="title:'<spring:message code="TFTSetting"/>'">
		<div style="padding:10px 60px 20px 60px">
			<table>
	    		<tr>
	    			<td><label><spring:message code="IPPackageSourceIP"/>:</label></td>
	    			<td><input class="easyui-textbox" data-options="value:'0.0.0.0',validType:'ip'" name="srcIp"/></td>
	    			<td><label><spring:message code="IPPackageDestinationIP"/>:</label></td>
	    			<td><input class="easyui-textbox" data-options="value:'0.0.0.0',validType:'ip'" name="dstIp"/></td>
				</tr>
				<tr>
	    			<td><label>IP包源端口开始值:</label></td>
	    			<td><input class="easyui-numberbox" data-options="value:'0',min:0" name="srcPortStart"/></td>
	    			<td><label>IP包源端口结束值:</label></td>
	    			<td><input class="easyui-numberbox" data-options="value:'0',min:0" name="srcPortEnd"/></td>
				</tr>
				<tr>
	    			<td><label>IP包目的端口开始值:</label></td>
	    			<td><input class="easyui-numberbox" data-options="value:'0',min:0" name="dstPortStart"/></td>
	    			<td><label>IP包目的端口结束值:</label></td>
	    			<td><input class="easyui-numberbox" data-options="value:'0',min:0" name="dstPortEnd"/></td>
				</tr>
				<tr>
	    			<td><label><spring:message code="DSCPStartValue"/>:</label></td>
	    			<td><input class="easyui-numberbox" data-options="value:'0',min:0" name="diffServStart"/></td>
	    			<td><label><spring:message code="DSCPEndValue"/>:</label></td>
	    			<td><input class="easyui-numberbox" data-options="value:'0',min:0" name="diffServEnd"/></td>
				</tr>
				<tr>
	    			<td><label><spring:message code="IPPackageLengthMin"/>:</label></td>
	    			<td><input class="easyui-numberbox" data-options="value:'0',min:0" name="pktLenMin"/></td>
	    			<td><label><spring:message code="IPPackageLengthMax"/>:</label></td>
	    			<td><input class="easyui-numberbox" data-options="value:'0',min:0" name="pktLenMax"/></td>
				</tr>
			</table>
		</div>
	</div>
	</c:if>
	<!-- TFT设置 结束-->
	
	<!-- 终端信息 开始 -->
	<c:if test="${zdxx==1}">
	<div id="addTerminalInfoService" class="easyui-panel" data-options="title:'<spring:message code="TerminalInformation"/>'">
		<div style="padding:10px 60px 20px 60px">
			<table>
	    		<tr>
	    			<td><label><spring:message code="TerminalNumber"/>:</label></td>
	    			<td><input class="easyui-textbox" id="addTerminalId" name="terminalId" data-options="validType:'terminalLength',width:143"/></td>
	    			<td><label><spring:message code="TerminalType"/>:</label></td>
	    			<td>
	    				<select class="easyui-combobox" id="addTerminalType" name="terminalType" 
	    					data-options="width:143,editable:false,panelHeight:'100'">
							<c:forEach items="${terminalTypes}" var="terminal">
								<option value="${terminal.terminalId }">${terminal.terminalName}</option>
							</c:forEach>
	    				</select>　　
	    			</td>
	    		</tr>
	    		<tr>
	    			<td><label><spring:message code="GWId"/>:</label></td>
	    			<td>
	    				<input class="easyui-numberbox" id="addGwId" name="gwId" data-options="width:143,value:2,editable:false"/>
	    			</td>
	    			<td><label><spring:message code="MaxPowerLevel"/>:</label></td>
	    			<td>
	    				<select class="easyui-combobox" id="addPowerLevel" name="powerLevel" data-options="width:143,editable:false,panelHeight:'100'">
	    					<c:forEach var="i" begin="1" end="7" step="1">
								<option value="${i}">${i}</option>
							</c:forEach>
	    				</select>&nbsp;<span id="addNote" style="color:red">${powerLevelText}</span>
	    			</td>
	    		</tr>
	    		<tr>
	    			<td><label><spring:message code="Department"/>:</label></td>
	    			<td>
	    				<input class="easyui-textbox" id="addDepartment" name="department" data-options="width:143,validType:'departmentLength'"/>
	    			</td>
	    			<td><label><spring:message code="UserType"/>:</label></td>
	    			<td>
	    				<select class="easyui-combobox" id="addUserType" name="userType" data-options="width:143,editable:false,panelHeight:'auto'">
							<option value="1" selected="selected">军用用户</option>
	    				</select>
	    			</td>
	    		</tr>
	    		<tr>
	    			<td><span hidden="true"><label><spring:message code="UserName"/>:</label></span></td>
	    			<td><span hidden="true"><input class="easyui-textbox" id="addUserName" name="username" data-options=""/></span></td>
	    			<td><span hidden="true"><label><spring:message code="UserInfo"/>:</label></span></td>
	    			<td colspan="3"><span hidden="true"><input class="easyui-textbox" id="addUserInfo" name="userInfo" data-options="width:143"/></span></td>
	    			<td><span hidden="true"><label>服务优先级:</label></span></td>
	    			<td>
		    			<span hidden="true">
			    			<select class="easyui-combobox" id="addServicePriority" name="servicePriority" data-options="width:143,editable:false,panelHeight:'100'">
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
	<!-- 终端信息 结束 -->
	
	<!-- EPC分组数据 开始 -->
	<c:if test="${epc==1}">
	<div id="addEpcGroupDataService" class="easyui-panel" data-options="title:'EPC分组数据'">
		<div style="padding:10px 60px 20px 60px">
			<table>
	    		<tr>
	    			<td><label>APN:</label></td>
	    			<td><input class="easyui-textbox" id="addApn" name="apn" data-options="value:'sunkaisens'<c:if test="${epc==1}">,required:true</c:if>"/>　　</td>
	    			<td><label>ErabId:</label></td>
	    			<td><input class="easyui-numberbox" id="addErabId" name="erabId" data-options="value:'5',min:0<c:if test="${epc==1}">,required:true</c:if>"/></td>
	    		</tr>
	    		<tr>
	    			<td><label>QCI:</label></td>
	    			<td><input class="easyui-numberbox" id="addQci" name="qci" data-options="value:'9',min:0<c:if test="${epc==1}">,required:true</c:if>"/></td>
	    			<td><label>ARPriority:</label></td>
	    			<td><input class="easyui-numberbox" id="addArPriority" name="arPriority" data-options="value:'3',min:0<c:if test="${epc==1}">,required:true</c:if>"/></td>
	    		</tr>
	    		<tr>
	    			<td><label>AggregateMaxULBitrate:</label></td>
	    			<td><input class="easyui-numberbox" id="addAggregateMaxULBitRate" name="aggregateMaxULBitRate" data-options="value:'3500000',min:0<c:if test="${epc==1}">,required:true</c:if>"/></td>
	    			<td><label>AggregateMaxDLBitrate:</label></td>
	    			<td><input class="easyui-numberbox" id="addAggregateMaxDLBitRate" name="aggregateMaxDLBitRate" data-options="value:'2500000',min:0<c:if test="${epc==1}">,required:true</c:if>"/></td>
	    		</tr>
	    		<tr>
	    			<td><label>GuaranteedULBitRate:</label></td>
	    			<td><input class="easyui-numberbox" id="addGuaranteedULBitRate" name="guaranteedULBitRate" data-options="value:'0',min:0<c:if test="${epc==1}">,required:true</c:if>"/></td>
	    			<td><label>GuaranteedDLBitRate:</label></td>
	    			<td><input class="easyui-numberbox" id="addGuaranteedDLBitRate" name="guaranteedDLBitRate" data-options="value:'0',min:0<c:if test="${epc==1}">,required:true</c:if>"/></td>
	    		</tr>
	    		<tr>
	    			<td><label>MaxULBitRate:</label></td>
	    			<td><input class="easyui-numberbox" id="addMaxULBitRate" name="maxULBitRate" data-options="value:'0',min:0<c:if test="${epc==1}">,required:true</c:if>"/></td>
	    			<td><label>MaxDLBitRate:</label></td>
	    			<td><input class="easyui-numberbox" id="addMaxDLBitRate" name="maxDLBitRate" data-options="value:'0',min:0<c:if test="${epc==1}">,required:true</c:if>"/></td>
	    		</tr>
	    	</table>
		</div>
	</div>
	</c:if>
	<!-- EPC分组数据  结束 -->
    </form>
</body>
</html>
