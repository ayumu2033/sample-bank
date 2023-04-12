package com.example.demo.testdata;

import com.example.demo.entity.Bank;
import com.example.demo.entity.Trade;
import com.example.demo.entity.UserInfo;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TestData {
  
  public static Bank createTestBank1() throws JsonMappingException, JsonProcessingException{
    return TestData.createBankEntity("{\"id\":1,\"userId\":1,\"bankNumber\":\"000001\",\"amount\":0}");
  }

  public static Bank createBankEntity(String data) throws JsonMappingException, JsonProcessingException{
    ObjectMapper mapper = new ObjectMapper();
    return mapper.readValue(data, new TypeReference<Bank>(){});
  }

  public static UserInfo createTestUser1() throws JsonMappingException, JsonProcessingException{
    return TestData.createUserInfoEntity("{\"userId\":1,\"username\":\"user\",\"password\":\"password\",\"authority\":\"ROLE_USER\"}");
  }

  public static UserInfo createUserInfoEntity(String data) throws JsonMappingException, JsonProcessingException{
    ObjectMapper mapper = new ObjectMapper();
    mapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
    return mapper.readValue(data, new TypeReference<UserInfo>(){});
  }

  public static Trade createTestTrade1() throws JsonMappingException, JsonProcessingException{
    return TestData.createTradeEntity("{\"tradeId\":1,\"targetUserId\":1,\"sourceUserId\":2,\"amount\":1000,\"description\":\"description1\"}");
  }

  public static Trade createTestNoIdTrade1() throws JsonMappingException, JsonProcessingException{
    return TestData.createTradeEntity("{\"targetUserId\":1,\"sourceUserId\":2,\"amount\":1000,\"description\":\"description1\"}");
  }
  public static Trade createTestNoIdTrade2() throws JsonMappingException, JsonProcessingException{
    return TestData.createTradeEntity("{\"targetUserId\":2,\"sourceUserId\":1,\"amount\":1000,\"description\":\"description2\"}");
  }

  public static Trade createTradeEntity(String data) throws JsonMappingException, JsonProcessingException{
    ObjectMapper mapper = new ObjectMapper();
    mapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
    return mapper.readValue(data, new TypeReference<Trade>(){});
  }

}
