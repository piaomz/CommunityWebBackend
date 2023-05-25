/*
 Navicat MySQL Data Transfer

 Source Server         : mysqllocal
 Source Server Type    : MySQL
 Source Server Version : 100421
 Source Host           : localhost:3306
 Source Schema         : dev

 Target Server Type    : MySQL
 Target Server Version : 100421
 File Encoding         : 65001

 Date: 23/05/2023 01:54:53
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for HistoryArticle
-- ----------------------------
DROP TABLE IF EXISTS `HistoryArticle`;
CREATE TABLE `HistoryArticle` (
  `hisId` int(11) NOT NULL AUTO_INCREMENT,
  `uid` int(11) NOT NULL,
  `articleid` int(11) NOT NULL,
  `time` datetime NOT NULL,
  PRIMARY KEY (`hisId`),
  KEY `uid` (`uid`),
  KEY `articleid` (`articleid`),
  CONSTRAINT `historyarticle_ibfk_1` FOREIGN KEY (`uid`) REFERENCES `user` (`uid`) ON DELETE CASCADE,
  CONSTRAINT `historyarticle_ibfk_2` FOREIGN KEY (`articleid`) REFERENCES `article` (`articleid`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for article
-- ----------------------------
DROP TABLE IF EXISTS `article`;
CREATE TABLE `article` (
  `articleid` int(11) NOT NULL AUTO_INCREMENT,
  `uid` int(11) NOT NULL,
  `lastmod` datetime DEFAULT NULL,
  `createtime` datetime DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `content` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `imageurl` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`articleid`),
  KEY `uid` (`uid`),
  CONSTRAINT `article_ibfk_1` FOREIGN KEY (`uid`) REFERENCES `user` (`uid`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of article
-- ----------------------------
BEGIN;
INSERT INTO `article` VALUES (2, 1, '2023-05-15 02:30:49', '2023-05-15 02:16:32', 'thisistitle', 'thisiscontent', 'thisistype2', NULL);
INSERT INTO `article` VALUES (3, 48, '2023-05-21 17:58:41', '2023-05-21 17:58:41', '', '', '', '');
INSERT INTO `article` VALUES (4, 48, '2023-05-21 18:49:12', '2023-05-21 17:59:50', '123', '', '', '');
COMMIT;

-- ----------------------------
-- Table structure for comment
-- ----------------------------
DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment` (
  `commentid` int(11) NOT NULL AUTO_INCREMENT,
  `articleid` int(11) NOT NULL,
  `uid` int(11) NOT NULL,
  `content` varchar(255) NOT NULL,
  `time` datetime NOT NULL,
  PRIMARY KEY (`commentid`),
  KEY `articleid` (`articleid`),
  KEY `uid` (`uid`),
  CONSTRAINT `articleid` FOREIGN KEY (`articleid`) REFERENCES `article` (`articleid`) ON DELETE CASCADE,
  CONSTRAINT `uid` FOREIGN KEY (`uid`) REFERENCES `user` (`uid`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of comment
-- ----------------------------
BEGIN;
INSERT INTO `comment` VALUES (1, 3, 1, '123123', '2023-05-22 01:14:51');
INSERT INTO `comment` VALUES (7, 4, 1, '123123', '2023-05-22 01:54:30');
INSERT INTO `comment` VALUES (8, 4, 1, '123123', '2023-05-22 01:54:33');
INSERT INTO `comment` VALUES (9, 4, 1, '123123', '2023-05-22 01:54:34');
COMMIT;

-- ----------------------------
-- Table structure for commentFavorite
-- ----------------------------
DROP TABLE IF EXISTS `commentFavorite`;
CREATE TABLE `commentFavorite` (
  `commentid` int(11) NOT NULL,
  `uid` int(11) NOT NULL,
  `time` datetime NOT NULL,
  PRIMARY KEY (`commentid`,`uid`),
  KEY `uid` (`uid`),
  CONSTRAINT `commentfavorite_ibfk_1` FOREIGN KEY (`commentid`) REFERENCES `comment` (`commentid`) ON DELETE CASCADE,
  CONSTRAINT `commentfavorite_ibfk_2` FOREIGN KEY (`uid`) REFERENCES `user` (`uid`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of commentFavorite
-- ----------------------------
BEGIN;
INSERT INTO `commentFavorite` VALUES (1, 1, '2023-05-22 22:54:56');
INSERT INTO `commentFavorite` VALUES (1, 48, '2023-05-22 22:52:12');
INSERT INTO `commentFavorite` VALUES (7, 48, '2023-05-22 22:53:49');
INSERT INTO `commentFavorite` VALUES (8, 48, '2023-05-22 22:53:53');
COMMIT;

-- ----------------------------
-- Table structure for favorite
-- ----------------------------
DROP TABLE IF EXISTS `favorite`;
CREATE TABLE `favorite` (
  `uid` int(11) NOT NULL,
  `articleid` int(11) NOT NULL,
  `time` datetime NOT NULL,
  `collection` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`uid`,`articleid`) USING BTREE,
  KEY `uid` (`uid`),
  KEY `aid` (`articleid`),
  CONSTRAINT `favorite_ibfk_1` FOREIGN KEY (`articleid`) REFERENCES `article` (`articleid`) ON DELETE CASCADE,
  CONSTRAINT `favorite_ibfk_2` FOREIGN KEY (`uid`) REFERENCES `user` (`uid`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of favorite
-- ----------------------------
BEGIN;
INSERT INTO `favorite` VALUES (1, 2, '2023-05-21 21:54:26', NULL);
INSERT INTO `favorite` VALUES (1, 4, '2023-05-21 21:52:41', NULL);
COMMIT;

-- ----------------------------
-- Table structure for loginHis
-- ----------------------------
DROP TABLE IF EXISTS `loginHis`;
CREATE TABLE `loginHis` (
  `loginHistoryID` int(11) NOT NULL AUTO_INCREMENT,
  `uid` int(11) NOT NULL,
  `time` datetime NOT NULL,
  PRIMARY KEY (`loginHistoryID`),
  KEY `uid` (`uid`),
  CONSTRAINT `loginhis_ibfk_1` FOREIGN KEY (`uid`) REFERENCES `user` (`uid`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of loginHis
-- ----------------------------
BEGIN;
INSERT INTO `loginHis` VALUES (1, 1, '2023-05-21 21:10:16');
INSERT INTO `loginHis` VALUES (2, 1, '2023-05-22 01:11:47');
INSERT INTO `loginHis` VALUES (3, 1, '2023-05-22 02:39:22');
INSERT INTO `loginHis` VALUES (4, 48, '2023-05-22 03:04:56');
INSERT INTO `loginHis` VALUES (5, 1, '2023-05-22 21:59:34');
INSERT INTO `loginHis` VALUES (6, 1, '2023-05-22 22:49:32');
INSERT INTO `loginHis` VALUES (7, 48, '2023-05-22 22:49:42');
INSERT INTO `loginHis` VALUES (8, 1, '2023-05-22 22:54:24');
INSERT INTO `loginHis` VALUES (9, 1, '2023-05-23 01:11:59');
COMMIT;

-- ----------------------------
-- Table structure for subscribe
-- ----------------------------
DROP TABLE IF EXISTS `subscribe`;
CREATE TABLE `subscribe` (
  `uid` int(11) NOT NULL,
  `subscribeUid` int(11) NOT NULL,
  `time` datetime DEFAULT NULL,
  PRIMARY KEY (`uid`,`subscribeUid`),
  KEY `subscribeUid` (`subscribeUid`),
  CONSTRAINT `subscribe_ibfk_1` FOREIGN KEY (`uid`) REFERENCES `user` (`uid`) ON DELETE CASCADE,
  CONSTRAINT `subscribe_ibfk_2` FOREIGN KEY (`subscribeUid`) REFERENCES `user` (`uid`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of subscribe
-- ----------------------------
BEGIN;
INSERT INTO `subscribe` VALUES (1, 48, '2023-05-23 01:15:11');
COMMIT;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `uid` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `nickname` varchar(255) DEFAULT NULL,
  `avatarurl` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`uid`) USING BTREE,
  UNIQUE KEY `unique_username` (`username`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=49 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of user
-- ----------------------------
BEGIN;
INSERT INTO `user` VALUES (1, 'user1', '$2a$10$tsRxpmhinkYbQPteTmU2oOnr8HsidvNCcJoO9tfpE4x27uPs0E/tq', 'admin_1', 'url', '000000', 'admin');
INSERT INTO `user` VALUES (24, 'user2', '$2a$10$tsRxpmhinkYbQPteTmU2oOnr8HsidvNCcJoO9tfpE4x27uPs0E/tq', 'forTest_user_1', NULL, NULL, 'user');
INSERT INTO `user` VALUES (48, 'user', '$2a$10$uBwllKmkHSVifpRud0UsN.SDKn90DLwlMJhxeOVlytS32giEVdfBu', 'test_user_2', '', '', 'test');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
