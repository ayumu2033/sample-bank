package com.example.demo.combine.service;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entity.Trade;
import com.example.demo.entity.UserInfo;
import com.example.demo.repository.TradeRepository;
import com.example.demo.service.TradeService;
import com.example.demo.service.UserInfoService;

import jakarta.persistence.EntityManager;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class TradeServiceTests {
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private TradeService tradeService;

    @Test
    @Transactional
    void 作成される() throws Exception {
      UserInfo userInfo = userInfoService.register("user", "password", UserInfo.Authority.ROLE_USER);
      UserInfo userInfo2 = userInfoService.register("user2", "password", UserInfo.Authority.ROLE_USER);
      Long amount = 1000L;
      String description  = "description";
      Trade trade = tradeService.create(userInfo.getUserId(), userInfo2.getUserId(), amount, description);
      assertNotNull(trade.getTradeId());
      assertEquals(userInfo.getUserId(), trade.getTargetUserId());
      assertEquals(userInfo2.getUserId(), trade.getSourceUserId());
      assertEquals(amount, trade.getAmount());
      assertEquals(description, trade.getDescription());
    }

    @Test
    @Transactional
    void 同一ユーザー名を指定して例外が出される() throws Exception {
      
      UserInfo userInfo = userInfoService.register("user", "password", UserInfo.Authority.ROLE_USER);
      Long amount = 1000L;
      String description  = "description";

      assertThrows(IllegalArgumentException.class, ()->{
        tradeService.create(userInfo.getUserId(), userInfo.getUserId(), amount, description);
      });
    }

    @Test
    @Transactional
    void 一覧が取得される() throws Exception {
      UserInfo userInfo = userInfoService.register("user", "password", UserInfo.Authority.ROLE_USER);
      UserInfo userInfo2 = userInfoService.register("user2", "password", UserInfo.Authority.ROLE_USER);
      UserInfo userInfo3 = userInfoService.register("user3", "password", UserInfo.Authority.ROLE_USER);
      Long amount = 1000L;
      String description  = "description";
      tradeService.create(userInfo.getUserId(), userInfo2.getUserId(), amount, description);
      tradeService.create(userInfo2.getUserId(), userInfo.getUserId(), amount, description);
      tradeService.create(userInfo.getUserId(), userInfo2.getUserId(), amount, description);
      tradeService.create(userInfo2.getUserId(), userInfo.getUserId(), amount, description);
      tradeService.create(userInfo.getUserId(), userInfo2.getUserId(), amount, description);

      tradeService.create(userInfo2.getUserId(), userInfo3.getUserId(), amount, description);

      Page<Trade> trades = tradeService.findAll(userInfo.getUserId(), null);
      assertEquals(5, trades.getSize());
      
      Page<Trade> userInfo2Trades = tradeService.findAll(userInfo2.getUserId(), null);
      assertEquals(6, userInfo2Trades.getSize());

      Page<Trade> userInfo3Trades = tradeService.findAll(userInfo3.getUserId(), null);
      assertEquals(1, userInfo3Trades.getSize());
    }
}
