package co.tala.bank.service;

import co.tala.bank.model.ApiResponse;
import co.tala.bank.model.Balance;
import co.tala.bank.model.ErrorMessage;

import co.tala.bank.persistence.BankAccountDAO;
import co.tala.bank.persistence.DepositLogDAO;
import co.tala.bank.persistence.entities.BankAccount;
import co.tala.bank.persistence.entities.DepositLog;

import javax.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import static co.tala.bank.util.Constants.*;
import co.tala.bank.util.CurrencyUtil;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import org.springframework.stereotype.Service;

/**
 * Business logic for deposits.
 * <p>
 * Copyright (c) Tala Ltd., July 27, 2017. author
 * <a href="tonyafula@gmail.com">Tony Afula</a>
 */
@Service
public class DepositService {

    /**
     * Max deposit for the day.
     */
    @Value("${max.deposit.day}")
    private double maxDepositDay; //read value from properties file

    /**
     * Max deposit per transaction.
     */
    @Value("${max.deposit.transaction}")
    private double maxDepositTransaction; //read value from properties file

    /**
     * Max deposit per transaction.
     */
    @Value("${max.deposit.frequency}")
    private int maxDepositFrequency; //read value from properties file

    @Resource
    private BankAccountDAO bankAccountDAO;

    @Resource
    private DepositLogDAO depositLogDAO;

    /**
     *
     */
    public DepositService() {

    }

    /**
     *
     * @param accountNumber
     * @param amount
     * @return
     */
    public ApiResponse deposit(String accountNumber, double amount) {
        ApiResponse apiResponse = new ErrorMessage(ERROR);
        BankAccount bankAccount;

        // Get the current date
        LocalDate today =LocalDate.now();
        
        if (StringUtils.isEmpty(accountNumber)) {
            apiResponse.setMessage("Account number is required");
        } else if (!StringUtils.isNumeric(accountNumber)) {
            apiResponse.setMessage("Invalid account number;Account number can only consist of digits");
        } else if (accountNumber.length() < 8 || accountNumber.length() > 15) {
            apiResponse.setMessage("Invalid account number; A/C No can consist of a minimum of 8 digits and "
                    + "a maximum of 15 digits");
        } else if (!StringUtils.isNumeric(accountNumber)) {
            apiResponse.setMessage("Invalid account number;Account number can only consist of digits");
        } else if ((bankAccount = bankAccountDAO.getAccount(accountNumber)) == null) {
            apiResponse.setMessage("Account number does not exist");
        } else if (amount > maxDepositTransaction) {
            apiResponse.setMessage("Exceeded Maximum Deposit of " + CurrencyUtil.formatNumber(maxDepositTransaction) + " Per Transaction");
        } else if (depositLogDAO.getTransactionAmount(accountNumber, today) > maxDepositDay) {
            apiResponse.setMessage("Exceeded Maximum Deposit for the day: " + CurrencyUtil.formatNumber(maxDepositDay));
        } else if (depositLogDAO.getTransactionCount(accountNumber, today) >= maxDepositFrequency) {
            apiResponse.setMessage("Exceeded Maximum Deposit frequency for the day: " + maxDepositFrequency + " transactions/day");
        } else if (amount < 100) {
            apiResponse.setMessage("Amount cannot be less than 100");
        } else {

            Double availableBalance = bankAccount.getAvailableBalance();
            Double newBalance = availableBalance + amount;

            bankAccount.setAvailableBalance(newBalance);

            boolean isUpdated = bankAccountDAO.updateAccount(bankAccount);
             String accountName = bankAccount.getAccountName();

            if (isUpdated) {
                
                StringBuilder stringBuffer =new StringBuilder();
                
                String message = stringBuffer.append(CurrencyUtil.formatNumber(amount))
                        .append(" has been deposited sucessfully from your account. (")
                        .append(accountNumber).append(", ").append(accountName)
                        .append(") Your new account balance is ")
                        .append(CurrencyUtil.formatNumber(newBalance)).toString();
//                apiResponse.setStatusCode(OK);
//                apiResponse.setMessage(message);
                
                
                        
                Balance balance = new Balance(OK,message,String.valueOf(newBalance),
                        accountNumber, accountName);
                /*
                balance.setStatusCode(OK);
                balance.setAvailableBalance(CurrencyUtil.formatNumber(newBalance));
                bankAccount.setAccountName(bankAccount.getAccountName());
                bankAccount.setAccountNumber(accountNumber);
                balance.setMessage(message);
                        */
                               
                
               
                DepositLog depositLog =new DepositLog();
                depositLog.setAmount(amount);
                depositLog.setTransactionDate(LocalDateTime.now());
                depositLog.setAccountNumber(accountNumber);
                
                depositLogDAO.logTransaction(depositLog);

                return balance;
            } else {
                apiResponse.setMessage("Unexpected server error, try again later");
            }
        }

        return apiResponse;
    }

}
