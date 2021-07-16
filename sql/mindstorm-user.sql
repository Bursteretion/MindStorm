/*
SQLyog Ultimate v12.09 (64 bit)
MySQL - 5.7.25 : Database - mindstorm-user
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`mindstorm-user` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;

USE `mindstorm-user`;

/*Table structure for table `msu_student` */

DROP TABLE IF EXISTS `msu_student`;

CREATE TABLE `msu_student` (
  `id` char(19) NOT NULL COMMENT '学生Id',
  `academy_id` varchar(19) NOT NULL COMMENT '院系Id',
  `profession_id` varchar(19) NOT NULL COMMENT '专业Id',
  `team_id` char(19) NOT NULL COMMENT '班级Id',
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='学生表';

/*Data for the table `msu_student` */

insert  into `msu_student`(`id`,`academy_id`,`profession_id`,`team_id`,`username`,`password`,`nick_name`,`real_name`,`sno`,`age`,`sex`,`birth_day`,`phone`,`email`,`status`,`avatar`,`wx_open_id`,`is_deleted`,`gmt_create`,`gmt_modified`) values ('1415279829995380737','1413060171875524610','1414567755866578945','','lwjppz','123456',NULL,'罗炜杰','20180861215',20,1,NULL,'13850801092','lwjppz@gmail.com',1,NULL,NULL,0,'2021-07-14 11:59:41','2021-07-14 11:59:41'),('1415295010376908802','1413060171875524610','1414567755866578945','','zeus','123456',NULL,'周寒莉','20180861214',20,2,NULL,'18750836893','1731667524@qq.com',1,NULL,NULL,0,'2021-07-14 13:00:00','2021-07-14 13:00:00');

/*Table structure for table `msu_teacher` */

DROP TABLE IF EXISTS `msu_teacher`;

CREATE TABLE `msu_teacher` (
  `id` char(19) NOT NULL COMMENT '教师Id',
  `academy_id` varchar(19) NOT NULL COMMENT '院系Id',
  `username` varchar(255) NOT NULL DEFAULT '' COMMENT '用户名',
  `password` varchar(100) NOT NULL DEFAULT '' COMMENT '密码',
  `nick_name` varchar(50) DEFAULT NULL COMMENT '昵称',
  `real_name` varchar(50) DEFAULT NULL COMMENT '真实姓名',
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='教师表';

/*Data for the table `msu_teacher` */

insert  into `msu_teacher`(`id`,`academy_id`,`username`,`password`,`nick_name`,`real_name`,`age`,`sex`,`birth_day`,`phone`,`email`,`status`,`avatar`,`wx_open_id`,`is_deleted`,`gmt_create`,`gmt_modified`) values ('1415306634357800961','1413060171875524610','罗老师','123456',NULL,'罗早早',32,1,NULL,'13850801092','13850801092@163.com',1,NULL,NULL,0,'2021-07-14 13:46:11','2021-07-14 13:47:05');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
