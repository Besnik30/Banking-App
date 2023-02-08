package com.beso.converter;

import com.beso.entity.Account;
import com.beso.entity.User;
import com.beso.resource.AccountResource;
import com.beso.resource.UserResource;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AccountConverter implements Converter<AccountResource, Account>{

    @Autowired
    private Converter<UserResource, User> userConverter;

    @Override
    public Account toEntity(AccountResource resource){
        Account entity=new Account();
        BeanUtils.copyProperties(resource, entity);
        return entity;
    }

    @Override
    public AccountResource fromEntity(Account entity){
        AccountResource resource=AccountResource.builder()
                .accountStatus(entity.getAccountStatus())
                .accountType(entity.getAccountType())
                .balance(entity.getBalance())
                .currency(entity.getCurrency())
                .iban(entity.getIban())
                .userResource(userConverter.fromEntity(entity.getClient()))
                .interest(entity.getInterest())
                .build();
        return resource;
    }
}
