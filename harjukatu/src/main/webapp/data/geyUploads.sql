DELIMITER @@
DROP PROCEDURE getUploads @@
CREATE PROCEDURE potkonen.getUploads
()
BEGIN
    SELECT * FROM FileItem;
END @@ 
DELIMITER ; 
