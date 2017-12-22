INSERT INTO `Users` (`id`,`accountNonExpired`,`accountNonLocked`,`creationDate`,`credentialsNonExpired`,`email`,`enabled`,`firstName`,`lastLogin`,`lastModified`,`lastName`,`password`,`rememberMeToken`,`secret`,`username`)
 VALUES (1,1,1,'2017-12-12 00:00:00',1,'admin@test.com',1,'admin',NULL,NULL,'min','bed2efa51d413247b0441aa9c3013650ccd83672ce6ad1cb8a618c744cb9bb7c',NULL,NULL,'admin');
 
 
INSERT INTO `Authority` (`id`,`authority`,`creationDate`,`description`,`lastModified`) VALUES (1,'ROLE_ADMIN',NULL,'Rol Adminitrator',NULL);
INSERT INTO `Authority` (`id`,`authority`,`creationDate`,`description`,`lastModified`) VALUES (2,'ROLE_USER',NULL,'Rol User',NULL);
 

INSERT INTO `Users_Authority` (`users_id`,`authorities_id`) VALUES (1,1);
INSERT INTO `Users_Authority` (`users_id`,`authorities_id`) VALUES (1,2);
 