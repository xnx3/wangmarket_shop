/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50623
 Source Host           : localhost
 Source Database       : wangmarket_shop_20210811

 Target Server Type    : MySQL
 Target Server Version : 50623
 File Encoding         : utf-8

 Date: 08/11/2021 16:37:35 PM
*/

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `agency`
-- ----------------------------
DROP TABLE IF EXISTS `agency`;
CREATE TABLE `agency` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` char(38) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '人名，公司名',
  `phone` char(20) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '代理的联系电话',
  `userid` int(11) DEFAULT NULL COMMENT '对应user.id',
  `reg_oss_have` int(11) DEFAULT NULL COMMENT '其客户注册成功后，会员所拥有的免费OSS空间',
  `oss_price` int(11) DEFAULT '1' COMMENT 'OSS空间的售价，单位是毛， 时间是年。  如填写30，则为每10M空间3元每年',
  `address` char(80) COLLATE utf8_unicode_ci DEFAULT '' COMMENT '办公地址',
  `qq` char(13) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '代理商的QQ',
  `site_size` int(11) DEFAULT '0' COMMENT '站点数量，站点余额。1个对应着一个网站/年',
  `version` int(11) DEFAULT '0' COMMENT '乐观锁',
  `parent_id` int(11) DEFAULT '0' COMMENT '推荐人id，父级代理的agency.id。若父级代理是总管理，则为0',
  `addtime` int(11) DEFAULT '0' COMMENT '开通时间',
  `expiretime` int(11) DEFAULT '0' COMMENT '到期时间。按年进行续费（站币）',
  `state` tinyint(2) DEFAULT '1' COMMENT '代理状态，1正常；2冻结',
  `allow_create_sub_agency` tinyint(1) DEFAULT '0' COMMENT '此代理是否允许开通下级代理，是否有开通下级代理的功能。0不允许，1允许',
  `allow_sub_agency_create_sub` tinyint(1) DEFAULT '0' COMMENT '若此代理允许开通下级代理，开通的下级代理是否允许继续开通其下级代理功能。0不允许，1允许',
  `money` int(11) DEFAULT '0' COMMENT '账户余额，单位是分',
  PRIMARY KEY (`id`),
  KEY `userid` (`userid`,`parent_id`,`expiretime`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=52 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='代理商信息';

-- ----------------------------
--  Records of `agency`
-- ----------------------------
BEGIN;
INSERT INTO `agency` VALUES ('51', '管雷鸣', '17000000001', '392', '1024', '120', '山东潍坊', '921153866', '99999999', '0', '0', '1512818402', '2143123200', '1', '0', '0', '0');
COMMIT;

-- ----------------------------
--  Table structure for `agency_data`
-- ----------------------------
DROP TABLE IF EXISTS `agency_data`;
CREATE TABLE `agency_data` (
  `id` int(11) DEFAULT NULL COMMENT '对应 agency.id',
  `notice` text COLLATE utf8_unicode_ci COMMENT '代理的公告信息，显示给下级用户看的'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='代理表的变长字段表，存储代理的公告等';

-- ----------------------------
--  Table structure for `pay_log`
-- ----------------------------
DROP TABLE IF EXISTS `pay_log`;
CREATE TABLE `pay_log` (
  `id` int(111) NOT NULL AUTO_INCREMENT,
  `channel` char(10) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '支付通道，alipay：支付宝； wx：微信，同ping++的channel，若中间有下划线，去掉下划线。',
  `addtime` int(11) DEFAULT NULL COMMENT '支付时间',
  `money` float(6,2) DEFAULT NULL COMMENT '付款金额，单位：元',
  `orderno` char(12) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '订单号，2个随机数＋10位linux时间戳',
  `userid` int(11) DEFAULT NULL COMMENT '支付的用户，关联user.id',
  PRIMARY KEY (`id`),
  KEY `orderno` (`orderno`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='支付成功的日志记录（暂未用到，预留）';

-- ----------------------------
--  Table structure for `permission`
-- ----------------------------
DROP TABLE IF EXISTS `permission`;
CREATE TABLE `permission` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `description` char(100) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '描述信息，备注，只是给后台设置权限的人看的',
  `url` char(80) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '资源url',
  `name` char(50) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '名字，菜单的名字，显示给用户的',
  `parent_id` int(11) DEFAULT NULL COMMENT '上级资源的id',
  `percode` char(80) COLLATE utf8_unicode_ci DEFAULT NULL,
  `menu` smallint(6) DEFAULT NULL,
  `rank` int(11) DEFAULT '0' COMMENT '排序，数字越小越靠前',
  `icon` char(100) COLLATE utf8_unicode_ci DEFAULT '' COMMENT '图标字符，这里是layui 的图标 ， https://www.layui.com/doc/element/icon.html ，这里存的是 unicode  字符，如  &#xe60c;',
  PRIMARY KEY (`id`),
  UNIQUE KEY `url` (`url`,`name`,`percode`),
  KEY `parent_id` (`parent_id`),
  KEY `suoyin_index` (`menu`,`rank`)
) ENGINE=InnoDB AUTO_INCREMENT=123 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='Shiro权限管理中的资源';

-- ----------------------------
--  Records of `permission`
-- ----------------------------
BEGIN;
INSERT INTO `permission` VALUES ('12', '后台的用户管理', '/admin/user/list.do', '用户管理', '0', 'adminUser', '1', '1', '&#xe612;'), ('13', '后台用户管理下的菜单', '/admin/user/list.do', '用户列表', '12', 'adminUserList', null, '0', ''), ('14', '后台用户管理下的菜单', '/admin/user/delete.do', '删除用户', '12', 'adminUserDelete', null, '0', ''), ('15', '管理后台－系统管理栏目', '/admin/system/index.do', '系统管理', '0', 'adminSystem', '1', '4', '&#xe614;'), ('16', '管理后台－系统管理－系统参数、系统变量', '/admin/system/variableList.do', '系统变量', '15', 'adminSystemVariable', '1', '0', ''), ('18', '退出登录，注销登录状态', '/user/logout.do', '退出登录', '0', 'userLogout', '1', '20', '&#xe633;'), ('21', '更改当前登录的密码', 'javascript:updatePassword();', '更改密码', '0', 'adminUserUpdatePassword', '1', '19', '&#xe642;'), ('44', '后台，权限管理', '/admin/role/roleList.do', '权限管理', '0', 'adminRole', '1', '3', '&#xe628;'), ('46', '后台，权限管理，新增、编辑角色', '/admin/role/editRole.do', '编辑角色', '44', 'adminRoleRole', null, '101', ''), ('48', '后台，权限管理，角色列表', '/admin/role/roleList.do', '角色管理', '44', 'adminRoleRoleList', '1', '1', ''), ('49', '后台，权限管理，删除角色', '/admin/role/deleteRole.do', '删除角色', '44', 'adminRoleDeleteRole', null, '102', ''), ('51', '后台，权限管理，资源Permission的添加、编辑功能', '/admin/role/editPermission.do', '编辑资源', '44', 'adminRolePermission', null, '103', ''), ('53', '后台，权限管理，资源Permission列表', '/admin/role/permissionList.do', '资源管理', '44', 'adminRolePermissionList', '1', '2', ''), ('54', '后台，权限管理，删除资源Permission', '/admin/role/deletePermission.do', '删除资源', '44', 'adminRoleDeletePermission', null, '104', ''), ('55', '后台，权限管理，编辑角色下资源', '/admin/role/editRolePermission.do', '编辑角色下资源', '44', 'adminRoleEditRolePermission', null, '105', ''), ('56', '后台，权限管理，编辑用户所属角色', '/admin/role/editUserRole.do', '编辑用户所属角色', '44', 'adminRoleEditUserRole', null, '106', ''), ('71', '后台，日志管理', '/admin/log/list.do', '日志统计', '0', 'adminLog', '1', '5', '&#xe62c;'), ('72', '后台，日志管理，用户动作的日志列表', '/admin/log/list.do', '用户动作', '71', 'adminLogList', '1', '1', ''), ('74', '管理后台－系统管理，新增、修改系统的全局变量', '/admin/system/variable.do', '修改变量', '15', 'adminSystemVariable', null, '0', ''), ('80', '后台，用户管理，查看用户详情', '/admin/user/view.do', '用户详情', '12', 'adminUserView', null, '0', ''), ('81', '后台，用户管理，冻结、解除冻结会员。冻结后用户将不能登录', '/admin/user/updateFreeze.do', '冻结用户', '12', 'adminUserUpdateFreeze', null, '0', ''), ('82', '后台，历史发送的短信验证码', '/admin/smslog/list.do', '短信验证', '0', 'adminSmsLogList', '1', '2', '&#xe63a;'), ('114', '后台管理首页，登录后台的话，需要授权此项，不然登录成功后仍然无法进入后台，被此页给拦截了', null, '管理后台', '0', 'adminIndex', null, '0', ''), ('115', '管理后台首页', '', '后台首页', '114', 'adminIndexIndex', null, '0', ''), ('116', '删除系统变量', 'admin/system/deleteVariable.do', '删除变量', '15', 'adminSystemDeleteVariable', null, '0', ''), ('117', '后台，日志管理，所有动作的日志图表', '/admin/log/cartogram.do', '动作统计', '71', 'adminLogCartogram', '1', '2', ''), ('120', '可以将某个资源设置为菜单是菜单项', '/admin/role/editPermissionMenu.do', '设为菜单', '44', 'adminRoleEditPermissionMenu', '0', '107', ''), ('121', '对资源进行排序', '/admin/role/savePermissionRank.do', '资源排序', '44', 'adminRoleEditPermissionRank', '0', '108', ''), ('122', '这里只有总管理才能使用这个功能，商家即使选上这个功能也没法用', '/shop/superadmin/store/list.do', '店铺管理', '0', 'ShopSuperAdminStoreController', '1', '1', '&#xe770;');
COMMIT;

-- ----------------------------
--  Table structure for `plugin_createstoreapi_authstorebind`
-- ----------------------------
DROP TABLE IF EXISTS `plugin_createstoreapi_authstorebind`;
CREATE TABLE `plugin_createstoreapi_authstorebind` (
  `id` int(11) NOT NULL COMMENT '店铺的id，对应 store.id',
  `auth` char(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '64位授权码',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
--  Table structure for `plugin_firstorderaward_award`
-- ----------------------------
DROP TABLE IF EXISTS `plugin_firstorderaward_award`;
CREATE TABLE `plugin_firstorderaward_award` (
  `id` int(11) NOT NULL COMMENT '对应 store.id， 是哪个商家的奖品规则',
  `goodsid` int(11) DEFAULT NULL COMMENT '奖品的商品id，对应 goods.id ，当推荐的用户下单购买消费成功后，会自动给推荐人一个0元的商品，这个便是给的商品的id',
  `is_use` int(11) DEFAULT '0' COMMENT '是否是在使用， 1使用，0不使用',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
--  Records of `plugin_firstorderaward_award`
-- ----------------------------
BEGIN;
INSERT INTO `plugin_firstorderaward_award` VALUES ('1', '15', '1'), ('219', null, '0'), ('341', null, '1'), ('4315', null, '0'), ('4321', null, '1'), ('4356', null, '0');
COMMIT;

-- ----------------------------
--  Table structure for `plugin_limitbuy_store`
-- ----------------------------
DROP TABLE IF EXISTS `plugin_limitbuy_store`;
CREATE TABLE `plugin_limitbuy_store` (
  `id` int(11) NOT NULL COMMENT '对应 store.id， 是哪个商家的奖品规则',
  `is_use` int(11) DEFAULT '0' COMMENT '是否是在使用， 1使用，0不使用',
  `limit_number` int(11) DEFAULT NULL COMMENT '限额的次数，限制购买多少次。（订单）',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
--  Table structure for `plugin_limitbuy_user`
-- ----------------------------
DROP TABLE IF EXISTS `plugin_limitbuy_user`;
CREATE TABLE `plugin_limitbuy_user` (
  `id` char(20) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'userid_storeid的组合体， 如  219_1',
  `limit_number` int(11) DEFAULT NULL COMMENT '限额的次数，限制购买多少次。（订单）',
  `storeid` int(11) DEFAULT NULL COMMENT '此用户拥有哪个站点的管理权。开通子账号会用到这个。如果这个有值，那么就是子账号了。对应 store.id',
  `use_number` int(11) DEFAULT NULL COMMENT '限额的次数，限制购买多少次。（订单）',
  `userid` int(11) DEFAULT NULL COMMENT '用户id，对应 User.id',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
--  Table structure for `plugin_pay_notice`
-- ----------------------------
DROP TABLE IF EXISTS `plugin_pay_notice`;
CREATE TABLE `plugin_pay_notice` (
  `id` int(11) NOT NULL COMMENT '对应 store.id， 是哪个商家的规则',
  `is_use` int(11) DEFAULT '0' COMMENT '是否是在使用， 1使用，0不使用',
  `phone` char(15) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '发送短信通知，发送到哪个手机号',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
--  Table structure for `plugin_sell_commission_log`
-- ----------------------------
DROP TABLE IF EXISTS `plugin_sell_commission_log`;
CREATE TABLE `plugin_sell_commission_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增，自动编号',
  `addtime` int(11) DEFAULT NULL COMMENT '此条记录产生的时间',
  `money` int(11) DEFAULT NULL COMMENT '收入的金额，单位是分',
  `orderid` int(11) DEFAULT NULL COMMENT '订单id， Order.id ，该佣金是哪个订单产生的',
  `storeid` int(11) DEFAULT NULL COMMENT '店铺id，该佣金是属于哪个店铺，要哪个店铺发放',
  `transfer_state` tinyint(2) DEFAULT NULL COMMENT '转账状态，结算状态，是否已经跟用户结算了。 1 已结算， 0未结算',
  `userid` int(11) DEFAULT NULL COMMENT '用户id，该佣金是属于哪个用户的',
  PRIMARY KEY (`id`),
  KEY `suoyin_index` (`userid`,`storeid`,`addtime`,`transfer_state`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
--  Table structure for `plugin_sell_storeset`
-- ----------------------------
DROP TABLE IF EXISTS `plugin_sell_storeset`;
CREATE TABLE `plugin_sell_storeset` (
  `id` int(11) NOT NULL COMMENT '对应 store.id， 是哪个商家的奖品规则',
  `dispose_day` int(2) DEFAULT '1' COMMENT '处理时间，也就是提交申请后，会在几个工作日内进行处理。这个字段主要是给用户端，给用户看的。',
  `first_commission` int(3) DEFAULT '0' COMMENT '一级分成， A推荐B注册，B消费完成后，A能获得百分之多少分成。 单位是百分之几。如这里填写2，则A能获得B实际支付的百分之2作为佣金',
  `is_use` int(11) DEFAULT '0' COMMENT '是否是在使用， 1使用，0不使用',
  `money` int(11) DEFAULT NULL COMMENT '提现时，需要金额满足多少钱，可提现金额大于这个钱，才允许提交提现申请。这里单位是分',
  `sms_notify` tinyint(11) DEFAULT NULL COMMENT '短信通知，是否开启短信通知， 0不开启，1开启。 开启这个的前提，是已经配置了短信接口。',
  `two_commission` int(3) DEFAULT '0' COMMENT '二级分成， A推荐B注册，B推荐C注册，C消费完成后，A能获得百分之多少分成。单位是百分之几。如这里填写2，则A能获得C实际支付的百分之2作为佣金',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
--  Table structure for `plugin_sell_tixian_log`
-- ----------------------------
DROP TABLE IF EXISTS `plugin_sell_tixian_log`;
CREATE TABLE `plugin_sell_tixian_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增，自动编号',
  `addtime` int(11) DEFAULT NULL COMMENT '此次申请提现的时间，10位时间戳',
  `card` char(30) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '卡号,收款账号',
  `money` int(11) DEFAULT NULL COMMENT '此次提现金额，单位是分',
  `phone` char(15) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '联系手机，如果出现什么问题，会打电话沟通',
  `state` tinyint(2) DEFAULT NULL COMMENT '当前状态。0申请中，1已通过并汇款，2已拒绝。拒绝后店家会主动联系这个客户说明情况，就不需要走线上了',
  `storeid` int(11) DEFAULT NULL COMMENT '此用户拥有哪个站点的管理权。开通子账号会用到这个。如果这个有值，那么就是子账号了。对应 store.id',
  `userid` int(11) DEFAULT NULL COMMENT '用户id，对应 User.id',
  `username` char(10) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '收款人姓名，收款的是谁',
  PRIMARY KEY (`id`),
  KEY `suoyin_index` (`userid`,`storeid`,`addtime`,`state`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
--  Table structure for `plugin_storesubaccount_user_role`
-- ----------------------------
DROP TABLE IF EXISTS `plugin_storesubaccount_user_role`;
CREATE TABLE `plugin_storesubaccount_user_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `menu` char(20) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '所管理的功能',
  `storeid` int(11) DEFAULT NULL COMMENT '对应商城id， store.id',
  `userid` int(11) DEFAULT NULL COMMENT '用户id',
  PRIMARY KEY (`id`),
  KEY `userid_index` (`userid`,`storeid`)
) ENGINE=MyISAM AUTO_INCREMENT=17 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
--  Records of `plugin_storesubaccount_user_role`
-- ----------------------------
BEGIN;
INSERT INTO `plugin_storesubaccount_user_role` VALUES ('1', 'xiugaimima', '1', '457'), ('2', 'plugin', '1', '457'), ('3', 'paySet', '1', '457'), ('4', 'storeset', '1', '457'), ('5', 'goodsTyle', '1', '457'), ('6', 'Api', '1', '457'), ('7', 'carouselImage', '1', '457'), ('8', 'order', '1', '457'), ('9', 'xiugaimima', '1', '458'), ('10', 'plugin', '1', '458'), ('11', 'paySet', '1', '458'), ('12', 'carouselImage', '1', '458'), ('13', 'storeset', '1', '458'), ('14', 'goodsTyle', '1', '458'), ('15', 'Api', '1', '458'), ('16', 'order', '1', '458');
COMMIT;

-- ----------------------------
--  Table structure for `plugin_weixinappletlogin_weixin_user`
-- ----------------------------
DROP TABLE IF EXISTS `plugin_weixinappletlogin_weixin_user`;
CREATE TABLE `plugin_weixinappletlogin_weixin_user` (
  `id` int(11) NOT NULL,
  `openid` char(40) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '用户关注微信公众号后，获取到的用户的openid',
  `unionid` char(40) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '用户的unionid',
  PRIMARY KEY (`id`),
  KEY `suoyin_index` (`openid`,`unionid`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
--  Records of `plugin_weixinappletlogin_weixin_user`
-- ----------------------------
BEGIN;
INSERT INTO `plugin_weixinappletlogin_weixin_user` VALUES ('472', 'oRsvU5PH90Q5ilAyUxUX-29ydJVs', ''), ('478', 'oRsvU5FUEqTzsEypq0eJIRKTtrfw', '');
COMMIT;

-- ----------------------------
--  Table structure for `role`
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` char(30) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '角色名',
  `description` char(50) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '角色说明',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='Shiro权限管理中的角色表';

-- ----------------------------
--  Records of `role`
-- ----------------------------
BEGIN;
INSERT INTO `role` VALUES ('8', '商家管理', '有权登录使用商家管理后台的'), ('9', '总管理', '总后台管理，超级管理员');
COMMIT;

-- ----------------------------
--  Table structure for `role_permission`
-- ----------------------------
DROP TABLE IF EXISTS `role_permission`;
CREATE TABLE `role_permission` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `roleid` int(11) DEFAULT NULL COMMENT '角色id，role.id，一个角色可以拥有多个permission资源',
  `permissionid` int(11) DEFAULT NULL COMMENT '资源id，permission.id，一个角色可以拥有多个permission资源',
  PRIMARY KEY (`id`),
  KEY `roleid` (`roleid`)
) ENGINE=InnoDB AUTO_INCREMENT=217 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='Shiro权限管理中，角色所拥有哪些资源的操作权限';

-- ----------------------------
--  Records of `role_permission`
-- ----------------------------
BEGIN;
INSERT INTO `role_permission` VALUES ('12', '9', '12'), ('13', '9', '13'), ('17', '9', '15'), ('18', '9', '16'), ('20', '9', '18'), ('23', '9', '21'), ('30', '9', '14'), ('49', '9', '44'), ('51', '9', '46'), ('53', '9', '48'), ('54', '9', '49'), ('56', '9', '51'), ('58', '9', '53'), ('59', '9', '54'), ('60', '9', '55'), ('61', '9', '56'), ('75', '9', '71'), ('76', '9', '72'), ('77', '9', '74'), ('85', '1', '18'), ('88', '1', '21'), ('101', '9', '80'), ('104', '9', '81'), ('109', '10', '1'), ('110', '10', '2'), ('111', '10', '18'), ('112', '10', '20'), ('113', '10', '21'), ('114', '10', '22'), ('115', '10', '75'), ('116', '10', '24'), ('117', '10', '25'), ('118', '10', '26'), ('119', '10', '27'), ('120', '10', '88'), ('121', '10', '89'), ('122', '10', '90'), ('123', '10', '91'), ('124', '11', '1'), ('125', '11', '2'), ('126', '11', '18'), ('127', '11', '19'), ('128', '11', '20'), ('129', '11', '21'), ('130', '11', '22'), ('131', '11', '75'), ('132', '11', '7'), ('133', '11', '9'), ('134', '11', '10'), ('135', '11', '11'), ('136', '11', '23'), ('137', '11', '24'), ('138', '11', '25'), ('139', '11', '26'), ('140', '11', '27'), ('141', '11', '88'), ('142', '11', '89'), ('143', '11', '90'), ('144', '11', '91'), ('145', '12', '1'), ('146', '12', '2'), ('147', '12', '18'), ('148', '12', '19'), ('149', '12', '20'), ('150', '12', '21'), ('151', '12', '22'), ('152', '12', '75'), ('153', '12', '88'), ('154', '12', '89'), ('155', '12', '90'), ('156', '12', '91'), ('162', '12', '4'), ('163', '12', '3'), ('164', '12', '28'), ('165', '12', '29'), ('166', '12', '30'), ('167', '12', '7'), ('168', '12', '9'), ('169', '12', '10'), ('170', '12', '11'), ('171', '12', '23'), ('172', '12', '24'), ('173', '12', '25'), ('174', '12', '26'), ('175', '12', '27'), ('176', '10', '19'), ('177', '10', '4'), ('178', '10', '3'), ('179', '10', '28'), ('180', '10', '29'), ('181', '10', '30'), ('182', '10', '7'), ('183', '10', '9'), ('184', '10', '10'), ('185', '10', '11'), ('186', '10', '23'), ('187', '10', '97'), ('195', '10', '105'), ('196', '10', '106'), ('197', '10', '107'), ('198', '10', '108'), ('199', '10', '109'), ('200', '10', '110'), ('201', '10', '111'), ('202', '10', '112'), ('203', '10', '113'), ('204', '9', '114'), ('205', '9', '115'), ('206', '10', '114'), ('207', '10', '115'), ('208', '9', '117'), ('209', '9', '116'), ('210', '10', '118'), ('211', '10', '119'), ('212', '9', '120'), ('213', '9', '121'), ('214', '9', '82'), ('216', '9', '122');
COMMIT;

-- ----------------------------
--  Table structure for `shop_address`
-- ----------------------------
DROP TABLE IF EXISTS `shop_address`;
CREATE TABLE `shop_address` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `address` char(150) DEFAULT NULL COMMENT '具体地址',
  `latitude` double(9,6) DEFAULT NULL COMMENT '经纬度',
  `longitude` double(9,6) DEFAULT NULL COMMENT '经纬度',
  `phone` char(13) DEFAULT NULL COMMENT '收货人手机号',
  `userid` int(11) DEFAULT NULL COMMENT '改地址所属用户，属于那个用户的，对应User.id',
  `username` char(10) DEFAULT NULL COMMENT '收货人用户姓名',
  `default_use` tinyint(2) DEFAULT '0' COMMENT '是否是默认使用的，1是默认使用的地址，0不是默认使用的。一个用户会有多个收货地址，但一个用户默认的收货地址只有一个',
  `house` char(80) DEFAULT '' COMMENT '具体房间号，如 17号楼2单元202室',
  `qu` char(20) DEFAULT '' COMMENT '所在的区，如 寒亭区',
  `sheng` char(20) DEFAULT '' COMMENT '所在的省，如 山东省',
  `shi` char(20) DEFAULT '' COMMENT '所在的市，如 潍坊市',
  PRIMARY KEY (`id`),
  KEY `suoyin_index` (`longitude`,`latitude`,`userid`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

-- ----------------------------
--  Records of `shop_address`
-- ----------------------------
BEGIN;
INSERT INTO `shop_address` VALUES ('1', 'sadfssdg阿斯顿阿斯顿', '0.000000', '0.000000', '1707', '1', 'guan是测试', '0', '', '', '', ''), ('5', '好多僵尸粉看巨额话费', '0.000000', '0.000000', '5545', '1', '哈哈哈', '0', '', '', '', ''), ('6', '山东潍坊市寒亭区开元街道通亭街亚星路路口向西20米路北，中国兽药饲料交易大厦十三楼E1308', '0.000000', '0.000000', '17076012262', '1', '管雷鸣', '1', '', '', '', ''), ('7', '33333', '0.000000', '0.000000', '344', '1', 'haha', '0', '', '', '', ''), ('8', '湖北', '0.000000', '0.000000', '15997778666', '398', 'ceshi520', '1', '', '', '', ''), ('10', '世界尽头', '1.333300', '1.333300', '1555555', '412', '渡劫', '1', '', '', '', ''), ('12', 'sgsdfsdf第三方史蒂夫史蒂夫史蒂夫史蒂夫史蒂夫史蒂夫收到sgsdfsdf第三方史蒂夫史蒂夫史蒂夫史蒂夫史蒂夫史蒂夫收到', '0.000000', '0.000000', '如444444', '411', '44444', '1', '', '', '', ''), ('22', '梧州市我的位置', '22.918280', '110.994900', '15526541989', '413', 'ggl', '0', '', '', '', ''), ('23', '广西壮族自治区梧州市岑溪市义州大道88号岑溪商贸中心', '22.915370', '110.995000', '15526541989', '413', 'ggl', '0', '', '', '', ''), ('24', '广西壮族自治区梧州市岑溪市水东街北水猪脚粉', '22.917499', '110.996238', '15526541989', '413', 'ggl', '0', '', '', '', ''), ('25', '在地图选择', '0.000000', '0.000000', '15526541999', '413', '555', '1', '', '', '', ''), ('26', 'sssssd', '0.000000', '0.000000', 'dddd', '411', 'sddd', '0', '', '', '', ''), ('27', '广西壮族自治区梧州市岑溪市义洲大道岑溪人民广场', '22.914580', '110.994590', '15526541989', '413', 'hh', '0', '', '', '', ''), ('28', '1', '0.000000', '0.000000', '1', '480', '1', '1', '', '', '', '');
COMMIT;

-- ----------------------------
--  Table structure for `shop_carousel_image`
-- ----------------------------
DROP TABLE IF EXISTS `shop_carousel_image`;
CREATE TABLE `shop_carousel_image` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `image_url` char(180) DEFAULT NULL COMMENT '轮播图url，绝对路径',
  `img_value` char(130) DEFAULT NULL COMMENT '值，如url的路径、商品的id',
  `name` char(40) DEFAULT NULL COMMENT '轮播图的名字，更多的是备注作用，给自己看的。用户看到的只是图片而已',
  `rank` int(11) DEFAULT NULL COMMENT '排序，数字越小越靠前',
  `type` tinyint(2) DEFAULT NULL COMMENT '类型，1：点击后到某个商品上，  2点击后到xxx模块上，  3点击后打开某个url，也就是打开一个h5页面',
  `storeid` int(11) DEFAULT '0' COMMENT '这个轮播图是哪个商家的，对应 store.id',
  PRIMARY KEY (`id`),
  KEY `suoyin_index` (`rank`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

-- ----------------------------
--  Records of `shop_carousel_image`
-- ----------------------------
BEGIN;
INSERT INTO `shop_carousel_image` VALUES ('1', '//wangmarket1577678564.oss-cn-qingdao.aliyuncs.com/slideshow/4ed0ea6be0414cd797792d64313f7226.jpg', '6', '王者归来', '6', '1', '1'), ('2', '//wangmarket1577678564.oss-cn-qingdao.aliyuncs.com/slideshow/719ea39d07b349ec98a73fc03a951318.jpg', '2', '欢乐不断', '9', '2', '1'), ('4', '//wangmarket1577678564.oss-cn-qingdao.aliyuncs.com/slideshow/16ac78c705344c549b1d889010c46c54.jpg', '3', '测试', '2', '2', '1'), ('8', '//cdn.shop.leimingyun.com/store/341/images/a2de3df36a9b485981b4412c0afe9bd9.png', '', 'ddd', '3', '1', '341'), ('9', '//cdn.shop.leimingyun.com/store/4096/images/22f36f7ce8664229a56c7acf7fc7ba65.jpg', '333444', '影音系统', '1', '1', '4096'), ('10', '//cdn.shop.leimingyun.com/store/4221/images/16367f5adebd415a8d6dcf5f3d52ab9b.jpg', '111', '合伙人', '1', '1', '4221'), ('12', '//cdn.shop.leimingyun.com/store/4338/images/fb85faea27e442d19c5d215ceeadfcf2.jpg', '1', 'ad', '1', '1', '4338');
COMMIT;

-- ----------------------------
--  Table structure for `shop_cart`
-- ----------------------------
DROP TABLE IF EXISTS `shop_cart`;
CREATE TABLE `shop_cart` (
  `id` int(11) NOT NULL,
  `text` text COMMENT '购物车信息，json格式',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

-- ----------------------------
--  Records of `shop_cart`
-- ----------------------------
BEGIN;
INSERT INTO `shop_cart` VALUES ('1', '{\"storeCartMap\":{},\"number\":3,\"money\":1800}'), ('393', '{\"storeCartMap\":{\"1\":{\"goodsCartMap\":{\"7\":{\"goods\":{\"addtime\":1581920717,\"id\":7,\"inventory\":530,\"originalPrice\":800,\"price\":2,\"putaway\":1,\"sale\":13,\"storeid\":1,\"title\":\"零食混合坚果恰恰果仁干果小黄袋\",\"titlepic\":\"//cdn.shop.leimingyun.com/store/1/images/ed1c4f9e107547f7b775181d281b7834.jpg\",\"typeid\":4,\"units\":\"斤\",\"userBuyRestrict\":0},\"number\":6,\"money\":12,\"selected\":0,\"exceptional\":0,\"exceptionalInfo\":\"\"}},\"number\":0,\"money\":0,\"store\":{\"address\":\"亚华大酒店\",\"addtime\":1582872019,\"city\":\"潍坊市\",\"contacts\":\"管雷鸣\",\"district\":\"寒亭区\",\"head\":\"//cdn.shop.leimingyun.com/slideshow/e57cf791afa44fe19f7a8d7e1f5bd6d7.png\",\"id\":1,\"latitude\":111.21735,\"longitude\":23.117628,\"name\":\"管大店铺1\",\"notice\":\"bie\",\"phone\":\"17076012262\",\"province\":\"山东省\",\"sale\":128,\"state\":1}}},\"number\":0,\"money\":0}'), ('394', '{\"storeCartMap\":{\"0\":{\"goodsCartMap\":{\"5\":{\"goods\":{\"addtime\":1581746682,\"alarmNum\":1,\"fakeSale\":10,\"id\":5,\"inventory\":10,\"isdelete\":1,\"originalPrice\":800,\"price\":600,\"putaway\":1,\"sale\":0,\"storeid\":1,\"title\":\"花生\",\"titlepic\":\"//cdn.weiunity.com/site/341/news/b67717924c8340bba321c7bceb884657.png\",\"typeid\":2,\"units\":\"斤\",\"updatetime\":1581918959,\"userBuyRestrict\":0,\"version\":21},\"number\":5,\"money\":3000,\"selected\":1,\"exceptional\":0,\"exceptionalInfo\":\"\"},\"6\":{\"goods\":{\"addtime\":1581919619,\"alarmNum\":1,\"fakeSale\":1,\"id\":6,\"inventory\":10,\"isdelete\":0,\"originalPrice\":800,\"price\":600,\"putaway\":1,\"sale\":0,\"storeid\":0,\"title\":\"猪肉\",\"titlepic\":\"goodsList/061e62e4f0c445d0898aa39413a498f4.jpg\",\"typeid\":1,\"units\":\"斤\",\"updatetime\":1581928676,\"userBuyRestrict\":0,\"version\":6},\"number\":4,\"money\":2400,\"selected\":1,\"exceptional\":0,\"exceptionalInfo\":\"\"}},\"number\":9,\"money\":5400,\"store\":null}},\"number\":9,\"money\":5400}'), ('397', '{\"storeCartMap\":{\"1\":{\"goodsCartMap\":{\"7\":{\"goods\":{\"addtime\":1581920717,\"id\":7,\"inventory\":551,\"originalPrice\":800,\"price\":2,\"putaway\":1,\"sale\":1,\"storeid\":1,\"title\":\"土豆\",\"titlepic\":\"//wangmarket1581924839.oss-cn-beijing.aliyuncs.com/goodsList/98dbfec08ef34edfb045c05c1671e437.png\",\"typeid\":1,\"units\":\"斤\",\"userBuyRestrict\":0},\"number\":1,\"money\":2,\"selected\":1,\"exceptional\":0,\"exceptionalInfo\":\"\"}},\"number\":1,\"money\":2,\"store\":{\"address\":\"亚华大酒店\",\"addtime\":1582872019,\"city\":\"潍坊市\",\"contacts\":\"管雷鸣\",\"district\":\"寒亭区\",\"head\":\"//cdn.shop.leimingyun.com/slideshow/e57cf791afa44fe19f7a8d7e1f5bd6d7.png\",\"id\":1,\"latitude\":111.21735,\"longitude\":23.117628,\"name\":\"管大店铺\",\"notice\":\"bie\",\"phone\":\"17076012262\",\"province\":\"山东省\",\"sale\":61,\"state\":1}}},\"number\":1,\"money\":2}'), ('398', '{\"storeCartMap\":{\"1\":{\"goodsCartMap\":{\"6\":{\"goods\":{\"addtime\":1581919619,\"id\":6,\"inventory\":753,\"originalPrice\":800,\"price\":1,\"putaway\":1,\"sale\":1,\"storeid\":1,\"title\":\"猪肉\",\"titlepic\":\"//cdn.shop.leimingyun.com/goodsList/3b4592a232204fb58a1318e604683ec8.jpg\",\"typeid\":1,\"units\":\"斤\",\"userBuyRestrict\":0},\"number\":4,\"money\":4,\"selected\":1,\"exceptional\":0,\"exceptionalInfo\":\"\"},\"7\":{\"goods\":{\"addtime\":1581920717,\"id\":7,\"inventory\":544,\"originalPrice\":800,\"price\":2,\"putaway\":1,\"sale\":1,\"storeid\":1,\"title\":\"土豆\",\"titlepic\":\"//wangmarket1581924839.oss-cn-beijing.aliyuncs.com/goodsList/98dbfec08ef34edfb045c05c1671e437.png\",\"typeid\":1,\"units\":\"斤\",\"userBuyRestrict\":0},\"number\":4,\"money\":8,\"selected\":0,\"exceptional\":0,\"exceptionalInfo\":\"\"}},\"number\":4,\"money\":4,\"store\":{\"address\":\"亚华大酒店\",\"addtime\":1582872019,\"city\":\"潍坊市\",\"contacts\":\"管雷鸣\",\"district\":\"寒亭区\",\"head\":\"//cdn.shop.leimingyun.com/slideshow/e57cf791afa44fe19f7a8d7e1f5bd6d7.png\",\"id\":1,\"latitude\":111.21735,\"longitude\":23.117628,\"name\":\"管大店铺\",\"notice\":\"bie\",\"phone\":\"17076012262\",\"province\":\"山东省\",\"sale\":87,\"state\":1}}},\"number\":4,\"money\":4}'), ('411', '{\"storeCartMap\":{\"1\":{\"goodsCartMap\":{\"10\":{\"goods\":{\"addtime\":1582005476,\"id\":10,\"inventory\":256,\"originalPrice\":800,\"price\":1,\"putaway\":1,\"sale\":32,\"storeid\":1,\"title\":\"沙发\",\"titlepic\":\"//cdn.shop.leimingyun.com/store/1/images/9a1511fa5d8548f1b02cc7db68f3af72.jpg\",\"typeid\":3,\"units\":\"个\",\"userBuyRestrict\":0},\"number\":2,\"money\":2,\"selected\":1,\"exceptional\":0,\"exceptionalInfo\":\"\"}},\"number\":2,\"money\":2,\"store\":{\"address\":\"亚华大酒店\",\"addtime\":1582872019,\"city\":\"潍坊市\",\"contacts\":\"管雷鸣\",\"district\":\"寒亭区\",\"head\":\"//cdn.shop.leimingyun.com/slideshow/e57cf791afa44fe19f7a8d7e1f5bd6d7.png\",\"id\":1,\"latitude\":111.21735,\"longitude\":23.117628,\"name\":\"管大店铺1\",\"notice\":\"bie\",\"phone\":\"17076012262\",\"province\":\"山东省\",\"sale\":176,\"state\":1}}},\"number\":2,\"money\":2}'), ('412', '{\"storeCartMap\":{},\"number\":0,\"money\":0}'), ('413', '{\"storeCartMap\":{\"1\":{\"goodsCartMap\":{\"10\":{\"goods\":{\"addtime\":1582005476,\"id\":10,\"inventory\":265,\"originalPrice\":800,\"price\":1,\"putaway\":1,\"sale\":23,\"storeid\":1,\"title\":\"沙发\",\"titlepic\":\"//cdn.shop.leimingyun.com/store/1/images/9a1511fa5d8548f1b02cc7db68f3af72.jpg\",\"typeid\":3,\"units\":\"个\",\"userBuyRestrict\":0},\"number\":1,\"money\":1,\"selected\":1,\"exceptional\":0,\"exceptionalInfo\":\"\"}},\"number\":1,\"money\":1,\"store\":{\"address\":\"亚华大酒店\",\"addtime\":1582872019,\"city\":\"潍坊市\",\"contacts\":\"管雷鸣\",\"district\":\"寒亭区\",\"head\":\"//cdn.shop.leimingyun.com/slideshow/e57cf791afa44fe19f7a8d7e1f5bd6d7.png\",\"id\":1,\"latitude\":111.21735,\"longitude\":23.117628,\"name\":\"管大店铺1\",\"notice\":\"bie\",\"phone\":\"17076012262\",\"province\":\"山东省\",\"sale\":185,\"state\":1}}},\"number\":1,\"money\":1}'), ('418', '{\"storeCartMap\":{},\"number\":0,\"money\":0}'), ('419', '{\"storeCartMap\":{\"1\":{\"goodsCartMap\":{\"10\":{\"goods\":{\"addtime\":1582005476,\"id\":10,\"inventory\":244,\"originalPrice\":800,\"price\":1,\"putaway\":1,\"sale\":44,\"storeid\":1,\"title\":\"沙发\",\"titlepic\":\"//cdn.shop.leimingyun.com/store/1/images/9a1511fa5d8548f1b02cc7db68f3af72.jpg\",\"typeid\":3,\"units\":\"个\",\"userBuyRestrict\":0},\"number\":1,\"money\":1,\"selected\":1,\"exceptional\":0,\"exceptionalInfo\":\"\"}},\"number\":1,\"money\":1,\"store\":{\"address\":\"亚华大酒店\",\"addtime\":1582872019,\"city\":\"潍坊市\",\"contacts\":\"管雷鸣\",\"district\":\"寒亭区\",\"head\":\"//cdn.shop.leimingyun.com/slideshow/e57cf791afa44fe19f7a8d7e1f5bd6d7.png\",\"id\":1,\"latitude\":111.21735,\"longitude\":23.117628,\"name\":\"管大店铺1\",\"notice\":\"bie\",\"phone\":\"17076012262\",\"province\":\"山东省\",\"sale\":188,\"state\":1}}},\"number\":1,\"money\":1}'), ('449', '{\"storeCartMap\":{},\"number\":0,\"money\":0}'), ('465', '{\"storeCartMap\":{},\"number\":0,\"money\":0}'), ('472', '{\"storeCartMap\":{},\"number\":0,\"money\":0}'), ('478', '{\"storeCartMap\":{},\"number\":0,\"money\":0}'), ('480', '{\"storeCartMap\":{},\"number\":0,\"money\":0}'), ('481', '{\"storeCartMap\":{},\"number\":0,\"money\":0}');
COMMIT;

-- ----------------------------
--  Table structure for `shop_freight`
-- ----------------------------
DROP TABLE IF EXISTS `shop_freight`;
CREATE TABLE `shop_freight` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `money` int(11) DEFAULT NULL COMMENT '运费，单位是分。',
  `order_max_money` int(11) DEFAULT NULL COMMENT '当金额超过多少钱后，就不再使用此运费规则。单位是分。比如 orderMinMoney设为0，orderMaxMoney 设为 1000，money设为500，则是订单金额低于10元时，有运费5元',
  `order_min_money` int(11) DEFAULT NULL COMMENT '当金额超过多少钱，使用此运费规则。单位是分',
  `storeid` int(11) DEFAULT NULL COMMENT '商家id,属于哪个商家的',
  PRIMARY KEY (`id`),
  KEY `suoyin_index` (`storeid`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
--  Table structure for `shop_goods`
-- ----------------------------
DROP TABLE IF EXISTS `shop_goods`;
CREATE TABLE `shop_goods` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `addtime` int(11) DEFAULT NULL COMMENT '商品添加时间，10位时间戳',
  `alarm_num` int(11) DEFAULT NULL COMMENT '告警数量，库存量低于这个数，会通知商家告警，提醒商家该加库存了',
  `fake_sale` int(11) DEFAULT NULL COMMENT '假的已售数量，比如店家想顾客看到的已售数量多增加500，那这里数值就是500，假的，额外增加的数量。用户实际看到的数量是 sale+fakeSale 的和',
  `inventory` int(11) DEFAULT NULL COMMENT '库存数量',
  `isdelete` tinyint(2) DEFAULT NULL COMMENT '该商品是否已被删除',
  `original_price` int(11) DEFAULT NULL COMMENT '原价，单位是分，好看用的，显示出来一个价格，加一条删除线',
  `price` int(11) DEFAULT NULL COMMENT '单价，单位是分',
  `putaway` tinyint(2) DEFAULT NULL COMMENT '是否上架在售，1出售中，0已下架',
  `sale` int(11) DEFAULT NULL COMMENT '已售数量，只要出售的，成功的，都记入这里。当然，如果退货了，这里就要减去了。这里是根据order进行筛选的。每成功一次或者退款一次，都会重新select count 统计一次',
  `storeid` int(11) DEFAULT NULL COMMENT '该商品是属于哪个店铺的，对应 Store.id',
  `title` char(40) DEFAULT NULL COMMENT '商品标题',
  `titlepic` char(150) DEFAULT NULL COMMENT '该商品的标题图片、列表图片,图片的绝对路径',
  `typeid` int(11) DEFAULT NULL COMMENT '该商品所属哪个分类下的，对应 GoodsType.id',
  `units` char(5) DEFAULT NULL COMMENT '计量，单位。如个、斤、条',
  `updatetime` int(11) DEFAULT NULL COMMENT '商品最后更改时间，10位时间戳',
  `user_buy_restrict` int(11) DEFAULT NULL COMMENT '用户购买限制。如果值是0，则可以任意购买，没有什么限制，如果是1，则代表每个用户只能购买一个，如果是2，代表每个用户只能购买2个以内，不超过2个。 只要是下单了，未退单成功的，都算是购买了',
  `version` int(11) DEFAULT '0' COMMENT '乐观锁',
  `note` char(100) DEFAULT NULL COMMENT '该商品的备注，只在后台显示',
  `rank` int(11) DEFAULT NULL COMMENT '排序，数字越小越靠前',
  `intro` char(40) DEFAULT NULL COMMENT '简介说明，限制40个字符',
  PRIMARY KEY (`id`),
  KEY `suoyin_index` (`putaway`,`sale`,`fake_sale`,`price`,`addtime`,`storeid`,`typeid`,`isdelete`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

-- ----------------------------
--  Records of `shop_goods`
-- ----------------------------
BEGIN;
INSERT INTO `shop_goods` VALUES ('6', '1581919619', '1', '1', '749', '0', '800', '51', '1', '7', '1', '松崎 卡尺高精度电子数显游标卡尺', '//cdn.shop.leimingyun.com/store/1/images/19d21648e1ce47179d726c2516503977.jpg', '5', '斤', '1585124870', '0', '31', null, '0', null), ('7', '1581920717', '1', '1', '530', '0', '800', '2', '1', '12', '1', '零食混合坚果恰恰果仁干果小黄袋', '//cdn.shop.leimingyun.com/store/1/images/ed1c4f9e107547f7b775181d281b7834.jpg', '4', '斤', '1585124887', '0', '28', null, '0', null), ('10', '1582005476', '4', '0', '262', '0', '800', '1', '1', '26', '1', '沙发', '//cdn.shop.leimingyun.com/store/1/images/9a1511fa5d8548f1b02cc7db68f3af72.jpg', '3', '个', '1586060094', '0', '36', null, '0', null), ('11', '1583049951', '2', '20', '1003', '0', '2300', '1', '1', '11', '1', '圣菲火（SHENGFEIHUO）手电筒小型', '//cdn.shop.leimingyun.com/store/1/images/026645215ea64274aebddbc05736679b.jpg', '2', '个', '1584708088', '0', '13', null, '0', null);
COMMIT;

-- ----------------------------
--  Table structure for `shop_goods_data`
-- ----------------------------
DROP TABLE IF EXISTS `shop_goods_data`;
CREATE TABLE `shop_goods_data` (
  `id` int(11) NOT NULL,
  `detail` mediumtext COMMENT '详情内容，富文本编辑区',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

-- ----------------------------
--  Records of `shop_goods_data`
-- ----------------------------
BEGIN;
INSERT INTO `shop_goods_data` VALUES ('6', '<p><img src=\"//cdn.shop.leimingyun.com/site/defaultparam1/news/20200320/1584707683678064374.jpg\" title=\"1584707683678064374.jpg\" alt=\"TB28ntab1uSBuNjSsplXXbe8pXa_!!2601841464.jpg\"/><img src=\"//cdn.shop.leimingyun.com/site/defaultparam1/news/20200320/1584707683667032052.jpg\" title=\"1584707683667032052.jpg\" alt=\"TB23wVybY9YBuNjy0FgXXcxcXXa_!!2601841464.jpg\"/></p>'), ('7', '<p><img src=\"//cdn.shop.leimingyun.com/site/defaultparam1/news/20200320/1584707909230089619.jpg\" title=\"1584707909230089619.jpg\" alt=\"O1CN01wAPgRV1KEUqvHhEjw_!!4023681132-0-scmitem6000.jpg\"/><img src=\"//cdn.shop.leimingyun.com/site/defaultparam1/news/20200320/1584707909233030967.jpg\" title=\"1584707909233030967.jpg\" alt=\"O1CN01p1MyIX1KEUqt3TAZD_!!4023681132-0-scmitem6000.jpg\"/></p>'), ('10', '<p><img src=\"//cdn.shop.leimingyun.com/site/defaultparam1/news/20200320/1584707249196079339.jpg\" title=\"1584707249196079339.jpg\" alt=\"O1CN01SxJVU421KvyWXVub9_!!3105116967.jpg\"/></p><p><img src=\"//cdn.shop.leimingyun.com/site/defaultparam1/news/20200320/1584707254669047427.jpg\" title=\"1584707254669047427.jpg\" alt=\"O1CN01SzpJrH21Kvxlx9SYZ_!!3105116967.jpg\"/><img src=\"//cdn.shop.leimingyun.com/site/defaultparam1/news/20200320/1584707261798038576.jpg\" title=\"1584707261798038576.jpg\" alt=\"O1CN01SxJVU421KvyWXVub9_!!3105116967.jpg\"/></p>'), ('11', '<p><img class=\"\" src=\"https://img10.360buyimg.com/imgzone/jfs/t1/42702/8/4647/137374/5cd517c8Eabda800a/39e9330a56535c93.jpg\" style=\"margin: 0px; padding: 0px; border: 0px; vertical-align: middle; color: rgb(102, 102, 102); font-family: tahoma, arial, \" microsoft=\"\" hiragino=\"\" sans=\"\" font-size:=\"\" font-style:=\"\" font-variant-ligatures:=\"\" font-variant-caps:=\"\" font-weight:=\"\" letter-spacing:=\"\" orphans:=\"\" text-align:=\"\" text-indent:=\"\" text-transform:=\"\" white-space:=\"\" widows:=\"\" word-spacing:=\"\" -webkit-text-stroke-width:=\"\" background-color:=\"\" text-decoration-style:=\"\" text-decoration-color:=\"\" width:=\"\" height:=\"\" max-width:=\"\"/><img class=\"\" src=\"https://img10.360buyimg.com/imgzone/jfs/t10990/279/2709631582/127483/693ba8fd/5cd517c9N21fe6b58.jpg\" style=\"margin: 0px; padding: 0px; border: 0px; vertical-align: middle; color: rgb(102, 102, 102); font-family: tahoma, arial, \" microsoft=\"\" hiragino=\"\" sans=\"\" font-size:=\"\" font-style:=\"\" font-variant-ligatures:=\"\" font-variant-caps:=\"\" font-weight:=\"\" letter-spacing:=\"\" orphans:=\"\" text-align:=\"\" text-indent:=\"\" text-transform:=\"\" white-space:=\"\" widows:=\"\" word-spacing:=\"\" -webkit-text-stroke-width:=\"\" background-color:=\"\" text-decoration-style:=\"\" text-decoration-color:=\"\" width:=\"\" height:=\"\" max-width:=\"\"/></p>');
COMMIT;

-- ----------------------------
--  Table structure for `shop_goods_image`
-- ----------------------------
DROP TABLE IF EXISTS `shop_goods_image`;
CREATE TABLE `shop_goods_image` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `goodsid` int(11) DEFAULT NULL COMMENT '对应商品id，Goods.id',
  `image_url` varchar(255) DEFAULT NULL COMMENT '图片的url绝对路径',
  `rank` int(11) DEFAULT NULL COMMENT '图片的排序，数字越小越靠前',
  PRIMARY KEY (`id`),
  KEY `suoyin_index` (`rank`,`goodsid`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

-- ----------------------------
--  Records of `shop_goods_image`
-- ----------------------------
BEGIN;
INSERT INTO `shop_goods_image` VALUES ('6', '6', '//cdn.shop.leimingyun.com/store/1/images/5fd999c864a54d219b20d69aba4dd6b6.jpg', '1'), ('7', '6', '//cdn.shop.leimingyun.com/goodsList/dcdc7a35e31d48188c992eba4149c389.jpg', '4'), ('9', '7', '//cdn.shop.leimingyun.com/store/1/images/cc92251ae7b04acb9317a6d5f0297a10.jpg', '1'), ('10', '10', '//cdn.shop.leimingyun.com/store/1/images/15e01d0dd685486791fc90c36aed2de5.jpg', '2'), ('11', '11', '//cdn.shop.leimingyun.com/store/1/images/0f9f8a968b03465589b647b2b7c60dac.png', '0'), ('12', '11', '//cdn.shop.leimingyun.com/store/1/images/72f6fb371254400ab4ffc66a520ad306.gif', '0'), ('15', '7', '//cdn.shop.leimingyun.com/store/1/images/447435a34cd1470f8869f5f325c696b9.jpg', '2');
COMMIT;

-- ----------------------------
--  Table structure for `shop_goods_type`
-- ----------------------------
DROP TABLE IF EXISTS `shop_goods_type`;
CREATE TABLE `shop_goods_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `icon` char(150) DEFAULT NULL COMMENT '分类的图标、图片',
  `isdelete` tinyint(2) DEFAULT NULL COMMENT '是否被删除',
  `rank` int(11) DEFAULT NULL COMMENT '分类间的排序，数字越小越靠前',
  `storeid` int(11) DEFAULT NULL COMMENT '对应店铺id, Store.id ， 该分类是属于那个店铺的',
  `title` char(20) DEFAULT NULL COMMENT '分类的名称',
  PRIMARY KEY (`id`),
  KEY `suoyin_index` (`rank`,`storeid`,`isdelete`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

-- ----------------------------
--  Records of `shop_goods_type`
-- ----------------------------
BEGIN;
INSERT INTO `shop_goods_type` VALUES ('1', '//cdn.shop.leimingyun.com/store/1/images/0505abd48c0e4283b06e85442858ff5c.png', '0', '1', '1', '农副馆'), ('2', '//cdn.shop.leimingyun.com/store/1/images/f9b133c7a2014d8c8e1829643ed2a52a.png', '0', '2', '1', '休闲零食'), ('3', '//wangmarket1577678564.oss-cn-qingdao.aliyuncs.com/goodsType/65589a47d76e43c4b0ee6f0c37aa1da8.jpg', '0', '3', '1', '酒水乳饮'), ('4', '//wangmarket1577678564.oss-cn-qingdao.aliyuncs.com/goodsType/7cc9e91476804996b3baee627ee4f36e.jpg', '0', '4', '1', '粮油调味'), ('5', '//cdn.shop.leimingyun.com/store/1/images/4e49c1d7f7cc41748e654a7335e487dd.png', '0', '5', '1', '餐食料理');
COMMIT;

-- ----------------------------
--  Table structure for `shop_order`
-- ----------------------------
DROP TABLE IF EXISTS `shop_order`;
CREATE TABLE `shop_order` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `address` char(100) DEFAULT NULL COMMENT '用户填写地址',
  `addtime` int(11) DEFAULT NULL COMMENT '订单创建时间，10为时间戳',
  `no` char(10) DEFAULT NULL COMMENT '订单号，这个是支付宝支付、微信支付传过去的订单号，也是给用户显示出来的订单号',
  `pay_money` int(11) DEFAULT NULL COMMENT '需要实际支付的金额,单位：元',
  `pay_time` int(11) DEFAULT NULL COMMENT '该订单支付的时间，10位时间戳',
  `remark` char(100) DEFAULT NULL COMMENT '买家的备注',
  `state` char(20) DEFAULT NULL COMMENT '订单状态',
  `storeid` int(11) DEFAULT NULL COMMENT '商家id，这个订单购买的商品是哪个商家的。如果一次购买多个店铺的商品，那么最终订单会分解为每个店铺一个订单',
  `total_money` int(11) DEFAULT NULL COMMENT '订单总金额,单位：元',
  `userid` int(11) DEFAULT NULL COMMENT '该订单所属的用户，是哪个用户下的单，对应 User.id',
  `version` int(11) DEFAULT '0' COMMENT '乐观锁',
  PRIMARY KEY (`id`),
  KEY `suoyin_index` (`no`,`storeid`,`userid`,`state`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

-- ----------------------------
--  Table structure for `shop_order_address`
-- ----------------------------
DROP TABLE IF EXISTS `shop_order_address`;
CREATE TABLE `shop_order_address` (
  `id` int(11) NOT NULL,
  `address` varchar(1000) DEFAULT NULL COMMENT '具体地址',
  `latitude` double(9,6) DEFAULT NULL COMMENT '经纬度',
  `longitude` double(9,6) DEFAULT NULL COMMENT '经纬度',
  `phone` varchar(100) DEFAULT NULL COMMENT '收货人手机号',
  `username` varchar(50) DEFAULT NULL COMMENT '收货人用户姓名',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

-- ----------------------------
--  Table structure for `shop_order_goods`
-- ----------------------------
DROP TABLE IF EXISTS `shop_order_goods`;
CREATE TABLE `shop_order_goods` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `price` int(11) DEFAULT NULL COMMENT '商品单价，单位是分，对应 Goods.price',
  `title` char(40) DEFAULT NULL COMMENT '商品名字，对应 Goods.title ，就是吧Goods的信息复制过来了，相当于给商品做了一个镜像',
  `titlepic` char(150) DEFAULT NULL COMMENT '商品的标题图片，列表图，对应 Goods.titlepic',
  `units` char(5) DEFAULT NULL COMMENT '商品单位，对应 Goods.units',
  `goodsid` int(11) DEFAULT NULL COMMENT '商品的id，对应 Goods.id',
  `number` int(11) DEFAULT NULL COMMENT '该商品的购买的数量',
  `orderid` int(11) DEFAULT NULL COMMENT '订单的ID，对应 Order.id',
  `userid` int(11) DEFAULT NULL COMMENT '购买者的用户ID，对应User.id',
  `goods_price` int(11) DEFAULT NULL COMMENT '商品单价，单位是分，对应 Goods.price',
  `goods_title` char(40) DEFAULT NULL COMMENT '商品名字，对应 Goods.title ，就是吧Goods的信息复制过来了，相当于给商品做了一个镜像',
  `goods_titlepic` char(100) DEFAULT NULL COMMENT '商品的标题图片，列表图，对应 Goods.titlepic',
  `goods_units` char(5) DEFAULT NULL COMMENT '商品单位，对应 Goods.units',
  PRIMARY KEY (`id`),
  KEY `suoyin_index` (`orderid`,`goodsid`,`userid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

-- ----------------------------
--  Table structure for `shop_order_refund`
-- ----------------------------
DROP TABLE IF EXISTS `shop_order_refund`;
CREATE TABLE `shop_order_refund` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `addtime` int(11) DEFAULT NULL COMMENT '添加时间，也就是提交时间,10位时间戳',
  `orderid` int(11) DEFAULT NULL COMMENT '属于哪个订单的，对应 Order.id',
  `reason` char(100) DEFAULT NULL COMMENT '退单的理由，原因',
  `state` tinyint(2) DEFAULT NULL COMMENT '该退单的状态，是否已经退单成功，还是正在进行中',
  `storeid` int(11) DEFAULT NULL COMMENT '属于哪个店铺的，对应 Store.id',
  `userid` int(11) DEFAULT NULL COMMENT '订单属于哪个用户的，对应 User.id',
  PRIMARY KEY (`id`),
  KEY `suoyin_index` (`userid`,`orderid`,`storeid`,`state`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

-- ----------------------------
--  Table structure for `shop_order_rule`
-- ----------------------------
DROP TABLE IF EXISTS `shop_order_rule`;
CREATE TABLE `shop_order_rule` (
  `id` int(11) NOT NULL,
  `distribution` tinyint(11) DEFAULT '1' COMMENT '是否使用配送中这个状态，如果没有，订单可以有已支付直接变为已完成。1使用，0不使用。默认是1使用',
  `refund` tinyint(11) DEFAULT '1' COMMENT '是否使用退款这个状态，也就是是否允许用户退款。1使用，0不使用。默认是1使用',
  `not_pay_timeout` int(11) DEFAULT '1800' COMMENT '订单如果创建订单了，但未支付，多长时间会自动取消订单，订单状态变为已取消。这里的单位是秒',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
--  Records of `shop_order_rule`
-- ----------------------------
BEGIN;
INSERT INTO `shop_order_rule` VALUES ('1', '0', '1', '1800');
COMMIT;

-- ----------------------------
--  Table structure for `shop_order_state_log`
-- ----------------------------
DROP TABLE IF EXISTS `shop_order_state_log`;
CREATE TABLE `shop_order_state_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `addtime` int(11) DEFAULT '0' COMMENT '此条记录的添加时间',
  `orderid` int(11) DEFAULT '0' COMMENT '此条记录对应的订单id',
  `state` char(20) COLLATE utf8_unicode_ci DEFAULT '' COMMENT '变化之后的订单状态，新的订单状态',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=229 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
--  Table structure for `shop_order_timeout`
-- ----------------------------
DROP TABLE IF EXISTS `shop_order_timeout`;
CREATE TABLE `shop_order_timeout` (
  `id` int(11) NOT NULL,
  `expiretime` int(11) DEFAULT '0' COMMENT '订单的过期时间，如果超过这个时间，说明这条记录已到定时时间了，该判断订单状态，进行转变状态了。同样，状态转变完了，也就是订单状态跟此条状态不一致了，此条信息就没有任何价值，要删除了',
  `state` char(20) COLLATE utf8_unicode_ci DEFAULT '' COMMENT '值跟订单状态一样，此记录添加时，所对应的订单状态,比如订单下单后，记录的此条信息，那么这条信息的这个状态便是刚下单但未支付的状态（跟订单状态一样）。限制20字符',
  PRIMARY KEY (`id`),
  KEY `suoyin_index` (`expiretime`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
--  Table structure for `shop_pay_log`
-- ----------------------------
DROP TABLE IF EXISTS `shop_pay_log`;
CREATE TABLE `shop_pay_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `addtime` int(11) DEFAULT '0' COMMENT '支付时间，10位时间戳',
  `channel` char(16) DEFAULT '' COMMENT '支付方式',
  `money` int(11) DEFAULT '0' COMMENT '支付金额，单位是分',
  `orderid` int(11) DEFAULT '0' COMMENT '订单号，订单id',
  `storeid` int(11) DEFAULT '0' COMMENT '支付的订单所属哪个商家',
  `userid` int(11) DEFAULT '0' COMMENT '支付的用户',
  PRIMARY KEY (`id`),
  KEY `suoyin_index` (`channel`,`userid`,`storeid`,`orderid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

-- ----------------------------
--  Table structure for `shop_pay_set`
-- ----------------------------
DROP TABLE IF EXISTS `shop_pay_set`;
CREATE TABLE `shop_pay_set` (
  `id` int(11) NOT NULL,
  `alipay_app_cert_public_key` varchar(255) DEFAULT NULL,
  `alipay_app_id` varchar(255) DEFAULT NULL,
  `alipay_app_private_key` varchar(4000) DEFAULT NULL,
  `alipay_cert_public_keyrsa2` varchar(255) DEFAULT NULL,
  `alipay_root_cert` varchar(255) DEFAULT NULL,
  `use_alipay` smallint(6) DEFAULT NULL,
  `use_private_pay` smallint(6) DEFAULT NULL,
  `use_weixin_pay` smallint(6) DEFAULT NULL,
  `weixin_applet_appid` varchar(255) DEFAULT NULL,
  `weixin_mch_id` varchar(255) DEFAULT NULL,
  `weixin_mch_key` varchar(255) DEFAULT NULL,
  `weixin_official_accounts_appsecret` varchar(70) DEFAULT '' COMMENT '微信公众号的 AppSecret',
  `weixin_official_accounts_appid` varchar(50) DEFAULT '' COMMENT '微信公众号的 AppId',
  `weixin_official_accounts_token` varchar(100) DEFAULT '' COMMENT '微信公众号的 token，这个是跟微信公众号中约定好的固定的token',
  `weixin_applet_appsecret` varchar(40) DEFAULT '' COMMENT '微信小程序的appSecret',
  `use_weixin_serviceprovider_pay` tinyint(2) DEFAULT '0' COMMENT '微信小程序的appSecret',
  `weixin_serivceprovider_sub_mch_id` varchar(30) DEFAULT '' COMMENT '子商户的mch_id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

-- ----------------------------
--  Records of `shop_pay_set`
-- ----------------------------
BEGIN;
INSERT INTO `shop_pay_set` VALUES ('0', null, null, null, null, null, null, null, '1', null, '', '', '', '', '', '', '0', ''), ('1', '', '', '', '', '', '1', '1', '1', '', '11111', '8888', '', '', 'xnx3Test', '', '1', '12');
COMMIT;

-- ----------------------------
--  Table structure for `shop_sms_set`
-- ----------------------------
DROP TABLE IF EXISTS `shop_sms_set`;
CREATE TABLE `shop_sms_set` (
  `id` int(11) NOT NULL,
  `password` char(30) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '短信平台登录的密码，30个字符内',
  `quota_day_ip` int(11) DEFAULT '30' COMMENT '发送限制，此店铺下某个ip一天最多能发多少条短信，默认30',
  `quota_day_phone` int(11) DEFAULT '5' COMMENT '发送限制，此店铺下某个手机号一天最多能发多少条短信。默认五条',
  `uid` int(11) DEFAULT '0' COMMENT '短信平台登录的uid',
  `use_sms` tinyint(2) DEFAULT '0' COMMENT '是否使用短信发送功能，启用短信接口的短信发送功能。1使用；0不使用。默认不使用。 ',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
--  Table structure for `shop_store`
-- ----------------------------
DROP TABLE IF EXISTS `shop_store`;
CREATE TABLE `shop_store` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `address` char(100) DEFAULT NULL COMMENT '店铺地址',
  `addtime` int(11) DEFAULT NULL COMMENT '店铺添加时间，10为时间戳',
  `city` char(20) DEFAULT NULL COMMENT '店铺所在位置-市',
  `contacts` char(10) DEFAULT NULL COMMENT '店铺联系人',
  `district` char(20) DEFAULT NULL COMMENT '店铺所在位置-区',
  `head` char(100) DEFAULT NULL COMMENT '店铺图片，图标，图片的绝对路径',
  `latitude` double(9,6) DEFAULT NULL COMMENT '店铺经纬度',
  `longitude` double(9,6) DEFAULT NULL COMMENT '店铺经纬度',
  `name` char(20) DEFAULT NULL COMMENT '店铺名字',
  `notice` char(150) DEFAULT NULL COMMENT '店铺公告',
  `phone` char(13) DEFAULT NULL COMMENT '店铺店家联系电话',
  `province` char(15) DEFAULT NULL COMMENT '店铺所在位置-省',
  `sale` int(11) DEFAULT '0' COMMENT '店铺已出售的商品总数量，总销量。这个一定是真实的，如果要造假数，可以在增加一个字段',
  `state` tinyint(2) DEFAULT NULL COMMENT '店铺状态，0审核中，1营业中，2已打烊',
  `userid` int(11) DEFAULT NULL COMMENT '店铺所属用户，哪个用户创建的，对应 User.id',
  `version` int(11) DEFAULT '0' COMMENT '乐观锁',
  `referrer_userid` int(11) DEFAULT '0' COMMENT '来源id，推荐人的user.id，上级的用户id。是哪个用户推荐这个商家入驻的',
  PRIMARY KEY (`id`),
  KEY `suoyin_index` (`userid`,`state`,`longitude`,`latitude`,`province`,`city`,`district`,`sale`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

-- ----------------------------
--  Records of `shop_store`
-- ----------------------------
BEGIN;
INSERT INTO `shop_store` VALUES ('1', '亚华大酒店', '1582872019', '潍坊市', '管雷鸣', '寒亭区', '//cdn.shop.leimingyun.com/slideshow/e57cf791afa44fe19f7a8d7e1f5bd6d7.png', '111.217350', '23.117628', '管大店铺1', 'bie', '17076000000', '山东省', '195', '1', '393', '39', '0');
COMMIT;

-- ----------------------------
--  Table structure for `shop_store_child_user`
-- ----------------------------
DROP TABLE IF EXISTS `shop_store_child_user`;
CREATE TABLE `shop_store_child_user` (
  `id` int(11) NOT NULL,
  `storeid` int(11) DEFAULT NULL COMMENT '此用户拥有哪个站点的管理权。开通子账号会用到这个。如果这个有值，那么就是子账号了。对应 store.id',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
--  Table structure for `shop_store_data`
-- ----------------------------
DROP TABLE IF EXISTS `shop_store_data`;
CREATE TABLE `shop_store_data` (
  `id` int(11) NOT NULL,
  `notice` text COLLATE utf8mb4_unicode_ci COMMENT '店铺公告',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
--  Records of `shop_store_data`
-- ----------------------------
BEGIN;
INSERT INTO `shop_store_data` VALUES ('1', '23');
COMMIT;

-- ----------------------------
--  Table structure for `shop_store_sms_log`
-- ----------------------------
DROP TABLE IF EXISTS `shop_store_sms_log`;
CREATE TABLE `shop_store_sms_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `addtime` int(11) DEFAULT NULL COMMENT '添加时间',
  `code` char(6) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '发送的验证码，6位数字',
  `ip` char(22) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '触发发送操作的客户ip地址',
  `phone` char(11) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '发送到的手机号',
  `storeid` int(11) DEFAULT NULL COMMENT '哪个店铺的',
  `type` char(10) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '类型',
  `used` tinyint(2) DEFAULT NULL COMMENT '是否被使用了,1已使用，0未使用',
  `userid` int(11) DEFAULT NULL COMMENT '哪个用户',
  PRIMARY KEY (`id`),
  KEY `suoyin_index` (`code`,`userid`,`used`,`type`,`addtime`,`phone`,`ip`,`storeid`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
--  Table structure for `shop_store_user`
-- ----------------------------
DROP TABLE IF EXISTS `shop_store_user`;
CREATE TABLE `shop_store_user` (
  `id` char(20) COLLATE utf8_unicode_ci NOT NULL,
  `storeid` int(11) DEFAULT NULL COMMENT '此用户拥有哪个站点的管理权。开通子账号会用到这个。如果这个有值，那么就是子账号了。对应 store.id',
  `userid` int(11) DEFAULT NULL COMMENT '用户id，对应 User.id',
  `referrerid` char(20) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '此用户的推荐人id, 注意，此处并不是 user.id 而是推荐人的 StoreUser.id ，这里面的值是userid_storeid的组合体， 如  219_1',
  PRIMARY KEY (`id`),
  KEY `suoyin_index` (`userid`,`storeid`)
) ENGINE=MyISAM AUTO_INCREMENT=535 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
--  Table structure for `sms_log`
-- ----------------------------
DROP TABLE IF EXISTS `sms_log`;
CREATE TABLE `sms_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `code` char(6) CHARACTER SET utf8 DEFAULT NULL COMMENT '发送的验证码，6位数字',
  `userid` int(11) DEFAULT NULL COMMENT '使用此验证码的用户编号，user.id',
  `used` tinyint(2) DEFAULT '0' COMMENT '是否使用，0未使用，1已使用',
  `type` tinyint(3) DEFAULT NULL COMMENT '验证码所属功能类型，  1:登录  ； 2:找回密码',
  `addtime` int(11) DEFAULT NULL COMMENT '创建添加时间，linux时间戳10位',
  `phone` char(11) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '接收短信的手机号',
  `ip` char(15) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '触发发送操作的客户ip地址',
  PRIMARY KEY (`id`),
  KEY `code` (`code`,`userid`,`used`,`type`,`addtime`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='短信验证码发送的日志记录';

-- ----------------------------
--  Table structure for `system`
-- ----------------------------
DROP TABLE IF EXISTS `system`;
CREATE TABLE `system` (
  `name` varchar(200) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '参数名,程序内调用',
  `description` char(200) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '说明描述',
  `value` varchar(2000) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '值',
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `lasttime` int(11) DEFAULT '0' COMMENT '最后修改时间，10位时间戳',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10005 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='系统变量，系统的一些参数相关，比如系统名字等';

-- ----------------------------
--  Records of `system`
-- ----------------------------
BEGIN;
INSERT INTO `system` VALUES ('USER_REG_ROLE', '用户注册后的权限，其值对应角色 role.id', '1', '6', '1506333513'), ('SITE_NAME', '网站名称', '网市场云商城', '7', '1628668798'), ('SITE_KEYWORDS', '网站SEO搜索的关键字，首页根内页没有设置description的都默认用此', '网市场云商城系统', '8', '1628668802'), ('SITE_DESCRIPTION', '网站SEO描述，首页根内页没有设置description的都默认用此', '管雷鸣', '9', null), ('ROLE_USER_ID', '普通用户的角色id，其值对应角色 role.id', '1', '15', '1506333544'), ('ROLE_SUPERADMIN_ID', '超级管理员的角色id，其值对应角色 role.id', '9', '16', '1506333534'), ('USER_HEAD_PATH', '用户头像(User.head)上传OSS或服务器进行存储的路径，存储于哪个文件夹中。<br/><b>注意</b><br/>1.这里最前面不要加/，最后要带/，如 head/<br/>2.使用中时，中途最好别改动，不然改动之前的用户设置好的头像就都没了', 'head/', '21', '1506481173'), ('ALLOW_USER_REG', '是否允许用户自行注册。<br/>1：允许用户自行注册<br/>0：禁止用户自行注册', '1', '22', '1507537911'), ('LIST_EVERYPAGE_NUMBER', '所有列表页面，每页显示的列表条数。', '15', '23', '1507538582'), ('SERVICE_MAIL', '网站管理员的邮箱。<br/>当网站出现什么问题，或者什么提醒时，会自动向管理员邮箱发送提示信息', '123456@qq.com', '24', '1511934294'), ('AGENCY_ROLE', '代理商得角色id', '10', '25', '1511943731'), ('IW_AUTO_INSTALL_USE', '是否允许通过访问/install/目录进行可视化配置参数。<br/>true：允许使用<br/>false:不允许使用<br/>建议不要动此处。执行完/install 配置完后，此处会自动变为false', 'true', '29', '1512616421'), ('MASTER_SITE_URL', '设置当前建站系统的域名。如建站系统的登录地址为 http://wang.market/login.do ，那么就将 http://wang.market/  填写到此处。', 'https://api.shop.leimingyun.com/', '134', '1515401613'), ('ATTACHMENT_FILE_URL', '设置当前建站系统中，上传的图片、附件的访问域名。若后续想要将附件转到云上存储、或开通CDN加速，可平滑上云使用。', '//www.xxxxxx.com/', '135', '1581924902'), ('ATTACHMENT_FILE_MODE', '当前文件附件存储使用的模式，用的阿里云oss，还是服务器本身磁盘进行存储。<br/>可选一：aliyunOSS：阿里云OSS模式存储<br/>可选二：localFile：服务器本身磁盘进行附件存储', 'localFile', '136', '1515395510'), ('STATIC_RESOURCE_PATH', '系统静态资源如css、js等调用的路径。填写如:  //res.weiunity.com/   默认是/ 则是调取当前项目的资源，以相对路径调用', '/', '150', '1540972613'), ('ROLE_ADMIN_SHOW', '总管理后台中，是否显示权限管理菜单。1为显示，0为不显示', '0', '151', '1540972613'), ('FEN_GE_XIAN', '分割线，系统变量，若您自己添加，请使用id为 10000以后的数字。 10000以前的数字为系统预留。', '10000', '10000', '1540972613');
COMMIT;

-- ----------------------------
--  Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '用户id编号',
  `username` char(50) COLLATE utf8_unicode_ci NOT NULL COMMENT '用户名',
  `email` char(40) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '邮箱',
  `password` char(32) COLLATE utf8_unicode_ci NOT NULL COMMENT '加密后的密码',
  `head` char(100) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '头像',
  `nickname` char(30) COLLATE utf8_unicode_ci NOT NULL COMMENT '姓名、昵称',
  `authority` char(20) COLLATE utf8_unicode_ci NOT NULL COMMENT '用户权限,主要纪录表再user_role表，一个用户可以有多个权限。多个权限id用,分割，如2,3,5',
  `regtime` int(10) unsigned NOT NULL COMMENT '注册时间,时间戳',
  `lasttime` int(10) unsigned NOT NULL COMMENT '最后登录时间,时间戳',
  `regip` char(15) COLLATE utf8_unicode_ci NOT NULL COMMENT '注册ip',
  `salt` char(6) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT 'shiro加密使用',
  `phone` char(11) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '手机号,11位',
  `currency` int(11) DEFAULT '0' COMMENT '资金，可以是积分、金币、等等站内虚拟货币',
  `referrerid` int(11) DEFAULT '0' COMMENT '推荐人的用户id。若没有推荐人则默认为0',
  `freezemoney` int(11) DEFAULT '0' COMMENT '账户冻结余额，金钱,RMB，单位：分',
  `lastip` char(15) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '最后一次登陆的ip',
  `isfreeze` tinyint(2) DEFAULT '0' COMMENT '是否已冻结，1已冻结（拉入黑名单），0正常',
  `money` int(11) DEFAULT '0' COMMENT '账户可用余额，金钱,RMB，单位：分',
  `sign` char(80) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '个人签名',
  `sex` char(4) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '男、女、未知',
  `version` int(11) DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `email` (`email`,`username`,`phone`) USING BTREE,
  KEY `username` (`username`,`email`,`phone`,`isfreeze`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=394 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='用户信息表。系统登陆的用户信息都在此处';

-- ----------------------------
--  Records of `user`
-- ----------------------------
BEGIN;
INSERT INTO `user` VALUES ('1', 'admin', '', '94940b4491a87f15333ed68cc0cdf833', 'default.png', '好看环境卡萨丁', '9', '1512818402', '1628670987', '127.0.0.1', '9738', '17000000002', '1', '0', '0.00', '127.0.0.1', '0', '0.00', '1', null, '256'), ('393', 'store', null, 'aa6d0fa04e70ab7b343a32c9812a4956', null, '商家', '8', '1589531514', '1589531514', '', '1234', null, '0', '1', '0.00', '127.0.0.1', '0', '0.00', null, null, '178');
COMMIT;

-- ----------------------------
--  Table structure for `user_quick_login`
-- ----------------------------
DROP TABLE IF EXISTS `user_quick_login`;
CREATE TABLE `user_quick_login` (
  `id` char(64) COLLATE utf8_unicode_ci NOT NULL COMMENT '快速登录的唯一码，通过这个码，来判断是哪个用户，从而进行登录',
  `userid` int(11) DEFAULT NULL COMMENT '该订单所属的用户，是哪个用户下的单，对应 User.id',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
--  Records of `user_quick_login`
-- ----------------------------
BEGIN;
INSERT INTO `user_quick_login` VALUES ('493a9c636f1745988e2e1556adf1d36108f3ffd8c91040329641f4142f3c023d', '394');
COMMIT;

-- ----------------------------
--  Table structure for `user_role`
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userid` int(11) DEFAULT NULL COMMENT '用户的id，user.id,一个用户可以有多个角色',
  `roleid` int(11) DEFAULT NULL COMMENT '角色的id，role.id ，一个用户可以有多个角色',
  PRIMARY KEY (`id`),
  KEY `userid` (`userid`)
) ENGINE=InnoDB AUTO_INCREMENT=512 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='用户拥有哪些角色';

-- ----------------------------
--  Records of `user_role`
-- ----------------------------
BEGIN;
INSERT INTO `user_role` VALUES ('412', '392', '10'), ('413', '1', '9'), ('414', '393', '1');
COMMIT;

-- ----------------------------
--  Table structure for `user_weixin`
-- ----------------------------
DROP TABLE IF EXISTS `user_weixin`;
CREATE TABLE `user_weixin` (
  `openid` char(40) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户在微信公众号的openid',
  `storeid` int(11) DEFAULT NULL COMMENT '对应store.id，用户是通过哪个店铺注册获取的openid',
  `unionid` char(40) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '用户的unionid',
  `userid` int(11) DEFAULT NULL COMMENT '对应user.id',
  PRIMARY KEY (`openid`),
  KEY `suoyin_index` (`storeid`,`unionid`,`userid`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
--  Table structure for `weixin_user`
-- ----------------------------
DROP TABLE IF EXISTS `weixin_user`;
CREATE TABLE `weixin_user` (
  `id` int(11) NOT NULL,
  `openid` char(40) DEFAULT NULL COMMENT '用户关注微信公众号后，获取到的用户的openid',
  `unionid` char(40) DEFAULT NULL COMMENT '用户的unionid',
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_a6buk7bav217bnormjo1tikip` (`openid`),
  UNIQUE KEY `UK_l5jw1hcwadlcy1gpbd2wp98qd` (`unionid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

-- ----------------------------
--  Records of `weixin_user`
-- ----------------------------
BEGIN;
INSERT INTO `weixin_user` VALUES ('394', 'oRsvU5PH90Q5ilAyUxUX-29ydJVs', '');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
