<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%-- <% response.sendRedirect(request.getContextPath() + "/user/home.action"); %> --%>
 <html>
 <head>
  <meta charset="UTF-8">
  <title>用户登陆</title> 
  <script type="text/javascript" src="${pageContext.request.contextPath}/js/broswerVersion.js"></script>
  <script type="text/javascript">
        var info = getOSAndBrowser();
        var browser = info.browser;
        var version = parseInt(info.version);
        var result = select(browser,version);
        var contextPath = "/"+location.pathname.split('/')[1];
        if(contextPath=="/"){
        	contextPath="";
        }
        if(!result){
        	window.location=contextPath+"/user/checkBrowserUI.action";
        }else{
        	window.location=contextPath+"/user/home.action";
        } 
  </script>
 </head>
  
 <body>
   
 </body>
</html>
