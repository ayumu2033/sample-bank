package com.example.demo.unit.service;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import com.example.demo.entity.Trade;
import com.example.demo.repository.TradeRepository;
import com.example.demo.service.TradeService;
import com.example.demo.service.Impl.TradeServiceImpl;
import com.example.demo.testdata.TestData;

public class TradeServiceTests {

    @Test
    void 作成できる() throws Exception {
      TradeRepository tradeRepository = Mockito.mock(TradeRepository.class);;
      TradeService tradeService = new TradeServiceImpl(tradeRepository);
      Trade testTrade = TestData.createTestTrade1();
      when(tradeRepository.save(any())).thenReturn(testTrade);

      Trade trade = tradeService.create(
          testTrade.getTargetUserId(),
          testTrade.getSourceUserId(),
          testTrade.getAmount(),
          testTrade.getDescription()
        );
      
      assertEquals(testTrade.getDescription(), trade.getDescription());
      assertEquals(testTrade.getSourceUserId(), trade.getSourceUserId());
      assertEquals(testTrade.getTargetUserId(), trade.getTargetUserId());
      assertEquals(testTrade.getAmount(), trade.getAmount());
    }
}
