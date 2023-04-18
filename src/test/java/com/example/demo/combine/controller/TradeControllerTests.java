package com.example.demo.combine.controller;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.controller.TradeController;
import com.example.demo.dto.CreateTradeDto;
import com.example.demo.entity.Trade;
import com.example.demo.entity.UserInfo;
import com.example.demo.repository.TradeRepository;
import com.example.demo.service.TradeService;
import com.example.demo.service.UserInfoService;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class TradeControllerTests {
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private TradeController tradeController;
    @Autowired
    private TradeService tradeService;
    @Autowired
    private TradeRepository tradeRepository;

    @Test
    @Transactional
    void 作成される() throws Exception {
      UserInfo userInfo = userInfoService.register("user", "password", UserInfo.Authority.ROLE_USER);
      UserInfo userInfo2 = userInfoService.register("user2", "password", UserInfo.Authority.ROLE_USER);
      Long amount = 1000L;
      String description  = "description";
      var createDto = new CreateTradeDto(userInfo2.getUserId(),amount, description);
      var preCount = tradeRepository.count();
      tradeController.post(createDto, userInfo);
      var afterCount = tradeRepository.count();

      assertEquals(preCount+1,afterCount);
    }

    @Test
    @Transactional
    void 同一ユーザー名を指定して例外が出される() throws Exception {
      
      UserInfo userInfo = userInfoService.register("user", "password", UserInfo.Authority.ROLE_USER);
      Long amount = 1000L;
      String description  = "description";
      var createDto = new CreateTradeDto(userInfo.getUserId(),amount, description);

      assertThrows(IllegalArgumentException.class, ()->{
        tradeController.post(createDto, userInfo);
      });
    }

    @Test
    @Transactional
    @SuppressWarnings("unchecked")
    void 一覧が取得される() throws Exception {
      UserInfo userInfo = userInfoService.register("user", "password", UserInfo.Authority.ROLE_USER);
      UserInfo userInfo2 = userInfoService.register("user2", "password", UserInfo.Authority.ROLE_USER);
      UserInfo userInfo3 = userInfoService.register("user3", "password", UserInfo.Authority.ROLE_USER);
      Long amount = 1000L;
      String description  = "description";
      var postTrades = new ArrayList<Trade>();
      postTrades.add(tradeService.create(userInfo.getUserId(), userInfo2.getUserId(), amount, description));
      postTrades.add(tradeService.create(userInfo2.getUserId(), userInfo.getUserId(), amount, description));
      postTrades.add(tradeService.create(userInfo.getUserId(), userInfo2.getUserId(), amount, description));
      postTrades.add(tradeService.create(userInfo2.getUserId(), userInfo.getUserId(), amount, description));
      postTrades.add(tradeService.create(userInfo.getUserId(), userInfo2.getUserId(), amount, description));

      postTrades.add(tradeService.create(userInfo2.getUserId(), userInfo3.getUserId(), amount, description));

      var mav = tradeController.index(new ModelAndView(), userInfo, null);
      var trades = (Page<Trade>) mav.getModelMap().get("trades");
      assertEquals(5, trades.getSize());
      
      var mav2  = tradeController.index(new ModelAndView(),userInfo2, null);
      var trades2 = (Page<Trade>) mav2.getModelMap().get("trades");
      assertEquals(6, trades2.getSize());

      var mav3  = tradeController.index(new ModelAndView(),userInfo3, null);
      var trades3 = (Page<Trade>) mav3.getModelMap().get("trades");
      assertEquals(1, trades3.getSize());
    }
}
