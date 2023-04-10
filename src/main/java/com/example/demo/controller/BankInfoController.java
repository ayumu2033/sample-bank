package com.example.demo.controller;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class BankInfoController {
  @GetMapping("/mypage")
  ModelAndView index(ModelAndView mav, @AuthenticationPrincipal UserDetails userDetail){
    mav.setViewName("mypage.html");
    
    mav.addObject("user",userDetail );
    return mav;
  }
}
