package com.beso.exception;

import com.beso.service.ErrorMessage;
import org.hibernate.PropertyValueException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    public @ResponseBody ErrorMessage handleUserNotFoundException(UserNotFoundException exception){
        return ErrorMessage.USER_NOT_FOUND;
    }

    @ExceptionHandler(AccountNotFoundException.class)
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    public @ResponseBody ErrorMessage handleAccountNotFoundException(AccountNotFoundException exception){
        return ErrorMessage.ACCOUNT_NOT_FOUND;
    }

    @ExceptionHandler(CardNotFoundException.class)
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    public @ResponseBody ErrorMessage handleCardNotFoundException(CardNotFoundException exception){
        return ErrorMessage.CARD_NOT_FOUND;
    }

    @ExceptionHandler(CreditCardApplicationException.class)
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    public @ResponseBody ErrorMessage handleCreditCardApplicationException(CreditCardApplicationException exception){
        return ErrorMessage.CREDIT_CARD_APPLICATION_NOT_FOUND;
    }

    @ExceptionHandler(AccountApplicationNotFoundException.class)
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    public @ResponseBody ErrorMessage handleAccountApplicationNotFoundException(AccountApplicationNotFoundException exception){
        return ErrorMessage.ACCOUNT_APPLICATION_NOT_FOUND;
    }

    @ExceptionHandler(PropertyValueException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public @ResponseBody ErrorMessage handlePropertyValueException(PropertyValueException exception){
        return ErrorMessage.NULL_VALUE;
    }
  /*
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public @ResponseBody ErrorMessage handleIllegalArgumentException(IllegalArgumentException exception){
        return ErrorMessage.INVALID_INPUT;
    } */

    @ExceptionHandler(WrongUserTypeException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public @ResponseBody ErrorMessage handleWrongUserTypeException(WrongUserTypeException exception){
        return  ErrorMessage.WRONG_USER_TYPE;
    }

    @ExceptionHandler(InsufficientFundsException.class)
    public @ResponseBody ErrorMessage handleInsufficientFundsException(InsufficientFundsException exception){
        return ErrorMessage.INSUFFICIENT_FUNDS;
    }

    @ExceptionHandler(LowMonthlyIncomeException.class)
    public @ResponseBody ErrorMessage handleLowMonthlyIncomeException(LowMonthlyIncomeException exception){
        return ErrorMessage.LOW_MONTHLY_INCOME;
    }

    @ExceptionHandler(AccountNotActiveException.class)
    public @ResponseBody ErrorMessage handleAccountNotActiveException(AccountNotActiveException exception){
        return ErrorMessage.ACCOUNT_NOT_ACTIVE;
    }

    @ExceptionHandler(WrongAccountTypeException.class)
    public @ResponseBody ErrorMessage handleWrongAccountTypeException(WrongAccountTypeException exception){
        return ErrorMessage.WRONG_ACCOUNT_TYPE;
    }

    @ExceptionHandler(CardExistsException.class)
    public @ResponseBody ErrorMessage handleCardExistsException(CardExistsException exception){
        return ErrorMessage.CARD_EXISTS_FOR_THIS_ACCOUNT;
    }

    @ExceptionHandler(NoCardConnectedException.class)
    public @ResponseBody ErrorMessage handleNoCardConnectedException(NoCardConnectedException exception){
        return ErrorMessage.ACCOUNT_NOT_CONNECTED_TO_ANY_CARD;
    }

    @ExceptionHandler(AccountApplicationStatusException.class)
    public @ResponseBody ErrorMessage handleAccountApplicationException(AccountApplicationStatusException exception){
        return ErrorMessage.INVALID_ACCOUNT_APPLICATION_STATUS;
    }

    @ExceptionHandler(CreditCardApplicationStatusException.class)
    public @ResponseBody ErrorMessage handleCreditCardApplicationStatusException(CreditCardApplicationStatusException exception){
        return ErrorMessage.INVALID_CREDIT_CARD_APPLICATION;
    }

    @ExceptionHandler(IbanException.class)
    public @ResponseBody ErrorMessage handleIbanException(IbanException exception){
        return ErrorMessage.INVALID_IBAN;
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public @ResponseBody ErrorMessage handleUsernameNotFoundException(UsernameNotFoundException exception){
        return ErrorMessage.USERNAME_NOT_FOUND;
    }

}
