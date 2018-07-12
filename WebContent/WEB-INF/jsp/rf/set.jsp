<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>射频参数设置</title>
<%@ include file="/WEB-INF/jsp/public/common.jspf"%>
<script type="text/javascript">

function removeit(){
	var rows=$('#dg').datagrid('getSelections');
	if(rows.length==0) {
		$.messager.alert('提示','请选择要删除的记录!');
		return;
	}
	$.messager.confirm(LOCALE.Confirm,LOCALE.DeleteMessage,
		function(yes){    
			if(yes){
				var rows=$('#dg').datagrid('getSelections');
				if(rows.length==0) return;
				var carrierIDs=[];
				$(rows).each(function(i){
					var id=rows[i].carrierId;
					carrierIDs.push(id);
				});
				$.messager.progress({msg:'正在删除请稍等...'});
				$.ajax({type:'post',traditional:true,data:{carrierIDs:carrierIDs},
					url:contextPath+"/rf/delete.action",
					success:function(data){
						$.messager.progress('close');
						submitSuccess(data);
					}
				});
			}
		}
	);
}

//打开添加窗口
function append(){
	$('#add').form('reset');
	$('#add').form({url:contextPath+'/rf/insert.action'});
	$('#wadd').window({title:"新建射频参数",iconCls:'icon-add'}).window('open');
}

//提交添加窗口的数据
function submitForm() {	
	$('#add').form('submit', {
		success : function(data) {
			$.messager.progress('close');
			if (submitSuccess(data)) {
				$('#wadd').window(close);
				window.location=contextPath+'/rf/setList.action';
			} 
		},
		onSubmit : function() {
			var isOk = $(this).form('enableValidation').form('validate');
			if (isOk) {
				$.messager.progress({
					msg : '正在保存请稍等'
				});
				
			}
			return isOk;
		}
	});
}

//点击修改执行
function onClickCell(index, field, value){
	if(field!='修改'){
		return;
	}
	var row=$('#dg').datagrid('getRows')[index];
	update(row);

}

//打开修改窗口
function update(row){
	$('#Update').form('load',row).form({url:contextPath+'/rf/update.action'});
	//$('#idu').textbox({readonly:true});
	$('#wupdate').window({title:"修改射频参数",iconCls:'icon-save'}).window('open');
}

