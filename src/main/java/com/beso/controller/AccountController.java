package com.beso.controller;

import com.beso.entity.AccountStatus;
import com.beso.resource.AccountResource;
import com.beso.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
public class AccountController {

    @Autowired
    private AccountService accountService;

    @GetMapping(value="/teller/accounts")
    public @ResponseBody Map<String,Object>showAccountsByStatus(@RequestParam String status,
                                                      @RequestParam(defaultValue = "0") int page,
                                                      @RequestParam(defaultValue = "2") int size){
        return accountService.getAccountsByStatus(status,page,size);
    }

    @GetMapping(value = "/account/{accountId}")
    public @ResponseBody AccountResource showAccountById(@PathVariable("accountId") Integer accountId){
        return accountService.getAccountResourceById(accountId);
    }

    @GetMapping(value = "/client/{clientId}/accounts")
    public @ResponseBody
    List<AccountResource> showClientAccounts(@PathVariable("clientId") Integer clientId){
        return accountService.getClientAccountResources(clientId);
    }

    @PatchMapping(value = "/teller/account/{accountId}/status/{status}")
    public @ResponseBody
    AccountResource updateAccountStatus(@PathVariable("accountId") Integer accountId,
                                        @PathVariable("status") AccountStatus status){
        return accountService.updateAccountStatus(accountId,status);
    }
}
