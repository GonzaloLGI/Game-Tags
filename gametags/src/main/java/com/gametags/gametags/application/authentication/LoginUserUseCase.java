package com.gametags.gametags.application.authentication;

import com.gametags.gametags.domain.model.LoginInput;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class LoginUserUseCase {

  @Autowired
  private AuthenticationManager authenticationManager;

  public String loginUser(LoginInput input) {
    System.out.println("INPUT: " + input.getUserName() + " " + input.getPassword());
    log.debug("INPUT: " + input.getUserName() + " " + input.getPassword());
    Authentication authentication = authenticationManager
        .authenticate(new UsernamePasswordAuthenticationToken(input.getUserName(), input.getPassword()));
    SecurityContextHolder.getContext().setAuthentication(authentication);
    return "User logged";
  }
}
