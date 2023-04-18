package com.example.demo.entity;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;

@Entity
public class Trade {
  @Id
  @GeneratedValue
  private Long tradeId;

  @Column(nullable = false)
  private Long targetUserId;

  @Column(nullable = false)
  private Long sourceUserId;
  
  @Column(nullable = false)
  private Long amount;
  
  @Column(nullable = false)
  private String description;
  
  @Column(nullable = false)
  private Date createdTime;

  public Trade(Long targetUserId, Long sourceUserId, Long amount, String description) {
    this.targetUserId = targetUserId;
    this.sourceUserId = sourceUserId;
    this.amount = amount;
    this.description = description;
  }

  Trade() {
    // for framework
  }

  public Long getTradeId() {
    return tradeId;
  }

  public Long getTargetUserId() {
    return targetUserId;
  }

  public Long getSourceUserId() {
    return sourceUserId;
  }

  public Long getAmount() {
    return amount;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }
  
  public Date getCreatedTime() {
    return createdTime;
  }

  @PrePersist
  public void onPrePersist() {
      this.createdTime = new Date();
  }
}
