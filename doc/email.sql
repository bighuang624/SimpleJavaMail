/*
 Navicat Premium Data Transfer

 Source Server         : loginDemo
 Source Server Type    : MySQL
 Source Server Version : 50505
 Source Host           : localhost
 Source Database       : Email

 Target Server Type    : MySQL
 Target Server Version : 50505
 File Encoding         : utf-8

 Date: 10/29/2017 20:05:44 PM
*/

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `email`
-- ----------------------------
DROP TABLE IF EXISTS `email`;
CREATE TABLE `email` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `address` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `email`
-- ----------------------------
BEGIN;
INSERT INTO `email` VALUES ('1', 'xx@qq.com'), ('2', 'yy@qq.com'), ('3', 'zz@126.com');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
