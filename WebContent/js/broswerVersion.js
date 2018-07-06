function select() {
	var browser = arguments[0];
	var version = arguments[1];
	/*if (browser.toLowerCase() == "firefox") {
		return true;
	}*/
	return true;
};
var getOSAndBrowser = function() {
	var os = navigator.platform;
	var userAgent = navigator.userAgent;
	var tempArray = "";
	var msg = {
		os : '',
		browser : '',
		version : ''
	};
	// 判断操作系统
	if (os.indexOf("Win") > -1) {
		if (userAgent.indexOf("Windows NT 5.0") > -1) {
			msg.os = "Win2000";
		} else if (userAgent.indexOf("Windows NT 5.1") > -1) {
			msg.os = "WinXP";
		} else if (userAgent.indexOf("Windows NT 5.2") > -1) {
			msg.os = "Win2003";
		} else if (userAgent.indexOf("Windows NT 6.0") > -1) {
			msg.os = "WindowsVista";
		} else if (userAgent.indexOf("Windows NT 6.1") > -1
				|| userAgent.indexOf("Windows 7") > -1) {
			msg.os = "Win7";
		} else if (userAgent.indexOf("Windows NT 6.2") > -1
				|| userAgent.indexOf("Windows 8") > -1) {
			msg.os = "Win8";
		} else if (userAgent.indexOf("Windows NT 6.3") > -1
				|| userAgent.indexOf("Windows 8.1") > -1) {
			msg.os = "Win8.1";
		} else if (userAgent.indexOf("Windows NT 10.0") > -1
				|| userAgent.indexOf("Windows 10") > -1) {
			msg.os = "Win10";
		} else {
			msg.os = "Other";
		}
	} else if (os.indexOf("Mac") > -1) {
		msg.os = "Mac";
	} else if (os.indexOf("X11") > -1) {
		msg.os = "Unix";
	} else if (os.indexOf("Linux") > -1) {
		msg.os = "Linux";
	} else {
		msg.os = "Other";
	}

	// 判断浏览器版本
	var isOpera = userAgent.indexOf("Opera") > -1; // 判断是否Opera浏览器
	var isIE = userAgent.indexOf("compatible") > -1 && userAgent.indexOf("MSIE") > -1 && !isOpera; // 判断是否IE浏览器
	var isEdge = userAgent.toLowerCase().indexOf("edge") > -1 && !isIE; // 判断是否IE的Edge浏览器
	var isIE11 = (userAgent.toLowerCase().indexOf("trident") > -1 && userAgent.indexOf("rv") > -1);
	if (/[Ff]irefox(\/\d+\.\d+)/.test(userAgent)) {
		tempArray = /([Ff]irefox)\/(\d+\.\d+)/.exec(userAgent);
		msg.browser = tempArray[1];
		msg.version = tempArray[2];
	} else if (isIE) {
		var version = "";
		var reIE = new RegExp("MSIE (\\d+\\.\\d+);");
		reIE.test(userAgent);
		var fIEVersion = parseFloat(RegExp["$1"]);
		if (fIEVersion == 7) {
			msg.browser = 'IE';
			msg.version = '7';
		} else if (fIEVersion == 8) {
			msg.browser = 'IE';
			msg.version = '8';
		} else if (fIEVersion == 9) {
			msg.browser = 'IE';
			msg.version = '9';
		} else if (fIEVersion == 10) {
			msg.browser = 'IE';
			msg.version = '10';
		} else {
			msg.browser = 'IE';
			msg.version = '0';
		}
	} else if (isEdge) {
		msg.browser = 'Edge';
	} else if (isIE11) {
		msg.browser = 'IE';
		msg.version = '11';
	} else if (/[Cc]hrome\/\d+/.test(userAgent)) {
		tempArray = /([Cc]hrome)\/(\d+)/.exec(userAgent);
		msg.browser = tempArray[1];
		msg.version = tempArray[2];
	} else if (/[Vv]ersion\/\d+\.\d+\.\d+(\.\d)* *[Ss]afari/.test(userAgent)) {
		tempArray = /[Vv]ersion\/(\d+\.\d+\.\d+)(\.\d)* *([Ss]afari)/.exec(userAgent);
		msg.browser = tempArray[3];
		msg.version = tempArray[1];
	} else if (/[Oo]pera.+[Vv]ersion\/\d+\.\d+/.test(userAgent)) {
		tempArray = /([Oo]pera).+[Vv]ersion\/(\d+)\.\d+/.exec(userAgent);
		msg.browser = tempArray[1];
		msg.version = tempArray[2];
	} else {
		msg.browser = "unknown";
		msg.version = "unknown";
	}
	return msg;
};