<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>终端用户号码列表</title>
<%@ include file="/WEB-INF/jsp/public/common.jspf"%>
<script type="text/javascript">
function diffSelect(){
	$('input[type=checkbox]').each(function(){
		this.checked=!this.checked;
		if(this.checked){
			$(this).parents('div:first').css('background-color','#FCD20A');
		}else{
			$(this).parents('div:first').css('background-color','');
		}
	});
}

function onClick(){
	if($('input[type=checkbox]:checked').length==0){
		$.messager.alert('<spring:message code="Prompt"></spring:message>',
				'<spring:message code="PromptMessage"></spring:message>');
		return;
	}
	var imsis=[];
	$('input[type=checkbox]:checked').each(function(){
		imsis.push(this.id);
	});
	var priority=$('#priority').combobox('getValue');
	$.ajax({
		url:contextPath+'/hss/updatePriority.action',
		traditional:true,type:'post',
		data:{imsis:imsis,priority:priority},
		success:submitSuccess
	});
}

function qq(value,name){
	var options=$('#pp').pagination('options');
	var pageNumber=options.pageNumber;
	var pageSize=options.pageSize;
	var mdn=value;
	var priority=$('#priorityFilter').combobox('getValue');
	loadMdn(pageNumber,pageSize,mdn,priority);
}

function onSelectPriority(record){
	var options=$('#pp').pagination('options');
	var pageNumber=options.pageNumber;
	var pageSize=options.pageSize;
	var mdn=$('#ss').searchbox('getValue');
	var priority=$('#priorityFilter').combobox('getValue');
	loadMdn(pageNumber,pageSize,mdn,priority);
}

function onSelectPage(pageNumber,pageSize){
	var mdn=$('#ss').searchbox('getValue');
	var priority=$('#priorityFilter').combobox('getValue');
	loadMdn(pageNumber,pageSize,mdn,priority);
}

function loadMdn(page,pageSize,mdn,priority){
	if(page<=0) page=1;
	$('#pp').pagination('loading');
	$.post(contextPath+"/hss/listMdnNum.action",{page:page,rows:pageSize,mdn:mdn,priority:priority},
		function(data){
			$('#mdns').empty();
			$('#pp').pagination({total:data.total,pageSize:data.pageSize,pageNumber:data.page}); 
			for(var i=0;i<data.rows.length;i++){
				//console.log(data.rows[i]);
				var input=$('<input type="checkbox" id="'+data.rows[i].imsi+'">').on
				var item=$('<label for="'+data.rows[i].imsi+'"/>').
					append('<input type="checkbox" id="'+data.rows[i].imsi+'">').
					append('<b>'+data.rows[i].mdn+'</b>');
				$('#mdns').append($('<div style="float:left;width:100px;"/>').
					append(item).on('mouseover',function(){
						$(this).css('background-color','#FCD20A');
					}).on('mouseout',function(){
						if($(this).find('input[type=checkbox]:checked').length==0)
							$(this).css('background-color','');
					})
				);
			}
			$('#pp').pagination('loaded');
		}
	);
}

$(function(){
	loadMdn(1,500,null,0);
});
</script>
</head>
<body>
	<div id="mm" style="width:120px">
		<div data-options="name:'mdn'"><spring:message code="MDN"></spring:message></div>
	</div>
	<div id="p" class="easyui-panel" title="<spring:message code="PriorityMessage"></spring:message>" 
		data-options="fit:true,footer:'#pp'">
		<div id="tt" style="background:#efefef;border:1px solid #ccc;">
			<label>按优先级过滤：</label>
			<select class="easyui-combobox" id="priorityFilter" data-options="onSelect:onSelectPriority,editable:false,panelHeight:'100',width:60">
				<option value="0" selected="selected">全部</option>
				<option value="1">1</option>
				<option value="2">2</option>
				<option value="3">3</option>
				<option value="4">4</option>
				<option value="5">5</option>
				<option value="6">6</option>
				<option value="7">7</option>
				<option value="8">8</option>
				<option value="9">9</option>
				<option value="10">10</option>
				<option value="11">11</option>
				<option value="12">12</option>
				<option value="13">13</option>
				<option value="14">14</option>
				<option value="15">15</option>
			</select>
			<input id="ss" class="easyui-searchbox" style="width:250px" 
				data-options="searcher:qq,prompt:'请输入要查询的号码片段',menu:'#mm'"/>
			<label>优先级调整为：</label>
			<select class="easyui-combobox" id="priority" data-options="editable:false,panelHeight:'100',width:100">
				<option value="1" selected="selected">1</option>
				<option value="2">2</option>
				<option value="3">3</option>
				<option value="4">4</option>
				<option value="5">5</option>
				<option value="6">6</option>
				<option value="7">7</option>
				<option value="8">8</option>
				<option value="9">9</option>
				<option value="10">10</option>
				<option value="11">11</option>
				<option value="12">12</option>
				<option value="13">13</option>
				<option value="14">14</option>
				<option value="15">15</option>
			</select>
			<a href="#" class="easyui-linkbutton" data-options="onClick:diffSelect"><spring:message code="InvertSelection"/></a>
			<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-ok',onClick:onClick"><spring:message code="Submit"/></a>
		</div>
		<div id="mdns"></div>
	</div> 
	<div id="pp" class="easyui-pagination" style="background:#efefef;border:1px solid #ccc;" 
		data-options="pageList:[50,100,200,500],onSelectPage:onSelectPage"></div> 
</body>
</html>