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

insert  into `mse_academy`(`id`,`pid`,`name`,`status`,`sort`,`is_deleted`,`gmt_create`,`gmt_modified`) values ('1413032069514436609','0','三明学院',1,0,0,'2021-07-08 07:07:53','2021-07-08 07:07:53'),('1413060171875524610','1413032069514436609','信息工程学院',1,1,0,'2021-07-08 08:59:33','2021-07-08 08:59:34'),('1413060352268345346','1413032069514436609','机电工程学院',1,2,0,'2021-07-08 09:00:16','2021-07-08 09:00:16');

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

insert  into `mse_profession`(`id`,`name`,`status`,`sort`,`is_deleted`,`gmt_create`,`gmt_modified`) values ('1412962345157955585','计算机科学专业技术',1,0,0,'2021-07-08 02:30:49','2021-07-08 02:32:04'),('1412962768916877313','通信工程',1,1,0,'2021-07-08 02:32:30','2021-07-08 03:07:26'),('1412963549984362497','网络工程',1,2,0,'2021-07-08 02:35:37','2021-07-08 03:07:33'),('1412963821011898369','物联网工程',1,3,0,'2021-07-08 02:36:41','2021-07-08 02:36:41'),('1412963958920613889','人工智能',1,4,0,'2021-07-08 02:37:14','2021-07-08 03:07:39'),('1412971817104347137','数学与应用数学(金融与统计)',1,5,0,'2021-07-08 03:08:28','2021-07-08 03:08:28');

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
