DROP TABLE IF EXISTS `token`;

CREATE TABLE `token` (
  `id`            INT UNSIGNED AUTO_INCREMENT
  COMMENT 'ID',
  `username`      VARCHAR(32)  NOT NULL
  COMMENT '账号',
  `password`      VARCHAR(32)  NOT NULL
  COMMENT '密码',
  `type`          INT UNSIGNED NOT NULL
  COMMENT '用户类型',
  access_token_id CHAR(16) COMMENT '访问令牌ID',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_username`(`username`),
  UNIQUE KEY `uk_access_token_id`(access_token_id)
) ENGINE = InnoDB DEFAULT CHARSET = utf8;

-- is created
