package com.beso.service;

import com.beso.converter.Converter;
import com.beso.entity.*;
import com.beso.exception.CreditCardApplicationException;
import com.beso.exception.CreditCardApplicationStatusException;
import com.beso.exception.LowMonthlyIncomeException;
import com.beso.exception.WrongUserTypeException;
import com.beso.repository.CreditCardApplicationRepository;
import com.beso.resource.CardResource;
import com.beso.resource.CreditCardApplicationResource;
import com.beso.resource.UserResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.*;

@Service
@Transactional
public class CreditCardApplicationService {
    @Autowired
    private CreditCardApplicationRepository creditCardApplicationRepository;

    @Autowired
    private ClientService clientService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private CardService cardService;

    @Autowired
    private Converter<CreditCardApplicationResource,CreditCardApplication>creditCardApplicationConverter;

    @Autowired
    private Converter<UserResource,User>userConverter;

    @Autowired
    private Converter<CardResource,Card> cardConverter;

    public CreditCardApplicationResource saveCreditCardRequest(Integer clientId, Integer monthlyIncome, String currency){
        User client=clientService.getUserById(clientId);

        if(client.getUserType() != UserType.CLIENT){
            throw new WrongUserTypeException();
        }

        if(monthlyIncome <500){
            throw new LowMonthlyIncomeException();
        }
        else {
            CreditCardApplication creditCardApplication =new CreditCardApplication(clientId,currency);
            creditCardApplication.setApplicationStatus(ApplicationStatus.UNAPPROVED);
            CreditCardApplication applicationSave=creditCardApplicationRepository.save(creditCardApplication);
            return creditCardApplicationConverter.fromEntity(applicationSave);
        }
    }

    public CreditCardApplicationResource updateCreditCardApplication(Integer applicationId, Integer interest, ApplicationStatus status){
        CreditCardApplication creditCardApplication=getCreditCardApplicationById(applicationId);

        if(creditCardApplication.getApplicationStatus() != ApplicationStatus.UNAPPROVED){
            throw new CreditCardApplicationStatusException();
        }

        creditCardApplication.setApplicationStatus(status);
       CreditCardApplication creditCardApplicationSave=creditCardApplicationRepository.save(creditCardApplication);

        if(creditCardApplication.getApplicationStatus() != ApplicationStatus.DENIED){
           cardService.createCreditCard( accountService.createTechnicalAccount(applicationId,interest));
        }

        return creditCardApplicationConverter.fromEntity(creditCardApplicationSave);
    }

    public CreditCardApplication getCreditCardApplicationById(Integer id){
        return  creditCardApplicationRepository.findById(id).orElseThrow(()->new CreditCardApplicationException());
    }

    public CreditCardApplicationResource getApplicationResourceById(Integer id){
        Optional<CreditCardApplication> creditCardApplicationOptional=creditCardApplicationRepository.findById(id);

        if(creditCardApplicationOptional.isEmpty()){
            throw new CreditCardApplicationException();
        }
        else {
            return creditCardApplicationConverter.fromEntity(creditCardApplicationOptional.get());
        }
    }

    public Map<String,Object> getApplicationByStatus(String status,Integer pageNo,Integer pageSize){
        Pageable page= PageRequest.of(pageNo,pageSize);
        Page<CreditCardApplication> pagedResult =creditCardApplicationRepository.getApplicationsByStatus(status,page);
        List<CreditCardApplication>applications=pagedResult.getContent();
        List<CreditCardApplicationResource> applicationResources=new ArrayList<>();
        CreditCardApplicationResource resource;

        for(CreditCardApplication application:applications){
            resource=creditCardApplicationConverter.fromEntity(application);
            applicationResources.add(resource);
        }

        Map<String,Object> result=new HashMap<>();
        result.put("Applications: ",applicationResources);
        result.put("currentPage: ",pagedResult.getNumber());
        result.put("totalItems: ",pagedResult.getTotalElements());
        result.put("totalPages: ",pagedResult.getTotalPages());

        return result;
    }
}
