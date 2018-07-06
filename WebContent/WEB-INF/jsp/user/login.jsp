<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<title>用户登录</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/login.css" />
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/easyui/jquery.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/easyui/jquery.cookie.js"></script>
<script type="text/javascript">
/* 	$(function() {
		$('.loginbox').css({
			'position' : 'absolute',
			'left' : ($(window).width()) / 4,
			'top' : ($(window).height()) / 3
		});
		$(window).resize(function() {
			$('.loginbox').css({
				'position' : 'absolute',
				'left' : ($(window).width()) / 4,
				'top' : ($(window).height()) / 3
			});
		});
       
	}); */
	
	//	清除session，防止登录页面在子页面出现
	if (window.parent != window) {
		window.parent.location.reload(true);
	}

	$(function() {
		//language change
		$('#switch_language')
				.change(
						function() {
							window.location = '${pageContext.request.contextPath}/user/switchLanguage.action?locale='
									+ $(this).val();
						});
		if (!window.WebSocket) {
			alert('您的浏览器版本过低,建议使用新版Chrome浏览器！');
		}
	});
	
</script>
<link rel="icon" href="${pageContext.request.contextPath}/images/logo.png">
<!-- 解决chrome浏览器中输入框颜色呈现黄色的问题 -->
<style type="text/css">
input:-webkit-autofill {
    -webkit-box-shadow: 0 0 0px 1000px #ecf5fa inset !important;
}
</style>
</head>

<body style="margin:0;padding:0;">
	<div>
	<img id="backImg" style="height:100%;width:100%;z-index:1;" src="${pageContext.request.contextPath}/images/loginImg/bannerImage2.jpg" width="" height="">
	<div class="loginbox" style="z-index:2;">
		<form class="loginform" method="post"
			action="${pageContext.request.contextPath}/user/login.action">
			<input type="hidden" name="_method" value="login" />
			<ul id="context">
				<li>
				<div class="loginuser">
				<input name="username" class="loginUserInput" type="text" placeholder="请输入用户名"
					value="${loginName}" onclick="JavaScript:this.value=''"
					/>
				</div>
				</li>
				<li>
				<div class="loginpwd">
				<input name="password" class="loginPwdInput" type="password" placeholder="请输入密码"
					value="${password}" onclick="JavaScript:this.value=''"
					/>
				</div>
				</li>
				<li><input type="submit" class="loginbtn"
					value="<spring:message code="login"></spring:message>" /> <input
					id="reset" type="reset" class="loginbtn"
					value="<spring:message code="reset"></spring:message>" /> <label><input
						name="" type="checkbox" value="" />记住密码</label> <c:if test="${empty locale}">
						<%session.setAttribute("locale", "zh_CN"); %>
					</c:if>
					<c:if test="${locale=='zh_CN'}">
						<a href="${pageContext.request.contextPath}/user/switchLanguage.action?locale=en_US">English</a>
					</c:if>
					<c:if test="${locale=='en_US'}"><a href="${pageContext.request.contextPath}/user/switchLanguage.action?locale=zh_CN">简体中文</a></c:if>
				</li>
				<li id="msg">${msg}</li>
			</ul>
		</form>
	</div>
	</div>
	
	<script type="text/javascript">
		$(function() {
			if ($.cookie('isChecked') == 'true') {
				var name = $.cookie('name');
				var password = $.cookie('password');
				$('input[name="username"]').val(name);
				$('input[name="password"]').val(password);
				$('input[type="checkbox"]').prop('checked', true);
			} else {
				$('input[name="username"]').val('');
				$('input[name="password"]').val('');
			}

			$('form').on(
					'submit',
					function() {
						var isChecked = $('input[type="checkbox"]')
								.prop('checked');
						if (isChecked) {
							var name = $('input[name="username"]')
									.val();
							var password = $('input[name="password"]')
									.val();
							$.cookie('name', name, {
								expires : 7
							});
							$.cookie('password', password, {
								expires : 7
							});
						}
						$.cookie('isChecked', isChecked, {
							expires : 7
						});
						return true;
					});

		});
	</script>
	
</body>

</html>