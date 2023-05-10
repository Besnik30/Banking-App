package com.beso.controller;

import com.beso.resource.UserResource;
import com.beso.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@Controller
public class ClientController {
    @Autowired
    private ClientService clientService;

    @PostMapping(value="/client")
    public @ResponseBody UserResource createClient(@RequestBody UserResource client){
        return clientService.createClient(client);
    }

    @GetMapping(value = "/clients")
    public @ResponseBody
    Map<String,Object> getAllClients( @RequestParam(defaultValue = "0") int pageNo,
                                      @RequestParam(defaultValue = "2") int size){
        return clientService.showAllClients(pageNo,size);
    }

    @GetMapping(value = "/client/{clientId}")
    @PreAuthorize("hasRole('CLIENT')")
    public @ResponseBody UserResource getClientById(@PathVariable("clientId") Integer clientId){
        return clientService.getClientResourceById(clientId);
    }

    @PutMapping(value="/client/{clientId}")
    public @ResponseBody UserResource updateClient(@PathVariable("clientId") Integer clientId,
                                                   @RequestBody UserResource client){
        return  clientService.updateClient(clientId,client);
    }

    @DeleteMapping(value="/client/{clientId}")
    public  @ResponseBody UserResource deleteClient(@PathVariable("clientId") Integer clientId){
        return clientService.deleteClient(clientId);
    }

}
