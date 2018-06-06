package co.tala.bank.service;

import co.tala.bank.model.ApiResponse;
import co.tala.bank.model.Balance;
import co.tala.bank.model.ErrorMessage;
import co.tala.bank.persistence.BankAccountDAO;
import co.tala.bank.persistence.WithdrawalLogDAO;
import co.tala.bank.persistence.entities.BankAccount;
import co.tala.bank.persistence.entities.WithdrawalLog;
import javax.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import static co.tala.bank.util.Constants.*;
import co.tala.bank.util.CurrencyUtil;
import java.util.Date;
import org.springframework.stereotype.Service;

/**
 * Business logic for withdrawals.
 * <p>
 * Copyright (c) Tala Ltd., July 27, 2017. author
 * <a href="tonyafula@gmail.com">Tony Afula</a>
 */
@Service
public class WithdrawalService {

    /**
     * Max withdrawal for the day.
     */
    @Value("${max.withdrawal.day}")
    private double maxWithdrawalDay; //read value from properties file

    /**
     * Max withdrawal per transaction.
     */
    @Value("${max.withdrawal.transaction}")
    private double maxWithdrawalTransaction; //read value from properties file

    /**
     * Max withdrawal frequency.
     */
    @Value("${max.withdrawal.frequency}")
    private int maxWithdrawalFrequency; //read value from properties file

    @Resource
    private BankAccountDAO bankAccountDAO;

    @Resource
    private WithdrawalLogDAO withdrawalLogDAO;

    /**
     *
     */
    public WithdrawalService() {

    }

    /**
     *
     * @param accountNumber
     * @param amount
     * @return
     */
    public ApiResponse withdrawal(String accountNumber, Double amount) {
        
        ApiResponse apiResponse = new ErrorMessage(ERROR);
        //apiResponse.setStatusCode(ERROR);
        BankAccount bankAccount;
        Date today = new Date();
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
        } else if (amount > maxWithdrawalTransaction) {
            apiResponse.setMessage("Exceeded Maximum Withdrawal of " + CurrencyUtil.formatNumber(maxWithdrawalTransaction) + " Per Transaction");
        } else if (withdrawalLogDAO.getTransactionAmount(accountNumber, today) > maxWithdrawalDay) {
            apiResponse.setMessage("Exceeded Maximum Withdrawal for the day: " + CurrencyUtil.formatNumber(maxWithdrawalDay));
        } else if (withdrawalLogDAO.getTransactionCount(accountNumber, today) >= maxWithdrawalFrequency) {
            apiResponse.setMessage("Exceeded Maximum Withdrawal frequency for the day: " + maxWithdrawalFrequency + " transactions/day");
        } else if (amount > bankAccount.getAvailableBalance()) {
            apiResponse.setMessage("Cannot withdraw when balance is less than withdrawal amount");
        } else if (amount < 100) {
            apiResponse.setMessage("Amount cannot be less than 100");
        } else {

            Double availableBalance = bankAccount.getAvailableBalance();
            Double newBalance = availableBalance - amount;
            
            bankAccount.setAvailableBalance(newBalance);
            
            System.err.println("Available Balance "+availableBalance);
            System.out.println("Amount Withdrawn "+amount);
            System.out.println("New balance "+newBalance);

            boolean isUpdated = bankAccountDAO.updateAccount(bankAccount);
            String accountName = bankAccount.getAccountName();
            if (isUpdated) {
                
                StringBuilder stringBuffer =new StringBuilder();
                
                String message = stringBuffer.append(CurrencyUtil.formatNumber(amount))
                        .append(" has been withdrawn sucessfully from your account. (")
                        .append(accountNumber).append(", ").append(accountName)
                        .append(") Your new account balance is ")
                        .append(CurrencyUtil.formatNumber(newBalance)).toString();
                
                
                
                Balance balance = new Balance(OK,message,String.valueOf((newBalance)),
                        accountNumber, accountName);
                /*
                balance.setStatusCode(OK);
                bankAccount.setAccountName(bankAccount.getAccountName());
                bankAccount.setAccountNumber(accountNumber);
                balance.setAvailableBalance(CurrencyUtil.formatNumber(newBalance));
                balance.setMessage(message);
                      */
                
                
                WithdrawalLog withdrawalLog =new WithdrawalLog();
                withdrawalLog.setAmount(amount);
                withdrawalLog.setTransactionDate(today);
                withdrawalLog.setAccountNumber(accountNumber);
                
                return balance;
            } else {
                apiResponse.setMessage("Unexpected server error, try again later");
            }
        }

        return apiResponse;
    }

}
