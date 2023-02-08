package com.beso.resource;

import com.beso.entity.CardType;
import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class CardResource {
    CardType cardType;
    Long cardNumber;
    AccountResource accountResource;

}

