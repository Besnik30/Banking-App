package com.beso.controller;

import com.beso.resource.UserResource;
import com.beso.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;

@Controller
@Transactional
public class TellerController {

    @Autowired
    private TellerService tellerService;

    @PostMapping(value="/teller")
    public @ResponseBody UserResource createTeller(@RequestBody UserResource teller){
        return tellerService.createTeller(teller);
    }

    @GetMapping(value = "/teller/{tellerId}")
    @PreAuthorize("hasRole('TELLER')")
    public @ResponseBody UserResource getTellerById(@PathVariable("tellerId") Integer tellerId){
        return tellerService.getTellerById(tellerId);
    }

    @GetMapping(value="/tellers")
    public @ResponseBody
    Map<String,Object> showAllTellers(@RequestParam(defaultValue = "0") int pageNo,
                                     @RequestParam(defaultValue = "2") int size){
        return tellerService.getAllTellers(pageNo,size);
    }

    @PutMapping(value="/teller/{tellerId}")
    public @ResponseBody UserResource updateTeller(@PathVariable("tellerId") Integer tellerId,
                                                   @RequestBody UserResource teller){
        return tellerService.updateTeller(tellerId,teller);
    }

    @DeleteMapping(value="/teller/{tellerId}")
    public @ResponseBody UserResource deleteTeller(@PathVariable("tellerId") Integer tellerId){
        return tellerService.deleteTeller(tellerId);
    }

}
