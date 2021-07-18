/*
SQLyog Ultimate v12.09 (64 bit)
MySQL - 5.7.25 : Database - mindstorm-permission
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`mindstorm-permission` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;

USE `mindstorm-permission`;

/*Table structure for table `msp_menu` */

DROP TABLE IF EXISTS `msp_menu`;

CREATE TABLE `msp_menu` (
  `id` char(19) NOT NULL DEFAULT '' COMMENT '编号',
  `pid` char(19) NOT NULL DEFAULT '' COMMENT '所属上级',
  `name` varchar(20) NOT NULL DEFAULT '' COMMENT '名称',
  `alias` varchar(50) DEFAULT NULL COMMENT '菜单别名',
  `type` tinyint(4) NOT NULL COMMENT '类型(0:目录,1:菜单,2:按钮)',
  `permission_value` varchar(50) DEFAULT NULL COMMENT '权限标识',
  `path` varchar(100) DEFAULT NULL COMMENT '访问路径',
  `redirect` varchar(50) DEFAULT NULL COMMENT '重定向地址',
  `icon` varchar(50) DEFAULT NULL COMMENT '图标',
  `status` tinyint(4) DEFAULT NULL COMMENT '状态(0:禁止,1:正常)',
  `sort` int(11) DEFAULT NULL COMMENT '排序',
  `component` varchar(100) DEFAULT NULL COMMENT '组件路径',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_pid` (`pid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='菜单表';

/*Data for the table `msp_menu` */

insert  into `msp_menu`(`id`,`pid`,`name`,`alias`,`type`,`permission_value`,`path`,`redirect`,`icon`,`status`,`sort`,`component`,`gmt_create`,`gmt_modified`) values ('1395198727612125186','','权限管理','permission',0,'','/permission','/permission/menu','SafetyOutlined',1,1,'Layout','2021-05-20 02:04:33','2021-07-06 00:10:39'),('1395198931514019842','1395198727612125186','角色管理','role',1,'permission:role:list','/permission/role','','',1,0,'Permission/role/index','2021-05-20 02:05:21','2021-07-04 01:20:56'),('1395199215187382273','1395198727612125186','菜单管理','menu',1,'permission:menu:list','/permission/menu','','',1,1,'Permission/menu/index','2021-05-20 02:06:29','2021-07-04 01:20:50'),('1396424936618491906','1395198931514019842','角色添加','',2,'permission:role:add','','','',1,1,'','2021-05-23 11:17:04','2021-05-27 07:46:02'),('1396425046786080769','1395198931514019842','角色删除','',2,'permission:role:delete','','','',1,2,'','2021-05-23 11:17:30','2021-05-27 07:46:06'),('1396425291972509698','1395198931514019842','角色修改','',2,'permission:role:update','','','',1,3,'','2021-05-23 11:18:29','2021-05-27 07:46:11'),('1396425381437014018','1395198931514019842','分配菜单','',2,'permission:role:distribute','','','',1,4,'','2021-05-23 11:18:50','2021-05-27 07:46:17'),('1396425692906029058','1395198931514019842','角色状态修改','',2,'permission:role:status','','','',1,5,'','2021-05-23 11:20:04','2021-05-27 07:46:23'),('1396425826536554497','1395199215187382273','菜单添加','',2,'permission:menu:add','','','',1,1,'','2021-05-23 11:20:36','2021-05-27 07:46:41'),('1396425911802560514','1395199215187382273','菜单修改','',2,'permission:menu:update','','','',1,2,'','2021-05-23 11:20:56','2021-05-27 07:46:45'),('1396426008544182274','1395199215187382273','菜单删除','',2,'permission:menu:delete','','','',1,3,'','2021-05-23 11:21:19','2021-05-27 07:46:49'),('1397459937418063874','1395199215187382273','菜单查询','',2,'permission:menu:query',NULL,'','',1,4,'','2021-05-26 07:49:47','2021-05-27 07:46:52'),('1397460745765302273','1395198931514019842','角色查询','',2,'permission:role:query','','','',1,6,'','2021-05-26 07:53:00','2021-05-27 07:46:29'),('1401706908295106562','1395198727612125186','用户管理','user',1,'permission:user:list','/permission/user','','',1,3,'Permission/user/index','2021-06-07 01:05:44','2021-07-04 01:20:25'),('1401709999199379458','1401706908295106562','用户查询','',2,'permission:user:query','','','',1,1,'','2021-06-07 01:18:01','2021-06-07 01:18:01'),('1401710194104492033','1401706908295106562','用户新增','',2,'permission:user:add','','','',1,2,'','2021-06-07 01:18:47','2021-06-07 01:18:47'),('1401710280725258241','1401706908295106562','用户修改','',2,'permission:user:update','','','',1,3,'','2021-06-07 01:19:08','2021-06-07 01:19:08'),('1401710364561006593','1401706908295106562','用户删除','',2,'permission:user:delete','','','',1,4,'','2021-06-07 01:19:28','2021-06-07 01:19:28'),('1401710461017415682','1401706908295106562','用户分配角色','',2,'permission:user:distribute','','','',1,5,'','2021-06-07 01:19:51','2021-06-07 01:19:51'),('1401710857546915841','1401706908295106562','用户状态修改','',2,'permission:user:status','','','',1,6,'','2021-06-07 01:21:26','2021-06-07 01:21:26'),('1412205842255900673','','仪表盘','dashboard',0,NULL,'/dashboard','/dashboard/analysis','DashboardOutlined',1,0,'Layout','2021-07-06 00:24:45','2021-07-06 00:24:45'),('1412206210335436802','1412205842255900673','分析','analysis',1,'dashboard:analysis:index','/dashboard/analysis',NULL,NULL,1,0,'Dashboard/analysis','2021-07-06 00:26:13','2021-07-06 00:26:13'),('1412216071764897793','','教学管理','education',0,NULL,'/education','/education/academy','BankOutlined',1,2,'Layout','2021-07-06 01:05:24','2021-07-06 01:06:44'),('1412216346265317378','1412216071764897793','院系管理','academy',1,'education:academy;index','/education/academy',NULL,NULL,1,0,'Education/academy','2021-07-06 01:06:29','2021-07-06 01:06:29'),('1412701411810250754','1412216071764897793','专业管理','profession',1,'education:profession:index','/education/profession',NULL,NULL,1,1,'Education/profession','2021-07-07 09:13:58','2021-07-07 09:13:58'),('1415143008472805378','','用户管理','personnel',0,NULL,'/personnel','/personnel/student','UsergroupAddOutlined',1,3,'Layout','2021-07-14 02:56:00','2021-07-14 03:21:42'),('1415149674572427266','1415143008472805378','学生管理','student',1,'personnel:student:index','/personnel/student',NULL,NULL,1,0,'Personnel/student','2021-07-14 03:22:29','2021-07-14 03:22:29'),('1415994278138007553','','课程管理','course',0,NULL,'/course','/course/list','ClusterOutlined',1,5,'Layout','2021-07-16 11:18:38','2021-07-16 11:18:38'),('1415994558384623618','1415994278138007553','所有课程','courseList',1,'course:list:index','/course/list',NULL,NULL,1,0,'Course/list','2021-07-16 11:19:45','2021-07-18 10:57:46'),('1415994743529590786','1415994278138007553','我的课程','myCourse',1,'course:my:index','/course/my',NULL,NULL,1,1,'Course/my','2021-07-16 11:20:29','2021-07-18 10:58:01');

/*Table structure for table `msp_role` */

DROP TABLE IF EXISTS `msp_role`;

CREATE TABLE `msp_role` (
  `id` char(19) NOT NULL DEFAULT '' COMMENT '角色id',
  `role_name` varchar(20) NOT NULL DEFAULT '' COMMENT '角色名称',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `sort` int(11) DEFAULT NULL COMMENT '排序',
  `status` tinyint(4) DEFAULT NULL COMMENT '状态, 1 表示正常，0 表示已禁用',
  `is_deleted` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '逻辑删除 1（true）已删除， 0（false）未删除',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色信息表';

/*Data for the table `msp_role` */

insert  into `msp_role`(`id`,`role_name`,`remark`,`sort`,`status`,`is_deleted`,`gmt_create`,`gmt_modified`) values ('1393763491195981825','admin','超级管理员',0,1,0,'2021-05-16 03:01:26','2021-05-16 03:01:26'),('1406454366870994946','测试','cs',1,1,0,'2021-06-20 03:30:26','2021-06-20 03:30:26'),('1409813066193072130','zhl','zhl',2,0,1,'2021-06-29 09:56:43','2021-06-29 09:56:43');

/*Table structure for table `msp_role_menu` */

DROP TABLE IF EXISTS `msp_role_menu`;

CREATE TABLE `msp_role_menu` (
  `id` char(19) NOT NULL DEFAULT '',
  `role_id` char(19) NOT NULL DEFAULT '' COMMENT '角色Id',
  `menu_id` char(19) NOT NULL DEFAULT '' COMMENT '菜单Id',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_role_id` (`role_id`),
  KEY `idx_menu_id` (`menu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色权限表';

/*Data for the table `msp_role_menu` */

insert  into `msp_role_menu`(`id`,`role_id`,`menu_id`,`gmt_create`,`gmt_modified`) values ('1412780066615857153','1406454366870994946','1412206210335436802','2021-07-07 14:26:31','2021-07-07 14:26:31'),('1412780066745880577','1406454366870994946','1412205842255900673','2021-07-07 14:26:31','2021-07-07 14:26:31'),('1415995896183050241','1393763491195981825','1415994278138007553','2021-07-16 11:25:04','2021-07-16 11:25:04'),('1415995896313073665','1393763491195981825','1415994558384623618','2021-07-16 11:25:04','2021-07-16 11:25:04'),('1415995896375988226','1393763491195981825','1415994743529590786','2021-07-16 11:25:04','2021-07-16 11:25:04'),('1415995896506011649','1393763491195981825','1415143008472805378','2021-07-16 11:25:04','2021-07-16 11:25:04'),('1415995896837361666','1393763491195981825','1415149674572427266','2021-07-16 11:25:04','2021-07-16 11:25:04'),('1415995896971579394','1393763491195981825','1412216071764897793','2021-07-16 11:25:04','2021-07-16 11:25:04'),('1415995897298735105','1393763491195981825','1412216346265317378','2021-07-16 11:25:05','2021-07-16 11:25:05'),('1415995897428758530','1393763491195981825','1412701411810250754','2021-07-16 11:25:05','2021-07-16 11:25:05'),('1415995897688805378','1393763491195981825','1395198727612125186','2021-07-16 11:25:05','2021-07-16 11:25:05'),('1415995898015961089','1393763491195981825','1395198931514019842','2021-07-16 11:25:05','2021-07-16 11:25:05'),('1415995898145984513','1393763491195981825','1395199215187382273','2021-07-16 11:25:05','2021-07-16 11:25:05'),('1415995898183733250','1393763491195981825','1401706908295106562','2021-07-16 11:25:05','2021-07-16 11:25:05'),('1415995898250842113','1393763491195981825','1396424936618491906','2021-07-16 11:25:05','2021-07-16 11:25:05'),('1415995898313756674','1393763491195981825','1396425046786080769','2021-07-16 11:25:05','2021-07-16 11:25:05'),('1415995898439585793','1393763491195981825','1396425291972509698','2021-07-16 11:25:05','2021-07-16 11:25:05'),('1415995898506694657','1393763491195981825','1396425381437014018','2021-07-16 11:25:05','2021-07-16 11:25:05'),('1415995898573803522','1393763491195981825','1396425692906029058','2021-07-16 11:25:05','2021-07-16 11:25:05'),('1415995898649300993','1393763491195981825','1397460745765302273','2021-07-16 11:25:05','2021-07-16 11:25:05'),('1415995898699632642','1393763491195981825','1396425826536554497','2021-07-16 11:25:05','2021-07-16 11:25:05'),('1415995898766741505','1393763491195981825','1396425911802560514','2021-07-16 11:25:05','2021-07-16 11:25:05'),('1415995898829656065','1393763491195981825','1396426008544182274','2021-07-16 11:25:05','2021-07-16 11:25:05'),('1415995898892570625','1393763491195981825','1397459937418063874','2021-07-16 11:25:05','2021-07-16 11:25:05'),('1415995899022594049','1393763491195981825','1401709999199379458','2021-07-16 11:25:05','2021-07-16 11:25:05'),('1415995899085508609','1393763491195981825','1401710194104492033','2021-07-16 11:25:05','2021-07-16 11:25:05'),('1415995899148423170','1393763491195981825','1401710280725258241','2021-07-16 11:25:05','2021-07-16 11:25:05'),('1415995899731431425','1393763491195981825','1401710364561006593','2021-07-16 11:25:05','2021-07-16 11:25:05'),('1415995900385742849','1393763491195981825','1401710461017415682','2021-07-16 11:25:05','2021-07-16 11:25:05'),('1415995901040054273','1393763491195981825','1401710857546915841','2021-07-16 11:25:05','2021-07-16 11:25:05'),('1415995901300101121','1393763491195981825','1412205842255900673','2021-07-16 11:25:05','2021-07-16 11:25:05'),('1415995901581119490','1393763491195981825','1412206210335436802','2021-07-16 11:25:06','2021-07-16 11:25:06');

/*Table structure for table `msp_user` */

DROP TABLE IF EXISTS `msp_user`;

CREATE TABLE `msp_user` (
  `id` char(19) NOT NULL COMMENT '用户Id',
  `username` varchar(255) NOT NULL DEFAULT '' COMMENT '用户名',
  `password` varchar(100) NOT NULL DEFAULT '' COMMENT '密码',
  `nick_name` varchar(50) DEFAULT NULL COMMENT '昵称',
  `real_name` varchar(50) NOT NULL COMMENT '真实姓名',
  `sno` varchar(50) DEFAULT NULL COMMENT '学生学号',
  `age` int(11) DEFAULT NULL COMMENT '年龄',
  `sex` tinyint(3) DEFAULT NULL COMMENT '性别 1.男 2.女',
  `birth_day` datetime DEFAULT NULL COMMENT '生日',
  `phone` varchar(50) DEFAULT NULL COMMENT '电话',
  `email` varchar(30) DEFAULT NULL COMMENT '邮箱',
  `status` tinyint(4) DEFAULT NULL COMMENT '状态(0:禁止,1:正常)',
  `avatar` varchar(255) DEFAULT NULL COMMENT '头像地址',
  `wx_open_id` varchar(255) DEFAULT NULL COMMENT '微信openId',
  `is_deleted` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '逻辑删除 1（true）已删除， 0（false）未删除',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

/*Data for the table `msp_user` */

insert  into `msp_user`(`id`,`username`,`password`,`nick_name`,`real_name`,`sno`,`age`,`sex`,`birth_day`,`phone`,`email`,`status`,`avatar`,`wx_open_id`,`is_deleted`,`gmt_create`,`gmt_modified`) values ('1391740932568969217','admin','$2a$10$j7LC9sLMaOJ2pkP1at4uq.963fVUVf5UnXdfkVFFoRqHmQi1Maqae',NULL,'罗炜杰','',20,1,'2000-08-28 16:00:00','13850801092','lwjppz@gmail.com',1,'https://www.lwjppz.cn/upload/2021/02/1410480970-3c4c82dd0212492b90219e38ae50fd91.jpeg',NULL,0,'2021-05-10 13:04:30','2021-06-02 08:36:14'),('1406454464963182593','cs','$2a$10$NCoYtR..jXjG59wwzsgxwOVDw2j/pjXfUvmjWnOgQmEB9cTZvINRa',NULL,'cs',NULL,0,1,NULL,'','',1,NULL,NULL,1,'2021-06-20 03:30:50','2021-06-20 03:30:50'),('1410893926338174978','demo','$2a$10$eq8hPTviHWDCap32Udz.wuH8CRelyTDwDbfZQRpraCuHoMMBKo1CG',NULL,'demo',NULL,19,1,'2002-07-10 16:00:00','13850801092','xxx@163.com',1,NULL,NULL,0,'2021-07-02 09:31:40','2021-07-02 13:10:03');

/*Table structure for table `msp_user_role` */

DROP TABLE IF EXISTS `msp_user_role`;

CREATE TABLE `msp_user_role` (
  `id` char(19) NOT NULL DEFAULT '' COMMENT '主键id',
  `role_id` char(19) NOT NULL DEFAULT '0' COMMENT '角色id',
  `user_id` char(19) NOT NULL DEFAULT '0' COMMENT '用户id',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_role_id` (`role_id`),
  KEY `idx_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户角色表';

/*Data for the table `msp_user_role` */

insert  into `msp_user_role`(`id`,`role_id`,`user_id`,`gmt_create`,`gmt_modified`) values ('1396359019167543298','1393763491195981825','1391740932568969217','2021-05-23 06:55:08','2021-05-23 06:55:08'),('1410948894696939522','1406454366870994946','1410893926338174978','2021-07-02 13:10:05','2021-07-02 13:10:05');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
