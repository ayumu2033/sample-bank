package com.example.demo.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.entity.UserInfo;

@Controller
public class PublicPageController {
  @GetMapping("/")
  ModelAndView index(ModelAndView mav, @AuthenticationPrincipal UserInfo user){
    mav.setViewName("index.html");
    mav.addObject("user",user );
    return mav;
  }
}
