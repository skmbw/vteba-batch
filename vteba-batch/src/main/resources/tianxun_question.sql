/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50711
Source Host           : localhost:3306
Source Database       : tianxun_question

Target Server Type    : MYSQL
Target Server Version : 50711
File Encoding         : 65001

Date: 2016-03-21 09:09:31
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for answer
-- ----------------------------
DROP TABLE IF EXISTS `answer`;
CREATE TABLE `answer` (
  `id` varchar(50) NOT NULL,
  `content` varchar(10240) NOT NULL COMMENT '回答的内容',
  `answer_user_id` varchar(50) NOT NULL COMMENT '回答问题的用户id',
  `answer_date` datetime NOT NULL COMMENT '回答时间',
  `question_id` varchar(50) NOT NULL COMMENT '对应的问题id',
  `orders` int(10) NOT NULL COMMENT '回答问题的顺序',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='答案表';

-- ----------------------------
-- Table structure for answer_image
-- ----------------------------
DROP TABLE IF EXISTS `answer_image`;
CREATE TABLE `answer_image` (
  `id` varchar(50) NOT NULL,
  `answer_id` varchar(50) NOT NULL,
  `image_path` varchar(250) NOT NULL COMMENT '图片路径',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='答案图片附件表';

-- ----------------------------
-- Table structure for category
-- ----------------------------
DROP TABLE IF EXISTS `category`;
CREATE TABLE `category` (
  `code` int(10) NOT NULL COMMENT '分类代码',
  `name` varchar(50) NOT NULL COMMENT '分类名',
  `state` int(10) NOT NULL COMMENT '分类状态',
  `level` int(10) NOT NULL COMMENT '分类层级',
  `parent` int(10) NOT NULL COMMENT '父级代码',
  PRIMARY KEY (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='问题分类表';

-- ----------------------------
-- Table structure for question
-- ----------------------------
DROP TABLE IF EXISTS `question`;
CREATE TABLE `question` (
  `id` varchar(50) NOT NULL,
  `content` varchar(10240) DEFAULT NULL COMMENT '提问内容',
  `asker` varchar(50) NOT NULL COMMENT '提问者id',
  `asker_name` varchar(100) DEFAULT NULL COMMENT '提问者姓名，没有显示昵称',
  `ask_date` datetime DEFAULT NULL COMMENT '提问时间',
  `answer_date` datetime DEFAULT NULL COMMENT '回答时间',
  `close_date` datetime DEFAULT NULL COMMENT '问题关闭时间',
  `category` int(10) DEFAULT NULL COMMENT '问题分类代码',
  `category_name` varchar(50) DEFAULT NULL COMMENT '问题分类名称',
  `satisfied_answer_id` varchar(50) DEFAULT NULL COMMENT '满意的回答id',
  `satisfied_user_id` varchar(50) DEFAULT NULL COMMENT '满意的回答者',
  `satisfied_user_name` varchar(100) DEFAULT NULL COMMENT '满意的回答者的用户名',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='问题表';

-- ----------------------------
-- Table structure for question_image
-- ----------------------------
DROP TABLE IF EXISTS `question_image`;
CREATE TABLE `question_image` (
  `id` varchar(50) NOT NULL,
  `question_id` varchar(50) NOT NULL,
  `image_path` varchar(250) NOT NULL COMMENT '图片路径',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='问题图片附件表';
