package com.gametags.gametags.application.authentication;

import com.gametags.gametags.domain.model.LoginInput;
import com.gametags.gametags.domain.model.User;
import com.gametags.gametags.domain.services.UserService;
import com.gametags.gametags.infrastructure.JWTGenerator;
import com.gametags.gametags.infrastructure.dtos.AuthResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.NoSuchElementException;

@Component
@Slf4j
public class LoginUserUseCase {

  @Autowired
  private JWTGenerator jwtGenerator;

  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private UserService userService;

  public AuthResponseDTO loginUser(LoginInput input) {
    User user = userService.findOneUserByUsername(input.getUserName());
    if(ObjectUtils.isNotEmpty(user)){
      Authentication authentication = authenticationManager
              .authenticate(new UsernamePasswordAuthenticationToken(input.getUserName(), input.getPassword(), List.of(new SimpleGrantedAuthority("ROLE_USER"))));
      SecurityContextHolder.getContext().setAuthentication(authentication);
      log.info("User logged" + SecurityContextHolder.getContext().getAuthentication().getName());
      String token = jwtGenerator.generateToken(authentication);
      return new AuthResponseDTO(token);
    }else{
      throw new NoSuchElementException("The user doesn't exist");
    }
  }
}
