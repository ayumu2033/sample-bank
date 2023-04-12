package com.example.demo.controller;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.entity.UserInfo;
import com.example.demo.service.BankInfoService;

@Controller
public class BankInfoController {
  private final BankInfoService bankInfoService;

  public BankInfoController(BankInfoService bankInfoService) {
    this.bankInfoService = bankInfoService;
  }

  @GetMapping("/mypage")
  public ModelAndView index(ModelAndView mav, @AuthenticationPrincipal UserInfo user){
    mav.setViewName("mypage.html");
    
    mav.addObject("user",user );
    mav.addObject("bankInfo",bankInfoService.findOne(user.getUserId()).get());
    return mav;
  }
}
