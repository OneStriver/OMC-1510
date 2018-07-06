<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/WEB-INF/jsp/public/common.jspf"%>
<html>
<head>
<title>组呼组列表</title>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/hssGroup/list.js?<%=new Date().getTime()%>"></script>
</head>
<body>
	<table id="dg" title="<spring:message code="GroupCallList"/>" class="easyui-datagrid" data-options="
				url:'${pageContext.request.contextPath}/hssGroup/list.action',
				width:800,
				border:false,
				rownumbers:true,
				fit:true,
				striped:true,
				pageList: [10,20,30,40,50],
				pageNumber:${pageBean.page},
				singleSelect:false,
				pagination:true,
				toolbar: '#tb',
				onClickCell:onClickCell,
				pageSize: ${pageBean.pageSize}">
		<!-- 表头字段 -->
		<thead>
		<tr>
			<th data-options="field:'ck',checkbox:true"></th>
			<th data-options="field:'id',width:'15%'"><spring:message code="GroupNumber"/></th>
			<th data-options="field:'groupType',
				formatter:function(value,row,rowIndex){
					switch(value){
					case 1:
						return '动态组';
					case 2:
						return '集群组';
					case 3:
						return '常规组';
					case 4:
						return '个人组';
					default:
						return value.toString();
					}
				}">组类型</th>
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
				<th data-options="field:'priority',formatter:function(value,row,rowIndex){
					if(value==0){
						return 0;
					}
					<c:forEach items="${groupPriority}" var="item">
						var convertInt = parseInt('${item.priority}');
						if(convertInt===value){
							return '${item.level}';
						}else{
							return '未知';
						}
					</c:forEach>
				}"><spring:message code="GroupPriority"/></th>
			<th data-options="field:'location',hidden:true"><spring:message code="GroupBusinessLocation"/></th>
			<th data-options="field:'status',hidden:true,
				formatter:function(value,row,rowIndex){
					return value==1?'<spring:message code="iscalling"/>':'<spring:message code="NotCall"/>';
				}"><spring:message code="GroupStatus"/></th>
			<th data-options="field:'createTime',width:150,
				formatter:function(value,row,rowIndex){
					if(typeof value==='number'){
						return new Date(value).format();
					}
					return value;
				}"><spring:message code="GroupCreateTime"></spring:message></th>
			<th data-options="field:'updateTime',width:150,
				formatter:function(value,row,rowIndex){
					if(typeof value==='number'){
						return new Date(value).format();
					}
					return value;
				},"><spring:message code="LastUpdateTime"/></th>
			<th data-options="field:'修改',
				formatter:function(value,row,rowIndex){
					return '<a href=#><spring:message code="Update"/></a>'
				}"><spring:message code="Update"/></th>
			<th data-options="field:'显示详情',
				formatter:function(value,row,rowIndex){
					return '<a href=${pageContext.request.contextPath}/groupMember/listUI.action?groupId='+row.id+'>查看组成员</a>';
				}">详细信息</th>
		</tr>
	</thead>
	</table>
	
	<!-- 表头的下拉列表信息 -->
	<div id="tb" style="height:auto">
		<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="append()"><spring:message code="Add"/></a>
		<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="removeit()"><spring:message code="Delete"/></a>
		<select class="easyui-combobox" name="callType"
			data-options="editable:false,onChange:callChange,width:110,panelHeight:'auto'">
			<option value="" selected="selected"><spring:message code="GroupCallType"/></option>
			<%-- 
			<option value="1"><spring:message code="Full-duplex"/></option> 
			--%>
			<option value="2"><spring:message code="Half-duplex"/></option>
			<%-- 
			<option value="3"><spring:message code="Broadcast"/></option> 
			--%>
		</select>
		<select class="easyui-combobox" name="busiType" 
			data-options="editable:false,width:110,onChange:busiChange,panelHeight:'auto'">
			<option value="" selected="selected"><spring:message code="BusinessType"></spring:message></option>
			<option value="1"><spring:message code="Voice"/></option>
			<%-- 
			<option value="2"><spring:message code="CSData"/></option>
			<option value="3"><spring:message code="PSData"/></option> 
			--%>
		</select>
		<!-- 搜索框 -->
		<input id="groupSearch" class="easyui-searchbox" style="width:250px" 
			data-options="searcher:qq,prompt:'<spring:message code="PleaseEnterTheNumber"/>',menu:'#groupMenu'"/>
	</div>
	
	<!-- 搜索框 -->
	<div id="groupMenu" style="width:120px"> 
		<div data-options="name:'id'"><spring:message code="GroupNumber"/></div>
	</div>
	
  	<!-- 添加窗口 -->
	<div id="addGroupWindow" class="easyui-window" data-options="
			modal:true,
			closed:true,
			minimizable:false,
			maximizable:false,
			collapsible:false">
		<form id="addGroupForm" class="easyui-form" method="post">
	    	<table>
	    		<tr>
	    			<!-- 组号 -->
	    			<td><label><spring:message code="GroupNumber"/>:</label></td>
	    			<td><input class="easyui-textbox" id="addGroupId" name="id" data-options="validType:'length[0,15]',min:0,required:true,width:130" value=""/></td>
	    			<td><label>组类型:</label></td>
	    			<td>
	    			<select class="easyui-combobox" id="addGroupType" name="groupType" data-options="editable:false,required:true,panelHeight:'auto',width:130">
						<option value="1">动态组</option>
						<option value="2">集群组</option>
						<!-- 
						<option value="3">常规组</option>
						<option value="4">个人组</option> 
						-->
    				</select>
    				</td>
	    		</tr>
	    		<!-- 组呼类型，业务类型 -->
	    		<tr>
	    			<td><label><spring:message code="GroupCallType"></spring:message>:</label></td>
	    			<td>
	    				<select class="easyui-combobox" id="addGroupCallType" name="callType" data-options="editable:false,required:true,panelHeight:'auto',width:130">
							<option value="2"><spring:message code="Half-duplex"></spring:message></option>
	    					<%-- 
							<option value="1"><spring:message code="Full-duplex"></spring:message></option>
							<option value="3"><spring:message code="Broadcast"></spring:message></option>
							 --%>
	    				</select>
	    			</td>
	    			<td><label><spring:message code="BusinessType"/>:</label></td>
	    			<td>
	    				<select class="easyui-combobox" id="addGroupBusiType" name="busiType" data-options="editable:false,required:true,panelHeight:'auto',width:130">
							<option value="1"><spring:message code="Voice"/></option>
							<%-- 
							<option value="2"><spring:message code="CSData"/></option>
							<option value="3"><spring:message code="PSData"/></option> 
							--%>
	    				</select>
	    			</td>
	    		</tr>
	    		<!-- 组优先级，组业务位置 -->
	    		<tr>
	    			<td><label><spring:message code="GroupPriority"/>:</label></td>
	    			<td>
	    				<select class="easyui-combobox" id="addGroupPriority" name="priority" data-options="editable:false,required:true,panelHeight:'auto',width:130">
	    				<c:forEach items="${groupPriority}" var="item">
							<option value="${item.priority}">${item.level}</option>
						</c:forEach>
						</select>
	    			</td>
	    			<td hidden="true"><label><spring:message code="GroupBusinessLocation"/>:</label></td>
	    			<td hidden="true"><input class="easyui-textbox" id="addGroupLocation" name="location" data-options="disabled:true,min:0"/></td>
	    		</tr>
	    		<!-- 组状态 -->
	    		<tr hidden="true">
	    			<td><label><spring:message code="GroupStatus"/>:</label></td>
	    			<td>
	    				<select class="easyui-combobox" id="addGroupStatus" name="status" data-options="editable:false,required:true,panelHeight:'auto',width:130">
							<option value="0"><spring:message code="NotCall"/></option>
							<option value="1"><spring:message code="iscalling"/></option>
						</select>
	    			</td>
	    		</tr> 
	    	</table>
	    </form>
	    <div style="text-align:center;padding:5px">
	    	<a href="#" class="easyui-linkbutton" onclick="addGroupSubmitForm()">　<spring:message code="save"/>　</a>
	    </div>
	 </div>
	 
	<!-- 修改窗口 -->
 	<div id="updateGroupWindow" class="easyui-window" data-options="
		modal:true,
		closed:true,
		minimizable:false,
		maximizable:false,
		collapsible:false">
		<form id="updateGroupForm" class="easyui-form" method="post" action="${pageContext.request.contextPath}/hssGroup/update.action" >
	    	<table>
	    		<tr>
	    			<!-- 组号 -->
	    			<td><label><spring:message code="GroupNumber"/>:</label></td>
	    			<td><input class="easyui-textbox" id="updateGroupId" name="id" data-options="validType:'length[0,15]',min:0,width:130"/></td>
	    			<td><label>组类型:</label></td>
	    			<td>
	    			<select class="easyui-combobox" id="updateGroupType" name="groupType" data-options="readonly:true,editable:false,required:true,panelHeight:'auto',width:130">
						<option value="1">动态组</option>
						<option value="2">集群组</option>
						<option value="3">常规组</option>
						<option value="4">个人组</option>
    				</select>
	    			</td>
	    		</tr>
	    		<!-- 组呼类型，业务类型 -->
	    		<tr>
	    			<td><label><spring:message code="GroupCallType"></spring:message>:</label></td>
	    			<td>
	    				<select class="easyui-combobox" id="updateGroupCallType" name="callType" data-options="editable:false,required:true,panelHeight:'auto',width:130">
							<option value="2"><spring:message code="Half-duplex"></spring:message></option>
							<option value="1"><spring:message code="Full-duplex"></spring:message></option>
							<option value="3"><spring:message code="Broadcast"></spring:message></option>
	    				</select>
	    			</td>
	    			<td><label><spring:message code="BusinessType"/>:</label></td>
	    			<td>
	    				<select class="easyui-combobox" id="updateGroupBusiType" name="busiType" data-options="editable:false,required:true,panelHeight:'auto',width:130">
							<option value="1"><spring:message code="Voice"/></option>
							<option value="2"><spring:message code="CSData"/></option>
							<option value="3"><spring:message code="PSData"/></option> 
	    				</select>
	    			</td>
	    		</tr>
	    		<!-- 组优先级，组业务位置 -->
	    		<tr>
	    			<td><label><spring:message code="GroupPriority"/>:</label></td>
	    			<td>
	    				<select class="easyui-combobox" id="updateGroupPriority" name="priority" data-options="editable:false,required:true,panelHeight:'auto',width:130">
		    				<c:forEach items="${groupPriority}" var="item">
								<option value="${item.priority}">${item.level}</option>
							</c:forEach>
						</select>
	    			</td>
	    			<td hidden="true"><label><spring:message code="GroupBusinessLocation"/>:</label></td>
	    			<td hidden="true"><input class="easyui-textbox" id="updateGroupLocation" name="location" data-options="disabled:true,min:0"/></td>
	    		</tr>
	    		<!-- 组状态 -->
	    		<tr hidden="true">
	    			<td><label><spring:message code="GroupStatus"/>:</label></td>
	    			<td>
	    				<select class="easyui-combobox" id="updateGroupStatus" name="status" data-options="editable:false,required:true,panelHeight:'auto',width:130">
							<option value="0"><spring:message code="NotCall"/></option>
							<option value="1"><spring:message code="iscalling"/></option>
						</select>
	    			</td>
	    		</tr>
	    	</table>
	    </form>
	    <div style="text-align:center;padding:5px">
	    	<a href="#" class="easyui-linkbutton" onclick="updateGroupSubmitForm()">　<spring:message code="save"/>　</a>
	    </div>
	 </div>
</body>
</html>