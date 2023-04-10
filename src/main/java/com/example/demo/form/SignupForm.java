package com.example.demo.form;

public class SignupForm {
  private String username;
  public String getUsername() {
    return username;
  }
  public void setUsername(String username) {
    this.username = username;
  }
  private String password;

  

  public void setPassword(String password) {
    this.password = password;
  }
  public SignupForm() {
  }

  public SignupForm(String username, String password) {
    this.username = username;
    this.password = password;
  }
  
  public String getPassword() {
    return password;
  }
}
