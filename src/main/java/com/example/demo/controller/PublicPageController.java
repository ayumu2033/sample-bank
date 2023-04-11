package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PublicPageController {
  @GetMapping("/")
  String index(){
    return "index.html";
  }
}
