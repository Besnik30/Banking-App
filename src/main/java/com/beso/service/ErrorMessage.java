package com.beso.service;

public enum ErrorMessage {

    INSUFFICIENT_FUNDS("Insufficient funds",405),
    LOW_MONTHLY_INCOME("Monthly income is lower than 500",406),
    ACCOUNT_NOT_ACTIVE("Account not active",407),
    WRONG_ACCOUNT_TYPE("Wrong account type",408),
    WRONG_USER_TYPE("Wrong user type",409),
    CARD_EXISTS_FOR_THIS_ACCOUNT("Card exists for this account ",410),
    ACCOUNT_NOT_CONNECTED_TO_ANY_CARD("Account not connected to any card",411),
    NULL_VALUE("Empty Field",412),
    INVALID_INPUT("Invalid input",413),
    INVALID_ACCOUNT_APPLICATION_STATUS("This application has been reviewed before ",414),
    INVALID_CREDIT_CARD_APPLICATION("This has been reviewed before",415),
    INVALID_IBAN("IBAN belongs to the same account",416),
    USER_NOT_FOUND("User not found",417),
    ACCOUNT_NOT_FOUND("Account not found",418),
    CARD_NOT_FOUND("Card not found",419),
    CREDIT_CARD_APPLICATION_NOT_FOUND("Credit card application not found",420),
    ACCOUNT_APPLICATION_NOT_FOUND("Account application not found",421),
    USERNAME_NOT_FOUND("User with this username not found",422);

    private String errorMessage;
    private int errorCode;

    ErrorMessage(String errorMessage, int errorCode) {
        this.errorMessage = errorMessage;
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public int getErrorCode() {
        return errorCode;
    }

}


