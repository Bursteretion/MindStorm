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

/*Table structure for table `msp_user` */

DROP TABLE IF EXISTS `msp_user`;

CREATE TABLE `msp_user` (
  `id` char(19) NOT NULL COMMENT '用户Id',
  `username` varchar(255) NOT NULL DEFAULT '' COMMENT '用户名',
  `password` varchar(100) NOT NULL DEFAULT '' COMMENT '密码',
  `nick_name` varchar(50) DEFAULT NULL COMMENT '昵称',
  `real_name` varchar(50) DEFAULT NULL COMMENT '真实姓名',
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

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
