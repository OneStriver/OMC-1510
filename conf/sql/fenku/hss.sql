CREATE DATABASE IF NOT EXISTS `hss` char set utf8;
use hss;
create table if not exists `HLR` (
  `IMSI` varchar(32) NOT NULL default '',
  `MDN` varchar(32) NOT NULL default '' unique,
  `ESN` varchar(32) NOT NULL default '' unique,
  `TMSI` varchar(32) NOT NULL default '',
  `RncLoc` varchar(32) NOT NULL default '0000-0000',
  `GeoLoc` varchar(32) NOT NULL default '0000-0000',
  `MSprofile`  int NOT NULL default '0',
  `MSprofile_extra`  int NOT NULL default '0',
  `MSvocodec`  int NOT NULL default '0',
  `Acount_num` varchar(32) NOT NULL default '',
  `currloc` varchar(128) NOT NULL default '@',
  `tstamp` timestamp NOT NULL default now(),
  `numOfSms` int NOT NULL  default '0',
  `numOfVM`   int  NOT NULL default '0',
  `status`    int  NOT NULL default '0',
  `VLRAddr` varchar(128) NOT NULL default '@',
  `DeviceType`    int  NOT NULL default '0',
  PRIMARY KEY(IMSI)
) ENGINE = InnoDB DEFAULT CHARSET = utf8;
alter table HLR default character set utf8;

create table if not exists `GroupInfo` (
  `IMSI` varchar(32) NOT NULL default '',
  `GROUP1` varchar(32) NOT NULL default '',
  `GROUP2` varchar(32) NOT NULL default '',
  `GROUP3` varchar(32) NOT NULL default '',
  `GROUP4` varchar(32) NOT NULL default '',
  `GROUP5` varchar(32) NOT NULL default '',
  `GROUP6` varchar(32) NOT NULL default '',
  `GROUP7` varchar(32) NOT NULL default '',
  `GROUP8` varchar(32) NOT NULL default '',
  `GROUP9` varchar(32) NOT NULL default '',
  `GROUP10` varchar(32) NOT NULL default '',
  `GROUP11` varchar(32) NOT NULL default '',
  `GROUP12` varchar(32) NOT NULL default '',
  `GROUP13` varchar(32) NOT NULL default '',
  `GROUP14` varchar(32) NOT NULL default '',
  `GROUP15` varchar(32) NOT NULL default '',
  `GROUP16` varchar(32) NOT NULL default '',
  PRIMARY KEY(IMSI)
) ENGINE = InnoDB DEFAULT CHARSET = utf8;

create table if not exists `HLRps` (
  `IMSI` varchar(32) NOT NULL default '',
  `MDN` varchar(32) NOT NULL default '',
  `ESN` varchar(32) NOT NULL default '',
  `TMSI` varchar(32) NOT NULL default '',
  `RncLoc` varchar(32) NOT NULL default '0000-0000',
  `GeoLoc` varchar(32) NOT NULL default '0000-0000',
  `MSprofile`  int NOT NULL default '0',
  `MSprofile_extra`  int NOT NULL default '0',
  `MSvocodec`  int NOT NULL default '0',
  `Acount_num` varchar(32) NOT NULL default '',
  `currloc` varchar(128) NOT NULL default '@',
  `tstamp` timestamp NOT NULL default now(),
  `numOfSms` int NOT NULL  default '0',
  `numOfVM`   int  NOT NULL default '0',
  `status`    int  NOT NULL default '0',
  `VLRAddr` varchar(128) NOT NULL default '@',
  `DeviceType`    int  NOT NULL default '0',
  PRIMARY KEY(IMSI),
  KEY(MDN)
) ENGINE = InnoDB DEFAULT CHARSET = utf8;

create table  if not exists `EPCSubscriptionData` (
  `IMSI` varchar(32) NOT NULL DEFAULT '',
  `APN` varchar(64) NOT NULL DEFAULT 'sunkaisens',
  `ErabId` int(11) NOT NULL DEFAULT '5',
  `QCI` int(11) NOT NULL DEFAULT '9',
  `AggregateMaxULBitrate` int(11) NOT NULL DEFAULT 2500000,
  `AggregateMaxDLBitrate` int(11) NOT NULL DEFAULT 3500000,
  `GuaranteedULBitRate` int(11) NOT NULL DEFAULT 0,
  `GuaranteedDLBitRate` int(11) NOT NULL DEFAULT 0,
  `MaxULBitRate` int(11) NOT NULL DEFAULT 0,
  `MaxDLBitRate` int(11) NOT NULL DEFAULT 0,
  `ARPriority` int(11) NOT NULL DEFAULT 3,
  PRIMARY KEY (`IMSI`,`APN`,`ErabId`)
) ENGINE=InnoDB DEFAULT CHARSET = utf8;

