package co.tala.bank.service;

import co.tala.bank.model.ApiResponse;
import co.tala.bank.model.Balance;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * Test cases for {@link WithdrawalService}.
 * <p>
 * Copyright (c) Tala Ltd., July 28, 2017
 *
 * author <a href="tonyafula@gmail.com">Tony Afula</a>
 */
@Rollback
@Transactional(transactionManager = "transactionManager")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "file:src/main/webapp/WEB-INF/dispatcher-servlet.xml")//loads the Spring configuration file manually
public class WithdrawalServiceTest {

    @Autowired
    WithdrawalService withdrawalService;

    private String accountNumber1 = "01103455";
    private String accountNumber2 = "01106711";

    public WithdrawalServiceTest() {
    }

    /**
     * Test of withdrawal method, of class WithdrawalService.
     */
    @Test
    public void testWithdrawal() {
        Balance apiResponse = (Balance) withdrawalService.withdrawal(accountNumber2, 500.00);
        assertEquals("$9,500", apiResponse.getAvailableBalance());
    }

}
