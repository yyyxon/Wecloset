CREATE USER `UserChat`@`localhost` IDENTIFIED BY 'yeon';
CREATE USER `UserChat`@`%` IDENTIFIED BY 'yeon';
  
CREATE DATABASE UserChat CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci;
  
GRANT ALL PRIVILEGES ON UserChat.* TO `UserChat`@`localhost` ;
GRANT ALL PRIVILEGES ON UserChat.* TO `UserChat`@`%` ;


CREATE TABLE USER (
	userID VARCHAR(20) NOT NULL unique ,
	userPassword VARCHAR(20),
	userName VARCHAR(20),
	userAge INT,
	userGender VARCHAR(20),
	userEmail VARCHAR(50),
	userProfile VARCHAR(50)
);


INSERT INTO `USER`(userID, userPassword, userName, userAge, userGender, userEmail, userProfile) VALUES('anonymous', NULL, '익명', NULL, NULL, NULL, NULL);



CREATE TABLE CHAT (
	chatID INT PRIMARY KEY AUTO_INCREMENT,
	fromID VARCHAR(20),
	toID VARCHAR(20),
	chatContent VARCHAR(100),
	chatTime DATETIME,
	chatRead INT
);

CREATE TABLE `BOARD` (
  `boardID` int(11) NOT NULL AUTO_INCREMENT,
  `userID` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `boardTitle` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `boardContent` text COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `boardHit` int(11) unsigned DEFAULT 0,
  `boardGroup` int(3) unsigned DEFAULT NULL,
  `boardName` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `boardSequence` int(2) unsigned DEFAULT 0,
  `boardLevel` int(2) unsigned DEFAULT 0,
  `boardAvailable` int(1) unsigned DEFAULT 1,
  `boardDate` datetime DEFAULT NULL,
  `updateDate` datetime DEFAULT NULL,
  `likeCount` int(11) DEFAULT 0,
  PRIMARY KEY (`boardID`),
  KEY `index_board` (`boardGroup`)
) ENGINE=InnoDB AUTO_INCREMENT=241 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci



ALTER TABLE BOARD ADD INDEX index_board (boardGroup);





CREATE TABLE BOARD_LIKE (
	likeID INT Auto_increment PRIMARY KEY,
   	userID VARCHAR(20),
   `boardID` INT(11) UNSIGNED , 
   `boardGroup` INT(3) UNSIGNED NULL,
   	regDate DATETIME
);


ALTER TABLE BOARD_LIKE ADD INDEX index_board_like (boardID,boardGroup);



CREATE TABLE ATTACHMENTS (
	fileID INT Auto_increment PRIMARY KEY,
   `boardID` INT(11) UNSIGNED , 
   `boardGroup` INT(3) UNSIGNED NULL,
	userID VARCHAR(20),
    filesystemName VARCHAR(100),
    originalFileName VARCHAR(100),
    fileDirectory VARCHAR(30),
    extension VARCHAR(7),
    fileSize VARCHAR(20),
    regDate DATETIME
);


ALTER TABLE ATTACHMENTS ADD INDEX index_attachments (boardID,boardGroup);


CREATE TABLE COMMENT(
	commentID INT Auto_increment PRIMARY KEY,	
	userID VARCHAR(20),
   `boardID` INT(11) UNSIGNED , 
   `boardGroup` INT(3) UNSIGNED NULL,
   `boardName` VARCHAR(50) NULL ,
	commentContent VARCHAR(3000),	
	regDate DATETIME,
	updateDate DATETIME
);


ALTER TABLE COMMENT ADD INDEX index_comment (boardID,boardGroup);



CREATE TABLE CALENDAR (
		calendarId INT(11) UNSIGNED NOT NULL AUTO_INCREMENT  PRIMARY KEY,
		title VARCHAR(250),
		description VARCHAR(1000),
		start VARCHAR(20),
		end VARCHAR(20),
		type VARCHAR(25),
		userID VARCHAR(20),
		backgroundColor VARCHAR(10),
		textColor VARCHAR(10),
		allDay VARCHAR(5)	
);
