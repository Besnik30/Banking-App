package com.beso.converter;

import com.beso.entity.Transaction;
import com.beso.resource.TransactionResource;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class TransactionConverter implements Converter<TransactionResource, Transaction>{

    @Override
    public Transaction toEntity(TransactionResource resource){
        Transaction entity=new Transaction();
        BeanUtils.copyProperties(resource, entity);
        return entity;
    }

    @Override
    public TransactionResource fromEntity(Transaction entity){
        TransactionResource resource=TransactionResource.builder()
                .transactionType(entity.getTransactionType())
                .accountId(entity.getAccount().getAccountId())
                .currency(entity.getCurrency())
                .amount(entity.getAmount())
                .build();
        return resource;
    }

}
