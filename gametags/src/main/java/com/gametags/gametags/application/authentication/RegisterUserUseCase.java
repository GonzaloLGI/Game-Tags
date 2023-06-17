package com.gametags.gametags.application.authentication;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import com.gametags.gametags.domain.model.RegisterInput;
import com.gametags.gametags.domain.model.User;
import com.gametags.gametags.domain.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class RegisterUserUseCase {

  @Autowired
  private UserService userService;

  public User registerUser(RegisterInput input) throws Exception {
    if (!Objects.isNull(userService.findOneUserByUsername(input.getUserName()).getId())) {
      throw new Exception("User already existing");
    }
    return userService.createUser(User.builder()
        .id(UUID.randomUUID())
        .password(input.getPassword())
        .username(input.getUserName())
        .roles(List.of("USER"))
        .build());
  }
}
