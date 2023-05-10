package com.beso.security.services;

import com.beso.entity.User;
import com.beso.entity.UserType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Component
public class UserDetailsImpl implements UserDetails {

    @Autowired
    private User user;

    public UserDetailsImpl(User user){
        this.user=user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities(){
        UserType userType=user.getUserType();
        List<SimpleGrantedAuthority> authorities=new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(userType.toString()));
        return authorities;
    }

    public Integer getId(){return user.getUserId();}

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUserName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
