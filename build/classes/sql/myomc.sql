create database if not exists myomc character set utf8;
use myomc;
create table if not exists user(
	id int primary key auto_increment,
	name varchar(20) unique not null,
	password varchar(50)
)engine=InnoDB;

create table if not exists role(
	id int primary key auto_increment,
	name varchar(20) unique not null,
	description varchar(255)
)engine=InnoDB;

create table if not exists privilege(
	id int primary key auto_increment,
	name varchar(30) unique not null,
	description varchar(255),
	url varchar(255) unique,
	icon varchar(255),
	leaf tinyint,
	parentId int,
	foreign key(parentId) REFERENCES privilege(id)
)engine=InnoDB;

create table if not exists user_role(
	userId int not null,
	roleId int not null,
	PRIMARY KEY (`userId`,`roleId`),
	foreign key(userId) REFERENCES user(id),
	foreign key(roleId) REFERENCES role(id)
)engine=InnoDB;

create table if not exists role_privilege(
	roleId int not null,
	privilegeId int not null,
	PRIMARY KEY (`roleId`,`privilegeId`),
	foreign key(roleId) REFERENCES role(id),
	foreign key(privilegeId) REFERENCES privilege(id)
)engine=InnoDB;

create table if not exists card(
	id int primary key auto_increment,
	name varchar(255) unique,
	cardNum int not null default 0,
	slotId int not null default 0,
	ip varchar(15)
)engine=InnoDB;

create table if not exists module(
	id int primary key,
	name varchar(255) unique,
	belong int default 0,
	description varchar(255) default '',
	version varchar(255)  default '',
	exe varchar(255) not null,
	log varchar(255)
)engine=InnoDB;

create table if not exists config(
	id int primary key auto_increment,
	name varchar(255),
	path varchar(255) default '',
	description varchar(255),
	sole tinyint default 0,
	moduleId int ,
	foreign key(moduleId) references module(id)
)engine=InnoDB;

create table if not exists common(
	id int primary key auto_increment,
	name varchar(255) unique not null
)engine=InnoDB;

create table if not exists relevance(
	id int primary key auto_increment,
	name varchar(255),
	val text, 
	description varchar(255) default '',
	formtype varchar(50) default 'textbox',
	min int,
	max int,
	minLen int not null,
	maxLen int not null,
	multiline tinyint not null default 0,
	required tinyint not null default 1,
	orderNum int,
	commonId int,
	foreign key(commonId) references common(id)
)engine=InnoDB;

create table if not exists item(
	id int primary key auto_increment,
	name varchar(255),
	val text, 
	description varchar(255) default '',
	formtype varchar(50) default 'textbox',
	min int,
	max int,
	minLen int not null default 0,
	maxLen int not null default 9999,
	multiline tinyint not null default 0,
	required tinyint not null default 1,
	orderNum int,
	parentId int,
	configId int,
	relevanceId int,
	foreign key(parentId) references item(id),
	foreign key(configId) references config(id),
	foreign key(relevanceId) references relevance(id)
)engine=InnoDB;

create table if not exists options(
	id int primary key auto_increment,
	text varchar(50) not null,
	val varchar(255) not null,
	itemId int not null,
	foreign key(itemId) references item(id)
)engine=InnoDB;

create table if not exists entity(
	id int primary key auto_increment,
	name varchar(255),
	instId int not null,
	status int default 0,
	type int default 0,
	description varchar(255),
	moduleId int,
	cardId int,
	foreign key(moduleId) references module(id),
	foreign key(cardId) references card(id)
)engine=InnoDB;

create table if not exists so(
	id int primary key auto_increment,
	name varchar(255) unique not null,
	updateDate date not null
)engine=InnoDB;

create table if not exists alarm(
	id int primary key auto_increment,
	level int,
	description varchar(255),
	createDate timestamp
)engine=InnoDB;

create table if not exists log(
	id int primary key auto_increment,
	description varchar(255),
	user varchar(255),
	success tinyint,
	reason varchar(255),
	createDate timestamp
)engine=InnoDB;

create table if not exists hssDeviceType(
	id int primary key not null,
	name varchar(10) not null,
	enable tinyint not null default 1
)engine=InnoDB;

create table if not exists hssVoiceType(
	id int primary key not null,
	name varchar(10) not null,
	enable tinyint not null default 1
)engine=InnoDB;

create table if not exists hssBusiness(
	id tinyint primary key auto_increment,
	name varchar(20) not null,
	i18n varchar(25) not null,
	basic tinyint not null default 1,
	enable tinyint not null default 1
)engine=InnoDB;

create table if not exists hssViewItem(
    id int primary key not null,
    name varchar(20) not null,
    enable tinyint not null default 1
)engine=InnoDB;

create table if not exists ipDns(
	cardNum int ,
	eth varchar(20),
	ip varchar(20) not null,
	dnsStr text,
	primary key(cardNum,eth)
)engine=InnoDB;

create table if not exists configUserPriority(
	id int primary key auto_increment, 
	level int not null , 
	priority int not null , 
	enable int not null,
	userType int not null
)engine=InnoDB;

create table if not exists `terminalType` (
  `terminalId` int(11) not null,
  `terminalName` varchar(32) not null	
) engine=InnoDB;

create table if not exists `bts`(
  `carrierId` int(11) NOT NULL primary key,
  `carrierType` varchar(255) DEFAULT NULL,
  `cellId` varchar(255) DEFAULT NULL,
  `carrierFreq` varchar(255) DEFAULT NULL,
  `pn` varchar(255) DEFAULT NULL,
  `btsId` varchar(255) DEFAULT NULL,
  `zoneId` varchar(255) DEFAULT NULL,
  `bandClass` varchar(255) DEFAULT NULL,
  `txGain` varchar(255) DEFAULT NULL,
  `pilotChGain` varchar(255) DEFAULT NULL,
  `syncChGain` varchar(255) DEFAULT NULL,
  `pagingChGain` varchar(255) DEFAULT NULL,
  `numOfPch` varchar(255) DEFAULT NULL,
  `numOfQpch` varchar(255) DEFAULT NULL,
  `qpchRate` varchar(255) DEFAULT NULL,
  `qpchCci` varchar(255) DEFAULT NULL,
  `qpchPwrPage` varchar(255) DEFAULT NULL,
  `qpchPwrCfg` varchar(255) DEFAULT NULL,
  `numAchPerPch` varchar(255) DEFAULT NULL,
  `cdmaChannelList` varchar(255) DEFAULT NULL
) engine=InnoDB;
