package com.beso.exception;

import com.beso.entity.UserType;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;


@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(){
    }
}
