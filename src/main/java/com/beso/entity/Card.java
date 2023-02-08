package com.beso.entity;

import javax.persistence.*;

@Entity
@Table(name="Card")
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @Column (nullable = false)
    @Enumerated(EnumType.STRING)
    private CardType cardType;

    @Column (nullable = false)
    private Long cardNumber;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name="accountId",nullable = false)
    private Account account;

    public Card(){}

    public Card(CardType cardType,Account account,Long cardNumber){
        this.cardType=cardType;
        this.account=account;
        this.cardNumber=cardNumber;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public CardType getCardType() {
        return cardType;
    }

    public void setCardType(CardType cardType) {
        this.cardType = cardType;
    }

    public Long getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(Long cardNumber) {
        this.cardNumber = cardNumber;
    }
}
