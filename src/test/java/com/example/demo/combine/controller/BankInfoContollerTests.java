package com.example.demo.combine.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.controller.BankInfoController;
import com.example.demo.entity.Bank;
import com.example.demo.entity.UserInfo;
import com.example.demo.service.BankInfoService;
import com.example.demo.service.UserInfoService;
import jakarta.transaction.Transactional;

@SpringBootTest
@DirtiesContext
public class BankInfoContollerTests {
  @Autowired
  private BankInfoController bankInfoController;
  @Autowired
  private BankInfoService bankInfoService;
  @Autowired
  private UserInfoService userInfoService;

  @Test
  @Transactional
  void モデルに値が入る() throws Exception {
    UserInfo userInfo = userInfoService.register("user", "password", UserInfo.Authority.ROLE_USER);
    System.out.println(userInfo.getUserId()); 
    Optional<Bank> testBank = bankInfoService.findOne(userInfo.getUserId());
    assertTrue(testBank.isPresent());

    ModelAndView mav = new ModelAndView();
    ModelAndView result = bankInfoController.index(mav, userInfo);
    
    assertEquals("mypage.html", result.getViewName());
    ModelMap model = result.getModelMap();
    assertEquals(userInfo, model.get("user"));
    assertEquals(testBank.get(), model.get("bankInfo"));
  }
}
