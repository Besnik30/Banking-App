package com.beso.resource;

import com.beso.entity.AccountStatus;
import com.beso.entity.AccountType;
import lombok.Builder;
import lombok.Value;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Builder
@Value
public class AccountResource {
    Integer accountId;
    String iban;
    String currency;
    Integer balance;
    @Enumerated(EnumType.STRING)
    AccountType accountType;
    Integer interest;
    @Enumerated(EnumType.STRING)
    AccountStatus accountStatus;
    UserResource userResource;
}
