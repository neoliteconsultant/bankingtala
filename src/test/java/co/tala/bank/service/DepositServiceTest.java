package co.tala.bank.service;

import co.tala.bank.model.ApiResponse;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * Test cases for {@link DepositService}.
 * <p>
 * Copyright (c) Tala Ltd., July 28, 2017
 *
 * author <a href="tonyafula@gmail.com">Tony Afula</a>
 */
@Rollback
@Transactional(transactionManager = "transactionManager")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "file:src/main/webapp/WEB-INF/dispatcher-servlet.xml")//loads the Spring configuration file manually
public class DepositServiceTest {
    @Autowired
    DepositService depositService;   
    public DepositServiceTest() {
    }

    /**
     * Test of deposit method, of class DepositService.
     */
    @Test
    public void testDeposit() {
        ApiResponse apiResponse = depositService.deposit("993999888", 1000);
        assertEquals("Account number does not exist", apiResponse.getMessage());
    }
    
}
