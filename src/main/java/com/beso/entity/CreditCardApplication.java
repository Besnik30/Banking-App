package com.beso.entity;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name="CreditCardApplication")
public class CreditCardApplication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer applicationId;

    @Column(nullable = false)
    private Integer clientId;

    @Column(nullable = false)
    private String currency;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private CardType cardType;

    @Column(nullable = false)
    private Date date;


    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ApplicationStatus applicationStatus;

    public CreditCardApplication(){}

    public CreditCardApplication(Integer clientId, String currency){
        long millis = System.currentTimeMillis();
        this.date=new java.sql.Date(millis);
        this.clientId=clientId;
        this.currency=currency;
        this.cardType=CardType.CREDIT;
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

    public void setApplicationStatus(ApplicationStatus applicationStatus) {
        this.applicationStatus = applicationStatus;
    }
    public ApplicationStatus getApplicationStatus() {
        return applicationStatus;
    }

    public CardType getCardType() {
        return cardType;
    }

    public Date getDate() {
        return date;
    }
}
