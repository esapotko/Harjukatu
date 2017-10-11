CREATE TABLE `potkonen`.`Posts` (
   ID int NOT NULL AUTO_INCREMENT,
  `PRIORITY` CHAR(1) NOT NULL DEFAULT '2',
  `TITLE` VARCHAR(256) NOT NULL,
  `newPostnewPostnewPostnewPostURL` VARCHAR(256) NULL,
  `DESCRIPTION` VARCHAR(256) NULL,
  `BANNER` VARCHAR(256) NULL,
  PRIMARY KEY (`ID`));
SET SQL_SAFE_UPDATES = 0;
SELECT * FROM potkonen.Posts;
UPDATE potkonen.Posts SET VISIBLE = '1';
DELETE FROM `potkonen`.`Posts` WHERE Id = 7;	
select * from Posts where Priority = '1' order by ID;
select * from Posts where Priority > '1' order by ID;