package com.example.demo.combine.service;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.annotation.DirtiesContext;
import com.example.demo.entity.Bank;
import com.example.demo.entity.UserInfo;
import com.example.demo.repository.UserInfoRepository;
import com.example.demo.service.BankInfoService;
import com.example.demo.testdata.TestData;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class BankInfoServiceTests {
    @Autowired
    private BankInfoService bankInfoService;
    @Autowired
    private UserInfoRepository userInfoRepository;

    @Test
    void ユーザーIDを指定して作成される() throws Exception {
      UserInfo userinfo = userInfoRepository.save(TestData.createTestUser1());
      Long userId = userinfo.getUserId();
      System.out.println(userId);
      Bank bank = bankInfoService.create(userId);
      assertNotNull(bank.getBankNumber());
      assertEquals(0, bank.getAmount());
      assertNotEquals(0, bank.getId());
    }

    @Test
    void ユーザーIDを指定して取得できる() throws Exception {
      UserInfo userinfo = userInfoRepository.save(TestData.createTestUser1());
      Long userId = userinfo.getUserId();
      System.out.println(userId);

      Bank bank = bankInfoService.create(userId);
      Optional<Bank> result = bankInfoService.findOne(userId);

      assertTrue(result.isPresent());
      Bank f_bank = result.get();

      assertNotNull(f_bank.getBankNumber());
      assertEquals(userId, f_bank.getUserId());
      assertEquals(0, f_bank.getAmount());
      assertEquals(bank.getId(), f_bank.getId());
    }
    
    @Test
    void 同一ユーザーIDを指定して例外が出される() throws Exception {
      UserInfo userinfo = userInfoRepository.save(TestData.createTestUser1());
      Long userId = userinfo.getUserId();
      System.out.println(userId);
      bankInfoService.create(userId);
      assertThrows(DataIntegrityViolationException.class,  ()->{
      bankInfoService.create(userId);
      });
    }
}
