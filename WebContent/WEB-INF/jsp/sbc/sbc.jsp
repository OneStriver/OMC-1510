<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<%@ include file="/WEB-INF/jsp/public/common.jspf"%>
<title>修改配置</title>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/sbc/sbc.js"></script>
<script type="text/javascript">
function load(){
	var names=$.map($('#ff input[name]').get(),function(i){return i.name;});
	if(names&&names.length){
		$.messager.progress({msg:'正在加载请稍等'});
		$.ajax({type:'post',traditional:true,data:{names:names},
			url:contextPath+'/sbc/load${packid}Scalar.action',
			success:function(data){
				$.messager.progress('close');
				var json=$.parseJSON(data);
				if(json.error){
					data='发生错误'+json.error;
					$.messager.alert('出错提示',data,'error');
				}else{
					$('#ff').form('load',json);
				}
			}
		});
	}
}
$(function(){
	load();
});
</script>
</head>
<body>
<div class="easyui-panel" title=" " data-options="fit:true,tools:[{iconCls:'icon-reload',handler:load}]">
	<div style="padding:10px 50px 20px 50px;">
	<form id="ff" method="post" 
		action="${pageContext.request.contextPath}/sbc/update${packid}Scalar.action">
    <c:forEach items="${items}" var="item">
    	<%request.setAttribute("old", "\n");%>
    	<c:set var="new1" value="\\n"/>
 		<c:if test="${item.formtype!='hidden'}">
    	<div style="float:left;margin:8px">
    		<c:if test="${!config.sole}">
	    		<label style="display:block;width:180px;float:left;text-align:right">
	    			<c:if test="${item.required}"><span style="color:red;">*</span></c:if>${item.description}:
	    		</label>
	    		
	    		<!-- 判断输入类型 开始 -->
	    		<c:choose>
					<c:when test="${item.formtype == 'textbox'}">
						<c:set var="formtype" value="textbox"/>
						<c:set var="validType" value="'length[${item.minLen},${item.maxLen}]'"/>
						<c:set var="hasForm" value="true"/>
					</c:when>
					<c:when test="${item.formtype == 'numberbox'}">
						<c:set var="formtype" value="numberbox"/>
						<c:set var="validType" value="['maxValue[${item.max==null?99999:item.max}]','minValue[${item.min==null?-99999:item.min}]']"/>
						<c:set var="hasForm" value="true"/>
					</c:when>
					<c:when test="${item.formtype == 'url'}">
						<c:set var="formtype" value="textbox"/>
						<c:set var="validType" value="'url'"/>
						<c:set var="hasForm" value="true"/>
					</c:when>
					<c:when test="${item.formtype == 'email'}">
						<c:set var="formtype" value="textbox"/>
						<c:set var="validType" value="'email'"/>
						<c:set var="hasForm" value="true"/>
					</c:when>
					<c:when test="${item.formtype == 'ip'}">
						<c:set var="formtype" value="textbox"/>
						<c:set var="validType" value="'ip'"/>
						<c:set var="hasForm" value="true"/>
					</c:when>
					<c:when test="${item.formtype == 'combobox'}">
						<c:set var="formtype" value="combobox"/>
						<c:set var="hasForm" value="true"/>
					</c:when>
					<c:when test="${item.formtype == 'radio'}">
						<c:set var="formtype" value="radio"/>
						<c:set var="hasForm" value="true"/>
					</c:when>
					<c:when test="${item.formtype == 'grid'}">
						<c:set var="formtype" value="datagrid"/>
					</c:when>
					
				</c:choose>
	    		<!-- 判断输入类型 结束 -->
	    		<c:choose>
	    		<c:when test="${formtype=='combobox'}">
		    		<select class="easyui-combobox" name="${item.name}" 
		    			data-options="editable:false,width:130,panelHeight:'auto',required:${item.required}">
		    			<c:forEach items="${item.optiones}" var="options">
		    				<option value="${options.val}" <c:if test="${item.value==options.val}">selected</c:if>>${options.text}</option>
		    			</c:forEach>
					</select>
				</c:when>
				<c:when test="${formtype=='radio'}">
					<div style="width:130px;float:left;">
					<c:forEach items="${item.optiones}" var="options" varStatus="status">
						<label for="${item.name}_${status.count}"><input id="${item.name}_${status.count}" <c:if test="${item.value==options.val}">checked</c:if>
							type="radio" name="${item.name}" value="${options.val}"/>${options.text}</label>
		    		</c:forEach>
		    		</div>
				</c:when>
				<%-- ---------------------表格开始------------------- --%>
				<c:when test="${formtype=='datagrid'}">
					<div id="tb">
						<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" 
							onclick="append(this)">添加</a>
						<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" 
							onclick="removeit(this,'${pageContext.request.contextPath}/sbc/delete${packid}.action')">删除</a>
						<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-save',plain:true" 
							onclick="accept(this,'${pageContext.request.contextPath}/sbc/update${packid}.action',
								'${pageContext.request.contextPath}/sbc/save.action')">保存</a>
					</div>
					<table id="grid_${item.id}" class="easyui-datagrid" data-options="
						height:'auto',width:'800',striped:true,onClickCell:onClickCell,
						fitColumns:false,toolbar:'#tb',rownumbers:true,loadFilter:loadFilter,
						pageList: [50,100,150,200],pagination:true,
						pageNumber:${pageBean.page},pageSize: ${pageBean.pageSize},
						url:'${pageContext.request.contextPath}/sbc/load${packid}Grid.action?id=${item.id}'
						">
					    <thead>   
					        <tr>
					        	<th data-options="field:'ck',checkbox:true"></th>
					        	<th data-options="field:'RecId',hidden:true"></th>
					       		<c:forEach items="${item.children}" var="child">
					       		<%@ include file="/WEB-INF/jsp/sbc/td.jspf"%>
					            <th data-options="field:'${child.name}',width:100,
					            	<c:if test="${child.formtype=='hidden'}">hidden:true,</c:if>
									editor:{
										type:'${cformtype}',
										options:{
											data: [
											<c:forEach items="${child.optiones}" var="options">
												{text:'${options.text}',value:'${options.val}'},
											</c:forEach>
											],
											panelHeight:'auto',
											required:${child.required},
											<c:if test='${cvalidType!=null}'>validType:'${cvalidType}',</c:if>
											min:${child.min==null?-99999:child.min},
			    							max:${child.max==null?99999:child.max}
										}
								}">${child.description}</th>
					        	</c:forEach> 
					        </tr>
					    </thead>
					</table>
				</c:when>
				<%-- ---------------------表格结束------------------- --%>
				<c:otherwise>
					<c:if test="${formtype!='hidden'}">
		    		<input class="easyui-${formtype}" name="${item.name}"
		    			data-options="
		    							required:${item.required},
		    							min:${item.min==null?-99999:item.min},
		    							max:${item.max==null?99999:item.max},
		    							<c:if test='${!empty validType}'>validType:${validType},</c:if>
										multiline:${item.multiline}<c:if test='${item.multiline}'>,height:88</c:if>"/>
					</c:if>
				</c:otherwise>
				</c:choose>
    		</c:if>
    		<c:if test="${config.sole}">
    												<!-- ${item.name} -->
    			<input class="easyui-textbox" name="${config.name}"
	    			data-options="required:${item.required},value:'${fn:replace(item.value,old,new1)}',multiline:true,height:88,width:400"/>
    		</c:if>
    	</div>
    	</c:if>
    </c:forEach>
    </form>
    </div>
    <c:if test="${hasForm}">
	<div style="text-align:center;padding:5px;clear:both;margin-top:200px">
		<a href="#" class="easyui-linkbutton" onclick="load()">刷新</a>
		<a href="#" class="easyui-linkbutton" onclick="submitForm()">提交</a>
		<a href="#" class="easyui-linkbutton" onclick="clearForm()">清空</a>
	</div>
	</c:if>
</div>
</body>
</html>