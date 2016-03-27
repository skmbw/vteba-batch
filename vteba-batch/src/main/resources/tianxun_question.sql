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
  `summary` varchar(1024) NOT NULL COMMENT '回答概述',
  `income` decimal(18,2) DEFAULT '0.00' COMMENT '回答问题所挣的钱',
  `platform` boolean DEFAULT false COMMENT '平台自己回答',
  `answer_user_id` varchar(50) NOT NULL COMMENT '回答问题的用户id',
  `answer_date` datetime NOT NULL COMMENT '回答时间',
  `question_id` varchar(50) NOT NULL COMMENT '对应的问题id',
  `accept` int(10) NOT NULL COMMENT '是否被采纳',
  `orders` int(10) NOT NULL COMMENT '回答问题的顺序',
  `state` int(10) DEFAULT 0 COMMENT '回答的状态',
  `open` boolean DEFAULT false COMMENT '回答是否开放',
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

DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment` (
  `id` varchar(50) NOT NULL,
  `content` varchar(10240) NOT NULL COMMENT '评论的内容',
  `user_id` varchar(50) NOT NULL COMMENT '评论问题的用户id',
  `nick_name` varchar(50) NOT NULL COMMENT '评论问题的用户昵称',
  `comment_date` datetime NOT NULL COMMENT '评论时间',
  `question_id` varchar(50) DEFAULT NULL COMMENT '对应的问题id',
  `answer_id` varchar(50) DEFAULT NULL COMMENT '对应的回答id',
  `orders` int(10) NOT NULL COMMENT '评论问题的顺序',
  `state` int(10) DEFAULT 0 COMMENT '评论的状态',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='问题评论表';

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
  `title` varchar(200) NOT NULL COMMENT '问题标题',
  `content` varchar(10240) DEFAULT NULL COMMENT '提问内容',
  `summary` varchar(1024) NOT NULL COMMENT '问题概述或关键字',
  `price` decimal(18,2) DEFAULT '0.00' COMMENT '问题价格',
  `asker` varchar(50) NOT NULL COMMENT '提问者id',
  `asker_name` varchar(100) DEFAULT NULL COMMENT '提问者姓名，没有显示昵称',
  `ask_date` datetime DEFAULT NULL COMMENT '提问时间',
  `answer_date` datetime DEFAULT NULL COMMENT '回答时间',
  `close_date` datetime DEFAULT NULL COMMENT '问题关闭时间',
  `solved` boolean DEFAULT false COMMENT '问题是否解决',
  `open` boolean DEFAULT false COMMENT '问题是否开放',
  `platform` boolean DEFAULT false COMMENT '平台自有问题',
  `category` int(10) DEFAULT NULL COMMENT '问题分类代码',
  `category_name` varchar(50) DEFAULT NULL COMMENT '问题分类名称',
  `satisfied_answer_id` varchar(50) DEFAULT NULL COMMENT '满意的回答id',
  `satisfied_user_id` varchar(50) DEFAULT NULL COMMENT '满意的回答者',
  `satisfied_user_name` varchar(100) DEFAULT NULL COMMENT '满意的回答者的用户名',
  `state` int(10) DEFAULT 0 COMMENT '问题状态',
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

DROP TABLE IF EXISTS `tags`;
-- 标签，就是热门分类，每新增一个问题或答案，在这个表中增加一行 或者增加热度
CREATE TABLE `tags` (
  `id` varchar(50) NOT NULL,
  `name` varchar(50) NOT NULL COMMENT '标签名',
  `state` int(10) DEFAULT NULL COMMENT '状态',
  `hot` int(10) DEFAULT NULL COMMENT '热度',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='热门标签';