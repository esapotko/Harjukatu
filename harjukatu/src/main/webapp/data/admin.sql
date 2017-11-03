CREATE TABLE `MenuItem` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `Position` varchar(10) DEFAULT NULL,
  `Title` varchar(25) DEFAULT NULL,
  `Description` varchar(255) DEFAULT NULL,
  `Action` varchar(255) DEFAULT NULL,
  `Image` varchar(255) DEFAULT NULL,
  `Target` varchar(45) DEFAULT '_self',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;
INSERT INTO `potkonen`.`MenuItem`
(`Title`,
`Position`,
`Description`,
`Action`,
`Image`)
VALUES
('Home',
'Top',
'Main page',
'index.html',
'');

INSERT INTO `potkonen`.`MenuItem`
(`Title`,
`Position`,
`Description`,
`Action`,
`Image`)
VALUES
('TODO',
'Top',
'Todo page',
'todo.html',
'');

SELECT * FROM MenuItem LIMIT 100;
SELECT * FROM MenuItem LIMIT 100;

DELETE FROM `potkonen`.`MenuItem`;
SET SQL_SAFE_UPDATES = 0;

