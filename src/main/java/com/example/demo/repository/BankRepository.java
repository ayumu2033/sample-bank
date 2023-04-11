package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Bank;

public interface BankRepository extends JpaRepository<Bank, Long> {
  Optional<Bank> findByUserId(Long id);
}
