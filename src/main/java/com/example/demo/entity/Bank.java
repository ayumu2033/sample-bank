package com.example.demo.entity;

import java.io.Serializable;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Bank implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Long getId() {
        return id;
    }


  @Column(nullable = false)
  private Long userId;

    public Long getUserId() {
        return userId;
    }


  @Column(nullable = false)
  private String bankNumber;

    public String getBankNumber() {
        return bankNumber;
    }


  @Column(nullable = false)
  private Long amount = 0L;

    // ... additional members, often include @OneToMany mappings

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    protected Bank() {
        // no-args constructor required by JPA spec
        // this one is protected since it should not be used directly
    }

    public Bank(Long userId, String bankNumber) {
        this.userId = userId;
        this.bankNumber = bankNumber;
    }

}
