package com.beso.service;

import com.beso.converter.Converter;
import com.beso.entity.User;
import com.beso.entity.UserType;
import com.beso.exception.AccountApplicationNotFoundException;
import com.beso.entity.AccountApplication;
import com.beso.entity.ApplicationStatus;
import com.beso.exception.AccountApplicationStatusException;
import com.beso.exception.WrongUserTypeException;
import com.beso.repository.AccountApplicationRepository;
import com.beso.resource.AccountApplicationResource;
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
public class AccountApplicationService {
    @Autowired
    private AccountApplicationRepository accountApplicationRepository;

    @Autowired
    private Converter<AccountApplicationResource, AccountApplication> accountApplicationConverter;

    @Autowired
    private Converter<UserResource,User> userConverter;

    @Autowired
    private AccountService accountService;

    @Autowired
    private ClientService clientService;

    public AccountApplicationResource requestAccountCreation(Integer clientId, String currency){
        User client=clientService.getUserById(clientId);

        if(client.getUserType() != UserType.CLIENT){
            throw new WrongUserTypeException();
        }
        else{
            AccountApplication accountApplication=new AccountApplication(currency,clientId);
            accountApplication.setApplicationStatus(ApplicationStatus.UNAPPROVED);
            AccountApplication applicationSave=accountApplicationRepository.save(accountApplication);
            return accountApplicationConverter.fromEntity(applicationSave);
        }
    }

    public Map<String,Object> getApplicationsByStatus(String status, Integer pageNo, Integer pageSize){
        Pageable page= PageRequest.of(pageNo,pageSize);
        Page<AccountApplication> pagedResult=accountApplicationRepository.showAccountApplicationsByStatus(status,page);
        List<AccountApplication> accountApplications=pagedResult.getContent();
        List<AccountApplicationResource> accountApplicationResources=new ArrayList<>();
        AccountApplicationResource accountApplicationResource;

        for(AccountApplication accountApplication:accountApplications){
            accountApplicationResource=accountApplicationConverter.fromEntity(accountApplication);
            accountApplicationResources.add(accountApplicationResource);
        }

        Map<String,Object> result=new HashMap<>();
        result.put("applications: ",accountApplicationResources);
        result.put("currentPage: ",pagedResult.getNumber());
        result.put("totalItems: ",pagedResult.getTotalElements());
        result.put("totalPages: ",pagedResult.getTotalPages());

        return result;
    }

    public AccountApplicationResource getApplicationByApplicationId(Integer applicationId){
        Optional<AccountApplication> accountApplicationOptional=accountApplicationRepository.findById(applicationId);

        if(accountApplicationOptional.isEmpty()){
            throw new AccountApplicationNotFoundException();
        }
        else{
            return accountApplicationConverter.fromEntity(accountApplicationOptional.get());
        }
    }

    public List<AccountApplicationResource> getApplicationsByClientId(Integer clientId){
        List<AccountApplication>applications=accountApplicationRepository.showApplicationsByClientId(clientId);
        List<AccountApplicationResource>applicationResources=new ArrayList<>();
        AccountApplicationResource resource;

        for(AccountApplication application:applications){
            resource= accountApplicationConverter.fromEntity(application);
            applicationResources.add(resource);
        }
        return applicationResources;
    }

    public AccountApplicationResource updateAccountApplication(Integer applicationId,ApplicationStatus status){
        Optional<AccountApplication> accountApplicationOptional=accountApplicationRepository.findById(applicationId);

        if(accountApplicationOptional.isEmpty()){
            throw new AccountApplicationNotFoundException();
        }
        if(accountApplicationOptional.get().getApplicationStatus() != ApplicationStatus.UNAPPROVED){
           throw new AccountApplicationStatusException();
        }
        AccountApplication accountApplication = accountApplicationOptional.get();
        accountApplication.setApplicationStatus(status);

        AccountApplication accountApplicationSave=accountApplicationRepository.save(accountApplication);

        if (accountApplication.getApplicationStatus() != ApplicationStatus.DENIED) {
            accountService.createCurrentAccount(applicationId);
        }
        return accountApplicationConverter.fromEntity(accountApplicationSave);
    }
}
