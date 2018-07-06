-- drop database if exists `hss`;
create database if not  exists  `hss`;
use hss;

create table if not exists HLR as 
(select * from hss1.HLR) union all
(select * from hss2.HLR) union all
(select * from hss3.HLR) union all
(select * from hss4.HLR);
alter table HLR add primary key(IMSI);
alter table HLR add unique(mdn);

create table if not exists HLRps as 
(select * from hss1.HLRps) union all
(select * from hss2.HLRps) union all
(select * from hss3.HLRps) union all
(select * from hss4.HLRps);
alter table HLRps add primary key(IMSI);

create table if not exists AUC as 
(select * from hss1.AUC) union all
(select * from hss2.AUC) union all
(select * from hss3.AUC) union all
(select * from hss4.AUC);
alter table AUC add primary key(IMSI);

create table if not exists GroupInfo as 
(select * from hss1.GroupInfo) union all
(select * from hss2.GroupInfo) union all
(select * from hss3.GroupInfo) union all
(select * from hss4.GroupInfo);
alter table GroupInfo add primary key(IMSI);

create table if not exists EPCSubscriptionData as 
(select * from hss1.EPCSubscriptionData) union all
(select * from hss2.EPCSubscriptionData) union all
(select * from hss3.EPCSubscriptionData) union all
(select * from hss4.EPCSubscriptionData);
alter table EPCSubscriptionData add primary key(IMSI);

create table if not exists MSService as 
(select * from hss1.MSService) union all
(select * from hss2.MSService) union all
(select * from hss3.MSService) union all
(select * from hss4.MSService);
alter table MSService add primary key(IMSI);

create table if not exists EPCSubscriptionTFT as 
(select * from hss1.EPCSubscriptionTFT) union all
(select * from hss2.EPCSubscriptionTFT) union all
(select * from hss3.EPCSubscriptionTFT) union all
(select * from hss4.EPCSubscriptionTFT);
alter table EPCSubscriptionTFT add primary key(IMSI);

create table if not exists csHLRGroupInfo as 
(select * from hss1.csHLRGroupInfo) union all
(select * from hss2.csHLRGroupInfo) union all
(select * from hss3.csHLRGroupInfo) union all
(select * from hss4.csHLRGroupInfo);
alter table csHLRGroupInfo add primary key(groupNum);

create table if not exists csHLRGroupMember as 
(select * from hss1.csHLRGroupMember) union all
(select * from hss2.csHLRGroupMember) union all
(select * from hss3.csHLRGroupMember) union all
(select * from hss4.csHLRGroupMember);
alter table csHLRGroupMember add primary key(groupNum,groupMember);

create table if not exists TerminalInfo as 
(select * from hss1.TerminalInfo) union all
(select * from hss2.TerminalInfo) union all
(select * from hss3.TerminalInfo) union all
(select * from hss4.TerminalInfo);
alter table TerminalInfo add primary key(imsi);
