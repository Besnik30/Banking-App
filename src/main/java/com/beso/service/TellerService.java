package com.beso.service;

import com.beso.converter.Converter;
import com.beso.exception.UserNotFoundException;
import com.beso.entity.*;
import com.beso.exception.WrongUserTypeException;
import com.beso.repository.UserRepository;
import com.beso.resource.UserResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    public UserResource createTeller(UserResource tellerResource){
        User teller=userConverter.toEntity(tellerResource);
       tellerValidationService.validateTellerInput(teller);
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
        teller1.setPassword(teller.getPassword());
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

    public List<UserResource> getAllTellers(){
        List<User> users=userRepository.showUsersByUserType((UserType.TELLER).toString());
        List<UserResource> userResources=new ArrayList<>();
        UserResource resource;

        for(User entity:users){
            resource=userConverter.fromEntity(entity);
            userResources.add(resource);
        }
        return userResources;
    }
}
