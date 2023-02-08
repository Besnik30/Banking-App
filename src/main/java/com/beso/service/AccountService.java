package com.beso.service;

import com.beso.converter.Converter;
import com.beso.entity.*;
import com.beso.exception.AccountApplicationNotFoundException;
import com.beso.exception.AccountNotFoundException;
import com.beso.exception.CreditCardApplicationException;
import com.beso.repository.AccountApplicationRepository;
import com.beso.repository.AccountRepository;
import com.beso.repository.CreditCardApplicationRepository;
import com.beso.resource.AccountResource;
import com.beso.resource.UserResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

@Service
@Transactional
public class AccountService {
    @Autowired
    private AccountRepository accountRepository;

   @Autowired
   private AccountApplicationRepository accountApplicationRepository;

    @Autowired
    private ClientService clientService;

    @Autowired
    private CreditCardApplicationRepository creditCardApplicationRepository;

    @Autowired
    private Converter<AccountResource,Account> accountConverter;

    @Autowired
    private Converter<UserResource,User> userConverter;


    public Account getAccountById(Integer id){
        return accountRepository.findById(id).orElseThrow(()->new AccountNotFoundException());
    }

    public AccountResource getAccountResourceById(Integer id){
        Optional<Account>accountOptional=accountRepository.findById(id);

        if(accountOptional.isEmpty()){
            throw  new AccountNotFoundException();
        }
        else{
            return accountConverter.fromEntity(accountOptional.get());
        }
    }

    public Integer getAccountIdByIban(String iban){
        return accountRepository.findAccountIdByIban(iban);
    }

    public List<AccountResource> getClientAccountResources(Integer clientId){
        List<Integer>accountIds=accountRepository.findAccountIdsByClientId(clientId);
        List<Account> accounts=accountRepository.findAllById(accountIds);
        List<AccountResource> accountResources=new ArrayList<>();
        AccountResource resource;


            for(Account entity:accounts){
                resource=accountConverter.fromEntity(entity);
                accountResources.add(resource);

        }
        return accountResources;
    }

    public List<Account> getClientAccounts(Integer clientId){
        List<Integer>accountIds=accountRepository.findAccountIdsByClientId(clientId);
        List<Account> accounts=accountRepository.findAllById(accountIds);

        return accounts;
    }

    public List<AccountResource> getAccountsByStatus(String status){
        List<Account>accounts=accountRepository.findAccountsByStatus(status);
        List<AccountResource>accountResources=new ArrayList<>();
        AccountResource resource;

        for(Account entity:accounts){
            resource=accountConverter.fromEntity(entity);
            accountResources.add(resource);
        }
        return accountResources;
    }

    public void creditBalance(Account account,Integer amount){
        account.setBalance(account.getBalance()+amount);
        accountRepository.save(account);
    }

    public void debitBalance(Account account,Integer amount,Integer interest){
        account.setBalance(account.getBalance()-(amount+(amount*interest/100)));
        accountRepository.save(account);
    }

    public void createCurrentAccount(Integer applicationId){
        Optional<AccountApplication> accountApplicationOptional = accountApplicationRepository.findById(applicationId);

        if (accountApplicationOptional.isEmpty()){
          throw new AccountApplicationNotFoundException();
        }
        else{
            AccountApplication accountApplication = accountApplicationOptional.get();
            User client=clientService.getUserById(accountApplication.getClientId());
            String iban = generateIban(accountApplication.getCurrency());
            Account account=new Account(iban, accountApplication.getCurrency(),0, AccountType.CURRENT,AccountStatus.INACTIVE,0,client);
            accountRepository.save(account);
        }
    }

    private String generateIban(String currency) {
        return currency +Math.abs(ThreadLocalRandom.current().nextInt());
    }

    public Account createTechnicalAccount(Integer applicationId,Integer interest){
        Optional<CreditCardApplication> creditCardApplicationOptional = creditCardApplicationRepository.findById(applicationId);
        if(creditCardApplicationOptional.isEmpty()){
            throw new CreditCardApplicationException();
        }
        else{
            CreditCardApplication creditCardApplication = creditCardApplicationOptional.get();
            creditCardApplication.setApplicationStatus(ApplicationStatus.APPROVED);
            User client=clientService.getUserById(creditCardApplication.getClientId());
            String iban= generateIban(creditCardApplication.getCurrency());
            Account account=new Account(iban, creditCardApplication.getCurrency(),0,AccountType.TECHNICAL,AccountStatus.ACTIVE,interest,client);
            accountRepository.save(account);
            return account;
        }
    }

    public AccountResource updateAccountStatus(Integer accountId,AccountStatus accountStatus){
       Optional<Account>accountOptional=accountRepository.findById(accountId);

       if(accountOptional.isEmpty()){
           throw new AccountNotFoundException();
       }
       else{
           accountOptional.get().setAccountStatus(accountStatus);
           return accountConverter.fromEntity(accountOptional.get());
       }
    }
}
