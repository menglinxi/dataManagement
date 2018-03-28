-- --------------------------------------------------------
-- 主机:                           127.0.0.1
-- 服务器版本:                        5.5.47 - MySQL Community Server (GPL)
-- 服务器操作系统:                      Win32
-- HeidiSQL 版本:                  9.3.0.4984
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

-- 导出 gamedate 的数据库结构
CREATE DATABASE IF NOT EXISTS `gamedate` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `gamedate`;


-- 导出  表 gamedate.t_apm_alarm 结构
CREATE TABLE IF NOT EXISTS `t_apm_alarm` (
  `id` bigint(64) NOT NULL AUTO_INCREMENT,
  `alarm_code` varchar(128) NOT NULL COMMENT '报警信息编号',
  `alarm_type` varchar(20) DEFAULT NULL COMMENT '报警类型',
  `alarm_time` datetime DEFAULT NULL COMMENT '报警时间',
  `alarm_msg` varchar(250) DEFAULT NULL COMMENT '报警消息',
  `alarm_ip` varchar(128) DEFAULT NULL COMMENT '报警 ip',
  `alarm_title` varchar(128) DEFAULT NULL COMMENT '报警标题',
  `alarm_device` varchar(128) DEFAULT NULL COMMENT '报警设备',
  `alarm_usage` decimal(15,10) DEFAULT NULL COMMENT ' 设备使用情况',
  `alarm_alarm_point` int(32) DEFAULT NULL COMMENT '设备告警点',
  PRIMARY KEY (`id`),
  UNIQUE KEY `alarm_code` (`alarm_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='性能告警表';

-- 正在导出表  gamedate.t_apm_alarm 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `t_apm_alarm` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_apm_alarm` ENABLE KEYS */;


-- 导出  表 gamedate.t_article 结构
CREATE TABLE IF NOT EXISTS `t_article` (
  `id` bigint(64) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='文章';

-- 正在导出表  gamedate.t_article 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `t_article` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_article` ENABLE KEYS */;


-- 导出  表 gamedate.t_branch 结构
CREATE TABLE IF NOT EXISTS `t_branch` (
  `id` bigint(64) NOT NULL AUTO_INCREMENT,
  `sub_name` varchar(200) NOT NULL COMMENT '分支机构名称',
  `sub_code` varchar(128) DEFAULT NULL COMMENT '分支机构编码',
  `sub_des` varchar(500) DEFAULT NULL COMMENT '分支机构描述',
  `sub_city` varchar(128) DEFAULT NULL COMMENT '所在城市',
  `sub_province` varchar(128) DEFAULT NULL COMMENT '所在省份',
  `sub_longitude` decimal(15,10) DEFAULT NULL COMMENT '经度',
  `sub_latitude` decimal(15,10) DEFAULT NULL COMMENT '纬度',
  `sub_district` varchar(128) DEFAULT NULL COMMENT '所在地区',
  `sub_address` varchar(500) DEFAULT NULL COMMENT '分支机构地址',
  `sub_phone` varchar(500) DEFAULT NULL COMMENT '分支机构电话',
  `sub_principal_id` int(32) DEFAULT NULL COMMENT '分支机构负责人id',
  `sub_parent_id` int(32) DEFAULT NULL COMMENT '上级机构编号',
  PRIMARY KEY (`id`),
  UNIQUE KEY `sub_name` (`sub_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='分支机构';

-- 正在导出表  gamedate.t_branch 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `t_branch` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_branch` ENABLE KEYS */;


-- 导出  表 gamedate.t_channel 结构
CREATE TABLE IF NOT EXISTS `t_channel` (
  `id` bigint(64) NOT NULL AUTO_INCREMENT,
  `audit_flag` varchar(2) DEFAULT NULL,
  `delete_flag` varchar(1) DEFAULT NULL,
  `channel_name` varchar(128) DEFAULT NULL COMMENT '游戏名',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  `gameid` varchar(128) DEFAULT NULL,
  `userid` varchar(128) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8 COMMENT='渠道表';

-- 正在导出表  gamedate.t_channel 的数据：~27 rows (大约)
/*!40000 ALTER TABLE `t_channel` DISABLE KEYS */;
INSERT INTO `t_channel` (`id`, `audit_flag`, `delete_flag`, `channel_name`, `create_date`, `update_date`, `gameid`, `userid`) VALUES
	(1, NULL, '0', '星空联盟DEV1', '2017-05-08 19:35:20', '2017-05-12 09:04:50', NULL, NULL),
	(2, NULL, '0', '星空联盟DEV2', '2017-05-08 19:35:24', '2017-05-12 09:04:50', NULL, NULL);
/*!40000 ALTER TABLE `t_channel` ENABLE KEYS */;


-- 导出  表 gamedate.t_channel_game 结构
CREATE TABLE IF NOT EXISTS `t_channel_game` (
  `id` bigint(64) NOT NULL AUTO_INCREMENT,
  `idss` varchar(255) DEFAULT NULL,
  `audit_flag` varchar(2) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `delete_flag` varchar(1) DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `channel_id` bigint(64) DEFAULT NULL,
  `game_id` varchar(255) DEFAULT NULL,
  `user_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=555 DEFAULT CHARSET=utf8;

-- 正在导出表  gamedate.t_channel_game 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `t_channel_game` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_channel_game` ENABLE KEYS */;


-- 导出  表 gamedate.t_codebook 结构
CREATE TABLE IF NOT EXISTS `t_codebook` (
  `id` bigint(64) NOT NULL AUTO_INCREMENT,
  `c_name` varchar(128) NOT NULL,
  `c_group_id` int(32) DEFAULT NULL,
  `c_value` varchar(128) DEFAULT NULL,
  `c_index` int(32) DEFAULT NULL,
  `c_parent_id` int(32) DEFAULT NULL,
  `c_is_active` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `c_name` (`c_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='码表';

-- 正在导出表  gamedate.t_codebook 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `t_codebook` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_codebook` ENABLE KEYS */;


-- 导出  表 gamedate.t_code_book_group 结构
CREATE TABLE IF NOT EXISTS `t_code_book_group` (
  `id` bigint(64) NOT NULL AUTO_INCREMENT,
  `g_name` varchar(128) NOT NULL COMMENT '分组名称',
  `g_descr` varchar(128) DEFAULT NULL COMMENT '分组描述',
  `g_del` tinyint(1) DEFAULT NULL COMMENT '分组可用状态',
  PRIMARY KEY (`id`),
  UNIQUE KEY `g_name` (`g_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='码表分组';

-- 正在导出表  gamedate.t_code_book_group 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `t_code_book_group` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_code_book_group` ENABLE KEYS */;


-- 导出  表 gamedate.t_context 结构
CREATE TABLE IF NOT EXISTS `t_context` (
  `id` bigint(64) NOT NULL AUTO_INCREMENT,
  `audit_flag` varchar(2) DEFAULT NULL,
  `ids` varchar(255) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `delete_flag` varchar(1) DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `add_number` varchar(255) DEFAULT NULL,
  `add_percent` varchar(255) DEFAULT NULL,
  `channel_name` varchar(255) DEFAULT NULL,
  `clientarppu` varchar(255) DEFAULT NULL,
  `client_percent` varchar(255) DEFAULT NULL,
  `excel_version` varchar(255) DEFAULT NULL,
  `game_name` varchar(255) DEFAULT NULL,
  `game_id` bigint(64) DEFAULT NULL COMMENT '游戏ID',
  `keep2` varchar(255) DEFAULT NULL,
  `keep7` varchar(255) DEFAULT NULL,
  `pay_money` varchar(255) DEFAULT NULL,
  `pay_percent` varchar(255) DEFAULT NULL,
  `import_time` varchar(255) DEFAULT NULL,
  `looktime` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='游戏日流水数据';

-- 正在导出表  gamedate.t_context 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `t_context` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_context` ENABLE KEYS */;


-- 导出  表 gamedate.t_departmanet 结构
CREATE TABLE IF NOT EXISTS `t_departmanet` (
  `id` bigint(64) NOT NULL AUTO_INCREMENT,
  `d_name` varchar(128) DEFAULT NULL COMMENT '部门名称',
  `d_descr` varchar(500) DEFAULT NULL COMMENT '部门描述',
  `d_branch_id` bigint(64) DEFAULT NULL COMMENT '归属机构',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='部门表';

-- 正在导出表  gamedate.t_departmanet 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `t_departmanet` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_departmanet` ENABLE KEYS */;


-- 导出  表 gamedate.t_game 结构
CREATE TABLE IF NOT EXISTS `t_game` (
  `id` bigint(64) NOT NULL AUTO_INCREMENT COMMENT '游戏真实ID，用来对应游戏名称',
  `idss` varchar(32) DEFAULT NULL,
  `audit_flag` varchar(2) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `delete_flag` varchar(1) DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `gamename` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=utf8;

-- 正在导出表  gamedate.t_game 的数据：~37 rows (大约)
/*!40000 ALTER TABLE `t_game` DISABLE KEYS */;
INSERT INTO `t_game` (`id`, `idss`, `audit_flag`, `create_date`, `delete_flag`, `update_date`, `gamename`) VALUES
	(1, '402881225d2a0173015d2a1e619b0022', NULL, '2017-07-10 09:30:20', '0', '2017-07-10 09:30:20', '勇士x勇士'),
	(2, '402881225d3970d4015d3a7600110025', NULL, '2017-07-13 13:39:58', '0', '2017-07-13 13:39:58', '元气战姬学院'),
	(3, '402881275f6acea2015f6be54981001e', NULL, '2017-10-30 14:08:29', '0', '2017-10-30 14:08:29', 'x19'),
	(4, '402881275f994b52015f9a2e155a0028', NULL, '2017-11-08 13:50:32', '0', '2017-11-08 13:50:32', '终结者2：审判日（电影官方手游）'),
	(5, '402881275fa56999015fa56b46bc001d', NULL, '2017-11-10 18:13:11', '0', '2017-11-10 18:13:11', '狼人杀'),
	(6, '402881ee5be7d44b015be7d7a40e0001', NULL, '2017-05-08 19:35:20', '0', '2017-05-08 19:35:20', '三少爷的剑'),
	(7, '402881ee5be7d44b015be7d7a5cb000e', NULL, '2017-05-08 19:35:21', '0', '2017-05-08 19:35:21', '大唐无双'),
	(8, '402881ee5be7d44b015be7d7a785001b', NULL, '2017-05-08 19:35:21', '0', '2017-05-08 19:35:21', '劲舞团'),
	(9, '402881ee5be7d44b015be7d7a9370027', NULL, '2017-05-08 19:35:22', '0', '2017-05-08 19:35:22', '率土之滨'),
	(10, '402881ee5be7d44b015be7d7ac34003e', NULL, '2017-05-08 19:35:22', '0', '2017-05-08 19:35:22', '阴阳师'),
	(11, '402881ee5be7d44b015be7d7ade8004a', NULL, '2017-05-08 19:35:23', '0', '2017-05-08 19:35:23', '倩女幽魂'),
	(12, '402881ee5be7d44b015be7d7b19b0061', NULL, '2017-05-08 19:35:24', '0', '2017-05-08 19:35:24', '光明大陆'),
	(13, '402881ee5be7d44b015be7d7b33c006e', NULL, '2017-05-08 19:35:24', '0', '2017-05-08 19:35:24', '大话西游手游'),
	(14, '402881ee5be7d44b015be7d7b521007a', NULL, '2017-05-08 19:35:25', '0', '2017-05-08 19:35:25', '我守护的一切'),
	(15, '402881ee5be7d44b015be7d7b6eb0086', NULL, '2017-05-08 19:35:25', '0', '2017-05-08 19:35:25', '迷雾世界'),
	(16, '402881ee5be7d44b015be7d7bbbb00aa', NULL, '2017-05-08 19:35:26', '0', '2017-05-08 19:35:26', '镇魔曲'),
	(17, '402881ee5be7d44b015be7d7c07f00cd', NULL, '2017-05-08 19:35:28', '0', '2017-05-08 19:35:28', '秘宝猎人'),
	(18, '402881ee5be7d44b015be7d7cc980129', NULL, '2017-05-08 19:35:31', '0', '2017-05-08 19:35:31', '坦克连'),
	(19, '402881ee5be7d44b015be7d7cfa20140', NULL, '2017-05-08 19:35:32', '0', '2017-05-08 19:35:32', '钢铁黎明'),
	(20, '402881ee5be7d44b015be7d7d4680162', NULL, '2017-05-08 19:35:33', '0', '2017-05-08 19:35:33', '大话西游热血版'),
	(21, '402881ee5be7d44b015be7d7d6000111', NULL, '2017-07-20 17:17:08', '0', '2017-07-20 17:17:10', '飞刀又见飞刀'),
	(22, '402881ee5be7d44b015be7d7d600016e', NULL, '2017-05-08 19:35:33', '0', '2017-05-08 19:35:33', '梦幻西游手游'),
	(23, '4802a76123c5415c958f126a7141a556', NULL, '2017-11-08 17:01:01', '0', '2017-11-08 17:01:01', '荒野行动'),
	(24, '4802a76123c5415c958f126a7141a557', NULL, '2017-05-08 19:35:40', '0', '2017-05-08 19:35:40', '魔法禁书录'),
	(25, '4902a76123c5415c958f126a7141a555', NULL, '2017-10-16 17:01:01', '0', '2017-10-16 17:01:01', '格罗亚传奇'),
	(26, '5002a76123c5415c958f126a7141a500', NULL, '2017-11-22 11:46:01', '0', '2017-11-22 11:47:01', '三国如龙传'),
	(27, '5012a76123c5415c958f126a7141a501', NULL, '2017-11-29 11:46:01', '0', '2017-11-29 11:47:01', '玩具大乱斗'),
	(28, '5282a76123c5415c958f126a7141a528', NULL, '2017-11-29 11:46:01', '0', '2017-11-29 11:47:01', '重装突击'),
	(29, '5292a76123c5415c958f126a7141a529', NULL, '2017-11-29 11:46:01', '0', '2017-11-29 11:47:01', '魂之轨迹'),
	(30, '5302a76123c5415c958f126a7141a530', NULL, '2017-11-29 11:46:01', '0', '2017-11-29 11:47:01', '天启联盟'),
	(31, '5312a76123c5415c958f126a7141a531', NULL, '2017-11-29 11:46:01', '0', '2017-11-29 11:47:01', '秘境对决'),
	(32, '5322a76123c5415c958f126a7141a532', NULL, '2017-11-29 11:46:01', '0', '2017-11-29 11:47:01', '荆棘王座'),
	(33, '5332a76123c5415c958f126a7141a533', NULL, '2017-12-21 11:46:01', '0', '2017-12-21 11:47:01', '无尽神域'),
	(34, '5342a76123c5415c958f126a7141a534', NULL, '2017-12-21 11:46:01', '0', '2017-12-21 11:47:01', '坦克世界闪击战'),
	(35, '5342a76123c5415c958f126a7141a535', NULL, '2018-01-24 11:09:33', '0', '2018-01-24 11:09:36', '边境之旅'),
	(36, '5342a76123c5415c958f126a7141a536', NULL, '2018-01-24 11:09:33', '0', '2018-01-24 11:09:36', '楚留香'),
	(40, '5342a76123c5415c958f126a7141a537', NULL, '2018-03-27 09:12:33', '0', '2018-03-27 09:12:33', '孤岛先锋');
/*!40000 ALTER TABLE `t_game` ENABLE KEYS */;


-- 导出  表 gamedate.t_nutzer 结构
CREATE TABLE IF NOT EXISTS `t_nutzer` (
  `id` bigint(64) NOT NULL AUTO_INCREMENT,
  `n_access_token` varchar(128) DEFAULT NULL COMMENT 'nutz.cn的accesstoken',
  `n_login_name` varchar(128) DEFAULT NULL COMMENT '登录名',
  `n_score` varchar(128) DEFAULT NULL COMMENT '积分',
  `n_avatar_url` varchar(150) DEFAULT NULL COMMENT '头像',
  `n_create_at` varchar(128) DEFAULT NULL COMMENT '注册时间',
  `n_create_at_forread` varchar(128) DEFAULT NULL COMMENT '注册相对时间',
  `c_openid` varchar(32) DEFAULT NULL COMMENT '微信用户的openid',
  `c_nick_name` varchar(128) DEFAULT NULL COMMENT '微信昵称',
  `c_city` varchar(128) DEFAULT NULL COMMENT '微信资料城市',
  `c_province` varchar(128) DEFAULT NULL COMMENT '微信资料省份',
  `c_country` varchar(128) DEFAULT NULL COMMENT '微信资料国家',
  `c_head_image_url` varchar(150) DEFAULT NULL COMMENT '头像地址',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='nutz 用户';

-- 正在导出表  gamedate.t_nutzer 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `t_nutzer` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_nutzer` ENABLE KEYS */;


-- 导出  表 gamedate.t_opt_log 结构
CREATE TABLE IF NOT EXISTS `t_opt_log` (
  `id` bigint(64) NOT NULL AUTO_INCREMENT,
  `opt_account` varchar(128) DEFAULT NULL COMMENT '操作人员账户',
  `opt_ip` varchar(128) DEFAULT NULL COMMENT '操作人员 ip 地址',
  `opt_module` varchar(128) DEFAULT NULL COMMENT '操作功能模块',
  `opt_action` varchar(128) DEFAULT NULL COMMENT '操作的具体功能',
  `opt_description` varchar(128) DEFAULT NULL COMMENT '功能描述',
  `opt_action_time` datetime DEFAULT NULL COMMENT '操作时间',
  `opt_execution_time` bigint(64) DEFAULT NULL COMMENT '方法执行时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 正在导出表  gamedate.t_opt_log 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `t_opt_log` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_opt_log` ENABLE KEYS */;


-- 导出  表 gamedate.t_permission 结构
CREATE TABLE IF NOT EXISTS `t_permission` (
  `id` bigint(64) NOT NULL AUTO_INCREMENT,
  `p_name` varchar(128) NOT NULL COMMENT '权限名称',
  `p_url` varchar(128) DEFAULT NULL COMMENT '权限对应url',
  `p_icon` varchar(128) DEFAULT NULL COMMENT '菜单icon',
  `p_desc` varchar(128) DEFAULT NULL COMMENT '描述',
  `installed` tinyint(1) DEFAULT NULL COMMENT '内置标识',
  `p_is_menu` tinyint(1) DEFAULT NULL COMMENT '是否菜单标识',
  `p_need` varchar(128) DEFAULT NULL COMMENT '前置权限ID',
  `p_hilight_key` varchar(128) DEFAULT NULL COMMENT '菜单高亮关键字,如果是菜单的时候此字段建议填写',
  `p_level` int(32) DEFAULT NULL COMMENT '权限级别,0为顶级菜单儿',
  PRIMARY KEY (`id`),
  UNIQUE KEY `p_name` (`p_name`)
) ENGINE=InnoDB AUTO_INCREMENT=45 DEFAULT CHARSET=utf8 COMMENT='权限表';

-- 正在导出表  gamedate.t_permission 的数据：~43 rows (大约)
/*!40000 ALTER TABLE `t_permission` DISABLE KEYS */;
INSERT INTO `t_permission` (`id`, `p_name`, `p_url`, `p_icon`, `p_desc`, `installed`, `p_is_menu`, `p_need`, `p_hilight_key`, `p_level`) VALUES
	(1, 'user.list', NULL, NULL, '用户管理', 1, 0, NULL, NULL, 0),
	(2, 'user.add', NULL, NULL, '用户添加', 1, 0, NULL, NULL, 0),
	(3, 'user.detail', NULL, NULL, '用户详情', 1, 0, NULL, NULL, 0),
	(4, 'user.role', NULL, NULL, '用户设置角色', 1, 0, NULL, NULL, 0),
	(5, 'user.grant', NULL, NULL, '用户设置权限', 1, 0, NULL, NULL, 0),
	(6, 'user.edit', NULL, NULL, '用户编辑', 1, 0, NULL, NULL, 0),
	(7, 'user.delete', NULL, NULL, '用户删除', 1, 0, NULL, NULL, 0),
	(8, 'role.list', NULL, NULL, '角色管理', 1, 0, NULL, NULL, 0),
	(9, 'role.add', NULL, NULL, '角色添加', 1, 0, NULL, NULL, 0),
	(10, 'role.grant', NULL, NULL, '角色设置权限', 1, 0, NULL, NULL, 0),
	(11, 'role.edit', NULL, NULL, '角色编辑', 1, 0, NULL, NULL, 0),
	(12, 'role.delete', NULL, NULL, '角色删除', 1, 0, NULL, NULL, 0),
	(13, 'permission.list', NULL, NULL, '权限管理', 1, 0, NULL, NULL, 0),
	(14, 'permission.add', NULL, NULL, '权限添加', 1, 0, NULL, NULL, 0),
	(15, 'permission.edit', NULL, NULL, '编辑权限', 1, 0, NULL, NULL, 0),
	(16, 'permission.delete', NULL, NULL, '删除权限', 1, 0, NULL, NULL, 0),
	(17, 'config.list', NULL, NULL, '配置管理', 1, 0, NULL, NULL, 0),
	(18, 'config.add', NULL, NULL, '配置添加', 1, 0, NULL, NULL, 0),
	(19, 'config.edit', NULL, NULL, '配置编辑', 1, 0, NULL, NULL, 0),
	(20, 'config.delete', NULL, NULL, '配置删除', 1, 0, NULL, NULL, 0),
	(21, 'config.wechat', NULL, NULL, '微信配置', 1, 0, NULL, NULL, 0),
	(22, 'group.list', NULL, NULL, '码本分组列表', 1, 0, NULL, NULL, 0),
	(23, 'group.add', NULL, NULL, '码本分组添加', 1, 0, NULL, NULL, 0),
	(24, 'group.edit', NULL, NULL, '码本分组编辑', 1, 0, NULL, NULL, 0),
	(25, 'group.detail', NULL, NULL, '码本分组详情', 1, 0, NULL, NULL, 0),
	(26, 'group.delete', NULL, NULL, '码本分组状态', 1, 0, NULL, NULL, 0),
	(27, 'codebook.list', NULL, NULL, '数据列表', 1, 0, NULL, NULL, 0),
	(28, 'codebook.add', NULL, NULL, '数据添加', 1, 0, NULL, NULL, 0),
	(29, 'codebook.edit', NULL, NULL, '数据编辑', 1, 0, NULL, NULL, 0),
	(30, 'codebook.delete', NULL, NULL, '数据状态', 1, 0, NULL, NULL, 0),
	(31, 'branch.list', NULL, NULL, '分支机构列表', 1, 0, NULL, NULL, 0),
	(32, 'branch.add', NULL, NULL, '分支机构添加', 1, 0, NULL, NULL, 0),
	(33, 'branch.edit', NULL, NULL, '分支机构编辑', 1, 0, NULL, NULL, 0),
	(34, 'beanch.delete', NULL, NULL, '分支机构状态', 1, 0, NULL, NULL, 0),
	(35, 'dept.list', NULL, NULL, '部门列表', 1, 0, NULL, NULL, 0),
	(36, 'dept.add', NULL, NULL, '部门添加', 1, 0, NULL, NULL, 0),
	(37, 'dept.edit', NULL, NULL, '部门编辑', 1, 0, NULL, NULL, 0),
	(38, 'dept.delete', NULL, NULL, '部门状态', 1, 0, NULL, NULL, 0),
	(39, 'user.myRefactor', NULL, NULL, '重构数据Id', 1, 0, NULL, NULL, 0),
	(41, 'user.test', NULL, NULL, '测试', 1, 0, NULL, NULL, 0),
	(42, 'context.upload', NULL, NULL, '上传数据文件', 0, 0, NULL, NULL, 0),
	(43, 'user.dosave', NULL, NULL, '保存', 1, 0, NULL, NULL, 0),
	(44, 'context.canUp', NULL, NULL, '上传更新游戏流水', 1, 0, NULL, NULL, 0);
/*!40000 ALTER TABLE `t_permission` ENABLE KEYS */;


-- 导出  表 gamedate.t_role 结构
CREATE TABLE IF NOT EXISTS `t_role` (
  `id` bigint(64) NOT NULL AUTO_INCREMENT,
  `r_name` varchar(128) NOT NULL COMMENT '角色名称',
  `r_desc` varchar(128) DEFAULT NULL COMMENT '描述',
  `r_installed` tinyint(1) DEFAULT NULL COMMENT '是否内置角色标识',
  PRIMARY KEY (`id`),
  UNIQUE KEY `r_name` (`r_name`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='角色表';

-- 正在导出表  gamedate.t_role 的数据：~2 rows (大约)
/*!40000 ALTER TABLE `t_role` DISABLE KEYS */;
INSERT INTO `t_role` (`id`, `r_name`, `r_desc`, `r_installed`) VALUES
	(1, 'admin', '平台管理员', 0),
	(2, '渠道用户', '只能查看游戏流水', 0);
/*!40000 ALTER TABLE `t_role` ENABLE KEYS */;


-- 导出  表 gamedate.t_role_permission 结构
CREATE TABLE IF NOT EXISTS `t_role_permission` (
  `id` bigint(64) NOT NULL AUTO_INCREMENT,
  `r_id` bigint(64) DEFAULT NULL COMMENT '角色id',
  `p_id` bigint(64) DEFAULT NULL COMMENT '权限id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=50 DEFAULT CHARSET=utf8 COMMENT='角色权限关系表';

-- 正在导出表  gamedate.t_role_permission 的数据：~46 rows (大约)
/*!40000 ALTER TABLE `t_role_permission` DISABLE KEYS */;
INSERT INTO `t_role_permission` (`id`, `r_id`, `p_id`) VALUES
	(1, 1, 1),
	(2, 1, 2),
	(3, 1, 3),
	(4, 1, 4),
	(5, 1, 5),
	(6, 1, 6),
	(7, 1, 7),
	(8, 1, 8),
	(9, 1, 9),
	(10, 1, 10),
	(11, 1, 11),
	(12, 1, 12),
	(13, 1, 13),
	(14, 1, 14),
	(15, 1, 15),
	(16, 1, 16),
	(17, 1, 17),
	(18, 1, 18),
	(19, 1, 19),
	(20, 1, 20),
	(21, 1, 21),
	(22, 1, 22),
	(23, 1, 23),
	(24, 1, 24),
	(25, 1, 25),
	(26, 1, 26),
	(27, 1, 27),
	(28, 1, 28),
	(29, 1, 29),
	(30, 1, 30),
	(31, 1, 31),
	(32, 1, 32),
	(33, 1, 33),
	(34, 1, 34),
	(35, 1, 35),
	(36, 1, 36),
	(37, 1, 37),
	(38, 1, 38),
	(39, 1, 39),
	(42, 1, 41),
	(43, 2, 22),
	(44, 1, 43),
	(45, 1, 42),
	(46, 2, 6),
	(47, 1, 44),
	(49, 2, 44);
/*!40000 ALTER TABLE `t_role_permission` ENABLE KEYS */;


-- 导出  表 gamedate.t_sys_config 结构
CREATE TABLE IF NOT EXISTS `t_sys_config` (
  `id` bigint(64) NOT NULL AUTO_INCREMENT,
  `cfg_key` varchar(128) NOT NULL COMMENT '配置键',
  `cfg_value` text COMMENT '配置值',
  `cfg_description` varchar(250) DEFAULT NULL COMMENT '配置说明',
  `cfg_installed` tinyint(1) DEFAULT NULL COMMENT '是否内置标识',
  PRIMARY KEY (`id`),
  UNIQUE KEY `cfg_key` (`cfg_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 正在导出表  gamedate.t_sys_config 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `t_sys_config` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_sys_config` ENABLE KEYS */;


-- 导出  表 gamedate.t_user 结构
CREATE TABLE IF NOT EXISTS `t_user` (
  `id` bigint(64) NOT NULL AUTO_INCREMENT,
  `u_name` varchar(128) NOT NULL COMMENT '用户登录名',
  `u_real_name` varchar(128) DEFAULT NULL COMMENT '用户真实姓名',
  `u_nick_name` varchar(128) DEFAULT NULL COMMENT '用户昵称',
  `u_pwd` varchar(128) DEFAULT NULL COMMENT '用户登录密码',
  `u_phone` varchar(128) DEFAULT NULL COMMENT '用户手机号',
  `u_email` varchar(128) DEFAULT NULL COMMENT '用户邮箱',
  `u_head_key` varchar(200) DEFAULT NULL COMMENT '用户头像',
  `u_create_time` datetime DEFAULT NULL COMMENT '用户创建时间',
  `u_status` varchar(20) DEFAULT NULL COMMENT '用户状态',
  `u_type` varchar(20) DEFAULT NULL COMMENT '用户类型',
  `is_manager` bit(1) DEFAULT b'0' COMMENT '是否超管，true的时候可以查看所有的游戏流水',
  `u_openid` varchar(128) DEFAULT NULL COMMENT '用户微信 openid',
  PRIMARY KEY (`id`),
  UNIQUE KEY `u_name` (`u_name`)
) ENGINE=InnoDB AUTO_INCREMENT=105 DEFAULT CHARSET=utf8 COMMENT='用户表';

-- 正在导出表  gamedate.t_user 的数据：~1 rows (大约)
/*!40000 ALTER TABLE `t_user` DISABLE KEYS */;
INSERT INTO `t_user` (`id`, `u_name`, `u_real_name`, `u_nick_name`, `u_pwd`, `u_phone`, `u_email`, `u_head_key`, `u_create_time`, `u_status`, `u_type`, `is_manager`, `u_openid`) VALUES
	(1, 'admin', 'Kerbores', 'Kerbores', 'dcdb15715d0ef12c60e90caa48ed55b7', '18996359755', 'kerbores@zhcs.club', NULL, '2017-12-27 10:22:40', 'ACTIVED', 'PLATFORM', b'1', NULL);
/*!40000 ALTER TABLE `t_user` ENABLE KEYS */;


-- 导出  表 gamedate.t_user_login 结构
CREATE TABLE IF NOT EXISTS `t_user_login` (
  `id` bigint(64) NOT NULL AUTO_INCREMENT,
  `login_user_id` bigint(64) DEFAULT NULL COMMENT '登录用户 id',
  `login_account` varchar(128) DEFAULT NULL COMMENT '登录账户',
  `login_time` datetime DEFAULT NULL COMMENT '登录时间',
  `login_ip` varchar(128) DEFAULT NULL COMMENT '登录 ip',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 正在导出表  gamedate.t_user_login 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `t_user_login` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_user_login` ENABLE KEYS */;


-- 导出  表 gamedate.t_user_permission 结构
CREATE TABLE IF NOT EXISTS `t_user_permission` (
  `id` bigint(64) NOT NULL AUTO_INCREMENT,
  `u_id` bigint(64) DEFAULT NULL COMMENT '用户id',
  `p_id` bigint(64) DEFAULT NULL COMMENT '权限id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=45 DEFAULT CHARSET=utf8 COMMENT='用户权限关系表';

-- 正在导出表  gamedate.t_user_permission 的数据：~42 rows (大约)
/*!40000 ALTER TABLE `t_user_permission` DISABLE KEYS */;
INSERT INTO `t_user_permission` (`id`, `u_id`, `p_id`) VALUES
	(3, 1, 1),
	(4, 1, 2),
	(5, 1, 3),
	(6, 1, 4),
	(7, 1, 5),
	(8, 1, 6),
	(9, 1, 7),
	(10, 1, 8),
	(11, 1, 9),
	(12, 1, 10),
	(13, 1, 11),
	(14, 1, 12),
	(15, 1, 13),
	(16, 1, 14),
	(17, 1, 15),
	(18, 1, 16),
	(19, 1, 17),
	(20, 1, 18),
	(21, 1, 19),
	(22, 1, 20),
	(23, 1, 21),
	(24, 1, 22),
	(25, 1, 23),
	(26, 1, 24),
	(27, 1, 25),
	(28, 1, 26),
	(29, 1, 27),
	(30, 1, 28),
	(31, 1, 29),
	(32, 1, 30),
	(33, 1, 31),
	(34, 1, 32),
	(35, 1, 33),
	(36, 1, 34),
	(37, 1, 35),
	(38, 1, 36),
	(39, 1, 37),
	(40, 1, 38),
	(41, 1, 39),
	(42, 1, 41),
	(43, 1, 42),
	(44, 1, 43);
/*!40000 ALTER TABLE `t_user_permission` ENABLE KEYS */;


-- 导出  表 gamedate.t_user_role 结构
CREATE TABLE IF NOT EXISTS `t_user_role` (
  `id` bigint(64) NOT NULL AUTO_INCREMENT,
  `u_id` bigint(64) DEFAULT NULL COMMENT '用户id',
  `r_id` bigint(64) DEFAULT NULL COMMENT '角色id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=64 DEFAULT CHARSET=utf8 COMMENT='用户角色关系表';

-- 正在导出表  gamedate.t_user_role 的数据：~56 rows (大约)
/*!40000 ALTER TABLE `t_user_role` DISABLE KEYS */;
INSERT INTO `t_user_role` (`id`, `u_id`, `r_id`) VALUES
	(1, 1, 1),
	(9, 44, 2),
	(10, 45, 2),
	(11, 46, 2),
	(12, 47, 2),
	(13, 48, 2),
	(14, 49, 2),
	(15, 50, 2),
	(16, 51, 2),
	(17, 52, 2),
	(18, 53, 2),
	(19, 54, 2),
	(20, 55, 2),
	(21, 56, 2),
	(22, 57, 2),
	(23, 58, 2),
	(24, 59, 2),
	(25, 60, 2),
	(26, 61, 2),
	(27, 62, 2),
	(28, 63, 2),
	(29, 64, 2),
	(30, 65, 2),
	(31, 66, 2),
	(32, 67, 2),
	(33, 68, 2),
	(34, 69, 2),
	(35, 70, 2),
	(36, 71, 2),
	(37, 72, 2),
	(38, 73, 2),
	(39, 74, 2),
	(40, 75, 2),
	(41, 76, 2),
	(42, 77, 2),
	(43, 78, 2),
	(44, 79, 2),
	(45, 80, 2),
	(46, 81, 2),
	(47, 82, 2),
	(48, 86, 2),
	(49, 85, 2),
	(50, 84, 2),
	(51, 87, 2),
	(52, 88, 2),
	(53, 89, 2),
	(54, 91, 2),
	(55, 93, 2),
	(56, 94, 2),
	(57, 96, 2),
	(58, 97, 2),
	(59, 100, 2),
	(60, 101, 2),
	(61, 102, 2),
	(62, 103, 2),
	(63, 104, 2);
/*!40000 ALTER TABLE `t_user_role` ENABLE KEYS */;


-- 导出  表 gamedate.t_wechat_config 结构
CREATE TABLE IF NOT EXISTS `t_wechat_config` (
  `id` bigint(64) NOT NULL AUTO_INCREMENT,
  `wc_app_id` varchar(128) DEFAULT NULL COMMENT '微信appid',
  `wc_app_secret` varchar(128) DEFAULT NULL COMMENT '微信appsecret',
  `wc_token` varchar(128) DEFAULT NULL COMMENT '微信token',
  `wc_encoding_aes_key` varchar(128) DEFAULT NULL COMMENT '微信encodingAesKey',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='微信配置';

-- 正在导出表  gamedate.t_wechat_config 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `t_wechat_config` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_wechat_config` ENABLE KEYS */;


-- 导出  表 gamedate.t_wechat_menu 结构
CREATE TABLE IF NOT EXISTS `t_wechat_menu` (
  `id` bigint(64) NOT NULL AUTO_INCREMENT,
  `m_parent_id` int(32) DEFAULT NULL COMMENT '父菜单 id',
  `m_name` varchar(128) DEFAULT NULL COMMENT '菜单名称',
  `m_description` varchar(128) DEFAULT NULL COMMENT '菜单描述',
  `m_index` int(32) DEFAULT NULL COMMENT '菜单序号',
  `m_type` varchar(20) DEFAULT NULL COMMENT '菜单类型',
  `m_action` varchar(128) DEFAULT NULL COMMENT '菜单动作',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='微信菜单';

-- 正在导出表  gamedate.t_wechat_menu 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `t_wechat_menu` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_wechat_menu` ENABLE KEYS */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
