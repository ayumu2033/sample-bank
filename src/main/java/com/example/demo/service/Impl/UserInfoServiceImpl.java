package com.example.demo.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entity.UserInfo;
import com.example.demo.repository.UserInfoRepository;
import com.example.demo.service.BankInfoService;
import com.example.demo.service.UserInfoService;

@Service
public class UserInfoServiceImpl implements UserInfoService {
  @Autowired
  private UserInfoRepository userInfoRepo;
  @Autowired
  private PasswordEncoder passwordEncoder;
  @Autowired
  private BankInfoService bankInfoService;

  @Override
  @Transactional
  public UserInfo register(String username, String rawPassword, UserInfo.Authority userRoll){
    UserInfo user = new UserInfo(username, passwordEncoder.encode(rawPassword), userRoll);
    userInfoRepo.save(user);
    bankInfoService.create(user.getUserId());
    return user;
  }
  

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    if (username == null || "".equals(username)) {
      throw new UsernameNotFoundException("Username is empty");
    }

    UserInfo userInfo = userInfoRepo.findByUsername(username);
    if (userInfo == null) {
      throw new UsernameNotFoundException("User not found for name: " + username);
    }

    return userInfo;
  }
}
