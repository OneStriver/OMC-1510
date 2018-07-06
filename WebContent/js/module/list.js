function onRowContextMenu(e, index, row){
	e.preventDefault();
	if(row==null)return ;
	var cmenu;
	createColumnMenu(index);
	cmenu.menu('show', {
		left:e.pageX-5,
		top:e.pageY-5
	});
	
	function createColumnMenu(index){
		cmenu = $('<div/>').appendTo('body');
		cmenu.menu({
			onClick: function(item){
				if(item.name == 'delete'){
					$.post(contextPath+"/module/delete.action",{ids:row.id},
						function(data){
							submitSuccess(data);
						});
				}else if(item.name == 'openFileManager'){
					var href=contextPath+'/module/comein.action?name='+row.name;
					$('#content').prop('src',href);
					$('#filesW').window('open');
				}
				cmenu.remove();
			}
		});
		cmenu.menu('appendItem',{
			text: '删    除',
			name: 'delete',
			iconCls: 'icon-ok'
		}).menu('appendItem',{
			text: '打开文件管理器',
			name: 'openFileManager',
			iconCls: 'icon-ok'
		});
	}
}
