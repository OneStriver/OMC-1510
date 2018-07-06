create database if not  exists  `CSCF_DB` character set utf8;
use CSCF_DB;
create table  if not exists `scscf_ue` (
  `ueUri` varchar(128) NOT NULL default '',
  `ueName` varchar(128) NOT NULL default '',
  `ueScscfDomain` varchar(256) NOT NULL default '',
  `ueScscfAddr` varchar(128) NOT NULL default '0.0.0.0:0',
  `uePcscfDomain` varchar(256) NOT NULL default '',
  `uePcscfAddr` varchar(128) NOT NULL default '0.0.0.0:0',
  `ueAddr` varchar(128) NOT NULL default '0.0.0.0:0',
  `ueStatus`    int  NOT NULL default '0',
  `ueUpdateType`    int  NOT NULL default '0',
  `ueUpdateTstamp` timestamp NOT NULL default now(),
  `ueHomeScscfDomain` varchar(256) NOT NULL default '',
  `ueHomeScscfAddr` varchar(128) NOT NULL default '0.0.0.0:0',
  `uePassword` varchar(128) NOT NULL default 'password',
  primary key(ueuri),
  key(uename),
  index using hash(ueuri),
  index using hash(uename)
) engine = innodb default charset = utf8;