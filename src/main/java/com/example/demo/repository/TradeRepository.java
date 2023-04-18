package com.example.demo.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Trade;

public interface TradeRepository extends JpaRepository<Trade,Long>{
  Page<Trade> findByTargetUserId(Long id, Pageable page);
  Page<Trade> findBySourceUserId(Long id, Pageable page);
  Page<Trade> findByTargetUserIdOrSourceUserIdOrderByCreatedTimeDesc(Long targetId,Long sourceId, Pageable page);
}
