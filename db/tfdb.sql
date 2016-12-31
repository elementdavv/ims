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
  `id` int(11) NOT NULL,
  `parent_id` int(11) NOT NULL,
  `code` varchar(256) NOT NULL,
  `name` varchar(256) NOT NULL,
  `shortname` varchar(256) NOT NULL,
  `englishname` varchar(256) NOT NULL,
  `avatar` varchar(256) NOT NULL,
  `domain` varchar(256) NOT NULL,
  `province_id` int(11) NOT NULL,
  `city_id` int(11) NOT NULL,
  `district_id` int(11) NOT NULL,
  `postcode` varchar(20) NOT NULL,
  `contact` varchar(256) NOT NULL,
  `address` varchar(1024) NOT NULL,
  `telephone` varchar(50) NOT NULL,
  `fax` varchar(50) NOT NULL,
  `email` varchar(256) NOT NULL,
  `website` varchar(256) NOT NULL,
  `inward_id` int(11) NOT NULL COMMENT '企业性质',
  `industry_id` int(11) NOT NULL COMMENT '主营行业',
  `capital` int(11) NOT NULL COMMENT '注册资金',
  `employnumber` int(11) NOT NULL,
  `computernumber` int(11) NOT NULL,
  `ad` varchar(1024) NOT NULL COMMENT '广告语',
  `intro` varchar(1024) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- 表的结构 `t_branch`：部门
--

CREATE TABLE `t_branch` (
  `id` int(11) NOT NULL,
  `organ_id` int(11) NOT NULL,
  `parent_id` int(11) NOT NULL,
  `name` varchar(256) NOT NULL,
  `manager_id` int(11) NOT NULL,
  `address` varchar(1024) NOT NULL,
  `website` varchar(256) NOT NULL,
  `telephone` varchar(50) NOT NULL,
  `fax` varchar(50) NOT NULL,
  `intro` varchar(1024) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- 表的结构 `t_member`：成员
--

CREATE TABLE `t_member` (
  `id` int(11) NOT NULL,
  `account` varchar(256) NOT NULL,
  `password` varchar(32) NOT NULL,
  `role_id` int(11) NOT NULL,
  `fullname` varchar(256) NOT NULL,
  `pinyin` varchar(256) NOT NULL,
  `workno` varchar(50) NOT NULL COMMENT '工号',
  `sex` varchar(2) NOT NULL,
  `birthday` date NOT NULL,
  `avatar` varchar(50) NOT NULL,
  `email` varchar(256) NOT NULL,
  `mobile` varchar(50) NOT NULL,
  `telephone` varchar(50) NOT NULL,
  `address` varchar(1024) NOT NULL,
  `groupmax` int(11) NOT NULL COMMENT '可建群数量',
  `groupuse` int(11) NOT NULL COMMENT '已建群数量',
  `intro` varchar(1024) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- 表的结构 `t_branch_member`部门-成员关系
--

CREATE TABLE `t_branch_member` (
  `id` int(11) NOT NULL,
  `branch_id` int(11) NOT NULL,
  `member_id` int(11) NOT NULL,
  `position_id` int(11) NOT NULL,
  `is_master` char(1) NOT NULL COMMENT '是否主要职能'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- 表的结构 `t_role`：角色（即身份）
--

CREATE TABLE `t_role` (
  `id` int(11) NOT NULL,
  `parent_id` int(11),
  `name` varchar(256) NOT NULL,
  `listorder` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- 表的结构 `t_priv`：权限
--

CREATE TABLE `t_priv` (
  `id` int(11) NOT NULL,
  `parent_id` int(11) NOT NULL,
  `name` varchar(256) NOT NULL,
  `category` char(1) NOT NULL COMMENT '种类：1权限，2层级限制'
  `grouping` varchar(256) NOT NULL COMMENT '分组'
  `url` varchar(512) NOT NULL COMMENT '按url控制权限',
  `listorder` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- 表的结构 `t_role_priv`：角色-权限关系
--

CREATE TABLE `t_role_priv` (
  `id` int(11) NOT NULL,
  `role_id` int(11) NOT NULL,
  `priv_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- 表的结构 `t_group`：群组
--

CREATE TABLE `t_group` (
  `id` int(11) NOT NULL,
  `code` varchar(20) NOT NULL,
  `name` varchar(256) NOT NULL,
  `createdate` varchar(8) NOT NULL,
  `creator_id` int(11) NOT NULL COMMENT '创建者',
  `volume` int(11) NOT NULL COMMENT '可容纳人数',
  `amount` int(11) NOT NULL COMMENT '已有人数',
  `space` int(11) NOT NULL COMMENT '共享空间',
  `annexlong` int(11) NOT NULL COMMENT '聊天附件保留天数',
  `notice` varchar(1024) NOT NULL COMMENT '群公告'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- 表的结构 `t_group_member`群组-成员关系
--

CREATE TABLE `t_group_member` (
  `id` int(11) NOT NULL,
  `group_id` int(11) NOT NULL,
  `member_id` int(11) NOT NULL,
  `is_creator` char(1) NOT NULL COMMENT '是否创建者'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- 表的结构 `t_position`：职务
--

CREATE TABLE `t_position` (
  `id` int(11) NOT NULL,
  `organ_id` int(11) NOT NULL,
  `name` varchar(50) CHARACTER SET utf8 NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- 表的结构 `t_friend`：好友
--

CREATE TABLE `t_friend` (
  `id` int(11) NOT NULL,
  `member_id` int(11) NOT NULL,
  `friend_id` int(11) NOT NULL,
  `createdate` varchar(8) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `t_organ`
--
ALTER TABLE `t_organ`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `t_branch`
--
ALTER TABLE `t_branch`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `t_member`
--
ALTER TABLE `t_member`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `t_branch_member`
--
ALTER TABLE `t_branch_member`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `t_role`
--
ALTER TABLE `t_role`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `t_priv`
--
ALTER TABLE `t_priv`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `t_role_priv`
--
ALTER TABLE `t_role_priv`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `t_group`
--
ALTER TABLE `t_group`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `t_group_member`
--
ALTER TABLE `t_group_member`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `t_position`
--
ALTER TABLE `t_position`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `t_friend`
--
ALTER TABLE `t_friend`
  ADD PRIMARY KEY (`id`);

--
-- 在导出的表使用AUTO_INCREMENT
--

--
-- 使用表AUTO_INCREMENT `t_organ`
--
ALTER TABLE `t_organ`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
--
-- 使用表AUTO_INCREMENT `t_branch`
--
ALTER TABLE `t_branch`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
--
-- 使用表AUTO_INCREMENT `t_member`
--
ALTER TABLE `t_member`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
--
-- 使用表AUTO_INCREMENT `t_branch_member`
--
ALTER TABLE `t_branch_member`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
--
-- 使用表AUTO_INCREMENT `t_role`
--
ALTER TABLE `t_role`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
--
-- 使用表AUTO_INCREMENT `t_priv`
--
ALTER TABLE `t_priv`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
--
-- 使用表AUTO_INCREMENT `t_role_priv`
--
ALTER TABLE `t_role_priv`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
--
-- 使用表AUTO_INCREMENT `t_group`
--
ALTER TABLE `t_group`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
--
-- 使用表AUTO_INCREMENT `t_group_member`
--
ALTER TABLE `t_group_member`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
--
-- 使用表AUTO_INCREMENT `t_position`
--
ALTER TABLE `t_position`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
--
-- 使用表AUTO_INCREMENT `t_friend`
--
ALTER TABLE `t_friend`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
