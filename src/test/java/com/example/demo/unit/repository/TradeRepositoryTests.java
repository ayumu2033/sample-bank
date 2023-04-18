package com.example.demo.unit.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;

import com.example.demo.entity.Trade;
import com.example.demo.repository.TradeRepository;
import com.example.demo.testdata.TestData;

import jakarta.persistence.EntityManager;

@DataJpaTest
public class TradeRepositoryTests {
  @Autowired
  private TradeRepository tradeRepository;
  
  @Autowired
  private EntityManager entityManager;

  @Test
  public void エンティティ作成() throws Exception{
    Trade testTrade = TestData.createTestNoIdTrade1();
    Trade trade = tradeRepository.save(testTrade);
    assertNotNull(trade.getTradeId());
    assertEquals(testTrade.getDescription(), trade.getDescription());
    assertEquals(testTrade.getSourceUserId(), trade.getSourceUserId());
    assertEquals(testTrade.getTargetUserId(), trade.getTargetUserId());
    assertEquals(testTrade.getAmount(), trade.getAmount());
  }
  
  @Test
  public void 取引履歴取得() throws Exception{
    entityManager.persist(TestData.createTestNoIdTrade1());
    entityManager.persist(TestData.createTestNoIdTrade2());
    entityManager.persist(TestData.createTestNoIdTrade1());
    entityManager.persist(TestData.createTestNoIdTrade2());
    entityManager.persist(TestData.createTestNoIdTrade1());
    
    Page<Trade> trades = tradeRepository.findByTargetUserIdOrSourceUserIdOrderByCreatedTimeDescTradeIdDesc(1L, 1L, null);
    assertEquals(5, trades.getSize());
  }
}
