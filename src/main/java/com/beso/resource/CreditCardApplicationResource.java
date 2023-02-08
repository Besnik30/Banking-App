package com.beso.resource;

import com.beso.entity.ApplicationStatus;
import com.beso.entity.CardType;
import lombok.Builder;
import lombok.Value;

import java.sql.Date;

@Builder
@Value
public class CreditCardApplicationResource {
    Integer applicationId;
    Integer clientId;
    String currency;
    CardType cardType;
    Date date;
    ApplicationStatus applicationStatus;

}
