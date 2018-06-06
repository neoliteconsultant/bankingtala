package co.tala.bank.service;

import co.tala.bank.model.ApiResponse;
import co.tala.bank.model.Balance;
import co.tala.bank.model.ErrorMessage;
import co.tala.bank.persistence.BankAccountDAO;
import co.tala.bank.persistence.entities.BankAccount;
import co.tala.bank.util.Constants;
import static co.tala.bank.util.Constants.OK;
import co.tala.bank.util.CurrencyUtil;
import javax.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

/**
 * Business logic for balance inquiry
 * <p>
 * Copyright (c) Tala Ltd., July 27, 2017. author
 * <a href="tonyafula@gmail.com">Tony Afula</a>
 */
@Service
public class BalanceService {
    


    @Resource
    private BankAccountDAO bankAccountDAO;

    public BalanceService() {

    }

    /**
     * 
     * @param accountNumber
     * @return 
     */
    public ApiResponse getBalance(String accountNumber) {
        ApiResponse apiResponse = new ErrorMessage(Constants.ERROR);
        BankAccount bankAccount;
        if (StringUtils.isEmpty(accountNumber)) {
            apiResponse.setMessage("Account number is required");
        }else if (!StringUtils.isNumeric(accountNumber)) {
            apiResponse.setMessage("Invalid account number;Account number can only consist of digits");
        } else if (accountNumber.length() < 8 || accountNumber.length() > 15) {
            apiResponse.setMessage("Invalid account number; A/C No can consist of a minimum of 8 digits and "
                    + "a maximum of 15 digits");
        } else if ((bankAccount = bankAccountDAO.getAccount(accountNumber)) == null) {
            apiResponse.setMessage("Account number does not exist");
        } else {
            Balance balance = new Balance(OK,"Balance Processed successfully",String.valueOf(bankAccount.getAvailableBalance()),
                        accountNumber, bankAccount.getAccountName());
            
            /*
            balance.setStatusCode(Constants.OK);
            balance.setAvailableBalance(CurrencyUtil.formatNumber(bankAccount.getAvailableBalance()));
            balance.setMessage("Balance Processed successfully");
            balance.setAccountNumber(accountNumber);
            balance.setAccountName(bankAccount.getAccountName());
                    */

           return balance;
        }

        return apiResponse;
    }

}
