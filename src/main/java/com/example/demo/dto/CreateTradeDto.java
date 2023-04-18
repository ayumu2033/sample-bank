package com.example.demo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public class CreateTradeDto {

  public CreateTradeDto(@NotNull Long targetUserId, @NotNull @PositiveOrZero Long amount,
      @NotBlank String description) {
    this.targetUserId = targetUserId;
    this.amount = amount;
    this.description = description;
  }

  public CreateTradeDto() {
  }

  @NotNull
  private Long targetUserId;
  
  @NotNull
  @PositiveOrZero
  private Long amount;
  
  @NotBlank
  private String description;

  public Long getTargetUserId() {
    return targetUserId;
  }

  public void setTargetUserId(Long targetUserId) {
    this.targetUserId = targetUserId;
  }

  public Long getAmount() {
    return amount;
  }

  public void setAmount(Long amount) {
    this.amount = amount;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  
}
