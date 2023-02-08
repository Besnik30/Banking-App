package com.beso.controller;

import com.beso.resource.TransactionResource;
import com.beso.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping(value = "/account/{accountId}/transaction")
    public @ResponseBody
    TransactionResource makeTransaction(@PathVariable("accountId") Integer accountId,
                                        @RequestParam String iban,
                                        @RequestParam Integer amount){
        return transactionService.makeTransaction(accountId,iban,amount);
    }

    @GetMapping(value="/client/{clientId}/transactions")
    public @ResponseBody
    List<TransactionResource> showClientTransactions(@PathVariable("clientId") Integer clientId){
        return transactionService.getClientTransactions(clientId);
    }

    @GetMapping(value ="/transactions")
    public @ResponseBody List<TransactionResource> showTransactions(){
        return transactionService.getTransactions();
    }

}
