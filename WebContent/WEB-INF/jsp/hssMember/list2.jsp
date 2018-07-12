<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>会议成员列表</title>
<%@ include file="/WEB-INF/jsp/public/common.jspf"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/hssMember/list2.js?<%=new Date().getTime()%>"></script>
<script>
$(function(){
	$('#cc').combobox({    
	    url:'${pageContext.request.contextPath}/meetingMember/listNum.action',    
	    valueField:'valueField',    
	    textField:'textField',
	    onLoadSuccess:function(){
	    var data = $('#cc').combobox('getData');
	    	if(data.length>0){
	    		$('#cc').combobox('select',data[0].textField);
	    	}
	    }
	});  
});
</script>
</head>
<body>
	<table id="dg" title="<spring:message code="GroupMemberList"/>" class="easyui-datagrid" style="width:800px;" data-options="
				url:'${pageContext.request.contextPath}/meetingMember/list.action',
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
			<th data-options="field:'groupId',width:'15%'"><spring:message code="GroupNumber"/></th>
			<th data-options="field:'mdn',width:'15%'"><spring:message code="MDN"/></th>
			<th data-options="field:'priority',hidden:true"><spring:message code="GroupMemberPriority"/></th>
			<th data-options="field:'role',
				formatter:function(value,row,rowIndex){
					switch(value){
					case 1:
						return '<spring:message code="Chairman"/>';
					case 2:
						return '<spring:message code="GroupMember"/>';
					case 4:
						return '<spring:message code="Dispatcher"/>';
					default:
						return value;
					}
				}"><spring:message code="GroupMemberRole"/></th>
			
			<th data-options="field:'service',hidden:true"><spring:message code="GroupMemberService"/></th>
			<th data-options="field:'addtion',hidden:true"><spring:message code="GroupMemberAdditionalService"/></th>
			<th data-options="field:'修改',
				formatter:function(value,row,rowIndex){
					return '<a href=#><spring:message code="Update"/></a>'
				}"><spring:message code="Update"/></th>
		</tr>
	</thead>
	</table>
	<div id="tb" style="height:auto">
		<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="append()"><spring:message code="Add"/></a>
		<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="removeit()"><spring:message code="Delete"/></a>
		<input id="ss" class="easyui-searchbox" style="width:250px" 
			data-options="searcher:qq,prompt:'<spring:message code="PleaseEnterTheNumber"/>',menu:'#mm'"/>
			
		<!-- 返回操作 -->
		<a href="${pageContext.request.contextPath}/meetingGroup/listUI2.action" class="easyui-linkbutton" 
				data-options="iconCls:'icon-back',plain:true">返回</a>	
	</div>
	<div id="mm" style="width:120px"> 
		<div data-options="name:'groupId'"><spring:message code="GroupNumber"/></div>
		<div data-options="name:'mdn'"><spring:message code="MDN"/></div>
	</div>
	
	<div id="w" class="easyui-window" data-options="
			modal:true,
			closed:true,
			minimizable:false,
			maximizable:false,
			collapsible:false,
			novalidate:true,
			draggable:false
		">
		<form id="ff" class="easyui-form" method="post" data-options="novalidate:true">
	    	<table>
	    		<tr>
	    			<td><label><spring:message code="MDN"/>:</label></td>
	    			<td><input class="easyui-numberbox" id="mdn" name="mdn" data-options="min:0,required:true"/>　　</td>
	    			<td><label><spring:message code="GroupNumber"/>:</label></td>
	    			<td>
	    				<!--  <select class="easyui-combobox" id="groupId" name="groupId"  data-options="required:true,editable:false,panelHeight:'auto',width:100">
	    					<c:forEach items="${groupNums}" var="item">
								<option value="${item.key}">${item.value}</option>
							</c:forEach>
	    				</select>
	    				-->
	    				<input id="cc" class="easyui-combobox" name="groupId"/>  
	    			</td>
	    			<!-- <td><input class="easyui-textbox" id="groupId" name="groupId" data-options="min:0,required:true"/></td> -->
	    		</tr>
	    		<tr>
	    			<td><label><spring:message code="GroupMemberRole"/>:</label></td>
	    			<td>
	    				<select class="easyui-combobox" name="role" data-options="required:true,editable:false,panelHeight:'auto',width:130">
							<option value="1"><spring:message code="Chairman"/></option>
							<option value="2"><spring:message code="GroupMember"/></option>
							<%-- <option value="4"><spring:message code="Dispatcher"/></option> --%>
	    				</select>
	    			</td>
	    			<td style="display:none"><label><spring:message code="GroupMemberPriority"/>:</label></td>
	    			<td style="display:none">
	    				<select class="easyui-combobox" name="priority"
							data-options="editable:false,width:130,panelHeight:'auto'">
							<c:forEach begin="1" end="5" step="1" var="i">
								<option value="${i}" <c:if test="${i==i}">selected</c:if>>${i}</option>
							</c:forEach>
						</select>
					</td>
	    		</tr>
	    		<tr style="display:none">
	    			<td><label><spring:message code="GroupMemberService"/>:</label></td>
	    			<td><input class="easyui-numberbox" name="service" data-options="value:'0',required:true,min:0"/></td>
	    			<td><label><spring:message code="GroupMemberAdditionalService"/>:</label></td>
	    			<td><input class="easyui-numberbox" name="addtion" data-options="value:'0',required:true,min:0"/></td>
	    		</tr>
	    	</table>
	    </form>
	    <div style="text-align:center;padding:5px">
	    	<a href="#" class="easyui-linkbutton" onclick="submitForm()">　<spring:message code="save"/></a>
	    	<%-- <a href="#" class="easyui-linkbutton" onclick="clearForm()"><spring:message code="ClearRecord"/></a> --%>
	    </div>
	 </div>
</body>
</html>