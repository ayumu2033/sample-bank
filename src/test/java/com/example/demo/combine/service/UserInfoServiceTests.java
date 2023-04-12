package com.example.demo.combine.service;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entity.UserInfo;
import com.example.demo.service.UserInfoService;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class UserInfoServiceTests {
    @Autowired
    private UserInfoService userInfoService;

    @Test
    @Transactional
    void ユーザーIDを指定して作成される() throws Exception {
      String username = "user";
      String rawPassword = "password";
      UserInfo.Authority userRoll = UserInfo.Authority.ROLE_USER;
      UserInfo userInfo = userInfoService.register(username, rawPassword, userRoll);
      assertEquals(username, userInfo.getUsername());
      assertNotEquals(rawPassword, userInfo.getPassword());
      assertNotEquals(0, userInfo.getUserId());
    }

    @Test
    @Transactional
    void 同一ユーザー名を指定して例外が出される() throws Exception {
      String username = "user";
      String rawPassword = "password";
      UserInfo.Authority userRoll = UserInfo.Authority.ROLE_USER;
      userInfoService.register(username, rawPassword, userRoll);
      assertThrows(DataIntegrityViolationException.class, ()->{
        userInfoService.register(username, rawPassword, userRoll);
      });
    }
}
