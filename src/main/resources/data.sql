-- CREATE CLIENT
INSERT INTO client VALUES (1, '1 Avenue des Champs-Élysées, 75001 Paris', '1987-10-24', 'Jean', 'Dupont', '0612345678');


-- CREATE BANK_ACCOUNT
INSERT INTO bank_account VALUES (1, 900, NOW(), 1);

-- CREATE OPERATION
INSERT INTO operation VALUES(1, 1000, NOW(), 'DEPOSIT', 1);
INSERT INTO operation VALUES(2, -100, NOW(), 'WITHDRAWAL', 1);

