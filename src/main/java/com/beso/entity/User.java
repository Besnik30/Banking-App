package com.beso.entity;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.persistence.*;

@Entity
@Table(name="User")
@Component
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;

    @Column (nullable = false)
    private String name;

    @Column (nullable = false)
    private String surname;

    @Column (nullable = false)
    private String userBirthDay;

    @Enumerated (EnumType.STRING)
    @Column(nullable = false)
    UserType userType;

    @Column (nullable = false)
    private String userName;

    @Column (nullable = false)
    private String password;

    public User() {}

    public User(Integer userId,String name,String surname,String userBirthDay,UserType userType,String userName,String password){
        this.userId=userId;
        this.name=name;
        this.surname=surname;
        this.userBirthDay=userBirthDay;
        this.userType=userType;
        this.userName=userName;
        this.password=password;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public String getUserBirthDay() {
        return userBirthDay;
    }

    public void setUserBirthDay(String userBirthDay) {
        this.userBirthDay = userBirthDay;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getUserId() {
       return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

}
