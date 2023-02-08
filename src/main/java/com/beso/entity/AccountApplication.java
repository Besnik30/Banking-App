package com.beso.entity;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name="AccountApplication")
public class AccountApplication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer applicationId;

    @Column (nullable = false)
    private String currency;

    @Column (nullable = false)
    private Integer clientId;

    @Column (nullable = false)
    private Date date;

    @Enumerated(EnumType.STRING)
    @Column (nullable = false)
    private ApplicationStatus applicationStatus;

    @Enumerated(EnumType.STRING)
    @Column (nullable = false)
    private AccountType accountType;


    public AccountApplication() {
    }

    public AccountApplication(String currency, Integer clientId){
        long millis = System.currentTimeMillis();
        this.date=new java.sql.Date(millis);
        this.accountType=AccountType.CURRENT;
        this.currency=currency;
        this.clientId=clientId;
    }

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Integer getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(Integer applicationId) {
        this.applicationId = applicationId;
    }

    public ApplicationStatus getApplicationStatus() {
        return applicationStatus;
    }

    public void setApplicationStatus(ApplicationStatus applicationStatus) {
        this.applicationStatus = applicationStatus;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getDate() {
        return date;
    }
}
