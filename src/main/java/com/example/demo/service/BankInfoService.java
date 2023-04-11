package com.example.demo.service;
import java.util.Optional;

import org.springframework.dao.DataAccessException;

import com.example.demo.entity.Bank;

public interface BankInfoService {
  public Bank create(Long userId) throws DataAccessException;
  public Optional<Bank> findOne(Long userId) throws DataAccessException;
}
