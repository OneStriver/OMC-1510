
<%@tag import="com.sunkaisens.omc.context.core.OmcServerContext"%>
<%
	String version=OmcServerContext.getInstance().getVersion();
	out.print("<span style='font-size:16px;color:#4599b1'>"+version+"</span>");
%>