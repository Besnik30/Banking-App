package com.beso.security.services;

import com.beso.entity.User;
import com.beso.repository.UserRepository;
import com.beso.service.ErrorMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        Optional<User>userOptional=userRepository.getUserByUsername(username);

        if(userOptional.isEmpty()){
            throw new UsernameNotFoundException(ErrorMessage.USERNAME_NOT_FOUND.getErrorMessage());
        }
        else {
            return new UserDetailsImpl(userOptional.get());
        }
    }
}
