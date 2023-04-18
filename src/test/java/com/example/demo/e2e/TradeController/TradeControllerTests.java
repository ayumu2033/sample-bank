package com.example.demo.e2e.TradeController;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Date;
import java.util.stream.LongStream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import com.example.demo.controller.TradeController;
import com.example.demo.entity.Trade;
import com.example.demo.entity.UserInfo;
import com.example.demo.service.TradeService;
import com.example.demo.service.UserInfoService;

import jakarta.persistence.EntityManager;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class TradeControllerTests {

  @Autowired
  WebApplicationContext  webApplicationContext ;
  @Autowired
  UserInfoService userInfoService;
  @Autowired
  TradeService tradeService;
  @Autowired
  TradeController tradeController;
  @Autowired
  EntityManager entityManager;

  MockMvc mockMvc;

  @BeforeEach
  void setup() {
    this.mockMvc = MockMvcBuilders
    .webAppContextSetup(webApplicationContext)
    .apply(SecurityMockMvcConfigurers.springSecurity())
    .build();
  }
  
  @Test
  @Transactional
  @SuppressWarnings({"all"})
  void 全件のうち最初の一部が取得される() throws Exception {
    UserInfo userInfo = userInfoService.register("user", "password", UserInfo.Authority.ROLE_USER);
    UserInfo userInfo2 = userInfoService.register("user2", "password", UserInfo.Authority.ROLE_USER);
    Long amount = 1000L;
    String description  = "description";
    LongStream.range(1, 50).forEach(id->{
      tradeService.create(userInfo.getUserId(), userInfo2.getUserId(), amount, description);
    });

    var mav = this.mockMvc.perform(get("/tradeHistory").with(user(userInfo))).andExpect(model().attributeExists("trades")).andReturn();
    var trades = (Page<Trade>) mav.getModelAndView().getModelMap().get("trades");
    assertEquals(25, trades.getNumberOfElements());
  }
    
  @Test
  @Transactional
  void 全件のうち最初の一部が取得される試験版() throws Exception {
    UserInfo userInfo = userInfoService.register("user", "password", UserInfo.Authority.ROLE_USER);
    UserInfo userInfo2 = userInfoService.register("user2", "password", UserInfo.Authority.ROLE_USER);
    Long amount = 1000L;
    String description  = "description";
    LongStream.range(1, 51).forEachOrdered(id->{
      tradeService.create(userInfo.getUserId(), userInfo2.getUserId(), amount, description);
    });
    Trade lastTrade;
    {
      var builder = entityManager.getCriteriaBuilder();
      var query = entityManager.getCriteriaBuilder().createQuery(Trade.class);
      var root = query.from(Trade.class);
      query.where(
        builder.or(
          builder.equal(root.<Long>get("targetUserId"), userInfo.getUserId()),
          builder.equal(root.<Long>get("sourceUserId"), userInfo.getUserId())
        )
      );
      query.orderBy(
        builder.desc(root.<Date>get("createdTime")),
        builder.desc(root.<Long>get("tradeId"))
      );
    
      int pageSize = 25;
      var list = entityManager.createQuery(query)
        .setMaxResults(pageSize)
        .getResultList();
      lastTrade = list.get(list.size()-1);
      
      var array = list.stream().map(Trade::getTradeId).toList();
      System.out.println(array);

      System.out.println(lastTrade.getTradeId());
      System.out.println(lastTrade.getCreatedTime());
      
    }

    {
      var builder = entityManager.getCriteriaBuilder();
      var query = entityManager.getCriteriaBuilder().createQuery(Trade.class);
      var root = query.from(Trade.class);
      Date lastCreatedTime= lastTrade.getCreatedTime();
      Long lastTradeId= lastTrade.getTradeId();
      query.where(
        builder.and(
          builder.lessThanOrEqualTo(root.<Date>get("createdTime"), lastCreatedTime),
          builder.not(
            builder.and(
              builder.equal(root.<Date>get("createdTime"), lastCreatedTime),
              builder.greaterThanOrEqualTo(root.<Long>get("tradeId"), lastTradeId)
            )
          ),
          builder.or(
            builder.equal(root.<Long>get("targetUserId"), userInfo.getUserId()),
            builder.equal(root.<Long>get("sourceUserId"), userInfo.getUserId())
          )
        )
      );
      query.orderBy(
        builder.desc(root.<Date>get("createdTime")),
        builder.desc(root.<Long>get("tradeId"))
      );
    
      int pageSize = 25;
      var list = entityManager.createQuery(query)
        .setMaxResults(pageSize)
        .getResultList();
      var array = list.stream().map(Trade::getTradeId).toList();
      System.out.println(array);
    }
  }

  
  @Test
  @Transactional
  @SuppressWarnings({"all"})
  void 全件のうち指定の一部が取得される() throws Exception {
    UserInfo userInfo = userInfoService.register("user", "password", UserInfo.Authority.ROLE_USER);
    UserInfo userInfo2 = userInfoService.register("user2", "password", UserInfo.Authority.ROLE_USER);
    Long amount = 1000L;
    String description  = "description";
    var arr = new ArrayList<Long>();
    LongStream.range(1, 51).forEachOrdered(id->{
      tradeService.create(userInfo.getUserId(), userInfo2.getUserId(), amount, description);
      arr.add(id);
    });
    Pageable page;
    {
      var result = this.mockMvc.perform(
          get("/tradeHistory")
            .with(user(userInfo)))
        .andExpect(model().attributeExists("trades"))
        .andReturn();
      var trades = (Page<Trade>) result.getModelAndView().getModelMap().get("trades");
      assertEquals(25, trades.getSize());
      var tradeIds = trades.stream().map(Trade::getTradeId).toList();
      assertEquals(25, tradeIds.size());
      assertEquals(50, tradeIds.get(0));
      assertEquals(49, tradeIds.get(1));
      page = trades.nextPageable();
    }

    {
      var result = this.mockMvc.perform(
          get("/tradeHistory")
            .with(user(userInfo))
            .queryParam("page",Integer.toString(page.getPageNumber()))
            .queryParam("size",Integer.toString(page.getPageSize()))
            )
        .andExpect(model().attributeExists("trades"))
        .andReturn();
      var trades = (Page<Trade>) result.getModelAndView().getModelMap().get("trades");
      assertEquals(25, trades.getSize());
      var tradeIds = trades.stream().map(Trade::getTradeId).toList();
      assertEquals(25, tradeIds.size());
      assertEquals(25, tradeIds.get(0));
      assertEquals(24, tradeIds.get(1));
    }
  }
}
