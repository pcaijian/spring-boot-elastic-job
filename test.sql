/*
Navicat MySQL Data Transfer

Source Server         : 10.0.0.5
Source Server Version : 50720
Source Host           : 10.0.0.5:3306
Source Database       : test

Target Server Type    : MYSQL
Target Server Version : 50720
File Encoding         : 65001

Date: 2017-12-26 14:19:41
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `t1`
-- ----------------------------
DROP TABLE IF EXISTS `t1`;
CREATE TABLE `t1` (
  `myDatatime` datetime DEFAULT NULL COMMENT '时间',
  `name` varchar(64) DEFAULT NULL COMMENT '名称',
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='表名';

-- ----------------------------
-- Records of t1
-- ----------------------------
INSERT INTO `t1` VALUES ('0000-00-00 00:00:00', 'a', '1');

-- ----------------------------
-- Table structure for `test`
-- ----------------------------
DROP TABLE IF EXISTS `test`;
CREATE TABLE `test` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `number` int(11) DEFAULT NULL,
  `host` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of test
-- ----------------------------
INSERT INTO `test` VALUES ('1', '140', 'Windows 10/YSD003');
INSERT INTO `test` VALUES ('2', '139', 'Windows 10/YSD003');
INSERT INTO `test` VALUES ('3', '139', 'Windows 10/YSD003');
INSERT INTO `test` VALUES ('4', '140', 'Windows 10/YSD003');
INSERT INTO `test` VALUES ('5', '139', 'Windows 10/YSD003');
INSERT INTO `test` VALUES ('6', '139', 'Windows 10/YSD003');
INSERT INTO `test` VALUES ('7', '140', 'Windows 10/YSD003');
INSERT INTO `test` VALUES ('8', '139', 'Windows 10/YSD003');

-- ----------------------------
-- Table structure for `t_location`
-- ----------------------------
DROP TABLE IF EXISTS `t_location`;
CREATE TABLE `t_location` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `Name` varchar(100) NOT NULL COMMENT '名字',
  `Location` geometry DEFAULT NULL COMMENT '坐标',
  `Longitude` float NOT NULL,
  `Latitude` float NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='测试一下坐标';

-- ----------------------------
-- Records of t_location
-- ----------------------------

-- ----------------------------
-- Table structure for `t_order`
-- ----------------------------
DROP TABLE IF EXISTS `t_order`;
CREATE TABLE `t_order` (
  `OrderId` int(11) NOT NULL,
  `OrderMoney` float DEFAULT NULL,
  PRIMARY KEY (`OrderId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_order
-- ----------------------------

-- ----------------------------
-- Table structure for `t_user`
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
  `UserId` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户编号',
  `UserName` varchar(64) DEFAULT NULL COMMENT '用户名称',
  PRIMARY KEY (`UserId`),
  UNIQUE KEY `NameWeiYi` (`UserName`) USING HASH
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='用户表';

-- ----------------------------
-- Records of t_user
-- ----------------------------
INSERT INTO `t_user` VALUES ('2', 'adds0133');
INSERT INTO `t_user` VALUES ('1', 'adds0243');

-- ----------------------------
-- Table structure for `t_userinfo`
-- ----------------------------
DROP TABLE IF EXISTS `t_userinfo`;
CREATE TABLE `t_userinfo` (
  `Id` int(11) NOT NULL,
  `Name` varchar(128) DEFAULT NULL,
  `UserId` int(11) NOT NULL,
  PRIMARY KEY (`Id`),
  KEY `WJUserId` (`UserId`),
  CONSTRAINT `WJUserId` FOREIGN KEY (`UserId`) REFERENCES `t_user` (`UserId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_userinfo
-- ----------------------------

-- ----------------------------
-- Table structure for `t_user_order`
-- ----------------------------
DROP TABLE IF EXISTS `t_user_order`;
CREATE TABLE `t_user_order` (
  `MyId` int(11) NOT NULL,
  `MyName` varchar(128) DEFAULT NULL,
  `UserId` int(11) NOT NULL,
  `OrderId` int(11) DEFAULT NULL,
  PRIMARY KEY (`MyId`),
  KEY `InfoUserId` (`UserId`),
  KEY `WjOrderId` (`OrderId`),
  CONSTRAINT `InfoUserId` FOREIGN KEY (`UserId`) REFERENCES `t_user` (`UserId`),
  CONSTRAINT `WjOrderId` FOREIGN KEY (`OrderId`) REFERENCES `t_order` (`OrderId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_user_order
-- ----------------------------

-- ----------------------------
-- Procedure structure for `Proc_Test`
-- ----------------------------
DROP PROCEDURE IF EXISTS `Proc_Test`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `Proc_Test`(IN d DECIMAL(3,2))
BEGIN
  SELECT Name,
  ST_X(ST_PointFromText(ST_AsWKT(Location))) as Longitude,
  ST_Y(ST_PointFromText(ST_AsWKT(Location))) as Latitude,
  ST_Distance(POINT(26.074507,119.296493), Location) as Distance
FROM `t_location` HAVING Distance<d ORDER BY Distance;
END
;;
DELIMITER ;
DROP TRIGGER IF EXISTS `Trigger_Test`;
DELIMITER ;;
CREATE TRIGGER `Trigger_Test` BEFORE INSERT ON `t_location` FOR EACH ROW BEGIN
    SET NEW.Location = ST_GeomFromText(CONCAT('POINT(',NEW.Latitude,' ',NEW.Longitude,')'));
  END
;;
DELIMITER ;
