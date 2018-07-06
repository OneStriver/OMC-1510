<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>批量添加终端用户</title>
<%@ include file="/WEB-INF/jsp/public/common.jspf"%>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/hss/add.css"/>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/hss/batchAdd.js"></script>

</head>
<body>
	<form id="batchAddHssForm" class="easyui-form" method="post" enctype="multipart/form-data">
		<!-- 基本信息 -->
       <div class="easyui-panel" id="batchAddBasicInfo" data-options="title:'<spring:message code="BasicInformation"/>'">
       		<div style="padding:10px 10px 10px 10px">
       			<table>
       				<tr>
       					<td><label><spring:message code="InsertNumber"/>:</label></td>
       					<td>
       						<input class="easyui-numberbox" id="batchAddCount" name="batchCount" data-options="width:150,validType:'countMax',required:true">
       					</td>
       				</tr>
       				<tr>
       					<td><label><spring:message code="StartIMSI"/>:</label></td>
       					<td>
       						<input class="easyui-numberbox" id="batchAddImsi" name="imsi" data-options="width:150,validType:'imsi',required:true">
       					</td>
       				</tr>
       				<tr>
       					<td><label><spring:message code="StartMDN"/>:</label></td>
       					<td>
       						<input class="easyui-numberbox" id="batchAddMdn" name="mdn" data-options="width:150,validType:'mdn',required:true">
       					</td>
       				</tr>
       				<tr id="batchAddEsnTr">
       					<td><label><spring:message code="StartESN"/>:</label></td>
       					<td>
       						<input class="easyui-textbox" id="batchAddEsn" name="esn"  data-options="width:150,validType:'esn',required:true">
       					</td>
       				</tr>
       				<tr>
						<td><label><spring:message code="DeviceType" />:</label></td>
						<td><select class="easyui-combobox" id="batchAddDeviceType" name="deviceType"
							data-options="editable:false,width:150,panelHeight:'100'">
								<c:forEach items="${deviceTypes}" var="item">
									<option value="${item.key}">${item.value}</option>
								</c:forEach>
						</select></td>
					</tr>
       				<tr>
						<td><label><spring:message code="SpeechCodecType" />:</label></td>
						<td><select class="easyui-combobox" id="batchAddMsvocodec" name="msvocodec"
							data-options="editable:false,width:150,panelHeight:'100'">
								<c:forEach items="${msvocodecs}" var="item">
									<option value="${item.key}">${item.value}</option>
								</c:forEach>
						</select></td>
					</tr>
					<c:if test="${ddyw==1}">
					<tr>
						<td><label><spring:message code="Priority" />:</label></td>
						<td>
							<select class="easyui-combobox" id="batchAddPriority" name="priority"
								data-options="editable:false,panelHeight:'100',width:150">
									<c:forEach items="${userPriority}" var="item">
										<option value="${item.priority}">${item.level}</option>
									</c:forEach>
							</select>
						</td>
					</tr>
					</c:if>
       			</table>
        	</div>
       </div>
       
       <!-- 基本业务开始 -->
       <div class="easyui-panel" id="batchAddJbyw" data-options="title:'<spring:message code="BasicBusiness"/>'">
	       <div style="padding:10px 10px 10px 10px">
	        	<c:forEach items="${basicBusinesses}" var="business" varStatus="status">
	        		<label for="jb_${status.count}"><input id="jb_${status.count}" type="checkbox" name="${business.name}" value="1" 
						<c:if test="${business.name=='shortMsg' || business.name=='secrecy' || business.name=='callerAllow' || business.name=='groupCallBusiness' 
								|| business.name=='circuitData' || business.name=='halfDuplexSingleCall' || business.name=='duplexSingleCall' || business.name=='NationalTraffic'}">
							checked
						</c:if>
						/>
						<spring:message code="${business.i18n}"/>
					</label>&nbsp;&nbsp;&nbsp;
				<c:if test="${status.count%4==0}"><br/></c:if>
				</c:forEach>
			</div>
		</div>
		<!-- 基本业务结束 -->
		
       <!-- Ims信息开始 -->
       <c:if test="${flag==1}">
         <div id="batchAddImsInfo" class="easyui-panel" data-options="title:'<spring:message code="IMSInformation"/>'">
			<div style="padding:10px 10px 10px 10px">
		    	<label><spring:message code="Domain"/>:</label>
		    	<input class="easyui-textbox" id="batchAddDomain" name="domain" data-options="value:'test.com',required:false,width:100"/>　
		    	<label><spring:message code="ClearText"/><input type="checkbox" onchange="batchAddMingWen(this)"> <spring:message code="Password"/>:</label>
		    	<input class="easyui-textbox" id="batchAddUePassword" name="uePassword" data-options="required:false,type:'password',width:100"/>
			</div>
		 </div>     	
        </c:if>
        <!-- Ims信息结束 -->
		
	   <!-- 监听业务 开始 -->
	   <c:if test="${jtyw==1}">
		<div class="easyui-panel" id="batchAddMonitorService" data-options="title:'<spring:message code="ListeningBusiness"/>'">
			<div style="padding:10px 10px 10px 10px">
				<table>
					<tr>
						<td><input type="checkbox" id="batchAddMonitorSwitch" name="monitorSwitch" value="0"/><spring:message code="OpenListeningService"/></td>
						<td><spring:message code="ListeningAddr"/>:</td>
						<td><input class="easyui-textbox" id="batchAddMonitorIP" name="monitorIP" data-options="width:100,validType:'ip',disabled:true,required:true" /></td>
						<td><spring:message code="ListeningPort"/>:</td>
						<td><input class="easyui-numberbox" id="batchAddMonitorPort" name="monitorPort" data-options="width:100,disabled:true,required:true,value:9654" /></td>
					</tr>
				</table>
			</div>
		 </div>
		</c:if>
		<!-- 监听业务 结束 -->
       
       <!-- auc信息 -->
       <fieldset style="display: none">
       	<label>鉴权参数(97-2003版本excel):</label>
       	<input class="easyui-filebox" id="batchAddAucExcel" name="aucExcel" data-options="validType:'excelFile',width:150,buttonText:'导入'"/>
       	<a href="${pageContext.servletContext.contextPath}/media/workbook.xls" class="easyui-linkbutton">下载模板</a>
       </fieldset>
		
		<!-- 终端信息 开始 -->
		<c:if test="${zdxx==1}">
			<div class="easyui-panel" id="batchAddTerminalInfo" data-options="title:'<spring:message code="TerminalInformation"/>'">
				<div style="padding: 10px 10px 10px 10px">
					<table>
						<tr>
							<td><label><spring:message code="TerminalNumber" />:</label></td>
							<td><input class="easyui-textbox" id="batchAddTerminalId" name="terminalId" data-options="width:100,validType:'terminalLength',value:1" /></td>
							<td><label><spring:message code="TerminalType" />:</label></td>
							<td>
							<select class="easyui-combobox" id="batchAddTerminalType" name="terminalType" data-options="width:150,editable:false,panelHeight:'100'">
									<c:forEach items="${terminalTypes}" var="terminal">
										<option value="${terminal.terminalId }">${terminal.terminalName}</option>
									</c:forEach>
							</select>
							</td>
						</tr>
						<tr>
							<td><label><spring:message code="GWId" />:</label></td>
							<td><input class="easyui-numberbox" id="batchAddGwId" name="gwId" data-options="width:100,value:2" /></td>
							<td><label><spring:message code="MaxPowerLevel" />:</label></td>
							<td><select class="easyui-combobox" id="batchAddPowerLevel" name="powerLevel" data-options="width:150,editable:false,panelHeight:'100'">
									<c:forEach var="i" begin="1" end="7" step="1">
										<option value="${i}">${i}</option>
									</c:forEach>
							</select></td>
						</tr>
						<tr>
							<td><label><spring:message code="Department" />:</label></td>
							<td><input class="easyui-textbox" id="batchAddDepartment" name="department" data-options="width:100,validType:'departmentLength'" /></td>
							<td><label><spring:message code="UserType" />:</label></td>
							<td>
							<select class="easyui-combobox" id="batchAddUserType" name="userType" data-options="width:150,editable:false,panelHeight:'auto'">
								<option value="1" selected="selected">军用用户</option>
							</select>
							</td>
						</tr>
					</table>
				</div>
			</div>
		</c:if>
		<c:if test="${zdxx==1}">
			<div>
		    	<span hidden="true"><label><spring:message code="UserName"/>:</label></span>
		    	<span hidden="true"><input class="easyui-textbox" id="batchAddUserName" name="username" data-options=""/></span>
		    	<span hidden="true"><label><spring:message code="UserInfo"/>:</label></span>
		    	<span hidden="true"><input class="easyui-textbox" id="batchAddUserInfo" name="userInfo" data-options="width:100"/></span>
		    	<span hidden="true"><label>服务优先级:</label></span>
		    	<span hidden="true">
		    	<select class="easyui-combobox" id="batchAddServicePriority" name="servicePriority" data-options="width:150,editable:false,panelHeight:'100'">
					<option value="1">1</option>
					<option value="2">2</option>
					<option value="3">3</option>
					<option value="4">4</option>
					<option value="5" selected="selected">5</option>
		    	</select>
		    	</span>
			</div>
		</c:if>
		<!-- 终端信息 结束 -->
    </form>
</body>
</html>
