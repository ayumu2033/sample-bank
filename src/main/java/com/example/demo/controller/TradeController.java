package com.example.demo.controller;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.entity.UserInfo;
import com.example.demo.service.TradeService;

@Controller
public class TradeController {
  private final TradeService tradeService;
  
  public TradeController(TradeService tradeService) {
    this.tradeService = tradeService;
  }

  @GetMapping("/tradeHistory")
  public ModelAndView index(ModelAndView mav, @AuthenticationPrincipal UserInfo user, @PageableDefault Pageable pageable){
    mav.setViewName("trade_history.html");
    mav.addObject("user",user );
    mav.addObject("trades",tradeService.findAll(user.getUserId(), pageable));
    return mav;
  }
}
