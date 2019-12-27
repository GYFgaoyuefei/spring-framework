SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for oauth_access_token
-- ----------------------------
CREATE TABLE IF NOT EXISTS  `oauth_access_token` (
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `token_id` varchar(255) DEFAULT NULL,
  `token` blob,
  `authentication_id` varchar(255) DEFAULT NULL,
  `user_name` varchar(255) DEFAULT NULL,
  `client_id` varchar(255) DEFAULT NULL,
  `authentication` blob,
  `refresh_token` varchar(255) DEFAULT NULL,
  KEY `token_id_index` (`token_id`),
  KEY `authentication_id_index` (`authentication_id`),
  KEY `user_name_index` (`user_name`),
  KEY `client_id_index` (`client_id`),
  KEY `refresh_token_index` (`refresh_token`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for oauth_approvals
-- ----------------------------
CREATE TABLE IF NOT EXISTS `oauth_approvals` (
  `userId` varchar(256) DEFAULT NULL,
  `clientId` varchar(256) DEFAULT NULL,
  `scope` varchar(256) DEFAULT NULL,
  `status` varchar(10) DEFAULT NULL,
  `expiresAt` datetime DEFAULT NULL,
  `lastModifiedAt` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- ----------------------------
-- Table structure for oauth_code
-- ----------------------------
CREATE TABLE IF NOT EXISTS `oauth_code` (
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `code` varchar(255) DEFAULT NULL,
  `authentication` blob,
  KEY `code_index` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for oauth_refresh_token
-- ----------------------------
CREATE TABLE IF NOT EXISTS `oauth_refresh_token` (
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `token_id` varchar(255) DEFAULT NULL,
  `token` blob,
  `authentication` blob,
  KEY `token_id_index` (`token_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for serv_client_details
-- ----------------------------
CREATE TABLE IF NOT EXISTS `serv_client_details` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `access_token_validity` int(11) DEFAULT NULL,
  `additional_information` varchar(4096) DEFAULT NULL,
  `authorities` varchar(255) DEFAULT NULL,
  `auto_approve_scopes` varchar(255) DEFAULT NULL,
  `client_id` varchar(255) DEFAULT NULL,
  `grant_types` varchar(255) DEFAULT NULL,
  `redirect` varchar(255) DEFAULT NULL,
  `refresh_token_validity` int(11) DEFAULT NULL,
  `resource_ids` varchar(255) DEFAULT NULL,
  `scope` varchar(255) DEFAULT NULL,
  `secret` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `serv_client_details_clientId` (`client_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;


CREATE TABLE IF NOT EXISTS `serv_user_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `mobile` varchar(255) DEFAULT NULL,
  `nick_name` varchar(255) DEFAULT NULL,
  `org_code` varchar(255) DEFAULT NULL,
  `pass_word` varchar(255) DEFAULT NULL,
  `state` varchar(255) DEFAULT NULL,
  `user_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `serv_user_info_index_userName` (`user_name`),
  UNIQUE KEY `serv_user_info_index_mobile` (`mobile`),
  KEY `serv_user_info_index_orgCode` (`org_code`),
  KEY `serv_user_info_index_nickName` (`nick_name`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;


CREATE TABLE IF NOT EXISTS `org_resource_defined` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `note` varchar(255) DEFAULT NULL,
  `resource_group` varchar(255) DEFAULT NULL,
  `resource_name` varchar(255) DEFAULT NULL,
  `resource_type` varchar(255) DEFAULT NULL,
  `status` int(11) NOT NULL,
  `org_code` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `org_resource_action_defined_uni` (`resource_type`,`resource_name`),
  UNIQUE KEY `org_resource_action_defined_note` (`note`),
  KEY `org_resource_action_defined_group` (`resource_group`),
  KEY `org_resource_action_defined_orgCode` (`org_code`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;


CREATE TABLE IF NOT EXISTS `org_power` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `auth_code` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `group_name` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `note` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `org_power_group_uni_name` (`group_name`),
  KEY `org_power_group_status` (`status`),
  KEY `org_power_group_name` (`group_name`),
  KEY `org_power_group_orgCode` (`auth_code`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;


CREATE TABLE IF NOT EXISTS `org_user_power` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `account` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `create_account` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `org_code` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `power_name` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `power_note` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `status` int(11) NOT NULL,
  `power_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `org_user_power_uni_power` (`account`,`org_code`,`power_id`),
  KEY `org_user_power_status` (`status`),
  KEY `org_user_power_name` (`power_name`),
  KEY `org_user_power_orgCode` (`org_code`),
  KEY `org_user_power_account` (`account`),
  KEY `FKhylanc1i99k1pmfl2x4gkk4j4` (`power_id`),
  CONSTRAINT `FKhylanc1i99k1pmfl2x4gkk4j4` FOREIGN KEY (`power_id`) REFERENCES `org_power` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

CREATE TABLE IF NOT EXISTS `org_power_item` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `action` int(11) NOT NULL,
  `res_id` bigint(20) NOT NULL,
  `res_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `res_type` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `group_id` bigint(20) DEFAULT NULL,
  `target` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `grant_code` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKplenwry0nbkfr72rbv3vkdo9l` (`res_id`,`group_id`),
  KEY `org_power_group_item_name` (`res_name`),
  KEY `org_power_group_item_group` (`group_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;