package com.example.demo.exception;

import java.io.IOException;
import java.io.PrintWriter;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class MyAccessDeniedHandler implements AccessDeniedHandler {
    
  @Override
  public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
      if (response.isCommitted()) {
          return;
      }
      response.sendRedirect("/?error="+ HttpServletResponse.SC_FORBIDDEN);
  }
}