CREATE DEFINER=`root`@`localhost` PROCEDURE `getPosts`(IN iPRIORITY INT)
BEGIN
    SELECT 
        *
    FROM
        Posts
    WHERE
        Priority = iPRIORITY
    ORDER BY ID;
END