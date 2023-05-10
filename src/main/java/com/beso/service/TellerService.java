package com.beso.service;

import com.beso.converter.Converter;
import com.beso.exception.UserNotFoundException;
import com.beso.entity.*;
import com.beso.exception.WrongUserTypeException;
import com.beso.repository.UserRepository;
import com.beso.resource.UserResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.*;

@Service
@Transactional
public class TellerService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AccountApplicationService accountApplicationService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private CardService cardService;

    @Autowired
    private CreditCardApplicationService creditCardApplicationService;

    @Autowired
    private TellerValidationService tellerValidationService;

    @Autowired
    private Converter<UserResource,User> userConverter;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserResource createTeller(UserResource tellerResource){
        User teller=userConverter.toEntity(tellerResource);
       tellerValidationService.validateTellerInput(teller);
       String encodedPassword=passwordEncoder.encode(teller.getPassword());
       teller.setPassword(encodedPassword);
       User tellerSave=userRepository.save(teller);
       return userConverter.fromEntity(tellerSave);

    }

    public UserResource updateTeller(Integer id,UserResource tellerResource){
        User teller=userConverter.toEntity(tellerResource);
        tellerValidationService.validateTeller(id,teller);
        User teller1=userRepository.findById(id).get();
        teller1.setName(teller.getName());
        teller1.setSurname(teller.getSurname());
        teller1.setUserName(teller.getUserName());
        teller1.setUserBirthDay(teller.getUserBirthDay());
        teller1.setPassword(passwordEncoder.encode(teller.getPassword()));
        User tellerSave=userRepository.save(teller1);
        return userConverter.fromEntity(tellerSave);
    }

    public UserResource deleteTeller(Integer id){
        User teller=userRepository.findById(id).orElseThrow(()->new UserNotFoundException());

        if(teller.getUserType() != UserType.TELLER){
            throw new WrongUserTypeException();
        }
        else{
            userRepository.deleteById(id);
            return userConverter.fromEntity(teller);
        }
    }

    public UserResource getTellerById(Integer id){
        Optional<User> userOptional=userRepository.findById(id);

        if(userOptional.isEmpty()){
            throw new UserNotFoundException();
        }
        if(userOptional.get().getUserType() != UserType.TELLER){
            throw new WrongUserTypeException();
        }
        else {
            return userConverter.fromEntity(userOptional.get());
        }
    }

    public Map<String,Object> getAllTellers(Integer pageNo,Integer pageSize){
        Pageable page= PageRequest.of(pageNo,pageSize);
        Page<User> pagedResult =userRepository.showUsersByUserType((UserType.TELLER).toString(),page);
        List<User>users=pagedResult.getContent();
        List<UserResource> userResources=new ArrayList<>();
        UserResource resource;

        for(User entity:users){
            resource=userConverter.fromEntity(entity);
            userResources.add(resource);
        }

        Map<String,Object> result=new HashMap<>();
        result.put("tellers: ",userResources);
        result.put("currentPage: ",pagedResult.getNumber());
        result.put("totalItems: ",pagedResult.getTotalElements());
        result.put("totalPages: ",pagedResult.getTotalPages());

        return result;
    }
}
