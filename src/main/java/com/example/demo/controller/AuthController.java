package com.example.demo.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.form.SignupForm;

import com.example.demo.service.UserDetailsService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class AuthController {

    @Autowired
    private UserDetailsService userDetailsService;
    
    
  @GetMapping("/login")
  public String getLogin(SignupForm signupForm, @AuthenticationPrincipal UserDetails userDetail) {
    // TODO:ログイン時にリダイレクト
    if(userDetail != null){
        return "redirect:/";
    }
    return "signup";
  }

  @GetMapping("/signup")
  public String getSignup(SignupForm signupForm) {
      return "signup";
  }

  @PostMapping(value="/signup")
  public String signup(SignupForm signupForm, Model model, HttpServletRequest request) throws ServletException{
      try {
        userDetailsService.register(signupForm.getUsername(), signupForm.getPassword(), "USER");
        request.login(signupForm.getUsername(), signupForm.getPassword());
      } catch (DuplicateKeyException e) {
          model.addAttribute("signupError", "同じユーザー名が使用されています");
          return "signup";
      } catch (DataAccessException e) {
          model.addAttribute("signupError", "ユーザー登録に失敗しました");
          return "signup";
      } catch(ServletException e){
        throw e;
      }
      return "redirect:/mypage";
  }
}
