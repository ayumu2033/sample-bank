package com.example.demo.service.Impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Trade;
import com.example.demo.repository.TradeRepository;
import com.example.demo.service.TradeService;

@Service
public class TradeServiceImpl implements TradeService{
  private final TradeRepository tradeRepository;

  public TradeServiceImpl(TradeRepository tradeRepository) {
    this.tradeRepository = tradeRepository;
  }

  @Override
  public Trade create(Long targetId, Long sourceId, Long amount, String description) {
    if(targetId == sourceId){
      throw new IllegalArgumentException("自分へ送金はできません");
    }
    Trade trade = new Trade(targetId, sourceId, amount, description);
    return tradeRepository.save(trade);
  }
  
  @Override
  public Page<Trade> findAll(Long userId, Pageable page) {
    return tradeRepository.findByTargetUserIdOrSourceUserId(userId,userId, page);
  }

}
