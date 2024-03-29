-- SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
-- SET time_zone = "+00:00";

CREATE DATABASE IF NOT EXISTS taladb /*!40100 DEFAULT CHARACTER SET latin1 */;
USE taladb;

CREATE USER 'talauser'@'localhost' IDENTIFIED BY 'Cv&T9fX?89';
GRANT INSERT, SELECT, CREATE, UPDATE ON taladb.* TO 'talauser'@'localhost'; 


--
-- Table structure for table account
-- Store a list of account holders
-- DROP TABLE IF EXISTS account;

CREATE TABLE bank_account (
  id int NOT NULL AUTO_INCREMENT PRIMARY KEY,
  accountName varchar(30) UNIQUE NOT NULL,
  accountNumber varchar(25) UNIQUE NOT NULL,
  availableBalance double NOT NULL,
  creationDate timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


INSERT INTO bank_account (accountName,accountNumber,availableBalance) VALUES ('Collins Liysosi','01103455',500); 
INSERT INTO bank_account (accountName,accountNumber,availableBalance) VALUES ('Magna Sundstrom','01106711',500);



--
-- Table structure for table deposit_log
-- Logs all deposits made by an account 
-- holder for auditing purposes.
-- DROP TABLE IF EXISTS deposit_log;
CREATE TABLE deposit_log (
  id int NOT NULL AUTO_INCREMENT PRIMARY KEY,
  accountNumber varchar(15) UNIQUE NOT NULL, 
  amount double NOT NULL,
  transactionDate timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (accountNumber) REFERENCES bank_account(accountNumber)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


--
-- Table structure for table withdrawal_log
-- Logs all withdrawals made by an account 
-- holder for auditing purposes.
-- DROP TABLE IF EXISTS withdrawal_log;
CREATE TABLE withdrawal_log (
  id int NOT NULL AUTO_INCREMENT PRIMARY KEY,
  accountNumber varchar(15) UNIQUE NOT NULL, 
  amount double NOT NULL,
  transactionDate timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (accountNumber) REFERENCES bank_account(accountNumber)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

