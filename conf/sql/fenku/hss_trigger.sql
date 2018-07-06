drop trigger if exists syncAddHlr $
drop trigger if exists syncAddHlrps $
drop trigger if exists syncAddEPCSubscriptionData $
drop trigger if exists syncAddAuc $
drop trigger if exists syncAddMSService $
drop trigger if exists syncAddEPCSubscriptionTFT $
drop trigger if exists syncAddGroupInfo $
drop trigger if exists syncAddTerminalInfo $
drop trigger if exists syncAddCsHlrGroupInfo $
drop trigger if exists syncAddCsHlrGroupMember $

drop trigger if exists syncUpdateHlr $
drop trigger if exists syncUpdateHlrps $
drop trigger if exists syncUpdateEPCSubscriptionData $
drop trigger if exists syncUpdateAuc $
drop trigger if exists syncUpdateMSService $
drop trigger if exists syncUpdateEPCSubscriptionTFT $
drop trigger if exists syncUpdateGroupInfo $
drop trigger if exists syncUpdateTerminalInfo $
drop trigger if exists syncUpdateCsHlrGroupInfo $
drop trigger if exists syncUpdateCsHlrGroupMember $

drop trigger if exists syncDeleteHlr $
drop trigger if exists syncDeleteHlrps $
drop trigger if exists syncDeleteEPCSubscriptionData $
drop trigger if exists syncDeleteAuc $
drop trigger if exists syncDeleteMSService $
drop trigger if exists syncDeleteEPCSubscriptionTFT $
drop trigger if exists syncDeleteGroupInfo $
drop trigger if exists syncDeleteTerminalInfo $
drop trigger if exists syncDeleteCsHlrGroupInfo $
drop trigger if exists syncDeleteCsHlrGroupMember $

create trigger syncAddHlr after insert on HLR
for each row begin
    insert into hss.HLR values(
		new.IMSI,new.MDN,new.ESN,new.TMSI,new.RncLoc,
		new.GeoLoc,new.MSprofile,new.MSprofile_extra,new.MSvocodec,
		new.Acount_num,new.currloc,new.tstamp,new.numOfSms,
		new.numOfVM,new.status,new.VLRAddr,new.DeviceType
    );
end $

create trigger syncAddHlrps after insert on HLRps
for each row begin
    insert into hss.HLRps values(
		new.IMSI,new.MDN,new.ESN,new.TMSI,new.RncLoc,
		new.GeoLoc,new.MSprofile,new.MSprofile_extra,new.MSvocodec,
		new.Acount_num,new.currloc,new.tstamp,new.numOfSms,
		new.numOfVM,new.status,new.VLRAddr,new.DeviceType
    );
end $

create trigger syncAddEPCSubscriptionData after insert on EPCSubscriptionData
for each row begin
    insert into hss.EPCSubscriptionData values(
		new.IMSI,new.APN,new.ErabId,new.QCI,new.AggregateMaxULBitrate,
		new.AggregateMaxDLBitrate,new.GuaranteedULBitRate,new.GuaranteedDLBitRate,
		new.MaxULBitRate,new.MaxDLBitRate,new.ARPriority
    );
end $

create trigger syncAddAuc after insert on AUC
for each row begin
    insert into hss.AUC values(
		new.IMSI,new.K,new.OP,new.OPc,new.AMF,
		new.SQN,new.RAND,new.tstamp,new.START,new.STOP
    );
end $

create trigger syncAddMSService after insert on MSService
for each row begin
    insert into hss.MSService values(
		new.IMSI,new.Status,new.directFwdNum,new.fwdOnBusyNum,new.fwdNoAnswerNum,
		new.voicemailNum,new.fwdNANum,new.WireTapAddr
    );
end $

create trigger syncAddEPCSubscriptionTFT after insert on EPCSubscriptionTFT
for each row begin
    insert into hss.EPCSubscriptionTFT values(
		new.IMSI,new.SrcIP,new.DstIP,new.SrcPortStart,new.SrcPortEnd,new.DstPortStart,
		new.DstPortEnd,new.DiffServStart,new.DiffServEnd,new.PktLenMin,new.PktLenMax
    );
end $

create trigger syncAddGroupInfo after insert on GroupInfo
for each row begin
    insert into hss.GroupInfo values(
		new.IMSI,new.GROUP1,new.GROUP2,new.GROUP3,new.GROUP4,new.GROUP5,
		new.GROUP6,new.GROUP7,new.GROUP8,new.GROUP9,new.GROUP10,
		new.GROUP11,new.GROUP12,new.GROUP13,new.GROUP14,new.GROUP15,new.GROUP16
    );
