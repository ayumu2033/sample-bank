package com.example.demo.unit.service;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import com.example.demo.entity.Bank;
import com.example.demo.repository.BankRepository;
import com.example.demo.service.BankInfoService;
import com.example.demo.service.Impl.BankInfoServiceImpl;
import com.example.demo.testdata.TestData;

public class BankInfoServiceTests {

    @Test
    void ユーザーIDを指定して取得される() throws Exception {
      BankRepository bankRepository = Mockito.mock(BankRepository.class);;
      BankInfoService bankInfoService = new BankInfoServiceImpl(bankRepository);
      when(bankRepository.findByUserId(anyLong())).thenReturn(Optional.of(TestData.createTestBank1()));
      Optional<Bank> result = bankInfoService.findOne(1L);
      assertTrue(result.isPresent());
      Bank bank = result.get();
      assertEquals("000001", bank.getBankNumber());
      assertEquals(1, bank.getUserId());
    }

    @Test
    void ユーザーIDを指定して作成される() throws Exception {
      BankRepository bankRepository = Mockito.mock(BankRepository.class);
      BankInfoService bankInfoService = new BankInfoServiceImpl(bankRepository);
      when(bankRepository.count()).thenReturn(0L);
      when(bankRepository.save(any())).thenReturn(TestData.createTestBank1());
      Bank bank = bankInfoService.create(1L);
      assertEquals("000001", bank.getBankNumber());
      assertEquals(0, bank.getAmount());
    }
}
