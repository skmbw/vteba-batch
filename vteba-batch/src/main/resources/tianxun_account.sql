/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50711
Source Host           : localhost:3306
Source Database       : tianxun_account

Target Server Type    : MYSQL
Target Server Version : 50711
File Encoding         : 65001

Date: 2016-03-21 09:09:06
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for account
-- ----------------------------
DROP TABLE IF EXISTS `account`;
CREATE TABLE `account` (
  `id` varchar(50) NOT NULL,
  `alipay` varchar(100) DEFAULT NULL COMMENT '支付宝账号',
  `weixin` varchar(100) DEFAULT NULL COMMENT '微信号',
  `balance` decimal(18,2) NOT NULL DEFAULT '0.00' COMMENT '账户余额',
  `user_id` varchar(50) NOT NULL COMMENT '外键用户id',
  `account` varchar(50) NOT NULL COMMENT '用户账户',
  `payment` decimal(18,2) NOT NULL DEFAULT '0.00' COMMENT '消费支出',
  `income` decimal(18,2) NOT NULL DEFAULT '0.00' COMMENT '收入所赚',
  `recharge` decimal(18,2) NOT NULL DEFAULT '0.00' COMMENT '充值',
  `state` int(10) NOT NULL DEFAULT '1' COMMENT '账户状态1正常，2锁定',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_account_account` (`account`) USING BTREE,
  UNIQUE KEY `idx_account_user_id` (`user_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户资金账户表';

-- ----------------------------
-- Table structure for account_detail
-- ----------------------------
DROP TABLE IF EXISTS `account_detail`;
CREATE TABLE `account_detail` (
  `id` varchar(50) NOT NULL,
  `user_id` varchar(50) NOT NULL COMMENT '用户id',
  `account_id` varchar(50) NOT NULL COMMENT '账户id',
  `amount` decimal(18,2) NOT NULL COMMENT '金额',
  `category` int(10) NOT NULL COMMENT '操作类型1充值2消费3收入',
  `operation_time` datetime NOT NULL COMMENT '操作时间',
  PRIMARY KEY (`id`),
  KEY `idx_account_detail_user_id` (`user_id`) USING BTREE,
  KEY `idx_account_detail_account_id` (`account_id`) USING BTREE,
  KEY `idx_account_detail_category` (`category`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户资金账户明细表';

-- ----------------------------
-- Table structure for platform_account
-- ----------------------------
DROP TABLE IF EXISTS `platform_account`;
CREATE TABLE `platform_account` (
  `id` varchar(50) NOT NULL,
  `balance` decimal(18,2) NOT NULL DEFAULT '0.00' COMMENT '资金余额',
  `total` decimal(18,2) NOT NULL DEFAULT '0.00',
  `discount` decimal(18,2) NOT NULL DEFAULT '0.00' COMMENT '优惠',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='平台账户表';

-- ----------------------------
-- Table structure for platform_flow
-- ----------------------------
DROP TABLE IF EXISTS `platform_flow`;
CREATE TABLE `platform_flow` (
  `id` varchar(50) NOT NULL,
  `user_id` varchar(50) NOT NULL COMMENT '用户id',
  `account_id` varchar(50) NOT NULL COMMENT '账户id',
  `amount` decimal(18,2) NOT NULL COMMENT '金额',
  `category` int(10) NOT NULL COMMENT '操作类型1充值2消费3收入',
  `operation_time` datetime NOT NULL COMMENT '操作时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='平台账户流水表';
