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
<script type="text/javascript">
$(function(){
	if('${isSave}'){
		$.messager.show({
			title:'<spring:message code="OperationTips"/>',msg:'<spring:message code="SaveCompleted"/>',
			showType:'fade',timeout:1000,
			style:{
				right:'',bottom:''
			}
		});
	}
});

</script>
<style type="text/css">
.pagination .pagination-num{
	width:3em;
}
</style>

</head>
<body>
	<table id="dg" title='<spring:message code="TerminalUserList"/>' class="easyui-datagrid" data-options="
				url:'${pageContext.request.contextPath}/hss/reduancylist.action',
				cache:false,
				width:800,
				border:false,
				rownumbers:true,
				fit:true,striped:true,
				pageList: [10,20,50,100,150,200],
				pageNumber:${pageBean.page},
				singleSelect:false,
				pagination:true,
				toolbar: '#tb',
				onClickCell:onClickCell,
				onRowContextMenu:onRowContextMenu,
				//onRowMouseOver:onRowMouseOver,
				//onRowMouseOut:onRowMouseOut,
				pageSize: ${pageBean.pageSize},
				onDblClickRow:onDblClickRow,
				rowStyler:rowStyler">
		<thead>
		<tr>
			<th data-options="field:'ck',checkbox:true"></th>
			<c:if test="${IMSI==1}"><th data-options="field:'imsi',width:'15%'"><spring:message code="IMSI"/></th></c:if>
			<c:if test="${MDN==1}"><th data-options="field:'mdn',width:'15%',editor:'textbox',
				formatter:function(value,row,rowIndex){
					return '<b>'+value+'</b>';
				}"><spring:message code="MDN"/></th></c:if>
			<c:if test="${DeviceName==1}"><th data-options="field:'deviceName'"><spring:message code="DeviceName"/></th></c:if>
			<th data-options="field:'status',hidden:true,
				formatter:function(value,row,rowIndex){
					return value==0?'<font color=red><spring:message code="Off-Line"/></font>':'<font color=green><spring:message code="On-Line"/></font>';
				}"><spring:message code="UserStatus"/></th>
			<c:if test="${SpeechCodec==1}"><th data-options="field:'vocodecName'"><spring:message code="SpeechCodec"/></th></c:if>
			<c:if test="${MacAddr==1}"><th data-options="field:'mscAddr',width:150,
				formatter:function(value,row,rowIndex){
					return row.currloc.split('@')[0];
				}"><spring:message code="MacAddr"/></th></c:if>
			<c:if test="${GateWayAddr==1}"><th data-options="field:'gwAddr',width:150,
				formatter:function(value,row,rowIndex){
					return row.currloc.split('@')[1];
				}"><spring:message code="GateWayAddr"/></th></c:if>
			<c:if test="${bmNum==1}"><th data-options="field:'acount_num'"><spring:message code="bmNum"/></th></c:if>
			<%-- <c:if test="${UeDestroy==1}"> --%>
			<th data-options="field:'destroy',
				formatter:function(value,row,rowIndex){
					if(value==1) return '否';
					else if (value==2) return '摇毙已确认';
					else if (value==3) return '恢复未确认';
					else if (value==4) return '遥毙未确认';
					else return '其他';
				}"><spring:message code="UeDestroy"/></th>
			<%-- </c:if> --%>
				
			<th data-options="field:'swoon',
				formatter:function(value,row,rowIndex){
					if(value==1) return '否';
					else if (value==2) return '摇晕已确认';
					else if (value==3) return '恢复未确认';
					else if (value==4) return '摇晕未确认';
					else return '其他';
				}">摇晕</th>
			<c:if test="${LastUpdateTime==1}"><th data-options="field:'tstamp',width:150,
				formatter:function(value,row,rowIndex){
					if(typeof value==='number'){
						return new Date(value).format();
					}
					return value;
				}"><spring:message code="LastUpdateTime"/></th></c:if>
			<th data-options="field:'currloc',hidden:true"></th>
			<!--<c:if test="${Update==1}"><th data-options="field:'修改',
				formatter:function(value,row,rowIndex){
					return '<a href=#><spring:message code="Update"/></a>';
				}"><spring:message code="Update"/></th></c:if>-->
		</tr>
	</thead>
	</table>
	<div id="tb" style="height:auto">
		<!-- <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="append()"><spring:message code="Add"/></a>
		<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="removeit()"><spring:message code="Delete"/></a> -->
		<select class="easyui-combobox" name="deviceType"
			data-options="editable:false,onChange:dChange,width:110,panelHeight:'auto'">
			<option value="" selected="selected"><spring:message code="AllDeviceType"/></option>
			<c:forEach items="${deviceTypes}" var="item">
				<option value="${item.key}">${item.value}</option>
			</c:forEach>
		</select>
		<select class="easyui-combobox" name="msvocodec" 
			data-options="editable:false,width:110,onChange:mChange,panelHeight:'auto'">
			<option value="" selected="selected"><spring:message code="AllEncodeType"/></option>
			<c:forEach items="${msvocodecs}" var="item">
				<option value="${item.key}">${item.value}</option>
			</c:forEach>
		</select>
		<select class="easyui-combobox" name="status" 
			data-options="editable:false,width:90,onChange:sChange,panelHeight:'auto'">
			<option value="" selected="selected"><spring:message code="AllStatus"/></option>
			<option value="0"><spring:message code="Off-Line"/></option>
			<option value="1"><spring:message code="On-Line"/></option>
		</select>
		<input id="ss" class="easyui-searchbox" style="width:250px" 
			data-options="validType:'integer',searcher:qq,prompt:'<spring:message code="PleaseEnterTheNumber"/>',menu:'#mm'"/>
		<!-- <a href="#" class="easyui-linkbutton" data-options="onClick:function(){$('#batchW').window('open');}"><spring:message code="ImportByNo"/></a>
		<a href="#" class="easyui-linkbutton" data-options="onClick:exportHss"><spring:message code="ExportSql"/></a>
		<a href="#" class="easyui-linkbutton" data-options="onClick:function(){$('#importHss').window('open');}"><spring:message code="ImportSql"/></a> -->
	</div>
	<div id="mm" style="width:120px"> 
	    <div data-options="name:'mdn'"><spring:message code="MDN"/></div> 
		<div data-options="name:'imsi'"><spring:message code="IMSI"/></div> 
	</div>
	
	<!-- 右键菜单 -->
	<div id="mm" class="easyui-menu" data-options="width:120">   
    	<div id="readStatus" data-options="name:'readStatus'"><spring:message code="ReadUerStatus"/></div>
    	<div id="syncStatus" data-options="name:'syncStatus'"><spring:message code="PushUserData"/></div>
    	<div id="ueDestroy" data-options="name:'ueDestroy'"><spring:message code="UeDestroy"/></div>
    	<div id="ueRestore" data-options="name:'ueRestore'"><spring:message code="UeRestore"/></div>
    </div>
	<!-- 读取用户状态对话窗口 -->
	<div id="dd" class="easyui-window" title="<spring:message code="UserStatus"/>" 
        data-options="iconCls:'icon-save',resizable:true,modal:false,closed:true,width:550">
        <table border="1" cellspacing="0" style="width:535px">
        	<tr>
        		<td colspan="2">
        			IMSI:<label title="UE-IMSI"></label> 
        			<spring:message code="MDN"/>:<label title="UE-MDN"></label> 
        			<spring:message code="bmNum"/>:<label title="BM"></label> 
        			LAC:<label title="CS-RncLoc"></label>
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
        	<tr>
        		<td colspan="2">
        			<spring:message code="GeographicalLocation"/>:<label title="CS-GeoLo"></label> 
        			<spring:message code="CurrentServiceVLR"/>:<label title="VLR"></label> 
        			<spring:message code="CurrentServiceSGSN"/>:<label title="SGSN"></label>
        		</td>
        	</tr>
        	<tr>
        		<td><spring:message code="CSDomainTMSI"/>:<label title="CS-TMSI"></label></td>
        		<td><spring:message code="PSDomainTMSI"/>:<label title="PS-TMSI"></label></td>
        	</tr>
        	<tr>
        		<td><spring:message code="CSDomainStatus"/>:<label title="CS-Status"></label></td>
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
	<div id="w" class="easyui-window"
        data-options="
        	onLoad:loadScript,
        	width:835,
        	iconCls:'icon-save',
        	modal:true,
        	title:'<spring:message code="AddTerminalUser"/>',
        	closed:true,
        	footer:'#footer'
        ">
	</div>
	
	<div id="footer" style="border:0px;text-align:center;">
	   	<a href="javascript:void()" class="easyui-linkbutton" onclick="submitForm()"><spring:message code="AddTerminalUser"/></a>
	   	<a href="javascript:void()" class="easyui-linkbutton" onclick="clearForm()"><spring:message code="ClearRecord"/></a>
	</div>
	
	<!-- 批量窗口 -->
	<div id="batchW" class="easyui-window"
        	data-options="title:'<spring:message code="InsertBatch"/>',iconCls:'icon-save',modal:true,closed:true,width:400,resizable:false,minimizable:false,maximizable:false">
        
        <%-- <form id="batchForm" method="post" enctype="multipart/form-data">
        <input type="hidden" name="payType" value="1">
        <fieldset>
        	<label><spring:message code="InsertNumber"/>:</label>
        	<input class="easyui-numberbox" name="batchCount" data-options="min:1,max:5000000,required:true">
        </fieldset>
        <fieldset>
        	<label><spring:message code="StartIMSI"/>:</label>
        	<input class="easyui-textbox" name="imsi" data-options="validType:'imsi',required:true">
        </fieldset>
        <fieldset>
        	<label><spring:message code="StartMDN"/>:</label>
        	<input class="easyui-textbox" name="mdn" data-options="validType:'mdn',required:true">
        </fieldset>
        <fieldset>
        	<label><spring:message code="StartESN"/>:</label>
        	<input class="easyui-textbox" name="esn"  data-options="validType:'esn',required:true">
        </fieldset>
        <fieldset>
        	<label><spring:message code="DeviceType"/>:</label>
        	<select class="easyui-combobox" name="deviceType" id="batchDevice" data-options="onSelect:onDeviceSelect"
				data-options="editable:false,width:130,panelHeight:'auto'">
				<c:forEach items="${deviceTypes}" var="item">
				<option value="${item.key}">${item.value}</option>
				</c:forEach>
			</select>
        </fieldset>
        <fieldset>
        	<label><spring:message code="SpeechCodecType"/>:</label>
        	<select class="easyui-combobox" name="msvocodec" 
				data-options="editable:false,width:130,panelHeight:'auto'">
				<c:forEach items="${msvocodecs}" var="item">
					<option value="${item.key}">${item.value}</option>
				</c:forEach>
			</select>
        </fieldset>
        <fieldset <c:if test="${ddyw!=1}">style="display:none;"</c:if>>
        	<label><spring:message code="Priority"/>:</label>
			<select class="easyui-combobox" name="priority" data-options="editable:false,panelHeight:'auto',width:130">
				<option value="1" selected="selected">1</option>
				<option value="2">2</option>
				<option value="3">3</option>
				<option value="4">4</option>
				<option value="5">5</option>
				<option value="6">6</option>
				<option value="7">7</option>
			</select>
        </fieldset>
        <fieldset>
        	<label>鉴权参数(97-2003版本excel):</label>
        	<input class="easyui-filebox" name="aucExcel" data-options="validType:'excelFile',width:100,buttonText:'导入'"/>
        	<a href="${pageContext.servletContext.contextPath}/media/workbook.xls" class="easyui-linkbutton">下载模板</a>
        </fieldset>
        <c:if test="${ims}">
        <fieldset>
        	<label>域名：</label>
			<input class="easyui-textbox" id="domain" name="domain" data-options="value:'sunkaisens.com',required:true,width:130"/>
        </fieldset>
        <fieldset>
        	<label>密码：</label>
			<input class="easyui-textbox" id="uePassword" name="uePassword" data-options="required:true,type:'password',width:130"/>
        </fieldset>
        </c:if>
        <div style="padding:10px 10px 20px 10px">
        	<c:forEach items="${basicBusinesses}" var="business" varStatus="status">
				<label for="jb_${status.count}"><input id="jb_${status.count}" type="checkbox" name="${business.name}" value="1" 
					<c:if test="${business.name!='callInLimit' && business.name!='callOutLimit' && business.name!='secrecy'}"
						>checked</c:if>/><spring:message code="${business.i18n}"/></label>&nbsp;&nbsp;
					<c:if test="${status.count%3==0}"><br/></c:if>
			</c:forEach>
        	<!-- 
			<label for="jb_1"><input id="jb_1" type="checkbox" name="data" value="1" checked="checked"/><spring:message code="DataTraffic"/></label>&nbsp;
			<label for="jb_2"><input id="jb_2" type="checkbox" name="shortMsg" value="1" checked="checked"/><spring:message code="MessageTraffic"/></label>&nbsp;
			<label for="jb_3"><input id="jb_3" type="checkbox" name="internationality" value="1" checked="checked"/><spring:message code="InternationalTraffic"/></label><br/>
			<label for="jb_4"><input id="jb_4" type="checkbox" name="callWaitting" value="1" checked="checked"/><spring:message code="CallTraffic"/></label>&nbsp;
			<label for="jb_5"><input id="jb_5" type="checkbox" name="threeWay" value="1" checked="checked"/><spring:message code="Three-way-calling"/></label>&nbsp;
			<label for="jb_6"><input id="jb_6" type="checkbox" name="callInLimit" value="1"/><spring:message code="Incoming-Call-Barring"/></label><br/>
			<label for="jb_7"><input id="jb_7" type="checkbox" name="callOutLimit" value="1"/><spring:message code="Outgoing-Call-Barring"/></label>&nbsp;
			<label for="jb_8"><input id="jb_8" type="checkbox" name="pairNet" value="1"/><spring:message code="Dual-Network-Business"/></label>
			 -->
		</div>
        <div style="text-align:center;padding:5px">
		   	<a href="#" class="easyui-linkbutton" onclick="batchSubmit()"><spring:message code="InsertBatchTerminalUser"/></a> 
		   	<a href="#" class="easyui-linkbutton" onclick="batchUpdate()"><spring:message code="UpdateBatchTerminalUser"/></a>
    	</div>
        </form> --%>
	</div>
	
	<div id="importHss" class="easyui-window"
        	data-options="width:400,title:'<spring:message code="UserImport"/>',iconCls:'icon-save',modal:true,closed:true,resizable:false,minimizable:false,maximizable:false">
      	<form id="hssF" action="${pageContext.request.contextPath}/hss/import.action" method="post" enctype="multipart/form-data">
      		<br/>
			<input class="easyui-filebox" name="file" data-options="validType:'sqlFile',width:380,buttonText:'<spring:message code="SelectSqlFile"/>',required:true,prompt:'<spring:message code="PleaseSelectSqlFile"/>'"/>
			<br/><br/>
			<div style="text-align:center;padding:5px;clear:both;">
			<a href="#" class="easyui-linkbutton" onclick="importHss()"><spring:message code="ImportUser"/></a>
			</div>
      	</form>
    </div>
    
    <div id="mystatus">
		<div>IMSI:<label title="UE-IMSI"></label>                                                   </div>
		<div><spring:message code="MDN"/>:<label title="UE-MDN"></label>                            </div>
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
		<div style="clear:left;color:red">PS域:</div>
		<div><spring:message code="PSDomainTMSI"/>:<label title="PS-TMSI"></label>                  </div>
		<div><spring:message code="PSDomainStatus"/>:<label title="PS-Status"></label>              </div>
		<div><spring:message code="CurrentServiceSGSN"/>:<label title="SGSN"></label>               </div>
		<div><spring:message code="BaseServ"/>:<label title="PS-BaseServ"></label>               	</div>
    </div>
</body>
</html>