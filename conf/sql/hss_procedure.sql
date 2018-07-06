drop procedure if exists addHlr$
drop procedure if exists updateHlr$
drop procedure if exists addHlrps$
drop procedure if exists updateHlrps$
drop procedure if exists addTft$
drop procedure if exists addEpc$
drop procedure if exists addAuc$
drop procedure if exists addMSService$
drop procedure if exists HlrPsAdd$
drop procedure if exists HlrPsUpdate$
drop procedure if exists addTerminalInfo$

create procedure addHlr(count INT,imsi bigint,mdn bigint,esn bigint,msprofile int,deviceType int,msvocodec int,msprofile_extra int)
begin
	declare i int default 0;
	declare err int default 0;
	declare continue handler FOR 1062 set err=-1;
	while i < count do
		insert into HLR (IMSI,MDN,ESN,MSprofile,DeviceType,MSvocodec,Msprofile_extra)values(CONCAT(imsi),CONCAT(mdn),right(CONCAT('00000000',CONV(esn,10,16)),8),msprofile,deviceType,msvocodec,msprofile_extra);
		set imsi = imsi + 1;
		set mdn = mdn + 1;
		set esn = esn + 1;
		set i=i+1;
	end while;
end$

create procedure updateHlr(count INT,imsi bigint,mdn bigint,esn bigint,msprofile int,deviceType int,msvocodec int,msprofile_extra int)
begin
	declare i int default 0;
	while i < count do
		set @sql='update HLR set MDN=CONCAT(?),ESN=CONCAT(?),MSprofile=?,DeviceType=?,MSvocodec=?,Msprofile_extra=? where IMSI=CONCAT(?);';
		set @imsi=imsi;set @mdn=mdn;set @esn=right(CONCAT('00000000',CONV(esn,10,16)),8);set @msprofile=msprofile;set @deviceType=deviceType;set @msvocodec=msvocodec;set @msprofile_extra=msprofile_extra;
		prepare stmt from @sql;
		execute stmt using @mdn,@esn,@msprofile,@deviceType,@msvocodec,@msprofile_extra,@imsi;
--		update HLR set MDN=CONCAT(mdn),
--			ESN=CONCAT(esn),
--			MSprofile=msprofile,
--			DeviceType=deviceType,
--			MSvocodec=msvocodec 
--			where IMSI=CONCAT(imsi);
		set imsi = imsi + 1;
		set mdn = mdn + 1;
		set esn = esn + 1;
		set i=i+1;
	end while;
end$

create procedure addHlrps(count INT,imsi bigint,mdn bigint,esn bigint,msprofile int,deviceType int,msvocodec int,msprofile_extra int)
begin
	declare i int default 0;
	declare err int default 0;
	declare continue handler FOR 1062 set err=-1;
	while i < count do
		insert into HLRps (IMSI,MDN,ESN,MSprofile,DeviceType,MSvocodec,Msprofile_extra)values(CONCAT(imsi),CONCAT(mdn),right(CONCAT('00000000',CONV(esn,10,16)),8),msprofile,deviceType,msvocodec,msprofile_extra);
		set imsi = imsi + 1;
		set mdn = mdn + 1;
		set esn = esn + 1;
		set i=i+1;
	end while;
end$

create procedure updateHlrps(count INT,imsi bigint,mdn bigint,esn bigint,msprofile int,deviceType int,msvocodec int,msprofile_extra int)
begin
	declare i int default 0;
	while i < count do
		set @sql='update HLRps set MDN=CONCAT(?),ESN=CONCAT(?),MSprofile=?,DeviceType=?,MSvocodec=?,Msprofile_extra=? where IMSI=CONCAT(?);';
		set @imsi=imsi;set @mdn=mdn;set @esn=right(CONCAT('00000000',esn),8);set @msprofile=msprofile;set @deviceType=deviceType;set @msvocodec=msvocodec;set @msprofile_extra=msprofile_extra;
		prepare stmt from @sql;
		execute stmt using @mdn,@esn,@msprofile,@deviceType,@msvocodec,@msprofile_extra,@imsi;
