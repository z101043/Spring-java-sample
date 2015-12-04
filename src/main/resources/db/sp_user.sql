DROP TABLE IF EXISTS `sp_user`;
CREATE TABLE IF NOT EXISTS `sp_user` (
  `id` BIGINT NOT NULL auto_increment, 
  `user_id` VARCHAR(50) NOT NULL,
  `user_pw` VARCHAR(200) NOT NULL,
  `user_nm` VARCHAR(20) NOT NULL, 
  `user_tel` VARCHAR(20) DEFAULT NULL,  
  `user_email` VARCHAR(50) DEFAULT NULL,  
  `reg_date` TIMESTAMP NOT NULL, 
  PRIMARY KEY (`id`), 
  UNIQUE KEY (`user_id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8;

