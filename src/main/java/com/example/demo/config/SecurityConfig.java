package com.example.demo.config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.example.demo.exception.MyAccessDeniedHandler;
import com.example.demo.service.UserInfoService;

import static org.springframework.security.config.Customizer.withDefaults;

import javax.sql.DataSource;

@EnableWebSecurity
@Configuration
@ConditionalOnWebApplication
public class SecurityConfig {
  @Autowired
  private ApplicationContext context;

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    UserInfoService userInfoService = context.getBean(UserInfoService.class);

    http
      .userDetailsService(userInfoService)
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
    
  // @Bean
  // public UserDetailsManager users(DataSource dataSource) {
  //   UserDetails user = User.builder()
  //       .username("user")
  //       .password(passwordEncoder().encode("password"))
  //       .roles("USER")
  //       .build();
  //   JdbcUserDetailsManager users = new JdbcUserDetailsManager(dataSource);
  //   users.createUser(user);
  //   return users;
  // }
}