package co.tala.bank.persistence;

import co.tala.bank.persistence.entities.BankAccount;
import co.tala.bank.persistence.entities.DepositLog;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import javax.annotation.Resource;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * Test cases for {@link DepositLogDAO}.
 * <p>
 * Copyright (c) Tala Ltd., July 28, 2017
 * author <a href="tonyafula@gmail.com">Tony Afula</a>
 */
@Rollback
@Transactional(transactionManager = "transactionManager")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "file:src/main/webapp/WEB-INF/dispatcher-servlet.xml")
public class DepositLogDAOTest {

    @Resource
    private DepositLogDAO depositLogDAO;
   
    
    private final String ACCOUNT_NUMBER="01103455";

    public DepositLogDAOTest() {
    }

    /**
     * Test of {@link DepositLogDAO#logTransaction(co.tala.bank.persistence.entities.DepositLog) }.
     */
    @Test
    @Ignore
    public void testLogTransaction() {
        LocalDateTime now = LocalDateTime.now();
        DepositLog depositLog1 = new DepositLog();
        depositLog1.setAmount(1000);
        depositLog1.setTransactionDate(now);
        depositLog1.setAccountNumber(ACCOUNT_NUMBER);
        
        
        DepositLog depositLog2 = new DepositLog();
        depositLog2.setAmount(1000);
        depositLog2.setTransactionDate(now);
        depositLog2.setAccountNumber(ACCOUNT_NUMBER);

        assertTrue(depositLogDAO.logTransaction(depositLog1));
        assertTrue(depositLogDAO.logTransaction(depositLog2));
    }

    /**
     * Test of {@link DepositLogDAO#getTransactionCount(co.tala.bank.persistence.entities.BankAccount, java.util.Date) }.
     */
    @Test   
    public void testGetTransactionCount() {
      
        

        int actualCount = depositLogDAO.getTransactionCount(ACCOUNT_NUMBER, LocalDate.now());
        
        int expectedCount = 2+actualCount;

        assertEquals(expectedCount, actualCount);
    }
    
    /**
     * Test of {@link DepositLogDAO#getTransactionAmount(co.tala.bank.persistence.entities.BankAccount, java.util.Date)  }.
     */
    @Test
    @Ignore
    public void testGetTransactionAmount() {
        double expectedAmount = 2000;

        double actualAmount = depositLogDAO.getTransactionAmount(ACCOUNT_NUMBER, LocalDate.now());
        
        //System.out.println("Actual Amount "+actualAmount);

        assertEquals(expectedAmount, actualAmount,0);
    }

}
