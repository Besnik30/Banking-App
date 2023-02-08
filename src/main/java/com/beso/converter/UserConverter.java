package com.beso.converter;

import com.beso.entity.User;
import com.beso.resource.UserResource;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class UserConverter implements Converter<UserResource, User>{

    @Override
    public User toEntity(UserResource resource){
        User entity=new User();
        BeanUtils.copyProperties(resource, entity);
        return entity;
    }

    @Override
    public UserResource fromEntity(User entity){
        return UserResource.builder()
                .name(entity.getName())
                .surname(entity.getSurname())
                .userBirthDay(entity.getUserBirthDay())
                .userType(entity.getUserType())
                .userName(entity.getUserName())
                .password(entity.getPassword())
                .build();
    }
}
