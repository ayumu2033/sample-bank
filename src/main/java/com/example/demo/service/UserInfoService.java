package com.example.demo.service;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.example.demo.entity.UserInfo;

public interface UserInfoService extends UserDetailsService{
  public UserInfo register(String username, String rawPassword, UserInfo.Authority userRoll) throws DataAccessException;
  
}