end $

create trigger syncAddTerminalInfo after insert on TerminalInfo
for each row begin
    insert into hss.TerminalInfo values(
		new.IMSI,new.terminalId,new.terminalType,new.powerLevel,new.suportBuss,new.gwId,
		new.department,new.userType,new.username,new.userInfo,new.createTime,new.servicePriority
    );
end $

create trigger syncAddCsHlrGroupInfo after insert on csHLRGroupInfo
for each row begin
    insert into hss.csHLRGroupInfo values(
		new.groupNum,new.groupTmsi,new.groupCallType,new.groupCallBear,
		new.priority,new.servloc,new.status,new.tstamp,new.lastUpdateTstamp
    );
end $

create trigger syncAddCsHlrGroupMember after insert on csHLRGroupMember
for each row begin
    insert into hss.csHLRGroupMember values(
		new.groupNum,new.groupMember,new.priority,new.role,
		new.service,new.serviceExtra,new.tstamp,new.lastUpdateTstamp
    );
end $


##################### update触发器开始 #############################
create trigger syncUpdateHlr after update on HLR
for each row begin
    update hss.HLR set
		IMSI=new.IMSI,
		MDN=new.MDN,
		ESN=new.ESN,
		TMSI=new.TMSI,
		RncLoc=new.RncLoc,
		GeoLoc=new.GeoLoc,
		MSprofile=new.MSprofile,
		MSprofile_extra=new.MSprofile_extra,
		MSvocodec=new.MSvocodec,
		Acount_num=new.Acount_num,
		currloc=new.currloc,
		tstamp=new.tstamp,
		numOfSms=new.numOfSms,
		numOfVM=new.numOfVM,
		status=new.status,
		VLRAddr=new.VLRAddr,
		DeviceType=new.DeviceType
		where IMSI=new.IMSI;
end $

create trigger syncUpdateHlrps after update on HLRps
for each row begin
    update hss.HLRps set
		IMSI=new.IMSI,
		MDN=new.MDN,
		ESN=new.ESN,
		TMSI=new.TMSI,
		RncLoc=new.RncLoc,
		GeoLoc=new.GeoLoc,
		MSprofile=new.MSprofile,
		MSprofile_extra=new.MSprofile_extra,
		MSvocodec=new.MSvocodec,
		Acount_num=new.Acount_num,
		currloc=new.currloc,
		tstamp=new.tstamp,
		numOfSms=new.numOfSms,
		numOfVM=new.numOfVM,
		status=new.status,
		VLRAddr=new.VLRAddr,
		DeviceType=new.DeviceType
		where IMSI=new.IMSI;
end $

create trigger syncUpdateAuc after update on AUC
for each row begin
    update hss.AUC set
		IMSI=new.IMSI,
		K=new.K,
		OP=new.OP,
		OPc=new.OPc,
		AMF=new.AMF,
		SQN=new.SQN,
		RAND=new.RAND,
		tstamp=new.tstamp,
		START=new.START,
		STOP=new.STOP
		where IMSI=new.IMSI;
end $

create trigger syncUpdateEPCSubscriptionData after update on EPCSubscriptionData
for each row begin
    update hss.EPCSubscriptionData set
		IMSI=new.IMSI,
		APN=new.APN,
		ErabId=new.ErabId,
		QCI=new.QCI,
		AggregateMaxULBitrate=new.AggregateMaxULBitrate,
		AggregateMaxDLBitrate=new.AggregateMaxDLBitrate,
		GuaranteedULBitRate=new.GuaranteedULBitRate,
		GuaranteedDLBitRate=new.GuaranteedDLBitRate,
		MaxULBitRate=new.MaxULBitRate,
		MaxDLBitRate=new.MaxDLBitRate,
		ARPriority=new.ARPriority
		where IMSI=new.IMSI;
end $

create trigger syncUpdateMSService after update on MSService
for each row begin
    update hss.MSService set
		IMSI=new.IMSI,
		Status=new.Status,
		directFwdNum=new.directFwdNum,
		fwdOnBusyNum=new.fwdOnBusyNum,
		fwdNoAnswerNum=new.fwdNoAnswerNum,
		voicemailNum=new.voicemailNum,
		fwdNANum=new.fwdNANum,
		WireTapAddr=new.WireTapAddr
		where IMSI=new.IMSI;
end $

