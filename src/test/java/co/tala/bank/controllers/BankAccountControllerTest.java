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

/**
 * Test cases for {@link BankAccountController} using Spring MVC Test.
 * <a href="http://docs.spring.io/spring/docs/current/spring-framework-reference/html/integration-testing.html#spring-mvc-test-framework">Read
 * more</a>
 * <p>
 * Copyright (c) Tala Ltd., July 28, 2017
 *
 * author <a href="tonyafula@gmail.com">Tony Afula</a>
 */
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = "file:src/main/webapp/WEB-INF/dispatcher-servlet.xml")//loads the Spring configuration file manually
public class BankAccountControllerTest {

    @Autowired
    private WebApplicationContext wac;//relies on the WebApplicationContext support of the TestContext framework for 
    //loading Spring configuration from an XML configuration file 

    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    /**
     * Test of {@link BankAccountController#balance(java.lang.String) }.
     */
    @Test
    public void testBalance() throws Exception {

        //a GET request to "/api/v1/balance" and verify that the resulting response has status 200
        //and available balance of 500
        this.mockMvc.perform(get("/api/v1/balance/{accountNumber}", "01103455")).andDo(print()) //dump the results of the performed request
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.availableBalance").value("$500.00"));
        
    }

    /**
     * Test of {@link BankAccountController#withdraw(java.lang.String, java.lang.Double)
     * }.
     */
    @Test
    public void testWithdraw() throws Exception {
        /*
        this.mockMvc.perform(patch("/api/v1/withdrawal/{accountNumber}/{amount}", "01103455",1900.00)).andDo(print()) //dump the results of the performed request
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.message").value("Cannot withdraw when balance is less than withdrawal amount"));
        */
         this.mockMvc.perform(patch("/api/v1/withdrawal/{accountNumber}/{amount}", "01106711",200.00)).andDo(print()) //dump the results of the performed request
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                 .andExpect(jsonPath("$.availableBalance").value("$300.00"));
    }

    /**
     * Test of {@link BankAccountController#deposit(java.lang.String, java.lang.Double)
     * }.
     */
    @Test
    public void testDeposit() throws Exception {
        /*
        this.mockMvc.perform(patch("/api/v1/deposit/{accountNumber}/{amount}", "01103455",45000.00)).andDo(print()) //dump the results of the performed request
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.message").value("Exceeded Maximum Deposit of $40,000.00 Per Transaction"));
        */
        
        
         this.mockMvc.perform(patch("/api/v1/deposit/{accountNumber}/{amount}", "01103455",500.00)).andDo(print()) //dump the results of the performed request
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                 .andExpect(jsonPath("$.availableBalance").value("$1,000.00"));
                
    }

}
