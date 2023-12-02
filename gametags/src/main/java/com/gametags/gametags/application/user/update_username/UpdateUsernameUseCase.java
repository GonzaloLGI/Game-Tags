package com.gametags.gametags.application.user.update_username;

import java.security.Principal;

import com.gametags.gametags.domain.model.User;
import com.gametags.gametags.domain.services.UserService;
import com.gametags.gametags.infrastructure.JWTGenerator;
import com.gametags.gametags.infrastructure.dtos.AuthResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class UpdateUsernameUseCase {

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private JWTGenerator jwtGenerator;

  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private UserService service;

  public AuthResponseDTO updateUsername(String newUsername, String existingPassword, Principal principal) {
    String username = principal.getName();
    log.info("EXISTING USERNAME: " + username);
    User existingUser = service.findOneUserByUsername(username);
    User changedUser = User.builder()
                    .id(existingUser.getId())
                            .roles(existingUser.getRoles())
                                    .email(existingUser.getEmail())
                                            .country(existingUser.getCountry())
                                                    .username(newUsername)
                                                            .password(existingUser.getPassword())
                                                                    .build();
    if(passwordEncoder.matches(existingPassword,existingUser.getPassword())){
      log.info("New username: " + newUsername);
      service.updateUser(changedUser);
      Authentication authentication = new UsernamePasswordAuthenticationToken(changedUser.getUsername(), existingPassword);
      SecurityContextHolder.getContext().setAuthentication(authentication);
      authenticationManager.authenticate(authentication);
      String token = jwtGenerator.generateToken(authentication);
      return new AuthResponseDTO(token, changedUser.getUsername());
    }else{
      throw new RuntimeException("Password is not correct");
    }
  }
}
