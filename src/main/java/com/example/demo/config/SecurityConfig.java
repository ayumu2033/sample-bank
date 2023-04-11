package com.example.demo.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;

import com.example.demo.exception.MyAccessDeniedHandler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import static org.springframework.security.config.Customizer.withDefaults;

import javax.sql.DataSource;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
		.formLogin(withDefaults())
		.formLogin(fl -> fl
      .loginPage("/login")
      .defaultSuccessUrl("/mypage"))
		.httpBasic(withDefaults())
    .authorizeHttpRequests(ahr -> ahr
      .requestMatchers("/").permitAll()
      .requestMatchers("/signup","/login").anonymous()
      .requestMatchers("/css/**","/js/**","/img/**","/**.html").permitAll()
      .anyRequest().authenticated())
    .exceptionHandling(exh->exh.accessDeniedHandler(new MyAccessDeniedHandler()));
    
    return http.build();
  }

  @Bean
  public DataSource dataSource() {
      return new EmbeddedDatabaseBuilder()
          .setType(EmbeddedDatabaseType.HSQL)
          .addScript(JdbcDaoImpl.DEFAULT_USER_SCHEMA_DDL_LOCATION)
          .build();
  }


  @Bean
  public PasswordEncoder passwordEncoder() {
      return new BCryptPasswordEncoder();
  }
    
  @Bean
  public UserDetailsManager users(DataSource dataSource) {
      UserDetails user = User.builder()
          .username("user")
          .password(passwordEncoder().encode("password"))
          .roles("USER")
          .build();
      JdbcUserDetailsManager users = new JdbcUserDetailsManager(dataSource);
      users.createUser(user);
      return users;
  }
}