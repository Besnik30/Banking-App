package com.beso.controller;

import com.beso.resource.TransactionResource;
import com.beso.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping(value = "/client/account/{accountId}/transaction")
    public @ResponseBody
    TransactionResource makeTransaction(@PathVariable("accountId") Integer accountId,
                                        @RequestParam String iban,
                                        @RequestParam Integer amount){
        return transactionService.makeTransaction(accountId,iban,amount);
    }

    @GetMapping(value="/client/{clientId}/transactions")
    public @ResponseBody
    Map<String,Object> showClientTransactions(@PathVariable("clientId") Integer clientId,
                                              @RequestParam(defaultValue = "0") int pageNo,
                                              @RequestParam(defaultValue = "2") int size){
        return transactionService.getClientTransactions(clientId,pageNo,size);
    }

    @GetMapping(value ="/teller/transactions")
    public
    @ResponseBody Map<String,Object> showTransactions(@RequestParam(defaultValue = "0") int pageNo,
                                                      @RequestParam(defaultValue = "2") int size){

        return transactionService.getTransactions(pageNo,size);
    }

}
