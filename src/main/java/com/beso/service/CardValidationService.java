package com.beso.service;

import com.beso.entity.Account;
import com.beso.entity.AccountType;
import com.beso.exception.AccountNotActiveException;
import com.beso.exception.CardExistsException;
import com.beso.exception.WrongAccountTypeException;
import com.beso.repository.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CardValidationService {

    @Autowired
    private AccountService accountService;

    @Autowired
    private CardRepository cardRepository;

    public Account validateAccount(Integer accountId) {
       Account account = accountService.getAccountById(accountId);
        AccountType accountType = account.getAccountType();

        if (!account.isActive()) {
           throw new AccountNotActiveException();
        }
        if ( cardRepository.existsById(account.getAccountId()) ) {
           throw new CardExistsException();
        }
        if (accountType == AccountType.TECHNICAL) {
           throw new WrongAccountTypeException();
        }
        return account;
    }
}
