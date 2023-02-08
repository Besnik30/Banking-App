package com.beso.entity;

import javax.persistence.*;

@Entity
@Table(name="Transaction")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer transactionId;

    @Column (nullable = false)
    private Integer amount;

    @Column (nullable = false)
    private String currency;

    @Enumerated(EnumType.STRING)
    @Column (nullable = false)
    private TransactionType transactionType;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "accountId",nullable = false)
    Account account;

    public Transaction() {}

    public Transaction(Integer amount, String currency, TransactionType transactionType, Account account){
        this.amount=amount;
        this.currency=currency;
        this.transactionType=transactionType;
        this.account=account;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

}
