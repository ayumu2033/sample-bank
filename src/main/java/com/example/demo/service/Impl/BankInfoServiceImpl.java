package com.example.demo.service.Impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Bank;
import com.example.demo.repository.BankRepository;
import com.example.demo.service.BankInfoService;

@Service
public class BankInfoServiceImpl implements BankInfoService{
  @Autowired
  private BankRepository bankRepository;

  @Override
  public Bank create(Long userId) throws DataAccessException {
    String bankNumber = String.format("%06d", bankRepository.count()+1);
    Bank bank = new Bank(userId, bankNumber);
    return bankRepository.save(bank);
  }

  @Override
  public Optional<Bank> findOne(Long userId) throws DataAccessException {
    return bankRepository.findByUserId(userId);
  }
  
}
