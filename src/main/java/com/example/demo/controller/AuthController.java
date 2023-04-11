package com.example.demo.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.entity.UserInfo;
import com.example.demo.form.LoginForm;
import com.example.demo.form.SignupForm;
import com.example.demo.service.UserInfoService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class AuthController {

  @Autowired
  private UserInfoService userInfoService;

  @GetMapping("/login")
  public String getLogin(LoginForm loginForm) {
    return "login.html";
  }

  @GetMapping("/signup")
  public String getSignup(SignupForm signupForm) {
    return "signup.html";
  }

  @PostMapping(value="/signup")
  public String signup(SignupForm signupForm, Model model, HttpServletRequest request) throws ServletException{
      try {
        userInfoService.register(signupForm.getUsername(), signupForm.getPassword(), UserInfo.Authority.ROLE_USER);
        request.login(signupForm.getUsername(), signupForm.getPassword());
      } catch (DuplicateKeyException e) {
          model.addAttribute("signupError", "同じユーザー名が使用されています");
          return "signup.html";
      } catch (DataAccessException e) {
          model.addAttribute("signupError", "ユーザー登録に失敗しました");
          return "signup.html";
      } catch(ServletException e){
        throw e;
      }
      return "redirect:/mypage";
  }
}
