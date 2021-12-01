package co.tala.bank.controllers;

import org.junit.Test;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;



import org.hibernate.Session;
import org.hibernate.SessionFactory;
import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Date;

import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import co.tala.bank.persistence.entities.BankAccount;
import co.tala.bank.persistence.entities.WithdrawalLog;
import co.tala.bank.persistence.entities.DepositLog;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = "file:src/main/webapp/WEB-INF/dispatcher-servlet-test.xml")//loads the Spring configuration file manually

/**
 * Test cases for {@link BankAccountController} using Spring MVC Test.
 * <a href="http://docs.spring.io/spring/docs/current/spring-framework-reference/html/integration-testing.html#spring-mvc-test-framework">Read
 * more</a>
 * <p>
 * Copyright (c) Tala Ltd., July 28, 2017
 *
 * author <a href="tonyafula@gmail.com">Tony Afula</a>
 */
public class BankAccountControllerTest {

    @Autowired
    private WebApplicationContext wac;//relies on the WebApplicationContext support of the TestContext framework for 
    //loading Spring configuration from an XML configuration file 
    @Resource
    private SessionFactory sessionFactory;
    private MockMvc mockMvc;
    private final String ACCOUNT_NUMBER = "011082398919";

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();

        BankAccount bankAccount = new BankAccount();
        bankAccount.setAccountName("Application Tester");
        bankAccount.setAccountNumber(ACCOUNT_NUMBER);
        bankAccount.setAvailableBalance(10000);
        bankAccount.setCreationDate(new Date());

        Session session = sessionFactory.openSession();
        session.save(bankAccount);
        session.flush();
    }

    /**
     * Test of {@link BankAccountController#balance(java.lang.String) }.
     */
    @Test
    public void testBalance() throws Exception {

        //a GET request to "/api/v1/balance" and verify that the resulting response has status 200
        //and available balance of 500
        this.mockMvc.perform(get("/api/v1/balance/{accountNumber}", ACCOUNT_NUMBER)).andDo(print()) //dump the results of the performed request
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.availableBalance").value("$1000.0"));
        
    }

    /**
     * Test of {@link BankAccountController#deposit(java.lang.String, java.lang.Double)
     * }.
     */
    @Test
    @Ignore
    public void testDeposit() throws Exception {
         this.mockMvc.perform(patch("/api/v1/deposit/{accountNumber}/{amount}", ACCOUNT_NUMBER, 200.00)).andDo(print()) //dump the results of the performed request
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                 .andExpect(jsonPath("$.availableBalance").value("$200.0"));
                
    }

    /**
     * Test max deposit for the day.
     */
    @Test
    @Ignore
    public void testMaxDepositForTheDay() throws Exception {
        this.mockMvc.perform(patch("/api/v1/deposit/{accountNumber}/{amount}", ACCOUNT_NUMBER,250_000.00)).andDo(print()) //dump the results of the performed request
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.message").value("Exceeded Maximum Deposit for the day: 150000"));
    }

    /**
     * Test max deposit per transaction.
     */
    @Test
    @Ignore
    public void testMaxDepositPerTransaction() throws Exception {
        this.mockMvc.perform(patch("/api/v1/deposit/{accountNumber}/{amount}", ACCOUNT_NUMBER,45_000.00)).andDo(print()) //dump the results of the performed request
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.message").value("Exceeded Maximum Deposit of $40,000.00 Per Transaction"));
    }

    /**
     * Max deposit frequency = 4 transactions/day
     */
    @Test
    @Ignore
    public void testMaxDepositFrequency() throws Exception {
        for(int day=1;day<=5;++day){
            DepositLog depositLog = new DepositLog();
            depositLog.setAccountNumber(ACCOUNT_NUMBER);
            depositLog.setAmount(891);
            depositLog.setTransactionDate(LocalDateTime.now());

            Session session = sessionFactory.openSession();
            session.save(depositLog);
            session.flush();
        }

        this.mockMvc.perform(patch("/api/v1/deposit/{accountNumber}/{amount}", ACCOUNT_NUMBER,1_000.00)).andDo(print()) //dump the results of the performed request
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.message").value("Exceeded Maximum Deposit frequency for the day: 4 transactions/day"));
    }

    /**
     * Test of {@link BankAccountController#withdraw(java.lang.String, java.lang.Double)
     * }.
     */
    @Test
    @Ignore
    public void testWithdraw() throws Exception {
         this.mockMvc.perform(patch("/api/v1/withdrawal/{accountNumber}/{amount}", ACCOUNT_NUMBER, 200.00)).andDo(print()) //dump the results of the performed request
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                 .andExpect(jsonPath("$.availableBalance").value("$700.0"));
    }

    /**
     * Test of {@link BankAccountController#withdraw(java.lang.String, java.lang.Double)
     * }.
     */
    @Test
    @Ignore
    public void testWithdrawalExceedingBalance() throws Exception {
        this.mockMvc.perform(patch("/api/v1/withdrawal/{accountNumber}/{amount}", ACCOUNT_NUMBER, 2900.00)).andDo(print()) //dump the results of the performed request
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.message").value("Cannot withdraw when balance is less than withdrawal amount"));
        
         
    }

    /**
     * Max withdrawal for the day = $50K.
     */
    @Test
    @Ignore
    public void testMaxWithdrawalForTheDay() throws Exception {
        this.mockMvc.perform(patch("/api/v1/withdrawal/{accountNumber}/{amount}", ACCOUNT_NUMBER, 90_000.00)).andDo(print()) //dump the results of the performed request
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.message").value("Exceeded Maximum Withdrawal for the day: 50000"));
    }

    /**
     * Max withdrawal per transaction = $20K.
     */
    @Test
    @Ignore
    public void testMaxWithdrawalPerTransaction() throws Exception {
        this.mockMvc.perform(patch("/api/v1/withdrawal/{accountNumber}/{amount}", ACCOUNT_NUMBER, 29_000.00)).andDo(print()) //dump the results of the performed request
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.message").value("Exceeded Maximum Withdrawal of 20000 Per Transaction"));
    }

     /**
     * Max deposit frequency = 4 transactions/day
     */
    @Test
    @Ignore
    public void testMaxWithdrawalFrequency() throws Exception {
        for(int day=1;day<=5;++day){
            WithdrawalLog withdrawalLog = new WithdrawalLog();
            withdrawalLog.setAccountNumber(ACCOUNT_NUMBER);
            withdrawalLog.setAmount(4_000);
            withdrawalLog.setTransactionDate(new Date());

            Session session = sessionFactory.openSession();
            session.save(withdrawalLog);
            session.flush();
        }

        this.mockMvc.perform(patch("/api/v1/withdrawal/{accountNumber}/{amount}", ACCOUNT_NUMBER, 1_000.00)).andDo(print()) //dump the results of the performed request
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.message").value("Exceeded Maximum Withdrawal frequency for the day: 4 transactions/day"));
    }


    


    
}
