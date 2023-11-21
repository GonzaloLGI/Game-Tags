package com.gametags.gametags.infrastructure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity(debug=true)
public class SecurityConfig {

  @Autowired
  private JWTAuthEntryPoint authEntryPoint;

  @Autowired
  private CustomUserDetailsService customUserDetailsService;

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
        .csrf().disable()
        .exceptionHandling()
        .authenticationEntryPoint(authEntryPoint)
        .and()
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        .authorizeHttpRequests()
//        .requestMatchers("/auth/**").permitAll()
//            .requestMatchers("/user/**").hasAuthority("ROLE_USER")
//            .requestMatchers("/comment").hasAuthority("ROLE_USER")
//            .requestMatchers("/comment/{id}").hasAuthority("ROLE_USER")
//            .requestMatchers("/comment/videogame/user").hasAuthority("ROLE_USER")
//            .requestMatchers("/comment/severity/user").hasAuthority("ROLE_USER")
//            .requestMatchers("/comment/category/user").hasAuthority("ROLE_USER")
//            .requestMatchers("/videogame/classification/{videoGameName}").hasAuthority("ROLE_USER")
//            .requestMatchers("/videogame/{id}").hasAuthority("ROLE_USER")
//            .requestMatchers("/videogame").hasAuthority("ROLE_USER")
//            .requestMatchers("/classification/**").hasAuthority("ROLE_USER")
//        .anyRequest().authenticated()
            .anyRequest().permitAll()
        .and()
        .httpBasic();
    http.addFilterBefore(authenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    return http.build();
  }

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
      throws Exception {
    return authenticationConfiguration.getAuthenticationManager();
  }

  @Bean
  PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public JWTAuthenticationFilter authenticationFilter(){
    return new JWTAuthenticationFilter();
  }
}
