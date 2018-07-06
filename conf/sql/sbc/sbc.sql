insert into config (name,description) values
('net','网络配置'),
('tls','TLS参数配置'),
('performance','性能参数配置'),
('service','基本服务配置'),
('runtime','运行参数配置'),
('board','板卡规划配置'),
('startup','开机启动'),
('groupManage','组配置'),
('groupPolicy','组策略配置'),
('groupEnsure','用户优先保障策略'),
('acl','ACLInfo'),
('blacklist','黑名单'),
('spite','恶意访问检测策略'),
('business','业务限制策略'),
('media','媒体链路信息'),
('status','用户注册状态'),
('boardStatus','板卡状态'),
('alarm','运行告警'),
('statistics','统计信息');

#-- ----------系统配置 开始-------------
insert into item 
(name,description,formtype,min,max,configId)
values
('UniNetworkAddr','用户侧网络地址','ip',null,null,1),
('UniNetworkPort','用户侧端口','numberbox',1,65535,1),
('NatUniNetwkAddr','用户侧NAT地址','ip',null,null,1),
('NniNetworkAddr','网络侧网络地址','ip',null,null,1),
('NniNetworkPort','网络侧端口','numberbox',1,65535,1),
('NatNniNetwkAddr','网络侧NAT地址','ip',null,null,1),
('MsrpServerIp','MSRP服务器地址','hidden',null,null,1),
('MgcUniAddr','MGC服务器用户侧地址','hidden',null,null,1),
('MgcNniAddr','MGC服务器网络侧地址','hidden',null,null,1),
('RemoteCscfIp','PCSCF地址','ip',null,null,1),
('RemoteCscfPort','PCSCF端口','numberbox',0,65535,1),
('InitMode','运行模式','combobox',null,null,null),#id=12  0 为单板运行；1为主板；2为备板
('BackupUniIp','备板用户侧IP地址','ip',null,null,null),#id=13 IP地址InitMode =1或者2
('BackupNniIp','备板网络侧地址','ip',null,null,null),#id=14 IP地址InitMode =1或者2
('TLSFunc','TLS功能开关','combobox',null,null,2),#id=15 0 表示关，1表示开。
('ClientCertificate','客户端证书文件','textbox',null,null,2),
('ClientKey','客户端键值文件','textbox',null,null,2),
('ServerCertificate','服务端证书文件','textbox',null,null,2),
('ServerKey','服务端键值文件','textbox',null,null,2),
('CACertificate','CA证书文件','textbox',null,null,2),
('MaxRegUserNum','最大用户注册数','numberbox',0,null,3),
('MaxSessionNum','最大会话数','numberbox',0,null,3),
('MaxCallSessionNum','最大呼叫会话数','numberbox',0,null,3),
('MaxCpuUsed','最大系统CPU使用率','numberbox',1,99,3),
('MaxMemUsed','最大系统MEM使用率','numberbox',1,99,3),
('MaxLanRate','最大网口速率','numberbox',1,null,3),
('MaxSigRate','最大信令速率','numberbox',1,null,3),
('MaxBandWidthUsed','最大媒体带宽速率','numberbox',0,null,3),
('MaxRegRate','最大注册速率','numberbox',1,null,null),
('MaxCallRate','最大呼叫速率','numberbox',1,null,null),
('SSH','SSH服务','combobox',null,null,4),#id=31 0关,1开
('FTP','FTP服务','combobox',null,null,4),#id=32 0关,1开
('HTTP','HTTP服务','combobox',null,null,4),#id=33 0关,1开
('TFTP','TFTP服务','combobox',null,null,4),#id=34 0关,1开
('DNS','DNS服务','combobox',null,null,4),#id=35 0关,1开
('BlkTimerVal','黑名单定时时长','numberbox',1,5,5),
('SigBoard1_UniAddr','信令板1用户侧IP地址','ip',null,null,6),
('SigBoard1_NniAddr','信令板1网络侧IP地址','ip',null,null,6),
('SigBoard2_UniAddr','信令板2用户侧IP地址','ip',null,null,6),
('SigBoard2_NniAddr','信令板2网络侧IP地址','ip',null,null,6),
('MediaBoard_UniAddr','媒体板用户侧IP地址','ip',null,null,6),
('MediaBoard_NniAddr','媒体板网络侧IP地址','ip',null,null,6),
('StartType','启动类型','numberbox',0,15,7);
#------------系统配置 结束-------------
#------------ CAC策略配置 开始------------
insert into item 
(name,description,formtype,min,max,configId,parentId)
values
('GoupManage','组配置','grid',null,null,8,null),# id=44
#('Location','组IP地址段','textbox',null,null,8,44),
('GroupCacProfileID','组CAC配置项ID','combobox',1,null,8,44),
('GroupID','组ID','numberbox',1,null,8,44),
('ParentGroupID','所属组ID','numberbox',1,null,8,44),
('GroupStartMdn','组起始MDN','numberbox',null,null,8,44),
('GroupEndMdn','组结束MDN','numberbox',null,null,8,44),
('GroupStartIp','组开始IP','ip',null,null,8,44),

