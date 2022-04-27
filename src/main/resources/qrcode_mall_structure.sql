-- MySQL dump 10.13  Distrib 8.0.16, for Win64 (x86_64)
--
-- Host: localhost    Database: code_mall
-- ------------------------------------------------------
-- Server version	8.0.16

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `account`
--

DROP TABLE IF EXISTS `account`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `account` (
  `account_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `goods_type_name` varchar(45) DEFAULT NULL,
  `goods_type_qrcode_quantity` int(11) DEFAULT NULL,
  PRIMARY KEY (`account_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='表示一个用户有多少个对应的二维码';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `admin`
--

DROP TABLE IF EXISTS `admin`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `admin` (
  `admin_id` int(11) NOT NULL AUTO_INCREMENT,
  `admin_name` varchar(45) NOT NULL COMMENT '名字',
  `admin_password` varchar(255) NOT NULL COMMENT '密码',
  PRIMARY KEY (`admin_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='管理员';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `carousel`
--

DROP TABLE IF EXISTS `carousel`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `carousel` (
  `carousel_id` int(11) NOT NULL AUTO_INCREMENT,
  `carousel_img_name` varchar(255) DEFAULT NULL COMMENT '图片名字',
  `carousel_link` varchar(255) DEFAULT NULL COMMENT '链接',
  `gmt_created` datetime DEFAULT NULL,
  `gmt_modified` datetime DEFAULT NULL,
  `is_deleted` int(11) DEFAULT '0',
  PRIMARY KEY (`carousel_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='首页轮播图';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `goods`
--

DROP TABLE IF EXISTS `goods`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `goods` (
  `goods_id` int(11) NOT NULL AUTO_INCREMENT,
  `goods_type_id` int(11) NOT NULL COMMENT '商品类别，1蔬菜，2水果，3种子，4农药，5化肥，type表主键',
  `goods_name` varchar(45) NOT NULL,
  `goods_price` decimal(10,2) NOT NULL COMMENT '一个套餐的价格',
  `goods_type_name` varchar(45) NOT NULL COMMENT 'goods_type表的name',
  `goods_qrcode_quantity` int(11) NOT NULL COMMENT '一个套餐的二维码数量',
  `goods_picture` varchar(255) DEFAULT NULL COMMENT '商品图片',
  `goods_detail` varchar(255) DEFAULT NULL COMMENT '详情',
  `goods_introduction` text COMMENT '商品简介',
  `goods_status` int(11) NOT NULL DEFAULT '0' COMMENT '0非特价，1特价',
  `gmt_created` datetime DEFAULT NULL,
  `gmt_modified` datetime DEFAULT NULL,
  `is_deleted` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`goods_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='商品表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `goods_type`
--

DROP TABLE IF EXISTS `goods_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `goods_type` (
  `goods_type_id` int(11) NOT NULL AUTO_INCREMENT,
  `goods_type_name` varchar(45) NOT NULL COMMENT '类别名字',
  `goods_type_message` text,
  `is_deleted` int(11) NOT NULL DEFAULT '0' COMMENT '0不删除',
  PRIMARY KEY (`goods_type_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='商品种类表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `notice`
--

DROP TABLE IF EXISTS `notice`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `notice` (
  `notice_id` int(11) NOT NULL AUTO_INCREMENT,
  `notice_tittle_name` varchar(255) NOT NULL COMMENT '标题名字',
  `notice_message` text NOT NULL COMMENT '主要信息',
  `gmt_created` datetime NOT NULL,
  `gmt_modified` datetime DEFAULT NULL,
  `is_deleted` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`notice_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='公告表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `order_form`
--

DROP TABLE IF EXISTS `order_form`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `order_form` (
  `order_form_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL COMMENT '用户表主键',
  `user_address_id` int(11) DEFAULT NULL COMMENT '参照user_address',
  `order_form_number` varchar(255) NOT NULL COMMENT '订单号',
  `order_form_status` int(11) NOT NULL DEFAULT '0' COMMENT '0未付款，1已付款',
  `order_form_price` decimal(10,2) DEFAULT '0.00',
  `order_form_pay_type` int(11) DEFAULT NULL COMMENT '支付方式，1支付宝，2微信',
  `order_form_pay_time` datetime DEFAULT NULL COMMENT '支付时间',
  `gmt_created` datetime NOT NULL,
  `gmt_modified` datetime DEFAULT NULL,
  `is_deleted` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`order_form_id`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='订单表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `order_form_detail`
--

DROP TABLE IF EXISTS `order_form_detail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `order_form_detail` (
  `order_form_deatil_id` int(11) NOT NULL AUTO_INCREMENT,
  `order_form_id` int(11) NOT NULL COMMENT '参照order_form_id',
  `goods_id` int(11) NOT NULL COMMENT '商品表id',
  `goods_name` varchar(45) NOT NULL COMMENT 'goods表',
  `goods_price` decimal(10,2) NOT NULL COMMENT 'goods表',
  `goods_qrcode_quantity` int(11) NOT NULL COMMENT 'goods表',
  `goods_type_name` varchar(45) NOT NULL COMMENT 'goods表',
  `gmt_created` datetime NOT NULL,
  `gmt_modified` datetime DEFAULT NULL,
  `is_deleted` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`order_form_deatil_id`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='订单详细信息';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `promotion`
--

DROP TABLE IF EXISTS `promotion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `promotion` (
  `promotion_id` int(11) NOT NULL AUTO_INCREMENT,
  `goods_id` int(11) DEFAULT NULL,
  `promotion_start_time` datetime DEFAULT NULL,
  `promotion_duration` int(11) DEFAULT NULL,
  `promotion_count` int(11) DEFAULT NULL,
  `promotion_value` decimal(10,2) DEFAULT NULL,
  `is_deleted` int(11) DEFAULT '0',
  PRIMARY KEY (`promotion_id`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='促销商品表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `property`
--

DROP TABLE IF EXISTS `property`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `property` (
  `property_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `property_key` varchar(255) NOT NULL COMMENT '每个种类要有什么字段',
  `goods_type_name` varchar(45) DEFAULT NULL,
  `goods_type_id` int(11) NOT NULL,
  `gmt_created` datetime DEFAULT NULL,
  `gmt_modified` datetime DEFAULT NULL,
  `is_deleted` int(11) DEFAULT '0',
  PRIMARY KEY (`property_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='不同种类对应描述字段不同，比如农药会有标准，水果没有，所以本表用来设定每个种类应该有什么字段，另一个表为各个字段的具体描述';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `property_value`
--

DROP TABLE IF EXISTS `property_value`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `property_value` (
  `property_value_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `property_id` int(11) NOT NULL COMMENT '参照property表',
  `qrcode_id` int(11) NOT NULL COMMENT '参照qrcode表',
  `property_key` varchar(255) NOT NULL COMMENT '字段名字',
  `property_value` text NOT NULL COMMENT '具体字段描述',
  `gmt_created` datetime DEFAULT NULL,
  `gmt_modified` datetime DEFAULT NULL,
  `is_deleted` int(11) DEFAULT '0',
  PRIMARY KEY (`property_value_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='本表用来存储各个二维码扫出字段的描述';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `qrcode`
--

DROP TABLE IF EXISTS `qrcode`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `qrcode` (
  `qrcode_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL COMMENT 'user表id',
  `goods_id` int(11) NOT NULL COMMENT 'goods表id',
  `goods_type_id` int(11) NOT NULL COMMENT 'goods_type表',
  `goods_type_name` varchar(45) NOT NULL COMMENT 'goods_type表',
  `qrcode_link` varchar(255) NOT NULL COMMENT '二维码地址',
  `gmt_created` datetime NOT NULL,
  `gmt_modified` datetime DEFAULT NULL,
  `is_deleted` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`qrcode_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='二维码表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `qrcode_batch`
--

DROP TABLE IF EXISTS `qrcode_batch`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `qrcode_batch` (
  `qrcode_batch_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `qrcode_number` int(11) DEFAULT NULL,
  `is_mail` int(11) DEFAULT NULL,
  `goods_type_name` varchar(45) DEFAULT NULL,
  `qrcode_id` varchar(45) DEFAULT NULL,
  `express_number` varchar(45) DEFAULT NULL,
  `express_services_company` varchar(45) DEFAULT NULL,
  `user_address_id` int(11) DEFAULT NULL,
  `gmt_created` datetime DEFAULT NULL,
  `gmt_modified` datetime DEFAULT NULL,
  `goods_type_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`qrcode_batch_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `schedule_task`
--

DROP TABLE IF EXISTS `schedule_task`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `schedule_task` (
  `schedule_task_id` int(11) NOT NULL AUTO_INCREMENT,
  `promotion_id` int(11) DEFAULT NULL,
  `start_id` varchar(45) DEFAULT NULL,
  `end_id` varchar(45) DEFAULT NULL,
  `is_deleted` int(11) DEFAULT '0',
  PRIMARY KEY (`schedule_task_id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='定时任务表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `user` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(45) NOT NULL COMMENT '用户名字不重复',
  `user_phone` varchar(45) DEFAULT NULL COMMENT '用户手机号不重复',
  `user_password` varchar(255) NOT NULL COMMENT '用户密码',
  `user_email` varchar(45) DEFAULT 'xxx@qq.com' COMMENT '用户邮箱不重复',
  `is_vip` int(11) NOT NULL DEFAULT '0' COMMENT '0不是VIP',
  `user_point` int(11) NOT NULL DEFAULT '0' COMMENT '积分',
  `is_deleted` int(11) NOT NULL DEFAULT '0',
  `user_father_proxy_id` int(11) DEFAULT NULL COMMENT '父级代理id',
  `user_father_proxy_name` varchar(45) DEFAULT NULL COMMENT '父级代理名字',
  `user_grandfather_proxy_id` int(11) DEFAULT NULL COMMENT '爷爷级代理id',
  `user_grandfather_proxy_name` varchar(45) DEFAULT NULL COMMENT '爷爷级代理名字',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user_address`
--

DROP TABLE IF EXISTS `user_address`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `user_address` (
  `user_address_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL COMMENT '参照user表',
  `user_address_province` varchar(45) NOT NULL COMMENT '省字段',
  `user_address_city` varchar(45) NOT NULL COMMENT '市',
  `user_address_district` varchar(45) NOT NULL COMMENT '区',
  `user_address_detail` varchar(45) NOT NULL COMMENT '详细地址',
  `user_address_default` int(11) NOT NULL DEFAULT '0' COMMENT '是否是默认地址，0不默认，1默认',
  `gmt_created` datetime NOT NULL,
  `gmt_modified` datetime DEFAULT NULL,
  `is_deleted` int(11) NOT NULL DEFAULT '0',
  `receive_name` varchar(255) NOT NULL,
  `receive_phone` varchar(255) NOT NULL,
  PRIMARY KEY (`user_address_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户地址表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user_bill`
--

DROP TABLE IF EXISTS `user_bill`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `user_bill` (
  `user_bill_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL COMMENT 'user主键',
  `user_bill_money` decimal(10,2) NOT NULL COMMENT '一个账单花了多少钱',
  `user_bill_direction` int(11) DEFAULT NULL COMMENT '0收入，1支出',
  `user_bill_remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `gmt_created` datetime DEFAULT NULL,
  `gmt_modified` datetime DEFAULT NULL,
  `is_deleted` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`user_bill_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='账单表';
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-04-27 13:27:32
