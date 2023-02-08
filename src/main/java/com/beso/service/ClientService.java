package com.beso.service;

import com.beso.converter.Converter;
import com.beso.converter.UserConverter;
import com.beso.entity.*;
import com.beso.exception.UserNotFoundException;
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
public class ClientService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ClientValidationService clientValidationService;

    @Autowired
    private Converter <UserResource,User> userConverter;

    public UserResource createClient(UserResource clientResource) {
        User client=userConverter.toEntity(clientResource);
        clientValidationService.validateClientInput(client);
        User clientSave=userRepository.save(client);
        return userConverter.fromEntity(clientSave);

    }

    public UserResource updateClient(Integer id, UserResource clientResource) {
        User client=userConverter.toEntity(clientResource);
        clientValidationService.validateClient(id,client);

            User client1=userRepository.findById(id).get();
            client1.setName(client.getName());
            client1.setSurname(client.getSurname());
            client1.setUserName(client.getUserName());
            client1.setUserBirthDay(client.getUserBirthDay());
            client1.setPassword(client.getPassword());
            User clientSave=userRepository.save(client1);
            return userConverter.fromEntity(clientSave);

    }

    public UserResource deleteClient(Integer id) {
        User client = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException());

        if (client.getUserType() != UserType.CLIENT) {
           throw new WrongUserTypeException();
        } else {
            userRepository.deleteById(id);
            return userConverter.fromEntity(client);
        }
    }

    public UserResource getClientResourceById(Integer id) {
        Optional<User> userOptional=userRepository.findById(id);

        if(userOptional.isEmpty()){
            throw  new UserNotFoundException();
        }
        if(userOptional.get().getUserType() != UserType.CLIENT){
            throw new WrongUserTypeException();
        }
        else{
            return userConverter.fromEntity(userOptional.get());
        }
    }

    public User getUserById(Integer id){
        Optional<User>userOptional=userRepository.findById(id);

        if(userOptional.isEmpty()){
            throw new UserNotFoundException();
        }
        else {
            return userOptional.get();
        }
    }

    public List<UserResource> showAllClients() {
        List<User> users=userRepository.showUsersByUserType((UserType.CLIENT).toString());
        List<UserResource>userResources=new ArrayList<>();
        UserResource resource;

        for(User entity:users){
            resource=userConverter.fromEntity(entity);
            userResources.add(resource);
        }
        return userResources;
    }

}