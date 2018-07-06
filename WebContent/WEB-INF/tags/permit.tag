<%@ tag import="com.sunkaisens.omc.po.core.User"%>
<%@ attribute name="url" required="true"%>
<%
	User user=(User)session.getAttribute("user");
	if(!user.hasPrivilegeByUrl(url)){
		return;
	}
%>
<jsp:doBody/>