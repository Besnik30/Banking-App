package com.beso.exception;

import com.beso.service.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class CreditCardApplicationException extends RuntimeException{
    ErrorMessage errorMessage;

    public CreditCardApplicationException(){
    }
}
