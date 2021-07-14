/*
SQLyog Ultimate v12.09 (64 bit)
MySQL - 5.7.25 : Database - mindstorm-education
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`mindstorm-education` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;

USE `mindstorm-education`;

/*Table structure for table `mse_academy` */

DROP TABLE IF EXISTS `mse_academy`;

CREATE TABLE `mse_academy` (
  `id` char(19) NOT NULL COMMENT '院系Id',
  `pid` char(19) NOT NULL DEFAULT '',
  `name` varchar(255) NOT NULL DEFAULT '' COMMENT '院系名称',
  `status` tinyint(1) NOT NULL DEFAULT '1' COMMENT '院系状态（0 被禁用，1 正常）',
  `sort` int(11) DEFAULT NULL COMMENT '排序',
  `is_deleted` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '逻辑删除 1（true）已删除， 0（false）未删除',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='学院表';

/*Data for the table `mse_academy` */

insert  into `mse_academy`(`id`,`pid`,`name`,`status`,`sort`,`is_deleted`,`gmt_create`,`gmt_modified`) values ('1413032069514436609','0','三明学院',1,0,0,'2021-07-08 07:07:53','2021-07-08 07:07:53'),('1413060171875524610','1413032069514436609','信息工程学院',1,1,0,'2021-07-08 08:59:33','2021-07-08 08:59:34'),('1413060352268345346','1413032069514436609','机电工程学院',1,2,0,'2021-07-08 09:00:16','2021-07-08 09:00:16'),('1414558640079507457','1413060352268345346','电子工程系',1,1,0,'2021-07-12 12:13:56','2021-07-12 12:13:56'),('1414558751606050817','1413060352268345346','机械工程系',1,2,0,'2021-07-12 12:14:22','2021-07-12 12:14:22'),('1414558920292569089','1413060352268345346','车辆工程系',1,3,0,'2021-07-12 12:15:02','2021-07-12 12:15:02');

/*Table structure for table `mse_academy_profession` */

DROP TABLE IF EXISTS `mse_academy_profession`;

CREATE TABLE `mse_academy_profession` (
  `id` char(19) NOT NULL COMMENT '院系Id',
  `academy_id` varchar(19) NOT NULL DEFAULT '' COMMENT '院系Id',
  `profession_id` varchar(19) NOT NULL DEFAULT '' COMMENT '专业Id',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='学院专业关联表';

/*Data for the table `mse_academy_profession` */

insert  into `mse_academy_profession`(`id`,`academy_id`,`profession_id`,`gmt_create`,`gmt_modified`) values ('1414567756021768193','1413060171875524610','1414567755866578945','2021-07-12 12:50:09','2021-07-12 12:50:09'),('1414567816671404033','1413060171875524610','1414567816084201473','2021-07-12 12:50:24','2021-07-12 12:50:24'),('1414567879887953921','1413060171875524610','1414567879820845058','2021-07-12 12:50:39','2021-07-12 12:50:39'),('1414567938532712450','1413060171875524610','1414567938268471298','2021-07-12 12:50:53','2021-07-12 12:50:53'),('1414568001191419906','1413060171875524610','1414568000801349634','2021-07-12 12:51:08','2021-07-12 12:51:08'),('1414568060469518338','1413060171875524610','1414568060016533505','2021-07-12 12:51:22','2021-07-12 12:51:22'),('1414568153683730433','1414558640079507457','1414568153213968385','2021-07-12 12:51:44','2021-07-12 12:51:44'),('1414568260235829249','1414558640079507457','1414568260172914689','2021-07-12 12:52:09','2021-07-12 12:52:09'),('1414568522715373570','1414558640079507457','1414568522249805826','2021-07-12 12:53:12','2021-07-12 12:53:12'),('1414568604244254721','1414558640079507457','1414568603849990145','2021-07-12 12:53:31','2021-07-12 12:53:31'),('1414568690068103170','1414558751606050817','1414568689397014529','2021-07-12 12:53:52','2021-07-12 12:53:52'),('1414568749782409217','1414558751606050817','1414568749715300354','2021-07-12 12:54:06','2021-07-12 12:54:06'),('1414568833496522754','1414558751606050817','1414568833291001857','2021-07-12 12:54:26','2021-07-12 12:54:26'),('1414568996243906561','1414558920292569089','1414568996046774273','2021-07-12 12:55:05','2021-07-12 12:55:05'),('1414569117115359234','1413060352268345346','1414569116989530113','2021-07-12 12:55:34','2021-07-12 12:55:34');

/*Table structure for table `mse_idea` */

DROP TABLE IF EXISTS `mse_idea`;

CREATE TABLE `mse_idea` (
  `id` char(19) NOT NULL DEFAULT '' COMMENT '知识点Id',
  `pid` char(19) DEFAULT '' COMMENT '上级知识点Id',
  `name` varchar(50) NOT NULL DEFAULT '' COMMENT '知识点名称',
  `description` text COMMENT '知识点描述',
  `sort` int(11) DEFAULT NULL COMMENT '排序',
  `is_deleted` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '逻辑删除 1（true）已删除， 0（false）未删除',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Data for the table `mse_idea` */

/*Table structure for table `mse_profession` */

DROP TABLE IF EXISTS `mse_profession`;

CREATE TABLE `mse_profession` (
  `id` char(19) NOT NULL COMMENT '专业Id',
  `name` varchar(255) NOT NULL DEFAULT '' COMMENT '专业名称',
  `status` tinyint(1) NOT NULL DEFAULT '1' COMMENT '专业状态（1 正常，0 禁用）',
  `sort` int(11) DEFAULT NULL COMMENT '排序',
  `is_deleted` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '逻辑删除 1（true）已删除， 0（false）未删除',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='开设专业表';

/*Data for the table `mse_profession` */

insert  into `mse_profession`(`id`,`name`,`status`,`sort`,`is_deleted`,`gmt_create`,`gmt_modified`) values ('1414567755866578945','计算机科学与技术(软件技术方向)',1,1,0,'2021-07-12 12:50:09','2021-07-12 12:50:09'),('1414567816084201473','物联网工程',1,2,0,'2021-07-12 12:50:23','2021-07-12 12:50:23'),('1414567879820845058','网络工程',1,3,0,'2021-07-12 12:50:39','2021-07-12 12:50:39'),('1414567938268471298','通信工程',1,4,0,'2021-07-12 12:50:53','2021-07-12 12:50:53'),('1414568000801349634','数学与应用数学（金融与统计）',1,5,0,'2021-07-12 12:51:07','2021-07-12 12:51:07'),('1414568060016533505','人工智能',1,6,0,'2021-07-12 12:51:22','2021-07-12 12:51:22'),('1414568153213968385','电子科学与技术',1,1,0,'2021-07-12 12:51:44','2021-07-12 12:51:44'),('1414568260172914689','电子信息工程（中外合作办学）',1,2,0,'2021-07-12 12:52:09','2021-07-12 12:52:09'),('1414568522249805826','光电信息科学与工程',1,3,0,'2021-07-12 12:53:12','2021-07-12 12:53:12'),('1414568603849990145','电子信息工程',1,4,0,'2021-07-12 12:53:31','2021-07-12 12:53:31'),('1414568689397014529','机器人工程',1,1,0,'2021-07-12 12:53:52','2021-07-12 12:53:52'),('1414568749715300354','机械设计制造及其自动化',1,2,0,'2021-07-12 12:54:06','2021-07-12 12:54:06'),('1414568833291001857','机械设计制造及其自动化(国际留学生)',1,3,0,'2021-07-12 12:54:26','2021-07-12 12:54:26'),('1414568996046774273','车辆工程',1,1,0,'2021-07-12 12:55:05','2021-07-12 12:55:05'),('1414569116989530113','物理学（师范类）',1,9,0,'2021-07-12 12:55:34','2021-07-12 12:55:34');

/*Table structure for table `mse_profession_idea` */

DROP TABLE IF EXISTS `mse_profession_idea`;

CREATE TABLE `mse_profession_idea` (
  `id` char(19) NOT NULL DEFAULT '' COMMENT 'Id',
  `profession_id` char(19) NOT NULL DEFAULT '' COMMENT '学科Id',
  `idea_id` char(19) NOT NULL DEFAULT '' COMMENT '知识点Id',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Data for the table `mse_profession_idea` */

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
