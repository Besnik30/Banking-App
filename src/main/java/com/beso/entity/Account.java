package com.beso.entity;

import javax.persistence.*;
import java.util.concurrent.ThreadLocalRandom;

@Entity
@Table(name="Account")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer accountId;

    @Column(nullable = false)
    private String iban;

    @Column(nullable = false)
    private String currency;

    @Column(nullable = false)
    private Integer balance;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AccountType accountType;

    @Column(nullable = false)
    private Integer interest;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AccountStatus accountStatus;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "clientId",nullable = false)
    private User user;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="cardId")
    private Card card;



    public Account() {}

    public Account(String currency,Integer balance,AccountType accountType,AccountStatus accountStatus,Integer interest,User user){
        this.currency=currency;
        this.iban=this.currency +Math.abs(ThreadLocalRandom.current().nextInt());
        this.balance=balance;
        this.accountType=accountType;
        this.interest=interest;
        this.user=user;
        this.accountStatus=accountStatus;
    }

    public Integer getInterest() {
        return interest;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public Integer getBalance() {
        return balance;
    }

    public String getCurrency() {
        return currency;
    }

    public String getIban() {
        return iban;
    }

    public void setInterest(Integer interest) {
        this.interest = interest;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public void setBalance(Integer balance) {
        this.balance = balance;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public void setIban(String currency, Integer number) {
        this.iban = currency+number;
    }

    public void setId(Integer accountId) {
        this.accountId = accountId;
    }

    public User getClient() {
        return user;
    }

    public void setClient(User client) {
        this.user = client;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public AccountStatus getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(AccountStatus accountStatus) {
        this.accountStatus = accountStatus;
    }

    public boolean isActive() {
        if(this.accountStatus==AccountStatus.ACTIVE){
            return true;
        }
        return false;
    }

    public Card getCard() {
        return this.card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

}

