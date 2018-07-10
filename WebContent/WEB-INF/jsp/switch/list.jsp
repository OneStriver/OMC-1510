<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>号码映射表</title>
<%@ include file="/WEB-INF/jsp/public/common.jspf"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/switch/switch.js"></script>
</head>
<body>
	<table id="dg" title='号码映射表' class="easyui-datagrid" data-options="
				url:'${pageContext.request.contextPath}/switch/list.action',
				cache:false,
				border:false,
				rownumbers:true,
				fit:true,
				fitColumns:true,
				striped:false,
				singleSelect:true,
				onClickCell:onClickCell,
				toolbar:'#tb',
				rowStyler: function(index,row){
					var originalNum = row.originalNumber;
					if (originalNum!=undefined && originalNum.startsWith('*')){
	                    return 'background-color:#D8D8D8;font-weight:bold;';
					}
                }">
		<thead>
		<tr>
			<th data-options="field:'originalNumber',width:'20%'">原始号码</th>
			<th data-options="field:'mappingNumber',width:'20%'">映射号码</th>
			<!-- 未使用 -->
			<th hidden="true" data-options="field:'serviceOption',width:'10%',
				formatter:function(value,row,rowIndex){
					if(value==1){
						return '市话';
					}else if (value==2){
						return '长途';
					}else if (value==3){
						return '国际长途';
					}else{
						return '未知';
					} 
				}">业务类型</th>
			<th data-options="field:'上移',
				formatter:function(value,row,rowIndex){
					var originalNum = row.originalNumber;
					if (originalNum.startsWith('*')){
						return '';
					}else{
						return '<a href=#>上移</a>';
					}
				}">操作(上移)</th>
			<th data-options="field:'下移',
				formatter:function(value,row,rowIndex){
					var originalNum = row.originalNumber;
					if (originalNum.startsWith('*')){
						return '';
					}else{
						return '<a href=#>下移</a>';
					}
				}">操作(下移)</th>
			<th data-options="field:'修改',
				formatter:function(value,row,rowIndex){
					var originalNum = row.originalNumber;
					if (originalNum.startsWith('*')){
						return '';
					}else{
						return '<a href=#>修改</a>'
					}
				}">操作(修改)</th>
			<th data-options="field:'删除',
				formatter:function(value,row,rowIndex){
					var originalNum = row.originalNumber;
					if (originalNum.startsWith('*')){
						return '';
					}else{
						return '<a href=#>删除</a>';
					}
				}">操作(删除)</th>
		</tr>
	</thead>
	</table>
	<!-- 表格顶部按钮信息 -->
	<div id="tb" style="height:auto">
	<omc:permit url="switch/addSwitch">
		<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="appendConvert()">添加转换</a>
	</omc:permit>
	</div>
	
	<!-- 添加号码映射表窗口 -->
	<div id="addNumberTranslation" class="easyui-window" data-options="
			modal:true,
			closed:true,
			minimizable:false,
			maximizable:false,
			collapsible:false,
			draggable:true
		">
		<form id="addForm" class="easyui-form" method="post">
	    	<table>
	    		<tr>
	    			<td><label>原始号码:</label></td>
	    			<td><input class="easyui-textbox" id="addOriginalNumber" name="originalNumber" data-options="validType:'switchNumber',panelHeight:'auto',width:150"/></td>
	    		</tr>
	    		<tr>
	    			<td><label>映射号码:</label></td>
	    			<td><input class="easyui-textbox" id="addMappingNumber" name="mappingNumber" data-options="validType:'switchNumber',panelHeight:'auto',width:150"/></td>
	    		</tr>	
	    		<!-- 未使用  -->
	    		<tr hidden="true">
	    			<td><label>业务类型:</label></td>
	    			<td>
		    			<select class="easyui-combobox" id="addServiceOption" name="serviceOption"  data-options="panelHeight:'auto',width:150">
							<option value="1">市话</option>
							<option value="2">长途</option>
							<option value="3">国际长途</option>
	    				</select>
    				</td>
	    		</tr>	
	    	</table>
	    </form>
	    <div style="text-align:center;padding:5px">
	    	<a href="#" class="easyui-linkbutton" onclick="addSubmitForm()">保存</a>
	    </div>
	 </div>
	 
	 <!-- 更新号码映射表窗口 -->
	 <div id="updateNumberTranslation" class="easyui-window" data-options="
			modal:true,
			closed:true,
			minimizable:false,
			maximizable:false,
			collapsible:false,
			novalidate:true,
			draggable:false
		">
		<form id="updateForm" class="easyui-form" method="post">
	    	<table>
	    		<tr>
	    			<td><label>原始号码:</label></td>
	    			<td><input class="easyui-textbox" id="updateOriginalNumber" name="originalNumber" data-options="validType:'switchNumber',panelHeight:'auto',width:150"/></td>
	    		</tr>
	    		<tr>
	    			<td><label>映射号码:</label></td>
	    			<td><input class="easyui-textbox" id="updateMappingNumber" name="mappingNumber" data-options="validType:'switchNumber',panelHeight:'auto',width:150"/></td>
	    		</tr>	
	    		<tr hidden="true">
	    			<td><label>业务类型:</label></td>
	    			<td>
		    			<select class="easyui-combobox" id="updateServiceOption" name="serviceOption"  data-options="panelHeight:'auto',width:150,editable:false">
							<option value="1" >市话</option>
							<option value="2" >长途</option>
							<option value="3" >国际长途</option>
	    				</select>
	    				
    				</td>
	    		</tr>
	    	</table>
	    </form>
	    <div style="text-align:center;padding:5px">
	    	<a href="#" class="easyui-linkbutton" onclick="updateSubmitForm()">保存</a>
	    </div>
	 </div>
	
</body>
</html>