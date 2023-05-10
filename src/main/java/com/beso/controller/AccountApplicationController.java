package com.beso.controller;

import com.beso.entity.ApplicationStatus;
import com.beso.resource.AccountApplicationResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.beso.service.*;
import java.util.List;
import java.util.Map;

@Controller
public class AccountApplicationController {

    @Autowired
    private AccountApplicationService accountApplicationService;

    @PostMapping(value="/client/{clientId}/account-application")
    public @ResponseBody
    AccountApplicationResource saveAccountApplication(@PathVariable("clientId") Integer clientId,
                                                      @RequestParam String currency){
        return accountApplicationService.requestAccountCreation(clientId,currency);
    }

    @GetMapping(value = "/teller/account-applications")
    public @ResponseBody
    Map<String,Object> getAccountApplicationsByStatus(@RequestParam String status,
                                                      @RequestParam(defaultValue = "0") int pageNo,
                                                      @RequestParam(defaultValue = "2") int size ){
        return accountApplicationService.getApplicationsByStatus(status,pageNo,size);
    }

    @GetMapping(value = "/account-application/{applicationId}")
    public @ResponseBody
    AccountApplicationResource getApplicationById(@PathVariable("applicationId") Integer applicationId){
        return accountApplicationService.getApplicationByApplicationId(applicationId);
    }

    @PatchMapping(value = "/teller/account-application/{applicationId}")
    public @ResponseBody
    AccountApplicationResource updateAccountApplication(@PathVariable("applicationId")Integer applicationId,
                                                        @RequestParam ApplicationStatus status){
        return accountApplicationService.updateAccountApplication(applicationId,status);
    }

    @GetMapping(value = "/client/{clientId}/account-applications")
    public @ResponseBody
    List<AccountApplicationResource>showClientAccountApplications(@PathVariable("clientId") Integer clientId){
        return accountApplicationService.getApplicationsByClientId(clientId);
    }
}
