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
  `pid` char(19) NOT NULL DEFAULT '' COMMENT '上级院系Id',
  `name` varchar(255) NOT NULL DEFAULT '' COMMENT '院系名称',
  `status` tinyint(1) NOT NULL DEFAULT '1' COMMENT '院系状态（0 被禁用，1 正常）',
  `sort` int(11) NOT NULL COMMENT '排序',
  `is_deleted` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '逻辑删除 1（true）已删除， 0（false）未删除',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='学院表';

/*Data for the table `mse_academy` */

insert  into `mse_academy`(`id`,`pid`,`name`,`status`,`sort`,`is_deleted`,`gmt_create`,`gmt_modified`) values ('1413032069514436609','0','三明学院',1,0,0,'2021-07-08 07:07:53','2021-07-08 07:07:53'),('1413060171875524610','1413032069514436609','信息工程学院',1,1,0,'2021-07-08 08:59:33','2021-07-08 08:59:34'),('1413060352268345346','1413032069514436609','机电工程学院',1,2,0,'2021-07-08 09:00:16','2021-07-08 09:00:16'),('1414558640079507457','1413060352268345346','电子工程系',1,1,0,'2021-07-12 12:13:56','2021-07-12 12:13:56'),('1414558751606050817','1413060352268345346','机械工程系',1,2,0,'2021-07-12 12:14:22','2021-07-12 12:14:22'),('1414558920292569089','1413060352268345346','车辆工程系',1,3,0,'2021-07-12 12:15:02','2021-07-12 12:15:02'),('1415298795950186497','1413032069514436609','马克思主义学院',1,3,0,'2021-07-14 13:15:03','2021-07-14 13:15:03');

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

/*Table structure for table `mse_course` */

DROP TABLE IF EXISTS `mse_course`;

