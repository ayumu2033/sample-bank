package com.example.demo.config;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import static org.springframework.security.config.Customizer.withDefaults;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
		.formLogin(withDefaults())
		.httpBasic(withDefaults())
    .authorizeHttpRequests(ahr -> ahr
      .requestMatchers("/").permitAll()
      .requestMatchers("/css/**").permitAll()
      .requestMatchers("/js/**").permitAll()
      .requestMatchers("/img/**").permitAll()
      .requestMatchers("/**.html").permitAll()
      //他のリンクは全て認証が必要である。
      .anyRequest().authenticated()  );
    
    return http.build();
  }
}