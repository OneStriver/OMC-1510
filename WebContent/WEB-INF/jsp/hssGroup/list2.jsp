<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/WEB-INF/jsp/public/common.jspf"%>
<html>
<head>
<title>会议组列表</title>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/hssGroup/list2.js?<%=new Date().getTime()%>"></script>
</head>
<body>
	<table id="dg" title="<spring:message code="meetingGroupList"/>" class="easyui-datagrid" data-options="
				url:'${pageContext.request.contextPath}/meetingGroup/list.action',
				width:800,
				border:false,
				rownumbers:true,
				fit:true,striped:true,
				pageList: [10,20,30,40,50],
				pageNumber:${pageBean.page},
				singleSelect:false,
				pagination:true,
				toolbar: '#tb',onClickCell:onClickCell,
				pageSize: ${pageBean.pageSize}">
		<thead>
		<tr>
			<th data-options="field:'ck',checkbox:true"></th>
			<th data-options="field:'id',width:'15%'"><spring:message code="GroupNumber"/></th>
			<th data-options="field:'tmsi'"><spring:message code="GroupTMSI"/></th>
			<th data-options="field:'callType',
				formatter:function(value,row,rowIndex){
					switch(value){
					case 1:
						return '<spring:message code="Full-duplex"/>';
					case 2:
						return '<spring:message code="Half-duplex"/>';
					case 3:
						return '<spring:message code="Broadcast"/>';
					default:
						return value.toString();
					}
				}"><spring:message code="GroupCallType"/></th>
			<th data-options="field:'busiType',
				formatter:function(value,row,rowIndex){
						switch(value){
						case 1:
							return '<spring:message code="Voice"/>';
						case 2:
							return '<spring:message code="CSData"/>';
						case 3:
							return '<spring:message code="PSData"/>';
						default:
							return value.toString();
						}
				}"><spring:message code="BusinessType"/></th>
			<th data-options="field:'priority',hidden:true,
				formatter:function(value,row,rowIndex){
					return value-10;
				}"><spring:message code="GroupPriority"/></th>
			<th data-options="field:'location'"><spring:message code="GroupBusinessLocation"/></th>
			<th data-options="field:'显示详情',
				formatter:function(value,row,rowIndex){
					return '<a href=${pageContext.request.contextPath}/meetingMember/listUI2.action?meetingGroupId='+row.id+'>查看会议组成员</a>';
				}">详细信息</th>
		</tr>
	</thead>
	</table>
	<div id="tb" style="height:auto">
		<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="append()"><spring:message code="Add"/></a>
		<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="removeit()"><spring:message code="Delete"/></a>
		<input id="ss" class="easyui-searchbox" style="width:250px" 
			data-options="searcher:qq,prompt:'<spring:message code="PleaseEnterTheNumber"/>',menu:'#mm'"/>
	</div>
	<div id="mm" style="width:120px"> 
		<div data-options="name:'id'"><spring:message code="GroupNumber"/></div>
	</div>
	
	<div id="w" class="easyui-window"
			data-options="modal:true,closed:true,minimizable:false,maximizable:false,collapsible:false">
		<form id="ff" class="easyui-form" method="post" data-options="novalidate:true" action="${pageContext.request.contextPath}/hssGroup/add.action">
	    	<table>
	    		<tr>
	    			<td><label><spring:message code="GroupNumber"/>:</label></td>
	    			<td><input class="easyui-numberbox" name="id" id="id" data-options="validType:'length[1,15]',min:0,required:true"/>　　</td>
	    			<td style="display:none"><label><spring:message code="GroupTMSI"/>:</label></td>
	    			<td style="display:none"><input class="easyui-numberbox" name="tmsi" data-options="min:0"/></td>
	    		</tr>
	    		<tr style="display:none">
	    			<td><label><spring:message code="GroupCallType"/>:</label></td>
	    			<td>
	    				<select class="easyui-combobox" name="callType" data-options="editable:false,required:true,panelHeight:'auto',width:130">
							<option value="1" selected><spring:message code="Full-duplex"/></option>
							<option value="2"><spring:message code="Half-duplex"/></option>
							<option value="3"><spring:message code="Broadcast"/></option>
	    				</select>
	    			</td>
	    			<td><label><spring:message code="BusinessType"/>:</label></td>
	    			<td>
	    				<select class="easyui-combobox" name="busiType" data-options="editable:false,required:true,panelHeight:'auto',width:130">
							<option value="1" selected><spring:message code="Voice"/></option>
							<option value="2"><spring:message code="CSData"/></option>
							<option value="3"><spring:message code="PSData"/></option>
	    				</select>
	    			</td>
	    		</tr>
	    		<tr style="display:none">
	    			<td><label><spring:message code="GroupPriority"/>:</label></td>
	    			<td>
	    				<select class="easyui-combobox" name="priority" data-options="editable:false,required:true,panelHeight:'auto',width:130">
	    				<c:forEach begin="1" end="5" step="1" var="i">
							<option value="${i+10}" <c:if test="${i==1}">selected</c:if>>${i}</option>
						</c:forEach>
						</select>
	    			</td>
	    			<td><label><spring:message code="GroupBusinessLocation"/>:</label></td>
	    			<td><input class="easyui-textbox" name="location" data-options="disabled:true,min:0"/></td>
	    		</tr>
	    		<tr style="display:none">
	    			<td><label><spring:message code="GroupStatus"/>:</label></td>
	    			<td>
	    				<select class="easyui-combobox" name="status" data-options="editable:false,required:true,panelHeight:'auto',width:130">
							<option value="0" selected><spring:message code="NotCall"/></option>
							<option value="1"><spring:message code="iscalling"/></option>
						</select>
	    			</td>
	    		</tr>
	    	</table>
	    </form>
	    <div style="text-align:center;padding:5px">
	    	<a href="#" class="easyui-linkbutton" onclick="submitForm()">　<spring:message code="save"/>　</a>
	    	<a href="#" class="easyui-linkbutton" onclick="clearForm()"><spring:message code="ClearRecord"/></a>
	    </div>
	 </div>
</body>
</html>