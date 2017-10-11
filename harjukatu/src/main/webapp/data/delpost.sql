CREATE DEFINER=`root`@`localhost` PROCEDURE `delPost`(IN iID int)
BEGIN
	DELETE FROM `potkonen`.`Posts` WHERE Id = iID;	
END