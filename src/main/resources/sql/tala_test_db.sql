

CREATE TABLE IF NOT EXISTS bank_account (
  id INTEGER IDENTITY PRIMARY KEY,
  accountName VARCHAR(30) UNIQUE NOT NULL,
  accountNumber VARCHAR(25) UNIQUE NOT NULL,
  availableBalance double NOT NULL,
  creationDate timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL 
);


INSERT INTO bank_account (accountName,accountNumber,availableBalance) VALUES ('Mark Langer','01103455001553',50000); 
INSERT INTO bank_account (accountName,accountNumber,availableBalance) VALUES ('Nyomi Rodgers','01106711001623',50000);
INSERT INTO bank_account (accountName,accountNumber,availableBalance) VALUES ('Sydney Orlando','0110274550067',1000); 
INSERT INTO bank_account (accountName,accountNumber,availableBalance) VALUES ('Adams Levine','01103300710073',1000);


--
-- Table structure for table deposit_log
-- Logs all deposits made by an account 
-- holder for auditing purposes.
-- DROP TABLE IF EXISTS deposit_log;
CREATE TABLE IF NOT EXISTS deposit_log (
  id INTEGER IDENTITY PRIMARY KEY,
  accountNumber VARCHAR(15) UNIQUE NOT NULL, 
  amount double NOT NULL,
  transactionDate timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL,
  FOREIGN KEY (accountNumber) REFERENCES bank_account(accountNumber)
);


--
-- Table structure for table withdrawal_log
-- Logs all withdrawals made by an account 
-- holder for auditing purposes.
-- DROP TABLE IF EXISTS withdrawal_log;
CREATE TABLE IF NOT EXISTS withdrawal_log (
  id INTEGER IDENTITY PRIMARY KEY,
  accountNumber VARCHAR(15) UNIQUE NOT NULL, 
  amount double NOT NULL,
  transactionDate timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL,
  FOREIGN KEY (accountNumber) REFERENCES bank_account(accountNumber)
);

