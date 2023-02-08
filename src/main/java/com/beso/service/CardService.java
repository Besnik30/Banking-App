package com.beso.service;

import com.beso.converter.Converter;
import com.beso.entity.*;
import com.beso.exception.AccountNotActiveException;
import com.beso.exception.CardNotFoundException;
import com.beso.exception.UserNotFoundException;
import com.beso.repository.CardRepository;
import com.beso.resource.AccountResource;
import com.beso.resource.CardResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;


@Service
@Transactional
public class CardService {
    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private AccountService accountService;

    @Autowired
    private CardValidationService cardValidationService;

    @Autowired
    private Converter<CardResource,Card> cardConverter;

    @Autowired
    private Converter<AccountResource,Account> accountConverter;

    public CardResource createDebitCard(Integer accountId) {
        Account account= cardValidationService.validateAccount(accountId);
        Long cardNumber = Math.abs(ThreadLocalRandom.current().nextLong(10000000000000000L));
        Card card = new Card(CardType.DEBIT, account, cardNumber);
        Card cardSave=cardRepository.save(card);
        account.setCard(card);
        return  cardConverter.fromEntity(cardSave);
    }

    public Card createCreditCard(Account account) {
        if(account==null){
           throw new UserNotFoundException();
        }
        if(!account.isActive()){
            throw new AccountNotActiveException();
        }
        else{
            Long cardNumber = Math.abs(ThreadLocalRandom.current().nextLong(10000000000000000L));
            Card card = new Card(CardType.CREDIT, account, cardNumber);
            account.setCard(card);
            Card cardSave=cardRepository.save(card);
            return cardSave;
        }
    }

    public boolean cardExists(Integer id) {
        return cardRepository.existsById(id);
    }

    public CardResource getCardResourceById(Integer id){
        Optional<Card>cardOptional=cardRepository.findById(id);

        if(cardOptional.isEmpty()){
            throw new CardNotFoundException();
        }
        else{
            return cardConverter.fromEntity(cardOptional.get());
        }
    }

    public List<CardResource> getClientCards(Integer clientId) {
        List<Account> accounts=accountService.getClientAccounts(clientId);
        List<Integer> accountIds = new ArrayList<>();


        for (Account account : accounts) {
            accountIds.add(account.getAccountId());
        }

        List<Card> cards = cardRepository.findAllById(accountIds);
        List<CardResource> cardResources=new ArrayList<>();
        CardResource resource;

        for(Card card:cards){
            resource=cardConverter.fromEntity(card);
            cardResources.add(resource);
        }
        return cardResources;
    }

    public List<CardResource> getCards() {
        List<Card> cards=cardRepository.findAll();
        List<CardResource>cardResources=new ArrayList<>();
        CardResource resource;

        for(Card entity:cards){
           resource=cardConverter.fromEntity(entity);
            cardResources.add(resource);
        }
        return cardResources;
    }
}
