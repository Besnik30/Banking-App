package com.beso.service;

import com.beso.converter.Converter;
import com.beso.converter.UserConverter;
import com.beso.entity.*;
import com.beso.exception.UserNotFoundException;
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
public class ClientService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ClientValidationService clientValidationService;

    @Autowired
    private Converter <UserResource,User> userConverter;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserResource createClient(UserResource clientResource) {
        User client=userConverter.toEntity(clientResource);
        clientValidationService.validateClientInput(client);
        String encodedPassword=passwordEncoder.encode(clientResource.getPassword());
        client.setPassword(encodedPassword);
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
            client1.setPassword(passwordEncoder.encode(client.getPassword()));
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

    public Map<String,Object> showAllClients(Integer pageNo,Integer pageSize) {
        Pageable page= PageRequest.of(pageNo,pageSize);
        Page<User> pagedResult=userRepository.showUsersByUserType((UserType.CLIENT).toString(),page);
        List<User>users=pagedResult.getContent();
        List<UserResource>userResources=new ArrayList<>();
        UserResource resource;

        for(User entity:users){
            resource=userConverter.fromEntity(entity);
            userResources.add(resource);
        }

        Map<String,Object> result=new HashMap<>();
        result.put("users: ",userResources);
        result.put("currentPage: ",pagedResult.getNumber());
        result.put("totalItems: ",pagedResult.getTotalElements());
        result.put("totalPages: ",pagedResult.getTotalPages());

        return result;
    }

}