('GroupPolicy','组策略配置','grid',null,null,9,null),# id=51
('GroupPolicy_maxUserRegNum','最大注册用户数','numberbox',0,null,9,51),
('GroupPolicy_maxSessionNum','最大会话数','numberbox',0,null,9,51),
('GroupPolicy_sigQos','组信令Qos','numberbox',0,63,9,51),
('GroupPolicy_meidaQos','组媒体Qos','numberbox',0,63,9,51),
('GroupPolicy_sigRate','最大信令速率','numberbox',1,null,9,51),
('GroupPolicy_mediaRate','最大媒体速率','numberbox',0,null,9,51),
('GroupPolicy_sigRatePerUser','最大单用户信令速率','numberbox',0,null,9,51),
('GroupPolicy_mediaRatePerUser','最大单用户媒体速率','numberbox',1,null,9,51),
('GroupPolicy_cpuUsed','最大CPU使用率','numberbox',1,null,9,51),
('GroupPolicy_maxRegRate','最大注册速率','numberbox',0,null,9,51),
('GroupPolicy_maxCallRate','最大呼叫速率','numberbox',0,null,9,51),
('GroupPolicy_cacProfileID','组CAC配置ID','numberbox',1,null,9,51),

('Level0_UserPriority','0级保障用户级别','numberbox',null,null,10,null),# id=64
('Level0_CPUUsed','0级保障CPU使用率','numberbox',null,null,10,null),
('Level0_BWUsed','0级保障媒体使用率','numberbox',null,null,10,null),
('Level1_UserPriority','1级保障用户级别','numberbox',null,null,10,null),
('Level1_CPUUsed','1级保障CPU使用率','numberbox',null,null,10,null),
('Level1_BWUsed','1级保障媒体使用率','numberbox',null,null,10,null),
('Level2_UserPriority','2级保障用户级别','numberbox',null,null,10,null),
('Level2_CPUUsed','2级保障CPU使用率','numberbox',null,null,10,null),
('Level2_BWUsed','2级保障媒体使用率','numberbox',null,null,10,null),

#------------ CAC策略配置 结束------------
#------------ 访问控制策略 开始------------
('Acl','ACLInfo','grid',null,null,11,null),# id=73
('ProtoType','协议类型','combobox',null,null,11,73),#id=74 UDP、TCP、ICMP（1-3）
('SrcAddr','源IP地址','ip',null,null,11,73),
('DstAddr','目的IP地址','ip',null,null,11,73),
('DstPort','目的端口号','numberbox',0,65535,11,73),
('SrcPort','源端口号','textbox',0,65535,11,73),
('AccessType','用户连接状态','combobox',null,null,11,73),#id=79 0:accept,1:denied

('Blacklist','黑名单','grid',null,null,12,null),#id=80
('ProtoType','协议类型','combobox',null,null,12,80),#id=81 UDP、TCP、ICMP（1-3）
('Port','端口号','numberbox',0,65535,12,80),
('Addr','IP地址','ip',null,null,12,80),

('MaxBadSipMsgRate','最大畸形消息速率','numberbox',0,20000,13,null),#id=84
('MaxInviteReqRate','最大呼叫请求速率','numberbox',0,20000,13,null),#id=85
('MaxRegReqRate','最大注册请求速率','numberbox',0,20000,13,null),#id=86

