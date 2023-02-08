package com.beso.converter;

import com.beso.entity.CreditCardApplication;
import com.beso.resource.CreditCardApplicationResource;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class CreditCardApplicationConverter implements Converter<CreditCardApplicationResource,CreditCardApplication>{

    @Override
    public CreditCardApplication toEntity(CreditCardApplicationResource resource){
        CreditCardApplication entity=new CreditCardApplication();
        BeanUtils.copyProperties(resource, entity);
        return entity;
    }

    @Override
    public CreditCardApplicationResource fromEntity(CreditCardApplication entity){
        CreditCardApplicationResource build= CreditCardApplicationResource.builder()
                .clientId(entity.getClientId())
                .currency(entity.getCurrency())
                .cardType(entity.getCardType())
                .date(entity.getDate())
                .applicationStatus(entity.getApplicationStatus())
                .build();
        return build;
    }
}
