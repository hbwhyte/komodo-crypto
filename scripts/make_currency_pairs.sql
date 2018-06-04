# Repopulates the table containing currency pairs if one or more currencies is added to the currency table.
# Courtesy of Matt Belsky :)

# Empties the currency pair table and gets the number of currencies presently in the currency table.
TRUNCATE `komodoDB`.`currency_pairs`;
SELECT COUNT(`currency_id`) INTO @numCurrencies FROM `komodoDB`.`currency` ORDER BY `currency_id` DESC LIMIT 1;

# A stored procedure that adds all combinations of pairs to the table containing pairs.
DROP PROCEDURE IF EXISTS makePairs;
DELIMITER // 
CREATE PROCEDURE makePairs() 
BEGIN
	SET @i := 1;
	WHILE @i <= @numCurrencies DO 
		SET @j := 1;
		WHILE @j <= @numCurrencies DO 
			IF @i != @j THEN 
				INSERT IGNORE INTO `komodoDB`.`currency_pairs` (`symbol1`, `symbol2`) VALUES (
					(SELECT `symbol` FROM `komodoDB`.`currency` WHERE `currency_id` = @i), 
					(SELECT `symbol` FROM `komodoDB`.`currency` WHERE `currency_id` = @j)
					);
			END IF;
        	    SET @j := @j + 1;
		END WHILE;
		SET @i := @i + 1;
	END WHILE;
END //
DELIMITER ;

# Calls the procedure and displays the list of pairs to ensure that it worked.
CALL makePairs();
SELECT * FROM `komodoDB`.`currency_pairs`;
