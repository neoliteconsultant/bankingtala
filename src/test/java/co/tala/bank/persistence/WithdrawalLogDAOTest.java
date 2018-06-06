package co.tala.bank.persistence;

import co.tala.bank.persistence.entities.WithdrawalLog;
import java.util.Date;
import javax.annotation.Resource;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * Test cases for {@link WithdrawalLogDAO}.
 * <p>
 * Copyright (c) Tala Ltd., July 28, 2017
 * author <a href="tonyafula@gmail.com">Tony Afula</a>
 */
@Rollback
@Transactional(transactionManager = "transactionManager")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "file:src/main/webapp/WEB-INF/dispatcher-servlet.xml")
public class WithdrawalLogDAOTest {

    @Resource
    private WithdrawalLogDAO withdrawalLogDAO;
    @Resource
    private BankAccountDAO bankAccountDAO;
    
    private final String ACCOUNT_NUMBER="01103455";

    public WithdrawalLogDAOTest() {
    }

    /**
     * Test of {@link WithdrawalLogDAO#logTransaction(co.tala.bank.persistence.entities.WithdrawalLog) }.
     */
    @Test 
    public void testLogTransaction() {
       
        WithdrawalLog withdrawalLog = new WithdrawalLog();
        withdrawalLog.setAmount(490);
        withdrawalLog.setTransactionDate(new Date());
        withdrawalLog.setAccountNumber(ACCOUNT_NUMBER);

        assertTrue(withdrawalLogDAO.logTransaction(withdrawalLog));
    }

    /**
     * Test of {@link WithdrawalLogDAO#getTransactionCount(co.tala.bank.persistence.entities.BankAccount, java.util.Date) }.
     */
    @Test
    public void testGetTransactionCount() {
      
        int expectedCount = 1;

        int actualCount = withdrawalLogDAO.getTransactionCount(ACCOUNT_NUMBER, new Date());

        assertEquals(expectedCount, actualCount);
    }
    
    /**
     * Test of {@link WithdrawalLogDAO#getTransactionAmount(co.tala.bank.persistence.entities.BankAccount, java.util.Date)  }.
     */
    @Test
    public void testGetTransactionAmount() {
        double expectedAmount = 1000;

        double actualAmount = withdrawalLogDAO.getTransactionAmount(ACCOUNT_NUMBER, new Date());
        

        assertEquals(expectedAmount, actualAmount,0);
    }

}
