<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<title>开机状态</title>
<%@ include file="/WEB-INF/jsp/public/common.jspf"%>
</head>
<body>
	<div class="easyui-panel" data-options="fit:true,border:false">
		<c:forEach items="${status}" var="o">
			<div class="easyui-panel" data-options="title:'[${o.cardName}]系统服务'">
				<c:forEach items="${o.service}" var="service">
					<c:if test="${service.serv!='network' and service.serv!='mysqld'}">
						<c:if test="${service.serv=='sshd'}">
							SSH服务:
							<c:if test="${service.status=='running'}">运行中&nbsp;&nbsp;</c:if>
							<c:if test="${service.status=='stopped'}">已停止&nbsp;&nbsp;</c:if>
							<c:if test="${service.status=='master'}">主用&nbsp;&nbsp;</c:if>
							<c:if test="${service.status=='backup'}">备用&nbsp;&nbsp;</c:if>
							<c:if test="${service.status=='unrecognized'}">未识别&nbsp;&nbsp;</c:if>
						</c:if>
						<c:if test="${service.serv=='iptables'}">
							IPV4防火墙:
							<c:if test="${service.status=='running'}">运行中&nbsp;&nbsp;</c:if>
							<c:if test="${service.status=='stopped'}">已停止&nbsp;&nbsp;</c:if>
							<c:if test="${service.status=='master'}">主用&nbsp;&nbsp;</c:if>
							<c:if test="${service.status=='backup'}">备用&nbsp;&nbsp;</c:if>
							<c:if test="${service.status=='unrecognized'}">未识别&nbsp;&nbsp;</c:if>
						</c:if>
						<c:if test="${service.serv=='ip6tables'}">
							IPV6防火墙:
							<c:if test="${service.status=='running'}">运行中&nbsp;&nbsp;</c:if>
							<c:if test="${service.status=='stopped'}">已停止&nbsp;&nbsp;</c:if>
							<c:if test="${service.status=='master'}">主用&nbsp;&nbsp;</c:if>
							<c:if test="${service.status=='backup'}">备用&nbsp;&nbsp;</c:if>
							<c:if test="${service.status=='unrecognized'}">未识别&nbsp;&nbsp;</c:if>
						</c:if>
						<c:if test="${service.serv=='ip_forward'}">
							IP转发:
							<c:if test="${service.status=='on'}">开启&nbsp;&nbsp;</c:if>
							<c:if test="${service.status=='off'}">关闭&nbsp;&nbsp;</c:if>
						</c:if>
						<c:if test="${service.serv=='selinux'}">
							SELinux:
							<c:if test="${service.status=='enforcing'}">强制&nbsp;&nbsp;</c:if>
							<c:if test="${service.status=='permissive'}">保留&nbsp;&nbsp;</c:if>
							<c:if test="${service.status=='disabled'}">禁用&nbsp;&nbsp;</c:if>
						</c:if>
					</c:if>
				</c:forEach>
			</div>
			<table class="easyui-datagrid" data-options="title:'[${o.cardName}]CPU状态',fitColumns:true">   
			    <thead><tr>   
		            <th data-options="field:'name',width:'11%'">name</th>   
		            <th data-options="field:'us',width:'11%'">user mode</th>   
		            <th data-options="field:'sy',width:'11%'">system mode</th>
		            <th data-options="field:'ni',width:'11%'">nice</th>
		            <th data-options="field:'id',width:'11%'">idle task</th>
		            <th data-options="field:'wa',width:'11%'">I/O waiting</th>
		            <th data-options="field:'hi',width:'11%'">servicing IRQs</th>
		            <th data-options="field:'si',width:'11%'">servicing soft IRQs</th>
		            <th data-options="field:'st',width:'11%'">steal</th>
			    </tr></thead>   
			    <tbody>
				    <c:forEach items="${o.cpu}" var="cpu">
					<tr><td>${cpu.name}</td><td>${cpu.us}</td><td>${cpu.sy}</td><td>${cpu.ni}</td><td>${cpu.id}</td>
					<td>${cpu.wa}</td><td>${cpu.hi}</td><td>${cpu.si}</td><td>${cpu.st}</td>
					</tr> 
					</c:forEach>  
			    </tbody>   
			</table>
			<table class="easyui-datagrid" data-options="title:'[${o.cardName}]内存状态',fitColumns:true">   
			    <thead><tr>   
		            <th data-options="field:'name',width:'20%'">内存类型</th>   
		            <th data-options="field:'total',width:'20%'">内存总量</th>   
		            <th data-options="field:'used',width:'20%'">使用内存</th>
		            <th data-options="field:'avail',width:'20%'">可用内存</th>
		            <th data-options="field:'use',width:'20%'">内存使用率</th>
			    </tr></thead>   
			    <tbody>
				    <c:forEach items="${o.mem}" var="mem">
					<tr><td>${mem.name}</td><td>${mem.total}</td><td>${mem.used}</td><td>${mem.avail}</td><td>${mem.use}</td></tr> 
					</c:forEach>  
			    </tbody>   
			</table>
			
			<table class="easyui-datagrid" data-options="title:'[${o.cardName}]硬盘状态',fitColumns:true">   
			    <thead><tr>   
		            <th data-options="field:'filesystem',width:'30%'">文件系统</th>   
		            <th data-options="field:'type',width:'15%'">类型</th>   
		            <th data-options="field:'size',width:'10%'">大小</th>
		            <th data-options="field:'used',width:'10%'">已用</th>
		            <th data-options="field:'avail',width:'10%'">可用</th>
		            <th data-options="field:'use',width:'10%'">使用率</th>
		            <th data-options="field:'mounted-on',width:'10%'">挂载点</th>
			    </tr></thead>   
			    <tbody>
				    <c:forEach items="${o.disk}" var="disk">
					<tr><td>${disk.filesystem}</td><td>${disk.type}</td><td>${disk.size}</td><td>${disk.used}</td><td>${disk.avail}</td><td>${disk.use}</td><td>${disk['mounted-on']}</td></tr> 
					</c:forEach>  
			    </tbody>   
			</table>
			<table class="easyui-datagrid" data-options="title:'[${o.cardName}]网络接口',fitColumns:true">   
			    <thead><tr>   
		            <th data-options="field:'interface',width:100">接口</th>   
		            <th data-options="field:'up',width:100,
					formatter:function(value,row,rowIndex){
						if(value=='yes'){
							return '是';
						}
						if(value=='no'){
							return '否';
						}
					}">启用</th>   
		            <th data-options="field:'running',width:100,
					formatter:function(value,row,rowIndex){
						if(value=='yes'){
							return '是';
						}
						if(value=='no'){
							return '否';
						}
					}">正在运行</th>
		            <th data-options="field:'inet_addr',width:200">IP地址</th>
		            <th data-options="field:'mask',width:200">掩码</th>
		            <th data-options="field:'bcast',width:200">广播地址</th>
		            <th data-options="field:'inet6_addr',width:200">IPv6</th>
			    </tr></thead>   
			    <tbody>
				    <c:forEach items="${o['net-if']}" var="ifc">
					<tr><td>${ifc.name}</td><td>${ifc.up}</td><td>${ifc.running}</td><td>${ifc.inet_addr}</td><td>${ifc.mask}</td><td>${ifc.bcast}</td><td>${ifc.inet6_addr}</td></tr> 
					</c:forEach>
			    </tbody>   
			</table>
		</c:forEach>
	</div>
</body>
</html>