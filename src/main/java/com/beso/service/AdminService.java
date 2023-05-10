package com.beso.service;

import com.beso.converter.Converter;
import com.beso.entity.User;
import com.beso.entity.UserType;
import com.beso.exception.UserNotFoundException;
import com.beso.exception.WrongUserTypeException;
import com.beso.repository.UserRepository;
import com.beso.resource.UserResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class AdminService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private Converter <UserResource,User> userConverter;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserResource getAdminById(Integer id){
        User user=userRepository.findById(id).orElseThrow(()->new UserNotFoundException());
        return userConverter.fromEntity(user);
    }

    public UserResource createAdmin(UserResource adminResource){
        User user=userConverter.toEntity(adminResource);
        if(user.getUserType() != UserType.ADMIN){
            throw new WrongUserTypeException();
        }
        else {
            String encodedPassword = passwordEncoder.encode(user.getPassword());
            user.setPassword(encodedPassword);
            User userSave=userRepository.save(user);
            return userConverter.fromEntity(userSave);
        }
    }

    public UserResource updateAdmin(Integer id, UserResource admin){
       validateAdmin(id, admin);
        User adminEntity = userConverter.toEntity(admin);
        adminEntity.setName(admin.getName());
        adminEntity.setSurname(admin.getSurname());
        adminEntity.setUserBirthDay(admin.getUserBirthDay());
        adminEntity.setUserName(admin.getUserName());
        String encodedPassword=passwordEncoder.encode(admin.getPassword());
        adminEntity.setPassword(encodedPassword);
        return userConverter.fromEntity(userRepository.save(adminEntity));
    }

    private void validateAdmin(Integer id,UserResource admin){
        Optional<User>userOptional=userRepository.findById(id);

        if(userOptional.isEmpty()){
            throw new UserNotFoundException();
        }
        if(admin ==null){
            throw new UserNotFoundException();
        }
        if (admin.getUserType() != UserType.ADMIN || userOptional.get().getUserType() != UserType.ADMIN){
           throw new WrongUserTypeException();
        }

    }
}
