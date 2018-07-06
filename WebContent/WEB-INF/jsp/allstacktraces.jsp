<%@page import="java.util.Map"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<title>服务器线程信息</title>
</head>
<body>
	<%
		for (Map.Entry<Thread, StackTraceElement[]> stackTrace : Thread
				.getAllStackTraces().entrySet()){
			Thread thread=stackTrace.getKey();
			StackTraceElement[] stack=stackTrace.getValue();
			
			if(thread.equals(Thread.currentThread())){
				continue;
			}
			
			out.println("\n线程: "+thread.getName()+"<br/>");
			for(StackTraceElement element:stack){
				out.println("　　"+element+"<br/>");
			}
		}
	%>
</body>
</html>