<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>修改终端用户</title>
<%@ include file="/WEB-INF/jsp/public/common.jspf"%>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/hss/add.css"/>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/hss/update.js"></script>
</head>
<body style="margin:auto"> 
	<form id="updateHssForm" class="easyui-form" method="post" data-options="novalidate:true" action="${pageContext.request.contextPath}/hss/update.action">
	
	<!-- 基本信息 开始 -->
	<div id="updateBasicInfo" class="easyui-panel" data-options="title:'<spring:message code="BasicInformation"/>'">
		<div style="padding:10px 60px 20px 60px">
	    	<table>
	    		<tr>
	    			<td><label><spring:message code="MDN"/>(MDN):</label></td>
	    			<td width="310px"><input class="easyui-numberbox" id="updateMdn" name="mdn" data-options="validType:'mdn',required:true,readonly:true,disabled:true" value="${hss.mdn}"/></td>
	    			<td><label><spring:message code="DeviceType"/>:</label></td>
	    			<td>
	    				<!-- 只是显示设备类型的名字 -->
						<input type="hidden" name="deviceType" value="${hss.deviceType}" />
						<input class="easyui-textbox" id="updateDeviceType" data-options="width:100,required:true,readonly:true" value="${hss.deviceName}"/>
	    			</td>
	    		</tr>
	    		<tr>
	    			<td><label><spring:message code="IMSI"/>(IMSI):</label></td>
	    			<td width="310px"><input class="easyui-numberbox" id="updateImsi" name="imsi" data-options="validType:'imsi',required:true,readonly:true,disabled:true" value="${hss.imsi}"/></td>
	    			<td><label><spring:message code="SpeechCodec"/>:</label></td>
	    			<td>
	    				<select class="easyui-combobox" id="updateMsvocodec" name="msvocodec" data-options="width:100,editable:false,required:true,panelHeight:'100'" style="width:100px">
	    					<c:forEach items="${msvocodecs}" var="item">
								<option value="${item.key}"  <c:if test="${item.key == hss.msvocodec}">selected="selected"</c:if>>${item.value}</option>
							</c:forEach>
	    				</select>
	    			</td>
	    		</tr>
	    		<tr>
	    			<td><label><spring:message code="ESN"/>(ESN):</label></td>
	    			<td width="310px"><input class="easyui-textbox" id="updateEsn" name="esn" data-options="validType:'esn',required:false" value="${hss.esn}"/></td>
	    		</tr>
	    	</table>
	    </div>
	</div>
	<!-- 基本信息 结束 -->
	
	<!-- 基本业务开始 -->
	<div id="updateBasicService" class="easyui-panel" data-options="title:'<spring:message code="BasicBusiness"/>'">
		<div style="padding:10px 55px 20px 60px">
			<c:forEach items="${basicBusinesses}" var="business" varStatus="status">
				<label for="jb_${status.count}">
				<input id="jb_${status.count}" type="checkbox" name="${business.name}" value="1" 
					<c:if test="${hss[business.name]==1}">checked="checked"</c:if>
				/>
				<spring:message code="${business.i18n}"/>
				</label>&nbsp;&nbsp;&nbsp;
				<c:if test="${status.count%10==0}"><br/></c:if>
			</c:forEach>
		</div>
	</div>
	<!-- 基本业务 结束 -->
	
	<input type="hidden" name="msprofile_extra" value="${hss.msprofile_extra}">
	<input type="hidden" name="swoon" value="${hss.swoon}">
	<input type="hidden" name="destroy" value="${hss.destroy}">
	<input type="hidden" name="airMonitor" value="${hss.airMonitor}">
	
	<!-- IMS信息 开始 -->
	<c:if test="${ims==1}">
	<c:if test="${hss.domain!=null}">
	<div id="updateImsInfo" class="easyui-panel" data-options="title:'<spring:message code="IMSInformation"/>'">
		<div style="padding:10px 60px 20px 60px">
	    	<label><spring:message code="Domain"/>:</label>
	    	<input class="easyui-textbox" id="updateDomain" name="domain" data-options="value:'${hss.domain}',required:false,width:130"/>　
	    	<label><spring:message code="ClearText"/><input type="checkbox" onchange="updateMingWen(this)"> <spring:message code="Password"/>:</label>
	    	<input class="easyui-textbox" id="updateUePassword" name="uePassword" data-options="value:'${hss.uePassword}',required:false,type:'password',width:130"/>
		</div>
	</div>
	</c:if>
	</c:if>
	<!-- IMS信息 结束 -->
	
	<!-- 监听业务 开始 -->
	<c:if test="${jtyw==1}">
	<div id="updateMonitorService" class="easyui-panel" data-options="title:'<spring:message code="ListeningBusiness"/>'">
		<div style="padding:10px 60px 20px 60px">
			<!-- 开启监听业务 -->
			<label for="updateMonitorSwitch">
				<input type="checkbox" id="updateMonitorSwitch" name="monitorSwitch" value="${hss.monitorSwitch}" <c:if test="${hss.monitorSwitch==1}">checked="checked"</c:if>><spring:message code="OpenBusiness"/>
			</label>&nbsp;
			<!-- 监听IP -->
			<label><spring:message code="ListeningAddr"/>:</label>
			<input id="updateMonitorIP" name="monitorIP" data-options="validType:'ip',required:true" class="easyui-textbox" value="${hss.monitorIP}"/>
			<!-- 监听Port -->
			<label><spring:message code="ListeningPort"/>:</label>
			<input id="updateMonitorPort" name="monitorPort" data-options="required:true" class="easyui-numberbox" value="${hss.monitorPort}"/>
		</div>
	</div>
	</c:if>
	<!-- 监听业务 结束 -->
	
	<!-- 监听业务 JS -->
	<script type="text/javascript">
		var value=${hss.monitorSwitch};
		if(value==0){
			$("#updateMonitorSwitch").val("0");
			$("#updateMonitorIP").attr("disabled",true);
			$("#updateMonitorPort").attr("disabled",true);
		}
		if(value==1){
			$("#updateMonitorSwitch").val("1");
			$("#updateMonitorIP").attr("disabled",false);
			$("#updateMonitorPort").attr("disabled",false);
		}
		var monitorSwitch = $('input[name=monitorSwitch]');
		monitorSwitch.bind("change", function() {
			var isChecked = $(this).prop("checked");
			if(isChecked){
				monitorSwitch.val("1");
			}else{
				monitorSwitch.val("0");
			}
			$('#updateMonitorIP').textbox({
				disabled : !isChecked
			});
			$('#updateMonitorPort').textbox({
				disabled : !isChecked
			});
		});
	</script>
	<!-- 监听业务JS结束 -->
	
	<!-- 分组业务 开始 -->
	<c:if test="${fzye==1}">
	<div id="updatePacketService" class="easyui-panel" data-options="title:'<spring:message code="GroupingBusiness"/>'">
		<div style="padding:10px 60px 20px 60px">
			<label for="updateRegion">
				<input type="checkbox" id="updateRegion" name="region" value="${hss.region}" <c:if test="${hss.region==1}">checked="checked"</c:if> onclick="updateCheckBox(this);"/><spring:message code="OpenBusiness"></spring:message>
			</label>
			<input type="radio" id="updateVoiceMailSwitch0" name="voiceMailSwitch" value="0" <c:if test="${hss.voiceMailSwitch==0}">checked="checked"</c:if>/>
			<label><spring:message code="DynamicGrouping"/></label>
			&nbsp;&nbsp;
			<input type="radio" id="updateVoiceMailSwitch1" name="voiceMailSwitch" value="1" <c:if test="${hss.voiceMailSwitch==1}">checked="checked"</c:if> />
			<label><spring:message code="StaticGrouping"/></label>
			<label><spring:message code="IPAddress"/>:</label><input class="easyui-textbox" id="updateVoiceMailNum" name="voiceMailNum" value="${hss.voiceMailNum}" data-options="validType:'ip',required:true"/>
			<br/>
			<label><input type="checkbox" id="updateOspfzb" name="ospfMulticast" value="${hss.ospfMulticast}" <c:if test="${hss.ospfMulticast==1}">checked="checked"</c:if>/>OSPF组播</label>&nbsp;
			<label><input type="checkbox" id="updateOspfgb" name="ospfBroadcast" value="${hss.ospfBroadcast}" <c:if test="${hss.ospfBroadcast==1}">checked="checked"</c:if>/>OSPF广播</label>
		</div>
	</div>
	</c:if>
	<!-- 分组业务 结束 -->
	
	<!-- 分组业务JS开始 -->
	 <script type="text/javascript">;
	 	var region = $("#updateRegion");
		var regionChecked=region.is(":checked");
		var boxChecked1=$("#updateVoiceMailSwitch1").is(":checked");
		if(!boxChecked1){
			$("#updateVoiceMailNum").prop("disabled",true);
		}
		 if(regionChecked){
			 region.val("1");
			 $("#updateVoiceMailSwitch0").prop("disabled", false);
			 $("#updateVoiceMailSwitch1").prop("disabled", false);
			 $("#updateOspfzb").prop("disabled",false);
			 $("#updateOspfgb").prop("disabled",false);
		 }else{
			 region.val("0");
			 $("#updateVoiceMailSwitch0").prop("disabled", true);
			 $("#updateVoiceMailSwitch1").prop("disabled", true);
			 $("#updateOspfzb").prop("disabled",true);
			 $("#updateOspfgb").prop("disabled",true);
		 }
		var ospfzb=$("#updateOspfzb");
		var ospfgb=$("#updateOspfgb");
		var ospfzbChecked=ospfzb.is(":checked");
		var ospfgbChecked=ospfgb.is(":checked");
		if(ospfzbChecked){
			ospfzb.prop("checked",true);
		}else{
			ospfzb.prop("checked",false);
		}
		if(ospfgbChecked){
			ospfgb.prop("checked",true);
		}else{
			ospfgb.prop("checked",false);
		}
		region.bind('change', function() {
			var isChecked = region.prop('checked');
			if(isChecked){
				region.val("1");
			}else{
				region.val("0");
			}
			$("#updateVoiceMailSwitch0").prop("disabled", !isChecked);
			$("#updateVoiceMailSwitch1").prop("disabled", !isChecked);
			$('#updateVoiceMailNum').textbox({
				disabled : !isChecked
			});	
		});
		$('input[name=voiceMailSwitch]').bind('change', function() {
			var isStatic = $(this).val() == 1;
			$('#updateVoiceMailNum').textbox({
				disabled : !isStatic
			});
		});
		region.click(function() {
			var isChecked=region.prop("checked");
			if(isChecked){
				region.val("1");
				$("#updateOspfzb").prop("disabled",false);
				$("#updateOspfgb").prop("disabled",false);
			}else{
				region.val("0");
				$("#updateOspfzb").prop("disabled",true);
				$("#updateOspfgb").prop("disabled",true);
			}
		});
	 </script>
	 <!-- 分组业务JS结束 -->
	 
	<!-- 调度业务 开始 -->
	<c:if test="${ddyw==1}">
	<div id="updateScheduleService" class="easyui-panel" data-options="title:'<spring:message code="ScheduingBusiness"/>'">
		<div style="padding:10px 60px 20px 60px">
			<label><spring:message code="Priority"/>:</label>
			<select class="easyui-combobox" id="updatePriority" name="priority" data-options="editable:false,panelHeight:'100',width:100">
				<c:forEach items="${userPriority}" var="item">
					<option value="${item.priority}">${item.level}</option>
				</c:forEach>
			</select>
		</div>
	</div>
	</c:if>
	<!-- 调度业务 结束 -->
	
	<!-- 补充业务 开始 -->
	<c:if test="${bcyw==1}"> 
	<div id="updateSupplementService" class="easyui-panel" data-options="title:'<spring:message code="SupplementaryServices"/>'">
		<div style="padding:10px 60px 20px 60px">
			<input type="checkbox" name="fwdNoAnswer" value="${hss.fwdNoAnswer}" <c:if test="${hss.fwdNoAnswer==1}">checked="checked"</c:if>/>
			<label><spring:message code="NoResponseForwarding"/>:</label>
			<input class="easyui-numberbox" id="updatefwdNoAnswerNum" name="fwdNoAnswerNum" data-options="validType:'mdn',prompt:'<spring:message code="PleaseEnterThePhoneNumber"/>',required:true" value="${hss.fwdNoAnswerNum}"/>
			&nbsp;&nbsp;
			<input type="checkbox" name="fwdOnBusy" value="${hss.fwdOnBusy}" <c:if test="${hss.fwdOnBusy==1}">checked="checked"</c:if>/>
			<label><spring:message code="CallForwarding"/>:</label>
			<input class="easyui-numberbox" id="updatefwdOnBusyNum" name="fwdOnBusyNum" data-options="validType:'mdn',prompt:'<spring:message code="PleaseEnterThePhoneNumber"/>',required:true" value="${hss.fwdOnBusyNum}"/>
			<br/>
			<input type="checkbox" name="directFwd" value="${hss.directFwd}" <c:if test="${hss.directFwd==1}">checked="checked"</c:if>/>
			<label><spring:message code="UnconditionalForwarding"/>:</label>
			<input class="easyui-numberbox" id="updatedirectFwdNum" name="directFwdNum" data-options="validType:'mdn',prompt:'<spring:message code="PleaseEnterThePhoneNumber"/>',required:true" value="${hss.directFwdNum}"/>
			&nbsp;&nbsp;
			<input type="checkbox" name="fwdNA" value="${hss.fwdNA}" <c:if test="${hss.fwdNA==1}">checked="checked"</c:if>/>
			<label><spring:message code="NoReachableForwarding"/>:</label>
			<input class="easyui-numberbox" id="updatefwdNANum" name="fwdNANum" data-options="validType:'mdn',prompt:'<spring:message code="PleaseEnterThePhoneNumber"/>', required:true" value="${hss.fwdNANum}"/>
		</div>
	</div>
	</c:if>
	<!-- 补充业务 结束 -->
	
	<!-- 补充业务 JS -->
	<script type="text/javascript">	 
	 	var directFwdIsChecked=$("input[name=directFwd]").prop("checked");
	 	if(!directFwdIsChecked){
	 		$("#directFwd").val("0");
	 		$("#updatedirectFwdNum").attr("disabled",true);
	 	}
	 	if(directFwdIsChecked){
	 		$("#directFwd").val("1");
	 		$("#updatedirectFwdNum").attr("disabled",false);
	 	}
	 	var fwdNoAnswerIsChecked=$("input[name=fwdNoAnswer]").prop("checked");
	 	if(!fwdNoAnswerIsChecked){
	 		$("#fwdNoAnswer").val("0");
	 		$("#updatefwdNoAnswerNum").attr("disabled",true);
	 	}
	 	if(fwdNoAnswerIsChecked){
	 		$("#fwdNoAnswer").val("1");
	 		$("#updatefwdNoAnswerNum").attr("disabled",false);
	 	}
	 	var fwdOnBusyIsChecked=$("input[name=fwdOnBusy]").prop("checked");
	 	if(!fwdOnBusyIsChecked){
	 		$("#fwdOnBusy").val("0");
	 		$("#updatefwdOnBusyNum").attr("disabled",true);
	 	}
	 	if(fwdOnBusyIsChecked){
	 		$("#fwdOnBusy").val("1");
	 		$("#updatefwdOnBusyNum").attr("disabled",false);
	 	}
	 	var fwdNAIsChecked=$("input[name=fwdNA]").prop("checked");
	 	if(!fwdNAIsChecked){
	 		$("#fwdNA").val("0");
	 		$("#updatefwdNANum").attr("disabled",true);
	 	}
	 	if(fwdNAIsChecked){
	 		$("#fwdNA").val("1");
	 		$("#updatefwdNANum").attr("disabled",false);
	 	}
	 	
		$('#updateSupplementService input[type=checkbox]').bind('change',
			function() {
				var name = $(this).prop('name');
				var isChecked = $(this).prop('checked');	
				if(isChecked){
					$(this).val("1");
				}else{
					$(this).val("0");
				}
				$("#update"+name+"Num").textbox({
					disabled : !isChecked
				});
				if (name == 'directFwd' && isChecked) {
					$('#updateSupplementService input[type=checkbox][name!=directFwd]').prop(
							'checked', false).each(function() {
						var name = $(this).prop('name');
						var isChecked = $(this).prop('checked');
						$('#update' + name + 'Num').textbox({
							disabled : !isChecked
						});
					});
				} else if (name != 'directFwd' && isChecked) {
					$('#updateSupplementService input[type=checkbox][name=directFwd]').prop(
							'checked', false);
					$("#updatedirectFwdNum").textbox({
						disabled : isChecked
					});
				}
			}).each(function() {
				var name = $(this).prop('name');
				var isChecked = $(this).prop('checked');
				if(isChecked){
					$(this).val("1");
				}else{
					$(this).val("0");
				}
				$('#update' + name + 'Num').textbox({
					disabled : !isChecked
				});
		});			
	 </script>
	<!-- 补充业务 JS完毕 -->
	
	<!-- GroupInfo 开始  修改用户选择属于哪些组 -->
	<c:if test="${groupInfo==1}">
	<div id="updateGroupInfoService" class="easyui-panel" data-options="title:'<spring:message code="GroupMessage"/>'">
		<div style="padding:10px 10px 10px 60px">
			<c:forEach items="${groups}" var="group" varStatus="status">
				 <label for="g_${status.count}">
					<input id="g_${status.count}" type="checkbox" name="groups" value="${group.id}"
						<c:forEach items="${hss.groups}" var="hasGroup">
							<c:if test="${group.id==hasGroup}">checked</c:if>
						</c:forEach>/>${group.id}
				</label>
			</c:forEach>
		</div>
	</div>
	</c:if>
	<!-- GroupInfo 结束   修改用户选择属于哪些组 -->
	
	<!-- 鉴权参数 开始 -->
	<c:if test="${auc==1}">
	<div id="updateAucService" class="easyui-panel" data-options="title:'<spring:message code="AUC"/>'">
		<div style="padding:10px 60px 10px 60px">
			<table>
				<tr id="updateAmf">
					<td><label>AMF:</label></td>
					<td>
						<input class="easyui-textbox" data-options="validType:'checkParam',width:40" id="updateAmf1" name="amf1" value="${hss.amf1}"/>
						<label>-</label>
						<input class="easyui-textbox" data-options="validType:'checkParam',width:40" id="updateAmf2" name="amf2" value="${hss.amf2}"/>
					</td>
				</tr>
				<tr id="updateSqn">
					<td><label>SQN:</label></td>
					<td>
						<input class="easyui-textbox" data-options="validType:'checkParam',width:40" id="updateSqn1" name="sqn1" value="${hss.sqn1}"/>
						<label>-</label>
						<input class="easyui-textbox" data-options="validType:'checkParam',width:40" id="updateSqn2" name="sqn2" value="${hss.sqn2}"/>
						<label>-</label>
						<input class="easyui-textbox" data-options="validType:'checkParam',width:40" id="updateSqn3" name="sqn3" value="${hss.sqn3}"/>
						<label>-</label>
						<input class="easyui-textbox" data-options="validType:'checkParam',width:40" id="updateSqn4" name="sqn4" value="${hss.sqn4}"/>
						<label>-</label>
						<input class="easyui-textbox" data-options="validType:'checkParam',width:40" id="updateSqn5" name="sqn5" value="${hss.sqn5}"/>
						<label>-</label>
						<input class="easyui-textbox" data-options="validType:'checkParam',width:40" id="updateSqn6" name="sqn6" value="${hss.sqn6}"/>
					</td>
				</tr>
				<tr id="updateK">
					<td><label>K:</label></td>
					<td>
						<input class="easyui-textbox" data-options="validType:'checkParam',width:40" id="updateK1" name="k1" value="${hss.k1}"/>
						<label>-</label>
						<input class="easyui-textbox" data-options="validType:'checkParam',width:40" id="updateK2" name="k2" value="${hss.k2}"/>
						<label>-</label>
						<input class="easyui-textbox" data-options="validType:'checkParam',width:40" id="updateK3" name="k3" value="${hss.k3}"/>
						<label>-</label>
						<input class="easyui-textbox" data-options="validType:'checkParam',width:40" id="updateK4" name="k4" value="${hss.k4}"/>
						<label>-</label>
						<input class="easyui-textbox" data-options="validType:'checkParam',width:40" id="updateK5" name="k5" value="${hss.k5}"/>
						<label>-</label>
						<input class="easyui-textbox" data-options="validType:'checkParam',width:40" id="updateK6" name="k6" value="${hss.k6}"/>
						<label>-</label>
						<input class="easyui-textbox" data-options="validType:'checkParam',width:40" id="updateK7" name="k7" value="${hss.k7}"/>
						<label>-</label>
						<input class="easyui-textbox" data-options="validType:'checkParam',width:40" id="updateK8" name="k8" value="${hss.k8}"/>
						<label>-</label>
						<input class="easyui-textbox" data-options="validType:'checkParam',width:40" id="updateK9" name="k9" value="${hss.k9}"/>
						<label>-</label>
						<input class="easyui-textbox" data-options="validType:'checkParam',width:40" id="updateK10" name="k10" value="${hss.k10}"/>
						<label>-</label>
						<input class="easyui-textbox" data-options="validType:'checkParam',width:40" id="updateK11" name="k11" value="${hss.k11}"/>
						<label>-</label>
						<input class="easyui-textbox" data-options="validType:'checkParam',width:40" id="updateK12" name="k12" value="${hss.k12}"/>
						<label>-</label>
						<input class="easyui-textbox" data-options="validType:'checkParam',width:40" id="updateK13" name="k13" value="${hss.k13}"/>
						<label>-</label>
						<input class="easyui-textbox" data-options="validType:'checkParam',width:40" id="updateK14" name="k14" value="${hss.k14}"/>
						<label>-</label>
						<input class="easyui-textbox" data-options="validType:'checkParam',width:40" id="updateK15" name="k15" value="${hss.k15}"/>
						<label>-</label>
						<input class="easyui-textbox" data-options="validType:'checkParam',width:40" id="updateK16" name="k16" value="${hss.k16}"/>
					</td>
				</tr>
				<tr id="updateOp">
					<td><label>OP:</label></td>
					<td>
						<input class="easyui-textbox" data-options="validType:'checkParam',width:40"  id="updateOp1" name="op1" value="${hss.op1}"/>
						<label>-</label>
						<input class="easyui-textbox" data-options="validType:'checkParam',width:40"  id="updateOp2" name="op2" value="${hss.op2}"/>
						<label>-</label>
						<input class="easyui-textbox" data-options="validType:'checkParam',width:40"  id="updateOp3" name="op3" value="${hss.op3}"/>
						<label>-</label>
						<input class="easyui-textbox" data-options="validType:'checkParam',width:40"  id="updateOp4" name="op4" value="${hss.op4}"/>
						<label>-</label>
						<input class="easyui-textbox" data-options="validType:'checkParam',width:40"  id="updateOp5" name="op5" value="${hss.op5}"/>
						<label>-</label>
						<input class="easyui-textbox" data-options="validType:'checkParam',width:40"  id="updateOp6" name="op6" value="${hss.op6}"/>
						<label>-</label>
						<input class="easyui-textbox" data-options="validType:'checkParam',width:40"  id="updateOp7" name="op7" value="${hss.op7}"/>
						<label>-</label>
						<input class="easyui-textbox" data-options="validType:'checkParam',width:40"  id="updateOp8" name="op8" value="${hss.op8}"/>
						<label>-</label>
						<input class="easyui-textbox" data-options="validType:'checkParam',width:40"  id="updateOp9" name="op9" value="${hss.op9}"/>
						<label>-</label>
						<input class="easyui-textbox" data-options="validType:'checkParam',width:40"  id="updateOp10" name="op10" value="${hss.op10}"/>
						<label>-</label>
						<input class="easyui-textbox" data-options="validType:'checkParam',width:40"  id="updateOp11" name="op11" value="${hss.op11}"/>
						<label>-</label>
						<input class="easyui-textbox" data-options="validType:'checkParam',width:40"  id="updateOp12" name="op12" value="${hss.op12}"/>
						<label>-</label>
						<input class="easyui-textbox" data-options="validType:'checkParam',width:40"  id="updateOp13" name="op13" value="${hss.op13}"/>
						<label>-</label>
						<input class="easyui-textbox" data-options="validType:'checkParam',width:40"  id="updateOp14" name="op14" value="${hss.op14}"/>
						<label>-</label>
						<input class="easyui-textbox" data-options="validType:'checkParam',width:40"  id="updateOp15" name="op15" value="${hss.op15}"/>
						<label>-</label>
						<input class="easyui-textbox" data-options="validType:'checkParam',width:40"  id="updateOp16" name="op16" value="${hss.op16}"/>
					</td>
				</tr>
				<tr id="updateOpc">
					<td><label>OPC:</label></td>
					<td>
						<input class="easyui-textbox" data-options="validType:'checkParam',width:40"  id="updateOpc1" name="opc1" value="${hss.opc1}"/>
						<label>-</label>
						<input class="easyui-textbox" data-options="validType:'checkParam',width:40"  id="updateOpc2" name="opc2" value="${hss.opc2}"/>
						<label>-</label>
						<input class="easyui-textbox" data-options="validType:'checkParam',width:40"  id="updateOpc3" name="opc3" value="${hss.opc3}"/>
						<label>-</label>
						<input class="easyui-textbox" data-options="validType:'checkParam',width:40"  id="updateOpc4" name="opc4" value="${hss.opc4}"/>
						<label>-</label>
						<input class="easyui-textbox" data-options="validType:'checkParam',width:40"  id="updateOpc5" name="opc5" value="${hss.opc5}"/>
						<label>-</label>
						<input class="easyui-textbox" data-options="validType:'checkParam',width:40"  id="updateOpc6" name="opc6" value="${hss.opc6}"/>
						<label>-</label>
						<input class="easyui-textbox" data-options="validType:'checkParam',width:40"  id="updateOpc7" name="opc7" value="${hss.opc7}"/>
						<label>-</label>
						<input class="easyui-textbox" data-options="validType:'checkParam',width:40"  id="updateOpc8" name="opc8" value="${hss.opc8}"/>
						<label>-</label>
						<input class="easyui-textbox" data-options="validType:'checkParam',width:40"  id="updateOpc9" name="opc9" value="${hss.opc9}"/>
						<label>-</label>
						<input class="easyui-textbox" data-options="validType:'checkParam',width:40"  id="updateOpc10" name="opc10" value="${hss.opc10}"/>
						<label>-</label>
						<input class="easyui-textbox" data-options="validType:'checkParam',width:40"  id="updateOpc11" name="opc11" value="${hss.opc11}"/>
						<label>-</label>
						<input class="easyui-textbox" data-options="validType:'checkParam',width:40"  id="updateOpc12" name="opc12" value="${hss.opc12}"/>
						<label>-</label>
						<input class="easyui-textbox" data-options="validType:'checkParam',width:40"  id="updateOpc13" name="opc13" value="${hss.opc13}"/>
						<label>-</label>
						<input class="easyui-textbox" data-options="validType:'checkParam',width:40"  id="updateOpc14" name="opc14" value="${hss.opc14}"/>
						<label>-</label>
						<input class="easyui-textbox" data-options="validType:'checkParam',width:40"  id="updateOpc15" name="opc15" value="${hss.opc15}"/>
						<label>-</label>
						<input class="easyui-textbox" data-options="validType:'checkParam',width:40"  id="updateOpc16" name="opc16" value="${hss.opc16}"/>
					</td>
				</tr>				
			</table>
		</div>
		<div style="padding:0px 60px 20px 60px">
			<label>TIME:</label>
			<input class="easyui-timespinner" style="width:90px;" name="start"
				required="required" data-options="showSeconds:true,editable:false" value="${hss.start}">
			<label>-</label>
			<input class="easyui-timespinner" style="width:90px;" name="stop"
				required="required" data-options="showSeconds:true,editable:false" value="${hss.stop}">
		</div>
	</div>
	</c:if>
	<!-- 鉴权参数 结束 -->
	
	<!-- TFT设置  开始-->
	<c:if test="${tft==1}">
	<div id="updateTftSetService" class="easyui-panel" data-options="title:'<spring:message code="TFTSetting"/>'">
		<div style="padding:10px 60px 20px 60px">
			<table>
	    		<tr>
	    			<td><label><spring:message code="IPPackageSourceIP"/>:</label></td>
	    			<td><input class="easyui-textbox" id="updateSrcIp" name="srcIp" data-options="value:'${hss.srcIp}',validType:'ip'"/></td>
	    			<td><label><spring:message code="IPPackageDestinationIP"/>:</label></td>
	    			<td><input class="easyui-textbox" id="updateDstIp" name="dstIp" data-options="value:'${hss.dstIp}',validType:'ip'"/></td>
				</tr>
				<tr>
	    			<td><label>IP包源端口开始值:</label></td>
	    			<td><input class="easyui-numberbox" id="updateSrcPortStart" name="srcPortStart" data-options="value:'${hss.srcPortStart}',min:0"/></td>
	    			<td><label>IP包源端口结束值:</label></td>
	    			<td><input class="easyui-numberbox" id="updateSrcPortEnd" name="srcPortEnd" data-options="value:'${hss.srcPortEnd}',min:0"/></td>
				</tr>
				<tr>
	    			<td><label>IP包目的端口开始值:</label></td>
	    			<td><input class="easyui-numberbox" id="updateDstPortStart" name="dstPortStart" data-options="value:'${hss.dstPortStart}',min:0"/></td>
	    			<td><label>IP包目的端口结束值:</label></td>
	    			<td><input class="easyui-numberbox" id="updateDstPortEnd" name="dstPortEnd" data-options="value:'${hss.dstPortEnd}',min:0"/></td>
				</tr>
				<tr>
	    			<td><label><spring:message code="DSCPStartValue"/>:</label></td>
	    			<td><input class="easyui-numberbox" id="updateDiffServStart" name="diffServStart" data-options="value:'${hss.diffServStart}',min:0"/></td>
	    			<td><label><spring:message code="DSCPEndValue"/>:</label></td>
	    			<td><input class="easyui-numberbox" id="updateDiffServEnd" name="diffServEnd" data-options="value:'${hss.diffServEnd}',min:0"/></td>
				</tr>
				<tr>
	    			<td><label><spring:message code="IPPackageLengthMin"/>:</label></td>
	    			<td><input class="easyui-numberbox" id="updatePktLenMin" name="pktLenMin" data-options="value:'${hss.pktLenMin}',min:0"/></td>
	    			<td><label><spring:message code="IPPackageLengthMax"/>:</label></td>
	    			<td><input class="easyui-numberbox" id="updatePktLenMax" name="pktLenMax" data-options="value:'${hss.pktLenMax}',min:0"/></td>
				</tr>
			</table>
		</div>
	</div>
	</c:if>
	<!-- TFT设置 结束-->
	
	<!-- 终端信息 开始 -->
	<c:if test="${zdxx==1}">
	<div id="updateTerminalInfoService" class="easyui-panel" data-options="title:'<spring:message code="TerminalInformation"/>'">
		<div style="padding:10px 60px 20px 60px">
			<table>
	    		<tr>
	    			<td><label><spring:message code="TerminalNumber"/>:</label></td>
	    			<td><input class="easyui-textbox" id="updateTerminalId" name="terminalId" value="${hss.terminalId}" data-options="width:143,validType:'terminalLength'"/></td>
	    			<td><label><spring:message code="TerminalType"/>:</label></td>
	    			<td>
	    				<select class="easyui-combobox" id="updateTerminalType" name="terminalType"
	    					data-options="width:143,editable:false,panelHeight:'100'">
							<c:forEach items="${terminalTypes}" var="terminal">
								<option value="${terminal.terminalId }" <c:if test="${terminal.terminalId==hss.terminalType }">selected</c:if>>${terminal.terminalName}</option>
							</c:forEach>
	    				</select>　　
	    			</td>
	    		</tr>
	    		
	    		<tr>
	    			<td><label><spring:message code="GWId"/>:</label></td>
	    			<td><input class="easyui-numberbox" id="updateGwId" name="gwId" data-options="width:143,value:'${hss.gwId}',editable:false"/></td>
	    			<td><label><spring:message code="MaxPowerLevel"/>:</label></td>
	    			<td>
	    				<select class="easyui-combobox" id="updatePowerLevel" name="powerLevel" data-options="width:143,editable:false,panelHeight:'100'">
	    					<c:forEach var="i" begin="1" end="7" step="1">
								<option value="${i}" <c:if test="${hss.powerLevel==i}">selected</c:if>>${i}</option>
							</c:forEach>
	    				</select>&nbsp;<span id="updateNote" style="color:red">${hss.powerLevelText}</span>
	    			</td>
	    		</tr>
	    		
	    		<tr>
	    			<td><label><spring:message code="Department"/>:</label></td>
	    			<td>
	    				<input class="easyui-textbox" id="updateDepartment" name="department" data-options="width:143,validType:'departmentLength',value:'${hss.department}'"/>
	    			</td>
	    			<td><label><spring:message code="UserType"/>:</label></td>
	    			<td>
	    				<select class="easyui-combobox" id="updateUserType" name="userType" data-options="width:143,editable:false,panelHeight:'auto'">
							<option value="1" <c:if test="${hss.userType==1}">selected</c:if>>军用用户</option>
	    				</select>
	    			</td>
	    		</tr>
	    		
	    		<tr>
	    			<td><span hidden="true"><label><spring:message code="UserName"/>:</label></span></td>
	    			<td><span hidden="true"><input class="easyui-textbox" id="updateUserName" name="username" data-options="value:'${hss.username}'"/></span></td>
	    			<td><span hidden="true"><label><spring:message code="UserInfo"/>:</label></span></td>
	    			<td colspan="3"><span hidden="true"><input class="easyui-textbox" id="updateUserInfo" name="userInfo" data-options="value:'${hss.userInfo}',width:370"/></span></td>
	    			<td><span hidden="true"><label>服务优先级:</label></span></td>
	    			<td>
		    			<span hidden="true">
			    			<select class="easyui-combobox" id="updateServicePriority" name="servicePriority" data-options="width:130,editable:false,panelHeight:'100'">
									<c:forEach var="i" begin="1" end="5" step="1">
										<option value="${i}" <c:if test="${hss.servicePriority==i}">selected</c:if>>${i}</option>
									</c:forEach>
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
	<c:if test="${hss.apn!=null}">
	<div id="updateEpcGroupDataService" class="easyui-panel" data-options="title:'EPC分组数据'">
		<div style="padding:10px 60px 20px 60px">
			<table>
	    		<tr>
	    			<td><label>APN:</label></td>
	    			<td><input class="easyui-textbox" id="updateApn" name="apn" data-options="width:143,value:'${hss.apn}'<c:if test="${epc==1}">,required:true</c:if>"/>　　</td>
	    			<td><label>ErabId:</label></td>
	    			<td><input class="easyui-numberbox" id="updateErabId" name="erabId" data-options="width:143,value:'${hss.erabId}',min:0<c:if test="${epc==1}">,required:true</c:if>"/></td>
	    		</tr>
	    		<tr>
	    			<td><label>QCI:</label></td>
	    			<td><input class="easyui-numberbox" id="updateQci" name="qci" data-options="width:143,value:'${hss.qci}',min:0<c:if test="${epc==1}">,required:true</c:if>"/></td>
	    			<td><label>ARPriority:</label></td>
	    			<td><input class="easyui-numberbox" id="updateArPriority" name="arPriority" data-options="width:143,value:'${hss.arPriority}',min:0<c:if test="${epc==1}">,required:true</c:if>"/></td>
	    		</tr>
	    		<tr>
	    			<td><label>AggregateMaxULBitrate:</label></td>
	    			<td><input class="easyui-numberbox" id="updateAggregateMaxULBitRate" name="aggregateMaxULBitRate" data-options="width:143,value:'${hss.aggregateMaxULBitRate}',min:0<c:if test="${epc==1}">,required:true</c:if>"/></td>
	    			<td><label>AggregateMaxDLBitrate:</label></td>
	    			<td><input class="easyui-numberbox" id="updateAggregateMaxDLBitRate" name="aggregateMaxDLBitRate" data-options="width:143,value:'${hss.aggregateMaxDLBitRate}',min:0<c:if test="${epc==1}">,required:true</c:if>"/></td>
	    		</tr>
	    		<tr>
	    			<td><label>GuaranteedULBitRate:</label></td>
	    			<td><input class="easyui-numberbox" id="updateGuaranteedULBitRate" name="guaranteedULBitRate" data-options="width:143,value:'${hss.guaranteedULBitRate}',min:0<c:if test="${epc==1}">,required:true</c:if>"/></td>
	    			<td><label>GuaranteedDLBitRate:</label></td>
	    			<td><input class="easyui-numberbox" id="updateGuaranteedDLBitRate" name="guaranteedDLBitRate" data-options="width:143,value:'${hss.guaranteedDLBitRate}',min:0<c:if test="${epc==1}">,required:true</c:if>"/></td>
	    		</tr>
	    		<tr>
	    			<td><label>MaxULBitRate:</label></td>
	    			<td><input class="easyui-numberbox" id="updateMaxULBitRate" name="maxULBitRate" data-options="width:143,value:'${hss.maxULBitRate}',min:0<c:if test="${epc==1}">,required:true</c:if>"/></td>
	    			<td><label>MaxDLBitRate:</label></td>
	    			<td><input class="easyui-numberbox" id="updateMaxDLBitRate" name="maxDLBitRate" data-options="width:143,value:'${hss.maxDLBitRate}',min:0<c:if test="${epc==1}">,required:true</c:if>"/></td>
	    		</tr>
	    	</table>
		</div>
	</div>
	</c:if>
	</c:if>
	<!-- EPC分组数据  结束 -->
	
    </form>

</body>
</html>
