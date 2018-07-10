
$(function(){
	$('td:even').css({'text-align':'left'});
	$('.easyui-combobox').combobox({width:134});
	$('#sipLocalSipIP').textbox({
        events: $.extend({},$.fn.textbox.defaults.events,{
        	 keypress:function(event){
        		var eventObj = event || e;  
       	        var keyCode = eventObj.keyCode || eventObj.which;  
       	        if ((keyCode==37) || (keyCode==39) || (keyCode==118) || (keyCode==99) || (keyCode==8) || (keyCode >= 48 && keyCode <= 57) || (keyCode == 46)){
       	            return true;  
       	        }else{
       	            return false;
       	        }  
	         },
	         compositionstart:function(event){
				console.log("中文输入:开始");
				zhInputFlag = true;
			 },
			 compositionend:function(event){
			 	console.log("中文输入:结束");
			 	if(zhInputFlag){
			 		this.value=this.value.replace(/[a-zA-Z]/g,'');
			 		this.value=this.value.replace(/[\u4e00-\u9fa5]/g,'');
			 	}
			 	zhInputFlag = false;
			 },
			 input:function(event){
			 	this.value = this.value.replace(/[\uff0c|\u3002|\u3001|\uff1b|\u003d|\u2018|\u2019|\u3010|\u3011||\u2018|\u002d|\u002c|\u002f|\u003b|\u005b|\u005d|\u005c|']/g,'');
			 }
         })
   	});
	$('#sipLocalSipPort').textbox({
		events: $.extend({},$.fn.textbox.defaults.events,{
			keypress:function(event){
				var eventObj = event || e;  
				var keyCode = eventObj.keyCode || eventObj.which;  
				if ((keyCode==37) || (keyCode==39) || (keyCode==118) || (keyCode==99) || (keyCode==8) || (keyCode >= 48 && keyCode <= 57)){
					return true;  
				}else{
					return false;
				}  
			},
			compositionstart:function(event){
			    console.log("中文输入:开始");
			    zhInputFlag = true;
			},
			compositionend:function(event){
				console.log("中文输入:结束");
			 	if(zhInputFlag){
			 		this.value=this.value.replace(/[a-zA-Z]/g,'');
			 		this.value=this.value.replace(/[\u4e00-\u9fa5]/g,'');
				}
				zhInputFlag = false;
			},
			input:function(event){
				this.value = this.value.replace(/[\uff0c|\u3002|\u3001|\uff1b|\u003d|\u2018|\u2019|\u3010|\u3011||\u2018|\u002d|\u002c|\u002f|\u003b|\u005b|\u005d|\u005c|']/g,'');
			}
		})
	});
	$('#sipRemoteSipIP').textbox({
		events: $.extend({},$.fn.textbox.defaults.events,{
			keypress:function(event){
				var eventObj = event || e;  
				var keyCode = eventObj.keyCode || eventObj.which;  
				if ((keyCode==37) || (keyCode==39) || (keyCode==118) || (keyCode==99) || (keyCode==8) || (keyCode >= 48 && keyCode <= 57) || (keyCode == 46)){
					return true;  
				}else{
					return false;
				}  
			},
			compositionstart:function(event){
				console.log("中文输入:开始");
				zhInputFlag = true;
			 },
			 compositionend:function(event){
			 	console.log("中文输入:结束");
			 	if(zhInputFlag){
			 		this.value=this.value.replace(/[a-zA-Z]/g,'');
			 		this.value=this.value.replace(/[\u4e00-\u9fa5]/g,'');
			 	}
			 	zhInputFlag = false;
			 },
			 input:function(event){
			 	this.value = this.value.replace(/[\uff0c|\u3002|\u3001|\uff1b|\u003d|\u2018|\u2019|\u3010|\u3011||\u2018|\u002d|\u002c|\u002f|\u003b|\u005b|\u005d|\u005c|']/g,'');
			 }
		})
	});
	$('#sipRemoteSipPort').textbox({
		events: $.extend({},$.fn.textbox.defaults.events,{
			keypress:function(event){
				var eventObj = event || e;  
				var keyCode = eventObj.keyCode || eventObj.which;  
				if ((keyCode==37) || (keyCode==39) || (keyCode==118) || (keyCode==99) || (keyCode==8) || (keyCode >= 48 && keyCode <= 57)){
					return true;  
				}else{
					return false;
				}  
			},
			compositionstart:function(event){
			    console.log("中文输入:开始");
			    zhInputFlag = true;
			},
			compositionend:function(event){
				console.log("中文输入:结束");
			 	if(zhInputFlag){
			 		this.value=this.value.replace(/[a-zA-Z]/g,'');
			 		this.value=this.value.replace(/[\u4e00-\u9fa5]/g,'');
				}
				zhInputFlag = false;
			},
			input:function(event){
				this.value = this.value.replace(/[\uff0c|\u3002|\u3001|\uff1b|\u003d|\u2018|\u2019|\u3010|\u3011||\u2018|\u002d|\u002c|\u002f|\u003b|\u005b|\u005d|\u005c|']/g,'');
			}
		})
	});
});

function submit(){
	$('#ff').form('submit',{
		success:function(data){
			$.messager.progress('close');
			submitSipSuccess(data);
		},
		onSubmit:function(){
			var sipRoutingNumsArray = [];
			var sipRoutingNums = $("input[name='routingNum']");    
			$.each(sipRoutingNums,function(index,object){
					sipRoutingNumsArray.push($(object).val());
				}   
			);
			var isOk=$(this).form('enableValidation').form('validate');
			if(isOk){
				var sipRoutingNumsArraySort = sipRoutingNumsArray.sort();
				for(var i=0;i<sipRoutingNumsArraySort.length;i++){
					if (sipRoutingNumsArraySort[i]==sipRoutingNumsArraySort[i+1]){
						isOk = false;
					}
				}
				if(isOk){
					$.messager.progress({msg:'正在保存请稍等'});
				}else{
					$.messager.alert("提示","SIP本地出局前缀号段存在重复的号段！");
				}
			}else{
				isOk = false;
			}
			return isOk;
		}
	});
}

function add(){
	var num=$('#add input').length/3;
	if(num>=0){
		$("#cb").prop("checked",false);
		$('#flag').textbox('setValue',1);
		$('#sipRoutNumCount').numberbox({
			disabled : false
		});
	}
	if(num<10){
		$("<input class='easyui-textbox' id='routingNum_"+(num+1)+"' name='routingNum' data-options='precision:0,width:80'/>").appendTo("#add");
		$.parser.parse();
		$('#routingNum_'+(num+1)).textbox({
            required:true,
            validType:'sipRoutingNum'
        });
		$('#routingNum_'+(num+1)).textbox({
			events: $.extend({},$.fn.textbox.defaults.events,{
				keypress:function(event){
					var eventObj = event || e;  
					var keyCode = eventObj.keyCode || eventObj.which;  
					if ((keyCode==37) || (keyCode==39) || (keyCode==118) || (keyCode==8) || (keyCode >= 48 && keyCode <= 57)){
						return true;  
					}else{
						return false;
					}  
				},
				compositionstart:function(event){
				    console.log("中文输入:开始");
				    zhInputFlag = true;
				},
				compositionend:function(event){
					console.log("中文输入:结束");
				 	if(zhInputFlag){
				 		this.value=this.value.replace(/[a-zA-Z]/g,'');
				 		this.value=this.value.replace(/[\u4e00-\u9fa5]/g,'');
					}
					zhInputFlag = false;
				},
				input:function(event){
					this.value = this.value.replace(/[\uff0c|\u3002|\u3001|\uff1b|\u003d|\u2018|\u2019|\u3010|\u3011||\u2018|\u002d|\u002e|\u002c|\u002f|\u003b|\u005b|\u005d|\u005c|']/g,'');
				}
			})
		});
		$('#routingNum_'+(num+1)).textbox('textbox').attr('maxlength',10);
	}else{
		$.messager.alert("警告","已达到可添加的上限");
	}
}

function defaultRouting(){
	var cb = $('#cb');
	var isChecked = cb.is(':checked');	
	var val = 0; 
	if(!isChecked)val=1;
	$('#flag').textbox('setValue',val);
	
	if(isChecked){
		$('#flag').textbox('setValue',0);
		$('#sipRoutNumCount').numberbox({
			disabled : true
		});
	}else{
		$('#flag').textbox('setValue',1);
		$('#sipRoutNumCount').numberbox({
			disabled : false
		});
	}
}

function submitSipSuccess(data){
	var json=$.parseJSON(data);
	console.log("json.msg:"+json.msg);
	if(json&&json.error) {
		$.messager.alert('出错提示',json.error,'error');
		return false;
	}else if(json.msg){
		$.messager.show({
			title:'操作提示',
			msg:json.msg+',请点击刷新按钮更新数据!',
			showType:'fade',
			timeout:1000,
			style:{
				right:'',bottom:''
			}
		});
		return true;
	}else{
		alert('未预料消息：'+data);
		return false;
	}
}