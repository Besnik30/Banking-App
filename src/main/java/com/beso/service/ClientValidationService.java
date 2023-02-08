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
public class ClientValidationService {

    @Autowired
    private UserRepository userRepository;

    public void validateClientInput(User client){
        if(client==null){
            throw new UserNotFoundException();
        }
        if(client.getUserType() != UserType.CLIENT){
            throw new WrongUserTypeException();
        }
    }

    public void validateClient(Integer id,User client){
        Optional<User> userOptional=userRepository.findById(id);

        if(userOptional.isEmpty()){
            throw new UserNotFoundException();
        }
        if(client ==null){
            throw new UserNotFoundException();
        }
        if (client.getUserType() != UserType.CLIENT || userOptional.get().getUserType() != UserType.CLIENT){
           throw new WrongUserTypeException();
        }
    }
}
