-- phpMyAdmin SQL Dump
-- version 4.5.4.1deb2ubuntu2
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: 2016-12-27 23:24:48
-- 服务器版本： 5.7.16-0ubuntu0.16.04.1
-- PHP Version: 7.0.8-0ubuntu0.16.04.3

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `tfdb`
--
CREATE DATABASE IF NOT EXISTS `tfdb` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
USE `tfdb`;

-- --------------------------------------------------------

--
-- 表的结构 `t_organ`：组织
--

CREATE TABLE `t_organ` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `code` VARCHAR(256),
  `name` VARCHAR(256),
  `shortname` VARCHAR(256),
  `englishname` VARCHAR(256),
  `logo` VARCHAR(256),
  `domain` VARCHAR(256),
  `province_id` INT NOT NULL DEFAULT 0,
  `city_id` INT NOT NULL DEFAULT 0,
  `district_id` INT NOT NULL DEFAULT 0,
  `postcode` VARCHAR(20),
  `contact` VARCHAR(256),
  `address` VARCHAR(1024),
  `telephone` VARCHAR(50),
  `fax` VARCHAR(50),
  `email` VARCHAR(256),
  `website` VARCHAR(256),
  `inward_id` INT NOT NULL DEFAULT 0 COMMENT '企业性质',
  `industry_id` INT NOT NULL DEFAULT 0 COMMENT '主营行业',
  `subdustry_id` INT NOT NULL DEFAULT 0 COMMENT '主营行业',
  `capital` INT NOT NULL DEFAULT 0 COMMENT '注册资金',
  `membernumber` INT NOT NULL DEFAULT 0 COMMENT '成员个数',
  `computernumber` INT NOT NULL DEFAULT 0 COMMENT '计算机台数',
  `ad` VARCHAR(1024) COMMENT '广告语',
  `intro` VARCHAR(1024),
  `listorder` INT NOT NULL DEFAULT 0,
   PRIMARY KEY(id)
) ENGINE=InnoDB;

alter table `t_organ` AUTO_INCREMENT=1;

-- --------------------------------------------------------

--
-- 表的结构 `t_branch`：部门
--

CREATE TABLE `t_branch` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `organ_id` INT NOT NULL DEFAULT 0,
  `parent_id` INT NOT NULL DEFAULT 0,
  `name` VARCHAR(256),
  `manager_id` INT NOT NULL DEFAULT 0 COMMENT '部门经理',
  `address` VARCHAR(1024),
  `website` VARCHAR(256),
  `telephone` VARCHAR(50),
  `fax` VARCHAR(50),
  `intro` VARCHAR(1024),
  `listorder` INT NOT NULL DEFAULT 0,
   PRIMARY KEY(id)
) ENGINE=InnoDB;

alter table `t_branch` AUTO_INCREMENT=101;
-- --------------------------------------------------------

--
-- 表的结构 `t_member`：成员
--

CREATE TABLE `t_member` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `account` VARCHAR(256),
  `password` VARCHAR(32),
  `token` VARCHAR(256) COMMENT '验证token',
  `createtokendate` INT DEFAULT 0 COMMENT '创建token时间到秒',
  `fullname` VARCHAR(256),
  `pinyin` VARCHAR(256) COMMENT '姓名首字母',
  `allpinyin` VARCHAR(256) COMMENT '姓名全拼',
  `workno` VARCHAR(50) COMMENT '工号',
  `sex` CHAR(1) not null comment '1',
  `birthday` VARCHAR(8),
  `logo` VARCHAR(256),
  `email` VARCHAR(256),
  `mobile` VARCHAR(50),
  `telephone` VARCHAR(50),
  `address` VARCHAR(1024),
  `organ_id` INT NOT NULL DEFAULT 0,
  `groupmax` INT NOT NULL DEFAULT 0 COMMENT '可建群数量',
  `groupuse` INT NOT NULL DEFAULT 0 COMMENT '已建群数量',
  `intro` VARCHAR(1024),
   PRIMARY KEY(id)
) ENGINE=InnoDB;

alter table `t_member` AUTO_INCREMENT=10001;

-- --------------------------------------------------------

--
-- 表的结构 `t_branch_member`部门-成员关系
--

CREATE TABLE `t_branch_member` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `branch_id` INT NOT NULL DEFAULT 0,
  `member_id` INT NOT NULL DEFAULT 0,
  `position_id` INT NOT NULL DEFAULT 0,
  `is_master` CHAR(1) DEFAULT '0' COMMENT '0非主要职能，1主要智能',
  `listorder` INT NOT NULL DEFAULT 0,
   PRIMARY KEY(id)
) ENGINE=InnoDB;

-- --------------------------------------------------------

--
-- 表的结构 `t_role`：角色
--

CREATE TABLE `t_role` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(256),
  `listorder` INT NOT NULL DEFAULT 0,
   PRIMARY KEY(id)
) ENGINE=InnoDB;

-- --------------------------------------------------------

--
-- 表的结构 `t_priv`：权限
--

