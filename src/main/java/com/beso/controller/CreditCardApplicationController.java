package com.beso.controller;

import com.beso.entity.ApplicationStatus;
import com.beso.resource.CreditCardApplicationResource;
import com.beso.service.CreditCardApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@Controller
public class CreditCardApplicationController {

    @Autowired
    private CreditCardApplicationService creditCardApplicationService;

    @PostMapping(value = "/client/credit-card-request/client/{clientId}")
    public @ResponseBody
    CreditCardApplicationResource saveCreditCardRequest(@PathVariable("clientId") Integer clientId,
                                                        @RequestParam Integer monthlyIncome,
                                                        @RequestParam String currency){
        return creditCardApplicationService.saveCreditCardRequest(clientId,monthlyIncome,currency);
    }

    @GetMapping(value = "/teller/credit-card-requests")
    public @ResponseBody
    Map<String,Object> getApplicationsByStatus(@RequestParam String status,
                                               @RequestParam(defaultValue = "0") int page,
                                               @RequestParam(defaultValue = "2") int size){
        return creditCardApplicationService.getApplicationByStatus(status,page,size);
    }

    @GetMapping(value = "credit-card-request/{requestId}")
    public @ResponseBody CreditCardApplicationResource getApplicationsById(@PathVariable("id") Integer id){
        return creditCardApplicationService.getApplicationResourceById(id);
    }

    @PatchMapping(value = "/teller/credit-card-request/{requestId}")
    public @ResponseBody
    CreditCardApplicationResource updateCreditCardApplication(@PathVariable("requestId") Integer requestId,
                                                              @RequestParam Integer interest,
                                                              @RequestParam ApplicationStatus status){
        return creditCardApplicationService.updateCreditCardApplication(requestId,interest, status);
    }
}
