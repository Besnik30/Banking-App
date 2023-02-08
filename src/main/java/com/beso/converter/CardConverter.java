package com.beso.converter;

import com.beso.entity.Account;
import com.beso.entity.Card;
import com.beso.resource.AccountResource;
import com.beso.resource.CardResource;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CardConverter implements Converter<CardResource, Card> {
    @Autowired
    private Converter<AccountResource, Account>accountConverter;

    @Override
    public Card toEntity(CardResource resource) {
        Card entity=new Card();
        BeanUtils.copyProperties(resource, entity);
        return entity;
    }

    @Override
    public CardResource fromEntity(Card entity) {

        CardResource build = CardResource.builder()
                .cardType(entity.getCardType())
                .cardNumber(entity.getCardNumber())
                .accountResource(accountConverter.fromEntity(entity.getAccount()))
                .build();
        return build;
    }
}