CREATE TABLE `t_priv` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `parent_id` INT NOT NULL DEFAULT 0,
  `name` VARCHAR(256),
  `category` CHAR(1) DEFAULT '0' COMMENT '种类：1权限，2层级限制',
  `grouping` CHAR(1) DEFAULT '0' COMMENT '0非分组记录，1分组记录',
  `url` VARCHAR(512) COMMENT '按url控制权限',
  `app` varchar(50) comment '所属应用',
  `listorder` INT NOT NULL DEFAULT 0,
   PRIMARY KEY(id)
) ENGINE=InnoDB;

-- --------------------------------------------------------

--
-- 表的结构 `t_role_priv`：角色-权限关系
--

CREATE TABLE `t_role_priv` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `role_id` INT NOT NULL DEFAULT 0,
  `priv_id` INT NOT NULL DEFAULT 0,
   PRIMARY KEY(id)
) ENGINE=InnoDB;

-- --------------------------------------------------------

--
-- 表的结构 `t_member_role`：成员-角色关系
--

CREATE TABLE `t_member_role` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `member_id` INT NOT NULL DEFAULT 0,
  `role_id` INT NOT NULL DEFAULT 0,
  `listorder` INT NOT NULL DEFAULT 0,
   PRIMARY KEY(id)
) ENGINE=InnoDB;

-- --------------------------------------------------------

--
-- 表的结构 `t_group`：群组
--

CREATE TABLE `t_group` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `code` VARCHAR(20),
  `name` VARCHAR(256),
  `createdate` VARCHAR(8),
  `creator_id` INT NOT NULL DEFAULT 0,
  `volume` INT NOT NULL DEFAULT 0 COMMENT '可容纳人数',
  `volumeuse` INT NOT NULL DEFAULT 0 COMMENT '已有人数',
  `space` INT NOT NULL DEFAULT 0 COMMENT '共享空间',
  `spaceuse` INT NOT NULL DEFAULT 0 COMMENT '已用共享空间',
  `annexlong` INT NOT NULL DEFAULT 0 COMMENT '聊天附件保留天数',
  `notice` VARCHAR(1024) COMMENT '群公告',
  `listorder` INT NOT NULL DEFAULT 0,
   PRIMARY KEY(id)
) ENGINE=InnoDB;

-- --------------------------------------------------------

--
-- 表的结构 `t_group_member`群组-成员关系
--

CREATE TABLE `t_group_member` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `group_id` INT NOT NULL DEFAULT 0,
  `member_id` INT NOT NULL DEFAULT 0,
  `is_creator` CHAR(1) DEFAULT '0' COMMENT '0非创建者，1创建者',
  `listorder` INT NOT NULL DEFAULT 0,
   PRIMARY KEY(id)
) ENGINE=InnoDB;

-- --------------------------------------------------------

--
-- 表的结构 `t_position`：职务
--

CREATE TABLE `t_position` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `organ_id` INT NOT NULL DEFAULT 0,
  `name` VARCHAR(50),
  `listorder` INT NOT NULL DEFAULT 0,
   PRIMARY KEY(id)
) ENGINE=InnoDB;

-- --------------------------------------------------------

--
-- 表的结构 `t_friend`：好友
--

CREATE TABLE `t_friend` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `member_id` INT NOT NULL DEFAULT 0,
  `friend_id` INT NOT NULL DEFAULT 0,
  `createdate` VARCHAR(8),
  `listorder` INT NOT NULL DEFAULT 0,
   PRIMARY KEY(id)
) ENGINE=InnoDB;

-- --------------------------------------------------------

--
-- 表的结构 `t_contact`：联系人
--

CREATE TABLE `t_contact` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `member_id` INT NOT NULL DEFAULT 0,
  `contact_id` INT NOT NULL DEFAULT 0,
  `contacttimes` INT NOT NULL DEFAULT 0 COMMENT '联系次数，一天内算一次',
  `lastcontactdate` VARCHAR(8) COMMENT '最后联系日期',
   PRIMARY KEY(id)
) ENGINE=InnoDB;

-- --------------------------------------------------------

--
-- 表的结构 `t_province`：字典：省份
--

CREATE TABLE `t_province` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(256),
  `listorder` INT NOT NULL DEFAULT 0,
   PRIMARY KEY(id)
) ENGINE=InnoDB;

-- --------------------------------------------------------

--
-- 表的结构 `t_city`：字典：城市
--

CREATE TABLE `t_city` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `province_id` INT NOT NULL DEFAULT 0,
  `name` VARCHAR(256),
  `listorder` INT NOT NULL DEFAULT 0,
   PRIMARY KEY(id)
) ENGINE=InnoDB;

-- --------------------------------------------------------

--
-- 表的结构 `t_district`：字典：地区
--

CREATE TABLE `t_district` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `city_id` INT NOT NULL DEFAULT 0,
  `name` VARCHAR(256),
  `listorder` INT NOT NULL DEFAULT 0,
   PRIMARY KEY(id)
) ENGINE=InnoDB;

-- --------------------------------------------------------

--
-- 表的结构 `t_inward`：字典：企业性质
--

CREATE TABLE `t_inward` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(256),
  `listorder` INT NOT NULL DEFAULT 0,
   PRIMARY KEY(id)
) ENGINE=InnoDB;

