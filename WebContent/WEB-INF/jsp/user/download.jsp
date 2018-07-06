<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<title>浏览器下载</title>
<style type="text/css">
        *
        {
            margin: 0;
            padding: 0;
        }
        html, body
        {
            color: #333333;
            width: 100%;
            height: 100%;
            font-family: Arial;
            margin:0 auto;
            text-align:center;
        }
        .container
        {
             margin:0 auto;
             height:100%;
             vertical-align:middle;
        }
        /*警示图标除外样式*/
        .word
        {
            padding-left: 42px;
            padding-top: 12px;
            margin: 0 auto;
            max-width: 480px;
            _width: 480px;
        }
        .word_a
        {
            font-size: 16px;
            font-weight: bold;
            color: #000000;
            padding-bottom: 23px;
        }
        .word_b
        {
            font-size: 14px;
            line-height: 24px;
            color: #000000;
        }
        .word_c
        {
            font-size: 14px;
            font-weight: bold;
            color: #333333;
            margin: 30px 0 12px 0;
        }
        
    </style>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/easyui/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/broswerVersion.js"></script>
<script type="text/javascript">
$(function(){
	 var info = getOSAndBrowser()
     $("#os").html("当前操作系统:"+info.os);
     $("#browser").html("浏览器版本:"+info.browser+info.version);
    // $("#a1").bind('click',function(){
    	// var contextPath="/"+location.pathname.split('/')[1];
     	 //var url = contextPath+"/browser/download.action";
     	
     	 //open(url,null,"height=200,width=400,status=yes,toolbar=no,menubar=no,location=no"); 
     	
     //});
});

    
</script>
</head>

<body >
	    <table class="container">
        <tbody><tr>
            <td valign="middle">
                <div class="word">
                <div style="position:relative;left:-42px">
                    <span class="word_a">系统暂不支持当前浏览器版本</span>
                  </div>
                  <div>
                   <span class="word_b" style="line-height:27px" id="os"></span>
                   <br/>
                    <span class="word_b" style="line-height:25px" id="browser"></span>
                  </div>                                                   
                    <div class="word_b">
                       	推荐使用&nbsp;<span  style="text-decoration: none;color:red">FireFox浏览器</span><br>
                       </div>
                    <div class="word_c">
                       	 相关应用下载
                    </div>   
                    <div class="img_download">
                        <a href="${pageContext.request.contextPath}/${downloadPath}" id="a1">下载</a>
                    </div>
                </div>
            </td>
        </tr>
    </tbody></table>
</body>

</html>