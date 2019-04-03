/*
 Navicat MySQL Data Transfer

 Source Server         : 本地连接
 Source Server Type    : MySQL
 Source Server Version : 50722
 Source Host           : localhost:3306
 Source Schema         : chatroom

 Target Server Type    : MySQL
 Target Server Version : 50722
 File Encoding         : 65001

 Date: 10/03/2019 15:26:29
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(30) NOT NULL,
  `password` char(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
BEGIN;
INSERT INTO `user` VALUES (1, 'lihaonan', '123456');
INSERT INTO `user` VALUES (2, 'tanghaocheng', '123456');
INSERT INTO `user` VALUES (3, 'songjiaqi', '123456');
INSERT INTO `user` VALUES (4, 'liuguanshan', '123456');
INSERT INTO `user` VALUES (5, 'yuanzhe', '123');
INSERT INTO `user` VALUES (6, 'liudingbang', '123456');
INSERT INTO `user` VALUES (7, 'lihaonan1', 'lihaonan');
INSERT INTO `user` VALUES (9, 'luoxinru', '123');
INSERT INTO `user` VALUES (10, 'lihaonan3', '123456');
INSERT INTO `user` VALUES (11, 'lihaonan4', '123456');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
