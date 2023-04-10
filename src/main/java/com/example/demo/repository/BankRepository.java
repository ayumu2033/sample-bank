package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Bank;

public interface BankRepository extends JpaRepository<Bank, Long> {

  Page<Bank> findAll(Pageable pageable);

  Optional<Bank> findById(Long id);

}
