/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50623
 Source Host           : localhost
 Source Database       : wangmarket_shop

 Target Server Type    : MySQL
 Target Server Version : 50623
 File Encoding         : utf-8

 Date: 05/07/2020 09:32:17 AM
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
--  Table structure for `area`
-- ----------------------------
DROP TABLE IF EXISTS `area`;
CREATE TABLE `area` (
  `id` int(11) NOT NULL,
  `province` char(20) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '省份',
  `city` char(30) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '城市',
  `district` char(40) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '区',
  `longitude` float(5,2) DEFAULT NULL COMMENT '经度',
  `latitude` float(5,2) DEFAULT NULL COMMENT '纬度',
  PRIMARY KEY (`id`),
  KEY `province` (`province`,`city`,`district`,`longitude`,`latitude`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='地区，省市区，地理城市表（暂未用到，预留）';

-- ----------------------------
--  Table structure for `carousel`
-- ----------------------------
DROP TABLE IF EXISTS `carousel`;
CREATE TABLE `carousel` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `url` char(120) COLLATE utf8_unicode_ci DEFAULT '' COMMENT '点击跳转的目标url',
  `addtime` int(11) DEFAULT NULL COMMENT '添加时间',
  `isshow` tinyint(2) DEFAULT '1' COMMENT '是否显示，1为显示，0为不显示',
  `rank` int(11) DEFAULT '0' COMMENT '排序，数小越靠前',
  `siteid` int(11) DEFAULT NULL COMMENT '轮播图属于哪个站点，对应site.id',
  `userid` int(11) DEFAULT NULL COMMENT '轮播图属于哪个用户建立的，对应user.id',
  `image` char(120) COLLATE utf8_unicode_ci DEFAULT '' COMMENT '轮播图的url，分两种，一种只是文件名，如asd.png  另一种为绝对路径',
  `type` tinyint(2) DEFAULT '1' COMMENT '类型，默认1:内页通用的头部图(有的模版首页也用)；2:只有首页顶部才会使用的图',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='网站轮播图，手机、电脑模式网站用到。现主要做CMS类型网站，CMS模式网站这个是用不到的。';

-- ----------------------------
--  Table structure for `collect`
-- ----------------------------
DROP TABLE IF EXISTS `collect`;
CREATE TABLE `collect` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userid` int(11) DEFAULT NULL COMMENT '用户id，发起方的user.id，我的id，发起关注的人',
  `othersid` int(11) DEFAULT NULL COMMENT 'userid这个人关注的其他用户，被关注人',
  `addtime` int(11) DEFAULT NULL COMMENT '添加时间',
  PRIMARY KEY (`id`),
  KEY `userid` (`userid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='用户关注表，用户可互相关注，（暂未用到，预留）';

-- ----------------------------
--  Table structure for `exchange`
-- ----------------------------
DROP TABLE IF EXISTS `exchange`;
CREATE TABLE `exchange` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type` char(5) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '兑换类型，如空间、代理、域名等',
  `goodsid` int(11) DEFAULT NULL COMMENT '兑换商品的编号,goods.id',
  `addtime` int(11) DEFAULT NULL COMMENT '提交时间',
  `userid` int(11) DEFAULT NULL COMMENT '申请兑换的用户id',
  `siteid` int(11) DEFAULT NULL COMMENT '申请兑换的用户的所属网站',
  `user_remark` char(30) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '用户提交的备注信息，备注说明',
  `status` tinyint(2) DEFAULT NULL COMMENT '当前的状态，2:申请中； 3:已兑换完毕；4拒绝兑换',
  `kefu_remark` char(30) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '客服备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='积分兑换的兑换申请列表。（暂未用到，预留）';

-- ----------------------------
--  Table structure for `feedback`
-- ----------------------------
DROP TABLE IF EXISTS `feedback`;
CREATE TABLE `feedback` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `addtime` int(11) DEFAULT '0' COMMENT '添加时间',
  `userid` int(11) DEFAULT NULL COMMENT '哪个用户提出的',
  `text` varchar(1000) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '问题反馈的反馈内容',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='问题反馈（暂未用到，已废弃）';

-- ----------------------------
--  Table structure for `friend`
-- ----------------------------
DROP TABLE IF EXISTS `friend`;
CREATE TABLE `friend` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '自动编号',
  `self` int(10) unsigned NOT NULL COMMENT '自己，例如QQ登录后，这个QQ的账号.此处关联user.id',
  `other` int(10) unsigned NOT NULL COMMENT '自己，例如QQ登录后，这个QQ的好友的账号,此处关联user.id',
  PRIMARY KEY (`id`),
  KEY `self` (`self`),
  KEY `other` (`other`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='好友关系表，可以互相加好友。（暂未用到，预留）';

-- ----------------------------
--  Table structure for `friend_log`
-- ----------------------------
DROP TABLE IF EXISTS `friend_log`;
CREATE TABLE `friend_log` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '对应friend.id',
  `self` int(10) unsigned NOT NULL COMMENT '主动添加请求的user.id',
  `other` int(10) unsigned NOT NULL COMMENT '被动接受好友添加的user.id',
  `time` int(10) unsigned NOT NULL COMMENT '执行此操作当前的时间，UNIX时间戳',
  `state` tinyint(3) unsigned NOT NULL COMMENT '1：主动发出添加好友申请;2：被动者接受或者拒绝好友申请;3：删除好友',
  `ip` char(15) COLLATE utf8_unicode_ci NOT NULL COMMENT '当前操作者的ip地址',
  PRIMARY KEY (`id`),
  KEY `self` (`self`,`other`,`time`,`state`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='好友操作日志记录';

-- ----------------------------
--  Table structure for `goods`
-- ----------------------------
DROP TABLE IF EXISTS `goods`;
CREATE TABLE `goods` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `money` int(11) DEFAULT NULL COMMENT '积分、money， 同user.money',
  `explain` char(100) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '说明',
  `deadline` char(10) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '有效期，期限，如1年、1次',
  `type` char(5) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '所属分类，如域名、空间等',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='积分兑换的可兑换商品表（暂未用到，预留）';

-- ----------------------------
--  Records of `goods`
-- ----------------------------
BEGIN;
INSERT INTO `goods` VALUES ('1', '400', '.top  .bin 后缀的顶级域名任选一个', '1年', '域名'), ('2', '6000', '.com  .cn 后缀的顶级域名任选一个', '1年', '域名'), ('3', '2000', '在您原有的附件存储空间基础上，增加100MB', '永久', '空间'), ('4', '20000', '在您原有的附件存储空间基础上，增加1000MB', '永久', '空间'), ('5', '1000', '普通代理。拥有代理后台，可以在代理后台开通任意数量的网站。其建立的网站可以送人，但不可售卖，不可用于商业用途', '1年', '代理资格'), ('6', '200000', '商用代理，同普通代理，其建立的网站允许对外出售，允许其用于商业用途', '1年', '代理资格'), ('7', '1000', '如果您想将网站独立出去，放到自己的服务器或者FTP上，我们可以吧您网站的源代码(html文件)、图片、附件等打包给你，直接上传就可以开通访问', '1次', '网站迁移'), ('8', '500000', '苹果 Apple iPhone7 4G手机 全网通(32G)', '1台', '手机');
COMMIT;

-- ----------------------------
--  Table structure for `im`
-- ----------------------------
DROP TABLE IF EXISTS `im`;
CREATE TABLE `im` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `siteid` int(11) DEFAULT NULL,
  `auto_reply` char(200) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '无客服在线时，自动回复的文字。若为空，或者空字符串，则不开启此功能',
  `use_off_line_email` tinyint(1) DEFAULT '0' COMMENT '是否使用离线时的邮件通知提醒，默认为0，不启用。  1为启用',
  `email` char(40) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '邮件内容，若上面开启邮件提醒通知了，那么这里是必须要填写的，不然是要通知谁',
  `userid` int(11) DEFAULT '0' COMMENT '此条是属于哪个用户。im应用取便是通过这个来的',
  `use_kefu` smallint(6) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `siteid` (`siteid`,`userid`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='网站在线客服设置';

-- ----------------------------
--  Records of `im`
-- ----------------------------
BEGIN;
INSERT INTO `im` VALUES ('8', '0', '亲，我现在不在，你可以加我QQ921153866', '0', null, '0', null), ('9', '0', '亲，我现在不在线，你可以先加我QQ921153866，或者直接在这里留言', '0', null, '0', null), ('10', '219', '抱歉，我现在不在，你可以先留下联系方式，我一会回来联系您，', '1', '9211538@qq.com', '243', '1'), ('11', null, null, '0', 'sd', '373', null), ('12', null, '哈喽，我现在不在，你可以先关注微信公众号wangmarket', '0', null, '326', '1');
COMMIT;

-- ----------------------------
--  Table structure for `input_model`
-- ----------------------------
DROP TABLE IF EXISTS `input_model`;
CREATE TABLE `input_model` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `siteid` int(11) DEFAULT '0' COMMENT '对应site.id',
  `text` varchar(20000) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '输入模型的内容',
  `remark` varchar(30) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '备注说明',
  `code_name` char(30) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '模型代码，每个网站的模型代码是唯一的',
  PRIMARY KEY (`id`),
  KEY `siteid` (`siteid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='输入模型';

-- ----------------------------
--  Table structure for `log`
-- ----------------------------
DROP TABLE IF EXISTS `log`;
CREATE TABLE `log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `addtime` int(11) DEFAULT NULL,
  `goalid` int(11) DEFAULT NULL,
  `isdelete` smallint(6) NOT NULL,
  `type` int(11) DEFAULT NULL,
  `userid` int(11) DEFAULT NULL,
  `value` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='用户动作日志（暂未用到，已废弃，由阿里云日志服务取代）';

-- ----------------------------
--  Table structure for `message`
-- ----------------------------
DROP TABLE IF EXISTS `message`;
CREATE TABLE `message` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `senderid` int(11) unsigned NOT NULL COMMENT '自己的userid，发信人的userid，若是为0则为系统信息',
  `recipientid` int(11) unsigned NOT NULL COMMENT '给谁发信，这就是谁的userid，目标用户的userid，收信人id',
  `time` int(11) unsigned NOT NULL COMMENT '此信息的发送时间',
  `state` tinyint(3) unsigned NOT NULL COMMENT '0:未读，1:已读 ，2已删除',
  `isdelete` tinyint(2) NOT NULL DEFAULT '0' COMMENT '是否已经被删除。0正常，1已删除，',
  PRIMARY KEY (`id`),
  KEY `self` (`senderid`,`recipientid`,`time`,`state`,`isdelete`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='发信箱，发信，用户发信表，站内信。（暂未用到，预留）';

-- ----------------------------
--  Table structure for `message_data`
-- ----------------------------
DROP TABLE IF EXISTS `message_data`;
CREATE TABLE `message_data` (
  `id` int(11) unsigned NOT NULL COMMENT '对应message.id',
  `content` varchar(400) COLLATE utf8_unicode_ci NOT NULL COMMENT '内容',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='对应message，存储内容';

-- ----------------------------
--  Table structure for `news`
-- ----------------------------
DROP TABLE IF EXISTS `news`;
CREATE TABLE `news` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userid` int(11) DEFAULT '0' COMMENT '对应user.id，是哪个用户发表的',
  `addtime` int(11) DEFAULT '0' COMMENT '发布时间',
  `title` char(60) COLLATE utf8_unicode_ci DEFAULT NULL,
  `titlepic` char(200) COLLATE utf8_unicode_ci DEFAULT '' COMMENT '头图',
  `intro` char(160) COLLATE utf8_unicode_ci DEFAULT '' COMMENT '简介,从内容正文里自动剪切出开始的160个汉字',
  `sharenum` int(11) NOT NULL DEFAULT '0' COMMENT '分享的总数量',
  `supportnum` int(11) NOT NULL DEFAULT '0' COMMENT '支持的总数量',
  `opposenum` int(11) DEFAULT '0' COMMENT '反对的总数量',
  `readnum` int(11) DEFAULT '0' COMMENT '阅读的总数量',
  `type` tinyint(2) DEFAULT '0' COMMENT '1新闻；2图文；3独立页面',
  `status` tinyint(2) DEFAULT '1' COMMENT '1：正常显示；2：隐藏不显示',
  `commentnum` int(11) DEFAULT '0' COMMENT '评论的总数量',
  `cid` int(11) DEFAULT '0' COMMENT '所属栏目id，对应site_column.id',
  `siteid` int(11) DEFAULT '0' COMMENT '所属站点，对应site.id',
  `legitimate` tinyint(2) DEFAULT '1' COMMENT '是否是合法的，1是，0不是，涉嫌',
  `reserve1` char(10) COLLATE utf8_unicode_ci DEFAULT '' COMMENT '预留字段，用户可使用输入模型来进行扩展',
  `reserve2` char(10) COLLATE utf8_unicode_ci DEFAULT '' COMMENT '预留字段，用户可使用输入模型来进行扩展',
  PRIMARY KEY (`id`,`supportnum`,`sharenum`),
  KEY `userid` (`userid`,`type`,`supportnum`,`readnum`,`commentnum`,`cid`,`status`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='网站的新闻、产品、独立页面等，都是存储在这';

-- ----------------------------
--  Table structure for `news_comment`
-- ----------------------------
DROP TABLE IF EXISTS `news_comment`;
CREATE TABLE `news_comment` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `newsid` int(11) DEFAULT NULL COMMENT '关联news.id',
  `userid` int(11) DEFAULT NULL COMMENT '关联user.id，评论用户的id',
  `addtime` int(11) DEFAULT NULL COMMENT '添加时间',
  `text` varchar(200) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '评论内容',
  PRIMARY KEY (`id`),
  KEY `newsid` (`newsid`,`userid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='news的评论（暂未用到，预留）';

-- ----------------------------
--  Table structure for `news_data`
-- ----------------------------
DROP TABLE IF EXISTS `news_data`;
CREATE TABLE `news_data` (
  `id` int(11) NOT NULL COMMENT '对应news.id',
  `text` mediumtext CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '信息内容',
  `extend` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '其他扩展字段，以json形式存在',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='news内容分表';

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
  PRIMARY KEY (`id`),
  UNIQUE KEY `url` (`url`,`name`,`percode`),
  KEY `parent_id` (`parent_id`)
) ENGINE=InnoDB AUTO_INCREMENT=120 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='Shiro权限管理中的资源';

-- ----------------------------
--  Records of `permission`
-- ----------------------------
BEGIN;
INSERT INTO `permission` VALUES ('1', '用户前台用户的个人中心', '/user/info.do', '个人中心', '0', 'user'), ('2', '用户前台用户的个人中心', '/user/info.do', '我的信息', '1', 'userInfo'), ('3', '前端用户在论坛发表帖子', '/bbs/addPost.do', '发帖', '4', 'bbsAddPost'), ('4', '前端，论坛', '/bbs/list.do', '论坛', '0', 'bbs'), ('7', '用户端的我的好友', '/friend/list.do', '我的好友', '0', 'friend'), ('9', '前端的我的好友，添加好友', '/friend/add.do', '添加好友', '7', 'friendAdd'), ('10', '前端，我的好友，好友列表', '/friend/list.do', '好友列表', '7', 'friendList'), ('11', '前端的我的好友，删除好友', '/friend/delete.do', '删除好友', '7', 'friendDelete'), ('12', '后台的用户管理', '/admin/user/list.do', '用户管理', '0', 'adminUser'), ('13', '后台用户管理下的菜单', '/admin/user/list.do', '用户列表', '12', 'adminUserList'), ('14', '后台用户管理下的菜单', '/admin/user/delete.do', '删除用户', '12', 'adminUserDelete'), ('15', '管理后台－系统管理栏目', '/admin/system/index.do', '系统管理', '0', 'adminSystem'), ('16', '管理后台－系统管理－系统参数、系统变量', '/admin/system/variableList.do', '全局变量', '15', 'adminSystemVariable'), ('17', '管理后台－系统管理，刷新所有缓存', '/admin/system/generateAllCache.do', '刷新缓存', '15', 'adminSystemGenerateAllCache'), ('18', '前端，用户中心，注销登录', '/user/logout.do', '注销', '1', 'userLogout'), ('19', '前端，用户中心，更改头像', '/user/uploadHead.do', '更改头像', '1', 'userUploadHead'), ('20', '前端，用户中心，更改用户自己的昵称', '/user/updateNickName.do', '更改昵称', '1', 'userUpdateNickName'), ('21', '前端，用户中心，更改密码', '/user/updatePassword.do', '更改密码', '1', 'userUpdatePassword'), ('22', '前端，用户中心，邮件邀请用户注册', '/user/inviteEmail.do', '邮件邀请注册', '1', 'userInviteEmail'), ('23', '前端，好友中心，首页', '/friend/index.do', '首页', '7', 'friendIndex'), ('24', '前端，站内信', '/message/list.do', '信息', '0', 'message'), ('25', '前端，站内信，信息列表', '/message/list.do', '信息列表', '24', 'messageList'), ('26', '前端，站内信，阅读信息', '/message/view.do', '阅读信息', '24', 'messageView'), ('27', '前端，站内信，发送信息', '/message/send.do', '发送信息', '24', 'messageSend'), ('28', '前端，论坛，帖子列表', '/bbs/list.do', '帖子列表', '4', 'bbsList'), ('29', '前端，论坛，帖子详情', '/bbs/view.do', '帖子详情', '4', 'bbsView'), ('30', '前端，论坛，回复帖子', '/bbs/addComment.do', '回复帖子', '4', 'bbsAddComment'), ('31', '前端，项目组', '/project/index.do', '项目组', '0', 'project'), ('32', '前端，项目组，项目列表', '/project/list.do', '列表', '31', 'projectList'), ('33', '前端，项目组，项目详情', '/project/view.do', '项目详情', '31', 'projectView'), ('34', '前端，项目组，添加项目', '/project/add.do', '添加项目', '31', 'projectAdd'), ('35', '前端，项目组，删除项目', '/project/delete.do', '删除项目', '31', 'projectDelete'), ('36', '前端，项目组，列出属于自己管理的项目列表', '/project/adminList.do', '管理项目列表', '31', 'projectAdminList'), ('37', '前端，项目组，添加成员到项目组', '/project/addMember.do', '添加成员到项目组', '31', 'projectAddMember'), ('38', '前端，项目组，获取项目组用户列表', '/project/memberList.do', '获取项目组用户列表', '31', 'projectMemberList'), ('39', '前端，图表（线框图，流程图等）', '/paint/index.do', '图表', '0', 'paint'), ('40', '前端，图表，图表首页', '/paint/index.do', '首页', '39', 'paintIndex'), ('41', '前端，图表，添加图表（流程、线框图）', '/paint/add.do', '添加', '39', 'paintAdd'), ('42', '前端，图表，评论图表（流程、线框图）', '/paint/addComment.do', '评论图表', '39', 'paintAddComment'), ('43', '前端，图表，该图（流程、线框图）的评论列表', '/paint/commentList.do', '该图评论列表', '39', 'paintCommentList'), ('44', '后台，权限管理', '/admin/role/roleList.do', '权限管理', '0', 'adminRole'), ('46', '后台，权限管理，新增、编辑角色', '/admin/role/editRole.do', '编辑角色', '44', 'adminRoleRole'), ('48', '后台，权限管理，角色列表', '/admin/role/roleList.do', '角色列表', '44', 'adminRoleRoleList'), ('49', '后台，权限管理，删除角色', '/admin/role/deleteRole.do', '删除角色', '44', 'adminRoleDeleteRole'), ('51', '后台，权限管理，资源Permission的添加、编辑功能', '/admin/role/editPermission.do', '编辑资源', '44', 'adminRolePermission'), ('53', '后台，权限管理，资源Permission列表', '/admin/role/permissionList.do', '资源列表', '44', 'adminRolePermissionList'), ('54', '后台，权限管理，删除资源Permission', '/admin/role/deletePermission.do', '删除资源', '44', 'adminRoleDeletePermission'), ('55', '后台，权限管理，编辑角色下资源', '/admin/role/editRolePermission.do', '编辑角色下资源', '44', 'adminRoleEditRolePermission'), ('56', '后台，权限管理，编辑用户所属角色', '/admin/role/editUserRole.do', '编辑用户所属角色', '44', 'adminRoleEditUserRole'), ('57', '后台，论坛管理', '/admin/bbs/postList.do', '论坛管理', '0', 'adminBbs'), ('58', '后台，论坛管理，帖子列表', '/admin/bbs/postList.do', '帖子列表', '57', 'adminBbsPostList'), ('59', '后台，论坛管理，删除帖子', '/admin/bbs/deletePost.do', '删除帖子', '57', 'adminBbsDeletePost'), ('60', '后台，论坛管理，添加板块', '/admin/bbs/addClass.do', '添加板块', '57', 'adminBbsAddClass'), ('61', '后台，论坛管理，添加，修改板块', '/admin/bbs/editClass.do', '修改板块', '57', 'adminBbsClass'), ('63', '后台，论坛管理，板块列表', '/admin/bbs/classList.do', '板块列表', '57', 'adminBbsClassList'), ('64', '后台，论坛管理，删除板块', '/admin/bbs/deleteClass.do', '删除板块', '57', 'adminBbsDeleteClass'), ('65', '后台，站内信消息管理', '/admin/message/list.do', '消息管理', '0', 'adminMessage'), ('66', '后台，站内信消息管理，消息列表', '/admin/message/list.do', '消息列表', '65', 'adminMessageList'), ('67', '后台，站内信消息管理，删除消息', '/admin/message/delete.do', '删除消息', '65', 'adminMessageDelete'), ('68', '后台，系统设置，用户注册后自动拥有的一个权限', '/admin/system/userRegRole.do', '注册用户权限', '15', 'adminSystemUserRegRole'), ('71', '后台，日志管理', '/admin/log/list.do', '日志管理', '0', 'adminLog'), ('72', '后台，日志管理，日志列表', '/admin/log/list.do', '日志列表', '71', 'adminLogList'), ('74', '管理后台－系统管理，新增、修改系统的全局变量', '/admin/system/variable.do', '修改全局变量', '15', 'adminSystemVariable'), ('75', '邀请注册页面，介绍说明页面', '/user/invite.do', '邀请注册页面', '1', 'userInvite'), ('77', '后台，论坛管理，帖子编辑、添加', '/admin/bbs/post.do', '添加修改帖子', '57', 'adminBbsPost'), ('78', '后台，论坛管理，删除帖子回复', '/admin/bbs/deleteComment.do', '删除回帖', '57', 'adminBbsDeletePostComment'), ('79', '后台，论坛管理，回帖列表', '/admin/bbs/commentList.do', '回帖列表', '57', 'adminBbsPostCommentList'), ('80', '后台，用户管理，查看用户详情', '/admin/user/view.do', '用户详情', '12', 'adminUserView'), ('81', '后台，用户管理，冻结、解除冻结会员', '/admin/user/updateFreeze.do', '冻结会员', '12', 'adminUserUpdateFreeze'), ('82', '后台，历史发送的短信验证码', '/admin/smslog/list.do', '验证码管理', '0', 'adminSmsLog'), ('83', '后台，历史发送的短信验证码列表', '/admin/smslog/list.do', '验证码列表', '82', 'adminSmsLogList'), ('86', '后台，在线支付记录', '/admin/payLog/list.do', '支付记录', '0', 'adminPayLog'), ('87', '后台，在线支付记录，记录列表', '/admin/payLog/list.do', '支付列表', '86', 'adminPayLogList'), ('88', '建站代理', '', '建站代理', '0', 'agencyIndex'), ('89', '建站代理，代理商后台首页', '', '首页', '88', 'agencyIndex'), ('90', '建站代理，代理商会员站点列表', '', '会员站点列表', '88', 'agencyUserList'), ('91', '建站代理，添加用户站点', '', '添加用户站点', '88', 'agencyAdd'), ('92', '信息文章相关操作', '', '文章管理', '0', 'adminNews'), ('93', 'News数据表，信息列表', '', '信息列表', '92', 'adminNewsList'), ('94', 'News数据表，信息详情', '', '信息详情', '92', 'adminNewsView'), ('95', 'News数据表，删除信息', '', '删除信息', '92', 'adminNewsDelete'), ('96', 'News数据表，合法性改为合法状态', '', '改为合法', '92', 'adminNewsCancelLegitimate'), ('97', '建站代理，代理商后台，开通其下级普通代理', '', '开通普通代理', '88', 'AgencyNormalAdd'), ('98', '查看当前在线的会员', '', '在线会员', '12', 'adminOnlineUserList'), ('99', '总管理后台的网站管理', '', '全部网站管理', '0', 'adminSite'), ('100', '网站列表', '', '网站列表', '99', 'adminSiteList'), ('101', '网站详情页面', '', '网站详情', '99', 'adminSiteView'), ('102', '添加一个网站跟用户', '', '添加网站', '99', 'adminSiteAdd'), ('103', '访问统计相关', '', '访问统计', '0', 'adminRequestLog'), ('104', '网站的访问情况', '', '访问统计', '103', 'adminRequestLogFangWen'), ('105', '操作的日志列表', '', '操作日志', '88', 'agencyActionLogList'), ('106', '资金变动日志', '', '资金日志', '88', 'agencySiteSizeLogList'), ('107', '我的下级代理商列表', '', '下级列表', '88', 'agencySubAgencyList'), ('108', '给我的下级代理充值站币', null, '站币充值', '88', 'agencyTransferSiteSizeToSubAgencyList'), ('109', '给我开通的网站续费延长使用时间', null, '网站续费', '88', 'agencySiteXuFie'), ('110', '给我下级的代理延长使用期限', '', '代理延期', '88', 'agencyYanQi'), ('111', '将我下级的代理冻结，暂停', '', '冻结代理', '88', 'agencyAgencyFreeze'), ('112', '将我下级的代理接触冻结，恢复正常', '', '解冻代理', '88', 'agencyAgencyUnFreeze'), ('113', '修改站点、代理帐户的密码', '', '修改密码', '88', 'agencySiteUpdatePassword'), ('114', '后台管理首页，登录后台的话，需要授权此项，不然登录成功后仍然无法进入后台，被此页给拦截了', null, '管理后台', '0', 'adminIndex'), ('115', '管理后台首页', '', '后台首页', '114', 'adminIndexIndex'), ('116', '删除系统变量', 'admin/system/deleteVariable.do', '删除变量', '15', 'adminSystemDeleteVariable'), ('117', '后台，日志管理，所有动作的日志图表', '/admin/log/cartogram.do', '统计图表', '71', 'adminLogCartogram'), ('118', '将自己直属下级的某个网站冻结', '', '冻结网站', '88', 'agencySiteFreeze'), ('119', '将自己直属下级的某个网站解除冻结', '', '网站解冻', '88', 'agencySiteFreeze');
COMMIT;

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
--  Table structure for `plugin_form`
-- ----------------------------
DROP TABLE IF EXISTS `plugin_form`;
CREATE TABLE `plugin_form` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `addtime` int(11) DEFAULT NULL,
  `siteid` int(11) DEFAULT NULL,
  `state` smallint(6) DEFAULT NULL,
  `title` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `suoyin_index` (`siteid`,`state`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
--  Table structure for `plugin_form_data`
-- ----------------------------
DROP TABLE IF EXISTS `plugin_form_data`;
CREATE TABLE `plugin_form_data` (
  `id` int(11) NOT NULL,
  `text` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci ROW_FORMAT=DYNAMIC;

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
INSERT INTO `role` VALUES ('1', '建站用户', '建立网站的用户'), ('2', '论坛用户', '用户网站自己的论坛用户'), ('9', '总管理', '总后台管理，超级管理员'), ('10', '代理', '商代理，可以开通子代理、网站');
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
) ENGINE=InnoDB AUTO_INCREMENT=212 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='Shiro权限管理中，角色所拥有哪些资源的操作权限';

-- ----------------------------
--  Records of `role_permission`
-- ----------------------------
BEGIN;
INSERT INTO `role_permission` VALUES ('1', '9', '7'), ('4', '9', '4'), ('5', '9', '3'), ('6', '1', '1'), ('7', '1', '2'), ('8', '1', '4'), ('9', '1', '3'), ('10', '1', '7'), ('11', '1', '9'), ('12', '9', '12'), ('13', '9', '13'), ('14', '9', '1'), ('15', '9', '2'), ('16', '9', '9'), ('17', '9', '15'), ('18', '9', '16'), ('19', '9', '17'), ('20', '9', '18'), ('21', '9', '19'), ('23', '9', '21'), ('24', '9', '22'), ('25', '9', '28'), ('27', '9', '30'), ('28', '9', '11'), ('29', '9', '23'), ('30', '9', '14'), ('31', '9', '24'), ('32', '9', '26'), ('33', '9', '27'), ('34', '9', '31'), ('35', '9', '32'), ('36', '9', '33'), ('37', '9', '34'), ('38', '9', '35'), ('39', '9', '36'), ('40', '9', '37'), ('41', '9', '38'), ('47', '9', '68'), ('48', '9', '25'), ('49', '9', '44'), ('51', '9', '46'), ('53', '9', '48'), ('54', '9', '49'), ('56', '9', '51'), ('58', '9', '53'), ('59', '9', '54'), ('60', '9', '55'), ('61', '9', '56'), ('75', '9', '71'), ('76', '9', '72'), ('77', '9', '74'), ('78', '9', '10'), ('79', '9', '20'), ('80', '9', '75'), ('81', '9', '29'), ('85', '1', '18'), ('86', '1', '19'), ('87', '1', '20'), ('88', '1', '21'), ('89', '1', '22'), ('90', '1', '75'), ('91', '1', '28'), ('92', '1', '29'), ('93', '1', '30'), ('94', '1', '10'), ('95', '1', '11'), ('96', '1', '23'), ('97', '1', '24'), ('98', '1', '25'), ('99', '1', '26'), ('100', '1', '27'), ('101', '9', '80'), ('104', '9', '81'), ('109', '10', '1'), ('110', '10', '2'), ('111', '10', '18'), ('112', '10', '20'), ('113', '10', '21'), ('114', '10', '22'), ('115', '10', '75'), ('116', '10', '24'), ('117', '10', '25'), ('118', '10', '26'), ('119', '10', '27'), ('120', '10', '88'), ('121', '10', '89'), ('122', '10', '90'), ('123', '10', '91'), ('124', '11', '1'), ('125', '11', '2'), ('126', '11', '18'), ('127', '11', '19'), ('128', '11', '20'), ('129', '11', '21'), ('130', '11', '22'), ('131', '11', '75'), ('132', '11', '7'), ('133', '11', '9'), ('134', '11', '10'), ('135', '11', '11'), ('136', '11', '23'), ('137', '11', '24'), ('138', '11', '25'), ('139', '11', '26'), ('140', '11', '27'), ('141', '11', '88'), ('142', '11', '89'), ('143', '11', '90'), ('144', '11', '91'), ('145', '12', '1'), ('146', '12', '2'), ('147', '12', '18'), ('148', '12', '19'), ('149', '12', '20'), ('150', '12', '21'), ('151', '12', '22'), ('152', '12', '75'), ('153', '12', '88'), ('154', '12', '89'), ('155', '12', '90'), ('156', '12', '91'), ('157', '9', '92'), ('158', '9', '93'), ('159', '9', '94'), ('160', '9', '95'), ('161', '9', '96'), ('162', '12', '4'), ('163', '12', '3'), ('164', '12', '28'), ('165', '12', '29'), ('166', '12', '30'), ('167', '12', '7'), ('168', '12', '9'), ('169', '12', '10'), ('170', '12', '11'), ('171', '12', '23'), ('172', '12', '24'), ('173', '12', '25'), ('174', '12', '26'), ('175', '12', '27'), ('176', '10', '19'), ('177', '10', '4'), ('178', '10', '3'), ('179', '10', '28'), ('180', '10', '29'), ('181', '10', '30'), ('182', '10', '7'), ('183', '10', '9'), ('184', '10', '10'), ('185', '10', '11'), ('186', '10', '23'), ('187', '10', '97'), ('188', '9', '98'), ('189', '9', '99'), ('190', '9', '100'), ('191', '9', '101'), ('192', '9', '102'), ('193', '9', '103'), ('194', '9', '104'), ('195', '10', '105'), ('196', '10', '106'), ('197', '10', '107'), ('198', '10', '108'), ('199', '10', '109'), ('200', '10', '110'), ('201', '10', '111'), ('202', '10', '112'), ('203', '10', '113'), ('204', '9', '114'), ('205', '9', '115'), ('206', '10', '114'), ('207', '10', '115'), ('208', '9', '117'), ('209', '9', '116'), ('210', '10', '118'), ('211', '10', '119');
COMMIT;

-- ----------------------------
--  Table structure for `shop_address`
-- ----------------------------
DROP TABLE IF EXISTS `shop_address`;
CREATE TABLE `shop_address` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `address` char(100) DEFAULT NULL COMMENT '具体地址',
  `latitude` double(50,6) DEFAULT NULL COMMENT '经纬度',
  `longitude` double(50,6) DEFAULT NULL COMMENT '经纬度',
  `phone` char(13) DEFAULT NULL COMMENT '收货人手机号',
  `userid` int(11) DEFAULT NULL COMMENT '改地址所属用户，属于那个用户的，对应User.id',
  `username` char(10) DEFAULT NULL COMMENT '收货人用户姓名',
  `default_use` tinyint(2) DEFAULT '0' COMMENT '是否是默认使用的，1是默认使用的地址，0不是默认使用的。一个用户会有多个收货地址，但一个用户默认的收货地址只有一个',
  PRIMARY KEY (`id`),
  KEY `suoyin_index` (`longitude`,`latitude`,`userid`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

-- ----------------------------
--  Records of `shop_address`
-- ----------------------------
BEGIN;
INSERT INTO `shop_address` VALUES ('1', 'sadfssdg阿斯顿阿斯顿', '0.000000', '0.000000', '1707', '1', 'guan是测试', '0'), ('5', '好多僵尸粉看巨额话费', '0.000000', '0.000000', '5545', '1', '哈哈哈', '0'), ('6', '山东潍坊市寒亭区开元街道通亭街亚星路路口向西20米路北，中国兽药饲料交易大厦十三楼E1308', '0.000000', '0.000000', '17076012262', '1', '管雷鸣', '1'), ('7', '33333', '0.000000', '0.000000', '344', '1', 'haha', '0'), ('8', '湖北', '0.000000', '0.000000', '15997778666', '398', 'ceshi520', '1'), ('10', '世界尽头', '1.333300', '1.333300', '1555555', '412', '渡劫', '1'), ('12', 'sgsdfsdf第三方史蒂夫史蒂夫史蒂夫史蒂夫史蒂夫史蒂夫收到sgsdfsdf第三方史蒂夫史蒂夫史蒂夫史蒂夫史蒂夫史蒂夫收到', '0.000000', '0.000000', '如444444', '411', '44444', '1'), ('22', '梧州市我的位置', '22.918280', '110.994900', '15526541989', '413', 'ggl', '0'), ('23', '广西壮族自治区梧州市岑溪市义州大道88号岑溪商贸中心', '22.915370', '110.995000', '15526541989', '413', 'ggl', '0'), ('24', '广西壮族自治区梧州市岑溪市水东街北水猪脚粉', '22.917499', '110.996238', '15526541989', '413', 'ggl', '0'), ('25', '在地图选择', '0.000000', '0.000000', '15526541999', '413', '555', '1'), ('26', 'sssssd', '0.000000', '0.000000', 'dddd', '411', 'sddd', '0'), ('27', '广西壮族自治区梧州市岑溪市义洲大道岑溪人民广场', '22.914580', '110.994590', '15526541989', '413', 'hh', '0'), ('28', '1', '0.000000', '0.000000', '1', '480', '1', '1');
COMMIT;

-- ----------------------------
--  Table structure for `shop_carousel_image`
-- ----------------------------
DROP TABLE IF EXISTS `shop_carousel_image`;
CREATE TABLE `shop_carousel_image` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `image_url` char(100) DEFAULT NULL COMMENT '轮播图url，绝对路径',
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
  `titlepic` char(100) DEFAULT NULL COMMENT '该商品的标题图片、列表图片,图片的绝对路径',
  `typeid` int(11) DEFAULT NULL COMMENT '该商品所属哪个分类下的，对应 GoodsType.id',
  `units` char(5) DEFAULT NULL COMMENT '计量，单位。如个、斤、条',
  `updatetime` int(11) DEFAULT NULL COMMENT '商品最后更改时间，10位时间戳',
  `user_buy_restrict` int(11) DEFAULT NULL COMMENT '用户购买限制。如果值是0，则可以任意购买，没有什么限制，如果是1，则代表每个用户只能购买一个，如果是2，代表每个用户只能购买2个以内，不超过2个。 只要是下单了，未退单成功的，都算是购买了',
  `version` int(11) DEFAULT '0' COMMENT '乐观锁',
  `note` char(100) DEFAULT NULL COMMENT '该商品的备注，只在后台显示',
  PRIMARY KEY (`id`),
  KEY `suoyin_index` (`putaway`,`sale`,`fake_sale`,`price`,`addtime`,`storeid`,`typeid`,`isdelete`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

-- ----------------------------
--  Records of `shop_goods`
-- ----------------------------
BEGIN;
INSERT INTO `shop_goods` VALUES ('4', '1581733544', '1', '10', '100', '1', '800', '300', '0', '0', '1', '花生', '//cdn.weiunity.com/site/341/news/b67717924c8340bba321c7bceb884657.png', '2', '斤', '1581733544', '0', '4', null), ('5', '1581746682', '1', '10', '10', '1', '800', '600', '1', '0', '1', '花生', '//cdn.weiunity.com/site/341/news/b67717924c8340bba321c7bceb884657.png', '2', '斤', '1581918959', '0', '21', null), ('6', '1581919619', '1', '1', '749', '0', '800', '51', '1', '7', '1', '松崎 卡尺高精度电子数显游标卡尺', '//cdn.shop.leimingyun.com/store/1/images/19d21648e1ce47179d726c2516503977.jpg', '5', '斤', '1585124870', '0', '30', null), ('7', '1581920717', '1', '1', '530', '0', '800', '2', '1', '12', '1', '零食混合坚果恰恰果仁干果小黄袋', '//cdn.shop.leimingyun.com/store/1/images/ed1c4f9e107547f7b775181d281b7834.jpg', '4', '斤', '1585124887', '0', '27', null), ('8', '1581986730', '0', '0', '10', '1', '800', '300', '1', '0', '1', '花生', '//cdn.shop.leimingyun.com/goodsList/14bc7bee63494be3a24e460f90d7fe00.jpg', '1', '', '1581986730', '0', '1', null), ('9', '1581987167', '0', '0', '500', '1', '800', '900', '1', '0', '1', '土豆', '//cdn.shop.leimingyun.com/goodsList/9be02767806e41e8928d8e84cbdcf8d9.jpg', '1', '个', '1581998493', '0', '3', null), ('10', '1582005476', '4', '0', '262', '0', '800', '1', '1', '26', '1', '沙发', '//cdn.shop.leimingyun.com/store/1/images/9a1511fa5d8548f1b02cc7db68f3af72.jpg', '3', '个', '1586060094', '0', '35', null), ('11', '1583049951', '2', '20', '1003', '0', '2300', '1', '1', '11', '1', '圣菲火（SHENGFEIHUO）手电筒小型', '//cdn.shop.leimingyun.com/store/1/images/026645215ea64274aebddbc05736679b.jpg', '2', '个', '1584708088', '0', '12', null), ('12', '1583589597', '0', '0', '3001', '1', '0', '0', '0', '0', '1', '111', '//cdn.shop.leimingyun.com/store/1/images/4e17794d5fc242488d83392f52c989a9.png', '3', '个', '1583589665', '0', '3', null), ('13', '1583760769', '0', '0', '33', '0', '0', '0', '1', '0', '1211', '哈哈哈', '//cdn.shop.leimingyun.com/store/1211/images/a8622a20d82b49b4bb3f33e6c301a3f3.png', '8', '个', '1583760778', '0', '1', null), ('14', '1585124570', '1', '10', '10', '0', '800', '500', '1', '0', '1', '芒果', '//cdn.shop.leimingyun.com/store/1/images/ec52bf9f09224925b7976641ab906ad6.jpg', '1', '斤', '1585124570', '0', '0', null), ('15', '1585124634', '1', '50', '100', '0', '1000', '500', '1', '0', '1', '白菜', '//cdn.shop.leimingyun.com/store/1/images/7974d2d7c1894f489bea0cf5cc2ba006.jpg', '1', '斤', '1585127448', '0', '3', null), ('16', '1585124722', '10', '1', '1000', '0', '1000', '200', '1', '0', '1', '西红柿', '//cdn.shop.leimingyun.com/store/1/images/9c435071c98a449ba0a98f91708bf2f4.jpg', '1', '斤', '1585127415', '0', '1', null), ('17', '1585125115', '10', '0', '1000', '0', '600', '300', '1', '0', '1', '黄瓜', '//cdn.shop.leimingyun.com/store/1/images/f9cace2e263944b583f7676dcd99f067.jpg', '1', '斤', '1585127390', '0', '5', null), ('18', '1585932228', '1', '0', '111', '0', '1000', '1000', '1', '0', '4192', '1', '', null, '个', '1585932228', '0', '0', null), ('19', '1586647089', '1', '1111', '1', '1', '100', '1000', '1', '0', '1399', '1', '//cdn.shop.leimingyun.com/store/1399/images/dc34561db41b4819abe7947b28b116d5.jpg', '11', '个', '1586647089', '0', '1', null), ('20', '1586647094', '1', '1111', '1', '1', '100', '1000', '1', '0', '1399', '1', '//cdn.shop.leimingyun.com/store/1399/images/dc34561db41b4819abe7947b28b116d5.jpg', '11', '个', '1586647094', '0', '1', null), ('21', '1586647112', '1', '1111', '10000', '1', '100', '1000', '1', '0', '1399', '1', '//cdn.shop.leimingyun.com/store/1399/images/dc34561db41b4819abe7947b28b116d5.jpg', '11', '个', '1586647112', '0', '1', null), ('22', '1587964555', '0', '0', '3254', '0', '23300', '32200', '1', '0', '4315', '234324', '', null, '个', '1587964555', '0', '0', null), ('23', '1588725283', '0', '0', '1111', '1', '0', '0', '1', '0', '4356', '1212', '', '13', '个', '1588725283', '0', '1', null), ('24', '1588725286', '0', '0', '1111', '0', '30000', '5000', '1', '0', '4356', '美甲', '//cdn.shop.leimingyun.com/store/4356/images/8b9a8bf76202419daa50436ac725e375.png', '13', '个', '1588737495', '0', '4', null), ('25', '1588737543', '110', '500', '2220', '0', '18000', '8000', '1', '0', '4356', '婚纱礼服', '//cdn.shop.leimingyun.com/store/4356/images/016ee2aa4d754e4fa43992b7c600233c.png', '13', '个', '1588749625', '0', '2', null), ('26', '1588743257', '110', '500', '2220', '0', '18000', '8000', '1', '0', '4356', '美瞳', '//cdn.shop.leimingyun.com/store/4356/images/ca3aec83269f4d24bbc74c58dd93b4ea.png', '13', '个', '1588743257', '0', '0', null);
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
INSERT INTO `shop_goods_data` VALUES ('4', '<p>我是描述</p>'), ('5', '<p>f大大大</p>'), ('6', '<p><img src=\"//cdn.shop.leimingyun.com/site/defaultparam1/news/20200320/1584707683678064374.jpg\" title=\"1584707683678064374.jpg\" alt=\"TB28ntab1uSBuNjSsplXXbe8pXa_!!2601841464.jpg\"/><img src=\"//cdn.shop.leimingyun.com/site/defaultparam1/news/20200320/1584707683667032052.jpg\" title=\"1584707683667032052.jpg\" alt=\"TB23wVybY9YBuNjy0FgXXcxcXXa_!!2601841464.jpg\"/></p>'), ('7', '<p><img src=\"//cdn.shop.leimingyun.com/site/defaultparam1/news/20200320/1584707909230089619.jpg\" title=\"1584707909230089619.jpg\" alt=\"O1CN01wAPgRV1KEUqvHhEjw_!!4023681132-0-scmitem6000.jpg\"/><img src=\"//cdn.shop.leimingyun.com/site/defaultparam1/news/20200320/1584707909233030967.jpg\" title=\"1584707909233030967.jpg\" alt=\"O1CN01p1MyIX1KEUqt3TAZD_!!4023681132-0-scmitem6000.jpg\"/></p>'), ('8', '<p>我是描述</p>'), ('9', '<p>我是描述</p>'), ('10', '<p><img src=\"//cdn.shop.leimingyun.com/site/defaultparam1/news/20200320/1584707249196079339.jpg\" title=\"1584707249196079339.jpg\" alt=\"O1CN01SxJVU421KvyWXVub9_!!3105116967.jpg\"/></p><p><img src=\"//cdn.shop.leimingyun.com/site/defaultparam1/news/20200320/1584707254669047427.jpg\" title=\"1584707254669047427.jpg\" alt=\"O1CN01SzpJrH21Kvxlx9SYZ_!!3105116967.jpg\"/><img src=\"//cdn.shop.leimingyun.com/site/defaultparam1/news/20200320/1584707261798038576.jpg\" title=\"1584707261798038576.jpg\" alt=\"O1CN01SxJVU421KvyWXVub9_!!3105116967.jpg\"/></p>'), ('11', '<p><img class=\"\" src=\"https://img10.360buyimg.com/imgzone/jfs/t1/42702/8/4647/137374/5cd517c8Eabda800a/39e9330a56535c93.jpg\" style=\"margin: 0px; padding: 0px; border: 0px; vertical-align: middle; color: rgb(102, 102, 102); font-family: tahoma, arial, \" microsoft=\"\" hiragino=\"\" sans=\"\" font-size:=\"\" font-style:=\"\" font-variant-ligatures:=\"\" font-variant-caps:=\"\" font-weight:=\"\" letter-spacing:=\"\" orphans:=\"\" text-align:=\"\" text-indent:=\"\" text-transform:=\"\" white-space:=\"\" widows:=\"\" word-spacing:=\"\" -webkit-text-stroke-width:=\"\" background-color:=\"\" text-decoration-style:=\"\" text-decoration-color:=\"\" width:=\"\" height:=\"\" max-width:=\"\"/><img class=\"\" src=\"https://img10.360buyimg.com/imgzone/jfs/t10990/279/2709631582/127483/693ba8fd/5cd517c9N21fe6b58.jpg\" style=\"margin: 0px; padding: 0px; border: 0px; vertical-align: middle; color: rgb(102, 102, 102); font-family: tahoma, arial, \" microsoft=\"\" hiragino=\"\" sans=\"\" font-size:=\"\" font-style:=\"\" font-variant-ligatures:=\"\" font-variant-caps:=\"\" font-weight:=\"\" letter-spacing:=\"\" orphans:=\"\" text-align:=\"\" text-indent:=\"\" text-transform:=\"\" white-space:=\"\" widows:=\"\" word-spacing:=\"\" -webkit-text-stroke-width:=\"\" background-color:=\"\" text-decoration-style:=\"\" text-decoration-color:=\"\" width:=\"\" height:=\"\" max-width:=\"\"/></p>'), ('12', ''), ('13', ''), ('14', ''), ('15', '<p><img src=\"//cdn.shop.leimingyun.com/site/defaultparam1/news/20200325/1585127430280044296.jpg\" title=\"1585127430280044296.jpg\" alt=\"白菜001.jpg\"/></p>'), ('16', '<p><img src=\"//cdn.shop.leimingyun.com/site/defaultparam1/news/20200325/1585127404554060385.jpg\" title=\"1585127404554060385.jpg\" alt=\"商品详情页-西红柿.jpg\"/></p>'), ('17', '<p><img src=\"//cdn.shop.leimingyun.com/site/defaultparam1/news/20200325/1585127376743008964.jpg\" title=\"1585127376743008964.jpg\" alt=\"商品详情页-黄瓜.jpg\"/></p>'), ('18', '<p>111111</p>'), ('19', '<p>测试111111111111111</p>'), ('20', '<p>测试111111111111111</p>'), ('21', '<p>测试111111111111111</p>'), ('22', ''), ('23', ''), ('24', ''), ('25', ''), ('26', '');
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
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

-- ----------------------------
--  Records of `shop_goods_image`
-- ----------------------------
BEGIN;
INSERT INTO `shop_goods_image` VALUES ('4', '5', '//cdn.shop.leimingyun.com/goodsList/471c8ddf7b81436da9efc0c81a5bb351.jpg', '3'), ('5', '8', '//cdn.shop.leimingyun.com/goodsList/b27b1c440f4544b18ec34523d1833d14.jpg', '1'), ('6', '6', '//cdn.shop.leimingyun.com/store/1/images/5fd999c864a54d219b20d69aba4dd6b6.jpg', '1'), ('7', '6', '//cdn.shop.leimingyun.com/goodsList/dcdc7a35e31d48188c992eba4149c389.jpg', '4'), ('8', '6', '//cdn.shop.leimingyun.com/store/1/images/63a835eee0614bf591af05ec6987f202.jpg', '3'), ('9', '7', '//cdn.shop.leimingyun.com/store/1/images/cc92251ae7b04acb9317a6d5f0297a10.jpg', '1'), ('10', '10', '//cdn.shop.leimingyun.com/store/1/images/15e01d0dd685486791fc90c36aed2de5.jpg', '2'), ('11', '11', '//cdn.shop.leimingyun.com/store/1/images/0f9f8a968b03465589b647b2b7c60dac.png', '0'), ('12', '11', '//cdn.shop.leimingyun.com/store/1/images/72f6fb371254400ab4ffc66a520ad306.gif', '0'), ('13', '12', '//cdn.shop.leimingyun.com/store/1/images/5faeb2643ed84e26b6c2d3485a20f93b.png', '0'), ('14', '12', '//cdn.shop.leimingyun.com/store/1/images/bc71d90e58674ff4bce5958576a07a2d.png', '0'), ('15', '7', '//cdn.shop.leimingyun.com/store/1/images/447435a34cd1470f8869f5f325c696b9.jpg', '2'), ('16', '14', '//cdn.shop.leimingyun.com/store/1/images/3ba5f17f22d540309747a6f39ac82100.jpg', '3'), ('17', '14', '//cdn.shop.leimingyun.com/store/1/images/a79ba9824f074c05b9a326261bfa264c.jpg', '2'), ('18', '15', '//cdn.shop.leimingyun.com/store/1/images/f5a07d6c78d44ae3a58ecb87625aa339.jpg', '2'), ('19', '15', '//cdn.shop.leimingyun.com/store/1/images/4866bc8756374817a7445abdb4152c4f.jpg', '3'), ('20', '17', '//cdn.shop.leimingyun.com/store/1/images/a1423f57f4c84b339b1a66090da53181.jpg', '2'), ('21', '17', '//cdn.shop.leimingyun.com/store/1/images/ace71fb3762e4dfbb3dcb4fe95a7f566.jpg', '3'), ('22', '16', '//cdn.shop.leimingyun.com/store/1/images/e89e36ff2fb74aaab57718cd2eb6428d.jpg', '1'), ('23', '16', '//cdn.shop.leimingyun.com/store/1/images/a164e5b612894c6ea96a1d53e16bf05b.jpg', '2'), ('24', '26', '//cdn.shop.leimingyun.com/store/4356/images/d69b33c875584f8f99381c159a0d15d6.jpg', '1'), ('26', '26', '//cdn.shop.leimingyun.com/store/4356/images/4a24fc5854e1459490db6cf780620338.jpg', '2'), ('27', '26', '//cdn.shop.leimingyun.com/store/4356/images/5a04625f624a4480b9fd2b6d29e87e4d.jpg', '3'), ('28', '26', '//cdn.shop.leimingyun.com/store/4356/images/f5ee29be58c54251a148f04586b2857b.jpg', '4'), ('29', '25', '//cdn.shop.leimingyun.com/store/4356/images/35f3bf3db76f4c549e26e47569f4879a.jpg', '0'), ('30', '25', '//cdn.shop.leimingyun.com/store/4356/images/468904881ac243b4af206dded5e6978e.jpg', '0'), ('31', '25', '//cdn.shop.leimingyun.com/store/4356/images/84ce00951b754912a72bac9dda403948.jpg', '0'), ('32', '25', '//cdn.shop.leimingyun.com/store/4356/images/7b749728403b469785902e97a49fb051.jpg', '0'), ('33', '24', '//cdn.shop.leimingyun.com/store/4356/images/cbf0819da0f44bb99a252e1b4f95b1e3.jpg', '0'), ('34', '24', '//cdn.shop.leimingyun.com/store/4356/images/a9ad8b571bbb4a7e85fce9eacee77e6a.jpg', '0'), ('35', '24', '//cdn.shop.leimingyun.com/store/4356/images/60ebc8c8cb7146618d351c69f0b950cd.jpg', '0'), ('36', '24', '//cdn.shop.leimingyun.com/store/4356/images/a21a12aa151e4a228aca9972f52f585b.jpg', '0');
COMMIT;

-- ----------------------------
--  Table structure for `shop_goods_type`
-- ----------------------------
DROP TABLE IF EXISTS `shop_goods_type`;
CREATE TABLE `shop_goods_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `icon` char(100) DEFAULT NULL COMMENT '分类的图标、图片',
  `isdelete` tinyint(2) DEFAULT NULL COMMENT '是否被删除',
  `rank` int(11) DEFAULT NULL COMMENT '分类间的排序，数字越小越靠前',
  `storeid` int(11) DEFAULT NULL COMMENT '对应店铺id, Store.id ， 该分类是属于那个店铺的',
  `title` char(20) DEFAULT NULL COMMENT '分类的名称',
  PRIMARY KEY (`id`),
  KEY `suoyin_index` (`rank`,`storeid`,`isdelete`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

-- ----------------------------
--  Records of `shop_goods_type`
-- ----------------------------
BEGIN;
INSERT INTO `shop_goods_type` VALUES ('1', '//cdn.shop.leimingyun.com/store/1/images/0505abd48c0e4283b06e85442858ff5c.png', '0', '1', '1', '农副馆'), ('2', '//cdn.shop.leimingyun.com/store/1/images/f9b133c7a2014d8c8e1829643ed2a52a.png', '0', '2', '1', '休闲零食'), ('3', '//wangmarket1577678564.oss-cn-qingdao.aliyuncs.com/goodsType/65589a47d76e43c4b0ee6f0c37aa1da8.jpg', '0', '3', '1', '酒水乳饮'), ('4', '//wangmarket1577678564.oss-cn-qingdao.aliyuncs.com/goodsType/7cc9e91476804996b3baee627ee4f36e.jpg', '0', '4', '1', '粮油调味'), ('5', '//cdn.shop.leimingyun.com/store/1/images/4e49c1d7f7cc41748e654a7335e487dd.png', '0', '5', '1', '餐食料理'), ('6', '//cdn.shop.leimingyun.com/store/1/images/acd86baaf63d459096fa868d4522482c.png', '1', '3', '1', '2222'), ('7', '//cdn.shop.leimingyun.com/store/1211/images/551398fc5a9c485fbda1e787513073b9.png', '0', '1', '1211', '当订单'), ('8', '//cdn.shop.leimingyun.com/store/1211/images/875581961cae4c038fe951a060bc54e2.gif', '0', '3', '1211', '444'), ('9', '//cdn.shop.leimingyun.com/store/1/images/21b740dd5ed5465993e1fb8c0aaa0855.jpg', '1', '6', '1', '个护美妆'), ('10', null, '0', '1', '4192', '1'), ('11', '//cdn.shop.leimingyun.com/store/1399/images/fe52cc670afc48c48c6a8ba1db1a508e.png', '1', '1', '1399', '1'), ('12', null, '0', '1', '4315', '23423'), ('13', null, '0', '1', '4356', '默认分类');
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
  `phone` char(20) DEFAULT NULL COMMENT '用户电话',
  `remark` char(100) DEFAULT NULL COMMENT '买家的备注',
  `state` char(20) DEFAULT NULL COMMENT '订单状态',
  `storeid` int(11) DEFAULT NULL COMMENT '商家id，这个订单购买的商品是哪个商家的。如果一次购买多个店铺的商品，那么最终订单会分解为每个店铺一个订单',
  `total_money` int(11) DEFAULT NULL COMMENT '订单总金额,单位：元',
  `userid` int(11) DEFAULT NULL COMMENT '该订单所属的用户，是哪个用户下的单，对应 User.id',
  `username` char(20) DEFAULT NULL COMMENT '用户填写名字',
  `version` int(11) DEFAULT '0' COMMENT '乐观锁',
  PRIMARY KEY (`id`),
  KEY `suoyin_index` (`no`,`storeid`,`userid`,`state`)
) ENGINE=InnoDB AUTO_INCREMENT=147 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

-- ----------------------------
--  Records of `shop_order`
-- ----------------------------
BEGIN;
INSERT INTO `shop_order` VALUES ('1', null, '1582200380', 'Q601MKMHCY', '1', null, null, '', 'generate_but_no_pay', '1', '600', '1', null, '0'), ('2', null, '1582200533', 'Q601QTC0B7', '1', null, null, '', 'refund_ing', '1', '1200', '1', null, '2'), ('3', null, '1582201620', 'Q602L0EEEU', '1', null, null, '', 'receive_goods', '1', '300', '1', null, '2'), ('4', null, '1582201650', 'Q602LUJQ3K', '1', null, null, '', 'private_pay', '1', '600', '1', null, '1'), ('5', null, '1582201693', 'Q602N1N4LQ', '1', null, null, '', 'my_cancel', '1', '600', '1', null, '1'), ('6', null, '1582264739', 'Q61FABY4S7', '1', null, null, '备注啊啊啊啊啊', 'private_pay', '1', '900', '1', null, '1'), ('7', null, '1582460325', 'Q65M79BGBI', '1', null, null, '哈哈哈哈哈', 'private_pay', '1', '900', '1', null, '1'), ('8', null, '1582723897', 'Q6B9KPYOHP', '1', null, null, 'adf', 'pay', '1', '600', '1', null, '1'), ('9', null, '1582777063', 'Q6CELJ4IVE', '1', null, null, '哥哥哥哥', 'generate_but_no_pay', '1', '600', '1', null, '0'), ('10', null, '1582778282', 'Q6CFJELLX4', '1', null, null, 'djdudjdj', 'generate_but_no_pay', '1', '600', '1', null, '0'), ('11', null, '1582896729', 'Q6EYXLOLTW', '1200', null, null, '6777', 'generate_but_no_pay', '1', '1200', '1', null, '0'), ('12', null, '1582939929', 'Q6FW9LWSJL', '2', null, null, '35656456', 'pay', '1', '2', '1', null, '1'), ('13', null, '1582940013', 'Q6FWBXZGTC', '1', null, null, '', 'pay_timeout_cancel', '1', '1', '1', null, '1'), ('14', null, '1583044227', 'Q6I4QRM6P6', '1', null, null, '', 'private_pay', '1', '1', '398', null, '1'), ('15', null, '1583044267', 'Q6I4RVBTWL', '2', null, null, '', 'pay_timeout_cancel', '1', '2', '398', null, '1'), ('16', null, '1583044324', 'Q6I4TGWEV3', '1', null, null, '', 'pay_timeout_cancel', '1', '1', '398', null, '1'), ('17', null, '1583140289', 'Q6K6V57JV0', '18', null, null, '444', 'pay_timeout_cancel', '1', '18', '1', null, '1'), ('18', null, '1583140395', 'Q6K6Y3O74D', '3', null, null, '', 'pay_timeout_cancel', '1', '3', '1', null, '1'), ('19', null, '1583140545', 'Q6K7291I8X', '2', null, null, '', 'pay_timeout_cancel', '1', '2', '1', null, '1'), ('20', null, '1583140673', 'Q6K75TI7H2', '1', null, null, '', 'pay_timeout_cancel', '1', '1', '1', null, '1'), ('21', null, '1583144845', 'Q6KADPCB0B', '2', null, null, '', 'private_pay', '1', '2', '1', null, '1'), ('22', null, '1583155109', 'Q6KIAT7R20', '9', null, null, '', 'pay_timeout_cancel', '1', '9', '398', null, '1'), ('23', null, '1583333281', 'Q6OBS1O5FS', '1', null, null, '333', 'pay_timeout_cancel', '1', '1', '1', null, '1'), ('24', null, '1583334480', 'Q6OCPCUWOW', '1', null, null, '', 'pay_timeout_cancel', '1', '1', '1', null, '1'), ('25', null, '1583335205', 'Q6OD9HL60H', '1', null, null, '', 'pay_timeout_cancel', '1', '1', '1', null, '1'), ('26', null, '1583335607', 'Q6ODKN2G2V', '2', null, null, '', 'pay_timeout_cancel', '1', '2', '1', null, '1'), ('27', null, '1583336229', 'Q6OE1XLNVE', '1', null, null, '', 'pay_timeout_cancel', '1', '1', '1', null, '1'), ('28', null, '1583371623', 'Q6P5D3RUHZ', '1', null, null, '', 'pay_timeout_cancel', '1', '1', '1', null, '1'), ('29', null, '1583372237', 'Q6P5U5DQZI', '1', null, null, '', 'pay_timeout_cancel', '1', '1', '1', null, '1'), ('30', null, '1583374685', 'Q6P7Q58SCB', '2', null, null, '', 'pay_timeout_cancel', '1', '2', '1', null, '1'), ('31', null, '1583458132', 'Q6R0448OWU', '1', null, null, '', 'pay_timeout_cancel', '1', '1', '1', null, '1'), ('32', null, '1583460079', 'Q6R1M71YE2', '1', null, null, '', 'my_cancel', '1', '1', '1', null, '1'), ('33', null, '1583464594', 'Q6R53M2CDH', '1', null, null, '', 'my_cancel', '1', '1', '1', null, '1'), ('34', null, '1583464709', 'Q6R56TQING', '1', null, null, '', 'my_cancel', '1', '1', '1', null, '1'), ('35', null, '1583466178', 'Q6R6BM3KV9', '1', null, null, '', '', '1', '1', '1', null, '3'), ('36', null, '1583482836', 'Q6RJ6C69VU', '1', null, null, '', 'pay_timeout_cancel', '1', '1', '1', null, '1'), ('37', null, '1583483512', 'Q6RJP4SLZR', '1', null, null, '', 'my_cancel', '1', '1', '1', null, '7'), ('38', null, '1583485180', 'Q6RKZGLFYM', '1', null, null, '', 'private_pay', '1', '1', '1', null, '1'), ('39', null, '1583485636', 'Q6RLC44G0L', '1', null, null, '', 'receive_goods', '1', '1', '1', null, '4'), ('40', null, '1583493126', 'Q6RR46CC88', '1', null, null, '', 'pay_timeout_cancel', '1', '1', '1', null, '1'), ('41', null, '1583493143', 'Q6RR4N4L8A', '1', null, null, '', 'pay_timeout_cancel', '1', '1', '1', null, '1'), ('42', null, '1583547404', 'Q6SWZWZKJD', '1', null, null, '', 'pay_timeout_cancel', '1', '1', '1', null, '1'), ('43', null, '1583558130', 'Q6T59U27RO', '51', null, null, '', 'pay_timeout_cancel', '1', '51', '1', null, '1'), ('44', null, '1583558221', 'Q6T5CDCH83', '1', null, null, '', 'my_cancel', '1', '1', '1', null, '1'), ('45', null, '1583562948', 'Q6T8ZOTYRQ', '153', null, null, '', 'pay_timeout_cancel', '1', '153', '1', null, '1'), ('46', null, '1583564609', 'Q6TA9TJ3JI', '1', null, null, '', 'pay_timeout_cancel', '1', '1', '1', null, '1'), ('47', null, '1583904358', 'Q70KFATW44', '1', null, null, '', 'pay_timeout_cancel', '1', '1', '1', null, '1'), ('48', null, '1583904746', 'Q70KQ2FKSK', '1', null, null, '', 'pay', '1', '1', '1', null, '1'), ('53', null, '1584525600', 'Q7DVS03IXY', '51', null, null, '', 'pay_timeout_cancel', '1', '51', '1', null, '1'), ('54', null, '1584525608', 'Q7DVS8LOA0', '5', null, null, '', 'pay_timeout_cancel', '1', '5', '1', null, '1'), ('55', null, '1584525623', 'Q7DVSN9P7R', '1', null, null, '', 'pay_timeout_cancel', '1', '1', '1', null, '1'), ('56', null, '1584526118', 'Q7DW6EY43H', '1', null, null, '', 'pay_timeout_cancel', '1', '1', '411', null, '1'), ('57', null, '1584580021', 'Q7F1RPIB85', '2', null, null, '', 'private_pay', '1', '2', '1', null, '1'), ('58', null, '1584580302', 'Q7F1ZIJ2V9', '1', null, null, '', 'pay_timeout_cancel', '1', '1', '1', null, '1'), ('59', null, '1584598124', 'Q7FFQKJCDY', '477', null, null, '', 'private_pay', '1', '477', '411', null, '1'), ('60', null, '1584600032', 'Q7FH7K7XC9', '357', null, null, '', 'private_pay', '1', '357', '411', null, '3'), ('61', null, '1584603627', 'Q7FJZF5JA5', '102', null, null, '', 'pay_timeout_cancel', '1', '102', '413', null, '1'), ('62', null, '1584603667', 'Q7FK0JYWK5', '51', null, null, '', 'private_pay', '1', '51', '1', null, '1'), ('63', null, '1584693893', 'Q7HHMTPNFE', '3', null, null, '', 'pay_timeout_cancel', '1', '3', '411', null, '1'), ('64', null, '1584699543', 'Q7HLZRCX9P', '24', null, null, '111', 'receive_goods', '1', '24', '411', null, '2'), ('65', null, '1584714893', 'Q7HXU5R1WI', '2', null, null, '', 'pay_timeout_cancel', '1', '2', '1', null, '1'), ('66', null, '1584753938', 'Q7IRYQ9CGH', '6', null, null, '三生三世', 'private_pay', '1', '6', '1', null, '3'), ('67', null, '1584754988', 'Q7ISRWG1FD', '36', null, null, '4444', 'private_pay', '1', '36', '411', null, '1'), ('68', null, '1584756455', 'Q7ITWNB2UI', '1', null, null, '', 'private_pay', '1', '1', '411', null, '1'), ('69', null, '1584770107', 'Q7J4FV20KN', '56', null, null, '', 'pay_timeout_cancel', '1', '56', '413', null, '1'), ('70', null, '1584770171', 'Q7J4HN1YQH', '1', null, null, '', 'pay_timeout_cancel', '1', '1', '413', null, '1'), ('71', null, '1584772262', 'Q7J63Q61Y9', '2', null, null, '', 'pay_timeout_cancel', '1', '2', '413', null, '1'), ('72', null, '1584772359', 'Q7J66F7SYT', '1', null, null, '', 'pay_timeout_cancel', '1', '1', '413', null, '1'), ('73', null, '1584772536', 'Q7J6BCTTA3', '1', null, null, '', 'pay_timeout_cancel', '1', '1', '413', null, '1'), ('74', null, '1584772745', 'Q7J6H5F23S', '1', null, null, '', 'pay_timeout_cancel', '1', '1', '413', null, '1'), ('75', null, '1584772822', 'Q7J6JA4U0B', '1', null, null, '', 'pay_timeout_cancel', '1', '1', '413', null, '1'), ('76', null, '1584772858', 'Q7J6KAF7ME', '1', null, null, '', 'pay_timeout_cancel', '1', '1', '413', null, '1'), ('77', null, '1584772888', 'Q7J6L4TXHR', '1', null, null, '', 'pay_timeout_cancel', '1', '1', '413', null, '1'), ('78', null, '1584772934', 'Q7J6MEZAPH', '1', null, null, '', 'pay_timeout_cancel', '1', '1', '413', null, '1'), ('79', null, '1584773278', 'Q7J6VYH0LT', '1', null, null, '', 'pay_timeout_cancel', '1', '1', '413', null, '1'), ('80', null, '1584773652', 'Q7J76CTT4R', '1', null, null, '', 'pay_timeout_cancel', '1', '1', '413', null, '1'), ('81', null, '1584773684', 'Q7J7786C80', '1', null, null, '', 'pay_timeout_cancel', '1', '1', '413', null, '1'), ('82', null, '1584773938', 'Q7J7EAO00X', '3', null, null, '', 'pay_timeout_cancel', '1', '3', '413', null, '1'), ('83', null, '1584774091', 'Q7J7IJK9NB', '1', null, null, '', 'pay_timeout_cancel', '1', '1', '413', null, '1'), ('84', null, '1584782895', 'Q7JEB3B0RT', '9', null, null, '', 'pay_timeout_cancel', '1', '9', '411', null, '1'), ('85', null, '1584783929', 'Q7JF3TCWZG', '8', null, null, '', 'pay_timeout_cancel', '1', '8', '411', null, '1'), ('86', null, '1584787553', 'Q7JHWHGNQI', '51', null, null, '', 'refund_ing', '1', '51', '418', null, '2'), ('87', null, '1584787821', 'Q7JI3XU9Q0', '6', null, null, '', 'pay_timeout_cancel', '1', '6', '1', null, '1'), ('88', null, '1584787856', 'Q7JI4WG6IK', '2', null, null, '', 'pay_timeout_cancel', '1', '2', '1', null, '1'), ('89', null, '1584787995', 'Q7JI8RGLXN', '2', null, null, '', 'private_pay', '1', '2', '1', null, '1'), ('90', null, '1584788037', 'Q7JI9XSRVK', '6', null, null, '', 'private_pay', '1', '6', '1', null, '1'), ('91', null, '1584792489', 'Q7JLPL65DF', '52', null, null, '', 'pay_timeout_cancel', '1', '52', '413', null, '1'), ('92', null, '1584927005', 'Q7MHI52AOW', '10', null, null, '', 'pay_timeout_cancel', '1', '10', '413', null, '1'), ('95', null, '1584930656', 'Q7MKBKM7MO', '51', null, null, '', 'pay_timeout_cancel', '1', '51', '413', null, '1'), ('96', null, '1584930702', 'Q7MKCUVNQ7', '1', null, null, '', 'pay_timeout_cancel', '1', '1', '413', null, '1'), ('97', null, '1584932239', 'Q7MLJJFRGP', '11', null, null, '', 'pay_timeout_cancel', '1', '11', '413', null, '1'), ('101', null, '1584932515', 'Q7MLR75YOV', '1', null, null, '', 'private_pay', '1', '1', '1', null, '1'), ('102', null, '1584932584', 'Q7MLT4U2RF', '1', null, null, '', 'pay_timeout_cancel', '1', '1', '1', null, '1'), ('103', null, '1584932629', 'Q7MLUDFM46', '1', null, null, '', 'receive_goods', '1', '1', '419', null, '4'), ('108', null, '1584943475', 'Q7MU7ND9HV', '1', null, null, '', 'pay_timeout_cancel', '1', '1', '413', null, '1'), ('109', null, '1584943839', 'Q7MUHRN93B', '2', null, null, '', 'pay_timeout_cancel', '1', '2', '413', null, '1'), ('110', null, '1585044685', 'Q7P0B15Y4B', '7', null, null, '', 'receive_goods', '1', '7', '1', null, '6'), ('111', null, '1585052934', 'Q7P6O6YPFK', '6', null, null, '', 'refund_ing', '1', '6', '1', null, '2'), ('112', null, '1585128244', 'Q7QSS4VGIX', '51', null, null, '', 'pay_timeout_cancel', '1', '51', '413', null, '1'), ('113', null, '1585297219', 'Q7UF5VXETR', '504', null, null, '', 'pay_timeout_cancel', '1', '504', '413', null, '1'), ('114', null, '1585299713', 'Q7UH35VC9X', '1', null, null, '', 'pay_timeout_cancel', '1', '1', '413', null, '1'), ('115', null, '1585299827', 'Q7UH6BUUP7', '2', null, null, '', 'pay_timeout_cancel', '1', '2', '413', null, '1'), ('116', null, '1585300389', 'Q7UHLX7V8B', '500', null, null, '', 'pay_timeout_cancel', '1', '500', '413', null, '1'), ('117', null, '1586070261', 'Q8AZN9BP4F', '1', null, null, '', 'receive_goods', '1', '1', '1', null, '2'), ('118', null, '1587019898', 'Q8VCE2Q08J', '1', null, null, '', 'receive_goods', '1', '1', '449', null, '2'), ('119', null, '1587019978', 'Q8VCGA550A', '1', null, null, '', 'pay_timeout_cancel', '1', '1', '449', null, '1'), ('120', null, '1587020318', 'Q8VCPQMFF7', '1', null, null, '', 'pay_timeout_cancel', '1', '1', '449', null, '1'), ('121', null, '1587889876', 'Q9DZO423PT', '2', null, null, '', 'receive_goods', '1', '2', '1', null, '4'), ('122', null, '1588040481', 'Q9H7VL8LCS', '500', null, null, '', 'private_pay', '1', '500', '1', null, '3'), ('123', null, '1588052502', 'Q9HH5I96JI', '2', null, null, '', 'receive_goods', '1', '2', '393', null, '2'), ('124', null, '1588052707', 'Q9HHB7VYC4', '1', null, null, '', 'receive_goods', '1', '1', '465', null, '4'), ('125', null, '1588053237', 'Q9HHPX23TB', '0', null, null, '推荐的用户id465首次下单并消费完成，这个0元产品是奖励给你的', 'pay', '1', '500', '393', null, '0'), ('126', null, '1588133331', 'Q9J7IRVK31', '1', null, null, '', 'pay_timeout_cancel', '1', '1', '472', null, '1'), ('127', null, '1588141559', 'Q9JDVBW38Q', '51', null, null, '', 'pay_timeout_cancel', '1', '51', '472', null, '1'), ('128', null, '1588141622', 'Q9JDX26IW5', '1', null, null, '', 'pay_timeout_cancel', '1', '1', '472', null, '1'), ('129', null, '1588141823', 'Q9JE2N7RTK', '1', null, null, '', 'pay_timeout_cancel', '1', '1', '472', null, '1'), ('130', null, '1588141903', 'Q9JE4VD4XZ', '1', null, null, '', 'pay_timeout_cancel', '1', '1', '472', null, '1'), ('131', null, '1588141942', 'Q9JE5YP9BW', '1', null, null, '', 'pay_timeout_cancel', '1', '1', '472', null, '1'), ('132', null, '1588141962', 'Q9JE6I7Y0W', '1', null, null, '', 'pay_timeout_cancel', '1', '1', '472', null, '1'), ('133', null, '1588144905', 'Q9JGG9GZHI', '2', null, null, '', 'pay_timeout_cancel', '1', '2', '478', null, '1'), ('134', null, '1588146126', 'Q9JHE6PFUI', '1', null, null, '', 'pay_timeout_cancel', '1', '1', '480', null, '1'), ('135', null, '1588208582', 'Q9KTL2PK9I', '1', null, null, '', 'private_pay', '1', '1', '481', null, '1'), ('136', null, '1588236863', 'Q9LFENASQY', '1', null, null, '', 'pay_timeout_cancel', '1', '1', '478', null, '1'), ('137', null, '1588236976', 'Q9LFHSCFOG', '1', null, null, '', 'pay_timeout_cancel', '1', '1', '478', null, '1'), ('138', null, '1588237103', 'Q9LFLBJ00A', '2', null, null, '', 'pay_timeout_cancel', '1', '2', '478', null, '1'), ('139', null, '1588237129', 'Q9LFM1R6FA', '2', null, null, '', 'pay_timeout_cancel', '1', '2', '478', null, '1'), ('140', null, '1588237550', 'Q9LFXQSDU7', '1', null, null, '', 'pay_timeout_cancel', '1', '1', '478', null, '1'), ('141', null, '1588237586', 'Q9LFYQZQ39', '51', null, null, '', 'pay_timeout_cancel', '1', '51', '478', null, '1'), ('142', null, '1588240301', 'Q9LI257N0K', '1', null, null, '', 'pay_timeout_cancel', '1', '1', '472', null, '1'), ('143', null, '1588241282', 'Q9LITEVK1B', '1', null, null, '', 'pay_timeout_cancel', '1', '1', '478', null, '1'), ('144', null, '1588244940', 'Q9LLN0DOS0', '200', null, null, '', 'pay_timeout_cancel', '1', '200', '472', null, '1'), ('145', null, '1588246511', 'Q9LMUNJKKG', '2', null, null, '', 'pay_timeout_cancel', '1', '2', '472', null, '1'), ('146', null, '1588812560', 'Q9XRM81SDN', '5000', null, null, '', 'pay_timeout_cancel', '4356', '5000', '472', null, '1');
COMMIT;

-- ----------------------------
--  Table structure for `shop_order_address`
-- ----------------------------
DROP TABLE IF EXISTS `shop_order_address`;
CREATE TABLE `shop_order_address` (
  `id` int(11) NOT NULL,
  `address` varchar(1000) DEFAULT NULL COMMENT '具体地址',
  `latitude` double(6,6) DEFAULT NULL COMMENT '经纬度',
  `longitude` double(6,6) DEFAULT NULL COMMENT '经纬度',
  `phone` varchar(100) DEFAULT NULL COMMENT '收货人手机号',
  `username` varchar(50) DEFAULT NULL COMMENT '收货人用户姓名',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

-- ----------------------------
--  Records of `shop_order_address`
-- ----------------------------
BEGIN;
INSERT INTO `shop_order_address` VALUES ('1', '', null, null, '', null), ('2', '', null, null, '', null), ('3', '', null, null, '', null), ('4', '', null, null, '', null), ('5', '', null, null, '', null), ('6', '山东潍坊市寒亭区开元街道通亭街亚星路路口向西20米路北，中国兽药饲料交易大厦十三楼E1308', '0.000000', '0.000000', '17076012262', null), ('7', '山东潍坊市寒亭区开元街道通亭街亚星路路口向西20米路北，中国兽药饲料交易大厦十三楼E1308', '0.000000', '0.000000', '17076012262', '管雷鸣'), ('8', '山东潍坊市寒亭区开元街道通亭街亚星路路口向西20米路北，中国兽药饲料交易大厦十三楼E1308', '0.000000', '0.000000', '17076012262', '管雷鸣'), ('9', '山东潍坊市寒亭区开元街道通亭街亚星路路口向西20米路北，中国兽药饲料交易大厦十三楼E1308', '0.000000', '0.000000', '17076012262', '管雷鸣'), ('10', '山东潍坊市寒亭区开元街道通亭街亚星路路口向西20米路北，中国兽药饲料交易大厦十三楼E1308', '0.000000', '0.000000', '17076012262', '管雷鸣'), ('11', '山东潍坊市寒亭区开元街道通亭街亚星路路口向西20米路北，中国兽药饲料交易大厦十三楼E1308', '0.000000', '0.000000', '17076012262', '管雷鸣'), ('12', '山东潍坊市寒亭区开元街道通亭街亚星路路口向西20米路北，中国兽药饲料交易大厦十三楼E1308', '0.000000', '0.000000', '17076012262', '管雷鸣'), ('13', '山东潍坊市寒亭区开元街道通亭街亚星路路口向西20米路北，中国兽药饲料交易大厦十三楼E1308', '0.000000', '0.000000', '17076012262', '管雷鸣'), ('14', '湖北', '0.000000', '0.000000', '15997778666', 'ceshi520'), ('15', '湖北', '0.000000', '0.000000', '15997778666', 'ceshi520'), ('16', '湖北', '0.000000', '0.000000', '15997778666', 'ceshi520'), ('17', '山东潍坊市寒亭区开元街道通亭街亚星路路口向西20米路北，中国兽药饲料交易大厦十三楼E1308', '0.000000', '0.000000', '17076012262', '管雷鸣'), ('18', '山东潍坊市寒亭区开元街道通亭街亚星路路口向西20米路北，中国兽药饲料交易大厦十三楼E1308', '0.000000', '0.000000', '17076012262', '管雷鸣'), ('19', '山东潍坊市寒亭区开元街道通亭街亚星路路口向西20米路北，中国兽药饲料交易大厦十三楼E1308', '0.000000', '0.000000', '17076012262', '管雷鸣'), ('20', '山东潍坊市寒亭区开元街道通亭街亚星路路口向西20米路北，中国兽药饲料交易大厦十三楼E1308', '0.000000', '0.000000', '17076012262', '管雷鸣'), ('21', '山东潍坊市寒亭区开元街道通亭街亚星路路口向西20米路北，中国兽药饲料交易大厦十三楼E1308', '0.000000', '0.000000', '17076012262', '管雷鸣'), ('22', '湖北', '0.000000', '0.000000', '15997778666', 'ceshi520'), ('23', '山东潍坊市寒亭区开元街道通亭街亚星路路口向西20米路北，中国兽药饲料交易大厦十三楼E1308', '0.000000', '0.000000', '17076012262', '管雷鸣'), ('24', '山东潍坊市寒亭区开元街道通亭街亚星路路口向西20米路北，中国兽药饲料交易大厦十三楼E1308', '0.000000', '0.000000', '17076012262', '管雷鸣'), ('25', '山东潍坊市寒亭区开元街道通亭街亚星路路口向西20米路北，中国兽药饲料交易大厦十三楼E1308', '0.000000', '0.000000', '17076012262', '管雷鸣'), ('26', '山东潍坊市寒亭区开元街道通亭街亚星路路口向西20米路北，中国兽药饲料交易大厦十三楼E1308', '0.000000', '0.000000', '17076012262', '管雷鸣'), ('27', '山东潍坊市寒亭区开元街道通亭街亚星路路口向西20米路北，中国兽药饲料交易大厦十三楼E1308', '0.000000', '0.000000', '17076012262', '管雷鸣'), ('28', '山东潍坊市寒亭区开元街道通亭街亚星路路口向西20米路北，中国兽药饲料交易大厦十三楼E1308', '0.000000', '0.000000', '17076012262', '管雷鸣'), ('29', '山东潍坊市寒亭区开元街道通亭街亚星路路口向西20米路北，中国兽药饲料交易大厦十三楼E1308', '0.000000', '0.000000', '17076012262', '管雷鸣'), ('30', '山东潍坊市寒亭区开元街道通亭街亚星路路口向西20米路北，中国兽药饲料交易大厦十三楼E1308', '0.000000', '0.000000', '17076012262', '管雷鸣'), ('31', '山东潍坊市寒亭区开元街道通亭街亚星路路口向西20米路北，中国兽药饲料交易大厦十三楼E1308', '0.000000', '0.000000', '17076012262', '管雷鸣'), ('32', '山东潍坊市寒亭区开元街道通亭街亚星路路口向西20米路北，中国兽药饲料交易大厦十三楼E1308', '0.000000', '0.000000', '17076012262', '管雷鸣'), ('33', '山东潍坊市寒亭区开元街道通亭街亚星路路口向西20米路北，中国兽药饲料交易大厦十三楼E1308', '0.000000', '0.000000', '17076012262', '管雷鸣'), ('34', '山东潍坊市寒亭区开元街道通亭街亚星路路口向西20米路北，中国兽药饲料交易大厦十三楼E1308', '0.000000', '0.000000', '17076012262', '管雷鸣'), ('35', '山东潍坊市寒亭区开元街道通亭街亚星路路口向西20米路北，中国兽药饲料交易大厦十三楼E1308', '0.000000', '0.000000', '17076012262', '管雷鸣'), ('36', '山东潍坊市寒亭区开元街道通亭街亚星路路口向西20米路北，中国兽药饲料交易大厦十三楼E1308', '0.000000', '0.000000', '17076012262', '管雷鸣'), ('37', '山东潍坊市寒亭区开元街道通亭街亚星路路口向西20米路北，中国兽药饲料交易大厦十三楼E1308', '0.000000', '0.000000', '17076012262', '管雷鸣'), ('38', '山东潍坊市寒亭区开元街道通亭街亚星路路口向西20米路北，中国兽药饲料交易大厦十三楼E1308', '0.000000', '0.000000', '17076012262', '管雷鸣'), ('39', '山东潍坊市寒亭区开元街道通亭街亚星路路口向西20米路北，中国兽药饲料交易大厦十三楼E1308', '0.000000', '0.000000', '17076012262', '管雷鸣'), ('40', '山东潍坊市寒亭区开元街道通亭街亚星路路口向西20米路北，中国兽药饲料交易大厦十三楼E1308', '0.000000', '0.000000', '17076012262', '管雷鸣'), ('41', '山东潍坊市寒亭区开元街道通亭街亚星路路口向西20米路北，中国兽药饲料交易大厦十三楼E1308', '0.000000', '0.000000', '17076012262', '管雷鸣'), ('42', '山东潍坊市寒亭区开元街道通亭街亚星路路口向西20米路北，中国兽药饲料交易大厦十三楼E1308', '0.000000', '0.000000', '17076012262', '管雷鸣'), ('43', '山东潍坊市寒亭区开元街道通亭街亚星路路口向西20米路北，中国兽药饲料交易大厦十三楼E1308', '0.000000', '0.000000', '17076012262', '管雷鸣'), ('44', '山东潍坊市寒亭区开元街道通亭街亚星路路口向西20米路北，中国兽药饲料交易大厦十三楼E1308', '0.000000', '0.000000', '17076012262', '管雷鸣'), ('45', '山东潍坊市寒亭区开元街道通亭街亚星路路口向西20米路北，中国兽药饲料交易大厦十三楼E1308', '0.000000', '0.000000', '17076012262', '管雷鸣'), ('46', '山东潍坊市寒亭区开元街道通亭街亚星路路口向西20米路北，中国兽药饲料交易大厦十三楼E1308', '0.000000', '0.000000', '17076012262', '管雷鸣'), ('47', '山东潍坊市寒亭区开元街道通亭街亚星路路口向西20米路北，中国兽药饲料交易大厦十三楼E1308', '0.000000', '0.000000', '17076012262', '管雷鸣'), ('48', '山东潍坊市寒亭区开元街道通亭街亚星路路口向西20米路北，中国兽药饲料交易大厦十三楼E1308', '0.000000', '0.000000', '17076012262', '管雷鸣'), ('53', '山东潍坊市寒亭区开元街道通亭街亚星路路口向西20米路北，中国兽药饲料交易大厦十三楼E1308', '0.000000', '0.000000', '17076012262', '管雷鸣'), ('54', '山东潍坊市寒亭区开元街道通亭街亚星路路口向西20米路北，中国兽药饲料交易大厦十三楼E1308', '0.000000', '0.000000', '17076012262', '管雷鸣'), ('55', '山东潍坊市寒亭区开元街道通亭街亚星路路口向西20米路北，中国兽药饲料交易大厦十三楼E1308', '0.000000', '0.000000', '17076012262', '管雷鸣'), ('56', '', '0.000000', '0.000000', '18706518236', ''), ('57', '', '0.000000', '0.000000', '', '上头'), ('58', '', '0.000000', '0.000000', '', '尽头'), ('59', '', '0.000000', '0.000000', '18706518236', ''), ('60', '', '0.000000', '0.000000', '18706518236', ''), ('61', 'null', '0.000000', '0.000000', 'null', 'null'), ('62', '山东潍坊市寒亭区开元街道通亭街亚星路路口向西20米路北，中国兽药饲料交易大厦十三楼E1308', '0.000000', '0.000000', '17076012262', '管雷鸣'), ('63', '', '0.000000', '0.000000', '', ''), ('64', 'sssssd', '0.000000', '0.000000', 'dddd', 'sddd'), ('65', '山东潍坊市寒亭区开元街道通亭街亚星路路口向西20米路北，中国兽药饲料交易大厦十三楼E1308', '0.000000', '0.000000', '17076012262', '管雷鸣'), ('66', '好多僵尸粉看巨额话费', '0.000000', '0.000000', '5545', '哈哈哈'), ('67', 'sgsdfsdf第三方史蒂夫史蒂夫史蒂夫史蒂夫史蒂夫史蒂夫收到sgsdfsdf第三方史蒂夫史蒂夫史蒂夫史蒂夫史蒂夫史蒂夫收到', '0.000000', '0.000000', '如444444', '44444'), ('68', 'sgsdfsdf第三方史蒂夫史蒂夫史蒂夫史蒂夫史蒂夫史蒂夫收到sgsdfsdf第三方史蒂夫史蒂夫史蒂夫史蒂夫史蒂夫史蒂夫收到', '0.000000', '0.000000', '如444444', '44444'), ('69', '梧州市我的位置', '0.000000', '0.000000', '15526541989', 'ggl'), ('70', '梧州市我的位置', '0.000000', '0.000000', '15526541989', 'ggl'), ('71', '梧州市我的位置', '0.000000', '0.000000', '15526541989', 'ggl'), ('72', '梧州市我的位置', '0.000000', '0.000000', '15526541989', 'ggl'), ('73', '梧州市我的位置', '0.000000', '0.000000', '15526541989', 'ggl'), ('74', '梧州市我的位置', '0.000000', '0.000000', '15526541989', 'ggl'), ('75', '梧州市我的位置', '0.000000', '0.000000', '15526541989', 'ggl'), ('76', '梧州市我的位置', '0.000000', '0.000000', '15526541989', 'ggl'), ('77', '梧州市我的位置', '0.000000', '0.000000', '15526541989', 'ggl'), ('78', '梧州市我的位置', '0.000000', '0.000000', '15526541989', 'ggl'), ('79', '梧州市我的位置', '0.000000', '0.000000', '15526541989', 'ggl'), ('80', '梧州市我的位置', '0.000000', '0.000000', '15526541989', 'ggl'), ('81', '梧州市我的位置', '0.000000', '0.000000', '15526541989', 'ggl'), ('82', '梧州市我的位置', '0.000000', '0.000000', '15526541989', 'ggl'), ('83', '梧州市我的位置', '0.000000', '0.000000', '15526541989', 'ggl'), ('84', 'sgsdfsdf第三方史蒂夫史蒂夫史蒂夫史蒂夫史蒂夫史蒂夫收到sgsdfsdf第三方史蒂夫史蒂夫史蒂夫史蒂夫史蒂夫史蒂夫收到', '0.000000', '0.000000', '如444444', '44444'), ('85', 'sgsdfsdf第三方史蒂夫史蒂夫史蒂夫史蒂夫史蒂夫史蒂夫收到sgsdfsdf第三方史蒂夫史蒂夫史蒂夫史蒂夫史蒂夫史蒂夫收到', '0.000000', '0.000000', '如444444', '44444'), ('86', 'null', '0.000000', '0.000000', 'null', 'null'), ('87', '山东潍坊市寒亭区开元街道通亭街亚星路路口向西20米路北，中国兽药饲料交易大厦十三楼E1308', '0.000000', '0.000000', '17076012262', '管雷鸣'), ('88', '山东潍坊市寒亭区开元街道通亭街亚星路路口向西20米路北，中国兽药饲料交易大厦十三楼E1308', '0.000000', '0.000000', '17076012262', '管雷鸣'), ('89', '山东潍坊市寒亭区开元街道通亭街亚星路路口向西20米路北，中国兽药饲料交易大厦十三楼E1308', '0.000000', '0.000000', '17076012262', '管雷鸣'), ('90', '山东潍坊市寒亭区开元街道通亭街亚星路路口向西20米路北，中国兽药饲料交易大厦十三楼E1308', '0.000000', '0.000000', '17076012262', '管雷鸣'), ('91', '梧州市我的位置', '0.000000', '0.000000', '15526541989', 'ggl'), ('92', '梧州市我的位置', '0.000000', '0.000000', '15526541989', 'ggl'), ('95', '梧州市我的位置', '0.000000', '0.000000', '15526541989', 'ggl'), ('96', '梧州市我的位置', '0.000000', '0.000000', '15526541989', 'ggl'), ('97', '梧州市我的位置', '0.000000', '0.000000', '15526541989', 'ggl'), ('101', '山东潍坊市寒亭区开元街道通亭街亚星路路口向西20米路北，中国兽药饲料交易大厦十三楼E1308', '0.000000', '0.000000', '17076012262', '管雷鸣'), ('102', '山东潍坊市寒亭区开元街道通亭街亚星路路口向西20米路北，中国兽药饲料交易大厦十三楼E1308', '0.000000', '0.000000', '17076012262', '管雷鸣'), ('103', 'null', '0.000000', '0.000000', 'null', 'null'), ('108', '在地图选择', '0.000000', '0.000000', '15526541999', '555'), ('109', '在地图选择', '0.000000', '0.000000', '15526541999', '555'), ('110', '山东潍坊市寒亭区开元街道通亭街亚星路路口向西20米路北，中国兽药饲料交易大厦十三楼E1308', '0.000000', '0.000000', '17076012262', '管雷鸣'), ('111', '山东潍坊市寒亭区开元街道通亭街亚星路路口向西20米路北，中国兽药饲料交易大厦十三楼E1308', '0.000000', '0.000000', '17076012262', '管雷鸣'), ('112', '在地图选择', '0.000000', '0.000000', '15526541999', '555'), ('113', '在地图选择', '0.000000', '0.000000', '15526541999', '555'), ('114', '在地图选择', '0.000000', '0.000000', '15526541999', '555'), ('115', '在地图选择', '0.000000', '0.000000', '15526541999', '555'), ('116', '在地图选择', '0.000000', '0.000000', '15526541999', '555'), ('117', '山东潍坊市寒亭区开元街道通亭街亚星路路口向西20米路北，中国兽药饲料交易大厦十三楼E1308', '0.000000', '0.000000', '17076012262', '管雷鸣'), ('118', 'null', '0.000000', '0.000000', 'null', 'null'), ('119', 'null', '0.000000', '0.000000', 'null', 'null'), ('120', 'null', '0.000000', '0.000000', 'null', 'null'), ('121', '山东潍坊市寒亭区开元街道通亭街亚星路路口向西20米路北，中国兽药饲料交易大厦十三楼E1308', '0.000000', '0.000000', '17076012262', '管雷鸣'), ('122', '山东潍坊市寒亭区开元街道通亭街亚星路路口向西20米路北，中国兽药饲料交易大厦十三楼E1308', '0.000000', '0.000000', '17076012262', '管雷鸣'), ('123', 'null', '0.000000', '0.000000', 'null', 'null'), ('124', 'null', '0.000000', '0.000000', 'null', 'null'), ('126', '', '0.000000', '0.000000', '', ''), ('127', '', '0.000000', '0.000000', '', ''), ('128', '', '0.000000', '0.000000', '', ''), ('129', '', '0.000000', '0.000000', '', ''), ('130', '', '0.000000', '0.000000', '', ''), ('131', '', '0.000000', '0.000000', '', ''), ('132', '', '0.000000', '0.000000', '', ''), ('133', '', '0.000000', '0.000000', '', ''), ('134', '1', '0.000000', '0.000000', '1', '1'), ('135', 'null', '0.000000', '0.000000', 'null', 'null'), ('136', '', '0.000000', '0.000000', '', ''), ('137', '', '0.000000', '0.000000', '', ''), ('138', '', '0.000000', '0.000000', '', ''), ('139', '', '0.000000', '0.000000', '', ''), ('140', '', '0.000000', '0.000000', '', ''), ('141', '', '0.000000', '0.000000', '', ''), ('142', '', '0.000000', '0.000000', '', ''), ('143', '', '0.000000', '0.000000', '', ''), ('144', '', '0.000000', '0.000000', '', ''), ('145', '', '0.000000', '0.000000', '', ''), ('146', '', '0.000000', '0.000000', '', '');
COMMIT;

-- ----------------------------
--  Table structure for `shop_order_goods`
-- ----------------------------
DROP TABLE IF EXISTS `shop_order_goods`;
CREATE TABLE `shop_order_goods` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `price` int(11) DEFAULT NULL COMMENT '商品单价，单位是分，对应 Goods.price',
  `title` char(40) DEFAULT NULL COMMENT '商品名字，对应 Goods.title ，就是吧Goods的信息复制过来了，相当于给商品做了一个镜像',
  `titlepic` char(100) DEFAULT NULL COMMENT '商品的标题图片，列表图，对应 Goods.titlepic',
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
) ENGINE=InnoDB AUTO_INCREMENT=212 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

-- ----------------------------
--  Records of `shop_order_goods`
-- ----------------------------
BEGIN;
INSERT INTO `shop_order_goods` VALUES ('1', '600', '猪肉', '//cdn.shop.leimingyun.com/goodsList/3b4592a232204fb58a1318e604683ec8.jpg', '斤', '6', '9', '1', '1', null, null, null, null), ('2', '600', '猪肉', '//cdn.shop.leimingyun.com/goodsList/3b4592a232204fb58a1318e604683ec8.jpg', '斤', '6', '1', '2', '1', null, null, null, null), ('3', '600', '啤酒', '//cdn.shop.leimingyun.com/goodsList/bb701d154678459aa0fb8a4a46b035c4.jpg', '个', '10', '3', '2', '1', null, null, null, null), ('4', '300', '土豆', '//wangmarket1581924839.oss-cn-beijing.aliyuncs.com/goodsList/98dbfec08ef34edfb045c05c1671e437.png', '斤', '7', '1', '3', '1', null, null, null, null), ('5', '600', '啤酒', '//cdn.shop.leimingyun.com/goodsList/bb701d154678459aa0fb8a4a46b035c4.jpg', '个', '10', '1', '4', '1', null, null, null, null), ('6', '600', '啤酒', '//cdn.shop.leimingyun.com/goodsList/bb701d154678459aa0fb8a4a46b035c4.jpg', '个', '10', '1', '5', '1', null, null, null, null), ('7', '300', '土豆', '//wangmarket1581924839.oss-cn-beijing.aliyuncs.com/goodsList/98dbfec08ef34edfb045c05c1671e437.png', '斤', '7', '2', '6', '1', null, null, null, null), ('8', '600', '啤酒', '//cdn.shop.leimingyun.com/goodsList/bb701d154678459aa0fb8a4a46b035c4.jpg', '个', '10', '2', '6', '1', null, null, null, null), ('9', '300', '土豆', '//wangmarket1581924839.oss-cn-beijing.aliyuncs.com/goodsList/98dbfec08ef34edfb045c05c1671e437.png', '斤', '7', '1', '7', '1', null, null, null, null), ('10', '600', '啤酒', '//cdn.shop.leimingyun.com/goodsList/bb701d154678459aa0fb8a4a46b035c4.jpg', '个', '10', '2', '7', '1', null, null, null, null), ('11', '600', '啤酒', '//cdn.shop.leimingyun.com/goodsList/bb701d154678459aa0fb8a4a46b035c4.jpg', '个', '10', '1', '8', '1', null, null, null, null), ('12', '600', '啤酒', '//cdn.shop.leimingyun.com/goodsList/bb701d154678459aa0fb8a4a46b035c4.jpg', '个', '10', '2', '9', '1', null, null, null, null), ('13', '600', '啤酒', '//cdn.shop.leimingyun.com/goodsList/bb701d154678459aa0fb8a4a46b035c4.jpg', '个', '10', '1', '10', '1', null, null, null, null), ('14', '1', '111', null, null, '1', '17', '1', '1', null, null, null, null), ('15', '1', '111', null, null, '1', '1', '1', '1', null, null, null, null), ('16', '1', '111', null, null, '1', '1', '1', '1', null, null, null, null), ('17', '1', '111', null, null, '1', '1', '1', '1', null, null, null, null), ('18', '1', '111', null, null, '1', '1', '1', '1', null, null, null, null), ('19', '1', '111', null, null, '1', '1', '1', '1', null, null, null, null), ('20', '1', '111', null, null, '1', '1', '1', '1', null, null, null, null), ('23', '1', '111', null, null, '1', '1', '1', '1', null, null, null, null), ('24', '1', '111', null, null, '1', '1', '1', '1', null, null, null, null), ('27', '1', '111', null, null, '1', '1', '1', '1', null, null, null, null), ('28', '1', '111', null, null, '1', '1', '1', '1', null, null, null, null), ('29', '1', '111', null, null, '1', '1', '1', '1', null, null, null, null), ('30', '1', '111', null, null, '1', '1', '1', '1', null, null, null, null), ('31', '1', '111', null, null, '1', '1', '1', '1', null, null, null, null), ('32', '1', '111', null, null, '1', '1', '1', '1', null, null, null, null), ('33', '1', '111', null, null, '1', '1', '1', '1', null, null, null, null), ('34', '1', '111', null, null, '1', '1', '1', '1', null, null, null, null), ('35', '1', '111', null, null, '1', '1', '1', '1', null, null, null, null), ('36', '1', '111', null, null, '1', '1', '1', '1', null, null, null, null), ('37', '1', '111', null, null, '1', '1', '1', '1', null, null, null, null), ('38', '1', '111', null, null, '1', '1', '1', '1', null, null, null, null), ('39', '1', 'fx5', null, null, '1', '1', '1', '1', null, null, null, null), ('40', '1', 'd9s', null, null, '1', '1', '1', '1', null, null, null, null), ('41', '1', 'pvv', null, null, '1', '1', '1', '1', null, null, null, null), ('42', '1', 'f02', null, null, '1', '1', '1', '1', null, null, null, null), ('43', '1', 'wah', null, null, '1', '1', '1', '1', null, null, null, null), ('44', '1', 'vy8', null, null, '1', '1', '1', '1', null, null, null, null), ('45', '1', 'rpe', null, null, '1', '1', '1', '1', null, null, null, null), ('46', '1', '8aj', null, null, '1', '1', '1', '1', null, null, null, null), ('48', '1', 'p0a', null, null, '1', '1', '1', '1', null, null, null, null), ('49', '1', '1gr', null, null, '1', '1', '1', '1', null, null, null, null), ('50', '1', 'lak', null, null, '1', '1', '1', '1', null, null, null, null), ('51', '1', 'nli', null, null, '1', '1', '1', '1', null, null, null, null), ('52', '1', 'hwl', null, null, '1', '1', '1', '1', null, null, null, null), ('53', '1', '5da', null, null, '1', '1', '1', '1', null, null, null, null), ('56', '1', 'd91', null, null, '1', '1', '1', '1', null, null, null, null), ('57', '1', 'bie', null, null, '1', '1', '1', '1', null, null, null, null), ('60', '300', '土豆', '//wangmarket1581924839.oss-cn-beijing.aliyuncs.com/goodsList/98dbfec08ef34edfb045c05c1671e437.png', '斤', '7', '4', '11', '1', null, null, null, null), ('61', '1', '猪肉', '//cdn.shop.leimingyun.com/goodsList/3b4592a232204fb58a1318e604683ec8.jpg', '斤', '6', '2', '12', '1', null, null, null, null), ('62', '1', '啤酒', '//cdn.shop.leimingyun.com/goodsList/bb701d154678459aa0fb8a4a46b035c4.jpg', '个', '10', '1', '13', '1', null, null, null, null), ('63', '1', '猪肉', '//cdn.shop.leimingyun.com/goodsList/3b4592a232204fb58a1318e604683ec8.jpg', '斤', '6', '1', '14', '398', null, null, null, null), ('64', '2', '土豆', '//wangmarket1581924839.oss-cn-beijing.aliyuncs.com/goodsList/98dbfec08ef34edfb045c05c1671e437.png', '斤', '7', '1', '15', '398', null, null, null, null), ('65', '1', '猪肉', '//cdn.shop.leimingyun.com/goodsList/3b4592a232204fb58a1318e604683ec8.jpg', '斤', '6', '1', '16', '398', null, null, null, null), ('66', '1', '猪肉', '//cdn.shop.leimingyun.com/goodsList/3b4592a232204fb58a1318e604683ec8.jpg', '斤', '6', '16', '17', '1', null, null, null, null), ('67', '1', '圣菲火（SHENGFEIHUO）手电筒小型', '//cdn.shop.leimingyun.com/goodsList/c6a2505728cc44f8a6cc146fea4929bc.jpg', '个', '11', '2', '17', '1', null, null, null, null), ('68', '2', '土豆', '//wangmarket1581924839.oss-cn-beijing.aliyuncs.com/goodsList/98dbfec08ef34edfb045c05c1671e437.png', '斤', '7', '1', '18', '1', null, null, null, null), ('69', '1', '啤酒', '//cdn.shop.leimingyun.com/goodsList/bb701d154678459aa0fb8a4a46b035c4.jpg', '个', '10', '1', '18', '1', null, null, null, null), ('70', '2', '土豆', '//wangmarket1581924839.oss-cn-beijing.aliyuncs.com/goodsList/98dbfec08ef34edfb045c05c1671e437.png', '斤', '7', '1', '19', '1', null, null, null, null), ('71', '1', '猪肉', '//cdn.shop.leimingyun.com/goodsList/3b4592a232204fb58a1318e604683ec8.jpg', '斤', '6', '1', '20', '1', null, null, null, null), ('72', '2', '土豆', '//wangmarket1581924839.oss-cn-beijing.aliyuncs.com/goodsList/98dbfec08ef34edfb045c05c1671e437.png', '斤', '7', '1', '21', '1', null, null, null, null), ('73', '1', '猪肉', '//cdn.shop.leimingyun.com/goodsList/3b4592a232204fb58a1318e604683ec8.jpg', '斤', '6', '3', '22', '398', null, null, null, null), ('74', '2', '土豆', '//wangmarket1581924839.oss-cn-beijing.aliyuncs.com/goodsList/98dbfec08ef34edfb045c05c1671e437.png', '斤', '7', '3', '22', '398', null, null, null, null), ('75', '1', '猪肉', '//cdn.shop.leimingyun.com/goodsList/3b4592a232204fb58a1318e604683ec8.jpg', '斤', '6', '1', '23', '1', null, null, null, null), ('76', '1', '啤酒', '//cdn.shop.leimingyun.com/goodsList/bb701d154678459aa0fb8a4a46b035c4.jpg', '个', '10', '1', '24', '1', null, null, null, null), ('77', '1', '猪肉', '//cdn.shop.leimingyun.com/goodsList/3b4592a232204fb58a1318e604683ec8.jpg', '斤', '6', '1', '25', '1', null, null, null, null), ('78', '2', '土豆', '//wangmarket1581924839.oss-cn-beijing.aliyuncs.com/goodsList/98dbfec08ef34edfb045c05c1671e437.png', '斤', '7', '1', '26', '1', null, null, null, null), ('79', '1', '啤酒', '//cdn.shop.leimingyun.com/goodsList/bb701d154678459aa0fb8a4a46b035c4.jpg', '个', '10', '1', '27', '1', null, null, null, null), ('80', '1', '猪肉', '//cdn.shop.leimingyun.com/goodsList/3b4592a232204fb58a1318e604683ec8.jpg', '斤', '6', '1', '28', '1', null, null, null, null), ('81', '1', '猪肉', '//cdn.shop.leimingyun.com/goodsList/3b4592a232204fb58a1318e604683ec8.jpg', '斤', '6', '1', '29', '1', null, null, null, null), ('82', '2', '土豆', '//wangmarket1581924839.oss-cn-beijing.aliyuncs.com/goodsList/98dbfec08ef34edfb045c05c1671e437.png', '斤', '7', '1', '30', '1', null, null, null, null), ('83', '1', '猪肉', '//cdn.shop.leimingyun.com/goodsList/3b4592a232204fb58a1318e604683ec8.jpg', '斤', '6', '1', '31', '1', null, null, null, null), ('84', '1', '啤酒', '//cdn.shop.leimingyun.com/goodsList/bb701d154678459aa0fb8a4a46b035c4.jpg', '个', '10', '1', '32', '1', null, null, null, null), ('85', '1', '啤酒', '//cdn.shop.leimingyun.com/goodsList/bb701d154678459aa0fb8a4a46b035c4.jpg', '个', '10', '1', '33', '1', null, null, null, null), ('86', '1', '圣菲火（SHENGFEIHUO）手电筒小型', '//cdn.shop.leimingyun.com/goodsList/c6a2505728cc44f8a6cc146fea4929bc.jpg', '个', '11', '1', '34', '1', null, null, null, null), ('87', '1', '猪肉', '//cdn.shop.leimingyun.com/goodsList/3b4592a232204fb58a1318e604683ec8.jpg', '斤', '6', '1', '35', '1', null, null, null, null), ('88', '1', '圣菲火（SHENGFEIHUO）手电筒小型', '//cdn.shop.leimingyun.com/goodsList/c6a2505728cc44f8a6cc146fea4929bc.jpg', '个', '11', '1', '36', '1', null, null, null, null), ('89', '1', '圣菲火（SHENGFEIHUO）手电筒小型', '//cdn.shop.leimingyun.com/goodsList/c6a2505728cc44f8a6cc146fea4929bc.jpg', '个', '11', '1', '37', '1', null, null, null, null), ('90', '1', '猪肉', '//cdn.shop.leimingyun.com/goodsList/3b4592a232204fb58a1318e604683ec8.jpg', '斤', '6', '1', '38', '1', null, null, null, null), ('91', '1', '啤酒', '//cdn.shop.leimingyun.com/goodsList/bb701d154678459aa0fb8a4a46b035c4.jpg', '个', '10', '1', '39', '1', null, null, null, null), ('92', '1', '圣菲火（SHENGFEIHUO）手电筒小型', '//cdn.shop.leimingyun.com/goodsList/c6a2505728cc44f8a6cc146fea4929bc.jpg', '个', '11', '1', '40', '1', null, null, null, null), ('93', '1', '圣菲火（SHENGFEIHUO）手电筒小型', '//cdn.shop.leimingyun.com/goodsList/c6a2505728cc44f8a6cc146fea4929bc.jpg', '个', '11', '1', '41', '1', null, null, null, null), ('94', '1', '圣菲火（SHENGFEIHUO）手电筒小型', '//cdn.shop.leimingyun.com/goodsList/c6a2505728cc44f8a6cc146fea4929bc.jpg', '个', '11', '1', '42', '1', null, null, null, null), ('95', '51', '猪肉', '//cdn.shop.leimingyun.com/goodsList/3b4592a232204fb58a1318e604683ec8.jpg', '斤', '6', '1', '43', '1', null, null, null, null), ('96', '1', '啤酒', '//cdn.shop.leimingyun.com/goodsList/bb701d154678459aa0fb8a4a46b035c4.jpg', '个', '10', '1', '44', '1', null, null, null, null), ('97', '51', '猪肉', '//cdn.shop.leimingyun.com/goodsList/3b4592a232204fb58a1318e604683ec8.jpg', '斤', '6', '3', '45', '1', null, null, null, null), ('98', '1', '圣菲火（SHENGFEIHUO）手电筒小型', '//cdn.shop.leimingyun.com/goodsList/c6a2505728cc44f8a6cc146fea4929bc.jpg', '个', '11', '1', '46', '1', null, null, null, null), ('99', '1', '圣菲火（SHENGFEIHUO）手电筒小型', '//cdn.shop.leimingyun.com/goodsList/c6a2505728cc44f8a6cc146fea4929bc.jpg', '个', '11', '1', '47', '1', null, null, null, null), ('100', '1', '圣菲火（SHENGFEIHUO）手电筒小型', '//cdn.shop.leimingyun.com/goodsList/c6a2505728cc44f8a6cc146fea4929bc.jpg', '个', '11', '1', '48', '1', null, null, null, null), ('105', '51', '猪肉', '//cdn.shop.leimingyun.com/goodsList/3b4592a232204fb58a1318e604683ec8.jpg', '斤', '6', '1', '53', '1', null, null, null, null), ('106', '1', '啤酒', '//cdn.shop.leimingyun.com/goodsList/bb701d154678459aa0fb8a4a46b035c4.jpg', '个', '10', '5', '54', '1', null, null, null, null), ('107', '1', '圣菲火（SHENGFEIHUO）手电筒小型', '//cdn.shop.leimingyun.com/goodsList/c6a2505728cc44f8a6cc146fea4929bc.jpg', '个', '11', '1', '55', '1', null, null, null, null), ('108', '1', '啤酒', '//cdn.shop.leimingyun.com/goodsList/bb701d154678459aa0fb8a4a46b035c4.jpg', '个', '10', '1', '56', '411', null, null, null, null), ('109', '1', '圣菲火（SHENGFEIHUO）手电筒小型', '//cdn.shop.leimingyun.com/goodsList/c6a2505728cc44f8a6cc146fea4929bc.jpg', '个', '11', '2', '57', '1', null, null, null, null), ('110', '1', '圣菲火（SHENGFEIHUO）手电筒小型', '//cdn.shop.leimingyun.com/goodsList/c6a2505728cc44f8a6cc146fea4929bc.jpg', '个', '11', '1', '58', '1', null, null, null, null), ('111', '51', '猪肉', '//cdn.shop.leimingyun.com/goodsList/3b4592a232204fb58a1318e604683ec8.jpg', '斤', '6', '9', '59', '411', null, null, null, null), ('112', '2', '土豆', '//wangmarket1581924839.oss-cn-beijing.aliyuncs.com/goodsList/98dbfec08ef34edfb045c05c1671e437.png', '斤', '7', '9', '59', '411', null, null, null, null), ('113', '51', '猪肉', '//cdn.shop.leimingyun.com/goodsList/3b4592a232204fb58a1318e604683ec8.jpg', '斤', '6', '7', '60', '411', null, null, null, null), ('114', '51', '猪肉', '//cdn.shop.leimingyun.com/goodsList/3b4592a232204fb58a1318e604683ec8.jpg', '斤', '6', '2', '61', '413', null, null, null, null), ('115', '51', '猪肉', '//cdn.shop.leimingyun.com/goodsList/3b4592a232204fb58a1318e604683ec8.jpg', '斤', '6', '1', '62', '1', null, null, null, null), ('116', '1', '圣菲火（SHENGFEIHUO）手电筒小型', '//cdn.shop.leimingyun.com/goodsList/c6a2505728cc44f8a6cc146fea4929bc.jpg', '个', '11', '3', '63', '411', null, null, null, null), ('117', '2', '土豆', '//wangmarket1581924839.oss-cn-beijing.aliyuncs.com/goodsList/98dbfec08ef34edfb045c05c1671e437.png', '斤', '7', '12', '64', '411', null, null, null, null), ('118', '2', '零食混合坚果恰恰果仁干果小黄袋', '//cdn.shop.leimingyun.com/store/1/images/ed1c4f9e107547f7b775181d281b7834.jpg', '斤', '7', '1', '65', '1', null, null, null, null), ('119', '2', '零食混合坚果恰恰果仁干果小黄袋', '//cdn.shop.leimingyun.com/store/1/images/ed1c4f9e107547f7b775181d281b7834.jpg', '斤', '7', '2', '66', '1', null, null, null, null), ('120', '1', '圣菲火（SHENGFEIHUO）手电筒小型', '//cdn.shop.leimingyun.com/store/1/images/026645215ea64274aebddbc05736679b.jpg', '个', '11', '2', '66', '1', null, null, null, null), ('121', '2', '零食混合坚果恰恰果仁干果小黄袋', '//cdn.shop.leimingyun.com/store/1/images/ed1c4f9e107547f7b775181d281b7834.jpg', '斤', '7', '1', '67', '411', null, null, null, null), ('122', '1', '沙发', '//cdn.shop.leimingyun.com/store/1/images/9a1511fa5d8548f1b02cc7db68f3af72.jpg', '个', '10', '27', '67', '411', null, null, null, null), ('123', '1', '圣菲火（SHENGFEIHUO）手电筒小型', '//cdn.shop.leimingyun.com/store/1/images/026645215ea64274aebddbc05736679b.jpg', '个', '11', '7', '67', '411', null, null, null, null), ('124', '1', '沙发', '//cdn.shop.leimingyun.com/store/1/images/9a1511fa5d8548f1b02cc7db68f3af72.jpg', '个', '10', '1', '68', '411', null, null, null, null), ('125', '51', '松崎 卡尺高精度电子数显游标卡尺', '//cdn.shop.leimingyun.com/store/1/images/19d21648e1ce47179d726c2516503977.jpg', '斤', '6', '1', '69', '413', null, null, null, null), ('126', '2', '零食混合坚果恰恰果仁干果小黄袋', '//cdn.shop.leimingyun.com/store/1/images/ed1c4f9e107547f7b775181d281b7834.jpg', '斤', '7', '2', '69', '413', null, null, null, null), ('127', '1', '沙发', '//cdn.shop.leimingyun.com/store/1/images/9a1511fa5d8548f1b02cc7db68f3af72.jpg', '个', '10', '1', '69', '413', null, null, null, null), ('128', '1', '圣菲火（SHENGFEIHUO）手电筒小型', '//cdn.shop.leimingyun.com/store/1/images/026645215ea64274aebddbc05736679b.jpg', '个', '11', '1', '70', '413', null, null, null, null), ('129', '1', '沙发', '//cdn.shop.leimingyun.com/store/1/images/9a1511fa5d8548f1b02cc7db68f3af72.jpg', '个', '10', '1', '71', '413', null, null, null, null), ('130', '1', '圣菲火（SHENGFEIHUO）手电筒小型', '//cdn.shop.leimingyun.com/store/1/images/026645215ea64274aebddbc05736679b.jpg', '个', '11', '1', '71', '413', null, null, null, null), ('131', '1', '圣菲火（SHENGFEIHUO）手电筒小型', '//cdn.shop.leimingyun.com/store/1/images/026645215ea64274aebddbc05736679b.jpg', '个', '11', '1', '72', '413', null, null, null, null), ('132', '1', '圣菲火（SHENGFEIHUO）手电筒小型', '//cdn.shop.leimingyun.com/store/1/images/026645215ea64274aebddbc05736679b.jpg', '个', '11', '1', '73', '413', null, null, null, null), ('133', '1', '圣菲火（SHENGFEIHUO）手电筒小型', '//cdn.shop.leimingyun.com/store/1/images/026645215ea64274aebddbc05736679b.jpg', '个', '11', '1', '74', '413', null, null, null, null), ('134', '1', '圣菲火（SHENGFEIHUO）手电筒小型', '//cdn.shop.leimingyun.com/store/1/images/026645215ea64274aebddbc05736679b.jpg', '个', '11', '1', '75', '413', null, null, null, null), ('135', '1', '圣菲火（SHENGFEIHUO）手电筒小型', '//cdn.shop.leimingyun.com/store/1/images/026645215ea64274aebddbc05736679b.jpg', '个', '11', '1', '76', '413', null, null, null, null), ('136', '1', '圣菲火（SHENGFEIHUO）手电筒小型', '//cdn.shop.leimingyun.com/store/1/images/026645215ea64274aebddbc05736679b.jpg', '个', '11', '1', '77', '413', null, null, null, null), ('137', '1', '圣菲火（SHENGFEIHUO）手电筒小型', '//cdn.shop.leimingyun.com/store/1/images/026645215ea64274aebddbc05736679b.jpg', '个', '11', '1', '78', '413', null, null, null, null), ('138', '1', '沙发', '//cdn.shop.leimingyun.com/store/1/images/9a1511fa5d8548f1b02cc7db68f3af72.jpg', '个', '10', '1', '79', '413', null, null, null, null), ('139', '1', '沙发', '//cdn.shop.leimingyun.com/store/1/images/9a1511fa5d8548f1b02cc7db68f3af72.jpg', '个', '10', '1', '80', '413', null, null, null, null), ('140', '1', '沙发', '//cdn.shop.leimingyun.com/store/1/images/9a1511fa5d8548f1b02cc7db68f3af72.jpg', '个', '10', '1', '81', '413', null, null, null, null), ('141', '1', '沙发', '//cdn.shop.leimingyun.com/store/1/images/9a1511fa5d8548f1b02cc7db68f3af72.jpg', '个', '10', '3', '82', '413', null, null, null, null), ('142', '1', '沙发', '//cdn.shop.leimingyun.com/store/1/images/9a1511fa5d8548f1b02cc7db68f3af72.jpg', '个', '10', '1', '83', '413', null, null, null, null), ('143', '1', '沙发', '//cdn.shop.leimingyun.com/store/1/images/9a1511fa5d8548f1b02cc7db68f3af72.jpg', '个', '10', '9', '84', '411', null, null, null, null), ('144', '2', '零食混合坚果恰恰果仁干果小黄袋', '//cdn.shop.leimingyun.com/store/1/images/ed1c4f9e107547f7b775181d281b7834.jpg', '斤', '7', '4', '85', '411', null, null, null, null), ('145', '51', '松崎 卡尺高精度电子数显游标卡尺', '//cdn.shop.leimingyun.com/store/1/images/19d21648e1ce47179d726c2516503977.jpg', '斤', '6', '1', '86', '418', null, null, null, null), ('146', '2', '零食混合坚果恰恰果仁干果小黄袋', '//cdn.shop.leimingyun.com/store/1/images/ed1c4f9e107547f7b775181d281b7834.jpg', '斤', '7', '3', '87', '1', null, null, null, null), ('147', '2', '零食混合坚果恰恰果仁干果小黄袋', '//cdn.shop.leimingyun.com/store/1/images/ed1c4f9e107547f7b775181d281b7834.jpg', '斤', '7', '1', '88', '1', null, null, null, null), ('148', '2', '零食混合坚果恰恰果仁干果小黄袋', '//cdn.shop.leimingyun.com/store/1/images/ed1c4f9e107547f7b775181d281b7834.jpg', '斤', '7', '1', '89', '1', null, null, null, null), ('149', '2', '零食混合坚果恰恰果仁干果小黄袋', '//cdn.shop.leimingyun.com/store/1/images/ed1c4f9e107547f7b775181d281b7834.jpg', '斤', '7', '2', '90', '1', null, null, null, null), ('150', '1', '沙发', '//cdn.shop.leimingyun.com/store/1/images/9a1511fa5d8548f1b02cc7db68f3af72.jpg', '个', '10', '2', '90', '1', null, null, null, null), ('151', '51', '松崎 卡尺高精度电子数显游标卡尺', '//cdn.shop.leimingyun.com/store/1/images/19d21648e1ce47179d726c2516503977.jpg', '斤', '6', '1', '91', '413', null, null, null, null), ('152', '1', '圣菲火（SHENGFEIHUO）手电筒小型', '//cdn.shop.leimingyun.com/store/1/images/026645215ea64274aebddbc05736679b.jpg', '个', '11', '1', '91', '413', null, null, null, null), ('153', '1', '沙发', '//cdn.shop.leimingyun.com/store/1/images/9a1511fa5d8548f1b02cc7db68f3af72.jpg', '个', '10', '10', '92', '413', null, null, null, null), ('156', '51', '松崎 卡尺高精度电子数显游标卡尺', '//cdn.shop.leimingyun.com/store/1/images/19d21648e1ce47179d726c2516503977.jpg', '斤', '6', '1', '95', '413', null, null, null, null), ('157', '1', '沙发', '//cdn.shop.leimingyun.com/store/1/images/9a1511fa5d8548f1b02cc7db68f3af72.jpg', '个', '10', '1', '96', '413', null, null, null, null), ('158', '1', '沙发', '//cdn.shop.leimingyun.com/store/1/images/9a1511fa5d8548f1b02cc7db68f3af72.jpg', '个', '10', '11', '97', '413', null, null, null, null), ('162', '1', '沙发', '//cdn.shop.leimingyun.com/store/1/images/9a1511fa5d8548f1b02cc7db68f3af72.jpg', '个', '10', '1', '101', '1', null, null, null, null), ('163', '1', '沙发', '//cdn.shop.leimingyun.com/store/1/images/9a1511fa5d8548f1b02cc7db68f3af72.jpg', '个', '10', '1', '102', '1', null, null, null, null), ('164', '1', '沙发', '//cdn.shop.leimingyun.com/store/1/images/9a1511fa5d8548f1b02cc7db68f3af72.jpg', '个', '10', '1', '103', '419', null, null, null, null), ('169', '1', '沙发', '//cdn.shop.leimingyun.com/store/1/images/9a1511fa5d8548f1b02cc7db68f3af72.jpg', '个', '10', '1', '108', '413', null, null, null, null), ('170', '1', '沙发', '//cdn.shop.leimingyun.com/store/1/images/9a1511fa5d8548f1b02cc7db68f3af72.jpg', '个', '10', '1', '109', '413', null, null, null, null), ('171', '1', '圣菲火（SHENGFEIHUO）手电筒小型', '//cdn.shop.leimingyun.com/store/1/images/026645215ea64274aebddbc05736679b.jpg', '个', '11', '1', '109', '413', null, null, null, null), ('172', '2', '零食混合坚果恰恰果仁干果小黄袋', '//cdn.shop.leimingyun.com/store/1/images/ed1c4f9e107547f7b775181d281b7834.jpg', '斤', '7', '1', '110', '1', null, null, null, null), ('173', '1', '沙发', '//cdn.shop.leimingyun.com/store/1/images/9a1511fa5d8548f1b02cc7db68f3af72.jpg', '个', '10', '3', '110', '1', null, null, null, null), ('174', '1', '圣菲火（SHENGFEIHUO）手电筒小型', '//cdn.shop.leimingyun.com/store/1/images/026645215ea64274aebddbc05736679b.jpg', '个', '11', '2', '110', '1', null, null, null, null), ('175', '2', '零食混合坚果恰恰果仁干果小黄袋', '//cdn.shop.leimingyun.com/store/1/images/ed1c4f9e107547f7b775181d281b7834.jpg', '斤', '7', '3', '111', '1', null, null, null, null), ('176', '51', '松崎 卡尺高精度电子数显游标卡尺', '//cdn.shop.leimingyun.com/store/1/images/19d21648e1ce47179d726c2516503977.jpg', '斤', '6', '1', '112', '413', null, null, null, null), ('177', '2', '零食混合坚果恰恰果仁干果小黄袋', '//cdn.shop.leimingyun.com/store/1/images/ed1c4f9e107547f7b775181d281b7834.jpg', '斤', '7', '2', '113', '413', null, null, null, null), ('178', '500', '白菜', '//cdn.shop.leimingyun.com/store/1/images/7974d2d7c1894f489bea0cf5cc2ba006.jpg', '斤', '15', '1', '113', '413', null, null, null, null), ('179', '1', '圣菲火（SHENGFEIHUO）手电筒小型', '//cdn.shop.leimingyun.com/store/1/images/026645215ea64274aebddbc05736679b.jpg', '个', '11', '1', '114', '413', null, null, null, null), ('180', '2', '零食混合坚果恰恰果仁干果小黄袋', '//cdn.shop.leimingyun.com/store/1/images/ed1c4f9e107547f7b775181d281b7834.jpg', '斤', '7', '1', '115', '413', null, null, null, null), ('181', '500', '芒果', '//cdn.shop.leimingyun.com/store/1/images/ec52bf9f09224925b7976641ab906ad6.jpg', '斤', '14', '1', '116', '413', null, null, null, null), ('182', '1', '圣菲火（SHENGFEIHUO）手电筒小型', '//cdn.shop.leimingyun.com/store/1/images/026645215ea64274aebddbc05736679b.jpg', '个', '11', '1', '117', '1', null, null, null, null), ('183', '1', '沙发', '//cdn.shop.leimingyun.com/store/1/images/9a1511fa5d8548f1b02cc7db68f3af72.jpg', '个', '10', '1', '118', '449', null, null, null, null), ('184', '1', '圣菲火（SHENGFEIHUO）手电筒小型', '//cdn.shop.leimingyun.com/store/1/images/026645215ea64274aebddbc05736679b.jpg', '个', '11', '1', '119', '449', null, null, null, null), ('185', '1', '沙发', '//cdn.shop.leimingyun.com/store/1/images/9a1511fa5d8548f1b02cc7db68f3af72.jpg', '个', '10', '1', '120', '449', null, null, null, null), ('186', '1', '沙发', '//cdn.shop.leimingyun.com/store/1/images/9a1511fa5d8548f1b02cc7db68f3af72.jpg', '个', '10', '2', '121', '1', null, null, null, null), ('187', '500', '白菜', '//cdn.shop.leimingyun.com/store/1/images/7974d2d7c1894f489bea0cf5cc2ba006.jpg', '斤', '15', '1', '122', '1', null, null, null, null), ('188', '1', '沙发', '//cdn.shop.leimingyun.com/store/1/images/9a1511fa5d8548f1b02cc7db68f3af72.jpg', '个', '10', '2', '123', '393', null, null, null, null), ('189', '1', '沙发', '//cdn.shop.leimingyun.com/store/1/images/9a1511fa5d8548f1b02cc7db68f3af72.jpg', '个', '10', '1', '124', '465', null, null, null, null), ('190', '500', '白菜', '//cdn.shop.leimingyun.com/store/1/images/7974d2d7c1894f489bea0cf5cc2ba006.jpg', '斤', '15', '1', '125', '393', null, null, null, null), ('191', '1', '沙发', '//cdn.shop.leimingyun.com/store/1/images/9a1511fa5d8548f1b02cc7db68f3af72.jpg', '个', '10', '1', '126', '472', null, null, null, null), ('192', '51', '松崎 卡尺高精度电子数显游标卡尺', '//cdn.shop.leimingyun.com/store/1/images/19d21648e1ce47179d726c2516503977.jpg', '斤', '6', '1', '127', '472', null, null, null, null), ('193', '1', '沙发', '//cdn.shop.leimingyun.com/store/1/images/9a1511fa5d8548f1b02cc7db68f3af72.jpg', '个', '10', '1', '128', '472', null, null, null, null), ('194', '1', '沙发', '//cdn.shop.leimingyun.com/store/1/images/9a1511fa5d8548f1b02cc7db68f3af72.jpg', '个', '10', '1', '129', '472', null, null, null, null), ('195', '1', '沙发', '//cdn.shop.leimingyun.com/store/1/images/9a1511fa5d8548f1b02cc7db68f3af72.jpg', '个', '10', '1', '130', '472', null, null, null, null), ('196', '1', '沙发', '//cdn.shop.leimingyun.com/store/1/images/9a1511fa5d8548f1b02cc7db68f3af72.jpg', '个', '10', '1', '131', '472', null, null, null, null), ('197', '1', '沙发', '//cdn.shop.leimingyun.com/store/1/images/9a1511fa5d8548f1b02cc7db68f3af72.jpg', '个', '10', '1', '132', '472', null, null, null, null), ('198', '2', '零食混合坚果恰恰果仁干果小黄袋', '//cdn.shop.leimingyun.com/store/1/images/ed1c4f9e107547f7b775181d281b7834.jpg', '斤', '7', '1', '133', '478', null, null, null, null), ('199', '1', '沙发', '//cdn.shop.leimingyun.com/store/1/images/9a1511fa5d8548f1b02cc7db68f3af72.jpg', '个', '10', '1', '134', '480', null, null, null, null), ('200', '1', '沙发', '//cdn.shop.leimingyun.com/store/1/images/9a1511fa5d8548f1b02cc7db68f3af72.jpg', '个', '10', '1', '135', '481', null, null, null, null), ('201', '1', '圣菲火（SHENGFEIHUO）手电筒小型', '//cdn.shop.leimingyun.com/store/1/images/026645215ea64274aebddbc05736679b.jpg', '个', '11', '1', '136', '478', null, null, null, null), ('202', '1', '圣菲火（SHENGFEIHUO）手电筒小型', '//cdn.shop.leimingyun.com/store/1/images/026645215ea64274aebddbc05736679b.jpg', '个', '11', '1', '137', '478', null, null, null, null), ('203', '2', '零食混合坚果恰恰果仁干果小黄袋', '//cdn.shop.leimingyun.com/store/1/images/ed1c4f9e107547f7b775181d281b7834.jpg', '斤', '7', '1', '138', '478', null, null, null, null), ('204', '2', '零食混合坚果恰恰果仁干果小黄袋', '//cdn.shop.leimingyun.com/store/1/images/ed1c4f9e107547f7b775181d281b7834.jpg', '斤', '7', '1', '139', '478', null, null, null, null), ('205', '1', '圣菲火（SHENGFEIHUO）手电筒小型', '//cdn.shop.leimingyun.com/store/1/images/026645215ea64274aebddbc05736679b.jpg', '个', '11', '1', '140', '478', null, null, null, null), ('206', '51', '松崎 卡尺高精度电子数显游标卡尺', '//cdn.shop.leimingyun.com/store/1/images/19d21648e1ce47179d726c2516503977.jpg', '斤', '6', '1', '141', '478', null, null, null, null), ('207', '1', '沙发', '//cdn.shop.leimingyun.com/store/1/images/9a1511fa5d8548f1b02cc7db68f3af72.jpg', '个', '10', '1', '142', '472', null, null, null, null), ('208', '1', '沙发', '//cdn.shop.leimingyun.com/store/1/images/9a1511fa5d8548f1b02cc7db68f3af72.jpg', '个', '10', '1', '143', '478', null, null, null, null), ('209', '200', '西红柿', '//cdn.shop.leimingyun.com/store/1/images/9c435071c98a449ba0a98f91708bf2f4.jpg', '斤', '16', '1', '144', '472', null, null, null, null), ('210', '2', '零食混合坚果恰恰果仁干果小黄袋', '//cdn.shop.leimingyun.com/store/1/images/ed1c4f9e107547f7b775181d281b7834.jpg', '斤', '7', '1', '145', '472', null, null, null, null), ('211', '5000', '美甲', '//cdn.shop.leimingyun.com/store/4356/images/8b9a8bf76202419daa50436ac725e375.png', '个', '24', '1', '146', '472', null, null, null, null);
COMMIT;

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
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

-- ----------------------------
--  Records of `shop_order_refund`
-- ----------------------------
BEGIN;
INSERT INTO `shop_order_refund` VALUES ('1', '1582201313', '2', 'aaaaa', '2', '1', '1'), ('2', '1583466322', '35', '', '2', '1', '1'), ('3', '1583483524', '37', '', '2', '1', '1'), ('4', '1583483540', '37', '', '2', '1', '1'), ('5', '1583485649', '39', '', '2', '1', '1'), ('6', '1584602091', '60', '', '2', '1', '411'), ('7', '1584754634', '66', '', '2', '1', '1'), ('8', '1584787590', '86', '', '2', '1', '418'), ('9', '1584932649', '103', '', '2', '1', '419'), ('10', '1585044884', '110', '', '2', '1', '1'), ('11', '1585045473', '110', '', '2', '1', '1'), ('12', '1585053179', '111', '', '2', '1', '1'), ('13', '1587889908', '121', '', '2', '1', '1'), ('14', '1588040851', '122', '', '2', '1', '1');
COMMIT;

-- ----------------------------
--  Table structure for `shop_order_rule`
-- ----------------------------
DROP TABLE IF EXISTS `shop_order_rule`;
CREATE TABLE `shop_order_rule` (
  `id` int(11) NOT NULL,
  `distribution` tinyint(11) DEFAULT '1' COMMENT '是否使用配送中这个状态，如果没有，订单可以有已支付直接变为已完成。1使用，0不使用。默认是1使用',
  `refund` tinyint(11) DEFAULT '1' COMMENT '是否使用退款这个状态，也就是是否允许用户退款。1使用，0不使用。默认是1使用',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
--  Records of `shop_order_rule`
-- ----------------------------
BEGIN;
INSERT INTO `shop_order_rule` VALUES ('1', '0', '1'), ('0', '1', '1'), ('1211', '1', '1'), ('219', '1', '1'), ('341', '1', '1'), ('4071', '1', '1'), ('4076', '1', '1'), ('4105', '1', '1'), ('4139', '1', '1'), ('4153', '1', '1'), ('241', '1', '1'), ('243', '1', '1'), ('3586', '1', '1'), ('4188', '1', '1'), ('4192', '1', '1'), ('4199', '1', '1'), ('4142', '1', '1'), ('4212', '1', '1'), ('3285', '1', '1'), ('4221', '1', '1'), ('1399', '1', '1'), ('4238', '1', '1'), ('3420', '1', '1'), ('4252', '1', '1'), ('3558', '1', '1'), ('4286', '1', '1'), ('4288', '1', '1'), ('4296', '1', '1'), ('4298', '1', '1'), ('4302', '1', '1'), ('4312', '1', '1'), ('4315', '1', '1'), ('4319', '1', '1'), ('4321', '1', '1'), ('4328', '1', '1'), ('4334', '1', '1'), ('4343', '1', '1'), ('4356', '0', '0');
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
--  Records of `shop_order_state_log`
-- ----------------------------
BEGIN;
INSERT INTO `shop_order_state_log` VALUES ('1', '1583482836', '36', 'generate_but_no_pay'), ('2', '1583483482', '35', ''), ('3', '1583483512', '37', 'generate_but_no_pay'), ('4', '1583483524', '37', 'refund_ing'), ('5', '1583483529', '37', 'generate_but_no_pay'), ('6', '1583483540', '37', 'refund_ing'), ('7', '1583483545', '37', 'generate_but_no_pay'), ('8', '1583484637', '36', 'pay_timeout_cancel'), ('9', '1583485115', '37', 'my_cancel'), ('10', '1583485180', '38', 'generate_but_no_pay'), ('11', '1583485636', '39', 'generate_but_no_pay'), ('12', '1583485638', '39', 'private_pay'), ('13', '1583485649', '39', 'refund_ing'), ('14', '1583485655', '39', 'private_pay'), ('15', '1583485742', '39', 'receive_goods'), ('16', '1583493126', '40', 'generate_but_no_pay'), ('17', '1583493143', '41', 'generate_but_no_pay'), ('18', '1583494934', '40', 'pay_timeout_cancel'), ('19', '1583494944', '41', 'pay_timeout_cancel'), ('20', '1583547404', '42', 'generate_but_no_pay'), ('21', '1583549213', '42', 'pay_timeout_cancel'), ('22', '1583558130', '43', 'generate_but_no_pay'), ('23', '1583558221', '44', 'generate_but_no_pay'), ('24', '1583558238', '44', 'my_cancel'), ('25', '1583559937', '43', 'pay_timeout_cancel'), ('26', '1583562948', '45', 'generate_but_no_pay'), ('27', '1583564609', '46', 'generate_but_no_pay'), ('28', '1583564759', '45', 'pay_timeout_cancel'), ('29', '1583566412', '46', 'pay_timeout_cancel'), ('30', '1583904359', '47', 'generate_but_no_pay'), ('31', '1583904746', '48', 'generate_but_no_pay'), ('32', '1583904765', '48', 'pay'), ('33', '1583906167', '47', 'pay_timeout_cancel'), ('34', '1584525600', '53', 'generate_but_no_pay'), ('35', '1584525608', '54', 'generate_but_no_pay'), ('36', '1584525623', '55', 'generate_but_no_pay'), ('37', '1584526118', '56', 'generate_but_no_pay'), ('38', '1584527406', '53', 'pay_timeout_cancel'), ('39', '1584527416', '54', 'pay_timeout_cancel'), ('40', '1584527426', '55', 'pay_timeout_cancel'), ('41', '1584527926', '56', 'pay_timeout_cancel'), ('42', '1584580021', '57', 'generate_but_no_pay'), ('43', '1584580173', '57', 'private_pay'), ('44', '1584580302', '58', 'generate_but_no_pay'), ('45', '1584582105', '58', 'pay_timeout_cancel'), ('46', '1584598124', '59', 'generate_but_no_pay'), ('47', '1584598132', '59', 'private_pay'), ('48', '1584600032', '60', 'generate_but_no_pay'), ('49', '1584600408', '60', 'private_pay'), ('50', '1584602091', '60', 'refund_ing'), ('51', '1584602094', '60', 'private_pay'), ('52', '1584603628', '61', 'generate_but_no_pay'), ('53', '1584603667', '62', 'generate_but_no_pay'), ('54', '1584603668', '62', 'private_pay'), ('55', '1584605438', '61', 'pay_timeout_cancel'), ('56', '1584693893', '63', 'generate_but_no_pay'), ('57', '1584695698', '63', 'pay_timeout_cancel'), ('58', '1584699543', '64', 'generate_but_no_pay'), ('59', '1584699548', '64', 'private_pay'), ('60', '1584705431', '64', 'receive_goods'), ('61', '1584714893', '65', 'generate_but_no_pay'), ('62', '1584716701', '65', 'pay_timeout_cancel'), ('63', '1584753938', '66', 'generate_but_no_pay'), ('64', '1584754346', '66', 'private_pay'), ('65', '1584754634', '66', 'refund_ing'), ('66', '1584754638', '66', 'private_pay'), ('67', '1584754988', '67', 'generate_but_no_pay'), ('68', '1584755018', '67', 'private_pay'), ('69', '1584756455', '68', 'generate_but_no_pay'), ('70', '1584756456', '68', 'private_pay'), ('71', '1584770107', '69', 'generate_but_no_pay'), ('72', '1584770171', '70', 'generate_but_no_pay'), ('73', '1584771908', '69', 'pay_timeout_cancel'), ('74', '1584771977', '70', 'pay_timeout_cancel'), ('75', '1584772262', '71', 'generate_but_no_pay'), ('76', '1584772359', '72', 'generate_but_no_pay'), ('77', '1584772536', '73', 'generate_but_no_pay'), ('78', '1584772745', '74', 'generate_but_no_pay'), ('79', '1584772822', '75', 'generate_but_no_pay'), ('80', '1584772858', '76', 'generate_but_no_pay'), ('81', '1584772889', '77', 'generate_but_no_pay'), ('82', '1584772935', '78', 'generate_but_no_pay'), ('83', '1584773278', '79', 'generate_but_no_pay'), ('84', '1584773652', '80', 'generate_but_no_pay'), ('85', '1584773684', '81', 'generate_but_no_pay'), ('86', '1584773939', '82', 'generate_but_no_pay'), ('87', '1584774066', '71', 'pay_timeout_cancel'), ('88', '1584774091', '83', 'generate_but_no_pay'), ('89', '1584774168', '72', 'pay_timeout_cancel'), ('90', '1584774338', '73', 'pay_timeout_cancel'), ('91', '1584774548', '74', 'pay_timeout_cancel'), ('92', '1584774628', '75', 'pay_timeout_cancel'), ('93', '1584774664', '76', 'pay_timeout_cancel'), ('94', '1584774696', '77', 'pay_timeout_cancel'), ('95', '1584774738', '78', 'pay_timeout_cancel'), ('96', '1584775088', '79', 'pay_timeout_cancel'), ('97', '1584775458', '80', 'pay_timeout_cancel'), ('98', '1584775488', '81', 'pay_timeout_cancel'), ('99', '1584775746', '82', 'pay_timeout_cancel'), ('100', '1584775898', '83', 'pay_timeout_cancel'), ('101', '1584782895', '84', 'generate_but_no_pay'), ('102', '1584783929', '85', 'generate_but_no_pay'), ('103', '1584784700', '84', 'pay_timeout_cancel'), ('104', '1584785730', '85', 'pay_timeout_cancel'), ('105', '1584787553', '86', 'generate_but_no_pay'), ('106', '1584787562', '86', 'private_pay'), ('107', '1584787590', '86', 'refund_ing'), ('108', '1584787821', '87', 'generate_but_no_pay'), ('109', '1584787856', '88', 'generate_but_no_pay'), ('110', '1584787995', '89', 'generate_but_no_pay'), ('111', '1584787996', '89', 'private_pay'), ('112', '1584788037', '90', 'generate_but_no_pay'), ('113', '1584788039', '90', 'private_pay'), ('114', '1584789631', '87', 'pay_timeout_cancel'), ('115', '1584789661', '88', 'pay_timeout_cancel'), ('116', '1584792489', '91', 'generate_but_no_pay'), ('117', '1584794291', '91', 'pay_timeout_cancel'), ('118', '1584927006', '92', 'generate_but_no_pay'), ('119', '1584928810', '92', 'pay_timeout_cancel'), ('120', '1584930656', '95', 'generate_but_no_pay'), ('121', '1584930702', '96', 'generate_but_no_pay'), ('122', '1584932239', '97', 'generate_but_no_pay'), ('123', '1584932462', '95', 'pay_timeout_cancel'), ('124', '1584932507', '96', 'pay_timeout_cancel'), ('125', '1584932515', '101', 'generate_but_no_pay'), ('126', '1584932520', '101', 'private_pay'), ('127', '1584932584', '102', 'generate_but_no_pay'), ('128', '1584932629', '103', 'generate_but_no_pay'), ('129', '1584932637', '103', 'private_pay'), ('130', '1584932649', '103', 'refund_ing'), ('131', '1584932653', '103', 'private_pay'), ('132', '1584932661', '103', 'receive_goods'), ('133', '1584934042', '97', 'pay_timeout_cancel'), ('134', '1584934393', '102', 'pay_timeout_cancel'), ('135', '1584943475', '108', 'generate_but_no_pay'), ('136', '1584943839', '109', 'generate_but_no_pay'), ('137', '1584945279', '108', 'pay_timeout_cancel'), ('138', '1584945649', '109', 'pay_timeout_cancel'), ('139', '1585044685', '110', 'generate_but_no_pay'), ('140', '1585044879', '110', 'private_pay'), ('141', '1585044884', '110', 'refund_ing'), ('142', '1585044887', '110', 'private_pay'), ('143', '1585045473', '110', 'refund_ing'), ('144', '1585045476', '110', 'private_pay'), ('145', '1585045489', '110', 'receive_goods'), ('146', '1585052934', '111', 'generate_but_no_pay'), ('147', '1585052989', '111', 'private_pay'), ('148', '1585053179', '111', 'refund_ing'), ('149', '1585128244', '112', 'generate_but_no_pay'), ('150', '1585130049', '112', 'pay_timeout_cancel'), ('151', '1585297219', '113', 'generate_but_no_pay'), ('152', '1585299024', '113', 'pay_timeout_cancel'), ('153', '1585299713', '114', 'generate_but_no_pay'), ('154', '1585299827', '115', 'generate_but_no_pay'), ('155', '1585300389', '116', 'generate_but_no_pay'), ('156', '1585301515', '114', 'pay_timeout_cancel'), ('157', '1585301634', '115', 'pay_timeout_cancel'), ('158', '1585302191', '116', 'pay_timeout_cancel'), ('159', '1586070261', '117', 'generate_but_no_pay'), ('160', '1586070266', '117', 'private_pay'), ('161', '1587019898', '118', 'generate_but_no_pay'), ('162', '1587019932', '118', 'private_pay'), ('163', '1587019978', '119', 'generate_but_no_pay'), ('164', '1587020318', '120', 'generate_but_no_pay'), ('165', '1587021784', '119', 'pay_timeout_cancel'), ('166', '1587022125', '120', 'pay_timeout_cancel'), ('167', '1587889877', '121', 'generate_but_no_pay'), ('168', '1587889895', '121', 'private_pay'), ('169', '1587889908', '121', 'refund_ing'), ('170', '1587889913', '121', 'private_pay'), ('171', '1587889920', '121', 'receive_goods'), ('172', '1588038778', '118', 'receive_goods'), ('173', '1588038911', '117', 'receive_goods'), ('174', '1588040481', '122', 'generate_but_no_pay'), ('175', '1588040485', '122', 'private_pay'), ('176', '1588040851', '122', 'refund_ing'), ('177', '1588040867', '122', 'private_pay'), ('178', '1588052502', '123', 'generate_but_no_pay'), ('179', '1588052506', '123', 'private_pay'), ('180', '1588052527', '123', 'receive_goods'), ('181', '1588052707', '124', 'generate_but_no_pay'), ('182', '1588052711', '124', 'private_pay'), ('183', '1588052917', '124', 'receive_goods'), ('184', '1588053117', '124', 'receive_goods'), ('185', '1588053190', '124', 'receive_goods'), ('186', '1588053237', '124', 'receive_goods'), ('187', '1588133331', '126', 'generate_but_no_pay'), ('188', '1588135132', '126', 'pay_timeout_cancel'), ('189', '1588141559', '127', 'generate_but_no_pay'), ('190', '1588141622', '128', 'generate_but_no_pay'), ('191', '1588141823', '129', 'generate_but_no_pay'), ('192', '1588141903', '130', 'generate_but_no_pay'), ('193', '1588141942', '131', 'generate_but_no_pay'), ('194', '1588141962', '132', 'generate_but_no_pay'), ('195', '1588143365', '127', 'pay_timeout_cancel'), ('196', '1588143425', '128', 'pay_timeout_cancel'), ('197', '1588143626', '129', 'pay_timeout_cancel'), ('198', '1588143706', '130', 'pay_timeout_cancel'), ('199', '1588143746', '131', 'pay_timeout_cancel'), ('200', '1588143766', '132', 'pay_timeout_cancel'), ('201', '1588144905', '133', 'generate_but_no_pay'), ('202', '1588146126', '134', 'generate_but_no_pay'), ('203', '1588146707', '133', 'pay_timeout_cancel'), ('204', '1588147927', '134', 'pay_timeout_cancel'), ('205', '1588208582', '135', 'generate_but_no_pay'), ('206', '1588208585', '135', 'private_pay'), ('207', '1588236863', '136', 'generate_but_no_pay'), ('208', '1588236976', '137', 'generate_but_no_pay'), ('209', '1588237103', '138', 'generate_but_no_pay'), ('210', '1588237129', '139', 'generate_but_no_pay'), ('211', '1588237550', '140', 'generate_but_no_pay'), ('212', '1588237586', '141', 'generate_but_no_pay'), ('213', '1588238667', '136', 'pay_timeout_cancel'), ('214', '1588238778', '137', 'pay_timeout_cancel'), ('215', '1588238908', '138', 'pay_timeout_cancel'), ('216', '1588238938', '139', 'pay_timeout_cancel'), ('217', '1588239358', '140', 'pay_timeout_cancel'), ('218', '1588239388', '141', 'pay_timeout_cancel'), ('219', '1588240301', '142', 'generate_but_no_pay'), ('220', '1588241282', '143', 'generate_but_no_pay'), ('221', '1588242109', '142', 'pay_timeout_cancel'), ('222', '1588243089', '143', 'pay_timeout_cancel'), ('223', '1588244940', '144', 'generate_but_no_pay'), ('224', '1588246511', '145', 'generate_but_no_pay'), ('225', '1588246750', '144', 'pay_timeout_cancel'), ('226', '1588248321', '145', 'pay_timeout_cancel'), ('227', '1588812560', '146', 'generate_but_no_pay'), ('228', '1588814369', '146', 'pay_timeout_cancel');
COMMIT;

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
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

-- ----------------------------
--  Records of `shop_pay_log`
-- ----------------------------
BEGIN;
INSERT INTO `shop_pay_log` VALUES ('1', '1582727739', 'alipay_pc', '1', '8', '1', '1'), ('2', '1582939970', 'alipay_pc', '2', '12', '1', '1'), ('3', '1583904765', 'alipay_pc', '1', '48', '1', '1');
COMMIT;

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
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

-- ----------------------------
--  Records of `shop_pay_set`
-- ----------------------------
BEGIN;
INSERT INTO `shop_pay_set` VALUES ('1', 'appCertPublicKey.crt', '20210011106000', 'MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCbvzTRZk/E5Zi3f4kXrjQm56Iy3oFNPNtcSs5VgcpDsSLYO6Nv77KWnqUS7LOHZVmP5j4FyQrdEwjR8BE5C+83YjsZ87WwNJ7CPNtm2OdNEjF6GpktXV/Z8ymNwWdXmdblOWIbXPhLlMIh8W7yZNSYdzQnZ2QYaDq9XmlOU9czh4oQqvbKo08Lg6dL4PED8QRquiOHKvpXfcc026Jvzw02KzjDe5L/nV0z2h83YYf/aWEjWhpYh8+1whDiJgfjxJkTHzaiMvwIwi/XhJEURVy57D4hpn/+r1GNU9QaJIR475WLNUxlNjNs6SgoRY9K3AYX2BXgnXlosAjDUYejh2XPAgMBAAECggEATgxRoaYNz3PnRf2Ja+6GgACQDcHnNheLLgLmx37VJ3r7JTUtwAh0jHpP6t3nUbB+Y1hUOH35DbReNSaC2v2aET0OtQCmSfj0ng31POLxdnPpi3EM1ej3AbeTWVBUQ1f6ksBg46mBXA7eAjiFCr9wsj76SczMIlcJKKDJUNE/BLjdT54cRh8lAyMuuCEmdgJ+WRxVRUKnWXvK4bkrV1fO8raNA1zxipuOTvw1AFVsjJHjAXiQ9BOpG4Ell+No7oVcB9Ird5z1uolLV2Y8h+sdXlPBizq3kV5gjwg65dvBr5fgrXHcVrU7qMIrg/JqWJqLp5uvZPFwIGqBNQTyY87TUQKBgQDMMca9dcRj7WOBZ85rTBdM4nxM3i39LuKb57WazdKd3S2Zxil6KV5feOZeBjXbvYg24NVywRR0j25hMiq9Pmc2UHdak4TsEXzDYJGIpUOPbA10AjUL5rXaq43DmphirPUO1ViSb7RBekgquZmzONK9FxSNgs7qPSBoPT9J/+6/RQKBgQDDQs+ddoShDGIEGPwxckALqGXivtCFpWy5a/QeW9Ky5OW9V/HKtFBp02CaSpQiGcN8RGRtX9JJ5fhiRqor50ayxgmBB704hNWJZNZZKAeLaRTPJpDQXnTTtUo0PrRPet5Kfgftap8YXHApHS8bm0Qqch9RTGL3O7EJ9hEvbk0IAwKBgQC2UBMUAdwRJFjdp2Nj5ZJAMU+uCgmhroThkmZRC0frbxlR3N4XlmwObhGxDXZ09apy5Rdu1/l9gLqayYHjaZ4ZnoqSMnhRleOBRe1qxbOmkGEKnRXjxF/GF7oBVTyNjcNXNSFZzaZxd86LZx9WJ3vxHHRbTNmVxnHy4Axr87ikyQKBgEcwlq1cRqLI9vtzeI82jm8xqGPxGemAFJHN8CUZaUpb9f8Aj2FOOHs8292lDzkXl5Q5Azx1USAsPlDmXqi/G6GWXmSyw6RVmd0L7LJ4y7diSnn0H5w4s/N0B2fg7LCFpYIXTSCcXQDuwt3ajBBOnZ20GgL0DKkDFFsDYeSfHGqtAoGBAJhFou++rQJeI5Di+Ogaof5kz2KbG/pdXKR28izzA9niKHIJclOldbPvXMYQ5kEleWex4f23OuPzjntssE45rekIiZSj/qkKQ9TNw2FL85xNsNIGl5e08qnDAR49DFokQAM5hmLqqs6e3qA8KwHt0JPpwzccG+AOFdi8ifjlVY', 'alipayCertPublicKey_RSA2.crt', 'alipayRootCert.crt', '1', '1', '1', 'wxcc19e7b9b36da2a3', '11111', '8888', 'ed9dc714874eb9524d6319fa09f31bcd', 'wxae29273f418487db', 'xnx3Test', '9aaf03a65a321df8e400d34a6538f360'), ('219', null, null, null, 'alipayCertPublicKeyRSA2.crt', null, '1', '1', '0', null, null, null, '', '', '', '');
COMMIT;

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
  `latitude` double(100,6) DEFAULT NULL COMMENT '店铺经纬度',
  `longitude` double(100,6) DEFAULT NULL COMMENT '店铺经纬度',
  `name` char(20) DEFAULT NULL COMMENT '店铺名字',
  `notice` char(150) DEFAULT NULL COMMENT '店铺公告',
  `phone` char(13) DEFAULT NULL COMMENT '店铺店家联系电话',
  `province` char(15) DEFAULT NULL COMMENT '店铺所在位置-省',
  `sale` int(11) DEFAULT '0' COMMENT '店铺已出售的商品总数量，总销量。这个一定是真实的，如果要造假数，可以在增加一个字段',
  `state` tinyint(2) DEFAULT NULL COMMENT '店铺状态，0审核中，1营业中，2已打烊',
  `userid` int(11) DEFAULT NULL COMMENT '店铺所属用户，哪个用户创建的，对应 User.id',
  `version` int(11) DEFAULT '0' COMMENT '乐观锁',
  PRIMARY KEY (`id`),
  KEY `suoyin_index` (`userid`,`state`,`longitude`,`latitude`,`province`,`city`,`district`,`sale`)
) ENGINE=InnoDB AUTO_INCREMENT=4361 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

-- ----------------------------
--  Records of `shop_store`
-- ----------------------------
BEGIN;
INSERT INTO `shop_store` VALUES ('1', '亚华大酒店', '1582872019', '潍坊市', '管雷鸣', '寒亭区', '//cdn.shop.leimingyun.com/slideshow/e57cf791afa44fe19f7a8d7e1f5bd6d7.png', '111.217350', '23.117628', '管大店铺1', 'bie', '17076012262', '山东省', '195', '1', '393', '39'), ('2', null, '1583737517', null, null, null, null, null, null, '商城的名字', null, null, null, null, '1', '401', '0'), ('3', null, '1583752774', null, null, null, null, null, null, '商城的名字', null, null, null, null, '1', '402', '0'), ('4', null, '1583753128', null, null, null, null, null, null, '商城的名字', null, null, null, null, '1', '403', '0'), ('219', null, '1583809025', null, null, null, null, null, null, '商铺名字', null, null, null, '0', '1', '405', '0'), ('241', null, '1585651386', null, null, null, null, null, null, '商铺名字', null, null, null, '0', '1', '427', '0'), ('242', null, '1585653905', null, null, null, null, null, null, '商铺名字', null, null, null, '0', '1', '428', '0'), ('243', null, '1585654910', null, null, null, null, null, null, '商铺名字', null, null, null, '0', '1', '429', '0'), ('341', null, '1583852645', null, null, null, null, null, null, '商铺名字', null, null, null, '0', '1', '406', '0'), ('1211', null, '1583753358', null, null, null, null, null, null, '商铺名字', null, null, null, '0', '1', '404', '0'), ('1399', null, '1586646514', null, null, null, null, null, null, '华宇网络在线商城', null, null, null, '0', '1', '443', '1'), ('3285', null, '1586270684', null, null, null, null, null, null, '商铺名字', null, null, null, '0', '1', '439', '0'), ('3420', null, '1586825320', null, null, null, null, null, null, '商铺名字', null, null, null, '0', '1', '446', '0'), ('3558', null, '1587215357', null, null, null, null, null, null, '商铺名字', null, null, null, '0', '1', '452', '0'), ('3586', null, '1585727456', null, null, null, null, null, null, '商铺名字', null, null, null, '0', '1', '431', '0'), ('4071', null, '1584067333', null, null, null, null, null, null, '商铺名字', null, null, null, '0', '1', '407', '0'), ('4072', null, '1584094867', null, null, null, null, null, null, '商铺名字', null, null, null, '0', '1', '408', '0'), ('4076', null, '1584246639', null, null, null, null, null, null, '商铺名字', null, null, null, '0', '1', '410', '0'), ('4080', null, '1584179355', null, null, null, null, null, null, '商铺名字', null, null, null, '0', '1', '409', '0'), ('4096', null, '1584597244', null, null, null, '//cdn.shop.leimingyun.com/store/4096/images/153431cd68f3482fb480ab1736de33f2.png', null, null, 'ZCL sperker systm', null, null, null, '0', '1', '417', '2'), ('4105', null, '1584523448', null, null, null, null, null, null, '商铺名字', null, null, null, '0', '1', '414', '0'), ('4107', null, '1584585096', null, null, null, null, null, null, '商铺名字', null, null, null, '0', '1', '415', '0'), ('4109', null, '1584593302', null, null, null, null, null, null, '商铺名字', null, null, null, '0', '1', '416', '0'), ('4132', null, '1584949137', null, null, null, null, null, null, '商铺名字', null, null, null, '0', '1', '422', '0'), ('4137', null, '1585668732', null, null, null, null, null, null, '商铺名字', null, null, null, '0', '1', '430', '0'), ('4139', null, '1585034207', null, null, null, null, null, null, '商铺名字', null, null, null, '0', '1', '423', '0'), ('4142', null, '1586254852', null, null, null, null, null, null, '商铺名字', null, null, null, '0', '1', '437', '0'), ('4149', null, '1585198437', null, null, null, null, null, null, '商铺名字', null, null, null, '0', '1', '424', '0'), ('4153', null, '1585226948', null, null, null, null, null, null, '商铺名字', null, null, null, '0', '1', '425', '0'), ('4160', null, '1586277556', null, null, null, null, null, null, '商铺名字', null, null, null, '0', '1', '440', '0'), ('4161', null, '1585486659', null, null, null, null, null, null, '商铺名字', null, null, null, '0', '1', '426', '0'), ('4183', null, '1585807044', null, null, null, null, null, null, '商铺名字', null, null, null, '0', '1', '432', '0'), ('4188', null, '1585900615', null, null, null, null, null, null, '商铺名字', null, null, null, '0', '1', '433', '0'), ('4192', null, '1585932164', null, null, null, null, null, null, '111', null, null, null, '0', '1', '434', '1'), ('4196', null, '1586057880', null, null, null, null, null, null, '商铺名字', null, null, null, '0', '1', '435', '0'), ('4199', null, '1586099109', null, null, null, null, null, null, '商铺名字', null, null, null, '0', '1', '436', '0'), ('4212', null, '1586257907', null, null, null, null, null, null, '商铺名字', null, null, null, '0', '1', '438', '0'), ('4216', null, '1586317865', null, null, null, null, null, null, '商铺名字', null, null, null, '0', '1', '441', '0'), ('4221', null, '1586414681', null, null, null, null, null, null, '商铺名字', null, null, null, '0', '1', '442', '0'), ('4236', null, '1586679282', null, null, null, null, null, null, '商铺名字', null, null, null, '0', '1', '444', '0'), ('4238', null, '1586744619', null, null, null, null, null, null, '商铺名字', null, null, null, '0', '1', '445', '0'), ('4252', null, '1586913937', null, null, null, null, null, null, '商铺名字', null, null, null, '0', '1', '447', '0'), ('4265', null, '1587017144', null, null, null, null, null, null, '商铺名字', null, null, null, '0', '1', '448', '0'), ('4270', null, '1587041480', null, null, null, null, null, null, '商铺名字', null, null, null, '0', '1', '450', '0'), ('4278', null, '1587215148', null, null, null, null, null, null, '商铺名字', null, null, null, '0', '1', '451', '0'), ('4282', null, '1587351296', null, null, null, null, null, null, '商铺名字', null, null, null, '0', '1', '453', '0'), ('4286', null, '1587368417', null, null, null, null, null, null, '商铺名字', null, null, null, '0', '1', '454', '0'), ('4287', null, '1587389596', null, null, null, null, null, null, '商铺名字', null, null, null, '0', '1', '455', '0'), ('4288', null, '1587442847', null, null, null, null, null, null, '商铺名字', null, null, null, '0', '1', '456', '0'), ('4294', null, '1587540323', null, null, null, null, null, null, '商铺名字', null, null, null, '0', '1', '459', '0'), ('4296', null, '1587546701', null, null, null, null, null, null, '商铺名字', null, null, null, '0', '1', '460', '0'), ('4298', null, '1587571611', null, null, null, null, null, null, '商铺名字', null, null, null, '0', '1', '461', '0'), ('4302', null, '1587631657', null, null, null, null, null, null, '商铺名字', null, null, null, '0', '1', '462', '0'), ('4312', null, '1587884116', null, null, null, null, null, null, '商铺名字', null, null, null, '0', '1', '463', '0'), ('4315', null, '1587956940', null, null, null, null, null, null, '商铺名字', null, null, null, '0', '1', '464', '0'), ('4319', null, '1588127827', null, null, null, null, null, null, '商铺名字', null, null, null, '0', '1', '467', '0'), ('4320', null, '1588134219', null, null, null, null, null, null, '商铺名字', null, null, null, '0', '1', '477', '0'), ('4321', null, '1588139659', null, null, null, null, null, null, '商铺名字', null, null, null, '0', '1', '479', '0'), ('4328', null, '1588211220', null, null, null, null, null, null, '商铺名字', null, null, null, '0', '1', '482', '0'), ('4334', null, '1588223664', null, null, null, null, null, null, '商铺名字', null, null, null, '0', '1', '483', '0'), ('4337', null, '1588233980', null, null, null, null, null, null, '商铺名字', null, null, null, '0', '1', '484', '0'), ('4338', null, '1588239826', null, null, null, null, null, null, 'latuer酒庄', null, null, null, '0', '1', '485', '1'), ('4343', null, '1588301952', null, null, null, null, null, null, '商铺名字', null, null, null, '0', '1', '486', '0'), ('4351', null, '1588564089', null, null, null, null, null, null, '商铺名字', null, null, null, '0', '1', '487', '0'), ('4356', null, '1588679067', null, null, null, null, null, null, '糖蜜美学', null, null, null, '0', '1', '488', '1'), ('4358', null, '1588691182', null, null, null, null, null, null, '商铺名字', null, null, null, '0', '1', '489', '0'), ('4360', null, '1588744079', null, null, null, null, null, null, '商铺名字', null, null, null, '0', '1', '490', '0');
COMMIT;

-- ----------------------------
--  Table structure for `shop_store_user`
-- ----------------------------
DROP TABLE IF EXISTS `shop_store_user`;
CREATE TABLE `shop_store_user` (
  `id` int(11) NOT NULL,
  `storeid` int(11) DEFAULT NULL COMMENT '此用户拥有哪个站点的管理权。开通子账号会用到这个。如果这个有值，那么就是子账号了。对应 store.id',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
--  Records of `shop_store_user`
-- ----------------------------
BEGIN;
INSERT INTO `shop_store_user` VALUES ('457', '1');
COMMIT;

-- ----------------------------
--  Table structure for `site`
-- ----------------------------
DROP TABLE IF EXISTS `site`;
CREATE TABLE `site` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` char(40) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '站点名字',
  `userid` int(11) DEFAULT '0' COMMENT '用户id，0为游客',
  `addtime` int(11) DEFAULT NULL COMMENT '创建时间',
  `m_show_banner` tinyint(1) DEFAULT '1' COMMENT '是否在首页显示Banner  1显示 0不显示',
  `phone` char(14) COLLATE utf8_unicode_ci DEFAULT '',
  `qq` char(12) COLLATE utf8_unicode_ci DEFAULT '',
  `template_id` int(11) DEFAULT '1' COMMENT '模版编号',
  `domain` char(30) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '二级域名',
  `about_us_cid` int(11) DEFAULT '0' COMMENT '网站关于我们介绍页面所对应的news.cid',
  `logo` char(80) COLLATE utf8_unicode_ci DEFAULT '' COMMENT 'PC端的LOGO',
  `client` tinyint(2) DEFAULT '2' COMMENT '客户端类型，1:PC，  2:WAP，3:CMS',
  `keywords` char(38) COLLATE utf8_unicode_ci DEFAULT '' COMMENT '搜索的关键词',
  `address` char(80) COLLATE utf8_unicode_ci DEFAULT '' COMMENT '地址（公司地址，办公地址，联系地址）',
  `username` char(10) COLLATE utf8_unicode_ci DEFAULT '' COMMENT '联系人姓名',
  `company_name` char(30) COLLATE utf8_unicode_ci DEFAULT '' COMMENT '公司名、工作室名字、团体名字',
  `bind_domain` char(30) CHARACTER SET utf8 DEFAULT NULL,
  `column_id` char(80) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '栏目id，每个id用英文逗号分割。这里主要是记录各个列表页面的栏目，如  ,12,23,  前后都要有,',
  `state` tinyint(2) DEFAULT '1' COMMENT '站点状态，1正常；2冻结',
  `contact_us_cid` int(11) DEFAULT '0' COMMENT '网站联系我们介绍页面所对应的news.cid',
  `div_template` char(15) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '自定义模版，位于/template/模版名字，修改HTML的形式',
  `template_name` char(20) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '自定义模版，位于/template/模版名字，修改HTML的形式',
  `column_code_url` tinyint(2) DEFAULT '0' COMMENT '是否使用栏目代码。默认为0，不使用，url使用id进行编码。  1:使用栏目代码作为URL，以后都会用1',
  `expiretime` int(11) DEFAULT '0' COMMENT '过期时间',
  `use_kefu` tinyint(1) DEFAULT '0' COMMENT '是否开启在线客服功能。0不开启，默认；  1开启',
  `attachment_size` int(11) DEFAULT NULL COMMENT '当前附件占用的空间大小，服务器空间，或云存储空间。计算的是 site/siteid/ 下的空间占用大小，单位是KB',
  `attachment_size_have` int(11) DEFAULT NULL COMMENT '当前用户网站所拥有的空间大小，单位是MB',
  `attachment_update_date` int(11) DEFAULT NULL COMMENT '当前附件占用空间大小，最后一次计算的日期，存储格式如 20191224 ，每天登录时都会计算一次',
  PRIMARY KEY (`id`),
  UNIQUE KEY `domain` (`domain`,`userid`,`state`,`expiretime`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=220 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='网站，每个站点都会存为一条记录';

-- ----------------------------
--  Records of `site`
-- ----------------------------
BEGIN;
INSERT INTO `site` VALUES ('219', '测试演示自定义模版站', '243', '1488446743', '1', '17753600820', '25689732', '1', 'cs', '591', null, '3', '测试演示的自定义模版站', '潍坊软件园', '雷爷', '', '', null, '1', '0', null, '', '0', '2000123200', '1', null, '1000', null);
COMMIT;

-- ----------------------------
--  Table structure for `site_column`
-- ----------------------------
DROP TABLE IF EXISTS `site_column`;
CREATE TABLE `site_column` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` char(60) COLLATE utf8_unicode_ci DEFAULT NULL,
  `url` char(100) COLLATE utf8_unicode_ci DEFAULT '' COMMENT '链接地址',
  `icon` char(100) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '本栏目的图片、图标，可在模版中使用{siteColumn.icon}进行调用此图以显示',
  `rank` int(4) DEFAULT '0' COMMENT '排序,数字越小越往前',
  `used` tinyint(1) DEFAULT '1' COMMENT '是否启用。1启用，0不启用',
  `siteid` int(11) DEFAULT NULL COMMENT '对应的站点id,site.id',
  `userid` int(11) DEFAULT NULL COMMENT '对应user.id',
  `parentid` int(11) DEFAULT NULL COMMENT '上级栏目的id，若是顶级栏目，则为0',
  `type` tinyint(3) DEFAULT '6' COMMENT '所属类型，1新闻信息；2图文信息；3独立页面；4留言板；5超链接；6纯文字',
  `client` tinyint(2) DEFAULT '2' COMMENT 'PC:1； WAP:2',
  `template_page_list_name` char(20) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '当前栏目所使用的列表模版名字。当site.templateName有值时，整个网站使用的自定义模版，这里的才会生效',
  `template_page_view_name` char(20) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '当前栏目所使用的内容模版名字。当site.templateName有值时，整个网站使用的自定义模版，这里的才会生效',
  `code_name` char(20) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '当前栏目的代码，当前用户的某个网站内，这个栏目代码是唯一的',
  `parent_code_name` char(20) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '父级栏目代码，父栏目的栏目代码',
  `list_num` int(4) DEFAULT '0' COMMENT '适用于CMS模式下，新闻、图文列表，在生成列表页时，每页显示多少条数据',
  `input_model_code_name` char(30) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT 'CMS模式有效，绑定此栏目的输入模型 ，对应input_model表的code_name，若是为null或者空，则为默认的输入模型',
  `edit_mode` tinyint(2) DEFAULT '0' COMMENT '若是独立页面，内容编辑的方式，使用富文本编辑框，使用输入模型，则为0， 若是使用模版进行编辑，则为1',
  `list_rank` tinyint(2) DEFAULT '1' COMMENT '栏目内信息的列表排序规则，1:按照发布时间倒序，发布时间越晚，排序越靠前; 2:按照发布时间正序，发布时间越早，排序越靠前。 如果这里为null，则是v4.4版本以前的，那么默认程序里面会将不为2的全部都认为是1。',
  `edit_use_titlepic` tinyint(2) DEFAULT NULL COMMENT '内容管理中，添加内容时，封面图片的输入。 0隐藏，1显示，若是null，则是兼容v4.6以前的版本，需要根据栏目类型type进行判断',
  `edit_use_intro` tinyint(2) DEFAULT NULL COMMENT '内容管理中，添加内容时，文章简介的输入 0隐藏，1显示，若是null，则是兼容v4.6以前的版本，需要根据栏目类型type进行判断',
  `edit_use_text` tinyint(2) DEFAULT NULL COMMENT '内容管理中，添加内容时，文章详情的输入 0隐藏，1显示，若是null，则是兼容v4.6以前的版本，需要根据栏目类型type进行判断',
  `edit_use_extend_photos` tinyint(2) DEFAULT NULL COMMENT '内容管理中，添加内容时，图集的输入 0隐藏，1显示，若是null，则是兼容v4.6以前的版本，需要根据栏目类型type进行判断',
  `use_generate_view` tinyint(2) DEFAULT '1' COMMENT '是否生成内容页面。取值1生成；0不生成，如果为null则默认为1，默认是生成。set时使用 SiteColumn.USED_ENABLE 赋值',
  `admin_news_used` int(2) DEFAULT NULL COMMENT '是否在内容管理中显示这个栏目。',
  `template_code_column_used` int(2) DEFAULT NULL COMMENT '是否在模版调用中显示（调取子栏目列表）。1使用，0不使用',
  `template_code_news_used` int(2) DEFAULT NULL COMMENT '是否在模版调用中显示（调取文章列表）。1使用，0不使用',
  PRIMARY KEY (`id`),
  KEY `rank` (`rank`,`used`,`siteid`,`userid`,`parentid`,`type`,`client`,`code_name`,`parent_code_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='栏目表，网站上的栏目';

-- ----------------------------
--  Table structure for `site_data`
-- ----------------------------
DROP TABLE IF EXISTS `site_data`;
CREATE TABLE `site_data` (
  `id` int(11) NOT NULL DEFAULT '0' COMMENT 'site.id',
  `index_description` varchar(400) COLLATE utf8_unicode_ci DEFAULT '' COMMENT '首页的描述',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='站点相关的一些信息，不常用或者长度变动比较大的。主要是电脑端网站有用到。';

-- ----------------------------
--  Table structure for `site_size_change`
-- ----------------------------
DROP TABLE IF EXISTS `site_size_change`;
CREATE TABLE `site_size_change` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `agency_id` int(11) DEFAULT NULL COMMENT '对应Agency.id，当前操作的若是代理，这里为当前的代理的id',
  `site_size_change` int(11) DEFAULT '0' COMMENT '代理变动的“站”余额的多少，消耗为负数，增加为正数  {@link Agency}.siteSize',
  `change_before` int(11) DEFAULT '0' COMMENT '变动前，站点的站余额是多少， {@link Agency}.siteSize',
  `change_after` int(11) DEFAULT '0' COMMENT '变化之后的站点的站余额是多少。{@link Agency}.siteSize',
  `goalid` int(11) DEFAULT '0' COMMENT '其余额变动，是开通的哪个站点引起的，记录站点的id，或者是哪个人给他增加的，记录给他增加的人的userid',
  `addtime` int(11) DEFAULT NULL COMMENT '变动时间',
  `userid` int(11) DEFAULT '0' COMMENT '当前操作的用户的user.id',
  PRIMARY KEY (`id`),
  KEY `site_size_change` (`site_size_change`,`addtime`,`userid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='站币变动日志记录表。站币变动日志由阿里云日志服务跟此表共同记录';

-- ----------------------------
--  Table structure for `site_user`
-- ----------------------------
DROP TABLE IF EXISTS `site_user`;
CREATE TABLE `site_user` (
  `userid` int(11) NOT NULL,
  `siteid` int(11) DEFAULT NULL COMMENT 'v4.9增加,v5.0版本从user表中转移到site_user,此用户拥有哪个站点的管理权。网站开通子账号会用到这个。如果这个有值，那么就是子账号了。对应 site.id',
  `id` int(11) NOT NULL,
  PRIMARY KEY (`userid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci ROW_FORMAT=DYNAMIC;

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
INSERT INTO `system` VALUES ('USER_REG_ROLE', '用户注册后的权限，其值对应角色 role.id', '1', '6', '1506333513'), ('SITE_NAME', '网站名称', '网·市场', '7', null), ('SITE_KEYWORDS', '网站SEO搜索的关键字，首页根内页没有设置description的都默认用此', '网市场云建站系统', '8', null), ('SITE_DESCRIPTION', '网站SEO描述，首页根内页没有设置description的都默认用此', '管雷鸣', '9', null), ('CURRENCY_NAME', '站内货币名字', '仙玉', '10', null), ('INVITEREG_AWARD_ONE', '邀请注册后奖励给邀请人多少站内货币（一级下线，直接推荐人，值必须为整数）', '5', '11', null), ('INVITEREG_AWARD_TWO', '邀请注册后奖励给邀请人多少站内货币（二级下线，值必须为整数）', '2', '12', null), ('INVITEREG_AWARD_THREE', '邀请注册后奖励给邀请人多少站内货币（三级下线，值必须为整数）', '1', '13', null), ('INVITEREG_AWARD_FOUR', '邀请注册后奖励给邀请人多少站内货币（四级下线，值必须为整数）', '1', '14', null), ('ROLE_USER_ID', '普通用户的角色id，其值对应角色 role.id', '1', '15', '1506333544'), ('ROLE_SUPERADMIN_ID', '超级管理员的角色id，其值对应角色 role.id', '9', '16', '1506333534'), ('BBS_DEFAULT_PUBLISH_CLASSID', '论坛中，如果帖子发布时，没有指明要发布到哪个论坛板块，那么默认选中哪个板块(分类)，这里便是分类的id，即数据表中的 post_class.id', '3', '20', '1506478724'), ('USER_HEAD_PATH', '用户头像(User.head)上传OSS或服务器进行存储的路径，存储于哪个文件夹中。<br/><b>注意</b><br/>1.这里最前面不要加/，最后要带/，如 head/<br/>2.使用中时，中途最好别改动，不然改动之前的用户设置好的头像就都没了', 'head/', '21', '1506481173'), ('ALLOW_USER_REG', '是否允许用户自行注册。<br/>1：允许用户自行注册<br/>0：禁止用户自行注册', '1', '22', '1507537911'), ('LIST_EVERYPAGE_NUMBER', '所有列表页面，每页显示的列表条数。', '15', '23', '1507538582'), ('SERVICE_MAIL', '网站管理员的邮箱。<br/>当网站出现什么问题，或者什么提醒时，会自动向管理员邮箱发送提示信息', '123456@qq.com', '24', '1511934294'), ('AGENCY_ROLE', '代理商得角色id', '10', '25', '1511943731'), ('ALIYUN_ACCESSKEYID', '阿里云平台的accessKeyId。<br/>若/src下的配置文件中有关此参数为空，则参数变会从这里赋值。<br/>可从这里获取 https://ak-console.aliyun.com', 'LTAI4FmkQKH2dSbJgCQ9uFA3', '26', '1581924825'), ('ALIYUN_ACCESSKEYSECRET', '阿里云平台的accessKeySecret。<br/>若/src下的配置文件中有关此参数为空，则参数变会从这里赋值。<br/>可从这里获取 https://ak-console.aliyun.com', 'BpKiQEdvwYyldc2HGqCvkoolP7VaXz', '27', '1581924830'), ('ALIYUN_OSS_BUCKETNAME', '其实就是xnx3Config配置文件中配置OSS节点进行文件上传的OSS配置。若xml文件中没有配置，那么会自动从这里读取。<br/>若值为auto，则会自动创建。建议值不必修改，默认即可。它可自动给你赋值。', 'wangmarket1581924839', '28', '1512626183'), ('IW_AUTO_INSTALL_USE', '是否允许通过访问/install/目录进行可视化配置参数。<br/>true：允许使用<br/>false:不允许使用<br/>建议不要动此处。执行完/install 配置完后，此处会自动变为false', 'true', '29', '1512616421'), ('ALIYUN_LOG_SITESIZECHANGE', '站币变动的日志记录。此项无需改动', 'sitemoneychange', '30', '1512700960'), ('AUTO_ASSIGN_DOMAIN', '网站生成后，会自动分配给网站一个二级域名。这里便是泛解析的主域名。<br/>如果分配有多个二级域名，则用,分割。并且第一个是作为主域名会显示给用户看到。后面的其他的域名用户不会看到，只可以使用访问网站。', '', '31', '1512717500'), ('MASTER_SITE_URL', '设置当前建站系统的域名。如建站系统的登录地址为 http://wang.market/login.do ，那么就将 http://wang.market/  填写到此处。', 'https://api.shop.leimingyun.com/', '134', '1515401613'), ('ATTACHMENT_FILE_URL', '设置当前建站系统中，上传的图片、附件的访问域名。若后续想要将附件转到云上存储、或开通CDN加速，可平滑上云使用。', '//cdn.shop.leimingyun.com/', '135', '1581924902'), ('ATTACHMENT_FILE_MODE', '当前文件附件存储使用的模式，用的阿里云oss，还是服务器本身磁盘进行存储。<br/>可选一：aliyunOSS：阿里云OSS模式存储<br/>可选二：localFile：服务器本身磁盘进行附件存储', 'aliyunOSS', '136', '1515395510'), ('SITEUSER_FIRST_USE_EXPLAIN_URL', '网站建站用户第一天登陆网站管理后台时，在欢迎页面会自动通过iframe引入的入门使用说明的视频，这里便是播放的视频的页面网址', '//video.leimingyun.com/sitehelp/sitehelp.html', '137', '1533238686'), ('AGENCYUSER_FIRST_USE_EXPLAIN_URL', '代理用户前15天登陆代理后台时，会自动弹出使用教程的提示。这里便是教程的链接地址', '//www.wscso.com/jianzhanDemo.html', '138', '1533238686'), ('SITE_TEMPLATE_DEVELOP_URL', '模版开发说明，模版开发入门', '//tag.wscso.com/4192.html', '139', '1540972613'), ('FORBID_DOMAIN', '保留不给用户申请的二级域名。多个用|分割，且填写字符必须小写，格式如 admin|domain|m|wap|www  如果留空不填则无保留域名', 'admin|domain', '149', '1566269940'), ('STATIC_RESOURCE_PATH', '系统静态资源如css、js等调用的路径。填写如:  //res.weiunity.com/   默认是/ 则是调取当前项目的资源，以相对路径调用', '/', '150', '1540972613'), ('ROLE_ADMIN_SHOW', '总管理后台中，是否显示权限管理菜单。1为显示，0为不显示', '0', '151', '1540972613'), ('FEN_GE_XIAN', '分割线，系统变量，若您自己添加，请使用id为 10000以后的数字。 10000以前的数字为系统预留。', '10000', '10000', '1540972613'), ('WEIXIN_APPLET_APPID', null, 'wxcc19e7b9b36da2a3', '10001', '0'), ('WEIXIN__APPLET_APPSECRET', null, '9aaf03a65a321df8e400d34a6538f360', '10002', '0'), ('ALIYUN_COMMON_ENDPOINT', '阿里云通用的地域设置。如香港，则此处的值为 hongkong ；上海，则是 shanghai', 'beijing', '10003', '1581924836'), ('ALIYUN_OSS_ENDPOINT', '阿里云OSS的Endpoint设置。如香港，则此处的值为 oss-cn-hongkong.aliyuncs.com ；上海，则是 oss-cn-shanghai.aliyuncs.com', 'oss-cn-beijing.aliyuncs.com', '10004', '1581924838');
COMMIT;

-- ----------------------------
--  Table structure for `template`
-- ----------------------------
DROP TABLE IF EXISTS `template`;
CREATE TABLE `template` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` char(50) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '模版的名字，编码，唯一，限制50个字符以内',
  `addtime` int(11) DEFAULT NULL COMMENT '模版添加时间',
  `userid` int(11) DEFAULT '0' COMMENT '此模版所属的用户，user.id。如果此模版是用户的私有模版，也就是 iscommon=0 时，这里存储导入此模版的用户的id',
  `remark` char(200) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '模版的简介，备注说明，限制300字以内',
  `preview_url` char(200) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '模版预览网址，示例网站网址，绝对路径，',
  `type` int(11) DEFAULT NULL COMMENT '模版所属分类，如广告、科技、生物、医疗等。',
  `companyname` char(50) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '模版开发者公司名字。如果没有公司，则填写个人姓名',
  `username` char(10) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '模版开发人员的名字，姓名',
  `siteurl` char(100) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '模版开发者官方网站、企业官网。如果是企业，这里是企业官网的网址，格式如： http://www.leimingyun.com  ，如果是个人，则填写个人网站即可',
  `terminal_mobile` tinyint(2) DEFAULT NULL COMMENT '网站模版是否支持手机端, 1支持，0不支持',
  `terminal_pc` tinyint(2) DEFAULT NULL COMMENT '网站模版是否支持PC端, 1支持，0不支持',
  `terminal_ipad` tinyint(2) DEFAULT NULL COMMENT '网站模版是否支持平板电脑, 1支持，0不支持',
  `terminal_display` tinyint(2) DEFAULT NULL COMMENT '网站模版是否支持展示机, 1支持，0不支持',
  `iscommon` tinyint(2) DEFAULT NULL COMMENT '是否是公共的模版 1是公共的模版， 0不是公共的，私有的，是用户自己开通网站导入的',
  `rank` int(11) DEFAULT NULL COMMENT '公共模版的排序，数字越小越靠前。',
  `wscso_down_url` char(100) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT 'wscso模版文件下载的url地址',
  `zip_down_url` char(100) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT 'zip模版素材包文件下载的url地址',
  `preview_pic` char(100) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '模版预览图的网址，preview.jpg 图片的网址',
  `resource_import` char(10) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT 'js、css等资源引用方式。 cloud：使用云端模版库； private:使用私有模版库，也就是本地的',
  PRIMARY KEY (`id`),
  KEY `name` (`name`,`userid`,`type`,`companyname`,`username`,`terminal_mobile`,`terminal_pc`,`terminal_ipad`,`terminal_display`,`iscommon`,`addtime`,`rank`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='模版';

-- ----------------------------
--  Table structure for `template_data`
-- ----------------------------
DROP TABLE IF EXISTS `template_data`;
CREATE TABLE `template_data` (
  `name` char(20) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '模版的名字，模版编码',
  `id` int(11) NOT NULL,
  PRIMARY KEY (`name`),
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='模版，分表，（暂未用到，预留）';

-- ----------------------------
--  Table structure for `template_page`
-- ----------------------------
DROP TABLE IF EXISTS `template_page`;
CREATE TABLE `template_page` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` char(20) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '当前模版页面的名字，仅给用户自己看，无其他作用',
  `type` tinyint(2) DEFAULT '0' COMMENT '类型；0其他；1首页；2新闻列表；3新闻详情；4图片列表；5图片详情；6单页面如关于我们',
  `userid` int(11) DEFAULT NULL COMMENT '所属的用户id',
  `template_name` char(20) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '所属模版的名字',
  `siteid` int(11) DEFAULT NULL COMMENT '当前使用的站点id',
  `remark` char(30) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '备注，限制30个字以内',
  `edit_mode` tinyint(2) DEFAULT NULL COMMENT '当前模版页面的编辑模式，1:智能模式； 2:代码模式。  这里，判断只要不是2，那都是智能模式，以兼容以前的版本',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='模版页面，CMS模式网站中用到';

-- ----------------------------
--  Table structure for `template_page_data`
-- ----------------------------
DROP TABLE IF EXISTS `template_page_data`;
CREATE TABLE `template_page_data` (
  `id` int(11) NOT NULL,
  `text` mediumtext COLLATE utf8mb4_unicode_ci COMMENT '当前模版页面的模版内容',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='模版页面的分表，存储具体模版页面的内容';

-- ----------------------------
--  Table structure for `template_var`
-- ----------------------------
DROP TABLE IF EXISTS `template_var`;
CREATE TABLE `template_var` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `template_name` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '模版名字',
  `userid` int(11) DEFAULT NULL COMMENT '模版所属用户的id',
  `updatetime` int(11) DEFAULT NULL COMMENT '最后修改时间',
  `addtime` int(11) DEFAULT NULL COMMENT '添加时间',
  `var_name` char(20) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '变量名字',
  `remark` char(30) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '备注，限制30个字以内',
  `siteid` int(11) DEFAULT NULL COMMENT '当前模版变量所属的网站',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='模版变量。CMS模式网站中用到。 userid为1的变量都是通用模版的变量，再系统启动起来时，会缓存到全局缓存中。';

-- ----------------------------
--  Table structure for `template_var_data`
-- ----------------------------
DROP TABLE IF EXISTS `template_var_data`;
CREATE TABLE `template_var_data` (
  `id` int(11) NOT NULL COMMENT '对应template_var.id',
  `text` mediumtext CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '模版变量的内容文字',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='模版变量，分表，存储具体变量的内容';

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
  `freezemoney` float(8,2) DEFAULT '0.00' COMMENT '账户冻结余额，金钱,RMB，单位：元',
  `lastip` char(15) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '最后一次登陆的ip',
  `isfreeze` tinyint(2) DEFAULT '0' COMMENT '是否已冻结，1已冻结（拉入黑名单），0正常',
  `money` float(8,2) DEFAULT '0.00' COMMENT '账户可用余额，金钱,RMB，单位：元',
  `sign` char(80) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '个人签名',
  `sex` char(4) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '男、女、未知',
  `version` int(11) DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `email` (`email`,`username`,`phone`) USING BTREE,
  KEY `username` (`username`,`email`,`phone`,`isfreeze`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=491 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='用户信息表。系统登陆的用户信息都在此处';

-- ----------------------------
--  Records of `user`
-- ----------------------------
BEGIN;
INSERT INTO `user` VALUES ('1', 'admin', '', '94940b4491a87f15333ed68cc0cdf833', 'default.png', '好看环境卡萨丁', '9', '1512818402', '1588492735', '127.0.0.1', '9738', '17000000002', '1', '0', '0.00', '127.0.0.1', '0', '0.00', '1', null, '256'), ('243', 'wangzhan', '', '0c5a0883e40a2a6ad84a42eab27519e6', '70877108e0684e1d9586f327eb5aafb5.png', '客服小红', '1', '1488446743', '1581925018', '218.56.88.231', '6922', '', '0', '392', '0.00', '127.0.0.1', '0', '0.00', null, null, '256'), ('392', 'agency', '', '80c5df10de72fde1b346de758c70d337', 'default.png', '代理', '10', '1512818402', '1515402763', '127.0.0.1', '9738', '17000000001', '0', '1', '0.00', '127.0.0.1', '0', '0.00', null, null, '1'), ('393', 'store', null, 'aa6d0fa04e70ab7b343a32c9812a4956', null, '商家', '8', '0', '1588491633', '', '1234', null, '0', '1', '0.00', '127.0.0.1', '0', '0.00', null, null, '169');
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
--  Table structure for `user_role`
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userid` int(11) DEFAULT NULL COMMENT '用户的id，user.id,一个用户可以有多个角色',
  `roleid` int(11) DEFAULT NULL COMMENT '角色的id，role.id ，一个用户可以有多个角色',
  PRIMARY KEY (`id`),
  KEY `userid` (`userid`)
) ENGINE=InnoDB AUTO_INCREMENT=511 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='用户拥有哪些角色';

-- ----------------------------
--  Records of `user_role`
-- ----------------------------
BEGIN;
INSERT INTO `user_role` VALUES ('257', '243', '1'), ('412', '392', '10'), ('413', '1', '9'), ('414', '394', '1'), ('415', '395', '1'), ('416', '396', '1'), ('417', '397', '1'), ('418', '398', '1'), ('419', '399', '1'), ('420', '400', '1'), ('421', '401', '1'), ('422', '402', '1'), ('423', '403', '1'), ('424', '404', '1'), ('425', '405', '1'), ('426', '406', '1'), ('427', '407', '1'), ('428', '408', '1'), ('429', '409', '1'), ('430', '410', '1'), ('431', '411', '1'), ('432', '412', '1'), ('433', '413', '1'), ('434', '414', '1'), ('435', '415', '1'), ('436', '416', '1'), ('437', '417', '1'), ('438', '418', '1'), ('439', '419', '1'), ('440', '420', '1'), ('441', '421', '1'), ('442', '422', '1'), ('443', '423', '1'), ('444', '424', '1'), ('445', '425', '1'), ('446', '426', '1'), ('447', '427', '1'), ('448', '428', '1'), ('449', '429', '1'), ('450', '430', '1'), ('451', '431', '1'), ('452', '432', '1'), ('453', '433', '1'), ('454', '434', '1'), ('455', '435', '1'), ('456', '436', '1'), ('457', '437', '1'), ('458', '438', '1'), ('459', '439', '1'), ('460', '440', '1'), ('461', '441', '1'), ('462', '442', '1'), ('463', '443', '1'), ('464', '444', '1'), ('465', '445', '1'), ('466', '446', '1'), ('467', '447', '1'), ('468', '448', '1'), ('469', '449', '1'), ('470', '450', '1'), ('471', '451', '1'), ('472', '452', '1'), ('473', '453', '1'), ('474', '454', '1'), ('475', '455', '1'), ('476', '456', '1'), ('477', '457', '8'), ('478', '458', '8'), ('479', '459', '1'), ('480', '460', '1'), ('481', '461', '1'), ('482', '462', '8'), ('483', '463', '8'), ('484', '464', '8'), ('485', '465', '1'), ('486', '466', '1'), ('487', '467', '8'), ('488', '468', '1'), ('489', '469', '1'), ('490', '470', '1'), ('491', '471', '1'), ('492', '472', '1'), ('493', '473', '1'), ('494', '474', '1'), ('495', '475', '1'), ('496', '476', '1'), ('497', '477', '8'), ('498', '478', '1'), ('499', '479', '8'), ('500', '480', '1'), ('501', '481', '1'), ('502', '482', '8'), ('503', '483', '8'), ('504', '484', '8'), ('505', '485', '8'), ('506', '486', '8'), ('507', '487', '8'), ('508', '488', '8'), ('509', '489', '8'), ('510', '490', '8');
COMMIT;

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