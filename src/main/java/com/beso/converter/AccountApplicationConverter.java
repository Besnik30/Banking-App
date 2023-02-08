package com.beso.converter;

import com.beso.entity.AccountApplication;
import com.beso.resource.AccountApplicationResource;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class AccountApplicationConverter implements Converter<AccountApplicationResource, AccountApplication> {


    @Override
    public AccountApplication toEntity(AccountApplicationResource resource) {
        AccountApplication entity=new AccountApplication();
        BeanUtils.copyProperties(resource, entity);
        return entity;
    }


    @Override
    public AccountApplicationResource fromEntity(AccountApplication entity) {

        AccountApplicationResource build = AccountApplicationResource.builder()
                .currency(entity.getCurrency())
                .clientId(entity.getClientId())
                .date(entity.getDate())
                .applicationStatus(entity.getApplicationStatus())
                .accountType(entity.getAccountType())
                .build();

        return build;
    }
}
