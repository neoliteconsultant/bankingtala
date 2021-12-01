package co.tala.bank.persistence;

import co.tala.bank.persistence.entities.BankAccount;
import javax.annotation.Resource;
import org.junit.Test;
import org.junit.After;
import org.junit.Before;
import static org.junit.Assert.*;

import org.junit.runner.RunWith;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;


/**
 * Test cases for {@link BankAccountDAO}.
 * <p>
 * Copyright (c) Tala Ltd., July 27, 2017
 *
 * author <a href="tonyafula@gmail.com">Tony Afula</a>
 */
@Rollback
@Transactional(transactionManager = "transactionManager")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "file:src/main/webapp/WEB-INF/dispatcher-servlet-test.xml")//loads the Spring configuration file manually
public class BankAccountDAOTest {
    private final String accountNumber1= "01103455001553";
    private EmbeddedDatabase db;
    
    @Resource
    private BankAccountDAO bankAccountDAO;

    @Before
    public void setUp() {
        // creates a HSQL in-memory db populated from classpath:tala_test_db
        //db = new EmbeddedDatabaseBuilder().addDefaultScripts().build();	
        
    }

    /**
     * Test of {@link BankAccountDAO#getAccount(java.lang.String)}.
     */
    @Test
    public void testGetAccount() {    
        BankAccount bankAccountActual = bankAccountDAO.getAccount(accountNumber1);

        assertEquals(accountNumber1, bankAccountActual.getAccountNumber());
        assertEquals("Mark Langer", bankAccountActual.getAccountName());
        assertEquals(50000, bankAccountActual.getAvailableBalance(),0.01);
    }

    /**
     * Test of {@link BankAccountDAO#updateAccountBalance(co.tala.bank.persistence.entities.BankAccount, java.lang.Double) }.
     */
    @Test
    public void testUpdateAccount() {
        Double newBalance =577.00; 
        
        BankAccount bankAccountExpected = new BankAccount();
        bankAccountExpected.setId(1);
        bankAccountExpected.setAccountNumber(accountNumber1);
        bankAccountExpected.setAvailableBalance(newBalance);
        
        boolean isUpdated = bankAccountDAO.updateAccount(bankAccountExpected);

        assertTrue(isUpdated);
    }

    @After
    public void tearDown() {
        //db.shutdown();
    }
   

}
