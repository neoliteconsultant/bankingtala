package co.tala.bank.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * A controller that routes requests 
 * to the base url to the
 * the index file.
 * <p>
 * Copyright (c) Tala Ltd., July 28, 2017.
 *
 * author <a href="tonyafula@gmail.com">Tony Afula</a>
 */
@Controller
public class HomeController {

    @RequestMapping(value = {"/","/api/v1"})
    public String homePage() {
      
        return "index";
    }
}
