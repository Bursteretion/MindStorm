/*
SQLyog Ultimate v12.09 (64 bit)
MySQL - 5.7.25 : Database - mindstorm-ucenter
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`mindstorm-ucenter` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;

USE `mindstorm-ucenter`;

/*Table structure for table `msu_student` */

DROP TABLE IF EXISTS `msu_student`;

CREATE TABLE `msu_student` (
  `id` char(19) NOT NULL COMMENT '学生Id',
  `dept_id` varchar(19) NOT NULL COMMENT '院系Id',
  `major_id` varchar(19) NOT NULL COMMENT '专业Id',
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
  `user_level` int(11) DEFAULT NULL COMMENT '学生年级(1-16)',
  `wx_open_id` varchar(255) DEFAULT NULL COMMENT '微信openId',
  `is_deleted` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '逻辑删除 1（true）已删除， 0（false）未删除',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