create trigger syncUpdateEPCSubscriptionTFT after update on EPCSubscriptionTFT
for each row begin
    update hss.EPCSubscriptionTFT set
		IMSI=new.IMSI,
		SrcIP=new.SrcIP,
		DstIP=new.DstIP,
		SrcPortStart=new.SrcPortStart,
		SrcPortEnd=new.SrcPortEnd,
		DstPortStart=new.DstPortStart,
		DstPortEnd=new.DstPortEnd,
		DiffServStart=new.DiffServStart,
		DiffServEnd=new.DiffServEnd,
		PktLenMin=new.PktLenMin,
		PktLenMax=new.PktLenMax
		where IMSI=new.IMSI;
end $

create trigger syncUpdateGroupInfo after update on GroupInfo
for each row begin
    update hss.GroupInfo set
		IMSI=new.IMSI,
		GROUP1=new.GROUP1,
		GROUP2=new.GROUP2,
		GROUP3=new.GROUP3,
		GROUP4=new.GROUP4,
		GROUP5=new.GROUP5,
		GROUP6=new.GROUP6,
		GROUP7=new.GROUP7,
		GROUP8=new.GROUP8,
		GROUP9=new.GROUP9,
		GROUP10=new.GROUP10,
		GROUP11=new.GROUP11,
		GROUP12=new.GROUP12,
		GROUP13=new.GROUP13,
		GROUP14=new.GROUP14,
		GROUP15=new.GROUP15,
		GROUP16=new.GROUP16
		where IMSI=new.IMSI;
end $

create trigger syncUpdateTerminalInfo after update on TerminalInfo
for each row begin
    update hss.TerminalInfo set
		terminalId=new.terminalId,
		terminalType=new.terminalType,
		powerLevel=new.powerLevel,
		suportBuss=new.suportBuss,
		gwId=new.gwId,
		department=new.department,
		userType=new.userType,
		username=new.username,
		userInfo=new.userInfo,
		createTime=new.createTime
		where IMSI=new.IMSI;
end $

create trigger syncUpdateCsHlrGroupInfo after update on csHLRGroupInfo
for each row begin
    update hss.csHLRGroupInfo set
		groupTmsi=new.groupTmsi,
		groupCallType=new.groupCallType,
		groupCallBear=new.groupCallBear,
		priority=new.priority,
		servloc=new.servloc,
		status=new.status,
		tstamp=new.tstamp,
		lastUpdateTstamp=new.lastUpdateTstamp
		where groupNum=new.groupNum;
end $

create trigger syncUpdateCsHlrGroupMember after update on csHLRGroupMember
for each row begin
    update hss.csHLRGroupMember set
		priority=new.priority,
		role=new.role,
		service=new.service,
		serviceExtra=new.serviceExtra,
		tstamp=new.tstamp,
		lastUpdateTstamp=new.lastUpdateTstamp
		where groupNum=new.groupNum and groupMember=new.groupMember;
end $


##################### delete触发器开始 #############################
create trigger syncDeleteHlr after delete on HLR
for each row begin
    delete from  hss.HLR where IMSI=old.IMSI;
end $

create trigger syncDeleteHlrps after delete on HLRps
for each row begin
    delete from  hss.HLRps where IMSI=old.IMSI;
end $

create trigger syncDeleteAuc after delete on AUC
for each row begin
    delete from  hss.AUC where IMSI=old.IMSI;
end $

create trigger syncDeleteEPCSubscriptionData after delete on EPCSubscriptionData
for each row begin
    delete from  hss.EPCSubscriptionData where IMSI=old.IMSI;
end $

create trigger syncDeleteMSService after delete on MSService
for each row begin
    delete from  hss.MSService where IMSI=old.IMSI;
end $

create trigger syncDeleteEPCSubscriptionTFT after delete on EPCSubscriptionTFT
for each row begin
    delete from  hss.EPCSubscriptionTFT where IMSI=old.IMSI;
end $

create trigger syncDeleteGroupInfo after delete on GroupInfo
for each row begin
    delete from  hss.GroupInfo where IMSI=old.IMSI;
end $

create trigger syncDeleteTerminalInfo after delete on TerminalInfo
for each row begin
    delete from  hss.TerminalInfo where IMSI=old.IMSI;
end $

create trigger syncDeleteCsHlrGroupInfo after delete on csHLRGroupInfo
for each row begin
    delete from  hss.csHLRGroupInfo where groupNum=old.groupNum;
end $

create trigger syncDeleteCsHlrGroupMember after delete on csHLRGroupMember
for each row begin
    delete from  hss.csHLRGroupMember where groupNum=old.groupNum and groupMember=old.groupMember;
end $