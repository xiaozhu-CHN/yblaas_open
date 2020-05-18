/*
Navicat MySQL Data Transfer

Source Server         : mysql5.7
Source Server Version : 50703
Source Host           : localhost:3306
Source Database       : yblaas_open

Target Server Type    : MYSQL
Target Server Version : 50703
File Encoding         : 65001

Date: 2020-05-18 14:23:30
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `attendance`
-- ----------------------------
DROP TABLE IF EXISTS `attendance`;
CREATE TABLE `attendance` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `teacher` varchar(20) NOT NULL COMMENT '发起人',
  `eclassid` int(11) NOT NULL COMMENT '考勤班级',
  `timestart` datetime NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '开始时间',
  `timeend` datetime NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '结束时间',
  `timechange` datetime NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '发起时间',
  `name` varchar(50) NOT NULL COMMENT '考勤名称',
  `longitude` varchar(15) NOT NULL COMMENT '经度',
  `latitude` varchar(15) NOT NULL COMMENT '纬度',
  `beiz` varchar(250) NOT NULL COMMENT '考勤备注',
  `state` varchar(1) NOT NULL COMMENT '考勤标记',
  PRIMARY KEY (`id`),
  KEY `teacher` (`teacher`),
  KEY `eclassid` (`eclassid`),
  CONSTRAINT `attendance_ibfk_1` FOREIGN KEY (`teacher`) REFERENCES `teacher` (`teacher`) ON UPDATE CASCADE,
  CONSTRAINT `attendance_ibfk_2` FOREIGN KEY (`eclassid`) REFERENCES `eclass` (`eclassid`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of attendance
-- ----------------------------

-- ----------------------------
-- Table structure for `attendance_info`
-- ----------------------------
DROP TABLE IF EXISTS `attendance_info`;
CREATE TABLE `attendance_info` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '考勤表ID',
  `student` varchar(20) NOT NULL COMMENT '考勤人',
  `time` datetime NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '考勤时间',
  `longitude` varchar(15) DEFAULT NULL COMMENT '经度',
  `latitude` varchar(15) DEFAULT NULL COMMENT '纬度',
  `mac` varchar(50) DEFAULT NULL COMMENT 'Mac地址',
  `type` varchar(1) DEFAULT NULL COMMENT '考勤类型',
  PRIMARY KEY (`id`,`student`),
  KEY `student` (`student`),
  CONSTRAINT `attendance_info_ibfk_1` FOREIGN KEY (`id`) REFERENCES `attendance` (`id`) ON UPDATE CASCADE,
  CONSTRAINT `attendance_info_ibfk_2` FOREIGN KEY (`student`) REFERENCES `student` (`student`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of attendance_info
-- ----------------------------

-- ----------------------------
-- Table structure for `college`
-- ----------------------------
DROP TABLE IF EXISTS `college`;
CREATE TABLE `college` (
  `collegeid` int(11) NOT NULL AUTO_INCREMENT COMMENT '学院ID',
  `name` varchar(20) NOT NULL COMMENT '学院名称',
  PRIMARY KEY (`collegeid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of college
-- ----------------------------

-- ----------------------------
-- Table structure for `dbconfig`
-- ----------------------------
DROP TABLE IF EXISTS `dbconfig`;
CREATE TABLE `dbconfig` (
  `ckey` varchar(50) NOT NULL,
  `cvalue` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`ckey`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dbconfig
-- ----------------------------
INSERT INTO `dbconfig` VALUES ('admin_password', 'f6fdffe48c908deb0f4c3bd36c032e72');
INSERT INTO `dbconfig` VALUES ('admin_user', 'admin');
INSERT INTO `dbconfig` VALUES ('attendance_accuracy', '25');
INSERT INTO `dbconfig` VALUES ('email', 'false');
INSERT INTO `dbconfig` VALUES ('email_call', null);
INSERT INTO `dbconfig` VALUES ('email_host', null);
INSERT INTO `dbconfig` VALUES ('email_name', null);
INSERT INTO `dbconfig` VALUES ('email_password', null);
INSERT INTO `dbconfig` VALUES ('email_port', null);
INSERT INTO `dbconfig` VALUES ('leave_xgc', '7');
INSERT INTO `dbconfig` VALUES ('leave_xyld', '3');
INSERT INTO `dbconfig` VALUES ('yblaas_ba', null);
INSERT INTO `dbconfig` VALUES ('yblaas_copyright', '2020 XXX学院/大学');
INSERT INTO `dbconfig` VALUES ('yblaas_title', '易班请假与考勤系统');
INSERT INTO `dbconfig` VALUES ('yiban_appId', null);
INSERT INTO `dbconfig` VALUES ('yiban_appsecret', null);
INSERT INTO `dbconfig` VALUES ('yiban_school', 'false');
INSERT INTO `dbconfig` VALUES ('yiban_this', null);
INSERT INTO `dbconfig` VALUES ('yiban_url', null);

-- ----------------------------
-- Table structure for `eclass`
-- ----------------------------
DROP TABLE IF EXISTS `eclass`;
CREATE TABLE `eclass` (
  `eclassid` int(11) NOT NULL AUTO_INCREMENT COMMENT '班级ID',
  `collegeid` int(11) DEFAULT NULL COMMENT '学院ID',
  `name` varchar(20) DEFAULT NULL COMMENT '班级名称',
  `teacher` varchar(20) DEFAULT NULL COMMENT '辅导员ID',
  PRIMARY KEY (`eclassid`),
  KEY `teacher` (`teacher`),
  KEY `collegeid` (`collegeid`),
  CONSTRAINT `eclass_ibfk_2` FOREIGN KEY (`teacher`) REFERENCES `teacher` (`teacher`) ON UPDATE CASCADE,
  CONSTRAINT `eclass_ibfk_3` FOREIGN KEY (`collegeid`) REFERENCES `college` (`collegeid`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of eclass
-- ----------------------------

-- ----------------------------
-- Table structure for `leave`
-- ----------------------------
DROP TABLE IF EXISTS `leave`;
CREATE TABLE `leave` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '假条ID',
  `student` varchar(20) NOT NULL COMMENT '用户ID',
  `timestart` date NOT NULL DEFAULT '0000-00-00' COMMENT '请假开始日期',
  `timeend` date NOT NULL DEFAULT '0000-00-00' COMMENT '请假结束日期',
  `timechange` datetime NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '假条申请时间',
  `day` int(4) NOT NULL COMMENT '请假天数',
  `whereabouts` varchar(250) NOT NULL COMMENT '请假去向',
  `cause` varchar(250) NOT NULL COMMENT '请假事由',
  `fdy` varchar(20) DEFAULT NULL COMMENT '辅导员id',
  `fdytime` datetime DEFAULT NULL COMMENT '辅导员审批时间',
  `xyld` varchar(20) DEFAULT NULL COMMENT '二级学院分管学生工作领导/学院主要负责人id',
  `xyldtime` datetime DEFAULT NULL COMMENT '二级学院分管学生工作领导/学院主要负责人审核时间',
  `xgc` varchar(20) DEFAULT NULL COMMENT '学工处审核id',
  `xgctime` datetime DEFAULT NULL COMMENT '学工处审核时间',
  `xj` varchar(20) DEFAULT NULL COMMENT '销假老师id',
  `xjtime` datetime DEFAULT NULL COMMENT '销假日期',
  `state` varchar(2) NOT NULL COMMENT '假条状态',
  PRIMARY KEY (`id`),
  KEY `student` (`student`),
  KEY `leave_ibfk_2` (`fdy`),
  KEY `fgxy` (`xyld`),
  KEY `xgc` (`xgc`),
  KEY `xj` (`xj`),
  CONSTRAINT `leave_ibfk_1` FOREIGN KEY (`student`) REFERENCES `student` (`student`) ON UPDATE CASCADE,
  CONSTRAINT `leave_ibfk_2` FOREIGN KEY (`fdy`) REFERENCES `teacher` (`teacher`) ON UPDATE CASCADE,
  CONSTRAINT `leave_ibfk_3` FOREIGN KEY (`xyld`) REFERENCES `teacher` (`teacher`) ON UPDATE CASCADE,
  CONSTRAINT `leave_ibfk_4` FOREIGN KEY (`xgc`) REFERENCES `teacher` (`teacher`) ON UPDATE CASCADE,
  CONSTRAINT `leave_ibfk_5` FOREIGN KEY (`xj`) REFERENCES `teacher` (`teacher`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of leave
-- ----------------------------

-- ----------------------------
-- Table structure for `permissions`
-- ----------------------------
DROP TABLE IF EXISTS `permissions`;
CREATE TABLE `permissions` (
  `id` int(5) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `rolename` varchar(20) NOT NULL COMMENT '角色名称',
  `permission` varchar(50) NOT NULL COMMENT '权限名称',
  PRIMARY KEY (`id`),
  KEY `rolename` (`rolename`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Records of permissions
-- ----------------------------
INSERT INTO `permissions` VALUES ('1', 'student', 'student:leave');
INSERT INTO `permissions` VALUES ('2', 'student', 'student:my');
INSERT INTO `permissions` VALUES ('3', 'student', 'student:attendance');

-- ----------------------------
-- Table structure for `roles`
-- ----------------------------
DROP TABLE IF EXISTS `roles`;
CREATE TABLE `roles` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `userid` varchar(20) NOT NULL COMMENT '易班ID',
  `rolename` varchar(20) NOT NULL COMMENT '角色名称',
  PRIMARY KEY (`id`),
  KEY `rolename` (`rolename`) USING BTREE,
  KEY `userid` (`userid`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Records of roles
-- ----------------------------
INSERT INTO `roles` VALUES ('1', '00000001', 'admin');

-- ----------------------------
-- Table structure for `student`
-- ----------------------------
DROP TABLE IF EXISTS `student`;
CREATE TABLE `student` (
  `student` varchar(20) NOT NULL COMMENT '学生ID',
  `numberid` varchar(20) DEFAULT NULL COMMENT '学号',
  `eclassid` int(11) DEFAULT NULL COMMENT '班级ID',
  `studentemail` varchar(100) DEFAULT NULL COMMENT '学生的邮箱',
  `name` varchar(10) DEFAULT NULL COMMENT '学生的名字',
  `studenttell` varchar(11) DEFAULT NULL COMMENT '学生联系电话',
  `parenttell` varchar(11) DEFAULT NULL COMMENT '学术家长联系电话',
  `parenname` varchar(10) DEFAULT NULL COMMENT '学生家长姓名',
  `address` varchar(250) DEFAULT NULL COMMENT '详细地址',
  `ems` varchar(10) DEFAULT NULL COMMENT '邮编',
  `sex` varchar(1) DEFAULT NULL COMMENT '学生性别',
  `studentqq` varchar(15) DEFAULT NULL COMMENT '学生QQ',
  `examine` varchar(1) DEFAULT NULL COMMENT '学生信息审核是否通过',
  `city` varchar(50) DEFAULT NULL COMMENT '地址区域',
  PRIMARY KEY (`student`),
  KEY `eclassid` (`eclassid`),
  CONSTRAINT `student_ibfk_1` FOREIGN KEY (`eclassid`) REFERENCES `eclass` (`eclassid`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of student
-- ----------------------------

-- ----------------------------
-- Table structure for `teacher`
-- ----------------------------
DROP TABLE IF EXISTS `teacher`;
CREATE TABLE `teacher` (
  `teacher` varchar(20) NOT NULL COMMENT '老师ID',
  `name` varchar(10) DEFAULT NULL COMMENT '老师的名字',
  `teachertell` varchar(11) DEFAULT NULL COMMENT '老师的电话',
  `teacheremail` varchar(100) DEFAULT NULL COMMENT '老师的邮箱',
  `collegeid` int(11) DEFAULT NULL COMMENT '学院ID',
  `teacherqq` varchar(15) DEFAULT NULL COMMENT '老师的QQ',
  PRIMARY KEY (`teacher`),
  KEY `collegeid` (`collegeid`),
  CONSTRAINT `teacher_ibfk_1` FOREIGN KEY (`collegeid`) REFERENCES `college` (`collegeid`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of teacher
-- ----------------------------

-- ----------------------------
-- Table structure for `verification`
-- ----------------------------
DROP TABLE IF EXISTS `verification`;
CREATE TABLE `verification` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `userid` varchar(20) NOT NULL COMMENT '用户id',
  `verification` varchar(4) NOT NULL COMMENT '验证码',
  `time` datetime NOT NULL COMMENT '验证码时间',
  `type` varchar(10) NOT NULL COMMENT '验证码类型',
  `field` varchar(50) NOT NULL COMMENT '验证的字段',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of verification
-- ----------------------------
