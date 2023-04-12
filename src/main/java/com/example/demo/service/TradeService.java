package com.example.demo.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.demo.entity.Trade;

public interface TradeService {
  public Trade create(Long targetId, Long sourceId, Long amount, String description);
  public Page<Trade> findAll(Long userId, Pageable page);
}
