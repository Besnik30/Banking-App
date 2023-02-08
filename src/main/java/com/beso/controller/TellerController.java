package com.beso.controller;

import com.beso.resource.UserResource;
import com.beso.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import javax.transaction.Transactional;
import java.util.List;

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
    public @ResponseBody UserResource getTellerById(@PathVariable("tellerId") Integer tellerId){
        return tellerService.getTellerById(tellerId);
    }

    @GetMapping(value="/tellers")
    public @ResponseBody List<UserResource> showAllTellers(){
        return tellerService.getAllTellers();
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
