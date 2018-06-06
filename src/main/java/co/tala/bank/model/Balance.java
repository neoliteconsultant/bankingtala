package co.tala.bank.model;

import com.fasterxml.jackson.annotation.JsonTypeName;

/**
 * A representation of a balance
 * inquiry.
 * <p>
 * Copyright (c) Tala Ltd., July 27, 2017.
 *
 * author <a href="tonyafula@gmail.com">Tony Afula</a>
 */
@JsonTypeName("balance")
public class Balance extends ApiResponse{
    private String availableBalance;
    private String accountNumber;
    private String accountName;
    


    /**
     * 
     * @param statusCode
     * @param message
     * @param availableBalance
     * @param accountNumber
     * @param accountName 
     */
    
    public Balance(Integer statusCode, String message,String availableBalance, String accountNumber, String accountName) {
        super(statusCode, message);
        this.availableBalance = availableBalance;
        this.accountNumber = accountNumber;
        this.accountName = accountName;
    }
  

  
    public String getAvailableBalance() {
        return availableBalance;
    }

    public void setAvailableBalance(String availableBalance) {
        this.availableBalance = availableBalance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    
   
}
