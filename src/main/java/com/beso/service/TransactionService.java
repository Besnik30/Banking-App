package com.beso.service;

import com.beso.converter.Converter;
import com.beso.entity.*;
import com.beso.exception.AccountNotActiveException;
import com.beso.exception.IbanException;
import com.beso.exception.InsufficientFundsException;
import com.beso.exception.NoCardConnectedException;
import com.beso.repository.AccountRepository;
import com.beso.repository.TransactionRepository;
import com.beso.resource.TransactionResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private AccountService accountService;

    @Autowired
    private CardService cardService;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private Converter<TransactionResource,Transaction> transactionConverter;

    public List<TransactionResource> getTransactions(){
        List<Transaction> transactions=transactionRepository.findAll();
        List<TransactionResource> transactionResources=new ArrayList<>();
        TransactionResource resource;

        for(Transaction entity:transactions){
            resource=transactionConverter.fromEntity(entity);
            transactionResources.add(resource);
        }
        return transactionResources;
    }

    public List<TransactionResource>getClientTransactions(Integer clientId){
        List<Transaction> transactions=transactionRepository.showClientTransactions(clientId);
        List<TransactionResource> transactionResources=new ArrayList<>();
        TransactionResource resource;

        for(Transaction entity:transactions){
            resource=transactionConverter.fromEntity(entity);
            transactionResources.add(resource);
        }
        return transactionResources;
    }

    public TransactionResource makeTransaction(Integer senderAccountId,String iban,Integer amount){
        Account sender=accountService.getAccountById(senderAccountId);
        Account receiver=accountService.getAccountById(accountService.getAccountIdByIban(iban));
        AccountType senderAccountType=sender.getAccountType();
        Integer senderBalance=sender.getBalance();

        if(sender.getAccountId() == receiver.getAccountId()){
            throw new IbanException();
        }

        if(senderAccountType==AccountType.CURRENT && senderBalance < amount){
            throw new InsufficientFundsException();
        }

        if (!cardService.cardExists(sender.getAccountId())
                || !cardService.cardExists(receiver.getAccountId())) {
            throw new NoCardConnectedException();
        }

        if( !sender.isActive() || !receiver.isActive()){
            throw new AccountNotActiveException();
        }

        if (senderAccountType == AccountType.CURRENT) {
            accountService.debitBalance(sender,amount,0);
            accountService.creditBalance(receiver,amount);
            Transaction transaction= createTransaction(amount,sender.getCurrency(),receiver.getCurrency(),receiver,sender);
            return transactionConverter.fromEntity(transaction);
        }

        if (senderAccountType==AccountType.TECHNICAL){
            accountService.debitBalance(sender,amount,sender.getInterest());
            accountService.creditBalance(receiver,amount);
            Transaction transaction= createTransaction(amount,sender.getCurrency(),receiver.getCurrency(),receiver,sender);
            return transactionConverter.fromEntity(transaction);
        }
       else return null;
    }

    public Transaction createTransaction(Integer amount,String senderCurrency,String receiverCurrency,
                                         Account receiver,Account sender){
        Transaction senderTransaction=new Transaction(amount,senderCurrency,TransactionType.DEBIT,sender);
        transactionRepository.save(senderTransaction);
        Transaction receiverTransaction=new Transaction(amount,receiverCurrency,TransactionType.CREDIT,receiver);
        transactionRepository.save(receiverTransaction);
        return senderTransaction;
    }
}