//提交修改窗口数据
function updateForm(){ 
	$('#Update').form('submit',{
		success:function(data){
			$.messager.progress('close');
			submitSuccess(data);
			$('#wupdate').window(close);
			window.location=contextPath+'/rf/setList.action';
		},
		onSubmit:function(){
			var isOk=$(this).form('enableValidation').form('validate');
			if(isOk) $.messager.progress({msg:'正在提交请稍等...'});
			return isOk;
		}
	});
}
</script>
</head>
<body>
	<table id="dg" class="easyui-datagrid" title='射频参数设置' style="width:500px;height:auto"
			data-options="
				url:'${pageContext.request.contextPath}/rf/set.action',
				rownumbers:true,
				fit:true,striped:true,border:false,
				pageList: [1,20,30,40,50],
				pageNumber:${pageBean.page},
				pageSize: ${pageBean.pageSize},
				pagination:true,
				iconCls: 'icon-edit',
				onClickCell:onClickCell,
				toolbar: '#tb'
			">
		<thead>
			<tr>
			<th data-options="field:'ck',checkbox:true"></th>
			<th data-options="field:'carrierId'">载频ID</th>
			<th data-options="field:'carrierType'">载频类型</th>
			<th data-options="field:'cellId'">小区ID</th>
			<th data-options="field:'carrierFreq'">频点</th>
			<th data-options="field:'pn'">PN值</th>
			<th data-options="field:'btsId'">btsId</th>
			<th data-options="field:'zoneId'">zoneId</th>
			<th data-options="field:'bandClass'">bandClass</th>
			<th data-options="field:'txGain'">射频输出功率</th>
			<th data-options="field:'pilotChGain'">导频信道增益</th>
			<th data-options="field:'syncChGain'">同步信道增益</th>
			<th data-options="field:'pagingChGain'">寻呼信道增益</th>
			<th data-options="field:'numOfPch'">寻呼信道个数</th>
			<th data-options="field:'numOfQpch'">快速寻呼信道个数</th>
			<th data-options="field:'qpchRate'">快速寻呼信道速率</th>
			<th data-options="field:'qpchCci'">qpchCci</th>
			<th data-options="field:'qpchPwrPage'">qpchPwrPage</th>
			<th data-options="field:'qpchPwrCfg'">qpchPwrCfg</th>
			<th data-options="field:'numAchPerpch'">numAchPerpch</th>
			<th data-options="field:'cdmaChannelList'">cdmaChannelList</th>
			<th data-options="field:'修改',
				formatter:function(value,row,rowIndex){
					return '<a href=#><spring:message code="Update"/></a>';
				}"><spring:message code="Update"/></th>
		</tr>
		</thead>
	</table>
	<div id="tb" style="height:auto">
		<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="append()"><spring:message code="Add"/></a>
		<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="removeit()"><spring:message code="Delete"/></a>
	</div>
	
	
	<!-- 添加窗口 -->
	<div id="wadd" class="easyui-window" data-options="
			modal:true,
			width:600,
			closed:true,
			minimizable:false,
			maximizable:false,
			collapsible:false">
		<form id="add" class="easyui-form" method="post">
			<div style="padding: 10px;">
				 <span style="color:red">*</span>
					carrierId：<input class="easyui-textbox" id="carrierId" name="carrierId"  data-options="validType:'carrierId',required:true">&nbsp;
					carrierType：&nbsp;<input class="easyui-textbox" id="carrierType" name="carrierType" data-options="validType:'carrierType'">&nbsp;
			</div>			
			<div style="padding: 10px;">
					cellId：<input class="easyui-textbox" id="cellId" name="cellId"  data-options="validType:'cellId'">&nbsp;
					pn：&nbsp;<input class="easyui-textbox" id="pn" name="pn"  data-options="validType:'pn'">&nbsp;
			</div>
			<div style="padding: 10px;">
					carrierFreq：<input class="easyui-textbox" id="carrierFreq" name="carrierFreq"  data-options="validType:'carrierFreq'">&nbsp;
					btsId：&nbsp;<input class="easyui-textbox" id="btsId" name="btsId"  data-options="validType:'btsId'">&nbsp;
			</div>			
			<div style="padding: 10px;">
					zoneId：<input class="easyui-textbox" id="zoneId" name="zoneId"  data-options="validType:'zoneId'">&nbsp;
					bandClass：&nbsp;<input class="easyui-textbox" id="bandClass" name="bandClass"  data-options="validType:'bandClass'">&nbsp;
			</div>			
			<div style="padding: 10px;">
					txGain：<input class="easyui-textbox" id="txGain" name="txGain"  data-options="validType:'txGain'">&nbsp;
					pilotChGain：&nbsp;<input class="easyui-textbox" id="pilotChGain" name="pilotChGain"  data-options="validType:'pilotChGain'">&nbsp;
			</div>			
			<div style="padding: 10px;">
					syncChGain：<input class="easyui-textbox" id="syncChGain" name="syncChGain"  data-options="validType:'syncChGain'">&nbsp;
					pagingChGain：&nbsp;<input class="easyui-textbox" id="pagingChGain" name="pagingChGain"  data-options="validType:'pagingChGain'">&nbsp;
			</div>			
			<div style="padding: 10px;">
					numOfPch：<input class="easyui-textbox" id="numOfPch" name="numOfPch"  data-options="validType:'numOfPch'">&nbsp;
					numOfQpch：&nbsp;<input class="easyui-textbox" id="numOfQpch" name="numOfQpch"  data-options="validType:'numOfQpch'">&nbsp;
			</div>			
			<div style="padding: 10px;">
					qpchRate：<input class="easyui-textbox" id="qpchRate" name="qpchRate"  data-options="validType:'qpchRate'">&nbsp;
					qpchCci：&nbsp;<input class="easyui-textbox" id="qpchCci" name="qpchCci"  data-options="validType:'qpchCci'">&nbsp;
			</div>			
			<div style="padding: 10px;">
					qpchPwrPage：<input class="easyui-textbox" id="qpchPwrPage" name="qpchPwrPage"  data-options="validType:'qpchPwrPage'">&nbsp;
					qpchPwrCfg：&nbsp;<input class="easyui-textbox" id="qpchPwrCfg" name="qpchPwrCfg"  data-options="validType:'qpchPwrCfg'">&nbsp;
			</div>			
			<div style="padding: 10px;">
					numAchPerpch：<input class="easyui-textbox" id="numAchPerpch" name="numAchPerpch"  data-options="validType:'numAchPerpch'">&nbsp;
					cdmaChannelList：&nbsp;<input class="easyui-textbox" id="cdmaChannelList" name="cdmaChannelList"  data-options="validType:'cdmaChannelList'">&nbsp;
			</div>

	    </form>
	    
	    <div style="text-align:center;padding:5px">
	    	<a href="#" class="easyui-linkbutton" onclick="submitForm()"><spring:message code="Submit"/></a>
	    </div>
	 </div>
	 
	 <!-- 添加窗口 -->
	<div id="wupdate" class="easyui-window" data-options="
			modal:true,
			width:600,
			closed:true,
			minimizable:false,
			maximizable:false,
			collapsible:false">
		<form id="Update" class="easyui-form" method="post">
			<div style="padding: 10px;">
				 <span style="color:red">*</span>
					carrierId：<input class="easyui-textbox" id="carrierId" name="carrierId"  data-options="validType:'carrierId',required:true,readonly:true,disabled:true">&nbsp;
					carrierType：&nbsp;<input class="easyui-textbox" id="carrierType" name="carrierType" data-options="validType:'carrierType'">&nbsp;
			</div>			
			<div style="padding: 10px;">
					cellId：<input class="easyui-textbox" id="cellId" name="cellId"  data-options="validType:'cellId'">&nbsp;
					pn：&nbsp;<input class="easyui-textbox" id="pn" name="pn"  data-options="validType:'pn'">&nbsp;
			</div>
			<div style="padding: 10px;">
					carrierFreq：<input class="easyui-textbox" id="carrierFreq" name="carrierFreq"  data-options="validType:'carrierFreq'">&nbsp;
					btsId：&nbsp;<input class="easyui-textbox" id="btsId" name="btsId"  data-options="validType:'btsId'">&nbsp;
			</div>			
			<div style="padding: 10px;">
					zoneId：<input class="easyui-textbox" id="zoneId" name="zoneId"  data-options="validType:'zoneId'">&nbsp;
					bandClass：&nbsp;<input class="easyui-textbox" id="bandClass" name="bandClass"  data-options="validType:'bandClass'">&nbsp;
			</div>			
			<div style="padding: 10px;">
					txGain：<input class="easyui-textbox" id="txGain" name="txGain"  data-options="validType:'txGain'">&nbsp;
					pilotChGain：&nbsp;<input class="easyui-textbox" id="pilotChGain" name="pilotChGain"  data-options="validType:'pilotChGain'">&nbsp;
			</div>			
			<div style="padding: 10px;">
					syncChGain：<input class="easyui-textbox" id="syncChGain" name="syncChGain"  data-options="validType:'syncChGain'">&nbsp;
					pagingChGain：&nbsp;<input class="easyui-textbox" id="pagingChGain" name="pagingChGain"  data-options="validType:'pagingChGain'">&nbsp;
			</div>			
			<div style="padding: 10px;">
					numOfPch：<input class="easyui-textbox" id="numOfPch" name="numOfPch"  data-options="validType:'numOfPch'">&nbsp;
					numOfQpch：&nbsp;<input class="easyui-textbox" id="numOfQpch" name="numOfQpch"  data-options="validType:'numOfQpch'">&nbsp;
			</div>			
			<div style="padding: 10px;">
					qpchRate：<input class="easyui-textbox" id="qpchRate" name="qpchRate"  data-options="validType:'qpchRate'">&nbsp;
					qpchCci：&nbsp;<input class="easyui-textbox" id="qpchCci" name="qpchCci"  data-options="validType:'qpchCci'">&nbsp;
			</div>			
			<div style="padding: 10px;">
					qpchPwrPage：<input class="easyui-textbox" id="qpchPwrPage" name="qpchPwrPage"  data-options="validType:'qpchPwrPage'">&nbsp;
					qpchPwrCfg：&nbsp;<input class="easyui-textbox" id="qpchPwrCfg" name="qpchPwrCfg"  data-options="validType:'qpchPwrCfg'">&nbsp;
			</div>			
			<div style="padding: 10px;">
					numAchPerpch：<input class="easyui-textbox" id="numAchPerpch" name="numAchPerpch"  data-options="validType:'numAchPerpch'">&nbsp;
					cdmaChannelList：&nbsp;<input class="easyui-textbox" id="cdmaChannelList" name="cdmaChannelList"  data-options="validType:'cdmaChannelList'">&nbsp;
			</div>

	    </form>
	    
	    <div style="text-align:center;padding:5px">
	    	<a href="#" class="easyui-linkbutton" onclick="updateForm()"><spring:message code="Submit"/></a>
	    </div>
	 </div>
</body>
</html>