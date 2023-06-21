package com.gametags.gametags.application.user.update_username;

import java.security.Principal;

import com.gametags.gametags.domain.model.User;
import com.gametags.gametags.domain.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class UpdateUsernameUseCase {

  @Autowired
  private UserService service;

  public User updateUsername(String newUsername, Principal principal) {
    String username = principal.getName();
    System.out.println("DETALLES: " + username);
    return null;
  }
}
