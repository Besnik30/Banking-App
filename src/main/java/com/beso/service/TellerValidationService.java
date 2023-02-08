package com.beso.service;

import com.beso.entity.User;
import com.beso.entity.UserType;
import com.beso.exception.UserNotFoundException;
import com.beso.exception.WrongUserTypeException;
import com.beso.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class TellerValidationService {

    @Autowired
    private UserRepository userRepository;

    public void validateTellerInput(User teller){
        if(teller==null){
           throw new UserNotFoundException();
        }
        if(teller.getUserType() != UserType.TELLER){
           throw new WrongUserTypeException();
        }
    }

    public void validateTeller(Integer id,User teller){
        Optional<User> userOptional=userRepository.findById(id);

        if(userOptional.isEmpty()){
            throw new UserNotFoundException();
        }
        if(teller ==null){
            throw new UserNotFoundException();
        }
        if (teller.getUserType() != UserType.TELLER || userOptional.get().getUserType() != UserType.TELLER){
           throw new WrongUserTypeException();
        }
    }
}
