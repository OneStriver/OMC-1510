var contextPath="/"+location.pathname.split('/')[1];
$.extend($.fn.validatebox.defaults.rules, {    
	instIdValue:{
    	validator: function(value){
    		if(value>=1){
    			return true;
    		}else{
    			return false;
    		}
        },
        message: '请输入大于0的网元实例ID'
    },
	ip:{    
        validator: function(value){
			var regex_0_255 = "(25[0-5]|2[0-4]\\d|1\\d{2}|[1-9]?\\d)";
			var ipRegex = new RegExp("^(" + regex_0_255 + "\\.){3}" + regex_0_255+"$");
            return ipRegex.test(value);
        },
        message: '请输入一个合法的IP地址'   
    },
    ipAdd:{    
        validator: function(value){
			var regex_0_255 = "(25[0-5]|2[0-4]\\d|1\\d{2}|[1-9]?\\d)";
			var ipRegex = new RegExp("^(" + regex_0_255 + "\\.){3}" + regex_0_255+"$");
            return ipRegex.test(value);
        },
        message: '请输入正确的IP地址'   
    },
    cardNum:{
    	 validator: function(value){
    		return /^[+]?[0-9]+\d*$/i.test(value); 
    	},
    	message:'请输入数字'
    },
    slotNum:{
   	    validator: function(value){
   		 	return /^[+]?[0-9]+\d*$/i.test(value); 
   	 	},
   		message:'请输入数字'
   },
   length:{
	   validator: function(value){
   		if(value.length>=0&&value.length<=15){
   			var regex= /^[\s\S]*$/;
           	return regex.test(value);
   		}else{
   			return false;
   		}
       },
       message: '输入长度必须介于0到15之间'
   },
   terminalLength:{
	   validator: function(value){
		   if(value.length>=0&&value.length<=15){
			   var regex= /^[+]?[0-9]+\d*$/;
			   return regex.test(value);
		   }else{
			   return false;
		   }
	   },
	   message: '请输入数字,输入长度必须介于0到15之间'
   },
   departmentLength:{
	   validator: function(value){
		   if(value.length>=0&&value.length<=2){
			   var regex= /^[0-9]*$/;
			   return regex.test(value);
		   }else{
			   return false;
		   }
	   },
	   message: '请输入数字,输入长度必须介于0到2之间'
   },
   
   tblength:{
	   validator: function(value){
   		if(value.length>=0&&value.length<=999){
   			var regex= /^[\s\S]*$/;
           	return regex.test(value);
   		}else{
   			return false;
   		}
       },
       message: '请输入合适的长度'
   },
   
    ipABC:{    
        validator: function(value){
			var regexipA = "^([1-9]|\\d{2}|1[0-1]\\d|12[0-6])\\.([0-9]|\\d{2}|1[0-9]\\d|25[0-5])\\.(25[0-5]|2[0-4]\\d|1\\d{2}|[1-9]?\\d)\\.(25[0-4]|2[0-4]\\d|1\\d{2}|[1-9]?\\d)$";
			var regexipB = "^(12[8-9]|1[3-8]\\d|191)\\.(25[0-5]|2[0-4]\\d|1\\d{2}|[1-9]?\\d)\\.(25[0-5]|2[0-4]\\d|1\\d{2}|[1-9]?\\d)\\.(25[0-4]|2[0-4]\\d|1\\d{2}|[1-9]?\\d)$";
			var regexipC = "^(19[2-9]|2[0-1]\\d|22[0-3])\\.(25[0-5]|2[0-4]\\d|1\\d{2}|[1-9]?\\d)\\.(25[0-5]|2[0-4]\\d|1\\d{2}|[1-9]?\\d)\\.(25[0-4]|2[0-4]\\d|1\\d{2}|[1-9]?\\d)$";
			var ipRegexA = new RegExp(regexipA);
			var ipRegexB = new RegExp(regexipB);
			var ipRegexC = new RegExp(regexipC);
			var result = ipRegexA.test(value)|ipRegexB.test(value)|ipRegexC.test(value)
            return result;
        },
        message: '请输入一个合法的A.B.C类地址'   
    },
    Mask:{    
        validator: function(value){
			var regex1 = "^(255\\.255\\.255\\.(0|128|192|224|240|248|252|254|255))$";
			var regex2 = "^(255\\.255\\.(0|128|192|224|240|248|252|254|255)\\.0)$";
			var regex3 = "^(255\\.(0|128|192|224|240|248|252|254|255)\\.0\\.0)$";
			var maskRegex1 = new RegExp(regex1);
			var maskRegex2 = new RegExp(regex2);
			var maskRegex3 = new RegExp(regex3);
			var result = maskRegex1.test(value)|maskRegex2.test(value)|maskRegex3.test(value)
            return result;
        },
        message: '请输入一个合法的掩码地址'   
    },
    realmName:{
    	validator:function(value){
    		var regex = "^(([A-Z]|[a-z]|\-|[0-9]|\\.)*)$";
    		var RealmName = new RegExp(regex);
    		var result = RealmName.test(value)
    		return result;
    	},
    	message:'请输入正确的域名'
    },
    MTU:{
    	validator: function(value){
    		if(value>=60&&value<=9200){
    			var regex= /^\d+$/;
            	return regex.test(value);
    		}else{
    			return false;
    		}
        },
        message: '请输入60到9200的MTU值'
    },
    checkParam : {
		validator : function(value, param) {
			var regex = /^[A-F\d]{2}$/;
			return regex.test(value);
		},
		message : '只能输入两位16进制的鉴权参数'
	},
    eth:{
    	validator: function(value){
//    			var regex= /^eth\d:[0-9]+$/;
//            	return regex.test(value);
    			return true;
        },
//        message: '请输入例如ethx:x格式'
        message: '请输入静态网卡名称'
    },
    activeEth:{
    	validator: function(value){
//			var regex= /^eth\d:[0-9]+$/;
//        	return regex.test(value);
    		return true;
    	},
//    	message: '请输入例如ethx:x格式'
    	message: '请输入动态网卡名称'
    },
    ipdot3:{    
        validator: function(value){
			var regex_0_255 = "(25[0-5]|2[0-4]\\d|1\\d{2}|[1-9]?\\d)";
			var ipRegex = new RegExp("^(" + regex_0_255 + "\\.){3}$");
            return ipRegex.test(value);
        },
        message: '请输入一个合法的3字节IP'   
    },
    phone:{
    	validator: function(value){
			var regex= /^1[3456789]\d{9}$/;
            return regex.test(value);
        },
        message: '请输入一个合法的11位电话号码'
    },
    imsi:{
    	validator: function(value){
			var regex= /^\d{15}$/;
            return regex.test(value);
        },
        message: '请输入一个合法的15位IMSI数字' 
    },
    mdn:{
    	validator: function(value){
    		if(value.length>=3&&value.length<=15){
    			var regex= /^\d+$/;
            	return regex.test(value);
    		}else{
    			return false;
    		}
        },
        message: '请输入3到15位的数字'
    },
    mdnMax:{
    	validator: function(value){
    		if(value.length>=3&&value.length<=15){
    			var regex= /^\d+$/;
            	return regex.test(value);
    		}else{
    			return false;
    		}
        },
        message: '请输入3到15位的数字'
    },
    countMax:{
    	validator: function(value){
    		if(value>=1&&value<=300000){
    			var regex= /^\d+$/;
            	return regex.test(value);
    		}else{
    			return false;
    		}
        },
        message: '请输入1到300000的数字'
    },
    batchCountMax:{
    	validator: function(value){
    		if(value>=1&&value<=400){
    			var regex= /^\d+$/;
    			return regex.test(value);
    		}else{
    			return false;
    		}
    	},
    	message: '请输入1到400的数字'
    },
    esn:{
    	validator: function(value){
    		var regex= /^\d+$/;
    		var regex= /^[0-9a-fA-F]{8}$/;
    		return regex.test(value);
        },
        message: '请输入8位16进制的序号'
    },
    equals:{    
        validator: function(value,param){
            return value == $(param[0]).textbox('getText');    
        },    
        message: '该字段与{1}不符'   
    },
    minValue: {    
        validator: function(value, param){
        	var regex= /^\d+$/;
            return regex.test(value)&&value>=param[0]
        },    
        message: '输入项必须大于{0}.'
    },
    maxValue:{
    	validator: function(value, param){
        	var regex= /^\d+$/;
            return regex.test(value)&&value<=param[0];
        },    
        message: '输入项必须小于{0}.'
    },
    numberTranslation:{
    	validator: function(value, param){
        	var regex= /^[0-9*#X?]{3,14}$/;
            return regex.test(value);
        },    
        message: '输入项为3到14位数字 ，*，#，?，X组合'
    },
    numberTranslationFrom:{
    	validator: function(value, param){
    		return $('input[name=ori][value="'+value+'"]').length==1;
        },    
        message: '转换规则不能重复'
    },
    numberTranslationTo:{
    	validator: function(value, param){
    		function getXCount(str){
    			var count=0;
    			for(var i=0;i<str.length;i++){
    				if(str.charAt(i)=='X')
    					count++;
    			}
    			return count;
    		}
    		console.log(this);
    		var ori=$(this).parent().parent().find('input[name=ori]').val();
    		var des=$(this).next().val();
    		var oriX=getXCount(ori);
    		var desX=getXCount(des);
    		console.log(ori);
    		console.log(des);
    		if(desX==0) return true;
    		else if(oriX!=desX) return false;
    		return true;
        },    
        message: '转换规则X位数错误'
    },
    integer:{
    	validator: function(value, param){
    		var regex= /^\d+$/;
            return regex.test(value);
        },    
        message: '输入项为任意数字'
    },
    sqlFile:{
    	validator: function(value, param){
    		var regex= /^.*\.sql$/;
            return regex.test(value);
        },    
        message: '文件扩展名为.sql'
    },
    zipFile:{
    	validator: function(value, param){
    		var regex= /^.*\.zip$/;
    		if($.isArray(value)){
    			for(var i in value){
    				if(!regex.test(value[i])) 
    					return false;
    			}
    			return true;
    		}else{
    			return regex.test(value);
    		}
        },    
        message: '文件扩展名为.zip'
    },
    excelFile:{
    	validator: function(value, param){
    		var regex= /^.*\.xls$/;
    		if($.isArray(value)){
    			for(var i in value){
    				if(!regex.test(value[i])) 
    					return false;
    			}
    			return true;
    		}else{
    			return regex.test(value);
    		}
        },    
        message: '文件扩展名为.xls'
    },
    xmlFile:{
    	validator: function(value, param){
    		var regex= /^.*\.xml$/;
            return regex.test(value);
        },   
        message: '文件扩展名为.xml'
    }
}); 

Date.prototype.format=function(){
	var year=this.getUTCFullYear();
	var month=this.getMonth() + 1;
	if(month<10)
		month='0'+month;
	var day=this.getDate();
	if(day<10)
		day='0'+day;
	var hour=this.getHours();
	if(hour<10)
		hour='0'+hour;
   	var minute=this.getMinutes ();
   	if(minute<10)
		minute='0'+minute;
    var second=this.getSeconds();
   	if(second<10)
		second='0'+second;
   	var millisecond=this.getUTCMilliseconds();
   	return year+'-'+month+'-'+day+' '+hour+':'+minute+':'+second;
}

function submitSuccess(data){
	var json=$.parseJSON(data);
	if(json&&json.error) {
		$.messager.alert('出错提示',json.error,'error');
		return false;
	}else if(json.msg){
		if($('#w')){
			$('#w').window('close');
		}
		$.messager.show({
			title:'操作提示',
			msg:json.msg,
			showType:'fade',
			timeout:2000,
			style:{
				right:'',bottom:''
			}
		});
		if($('#dg')){
			$('#dg').datagrid('reload');
		} 
		return true;
	}else{
		alert('未预料消息:'+data);
		return false;
	}
}