CREATE DEFINER=`root`@`localhost` PROCEDURE `delPost`(IN iID int)
BEGIN
	UPDATE potkonen.Posts SET Visible = '0' WHERE Id = iID;	
END