package com.example.demo.form;

public class LoginForm {
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
  public LoginForm() {
  }

  public LoginForm(String username, String password) {
    this.username = username;
    this.password = password;
  }
  
  public String getPassword() {
    return password;
  }
}
