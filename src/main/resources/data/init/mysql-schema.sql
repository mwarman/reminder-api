DROP TABLE IF EXISTS `Reminder`;
DROP TABLE IF EXISTS `User`;

CREATE TABLE `User` (
  `id` bigint(20) unsigned NOT NULL auto_increment,
  `email` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 DEFAULT COLLATE=utf8_bin;

CREATE TABLE `Reminder` (
  `id` bigint(20) unsigned NOT NULL auto_increment,
  `text` varchar(100) NOT NULL,
  `createdAt` datetime NOT NULL,
  `dueAt` datetime DEFAULT NULL,
  `complete` bit(1) NOT NULL DEFAULT 0,
  `completedAt` datetime DEFAULT NULL,
  `userId` bigint(20) unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_Reminder_UserId` (`userId`),
  CONSTRAINT `FK_Reminder_UserId` FOREIGN KEY (`userId`) REFERENCES `User` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 DEFAULT COLLATE=utf8_bin;
