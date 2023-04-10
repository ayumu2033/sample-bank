package com.example.demo.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;

import com.example.demo.service.UserDetailsService;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{
  
  @Autowired
  private UserDetailsManager userDetailsManager;
  @Autowired
  private PasswordEncoder passwordEncoder;


  @Override
  public UserDetails register(String username, String rawPassword, String userRoll){
    UserDetails user = User.builder()
          .username(username)
          .password(passwordEncoder.encode(rawPassword))
          .roles(userRoll)
          .build();
    userDetailsManager.createUser(user);
    return user;
  }
  
}