('Business','业务限制策略','grid',null,null,14,null),# id=87
('RegLimitIp','注册限制IP','ip',null,null,14,87),
('RegLimitMdn','注册限制MDN','numberbox',null,null,14,null),
('CallLimitIn','主叫SIP URI限制','textbox',null,null,14,87),
('CallLimitOut','被叫SIP URI限制','textbox',null,null,14,87),
('DstIPLimit','目的IP限制','ip',null,null,14,87),
('SrcIPLimit','源IP限制','ip',null,null,14,null),
#------------ 访问控制策略 结束------------
#------------ 状态查询 开始------------
('Media','媒体链路信息','grid',null,null,15,null),# id=94
('LinkId','链路ID','numberbox',1,null,15,94),
('SrcAddr','源地址','ip',null,null,15,94),
('DstAddr','目的地址','ip',null,null,15,94),
('SrcPort','源端口','numberbox',0,65535,15,94),
('DstPort','目的端口','numberbox',0,65535,15,94),
('QosVal','Qos值','numberbox',0,31,15,94),

('Status','用户注册状态','grid',null,null,16,null),#id=101
('UserId','用户ID','numberbox',1,null,null,null),#删除
('GroupNo','组号','numberbox',1,null,16,101),
('UserNo','用户MDN','textbox',null,null,16,101),
('UeIp','用户IP','ip',null,null,16,101),
('UePort','用户端口','numberbox',0,65535,16,101),
('RegisterStatus','注册状态','combobox',null,null,16,101),#id=107 0off,1on
('RegisterTime','首次注册时间','textbox',null,null,16,101),
('LastRegisterTime','上次注册时间','textbox',null,null,16,101),
#------------ 状态查询 结束------------
#------------ 系统状态 开始------------
('BoardStatusGrid','板卡状态查询','grid',null,null,17,null),#id=110
('BoardID','板卡ID','numberbox',1,16,17,110),
('BoardType','板卡类型','combobox',null,null,17,110),#id=112 0:信令板；1：媒体板；2：交换板；3：接口板
('BoardStatus','板卡状态','combobox',null,null,17,110),#id=113 0:正常；1：链路故障；2：断电
('Reason','异常原因','textbox',null,null,17,110),

('Alarm','运行告警','grid',null,null,18,null),#id=115
('WarnningID','告警ID','numberbox',null,null,18,115),
('Level','告警级别','numberbox',null,null,18,115),
('Description','告警描述','textbox',null,null,18,115),
('Type','告警类型','numberbox',null,null,18,115),

('UserRegisteredNum','已注册用户数','numberbox',null,null,19,null),#id=120
('AliveCallNum','呼叫保持会话数','numberbox',null,null,19,null),
('ProcedingCallNum','呼叫进行会话数','numberbox',null,null,19,null),
('FailedCallNum','呼叫失败会话数','numberbox',null,null,19,null),
('UsedCPU','CPU使用率','numberbox',null,null,19,null),
('MediaBWUsed','媒体带宽使用率','numberbox',null,null,19,null),
#新增
('GroupEndIp','组结束IP','ip',null,null,8,44),
('Level3_UserPriority','3级保障用户级别','numberbox',null,null,10,null),
('Level3_CPUUsed','3级保障CPU使用率','numberbox',null,null,10,null),
('Level3_BWUsed','3级保障媒体使用率','numberbox',null,null,10,null),
('BlkInviteReqRate','最大呼叫请求速率(黑名单)','numberbox',0,20000,13,null),
('BlkRegReqRate','最大注册请求速率(黑名单)','numberbox',0,20000,13,null),
('LocalTlsIP','本地地址','ip',null,null,2,null),
('LocalTlsPort','本地端口','numberbox',null,null,2,null),
('RemoteTlsIP','远端地址','ip',null,null,2,null),
('RemoteTlsPort','本地地址','numberbox',null,null,2,null);
#------------ 系统状态 结束------------

insert into options(text,val,itemId) values
('单板运行','0',12),
('主板运行','1',12),
('备板运行','2',12),
('关','0',15),
('开','1',15),
('关','0',31),
('开','1',31),
('关','0',32),
('开','1',32),
('关','0',33),
('开','1',33),
('关','0',34),
('开','1',34),
('关','0',35),
('开','1',35),
('UDP','1',74),
('TCP','2',74),
('ICMP','3',74),
('accept','0',79),
('denied','1',79),
('UDP','1',81),
('TCP','2',81),
('ICMP','3',81),
('off','0',107),
('on','1',107),
('信令板','0',112),
('媒体板','1',112),
('交换板','2',112),
('接口板','3',112),
('正常','0',113),
('链路故障','1',113),
('断电','2',113);