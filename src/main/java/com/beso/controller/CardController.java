package com.beso.controller;

import com.beso.resource.CardResource;
import com.beso.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
public class CardController {

    @Autowired
    private CardService cardService;

    @PostMapping(value = "/client/account/{accountId}/debit-card")
    public @ResponseBody
    CardResource createDebitCard(@PathVariable("accountId") Integer accountId) {
        return cardService.createDebitCard(accountId);
    }

    @GetMapping(value="/client/{clientId}/cards")
    public @ResponseBody List<CardResource> showClientCards(@PathVariable("clientId") Integer clientId){
        return cardService.getClientCards(clientId);
    }

    @GetMapping(value = "/card/{cardId}")
    public @ResponseBody CardResource showCardById(@PathVariable("cardId") Integer cardId){
        return cardService. getCardResourceById(cardId);
    }

    @GetMapping(value = "/cards")
    public @ResponseBody List<CardResource> showAllCards(){
        return cardService.getCards();
    }
}
