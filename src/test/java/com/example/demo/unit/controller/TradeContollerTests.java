package com.example.demo.unit.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.controller.BankInfoController;
import com.example.demo.controller.TradeController;
import com.example.demo.entity.Bank;
import com.example.demo.entity.Trade;
import com.example.demo.entity.UserInfo;
import com.example.demo.service.BankInfoService;
import com.example.demo.service.TradeService;
import com.example.demo.service.Impl.BankInfoServiceImpl;
import com.example.demo.service.Impl.TradeServiceImpl;
import com.example.demo.testdata.TestData;

public class TradeContollerTests {

  @Test
  void モデルに値が入る() throws Exception {
    TradeService bankInfoService = Mockito.mock(TradeServiceImpl.class);;
    TradeController tradeController = new TradeController(bankInfoService);
    List<Trade> content = new ArrayList<>();
    content.add(TestData.createTestNoIdTrade1());
    content.add(TestData.createTestNoIdTrade2());
    content.add(TestData.createTestNoIdTrade1());
    content.add(TestData.createTestNoIdTrade2());
    content.add(TestData.createTestNoIdTrade1());
    Page<Trade> page = new PageImpl<Trade>(content);

    when(bankInfoService.findAll(anyLong(),any())).thenReturn(page);
    
    ModelAndView mav = new ModelAndView();
    UserInfo userInfo = TestData.createTestUser1();
    ModelAndView result = tradeController.index(mav, userInfo, null);
    
    assertEquals("trade_history.html", result.getViewName());
    ModelMap model = result.getModelMap();
    assertEquals(userInfo, model.get("user"));
    assertEquals(page, model.get("trades"));
  }
}
