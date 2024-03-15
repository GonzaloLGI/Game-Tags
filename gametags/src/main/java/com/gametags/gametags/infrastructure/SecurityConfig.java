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

  private final String ROLE_USER = "ROLE_USER";

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
        .requestMatchers("/auth/**").permitAll()
            .requestMatchers("/videogame/latest").permitAll()
            .requestMatchers("/user/**").hasAuthority(ROLE_USER)
            .requestMatchers(HttpMethod.POST,"/comment").hasAuthority(ROLE_USER)
            .requestMatchers(HttpMethod.DELETE,"/comment/{id}").hasAuthority(ROLE_USER)
            .requestMatchers("/comment/videogame/user").hasAuthority(ROLE_USER)
            .requestMatchers("/comment/severity/user").hasAuthority(ROLE_USER)
            .requestMatchers("/comment/category/user").hasAuthority(ROLE_USER)
            .requestMatchers("/videogame/classification/{videoGameName}").hasAuthority(ROLE_USER)
            .requestMatchers(HttpMethod.DELETE,"/videogame/{id}").hasAuthority(ROLE_USER)
            .requestMatchers(HttpMethod.POST,"/videogame").hasAuthority(ROLE_USER)
            .requestMatchers(HttpMethod.PUT,"/videogame").hasAuthority(ROLE_USER)
            .requestMatchers(HttpMethod.DELETE,"/classification/{id}").hasAuthority(ROLE_USER)
            .requestMatchers(HttpMethod.POST,"/videogame/platforms").permitAll()
        .anyRequest().permitAll()
        .and()
        .httpBasic();
    http.cors();
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
