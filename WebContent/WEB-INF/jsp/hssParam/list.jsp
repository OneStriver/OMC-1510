<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/WEB-INF/jsp/public/common.jspf"%>
<html>
<head>
<title>HSS配置项参数</title>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/hssParam/list.js"></script>
</head>

<body>
	<div class="easyui-panel" data-options="fit:true,border:false">
		<form id="ff" class="easyui-form" method="post" data-options="novalidate:true" action="${pageContext.request.contextPath}/hssParam/update.action">
		<div class="easyui-panel" data-options="title:'请选择需要的设备类型：'" style="text-align:center;padding:15px 60px 20px 60px">
			<c:forEach items="${devices}" var="device">
				<label for="d_${device.id}">
					<input id="d_${device.id}" type="checkbox" name="device" value="${device.id}"
						<c:if test="${device.enable==1}">checked</c:if>
					>${device.name}
				</label>
			</c:forEach>
			<br/><br/><br/>
			<div style="text-align:center;padding:5px">
			   	<a href="#" class="easyui-linkbutton" onclick="fullSelect(this)">全选</a>
			   	<a href="#" class="easyui-linkbutton" onclick="reverseSelect(this)">反选</a>
		    </div>
		</div>
		<div class="easyui-panel" data-options="title:'请选择需要的语音类型：'" style="text-align:center;padding:15px 60px 20px 60px">
			<c:forEach items="${voices}" var="voice">
				<label for="v_${voice.id}">
					<input id="v_${voice.id}" type="checkbox" name="voice" value="${voice.id}"
						<c:if test="${voice.enable==1}">checked</c:if>
					>${voice.name}
				</label>
			</c:forEach>
			<br/><br/><br/>
			<div style="text-align:center;padding:5px">
			   	<a href="#" class="easyui-linkbutton" onclick="fullSelect(this)">全选</a>
			   	<a href="#" class="easyui-linkbutton" onclick="reverseSelect(this)">反选</a>
		    </div>
		</div>
		<div class="easyui-panel" data-options="title:'请选择需要的业务种类：'" style="text-align:center;padding:15px 50px 20px 60px">
			<c:forEach items="${allBusinesses}" var="business" varStatus="status">
				<label for="jb_${status.count}">
					<input id="jb_${status.count}" type="checkbox" name="business" value="${business.id}"
						<c:if test="${business.enable==1}">checked</c:if>/>
						<spring:message code="${business.i18n}"/>
				</label>
				<c:if test="${status.count%8==0}"><br/></c:if>
			</c:forEach>
			<br/><br/><br/>
			<div style="text-align:center;padding:5px">
			   	<a href="#" class="easyui-linkbutton" onclick="fullSelect(this)">全选</a>
			   	<a href="#" class="easyui-linkbutton" onclick="reverseSelect(this)">反选</a>
		    </div>
		</div>
		<div class="easyui-panel" data-options="title:'请选择需要显示的列名'" style="text-align:center;padding:15px 60px 20px 60px">
			<c:forEach items="${viewItems}" var="viewItem">
				<label for="view_${viewItem.id}">
					<input id="view_${viewItem.id}" type="checkbox" name="viewItem" value="${viewItem.id}"
						<c:if test="${viewItem.enable==1}">checked</c:if>
					><spring:message code="${viewItem.name}"/>
				</label>
			</c:forEach>
			<br/><br/><br/>
			<div style="text-align:center;padding:5px">
			   	<a href="#" class="easyui-linkbutton" onclick="fullSelect(this)">全选</a>
			   	<a href="#" class="easyui-linkbutton" onclick="reverseSelect(this)">反选</a>
		    </div>
		</div>
		<br/>
		<div style="text-align:center;padding:5px">
		   	<a href="#" class="easyui-linkbutton" onclick="submitForm()">保存设置</a>
		   	<a href="#" class="easyui-linkbutton" onclick="fullSelect(this)">全选</a>
		   	<a href="#" class="easyui-linkbutton" onclick="reverseSelect(this)">反选</a>
	    </div>
		</form>
	</div>
</body>
</html>