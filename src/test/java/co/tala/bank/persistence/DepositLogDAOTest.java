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
@ContextConfiguration(locations = "file:src/main/webapp/WEB-INF/dispatcher-servlet-test.xml")
public class DepositLogDAOTest {

    @Resource
    private DepositLogDAO depositLogDAO;
   
    
   

    public DepositLogDAOTest() {
    }

    /**
     * Test of {@link DepositLogDAO#logTransaction(co.tala.bank.persistence.entities.DepositLog) }.
     */
    @Test
    public void testLogTransaction() {
        String accountNumber="0110274550067"; 

        LocalDateTime now = LocalDateTime.now();
        DepositLog depositLog1 = new DepositLog();
        depositLog1.setAmount(1000);
        depositLog1.setTransactionDate(now);
        depositLog1.setAccountNumber(accountNumber);
        
        assertTrue(depositLogDAO.logTransaction(depositLog1));
    }

    /**
     * Test of {@link DepositLogDAO#getTransactionCount(co.tala.bank.persistence.entities.BankAccount, java.util.Date) }.
     */
    @Test  
    public void testGetTransactionCount() {
        String accountNumber="01103300710073"; 
        int expectedCount = 3;
        for(int count=1;count<=expectedCount;++count){
            DepositLog depositLog2 = new DepositLog();
            depositLog2.setAmount(1000);
            depositLog2.setTransactionDate(LocalDateTime.now());
            depositLog2.setAccountNumber(accountNumber);
            depositLogDAO.logTransaction(depositLog2);
        }
       

        int actualCount = depositLogDAO.getTransactionCount(accountNumber, LocalDate.now());   
       

        assertEquals(expectedCount, actualCount);
    }
    
    /**
     * Test of {@link DepositLogDAO#getTransactionAmount(co.tala.bank.persistence.entities.BankAccount, java.util.Date)  }.
     */
    @Test
    public void testGetTransactionAmount() {
        LocalDateTime now = LocalDateTime.now();
        String accountNumber="01106711001623"; 

        double expectedAmount = 3000;
        DepositLog depositLog3 = new DepositLog();
        depositLog3.setAmount(expectedAmount);
        depositLog3.setTransactionDate(now);
        depositLog3.setAccountNumber(accountNumber);
        depositLogDAO.logTransaction(depositLog3);

        double actualAmount = depositLogDAO.getTransactionAmount(accountNumber, LocalDate.now());
        assertEquals(expectedAmount, actualAmount,0.01);
    }

}
