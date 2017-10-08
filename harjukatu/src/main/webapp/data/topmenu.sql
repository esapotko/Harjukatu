CREATE DEFINER=`root`@`localhost` PROCEDURE `getTopMenu`()
BEGIN
    SELECT * FROM potkonen.MenuItem where Position = 'Top';
END