CREATE TABLE `mse_course` (
  `id` char(19) NOT NULL DEFAULT '' COMMENT '课程Id',
  `user_id` char(19) NOT NULL COMMENT '课程所有者Id',
  `name` varchar(255) NOT NULL COMMENT '课程名称',
  `teacher_name` varchar(50) NOT NULL COMMENT '开设该课程的教师名称',
  `academy_id` char(19) NOT NULL COMMENT '课程所属院系Id',
  `thumbnail` varchar(255) NOT NULL COMMENT '课程封面',
  `encryption` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否加密考试、题库（0 不加密， 1 加密）',
  `description` varchar(500) DEFAULT NULL COMMENT '课程说明',
  `status` tinyint(1) DEFAULT '1' COMMENT '课程状态（0 被禁用，1 正常）',
  `is_deleted` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '逻辑删除 1（true）已删除， 0（false）未删除',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='课程表';

/*Data for the table `mse_course` */

insert  into `mse_course`(`id`,`user_id`,`name`,`teacher_name`,`academy_id`,`thumbnail`,`encryption`,`description`,`status`,`is_deleted`,`gmt_create`,`gmt_modified`) values ('1416199639684030465','1391740932568969217','测试课程','罗炜杰','1413060171875524610','http://localhost:8000/mindstorm-file/upload/course/course-cover/2021/07/alone-9ce31a46152d465480bb1ad12c6d1e67.jpg',0,NULL,1,0,'2021-07-17 00:54:40','2021-07-17 00:54:40'),('1418217402132779010','1410893926338174978','炉石传说酒馆战旗','周寒莉','1413060171875524610','http://localhost:8000/mindstorm-file/upload/course/course-cover/2021/07/20200407225122-79d936665a94459d92a0e6b1eaeab95c.jpg',0,NULL,1,0,'2021-07-22 14:32:33','2021-07-22 14:32:33'),('1419651249651220482','1391740932568969217','C语言程序设计','罗炜杰','1413060171875524610','http://localhost:8000/mindstorm-file/upload/course/course-cover/2021/07/2b1236750b049312a8b1c5eafed0877e-b3dbd07d25da4ed28a04f3b9c2043e00.jpg',0,NULL,1,0,'2021-07-26 13:30:08','2021-07-26 13:30:08');

/*Table structure for table `mse_course_class` */

DROP TABLE IF EXISTS `mse_course_class`;

CREATE TABLE `mse_course_class` (
  `id` char(19) NOT NULL DEFAULT '' COMMENT '课程班级Id',
  `course_id` char(19) NOT NULL COMMENT '课程Id',
  `class_name` varchar(100) NOT NULL COMMENT '班级名称',
  `sort` int(11) DEFAULT NULL COMMENT '班级排序',
  `invitation_code` varchar(50) DEFAULT NULL COMMENT '班级邀请码',
  `is_deleted` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '逻辑删除 1（true）已删除， 0（false）未删除',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='课程班级表';

/*Data for the table `mse_course_class` */

insert  into `mse_course_class`(`id`,`course_id`,`class_name`,`sort`,`invitation_code`,`is_deleted`,`gmt_create`,`gmt_modified`) values ('1416199646961147906','1416199639684030465','计算机科学与技术2班',0,'l7WsovGw',0,'2021-07-17 00:54:42','2021-07-18 12:14:19'),('1416739061374537729','1416199639684030465','测试',2,'VBQOgMgr',1,'2021-07-18 12:38:09','2021-07-18 12:38:09'),('1416739337309409282','1416199639684030465','计算机科学与技术1班',1,'knLiZxFk',1,'2021-07-18 12:39:14','2021-07-18 12:39:14'),('1416936753467949057','1416199639684030465','demo',2,'R6tXi2ap',1,'2021-07-19 01:43:42','2021-07-19 01:43:42'),('1416948351054065665','1416199639684030465','周寒莉的小班级',1,'fLGF1ZPp',0,'2021-07-19 02:29:47','2021-07-22 12:32:21'),('1417062347308453889','1416199639684030465','demo',4,'o4MYaF1x',1,'2021-07-19 10:02:46','2021-07-19 10:02:46'),('1418217403479150594','1418217402132779010','酒馆战旗1班',0,'BwGafWcz',0,'2021-07-22 14:32:33','2021-07-22 14:33:02'),('1418367028949299202','1418217402132779010','酒馆战旗2班',1,'yO4IVQXt',0,'2021-07-23 00:27:06','2021-07-23 00:27:06'),('1418367080249831425','1418217402132779010','酒馆战旗3班',2,'swiiLhtL',0,'2021-07-23 00:27:19','2021-07-23 00:27:19'),('1418367150668001281','1418217402132779010','酒馆战旗4班',3,'oi25FyhQ',0,'2021-07-23 00:27:35','2021-07-23 00:27:35'),('1419651251890978817','1419651249651220482','默认班级',0,'h2TdzoEZ',0,'2021-07-26 13:30:09','2021-07-26 13:30:09');

/*Table structure for table `mse_course_class_student` */

DROP TABLE IF EXISTS `mse_course_class_student`;

CREATE TABLE `mse_course_class_student` (
  `id` char(19) NOT NULL DEFAULT '' COMMENT 'Id',
  `class_id` char(19) NOT NULL COMMENT '班级Id',
  `student_id` char(19) NOT NULL COMMENT '学生Id',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='课程班级学生表';

/*Data for the table `mse_course_class_student` */

insert  into `mse_course_class_student`(`id`,`class_id`,`student_id`,`gmt_create`,`gmt_modified`) values ('1417329278942855170','1416948351054065665','1415295010376908802','2021-07-20 03:43:27','2021-07-20 03:43:27'),('1417329563803205634','1416948351054065665','1415279829995380737','2021-07-20 03:44:35','2021-07-20 03:44:35'),('1418186930870689794','1416199646961147906','1415279829995380737','2021-07-22 12:31:28','2021-07-22 12:31:28'),('1418186938802118657','1416199646961147906','1415295010376908802','2021-07-22 12:31:30','2021-07-22 12:31:30'),('1418217561675714562','1418217403479150594','1415279829995380737','2021-07-22 14:33:11','2021-07-22 14:33:11');

/*Table structure for table `mse_exam` */

DROP TABLE IF EXISTS `mse_exam`;

CREATE TABLE `mse_exam` (
  `id` char(19) NOT NULL DEFAULT '' COMMENT '考试Id',
  `exam_paper_id` char(19) NOT NULL COMMENT '试卷Id',
  `course_id` char(19) NOT NULL COMMENT '考试所属课程Id',
  `status` tinyint(3) NOT NULL DEFAULT '0' COMMENT '考试状态（0-未开始，1-进行中，2-已结束）',
  `sort` int(11) NOT NULL DEFAULT '0' COMMENT '考试排序',
  `is_deleted` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '逻辑删除 1（true）已删除， 0（false）未删除',
  `gmt_create` datetime DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='考试表';

/*Data for the table `mse_exam` */

/*Table structure for table `mse_exam_paper` */

DROP TABLE IF EXISTS `mse_exam_paper`;

CREATE TABLE `mse_exam_paper` (
  `id` char(19) NOT NULL DEFAULT '' COMMENT '试卷Id',
  `pid` char(19) DEFAULT NULL COMMENT '上级文件夹Id',
  `course_id` char(19) NOT NULL COMMENT '试卷所属课程Id',
  `user_id` char(19) NOT NULL COMMENT '试卷创建用户Id',
  `title` varchar(100) NOT NULL COMMENT '试卷（文件夹）名称',
  `question_count` int(11) DEFAULT NULL COMMENT '试卷题目数量',
  `total_score` decimal(10,0) NOT NULL COMMENT '试卷总分',
  `publish_count` int(11) DEFAULT NULL COMMENT '试卷发放次数',
  `difficulty` tinyint(3) DEFAULT '0' COMMENT '试卷难度（0-简单，1-中等，2-困难）',
  `sort` int(11) NOT NULL DEFAULT '0' COMMENT '试卷排序',
  `is_folder` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '是否是文件夹（0-题目，1-文件夹）',
  `status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '试卷状态（0-未完成，1-完成）',
  `is_deleted` tinyint(1) unsigned DEFAULT '0' COMMENT '逻辑删除 1（true）已删除， 0（false）未删除',
  `gmt_create` datetime DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='试卷表';

/*Data for the table `mse_exam_paper` */

/*Table structure for table `mse_exam_paper_question` */

DROP TABLE IF EXISTS `mse_exam_paper_question`;

CREATE TABLE `mse_exam_paper_question` (
  `id` char(19) NOT NULL DEFAULT '' COMMENT 'Id',
  `exam_paper_id` char(19) NOT NULL COMMENT '试卷Id',
  `question_id` char(19) NOT NULL COMMENT '题目Id',
  `score` float NOT NULL COMMENT '题目分数',
  `gmt_create` datetime DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='试卷题目表';

/*Data for the table `mse_exam_paper_question` */

/*Table structure for table `mse_exam_setting` */

DROP TABLE IF EXISTS `mse_exam_setting`;

CREATE TABLE `mse_exam_setting` (
  `id` char(19) NOT NULL DEFAULT '' COMMENT '设置Id',
  `exam_paper_id` char(19) NOT NULL COMMENT '考试试卷Id',
  `publish_class` varchar(255) NOT NULL COMMENT '考试发放班级Id集合',
  `start_time` datetime NOT NULL COMMENT '考试开始时间',
  `end_time` datetime NOT NULL COMMENT '考试结束时间',
  `limit_time` int(11) NOT NULL COMMENT '考试限时',
  `immediate_publish` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '是否立即发放（0-否，1-是）',
  `submit_limit_time` int(11) DEFAULT NULL COMMENT '限时提交时间（考试开始xx分钟内不允许提交）',
  `entry_limit_time` int(11) DEFAULT NULL COMMENT '限时进入时间（开考xx分钟后不允许参加考试）',
  `disorder_question` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '是否打乱题目顺序（学生接收到的题目显示顺序不同）',
  `disorder_option` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '是否打乱选项顺序（学生接收到的题目选项顺序不同）',
  `passing_standard_score` decimal(10,0) NOT NULL COMMENT '及格标准分数',
  `expired_auto_submit` tinyint(1) unsigned NOT NULL COMMENT '考试到达截止时间后是否自动提交',
  `allow_retest` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '是否允许学生重考',
  `retest_count` int(11) DEFAULT NULL COMMENT '允许重考次数',
  `keep_last_answer` tinyint(1) unsigned DEFAULT NULL COMMENT '学生重考是否保留前一次考试的作答记录',
  `final_score_standard` tinyint(1) DEFAULT NULL COMMENT '最终成绩（0-最后一次考试成绩，1-最高成绩）',
  `allow_look_paper` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '是否允许学生考后查看试卷',
  `allow_look_answer` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '是否允许查看答案',
  `look_answer_standard` tinyint(1) NOT NULL DEFAULT '0' COMMENT '查看答案标准（0-学生提交后，1-考试截止后）',
  `allow_look_score` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '是否允许学生查看分数',
  `allow_look_rank` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '是否允许学生查看排名',
  `allow_paste` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '是否允许学生粘贴答案',
  `send_notice` tinyint(1) unsigned NOT NULL DEFAULT '1' COMMENT '是否发通知提醒',
  `notice_content` text COMMENT '通知内容',
  `need_exam_code` tinyint(1) unsigned NOT NULL DEFAULT '1' COMMENT '是否进入考试需要考试码',
  `exam_code` varchar(50) DEFAULT NULL COMMENT '考试码',
  `exam_code_expired_time` datetime DEFAULT NULL COMMENT '考试码到期时间',
  `limit_ip` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '是否限制IP参加考试',
  `allow_ip` text COMMENT '允许的IP',
  `fill_to_subjective` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '是否填空类型的题目设为主观题',
  `fill_ingore_case` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '填空题答案是否不区分大小写',
  `multiple_half_score` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '多选题未选全给一半分',
  `is_deleted` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '逻辑删除 1（true）已删除， 0（false）未删除',
  `gmt_create` datetime DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='考试设置表';

/*Data for the table `mse_exam_setting` */

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

insert  into `mse_profession`(`id`,`name`,`status`,`sort`,`is_deleted`,`gmt_create`,`gmt_modified`) values ('1414567755866578945','计算机科学与技术',1,1,0,'2021-07-12 12:50:09','2021-07-14 12:58:06'),('1414567816084201473','物联网工程',1,2,0,'2021-07-12 12:50:23','2021-07-12 12:50:23'),('1414567879820845058','网络工程',1,3,0,'2021-07-12 12:50:39','2021-07-12 12:50:39'),('1414567938268471298','通信工程',1,4,0,'2021-07-12 12:50:53','2021-07-12 12:50:53'),('1414568000801349634','数学与应用数学（金融与统计）',1,5,0,'2021-07-12 12:51:07','2021-07-12 12:51:07'),('1414568060016533505','人工智能',1,6,0,'2021-07-12 12:51:22','2021-07-12 12:51:22'),('1414568153213968385','电子科学与技术',1,1,0,'2021-07-12 12:51:44','2021-07-12 12:51:44'),('1414568260172914689','电子信息工程（中外合作办学）',1,2,0,'2021-07-12 12:52:09','2021-07-12 12:52:09'),('1414568522249805826','光电信息科学与工程',1,3,0,'2021-07-12 12:53:12','2021-07-12 12:53:12'),('1414568603849990145','电子信息工程',1,4,0,'2021-07-12 12:53:31','2021-07-12 12:53:31'),('1414568689397014529','机器人工程',1,1,0,'2021-07-12 12:53:52','2021-07-12 12:53:52'),('1414568749715300354','机械设计制造及其自动化',1,2,0,'2021-07-12 12:54:06','2021-07-12 12:54:06'),('1414568833291001857','机械设计制造及其自动化(国际留学生)',1,3,0,'2021-07-12 12:54:26','2021-07-12 12:54:26'),('1414568996046774273','车辆工程',1,1,0,'2021-07-12 12:55:05','2021-07-12 12:55:05'),('1414569116989530113','物理学（师范类）',1,9,0,'2021-07-12 12:55:34','2021-07-12 12:55:34');

/*Table structure for table `mse_question` */

DROP TABLE IF EXISTS `mse_question`;

CREATE TABLE `mse_question` (
  `id` char(19) NOT NULL DEFAULT '' COMMENT '题目Id',
  `pid` char(19) DEFAULT NULL COMMENT '当且仅当（is_folder=true）且由上级文件夹',
  `course_id` char(19) NOT NULL COMMENT '所属课程Id',
  `user_id` char(19) NOT NULL COMMENT '创建用户Id',
  `question_type_id` char(19) DEFAULT NULL COMMENT '题目类型Id',
  `original_content` text NOT NULL COMMENT '题目描述（纯文本）',
  `format_content` text NOT NULL COMMENT '题目描述（html）',
  `sort` int(11) NOT NULL DEFAULT '0' COMMENT '题目排序（文件夹自动为1，题目为0）',
  `usage_amount` int(10) DEFAULT NULL COMMENT '题目被使用量',
  `difficulty` tinyint(3) DEFAULT NULL COMMENT '题目难度',
  `is_folder` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '是否是文件夹（0-题目，1-文件夹）',
  `answer_analyze` text COMMENT '题目答案解析',
  `is_deleted` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '逻辑删除 1（true）已删除， 0（false）未删除',
  `gmt_create` datetime DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='题目表';

/*Data for the table `mse_question` */

insert  into `mse_question`(`id`,`pid`,`course_id`,`user_id`,`question_type_id`,`original_content`,`format_content`,`sort`,`usage_amount`,`difficulty`,`is_folder`,`answer_analyze`,`is_deleted`,`gmt_create`,`gmt_modified`) values ('1420715153412231170','0','1419651249651220482','1391740932568969217',NULL,'计算机','计算机',1,NULL,NULL,1,NULL,0,'2021-07-29 11:57:43','2021-07-29 11:57:43'),('1420715154230120450','1420715153412231170','1419651249651220482','1391740932568969217','1417478560375365633','关于《花间集》说法错误的是','关于《花间集》说法错误的是',0,0,1,0,'花间集题目的答案解析',0,'2021-07-29 11:57:43','2021-07-29 11:57:43'),('1420715156687982594','0','1419651249651220482','1391740932568969217',NULL,'子文件夹','子文件夹',1,NULL,NULL,1,NULL,0,'2021-07-29 11:57:44','2021-07-29 11:57:44'),('1420715157002555394','1420715156687982594','1419651249651220482','1391740932568969217',NULL,'子文件夹','子文件夹',1,NULL,NULL,1,NULL,0,'2021-07-29 11:57:44','2021-07-29 11:57:44'),('1420715157132578817','1420715157002555394','1419651249651220482','1391740932568969217','1417478609448722434','关于苏东坡《八声甘州“寄参寥子”》说法正确的是？','关于苏东坡《八声甘州“寄参寥子”》说法正确的是？',0,0,2,0,NULL,0,'2021-07-29 11:57:44','2021-07-29 11:57:44'),('1420715158231486466','1420715157002555394','1419651249651220482','1391740932568969217','1417478698783203329','中国诗经的写作方式不包括','中国诗经的写作方式不包括',0,0,1,0,NULL,0,'2021-07-29 11:57:44','2021-07-29 11:57:44'),('1420715158818689026','0','1419651249651220482','1391740932568969217','1417478737215610881','多音节是中国语言文化的特性','多音节是中国语言文化的特性',0,0,0,0,NULL,0,'2021-07-29 11:57:44','2021-07-29 11:57:44'),('1420715159145844738','0','1419651249651220482','1391740932568969217','1417478770765848578','试简述三峡工程的导流方案，并给出示意图。','试简述三峡工程的导流方案，并给出示意图。',0,0,2,0,NULL,0,'2021-07-29 11:57:44','2021-07-29 11:57:44');

/*Table structure for table `mse_question_answer` */

DROP TABLE IF EXISTS `mse_question_answer`;

CREATE TABLE `mse_question_answer` (
  `id` char(19) NOT NULL DEFAULT '' COMMENT '题目答案Id',
  `question_id` char(19) NOT NULL COMMENT '题目Id',
  `option_id` char(19) DEFAULT NULL COMMENT '题目答案对应的选项的Id',
  `value` text COMMENT '题目答案内容（当题目没有选项时）',
  `gmt_create` datetime DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='题目答案表';

/*Data for the table `mse_question_answer` */

insert  into `mse_question_answer`(`id`,`question_id`,`option_id`,`value`,`gmt_create`,`gmt_modified`) values ('1420715156302106625','1420715154230120450','1420715155073175554',NULL,'2021-07-29 11:57:44','2021-07-29 11:57:44'),('1420715157778501633','1420715157132578817','1420715157518454786',NULL,'2021-07-29 11:57:44','2021-07-29 11:57:44'),('1420715158034354178','1420715157132578817','1420715157585563650',NULL,'2021-07-29 11:57:44','2021-07-29 11:57:44'),('1420715158097268737','1420715157132578817','1420715157648478210',NULL,'2021-07-29 11:57:44','2021-07-29 11:57:44'),('1420715158562836482','1420715158231486466',NULL,'赋','2021-07-29 11:57:44','2021-07-29 11:57:44'),('1420715158625751041','1420715158231486466',NULL,'比','2021-07-29 11:57:44','2021-07-29 11:57:44'),('1420715158755774465','1420715158231486466',NULL,'兴','2021-07-29 11:57:44','2021-07-29 11:57:44'),('1420715158881603586','1420715158818689026',NULL,'0','2021-07-29 11:57:44','2021-07-29 11:57:44'),('1420715159208759297','1420715159145844738',NULL,'第一阶段：施工准备及一期工程。利用中堡岛修建一期土石围堰围护右岸叉河。一期基坑内修建导流明渠和混凝土纵向围堰。同时，在左岸岸坡修建临时船闸。江水及船舶仍从主河槽通过（3分）。第二阶段：二期工程。修建二期上下游横向围堰，与混凝土纵向围堰形成二期基坑。进行河床泄洪坝段、左岸电站坝段和左岸电站的建设。同时，在左岸修建永久通航建筑物。二期导流期间，江水经导流明渠下泄，船舶经导流明渠或临时船闸通行（3分）。第三阶段：三期工程。修建三期碾压混凝土围堰，拦断导流明渠。水库蓄水至135米高程。左岸电站及永久船闸开始投入运行。三期围堰与混凝土纵向围堰形成三期基坑，基坑内修建右岸大坝和电站。三期导流期间，江水经由泄洪坝段的永久深孔和22个临时导流底孔下泄，船舶经永久船闸通行（3分）。示意图略（3分）','2021-07-29 11:57:44','2021-07-29 11:57:44');

/*Table structure for table `mse_question_option` */

DROP TABLE IF EXISTS `mse_question_option`;

CREATE TABLE `mse_question_option` (
  `id` char(19) NOT NULL DEFAULT '' COMMENT '题目标签Id',
  `question_id` char(19) NOT NULL COMMENT '题目Id',
  `name` varchar(25) NOT NULL DEFAULT '' COMMENT '选项标签（A、B、C、D等等）',
  `value` text NOT NULL COMMENT '选项内容',
  `gmt_create` datetime DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='题目选项表';

/*Data for the table `mse_question_option` */

insert  into `mse_question_option`(`id`,`question_id`,`name`,`value`,`gmt_create`,`gmt_modified`) values ('1420715154947346433','1420715154230120450','A','作者是赵崇祚','2021-07-29 11:57:43','2021-07-29 11:57:43'),('1420715155010260994','1420715154230120450','B','收录当时流行歌曲歌词','2021-07-29 11:57:43','2021-07-29 11:57:43'),('1420715155073175554','1420715154230120450','C','针砭时弊','2021-07-29 11:57:43','2021-07-29 11:57:43'),('1420715155584880641','1420715154230120450','D','内容是美女与爱情','2021-07-29 11:57:43','2021-07-29 11:57:43'),('1420715157191299073','1420715157132578817','A','儿女的柔情','2021-07-29 11:57:44','2021-07-29 11:57:44'),('1420715157518454786','1420715157132578817','B','长调','2021-07-29 11:57:44','2021-07-29 11:57:44'),('1420715157585563650','1420715157132578817','C','开阔博大','2021-07-29 11:57:44','2021-07-29 11:57:44'),('1420715157648478210','1420715157132578817','D','既有诗的美感，也有词的美感','2021-07-29 11:57:44','2021-07-29 11:57:44');

/*Table structure for table `mse_question_topic` */

DROP TABLE IF EXISTS `mse_question_topic`;

CREATE TABLE `mse_question_topic` (
  `id` char(19) NOT NULL DEFAULT '' COMMENT '题目知识点关联Id',
  `question_id` char(19) NOT NULL COMMENT '题目Id',
  `topic_id` char(19) NOT NULL COMMENT '知识点Id',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='题目知识点关联表';

/*Data for the table `mse_question_topic` */

/*Table structure for table `mse_question_type` */

DROP TABLE IF EXISTS `mse_question_type`;

CREATE TABLE `mse_question_type` (
  `id` char(19) NOT NULL DEFAULT '' COMMENT '题型Id',
  `name` varchar(20) NOT NULL COMMENT '题型名称',
  `type` tinyint(10) NOT NULL COMMENT '题型类型',
  `is_customize` tinyint(1) NOT NULL DEFAULT '1' COMMENT '是否是用户自定义题型（1-是，0-不是）',
  `is_deleted` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '逻辑删除 1（true）已删除， 0（false）未删除',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='题目类型（题型）表';

/*Data for the table `mse_question_type` */

insert  into `mse_question_type`(`id`,`name`,`type`,`is_customize`,`is_deleted`,`gmt_create`,`gmt_modified`) values ('1417478560375365633','单选题',0,0,0,'2021-07-20 13:36:39','2021-07-20 13:36:39'),('1417478609448722434','多选题',1,0,0,'2021-07-20 13:36:51','2021-07-20 13:36:51'),('1417478698783203329','填空题',2,0,0,'2021-07-20 13:37:12','2021-07-20 13:37:12'),('1417478737215610881','判断题',3,0,0,'2021-07-20 13:37:21','2021-07-20 13:37:21'),('1417478770765848578','简答题',4,0,0,'2021-07-20 13:37:29','2021-07-20 13:37:29'),('1418554870136725505','测试题型1',0,1,0,'2021-07-23 12:53:32','2021-07-23 12:53:32'),('1418555366280945666','测试题型2',1,1,1,'2021-07-23 12:55:30','2021-07-23 12:55:30'),('1418556886904889346','测试题型2',4,1,0,'2021-07-23 13:01:32','2021-07-23 13:01:32');

/*Table structure for table `mse_student` */

DROP TABLE IF EXISTS `mse_student`;

CREATE TABLE `mse_student` (
  `id` char(19) NOT NULL COMMENT '学生Id',
  `academy_id` varchar(19) NOT NULL COMMENT '院系Id',
  `profession_id` varchar(19) NOT NULL COMMENT '专业Id',
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

/*Data for the table `mse_student` */

insert  into `mse_student`(`id`,`academy_id`,`profession_id`,`username`,`password`,`nick_name`,`real_name`,`sno`,`age`,`sex`,`birth_day`,`phone`,`email`,`status`,`avatar`,`wx_open_id`,`is_deleted`,`gmt_create`,`gmt_modified`) values ('1415279829995380737','1413060171875524610','1414567755866578945','lwjppz','123456',NULL,'罗炜杰','20180861215',20,1,NULL,'13850801092','lwjppz@gmail.com',1,NULL,NULL,0,'2021-07-14 11:59:41','2021-07-14 11:59:41'),('1415295010376908802','1413060171875524610','1414567755866578945','zeus','123456',NULL,'周寒莉','20180861214',20,2,NULL,'18750836893','1731667524@qq.com',1,NULL,NULL,0,'2021-07-14 13:00:00','2021-07-16 11:28:41'),('1418215817088520193','1413060171875524610','1414567755866578945','student01','123456',NULL,'student01','20180861200',20,1,NULL,'13850801000','13850801000@163.com',1,NULL,NULL,0,'2021-07-22 14:26:15','2021-07-22 14:26:15'),('1418216047817183233','1413060171875524610','1414567755866578945','student02','123456',NULL,'student01','201808612100',20,2,NULL,'13850801099','13850801099@163.com',1,NULL,NULL,0,'2021-07-22 14:27:10','2021-07-22 14:27:10');

/*Table structure for table `mse_topic` */

DROP TABLE IF EXISTS `mse_topic`;

CREATE TABLE `mse_topic` (
  `id` char(19) NOT NULL DEFAULT '' COMMENT '知识点Id',
  `name` varchar(20) NOT NULL COMMENT '知识点名称',
  `is_deleted` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '逻辑删除 1（true）已删除， 0（false）未删除',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='知识点表';

/*Data for the table `mse_topic` */

insert  into `mse_topic`(`id`,`name`,`is_deleted`,`gmt_create`,`gmt_modified`) values ('1418926047741034498','酒馆升级',1,'2021-07-24 13:28:27','2021-07-24 14:02:51'),('1418933522947063809','酒馆铸币',1,'2021-07-24 13:58:09','2021-07-24 13:58:09'),('1419119668297719810','酒馆升级',1,'2021-07-25 02:17:50','2021-07-25 02:17:50'),('1419119721401802753','酒馆随从',1,'2021-07-25 02:18:02','2021-07-25 02:18:02'),('1419653949617065985','位运算',0,'2021-07-26 13:40:52','2021-07-26 13:40:52'),('1419653977223974913','指针与数组',0,'2021-07-26 13:40:59','2021-07-26 13:40:59'),('1419654006772846594','宏和预定义',0,'2021-07-26 13:41:06','2021-07-26 13:41:06'),('1419654039010267138','switch语句',0,'2021-07-26 13:41:13','2021-07-26 13:41:13'),('1419654065077866498','字符串和字符数组',0,'2021-07-26 13:41:20','2021-07-26 13:41:20'),('1419654099257249793','枚举类型',0,'2021-07-26 13:41:28','2021-07-26 13:41:28'),('1419654123529687042','结构体',0,'2021-07-26 13:41:34','2021-07-26 13:41:34'),('1419654153208582146','指针',0,'2021-07-26 13:41:41','2021-07-26 13:41:41'),('1419654174880550914','递归函数',0,'2021-07-26 13:41:46','2021-07-26 13:41:46'),('1419654202764283906','二维数组',0,'2021-07-26 13:41:53','2021-07-26 13:41:53'),('1419654226478878722','一维数组',0,'2021-07-26 13:41:58','2021-07-26 13:41:58'),('1419654248759021569','循环语句',0,'2021-07-26 13:42:03','2021-07-26 13:42:03'),('1419654268132511745','if语句',0,'2021-07-26 13:42:08','2021-07-26 13:42:08'),('1419654297459085314','变量和常量',0,'2021-07-26 13:42:15','2021-07-26 13:42:15'),('1419654321987375105','标识符',0,'2021-07-26 13:42:21','2021-07-26 13:42:21'),('1419654341092429826','文件',0,'2021-07-26 13:42:25','2021-07-26 13:42:25'),('1419654364064632834','流程图',0,'2021-07-26 13:42:31','2021-07-26 13:42:31'),('1419654386290249730','算法',0,'2021-07-26 13:42:36','2021-07-26 13:42:36'),('1419654407161106434','函数',0,'2021-07-26 13:42:41','2021-07-26 13:42:41'),('1419654427407011842','顺序结构',0,'2021-07-26 13:42:46','2021-07-26 13:42:46'),('1419654451658477570','数据的输入',0,'2021-07-26 13:42:52','2021-07-26 13:42:52'),('1419654470230855682','输出格式',0,'2021-07-26 13:42:56','2021-07-26 13:42:56'),('1419654490833276930','运算符和表达式',0,'2021-07-26 13:43:01','2021-07-26 13:43:01'),('1419654508587765761','数据类型',0,'2021-07-26 13:43:05','2021-07-26 13:43:05'),('1419654528380686337','C程序基本知识',0,'2021-07-26 13:43:10','2021-07-26 13:43:10');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
