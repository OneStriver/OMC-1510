drop procedure if exists addHlr$
drop procedure if exists updateHlr$
drop procedure if exists addAuc$
drop procedure if exists addMSService$
drop procedure if exists addTerminalInfo$
drop procedure if exists addTft$
drop procedure if exists addEpc$

create procedure addHlr(count int,imsi bigint,mdn bigint,esn bigint,msprofile int,deviceType int,msvocodec int)
begin
	declare i int default 0;
	declare err int default 0;
	declare d_index int default 0;
	declare continue handler FOR 1062 set err=-1;
	while i < count do
		set d_index = imsi%4;
		if d_index = 0 then set d_index = 4; end if;
		set @sql=concat(concat('insert into hss',d_index),'.HLR (IMSI,MDN,ESN,MSprofile,DeviceType,MSvocodec) values(CONCAT(?),CONCAT(?),CONCAT(?),?,?,?);');
		set @imsi=imsi;
		set @mdn=mdn;
		set @esn=right(CONCAT('00000000',CONV(esn,10,16)),8);
		set @msprofile=msprofile;
		set @deviceType=deviceType;
		set @msvocodec=msvocodec;
		prepare stmt from @sql;
		execute stmt using @imsi,@mdn,@esn,@msprofile,@deviceType,@msvocodec;
		set imsi = imsi + 1;
		set mdn = mdn + 1;
		set esn = esn + 1;
		set i=i+1;
	end while;
end$

create procedure updateHlr(count INT,imsi bigint,mdn bigint,esn bigint,msprofile int,deviceType int,msvocodec int)
begin
	declare i int default 0;
	declare err int default 0;
	declare d_index int default 0;
	declare continue handler FOR 1062 set err=-1;
	while i < count do
		set d_index = imsi%4;
		if d_index = 0 then set d_index = 4; end if;
		set @sql=concat(concat('update hss',d_index),'.HLR set MDN=CONCAT(?),ESN=CONCAT(?),MSprofile=?,DeviceType=?,MSvocodec=? where IMSI=CONCAT(?);');
		set @imsi=imsi;
		set @mdn=mdn;
		set @esn=right(CONCAT('00000000',esn),8);
		set @msprofile=msprofile;
		set @deviceType=deviceType;
		set @msvocodec=msvocodec;
		prepare stmt from @sql;
		execute stmt using @mdn,@esn,@msprofile,@deviceType,@msvocodec,@imsi;
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
	declare d_index int default 0;
	declare continue handler for 1062 set err=-1;
	while i < count do
		set d_index = imsi%4;
		if d_index = 0 then set d_index = 4; end if;
		set @sql=concat(concat('insert into hss',d_index),'.AUC (IMSI) values(CONCAT(?));');
		set @imsi = imsi;
		prepare stmt from @sql;
		execute stmt using @imsi;
		set imsi = imsi + 1;
		set i=i+1;
	end while;
end$

create procedure addMSService(count int,imsi bigint)
begin
	declare i int default 0;
	declare err int default 0;
	declare d_index int default 0;
	declare continue handler for 1062 set err=-1;
	while i < count do
		set d_index = imsi%4;
		if d_index = 0 then set d_index = 4; end if;
		set @sql=concat(concat('insert into hss',d_index),'.MSService (IMSI) values(CONCAT(?));');
		set @imsi = imsi;
		prepare stmt from @sql;
		execute stmt using @imsi;
		set imsi = imsi + 1;
		set i=i+1;
	end while;
end$

create procedure addTerminalInfo(count int,imsi bigint,terminalType int,powerLevel int,suportBuss int,gwId int,userType int,createTime timestamp,servicePriority int)
begin
	declare i int default 0;
	declare err int default 0;
	declare d_index int default 0;
	declare continue handler for 1062 set err=-1;
 while i < count do
 	 set d_index = imsi%4;
 	 if d_index = 0 then set d_index = 4; end if;
 	 set @sql=concat(concat('insert into hss',d_index),'.TerminalInfo (IMSI,terminalType,powerLevel,suportBuss,gwId,userType,createTime,servicePriority) values(CONCAT(?),?,?,?,?,?,?,?);');
 	 set @imsi = imsi;
 	 prepare stmt from @sql;
 	 execute stmt using @imsi;
 	 set imsi = imsi + 1;
 	 set i=i+1;
 end while;
end$

create procedure addTft(count int,imsi bigint)
begin
	declare i int default 0;
	declare err int default 0;
	declare d_index int default 0;
	declare continue handler for 1062 set err=-1;
	while i < count do
	set d_index = imsi%4;
		if d_index = 0 then set d_index = 4; end if;
		set @sql=concat(concat('insert into hss',d_index),'.EPCSubscriptionTFT (IMSI) values(CONCAT(?));');
		set @imsi = imsi;
		prepare stmt from @sql;
		execute stmt using @imsi;
		set imsi = imsi + 1;
		set i=i+1;
	end while;
end$

create procedure addEpc(count int,imsi bigint)
begin
	declare i int default 0;
	declare err int default 0;
	declare d_index int default 0;
	declare continue handler for 1062 set err=-1;
	while i < count do
	set d_index = imsi%4;
		if d_index = 0 then set d_index = 4; end if;
		set @sql=concat(concat('insert into hss',d_index),'.EPCSubscriptionData (IMSI) values(CONCAT(?));');
		set @imsi = imsi;
		prepare stmt from @sql;
		execute stmt using @imsi;
		set imsi = imsi + 1;
		set i=i+1;
	end while;
end$
