package com.example.demo.service;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserDetailsService {
  public UserDetails register(String username, String rawPassword, String userRoll) throws DataAccessException;
}
