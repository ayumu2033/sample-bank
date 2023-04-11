package com.example.demo.controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {
  @GetMapping("/admin")
  String index(){
    return "admin.html";
  }

  @GetMapping("/admin/approval")
  String approval(){
    return "approval.html";
  }
}