-- --------------------------------------------------------

--
-- 表的结构 `t_industry`：字典：主行业
--

CREATE TABLE `t_industry` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(256),
  `listorder` INT NOT NULL DEFAULT 0,
   PRIMARY KEY(id)
) ENGINE=InnoDB;

-- --------------------------------------------------------

--
-- 表的结构 `t_subdustry`：字典：子行业
--

CREATE TABLE `t_subdustry` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `industry_id` INT NOT NULL DEFAULT 0,
  `name` VARCHAR(256),
  `listorder` INT NOT NULL DEFAULT 0,
   PRIMARY KEY(id)
) ENGINE=InnoDB;

-- --------------------------------------------------------

--
-- 表的结构 `t_sex`：字典：性别
--

CREATE TABLE `t_sex` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(256),
  `listorder` INT NOT NULL DEFAULT 0,
   PRIMARY KEY(id)
) ENGINE=InnoDB;

-- --------------------------------------------------------

--
-- 表的结构 `t_function`：辅助功能
--

CREATE TABLE `t_function` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(256),
  `is_open` CHAR(1) NOT NULL DEFAULT '0',
  `listorder` INT NOT NULL DEFAULT 0,
   PRIMARY KEY(id)
) ENGINE=InnoDB;

-- --------------------------------------------------------

--
-- 表的结构 `t_dontdistrub`：群组消息免打扰
--

CREATE TABLE `t_dontdistrub` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `group_id` INT NOT NULL DEFAULT '0',
  `member_id` INT NOT NULL DEFAULT '0',
  `is_open` CHAR(1) NOT NULL DEFAULT '0',
  `listorder` INT NOT NULL DEFAULT 0,
   PRIMARY KEY(id)
) ENGINE=InnoDB;

-- --------------------------------------------------------

--
-- 表的结构 `t_textcode`：手机验证码保存
--
CREATE TABLE `t_textcode` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `phone_number` VARCHAR(256) NOT NULL DEFAULT '0',
  `text_code` VARCHAR(256) NOT NULL DEFAULT '0',
  `create_time` BIGINT(11) NOT NULL DEFAULT '0',
   PRIMARY KEY(id)
) ENGINE=InnoDB;

--
-- 表的结构 `t_textcode`：头像裁剪临时库
--
CREATE TABLE `t_cutlogtemp` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `user_id` INT NOT NULL DEFAULT '0',
  `log_name` VARCHAR(256) NOT NULL DEFAULT '0',
   PRIMARY KEY(id)
) ENGINE=InnoDB;

--
-- 表的结构 `t_map`：位置坐标表
--
CREATE TABLE `t_map` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `user_id` INT NOT NULL DEFAULT '0',
  `latitude` VARCHAR(50) NOT NULL DEFAULT '0',
  `longitude` VARCHAR(50) NOT NULL DEFAULT '0',
  `subdate` VARCHAR(15) NOT NULL DEFAULT '0',
   PRIMARY KEY(id)
) ENGINE=InnoDB;

--
-- 表的结构 `t_msgtop`：辅助功能
--

CREATE TABLE `t_msgtop` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `user_id` INT NOT NULL DEFAULT '0',
  `top_id` INT NOT NULL DEFAULT '0',
  `msg_type` CHAR(1) NOT NULL DEFAULT '0' COMMENT '1群组，2成员',
  `listorder` INT NOT NULL DEFAULT '0',
   PRIMARY KEY(id)
) ENGINE=InnoDB;

--以下为认证系统数据表实现
--应用数据表
CREATE TABLE `t_appsecret` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `appname` VARCHAR(50) NOT NULL DEFAULT '0',
  `appId` VARCHAR(100) NOT NULL DEFAULT '0',
  `secert` VARCHAR(200) NOT NULL DEFAULT '0',
  `callbackurl` VARCHAR(300) NOT NULL DEFAULT '0',
  `apptime` BIGINT(11) NOT NULL DEFAULT 0,
  `isopen` int(1) not null default 1 comment '0关闭1开启',
   PRIMARY KEY(id)
) ENGINE=InnoDB;

--登陆用户数据表
CREATE TABLE `t_uservalid` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `asid` int default 0,
  `unauthtoken` VARCHAR(200) DEFAULT '0',
  `authtoken` VARCHAR(200) DEFAULT '0',
  `visittoken` VARCHAR(200) DEFAULT '0',
  `unauthtokentime` BIGINT(11) default 0,
  `authtokentime` BIGINT(11) DEFAULT 0,
  `visittokentime` BIGINT(11) DEFAULT 0,
  `userid` int,
  `info` int default 3,
   PRIMARY KEY(id)
) ENGINE=InnoDB;

---用户应用关系表
CREATE TABLE `t_usersysrelation` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `userid` int DEFAULT 0,
  `appid` int DEFAULT 0,
   PRIMARY KEY(id)
) ENGINE=InnoDB;

---角色应用关系表
create table `t_role_appsecret` (
	`id` int (11),
	`role_id` int (11),
	`appsecret_id` int (11)
); 
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
