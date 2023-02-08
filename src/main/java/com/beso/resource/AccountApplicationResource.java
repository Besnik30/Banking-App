package com.beso.resource;

import com.beso.entity.AccountType;
import com.beso.entity.ApplicationStatus;
import lombok.Builder;
import lombok.Value;
import java.sql.Date;

@Value
@Builder
public class AccountApplicationResource {

     Integer applicationId;
     String currency;
     Integer clientId;
     Date date;
     ApplicationStatus applicationStatus;
     AccountType accountType;
}
