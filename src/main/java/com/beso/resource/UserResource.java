package com.beso.resource;

import com.beso.entity.UserType;
import lombok.Builder;
import lombok.Value;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;


@Value
@Builder (toBuilder = true)
public class UserResource {
    Integer userId;
    String name;
    String surname;
    String userBirthDay;
    @Enumerated(EnumType.STRING)
    UserType userType;
    String userName;
    String password;
}
