package co.tala.bank.model;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;


/**
 * A generic bean for displaying
 * the status (sucess or failure) of a request.
 * <p>
 * Copyright (c) Tala Ltd., July 26, 2017.
 *
 * author <a href="tonyafula@gmail.com">Tony Afula</a>
 */
@JsonTypeInfo(use = Id.NAME, include = As.PROPERTY, property = "type")//support inheritance in jackson
@JsonSubTypes({
    @JsonSubTypes.Type(value = Balance.class, name = "balance"),
   
})
//http://www.baeldung.com/jackson-inheritance
public abstract class ApiResponse {

    private Integer statusCode;
    private String message;

    protected ApiResponse(Integer statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }

    
    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer status) {
        this.statusCode = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    
    
    
    
    
    
}
