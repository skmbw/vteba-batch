/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50711
Source Host           : localhost:3306
Source Database       : tianxun_order

Target Server Type    : MYSQL
Target Server Version : 50711
File Encoding         : 65001

Date: 2016-03-21 09:09:19
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for order
-- ----------------------------
DROP TABLE IF EXISTS `order`;
CREATE TABLE `order` (
  `id` varchar(50) NOT NULL,
  `ask_id` varchar(50) NOT NULL COMMENT '问题id',
  `answer_id` varchar(50) NOT NULL COMMENT '答案id',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `amount` decimal(18,2) NOT NULL COMMENT '订单金额',
  `buyer` varchar(50) NOT NULL COMMENT '买方id',
  `seller` varchar(50) NOT NULL COMMENT '卖方id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