create table if not exists `MSService` (
  `IMSI` varchar(32) NOT NULL default '',
  `Status` varchar(32) NOT NULL default 'inactive',
  `directFwdNum` varchar(32) NOT NULL default '',
  `fwdOnBusyNum` varchar(32) NOT NULL default '',
  `fwdNoAnswerNum` varchar(32) NOT NULL default '',
  `voicemailNum` varchar(32) NOT NULL default '',
  `fwdNANum` varchar(32) NOT NULL default '',
  `WireTapAddr` varchar(32) NOT NULL default '',
  PRIMARY KEY(IMSI)
) ENGINE = InnoDB DEFAULT CHARSET = utf8;

create table if not exists `AUC` (
  `IMSI` varchar(32) NOT NULL default 'NULL',
  `K`     varchar(48) NOT NULL default 'FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF',
  `OP`    varchar(48) NOT NULL default 'FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF',
  `OPc`    varchar(48) NOT NULL default 'FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF',
  `AMF`    varchar(6) NOT NULL default 'F2 4C',
  `SQN`    varchar(18)  NOT NULL default '00 00 00 00 00 00',
  `RAND`   varchar(48) NOT NULL default '00 00 00 00 00 00',
  `tstamp` timestamp   NOT NULL default now(),
  `START`  TIME NOT NULL default '00:00:00',
  `STOP`   TIME NOT NULL default '23:59:59',
  PRIMARY KEY(IMSI)
) ENGINE = InnoDB DEFAULT CHARSET = utf8;

create table if not exists `EPCSubscriptionTFT` (
  `IMSI` varchar(32) NOT NULL default '',
  `SrcIP` varchar(64) NOT NULL default '0.0.0.0',           #IP包源IP
  `DstIP` varchar(64) NOT NULL default '0.0.0.0',           #IP包目的IP
  `SrcPortStart` int NOT NULL default '0',                  #IP包源端口开始值
  `SrcPortEnd` int NOT NULL default '0',                    #IP包源端口结束值
  `DstPortStart` int NOT NULL default '0',                	#IP包目的端口开始值
  `DstPortEnd` int NOT NULL default '0',             		#IP包目的端口结束值
  `DiffServStart` int NOT NULL default '0',               	#DSCP开始值
  `DiffServEnd` int NOT NULL default '0',                  	#DSCP结束值
  `PktLenMin` int NOT NULL default '0',                    	#IP包长度最小值
  `PktLenMax`  int NOT NULL default '0',                   	#IP包长度最大值
  PRIMARY KEY(IMSI)
) ENGINE = InnoDB DEFAULT CHARSET = utf8;

create table if not exists TerminalInfo(
	IMSI varchar(32) NOT NULL PRIMARY KEY,
	terminalId varchar(30),
	terminalType int,
	powerLevel int,
	suportBuss int,
	gwId int,
	department varchar(50),
	userType int,
	username varchar(50),
	userInfo varchar(255),
	servicePriority int,
	createTime timestamp default current_timestamp
)ENGINE = InnoDB DEFAULT CHARSET = utf8;

create table if not exists `csHLRGroupMember` (
  `groupNum` varchar(32) not null default '',
  `groupMember` varchar(32) not null default '',
  `priority` int(11) not null default '0',
  `role` int(11) not null default '0',
  `service` int(11) default '0',
  `serviceExtra` int(11) default '0',
  `tstamp` timestamp not null default '0000-00-00 00:00:00',
  `lastUpdateTstamp` timestamp not null default current_timestamp,
  primary key (`groupNum`,`groupMember`)
) engine=innodb default charset=utf8;

create table if not exists `csHLRGroupInfo` (
  `groupNum` varchar(32) not null default '',
  `groupTmsi` varchar(32) not null default '',
  `groupCallType` int(11) not null default '0',
  `groupCallBear` int(11) not null default '0',
  `priority` int(11) not null default '0',
  `servloc` varchar(128) not null default '@',
  `status` int(11) not null default '0',
  `tstamp` timestamp not null default '0000-00-00 00:00:00',
  `lastUpdateTstamp` timestamp not null default current_timestamp,
  primary key (`groupNum`)
) engine=InnoDB default charset=utf8;

create table if not exists `csHLRGroupData` (
  `groupNum` varchar(32) not null default '',
  `ssCode` int(11) not null default '0',
  `ssStatus` int(11) not null default '0',
  `ssOpt` int(11) not null default '0',
  `ssDetail` varchar(32) not null default '',
  `description` varchar(128) not null default '',
  `lastUpdateTstamp` timestamp not null default current_timestamp,
  primary key (`groupNum`,`ssCode`)
) engine=innodb default charset=utf8;
