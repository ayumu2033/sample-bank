package com.example.demo.unit.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.controller.BankInfoController;
import com.example.demo.entity.Bank;
import com.example.demo.entity.UserInfo;
import com.example.demo.service.BankInfoService;
import com.example.demo.service.Impl.BankInfoServiceImpl;
import com.example.demo.testdata.TestData;

public class BankInfoContollerTests {

  @Test
  void モデルに値が入る() throws Exception {
    BankInfoService bankInfoService = Mockito.mock(BankInfoServiceImpl.class);;
    BankInfoController bankInfoController = new BankInfoController(bankInfoService);
    Bank testBank = TestData.createTestBank1();
    when(bankInfoService.findOne(anyLong())).thenReturn(Optional.of(testBank));

    ModelAndView mav = new ModelAndView();
    UserInfo userInfo = TestData.createTestUser1();
    ModelAndView result = bankInfoController.index(mav, userInfo);
    
    assertEquals("mypage.html", result.getViewName());
    ModelMap model = result.getModelMap();
    assertEquals(userInfo, model.get("user"));
    assertEquals(testBank, model.get("bankInfo"));
  }
}
