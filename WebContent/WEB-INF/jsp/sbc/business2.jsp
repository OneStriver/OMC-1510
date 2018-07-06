<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script>
//var isAdded=true;
function add(){
	$('#d_${item.id}').css('display','');
}

function save(){
	var isValid=$('#template_${item.id}').textbox('isValid');
	if(!isValid) return;
	var value=$('#template_${item.id}').val();
	$.post('${pageContext.request.contextPath}/sbc/addBusiness.action',
		{'${item.name}':value},function(data){
			var json=$.parseJSON(data);
			if(json&&json.error){
				$('#error_${item.id}').html('保存失败,'+json.error);
			}else{
				$('#p_${item.id}').panel('refresh');
			}
		});
}

function remove(e){
	var recId=$(e.data.target).prev('input[type=hidden]').val();
	$.post('${pageContext.request.contextPath}/sbc/deleteBusiness.action',
		{ids:recId,name:'${item.name}'},function(data){
			var json=$.parseJSON(data);
			if(json&&json.error){
				$('#error_${item.id}').html('删除失败,'+json.error);
			}else{
				$('#p_${item.id}').panel('refresh');
			}
		});
}

</script>
<c:choose>
	<c:when test="${item.formtype == 'textbox'}">
		<c:set var="formtype" value="textbox"/>
		<c:set var="validType" value="'length[${item.minLen},${item.maxLen}]'"/>
	</c:when>
	<c:when test="${item.formtype == 'numberbox'}">
		<c:set var="formtype" value="numberbox"/>
		<c:set var="validType" value="['minValue[${item.min==null?0:item.min}]','maxValue[${item.max==null?9999999999999999:item.max}]']"/>
	</c:when>
	<c:when test="${item.formtype == 'url'}">
		<c:set var="formtype" value="textbox"/>
		<c:set var="validType" value="'url'"/>
	</c:when>
	<c:when test="${item.formtype == 'email'}">
		<c:set var="formtype" value="textbox"/>
		<c:set var="validType" value="'email'"/>
	</c:when>
	<c:when test="${item.formtype == 'ip'}">
		<c:set var="formtype" value="textbox"/>
		<c:set var="validType" value="'ip'"/>
	</c:when>
	<c:when test="${item.formtype == 'combobox'}">
		<c:set var="formtype" value="combobox"/>
	</c:when>
	<c:when test="${item.formtype == 'radio'}">
		<c:set var="formtype" value="radio"/>
	</c:when>
	<c:when test="${item.formtype == 'grid'}">
		<c:set var="formtype" value="datagrid"/>
	</c:when>
</c:choose>

<div><span id="error_${item.id}" style='color:red'>${error}</span></div>
<c:forEach items="${pageBean.rows}" var="row">
<input type="hidden" value="${row.RecId}"/>
<input class="easyui-${formtype}" name="${item.name}" 
	data-options="required:${item.required},
		icons:[{
			iconCls:'icon-remove',
			handler:remove
		}],
		editable:false,
		value:'${row[item.name]}',
		min:${item.min==null?0:item.min},
		max:${item.max==null?9999999999999999:item.max},
		data: [
			<c:forEach items="${item.optiones}" var="options">
			{text:'${options.text}',value:'${options.val}'},
			</c:forEach>
		],
		<c:if test='${validType!=null}'>validType:${validType},</c:if>
		multiline:${item.multiline}<c:if test='${item.multiline}'>,height:88</c:if>"/>
</c:forEach>
<div id="d_${item.id}" style="display:none">
<input class="easyui-${formtype}" name="${item.name}" id="template_${item.id}"
	data-options="required:${item.required},
		min:${item.min==null?0:item.min},
		max:${item.max==null?9999999999999999:item.max},
		data: [
			<c:forEach items="${item.optiones}" var="options">
			{text:'${options.text}',value:'${options.val}'},
			</c:forEach>
		],
		<c:if test='${validType!=null}'>validType:${validType},</c:if>
		multiline:${item.multiline}<c:if test='${item.multiline}'>,height:88</c:if>"/>
</div>
<div class="easyui-pagination" id="pagination_${item.id}"
	style="position:absolute;bottom:0px;" data-options="
	total:${pageBean.total},
	pageNumber:${pageBean.page},
	pageSize:${pageBean.pageSize},
	pageList: [10,20,30,40,50,60],
	buttons: [{
		iconCls:'icon-add',
		handler:add
	},'-',{
		iconCls:'icon-save',
		handler:save
	}],
	onSelectPage:function(pageNumber,pageSize){
		var href=$('#p_${item.id}').panel('options').href;
		if(href.indexOf('&')!=-1)
			href=href.substring(0,href.indexOf('&'));
		href+='&page='+pageNumber+'&rows='+pageSize
		$('#p_${item.id}').panel('refresh',href);
    }"
></div>
