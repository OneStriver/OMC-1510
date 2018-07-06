drop procedure if exists addUe $
drop procedure if exists deleteUe $

create procedure addUe(count int,mdn bigint,domain varchar(128),password varchar(128))
begin
	declare i int default 0;
	declare err int default 0;
	declare continue handler FOR 1062 set err=-1;
	while i < count do
		insert into scscf_ue (ueUri,ueName,uePassword)values(concat(concat(concat('sip:',mdn),'@'),domain),concat(mdn),password);
		set mdn = mdn + 1;
		set i=i+1;
	end while;
end$

create procedure deleteUe(count int,mdn bigint)
begin
	declare i int default 0;
	declare err int default 0;
	declare endMdn bigint default 0;
	declare continue handler FOR 1062 set err=-1;
	set endMdn = mdn+count-1;
	delete from scscf_ue where ueName>=concat(mdn) and ueName<=concat(endMdn);
end$
