package co.tala.bank.model;

/**
 * <p>
 * Copyright (c) <a href="http://www.ekenya.co.ke/">Eclectic International</a>., Jul 28, 2017
 * @author <a href="maganga.tony@ekenya.co.ke">Anthony Wafula</a>
 */
public class ErrorMessage extends ApiResponse{

    public ErrorMessage(Integer statusCode, String message) {
        super(statusCode,message);
    }

    public ErrorMessage(Integer statusCode) {
        super(statusCode,"");
    }
    
    

    
}