--		update HLR set MDN=CONCAT(mdn),
--			ESN=CONCAT(esn),
--			MSprofile=msprofile,
--			DeviceType=deviceType,
--			MSvocodec=msvocodec 
--			where IMSI=CONCAT(imsi);
		set imsi = imsi + 1;
		set mdn = mdn + 1;
		set esn = esn + 1;
		set i=i+1;
	end while;
end$

create procedure HlrPsAdd(count INT,imsi bigint,mdn bigint,esn bigint,msprofile int,deviceType int,msvocodec int,msprofile_extra int)
begin
	declare i int default 0;
	declare err int default 0;
	declare continue handler FOR 1062 set err=-1;
	while i < count do
		insert into HLRps (IMSI,MDN,ESN,MSprofile,DeviceType,MSvocodec,msprofile_extra)values(CONCAT(imsi),CONCAT(mdn),right(CONCAT('00000000',CONV(esn,10,16)),8),msprofile,deviceType,msvocodec,msprofile_extra);
		set imsi = imsi + 1;
		set mdn = mdn + 1;
		set esn = esn + 1;
		set i=i+1;
	end while;
end$

create procedure HlrPsUpdate(count INT,imsi bigint,mdn bigint,esn bigint,msprofile int,deviceType int,msvocodec int,msprofile_extra int)
begin
	declare i int default 0;
	while i < count do
		set @sql='update HLRps set MDN=CONCAT(?),ESN=CONCAT(?),MSprofile=?,DeviceType=?,MSvocodec=?,Msprofile_extra=? where IMSI=CONCAT(?);';
		set @imsi=imsi;set @mdn=mdn;set @esn=right(CONCAT('00000000',esn),8);set @msprofile=msprofile;set @deviceType=deviceType;set @msvocodec=msvocodec;set @msprofile_extra=msprofile_extra;
		prepare stmt from @sql;
		execute stmt using @mdn,@esn,@msprofile,@deviceType,@msvocodec,@msprofile_extra,@imsi;
		set imsi = imsi + 1;
		set mdn = mdn + 1;
		set esn = esn + 1;
		set i=i+1;
	end while;
end$

create procedure addAuc(count INT,imsi bigint)
begin
	declare i int default 0;
	declare err int default 0;
	declare continue handler for 1062 set err=-1;
	while i < count do
		insert into AUC (IMSI)values(CONCAT(imsi));
		set imsi = imsi + 1;
		set i=i+1;
	end while;
end$

create procedure addTerminalInfo(count int,imsi bigint,terminalId bigint,terminalType int,powerLevel int,suportBuss int,gwId int,department int,userType int,createTime timestamp,servicePriority int)
begin
	declare i int default 0;
	declare err int default 0;
	declare continue handler for 1062 set err=-1;
	while i < count do
		insert into TerminalInfo(IMSI,terminalId,terminalType,powerLevel,suportBuss,gwId,department,userType,createTime,servicePriority) 
		values(CONCAT(imsi),CONCAT(terminalId),terminalType,powerLevel,suportBuss,gwId,CONCAT(department),userType,createTime,servicePriority);
		set imsi = imsi + 1;
		set i=i+1;
	end while;
end$

create procedure addMSService(count int,imsi bigint,WireTapAddr varchar(32))
begin
	declare i int default 0;
	declare err int default 0;
	declare continue handler for 1062 set err=-1;
	while i < count do
		insert into MSService (IMSI,WireTapAddr)values(CONCAT(imsi),WireTapAddr);
		set imsi = imsi + 1;
		set i=i+1;
	end while;
end$

create procedure addTft(count int,imsi bigint)
begin
	declare i int default 0;
	declare err int default 0;
	declare continue handler for 1062 set err=-1;
	while i < count do
		insert into EPCSubscriptionTFT (IMSI)values(CONCAT(imsi));
		set imsi = imsi + 1;
		set i=i+1;
	end while;
end$

create procedure addEpc(count int,imsi bigint)
begin
	declare i int default 0;
	declare err int default 0;
	declare continue handler for 1062 set err=-1;
	while i < count do
		insert into EPCSubscriptionData (IMSI)values(CONCAT(imsi));
		set imsi = imsi + 1;
		set i=i+1;
	end while;
end$
