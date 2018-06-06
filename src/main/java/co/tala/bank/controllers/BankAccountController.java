package co.tala.bank.controllers;

import co.tala.bank.service.BalanceService;
import co.tala.bank.service.DepositService;
import co.tala.bank.model.ApiResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import co.tala.bank.service.WithdrawalService;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.PathVariable;

/**
 * API endpoints for performing banking transaction; balance, withdrawal, and
 * deposit.
 * <p>
 * Copyright (c) Tala Ltd., July 26, 2017
 *
 * @author <a href="tonyafula@gmail.com">Tony Afula</a>
 */
@RequestMapping("/api/v1")
@RestController
public class BankAccountController {
    private final Logger logger = Logger.getLogger(this.getClass());

    @Autowired
    DepositService depositService;

    @Autowired
    WithdrawalService withdrawalService;

    @Autowired
    BalanceService balanceService;

    /**
     * Get the balance corresponding to the specified account
     *
     * @param accountNumber - account number to deposit to.
     * @return - account balance or error message.
     */
    @RequestMapping(value = "/balance/{accountNumber}", produces = "application/json", headers = "Accept=application/json", method = RequestMethod.GET)
    public @ResponseBody
    String balance(@PathVariable String accountNumber
    ) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.enableDefaultTyping();

            ApiResponse apiResponse = balanceService.getBalance(accountNumber);

            String jsonDataString = mapper.writeValueAsString(apiResponse);
            logger.info("/balance/"+accountNumber+":jsonDataString");
            return jsonDataString;
        } catch (JsonProcessingException ex) {
            ex.printStackTrace();
        }

        return "{\"status\":true,\"message\":\"JSONException\"}";
    }

    /**
     * Debit amount to the specified account
     *
     * @param accountNumber - account number to withdraw from.
     * @param amount - amount to withdraw.
     * @return - new account balance or error message.
     */
    @RequestMapping(value = "/withdrawal/{accountNumber}/{amount}", produces = "application/json", headers = "Accept=application/json", method = RequestMethod.PATCH
    )
    public @ResponseBody
    String withdraw(@PathVariable String accountNumber,
            @PathVariable Double amount
    ) {

        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.enableDefaultTyping();

            ApiResponse apiResponse = withdrawalService.withdrawal(accountNumber, amount);

            String jsonDataString = mapper.writeValueAsString(apiResponse);
            logger.info("/withdrawal/"+accountNumber+":jsonDataString");
            return jsonDataString;
            
        } catch (JsonProcessingException ex) {
            ex.printStackTrace();
        }

        return "{\"status\":true,\"message\":\"JSONException\"}";
    }

    /**
     * Credit amount to the specified account
     *
     * @param accountNumber, - account number to deposit to.
     * @param amount - amount to deposit.
     * @return - new account balance or error message.
     */
    @RequestMapping(value = "/deposit/{accountNumber}/{amount}", produces = "application/json", headers = "Accept=application/json", method = RequestMethod.PATCH)
    public @ResponseBody
    String deposit(@PathVariable String accountNumber,
            @PathVariable Double amount
    ) {

        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.enableDefaultTyping();

            ApiResponse apiResponse = depositService.deposit(accountNumber, amount);

            String jsonDataString = mapper.writeValueAsString(apiResponse);
            logger.info("/deposit/"+accountNumber+":jsonDataString");
            return jsonDataString;
        } catch (JsonProcessingException ex) {
            ex.printStackTrace();
        }

        return "{\"status\":true,\"message\":\"JSONException\"}";
    }
}
