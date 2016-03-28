/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50711
Source Host           : localhost:3306
Source Database       : tianxun_user

Target Server Type    : MYSQL
Target Server Version : 50711
File Encoding         : 65001

Date: 2016-03-21 09:09:40
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` varchar(50) NOT NULL COMMENT '主键',
  `name` varchar(100) DEFAULT NULL COMMENT '用户姓名',
  `nick_name` varchar(50) NOT NULL COMMENT '昵称，默认手机号',
  `account` varchar(50) NOT NULL COMMENT '用户的登录账户名',
  `email` varchar(100) DEFAULT NULL COMMENT '邮箱',
  `mobile` varchar(50) NOT NULL COMMENT '手机号码',
  `password` varchar(50) NOT NULL COMMENT '密码',
  `regsiter_date` datetime DEFAULT NULL COMMENT '注册日期',
  `age` int(10) DEFAULT NULL COMMENT '年龄',
  `last_login_date` datetime DEFAULT NULL COMMENT '上次登录日期',
  `state` int(11) DEFAULT '0' COMMENT '用户状态',
  `level` int(11) DEFAULT '1' COMMENT '用户等级',
  `asker` int(11) DEFAULT '1' COMMENT '提问者',
  `answer` int(11) DEFAULT '0' COMMENT '回答问题者',
  `identity_card` varchar(50) DEFAULT NULL COMMENT '证件号码',
  `identity_type` int(11) DEFAULT NULL COMMENT '证件类型1身份证2护照3其他',
  `alipay` varchar(100) DEFAULT NULL COMMENT '支付宝账号',
  `weixin` varchar(100) DEFAULT NULL COMMENT '微信账号',
  `qq` varchar(255) DEFAULT NULL COMMENT 'qq号',
  `address` varchar(250) DEFAULT NULL COMMENT '地址',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_user_account` (`account`) USING BTREE,
  UNIQUE KEY `idx_user_mobile` (`mobile`) USING BTREE,
  UNIQUE KEY `idx_user_alipay` (`alipay`) USING BTREE,
  UNIQUE KEY `idx_user_weixin` (`weixin`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户表';

-- 积分表
CREATE TABLE `score` (
  `id` varchar(50) NOT NULL COMMENT '主键',
  `user_id` varchar(50) NOT NULL COMMENT '用户id',
  `year` varchar(4) DEFAULT NULL COMMENT '积分年度',
  `score` bigint(12) DEFAULT NULL COMMENT '积分',
  `last_score` bigint(12) DEFAULT NULL COMMENT '去年的积分',
  `state` int(11) DEFAULT NULL COMMENT '1有效，2无效',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `score_idx_user_id` (`user_id`,`year`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户积分表';

-- 积分明细表
CREATE TABLE `score_detail` (
  `id` varchar(50) NOT NULL,
  `score_id` varchar(50) NOT NULL COMMENT '积分id',
  `score` int(10) NOT NULL COMMENT '分数',
  `key` varchar(50) NOT NULL COMMENT '相关业务主键',
  `key_type` varchar(50) NOT NULL COMMENT '业务类型',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `comment` varchar(200) DEFAULT NULL COMMENT '获取积分的描述',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='积分明细表';

-- 系统参数
CREATE TABLE `params` (
  `id` varchar(50) NOT NULL COMMENT '主键',
  `name` varchar(100) NOT NULL COMMENT 'key中文名',
  `code` varchar(50) NOT NULL COMMENT 'key代码',
  `value` varchar(250) NOT NULL COMMENT 'key值',
  `state` int(10) DEFAULT '0' COMMENT 'key状态1有效,0失效',
  `remark` varchar(250) DEFAULT NULL COMMENT '注释',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `update_date` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_params_code` (`code`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='参数设置';