CREATE TABLE `fly_article` (
  `id` bigint(20) NOT NULL,
  `author` bigint(20) NOT NULL COMMENT '作者ID',
  `title` varchar(200) NOT NULL COMMENT '标题',
  `path` varchar(200) DEFAULT NULL,
  `category` int(11) NOT NULL COMMENT '类型 公告 分享等',
  `content` text NOT NULL COMMENT '内容',
  `top` bit(1) DEFAULT NULL COMMENT '是否置顶',
  `special` bit(1) DEFAULT NULL COMMENT '是否精华',
  `del` bit(1) DEFAULT NULL COMMENT '是否已经删除',
  `create_at` bigint(20) NOT NULL COMMENT '创建时间',
  `update_at` bigint(20) DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='文章（帖子）';

CREATE TABLE `fly_account` (
  `id` bigint(20) NOT NULL,
  `login_name` varchar(20) DEFAULT NULL,
  `login_pwd` varchar(64) DEFAULT NULL,
  `create_at` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `fly_user` (
  `id` bigint(20) NOT NULL,
  `name` varchar(20) NOT NULL COMMENT '姓名',
  `avatar` varchar(200) NOT NULL COMMENT '头像',
  `sex` tinyint(4) NOT NULL COMMENT '性别 1 男 2 女 0 未知',
  `vip` tinyint(4) NOT NULL COMMENT '会员等级  0 非会员',
  `auth` bit(1) NOT NULL COMMENT '是否认证\n',
  `address` varchar(50) DEFAULT NULL COMMENT '地址',
  `sign` varchar(200) DEFAULT NULL COMMENT '签名',
  `auth_str` varchar(45) DEFAULT NULL COMMENT '认证信息',
  `create_at` bigint(20) NOT NULL COMMENT '注册时间\n',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户i基础信息表';
