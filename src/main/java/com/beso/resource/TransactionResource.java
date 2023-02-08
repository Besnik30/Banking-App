package com.beso.resource;

import com.beso.entity.TransactionType;
import lombok.Builder;
import lombok.Value;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Builder
@Value
public class TransactionResource {
    Integer transactionId;
    Integer amount;
    String currency;
    @Enumerated(EnumType.STRING)
    TransactionType transactionType;
    Integer accountId;
}
