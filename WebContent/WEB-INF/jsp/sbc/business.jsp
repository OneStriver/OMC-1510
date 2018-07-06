<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<%@ include file="/WEB-INF/jsp/public/common.jspf"%>
<title>修改配置</title>
<style type="text/css">
li div{text-indent:2em;margin:5px;}
</style>
<script type="text/javascript">
function onClose(){
	$('#accordion').accordion({fit:true});
}
</script>
</head>
<body>
	<div class="easyui-panel" style="margin:0px;padding:0px" data-options="fit:true">
	<div id="desc" class="easyui-panel" title="限制说明"     
        style="padding:0px;background:#fafafa;"   
        data-options="closable:true,onClose:onClose">   
	    <ul>
		    <li><b>注册限制IP</b><div>说明：未注册入网用户，若其IP地址加入限制，则注册受限制； 已注册入网用户，若其IP地址加入限制，则主叫受限制。 范例：10.10.10.10</div></li>
		    <li><b>被叫SIPURI限制</b><div>说明：SIPURI受限的用户，无法进行音、视频被叫。范例：0201312345@mcs.zb.cpl tel-0201312345</div></li>
		    <li><b>主叫SIPUri限制</b><div>说明：SIPURI受限的用户，无法进行音、视频主叫。范例：0201312345@mcs.zb.cpl tel-0201312345</div></li>
		    <li><b>目的IP限制</b><div>说明：基于受限IP的用户，无法进行音、视频被叫。 范例：10.10.10.10</div></li>
	    </ul>
	</div>
	
    <c:forEach items="${items}" var="item">
	<c:choose>
	<c:when test="${item.formtype=='grid'}">
		<div class="easyui-accordion" data-options="" id="accordion">
		<c:forEach items="${item.children}" var="child" varStatus="status">
			<div id="p_${child.id}" title="${child.description}" style="padding:10px;position:relative"
				data-options="
				height:220,
				href:'${pageContext.request.contextPath}/sbc/load${packid}Grid.action?id=${child.id}',
				//iconCls:'icon-cancel',
				tools:[{
					iconCls:'icon-reload',
					handler:function(){
						$('#p_${child.id}').panel('refresh', '${pageContext.request.contextPath}/sbc/load${packid}Grid.action?id=${child.id}');
					}
				}]">
			</div>
		</c:forEach>
		</div>
	</c:when>
	</c:choose>
    </c:forEach>
    </div>
</body>
</html>