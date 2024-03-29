/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.tala.bank.service;

import co.tala.bank.model.ApiResponse;
import org.junit.Test;
import static org.junit.Assert.*;
import co.tala.bank.service.BalanceService;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * Test cases for {@link BalanceService}.
 * <p>
 * Copyright (c) Tala Ltd., July 28, 2017
 *
 * author <a href="tonyafula@gmail.com">Tony Afula</a>
 */
@Rollback
@Transactional(transactionManager = "transactionManager")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "file:src/main/webapp/WEB-INF/dispatcher-servlet.xml")//loads the Spring configuration file manually
public class BalanceServiceTest {

    @Autowired
    BalanceService balanceService;

    public BalanceServiceTest() {
    }

    /**
     * Test of {@link BalanceService#getBalance(java.lang.String) }.
     */
    @Test
    public void testGetBalance() {
        ApiResponse apiResponse = balanceService.getBalance("993999888");
        assertEquals("Account number does not exist", apiResponse.getMessage());
    }

}
