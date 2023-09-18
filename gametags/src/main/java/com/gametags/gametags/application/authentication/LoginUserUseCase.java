package com.gametags.gametags.application.authentication;

import com.gametags.gametags.domain.model.LoginInput;
import com.gametags.gametags.infrastructure.JWTGenerator;
import com.gametags.gametags.infrastructure.dtos.AuthResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class LoginUserUseCase {

  @Autowired
  private JWTGenerator jwtGenerator;

  @Autowired
  private AuthenticationManager authenticationManager;

  public AuthResponseDTO loginUser(LoginInput input) {
    Authentication authentication = authenticationManager
        .authenticate(new UsernamePasswordAuthenticationToken(input.getUserName(), input.getPassword()));
    SecurityContextHolder.getContext().setAuthentication(authentication);
    log.info("User logged" + SecurityContextHolder.getContext().getAuthentication().getName());
    String token = jwtGenerator.generateToken(authentication);
    return new AuthResponseDTO(token);
  }
}
